package com.yapu.archive.action;

/**
 * 查询action
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;

import com.google.gson.Gson;
import com.yapu.archive.entity.DynamicExample;
import com.yapu.archive.entity.SysTable;
import com.yapu.archive.entity.SysTemplet;
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.entity.SysTree;
import com.yapu.archive.entity.SysTreeExample;
import com.yapu.archive.entity.SysTreeExample.Criteria;
import com.yapu.archive.service.itf.IDynamicService;
import com.yapu.archive.service.itf.ISearchService;
import com.yapu.archive.service.itf.ITreeService;
import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.service.itf.IAccountService;
import com.yapu.system.service.itf.IOrgService;
import com.yapu.system.util.Constants;

public class SearchAction extends BaseAction{
private static final long serialVersionUID = 5004188718476484590L;
	
	private IAccountService accountService;
	private IOrgService orgService;
	private ITreeService treeService;
	private ISearchService searchService;
	
	private IDynamicService dynamicService;
	
	private String treeid;
	private String searchTxt;
	private String tableType;
	
	private String tablename;
	private String selectid;
	
	//总行数
	private int rowCount = 0;
	//总页数
	private int pages = 0;
	//当前页
	private int currentPage = 0;
	//每页记录数
	private int pageSize = 20;
	
	/**
	 * 取当前session帐户能查询的档案树节点。给页面显示用
	 * @return
	 * @throws IOException
	 */
	public String getSearchTree() throws IOException {
		PrintWriter out = this.getPrintWriter();
		
		List<SysTree> treeList = new ArrayList<SysTree>();
		treeList = getTreeNode();
		List list = new ArrayList();
		if (null != treeList || treeList.size() > 0) {
			for (SysTree tree :treeList) {
//				if ("0".equals(tree.getParentid())) {
					HashMap map = new HashMap();
					map.put("treeid", tree.getTreeid());
					map.put("treename", tree.getTreename());
					map.put("parentid", tree.getParentid());
					map.put("treetype", tree.getTreetype());
					map.put("treenode", tree.getTreenode());
					list.add(map);
//				}
			}
		}
		else {
			out.write("error");
			return null;
		}
		Gson gson = new Gson();
		out.write(gson.toJson(list));
		
		return null;
	}
	/**
	 * 获取当前帐户所能管理的树节点
	 * @return
	 */
	private List<SysTree> getTreeNode() {
		//读取session里的登录帐户
		SysAccount account = (SysAccount) this.getHttpSession().getAttribute(Constants.user_in_session);
		if (null == account) {
			//TODO 这里因为session过期，以后处理
			return null;
		}
		
		List<SysTree> treeList = new ArrayList<SysTree>();
		//如果当前登录帐户是admin，则显示全部。
		if (!"admin".equals(account.getAccountcode())) {
			//读取帐户本身的树节点范围
			
			treeList = accountService.getTree(account.getAccountid());
			
			//判断帐户是否有档案树节点操作
			if (null == treeList || treeList.size() < 1) {
				//如果未设置帐户自己的树节点范围，读取所属组的范围
				SysOrg org = accountService.getAccountOfOrg(account);
				treeList = orgService.getTree(org.getOrgid());
				if (null == treeList || treeList.size() <= 0 ) {
//					out.write("error");
					return null;
				}
			}
		}
		else {
			SysTreeExample ex = new SysTreeExample();
			//ex.createCriteria().andParentidEqualTo("0");
			treeList = treeService.selectByWhereNotPage(ex);
		}
		return treeList;
	}
	
	/**
	 * 得到档案类型跟节点的子节点
	 * @param treeid
	 * @return
	 */
	private List<SysTree> getChildTree(String treeid) {
		//得到节点实体
		SysTree tree = treeService.selectByPrimaryKey(treeid);
		
		SysTreeExample te = new SysTreeExample();
		Criteria criteria = te.createCriteria();
		criteria.andTreenodeLike(tree.getTreenode() + "%");
		criteria.andTreetypeEqualTo("W");
		List<SysTree> childTreeList = treeService.selectByWhereNotPage(te);
		
		return childTreeList;
	}
	
	private List getSearchNumberByTreeid(String treeid) throws IOException {
		List<HashMap> resultList = new ArrayList<HashMap>();
		//得到树节点对应的表集合
		List<SysTable> tableList = treeService.getTreeOfTable(treeid);
		List<SysTree> childTreeList = getChildTree(treeid);
		
		//得到所有table的信息。包括表名和字段，放入map里。多个表放入list里
		List<HashMap> tableInfoList = new ArrayList<HashMap>();
		for (SysTable table:tableList) {
			List<SysTempletfield> fieldsList = treeService.getTreeOfTempletfield(treeid, table.getTabletype());
			List<String> searchFieldsList = new ArrayList<String>();
			List<String> tmpList = new ArrayList<String>();
			for (int i=0;i<fieldsList.size();i++) {
				tmpList.add(fieldsList.get(i).getEnglishname().toLowerCase());
				if (fieldsList.get(i).getIssearch() == 1) {
					searchFieldsList.add(fieldsList.get(i).getEnglishname().toLowerCase());
				}
			}
			String[] fields = new String[searchFieldsList.size()];
			searchFieldsList.toArray(fields);
			HashMap map = new HashMap();
			map.put("tablename", table.getTablename());
			map.put("fields", fields);
			map.put("tabletype", table.getTabletype());
			tableInfoList.add(map);
		}
		
		//取得当前帐户权限范围，该档案类型下的子节点
		List<SysTree> treeList = getTreeNode();
		List<SysTree> tmpTreeList = new ArrayList<SysTree>();
		for (int i=0;i<childTreeList.size();i++) {
			for (int j=0;j<treeList.size();j++) {
				if (childTreeList.get(i).getTreeid().equals(treeList.get(j).getTreeid())) {
					tmpTreeList.add(childTreeList.get(i));
				}
			}
		}
		
		for (SysTree tree :tmpTreeList) {
			HashMap map = new HashMap();
			map.put("treeid", tree.getTreeid());
			String ajStr = "";
			String wjStr = "";
			for (int i=0;i<tableInfoList.size();i++) {
//				ajStr = "";
//				wjStr = "";
				HashMap tmp = tableInfoList.get(i);
				HashMap<String,Integer> numMap = searchService.searchNumber(tmp.get("tablename").toString(), (String[])tmp.get("fields"),searchTxt,tree.getTreeid());
				if (tableInfoList.size() == 2) {
					if ("01".equals(tmp.get("tabletype"))) {
						if (null == numMap) {
							ajStr = "案卷级(0)";
						}
						else {
							ajStr = "案卷级(" + numMap.get(tree.getTreeid()).toString() + ")";
						}
					}
					else if("02".equals(tmp.get("tabletype"))) {
						if (null == numMap) {
							wjStr = "文件级(0)";
						}
						else {
							wjStr = "文件级(" + numMap.get(tree.getTreeid()).toString() + ")";
						}
					}
				}
				else {
					if (null == numMap) {
						wjStr = "文件级(0)";
					}
					else {
						wjStr = "文件级(" + numMap.get(tree.getTreeid()).toString() + ")";
					}
				}
			}
			if ("".equals(ajStr)) {
				map.put("num", wjStr);
			}
			else {
				map.put("num", ajStr + " " + wjStr);
			}
			resultList.add(map);
		}
		
		return resultList;
	}
	/**
	 * 根据树节点id和搜索关键字，返回该节点下案卷和文件查询出的数量
	 * @return
	 * @throws IOException 
	 */
	public String getSearchNumber() throws IOException {
		
		PrintWriter out = this.getPrintWriter();
		
		List resultList = new ArrayList();
		if ("all".equals(treeid.toLowerCase())) {
			//获得当前帐户的树节点范围
			List<SysTree> treeList = new ArrayList<SysTree>();
			treeList = getTreeNode();
			for(SysTree tree:treeList) {
				//TODO 这里有问题。档案根节点下。现在查parent为0的。但0下面的，有可能当前帐户也没有权限。不能都查出来。
				if ("0".equals(tree.getParentid())) {
					List a = getSearchNumberByTreeid(tree.getTreeid());
					for (int i=0;i<a.size();i++) {
						resultList.add(a.get(i));
					}
				}
			}
		}
		else {
			List a = getSearchNumberByTreeid(treeid);
			for (int i=0;i<a.size();i++) {
				resultList.add(a.get(i));
			}
		}
		Gson gson = new Gson();
		System.out.println(gson.toJson(resultList));
		
		out.write(gson.toJson(resultList));
		return null;
	}
	
	public String search() throws IOException, ParseException {
		PrintWriter out = this.getPrintWriter();
		HashMap bb = new HashMap();
		//根据treeid来执行不同的查询
		if ("all".equals(treeid.toLowerCase())) {
			//获得当前帐户的树节点范围
			List<SysTree> treeList = new ArrayList<SysTree>();
			treeList = getTreeNode();
			
			if (treeList.size() <= 0) {
				return "";
			}
			for (SysTree tree :treeList) {
				if (!"0".equals(tree.getParentid())) {
					if ("W".equals(tree.getTreetype())) {
						bb = searchData(tree);
						if (((List)bb.get("DATA")).size() > 0) {
							break;
						}
					}
				}
			}
		}
		else {
			SysTree tree = treeService.selectByPrimaryKey(treeid);
			if ("0".equals(tree.getParentid())) {
				//获得当前帐户的树节点范围
				List<SysTree> treeList = new ArrayList<SysTree>();
				treeList = getTreeNode();
				
				//获得该节点下的子节点
				List<SysTree> childList = getChildTree(tree.getTreeid());
				
				//得到当前帐户能管理的子节点。
				List<SysTree> tmpList = new ArrayList<SysTree>();
				for (int i=0;i<childList.size();i++) {
					for (int j=0;j<treeList.size();j++) {
						if (childList.get(i).getTreeid().equals(treeList.get(j).getTreeid())) {
							tmpList.add(childList.get(i));
						}
					}
				}
				
				for (SysTree tmp : tmpList) {
					bb = searchData(tmp);
					if (((List)bb.get("DATA")).size() > 0) {
						break;
					}
				}
			}
			else {
				bb = searchData(tree);
			}
		}
		
		List result = new ArrayList();
		result.add(bb);
		Gson gson = new Gson();
//		result = searchData(tree);
		out.write(gson.toJson(result));
		
		return null;
	}
	
	private HashMap searchData(SysTree tree) throws IOException {
		HashMap resultMap = new HashMap();
		
		//得到节点对应的模板。用来判断档案类型
		SysTemplet templet = treeService.getTreeOfTemplet(tree.getTreeid());
		//得到树节点对应的表集合
		List<SysTable> tableList = treeService.getTreeOfTable(tree.getTreeid());
		
		SysTable tableAj = new SysTable();
		SysTable tableWj = new SysTable();
		
		for (SysTable tmp : tableList) {
			if ("01".equals(tmp.getTabletype())) {
				tableAj = tmp;
			}
			else if ("02".equals(tmp.getTabletype())){
				tableWj = tmp;
			}
		}
		//判断模板类型
		List<SysTempletfield> fieldsList = new ArrayList<SysTempletfield>();
		if ("A".equals(templet.getTemplettype())) {
			fieldsList = treeService.getTreeOfTempletfield(tree.getTreeid(), "01");
			if ("".equals(tableType)) {
				if ("0".equals(tree.getParentid())) {
					resultMap = searchService.search(tableAj.getTablename(),fieldsList,searchTxt,currentPage,pageSize);
				}
				else {
					resultMap = searchService.search(tableAj.getTablename(),fieldsList,searchTxt,tree.getTreeid(),currentPage,pageSize);
				}
				
				resultMap.put("TABLETYPE", "01");
				resultMap.put("TEMPLETTYPE", "A");
				resultMap.put("FIELDS", fieldsList);
				resultMap.put("TABLELABEL", tableAj.getTablelabel() + " - " + tree.getTreename());
				//table   表名。用来查看单个档案时，向后台传 表名和记录id就能找到记录返回前台。
				resultMap.put("TABLENAME", tableAj.getTablename());
				if (((List)resultMap.get("DATA")).size() <= 0 ) {
					fieldsList = treeService.getTreeOfTempletfield(tree.getTreeid(), "02");
					if ("0".equals(tree.getParentid())) {
						resultMap = searchService.search(tableWj.getTablename(),fieldsList,searchTxt,currentPage,pageSize);
					}
					else {
						resultMap = searchService.search(tableWj.getTablename(),fieldsList,searchTxt,tree.getTreeid(),currentPage,pageSize);
					}
					resultMap.put("TABLETYPE", "02");
					resultMap.put("TEMPLETTYPE", "A");
					resultMap.put("FIELDS", fieldsList);
					resultMap.put("TABLELABEL", tableWj.getTablelabel() + " - " + tree.getTreename());
					resultMap.put("TABLENAME", tableWj.getTablename());
				}
			}
			else if ("01".equals(tableType)) {
				if ("0".equals(tree.getParentid())) {
					resultMap = searchService.search(tableAj.getTablename(),fieldsList,searchTxt,currentPage,pageSize);
				}
				else {
					resultMap = searchService.search(tableAj.getTablename(),fieldsList,searchTxt,tree.getTreeid(),currentPage,pageSize);
				}
				
				resultMap.put("TABLETYPE", "01");
				resultMap.put("TEMPLETTYPE", "A");
				resultMap.put("FIELDS", fieldsList);
				resultMap.put("TABLELABEL", tableAj.getTablelabel() + " - " + tree.getTreename());
				resultMap.put("TABLENAME", tableAj.getTablename());
			}
			else if ("02".equals(tableType)) {
				fieldsList = treeService.getTreeOfTempletfield(tree.getTreeid(), "02");
				if ("0".equals(tree.getParentid())) {
					resultMap = searchService.search(tableWj.getTablename(),fieldsList,searchTxt,currentPage,pageSize);
				}
				else {
					resultMap = searchService.search(tableWj.getTablename(),fieldsList,searchTxt,tree.getTreeid(),currentPage,pageSize);
				}
				resultMap.put("TABLETYPE", "02");
				resultMap.put("TEMPLETTYPE", "A");
				resultMap.put("FIELDS", fieldsList);
				resultMap.put("TABLELABEL", tableWj.getTablelabel() + " - " + tree.getTreename());
				resultMap.put("TABLENAME", tableWj.getTablename());
			}
			else {
				
			}
			
		}
		else if ("F".equals(templet.getTemplettype())){
			fieldsList = treeService.getTreeOfTempletfield(tree.getTreeid(), "01");
			if ("0".equals(tree.getParentid())) {
				resultMap = searchService.search(tableAj.getTablename(),fieldsList,searchTxt,currentPage,pageSize);
			}
			else {
				resultMap = searchService.search(tableAj.getTablename(),fieldsList,searchTxt,tree.getTreeid(),currentPage,pageSize);
			}
			
			resultMap.put("TABLETYPE", "01");
			resultMap.put("TEMPLETTYPE", "F");
			resultMap.put("FIELDS", fieldsList);
			resultMap.put("TABLELABEL", tableAj.getTablelabel() + " - " + tree.getTreename());
			resultMap.put("TABLENAME", tableAj.getTablename());
		}
		else {
			return resultMap;
		}
//		if (((List)resultMap.get("DATA")).size() > 0) {
			resultMap.put("TREEID", tree.getTreeid());
			resultMap.put("CURRENTPAGE", currentPage);
//		}
		
		return resultMap;
	}
	
	private HashMap getData(SysTree tree,SysTable table,List fieldsList) throws IOException {
		
		HashMap map = new HashMap();
		if ("0".equals(tree.getParentid())) {
			map = searchService.search(table.getTablename(),fieldsList,searchTxt,currentPage,pageSize);
//			result = (List) tmpMap.get("DATA");
		}
		else {
//			result = searchService.search(table.getTablename(),fieldsList,searchTxt, tree.getTreeid(),currentPage,pageSize);
		}
		return null;
	}
	
	public String getSelectData() throws IOException {
		
		PrintWriter out = this.getPrintWriter();
		
		if (null == tablename || "".equals(tablename)) {
			return "";
		}
		if (null == selectid || "".equals(selectid)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(tablename).append(" where id='").append(selectid).append("'");
		
		DynamicExample de = new DynamicExample();
		DynamicExample.Criteria criteria = de.createCriteria();
		criteria.andEqualTo("id", selectid);
		de.setTableName(tablename);
		List list = dynamicService.selectByExample(de);
		
		Gson gson = new Gson();
		out.write(gson.toJson(list));
		
		return null;
	}
	
	
	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
	public void setOrgService(IOrgService orgService) {
		this.orgService = orgService;
	}
	public void setTreeService(ITreeService treeService) {
		this.treeService = treeService;
	}
	public String getSearchTxt() {
		return searchTxt;
	}
	public void setSearchTxt(String searchTxt) {
		this.searchTxt = searchTxt;
	}
	public void setSearchService(ISearchService searchService) {
		this.searchService = searchService;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getTreeid() {
		return treeid;
	}
	public void setTreeid(String treeid) {
		this.treeid = treeid;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public void setDynamicService(IDynamicService dynamicService) {
		this.dynamicService = dynamicService;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getSelectid() {
		return selectid;
	}
	public void setSelectid(String selectid) {
		this.selectid = selectid;
	}
}

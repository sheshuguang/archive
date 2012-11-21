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
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.entity.SysTree;
import com.yapu.archive.entity.SysTreeExample;
import com.yapu.archive.service.itf.IDynamicService;
import com.yapu.archive.service.itf.ISearchService;
import com.yapu.archive.service.itf.ITreeService;
import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.service.itf.IAccountService;
import com.yapu.system.service.itf.IOrgService;
import com.yapu.system.util.Constants;

public class SearchAction extends BaseAction {
	private static final long serialVersionUID = 5004188718476484590L;
	
	private IAccountService accountService;
	private IOrgService orgService;
	private ITreeService treeService;
	private ISearchService searchService;
	
	private String searchType;
	private String searchTxt;

	/**
	 * 取当前session帐户能查询的档案类型
	 * @return
	 * @throws IOException
	 */
	public String getTemplet() throws IOException {
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
	
	public String search() throws IOException, ParseException {
		
		PrintWriter out = this.getPrintWriter();
		//获得当前帐户的树节点范围
		List<SysTree> treeList = new ArrayList<SysTree>();
		treeList = getTreeNode();
		HashMap map  = new HashMap();
		if ("all".equals(searchType)) {
			
		}
		else {
			//得到树节点对应的表集合
			List<SysTable> tableList = treeService.getTreeOfTable(searchType);
			for (SysTable table:tableList) {
				List<SysTempletfield> fieldsList = treeService.getTreeOfTempletfield(searchType, table.getTabletype());
				List<String> searchFieldsList = new ArrayList<String>();
				List<String> tmpList = new ArrayList<String>();
				for (int i=0;i<fieldsList.size();i++) {
					tmpList.add(fieldsList.get(i).getEnglishname().toLowerCase());
					if (fieldsList.get(i).getIssearch() == 1) {
						searchFieldsList.add(fieldsList.get(i).getEnglishname().toLowerCase());
					}
				}
				String[] fields = new String[searchFieldsList.size()];
				for(int i=0;i<searchFieldsList.size();i++) {
					fields[i] = searchFieldsList.get(i);
				}
				List result = searchService.search(table.getTablename(), fields,tmpList,searchTxt);
				if (null != result && result.size() >0) {
					map.put(table.getTablename(), result);
				}
			}
			List list = new ArrayList();
			for(int i=0;i<tableList.size();i++) {
				list.add(map.get(tableList.get(i).getTablename()));
			}
			Gson gson = new Gson();
			out.write(gson.toJson(list));
		}
		
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
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
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
	
	
}

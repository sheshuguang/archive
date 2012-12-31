package com.yapu.archive.action;

/**
 * 电子全文检索
 * author wangf
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.yapu.archive.entity.SysDocserver;
import com.yapu.archive.entity.SysDocserverExample;
import com.yapu.archive.entity.SysTree;
import com.yapu.archive.entity.SysTreeExample;
import com.yapu.archive.entity.SysTreeExample.Criteria;
import com.yapu.archive.service.itf.IDocserverService;
import com.yapu.archive.service.itf.ISearchService;
import com.yapu.archive.service.itf.ITreeService;
import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.service.itf.IAccountService;
import com.yapu.system.service.itf.IOrgService;
import com.yapu.system.util.Constants;

public class SearchFileAction extends BaseAction {

	// 传递的变量
	private String keyword;
	private String treeid;

	// 总行数
	private int rowCount = 0;
	// 总页数
	private int pages = 0;
	// 当前页
	private int currentPage = 0;
	// 每页记录数
	private int pageSize = 2;

	private IAccountService accountService;
	private IOrgService orgService;
	private ITreeService treeService;
	private ISearchService searchService;

	private IDocserverService docServerService;

	/**
	 * 全文检索入口
	 * 
	 * @return
	 * @throws IOException
	 */
	public String search() throws IOException {
		PrintWriter out = this.getPrintWriter();
		HashMap bb = new HashMap();
		
		List<SysTree> tmpList = new ArrayList<SysTree>();
		
		//取得当前开启的服务器
		SysDocserverExample sde = new SysDocserverExample();
		SysDocserverExample.Criteria criteria = sde.createCriteria();
		criteria.andServerstateEqualTo(1);
		List<SysDocserver> docserverList = docServerService.selectByWhereNotPage(sde);
		SysDocserver docserver = docserverList.get(0);
		
		

		// 根据treeid来执行不同的查询
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
						tmpList.add(tree);
					}
				}
			}
			
			bb = searchService.search(keyword, docserver.getDocserverid(), tmpList, currentPage, pageSize);
			
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
				for (int i=0;i<childList.size();i++) {
					for (int j=0;j<treeList.size();j++) {
						if (childList.get(i).getTreeid().equals(treeList.get(j).getTreeid())) {
							tmpList.add(childList.get(i));
						}
					}
				}
				bb = searchService.search(keyword, docserver.getDocserverid(), tmpList, currentPage, pageSize);
			}
		}
		
		bb.put("CURRENTPAGE", currentPage);
		List result = new ArrayList();
		result.add(bb);
		Gson gson = new Gson();
		out.write(gson.toJson(result));
		return null;
	}

	/**
	 * 获取当前帐户所能管理的树节点
	 * 
	 * @return
	 */
	private List<SysTree> getTreeNode() {
		// 读取session里的登录帐户
		SysAccount account = (SysAccount) this.getHttpSession().getAttribute(
				Constants.user_in_session);
		if (null == account) {
			// TODO 这里因为session过期，以后处理
			return null;
		}

		List<SysTree> treeList = new ArrayList<SysTree>();
		// 如果当前登录帐户是admin，则显示全部。
		if (!"admin".equals(account.getAccountcode())) {
			// 读取帐户本身的树节点范围

			treeList = accountService.getTree(account.getAccountid());

			// 判断帐户是否有档案树节点操作
			if (null == treeList || treeList.size() < 1) {
				// 如果未设置帐户自己的树节点范围，读取所属组的范围
				SysOrg org = accountService.getAccountOfOrg(account);
				treeList = orgService.getTree(org.getOrgid());
				if (null == treeList || treeList.size() <= 0) {
					// out.write("error");
					return null;
				}
			}
		} else {
			SysTreeExample ex = new SysTreeExample();
			// ex.createCriteria().andParentidEqualTo("0");
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


	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTreeid() {
		return treeid;
	}

	public void setTreeid(String treeid) {
		this.treeid = treeid;
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

	public IAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

	public IOrgService getOrgService() {
		return orgService;
	}

	public void setOrgService(IOrgService orgService) {
		this.orgService = orgService;
	}

	public ITreeService getTreeService() {
		return treeService;
	}

	public void setTreeService(ITreeService treeService) {
		this.treeService = treeService;
	}

	public ISearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(ISearchService searchService) {
		this.searchService = searchService;
	}

	public IDocserverService getDocServerService() {
		return docServerService;
	}

	public void setDocServerService(IDocserverService docServerService) {
		this.docServerService = docServerService;
	}

}

package com.yapu.archive.action;

/**
 * 查询action
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.yapu.archive.entity.DynamicExample;
import com.yapu.archive.entity.SysTable;
import com.yapu.archive.entity.SysTree;
import com.yapu.archive.entity.SysTreeExample;
import com.yapu.archive.service.itf.IDynamicService;
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
	private IDynamicService dynamicService;
	
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
	
	public String search() {
		//获得当前帐户的树节点范围
		List<SysTree> treeList = new ArrayList<SysTree>();
		treeList = getTreeNode();
		
		if ("all".equals(searchType)) {
			
		}
		else {
			//得到树节点对应的表集合
			List<SysTable> tableList = treeService.getTreeOfTable(searchType);
			
			DynamicExample de = new DynamicExample();
	        DynamicExample.Criteria criteria = de.createCriteria();
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
	public void setDynamicService(IDynamicService dynamicService) {
		this.dynamicService = dynamicService;
	}
	
}

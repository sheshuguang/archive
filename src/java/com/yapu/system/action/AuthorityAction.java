package com.yapu.system.action;

/**
 * 权限管理Action。
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yapu.archive.entity.SysAccountTree;
import com.yapu.archive.entity.SysAccountTreeExample;
import com.yapu.archive.entity.SysOrgTree;
import com.yapu.archive.entity.SysOrgTreeExample;
import com.yapu.archive.entity.SysTree;
import com.yapu.archive.entity.SysTreeExample;
import com.yapu.archive.entity.SysTreeTempletExample.Criteria;
import com.yapu.archive.service.itf.ITreeService;
import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.entity.SysRole;
import com.yapu.system.entity.SysRoleExample;
import com.yapu.system.service.itf.IAccountService;
import com.yapu.system.service.itf.IOrgService;
import com.yapu.system.service.itf.IRoleService;

public class AuthorityAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private String orgid;
	private String roleid;
	private String accountid;
	private IOrgService orgService;
	private IRoleService roleService;
	private ITreeService treeService;
	private IAccountService accountService;
	
	private String par;
	
	private String orgTreeid;
	private String filescan = "0";
	private String filedown = "0";
	private String fileprint = "0";
	
	private String accountTreeid;
	
	

	public String getOrgAuthorityInfo() throws IOException {
		
		PrintWriter out = this.getPrintWriter();
		if ("".equals(orgid)) {
			out.write("");
			return null;
		}
		SysOrg org = new SysOrg();
		org.setOrgid(orgid);
		//得到该组下所有帐户
		List<SysAccount> orgOfAccountList = orgService.getOrgOfAccount(org);
		//得到该组的角色
		SysRole orgOfRole = orgService.getOrgOfRole(org);
		
		StringBuffer sb = new StringBuffer();
		sb.append("var accountnum='<font color=red>").append(orgOfAccountList.size() + "</font> 个").append("'");
		if (orgOfRole != null) {
			sb.append(";var rolename='<font color=red>").append(orgOfRole.getRolename()).append("</font>'");
		}
		else {
			sb.append(";var rolename='<font color=red>未设置角色</font>'");
		}
		
		
		out.write(sb.toString());
		
		return null;
	}
	/**
	 * 读取帐户权限信息内容
	 * @return
	 * @throws IOException
	 */
	public String getAccountAuthorityInfo() throws IOException {
		
		PrintWriter out = this.getPrintWriter();
		if (null == accountid || "".equals(accountid)) {
			out.write("");
			return null;
		}
		
		SysAccount account = new SysAccount();
		account.setAccountid(accountid);
		//得到该组的角色
		SysRole role = accountService.getAccountOfRole(account);
		
		StringBuffer sb = new StringBuffer();
		if (role != null) {
			sb.append("var role='<font color=red>").append(role.getRolename()).append("</font>'");
		}
		else {
			sb.append(";var role='<font color=red>未设置角色(继承所属组的角色)</font>'");
		}
		
		out.write(sb.toString());
		
		return null;
	}
	
	public String listRoleForAuthority() throws IOException {
		PrintWriter out = this.getPrintWriter();
		
		SysRoleExample example = new SysRoleExample();
		List<SysRole> rolesList = roleService.selectByWhereNotPage(example);
		StringBuffer sb = new StringBuffer();
		
		Gson gson = new Gson();
		
		sb.append("var rowList = ").append(gson.toJson(rolesList));
		out.write(sb.toString());
		return null;
	}
	
	public String setOrgRole() throws IOException {
		PrintWriter out = this.getPrintWriter();
		
		
		boolean result = false;
		if (null != orgid && null != roleid) {
			List<String> orgIDList = new ArrayList<String>();
			orgIDList.add(orgid);
			result = orgService.updateOrgOfRole(orgIDList, roleid);
		}
		if (result) {
			SysRole role = new SysRole();
			role = roleService.selectByPrimaryKey(roleid);
			out.write("<font color=red>" + role.getRolename() + "</font>");
		}
		else {
			out.write("error");
		}
		
		return null;
	}
	
	public String setAccountRole() throws IOException {
		PrintWriter out = this.getPrintWriter();
		
		
		boolean result = false;
		if (null != accountid && null != accountid) {
			SysAccount account = new SysAccount();
			account.setAccountid(accountid);
			result = accountService.updateAccountOfRole(account, roleid);
		}
		if (result) {
			SysRole role = new SysRole();
			role = roleService.selectByPrimaryKey(roleid);
			out.write("<font color=red>" + role.getRolename() + "</font>");
		}
		else {
			out.write("error");
		}
		
		return null;
	}
	
	public String updateOrgTree() throws IOException {
		PrintWriter out = this.getPrintWriter();
		String result = "更新时出错，请重试或与管理员联系。";
		if (null == orgTreeid || "".equals(orgTreeid)) {
			return null;
		}
		SysOrgTree orgTree = new SysOrgTree();
		//判断节点是夹还是叶子
		orgTree = orgService.getOrgOfTree(orgTreeid);
		//根据treeid 判断节点是夹还是叶子
		SysTree tree;
		if ("0".equals(orgTree.getTreeid())) {
			tree = new SysTree();
			tree.setTreetype("F");
			tree.setTreenode("0");
		}
		else {
			tree = treeService.selectByPrimaryKey(orgTree.getTreeid());
		}
				
		
		SysOrgTree record = new SysOrgTree();
		
		record.setFilescan(Integer.parseInt(filescan));
		record.setFiledown(Integer.parseInt(filedown));
		record.setFileprint(Integer.parseInt(fileprint));
		
		//如果是夹，将夹下的节点都赋予相同的全文权限
		if ("F".equals(tree.getTreetype())) {
			SysTreeExample treeEx = new SysTreeExample();
			String node = tree.getTreenode() + "%";
			SysTreeExample.Criteria criteria = treeEx.createCriteria();
			criteria.andTreenodeLike(node);
			List <SysTree> treeList = treeService.selectByWhereNotPage(treeEx);
			
			SysOrgTreeExample ex = new SysOrgTreeExample();
			SysOrgTreeExample.Criteria cr = ex.createCriteria();
			
			List idList = new ArrayList();
			for (SysTree t : treeList) {
				idList.add(t.getTreeid());
			}
			
			cr.andOrgidEqualTo(orgTree.getOrgid()).andTreeidIn(idList);
			if (orgService.updateOrgOfTree(record, ex) > 0) {
				result = "SUCCESS";
			}
			
		}
		else {
			record.setOrgTreeId(orgTreeid);
			if (orgService.updateOrgOfTree(record) > 0) {
				result = "SUCCESS";
			}
		}
		
		
		out.write(result);
		return null;
	}
	
	public String insertOrgTree() throws IOException {
		PrintWriter out = this.getPrintWriter();
		
		String result = "更新时出错，请重试或与管理员联系。";
		if (null == par || "".equals(par)) {
			SysOrgTreeExample ex = new SysOrgTreeExample();
			ex.createCriteria().andOrgidEqualTo(orgid);
			orgService.deleteOrgOfTree(ex);
			result = "SUCCESS";
			out.write(result);
			return null;
		}
		try {
			Gson gson = new Gson();
			List<SysOrgTree> list = new ArrayList<SysOrgTree>();
			list = gson.fromJson(par, new TypeToken<List<SysOrgTree>>(){}.getType());
			if (list.size() > 0) {
				for (SysOrgTree orgTree : list) {
					orgTree.setOrgTreeId(UUID.randomUUID().toString());
				}
			}
			//首先删除
			SysOrgTreeExample ex = new SysOrgTreeExample();
			ex.createCriteria().andOrgidEqualTo(orgid);
			orgService.deleteOrgOfTree(ex);
			//插入
			orgService.insertOrgOfTree(list);
			result = "SUCCESS";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		out.write(result);
		return null;
	}
	
	public String insertAccountTree() throws IOException {
		PrintWriter out = this.getPrintWriter();
		
		String result = "更新时出错，请重试或与管理员联系。";
		if (null == par || "".equals(par)) {
			SysAccountTreeExample ex = new SysAccountTreeExample();
			ex.createCriteria().andAccountidEqualTo(accountid);
			accountService.deleteAccountOfTree(ex);
			result = "SUCCESS";
			out.write(result);
			return null;
		}
		try {
			Gson gson = new Gson();
			List<SysAccountTree> list = new ArrayList<SysAccountTree>();
			list = gson.fromJson(par, new TypeToken<List<SysAccountTree>>(){}.getType());
			if (list.size() > 0) {
				for (SysAccountTree accountTree : list) {
					accountTree.setAccountTreeId(UUID.randomUUID().toString());
				}
			}
			//首先删除
			SysAccountTreeExample ex = new SysAccountTreeExample();
			ex.createCriteria().andAccountidEqualTo(accountid);
			accountService.deleteAccountOfTree(ex);
			//插入
			accountService.insertAccountOfTree(list);
			result = "SUCCESS";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		out.write(result);
		return null;
	}
	
	public String updateAccountTree() throws IOException {
		PrintWriter out = this.getPrintWriter();
		String result = "更新时出错，请重试或与管理员联系。";
		if (null == accountTreeid || "".equals(accountTreeid)) {
			return null;
		}
		SysAccountTree accountTree = new SysAccountTree();
		//判断节点是夹还是叶子
		accountTree = accountService.getAccountOfTree(accountTreeid);
		//根据treeid 判断节点是夹还是叶子
		SysTree tree;
		if ("0".equals(accountTree.getTreeid())) {
			tree = new SysTree();
			tree.setTreetype("F");
			tree.setTreenode("0");
		}
		else {
			tree = treeService.selectByPrimaryKey(accountTree.getTreeid());
		}
				
		
		SysAccountTree record = new SysAccountTree();
		
		record.setFilescan(Integer.parseInt(filescan));
		record.setFiledown(Integer.parseInt(filedown));
		record.setFileprint(Integer.parseInt(fileprint));
		
		//如果是夹，将夹下的节点都赋予相同的全文权限
		if ("F".equals(tree.getTreetype())) {
			SysTreeExample treeEx = new SysTreeExample();
			String node = tree.getTreenode() + "%";
			SysTreeExample.Criteria criteria = treeEx.createCriteria();
			criteria.andTreenodeLike(node);
			List <SysTree> treeList = treeService.selectByWhereNotPage(treeEx);
			
			SysAccountTreeExample ex = new SysAccountTreeExample();
			SysAccountTreeExample.Criteria cr = ex.createCriteria();
			
			List idList = new ArrayList();
			for (SysTree t : treeList) {
				idList.add(t.getTreeid());
			}
			
			cr.andAccountidEqualTo(accountTree.getAccountid()).andTreeidIn(idList);
			if (accountService.updateAccountOfTree(record, ex) > 0) {
				result = "SUCCESS";
			}
			
		}
		else {
			record.setAccountTreeId(accountTreeid);
			if (accountService.updateAccountOfTree(record) > 0) {
				result = "SUCCESS";
			}
		}
		
		
		out.write(result);
		return null;
	}

	
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public void setOrgService(IOrgService orgService) {
		this.orgService = orgService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getPar() {
		return par;
	}

	public void setPar(String par) {
		this.par = par;
	}

	public String getOrgTreeid() {
		return orgTreeid;
	}

	public void setOrgTreeid(String orgTreeid) {
		this.orgTreeid = orgTreeid;
	}

	public String getFilescan() {
		return filescan;
	}

	public void setFilescan(String filescan) {
		this.filescan = filescan;
	}

	public String getFiledown() {
		return filedown;
	}

	public void setFiledown(String filedown) {
		this.filedown = filedown;
	}

	public String getFileprint() {
		return fileprint;
	}

	public void setFileprint(String fileprint) {
		this.fileprint = fileprint;
	}

	public void setTreeService(ITreeService treeService) {
		this.treeService = treeService;
	}
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
	public String getAccountTreeid() {
		return accountTreeid;
	}
	public void setAccountTreeid(String accountTreeid) {
		this.accountTreeid = accountTreeid;
	}
	
	
}

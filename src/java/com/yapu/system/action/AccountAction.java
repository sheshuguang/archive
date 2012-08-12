package com.yapu.system.action;

/**
 * 帐户相关操作
 * 2011-07-18
 * @author wangf
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yapu.archive.entity.SysAccountTree;
import com.yapu.archive.entity.SysOrgTree;
import com.yapu.archive.entity.SysTree;
import com.yapu.archive.entity.SysTreeExample;
import com.yapu.archive.service.itf.ITreeService;
import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.entity.SysConfig;
import com.yapu.system.entity.SysConfigExample;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.service.itf.IAccountService;
import com.yapu.system.service.itf.IConfigService;
import com.yapu.system.service.itf.IOrgService;
import com.yapu.system.util.MD5;

public class AccountAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private IAccountService accountService;
	private IOrgService orgService;
	private IConfigService configService;
	private ITreeService treeService;
	
	private String accountid;
	private String accountcode;
	private String password;
	private String par;
	private String orgid;
	
	private String selectAccounts;
	private String targetorgid;
	
	
	public String showListAccount() {
		return SUCCESS;
	}
	
	/**
	 * 得到request的帐户组id，返回所属帐户的json
	 * @return
	 */
	public String list() throws IOException {
		//如果得不到帐户组id，返回空字符
		if (null == orgid || "".equals(orgid)) {
			return "";
		}
		PrintWriter out  = this.getPrintWriter();
		
		//获得父节点为nodeId的账户节点
		SysOrg sysOrg = new SysOrg();
		sysOrg.setOrgid(orgid);
		List<SysAccount> accounts =orgService.getOrgOfAccount(sysOrg);
		StringBuffer sb = new StringBuffer();
		sb.append("{\"total\":").append(accounts.size()).append(",\"rows\":[");
		String resultStr = "";
		if(null!=accounts && accounts.size()>0){
			
			for (SysAccount sysAccount : accounts) {
				sb.append("{");
				sb.append("\"accountid\":\""+sysAccount.getAccountid()+"\",");
				sb.append("\"accountcode\":\""+sysAccount.getAccountcode()+"\",");
				if (sysAccount.getAccountstate() == 1) {
					sb.append("\"accountstate\":\"<img alt='1' src='../../images/icons/status_online.png' title='已启用'>\",");
				}
				else {
					sb.append("\"accountstate\":\"<img alt='0' src='../../images/icons/status_offline.png' title='已停用'>\",");
				}
				//sb.append("\"accountstate\":\""+sysAccount.getAccountstate()+"\",");
				sb.append("\"accountmemo\":\""+sysAccount.getAccountmemo()+"\"");
				sb.append("},");
			}
			resultStr = sb.substring(0,sb.length()-1);
			
		}
		else {
			resultStr = sb.toString();
		}
		resultStr += "]}";
		out.write(resultStr);
		return null;
	}
	
	public String accountList() throws IOException {
		PrintWriter out = this.getPrintWriter();
		
		//如果得不到帐户组id，返回空字符
		if (null == orgid || "".equals(orgid)) {
			return "";
		}
		
		//获得父节点为nodeId的账户节点
		SysOrg sysOrg = new SysOrg();
		sysOrg.setOrgid(orgid);
		List<SysAccount> accounts =orgService.getOrgOfAccount(sysOrg);
		StringBuffer sb = new StringBuffer();
		
		Gson gson = new Gson();
		
		sb.append("var rowList = ").append(gson.toJson(accounts));
		out.write(sb.toString());
		return null;
	}
	
	public String save() throws IOException {
		PrintWriter out  = this.getPrintWriter();
		
		String result = "保存完毕。";
		Gson gson = new Gson();
		Map<String, List<SysAccount>> accountMap = new HashMap<String, List<SysAccount>>();
		try {
			accountMap = (Map)gson.fromJson(par, new TypeToken<Map<String, List<SysAccount>>>(){}.getType());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//处理添加的帐户
		List<SysAccount> insertAccountList = accountMap.get("inserted");
		if (insertAccountList.size() > 0) {
			//循环保存添加的帐户
			for (SysAccount sysAccount : insertAccountList) {
				addAccount(sysAccount);
			}
		}
		//处理更新的帐户
		List<SysAccount> updateAccountList = accountMap.get("updated");
		if (updateAccountList.size() > 0) {
			//循环保存添加的帐户
			for (SysAccount sysAccount : updateAccountList) {
				updateAccount(sysAccount);
			}
		}
		//处理删除帐户
		List<SysAccount> delAccountList = accountMap.get("deleted");
		if (delAccountList.size() > 0) {
			//循环删除帐户
			for (SysAccount sysAccount : delAccountList) {
				delAccount(sysAccount.getAccountid());
			}
		}
		out.write(result);
		return null;
	}
	/**
	 * 添加帐户
	 * @param sysAccount
	 * @return
	 */
	private boolean addAccount(SysAccount sysAccount) {
		boolean result = false;
		if (sysAccount != null) {
			//sysAccount.setAccountid(UUID.randomUUID().toString());
//			sysAccount.setOrgBaseID(orgid);
			//读取系统配置表，得到设置的默认密码
			SysConfigExample example = new SysConfigExample();
			example.createCriteria().andConfigkeyEqualTo("PASSWORD");
			
			List<SysConfig> configList = configService.selectByWhereNotPage(example);
			
			if (configList.size() == 1) {
				String pass = MD5.encode(configList.get(0).getConfigvalue());
				sysAccount.setPassword(pass);
			}
			
			if(accountService.insertAccount(sysAccount,orgid)){
				result=true;
			}
		}
		return result;
	}
	/**
	 * 更新帐户
	 * @param sysAccount
	 * @return
	 */
	private boolean updateAccount(SysAccount sysAccount) {
		boolean result = false;
		if (sysAccount != null) {
			if(accountService.updateAccount(sysAccount) > 0){
				result = true;
			}
		}
		return result;
	}
	/**
	 * 删除帐户
	 * @param accountid
	 * @return
	 */
	public boolean delAccount(String accountid) {
		boolean result = false;
		if (null != accountid && !"".equals(accountid)) {
			int num = accountService.deleteAccount(accountid);
			if (num >0) {
				result = true;
			}
		}
		return result;
	}
	/**
	 * 移动帐户
	 * @return
	 */
	public String moveAccount() throws IOException {
		String result = "failure";
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		if (null != selectAccounts && !"".equals(selectAccounts)) {
			selectAccounts = selectAccounts.substring(0, selectAccounts.length() - 1);
			String[] s = selectAccounts.split(",");
			List<String> idList = new ArrayList<String>();
			
			idList = Arrays.asList(s);
			boolean num = accountService.updateAccountOfOrg(idList, targetorgid);
			if (num) {
				result = "success";
			}
		}
		out.write(result);
		return null;
	}
	
	public String getAccountTree() throws IOException {
		PrintWriter out = this.getPrintWriter();
		SysAccount account = new SysAccount();
		account.setAccountid(accountid);
		List<SysAccountTree> treeList = accountService.getAccountOfTree(account);
		Gson gson = new Gson();
		out.write(gson.toJson(treeList));
		return null;
		
	}
	
	/**
	 * 读取档案节点树，全部读取。分别为组和帐户
	 * @return
	 * @throws IOException 
	 */
	public String loadAccountTreeData() throws IOException {
		PrintWriter out  = this.getPrintWriter();
		
		SysAccount account = new SysAccount();
		account.setAccountid(accountid);
		List<SysAccountTree> treeList = accountService.getAccountOfTree(account);
		
		List temp = new ArrayList();
		HashMap map = new HashMap();
		map.put("id", "0");
		map.put("text", "档案节点树");
		map.put("iconCls", "");
		map.put("state", "open");
		List list = getTreeJson("0",treeList);
		map.put("children", list);
		temp.add(map);
		Gson gson = new Gson();
		out.write(gson.toJson(temp));
		return null;
	}
	
	/**  
     * 无限递归获得tree的json字串  
     *   
     * @param parentId  
     *            父权限id 
     * @return  
     */  
    private List getTreeJson(String parentId,List<SysAccountTree> treeList)
    {
    	//得到节点
		SysTreeExample example = new SysTreeExample();
		example.createCriteria().andParentidEqualTo(parentId);
		List<SysTree> trees = treeService.selectByWhereNotPage(example);
		List list = new ArrayList();
		if(null!=trees && trees.size()>0){
			for(int i=0;i<trees.size();i++){
				HashMap temp = new HashMap();
				SysTree tree =(SysTree)trees.get(i);
				String ico = "";
				if ("0".equals(tree.getParentid())) {
					ico = "icon-book-open";
				}
				else if ("F".equals(tree.getTreetype())) {
					ico = "";
				}
				else {
					ico = "icon-page";
				}
				//判断该节点下是否有子节点
				example.clear();
				example.createCriteria().andParentidEqualTo(tree.getTreeid());
				if (treeService.selectByWhereNotPage(example).size() >0) {
					temp.put("id", tree.getTreeid());
					temp.put("text", tree.getTreename());
					temp.put("iconCls", ico);
					temp.put("state", "closed");
					temp.put("children", this.getTreeJson(tree.getTreeid(),treeList));
				}
				else {
					temp.put("id", tree.getTreeid());
					temp.put("text", tree.getTreename());
					temp.put("iconCls", ico);
					if (null != treeList) {
						for (int j=0;j<treeList.size();j++) {
							if (treeList.get(j).getTreeid().equals(tree.getTreeid())) {
								temp.put("checked", true);
							}
						}
					}
				}
				list.add(temp);
			}
		}
        
        return list;
    }
    

	public String getAccountcode() {
		return accountcode;
	}
	public void setAccountcode(String accountcode) {
		this.accountcode = accountcode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
	public void setOrgService(IOrgService orgService) {
		this.orgService = orgService;
	}
	public void setConfigService(IConfigService configService) {
		this.configService = configService;
	}
	public String getPar() {
		return par;
	}
	public void setPar(String par) {
		this.par = par;
	}
//	public void setPublicsAccountService(
//			IPublicsAccountService publicsAccountService) {
//		this.publicsAccountService = publicsAccountService;
//	}
	public String getSelectAccounts() {
		return selectAccounts;
	}
	public void setSelectAccounts(String selectAccounts) {
		this.selectAccounts = selectAccounts;
	}
	public String getTargetorgid() {
		return targetorgid;
	}
	public void setTargetorgid(String targetorgid) {
		this.targetorgid = targetorgid;
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
	
}

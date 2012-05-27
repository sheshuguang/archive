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
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
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
	
	public String save() throws IOException {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
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

//	@DirectFormPostMethod
//	public String importExcel(Map<String,String> param,Map<String,FileItem> files) throws Exception {
//		assert param != null;
//		assert files != null;
//		try {
//			
//			FileItem f = files.get("uploadfile");
//			String fileName = f.getName();
//			jxl.Workbook rwb = Workbook.getWorkbook(f.getInputStream());
//			int a = rwb.getNumberOfSheets();
//			Sheet rs = rwb.getSheet(0);
//			int aaaa = rs.getRows();
//			System.out.println(String.valueOf(aaaa));
//			Cell c11 = rs.getCell(1, 1);
//			String strc11 = c11.getContents();
//			System.out.println(strc11);
//			rwb.close();
//			
////			File file = new File("D:\\aa.xls");
////			f.write(file);   //TODO 为什么这里写文件报错呢，Cannot write uploaded file to disk!  问阿佘这是为什么
//			
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		
////		files.get("uploadfile").write(file);
//		
////		BufferedInputStream bis=new BufferedInputStream(request.getInputStream());
////		BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream("e:\\test.txt",true));
////		PrintWriter out= getResponse().getWriter();
////		if(bis!=null){
////			int ch;
////			while((ch=bis.read())!=-1)
////			{
////				bos.write(ch);
////			}
////			bis.close();
////			bos.close();
////			out.write("上传成功");
////		}
////		out.write("上传失败");
//
//		return null;
//		
//	}


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
	
}

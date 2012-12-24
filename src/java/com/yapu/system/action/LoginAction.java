package com.yapu.system.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.entity.SysFunction;
import com.yapu.system.entity.SysFunctionExample;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.entity.SysRole;
import com.yapu.system.service.itf.IAccountService;
import com.yapu.system.service.itf.IFunctionService;
import com.yapu.system.service.itf.IOrgService;
import com.yapu.system.service.itf.IRoleService;
import com.yapu.system.util.Constants;
import com.yapu.system.util.Logger;


public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Logger log = new Logger(LoginAction.class);
	
	private IAccountService accountService;
	private IOrgService orgService;
	private IRoleService roleService;
	private IFunctionService functionService;
	private String accountcode;
	private String password;
	
	public String login(){
//		setAccountcode("admin");
//		setPassword("password");
		
		//登录时先判断session里是否有该账户,防止同一台机器有2个session登录
		SysAccount accountTmp = (SysAccount)this.getHttpSession().getAttribute(Constants.user_in_session);
		
		if (accountTmp != null){
			this.getHttpSession().removeAttribute(Constants.user_in_session);
		}
		
		SysAccount account = new SysAccount();
		account.setAccountcode(accountcode);
		account.setPassword(password);
		SysAccount res = accountService.login(account);
		
		if (null != res) {
			//用户登录成功，将用户实体存入session
			getHttpSession().setAttribute(Constants.user_in_session, res);
			return SUCCESS;
		}
		else {
			getRequest().setAttribute("str", "输入的账户名 或密码错误，请重新输入。");
			return ERROR;
		}
	}
	
	/**
	 * 转到首页框架,读取首页功能菜单
	 * @return
	 */
//	public String banner() throws IOException {
//		return SUCCESS;
//	}
	/**
	 * 读取登录用户的模块菜单访问权限
	 * @return
	 * @throws IOException
	 */
	public String menu() throws IOException {
		PrintWriter out  = this.getPrintWriter();
		
		SysAccount account = (SysAccount) this.getHttpSession().getAttribute(Constants.user_in_session);
		if (null == account) {
			out.write("error");
			return null;
		}
		
		String result = "var account='" + account.getAccountcode() + "';";
		//得到帐户的角色
		SysRole role = new SysRole();
		role = accountService.getAccountOfRole(account);
		
		//如果帐户没有自己的角色，读取帐户所属组的角色
		if (null == role) {
			SysOrg org = accountService.getAccountOfOrg(account);
			role = orgService.getOrgOfRole(org);
		}
		List<SysFunction> functionList = new ArrayList<SysFunction>(); 
		if (null != role) {
			//根据角色，得到角色对应的功能权限
			functionList = roleService.getRoleOfFunction(role);
		}
		Gson gson = new Gson();
		if (functionList.size() > 0) {
			result += "var functionList=" + gson.toJson(functionList);
		}
		
		out.write(result);
		
		
		
		
//		String str = "";
//		StringBuffer sb = new StringBuffer();
//		//添加首页按钮 
//		sb.append("<li><h3><a href=\"#\">Archive</a></h3></li>");
//		sb.append("<li><a href=\"#\" onclick=\"javascript:$(window.parent.document).find('#ifr').attr('src','webpage/archive/search/SearchMgr.html')\">首页</a></li>");
//		sb.append("<li style=\"float:right;\"><a href=\"#\" onclick=\"quit()\">退出</a></li>");
//		sb.append("<li style=\"float:right;\"><a href=\"#\" onClick=\"openAccountInfo();\">欢迎您 "+account.getAccountcode()+" </a></li>");
//		//TODO 这里要改。改为：admin也是一个帐户，不应单独判断。系统初始化一个超级帐户角色，将作为admin的默认角色。admin不能输入其他角色
//		//可以给超级帐户赋予权限，这样这里的代码就会省略。
//		if ("admin".equals(account.getAccountcode())) {
//			SysFunctionExample funExample = new SysFunctionExample();
//			List<SysFunction> functionList = functionService.selectByWhereNotPage(funExample);
//			if (null != functionList) {
//				for (SysFunction sysFunction : functionList) {
//					if ("0".equals(sysFunction.getFunparent())) {
//						sb.append("<li><a href=\"javascript:void(0)\"");
//						if (null != sysFunction.getFunpath() && !"".equals(sysFunction.getFunpath())) {
//							sb.append(" onclick=\"javascript:$(window.parent.document).find('#ifr').attr('src','"+sysFunction.getFunpath()+"')\"");
//						}
//						sb.append(">"+sysFunction.getFunchinesename()+"</a>");
//						StringBuffer childsb = new StringBuffer();
//						for (SysFunction function : functionList) {
//							if (function.getFunparent().equals(sysFunction.getFunctionid())) {
//								childsb.append("<li><a href=\"javascript:void(0)\"");
//								childsb.append(" onclick=\"javascript:$(window.parent.document).find('#ifr').attr('src','"+function.getFunpath()+"')\"");
//								childsb.append(">"+function.getFunchinesename()+"</a></li>");
//							}
//						}
//						//有子菜单
//						if (childsb.length() > 0) {
//							sb.append("<ul>");
//							sb.append(childsb.toString());
//							sb.append("</ul>");
//						}
//						sb.append("</li>");
//					}
//				}
//			}
//		}
//		else {
//			//得到帐户的角色
//			SysRole role = new SysRole();
//			role = accountService.getAccountOfRole(account);
//			
//			//如果帐户没有自己的角色，读取帐户所属组的角色
//			if (null == role) {
//				SysOrg org = accountService.getAccountOfOrg(account);
//				role = orgService.getOrgOfRole(org);
//			}
////			String str = "";
//			if (null != role) {
//				//根据角色，得到角色对应的功能权限
//				List<SysFunction> functionList = roleService.getRoleOfFunction(role);
//				if (null != functionList) {
//					
//					for (SysFunction sysFunction : functionList) {
//						if ("0".equals(sysFunction.getFunparent())) {
//							str = "<a href=\"javascript:void(0)\" id="+ sysFunction.getFunctionid() +" class=\"easyui-menubutton\" menu=\"#"+sysFunction.getFunenglishname()+"\" iconCls=\""+sysFunction.getFunicon()+"\">"+sysFunction.getFunchinesename()+"</a>";
//							str += "<div id=\""+sysFunction.getFunenglishname()+"\" style=\"width:150px;\">";
//							for (SysFunction function : functionList) {
//								if (function.getFunparent().equals(sysFunction.getFunctionid())) {
//									str += "<div iconCls=\""+function.getFunicon()+"\" onclick=\"javascript:$(window.parent.document).find('#ifr').attr('src','"+function.getFunpath()+"')\">"+function.getFunchinesename()+"</div>";
//								}
//							}
//							str += "</div>";
//						}
//					}
//				}
//				
//			}
//		}
//		out.write(sb.toString());
		return null;
	}
	
	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	public void setFunctionService(IFunctionService functionService) {
		this.functionService = functionService;
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

	public void setOrgService(IOrgService orgService) {
		this.orgService = orgService;
	}
	
}

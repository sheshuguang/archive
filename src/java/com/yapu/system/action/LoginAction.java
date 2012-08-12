package com.yapu.system.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.entity.SysFunction;
import com.yapu.system.entity.SysFunctionExample;
import com.yapu.system.entity.SysRole;
import com.yapu.system.service.itf.IAccountService;
import com.yapu.system.service.itf.IFunctionService;
import com.yapu.system.service.itf.IRoleService;
import com.yapu.system.util.Constants;
import com.yapu.system.util.Logger;


public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Logger log = new Logger(LoginAction.class);
	
	private IAccountService accountService;
	private IRoleService roleService;
	private IFunctionService functionService;
	private String accountcode;
	private String password;
	
	public String login(){
		setAccountcode("admin");
		setPassword("password");
		
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
			return ERROR;
		}
		String str = "";
		if ("admin".equals(account.getAccountcode())) {
			SysFunctionExample funExample = new SysFunctionExample();
			List<SysFunction> functionList = functionService.selectByWhereNotPage(funExample);
			if (null != functionList) {
				for (SysFunction sysFunction : functionList) {
					if ("0".equals(sysFunction.getFunparent())) {
						str += "<a href=\"javascript:void(0)\" id="+ sysFunction.getFunctionid() +" class=\"easyui-menubutton\" menu=\"#"+sysFunction.getFunenglishname()+"\" iconCls=\""+sysFunction.getFunicon()+"\">"+sysFunction.getFunchinesename()+"</a>";
						str += "<div id=\""+sysFunction.getFunenglishname()+"\" style=\"width:150px;\">";
						for (SysFunction function : functionList) {
							if (function.getFunparent().equals(sysFunction.getFunctionid())) {
								str += "<div iconCls=\""+function.getFunicon()+"\" onclick=\"javascript:$(window.parent.document).find('#ifr').attr('src','"+function.getFunpath()+"')\">"+function.getFunchinesename()+"</div>";
							}
						}
						str += "</div>";
					}
				}
			}
		}
		else {
			//得到帐户的角色
			SysRole role = accountService.getAccountOfRole(account);
//			String str = "";
			if (null != role) {
				//根据角色，得到角色对应的功能权限
				List<SysFunction> functionList = roleService.getRoleOfFunction(role);
				if (null != functionList) {
					
					for (SysFunction sysFunction : functionList) {
						if ("0".equals(sysFunction.getFunparent())) {
							str = "<a href=\"javascript:void(0)\" id="+ sysFunction.getFunctionid() +" class=\"easyui-menubutton\" menu=\"#"+sysFunction.getFunenglishname()+"\" iconCls=\""+sysFunction.getFunicon()+"\">"+sysFunction.getFunchinesename()+"</a>";
							str += "<div id=\""+sysFunction.getFunenglishname()+"\" style=\"width:150px;\">";
							for (SysFunction function : functionList) {
								if (function.getFunparent().equals(sysFunction.getFunctionid())) {
									str += "<div iconCls=\""+function.getFunicon()+"\" onclick=\"javascript:$(window.parent.document).find('#ifr').attr('src','"+function.getFunpath()+"')\">"+function.getFunchinesename()+"</div>";
								}
							}
							str += "</div>";
						}
					}
				}
				
			}
		}
		out.write(str);
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
	
}

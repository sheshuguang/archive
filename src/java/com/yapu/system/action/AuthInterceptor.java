package com.yapu.system.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.util.Constants;

public class AuthInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = 6305298392022425555L;

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		ActionContext ctx = arg0.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = request.getSession();
		SysAccount account = (SysAccount) session.getAttribute(Constants.user_in_session);
		String b = request.getRequestURI();
		int a = request.getRequestURI().indexOf("login");
		
		if (account != null || request.getRequestURI().indexOf("login") != -1){//login.action不拦截  ) {
//			log.info("��ǰ�û�Ϊ��" + account.getUsername());
			return arg0.invoke();  
		}
		else {
			HttpServletResponse response = ServletActionContext.getResponse();
			PrintWriter pw = response.getWriter();

			String flag = "";
			
			if (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
				response.setCharacterEncoding("text/html;charset=utf-8");
				response.setContentType("text/html;charset=utf-8");
				flag = "9999";
				pw.write(flag);

				return null;

				//不是异步请求的拦截
				
			}
			else {
				response.setCharacterEncoding("text/html;charset=utf-8");
				response.sendRedirect("/login.jsp");
				//对ajax请求的拦截 
				return "login"; 		

			}
		}
	}

}

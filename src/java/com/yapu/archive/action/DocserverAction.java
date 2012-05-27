package com.yapu.archive.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yapu.archive.entity.SysDocserver;
import com.yapu.archive.entity.SysDocserverExample;
import com.yapu.archive.service.itf.IDocserverService;
import com.yapu.system.common.BaseAction;

public class DocserverAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private IDocserverService docserverService;
	
	private String par;
	private String docserverid;
	
	/**
	 * 返回角色的json
	 * @return
	 */
	public String list() throws IOException {
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		SysDocserverExample example = new SysDocserverExample();
		List<SysDocserver> docserverList = docserverService.selectByWhereNotPage(example);
		StringBuffer sb = new StringBuffer();
		sb.append("{\"total\":").append(docserverList.size()).append(",\"rows\":[");
		String resultStr = "";
		if(null!=docserverList && docserverList.size()>0){
			
			for (SysDocserver sysDocserver : docserverList) {
				sb.append("{");
				sb.append("\"docserverid\":\""+sysDocserver.getDocserverid()+"\",");
				sb.append("\"servername\":\""+sysDocserver.getServername()+"\",");
				sb.append("\"serverip\":\""+sysDocserver.getServerip()+"\",");
				sb.append("\"ftpuser\":\""+sysDocserver.getFtpuser()+"\",");
				sb.append("\"ftppassword\":\""+sysDocserver.getFtppassword()+"\",");
				sb.append("\"serverport\":\""+sysDocserver.getServerport()+"\",");
				sb.append("\"serverpath\":\""+sysDocserver.getServerpath()+"\",");
				sb.append("\"servertype\":\""+sysDocserver.getServertype()+"\",");
//				if (sysDocserver.getServerstate() == 1) {
//					sb.append("\"serverstate\":\"启用\",");
//				}
//				else {
//					sb.append("\"serverstate\":\"备用\",");
//				}
				sb.append("\"serverstate\":\""+sysDocserver.getServerstate()+"\",");
				sb.append("\"servermemo\":\""+sysDocserver.getServermemo()+"\"");
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
	
	@SuppressWarnings("unchecked")
	public String save() throws IOException {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		String result = "保存完毕。";
		try {
			Gson gson = new Gson();
			Map<String, List<SysDocserver>> docserverMap = new HashMap<String, List<SysDocserver>>();
			try {
				docserverMap = (Map)gson.fromJson(par, new TypeToken<Map<String, List<SysDocserver>>>(){}.getType());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				result = "保存失败，请重新尝试，或与管理员联系。";
				out.write(result);
				return null;
			}
			//处理添加的
			List<SysDocserver> insertDocserverList = docserverMap.get("inserted");
			if (insertDocserverList.size() > 0) {
				//循环保存添加
				for (SysDocserver sysDocserver : insertDocserverList) {
					sysDocserver.setServerstate(0);
					addDocserver(sysDocserver);
				}
			}
			//处理更新的
			List<SysDocserver> updateDocserverList = docserverMap.get("updated");
			if (updateDocserverList.size() > 0) {
				//循环保存更新的
				for (SysDocserver sysDocserver : updateDocserverList) {
					updateDocserver(sysDocserver);
				}
			}
			//处理删除帐户
			List<SysDocserver> delDocserverList = docserverMap.get("deleted");
			if (delDocserverList.size() > 0) {
				//循环删除
				for (SysDocserver sysDocserver : delDocserverList) {
					delDocserver(sysDocserver.getDocserverid());
				}
			}
		} catch (Exception e) {
			result = "保存失败，请重新尝试，或与管理员联系。";
			out.write(result);
			return null;
		}
		
		out.write(result);
		return null;
	}
	/**
	 * 添加
	 * @param docserver
	 * @return boolean
	 */
	public boolean addDocserver(SysDocserver docserver) {
		boolean result = false;
		if (docserver != null) {
//			docserver.setDocserverid(UUID.randomUUID().toString());
			
			if(docserverService.insertDocserver(docserver)){
				result=true;
			}
		}
		return result;
	}
	/**
	 * 更新
	 * @param docserver
	 * @return
	 */
	private boolean updateDocserver(SysDocserver docserver) {
		boolean result = false;
		if (docserver != null) {
			if(docserverService.updateDocserver(docserver) > 0){
				result = true;
			}
		}
		return result;
	}
	/**
	 * 删除
	 * @param 
	 * @return
	 */
	public boolean delDocserver(String docserverid) {
		boolean result = false;
		if (null != docserverid && !"".equals(docserverid)) {
			int num = docserverService.deleteDocserver(docserverid);
			if (num >0) {
				result = true;
			}
		}
		return result;
	}
	/**
	 * 更改服务器状态(是否启用)
	 * @return
	 * @throws IOException
	 */
	public String setDocserverState() throws IOException {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		String result = "服务器状态已更新完毕。";
		
		try {
			SysDocserverExample example = new SysDocserverExample();
			//将所有服务器状态设置为备用
			SysDocserver docserver = new SysDocserver();
			docserver.setServerstate(0);
			docserverService.updateDocserver(docserver, example);
			
			//将指定的服务器状态修改为启用。
			docserver.setServerstate(1);
			docserver.setDocserverid(docserverid);
			docserverService.updateDocserver(docserver);
		} catch (Exception e) {
			result = "<span style='color:red'>服务器状态更新失败。</span><br> 请尝试重新操作，或与管理员联系。";
			out.write(result);
		}
		out.write(result);
		return null;
	}
	
	public String getDocserverid() {
		return docserverid;
	}
	public void setDocserverid(String docserverid) {
		this.docserverid = docserverid;
	}
	public void setDocserverService(IDocserverService docserverService) {
		this.docserverService = docserverService;
	}
	public String getPar() {
		return par;
	}
	public void setPar(String par) {
		this.par = par;
	}

}

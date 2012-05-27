package com.yapu.system.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysConfig;
import com.yapu.system.entity.SysConfigExample;
import com.yapu.system.entity.SysRole;
import com.yapu.system.service.itf.IConfigService;

public class ConfigAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private IConfigService configService;
	
	private String par;
	
	/**
	 * 返回系统属性列表
	 * @return
	 */
	public String list() throws IOException {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		SysConfigExample example = new SysConfigExample();
		List<SysConfig> configList = configService.selectByWhereNotPage(example);
		StringBuffer sb = new StringBuffer();
		sb.append("{\"total\":").append(configList.size()).append(",\"rows\":[");
		String resultStr = "";
		if(null!=configList && configList.size()>0){
			
			for (SysConfig config : configList) {
				sb.append("{");
				sb.append("\"configid\":\""+config.getConfigid()+"\",");
				sb.append("\"configname\":\""+config.getConfigname()+"\",");
				sb.append("\"configkey\":\""+config.getConfigkey()+"\",");
				sb.append("\"configvalue\":\""+config.getConfigvalue()+"\",");
				sb.append("\"configmemo\":\""+config.getConfigmemo()+"\"");
				//sb.append("\"editor\":{\"type\":\"validatebox\",\"options\":{\"required\":\"true\",\"missingMessage\":\"此处请填写内容，不能为空！\"}}");
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
	/**
	 * 保存系统属性设置
	 * @return
	 * @throws IOException
	 */
	public String save() throws IOException {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		String result = "保存完毕。";
		Gson gson = new Gson();
		Map<String, List<SysConfig>> configMap = new HashMap<String, List<SysConfig>>();
		try {
			configMap = (Map)gson.fromJson(par, new TypeToken<Map<String, List<SysConfig>>>(){}.getType());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//处理更新的
		List<SysConfig> updateConfigList = configMap.get("updated");
		if (updateConfigList.size() > 0) {
			//循环保存更新的
			for (SysConfig config : updateConfigList) {
				configService.updateConfig(config);
			}
		}
		out.write(result);
		return null;
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
	
}

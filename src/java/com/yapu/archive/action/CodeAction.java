package com.yapu.archive.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yapu.archive.entity.SysCode;
import com.yapu.archive.entity.SysCodeExample;
import com.yapu.archive.service.itf.ICodeService;
import com.yapu.system.common.BaseAction;

public class CodeAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private ICodeService codeService;
	
	private String codeid;
	private String jsonStr;
	private String par;
	private String templetfieldid;
	
//	public String showListCode() {
//		return SUCCESS;
//	}
	
	public String list() throws IOException {
		//如果得不到字段id，返回空字符
		if (null == templetfieldid || "".equals(templetfieldid)) {
			return null;
		}
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		//获得templetfieldid的code列表
		SysCodeExample example = new SysCodeExample();
		example.createCriteria().andTempletfieldidEqualTo(templetfieldid);
		example.setOrderByClause("codeorder");
		
		List<SysCode> codeList = codeService.selectByWhereNotPage(example);
		StringBuffer sb = new StringBuffer();
		sb.append("{\"total\":").append(codeList.size()).append(",\"rows\":[");
		String resultStr = "";
		if(null!=codeList && codeList.size()>0){
			
			for (SysCode code : codeList) {
				sb.append("{");
				sb.append("\"codeid\":\""+code.getCodeid()+"\",");
				sb.append("\"columnname\":\""+code.getColumnname()+"\",");
				sb.append("\"columndata\":\""+code.getColumndata()+"\",");
				sb.append("\"codeorder\":"+code.getCodeorder()+",");
				sb.append("\"templetfieldid\":\""+code.getTempletfieldid()+"\"");
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
		HashMap<String, List<SysCode>> map = new HashMap<String, List<SysCode>>();
		try {
			map = (HashMap)gson.fromJson(par, new TypeToken<Map<String, List<SysCode>>>(){}.getType());
			Boolean saveResult = codeService.save(map,templetfieldid);
			if (!saveResult) {
				result = "保存错误，请重新尝试或与管理员联系。";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		out.write(result);
		return null;
	}
	
//	private boolean add(SysCode code) {
//		boolean result = false;
//		if (null != code) {
//			if (codeService.insertCode(code)) {
//				result = true;
//			}
//		}
//		return result;
//	}
//	
//	private boolean update(SysCode code) {
//		boolean result = false;
//		if (code != null) {
//			if(codeService.updateCode(code) > 0){
//				result = true;
//			}
//		}
//		return result;
//	}
//
//	private boolean delete(String id) {
//		boolean result = false;
//		try {
//			if (null != id && !"".equals(id)) {
//				int num = codeService.deleteCode(id);
//				if (num >0) {
//					result = true;
//				}
//			}
//			return result;
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			return false;
//		}
//	}
	
	
	public String getCodeid() {
		return codeid;
	}
	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	public String getPar() {
		return par;
	}
	public void setPar(String par) {
		this.par = par;
	}
	public String getTempletfieldid() {
		return templetfieldid;
	}
	public void setTempletfieldid(String templetfieldid) {
		this.templetfieldid = templetfieldid;
	}
	public void setCodeService(ICodeService codeService) {
		this.codeService = codeService;
	}

}

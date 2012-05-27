package com.yapu.archive.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.entity.SysTempletfieldExample;
import com.yapu.archive.service.itf.ITempletfieldService;
import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.util.Constants;

public class TempletfieldAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private ITempletfieldService templetfieldService;
	
	private String tableid;
	private String jsonStr;
	private String par;
	private String templetfieldid;
	
	public String showListTempletfield() {
		return SUCCESS;
	}
	
	public String list() throws IOException {
		//如果得不到表名管理表id，返回空字符
		if (null == tableid || "".equals(tableid)) {
			return null;
		}
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		//获得父节点为tableid的templetfield字段列表
		SysTempletfieldExample example = new SysTempletfieldExample();
		SysTempletfieldExample.Criteria criteria = example.createCriteria();
		criteria.andTableidEqualTo(tableid);
		//读取session里的登录帐户
		SysAccount account = (SysAccount) this.getHttpSession().getAttribute(Constants.user_in_session);
		if (null == account) {
			//TODO 这里因为session过期，以后处理
			return ERROR;
		}
		//如果当前登录帐户是admin，则显示全部字段。否则显示排序号>=0的字段。
		if (!"admin".equals(account.getAccountcode())) {
			criteria.andSortGreaterThanOrEqualTo(0);
		}
		example.setOrderByClause("SORT");
		
		List<SysTempletfield> templetfieldList = templetfieldService.selectByWhereNotPage(example);
		StringBuffer sb = new StringBuffer();
		sb.append("{\"total\":").append(templetfieldList.size()).append(",\"rows\":[");
		String resultStr = "";
		if(null!=templetfieldList && templetfieldList.size()>0){
			
			for (SysTempletfield templetfield : templetfieldList) {
				sb.append("{");
				sb.append("\"templetfieldid\":\""+templetfield.getTempletfieldid()+"\",");
				sb.append("\"englishname\":\""+templetfield.getEnglishname()+"\",");
				sb.append("\"chinesename\":\""+templetfield.getChinesename()+"\",");
				sb.append("\"fieldsize\":"+templetfield.getFieldsize()+",");
				sb.append("\"fieldtype\":\""+templetfield.getFieldtype()+"\",");
				sb.append("\"ispk\":\""+templetfield.getIspk()+"\",");
				sb.append("\"defaultvalue\":\""+templetfield.getDefaultvalue()+"\",");
				if (templetfield.getIsrequire() == 1) {
					sb.append("\"isrequire\":\"<img alt='1' src='../../images/icons/accept.png' title='必须著录'>\",");
				}
				else {
					sb.append("\"isrequire\":\"\",");
				}
				if (templetfield.getIsunique() == 1) {
					sb.append("\"isunique\":\"<img alt='1' src='../../images/icons/accept.png' title='字段属性值唯一，不能重复!'>\",");
				}
				else {
					sb.append("\"isunique\":\"\",");
				}
				if (templetfield.getIssearch() == 1) {
					sb.append("\"issearch\":\"<img alt='1' src='../../images/icons/accept.png' title='这个字段可以模糊检索!'>\",");
				}
				else {
					sb.append("\"issearch\":\"\",");
				}
				if (templetfield.getIsgridshow() == 1) {
					sb.append("\"isgridshow\":\"<img alt='1' src='../../images/icons/accept.png' title='这个字段在列表上显示!'>\",");
				}
				else {
					sb.append("\"isgridshow\":\"\",");
				}
				sb.append("\"sort\":"+templetfield.getSort()+",");
//				sb.append("\"isedit\":\""+templetfield.getIsedit()+"\",");
				if (templetfield.getIscode() == 1) {
					sb.append("\"iscode\":\"<img alt='1' src='../../images/icons/accept.png' title='这个字段是可选项，有代码!'>\",");
				}
				else {
					sb.append("\"iscode\":\"\",");
				}
//				sb.append("\"iscode\":"+templetfield.getIscode()+",");
				sb.append("\"issystem\":\""+templetfield.getIssystem()+"\",");
//				sb.append("\"fieldcss\":\""+templetfield.getFieldcss()+"\",");
				sb.append("\"tableid\":\""+templetfield.getTableid()+"\"");
				sb.append("},");
			}
			resultStr = sb.substring(0,sb.length()-1);
			
		}
		else {
			resultStr = sb.toString();
		}
		resultStr += "]}";
        System.out.print(resultStr);
		out.write(resultStr);
		return null;
	}
	
	/**
	 * 保存添加字段
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public String add() throws IOException {
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		String result = "保存时出现异常错误，请尝试重新操作或与管理员联系。";
		
		if (null != jsonStr && !"".equals(jsonStr)) {
			Gson gson = new Gson();
			Map<String, String> map = new HashMap<String, String>();
			try {
				map = (Map)gson.fromJson(jsonStr, new TypeToken<Map<String, String>>(){}.getType());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				out.write(result);
				return null;
			}
			//将map里的数据转换成对象
			if (map.size() > 0) {
				SysTempletfield templetfield = new SysTempletfield();
				templetfield.setTempletfieldid(map.get("templetfieldid"));
				templetfield.setChinesename(map.get("chinesename"));
				templetfield.setEnglishname(map.get("englishname"));
				templetfield.setFieldsize(Integer.parseInt(map.get("fieldsize")));
				templetfield.setFieldtype(map.get("fieldtype"));
				templetfield.setDefaultvalue(map.get("defaultvalue"));
				templetfield.setSort(Integer.parseInt(map.get("sort")));
				templetfield.setIsrequire(Integer.parseInt(map.get("isrequire")));
				templetfield.setIsunique(Integer.parseInt(map.get("isunique")));
				templetfield.setIssearch(Integer.parseInt(map.get("issearch")));
				templetfield.setIsgridshow(Integer.parseInt(map.get("isgridshow")));
				//后台添加的字段属性
				templetfield.setIspk(0);
				templetfield.setIsedit(0);
				templetfield.setIscode(0);
				templetfield.setIssystem(0);
				templetfield.setFieldcss("");
				templetfield.setTableid(tableid);
				//验证字段标识是否重复
				SysTempletfieldExample example = new SysTempletfieldExample();
				example.createCriteria().andEnglishnameEqualTo(templetfield.getEnglishname()).andTableidEqualTo(tableid);
				List<SysTempletfield> listTempletfield = new ArrayList<SysTempletfield>(); 
				listTempletfield = templetfieldService.selectByWhereNotPage(example);
				if (listTempletfield.size() > 0) {
					result = "字段标识重复，请重新输入！";
				}
				else {
					//保存到数据库
					try {
						boolean b = templetfieldService.insertTempletfield(templetfield);
						if (b) {
							result = "字段属性保存成功！";
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
						out.write(result);
						return null;
					}
				}
			}
		}
		out.write(result);
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
			Map<String, List<SysTempletfield>> map = new HashMap<String, List<SysTempletfield>>();
			try {
				map = (Map)gson.fromJson(par, new TypeToken<Map<String, List<SysTempletfield>>>(){}.getType());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				result = "保存失败，请重新尝试，或与管理员联系。";
				out.write(result);
				return null;
			}
			//处理更新的
			List<SysTempletfield> updateList = map.get("updated");
			if (updateList.size() > 0) {
				//循环保存更新的
				for (SysTempletfield sysTempletfield : updateList) {
					update(sysTempletfield);
				}
			}
			//处理删除
			List<SysTempletfield>  delList = map.get("deleted");
			if (delList.size() > 0) {
				//循环删除
				for (SysTempletfield sysTempletfield : delList) {
					delete(sysTempletfield.getTempletfieldid());
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
	
	private boolean update(SysTempletfield templetfield) {
		boolean result = false;
		if (templetfield != null) {
			if(templetfieldService.updateTempletfield(templetfield) > 0){
				result = true;
			}
		}
		return result;
	}
	
	private boolean delete(String id) {
		boolean result = false;
		try {
			if (null != id && !"".equals(id)) {
				int num = templetfieldService.deleteTempletfield(id);
				if (num >0) {
					result = true;
				}
			}
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public String getTableid() {
		return tableid;
	}
	public void setTableid(String tableid) {
		this.tableid = tableid;
	}
	public void setTempletfieldService(ITempletfieldService templetfieldService) {
		this.templetfieldService = templetfieldService;
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
}

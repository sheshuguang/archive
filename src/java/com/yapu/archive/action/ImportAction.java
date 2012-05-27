package com.yapu.archive.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TryCatchFinally;

import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.service.itf.ITempletfieldService;
import com.yapu.archive.service.itf.ITreeService;
import com.yapu.system.common.BaseAction;
import com.yapu.system.util.Excel;

public class ImportAction extends BaseAction {
	
	private static final long serialVersionUID = -3858031783187186618L;
	
	private File selectfile;//得到上传的文件
	private String treeid;
	private String tableType;
	
	private ITempletfieldService templetfieldService;
	private ITreeService treeService;
	
	public String upload() throws Exception {
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		//得到excel表内容
		Excel e = new Excel();
		Vector v = e.readFromExcel(selectfile);
		//得到excel表第一行列头，作为字段名称
		String excelFieldName = "";
		if (v.size() > 2) {
			excelFieldName = (String) v.get(0);
		}
		else {
			out.write("<script>parent.showCallback('failure','Excel文件读取错误，请检查Excel文件中是否包含上传数据。')</script>");
			return null;
		}
		
		List<String> excelField = Arrays.asList(v.get(0).toString().split(","));
		
		//得到数据库字段
		List<SysTempletfield> fieldList = treeService.getTreeOfTempletfield(treeid,tableType);
		
		List<String> tmpFieldList = new ArrayList<String>();
		//数据库字段名称与excel列头对比，找出需要导入哪些字段
		for (int i=0;i<excelField.size();i++) {
			for (SysTempletfield field : fieldList) {
				if (excelField.get(i).toString().equals(field.getChinesename())) {
					tmpFieldList.add(field.getEnglishname());
				}
			}
		}
		if (tmpFieldList.size() <= 0) {
			out.write("<script>parent.showCallback('failure','Excel文件读取错误，请检查Excel文件是否符合本系统的要求！')</script>");
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
//		sb.append("{\"total\":").append(v.size()-1).append(",\"rows\":[");
		sb.append("[");
		//创建json数据
		for (int i=1;i<v.size();i++) {
			List<String> row = Arrays.asList(v.get(i).toString().split(","));
			sb.append("{").append("\"id\":\"").append(UUID.randomUUID()).append("\",\"TREEID\":\"");
			sb.append(treeid).append("\",\"ISDOC\":\"0\",");
			for (int j=0;j<tmpFieldList.size();j++) {
				sb.append("\"");
				sb.append(tmpFieldList.get(j).toString());
				sb.append("\":");
				sb.append("\"");
				try {
					sb.append(row.get(j));
				} catch (Exception e2) {
					
				}
				
				sb.append("\",");
			}
			sb.deleteCharAt(sb.length() - 1).append("},");
		}
//		sb.deleteCharAt(sb.length() - 1).append("]}");
		sb.deleteCharAt(sb.length() - 1).append("]");
		
		out.write("<script>parent.showCallback('success','"+sb.toString()+"')</script>");
		return null;
	}

	public File getSelectfile() {
		return selectfile;
	}
	public void setSelectfile(File selectfile) {
		this.selectfile = selectfile;
	}
	public String getTreeid() {
		return treeid;
	}
	public void setTreeid(String treeid) {
		this.treeid = treeid;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public void setTempletfieldService(ITempletfieldService templetfieldService) {
		this.templetfieldService = templetfieldService;
	}

	public void setTreeService(ITreeService treeService) {
		this.treeService = treeService;
	}
	
}

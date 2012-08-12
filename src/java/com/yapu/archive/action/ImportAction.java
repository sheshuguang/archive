package com.yapu.archive.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yapu.archive.entity.SysTable;
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.service.itf.IDynamicService;
import com.yapu.archive.service.itf.ITreeService;
import com.yapu.system.common.BaseAction;
import com.yapu.system.util.Excel;

public class ImportAction extends BaseAction {
	
	private static final long serialVersionUID = -3858031783187186618L;
	
	private File selectfile;//得到上传的文件
	private String treeid;
	private String tableType;
	//如果是文件导入，需要在生成导入数据时，增加父节点id，也就是所属案卷的id
	private String selectAid;
	
	private String importData;
	
	private ITreeService treeService;
	private IDynamicService dynamicService;
	
	/*
	 * 接收上传文件，读取文件，返回数据
	 */
	public String upload() throws Exception {

		PrintWriter out = getPrintWriter();
		
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
		//存储导入字段
		List<String> tmpFieldList = new ArrayList<String>();
		//存储没有出现在excel里的字段。
		List<SysTempletfield> noImportFieldList = new ArrayList<SysTempletfield>();
		//数据库字段名称与excel列头对比，找出需要导入哪些字段
		String[] stringArr = new String[excelField.size()];
		for (int i=0;i<fieldList.size();i++) {
			String a = fieldList.get(i).getChinesename();
			int num = excelField.indexOf(a);
			if (num >= 0) {
				stringArr[num] = fieldList.get(i).getEnglishname();
			}
			else if (fieldList.get(i).getSort() >= 0) {
				noImportFieldList.add(fieldList.get(i));
			}
		}
		
		tmpFieldList = Arrays.asList(stringArr);
		
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
			//生成系统字段
			sb.append("{").append("\"id\":\"").append(UUID.randomUUID()).append("\",\"treeid\":\"");
			sb.append(treeid).append("\",\"isdoc\":\"0\",\"rownum\":\"").append(i).append("\",");
			if (tableType.equals("02")) {
				sb.append("\"parentid\":\"").append(selectAid).append("\",");
			}
			//生成excel导入字段数据
			for (int j=0;j<tmpFieldList.size();j++) {
				sb.append("\"");
				sb.append(tmpFieldList.get(j).toString().toLowerCase());
				sb.append("\":");
				sb.append("\"");
				try {
					sb.append(row.get(j));
				} catch (Exception e2) {
					
				}
				
				sb.append("\",");
			}
			//生成非excel导入字段，并且字段类型是int或有默认值的
			for (SysTempletfield field : noImportFieldList) {
				if (field.getFieldtype().equals("INT")) {
					sb.append("\"");
					sb.append(field.getEnglishname().toString().toLowerCase());
					sb.append("\":");
					sb.append("\"");
					try {
						sb.append("0");
					} catch (Exception e2) {
						
					}
					sb.append("\",");
				}
				else {
					sb.append("\"");
					sb.append(field.getEnglishname().toString().toLowerCase());
					sb.append("\":");
					sb.append("\"");
					try {
						sb.append(field.getDefaultvalue());
					} catch (Exception e2) {
						
					}
					sb.append("\",");
				}
			}
			sb.deleteCharAt(sb.length() - 1).append("},");
		}
//		sb.deleteCharAt(sb.length() - 1).append("]}");
		sb.deleteCharAt(sb.length() - 1).append("]");
		out.write("<script>parent.showCallback('success','"+sb.toString()+"')</script>");
		return null;
	}
	
	public String insertImport() throws IOException {
		String result = "保存完毕。";
		PrintWriter out = getPrintWriter();
		
		Gson gson = new Gson();
		List<HashMap<String,String>> archiveList = new ArrayList<HashMap<String, String>>();
		
		try {
			//将传入的json字符串，转换成list
			archiveList = (List)gson.fromJson(importData, new TypeToken<List<HashMap<String,String>>>(){}.getType());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			result = "保存失败，请重新尝试，或与管理员联系。";
			out.write(result);
			return null;
		}
		
		if (archiveList.size() <= 0) {
			result = "没有找到数据，请重新尝试或与管理员联系。";
			out.write(result);
			return null;
		}
		
		List<SysTable> tableList = treeService.getTreeOfTable(archiveList.get(0).get("treeid").toString());
		//sb存储insert语句values前的
		StringBuffer sb = new StringBuffer();
		//value 存储values之后的
		StringBuffer value = new StringBuffer();
		String tableName = "";
		//得到表名
		for (int i=0;i<tableList.size();i++) {
			if (tableList.get(i).getTabletype().equals(tableType)) {
				tableName = tableList.get(i).getTablename();
				break;
			}
		}
		List<SysTempletfield> fieldList = treeService.getTreeOfTempletfield(archiveList.get(0).get("treeid").toString(), tableType);
		boolean b = false;
		
		//存储sql语句
		List<String> sqlList = new ArrayList<String>();
		try {
			for (int z=0;z<archiveList.size();z++) {
				//创建insert sql
				HashMap<String,String> row = (HashMap<String,String>) archiveList.get(z);
				sb.append("insert into ").append(tableName);
				
				sb.append(" (");
				value.append(" (");
				for (SysTempletfield field : fieldList) {
					sb.append(field.getEnglishname()).append(",");
					if (field.getFieldtype().contains("VARCHAR")) {
						value.append("'").append(row.get(field.getEnglishname().toLowerCase())).append("',");
					}
					else {
						value.append(row.get(field.getEnglishname().toLowerCase())).append(",");
					}
				}
				sb.deleteCharAt(sb.length() -1).append(" ) values ");
				value.deleteCharAt(value.length() - 1).append(" )");
				
				sb.append(value.toString());
				sqlList.add(sb.toString());
				//清空sb和value ，进行创建下一条sql
				sb.setLength(0);
				value.setLength(0);
			}
			if (sqlList.size() > 0) {
				b = dynamicService.insert(sqlList);
				
				if (b) {
					result = "保存错误，操作中止，请检查数据。";
					out.write(result);
					return null;
				}
			}
		} catch (Exception e) {
			result = "保存错误，操作中止，请检查数据。";
			out.write(result);
			return null;
		}
		
		out.write(result);
		return null;
	}
	
	public String updateImport() throws IOException {
		String result = "保存完毕。";
		PrintWriter out = getPrintWriter();
		
		Gson gson = new Gson();
		List<HashMap<String,String>> archiveList = new ArrayList<HashMap<String, String>>();
		
		try {
			//将传入的json字符串，转换成list
			archiveList = (List)gson.fromJson(importData, new TypeToken<List<HashMap<String,String>>>(){}.getType());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			result = "保存失败，请重新尝试，或与管理员联系。";
			out.write(result);
			return null;
		}
		
		if (archiveList.size() <= 0) {
			result = "没有找到数据，请重新尝试或与管理员联系。";
			out.write(result);
			return null;
		}
		
		List<SysTable> tableList = treeService.getTreeOfTable(archiveList.get(0).get("treeid").toString());
		//sb存储update语句
		StringBuffer sb = new StringBuffer();
		String tableName = "";
		//得到表名
		for (int i=0;i<tableList.size();i++) {
			if (tableList.get(i).getTabletype().equals(tableType)) {
				tableName = tableList.get(i).getTablename();
				break;
			}
		}
		List<SysTempletfield> fieldList = treeService.getTreeOfTempletfield(archiveList.get(0).get("treeid").toString(), tableType);
		boolean b = false;
		
		//存储sql语句
		List<String> sqlList = new ArrayList<String>();
		try {
			for (int z=0;z<archiveList.size();z++) {
				//创建insert sql
				HashMap<String,String> row = (HashMap<String,String>) archiveList.get(z);
				sb.append("update ").append(tableName).append(" set ");
				
				for (SysTempletfield field : fieldList) {
					if (!"id".equals(field.getEnglishname().toLowerCase())) {
						sb.append(field.getEnglishname().toLowerCase()).append("=");
						if (field.getFieldtype().contains("VARCHAR")) {
							sb.append("'").append(row.get(field.getEnglishname().toLowerCase())).append("',");
						}
						else {
							sb.append(row.get(field.getEnglishname().toLowerCase())).append(",");
						}
					}
				}
				sb.deleteCharAt(sb.length() -1).append(" where id='").append(row.get("id").toString()).append("'");
				
				sqlList.add(sb.toString());
				//清空sb，进行创建下一条sql
				sb.setLength(0);
			}
			if (sqlList.size() > 0) {
				b = dynamicService.update(sqlList);
				
				if (!b) {
					result = "保存错误，操作中止，请检查数据。";
					out.write(result);
					return null;
				}
			}
		} catch (Exception e) {
			result = "保存错误，操作中止，请检查数据。";
			out.write(result);
			return null;
		}
		
		out.write(result);
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

	public void setTreeService(ITreeService treeService) {
		this.treeService = treeService;
	}

	public String getImportData() {
		return importData;
	}

	public void setImportData(String importData) {
		this.importData = importData;
	}

	public void setDynamicService(IDynamicService dynamicService) {
		this.dynamicService = dynamicService;
	}

	public String getSelectAid() {
		return selectAid;
	}

	public void setSelectAid(String selectAid) {
		this.selectAid = selectAid;
	}
	
	
}

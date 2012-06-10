package com.yapu.archive.action;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yapu.archive.entity.*;
import com.yapu.archive.service.itf.IDynamicService;
import com.yapu.archive.service.itf.ITempletfieldService;
import com.yapu.archive.service.itf.ITreeService;
import com.yapu.system.common.BaseAction;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArchiveAction extends BaseAction {
	
	private static final long serialVersionUID = 4009249393951511369L;

	private ITreeService treeService;
	private IDynamicService dynamicService;
	private ITempletfieldService templetfieldService;
	private String treeid;
	private String tableType;
    private String selectAid;
    //如果是导入类型，读取字段时，去掉文件级标识
    private String importType;
	
	private String par;

	public String showArchive() {
		return SUCCESS;
	}

    public String showWjArchive() {
        return SUCCESS;
    }
	/*
	 * 创建档案动态字段
	 */
	public String createField() throws IOException {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/javascript");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		StringBuilder result = new StringBuilder();
		//如果没有得到树节点id，返回error
		if (null == treeid || "".equals(treeid)) {
			result.append("error");
			out.write(result.toString());
			return null;
		}
		List<SysTempletfield> fieldList = treeService.getTreeOfTempletfield(treeid,tableType);
		result.append("var fields = [[");
        //添加【文件级】【是否有全文】2个固定不需要编辑的字段。
        //1。得到treeid对应的templet，templet的type如果是A，则是标准档案，是F则是纯文件级。标准档案才添加文件级按钮
        SysTemplet templet = treeService.getTreeOfTemplet(treeid);
        if ("0".equals(importType)) {
	        if ("A".equals(templet.getTemplettype()) && "01".equals(tableType)) {
	            result.append("{field:'files',title:'文件级',width:45,align:'center',");
	            result.append("formatter:function(value){");
	            result.append("return  '<img src=\"../../images/icons/page.png\" title=\"点击查看文件级\" onclick=\"showWjTab(\\''+ value +'\\')\" />'  }},");
	//            result.append("return  '<a href=\"#\" onClick=\"showWjTab(\\''+ value +'\\')\" title=\"点击查看文件级\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=icon-table_add></a>'  }},");
	        }
	        //添加【是否有全文】标识
	        result.append("{field:'docs',title:'全文',width:30,align:'center',");
	        result.append("formatter:function(value){");
	        //得到表名 ，为了电子全文
	        List<SysTable> tableList = treeService.getTreeOfTable(treeid);
	        String tableid = "";
	        for (SysTable table :tableList) {
	            if (tableType.endsWith(table.getTabletype())) {
	                tableid = table.getTableid();
	            }
	        }
	        result.append("if (value==1) {return  '<img src=\"../../images/icons/flag_blue.png\" title=\"点击查看电子文件\" onclick=\"showDocTab(\\''+ value +'\\',\\'"+tableid+"\\')\" />'  }}},");
        }
		//返回字段特殊属性，例如默认值，在页面添加时，直接赋值
		StringBuilder fieldsDefaultValue = new StringBuilder();
		fieldsDefaultValue.append("; var fieldsDefaultValue = [");
		for (SysTempletfield field : fieldList) {
			if (!"".equals(field.getDefaultvalue())) {
				fieldsDefaultValue.append("{fieldname:'");
				fieldsDefaultValue.append(field.getEnglishname());
				fieldsDefaultValue.append("',fieldtype:'");
				fieldsDefaultValue.append(field.getFieldtype());
				fieldsDefaultValue.append("',value:'");
				fieldsDefaultValue.append(field.getDefaultvalue());
				fieldsDefaultValue.append("'},");
			}
			
			if (field.getSort() >= 0 && field.getIsgridshow() == 1) {
				result.append("{field:'");
				result.append(field.getEnglishname());
				result.append("',title:'");
				result.append(field.getChinesename());
				result.append("',width:");
				result.append(field.getFieldsize() * 1.5);
				if (field.getFieldtype().contains("INT")) {
					result.append(",align:'center'");
				}
				result.append(",editor:{");
//				if (field.getIsrequire() == 1) {
//					result.append("type:'validatebox',options:{required:true}");
//				}
//				else if (field.getFieldtype().contains("INT")) {//required:true,
//					result.append("type:'validatebox',options:{validType:'number',missingMessage:'请填写数字，不能为空！'}");
//				}
//				else {
//					result.append("type:'text'");
//				}
				
				String editTxt = "";
				if (field.getIsrequire() == 1) {
					editTxt = "type:'validatebox',options:{required:true,missingMessage:'请填写内容，不能为空！'}";
				}
				else {
					editTxt = "type:'text'";
				}
				
				if (field.getFieldtype().contains("INT")) {
					editTxt = "type:'validatebox',options:{required:true,validType:'number',missingMessage:'请填写数字！'}";
				}
				else if (field.getFieldtype().contains("INT") && field.getIsrequire() == 1){
					editTxt = "type:'validatebox',options:{required:true,validType:'number',missingMessage:'请填写数字，不能为空！'}";
				}
				//搞有代码的字段，我靠
				
				if (field.getIscode() == 1) {
					List<SysCode> codeList = templetfieldService.getTempletfieldOfCode(field.getTempletfieldid());
					if (codeList.size() > 0) {
						editTxt = "type:'combobox',options:{valueField	:'id',textField	:'name',data:[";
						for (SysCode sysCode : codeList) {
							editTxt += "{id:'" + sysCode.getColumndata() + "',name:'" + sysCode.getColumnname() + "'},";
						}
						editTxt = editTxt.substring(0, editTxt.length() -1);
						editTxt += "],required:true,missingMessage:'请选择，不能为空！'}";
//						editTxt = "type:'combobox',options:{valueField	:'id',textField	:'name',data:[{id:'1',name:'1'},{id:'2',name:'2'}],required:true}";
					}
					
				}
				
				result.append(editTxt);
				result.append("}");
				result.append("},");
			}
		}
		result.deleteCharAt(result.length() - 1).append("]]");
		fieldsDefaultValue.deleteCharAt(fieldsDefaultValue.length() - 1).append("]");
		result.append(fieldsDefaultValue.toString());
		out.write(result.toString());
		return null;
	}
	
	/*
	 * 获得档案动态字段   SlickGrid专用
	 */
	public String getField() throws IOException {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/javascript");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		StringBuilder result = new StringBuilder();
		//如果没有得到树节点id，返回error
		if (null == treeid || "".equals(treeid)) {
			result.append("error");
			out.write(result.toString());
			return null;
		}
		List<SysTempletfield> fieldList = treeService.getTreeOfTempletfield(treeid,tableType);
		result.append("var fields = [");
        //添加【文件级】【是否有全文】2个固定不需要编辑的字段。
        //1。得到treeid对应的templet，templet的type如果是A，则是标准档案，是F则是纯文件级。标准档案才添加文件级按钮
        SysTemplet templet = treeService.getTreeOfTemplet(treeid);
        if ("0".equals(importType)) {
	        if ("A".equals(templet.getTemplettype()) && "01".equals(tableType)) {
	            result.append("{field:'files',title:'文件级',width:45,align:'center',");
	            result.append("formatter:function(value){");
	            result.append("return  '<img src=\"../../images/icons/page.png\" title=\"点击查看文件级\" onclick=\"showWjTab(\\''+ value +'\\')\" />'  }},");
	//            result.append("return  '<a href=\"#\" onClick=\"showWjTab(\\''+ value +'\\')\" title=\"点击查看文件级\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=icon-table_add></a>'  }},");
	        }
	        //添加【是否有全文】标识
	        result.append("{field:'docs',title:'全文',width:30,align:'center',");
	        result.append("formatter:function(value){");
	        //得到表名 ，为了电子全文
	        List<SysTable> tableList = treeService.getTreeOfTable(treeid);
	        String tableid = "";
	        for (SysTable table :tableList) {
	            if (tableType.endsWith(table.getTabletype())) {
	                tableid = table.getTableid();
	            }
	        }
	        result.append("if (value==1) {return  '<img src=\"../../images/icons/flag_blue.png\" title=\"点击查看电子文件\" onclick=\"showDocTab(\\''+ value +'\\',\\'"+tableid+"\\')\" />'  }}},");
        }
        
//        {id: "start", name: "Start", field: "start", editor: Slick.Editors.Text},
        //增加行号字段
        result.append("{id:'ROWNUM',name:'行号',field:'ROWNUM',behavior:'select',cssClass:'cell-selection',width:40,cannotTriggerInsert:true,resizable:false,selectable:false},");
//		//返回字段特殊属性，例如默认值，在页面添加时，直接赋值
//		StringBuilder fieldsDefaultValue = new StringBuilder();
//		fieldsDefaultValue.append("; var fieldsDefaultValue = [");
		for (SysTempletfield field : fieldList) {
//			if (!"".equals(field.getDefaultvalue())) {
//				fieldsDefaultValue.append("{fieldname:'");
//				fieldsDefaultValue.append(field.getEnglishname());
//				fieldsDefaultValue.append("',fieldtype:'");
//				fieldsDefaultValue.append(field.getFieldtype());
//				fieldsDefaultValue.append("',value:'");
//				fieldsDefaultValue.append(field.getDefaultvalue());
//				fieldsDefaultValue.append("'},");
//			}
			
			if (field.getSort() >= 0 && field.getIsgridshow() == 1) {
				result.append("{id:'");
				result.append(field.getEnglishname());
				result.append("',name:'");
				result.append(field.getChinesename());
				result.append("',field:'");
				result.append(field.getEnglishname());
				result.append("',width:");
				result.append(field.getFieldsize() * 1.5);
				if (field.getFieldtype().contains("INT")) {
					result.append(",cssClass:'cell-center',editor:Slick.Editors.Integer");
//					result.append(",formatter:function(row,column,value) {");
//					result.append("if (value == '' || typeof (value) == 'undefined'){");
//					result.append("return 0;");
//					result.append("}");
//					result.append("return value");
//					result.append("}");
				}
				else if (field.getFieldtype().contains("VARCHAR")) {
					result.append(",editor:Slick.Editors.Text");
//					if (!"".equals(field.getDefaultvalue())) {
//						result.append(",formatter:function(row,column,value) {");
//						result.append("if (value == '' || typeof (value) == 'undefined'){");
//						result.append("return '").append(field.getDefaultvalue()).append("';");
//						result.append("}");
//						result.append("return value");
//						result.append("}");
//					}
				}
				
				if (field.getIsrequire() == 1) {
					result.append(",validator:requiredFieldValidator");
				}
				String editTxt = "";
				//搞有代码的字段，我靠
				if (field.getIscode() == 1) {
					List<SysCode> codeList = templetfieldService.getTempletfieldOfCode(field.getTempletfieldid());
					if (codeList.size() > 0) {
						editTxt = ",options:'";
						for (SysCode sysCode : codeList) {
							editTxt += "sysCode.getColumndata(),";
						}
						editTxt = editTxt.substring(0, editTxt.length() -1);
					}
					result.append(editTxt).append(", editor: SelectCellEditor");
				}
//				result.append("}");
				result.append("},");
			}
		}
		result.deleteCharAt(result.length() - 1).append("]");
//		fieldsDefaultValue.deleteCharAt(fieldsDefaultValue.length() - 1).append("]");
//		result.append(fieldsDefaultValue.toString());
		out.write(result.toString());
		return null;
	}
	
	public String list() throws IOException {
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		StringBuilder result = new StringBuilder();
		//如果没有得到树节点id，返回error
		if (null == treeid || "".equals(treeid)) {
			result.append("error");
			out.write(result.toString());
			return null;
		}
		
//		SysTree tree = new SysTree();
//		tree.setTreeid(treeid);
		//得到树节点对应的表集合
		List<SysTable> tableList = treeService.getTreeOfTable(treeid);
		
		DynamicExample de = new DynamicExample();
        DynamicExample.Criteria criteria = de.createCriteria();
        criteria.andEqualTo("treeid",treeid);
        if ("02".equals(tableType)) {
            criteria.andEqualTo("parentid",selectAid);
        }
//		de.createCriteria().andEqualTo("treeid", treeid);

		for (int i=0;i<tableList.size();i++) {
			if (tableList.get(i).getTabletype().equals(tableType)) {
				de.setTableName(tableList.get(i).getTablename());
			}
		}
		List dynamicList = dynamicService.selectByExample(de);
		
		//得到字段列表
		List<SysTempletfield> templetfieldList = treeService.getTreeOfTempletfield(treeid, tableType);
		
		StringBuffer sb = new StringBuffer();
		sb.append("{\"total\":").append(dynamicList.size()).append(",\"rows\":[");
		
		if(null!=dynamicList && dynamicList.size()>0){
			for (int i =0; i< dynamicList.size();i++) {
				HashMap tempMap = (HashMap) dynamicList.get(i);
				sb.append("{");
                //添加文件级标识
                SysTemplet templet = treeService.getTreeOfTemplet(treeid);
                if ("A".equals(templet.getTemplettype())) {
                    sb.append("\"files\":\"").append(tempMap.get("ID").toString()).append("\",");
                }
                //添加电子全文标识
                sb.append("\"docs\":\"1\",");
				for (SysTempletfield sysTempletfield : templetfieldList) {
					if (sysTempletfield.getFieldtype().contains("VARCHAR")) {
						if (null == tempMap.get(sysTempletfield.getEnglishname())) {
							sb.append("\""+sysTempletfield.getEnglishname()+"\":\"\",");
						}
						else {
							sb.append("\""+sysTempletfield.getEnglishname()+"\":\""+tempMap.get(sysTempletfield.getEnglishname())+"\",");
						}
					}
					else if (sysTempletfield.getFieldtype().contains("INT")) {
						sb.append("\""+sysTempletfield.getEnglishname()+"\":"+tempMap.get(sysTempletfield.getEnglishname())+",");
					}
				}
				sb.deleteCharAt(sb.length() - 1).append("},");
			}
		}
		sb.deleteCharAt(sb.length() - 1).append("]}");
		out.write(sb.toString());
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
//			Map<String, List<HashMap<String,String>>> archiveMap = new HashMap<String, List<HashMap<String,String>>>();
            Map<String,List<HashMap<String,String>>> archiveMap = new HashMap<String, List<HashMap<String, String>>>();
			try {
				archiveMap = (Map)gson.fromJson(par, new TypeToken<Map<String, List<HashMap<String,String>>>>(){}.getType());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				result = "保存失败，请重新尝试，或与管理员联系。";
				out.write(result);
				return null;
			}
			//处理添加的
			List<HashMap<String,String>> insertList = archiveMap.get("inserted");
			if (insertList.size() > 0) {
				insert(insertList);
			}
//			//处理更新的
			List<HashMap<String,String>> updateList = archiveMap.get("updated");
			if (updateList.size() > 0) {
				update(updateList);
			}
			//处理删除
			List<HashMap<String,String>> deleteList = archiveMap.get("deleted");
			if (deleteList.size() > 0) {
				delete(deleteList);
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
	 * @param rowList
	 * @return
	 */
	public boolean insert(List<HashMap<String,String>> rowList) {
		boolean result = false;
		if (rowList.size() <= 0) {
			return result;
		}
		
		List<SysTable> tableList = treeService.getTreeOfTable(rowList.get(0).get("TREEID").toString());
		//sb存储insert语句values前的
		StringBuffer sb = new StringBuffer();
		//value 存储values之后的
		StringBuffer value = new StringBuffer();
		String tableName = "";
		//得到表名
		for (int i=0;i<tableList.size();i++) {
			if (tableList.get(i).getTabletype().equals(tableType)) {
				tableName = tableList.get(i).getTablename();
			}
		}
		List<SysTempletfield> fieldList = treeService.getTreeOfTempletfield(rowList.get(0).get("TREEID").toString(), tableType);
		boolean b = false;
		for (int z=0;z<rowList.size();z++) {
			//创建insert sql
			HashMap<String,String> row = (HashMap<String,String>) rowList.get(z);
			sb.append("insert into ").append(tableName);
			
			sb.append(" (");
			value.append(" (");
			for (SysTempletfield field : fieldList) {
				sb.append(field.getEnglishname()).append(",");
				if (field.getFieldtype().contains("VARCHAR")) {
					value.append("'").append(row.get(field.getEnglishname())).append("',");
				}
				else {
					value.append(row.get(field.getEnglishname())).append(",");
				}
			}
			sb.deleteCharAt(sb.length() -1).append(" ) values ");
			value.deleteCharAt(value.length() - 1).append(" )");
			
			sb.append(value.toString());
//			b = dynamicService.insert(sb.toString());
			//清空sb和value ，进行创建下一条sql
			sb.setLength(0);
			value.setLength(0);
		}
		
		if (b) {
			result = true;
		}
		
		return result;
	}
	
	private boolean update(List<HashMap<String,String>> rowList) {
		boolean result = false;
		if (rowList.size() <= 0) {
			return result;
		}
		
		List<SysTable> tableList = treeService.getTreeOfTable(rowList.get(0).get("TREEID").toString());
		//sb存储update语句
		StringBuffer sb = new StringBuffer();
		String tableName = "";
		//得到表名
		for (int i=0;i<tableList.size();i++) {
			if (tableList.get(i).getTabletype().equals(tableType)) {
				tableName = tableList.get(i).getTablename();
			}
		}
		List<SysTempletfield> fieldList = treeService.getTreeOfTempletfield(rowList.get(0).get("TREEID").toString(), tableType);
		boolean b = false;
		for (int z=0;z<rowList.size();z++) {
			//创建 sql
			HashMap<String,String> row = (HashMap<String,String>) rowList.get(z);
			sb.append("update ").append(tableName).append(" set ");
			
			for (SysTempletfield field : fieldList) {
				if (!"ID".equals(field.getEnglishname())) {
					sb.append(field.getEnglishname()).append(" = ");
					if (field.getFieldtype().contains("VARCHAR")) {
						sb.append("'").append(row.get(field.getEnglishname())).append("',");
					}
					else {
						sb.append(row.get(field.getEnglishname())).append(",");
					}
				}
			}
			sb.deleteCharAt(sb.length() -1).append(" where id = '").append(row.get("ID").toString()).append("'");
			b = dynamicService.update((sb.toString()));
			//清空sb ，进行创建下一条sql
			sb.setLength(0);
		}
		
		if (b) {
			result = true;
		}
		
		return result;
	}
	
	private boolean delete(List<HashMap<String,String>> rowList) {
		boolean result = false;
		if (rowList.size() <= 0) {
			return result;
		}
		List<SysTable> tableList = treeService.getTreeOfTable(rowList.get(0).get("TREEID").toString());
		//sb存储delete语句
		StringBuffer sb = new StringBuffer();
		String tableName = "";
		//得到表名
		for (int i=0;i<tableList.size();i++) {
			if (tableList.get(i).getTabletype().equals(tableType)) {
				tableName = tableList.get(i).getTablename();
			}
		}
		sb.append("delete from ").append(tableName).append(" where id in (");
		for (int z=0;z<rowList.size();z++) {
			//得到id集合
			HashMap<String,String> row = (HashMap<String,String>) rowList.get(z);
			sb.append("'").append(row.get("ID").toString()).append("',");
		}
		sb.deleteCharAt(sb.length() - 1).append(")");
		int b = dynamicService.delete((sb.toString()));
		//TODO 要删除挂接的物理文件
		if (b > 0) {
			result = true;
		}
		
		return result;
	}
	

//	/**
//	 * 删除
//	 * @param 
//	 * @return
//	 */
//	public boolean delDocserver(String docserverid) {
//		boolean result = false;
//		if (null != docserverid && !"".equals(docserverid)) {
//			int num = docserverService.deleteDocserver(docserverid);
//			if (num >0) {
//				result = true;
//			}
//		}
//		return result;
//	}



	public String getTreeid() {
		return treeid;
	}
	public void setTreeid(String treeid) {
		this.treeid = treeid;
	}
	public void setTreeService(ITreeService treeService) {
		this.treeService = treeService;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public void setDynamicService(IDynamicService dynamicService) {
		this.dynamicService = dynamicService;
	}
	public String getPar() {
		return par;
	}
	public void setPar(String par) {
		this.par = par;
	}
	public void setTempletfieldService(ITempletfieldService templetfieldService) {
		this.templetfieldService = templetfieldService;
	}
    public String getSelectAid() {
            return selectAid;
        }
    public void setSelectAid(String selectAid) {
        this.selectAid = selectAid;
    }

	public String getImportType() {
		return importType;
	}

	public void setImportType(String importType) {
		this.importType = importType;
	}
    
}

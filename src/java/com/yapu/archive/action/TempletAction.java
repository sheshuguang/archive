package com.yapu.archive.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.SystemPropertyUtils;

import com.yapu.archive.entity.SysTable;
import com.yapu.archive.entity.SysTableExample;
import com.yapu.archive.entity.SysTemplet;
import com.yapu.archive.entity.SysTempletExample;
import com.yapu.archive.service.itf.ITableService;
import com.yapu.archive.service.itf.ITempletService;
import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysOrg;

public class TempletAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private ITempletService templetService;
	private ITableService tableService;
	
	private String templetname;
	private String templetid;
	//创建档案库时，选择的以哪个档案库为标准创建
	private String copyTempletid;
	
	
	public String loadTempletTreeData() throws IOException {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		StringBuilder resultStr = new StringBuilder();
//		String resultStr;
		
		//获得模板表所有数据
		SysTempletExample example = new SysTempletExample();
		List<SysTemplet> templetList = templetService.selectByWhereNotPage(example);
		
		//获得表名管理表所有数据
		SysTableExample tableExample = new SysTableExample();
		tableExample.setOrderByClause("tabletype asc");
		List<SysTable> tableList = tableService.selectByWhereNotPage(tableExample);
		
		if(null!=templetList && templetList.size()>0){
			resultStr.append("[{\"id\":\"0\",\"text\":\"档案模板管理\",\"iconCls\":\"\",\"state\":\"open\",\"children\":[");
			for(int i=0;i<templetList.size();i++){
				SysTemplet temp =(SysTemplet)templetList.get(i);
				resultStr.append("{");
				resultStr.append("\"id\": \""+temp.getTempletid()+"\", \"text\": \""+temp.getTempletname()+"\", \"iconCls\": \"\", \"state\": \"closed\",\"children\":[");
				for (SysTable sysTable : tableList) {
					if (temp.getTempletid().equals(sysTable.getTempletid())) {
						resultStr.append("{");
						resultStr.append("\"id\": \""+sysTable.getTableid()+"\", \"text\": \""+sysTable.getTablelabel()+"\", \"iconCls\": \"icon-table\"");
						resultStr.append("},");
					}
				}
				resultStr.deleteCharAt(resultStr.length() -1).append("]");
				resultStr.append("},");
			}
			resultStr.deleteCharAt(resultStr.length() - 1).append("]}]");
			out.write(resultStr.toString());
		}
		return null;
	}
	
	/**
	 * 新建帐户组
	 * @return
	 * @throws IOException
	 */
	public String insert() throws IOException {
		String result =	"保存时出现错误，请重试或与管理员联系。";
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		if (null == templetname || "".equals(templetname)) {
			out.write(result);
			return null;
		}
		SysTemplet templet = new SysTemplet();
		templet.setTempletid(templetid);
		templet.setTempletname(templetname);
		if(templetService.insertTemplet(templet,copyTempletid)){
			result="保存成功！";
		}
		out.write(result);
		return null;
	}
	
	public String delete() throws IOException {
		String result =	"删除档案库错误，请重试或与管理员联系！";
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		if (null != templetid && !"".equals(templetid)) {
			int num = templetService.deleteTemplet(templetid);
			if (num >0) {
				result = "删除档案库成功！";
			}
		}
		out.write(result);
		return null;
	}

	public void setTempletService(ITempletService templetService) {
		this.templetService = templetService;
	}
	public void setTableService(ITableService tableService) {
		this.tableService = tableService;
	}
	
	public String getTempletname() {
		return templetname;
	}

	public void setTempletname(String templetname) {
		this.templetname = templetname;
	}

	public String getTempletid() {
		return templetid;
	}
	public void setTempletid(String templetid) {
		this.templetid = templetid;
	}
	public String getCopyTempletid() {
		return copyTempletid;
	}
	public void setCopyTempletid(String copyTempletid) {
		this.copyTempletid = copyTempletid;
	}
}

package com.yapu.archive.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yapu.archive.entity.SysDoc;
import com.yapu.archive.entity.SysTable;
import com.yapu.archive.service.itf.IDocService;
import com.yapu.archive.service.itf.IDynamicService;
import com.yapu.archive.service.itf.ITableService;
import com.yapu.archive.service.itf.ITreeService;
import com.yapu.system.common.BaseAction;

public class AttachmentAction extends BaseAction {
	
	private static final long serialVersionUID = -2068973158929387544L;
	
	private String items;
	private String treeid;
	private String tableType;
	
	private ITreeService treeService;
	private IDocService docService;
	private IDynamicService dynamicService;

	
	/**
	 * 挂接电子全文
	 * @return
	 * @throws IOException 
	 */
	public String insertBatchAttachmentArchive() throws IOException {
		
		String result = "保存完毕。";
		PrintWriter out = getPrintWriter();
		
		Gson gson = new Gson();
//		List<HashMap<String,String>> itemsList = new ArrayList<HashMap<String, String>>();
		List<SysDoc> itemsList = new ArrayList<SysDoc>();
		
		try {
			//将传入的json字符串，转换成list
//			itemsList = (List)gson.fromJson(items, new TypeToken<List<HashMap<String,String>>>(){}.getType());
			itemsList = (List<SysDoc>)gson.fromJson(items, new TypeToken<List<SysDoc>>(){}.getType());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			result = "保存失败，请重新尝试，或与管理员联系。";
			out.write(result);
			return null;
		}
		
		if (itemsList.size() <= 0) {
			result = "没有找到数据，请重新尝试或与管理员联系。";
			out.write(result);
			return null;
		}
		//得到table
		List<SysTable> tableList = treeService.getTreeOfTable(treeid);
		String tableName = "";
		String tableid = "";
		//得到表名
		for (int i=0;i<tableList.size();i++) {
			if (tableList.get(i).getTabletype().equals(tableType)) {
				tableName = tableList.get(i).getTablename();
				tableid = tableList.get(i).getTableid();
				break;
			}
		}
		for (int i=0;i<itemsList.size();i++) {
			itemsList.get(i).setTableid(tableid);
		}
		
		//保存到doc表
		int num = docService.updateDoc(itemsList);
		if (num>0) {
			out.write(result);
		}
		else {
			result = "保存失败，请重新尝试，或与管理员联系。";
			out.write(result);
		}
		
		return null;
	}


	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
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
	public void setDocService(IDocService docService) {
		this.docService = docService;
	}
	public void setDynamicService(IDynamicService dynamicService) {
		this.dynamicService = dynamicService;
	}

}

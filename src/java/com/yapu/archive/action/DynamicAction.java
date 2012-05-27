package com.yapu.archive.action;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yapu.archive.entity.DynamicExample;
import com.yapu.archive.entity.SysTable;
import com.yapu.archive.entity.SysTree;
import com.yapu.archive.service.itf.IDynamicService;
import com.yapu.archive.service.itf.ITreeService;
import com.yapu.system.common.BaseAction;

public class DynamicAction extends BaseAction {
	
	private IDynamicService dynamicService;
	private ITreeService treeService;
	
//	public String loadArchiveData(JsonArray data) {
//		assert data != null;
//		JsonElement element = data.get(0);
//		JsonObject bean = element.getAsJsonObject();
//		String treeid = bean.get("treeid").getAsString();
//		String tableType = bean.get("tabletype").getAsString();
//		
////		SysTree tree = new SysTree();
////		tree.setTreeid(treeid);
//		//得到树节点对应的表集合
//		List<SysTable> tableList = treeService.getTreeOfTable(treeid);
//		
//		DynamicExample de = new DynamicExample();
//		de.createCriteria().andEqualTo("treeid", treeid);
//		
//		for (int i=0;i<tableList.size();i++) {
//			if (tableList.get(i).getTabletype().equals(tableType)) {
//				de.setTableName(tableList.get(i).getTablename());
//			}
//		}
//		List dynamicList = dynamicService.selectByExample(de);
//		
//		StoreData storeData = new StoreData();
//		storeData.total = dynamicList.size();
//		storeData.results = dynamicList;
//		return storeData;
//	}

	public void setDynamicService(IDynamicService dynamicService) {
		this.dynamicService = dynamicService;
	}
	public void setTreeService(ITreeService treeService) {
		this.treeService = treeService;
	}
}

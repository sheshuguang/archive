package com.yapu.archive.action;


/**
 * 索引操作类
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yapu.archive.entity.DynamicExample;
import com.yapu.archive.entity.SysTable;
import com.yapu.archive.entity.SysTableExample;
import com.yapu.archive.entity.SysTemplet;
import com.yapu.archive.entity.SysTempletExample;
import com.yapu.archive.entity.SysTempletExample.Criteria;
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.entity.SysTempletfieldExample;
import com.yapu.archive.service.itf.IDynamicService;
import com.yapu.archive.service.itf.IIndexerService;
import com.yapu.archive.service.itf.ITableService;
import com.yapu.archive.service.itf.ITempletService;
import com.yapu.archive.service.itf.ITempletfieldService;
import com.yapu.system.common.BaseAction;
import com.yapu.system.util.lucene.Indexer;

public class IndexerAction extends BaseAction {

	private static final long serialVersionUID = 3775364826517638693L;
	
	private ITableService tableService;
	private ITempletService templetService;
	private ITempletfieldService templetfieldService;
	private IIndexerService indexerService;
	private IDynamicService dynamicService;
	
	private List<SysTemplet> getTemplet() {
		SysTempletExample te = new SysTempletExample();
		Criteria criteria = te.createCriteria();
		criteria.andTemplettypeNotEqualTo("CA").andTemplettypeNotEqualTo("CF");
		List<SysTemplet> templetList = templetService.selectByWhereNotPage(te);
		return templetList;
	}
	
	private List<SysTable> getTable(String templetid) {
		SysTableExample te = new SysTableExample();
		SysTableExample.Criteria criteria = te.createCriteria();
		criteria.andTempletidEqualTo(templetid);
		List<SysTable> tableList = tableService.selectByWhereNotPage(te);
		return tableList;
	}
	
	private List<SysTempletfield> getField(String tableid) {
		SysTempletfieldExample te = new SysTempletfieldExample();
		SysTempletfieldExample.Criteria criteria = te.createCriteria();
		criteria.andTableidEqualTo(tableid);
		List<SysTempletfield> templetFieldList = templetfieldService.selectByWhereNotPage(te);
		return templetFieldList;
	}
	
	public void createIndexerOnCREATE() {
		createIndexer("CREATE");
	}
	
	private void createIndexer(String openMode) {
		List<SysTemplet> templetList = getTemplet();
		for(SysTemplet temp :templetList) {
			List<SysTable> tableList = getTable(temp.getTempletid());
			for(SysTable table :tableList) {
				List<SysTempletfield> fieldList = getField(table.getTableid());
				DynamicExample de = new DynamicExample();
//		        DynamicExample.Criteria criteria = de.createCriteria();
		        de.setTableName(table.getTablename());
		        List dynamicList = dynamicService.selectByExample(de);
		        if (dynamicList.size() > 0) {
		        	indexerService.createIndex(table.getTablename(), fieldList, dynamicList, openMode);
		        }
			}
		}
	}
	/**
	 * 删除索引 （测试代码。暂不开发删除、更新、追加索引功能）
	 * @return
	 * @throws IOException
	 */
	public String deleteIndexer() throws IOException {
		Indexer indexer = new Indexer();
		
		indexer.deleteIndexer("A_C51C96AC_01", "C578EA96D2900001D67AA9D01CB0182B");
		return null;
	}
	/**
	 * 追加索引
	 * @return
	 */
	public String appendIndexer() {
		SysTable table = new SysTable();
		table.setTablename("A_C51C96AC_01");
		table.setTableid("bbea8638-ce81-4d78-9143-37ae305a9415");
		List<SysTempletfield> fieldList = getField(table.getTableid());
		
		List dynamicList = new ArrayList();
		
		HashMap map = new HashMap();
		map.put("ID", "asdfasdf");
		map.put("TREEID", "wwwwww");
		map.put("AJH", "ajh");
		map.put("TM", "题名");
		map.put("ZRZ", "北京");
		
		dynamicList.add(map);
		indexerService.createIndex(table.getTablename(), fieldList, dynamicList, "CREATE_OR_APPEND");
		return null;
	}
	/**
	 * 更新索引
	 * @return
	 * @throws IOException
	 */
	public String updateIndexer() throws IOException {
		SysTable table = new SysTable();
		table.setTablename("A_C51C96AC_01");
		table.setTableid("bbea8638-ce81-4d78-9143-37ae305a9415");
		List<SysTempletfield> fieldList = getField(table.getTableid());
		
		List dynamicList = new ArrayList();
		
		HashMap map = new HashMap();
		map.put("ID", "C560271B45F000013A3971D011101DAD");
		map.put("TREEID", "FFFFF");
		map.put("AJH", "ajh");
		map.put("TM", "题名dddddd");
		map.put("ZRZ", "北京ddddddd");
		
		dynamicList.add(map);
		Indexer indexer = new Indexer();
		indexer.updateIndexer(table.getTablename(), fieldList, dynamicList);
		
//		indexerService.createIndex(table.getTablename(), fieldList, dynamicList, "CREATE_OR_APPEND");
		return null;
	}
	
	
	public void setTableService(ITableService tableService) {
		this.tableService = tableService;
	}
	public void setTempletService(ITempletService templetService) {
		this.templetService = templetService;
	}
	public void setTempletfieldService(ITempletfieldService templetfieldService) {
		this.templetfieldService = templetfieldService;
	}
	public void setIndexerService(IIndexerService indexerService) {
		this.indexerService = indexerService;
	}

	public void setDynamicService(IDynamicService dynamicService) {
		this.dynamicService = dynamicService;
	}
	
	
	
}

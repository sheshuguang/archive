package com.yapu.archive.action;


/**
 * 索引操作类
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.yapu.archive.entity.DynamicExample;
import com.yapu.archive.entity.SysDoc;
import com.yapu.archive.entity.SysDocExample;
import com.yapu.archive.entity.SysDocserver;
import com.yapu.archive.entity.SysDocserverExample;
import com.yapu.archive.entity.SysTable;
import com.yapu.archive.entity.SysTableExample;
import com.yapu.archive.entity.SysTemplet;
import com.yapu.archive.entity.SysTempletExample;
import com.yapu.archive.entity.SysTempletExample.Criteria;
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.entity.SysTempletfieldExample;
import com.yapu.archive.service.itf.IDocService;
import com.yapu.archive.service.itf.IDocserverService;
import com.yapu.archive.service.itf.IDynamicService;
import com.yapu.archive.service.itf.IIndexerService;
import com.yapu.archive.service.itf.ITableService;
import com.yapu.archive.service.itf.ITempletService;
import com.yapu.archive.service.itf.ITempletfieldService;
import com.yapu.system.common.BaseAction;
import com.yapu.system.util.ReadFile;
import com.yapu.system.util.lucene.Indexer;

public class IndexerAction extends BaseAction {

	private static final long serialVersionUID = 3775364826517638693L;
	
	private ITableService tableService;
	private ITempletService templetService;
	private ITempletfieldService templetfieldService;
	private IIndexerService indexerService;
	private IDynamicService dynamicService;
	private IDocserverService docServerService;
	private IDocService docService;
	
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
	 * 创建电子全文索引
	 * @return
	 * @throws Exception 
	 */
	public String createFilesIndexer() throws Exception {
		
		PrintWriter out = this.getPrintWriter();
		
		//取得当前开启的服务器
		SysDocserverExample sde = new SysDocserverExample();
		SysDocserverExample.Criteria criteria = sde.createCriteria();
		criteria.andServerstateEqualTo(1);
		List<SysDocserver> docserverList = docServerService.selectByWhereNotPage(sde);
		SysDocserver docserver = docserverList.get(0);
		
		//取得当前开启服务器下的文本型全文
		
		
		//TODO 跟阿佘说，他那电子文件库上传的文件路径不能带  /  杠，否则挂接时报错javascript.  商量一下上传的统一标准
		SysDocExample example = new SysDocExample();
        SysDocExample.Criteria docCriteria1 = example.createCriteria();
        docCriteria1.andDocserveridEqualTo(docserver.getDocserverid());
        docCriteria1.andFileidNotEqualTo("");
        SysDocExample.Criteria docCriteria2 = example.createCriteria();
        docCriteria2.andFileidIsNotNull();
        example.or(docCriteria2);
    	List<SysDoc> docList = docService.selectByWhereNotPage(example);
    	
    	List<SysDoc> list = new ArrayList<SysDoc>();
    	
    	
    	HashMap<String,String> map = new HashMap<String, String>();
    	if ("LOCAL".equals(docserver.getServertype())) {
    		for (SysDoc doc :docList) {
        		String content = "";
        		String ext = doc.getDocext();
        		ReadFile read = new ReadFile();
        		String serverPath = docserver.getServerpath();
        		if (!serverPath.substring(serverPath.length()-1,serverPath.length()).equals("/")) {
            		serverPath += "/";
                }
        		String path = serverPath + doc.getDocpath();
        		
        		//处理doc文档
        		if ("DOC".equals(ext)) {
        			content = read.readWord(path);
        			list.add(doc);
        		}//处理doc2007格式
        		else if ("DOCX".equals(ext)) {
        			content = read.readWord2007(path);
        			list.add(doc);
        		}//处理xls格式
        		else if("XLS".equals(ext)) {
        			content = read.ReadExcel(path);
        			list.add(doc);
        		}//处理xlsx格式  2007格式
        		else if("XLSX".equals(ext)) {
        			content = read.readExcel2007(path);
        			list.add(doc);
        		}//处理txt格式
        		else if("TXT".equals(ext)) {
        			content = read.readTxt(path);
        			list.add(doc);
        		}
        		else if("PDF".equals(ext)) {
        			content = read.readPdf(path);
        			list.add(doc);
        		}
        		else if("PPT".equals(ext)) {
        			content = read.readPowerPoint(path);
        			list.add(doc);
        		}
        		else {
        			content = "";
        		}
        		
        		if (!"".equals(content)) {
        			map.put(doc.getDocid(), content);
        		}
        	}
    	}
    	else {
    		//TODO 处理ftp类型的
    	}
    	
    	
    	if (!map.isEmpty()) {
    		String result = indexerService.createIndex(docserver.getDocserverid(), list, map, "CREATE");
    		Gson gson = new Gson();
    		out.write(gson.toJson(result));
    		return null;
    	}
		return null;
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


	public IDocService getDocService() {
		return docService;
	}

	public void setDocService(IDocService docService) {
		this.docService = docService;
	}

	public void setDocServerService(IDocserverService docServerService) {
		this.docServerService = docServerService;
	}
	
	
	
	
	
	
}

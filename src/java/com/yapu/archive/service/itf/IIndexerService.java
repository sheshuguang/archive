package com.yapu.archive.service.itf;

import java.util.HashMap;
import java.util.List;

import com.yapu.archive.entity.SysDoc;

/**
 * 索引操作专用服务
 * @author wangf
 *
 */
public interface IIndexerService {
	
	//创建索引
	void createIndex(String tablename,List fieldList,List dataList,String openMode);
	
	//创建电子全文索引
	String createIndex(String docServerid,List<SysDoc> docList,HashMap<String,String> contentMap,String openMode);
}

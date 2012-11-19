package com.yapu.archive.service.itf;

import java.util.List;

/**
 * 索引操作专用服务
 * @author wangf
 *
 */
public interface IIndexerService {
	
	//创建索引
	void createIndex(String tablename,List fieldList,List dataList,String openMode);
}

package com.yapu.archive.service.impl;
/**
 * 索引服务
 */

import java.util.List;

import com.yapu.archive.service.itf.IIndexerService;
import com.yapu.system.util.lucene.Indexer;

public class IndexerService implements IIndexerService {

	@Override
	public void createIndex(String tablename, List fieldList, List dataList,
			String openMode) {
		Indexer indexer = new Indexer();
		indexer.CreateIndex(tablename, fieldList, dataList, openMode);

	}

}

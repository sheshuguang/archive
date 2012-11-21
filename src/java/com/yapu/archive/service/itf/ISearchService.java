package com.yapu.archive.service.itf;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;


/**
 * 搜索服务
 * @author wangf
 *
 */
public interface ISearchService {

	List search(String tableName, String[] fields, List<String> fieldsList,String searchTxt) throws IOException;
}

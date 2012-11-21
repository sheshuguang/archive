package com.yapu.archive.service.impl;

/**
 * 搜索服务实现类
 * 2012-11-18 wangf
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.struts2.ServletActionContext;

import com.yapu.archive.service.itf.ISearchService;

public class SearchService implements ISearchService {

	private String indexDir = "/LUCENE";

	@SuppressWarnings("unused")
	@Override
	public List search(String tableName, String[] fields,
			List<String> fieldsList, String searchTxt) throws IOException {

		String path = indexDir + "/" + tableName;
		IndexSearcher indexSearch = null;

		String tableIndexDir = ServletActionContext.getServletContext()
				.getRealPath(path) + File.separator;

		try {
			if (!(new File(tableIndexDir).isDirectory())) {
				return null;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		try {
			Directory dir = new SimpleFSDirectory(new File(tableIndexDir));

			IndexReader reader = IndexReader.open(dir);
			// 创建 IndexSearcher对象
			indexSearch = new IndexSearcher(reader);

			// 创建一个分词器,和创建索引时用的分词器要一致
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);

			// 创建QueryParser对象,第一个参数表示Lucene的版本,第二个表示搜索Field的字段,第三个表示搜索使用分词器
			QueryParser queryParser = new MultiFieldQueryParser(
					Version.LUCENE_36, fields, analyzer);

			// 生成Query对象
			Query query = queryParser.parse(searchTxt); // 查询hello这个词
			// 搜索结果 TopDocs里面有scoreDocs[]数组，里面保存着索引值
			TopDocs hits = indexSearch.search(query, 10);
			// hits.totalHits表示一共搜到多少个
			System.out.println("找到了" + hits.totalHits + "个");
			// 声明返回用的list
			List resultList = new ArrayList();

			// 循环hits.scoreDocs数据，并使用indexSearch.doc方法把Document还原，再拿出对应的字段的值
			for (int i = 0; i < hits.scoreDocs.length; i++) {
				ScoreDoc sdoc = hits.scoreDocs[i];
				Document doc = indexSearch.doc(sdoc.doc);
				HashMap map = new HashMap();
				for (int j=0;j<fieldsList.size();j++) {
					map.put(fieldsList.get(j).toString(), doc.get(fieldsList.get(j)));
				}
				resultList.add(map);
				
				// System.out.println("==========================");
				// System.out.println(doc.get("id"));
				// System.out.println(doc.get("ajh"));
				// System.out.println(doc.get("gddw"));
				// System.out.println(doc.get("zrz"));
				// System.out.println(doc.get("csd"));
				// System.out.println("==========================");

			}
			indexSearch.close();
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				indexSearch.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

}

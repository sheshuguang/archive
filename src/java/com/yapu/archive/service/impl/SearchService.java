package com.yapu.archive.service.impl;

/**
 * 搜索服务实现类
 * 2012-11-18 wangf
 */

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.struts2.ServletActionContext;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.service.itf.ISearchService;

public class SearchService implements ISearchService {

	private String indexDir = "/LUCENE";

	/*
	 * (non-Javadoc)
	 * @see com.yapu.archive.service.itf.ISearchService#searchNumber(java.lang.String, java.lang.String[], java.lang.String, java.lang.String)
	 */
	@Override
	public HashMap searchNumber(String tableName, String[] fields,
			String searchTxt, String treeid) {
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
			// Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);  //默认的分词（汉字单个分。英文按词分）
			Analyzer analyzer = new IKAnalyzer();

			Query query = new TermQuery(new Term("treeid", treeid));

			QueryParser qp1 = new MultiFieldQueryParser(Version.LUCENE_36,fields, analyzer);
			// 设置检索的条件.OR_OPERATOR表示"或"
			qp1.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query1 = qp1.parse(searchTxt);

			BooleanQuery m_BooleanQuery = new BooleanQuery();// 得到一个组合检索对象

			m_BooleanQuery.add(query1, BooleanClause.Occur.MUST);
			m_BooleanQuery.add(query, BooleanClause.Occur.MUST);

			// 搜索结果 TopDocs里面有scoreDocs[]数组，里面保存着索引值
			TopDocs hits = indexSearch.search(m_BooleanQuery, 30);
			// hits.totalHits表示一共搜到多少个
//			System.out.println("找到了" + hits.totalHits + "个");
			HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
			resultMap.put(treeid, hits.totalHits);
			indexSearch.close();
			return resultMap;
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
	/*
	 * (non-Javadoc)
	 * @see com.yapu.archive.service.itf.ISearchService#search(java.lang.String, java.util.List, java.lang.String, int, int)
	 */
	@Override
	public HashMap search(String tableName,List<SysTempletfield> tmpList, String searchTxt,int currentPage,int pageSize)
			throws IOException {
		
		if (currentPage <= 0) {
			currentPage = 1;
        }
        if (pageSize <= 0) {
            pageSize = 20;
        }
      //生成返回map
		HashMap resultMap = new HashMap();
		// 声明返回用的list
		List resultList = new ArrayList();
		
        int start = (currentPage - 1) * pageSize;

		String path = indexDir + "/" + tableName;
		IndexSearcher indexSearch = null;

		String tableIndexDir = ServletActionContext.getServletContext()
				.getRealPath(path) + File.separator;

		try {
			if (!(new File(tableIndexDir).isDirectory())) {
				resultMap.put("DATA", resultList);
				resultMap.put("ROWCOUNT", 0);
				resultMap.put("PAGES", 0);
				return resultMap;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		//临时list。用来创建查询字段数组
		List<String> searchFieldsList = new ArrayList<String>();
		
		List<String> fieldsList = new ArrayList<String>();
		
		for (int i=0;i<tmpList.size();i++) {
			fieldsList.add(tmpList.get(i).getEnglishname().toLowerCase());
			if (tmpList.get(i).getIssearch() == 1) {
				searchFieldsList.add(tmpList.get(i).getEnglishname().toLowerCase());
			}
		}
		//生成查询字段
		String[] fields = new String[searchFieldsList.size()];
		searchFieldsList.toArray(fields);
		
		try {
			Directory dir = new SimpleFSDirectory(new File(tableIndexDir));

			IndexReader reader = IndexReader.open(dir);
			// 创建 IndexSearcher对象
			indexSearch = new IndexSearcher(reader);

			// 创建一个分词器,和创建索引时用的分词器要一致
			// Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
			Analyzer analyzer = new IKAnalyzer();

			// 创建QueryParser对象,第一个参数表示Lucene的版本,第二个表示搜索Field的字段,第三个表示搜索使用分词器
			QueryParser queryParser = new MultiFieldQueryParser(
					Version.LUCENE_36, fields, analyzer);
			queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
			// 生成Query对象
			Query query = queryParser.parse(searchTxt); // 查询这个词
			
			BooleanQuery m_BooleanQuery = new BooleanQuery();// 得到一个组合检索对象
			m_BooleanQuery.add(query, BooleanClause.Occur.MUST);
			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
					"<span style='color:red'>", "</span>");
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter,
					new QueryScorer(m_BooleanQuery));

			// 搜索结果 TopDocs里面有scoreDocs[]数组，里面保存着索引值
			int hm = start + pageSize;
            TopScoreDocCollector res = TopScoreDocCollector.create(hm, false);
			indexSearch.search(m_BooleanQuery, res);
//			TopDocs hits = indexSearch.search(m_BooleanQuery, 30);
			int rowCount = res.getTotalHits();
			int pages = (rowCount - 1) / pageSize + 1; //计算总页数
			TopDocs hits = res.topDocs(start, pageSize);
			
			// hits.totalHits表示一共搜到多少个
			System.out.println("找到了" + hits.totalHits + "个");

			// 循环hits.scoreDocs数据，并使用indexSearch.doc方法把Document还原，再拿出对应的字段的值
			for (int i = 0; i < hits.scoreDocs.length; i++) {
				ScoreDoc sdoc = hits.scoreDocs[i];
				Document doc = indexSearch.doc(sdoc.doc);
				
				//设置高亮
				HashMap map = new HashMap();
				for (int j = 0; j < tmpList.size(); j++) {
					SysTempletfield field = tmpList.get(j);
					if (field.getIssearch() == 1) {
						String str = doc.get(field.getEnglishname().toLowerCase());
						highlighter.setTextFragmenter(new SimpleFragmenter(str.length()));
						TokenStream tk = analyzer.tokenStream(field.getEnglishname().toLowerCase(),new StringReader(str));
						String htext = highlighter.getBestFragment(tk, str);
						if (null != htext && !"".equals(htext)) {
							map.put(field.getEnglishname().toLowerCase(),htext);
						}
						else {
							map.put(field.getEnglishname().toLowerCase(),doc.get(field.getEnglishname().toLowerCase()));
						}
					}
					else {
						map.put(field.getEnglishname().toLowerCase(),doc.get(field.getEnglishname().toLowerCase()));
					}
//					map.put(fieldsList.get(j).toString(),
//							doc.get(fieldsList.get(j)));
				}
				resultList.add(map);
			}
			indexSearch.close();
			
			resultMap.put("DATA", resultList);
			resultMap.put("ROWCOUNT", rowCount);
			resultMap.put("PAGES", pages);
			return resultMap;
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

	/*
	 * (non-Javadoc)
	 * @see com.yapu.archive.service.itf.ISearchService#search(java.lang.String, java.util.List, java.lang.String, java.lang.String, int, int)
	 */
	public HashMap search(String tableName, List<SysTempletfield> tmpList, String searchTxt, String treeid,int currentPage,int pageSize) {

		if (currentPage <= 0) {
			currentPage = 1;
        }
        if (pageSize <= 0) {
            pageSize = 20;
        }
      //生成返回map
		HashMap resultMap = new HashMap();
		// 声明返回用的list
		List resultList = new ArrayList();
		
		int start = (currentPage - 1) * pageSize;

		String path = indexDir + "/" + tableName;
		IndexSearcher indexSearch = null;

		String tableIndexDir = ServletActionContext.getServletContext()
				.getRealPath(path) + File.separator;

		try {
			if (!(new File(tableIndexDir).isDirectory())) {
				resultMap.put("DATA", resultList);
				resultMap.put("ROWCOUNT", 0);
				resultMap.put("PAGES", 0);
				return resultMap;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		List<String> searchFieldsList = new ArrayList<String>();
		List<String> fieldsList = new ArrayList<String>();
		
		for (int i=0;i<tmpList.size();i++) {
			fieldsList.add(tmpList.get(i).getEnglishname().toLowerCase());
			if (tmpList.get(i).getIssearch() == 1) {
				searchFieldsList.add(tmpList.get(i).getEnglishname().toLowerCase());
			}
		}
		//生成查询字段
		String[] fields = new String[searchFieldsList.size()];
		searchFieldsList.toArray(fields);

		try {
			Directory dir = new SimpleFSDirectory(new File(tableIndexDir));

			IndexReader reader = IndexReader.open(dir);
			// 创建 IndexSearcher对象
			indexSearch = new IndexSearcher(reader);

			// 创建一个分词器,和创建索引时用的分词器要一致
			// Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
			Analyzer analyzer = new IKAnalyzer();
			// 得到一个组合检索对象
			BooleanQuery m_BooleanQuery = new BooleanQuery();

			QueryParser parser = new QueryParser(Version.LUCENE_36, "treeid",
					analyzer);
			Query query = new TermQuery(new Term("treeid", treeid));
			m_BooleanQuery.add(query, BooleanClause.Occur.MUST);

			if (null != searchTxt && !"".equals(searchTxt)) {
				QueryParser qp1 = new MultiFieldQueryParser(Version.LUCENE_36,
						fields, analyzer);// 检索content列
				qp1.setDefaultOperator(QueryParser.AND_OPERATOR);
				Query query1 = qp1.parse(searchTxt);

				m_BooleanQuery.add(query1, BooleanClause.Occur.MUST);
			}
			
			
			
			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
					"<span style='color:red'>", "</span>");
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter,
					new QueryScorer(m_BooleanQuery));

			int hm = start + pageSize;
            TopScoreDocCollector res = TopScoreDocCollector.create(hm, false);
			indexSearch.search(m_BooleanQuery, res);
			
			int rowCount = res.getTotalHits();
			int pages = (rowCount - 1) / pageSize + 1; //计算总页数
			TopDocs hits = res.topDocs(start, pageSize);
			
			// 搜索结果 TopDocs里面有scoreDocs[]数组，里面保存着索引值
//			TopDocs hits = indexSearch.search(m_BooleanQuery, 30);
			// hits.totalHits表示一共搜到多少个
			System.out.println("找到了" + hits.totalHits + "个");

			// 循环hits.scoreDocs数据，并使用indexSearch.doc方法把Document还原，再拿出对应的字段的值
			for (int i = 0; i < hits.scoreDocs.length; i++) {
				ScoreDoc sdoc = hits.scoreDocs[i];
				Document doc = indexSearch.doc(sdoc.doc);
				
				//设置高亮
				HashMap map = new HashMap();
				for (int j = 0; j < tmpList.size(); j++) {
					SysTempletfield field = tmpList.get(j);
					if (field.getIssearch() == 1) {
						String str = doc.get(field.getEnglishname().toLowerCase());
						highlighter.setTextFragmenter(new SimpleFragmenter(str.length()));
						TokenStream tk = analyzer.tokenStream(field.getEnglishname().toLowerCase(),new StringReader(str));
						String htext = highlighter.getBestFragment(tk, str);
						if (null != htext && !"".equals(htext)) {
							map.put(field.getEnglishname().toLowerCase(),htext);
						}
						else {
							map.put(field.getEnglishname().toLowerCase(),doc.get(field.getEnglishname().toLowerCase()));
						}
					}
					else {
						map.put(field.getEnglishname().toLowerCase(),doc.get(field.getEnglishname().toLowerCase()));
					}
				}
				resultList.add(map);
			}
			indexSearch.close();
			resultMap.put("DATA", resultList);
			resultMap.put("ROWCOUNT", rowCount);
			resultMap.put("PAGES", pages);
			return resultMap;
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

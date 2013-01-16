package com.yapu.system.util.lucene;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.LogByteSizeMergePolicy;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.struts2.ServletActionContext;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.yapu.archive.entity.SysDoc;
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.system.util.FileOperate;

/**
 * lucene 数据库索引相关操作
 * @author wangf
 *
 */

public class Indexer {
	
	private String indexDir = "/LUCENE";
	
	private void delLucentIndex(String folderPath) {
		FileOperate fo = new FileOperate();
		fo.delFolder(folderPath);
	}
	
	/**
	 * 创建索引
	 * @param tablename		数据库表名
	 * @param fieldList		字段list
	 * @param dataList		数据list
	 * @param openMode		创建模式   CREATE：全部重新创建。 APPEND：追加。CREATE_OR_APPEND：
	 */
	public void CreateIndex(String tablename,List fieldList,List dataList,String openMode) {
		
		IndexWriter indexWriter = null;
		try {
			// 创建表对应的独立索引存放目录
			String temp = indexDir + "/" + tablename;
			String tableIndexDir = ServletActionContext.getServletContext().getRealPath(temp)+ File.separator;
			System.out.println(tableIndexDir);
			// 创建Directory对象
			Directory dir = new SimpleFSDirectory(new File(tableIndexDir));
//			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
			Analyzer analyzer = new IKAnalyzer();
			// 创建的是哪个版本的IndexWriterConfig
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
                    Version.LUCENE_36, analyzer);
            LogMergePolicy mergePolicy = new LogByteSizeMergePolicy();
            //设置segment添加文档(Document)时的合并频率
            //值较小,建立索引的速度就较慢
            //值较大,建立索引的速度就较快,>10适合批量建立索引
            mergePolicy.setMergeFactor(50);
            //设置segment最大合并文档(Document)数
            //值较小有利于追加索引的速度
            //值较大,适合批量建立索引和更快的搜索
            mergePolicy.setMaxMergeDocs(5000);
            //启用复合式索引文件格式,合并多个segment
            mergePolicy.setUseCompoundFile(true);
            indexWriterConfig.setMergePolicy(mergePolicy);
            if ("CREATE".equals(openMode)) {
            	indexWriterConfig.setOpenMode(OpenMode.CREATE);
            }
            else if ("APPEND".equals(openMode)) {
            	indexWriterConfig.setOpenMode(OpenMode.APPEND);
            }
            else if ("CREATE_OR_APPEND".equals(openMode)) {
            	indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
            }
            else {
            	indexWriterConfig.setOpenMode(OpenMode.CREATE);
            }
            
            indexWriter = new IndexWriter(dir, indexWriterConfig);
			
            //写索引
            createDocument(indexWriter, tablename, fieldList, dataList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				indexWriter.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 写到索引文件里
	 * @param indexWriter
	 * @param tablename
	 * @param fieldList
	 * @param dataList
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 */
	@SuppressWarnings("unused")
	private void createDocument(IndexWriter indexWriter,String tablename,
			List<SysTempletfield> fieldList,List dataList) throws CorruptIndexException, IOException {
		Document doc = null;
		for (int i=0;i<dataList.size();i++) {
			doc = new Document();
			HashMap tempMap = (HashMap) dataList.get(i);
			for (SysTempletfield sysTempletfield : fieldList) {
				if (sysTempletfield.getIssearch() == 1) {
					String tmp = "";
					if (null != tempMap.get(sysTempletfield.getEnglishname())) {
						tmp = tempMap.get(sysTempletfield.getEnglishname()).toString();
					}
					if (null != tmp ) {
						doc.add(new Field(sysTempletfield.getEnglishname().toLowerCase(), 
								tmp,Field.Store.YES, Field.Index.ANALYZED));
					}
				}
				else {
					String tmp = "";
					if (null != tempMap.get(sysTempletfield.getEnglishname())) {
						tmp = tempMap.get(sysTempletfield.getEnglishname()).toString();
					}
					if (null != tmp ) {
						String fieldName = sysTempletfield.getEnglishname().toLowerCase();
						if ("treeid".equals(fieldName) || "id".equals(fieldName)) {
							doc.add(new Field(sysTempletfield.getEnglishname().toLowerCase(), 
									tmp,Field.Store.YES, Field.Index.NOT_ANALYZED));
						}
						else {
							doc.add(new Field(sysTempletfield.getEnglishname().toLowerCase(), 
									tmp,Field.Store.YES, Field.Index.NO));
						}
					}
				}
			}
			// 写入IndexWriter
			if (null != doc) {
				indexWriter.addDocument(doc);
			}
		}
		indexWriter.close();
	}
	
	/**
	 * 创建电子全文索引
	 * @param docServerid		电子全文服务器id
	 * @param openMode			创建方式
	 * @param docList			电子全文list
	 * @param contentMap		电子全文内容map
	 * @return
	 */
	public String CreateIndex(String docServerid,List<SysDoc> docList,HashMap<String,String> contentMap,String openMode) {
		
		if (null == docServerid || "".equals(docServerid)) {
			return "error";
		}
		IndexWriter indexWriter = null;
		
		try {
			// 创建表对应的独立索引存放目录
			String temp = indexDir + "/" + docServerid;
			String fileIndexDir = ServletActionContext.getServletContext().getRealPath(temp)+ File.separator;
			System.out.println(fileIndexDir);
			// 创建Directory对象
			Directory dir = new SimpleFSDirectory(new File(fileIndexDir));
//						Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
			Analyzer analyzer = new IKAnalyzer();
			// 创建的是哪个版本的IndexWriterConfig
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
                    Version.LUCENE_36, analyzer);
            LogMergePolicy mergePolicy = new LogByteSizeMergePolicy();
            //设置segment添加文档(Document)时的合并频率
            //值较小,建立索引的速度就较慢
            //值较大,建立索引的速度就较快,>10适合批量建立索引
            mergePolicy.setMergeFactor(50);
            //设置segment最大合并文档(Document)数
            //值较小有利于追加索引的速度
            //值较大,适合批量建立索引和更快的搜索
            mergePolicy.setMaxMergeDocs(5000);
            //启用复合式索引文件格式,合并多个segment
            mergePolicy.setUseCompoundFile(true);
            indexWriterConfig.setMergePolicy(mergePolicy);
            if ("CREATE".equals(openMode)) {
            	indexWriterConfig.setOpenMode(OpenMode.CREATE);
            }
            else if ("APPEND".equals(openMode)) {
            	indexWriterConfig.setOpenMode(OpenMode.APPEND);
            }
            else if ("CREATE_OR_APPEND".equals(openMode)) {
            	indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
            }
            else {
            	indexWriterConfig.setOpenMode(OpenMode.CREATE);
            }
            
            indexWriter = new IndexWriter(dir, indexWriterConfig);
			
            //写索引
            createDocument(indexWriter, docList,contentMap);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}finally {
			try {
				indexWriter.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return "success";
	}
	
	private void createDocument(IndexWriter indexWriter,List<SysDoc> docList,HashMap<String,String> contentMap) throws CorruptIndexException, IOException {
		Document document = null;
		for (int i=0;i<docList.size();i++) {
			document = new Document();
			SysDoc doc = docList.get(i);
			
			document.add(new Field("docid", doc.getDocid(),Field.Store.YES, Field.Index.NO));
			document.add(new Field("treeid", doc.getTreeid(),Field.Store.YES, Field.Index.NOT_ANALYZED));
			document.add(new Field("fileid", doc.getFileid(),Field.Store.YES, Field.Index.NO));
			document.add(new Field("tableid", doc.getTableid(),Field.Store.YES, Field.Index.NO));
			document.add(new Field("docoldname", doc.getDocoldname(),Field.Store.YES, Field.Index.ANALYZED));
			document.add(new Field("docext", doc.getDocext(),Field.Store.YES, Field.Index.NOT_ANALYZED));
			document.add(new Field("doclength", doc.getDoclength(),Field.Store.YES, Field.Index.NO));
			document.add(new Field("creater", doc.getCreater(),Field.Store.YES, Field.Index.ANALYZED));
			document.add(new Field("createtime", doc.getCreatetime(),Field.Store.YES, Field.Index.ANALYZED));
			document.add(new Field("content", contentMap.get(doc.getDocid()),Field.Store.YES, Field.Index.ANALYZED));
			// 写入IndexWriter
			if (null != document) {
				indexWriter.addDocument(document);
			}
		}
		indexWriter.close();
	}
	
	
	
	
	@SuppressWarnings("deprecation")
	/** (代码为测试用。暂时不开发删除和更新索引。本系统采用全部重新建立索引的方式)
	 * 根据数据库id ，删除索引
	 * @param tableName		数据库表名。索引物理文件存在的文件夹
	 * @param id			表id	
	 * @return
	 * @throws IOException
	 */
	public String deleteIndexer(String tableName,String id) throws IOException {
		
		String temp = indexDir + "/" + tableName;
		String tableIndexDir = ServletActionContext.getServletContext().getRealPath(temp)+ File.separator;
		// 创建Directory对象
		Directory dir = new SimpleFSDirectory(new File(tableIndexDir));
//		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		Analyzer analyzer = new IKAnalyzer();
		
		Term term = new Term("id",id);
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
                Version.LUCENE_36, analyzer);
		
		IndexWriter iw = new IndexWriter(dir, indexWriterConfig);
		iw.deleteDocuments(term);
		iw.close();

		return null;
	}
	
	public String updateIndexer(String tableName,List fieldList,List dataList) throws IOException {
		
		String temp = indexDir + "/" + tableName;
		String tableIndexDir = ServletActionContext.getServletContext().getRealPath(temp)+ File.separator;
		// 创建Directory对象
		Directory dir = new SimpleFSDirectory(new File(tableIndexDir));
//		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		Analyzer analyzer = new IKAnalyzer();
		
		HashMap map = (HashMap) dataList.get(0);
		
		Term term = new Term("id",map.get("ID").toString());
		
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
                Version.LUCENE_36, analyzer);
		
		IndexWriter iw = new IndexWriter(dir, indexWriterConfig);
		Document doc = new Document();
		doc.add(new Field("id",map.get("ID").toString(),Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("treeid",map.get("TREEID").toString(),Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("ajh",map.get("AJH").toString(),Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("tm",map.get("TM").toString(),Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("ztz",map.get("ZRZ").toString(),Field.Store.YES, Field.Index.ANALYZED));
		iw.updateDocument(term, doc);
		iw.close();
		return null;
	}

}

package com.yapu.system.util.lucene;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogByteSizeMergePolicy;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.struts2.ServletActionContext;

import com.yapu.archive.entity.SysTempletfield;

/**
 * lucene 索引相关操作
 * @author wangf
 *
 */

public class Indexer {
	
	private String indexDir = "/LUCENE";
	
	public void CreateIndex(String tablename,List fieldList,List dataList,String openMode) {
		
		IndexWriter indexWriter = null;
		try {
			// 创建表对应的独立索引存放目录
//			String tableIndexDir = "e:\\LUCENE\\" + tablename;
			String temp = indexDir + "/" + tablename;
			String tableIndexDir = ServletActionContext.getServletContext().getRealPath(temp)+ File.separator;
			System.out.println(tableIndexDir);
			// 创建Directory对象
			Directory dir = new SimpleFSDirectory(new File(tableIndexDir));
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
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
					doc.add(new Field(sysTempletfield.getEnglishname().toLowerCase(), 
							tempMap.get(sysTempletfield.getEnglishname()).toString(),Field.Store.YES, Field.Index.ANALYZED));
				}
				else {
					doc.add(new Field(sysTempletfield.getEnglishname().toLowerCase(), 
							tempMap.get(sysTempletfield.getEnglishname()).toString(),Field.Store.YES, Field.Index.NOT_ANALYZED));
				}
			}
			// 写入IndexWriter
			if (null != doc) {
				indexWriter.addDocument(doc);
			}
		}
		
	}

}

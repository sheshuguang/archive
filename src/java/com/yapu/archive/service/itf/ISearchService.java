package com.yapu.archive.service.itf;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;

import com.yapu.archive.entity.SysTempletfield;


/**
 * 搜索服务
 * @author wangf
 *
 */
public interface ISearchService {
	/**
	 * 根据查询关键字，得到树节点的符合查询条件的数量
	 * @param tableName
	 * @param fieldList
	 * @param searchTxt
	 * @param treeid
	 * @return
	 */
	HashMap searchNumber(String tableName,String[] fields,String searchTxt,String treeid);

	/**
	 * 根据查询关键字，在档案类型下检索内容
	 * @param tableName
	 * @param tmpList
	 * @param searchTxt
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws IOException
	 */
	HashMap search(String tableName, List<SysTempletfield> tmpList,String searchTxt,int currentPage,int pageSize) throws IOException;
	/**
	 * 根据关键字，在树节点下检索内容
	 * @param tableName		表名
	 * @param fields		查询字段
	 * @param fieldsList	全部字段
	 * @param searchTxt		查询关键字
	 * @param treeid		树节点id
	 * @param currentPage	当前页
	 * @param pageSize		每页数量
	 * @return
	 * @throws IOException
	 */
	HashMap search(String tableName, List<SysTempletfield> tmpList,String searchTxt,String treeid,int currentPage,int pageSize) throws IOException;
}

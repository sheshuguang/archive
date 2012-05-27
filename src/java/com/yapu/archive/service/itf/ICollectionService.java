package com.yapu.archive.service.itf;
/**
 * 收藏夹服务类
 * @author wangf
 * @date	2010-11-19
 */

import java.util.List;

import com.yapu.archive.entity.ArCollection;

public interface ICollectionService {

	//=============增、删、改、查==================
	/**
	 * 插入新收藏
	 * @param collection			收藏实体对象
	 * @return						正确处理返回true，失败返回false
	 */
	Boolean insertCollection(ArCollection collection);
	/**
	 * 更新收藏。以参数实体对象的id为条件更新。
	 * @param collection			收藏实体对象
	 * @return						更新的数量
	 */
	int updateCollection(ArCollection collection);
	/**
	 * 删除收藏。
	 * @param collectionID			收藏ID
	 * @return
	 */
	int deleteCollection(String collectionID);
	/**
	 * 删除收藏。
	 * @param collection			收藏实体对象
	 * @return
	 */
	int deleteCollection(ArCollection collection);
	/**
	 * 批量删除收藏。
	 * @param collectionIDList		收藏id的List集合。
	 * @return
	 */
	int deleteCollection(List<String> collectionIDList);
	/**
	 * 按主键值查找收藏。
	 * @param collectionID			收藏id
	 * @return
	 */
	ArCollection selectByPrimaryKey(String collectionID);
	/**
	 * 不分页查询
	 * @param collection			收藏实体对象
	 * @return
	 */
	List<ArCollection> selectByWhereNotPage(ArCollection collection);
	/**
	 * 分页查询
	 * @param collection			收藏实体对象
	 * @return
	 */
	List<ArCollection> selectByWherePage(ArCollection collection);
	/**
	 * 查询总行数
	 * @param collection			收藏实体对象。
	 * @return
	 */
	int rowCount(ArCollection collection);
	
	//===========================================
	
	//============相关的操作=====================
}

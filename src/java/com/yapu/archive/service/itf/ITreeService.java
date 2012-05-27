package com.yapu.archive.service.itf;

import java.util.List;

import com.yapu.archive.entity.SysTable;
import com.yapu.archive.entity.SysTemplet;
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.entity.SysTree;
import com.yapu.archive.entity.SysTreeExample;
import com.yapu.archive.entity.SysTreeTemplet;

/**
 * 资源树服务
 * @author wangf
 *
 */
public interface ITreeService {
	
	//=============增、删、改、查==================
	/**
	 * 插入新树节点
	 * @param tree			树节点实体对象
	 * @return				正确处理返回true，失败返回false
	 */
	Boolean insertTree(SysTree tree);
	/**
	 * 批量插入树节点
	 * @param treeList		树节点实体对象的list集合
	 * @return
	 */
	Boolean insertTree(List<SysTree> treeList);
	/**
	 * 更新树节点。以参数实体对象的id为条件更新。
	 * @param tree			树节点实体对象
	 * @return				更新的数量
	 */
	int updateTree(SysTree tree);
	/**
	 * 批量更新树节点。以组实体对象的ID值为条件更新。
	 * @param treeList		树节点实体对象的List集合。
	 * @return
	 */
	int updateTree(List<SysTree> treeList);
	/**
	 * 删除树节点。
	 * @param treeID			树节点ID
	 * @return
	 */
	int deleteTree(String treeID);
	/**
	 * 删除树节点。
	 * @param tree			树节点实体对象
	 * @return
	 */
	int deleteTree(SysTree tree);
	/**
	 * 按主键值查找树节点。
	 * @param treeID			树节点id
	 * @return
	 */
	SysTree selectByPrimaryKey(String treeID);
	/**
	 * 不分页查询
	 * @param tree			树节点实体对象
	 * @return
	 */
	List<SysTree> selectByWhereNotPage(SysTreeExample ste);
	
	
	//===========================================
	
	//============资源树相关的操作=====================
	
	/**
	 * 得到树节点对应的模板实体对象
	 * @param	treeid
	 */
	SysTemplet getTreeOfTemplet(String treeid);
	/**
	 * 设置树节点与模板的对应关系
	 * @param treeTemplet
	 * @return
	 */
	Boolean setTreeOfTemplet(SysTreeTemplet treeTemplet);
	/**
	 * 根据树节点id，得到对应的表名实体对象。表名可能为1-2个。
	 * @param treeid
	 * @return
	 */
	List<SysTable> getTreeOfTable(String treeid);
	
	/**
	 * 根据树节点id和表类型，得到对应的表字段属性List集合。
	 * 表类型含义为： 
	 * 模板类型为  A 的（案卷文件类型）： 表类型  01 ：案卷级表   02 ：文件级 表
	 * 模板类型为  F 的 （只文件级类型）：表类型  01 
	 * @param treeid
	 * @return
	 */
	List<SysTempletfield> getTreeOfTempletfield(String treeid,String tableType);

}

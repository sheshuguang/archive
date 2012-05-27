package com.yapu.system.common;

import java.io.Serializable;
import java.util.List;

public interface BaseInterface {

	/**
	 * 根据ID删除记录
	 * @param id
	 */
	public abstract void delete(Long id);

	/**
	 * 返回总行数,用于分页等情况
	 * @param value
	 * @return
	 */
	public abstract int getTotalRow(String value);

	
	public abstract List queryAll();

	/**
	 * 保存函数
	 * @param entity	实体类
	 * @return			新纪录的ID值
	 */
	public abstract Long save(Serializable entity);

	/**
	 * 删除函数
	 * @param entity	实体类
	 */
	public abstract void delete(Serializable entity);

	/**
	 * 更新函数
	 * @param entity	实体类
	 */
	public abstract void update(Serializable entity);

	/**
	 * 根据ID查找实体类
	 * @param id	id
	 * @return		实体类
	 */
	public Serializable findById(Long id);
	
}

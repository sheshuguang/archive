package com.yapu.system.common;

import com.yapu.system.util.Constants;

public class BaseEntity {
	/**
	 * 排序规则
	 */
	protected String sortRules;
	/**
	 * 操作符。传入的实体属性值，以什么操作符来查询。例如like、
	 */
//	protected String operator;
	
	/**
	 * 每页显示的行数
	 */
	protected Integer pageSize = 0;
	/**
	 * 当前页显示的起始行
	 */
	protected Integer startRow = Constants.start_row;
	
	protected String orgBaseID;
	
	
	public String getSortRules() {
		return sortRules;
	}
	/**
	 * 排序规则。此属性只在分页语句中用到，值例如："ddd desc" or "ddd asc"
	 * @param sortRules
	 */
	public void setSortRules(String sortRules) {
		this.sortRules = sortRules;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	/**
	 * 分页大小。此属性只在分页时才用到。
	 * @param pageSize
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getStartRow() {
		return startRow;
	}
	/**
	 * 分页开始行号。此属性只在分页时才用到。msslq数据库分页从0开始。
	 * @param startRow
	 */
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public String getOrgBaseID() {
		return orgBaseID;
	}
	/**
	 * 组id，账户实体对应的组id。例如创建账户时，组必须属于一个组。如果null，则账户属于默认组
	 * @param orgID
	 */
	public void setOrgBaseID(String orgBaseID) {
		this.orgBaseID = orgBaseID;
	}
}

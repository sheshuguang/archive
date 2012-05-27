package com.yapu.system.common;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.yapu.system.util.Logger;


public abstract class BaseDAO  extends SqlMapClientDaoSupport {
	
	protected final Logger log = new Logger(getClass());
	
}

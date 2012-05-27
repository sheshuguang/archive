package com.yapu.system.service.impl;

/**
 * 日志服务。包括登录日志、操作日志、错误日志
 * @author wangf
 * @date 	2010-11-19
 */

import java.util.HashMap;
import java.util.List;

import com.yapu.system.dao.itf.SysErrorlogDAO;
import com.yapu.system.dao.itf.SysLoginlogDAO;
import com.yapu.system.dao.itf.SysOperatelogDAO;
import com.yapu.system.entity.SysErrorlog;
import com.yapu.system.entity.SysErrorlogExample;
import com.yapu.system.entity.SysLoginlog;
import com.yapu.system.entity.SysLoginlogExample;
import com.yapu.system.entity.SysOperatelog;
import com.yapu.system.entity.SysOperatelogExample;
import com.yapu.system.service.itf.ILogService;

public class LogService implements ILogService {
	
	private SysLoginlogDAO loginlogDao;
	private SysErrorlogDAO errorlogDao;
	private SysOperatelogDAO operatelogDao;
	
	//===============登录日志部分=================
	/**
	 * 拆分实体对象，返回LoginlogExample类。
	 * @param loginlog
	 * @return
	 */
	@SuppressWarnings("unused")
	private SysLoginlogExample splitLoginlogEntity(SysLoginlog loginlog) {
		SysLoginlogExample loginlogExample = new SysLoginlogExample();
		SysLoginlogExample.Criteria criteria = loginlogExample.createCriteria();
		if (null != loginlog) {
			if (null != loginlog.getAccountcode()) {
				criteria.andAccountcodeLike("%" + loginlog.getAccountcode() + "%");
			}
			if (null != loginlog.getUsername()) {
				criteria.andUsernameLike("%" + loginlog.getUsername() + "%");
			}
			if (null != loginlog.getLoginip()) {
				criteria.andLoginipLike("%" + loginlog.getLoginip() + "%");
			}
			if (null != loginlog.getLogintime()) {
				criteria.andLogintimeLike("%" + loginlog.getLogintime() + "%");
			}
			if (null != loginlog.getOuttime()) {
				criteria.andOuttimeLike("%" + loginlog.getOuttime() + "%");
			}
			return loginlogExample;
		}
		return loginlogExample;
	}

	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#deleteLoginlog(java.lang.String)
	 */
	public int deleteLoginlog(String loginlogID) {
		if (null != loginlogID && !"".equals(loginlogID)) {
			try {
				return loginlogDao.deleteByPrimaryKey(loginlogID);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
			
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#deleteLoginlog(com.yapu.system.entity.SysLoginlog)
	 */
	public int deleteLoginlog(SysLoginlog loginlog) {
		if (null != loginlog) {
			return deleteLoginlog(loginlog.getLoginlogid());
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#deleteLoginlog(java.util.List)
	 */
	public int deleteLoginlog(List<String> loginlogIDList) {
		if (null != loginlogIDList && loginlogIDList.size() >0) {
			int num = 0;
			for (int i=0;i<loginlogIDList.size();i++ ) {
				deleteLoginlog(loginlogIDList.get(i));
				num += 1;
			}
			return num;
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#insertLoginlog(com.yapu.system.entity.SysLoginlog)
	 */
	public Boolean insertLoginlog(SysLoginlog loginlog) {
		if (null != loginlog) {
			try {
				loginlogDao.insertSelective(loginlog);
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#rowCount(com.yapu.system.entity.SysLoginlog)
	 */
	public int rowLoginlogCount(SysLoginlog loginlog) {
		if (null != loginlog ) {
			SysLoginlogExample loginlogExample = splitLoginlogEntity(loginlog);
			if (null != loginlogExample) {
				return loginlogDao.countByExample(loginlogExample);
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#selectLoginlogByPrimaryKey(java.lang.String)
	 */
	public SysLoginlog selectLoginlogByPrimaryKey(String loginlogID) {
		if (null != loginlogID && !"".equals(loginlogID)) {
			return loginlogDao.selectByPrimaryKey(loginlogID);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#selectLoginlogByPrimaryKey(java.util.List)
	 */
	public List<SysLoginlog> selectLoginlogByPrimaryKey(List<String> loginlogIDList) {
		if (null != loginlogIDList && loginlogIDList.size() >0 ) {
			SysLoginlogExample loginlogExample = new SysLoginlogExample();
			loginlogExample.createCriteria().andLoginlogidIn(loginlogIDList);
			return loginlogDao.selectByExample(loginlogExample);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#selectLoginlogByWhereNotPage(com.yapu.system.entity.SysLoginlog)
	 */
	public List<SysLoginlog> selectLoginlogByWhereNotPage(SysLoginlog loginlog) {
		if (null != loginlog) {
			SysLoginlogExample loginlogExample = splitLoginlogEntity(loginlog);
			if (null != loginlogExample) {
				return loginlogDao.selectByExample(loginlogExample);
			}
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#selectLoginlogByWherePage(com.yapu.system.entity.SysLoginlog)
	 */
	public List<SysLoginlog> selectLoginlogByWherePage(SysLoginlog loginlog) {
		//如果实体、分页大小、开始行号为空，不查询，直接返回null
		if (null == loginlog || null == loginlog.getPageSize() || null == loginlog.getStartRow()) {
			return null;
		}
		HashMap map = new HashMap();
		map.put("PAGESIZE", loginlog.getPageSize());
		map.put("STARTROW", loginlog.getStartRow());
		map.put("ORDERBY", loginlog.getSortRules());
		
		StringBuffer whereStr = new StringBuffer();
		if (null != loginlog.getAccountcode()) {
			whereStr.append("accountcode like '%" + loginlog.getAccountcode() + "%' and ");
		}
		if (null != loginlog.getUsername()) {
			whereStr.append(" USERNAME like '%" + loginlog.getUsername() + "%' and ");
		}
		if (null != loginlog.getLoginip()) {
			whereStr.append("LOGINIP like '%" + loginlog.getLoginip() + "%' and ");
		}
		if (null != loginlog.getLogintime()) {
			whereStr.append("LOGINTIME like '%" + loginlog.getLogintime() + "%' and ");
		}
		if (null != loginlog.getOuttime()) {
			whereStr.append("OUTTIME like '%" + loginlog.getOuttime() + "%' and ");
		}
		if (whereStr.length() > 0 ) {
			String where = whereStr.substring(0, whereStr.length() - 4);
			map.put("WHEREVALUE", where);
		}
		List loginlogList = loginlogDao.selectByMapPage(map);
		return loginlogList;
	}
	

	
	//===============操作日志部分=================
	
	/**
	 * 拆分实体对象，返回LoginlogExample类。
	 * @param loginlog
	 * @return
	 */
	@SuppressWarnings("unused")
	private SysOperatelogExample splitOperatelogEntity(SysOperatelog operatelog) {
		SysOperatelogExample operatelogExample = new SysOperatelogExample();
		SysOperatelogExample.Criteria criteria = operatelogExample.createCriteria();
		if (null != operatelog) {
			if (null != operatelog.getAccountcode()) {
				criteria.andAccountcodeLike("%" + operatelog.getAccountcode() + "%");
			}
			if (null != operatelog.getUsername()) {
				criteria.andUsernameLike("%" + operatelog.getUsername() + "%");
			}
			if (null != operatelog.getFunname()) {
				criteria.andFunnameEqualTo(operatelog.getFunname());
			}
			if (null != operatelog.getOperatetime()) {
				criteria.andOperatetimeLike("%" + operatelog.getOperatetime() + "%");
			}
			if (null != operatelog.getLogdoc()) {
				criteria.andLogdocLike("%" + operatelog.getLogdoc() + "%");
			}
			return operatelogExample;
		}
		return operatelogExample;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#deleteOperatelog(java.lang.String)
	 */
	public int deleteOperatelog(String operatelogID) {
		if (null != operatelogID && !"".equals(operatelogID)) {
			try {
				return operatelogDao.deleteByPrimaryKey(operatelogID);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
			
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#deleteOperatelog(com.yapu.system.entity.SysOperatelog)
	 */
	public int deleteOperatelog(SysOperatelog operatelog) {
		if (null != operatelog) {
			return deleteOperatelog(operatelog.getOperatelogid());
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#deleteOperatelog(java.util.List)
	 */
	public int deleteOperatelog(List<String> operatelogIDList) {
		if (null != operatelogIDList && operatelogIDList.size() >0) {
			int num = 0;
			for (int i=0;i<operatelogIDList.size();i++ ) {
				deleteOperatelog(operatelogIDList.get(i));
				num += 1;
			}
			return num;
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#insertOperatelog(com.yapu.system.entity.SysOperatelog)
	 */
	public Boolean insertOperatelog(SysOperatelog operatelog) {
		if (null != operatelog) {
			try {
				operatelogDao.insertSelective(operatelog);
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#rowCount(com.yapu.system.entity.SysOperatelog)
	 */
	public int rowOperatelogCount(SysOperatelog operatelog) {
		if (null != operatelog ) {
			SysOperatelogExample operatelogExample = splitOperatelogEntity(operatelog);
			if (null != operatelogExample) {
				return operatelogDao.countByExample(operatelogExample);
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#selectOperatelogByPrimaryKey(java.lang.String)
	 */
	public SysOperatelog selectOperatelogByPrimaryKey(String operatelogID) {
		if (null != operatelogID && !"".equals(operatelogID)) {
			return operatelogDao.selectByPrimaryKey(operatelogID);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#selectOperatelogByPrimaryKey(java.util.List)
	 */
	public List<SysOperatelog> selectOperatelogByPrimaryKey(List<String> operatelogIDList) {
		if (null != operatelogIDList && operatelogIDList.size() >0 ) {
			SysOperatelogExample operatelogExample = new SysOperatelogExample();
			operatelogExample.createCriteria().andOperatelogidIn(operatelogIDList);
			return operatelogDao.selectByExample(operatelogExample);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#selectOperatelogByWhereNotPage(com.yapu.system.entity.SysOperatelog)
	 */
	public List<SysOperatelog> selectOperatelogByWhereNotPage(SysOperatelog operatelog) {
		if (null != operatelog) {
			SysOperatelogExample operatelogExample = splitOperatelogEntity(operatelog);
			if (null != operatelogExample) {
				return operatelogDao.selectByExample(operatelogExample);
			}
		}
		return null;
	}

	public List<SysOperatelog> selectOperatelogByWherePage(SysOperatelog operatelog) {
		//如果实体、分页大小、开始行号为空，不查询，直接返回null
		if (null == operatelog || null == operatelog.getPageSize() || null == operatelog.getStartRow()) {
			return null;
		}
		HashMap map = new HashMap();
		map.put("PAGESIZE", operatelog.getPageSize());
		map.put("STARTROW", operatelog.getStartRow());
		map.put("ORDERBY", operatelog.getSortRules());
		
		StringBuffer whereStr = new StringBuffer();
		if (null != operatelog.getAccountcode()) {
			whereStr.append("ACCOUNTCODE like '%" + operatelog.getAccountcode() + "%' and ");
		}
		if (null != operatelog.getUsername()) {
			whereStr.append(" USERNAME like '%" + operatelog.getUsername() + "%' and ");
		}
		if (null != operatelog.getFunname()) {
			whereStr.append("FUNNAME = '" + operatelog.getFunname() + "' and ");
		}
		if (null != operatelog.getOperatetime()) {
			whereStr.append("OPERATETIME like '%" + operatelog.getOperatetime() + "%' and ");
		}
		if (null != operatelog.getLogdoc()) {
			whereStr.append("LOGDOC like '%" + operatelog.getLogdoc() + "%' and ");
		}
		if (whereStr.length() > 0 ) {
			String where = whereStr.substring(0, whereStr.length() - 4);
			map.put("WHEREVALUE", where);
		}
		List operatelogList = operatelogDao.selectByMapPage(map);
		return operatelogList;
	}
	
	
	//===============错误日志部分=================
	
	/**
	 * 拆分实体对象，返回LoginlogExample类。
	 * @param loginlog
	 * @return
	 */
	@SuppressWarnings("unused")
	private SysErrorlogExample splitErrorlogEntity(SysErrorlog errorlog) {
		SysErrorlogExample errorlogExample = new SysErrorlogExample();
		SysErrorlogExample.Criteria criteria = errorlogExample.createCriteria();
		if (null != errorlog) {
			if (null != errorlog.getFunname()) {
				criteria.andFunnameEqualTo(errorlog.getFunname());
			}
			if (null != errorlog.getErrortime()) {
				criteria.andErrortimeLike("%" + errorlog.getErrortime() + "%");
			}
			if (null != errorlog.getErrordoc()) {
				criteria.andErrordocLike("%" + errorlog.getErrordoc() + "%");
			}
			return errorlogExample;
		}
		return errorlogExample;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#deleteErrorlog(java.lang.String)
	 */
	public int deleteErrorlog(String errorlogID) {
		if (null != errorlogID && !"".equals(errorlogID)) {
			try {
				return errorlogDao.deleteByPrimaryKey(errorlogID);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
			
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#deleteErrorlog(com.yapu.system.entity.SysErrorlog)
	 */
	public int deleteErrorlog(SysErrorlog errorlog) {
		if (null != errorlog) {
			return deleteErrorlog(errorlog.getErrorlogid());
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#deleteErrorlog(java.util.List)
	 */
	public int deleteErrorlog(List<String> errorlogIDList) {
		if (null != errorlogIDList && errorlogIDList.size() >0) {
			int num = 0;
			for (int i=0;i<errorlogIDList.size();i++ ) {
				deleteErrorlog(errorlogIDList.get(i));
				num += 1;
			}
			return num;
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#insertErrorlog(com.yapu.system.entity.SysErrorlog)
	 */
	public Boolean insertErrorlog(SysErrorlog errorlog) {
		if (null != errorlog) {
			try {
				errorlogDao.insertSelective(errorlog);
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#rowCount(com.yapu.system.entity.SysErrorlog)
	 */
	public int rowCount(SysErrorlog errorlog) {
		if (null != errorlog ) {
			SysErrorlogExample errorlogExample = splitErrorlogEntity(errorlog);
			if (null != errorlogExample) {
				return errorlogDao.countByExample(errorlogExample);
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#selectErrorlogByPrimaryKey(java.lang.String)
	 */
	public SysErrorlog selectErrorlogByPrimaryKey(String errorlogID) {
		if (null != errorlogID && !"".equals(errorlogID)) {
			return errorlogDao.selectByPrimaryKey(errorlogID);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#selectErrorlogByPrimaryKey(java.util.List)
	 */
	public List<SysErrorlog> selectErrorlogByPrimaryKey(
			List<String> errorlogIDList) {
		if (null != errorlogIDList && errorlogIDList.size() >0 ) {
			SysErrorlogExample errorlogExample = new SysErrorlogExample();
			errorlogExample.createCriteria().andErrorlogidIn(errorlogIDList);
			return errorlogDao.selectByExample(errorlogExample);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ILogService#selectErrorlogByWhereNotPage(com.yapu.system.entity.SysErrorlog)
	 */
	public List<SysErrorlog> selectErrorlogByWhereNotPage(SysErrorlog errorlog) {
		if (null != errorlog) {
			SysErrorlogExample errorlogExample = splitErrorlogEntity(errorlog);
			if (null != errorlogExample) {
				return errorlogDao.selectByExample(errorlogExample);
			}
		}
		return null;
	}

	public List<SysErrorlog> selectErrorlogByWherePage(SysErrorlog errorlog) {
		//如果实体、分页大小、开始行号为空，不查询，直接返回null
		if (null == errorlog || null == errorlog.getPageSize() || null == errorlog.getStartRow()) {
			return null;
		}
		HashMap map = new HashMap();
		map.put("PAGESIZE", errorlog.getPageSize());
		map.put("STARTROW", errorlog.getStartRow());
		map.put("ORDERBY", errorlog.getSortRules());
		
		StringBuffer whereStr = new StringBuffer();
		if (null != errorlog.getFunname()) {
			whereStr.append("FUNNAME = '" + errorlog.getFunname() + "' and ");
		}
		if (null != errorlog.getErrortime()) {
			whereStr.append("ERRORTIME like '%" + errorlog.getErrortime() + "%' and ");
		}
		if (null != errorlog.getErrordoc()) {
			whereStr.append("ERRORDOC like '%" + errorlog.getErrordoc() + "%' and ");
		}
		if (whereStr.length() > 0 ) {
			String where = whereStr.substring(0, whereStr.length() - 4);
			map.put("WHEREVALUE", where);
		}
		List errorlogList = errorlogDao.selectByMapPage(map);
		return errorlogList;
	}
	
	
	public void setLoginlogDao(SysLoginlogDAO loginlogDao) {
		this.loginlogDao = loginlogDao;
	}
	public void setErrorlogDao(SysErrorlogDAO errorlogDao) {
		this.errorlogDao = errorlogDao;
	}
	public void setOperatelogDao(SysOperatelogDAO operatelogDao) {
		this.operatelogDao = operatelogDao;
	}

	
}

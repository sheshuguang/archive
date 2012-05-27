package com.yapu.archive.service.impl;
/**
 * 发布服务
 * @author wangf
 * @date	2010-11-19
 */

import java.util.HashMap;
import java.util.List;

import com.yapu.archive.dao.itf.ArReleaseDAO;
import com.yapu.archive.entity.ArRelease;
import com.yapu.archive.entity.ArReleaseExample;
import com.yapu.archive.service.itf.IReleaseService;

public class ReleaseService implements IReleaseService {
	
	private ArReleaseDAO releaseDao;
	
	/**
	 * 拆分实体对象，返回ReleaseExample类。
	 * @param account
	 * @return
	 */
	@SuppressWarnings("unused")
	private ArReleaseExample splitReleaseEntity(ArRelease release) {
		ArReleaseExample releaseExample = new ArReleaseExample();
		ArReleaseExample.Criteria criteria = releaseExample.createCriteria();
		if (null != release) {
			if (null != release.getTableid()) {
				criteria.andTableidEqualTo(release.getTableid());
			}
			if (null != release.getFileid()) {
				criteria.andFileidEqualTo(release.getFileid());
			}
			if (null != release.getReleaseman()) {
				criteria.andReleasemanEqualTo(release.getReleaseman());
			}
			return releaseExample;
		}
		return releaseExample;
	}

	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IReleaseService#deleteRelease(java.lang.String)
	 */
	public int deleteRelease(String releaseID) {
		if (null != releaseID && !"".equals(releaseID)) {
			try {
				return releaseDao.deleteByPrimaryKey(releaseID);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IReleaseService#deleteRelease(com.yapu.system.entity.ArRelease)
	 */
	public int deleteRelease(ArRelease release) {
		if (null != release) {
			return deleteRelease(release.getReleaseid());
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IReleaseService#deleteRelease(java.util.List)
	 */
	public int deleteRelease(List<String> releaseIDList) {
		if (null != releaseIDList && releaseIDList.size() > 0) {
			int num = 0;
			for (int i=0;i<releaseIDList.size();i++) {
				deleteRelease(releaseIDList.get(i));
				num += 1;
			}
			return num;
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IReleaseService#insertRelease(com.yapu.system.entity.ArRelease)
	 */
	public Boolean insertRelease(ArRelease release) {
		if (null != release ) {
			try {
				releaseDao.insertSelective(release);
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
	 * @see com.yapu.system.service.itf.IReleaseService#insertRelease(java.util.List)
	 */
	public Boolean insertRelease(List<ArRelease> releaseList) {
		if (null != releaseList && releaseList.size() >0 ) {
			for (int i=0;i<releaseList.size();i++) {
				insertRelease(releaseList.get(i));
			}
			return true;
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IReleaseService#rowCount(com.yapu.system.entity.ArRelease)
	 */
	public int rowCount(ArRelease release) {
		if (null != release) {
			ArReleaseExample releaseExample = splitReleaseEntity(release);
			if (null != releaseExample) {
				return releaseDao.countByExample(releaseExample);
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IReleaseService#selectByPrimaryKey(java.lang.String)
	 */
	public ArRelease selectByPrimaryKey(String releaseID) {
		if (null != releaseID && !"".equals(releaseID)) {
			return releaseDao.selectByPrimaryKey(releaseID);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IReleaseService#selectByWhereNotPage(com.yapu.system.entity.ArRelease)
	 */
	public List<ArRelease> selectByWhereNotPage(ArRelease release) {
		if (null != release) {
			ArReleaseExample releaseExample = splitReleaseEntity(release);
			if (null != releaseExample) {
				return releaseDao.selectByExample(releaseExample);
			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IReleaseService#selectByWherePage(com.yapu.system.entity.ArRelease)
	 */
	public List<ArRelease> selectByWherePage(ArRelease release) {
		//如果实体、分页大小、开始行号为空，不查询，直接返回null
		if (null == release || null == release.getPageSize() || null == release.getStartRow()) {
			return null;
		}
		HashMap map = new HashMap();
		map.put("PAGESIZE", release.getPageSize());
		map.put("STARTROW", release.getStartRow());
		map.put("ORDERBY", release.getSortRules());
		
		StringBuffer whereStr = new StringBuffer();
		if (null != release.getTableid()) {
			whereStr.append("TABLEID = '" + release.getTableid() + "' and ");
		}
		if (null != release.getFileid()) {
			whereStr.append(" FILEID = '" + release.getFileid() + "' and ");
		}
		if (null != release.getReleaseman()) {
			whereStr.append("RELEASEMAN = '" + release.getReleaseman() + "' and ");
		}
		if (whereStr.length() > 0 ) {
			String where = whereStr.substring(0, whereStr.length() - 4);
			map.put("WHEREVALUE", where);
		}
		List accountList = releaseDao.selectByMapPage(map);
		return accountList;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IReleaseService#updateRelease(com.yapu.system.entity.ArRelease)
	 */
	public int updateRelease(ArRelease release) {
		if (null != release) {
			try {
				return releaseDao.updateByPrimaryKeySelective(release);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}

	public void setReleaseDao(ArReleaseDAO releaseDao) {
		this.releaseDao = releaseDao;
	}

}

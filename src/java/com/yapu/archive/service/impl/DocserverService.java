package com.yapu.archive.service.impl;

import java.util.List;

import com.yapu.archive.dao.itf.SysDocserverDAO;
import com.yapu.archive.entity.SysDocserver;
import com.yapu.archive.entity.SysDocserverExample;
import com.yapu.archive.service.itf.IDocserverService;

public class DocserverService implements IDocserverService {
	
	private SysDocserverDAO docserverDao;
	

	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IDocserverService#deleteDocserver(java.lang.String)
	 */
	public int deleteDocserver(String docserverID) {
		if (null != docserverID && !"".equals(docserverID)) {
			try {
				return docserverDao.deleteByPrimaryKey(docserverID);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IDocserverService#deleteDocserver(com.yapu.system.entity.SysDocserver)
	 */
	public int deleteDocserver(SysDocserver docserver) {
		if (null != docserver) {
			return deleteDocserver(docserver.getDocserverid());
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IDocserverService#insertDocserver(com.yapu.system.entity.SysDocserver)
	 */
	public Boolean insertDocserver(SysDocserver docserver) {
		if (null != docserver ) {
			try {
				docserverDao.insertSelective(docserver);
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
	 * @see com.yapu.system.service.itf.IDocserverService#selectByPrimaryKey(java.lang.String)
	 */
	public SysDocserver selectByPrimaryKey(String docserverID) {
		if (null != docserverID && !"".equals(docserverID)) {
			return docserverDao.selectByPrimaryKey(docserverID);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.archive.service.itf.IDocserverService#selectByWhereNotPage(com.yapu.archive.entity.SysDocserverExample)
	 */
	public List<SysDocserver> selectByWhereNotPage(SysDocserverExample example) {
		return docserverDao.selectByExample(example);
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IDocserverService#updateDocserver(com.yapu.system.entity.SysDocserver)
	 */
	public int updateDocserver(SysDocserver docserver) {
		if (null != docserver) {
			try {
				return docserverDao.updateByPrimaryKeySelective(docserver);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.archive.service.itf.IDocserverService#updateDocserver(com.yapu.archive.entity.SysDocserver, com.yapu.archive.entity.SysDocserverExample)
	 */
	public int updateDocserver(SysDocserver record, SysDocserverExample example) {
		try {
			return docserverDao.updateByExampleSelective(record, example);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	public void setDocserverDao(SysDocserverDAO docserverDao) {
		this.docserverDao = docserverDao;
	}
	
	
	

}

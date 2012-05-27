package com.yapu.system.service.impl;

import java.util.List;

import com.yapu.system.dao.itf.SysConfigDAO;
import com.yapu.system.entity.SysConfig;
import com.yapu.system.entity.SysConfigExample;
import com.yapu.system.service.itf.IConfigService;

public class ConfigService implements IConfigService {
	
	private SysConfigDAO configDao;
	
//	/**
//	 * 拆分组实体对象。为了查询
//	 * @param config
//	 * @return
//	 */
//	private SysConfigExample splitConfigEntity(SysConfig config) {
//		if (null != config) {
//			SysConfigExample configExample = new SysConfigExample();
//			SysConfigExample.Criteria criteria = configExample.createCriteria();
//			if (null != config.getConfigkey()) {
//				criteria.andConfigkeyLike("%" + config.getConfigkey() + "%");
//			}
//			if (null != config.getConfigvalue()) {
//				criteria.andConfigvalueLike("%" + config.getConfigvalue() + "%");
//			}
//			if (null != config.getConfigmemo()) {
//				criteria.andConfigmemoLike("%" + config.getConfigmemo() + "%");
//			}
//			return configExample;
//		}
//		return null;
//	}

	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IConfigService#deleteConfig(java.lang.String)
	 */
	public int deleteConfig(String configID) {
		if (null != configID && !"".equals(configID)) {
			try {
				return configDao.deleteByPrimaryKey(configID);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IConfigService#deleteConfig(com.yapu.system.entity.SysConfig)
	 */
	public int deleteConfig(SysConfig config) {
		if (null != config) {
			try {
				return deleteConfig(config.getConfigid());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IConfigService#insertConfig(com.yapu.system.entity.SysConfig)
	 */
	public Boolean insertConfig(SysConfig config) {
		if (null != config) {
			try {
				configDao.insertSelective(config);
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
	 * @see com.yapu.system.service.itf.IConfigService#selectByPrimaryKey(java.lang.String)
	 */
	public SysConfig selectByPrimaryKey(String configID) {
		if (null != configID && !"".equals(configID)) {
			return configDao.selectByPrimaryKey(configID);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IConfigService#selectByWhereNotPage(com.yapu.system.entity.SysConfigExample)
	 */
	public List<SysConfig> selectByWhereNotPage(SysConfigExample example) {
		if (null != example) {
//			SysConfigExample configExample = splitConfigEntity(config);
			return configDao.selectByExample(example);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IConfigService#updateConfig(com.yapu.system.entity.SysConfig)
	 */
	public int updateConfig(SysConfig config) {
		if (null != config) {
			try {
				return configDao.updateByPrimaryKeySelective(config);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IConfigService#updateConfig(java.util.List)
	 */
	public int updateConfig(List<SysConfig> configList) {
		if (null != configList) {
			int num = 0;
			for (int i=0;i<configList.size();i++){
				SysConfig config = configList.get(i);
				updateConfig(config);
				num += 1;
			}
			return num;
		}
		return 0;
	}
	
	
	
	
	public void setConfigDao(SysConfigDAO configDao) {
		this.configDao = configDao;
	}
}

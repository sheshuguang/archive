package com.yapu.system.service.impl;

import java.util.List;

import com.yapu.system.dao.itf.SysFunctionDAO;
import com.yapu.system.dao.itf.SysRoleFunctionDAO;
import com.yapu.system.entity.SysFunction;
import com.yapu.system.entity.SysFunctionExample;
import com.yapu.system.entity.SysRoleFunctionExample;
import com.yapu.system.service.itf.IFunctionService;

public class FunctionService implements IFunctionService {
	
	private SysFunctionDAO functionDao;
	private SysRoleFunctionDAO rolefunctionDao;
	
	/**
	 * 删除功能前，删除角色与功能的对应
	 * @param function
	 * @return
	 */
	private Boolean beforeDeleteFunction(SysFunction function) {
		if (null != function) {
			try {
				SysRoleFunctionExample roleFunctionExample = new SysRoleFunctionExample();
				roleFunctionExample.createCriteria().andFunctionidEqualTo(function.getFunctionid());
				rolefunctionDao.deleteByExample(roleFunctionExample);
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
	 * @see com.yapu.system.service.itf.IFunctionService#deleteFunction(com.yapu.system.entity.SysFunction)
	 */
	public int deleteFunction(SysFunction function) {
		if (null != function ) {
			try {
				if (beforeDeleteFunction(function)) {
					return functionDao.deleteByPrimaryKey(function.getFunctionid());
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IFunctionService#insertFunction(com.yapu.system.entity.SysFunction)
	 */
	public Boolean insertFunction(SysFunction function) {
		if (null != function ) {
			try {
				functionDao.insertSelective(function);
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
	 * @see com.yapu.system.service.itf.IFunctionService#selectByPrimaryKey(java.lang.String)
	 */
	public SysFunction selectByPrimaryKey(String functionID) {
		if (null != functionID ) {
			return functionDao.selectByPrimaryKey(functionID);
		}
		return null;
	}
//	
//	private SysFunctionExample splitFunctionEntity(SysFunction function) {
//		SysFunctionExample functionExample = new SysFunctionExample();
//		SysFunctionExample.Criteria criteria = functionExample.createCriteria();
//		if (null != function ) {
//			if (null != function.getFunchinesename()) {
//				criteria.andFunchinesenameLike("%" + function.getFunchinesename() + "%");
//			}
//			if (null != function.getFunenglishname()) {
//				criteria.andFunenglishnameLike("%" + function.getFunenglishname() + "%");
//			}
//			if (null != function.getFunparent()) {
//				criteria.andFunparentEqualTo(function.getFunparent());
//			}
//		}
//		return functionExample;
//	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IFunctionService#selectByWhereNotPage(com.yapu.system.entity.SysFunctionExample)
	 */
	public List<SysFunction> selectByWhereNotPage(SysFunctionExample example) {
		return functionDao.selectByExample(example);
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IFunctionService#updateFunction(com.yapu.system.entity.SysFunction)
	 */
	public int updateFunction(SysFunction function) {
		if (null != function ) {
			try {
				return functionDao.updateByPrimaryKeySelective(function);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}

	public void setFunctionDao(SysFunctionDAO functionDao) {
		this.functionDao = functionDao;
	}
	public void setRolefunctionDao(SysRoleFunctionDAO rolefunctionDao) {
		this.rolefunctionDao = rolefunctionDao;
	}

}

package com.yapu.archive.service.impl;

import java.util.HashMap;
import java.util.List;

import com.yapu.archive.dao.itf.SysCodeDAO;
import com.yapu.archive.dao.itf.SysTempletfieldDAO;
import com.yapu.archive.entity.SysCode;
import com.yapu.archive.entity.SysCodeExample;
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.service.itf.ICodeService;

public class CodeService implements ICodeService {
	
	private SysCodeDAO codeDao;
	private SysTempletfieldDAO templetfieldDao;
	
	
	public Boolean save(HashMap<String, List<SysCode>> map,String templetfieldid) {
//		try {
			//处理添加的代码
			List<SysCode> insertCodeList = map.get("inserted");
			if (insertCodeList.size() > 0) {
				//循环保存添加的代码
				for (SysCode SysCode : insertCodeList) {
					insertCode(SysCode);
				}
			}
			//处理更新的代码
			List<SysCode> updateCodeList = map.get("updated");
			if (updateCodeList.size() > 0) {
				//循环保存添加的代码
				for (SysCode SysCode : updateCodeList) {
					updateCode(SysCode);
				}
			}
			//处理删除代码
			List<SysCode> delCodeList = map.get("deleted");
			if (delCodeList.size() > 0) {
				//循环删除代码
				for (SysCode SysCode : delCodeList) {
					deleteCode(SysCode.getCodeid());
				}
			}
			
			//检查当前字段下是否有代码，如果没有，就将字段的iscode字段设置为0.
			//获得templetfieldid的code列表
			SysCodeExample example = new SysCodeExample();
			example.createCriteria().andTempletfieldidEqualTo(templetfieldid);
			example.setOrderByClause("codeorder");
			
			List<SysCode> codeList = selectByWhereNotPage(example);
			//TODO 这报错，原因是更新字段时，要更新字段哪里,问阿佘，为什么spring事务不起作用。
			if(codeList.size() > 0) {
				SysTempletfield templetfield = new SysTempletfield();
				templetfield.setTempletfieldid(templetfieldid);
				templetfield.setIscode(1);
				templetfieldDao.updateByPrimaryKeySelective(templetfield);
//				num = templetfieldService.updateTempletfield(templetfield);
				
			}
			else {
				SysTempletfield templetfield = new SysTempletfield();
				templetfield.setTempletfieldid(templetfieldid);
				templetfield.setIscode(0);
				templetfieldDao.updateByPrimaryKeySelective(templetfield);
//				num = templetfieldService.updateTempletfield(templetfield);
			}
			return true;
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			return false;
//		}
		
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICodeService#deleteCode(java.lang.String)
	 */
	public int deleteCode(String codeID) {
		if (null != codeID && !"".equals(codeID)) {
			try {
				return codeDao.deleteByPrimaryKey(codeID);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICodeService#deleteCode(com.yapu.system.entity.SysCode)
	 */
	public int deleteCode(SysCode code) {
		if (null != code) {
			return deleteCode(code.getCodeid());
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICodeService#insertCode(com.yapu.system.entity.SysCode)
	 */
	public Boolean insertCode(SysCode code) {
		if (null != code) {
			try {
				codeDao.insertSelective(code);
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
	 * @see com.yapu.system.service.itf.ICodeService#insertCode(java.util.List)
	 */
	public Boolean insertCode(List<SysCode> codeList) {
		if (null != codeList && codeList.size() >0) {
			for (int i= 0;i<codeList.size();i++) {
				insertCode(codeList.get(i));
			}
			return true;
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICodeService#selectByPrimaryKey(java.lang.String)
	 */
	public SysCode selectByPrimaryKey(String codeID) {
		if (null != codeID && !"".equals(codeID)) {
			return codeDao.selectByPrimaryKey(codeID);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.archive.service.itf.ICodeService#selectByWhereNotPage(com.yapu.archive.entity.SysCodeExample)
	 */
	@SuppressWarnings("unchecked")
	public List<SysCode> selectByWhereNotPage(SysCodeExample example) {
		if (null != example) {
			return codeDao.selectByExample(example);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICodeService#updateCode(com.yapu.system.entity.SysCode)
	 */
	public int updateCode(SysCode code) {
		if (null != code) {
			try {
				return codeDao.updateByPrimaryKeySelective(code);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICodeService#updateCode(java.util.List)
	 */
	public int updateCode(List<SysCode> codeList) {
		int num = 0;
		if (null != codeList && codeList.size() >0) {
			for (int i=0;i<codeList.size();i++) {
				updateCode(codeList.get(i));
				num += 1;
			}
			return num;
		}
		return 0;
	}

	public void setCodeDao(SysCodeDAO codeDao) {
		this.codeDao = codeDao;
	}
	public void setTempletfieldDao(SysTempletfieldDAO templetfieldDao) {
		this.templetfieldDao = templetfieldDao;
	}
}

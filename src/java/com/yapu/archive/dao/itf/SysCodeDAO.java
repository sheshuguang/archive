package com.yapu.archive.dao.itf;

import java.util.List;

import com.yapu.archive.entity.SysCode;
import com.yapu.archive.entity.SysCodeExample;

public interface SysCodeDAO {
	
    int countByExample(SysCodeExample example);

    int deleteByExample(SysCodeExample example);

    int deleteByPrimaryKey(String codeid);

    void insert(SysCode record);

    void insertSelective(SysCode record);

    List<SysCode> selectByExample(SysCodeExample example);

    SysCode selectByPrimaryKey(String codeid);

    int updateByExampleSelective(SysCode record, SysCodeExample example);

    int updateByExample(SysCode record, SysCodeExample example);

    int updateByPrimaryKeySelective(SysCode record);

    int updateByPrimaryKey(SysCode record);
}
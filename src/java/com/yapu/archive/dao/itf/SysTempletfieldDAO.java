package com.yapu.archive.dao.itf;

import java.util.List;

import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.entity.SysTempletfieldExample;

public interface SysTempletfieldDAO {
    
    int countByExample(SysTempletfieldExample example);
    
    int deleteByExample(SysTempletfieldExample example);
    
    int deleteByPrimaryKey(String templetfieldid);

    void insert(SysTempletfield record);

    void insertSelective(SysTempletfield record);

    List<SysTempletfield> selectByExample(SysTempletfieldExample example);

    SysTempletfield selectByPrimaryKey(String templetfieldid);

    int updateByExampleSelective(SysTempletfield record, SysTempletfieldExample example);

    int updateByExample(SysTempletfield record, SysTempletfieldExample example);

    int updateByPrimaryKeySelective(SysTempletfield record);

    int updateByPrimaryKey(SysTempletfield record);
}
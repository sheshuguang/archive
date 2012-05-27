package com.yapu.system.dao.itf;


import java.util.List;

import com.yapu.system.entity.SysOrg;
import com.yapu.system.entity.SysOrgExample;

public interface SysOrgDAO {
    
    int countByExample(SysOrgExample example);
    
    int deleteByExample(SysOrgExample example);
    
    int deleteByPrimaryKey(String orgid);
    
    void insert(SysOrg record);
    
    void insertSelective(SysOrg record);

    List selectByExample(SysOrgExample example);
    
    SysOrg selectByPrimaryKey(String orgid);
    
    int updateByExampleSelective(SysOrg record, SysOrgExample example);
    
    int updateByExample(SysOrg record, SysOrgExample example);
    
    int updateByPrimaryKeySelective(SysOrg record);
    
    int updateByPrimaryKey(SysOrg record);
}
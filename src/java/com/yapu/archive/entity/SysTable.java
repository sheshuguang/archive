package com.yapu.archive.entity;

public class SysTable {
    
    private String tableid;
    private String tablename;
    private String templetid;
    private String tablelabel;
    private String tabletype;
    public String getTableid() {
        return tableid;
    }
    public void setTableid(String tableid) {
        this.tableid = tableid;
    }
    public String getTablename() {
        return tablename;
    }
    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getTempletid() {
        return templetid;
    }

    public void setTempletid(String templetid) {
        this.templetid = templetid;
    }

    
    public String getTablelabel() {
		return tablelabel;
	}
	public void setTablelabel(String tablelabel) {
		this.tablelabel = tablelabel;
	}
	public String getTabletype() {
        return tabletype;
    }

    public void setTabletype(String tabletype) {
        this.tabletype = tabletype;
    }
}
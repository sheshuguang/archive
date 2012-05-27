package com.yapu.archive.entity;

public class SysDocserver {
    private String docserverid;
    private String serverip;
    private String serverpath;
    private String ftpuser;
    private String ftppassword;
    private String servername;
    private String servertype;
    private Integer serverstate;
    private Integer serverport;
    private String servermemo;

    public String getDocserverid() {
        return docserverid;
    }
    public void setDocserverid(String docserverid) {
        this.docserverid = docserverid;
    }
    public String getServerip() {
        return serverip;
    }
    public void setServerip(String serverip) {
        this.serverip = serverip;
    }
    public String getServerpath() {
        return serverpath;
    }
    public void setServerpath(String serverpath) {
        this.serverpath = serverpath;
    }
    public String getFtpuser() {
        return ftpuser;
    }
    public void setFtpuser(String ftpuser) {
        this.ftpuser = ftpuser;
    }
    public String getFtppassword() {
        return ftppassword;
    }
    public void setFtppassword(String ftppassword) {
        this.ftppassword = ftppassword;
    }
    public String getServername() {
        return servername;
    }
    public void setServername(String servername) {
        this.servername = servername;
    }
    public String getServertype() {
        return servertype;
    }
    public void setServertype(String servertype) {
        this.servertype = servertype;
    }
    public Integer getServerstate() {
        return serverstate;
    }
    public void setServerstate(Integer serverstate) {
        this.serverstate = serverstate;
    }
    public Integer getServerport() {
		return serverport;
	}
	public void setServerport(Integer serverport) {
		this.serverport = serverport;
	}
	public String getServermemo() {
        return servermemo;
    }
    public void setServermemo(String servermemo) {
        this.servermemo = servermemo;
    }
}
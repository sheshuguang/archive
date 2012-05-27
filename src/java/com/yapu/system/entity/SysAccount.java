package com.yapu.system.entity;

public class SysAccount {
    
    private String accountid;
    private String accountcode;
    private String password;
    private Integer accountstate;
    private String accountmemo;
    
    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getAccountcode() {
        return accountcode;
    }

    public void setAccountcode(String accountcode) {
        this.accountcode = accountcode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAccountstate() {
        return accountstate;
    }

    public void setAccountstate(Integer accountstate) {
        this.accountstate = accountstate;
    }

    public String getAccountmemo() {
        return accountmemo;
    }

    public void setAccountmemo(String accountmemo) {
        this.accountmemo = accountmemo;
    }
}
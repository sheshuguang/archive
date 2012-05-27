package com.yapu.system.entity;

public class SysFunction {
    
    private String functionid;
    private String funchinesename;
    private String funenglishname;
    private String funpath;
    private Integer funorder;
    private Integer funsystem;
    private String funparent;
    private String funicon;

    public String getFunctionid() {
        return functionid;
    }
    public void setFunctionid(String functionid) {
        this.functionid = functionid;
    }
    public String getFunchinesename() {
        return funchinesename;
    }
    public void setFunchinesename(String funchinesename) {
        this.funchinesename = funchinesename;
    }
    public String getFunenglishname() {
        return funenglishname;
    }
    public void setFunenglishname(String funenglishname) {
        this.funenglishname = funenglishname;
    }
    public String getFunpath() {
        return funpath;
    }
    public void setFunpath(String funpath) {
        this.funpath = funpath;
    }
    public Integer getFunorder() {
        return funorder;
    }
    public void setFunorder(Integer funorder) {
        this.funorder = funorder;
    }
    public Integer getFunsystem() {
        return funsystem;
    }
    public void setFunsystem(Integer funsystem) {
        this.funsystem = funsystem;
    }
    public String getFunparent() {
        return funparent;
    }
    public void setFunparent(String funparent) {
        this.funparent = funparent;
    }
	public String getFunicon() {
		return funicon;
	}
	public void setFunicon(String funicon) {
		this.funicon = funicon;
	}
    
}
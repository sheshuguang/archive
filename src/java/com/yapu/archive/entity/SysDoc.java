package com.yapu.archive.entity;

import com.yapu.system.util.CommonUtils;

public class SysDoc {
    private String docid;
    private String docserverid;
    private String docoldname;
    private String docnewname;
    private String doctype;
    private String docext;
    private String doclength;
    private String docpath;
    private String creater;
    private String createtime;
    private String fileid;
    private String tableid;
    private String treeid;
    private String  authority;      //权限 参考linux文件系统
    private Integer  height;        //高
    private Integer width;           //宽
    private String  hidden;          //是否隐藏
    private String locked;           //是否锁定
    private String mwrite;            //写权限
    private String mread;             //读权限
    private String mime;             //文件类型
    private Long mtime;           //修改时间
    private String parentid;        //父亲节点id

    public String getMwrite() {
        return mwrite;
    }

    public void setMwrite(String mwrite) {
        this.mwrite = mwrite;
    }

    public String getMread() {
        return mread;
    }

    public void setMread(String mread) {
        this.mread = mread;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }



    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public Long getMtime() {
        return mtime;
    }

    public void setMtime(Long mtime) {
        this.mtime = mtime;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDocserverid() {
        return docserverid;
    }

    public void setDocserverid(String docserverid) {
        this.docserverid = docserverid;
    }

    public String getDocoldname() {
        return docoldname;
    }

    public void setDocoldname(String docoldname) {
        this.docoldname = docoldname;
    }

    public String getDocnewname() {
        return docnewname;
    }

    public void setDocnewname(String docnewname) {
        this.docnewname = docnewname;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = CommonUtils.replaceNull2Space(doctype).toUpperCase();
    }
    
    public String getDocext() {
		return docext;
	}

	public void setDocext(String docext) {
		this.docext = docext;
	}

	public String getDoclength() {
        return doclength;
    }

    public void setDoclength(String doclength) {
        this.doclength = doclength;
    }

    public String getDocpath() {
        return docpath;
    }

    public void setDocpath(String docpath) {
        this.docpath = docpath;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getTableid() {
        return tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }

	public String getTreeid() {
		return treeid;
	}

	public void setTreeid(String treeid) {
		this.treeid = treeid;
	}
    
}
package com.yapu.archive.vo;

import com.yapu.archive.entity.SysDocserver;

/**
 * 1.保存上传文件服务器的各种信息。
 * 2 
 * User: wangf
 * Date: 12-1-16
 * Time: 上午12:27
 */
public class UploadVo {
    //服务器对象
    private SysDocserver docserver;

    public SysDocserver getDocserver() {
        return docserver;
    }

    public void setDocserver(SysDocserver docserver) {
        this.docserver = docserver;
    }
}

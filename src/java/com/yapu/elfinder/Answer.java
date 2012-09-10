package com.yapu.elfinder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ssg
 * Date: 12-9-7
 * Time: 上午12:09
 * To change this template use File | Settings | File Templates.
 */
public class Answer {
    private String api="2.0";//交换协议的版本号  只初始化时需要
    private DirFileInfor cwd; //当前目录信息
    private List<DirFileInfor> files ;// 当前目录包含的文件信息
    private Options options;//
    private String uplMaxSize;//上传文件最大限制 如：32M
    private String [] netDrivers;//可以被挂载的网络协议列表 如：["ftp"]
    private Debug debug;//客户端调试信息

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public DirFileInfor getCwd() {
        return cwd;
    }

    public void setCwd(DirFileInfor cwd) {
        this.cwd = cwd;
    }

    public List<DirFileInfor> getFiles() {
        return files;
    }

    public void setFiles(List<DirFileInfor> files) {
        this.files = files;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public String getUplMaxSize() {
        return uplMaxSize;
    }

    public void setUplMaxSize(String uplMaxSize) {
        this.uplMaxSize = uplMaxSize;
    }

    public String[] getNetDrivers() {
        return netDrivers;
    }

    public void setNetDrivers(String[] netDrivers) {
        this.netDrivers = netDrivers;
    }

    public Debug getDebug() {
        return debug;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }
}

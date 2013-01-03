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
    private String api;//交换协议的版本号  只初始化时需要
    private DirFileInfor cwd; //当前目录信息
    private List<DirFileInfor> files ;// 当前目录包含的文件信息
    private Options options;//
    private String uplMaxSize;//上传文件最大限制 如：32M
    private List<String> netDrivers;//可以被挂载的网络协议列表 如：["ftp"]
    private Debug debug;//客户端调试信息
    private List<DirFileInfor> tree; //directors
    private List<DirFileInfor> added; //添加的文件列表
    private List<String> removed;//删除掉的文件；

    private List<String> select;
    private Object error;
    private Object errorData;

    public List<String> getSelect() {
        return select;
    }

    public void setSelect(List<String> select) {
        this.select = select;
    }

    public Object getErrorData() {
        return errorData;
    }

    public void setErrorData(Object errorData) {
        this.errorData = errorData;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public List<String> getRemoved() {
        return removed;
    }

    public void setRemoved(List<String> removed) {
        this.removed = removed;
    }

    public List<DirFileInfor> getAdded() {
        return added;
    }

    public void setAdded(List<DirFileInfor> added) {
        this.added = added;
    }

    public List<DirFileInfor> getTree() {
        return tree;
    }
   public void setTree(List<DirFileInfor> tree) {
        this.tree = tree;
    }
   public List<String> getNetDrivers() {
        return netDrivers;
    }

    public void setNetDrivers(List<String> netDrivers) {
        this.netDrivers = netDrivers;
    }

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


    public Debug getDebug() {
        return debug;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }
}

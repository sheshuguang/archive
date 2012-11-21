package com.yapu.elfinder;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ssg
 * Date: 12-9-7
 * Time: 上午12:51
 * To change this template use File | Settings | File Templates.
 */
public class Archiver {
    private List<Map<Integer,String> > create; //":[],
    private List<Map<Integer,String> >extract;//:[]

    public List<Map<Integer, String>> getCreate() {
        return create;
    }

    public void setCreate(List<Map<Integer, String>> create) {
        this.create = create;
    }

    public List<Map<Integer, String>> getExtract() {
        return extract;
    }

    public void setExtract(List<Map<Integer, String>> extract) {
        this.extract = extract;
    }
}

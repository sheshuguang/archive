package com.yapu.elfinder;

import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.yapu.system.common.BaseAction;

import java.io.IOException;
import java.io.PrintWriter;


public class ConnectorAction extends BaseAction {
    private String answer;
    private String target; //打开目标目录的Hash值  初始化为null
    private  String init; // 1--初始化
    private String tree; //1--必须包含跟目录的子目录树结构信息
    private String cmd; // open
    /*open - open directory
    file - output file contents to the browser (download)
    tree - return child directories
    parents - return parent directories and its childs
    ls - list files in directory
    tmb - create thumbnails for selected files
    size - return size for selected files or total folder(s) size
    dim - return image dimensions
    mkdir - create directory
    mkfile - create text file
    rm - delete file
    rename - rename file
    duplicate - create copy of file
    paste - copy or move files
    upload - upload file
    get - return text file contents
    put - save text file
    archive - create archive
    extract - extract archive
    search - search for files
    info - return info for files. (used by client "places" ui)
    resize - modify image file (resize/crop/rotate)
    netmount - mount network volume during user session. Only ftp now supports.*/

    public String run() throws IOException {
        PrintWriter jsonOuter = getJsonOuter() ;
        Gson gson = new Gson();

        jsonOuter.write("");
        return  null;

    }
    private String encode(String path){
        //DigestUtils.
        if(null!=path&&path.endsWith("")){

        }
        Base64.encode(path.getBytes());
        return path;
    }
    private String decode(String path){

        return  path;

    }

}

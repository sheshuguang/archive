package com.yapu.elfinder;

import com.google.gson.Gson;
import com.yapu.system.common.BaseAction;
import com.yapu.system.util.Base64Utils;
import org.apache.commons.lang.xwork.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class ConnectorAction extends BaseAction {
    private String DIRECTORY_SEPARATOR="/";
    private String volumeid = "l1_";        //挂接点id
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
        DirFileInfor cwd = new DirFileInfor();
        cwd.setDirs(1);
        cwd.setMime("directory");
        cwd.setHash("l1_XA");
        cwd.setLocked(1);
        cwd.setName("files");
        cwd.setRead(1);
        cwd.setSize(0);
        cwd.setTs(1346942638);
        cwd.setVolumeid("l1_");
        cwd.setWrite(1);
        Archiver archivers = new Archiver();
        archivers.setCreate(new ArrayList());
        archivers.setExtract(new ArrayList());
        Options options = new Options();
        options.setPath("files");
        options.setCopyOverwrite(1);
        options.setUrl("\\/elFinder\\/php\\/..\\/files\\/");
        options.setSeparator("\\");
        options.setDisabled(new ArrayList());
        options.setTmbUrl("\\/elFinder\\/php\\/..\\/files\\/.tmb\\/");
        options.setArchivers(archivers);
        Answer answer = new Answer();
        answer.setCwd(cwd);
        answer.setOptions(options);

        answer.setUplMaxSize("30M");
        jsonOuter.write(gson.toJson(answer));
        return  null;

    }

    private String encode(String path) throws UnsupportedEncodingException {
        //DigestUtils.
        if (null == path) path = "";
        if (path.equals("")) path = DIRECTORY_SEPARATOR;
        path = Base64Utils.getBASE64(path);
        path = StringUtils.replace(path, "+", "-");
        path = StringUtils.replace(path, "/", "_");
        path = StringUtils.replace(path, "=", ".");
        path = StringUtils.removeEnd(path, ".");
        return this.volumeid + path;
    }
    private String  decode(String path) throws UnsupportedEncodingException {
        path=StringUtils.substring(path,this.volumeid.length());
        path = StringUtils.replace(path, "-", "+");
        path = StringUtils.replace(path, "_", "/");
        path = StringUtils.replace(path, ".", "=");
        path = Base64Utils.getFromBASE64(path);
        return  path;

    }
   public static void main(String [] args) throws UnsupportedEncodingException {
       ConnectorAction ca = new ConnectorAction();
       String  e =ca.encode("D:\\tmp\\shared_docs\\档案夹\\未命名文件.txt");
       System.out.println(ca.decode(e));
   }
}

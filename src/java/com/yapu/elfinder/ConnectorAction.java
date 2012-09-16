package com.yapu.elfinder;

import com.google.gson.Gson;
import com.yapu.archive.entity.SysDoc;
import com.yapu.archive.entity.SysDocExample;
import com.yapu.archive.service.itf.IDocService;
import com.yapu.system.common.BaseAction;
import com.yapu.system.util.Base64Utils;
import org.apache.commons.lang.xwork.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class ConnectorAction extends BaseAction {
    private IDocService docService;
    private String DIRECTORY_SEPARATOR="/";
    private String volumeid = "l1_";        //挂接点id
    private String target; //打开目标目录的Hash值  初始化为null
    private  String init; // 1--初始化
    private String tree; //1--必须包含跟目录的子目录树结构信息
    private String cmd; // open

    public IDocService getDocService() {
        return docService;
    }

    public void setDocService(IDocService docService) {
        this.docService = docService;
    }
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
        /*DirFileInfor cwd = new DirFileInfor();
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

        List files = new ArrayList<DirFileInfor>();
        DirFileInfor file = cwd;
        file.setHash("Zxxx");
        file.setName("test");
        files.add(file);

        List netDrivers =  new ArrayList<String>();
        netDrivers.add("ftp");

        Answer answer = new Answer();
        answer.setCwd(cwd);
        answer.setOptions(options);
        answer.setFiles(files);
        answer.setUplMaxSize("30M");
        answer.setNetDrivers(netDrivers);
        answer.setDebug(new Debug());
        jsonOuter.write(gson.toJson(answer));*/
        if(this.cmd.equals("open")&&this.init.equals("1")&&(null==target||target.equals(""))) jsonOuter.write(gson.toJson(init()));
        if(this.cmd.equals("open")&&null!=target&&!target.equals("")) jsonOuter.write(gson.toJson(open()));
        if(this.cmd.equals("tree")&&null!=target)  jsonOuter.write(gson.toJson(tree()));
        return  null;

    }
    private Answer init(){
        Answer answer = new Answer();
        SysDocExample where = new SysDocExample();
        where.createCriteria().andParentidIsNull();
        List<SysDoc> dosList = docService.selectByWhereNotPage(where);
        DirFileInfor cwd = new DirFileInfor();
        if(null!=dosList&&dosList.size()>0){             //default root
            SysDoc firstRoot= dosList.get(0);
            cwd.setDirs(Integer.valueOf(firstRoot.getDoctype()));
            cwd.setMime(firstRoot.getMime());
            cwd.setHash(firstRoot.getDocid());
            cwd.setLocked(Integer.valueOf(firstRoot.getLocked()));
            cwd.setName(firstRoot.getDocnewname());
            cwd.setRead(Integer.valueOf(firstRoot.getMread()));
            cwd.setSize(Integer.valueOf(firstRoot.getDoclength()));
            cwd.setTs(firstRoot.getMtime());
            cwd.setVolumeid(firstRoot.getDocserverid());
            cwd.setWrite(Integer.valueOf(firstRoot.getMwrite()));
        }
        answer.setCwd(cwd);

        List files = new ArrayList<DirFileInfor>();
        files.add(cwd);
        for(SysDoc doc:dosList){                      //all root
            DirFileInfor file = new DirFileInfor();
            file.setDirs(Integer.valueOf(doc.getDoctype()));
            file.setMime(doc.getMime());
            file.setHash(doc.getDocid());
            file.setLocked(Integer.valueOf(doc.getLocked()));
            file.setName(doc.getDocnewname());
            file.setRead(Integer.valueOf(doc.getMread()));
            file.setSize(Integer.valueOf(doc.getDoclength()));
            file.setTs(doc.getMtime());
            file.setVolumeid(doc.getDocserverid());
            file.setWrite(Integer.valueOf(doc.getMwrite()));
            files.add(file);
        }
        answer.setFiles(files);
        Archiver archivers = new Archiver();
        archivers.setCreate(new ArrayList());
        archivers.setExtract(new ArrayList());
        Options options = new Options();
        //options.setPath("files");
        //options.setCopyOverwrite(1);
        //options.setUrl("\\/elFinder\\/php\\/..\\/files\\/");
        options.setSeparator("\\");
        //options.setDisabled(new ArrayList());
        //options.setTmbUrl("\\/elFinder\\/php\\/..\\/files\\/.tmb\\/");
        options.setArchivers(archivers);
        answer.setOptions(options);
        List netDrivers =  new ArrayList<String>();
        netDrivers.add("ftp");
        answer.setNetDrivers(netDrivers);
        answer.setUplMaxSize("30M");
        answer.setDebug(new Debug());
        return answer;
    }
    private Answer tree(){
        Answer answer = new Answer();
        SysDoc firstRoot = docService.selectByPrimaryKey(target);
        List files = new ArrayList<DirFileInfor>();
        DirFileInfor cwd = new DirFileInfor();
        cwd.setDirs(Integer.valueOf(firstRoot.getDoctype()));
        cwd.setMime(firstRoot.getMime());
        cwd.setHash(firstRoot.getDocid());
        cwd.setLocked(Integer.valueOf(firstRoot.getLocked()));
        cwd.setName(firstRoot.getDocnewname());
        cwd.setRead(Integer.valueOf(firstRoot.getMread()));
        cwd.setSize(Integer.valueOf(firstRoot.getDoclength()));
        cwd.setTs(firstRoot.getMtime());
        cwd.setVolumeid(firstRoot.getDocserverid());
        cwd.setWrite(Integer.valueOf(firstRoot.getMwrite()));
        files.add(cwd);
        SysDocExample where = new SysDocExample();
        where.createCriteria().andParentidEqualTo(target).andDoctypeEqualTo("1");
        List<SysDoc> dosList = docService.selectByWhereNotPage(where);
        for(SysDoc doc:dosList){                      //all root
            DirFileInfor file = new DirFileInfor();
            file.setDirs(Integer.valueOf(doc.getDoctype()));
            file.setMime(doc.getMime());
            file.setHash(doc.getDocid());
            file.setLocked(Integer.valueOf(doc.getLocked()));
            file.setName(doc.getDocnewname());
            file.setRead(Integer.valueOf(doc.getMread()));
            file.setSize(Integer.valueOf(doc.getDoclength()));
            file.setTs(doc.getMtime());
            file.setVolumeid(doc.getDocserverid());
            file.setWrite(Integer.valueOf(doc.getMwrite()));
            file.setPhash(doc.getParentid());
            files.add(file);
        }
        answer.setTree(files);
        answer.setDebug(new Debug());
        return answer;
    }
    private Answer open(){
        Answer answer = new Answer();
        SysDoc firstRoot = docService.selectByPrimaryKey(target);
        List files = new ArrayList<DirFileInfor>();
        DirFileInfor cwd = new DirFileInfor();
        cwd.setDirs(Integer.valueOf(firstRoot.getDoctype()));
        cwd.setMime(firstRoot.getMime());
        cwd.setHash(firstRoot.getDocid());
        cwd.setLocked(Integer.valueOf(firstRoot.getLocked()));
        cwd.setName(firstRoot.getDocnewname());
        cwd.setRead(Integer.valueOf(firstRoot.getMread()));
        cwd.setSize(Integer.valueOf(firstRoot.getDoclength()));
        cwd.setTs(firstRoot.getMtime());
        cwd.setVolumeid(firstRoot.getDocserverid());
        cwd.setWrite(Integer.valueOf(firstRoot.getMwrite()));
        files.add(cwd);

        SysDocExample where = new SysDocExample();
        where.createCriteria().andParentidEqualTo(target);
        List<SysDoc> dosList = docService.selectByWhereNotPage(where);
        for(SysDoc doc:dosList){                      //all root
            DirFileInfor file = new DirFileInfor();
            file.setDirs(Integer.valueOf(doc.getDoctype()));
            file.setMime(doc.getMime());
            file.setHash(doc.getDocid());
            file.setLocked(Integer.valueOf(doc.getLocked()));
            file.setName(doc.getDocnewname());
            file.setRead(Integer.valueOf(doc.getMread()));
            file.setSize(Integer.valueOf(doc.getDoclength()));
            file.setTs(doc.getMtime());
            file.setVolumeid(doc.getDocserverid());
            file.setWrite(Integer.valueOf(doc.getMwrite()));
            file.setPhash(doc.getParentid());
            files.add(file);
        }
        answer.setTree(files);
        answer.setDebug(new Debug());
        return answer;
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
       System.out.println(ca.decode("l1_XA"));
   }



    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}

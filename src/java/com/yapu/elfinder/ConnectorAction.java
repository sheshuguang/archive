package com.yapu.elfinder;

import com.google.gson.Gson;
import com.yapu.archive.entity.SysDoc;
import com.yapu.archive.entity.SysDocExample;
import com.yapu.archive.entity.SysDocserver;
import com.yapu.archive.service.itf.IDocService;
import com.yapu.archive.service.itf.IDocserverService;
import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.util.*;
import org.apache.commons.lang.xwork.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ConnectorAction extends BaseAction {
    private IDocService docService;
    private IDocserverService docServerService;
    private String DIRECTORY_SEPARATOR="/";
    private String volumeid = "l1_";        //挂接点id
    private String target; //打开目标目录的Hash值  初始化为null
    private  String init; // 1--初始化
    private String tree; //1--必须包含跟目录的子目录树结构信息
    private String cmd; // open
    private String name;//创建文件夹名
    private List<String> targets;

    private List<File> upload;
    private List<String> uploadContentType;
    private List<String> uploadFileName;

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
        if(this.cmd.equals("open")) {jsonOuter.write(gson.toJson(open()));}
        if(this.cmd.equals("tree")) {jsonOuter.write(gson.toJson(tree()));}
        if(this.cmd.equals("mkdir")) {jsonOuter.write(gson.toJson(mkdir()));}
        if(this.cmd.equals("mkfile")){jsonOuter.write(gson.toJson(mkfile()));}
        if(this.cmd.equals("rename")){jsonOuter.write(gson.toJson(rename()));}
        if(this.cmd.equals("upload")){jsonOuter.write(gson.toJson(upload()));}
        return  null;

    }

    private Answer open(){
        DirFileInfor cwd =  CommonUtils.copyDoc2Dfi(docService.targetDoc(this.getTarget()), new DirFileInfor());       //当前目录
        this.setTarget(cwd.getHash());
        /*if (null != this.getInit()&&  this.getInit().equals("1")){   //为初始化设置目标目录为默认目录
            cwd = CommonUtils.copyDoc2Dfi(docService.selectCWDDoc(null), new DirFileInfor());  //当前目录
            this.setTarget(cwd.getHash());
        }
        if (null == this.getTarget()|| !docService.checkTarget(this.getTarget())){
             cwd = CommonUtils.copyDoc2Dfi(docService.selectCWDDoc(this.getTarget()), new DirFileInfor());  //当前目录
            this.setTarget(cwd.getHash());
        }*/
        //DirFileInfor cwd = CommonUtils.copyDoc2Dfi(cwdDoc, new DirFileInfor());
        List<DirFileInfor> files = new ArrayList<DirFileInfor>();       //文件列表
        files.add(cwd);
        List<SysDoc> rootDocList = docService.selectAllroot();           //所有一级节点
        if (null != this.getTree() && this.getTree().equals("1")) {                        //需要返回目录结构
            for (SysDoc root : rootDocList) {
                files.add(CommonUtils.copyDoc2Dfi(root, new DirFileInfor()));
                List<SysDoc> rootSubDirs = docService.selectChildrenDirsByParentId(root.getDocid());
                for(SysDoc rootSubDir:rootSubDirs){
                    DirFileInfor rootSubdirInfor = CommonUtils.copyDoc2Dfi(rootSubDir, new DirFileInfor());
                    files.add(rootSubdirInfor);
                }
            }
        }
        List<SysDoc> childDocs = docService.selectChildrensByParentId(this.getTarget());     //添加目标目录下的文件列表
        for (SysDoc child : childDocs) {
            if(!CommonUtils.filesContains(files,child.getDocid())){
                DirFileInfor childfile = CommonUtils.copyDoc2Dfi(child, new DirFileInfor());
                files.add(childfile);
            }
        }
        Answer answer = new Answer();
        answer.setCwd(cwd);
        answer.setFiles(files);
        if(null != this.getInit()&&  this.getInit().equals("1")){
            answer.setApi("2.0");
            List netDrivers =  new ArrayList<String>();
            netDrivers.add("ftp");
            answer.setNetDrivers(netDrivers);
            answer.setUplMaxSize("50M");
            Options opt = new Options();
            opt.setSeparator("\\");
            answer.setOptions(opt);
            answer.setDebug(new Debug());
        }
        return answer;
    }
    private Answer tree(){
        Answer answer = new Answer();
        SysDoc firstRoot = docService.targetDoc(target);         //当前文件夹
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
        where.createCriteria().andParentidEqualTo(target).andDoctypeEqualTo("1");//当前文件夹下的所有文件夹
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
    private Answer upload() throws IOException {
        Answer answer = new Answer();
        SysDoc targetDoc = docService.targetDoc(this.getTarget());
        SysDocserver sysDocserver =docServerService.selectByPrimaryKey(targetDoc.getDocserverid());
        SysAccount sessionAccount = (SysAccount) this.getHttpSession().getAttribute(Constants.user_in_session);
        if (null == sessionAccount) return null;
        List<DirFileInfor> addedFiles = new ArrayList<DirFileInfor>();       //文件列表
        if (null !=upload  && upload.size() > 0) {
            for (int i = 0; i < upload.size(); i++) {
                File inputFile =   upload.get(i);
                //String mineType = CommonUtils.getMime(inputFile);
                FileInputStream fis = new FileInputStream(inputFile);
                String fileName = uploadFileName.get(i) ;
                if("FTP".equals(sysDocserver.getServertype())){                        //文件上传到ftp服务器
                    FtpUtil util = new FtpUtil();
                    util.connect(sysDocserver.getServerip(),
                            sysDocserver.getServerport(),
                            sysDocserver.getFtpuser(),
                            sysDocserver.getFtppassword(),
                            sysDocserver.getServerpath() + targetDoc.getDocpath());
                    util.uploadFile(fis, fileName);
                    util.closeServer();
                }else {                                                                 //文件保存到本地目录
                    FileOutputStream fos = new FileOutputStream(sysDocserver.getServerpath() +targetDoc.getDocpath()+ fileName);
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = fis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fis.close();
                    fos.close();
                }
                SysDoc sysDoc = new SysDoc();
                sysDoc.setDocid(CommonUtils.getId());      //uuid
                sysDoc.setDocserverid(targetDoc.getDocserverid()); //当前激活的serverid
                sysDoc.setDocoldname(fileName);//原文件名
                sysDoc.setDocnewname(fileName);//文件名；
                sysDoc.setDoctype("0");//1 文件夹 0 文件   上传只能是文件
                sysDoc.setDoclength(String.valueOf(inputFile.length())); //文件大小
                sysDoc.setDocpath(targetDoc.getDocpath()+fileName);      //路径
                sysDoc.setCreater(sessionAccount.getAccountcode());   //创建者
                sysDoc.setCreatetime(CommonUtils.Time2String(new Date()));  //创建时间
                sysDoc.setFileid(null);// 挂接字段
                sysDoc.setTableid(null);//挂接表
                sysDoc.setAuthority(null);//权限
                sysDoc.setHeight(null);   //图片 高
                sysDoc.setWidth(null);    //图片 宽
                sysDoc.setHidden("1");
                sysDoc.setLocked("0");                     //是否锁定 1-锁定（不能剪切、删除 重命名）0-没有锁定（可以剪切、删除 重命名）
                sysDoc.setMwrite("1");                     //是否有写权限 1-有写权限
                sysDoc.setMread("1");                      //是否有读权限 1-有读权限
                sysDoc.setMime(getUploadContentType().get(i));//mine 类型        //
                sysDoc.setMtime(System.currentTimeMillis());//修改时间
                sysDoc.setParentid(this.getTarget());      //父节点
                if(docService.insertDoc(sysDoc)){
                    DirFileInfor file = CommonUtils.copyDoc2Dfi(sysDoc, new DirFileInfor());
                    addedFiles.add(file);
                }
            }
        }
        answer.setAdded(addedFiles);
        answer.setDebug(new Debug());
        return answer;
    }
    private Answer rename(){
        SysAccount sessionAccount = (SysAccount) this.getHttpSession().getAttribute(Constants.user_in_session);
        if (null == sessionAccount) {
            return null;
        }
        SysDoc sysDoc = docService.selectByPrimaryKey(this.getTarget());
        sysDoc.setDocoldname(sysDoc.getDocnewname());
        sysDoc.setDocnewname(this.getName());
        sysDoc.setMtime(222l);
        docService.updateDoc(sysDoc);
        Answer answer = new Answer();
        answer.setDebug(new Debug());
        return answer;
    }
    private Answer mkfile(){
        SysDoc targetDoc = docService.targetDoc(this.getTarget());
        //SysDocserver sysDocserver =docServerService.selectByPrimaryKey(targetDoc.getDocserverid());

        SysAccount sessionAccount = (SysAccount) this.getHttpSession().getAttribute(Constants.user_in_session);
        if (null == sessionAccount) {
            return null;
        }
        Answer answer = new Answer();
        List<DirFileInfor> addedFiles = new ArrayList<DirFileInfor>();       //文件列表
        SysDoc sysDoc = new SysDoc();
        sysDoc.setDocid(CommonUtils.getId());
        sysDoc.setDocserverid(targetDoc.getDocserverid()); //当前激活的serverid
        sysDoc.setDocoldname(this.getName());
        sysDoc.setDocnewname(this.getName());//文件名；
        sysDoc.setDoctype("1");//1 文件夹 0 文件
        sysDoc.setDoclength("0");
        sysDoc.setDocpath(targetDoc.getDocpath()+this.getName());      //路径
        sysDoc.setCreater(sessionAccount.getAccountcode());   //创建者
        sysDoc.setCreatetime("111");  //创建时间
        sysDoc.setParentid(this.getTarget());
        sysDoc.setMtime(111l);//修改时间
        sysDoc.setLocked("1");
        sysDoc.setHidden("1");
        /*sysDoc.setHeight(0);
        sysDoc.setWidth(0);*/
        sysDoc.setMread("1");
        sysDoc.setMwrite("1");
        sysDoc.setMime("text/plain");//mine 类型
        if(docService.insertDoc(sysDoc)){
            DirFileInfor file = CommonUtils.copyDoc2Dfi(sysDoc, new DirFileInfor());
            addedFiles.add(file);
        }
        answer.setAdded(addedFiles);
        answer.setDebug(new Debug());
        return answer;
    }
    private Answer mkdir() throws IOException {
        SysAccount sessionAccount = (SysAccount) this.getHttpSession().getAttribute(Constants.user_in_session);
        if (null == sessionAccount) { return null; }
        SysDoc targetDoc = docService.targetDoc(this.getTarget());
        SysDocserver sysDocserver =docServerService.selectByPrimaryKey(targetDoc.getDocserverid());
        Answer answer = new Answer();
        List<DirFileInfor> addedFiles = new ArrayList<DirFileInfor>();       //文件列表
        if("FTP".equals(sysDocserver.getServertype())){   //在ftp服务器上创建文件夹
            FtpUtil util = new FtpUtil();
            util.connect(sysDocserver.getServerip(),sysDocserver.getServerport(),sysDocserver.getFtpuser(),sysDocserver.getFtppassword());
            util.createDirectory(sysDocserver.getServerpath()+targetDoc.getDocpath()+name+"/");
            util.closeServer();
        }else {                                           //在本地创建文件目录
            FileOperate.newFolder(sysDocserver.getServerpath()+targetDoc.getDocpath()+name+"/");
        }
        SysDoc sysDoc = new SysDoc();
        sysDoc.setDocid(CommonUtils.getId());
        sysDoc.setDocserverid(targetDoc.getDocserverid()); //当前激活的serverid
        sysDoc.setDocoldname(name);
        sysDoc.setDocnewname(name);//文件名；
        sysDoc.setDoctype("1");//1 文件夹 0 文件
        sysDoc.setDoclength("0");
        sysDoc.setDocpath(targetDoc.getDocpath()+name+"/");      //路径
        sysDoc.setCreater(sessionAccount.getAccountcode());   //创建者
        sysDoc.setCreatetime(CommonUtils.Time2String(new Date()));  //创建时间
        sysDoc.setFileid(null);// 挂接字段
        sysDoc.setTableid(null);//挂接表
        sysDoc.setAuthority(null);//权限
        sysDoc.setHeight(null);   //图片 高
        sysDoc.setWidth(null);    //图片 宽
        sysDoc.setHidden("1");
        sysDoc.setLocked("0");                     //是否锁定 1-锁定（不能剪切、删除 重命名）0-没有锁定（可以剪切、删除 重命名）
        sysDoc.setMwrite("1");                     //是否有写权限 1-有写权限
        sysDoc.setMread("1");                      //是否有读权限 1-有读权限
        sysDoc.setMime("directory");//mine 类型        //
        sysDoc.setMtime(System.currentTimeMillis());//修改时间
        sysDoc.setParentid(this.getTarget());      //父节点
        if(docService.insertDoc(sysDoc)){
            DirFileInfor file = CommonUtils.copyDoc2Dfi(sysDoc, new DirFileInfor());
            addedFiles.add(file);
        }
        answer.setAdded(addedFiles);
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
    public List<String> getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(List<String> uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public List<String> getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(List<String> uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public List<File> getUpload() {
        return upload;
    }

    public void setUpload(List<File> upload) {
        this.upload = upload;
    }

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IDocService getDocService() {
        return docService;
    }

    public void setDocService(IDocService docService) {
        this.docService = docService;
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
    public IDocserverService getDocServerService() {
        return docServerService;
    }

    public void setDocServerService(IDocserverService docServerService) {
        this.docServerService = docServerService;
    }
}

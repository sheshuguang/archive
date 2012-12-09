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
import eu.medsea.mimeutil.MimeUtil;
import org.apache.commons.lang.xwork.StringUtils;

import java.io.*;
import java.util.*;


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
        if(this.cmd.equals("rm")){jsonOuter.write(gson.toJson(rm()));}
        return  null;

    }

    private Answer rm() {
        Answer answer = new Answer();
        Iterator it = targets.iterator();
        List removedList = new ArrayList();
        while(it.hasNext()){
            String target = (String)it.next();
            rm(target,removedList);
        }
        answer.setRemoved(removedList);
        return answer;
    }
    private void removeDirectory(String docId,List removedList){
        SysDocExample where = new SysDocExample();
        where.createCriteria().andParentidEqualTo(docId);
        List<SysDoc> childrenList = docService.selectByWhereNotPage(where);
        if(null != childrenList&& childrenList.size()>0){
            for(SysDoc children:childrenList){
                rm(children.getDocid(),removedList);
            }
        }
        if(rmfile(docId)&&1==docService.deleteDoc(docId)){
            removedList.add(docId);
        }
    }
    private void rm(String docId,List removedList){
        SysDoc targetDoc = docService.selectByPrimaryKey(docId);
        if(targetDoc.getDoctype().equals("1")){         //文件夹
            removeDirectory(docId,removedList);
        }else{
            if(rmfile(docId)&&1==docService.deleteDoc(docId)){
                removedList.add(docId);
            }
        }
    }
    private boolean rmfile(String docId){
         boolean result = false;
         SysDoc targetDoc = docService.selectByPrimaryKey(docId);
         SysDocserver sysDocserver =docServerService.selectByPrimaryKey(targetDoc.getDocserverid());
        if("FTP".equals(sysDocserver.getServertype())){
            FtpUtil util = new FtpUtil();
            try {
                util.connect(sysDocserver.getServerip(),
                        sysDocserver.getServerport(),
                        sysDocserver.getFtpuser(),
                        sysDocserver.getFtppassword(),
                        sysDocserver.getServerpath());
                if(targetDoc.getDoctype().equals("1")){
                    result = util.removeDirectory(sysDocserver.getServerpath() + targetDoc.getDocpath());
                }else{
                    result = util.deleteFile(sysDocserver.getServerpath() + targetDoc.getDocpath());
                }
                util.closeServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if("LOCAL".equals(sysDocserver.getServertype())){
            result = FileOperate.delFileOrDir(sysDocserver.getServerpath()+targetDoc.getDocpath());
        }
         return  result;
    }
    private Answer open(){
        DirFileInfor cwd =  CommonUtils.copyDoc2Dfi(docService.targetDoc(this.getTarget()), new DirFileInfor());       //当前目录
        this.setTarget(cwd.getHash());
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
        List files = new ArrayList<DirFileInfor>();
        DirFileInfor cwd =  CommonUtils.copyDoc2Dfi(docService.targetDoc(target), new DirFileInfor());       //当前目录
        files.add(cwd);
        SysDocExample where = new SysDocExample();
        where.createCriteria().andParentidEqualTo(target).andDoctypeEqualTo("1");//当前文件夹下的所有文件夹
        List<SysDoc> dosList = docService.selectByWhereNotPage(where);
        for(SysDoc doc:dosList){                      //all root
            DirFileInfor file = CommonUtils.copyDoc2Dfi(doc, new DirFileInfor());
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
            if(!isFilesExist(targetDoc,uploadFileName)) {
                for (int i = 0; i < upload.size(); i++) {
                    File inputFile =   upload.get(i);
                    String fileName = uploadFileName.get(i) ;
                    String fileSize = String.valueOf(inputFile.length());
                    String fileMime = CommonUtils.getMime(inputFile);
                    //文件系统操作
                    boolean result = this.save(inputFile,fileName,sysDocserver,targetDoc);
                    //数据库操作
                    if(result){
                        SysDoc sysDoc = new SysDoc();
                        sysDoc.setDocid(CommonUtils.getId());      //uuid
                        sysDoc.setDocserverid(targetDoc.getDocserverid()); //当前激活的serverid
                        sysDoc.setDocoldname(fileName);//原文件名
                        sysDoc.setDocnewname(fileName);//文件名；
                        sysDoc.setDoctype(fileName.substring(fileName.lastIndexOf(".")+1));//1 文件夹 0 文件   上传只能是文件
                        sysDoc.setDoclength(fileSize); //文件大小
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
                        sysDoc.setMime(fileMime);//mine 类型        //     getUploadContentType().get(i)
                        sysDoc.setMtime(System.currentTimeMillis());//修改时间
                        sysDoc.setParentid(this.getTarget());      //父节点
                        if(docService.insertDoc(sysDoc)){
                            DirFileInfor file = CommonUtils.copyDoc2Dfi(sysDoc, new DirFileInfor());
                            addedFiles.add(file);
                        }
                    }
                }
            }else{
                answer.setError("有同名文件存在！");
            }

        }
        answer.setAdded(addedFiles);
        answer.setDebug(new Debug());
        return answer;
    }
    private boolean rename(SysDocserver sysDocserver,SysDoc targetDoc){
        boolean  result = false;
        String targetPath = targetDoc.getDocpath();
        targetPath = targetPath.substring(0, targetPath.lastIndexOf('/'));
        try{
            if("FTP".equals(sysDocserver.getServertype())){   //在ftp服务器上创建文件夹

                FtpUtil util = new FtpUtil();
                util.connect(sysDocserver.getServerip(),
                        sysDocserver.getServerport(),
                        sysDocserver.getFtpuser(),
                        sysDocserver.getFtppassword(),
                        sysDocserver.getServerpath() + targetPath);
                result = util.rename(targetDoc.getDocnewname(),name);
                util.closeServer();
            }else if("LOCAL".equals(sysDocserver.getServertype())){                                           //在本地创建文件目录
                result = FileOperate.rename(sysDocserver.getServerpath()+targetDoc.getDocpath(),sysDocserver.getServerpath()+targetPath+File.separatorChar+name);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return result;
    }
    private Answer rename(){
        SysAccount sessionAccount = (SysAccount) this.getHttpSession().getAttribute(Constants.user_in_session);
        if (null == sessionAccount) { return null;  }
        Answer answer = new Answer();
        List<DirFileInfor> addedFiles = new ArrayList<DirFileInfor>();       //文件列表
        SysDoc targetDoc = docService.selectByPrimaryKey(this.getTarget());
        SysDocserver sysDocserver =docServerService.selectByPrimaryKey(targetDoc.getDocserverid());
        if(rename(sysDocserver,targetDoc)){     //操作物理文件
            //targetDoc.setDocid(CommonUtils.getId());
            String targetPath = targetDoc.getDocpath();
            targetPath = targetPath.substring(0, targetPath.lastIndexOf('/'));
            targetDoc.setDocnewname(name);
            targetDoc.setDocpath(targetPath + "/" + name);
            targetDoc.setMtime(System.currentTimeMillis());
            if(1!=docService.updateDoc(targetDoc)){
                //需要进一步处理
                answer.setError("修改文件名出错！");
            }
            /*if(docService.insertDoc(targetDoc)){
                DirFileInfor file = CommonUtils.copyDoc2Dfi(targetDoc, new DirFileInfor());
                addedFiles.add(file);
            }
            if(docService.deleteDoc(this.getTarget())>=1){
                List removedIds = new ArrayList();
                removedIds.add(this.getTarget());
                answer.setRemoved(removedIds);
            }*/
        }else{
            answer.setError("修改物理文件名出错！");
        }
        answer.setDebug(new Debug());
        return answer;
    }
    private boolean mkdir(SysDocserver sysDocserver,SysDoc targetDoc){
        boolean  result = false;
        try{
            if("FTP".equals(sysDocserver.getServertype())){   //在ftp服务器上创建文件夹
                FtpUtil util = new FtpUtil();
                util.connect(sysDocserver.getServerip(),sysDocserver.getServerport(),sysDocserver.getFtpuser(),sysDocserver.getFtppassword());
                result = util.createDirectory(sysDocserver.getServerpath()+targetDoc.getDocpath()+name+"/");
                util.closeServer();
            }else if("LOCAL".equals(sysDocserver.getServertype())){                                           //在本地创建文件目录
                result = FileOperate.newFolder(sysDocserver.getServerpath()+targetDoc.getDocpath()+name+"/");
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
         return result;
    }
    private Answer mkdir() throws IOException {
        SysAccount sessionAccount = (SysAccount) this.getHttpSession().getAttribute(Constants.user_in_session);
        if (null == sessionAccount) { return null; }
        SysDoc targetDoc = docService.targetDoc(this.getTarget());
        SysDocserver sysDocserver =docServerService.selectByPrimaryKey(targetDoc.getDocserverid());
        Answer answer = new Answer();
        List<DirFileInfor> addedFiles = new ArrayList<DirFileInfor>();       //文件列表
        if( mkdir( sysDocserver, targetDoc)) {
            SysDoc sysDoc = new SysDoc();
            sysDoc.setDocid(CommonUtils.getId());
            sysDoc.setDocserverid(targetDoc.getDocserverid()); //当前激活的serverid
            sysDoc.setDocoldname(name);
            sysDoc.setDocnewname(name);//文件名；
            sysDoc.setDoctype("1");//1 文件夹
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
        }else {
            answer.setError("创建文件夹时出错！");
        }
        answer.setAdded(addedFiles);
        answer.setDebug(new Debug());
        return answer;
    }
    private  boolean  mkfile(SysDocserver sysDocserver,SysDoc targetDoc){
        boolean result = false;
        try{
            if("FTP".equals(sysDocserver.getServertype())){   //在ftp服务器上创建文件夹
                String fileTempName = CommonUtils.getId();
                //FileOperate.newFile(System.getProperty("java.io.tmpdir")+File.separatorChar+fileTempName+".tmp","");
                File inputFile = new File(System.getProperty("java.io.tmpdir")+File.separatorChar+fileTempName+".tmp");
                inputFile.createNewFile();
                FileInputStream fis = new FileInputStream(inputFile);
                FtpUtil util = new FtpUtil();
                util.connect(sysDocserver.getServerip(),
                        sysDocserver.getServerport(),
                        sysDocserver.getFtpuser(),
                        sysDocserver.getFtppassword(),
                        sysDocserver.getServerpath() + targetDoc.getDocpath());
                result = util.uploadFile(fis, name);
                util.closeServer();
            }else if("LOCAL".equals(sysDocserver.getServertype())){                                           //在本地创建文件
                result = FileOperate.newFile(sysDocserver.getServerpath()+targetDoc.getDocpath()+name,"");
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return result;
    }
    private Answer mkfile(){
        SysAccount sessionAccount = (SysAccount) this.getHttpSession().getAttribute(Constants.user_in_session);
        if (null == sessionAccount) {
            return null;
        }
        SysDoc targetDoc = docService.targetDoc(this.getTarget());
        SysDocserver sysDocserver =docServerService.selectByPrimaryKey(targetDoc.getDocserverid());
        Answer answer = new Answer();
        List<DirFileInfor> addedFiles = new ArrayList<DirFileInfor>();       //文件列表
        if(mkfile(sysDocserver,targetDoc)){
            SysDoc sysDoc = new SysDoc();
            sysDoc.setDocid(CommonUtils.getId());
            sysDoc.setDocserverid(targetDoc.getDocserverid()); //当前激活的serverid
            sysDoc.setDocoldname(name);
            sysDoc.setDocnewname(name);//文件名；
            sysDoc.setDoctype("TXT");//1 文件夹
            sysDoc.setDoclength("0");
            sysDoc.setDocpath(targetDoc.getDocpath()+name);      //路径
            sysDoc.setCreater(sessionAccount.getAccountcode());   //创建者
            sysDoc.setCreatetime(CommonUtils.Time2String(new Date()));  //创建时间
            sysDoc.setFileid(null);// 挂接字段
            sysDoc.setTableid(null);//挂接表
            sysDoc.setAuthority(null);//权限
            sysDoc.setHeight(null);   //图片 高
            sysDoc.setWidth(null);    //图片 宽
            sysDoc.setLocked("0");
            sysDoc.setHidden("1");
            sysDoc.setMread("1");
            sysDoc.setMwrite("1");
            sysDoc.setMime("text/plain");//mine 类型
            sysDoc.setMtime(System.currentTimeMillis());//修改时间
            sysDoc.setParentid(this.getTarget());
            if(docService.insertDoc(sysDoc)){
                DirFileInfor file = CommonUtils.copyDoc2Dfi(sysDoc, new DirFileInfor());
                addedFiles.add(file);
            }
        }else{
            answer.setError("创建文件失败！");
        }
        answer.setAdded(addedFiles);
        answer.setDebug(new Debug());
        return answer;
    }

    private boolean save (File inputFile,String fileName,SysDocserver sysDocserver,SysDoc targetDoc){
        boolean result = false;
        try {
            FileInputStream fis = new FileInputStream(inputFile);
            if("FTP".equals(sysDocserver.getServertype())){                        //文件上传到ftp服务器
                FtpUtil util = new FtpUtil();
                util.connect(sysDocserver.getServerip(),
                        sysDocserver.getServerport(),
                        sysDocserver.getFtpuser(),
                        sysDocserver.getFtppassword(),
                        sysDocserver.getServerpath() + targetDoc.getDocpath());
                result = util.uploadFile(fis, fileName);
                util.closeServer();
            }else if ("LOCAL".equals(sysDocserver.getServertype())){
                FileOutputStream fos = new FileOutputStream(sysDocserver.getServerpath() +targetDoc.getDocpath()+ fileName);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fis.close();
                fos.close();
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }
    private boolean isFilesExist(SysDoc targetDoc,List<String> uploadFileName){
        Iterator it  = uploadFileName.iterator();
        while (it.hasNext()){
            String fileName = (String)it.next();
            SysDocExample where = new SysDocExample();
            where.createCriteria().andDocserveridEqualTo(targetDoc.getDocserverid())
                    .andDocpathEqualTo(targetDoc.getDocpath()+fileName);
            if(docService.rowCount(where)>0){
                return true;
            }
        }
        return false;
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

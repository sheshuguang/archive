package com.yapu.system.util;
/**
 * ftp 功能操作类。
 * author  wangf
 * date    2012-1-14
 */
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class FtpUtil {
    //private FTPClient ftpClient;
    private FTPClient ftpClient = new FTPClient();
    public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
    public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;
    public FtpUtil(){
        //设置将过程中使用到的命令输出到控制台
        this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    }
    /**
     * 连接到FTP服务器
     * @param hostname 主机名
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return 是否连接成功
     * @throws IOException
     */
    public boolean connect(String hostname,int port,String username,String password) throws IOException{
        ftpClient.connect(hostname, port);
        ftpClient.setControlEncoding("GBK");
        if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
            if(ftpClient.login(username, password)){
                return true;
            }
        }
        disconnect();
        return false;
    }
    /**
     * 断开与远程服务器的连接
     * @throws IOException
     */
    public void disconnect() throws IOException{
        if(ftpClient.isConnected()){
            ftpClient.disconnect();
        }
    }
    /**
     * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报
     * @param remote 远程文件路径
     * @param local 本地文件路径
     * @return 上传的状态
     * @throws IOException
     */
    public DownloadStatus download(String remote,String local) throws IOException{
        //设置被动模式
        ftpClient.enterLocalPassiveMode();
        //设置以二进制方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        DownloadStatus result;

        //检查远程文件是否存在
        FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"),"iso-8859-1"));
        if(files.length != 1){
            System.out.println("远程文件不存在");
            return DownloadStatus.Remote_File_Noexist;
        }

        long lRemoteSize = files[0].getSize();
        File f = new File(local);
        //本地存在文件，进行断点下载
        if(f.exists()){
            long localSize = f.length();
            //判断本地文件大小是否大于远程文件大小
            if(localSize >= lRemoteSize){
                System.out.println("本地文件大于远程文件，下载中止");
                return DownloadStatus.Local_Bigger_Remote;
            }

            //进行断点续传，并记录状态
            FileOutputStream out = new FileOutputStream(f,true);
            ftpClient.setRestartOffset(localSize);
            InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));
            byte[] bytes = new byte[1024];
            long step = lRemoteSize /100;
            long process=localSize /step;
            int c;
            while((c = in.read(bytes))!= -1){
                out.write(bytes,0,c);
                localSize+=c;
                long nowProcess = localSize /step;
                if(nowProcess > process){
                    process = nowProcess;
                    if(process % 10 == 0)
                        System.out.println("下载进度："+process);
                    //TODO 更新文件下载进度,值存放在process变量中
                }
            }
            in.close();
            out.close();
            boolean isDo = ftpClient.completePendingCommand();
            if(isDo){
                result = DownloadStatus.Download_From_Break_Success;
            }else {
                result = DownloadStatus.Download_From_Break_Failed;
            }
        }else {
            OutputStream out = new FileOutputStream(f);
            InputStream in= ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));
            byte[] bytes = new byte[1024];
            long step = lRemoteSize /100;
            long process=0;
            long localSize = 0L;
            int c;
            while((c = in.read(bytes))!= -1){
                out.write(bytes, 0, c);
                localSize+=c;
                long nowProcess = localSize /step;
                if(nowProcess > process){
                    process = nowProcess;
                    if(process % 10 == 0)
                        System.out.println("下载进度："+process);
                    //TODO 更新文件下载进度,值存放在process变量中
                }
            }
            in.close();
            out.close();
            boolean upNewStatus = ftpClient.completePendingCommand();
            if(upNewStatus){
                result = DownloadStatus.Download_New_Success;
            }else {
                result = DownloadStatus.Download_New_Failed;
            }
        }
        return result;
    }

    /**
     * 上传文件到FTP服务器，支持断点续传
     * @param local 本地文件名称，绝对路径
     * @param remote 远程文件路径，使用/home/directory1/subdirectory/file.ext或是 http://www.guihua.org /subdirectory/file.ext 按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
     * @return 上传结果
     * @throws IOException
     */
    public UploadStatus upload(String local,String remote) throws IOException{
        //设置PassiveMode传输
        ftpClient.enterLocalPassiveMode();
        //设置以二进制流的方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.setControlEncoding("GBK");
        UploadStatus result;
        //对远程目录的处理
        String remoteFileName = remote;
        if(remote.contains("/")){
            remoteFileName = remote.substring(remote.lastIndexOf("/")+1);
            //创建服务器远程目录结构，创建失败直接返回
            if(CreateDirecroty(remote, ftpClient)==UploadStatus.Create_Directory_Fail){
                return UploadStatus.Create_Directory_Fail;
            }
        }

        //检查远程是否存在文件
        FTPFile[] files = ftpClient.listFiles(new String(remoteFileName.getBytes("GBK"),"iso-8859-1"));
        if(files.length == 1){
            long remoteSize = files[0].getSize();
            File f = new File(local);
            long localSize = f.length();
            if(remoteSize==localSize){
                return UploadStatus.File_Exits;
            }else if(remoteSize > localSize){
                return UploadStatus.Remote_Bigger_Local;
            }

            //尝试移动文件内读取指针,实现断点续传
            result = uploadFile(remoteFileName, f, ftpClient, remoteSize);

            //如果断点续传没有成功，则删除服务器上文件，重新上传
            if(result == UploadStatus.Upload_From_Break_Failed){
                if(!ftpClient.deleteFile(remoteFileName)){
                    return UploadStatus.Delete_Remote_Faild;
                }
                result = uploadFile(remoteFileName, f, ftpClient, 0);
            }
        }else {
            result = uploadFile(remoteFileName, new File(local), ftpClient, 0);
        }
        return result;
    }

    /**
     * 递归创建远程服务器目录
     * @param remote 远程服务器文件绝对路径
     * @param ftpClient FTPClient对象
     * @return 目录创建是否成功
     * @throws IOException
     */
    public UploadStatus CreateDirecroty(String remote,FTPClient ftpClient) throws IOException{
        UploadStatus status = UploadStatus.Create_Directory_Success;
        String directory = remote.substring(0,remote.lastIndexOf("/")+1);
        if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(new String(directory.getBytes("GBK"),"iso-8859-1"))){
            //如果远程目录不存在，则递归创建远程服务器目录
            int start=0;
            int end = 0;
            if(directory.startsWith("/")){
                start = 1;
            }else{
                start = 0;
            }
            end = directory.indexOf("/",start);
            while(true){
                String subDirectory = new String(remote.substring(start,end).getBytes("GBK"),"iso-8859-1");
                if(!ftpClient.changeWorkingDirectory(subDirectory)){
                    if(ftpClient.makeDirectory(subDirectory)){
                        ftpClient.changeWorkingDirectory(subDirectory);
                    }else {
                        System.out.println("创建目录失败");
                        return UploadStatus.Create_Directory_Fail;
                    }
                }

                start = end + 1;
                end = directory.indexOf("/",start);

                //检查所有目录是否创建完毕
                if(end <= start){
                    break;
                }
            }
        }
        return status;
    }

    /**
     * 上传文件到服务器,新上传和断点续传
     * @param remoteFile 远程文件名，在上传之前已经将服务器工作目录做了改变
     * @param localFile 本地文件File句柄，绝对路径
     * @param ftpClient FTPClient引用
     * @return
     * @throws IOException
     */
    public UploadStatus uploadFile(String remoteFile,File localFile,FTPClient ftpClient,long remoteSize) throws IOException{
        UploadStatus status;
        //显示进度的上传
        long step = localFile.length() / 100;
        long process = 0;
        long localreadbytes = 0L;
        RandomAccessFile raf = new RandomAccessFile(localFile,"r");
        OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes("GBK"),"iso-8859-1"));
        //断点续传
        if(remoteSize>0){
            ftpClient.setRestartOffset(remoteSize);
            process = remoteSize /step;
            raf.seek(remoteSize);
            localreadbytes = remoteSize;
        }
        byte[] bytes = new byte[1024];
        int c;
        while((c = raf.read(bytes))!= -1){
            out.write(bytes,0,c);
            localreadbytes+=c;
            if(localreadbytes / step != process){
                process = localreadbytes / step;
                System.out.println("上传进度:" + process);
                //TODO 汇报上传状态
            }
        }
        out.flush();
        raf.close();
        out.close();
        boolean result =ftpClient.completePendingCommand();
        if(remoteSize > 0){
            status = result?UploadStatus.Upload_From_Break_Success:UploadStatus.Upload_From_Break_Failed;
        }else {
            status = result?UploadStatus.Upload_New_File_Success:UploadStatus.Upload_New_File_Failed;
        }
        return status;
    }
    // path should not the path from root index
    // or some FTP server would go to root as '/'.
//    public void connectServer(FtpConfig ftpConfig) throws SocketException,
//            IOException {
//        String server = ftpConfig.getServer();
//        int port = ftpConfig.getPort();
//        String user = ftpConfig.getUsername();
//        String password = ftpConfig.getPassword();
//        String location = ftpConfig.getLocation();
//        connectServer(server, port, user, password, location);
//    }

    public boolean testFtpConnect(String server, int port, String user,String password, String path) throws IOException {
        boolean reply = connect(server,port,user,password,path);
        closeServer();
        return reply;
    }

     /*
        连接ftp服务器
     */
    public boolean connect(String server, int port, String user, String password, String path) throws SocketException, IOException {
        boolean result = false;
        try{
            ftpClient.connect(server, port);
            ftpClient.setControlEncoding("GBK");
            System.out.println("Connected to " + server + ".");
            System.out.println(ftpClient.getReplyCode());
            ftpClient.login(user, password);

            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                closeServer();
                return result;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            closeServer();
            return result;
        }

        result = true;
        // Path is the sub-path of the FTP path
        if (path.length() != 0) {
            ftpClient.changeWorkingDirectory(new String(path.getBytes("GBK"),"iso-8859-1"));
        }

        return result;
    }

    //FTP.BINARY_FILE_TYPE | FTP.ASCII_FILE_TYPE
    // 设置文件类型。（BINARY_FILE_TYPE 二进制）  （FTP.ASCII_FILE_TYPE ascII）
    public void setFileType(int fileType) throws IOException {
        ftpClient.setFileType(fileType);
    }

    /*
    关闭ftp服务器连接
     */
    public void closeServer() throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.disconnect();
        }
    }
    //=======================================================================
    //==         About directory  服务器文件夹操作      =====
    // The following method using relative path better.
    //=======================================================================

    public boolean changeDirectory(String path) throws IOException {
        return ftpClient.changeWorkingDirectory(path);
    }

    public boolean createDirectory(String pathName) throws IOException {
        if(UploadStatus.Create_Directory_Success == this.CreateDirecroty(pathName,ftpClient)){
             return true;
        }
        //return ftpClient.makeDirectory(pathName);
        return false;
    }

    public boolean removeDirectory(String path) throws IOException {
        return ftpClient.removeDirectory(new String(path.getBytes("GBK"),"iso-8859-1"));
    }

    // delete all subDirectory and files.
    //删除所有子文件夹和文件
    public boolean removeDirectory(String path, boolean isAll)
            throws IOException {

        if (!isAll) {
            return removeDirectory(path);
        }

        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr == null || ftpFileArr.length == 0) {
            return removeDirectory(path);
        }
        //
        for (FTPFile ftpFile : ftpFileArr) {
            String name = ftpFile.getName();
            if (ftpFile.isDirectory()) {
                System.out.println("* [sD]Delete subPath [" + path + "/" + name + "]");
//                if(!ftpFile.getName().equals(".")&&(!ftpFile.getName().equals(".."))){}
                removeDirectory(path + "/" + name, true);
            } else if (ftpFile.isFile()) {
                System.out.println("* [sF]Delete file [" + path + "/" + name + "]");
                deleteFile(path + "/" + name);
            } else if (ftpFile.isSymbolicLink()) {

            } else if (ftpFile.isUnknown()) {

            }
        }
        return ftpClient.removeDirectory(path);
    }

    // Check the path is exist; exist return true, else false.
    //检查这个路径是否存在，存在返回true，否则 flash
    public boolean existDirectory(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        for (FTPFile ftpFile : ftpFileArr) {
            if (ftpFile.isDirectory()
                    && ftpFile.getName().equalsIgnoreCase(path)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    //=======================================================================
    //==         About file  关于文件操作      =====
    // Download and Upload file using  下载和上传文件最好使用二进制编码
    // ftpUtil.setFileType(FtpUtil.BINARY_FILE_TYPE) better!
    //=======================================================================

    // #1. list & delete operation
    // Not contains directory
    public List<String> getFileList(String path) throws IOException {
        // listFiles return contains directory and file, it's FTPFile instance
        // listNames() contains directory, so using following to filer directory.
        //String[] fileNameArr = ftpClient.listNames(path);
        FTPFile[] ftpFiles = ftpClient.listFiles(path);

        List<String> retList = new ArrayList<String>();
        if (ftpFiles == null || ftpFiles.length == 0) {
            return retList;
        }
        for (FTPFile ftpFile : ftpFiles) {
            if (ftpFile.isFile()) {
                retList.add(ftpFile.getName());
            }
        }
        return retList;
    }

    public boolean deleteFile(String pathName) throws IOException {
        return ftpClient.deleteFile(new String(pathName.getBytes("GBK"),"iso-8859-1"));
    }

    // #2. upload to ftp server
    // InputStream <------> byte[]  simple and See API

    public boolean uploadFile(String fileName, String newName)
            throws IOException {
        boolean flag = false;
        InputStream iStream = null;
        try {
            iStream = new FileInputStream(fileName);
            flag = ftpClient.storeFile(newName, iStream);
        } catch (IOException e) {
            flag = false;
            return flag;
        } finally {
            if (iStream != null) {
                iStream.close();
            }
        }
        return flag;
    }

    public boolean uploadFile(String fileName) throws IOException {
        return uploadFile(fileName, fileName);
    }

    public boolean uploadFile(InputStream iStream, String newName)
            throws IOException {
        boolean flag = false;
        setFileType(BINARY_FILE_TYPE);
        try {
            // can execute [OutputStream storeFileStream(String remote)]
            // Above method return's value is the local file stream.
            flag = ftpClient.storeFile(new String(newName.getBytes("GBK"),"iso-8859-1"), iStream);
        } catch (IOException e) {
            flag = false;
            return flag;
        } finally {
            if (iStream != null) {
                iStream.close();
            }
        }
        return flag;
    }

    // #3. Down load

   /* public boolean download(String remoteFileName, String localFileName)
            throws IOException {
        boolean flag = false;
        File outfile = new File(localFileName);
        OutputStream oStream = null;
        try {
            oStream = new FileOutputStream(outfile);
            flag = ftpClient.retrieveFile(remoteFileName, oStream);
        } catch (IOException e) {
            flag = false;
            return flag;
        } finally {
            oStream.close();
        }
        return flag;
    }*/

    public InputStream downFile(String sourceFileName) throws IOException {
        sourceFileName = new String(sourceFileName.getBytes("GBK"),"iso-8859-1");
        return ftpClient.retrieveFileStream(sourceFileName);
    }

    public boolean rename(String oldName, String newName) {
        try {
           return ftpClient.rename(new String(oldName.getBytes("GBK"),"iso-8859-1"),new String(newName.getBytes("GBK"),"iso-8859-1")) ;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }
}
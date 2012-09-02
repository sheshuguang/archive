package com.yapu.system.util;
/**
 * ftp 功能操作类。
 * author  wangf
 * date    2012-1-14
 */
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class FtpUtil {
    private FTPClient ftpClient;
    public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
    public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;

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

    public boolean testFtpConnect(String server, int port, String user,
                                  String password, String path) throws IOException {
        boolean reply = connectServer(server,port,user,password,path);
        closeServer();
        return reply;
    }

    /*
        连接ftp服务器
     */
    public boolean connectServer(String server, int port, String user,
                              String password, String path) throws SocketException, IOException {
        boolean result = false;

        ftpClient = new FTPClient();
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
            ftpClient.changeWorkingDirectory(path);
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
        return ftpClient.makeDirectory(pathName);
    }

    public boolean removeDirectory(String path) throws IOException {
        return ftpClient.removeDirectory(path);
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
        return ftpClient.deleteFile(pathName);
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

    // #3. Down load

    public boolean download(String remoteFileName, String localFileName)
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
    }

    public InputStream downFile(String sourceFileName) throws IOException {
        return ftpClient.retrieveFileStream(sourceFileName);
    }
}
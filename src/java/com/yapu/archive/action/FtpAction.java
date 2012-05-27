package com.yapu.archive.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.yapu.archive.entity.SysDocserver;
import com.yapu.archive.service.itf.IDocserverService;
import com.yapu.system.util.FtpUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;

import com.yapu.system.common.BaseAction;
import org.apache.commons.net.ftp.FTPReply;

public class FtpAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;

    private IDocserverService docserverService;
	private String docserverid;

	public  String testFtp() throws IOException {
        HttpServletResponse response = getResponse();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String result = "ftp测试连接成功！";

        SysDocserver docserver = docserverService.selectByPrimaryKey(docserverid);

        if (null == docserver) {
            result = "ftp测试连接失败，请检查输入及ftp服务器设置是否正确。";
            out.write(result);
            return null;
        }

        FtpUtil ftpUtil = new FtpUtil();
        boolean reply = ftpUtil.testFtpConnect(docserver.getServerip(),docserver.getServerport(),
                docserver.getFtpuser(),docserver.getFtppassword(),docserver.getServerpath());
        if (!reply) {
            result = "ftp测试连接失败，请检查输入及ftp服务器设置是否正确。";
        }

        out.write(result);
        return null;
    }
    
//	public String testFtp1() throws IOException {
//		HttpServletResponse response = getResponse();
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html");
//		response.setHeader("Cache-Control", "no-cache");
//		PrintWriter out  = response.getWriter();
//
//		String result = "ftp测试连接成功！";
//
//        SysDocserver docserver = docserverService.selectByPrimaryKey(docserverid);
//
//        if (null == docserver) {
//            result = "ftp测试连接失败，请检查输入及ftp服务器设置是否正确。";
//            out.write(result);
//            return null;
//        }
//
//		FTPClient ftpClient = new FTPClient();
//        int reply;
//		try {
//            ftpClient.connect(docserver.getServerip());
//            ftpClient.login(docserver.getFtpuser(), docserver.getFtppassword());
////            String reply = ftpClient.getReplyString();
//            reply = ftpClient.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                ftpClient.disconnect();
//                result = "ftp测试连接失败，请检查输入及ftp服务器设置是否正确。";
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            result = "ftp测试连接失败，请检查输入及ftp服务器设置是否正确。";
//
//        } finally {
//            try {
//                ftpClient.disconnect();
//            } catch (IOException e) {
//                e.printStackTrace();
//                throw new RuntimeException("关闭FTP连接发生异常！", e);
//            }
//        }
//
//		out.write(result);
//		return null;
//	}
	
	
	
	public String ftp() throws IOException {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		String result = "ftp测试连接成功！";
		
		FTPClient ftpClient = new FTPClient();
		FileInputStream fis = null; 
		try {
            ftpClient.connect("192.168.1.102");
            ftpClient.login("a", "aa");

            String fileName = "房屋租赁合同.doc";
            	
            File srcFile = new File("C:\\" + fileName);
            fis = new FileInputStream(srcFile);
            //设置上传目录
            String path = "讲座";
            path = new String(path.getBytes("GBK"),"iso-8859-1");  
            ftpClient.changeWorkingDirectory("/" + path);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);  
            conf.setServerLanguageCode("zh");  
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            
            ftpClient.storeFile(new String(fileName.getBytes("GBK"),"iso-8859-1"), fis);
        } catch (IOException e) {
            e.printStackTrace();
            result = "ftp测试连接失败。";
            
        } finally {
            IOUtils.closeQuietly(fis);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        } 
		
		out.write(result);
		return null;
	}

	public String getDocserverid() {
		return docserverid;
	}
	public void setDocserverid(String docserverid) {
		this.docserverid = docserverid;
	}

    public void setDocserverService(IDocserverService docserverService) {
        this.docserverService = docserverService;
    }
}

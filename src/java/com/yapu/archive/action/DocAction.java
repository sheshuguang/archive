package com.yapu.archive.action;

import DBstep.iMsgServer2000;
import com.google.gson.Gson;
import com.yapu.archive.entity.SysDoc;
import com.yapu.archive.entity.SysDocExample;
import com.yapu.archive.entity.SysDocserver;
import com.yapu.archive.entity.SysDocserverExample;
import com.yapu.archive.service.itf.IDocService;
import com.yapu.archive.service.itf.IDocserverService;
import com.yapu.archive.vo.UploadVo;
import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.util.CommonUtils;
import com.yapu.system.util.Constants;
import com.yapu.system.util.Coverter;
import com.yapu.system.util.FtpUtil;
import org.apache.struts2.ServletActionContext;
import org.springframework.dao.support.DaoSupport;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * .
 * User: wangf
 * Date: 12-1-13
 * Time: 下午6:52
 */
public class DocAction extends BaseAction{

    private File archive;
    private String archiveFileName;
    private static final int BUFFER_SIZE = 2 * 1024;

    private IDocserverService docserverService;
    private IDocService docService;
    private String docId;

    private SysDoc doc;
    private String tableid;
    private String selectRowid;
    private int chunks;
    private String name;
    private int chunk;
    private File file;
    private String savePath;
    
    private String docName;
    private String contentType;

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String showDocListTab() {
        return SUCCESS;
    }

    public String list() throws IOException {
        PrintWriter out = this.getPrintWriter();
        SysDocExample example = new SysDocExample();
       // SysDocExample.Criteria criteria = example.createCriteria();
       // criteria.andFileidEqualTo(selectRowid);
       // criteria.andTableidEqualTo(tableid);
    	List<SysDoc> docList = docService.selectByWhereNotPage(example);
    	Gson gson = new Gson();
    	out.write(gson.toJson(docList));
    	return null;
    }
    
    public String listLinkDoc() throws IOException {
    	PrintWriter out = this.getPrintWriter();
    	SysDocExample example = new SysDocExample();
        SysDocExample.Criteria criteria = example.createCriteria();
        criteria.andFileidEqualTo(selectRowid);
        criteria.andTableidEqualTo(tableid);
     	List<SysDoc> docList = docService.selectByWhereNotPage(example);
     	Gson gson = new Gson();
     	out.write(gson.toJson(docList));
     	return null;
    }
    /**
     * 得到当前帐户上传的未挂接的电子全文
     * @return
     * @throws IOException
     */
    public String listNoLinkDocAsAccount() throws IOException {
    	PrintWriter out = this.getPrintWriter();
    	//得到当前登录帐户
    	SysAccount sessionAccount = (SysAccount) this.getHttpSession().getAttribute(Constants.user_in_session);
    	if (null == sessionAccount) {
    		out.write("error");
    		return null;
    	}
        SysDocExample example = new SysDocExample();
        SysDocExample.Criteria criteria = example.createCriteria();
        criteria.andCreaterEqualTo(sessionAccount.getAccountcode());
        criteria.andFileidEqualTo("");
        criteria.andTableidEqualTo("");
    	List<SysDoc> docList = docService.selectByWhereNotPage(example);
    	Gson gson = new Gson();
    	out.write(gson.toJson(docList));
    	return null;
    }
    public String upload() throws Exception {
        //得到当前登录帐户
        SysAccount sessionAccount = (SysAccount) this.getHttpSession().getAttribute(Constants.user_in_session);
        if (null == sessionAccount) {
            return null;
        }
        String contextPath = ServletActionContext.getServletContext().getRealPath(this.getSavePath())+ File.separator;
        String dstPath =  contextPath+ this.getName();
        File dstFile = new File(dstPath);
        // 文件已存在（上传了同名的文件）
        if (chunk == 0 && dstFile.exists()) {
            dstFile.delete();
            dstFile = new File(dstPath);
        }
        cat(this.file, dstFile);
        if (chunk == chunks - 1) {   // 完成一整个文件;
            String docType = "";//扩展名
            String oldName = dstFile.getName();
            String docId = UUID.randomUUID().toString();

            if (oldName.lastIndexOf(".") >= 0) {
                docType = oldName.substring(oldName.lastIndexOf("."));
            }
            String newName = docId+docType;
            File newFile =new File(contextPath+newName);
            dstFile.renameTo(newFile);
            SysDoc doc = new SysDoc();
            doc.setDocid(docId);
            doc.setDocnewname(newName);
            doc.setDoclength(CommonUtils.formatFileSize(newFile.length()));
            doc.setDocoldname(oldName);
            doc.setDoctype(docType);
            doc.setCreater(sessionAccount.getAccountcode());
            doc.setCreatetime(CommonUtils.getTimeStamp());
            SysDocserverExample example = new SysDocserverExample();
            example.createCriteria().andServerstateEqualTo(1);
            List<SysDocserver> docserverList = docserverService.selectByWhereNotPage(example);
            SysDocserver docserver = docserverList.get(0);
            if ("FTP".equals(docserver.getServertype())) {
                FtpUtil util = new FtpUtil();
                util.connectServer(docserver.getServerip(),
                        docserver.getServerport(),
                        docserver.getFtpuser(),
                        docserver.getFtppassword(),
                        docserver.getServerpath());
                FileInputStream s = new FileInputStream(newFile);
                util.uploadFile(s, newName);
                util.closeServer();
            } else if ("LOCAL".equals(docserver.getServertype())){
                String serverPath = docserver.getServerpath();
                String savePath = docserver.getServerpath();
                if (null == serverPath || "".equals(serverPath)) {
                        return null;
                }
                else {
                    if (!serverPath.substring(serverPath.length()-1,serverPath.length()).equals("/")) {
                        savePath += "/";
                    }
                }
                newFile.renameTo(new File(savePath + newName));
            }
            doc.setDocpath( docserver.getServerpath() + newName);
            doc.setDocserverid(docserver.getDocserverid());
            docService.insertDoc(doc);

        }
        return null;
    }
    public String delete() throws Exception {
        docService.deleteDoc(this.getDocId());
        return null;
    }
    public String read() throws Exception {
        String contextPath = ServletActionContext.getServletContext().getRealPath(this.getSavePath())+ File.separator;
        SysDoc doc = docService.selectByPrimaryKey(this.getDocId());
        String filename = doc.getDocnewname();
        File swfFile = null;
        if(filename.toLowerCase().endsWith(".pdf")) {
            swfFile = new File(contextPath+filename+".swf");
            if(!swfFile.exists()) swfFile =  Coverter.PdfToSwf(new File(doc.getDocpath()));
        }else if(CommonUtils.isPdfPrintType(filename)){
            swfFile = new File(contextPath+filename+".pdf.swf");
            if(!swfFile.exists()) {
                File pdf = Coverter.toPdf(new File(doc.getDocpath()));
                swfFile = Coverter.PdfToSwf(pdf);
            }
        }
        outSwf(swfFile);
        return null;
    }
    public String iwebRead() throws Exception{
        SysDoc doc = docService.selectByPrimaryKey(this.getDocId());
        iMsgServer2000 MsgObj = new iMsgServer2000();
        MsgObj.Load(this.getRequest());
        if (MsgObj.GetMsgByName("DBSTEP").equalsIgnoreCase("DBSTEP")) {         //判断是否是合法的信息包，或者数据包信息是否完整
            String mRecordID=MsgObj.GetMsgByName("RECORDID");		//取得文档编号
            String mOption = MsgObj.GetMsgByName("OPTION");                              //取得操作信息
            if (mOption.equalsIgnoreCase("LOADFILE")) {                           //下面的代码为打开服务器数据库里的文件
               MsgObj.MsgTextClear();                                              //清除文本信息
               if (MsgObj.MsgFileLoad(doc.getDocpath())){			            //从文件夹调入文档
                    MsgObj.SetMsgByName("STATUS", "打开成功!");                       //设置状态信息
                    MsgObj.MsgError("");                                              //清除错误信息
                }
                else {
                    MsgObj.MsgError("打开失败!");                                     //设置错误信息
                }
            }
        }
        MsgObj.Send(this.getResponse());                                                    //8.1.0.2新版后台类新增的功能接口，返回信息包数据
        return null;
    }
    private void outSwf(File file)throws Exception{
        this.getResponse().setContentType("application/x-shockwave-flash");
        OutputStream os = this.getResponse().getOutputStream(); // 页面输出流，jsp/servlet都可以
        InputStream is = new FileInputStream(file); // 文件输入流
        byte[] bs = new byte[BUFFER_SIZE];  // 读取缓冲区
        int len;
        while((len=is.read(bs))!=-1){ // 循环读取
            os.write(bs,0,len); // 写入到输出流
        }
        is.close();  // 关闭
        os.close(); // 关闭
    }
    public String uploadFile() throws IOException {
        String extName = "";//扩展名
        String newFileName = "";//新文件名
//        String nowTime = new SimpleDateFormat("yyyymmddHHmmss").format(new Date());//当前时间
//        String savePath = ServletActionContext.getRequest().getRealPath("");
//        savePath = savePath + "/uploads/";

        HttpServletResponse response = getResponse();
        response.setCharacterEncoding("utf-8");

        //获取扩展名
        if (archiveFileName.lastIndexOf(".") >= 0) {
            extName = archiveFileName.substring(archiveFileName.lastIndexOf("."));
        }
        //生成新文件名
        String uuid = UUID.randomUUID().toString();
        newFileName = uuid + extName;

//        archive.renameTo(new File(savePath + newFileName));
        SysDocserver docserver;
        UploadVo vo = (UploadVo) getHttpSession().getAttribute("uploadVo");
        if (null == vo) {
            SysDocserverExample example = new SysDocserverExample();
            example.createCriteria().andServerstateEqualTo(1);
            List<SysDocserver> docserverList = docserverService.selectByWhereNotPage(example);
            docserver = docserverList.get(0);
            UploadVo uploadVo = new UploadVo();
            uploadVo.setDocserver(docserver);
            getHttpSession().setAttribute("uploadVo", uploadVo);
        }
        else {
            docserver = vo.getDocserver();
        }

        if ("FTP".equals(docserver.getServertype())) {
            FtpUtil util = new FtpUtil();
            util.connectServer(docserver.getServerip(), docserver.getServerport(),
                    docserver.getFtpuser(), docserver.getFtppassword(), docserver.getServerpath());

            FileInputStream s = new FileInputStream(archive);
            util.uploadFile(s, newFileName);
            util.closeServer();
        }
        else if ("LOCAL".equals(docserver.getServertype())) {
            String serverPath = docserver.getServerpath();
            String savePath = docserver.getServerpath();
            if (null == serverPath || "".equals(serverPath)) {
                response.getWriter().print(0);
                return null;
            }
            else {
                if (!serverPath.substring(serverPath.length()-1,serverPath.length()).equals("/")) {
                    savePath += "/";
                }
            }

            archive.renameTo(new File(savePath + newFileName));
        }


//        archive.renameTo(new File(savePath + archiveFileName));

//        response.getWriter().print(archiveFileName +"上传成功");
        response.getWriter().print(0);
        return null;
    }

    /**
     * 将原文件，拼接到目标文件dst
     * @param src
     * @param dst
     */
    private void cat(File src, File dst) {
        InputStream in = null;
        OutputStream out = null;
        try {
            if (dst.exists()) {
                out = new BufferedOutputStream(new FileOutputStream(dst, true),BUFFER_SIZE);
            } else {
                out = new BufferedOutputStream(new FileOutputStream(dst),BUFFER_SIZE);
            }
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);

            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public String downDoc() {
    	if (null == docId || "".equals(docId)) {
    		return ERROR;
    	}
    	//根据docid读取电子全文信息
    	doc = docService.selectByPrimaryKey(docId);
    	if (null == doc || "".equals(doc)) {
    		return ERROR;
    	}
    	//得到原文件名
    	docName = doc.getDocoldname();
    	//判断文件所属服务器
    	String serverid = doc.getDocserverid();
    	//得到所属服务器的信息
    	SysDocserver docServer = docserverService.selectByPrimaryKey(serverid);
    	if (null == docServer || "".equals(docServer)) {
    		return ERROR;
    	}
    	//判断服务器类型。根据不同类型，执行不同的操作
    	String serverType = docServer.getServertype();
    	if ("LOCAL".equals(serverType)) {
    		savePath = docServer.getServerpath();
    	}
    	return SUCCESS;
    }
    
    public InputStream getInputStream() throws FileNotFoundException {
//    	docName = "文档 阿斯 顿飞.doc";
//    	InputStream aa = ServletActionContext.getServletContext().getResourceAsStream("/aa.doc");
//    	FileInputStream aa = new FileInputStream("d:\\文档 阿斯 顿飞.doc");
    	String fileName = savePath + doc.getDocnewname();
    	FileInputStream aa = new FileInputStream(fileName);
    	return aa;
	}
    
    public String readDoc() {
//    	docId = "1f4fe3f4-c285-4600-884c-2fd57f2e0d3a";
    	if (null == docId || "".equals(docId)) {
    		return ERROR;
    	}
    	//根据docid读取电子全文信息
    	doc = docService.selectByPrimaryKey(docId);
    	if (null == doc || "".equals(doc)) {
    		return ERROR;
    	}
    	//得到原文件名
    	docName = doc.getDocoldname();
    	//判断文件所属服务器
    	String serverid = doc.getDocserverid();
    	//得到所属服务器的信息
    	SysDocserver docServer = docserverService.selectByPrimaryKey(serverid);
    	if (null == docServer || "".equals(docServer)) {
    		return ERROR;
    	}
    	//判断服务器类型。根据不同类型，执行不同的操作
    	String serverType = docServer.getServertype();
    	if ("LOCAL".equals(serverType)) {
    		savePath = docServer.getServerpath();
    	}
    	String docType = doc.getDoctype();
    	if ("XLS".equals(docType)) {
    		// contentType设定      
            contentType = "application/vnd.ms-excel;charset=utf-8";      
            // attachment表示网页会出现保存、打开对话框      
            docName = "inline; filename=" + docName;      
    	}
    	else if("DOC".equals(docType)) {
    		// contentType设定      
            contentType = "application/msword;charset=utf-8";      
            // attachment表示网页会出现保存、打开对话框      
            docName = "inline; filename=" + docName;    
    	}
    	else {
    		// contentType设定      
            contentType = "text/plain;charset=utf-8";      
            // attachment表示网页会出现保存、打开对话框      
            docName = "inline; filename=" + docName;   
    	}
    	return SUCCESS;
    }


    public File getArchive() {
        return archive;
    }

    public void setArchive(File archive) {
        this.archive = archive;
    }

    public String getArchiveFileName() {
        return archiveFileName;
    }

    public void setArchiveFileName(String archiveFileName) {
        this.archiveFileName = archiveFileName;
    }

    public void setDocserverService(IDocserverService docserverService) {
        this.docserverService = docserverService;
    }

    public void setDocService(IDocService docService) {
        this.docService = docService;
    }

    public String getTableid() {
        return tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }

    public String getSelectRowid() {
        return selectRowid;
    }

    public void setSelectRowid(String selectRowid) {
        this.selectRowid = selectRowid;
    }

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChunk() {
        return chunk;
    }

    public void setChunk(int chunk) {
        this.chunk = chunk;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

	public String getDocName() throws UnsupportedEncodingException {
		//文件名字转码，为了下载显示中文名不出现乱码
		docName = new String(docName.getBytes(),"ISO-8859-1");
		return docName;
	}

	public void setDocName(String docName) {
		
		this.docName = docName;
	}

	public SysDoc getDoc() {
		return doc;
	}

	public void setDoc(SysDoc doc) {
		this.doc = doc;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}



}

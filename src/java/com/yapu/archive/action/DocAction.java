package com.yapu.archive.action;

import com.google.gson.Gson;
import com.yapu.archive.entity.SysDoc;
import com.yapu.archive.entity.SysDocExample;
import com.yapu.archive.entity.SysDocserver;
import com.yapu.archive.entity.SysDocserverExample;
import com.yapu.archive.service.itf.IDocService;
import com.yapu.archive.service.itf.IDocserverService;
import com.yapu.archive.vo.UploadVo;
import com.yapu.system.common.BaseAction;
import com.yapu.system.util.CommonUtils;
import com.yapu.system.util.Coverter;
import com.yapu.system.util.FtpUtil;
import org.apache.struts2.ServletActionContext;

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

    private String tableid;
    private String selectRowid;
    private int chunks;
    private String name;
    private int chunk;
    private File file;
    private String savePath;

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
    public String upload() throws Exception {
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
            dstFile.renameTo(new File(contextPath+newName));
            SysDoc doc = new SysDoc();
            doc.setDocid(docId);
            doc.setDocnewname(newName);
            doc.setDoclength(String.valueOf(dstFile.getTotalSpace()));
            doc.setDocoldname(oldName);
            doc.setDocpath(contextPath+newName);
            doc.setDoctype(docType);
            doc.setCreater("上传者");
            doc.setCreatetime("");
            doc.setDocserverid("");
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



}

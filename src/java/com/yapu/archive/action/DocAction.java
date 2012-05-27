package com.yapu.archive.action;

import com.yapu.archive.entity.SysDoc;
import com.yapu.archive.entity.SysDocExample;
import com.yapu.archive.entity.SysDocserver;
import com.yapu.archive.entity.SysDocserverExample;
import com.yapu.archive.service.itf.IDocService;
import com.yapu.archive.service.itf.IDocserverService;
import com.yapu.archive.vo.UploadVo;
import com.yapu.system.common.BaseAction;
import com.yapu.system.util.FtpUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
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

    private IDocserverService docserverService;
    private IDocService docService;

    private String tableid;
    private String selectRowid;
    
    public String showDocListTab() {
        return SUCCESS;
    }

    public String list() throws IOException {

        //如果得不到字段id，返回空字符
        if (null == selectRowid || "".equals(selectRowid)) {
            return null;
        }
        HttpServletResponse response = getResponse();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        //获得templetfieldid的code列表
        SysDocExample example = new SysDocExample();
        SysDocExample.Criteria criteria = example.createCriteria();
        criteria.andFileidEqualTo(selectRowid);
        criteria.andTableidEqualTo(tableid);

    		List<SysDoc> docList = docService.selectByWhereNotPage(example);
    		StringBuffer sb = new StringBuffer();
    		sb.append("{\"total\":").append(docList.size()).append(",\"rows\":[");
    		String resultStr = "";
    		if(null!=docList && docList.size()>0){

    			for (SysDoc doc : docList) {
    				sb.append("{");
    				sb.append("\"docid\":\""+doc.getDocid()+"\",");
    				sb.append("\"docserverid\":\""+doc.getDocserverid()+"\",");
    				sb.append("\"docoldname\":\""+doc.getDocoldname()+"\",");
    				sb.append("\"docnewname\":"+doc.getDocnewname()+",");
    				sb.append("\"doctype\":\""+doc.getDoctype()+"\"");
                    sb.append("\"doclength\":\""+doc.getDoclength()+"\"");
                    sb.append("\"docpath\":\""+doc.getDocpath()+"\"");
                    sb.append("\"creater\":\""+doc.getCreater()+"\"");
                    sb.append("\"createtime\":\""+doc.getCreatetime()+"\"");
                    sb.append("\"fileid\":\""+doc.getFileid()+"\"");
                    sb.append("\"tableid\":\""+doc.getTableid()+"\"");
    				sb.append("},");
    			}
    			resultStr = sb.substring(0,sb.length()-1);

    		}
    		else {
    			resultStr = sb.toString();
    		}
    		resultStr += "]}";
    		out.write(resultStr);
    		return null;
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
            getHttpSession().setAttribute("uploadVo",uploadVo);
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
}

package com.yapu.archive.action;

import com.yapu.archive.entity.*;
import com.yapu.archive.service.itf.IDynamicService;
import com.yapu.archive.service.itf.ITreeService;
import com.yapu.archive.vo.UploadVo;
import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.util.CommonUtils;
import com.yapu.system.util.Constants;
import com.yapu.system.util.FtpUtil;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-21
 * Time: 下午5:43
 * To change this template use File | Settings | File Templates.
 */
public class MediaAction extends BaseAction {

    private String savePath;
    private String name;
    private int chunk;
    private int chunks;
    private File file;
    private static final int BUFFER_SIZE = 2 * 1024;


    private String parentid;
    private String treeid;
    private String tableType;

    private ITreeService treeService;
    private IDynamicService dynamicService;

    /**
     * 上次多媒体文件
     * @return
     */
    public String uploadMedia() {
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
            String docExt = "";//扩展名
            String oldName = dstFile.getName();
            String docid = UUID.randomUUID().toString();

            if (oldName.lastIndexOf(".") >= 0) {
                docExt = oldName.substring(oldName.lastIndexOf("."));
            }
            String newName = docid+docExt;
//            String newName = oldName;
            File newFile =new File(contextPath+newName);
            dstFile.renameTo(newFile);

            String savePath = ServletActionContext.getServletContext().getRealPath("/webpage/media")+ File.separator;
            String filePath = savePath + newName;
            newFile.renameTo(new File(filePath));

            //增加多媒体文件级表内容
            List<SysTable> tableList = treeService.getTreeOfTable(treeid);
            String tableName = "";
            //得到表名
            for (int i=0;i<tableList.size();i++) {
                if (tableList.get(i).getTabletype().equals(tableType)) {
                    tableName = tableList.get(i).getTablename();
                    break;
                }
            }
            List<SysTempletfield> fieldList = treeService.getTreeOfTempletfield(treeid, tableType);
            HashMap map = new HashMap();
            map.put("id",UUID.randomUUID().toString());
            map.put("treeid",treeid);
            map.put("isdoc","0");
            map.put("parentid",parentid);
            map.put("slt",newName);
            map.put("imagepath","");
            for (SysTempletfield field :fieldList) {
                if (field.getSort() > 0) {
                    if ("INT".equals(field.getFieldtype())) {
                        map.put(field.getEnglishname().toLowerCase(),"0");
                    }
                    else {
                        map.put(field.getEnglishname().toLowerCase(),"");
                    }
                }
            }
            List archiveList = new ArrayList();
            archiveList.add(map);
            boolean b = dynamicService.insert(archiveList,tableName,fieldList);
        }
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

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
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

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTreeid() {
        return treeid;
    }

    public void setTreeid(String treeid) {
        this.treeid = treeid;
    }

    public void setTreeService(ITreeService treeService) {
        this.treeService = treeService;
    }

    public void setDynamicService(IDynamicService dynamicService) {
        this.dynamicService = dynamicService;
    }
}

package com.yapu.document.commands;

import com.yapu.archive.entity.SysDoc;
import com.yapu.archive.entity.SysDocExample;
import com.yapu.archive.service.itf.IDocService;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.util.BeanFactory;
import com.yapu.system.util.CommonUtils;
import com.yapu.system.util.Constants;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * This class allows overriding default behaviors in AbstractCommand.<br>
 * You can edit it as you want, it may be useful to override all commands at once...
 *
 * @author Antoine Walter (www.anw.fr)
 * @date 29 aug. 2011
 * @version $Id$
 * @license BSD
 */
public abstract class AbstractCommandOverride extends AbstractCommand {
    private IDocService docService = (IDocService) BeanFactory.getBean("docService");
	public AbstractCommandOverride() {
		super();
	}
    protected void rmInfor(File file){
        SysDocExample where = new SysDocExample();
        where.createCriteria().andDocpathEqualTo(file.getAbsolutePath());
        List<SysDoc> docList = docService.selectByWhereNotPage(where);
        if(null!=docList&&docList.size()>0){
            Iterator i = docList.iterator();
            while (i.hasNext()){
                docService.deleteDoc((SysDoc)i.next());
            }
        }


    }
    protected void updateInfor(File targetFile ,File futureFile){
        SysDocExample where = new SysDocExample();
        where.createCriteria().andDocpathEqualTo(targetFile.getAbsolutePath());
        List<SysDoc> docList = docService.selectByWhereNotPage(where);
        if(null!=docList&&docList.size()>0){
            Iterator i = docList.iterator();
            while (i.hasNext()){
                SysDoc doc =(SysDoc)i.next();
                doc.setDocnewname(futureFile.getName());
                doc.setDocpath(futureFile.getAbsolutePath());
                doc.setDoctype(futureFile.getName().substring(futureFile.getName().lastIndexOf(".")));
                docService.updateDoc(doc);
            }
        }
    }
    protected void saveInfor(File file){
        String fileName = file.getName();
        SysAccount sessionAccount = (SysAccount)getRequest().getSession().getAttribute(Constants.user_in_session);
        SysDoc doc = new SysDoc();
        doc.setDocid(_hash(file));
        doc.setDocpath(file.getAbsolutePath());
        doc.setCreater(null!=sessionAccount?sessionAccount.getAccountcode():null);
        doc.setCreatetime(CommonUtils.getTimeStamp());
        doc.setDoclength(CommonUtils.formatFileSize(file.length()));
        doc.setDocnewname(fileName);
        doc.setDocoldname(fileName);
        doc.setDocserverid("local");
        doc.setDoctype(fileName.substring(fileName.lastIndexOf(".")));
        docService.insertDoc(doc);
    }

}

package com.yapu.system.util;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;

/**
 */
public class DayQuartzJob implements Job {

    public DayQuartzJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        FilePathMgr filePathMgr = new FilePathMgr();
        File docDir = new File(filePathMgr.getUploadDir());
        String[] docFiles = docDir.list();
        if(null!=docFiles&&docFiles.length>0){
            for(int i=0 ;i<docFiles.length;i++ ){
                String filename = docFiles[i];
                if(filename.toLowerCase().endsWith(".pdf")){
                    File swfFile = new File(filePathMgr.getSwfDocDir()+filename+".swf");
                    if(!swfFile.exists()) Coverter.PdfToSwf(new File(docDir+filename));
                }else if(CommonUtils.isPdfPrintType(filename)){
                    File swfFile = new File(filePathMgr.getSwfDocDir()+filename+".pdf.swf");
                    if(!swfFile.exists()) {
                        File pdf = Coverter.toPdf(new File(docDir+filename));
                        Coverter.PdfToSwf(pdf);
                    }
                }
            }
        }
    }
}

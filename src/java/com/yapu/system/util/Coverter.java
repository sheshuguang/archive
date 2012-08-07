package com.yapu.system.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Coverter {
	   public static File toPdf(File infile){
		   String filePath=infile.getPath();		  
		   String fileName= infile.getName();		   
			FilePathMgr filePathMgr=new FilePathMgr();
			String fileoutParth=filePathMgr.getTempDir()+fileName+".pdf";
		    SysProcess sp = new SysProcess();
			List commline = new ArrayList();
			commline.add(filePathMgr.getFlashPrinter());
			commline.add(filePath);
			commline.add("-o");
			commline.add(fileoutParth);
			sp.runCommandLine(commline);
			File file = new File(fileoutParth);
			while(!file.exists()){
				System.out.println("等待pdf生成");
			}
	    	return file;
	    }
	 
	    public static File PdfToSwf(File infile){
	    	String fileName=infile.getName();			
	    	SysProcess sp = new SysProcess();
	    	FilePathMgr filePathMgr=new FilePathMgr();
	    	String fileOutPath=filePathMgr.getSwfDocDir()+fileName+".swf";
	    	sp.runPdf2Swfexe(infile.getPath(), fileOutPath);
	    	File file = new File(fileOutPath);
	    	while(!file.exists()){
				System.out.println("等待swf生成");
			}
	    	return file;
	    }
	   
}

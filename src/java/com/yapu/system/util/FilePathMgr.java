package com.yapu.system.util;

import java.io.File;

public class FilePathMgr {
	private String ctxDir;	
	public FilePathMgr(){		
		this.ctxDir = EvnManager.getContexdDir();		
	}	
	public String getFlashPaperCreatorRoot(){		
		
		return this.getToolsRootDir()+"FlashPaperCreator"+File.separatorChar;		
	}
	public String getFlashPaperCreatorIntDir(){		
		return this.getFlashPaperCreatorRoot()+"init"+File.separatorChar;		
	}
	public String getFrontDir(){		
		return this.getToolsRootDir()+"xpdf-chinese-simplified"+File.separatorChar;		
	}
	public String getToolsRootDir(){
		return ctxDir+"WEB-INF"+File.separatorChar+"tools"+File.separatorChar;
		
	}
	public String getSwfDocDir(){
		return ctxDir+"WEB-INF"+File.separatorChar+"files"+File.separatorChar+"swfdoc"+File.separatorChar;
	}
	public String getPdf2Swfexe(){
		return this.getToolsRootDir()+"swftools"+File.separatorChar+"pdf2swf.exe";
	}
	public String getFlashPrinter(){
		return this.getFlashPaperCreatorRoot()+File.separatorChar+"FlashPrinter.exe";
	}
	public String getTempDir(){
		return ctxDir+"WEB-INF"+File.separatorChar+"files"+File.separatorChar+"temp"+File.separatorChar;
	}
	public String getUploadDir(){		
		return  ctxDir +  "WEB-INF"+ File.separatorChar + "files" + File.separatorChar + "upload" + File.separatorChar;
	}	
	public String getDbParth(){
		return  ctxDir +  "WEB-INF"+ File.separatorChar + "db" + File.separatorChar + "filedb";
	}
	public String getCtxDir() {
		return ctxDir;
	}
	public void setCtxDir(String ctxDir) {
		this.ctxDir = ctxDir;
	}

}

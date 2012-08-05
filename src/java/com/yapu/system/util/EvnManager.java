package com.yapu.system.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class EvnManager {
	private static String contexdDir="";
	public static String getContexdDir(){
		return EvnManager.contexdDir;
	}
	public static void setContexdDir(String contexdDir){
		EvnManager.contexdDir=contexdDir;
	}	
	public static void initFlashPaperCreator(){
		FilePathMgr fpm = new FilePathMgr();		
		String initbatDir = fpm.getFlashPaperCreatorIntDir();		
		SysProcess sysProcess = new SysProcess();
		List commandList = new ArrayList();
		commandList.add("cmd");
		commandList.add("/c");
		commandList.add("start");
		commandList.add("/min");
		commandList.add("/D"+initbatDir);
		commandList.add("init.bat");		
		sysProcess.runCommandLine(commandList);
	}
	public static void initPdf2swf(){
		FilePathMgr fpm = new FilePathMgr();
		String fontDir =fpm.getFrontDir();		
		List txt = new ArrayList();
		txt.add("cidToUnicode	Adobe-GB1	"+fontDir+"Adobe-GB1.cidToUnicode");
		txt.add("unicodeMap	ISO-2022-CN	"+fontDir+"ISO-2022-CN.unicodeMap");
		txt.add("unicodeMap	EUC-CN		"+fontDir+"EUC-CN.unicodeMap");
		txt.add("unicodeMap	GBK		"+fontDir+"GBK.unicodeMap");
		txt.add("cMapDir		Adobe-GB1	"+fontDir+"CMap");
		txt.add("toUnicodeDir			"+fontDir+"CMap");		
		File file =new File(fontDir+"add-to-xpdfrc");	
		try {
			FileUtils.writeLines(file, txt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

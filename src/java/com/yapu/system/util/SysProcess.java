package com.yapu.system.util;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SysProcess {	    
	    public boolean excBat(String cmd){           
            try {
                    Process child = Runtime.getRuntime().exec(cmd);
                    InputStream in = child.getInputStream();
                    int c;
                    while ((c = in.read()) != -1) {
                            System.out.print(c);//如果你不需要看输出，这行可以注销掉
                    }
                    in.close();
                    try {
                            child.waitFor();
                    } catch (InterruptedException e) {                            
                            e.printStackTrace();
                    }
                    
            } catch (IOException e) {
                    e.printStackTrace();
            }
	    	return false;
	    }
	    public void runPdf2Swfexe(String fileInPath,String fileOutPath){
	    	Process process = null;
			try {
			FilePathMgr filePathMgr=new FilePathMgr();
			 StringBuilder message = new StringBuilder();
			 List<String> commandList = new ArrayList<String>();			 
	          commandList.add(filePathMgr.getPdf2Swfexe());
	          commandList.add(fileInPath);
	          commandList.add("-o");
	          commandList.add(fileOutPath);
	          commandList.add("-T");
	          commandList.add("9");
	          commandList.add("-s");
	          commandList.add("languagedir="+filePathMgr.getFrontDir());          
	          
	          ProcessBuilder builder = new ProcessBuilder(commandList);
              builder.redirectErrorStream(true);
              //builder.directory();
              process = builder.start();
              BufferedReader in = new BufferedReader(
              new InputStreamReader(process.getInputStream()));
              String line = null;
              while ((line = in.readLine()) != null) {
                  System.out.println(line);
                  message.append(line + System.getProperty("line.separator"));
              }      
              process.waitFor();
			 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
                if (process != null)
                    process.destroy();
            }
            process.destroy();            
            System.out.println("process.runPdf2Swfexe:" +process.exitValue()); 	
	    	
	    }
	    public void runCommandLine(List commandList){
	    	Process process = null;
			try {
			  StringBuilder message = new StringBuilder();     
	          ProcessBuilder builder = new ProcessBuilder(commandList);
              builder.redirectErrorStream(true);
              //builder.directory();
              process = builder.start();
              BufferedReader in = new BufferedReader(
              new InputStreamReader(process.getInputStream()));
              String line = null;
              while ((line = in.readLine()) != null) {
                  System.out.println(line);
                  message.append(line + System.getProperty("line.separator"));
              }      
              process.waitFor();
			 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
                if (process != null)
                    process.destroy();
            }
            process.destroy();            
            System.out.println("process.runCommandLine:" +process.exitValue()); 	
	    	
	    }
		public void initFlashPaperCreator() {
			try {
			FilePathMgr fpm = new FilePathMgr();
			String fpcroot = fpm.getFlashPaperCreatorRoot();		
			
			File srcFile = new File(fpcroot+"init"+File.separatorChar+"license.inf");
			File destFile = new File(fpcroot+"license.inf");
            FileOperate.moveFile(srcFile, destFile);
			
			runDll32(fpcroot);
			//regsvr32(fpcroot+"Flash.ocx");     注意版本有冲突
            regsvr32(fpcroot+"Flash10e.ocx");
			regsvr32(fpcroot+"OfficePrintAddIn.dll");
			regsvr32(fpcroot+"FlashPaperContextMenu.dll");  
			
			srcFile = new File(fpcroot+"license.inf");
			destFile = new File(fpcroot+"init"+File.separatorChar+"license.inf");
            FileOperate.moveFile(srcFile, destFile);
			
			srcFile = new File(fpcroot+"flashpaperprinterdrv2.dll");
			destFile = new File(fpcroot+"init"+File.separatorChar+"flashpaperprinterdrv2.dll");
            FileOperate.moveFile(srcFile, destFile);
			
			srcFile = new File(fpcroot+"flashpaperprinterui2.dll");
			destFile = new File(fpcroot+"init"+File.separatorChar+"flashpaperprinterui2.dll");
            FileOperate.moveFile(srcFile, destFile);
			
			fpdriversetup(fpcroot+"fpdriversetup");
			
			srcFile = new File(fpcroot+"init"+File.separatorChar+"flashpaperprinterdrv2.dll");	
			destFile= new File(fpcroot+"flashpaperprinterdrv2.dll");
            FileOperate.moveFile(srcFile, destFile);
			
			srcFile = new File(fpcroot+"init"+File.separatorChar+"flashpaperprinterui2.dll");		
			destFile= new File(fpcroot+"flashpaperprinterui2.dll");
            FileOperate.moveFile(srcFile, destFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public void fpdriversetup(String fpdriversetuppath){

			
			Process process = null;
			try {			
			 StringBuilder message = new StringBuilder();
			 List<String> commandList = new ArrayList<String>();
	          commandList.add(fpdriversetuppath);
	          commandList.add("i");  
	          
	          ProcessBuilder builder = new ProcessBuilder(commandList);
              builder.redirectErrorStream(true);
              //builder.directory();
              process = builder.start();
              BufferedReader in = new BufferedReader(
              new InputStreamReader(process.getInputStream()));
              String line = null;
              while ((line = in.readLine()) != null) {
                  System.out.println(line);
                  message.append(line + System.getProperty("line.separator"));
              }      
              process.waitFor();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
                if (process != null)
                    process.destroy();
            }
            process.destroy();
            System.out.println("process.exitValue():" +process.exitValue());
		}
		
	    public void runDll32(String fpcroot){
			
			Process process = null;
			try {			
			 StringBuilder message = new StringBuilder();
			 List<String> commandList = new ArrayList<String>();
	          commandList.add("RunDll32");
	          commandList.add("advpack.dll,LaunchINFSection");
	          commandList.add(fpcroot+"license.inf,DefaultInstall");
	          
	          ProcessBuilder builder = new ProcessBuilder(commandList);
              builder.redirectErrorStream(true);
              //builder.directory();
              process = builder.start();
              BufferedReader in = new BufferedReader(
              new InputStreamReader(process.getInputStream()));
              String line = null;
              while ((line = in.readLine()) != null) {
                  System.out.println(line);
                  message.append(line + System.getProperty("line.separator"));
              }      
              process.waitFor();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
                if (process != null)
                    process.destroy();
            }
            process.destroy();
            System.out.println("process.exitValue():" +process.exitValue());
	    	
	    }
	    public void regsvr32(String dllPath){
	    	Process process = null;
			try {			
			 StringBuilder message = new StringBuilder();
			 List<String> commandList = new ArrayList<String>();
	          commandList.add("regsvr32");
	          commandList.add("/s");
	          commandList.add(dllPath);
	          
	          ProcessBuilder builder = new ProcessBuilder(commandList);
              builder.redirectErrorStream(true);
              //builder.directory();
              process = builder.start();
              BufferedReader in = new BufferedReader(
              new InputStreamReader(process.getInputStream()));
              String line = null;
              while ((line = in.readLine()) != null) {
                  System.out.println(line);
                  message.append(line + System.getProperty("line.separator"));
              }      
              process.waitFor();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
                if (process != null)
                    process.destroy();
            }
            process.destroy();
            System.out.println("process.exitValue():" +process.exitValue());
	    	
	    }

}
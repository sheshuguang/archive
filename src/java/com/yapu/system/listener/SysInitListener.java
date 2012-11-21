package com.yapu.system.listener;

import com.yapu.system.util.CronTriggerRunner;
import com.yapu.system.util.EvnManager;
import org.quartz.SchedulerException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class SysInitListener implements ServletContextListener {

	public SysInitListener() {
	}
   	public void contextInitialized(ServletContextEvent event) {
		String ctxDir = event.getServletContext().getRealPath(String.valueOf(File.separatorChar));	
		EvnManager.setContexdDir(ctxDir);
        //初始化pdf虚拟打印机
		//EvnManager.initFlashPaperCreator();
       //初始化pdf转swf
		//EvnManager.initPdf2swf();
       //启动定时装置
        CronTriggerRunner cronRunner = new CronTriggerRunner();
           try {
               cronRunner.task();
           } catch (SchedulerException e) {
                e.printStackTrace();
           }
       }
	public void contextDestroyed(ServletContextEvent event) {

	}
}
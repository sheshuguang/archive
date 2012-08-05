package com.yapu.system.listener;

import com.yapu.system.util.EvnManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class SysInitListener implements ServletContextListener {

	public SysInitListener() {
	
	}

	public void contextInitialized(ServletContextEvent event) {		
		String ctxDir = event.getServletContext().getRealPath(String.valueOf(File.separatorChar));	
		EvnManager.setContexdDir(ctxDir);
		EvnManager.initFlashPaperCreator();
		EvnManager.initPdf2swf();
	}
	public void contextDestroyed(ServletContextEvent event) {

	}
}
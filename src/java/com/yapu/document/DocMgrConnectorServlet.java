package com.yapu.document;


import org.apache.commons.lang3.StringUtils;
import com.yapu.document.config.AbstractConnectorConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author Özkan Pakdil
 * @date 29 aug. 2011
 * @version $Id$
 * @license BSD
 */

/**
 * Sample of custom servlet implementation.
 */
@SuppressWarnings("serial")
public class DocMgrConnectorServlet extends AbstractConnectorServlet {

	public static String SHARED_DOCS = "Shared docs";
	public static String THUMBNAIL = "/thumbnailer?p=";
	public static String HOME_SHARED_DOCS = "/tmp/shared_docs";
	public static String REALOBJECTURL = "/servlet/virtualproxy";

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if(!StringUtils.isBlank(getServletContext().getInitParameter("HOME_SHARED_DOCS"))){
			HOME_SHARED_DOCS = getServletContext().getInitParameter("HOME_SHARED_DOCS");
			File f=new File(HOME_SHARED_DOCS);
			if(!f.exists()){
				f.mkdirs();
			}
		}
		if(!StringUtils.isBlank(getServletContext().getInitParameter("THUMBNAIL")))
			THUMBNAIL = getServletContext().getInitParameter("THUMBNAIL");
		if(!StringUtils.isBlank(getServletContext().getInitParameter("SHARED_DOCS")))
			SHARED_DOCS = getServletContext().getInitParameter("SHARED_DOCS");
		if(!StringUtils.isBlank(getServletContext().getInitParameter("REALOBJECTURL")))
			REALOBJECTURL = getServletContext().getInitParameter("REALOBJECTURL");
	}

	@Override
	protected AbstractConnectorConfig prepareConfig(HttpServletRequest request) {
		// here we could use various configs based on request URL/cookies...
		return new DocMgrConnectorConfig();
	}

}

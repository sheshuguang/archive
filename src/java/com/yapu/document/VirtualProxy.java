package com.yapu.document;


import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * return real file to any request.
 */
public class VirtualProxy extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getParameter("p");
		
		// security
		path = FilenameUtils.normalize(path);

		File file = new File(DocMgrConnectorServlet.HOME_SHARED_DOCS, path);
		
		
		FileInputStream fileIn = new FileInputStream(file);
		ServletOutputStream out = response.getOutputStream();

		byte[] outputByte = new byte[4096];
		while (fileIn.read(outputByte, 0, 4096) != -1) {
			out.write(outputByte, 0, 4096);
		}
		fileIn.close();
		out.flush();
		out.close();
	}
}

package com.yapu.document;


import com.yapu.document.config.AbstractConnectorConfig;
import com.yapu.document.fs.DiskFsImpl;
import com.yapu.document.fs.IFsImpl;

import java.io.File;


public class DocMgrConnectorConfig extends AbstractConnectorConfig {

	private DiskFsImpl fsImpl;

	public DocMgrConnectorConfig() {
		fsImpl = new DiskFsImpl();
	}

	@Override
	public IFsImpl getFs() {
		return fsImpl;
	}

	@Override
	public String getRoot() {
		return DocMgrConnectorServlet.HOME_SHARED_DOCS;
	}

	@Override
	public String getRootUrl() {
		return DocMgrConnectorServlet.REALOBJECTURL;
	}
	
	@Override
	public String getFileUrl(File path) {
		return getRootUrl() + "/" + getRelativePath(path);
	}

	@Override
	public String rootAliasOrBaseName() {
		return DocMgrConnectorServlet.SHARED_DOCS;
	}
	
	@Override
	public String getThumbnailUrl(File path) {
		return DocMgrConnectorServlet.THUMBNAIL + getRelativePath(path);
	}
	
	@Override
	public boolean hasThumbnail(File path) {
		String mimeType = getMime(path);
		return mimeType.contains("image");
	}
}

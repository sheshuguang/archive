package com.yapu.system.action;

import com.yapu.system.common.BaseAction;
import com.yapu.system.util.Logger;

public class DispatchAction extends BaseAction {
	
	private Logger log = new Logger(DispatchAction.class);
	
	private String page;
	
	public String dispatch() throws Exception {
		log.info(getPage());
		if (getPage() == null)
			return ERROR;
		return SUCCESS;
	}
	

	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	
	
}

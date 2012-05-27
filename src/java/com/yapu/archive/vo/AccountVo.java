package com.yapu.archive.vo;

import java.util.HashMap;
import java.util.List;
import com.yapu.system.entity.SysAccount;

/**
 * 用户的vo，因用户有基础信息、账户信息还有权限，所以把这些信息统一起来，存入vo，返回给页面
 * @author wangf
 * @date	2010-02-04
 *
 */

public class AccountVo {
	//声明账户属性
	private SysAccount account;
	private HashMap userMap;

	public SysAccount getAccount() {
		return account;
	}
	public void setAccount(SysAccount account) {
		this.account = account;
	}
	public HashMap getUserMap() {
		return userMap;
	}
	public void setUserMap(HashMap userMap) {
		this.userMap = userMap;
	}
	
}

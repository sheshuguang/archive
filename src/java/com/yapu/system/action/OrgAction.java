package com.yapu.system.action;

/**
 * 帐户组相关操作
 * 2011-08-18
 * @author wangf
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.yapu.archive.entity.SysOrgTree;
import com.yapu.archive.entity.SysTree;
import com.yapu.archive.entity.SysTreeExample;
import com.yapu.archive.service.itf.ITreeService;
import com.yapu.publics.service.PublicAccountService;
import com.yapu.publics.service.PublicOrgService;
import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysFunction;
import com.yapu.system.entity.SysFunctionExample;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.entity.SysOrgExample;
import com.yapu.system.service.itf.IOrgService;

public class OrgAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;

	private IOrgService orgService;
	private ITreeService treeService;
	
	private String nodeId;
	private String orgid;
	private String orgname;
	private String parentid;
	
	private String newParentid;
	
	/**
	 * 读取指定节点下的组，以树节点形式返回json。
	 * @return
	 */
	public String loadOrgTreeData() throws IOException {
		
		PrintWriter out  = this.getPrintWriter();
		
		//获得父节点为nodeId的子节点
		SysOrgExample example = new SysOrgExample();
		example.createCriteria().andParentidEqualTo(nodeId);
		List<SysOrg> orgs = orgService.selectByWhereNotPage(example);

		List<HashMap<String, String>> temp = new ArrayList<HashMap<String, String>>();
		for (SysOrg org :orgs) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", org.getOrgid());
			map.put("text", org.getOrgname());
			map.put("iconCls", "");
			map.put("state", "closed");
			temp.add(map);
		}
		Gson gson = new Gson();
        out.write(gson.toJson(temp));
		return null;
	}
	/**
	 * 新建帐户组
	 * @return
	 * @throws IOException
	 */
	public String addOrg() throws IOException {
		String result =	"failure";
		
		PrintWriter out  = this.getPrintWriter();
		
		if (orgname == null) {
			out.write(result);
			return null;
		}
		SysOrg org = new SysOrg();
		
		org.setOrgid(orgid);
		org.setOrgname(orgname);
		org.setParentid(parentid);
		org.setOrgorder(1);
		if(orgService.insertOrg(org)){
			result="success";
		}
		out.write(result);
		return null;
	}
	/**
	 * 更新帐户组
	 * @return
	 */
	public String updateOrg() throws IOException {
		String result = "failure";
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		if (null != orgid && !"".equals(orgid)) {
			SysOrg org = orgService.selectByPrimaryKey(orgid);
			org.setOrgname(orgname);
			if (orgService.updateOrg(org) > 0) {
				result = "success";
			}
		}
		
		out.write(result);
		return null;
	}
	/**
	 * 删除账户组
	 * @return
	 */
	public String deleteOrg() throws IOException {
		String result =	"failure";
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		if (null != orgid && !"".equals(orgid)) {
			int num = orgService.deleteOrg(orgid);
			if (num >0) {
				result = "success";
			}
		}
		out.write(result);
		return null;

	}
	
	/**
	 * 移动组到新的组下
	 * @return
	 */
	public String moveOrg() throws IOException {
		String result = "failure";
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		if (null != orgid && !"".equals(orgid)) {
			//判断
			//获得父节点为orgid的子节点
			SysOrgExample example = new SysOrgExample();
			example.createCriteria().andParentidEqualTo(nodeId);
			List<SysOrg> childOrgsList = orgService.selectByWhereNotPage(example);
			if (childOrgsList.size() > 0) {
				for (SysOrg sysOrg : childOrgsList) {
					if (sysOrg.getOrgid().equals(newParentid)) {
						result = "error";
						out.write(result);
						return null;
					}
				}
			}
			SysOrg org = new SysOrg();
			org.setOrgid(orgid);
			org.setParentid(newParentid);
			int num = orgService.updateOrg(org);
			if (num > 0) {
				result = "success";
			}
		}
		out.write(result);
		return null;
	}
	
	public String getOrgTree() throws IOException {
		PrintWriter out = this.getPrintWriter();
		SysOrg org = new SysOrg();
		org.setOrgid(orgid);
		List<SysOrgTree> treeList = orgService.getOrgOfTree(org);
		Gson gson = new Gson();
		out.write(gson.toJson(treeList));
		return null;
		
	}
	
	/**
	 * 读取档案节点树，全部读取。分别为组和帐户
	 * @return
	 * @throws IOException 
	 */
	public String loadOrgOfTreeData() throws IOException {
		PrintWriter out  = this.getPrintWriter();
		
		SysOrg org = new SysOrg();
		org.setOrgid(orgid);
		List<SysOrgTree> treeList = orgService.getOrgOfTree(org);
		
//		StringBuilder resultStr = new StringBuilder();
//		//创建根节点
//		resultStr.append("[{\"id\":\"0\",\"text\":\"档案节点树\",\"iconCls\":\"\",\"state\":\"open\",\"children\":");
		List temp = new ArrayList();
		HashMap map = new HashMap();
		map.put("id", "0");
		map.put("text", "档案节点树");
		map.put("iconCls", "");
		map.put("state", "open");
		List list = getTreeJson("0",treeList);
		map.put("children", list);
		temp.add(map);
		Gson gson = new Gson();
		out.write(gson.toJson(temp));
		return null;
	}
	
	/**  
     * 无限递归获得tree的json字串  
     *   
     * @param parentId  
     *            父权限id 
     * @return  
     */  
    private List getTreeJson(String parentId,List<SysOrgTree> treeList)
    {
    	//得到节点
		SysTreeExample example = new SysTreeExample();
		example.createCriteria().andParentidEqualTo(parentId);
		List<SysTree> trees = treeService.selectByWhereNotPage(example);
		List list = new ArrayList();
		if(null!=trees && trees.size()>0){
			for(int i=0;i<trees.size();i++){
				HashMap temp = new HashMap();
				SysTree tree =(SysTree)trees.get(i);
				String ico = "";
				if ("0".equals(tree.getParentid())) {
					ico = "icon-book-open";
				}
				else if ("F".equals(tree.getTreetype())) {
					ico = "";
				}
				else {
					ico = "icon-page";
				}
				//判断该节点下是否有子节点
				example.clear();
				example.createCriteria().andParentidEqualTo(tree.getTreeid());
				if (treeService.selectByWhereNotPage(example).size() >0) {
					temp.put("id", tree.getTreeid());
					temp.put("text", tree.getTreename());
					temp.put("iconCls", ico);
					temp.put("state", "closed");
					temp.put("children", this.getTreeJson(tree.getTreeid(),treeList));
				}
				else {
					temp.put("id", tree.getTreeid());
					temp.put("text", tree.getTreename());
					temp.put("iconCls", ico);
					if (null != treeList) {
						for (int j=0;j<treeList.size();j++) {
							if (treeList.get(j).getTreeid().equals(tree.getTreeid())) {
								temp.put("checked", true);
							}
						}
					}
				}
				list.add(temp);
			}
		}
        
        return list;
    }
	
	public void setOrgService(IOrgService orgService) {
		this.orgService = orgService;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getNewParentid() {
		return newParentid;
	}
	public void setNewParentid(String newParentid) {
		this.newParentid = newParentid;
	}
	public void setTreeService(ITreeService treeService) {
		this.treeService = treeService;
	}
	
}

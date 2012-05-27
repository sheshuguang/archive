package com.yapu.system.action;

/**
 * 帐户组相关操作
 * 2011-08-18
 * @author wangf
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.entity.SysOrgExample;
import com.yapu.system.service.itf.IOrgService;

public class OrgAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;

	private IOrgService orgService;
	
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
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		StringBuilder resultStr = new StringBuilder();
		
		//获得父节点为nodeId的子节点
		SysOrgExample example = new SysOrgExample();
		example.createCriteria().andParentidEqualTo(nodeId);
		List<SysOrg> orgs = orgService.selectByWhereNotPage(example);

        resultStr.append("[");
		if(null!=orgs && orgs.size()>0){

			for(int i=0;i<orgs.size();i++){
				SysOrg temp =(SysOrg)orgs.get(i);
				resultStr.append("{");
				resultStr.append("\"id\": \""+temp.getOrgid()+"\", \"text\": \""+temp.getOrgname()+"\", \"iconCls\": \"\", \"state\": \"closed\"");
				resultStr.append("},");
			}
			resultStr.deleteCharAt(resultStr.length() - 1);

		}
        resultStr.append("]");
        out.write(resultStr.toString());
		return null;
	}
	/**
	 * 新建帐户组
	 * @return
	 * @throws IOException
	 */
	public String addOrg() throws IOException {
		String result =	"failure";
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
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
	
	

//	@DirectFormPostMethod
//	public String importExcel(Map<String,String> param,Map<String,FileItem> files) throws Exception {
//		assert param != null;
//		assert files != null;
//		try {
//			
//			FileItem f = files.get("uploadfile");
//			String fileName = f.getName();
//			jxl.Workbook rwb = Workbook.getWorkbook(f.getInputStream());
//			int a = rwb.getNumberOfSheets();
//			Sheet rs = rwb.getSheet(0);
//			int aaaa = rs.getRows();
//			System.out.println(String.valueOf(aaaa));
//			Cell c11 = rs.getCell(1, 1);
//			String strc11 = c11.getContents();
//			System.out.println(strc11);
//			rwb.close();
//			
////			File file = new File("D:\\aa.xls");
////			f.write(file);   //TODO 为什么这里写文件报错呢，Cannot write uploaded file to disk!  问阿佘这是为什么
//			
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		
////		files.get("uploadfile").write(file);
//		
////		BufferedInputStream bis=new BufferedInputStream(request.getInputStream());
////		BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream("e:\\test.txt",true));
////		PrintWriter out= getResponse().getWriter();
////		if(bis!=null){
////			int ch;
////			while((ch=bis.read())!=-1)
////			{
////				bos.write(ch);
////			}
////			bis.close();
////			bos.close();
////			out.write("上传成功");
////		}
////		out.write("上传失败");
//
//		return null;
//		
//	}

}

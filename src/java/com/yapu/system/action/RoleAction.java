package com.yapu.system.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yapu.system.common.BaseAction;
import com.yapu.system.entity.SysFunction;
import com.yapu.system.entity.SysFunctionExample;
import com.yapu.system.entity.SysRole;
import com.yapu.system.entity.SysRoleExample;
import com.yapu.system.service.itf.IFunctionService;
import com.yapu.system.service.itf.IRoleService;

public class RoleAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private IRoleService roleService;
	private IFunctionService functionService;
	
	private String par;
	private String roleid;
	private String functionids;
	
	/**
	 * 返回角色的json
	 * @return
	 */
	public String list() throws IOException {
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		SysRoleExample example = new SysRoleExample();
		List<SysRole> rolesList = roleService.selectByWhereNotPage(example);
		StringBuffer sb = new StringBuffer();
		sb.append("{\"total\":").append(rolesList.size()).append(",\"rows\":[");
		String resultStr = "";
		if(null!=rolesList && rolesList.size()>0){
			
			for (SysRole sysRole : rolesList) {
				sb.append("{");
				sb.append("\"roleid\":\""+sysRole.getRoleid()+"\",");
				sb.append("\"rolename\":\""+sysRole.getRolename()+"\",");
				sb.append("\"rolememo\":\""+sysRole.getRolememo()+"\"");
				sb.append("},");
			}
			resultStr = sb.substring(0,sb.length()-1);
			
		}
		else {
			resultStr = sb.toString();
		}
		resultStr += "]}";
		out.write(resultStr);
		return null;
	}
	
	public String save() throws IOException {
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		String result = "保存完毕。";
		Gson gson = new Gson();
		Map<String, List<SysRole>> roleMap = new HashMap<String, List<SysRole>>();
		try {
			roleMap = (Map)gson.fromJson(par, new TypeToken<Map<String, List<SysRole>>>(){}.getType());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//处理添加的角色
		List<SysRole> insertRoleList = roleMap.get("inserted");
		if (insertRoleList.size() > 0) {
			//循环保存添加
			for (SysRole sysRole : insertRoleList) {
				addRole(sysRole);
			}
		}
		//处理更新的
		List<SysRole> updateRoleList = roleMap.get("updated");
		if (updateRoleList.size() > 0) {
			//循环保存更新的
			for (SysRole sysRole : updateRoleList) {
				updateRole(sysRole);
			}
		}
		//处理删除帐户
		List<SysRole> delRoleList = roleMap.get("deleted");
		if (delRoleList.size() > 0) {
			//循环删除
			for (SysRole sysRole : delRoleList) {
				delRole(sysRole.getRoleid());
			}
		}
		out.write(result);
		return null;
	}
	/**
	 * 添加角色
	 * @param SysRole
	 * @return boolean
	 */
	public boolean addRole(SysRole role) {
		boolean result = false;
		if (role != null) {
//			role.setRoleid(UUID.randomUUID().toString());
			
			if(roleService.insertRole(role)){
				result=true;
			}
		}
		return result;
	}
	/**
	 * 更新
	 * @param SysRole
	 * @return
	 */
	private boolean updateRole(SysRole role) {
		boolean result = false;
		if (role != null) {
			if(roleService.updateRole(role) > 0){
				result = true;
			}
		}
		return result;
	}
	/**
	 * 删除
	 * @param 
	 * @return
	 */
	public boolean delRole(String roleid) {
		boolean result = false;
		if (null != roleid && !"".equals(roleid)) {
			int num = roleService.deleteRole(roleid);
			if (num >0) {
				result = true;
			}
		}
		return result;
	}
	
	
	/**
	 * 角色赋功能权树，带checkbox。
	 * @return
	 * @throws IOException 
	 */
	public String loadRoleFunctionTree() throws IOException {
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		SysRole role = new SysRole();
		role.setRoleid(roleid);
		
		//得到角色id对应的功能
		List<SysFunction> roleFunctionList = roleService.getRoleOfFunction(role);
		
		StringBuilder resultStr = new StringBuilder();
		//创建根节点
		resultStr.append("[{\"id\":\"0\",\"text\":\"系统功能\",\"iconCls\":\"icon_role_go\",\"state\":\"open\",\"children\":");
		String json = getTreeJson("0",roleFunctionList);
		resultStr.append(json).append("}]");
		out.write(resultStr.toString());
		return null;
	}
	
	/**  
     * 无限递归获得tree的json字串  
     *   
     * @param parentId  
     *            父权限id 
     * @return  
     */  
    private String getTreeJson(String parentId,List<SysFunction> roleFunctionList)
    {
    	String str ="";
    	//得到节点
		SysFunctionExample example = new SysFunctionExample();
		example.createCriteria().andFunparentEqualTo(parentId);
		List<SysFunction> functions = functionService.selectByWhereNotPage(example);
		
		if(null!=functions && functions.size()>0){
			str = "[";
			for(int i=0;i<functions.size();i++){
				SysFunction function =(SysFunction)functions.get(i);
				//判断该节点下是否有子节点
				example.clear();
				example.createCriteria().andFunparentEqualTo(function.getFunctionid());
				if (functionService.selectByWhereNotPage(example).size() >0) {
					str += "{\"id\":\"" + function.getFunctionid() + "\",\"text\":\"" + function.getFunchinesename() + "\",\"iconCls\":\"" + function.getFunicon() + "\", \"state\": \"closed\"";
//					for (int j=0;j<roleFunctionList.size();j++) {
//						if (roleFunctionList.get(j).getFunctionid().equals(function.getFunctionid())) {
//							str += ",\"checked\":true";
//						}
//					}
					str += ",\"children\":";
	                str += this.getTreeJson(function.getFunctionid(),roleFunctionList);
	                str += "}";
				}
				else {
					str += "{\"id\":\"" + function.getFunctionid() + "\",\"text\":\"" + function.getFunchinesename() + "\",\"iconCls\":\"" + function.getFunicon() + "\"";
					if (null != roleFunctionList) {
						for (int j=0;j<roleFunctionList.size();j++) {
							if (roleFunctionList.get(j).getFunctionid().equals(function.getFunctionid())) {
								str += ",\"checked\":true";
							}
						}
					}
					str += "}";
				}
				if (i < functions.size() - 1)
	            {
	                str += ",";
	            }   
			}
			str += " ]";
//			String str = resultStr.substring(0, resultStr.length() - 1);
			
		}
        
        return str;   
    }
    
	
	/**
	 * 保存角色对应的功能
	 * @return
	 */
	public String saveRoleFun() throws IOException {
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out  = response.getWriter();
		
		String result =	"failure";
		if (null != roleid && !"".equals(roleid)) {
			boolean boo = roleService.insertRoleOfFunction(roleid, functionids);
			if (boo) {
				result = "success";
			}
		}
		out.write(result);
		return null;
	}
	
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	public void setFunctionService(IFunctionService functionService) {
		this.functionService = functionService;
	}
	public String getPar() {
		return par;
	}
	public void setPar(String par) {
		this.par = par;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getFunctionids() {
		return functionids;
	}
	public void setFunctionids(String functionids) {
		this.functionids = functionids;
	}
}

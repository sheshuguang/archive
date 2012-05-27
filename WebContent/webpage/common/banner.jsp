<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<div style="background:#E0ECFF;padding:0px;width:100%;text-align:right;">
		<a href="javascript:void(0)" onClick="javascript:$(window.parent.document).find('#ifr').attr('src','index.html')" id="firstpage" class="easyui-linkbutton" plain="true" iconCls="icon-house">首页</a>
		<span id="ajaxDate"><img src="webpage/images/icons/loading.gif" />正在加载数据，请稍候……</span>
		<a href="#" onClick="quit()" class="easyui-linkbutton" plain="true" iconCls=icon-quit>退出</a>
	</div>
</body>
</html>
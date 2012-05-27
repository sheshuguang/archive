<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="webpage/css/style.css" />
	<link rel="stylesheet" type="text/css" href="webpage/images/images.css" />
	<link rel="stylesheet" type="text/css" href="webpage/js/jquery-easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="webpage/js/zebra-dialog/css/zebra_dialog.css" />
	<script type="text/javascript" src="webpage/js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="webpage/js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="webpage/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="webpage/js/zebra-dialog/js/zebra_dialog.js"></script>
	<script language="javascript">
		$(function () {
			//$('#ajaxDate').focus();
			var par = "";
			//par = { anticache : Math.floor(Math.random()*1000)}
			$.post("menu.action",par,function(data){
				$("#ajaxDate").html(data);
				//alert(data);
				$.parser.parse($('#ajaxDate'));
			});
		});
		function quit(){
            $.Zebra_Dialog('真的要退出系统吗? ', {
                'type':     'question',
                'title':    '系统提示',
                'buttons':  ['退出', '取消'],
                'onClose':  function(caption) {
                    if (caption == '退出') {
                    	window.location.href="webpage/common/logout.jsp";
                    }
                }
            });
			//if(confirm("真的要退出系统吗?")){
			//	window.location.href="webpage/common/logout.jsp";
			//}
		}
	</script>
	<title></title>
</head>
<body class="easyui-layout">
	<div region="north"  border="false" split="false" style="height:72px;padding:0px;">
		
		<div id="desktopTitlebarWrapper">
			<div id="desktopTitlebar">
				<h1 class="applicationTitle">Mocha UI</h1>
				<h2 class="tagline">亚普档案管理系统 </h2>
				<div id="topNav">
					<ul class="menu-right">
						<li>欢迎您 <a href="#" onclick="MochaUI.notification('Do Something');return false;">${CURRENT_USER_IN_SESSION.accountcode }</a>.</li>
						<li><a href="#" onclick="quit()">退出</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div style="background:#f2f2f2;padding:0px;width:100%;text-align:left;"><span >&nbsp;&nbsp;</span>
			<a href="javascript:void(0)" onClick="javascript:$(window.parent.document).find('#ifr').attr('src','index.html')" id="firstpage" class="easyui-linkbutton" plain="true" iconCls="icon-house">首页</a>
			<span id="ajaxDate"><img src="webpage/images/icons/loading.gif" />正在加载数据，请稍候……</span>
		</div>
		
	</div>
	<div region="center" style="overflow:hidden;"   border="false" >
		<iframe id="ifr" name="ifr" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" src="index.html"></iframe>
	</div>
	<div region="south" border="false" id="desktopFooter">
		&copy; 2011-2015 <a target="_blank" href="#">亚普软件（北京）有限公司</a> - <a id="licenseLink" href="#">www.upsoft.com</a>
	</div>
</body>
</html>
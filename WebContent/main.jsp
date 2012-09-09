<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="webpage/css/style.css" />
	<link rel="stylesheet" type="text/css" href="webpage/images/images.css" />
	<!-- <link rel="stylesheet" type="text/css" href="webpage/js/jquery-easyui/themes/gray/easyui.css"> -->
	
	<link rel="stylesheet" type="text/css" href="webpage/js/zebra-dialog/css/zebra_dialog.css" />
	<link rel="stylesheet" type="text/css" href="webpage/js/jquery.layout/layout-default-latest.css" />
	
	<script type="text/javascript" src="webpage/js/jquery-1.7.1.js"></script>
	<link rel="stylesheet" type="text/css" href="webpage/js/jquery-ui/css/cupertino/jquery-ui-1.8.23.custom.css" />
	<script type="text/javascript" src="webpage/js/jquery-ui/jquery-ui-1.8.23.custom.min.js"></script>
	<!-- <script type="text/javascript" src="webpage/js/jquery-easyui/jquery.easyui.min.js"></script> -->
	
	<script type="text/javascript" src="webpage/js/jquery-easyui/easyloader.js"></script> 
 
	
	<script type="text/javascript" src="webpage/js/jquery.layout/jquery.layout-latest.js"></script>
	
	<script type="text/javascript" src="webpage/js/zebra-dialog/js/zebra_dialog.js"></script>
	<script language="javascript">
		$(function () {
			
			pageLayout = $('body').layout({
				applyDefaultStyles: false,
				north: {
					size		:	"73",
					spacing_open:	0,
					closable	:	false,
					resizable	:	false
				},
				south: {
					size		:	"30",
					spacing_open:	0,
					closable	:	false,
					resizable	:	false
				}
			});
			
			
			
			$( "#dialog-form" ).dialog({
				autoOpen: false,
				height: 300,
				width: 350,
				modal: true,
				buttons: {
					"Create an account": function() {
						var bValid = true;
						allFields.removeClass( "ui-state-error" );

						bValid = bValid && checkLength( name, "username", 3, 16 );
						bValid = bValid && checkLength( email, "email", 6, 80 );
						bValid = bValid && checkLength( password, "password", 5, 16 );

						bValid = bValid && checkRegexp( name, /^[a-z]([0-9a-z_])+$/i, "Username may consist of a-z, 0-9, underscores, begin with a letter." );
						// From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
						bValid = bValid && checkRegexp( email, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "eg. ui@jquery.com" );
						bValid = bValid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );

						if ( bValid ) {
							$( "#users tbody" ).append( "<tr>" +
								"<td>" + name.val() + "</td>" + 
								"<td>" + email.val() + "</td>" + 
								"<td>" + password.val() + "</td>" +
							"</tr>" ); 
							$( this ).dialog( "close" );
						}
					},
					Cancel: function() {
						$( this ).dialog( "close" );
					}
				},
				close: function() {
					allFields.val( "" ).removeClass( "ui-state-error" );
				}
			});
			
					
			var par = "";
			//par = { anticache : Math.floor(Math.random()*1000)}
			$.post("menu.action",par,function(data){
				$("#ajaxDate").html(data);
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
		function openAccountInfo() {
			$( "#dialog-form" ).dialog( "open" );
		}
	</script>
	<title></title>
</head>
<body>
	<div class="ui-layout-north" style="height:72px;padding:0px;">
		<div id="desktopTitlebarWrapper">
			<div id="desktopTitlebar">
				<h1 class="applicationTitle">USSOFT</h1>
				<h2 class="tagline">亚普档案管理系统 </h2>
				<div id="topNav">
					<ul class="menu-right">
						<li>欢迎您 <a href="#" onClick="openAccountInfo();">${CURRENT_USER_IN_SESSION.accountcode }</a>.</li>
						<li><a href="#" onclick="quit()">退出</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div style="background:#f2f2f2;padding:0px;width:100%;height:25px;text-align:left;"><span >&nbsp;&nbsp;</span>
			<a href="javascript:void(0)" onClick="javascript:$(window.parent.document).find('#ifr').attr('src','index.html')" id="firstpage" class="easyui-linkbutton" plain="true" iconCls="icon-house">首页</a>
			<span id="ajaxDate"><img src="webpage/images/icons/loading.gif" />正在加载数据，请稍候……</span>
		</div>
		
	</div>
	<div class="ui-layout-center" style="overflow:hidden;"   border="false" >
		<iframe id="ifr" name="ifr" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" src="index.html"></iframe>
	</div>
	<div id="desktopFooter" class="ui-layout-south" >
		&copy; 2011-2015 <a target="_blank" href="#">亚普软件（北京）有限公司</a> - <a id="licenseLink" href="#">www.upsoft.com</a>
	</div>
	
	<div id="dialog-form" title="Create new user">
		<p class="validateTips">All form fields are required.</p>
	
		<form>
			<fieldset>
				<label for="name">Name</label>
				<input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all" />
				<label for="email">Email</label>
				<input type="text" name="email" id="email" value="" class="text ui-widget-content ui-corner-all" />
				<label for="password">Password</label>
				<input type="password" name="password" id="password" value="" class="text ui-widget-content ui-corner-all" />
			</fieldset>
		</form>
	</div>
</body>
</html>
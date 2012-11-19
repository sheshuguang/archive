<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="webpage/css/style.css" />
	<link rel="stylesheet" type="text/css" href="webpage/images/images.css" />
	
	<link rel="stylesheet" type="text/css" href="webpage/js/jquery.layout/layout-default-latest.css" />
	
	<script type="text/javascript" src="webpage/js/jquery-1.7.1.js"></script>
	<link rel="stylesheet" type="text/css" href="webpage/js/jquery-ui/css/custom-theme/jquery-ui-1.8.16.custom.css" />
	<script type="text/javascript" src="webpage/js/jquery-ui/jquery-ui-1.8.16.custom.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="webpage/js/wijmo/jquery.wijmo-open.2.2.1.css"  />
	<script type="text/javascript" src="webpage/js/wijmo/jquery.wijmo-open.all.2.2.1.min.js" ></script>
	<script type="text/javascript" src="webpage/js/jquery.layout/jquery.layout-latest.js"></script>
	
	<script type="text/javascript" src="webpage/js/us.archive.util.js"></script>
	<style>
         .wijmo-container{
             display: block;
             clear: both;
             width:100%;
			 float:left;
         }
         /*label, input { display:block; }
         fieldset { padding:0; border:0; margin-top:15px; }
         input.text { margin-bottom:12px; width:85%; padding: .4em; }
         /*.validateTips { border: 1px solid transparent; padding: 0.3em; }*/
    </style>
	<script language="javascript"> 
		$(function () {
			$( "#dialog-form" ).dialog({
				autoOpen: false,
				height: 300,
				width: 350,
				modal: true,
				resizable:false,
				buttons: {
					"提交": function() {
						
					},
					"关闭": function() {
						$( this ).dialog( "close" );
					}
				},
				close: function() {
					
				}
			});
			
					
			var par = "";
			//par = { anticache : Math.floor(Math.random()*1000)}
			$.post("menu.action",par,function(data){
				$("#menu").html(data);
				$("#menu").wijmenu({triggerEvent: "click" });
				ifrHeight();
			});
		});
		function quit(){
			us.openconfirm("真的要退出系统吗?","系统提示",
				function() {
					window.location.href="webpage/common/logout.jsp";
				}
			);
		}
		function openAccountInfo() {
			$( "#dialog-form" ).dialog( "open" );
		}
		function ifrHeight() {
			var h = pageHeight();
			$("#ifr").height(h - $("#desktopFooter").height() - $(".wijmo-container").height() - 6);
		}
		function pageHeight(){
			if($.browser.msie){
				return document.compatMode == "CSS1Compat"? document.documentElement.clientHeight :
				document.body.clientHeight;
			}else{
				return self.innerHeight;
			}
		}; 
	</script>
	
	<title></title>
</head>
<body>
	<div id="top" class="ui-layout-north" style="padding:0px;">
		<div class="wijmo-container" style="float:left">
               <ul id="menu"></ul>
        </div>
	</div>
	
	<iframe id="ifr" onload="ifrHeight()" name="ifr" width="100%"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" allowtransparency="yes" src="index.html"></iframe>

	<div id="desktopFooter" class="ui-layout-south" style="height:22px;" >
		&copy; 2011-2015 <a target="_blank" href="#">亚普软件（北京）有限公司</a> - <a id="licenseLink" href="#">www.upsoft.com</a>
	</div>
	
	<div id="dialog-form" title="帐户信息">
		<form>
			<fieldset class="fieldset">
				<label for="name" class="label">帐户名</label>
				<input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all input" /></br>
				<label for="email" class="label">密码</label>
				<input type="text" name="email" id="email" value="" class="text ui-widget-content ui-corner-all input" />
				<label for="password" class="label">重复密码</label>
				<input type="password" name="password" id="password" value="" class="text ui-widget-content ui-corner-all input" />
			</fieldset>
		</form>
	</div>
</body>
</html>
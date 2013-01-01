<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="webpage/css/style.css" />
<link rel="stylesheet" type="text/css" href="webpage/images/images.css" />
<link rel="stylesheet" type="text/css"
	href="webpage/js/jquery.layout/layout-default-latest.css" />
<script type="text/javascript" src="webpage/js/jquery-1.7.1.js"></script>
<link rel="stylesheet" type="text/css"
	href="webpage/js/bootstrap/css/bootstrap.css" />
<script type="text/javascript"
	src="webpage/js/bootstrap/js/bootstrap.js"></script>
	
<link rel="stylesheet" type="text/css"
	href="webpage/js/jquery-ui/css/custom-theme/jquery-ui-1.8.16.custom.css" />
<script type="text/javascript"
	src="webpage/js/jquery-ui/jquery-ui-1.8.16.custom.min.js"></script>

<script type="text/javascript"
	src="webpage/js/jquery.layout/jquery.layout-latest.js"></script>
<script type="text/javascript" src="webpage/js/us.archive.util.js"></script>
<script language="javascript">
	$(function() {
		$('.tooltip-test').tooltip();
		$('.popover-test').popover();
		// popover demo
		$("a[rel=popover]").popover().click(function(e) {
			e.preventDefault();
		});
		$("#dialog-form").dialog({
			autoOpen : false,
			height : 370,
			width : 350,
			modal : true,
			resizable : false,
			buttons : {
				"提交" : function() {
					var oldpass = $("#oldpassword");
					var newpass = $("#newpassword");
					var confirmpass = $("#confirmpassword");
					
					if (oldpass.val() == "") {
						us.openalert("请输入原密码。","系统提示","alertbody alert_Information");
						oldpass.focus();
						return;
					}
					if (newpass.val() == "") {
						us.openalert("请输入新密码。","系统提示","alertbody alert_Information");
						newpass.focus();
						return;
					}
					if (confirmpass.val() == "") {
						us.openalert("请输入重复密码。","系统提示","alertbody alert_Information");
						confirmpass.focus();
						return;
					}
					
					if (oldpass.val() == "") {
						us.openalert("请输入原密码。","系统提示","alertbody alert_Information");
						return;
					}
					
					if (newpass.val() != confirmpass.val()) {
						us.openalert("新密码2次输入不一致。请重新输入。","系统提示","alertbody alert_Information");
						return;
					}
					
					var par = {
							oldpassword:"",
							newpassword:""
					};
					par.oldpassword = oldpass.val();
					par.newpassword = newpass.val();
					var aa = "par=" + JSON.stringify(par);
					$.post("updatePass.action",aa,function(data){
							us.openalert(data,"系统提示","alertbody alert_Information");
						}
					);
					
				},
				"关闭" : function() {
					$(this).dialog("close");
				}
			},
			close : function() {

			}
		});

		$
				.ajax({
					async : false,
					url : "menu.action",
					type : 'post',
					dataType : 'script',
					success : function(data) {
						if (data != "error") {
							$("#welcome").html("欢迎您：" + account);
							var funList = eval(functionList);
							var funStr = "<li><a href=\"#\" onclick=\"javascript:$(window.parent.document).find('#ifr').attr('src','index.html')\">首页</a></li>";
							for ( var i = 0; i < funList.length; i++) {
								var fun = funList[i];
								if (fun.funparent == 0) {
									//先得到是否有子节点
									var childFun = "";
									var tmp = "";
									for ( var j = 0; j < funList.length; j++) {
										child = funList[j];
										if (child.funparent == fun.functionid) {
											tmp += "<li><a href=\"javascript:void(0)\" onclick=\"javascript:$(window.parent.document).find('#ifr').attr('src','"
													+ child.funpath
													+ "')\">"
													+ child.funchinesename
													+ "</a></li>";
										}
									}
									if (tmp != "") {
										childFun = "<ul class=\"dropdown-menu\">";
										childFun += tmp + "</ul>";
									}

									if (childFun != "") {
										funStr += "<li class=\"dropdown\"><a href=\"javascript:void(0)\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">"
												+ fun.funchinesename
												+ " <b class=\"caret\"></b></a>";
										funStr += childFun + "</li>";
									} else {
										funStr += "<li><a href=\"javascript:void(0)\" onclick=\"javascript:$(window.parent.document).find('#ifr').attr('src','"
												+ child.funpath
												+ "')\">"
												+ fun.funchinesename + "</a>";
										funStr += "</li>";
									}
								}
							}
							$("#fun").html(funStr);
							//ifrHeight();

						} else {
							us
									.openalert(
											'<span style="color:red">读取功能信息时出错.</span></br>请关闭浏览器，重新登录尝试或与管理员联系!',
											'系统提示',
											'alertbody alert_Information');
						}
					}
				});
	});
	function quit() {
		us.openconfirm("真的要退出系统吗?", "系统提示", function() {
			window.location.href = "webpage/common/logout.jsp";
		});
	}
	function openAccountInfo() {
		$("#oldpassword").val("");
		$("#newpassword").val("");
		$("#confirmpassword").val("");
		$("#dialog-form").dialog("open");
	}
	function ifrHeight() {
		var h = pageHeight();
		$("#ifr").height(
				h - $("#desktopFooter").height() - $("#menu").height() - 6);
	}
	function pageHeight() {
		if ($.browser.msie) {
			return document.compatMode == "CSS1Compat" ? document.documentElement.clientHeight
					: document.body.clientHeight;
		} else {
			return self.innerHeight;
		}
	};
</script>

<title></title>
</head>
<body>
	<div id="menu" class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a><a class="brand" href="#">Archive</a>
				<div class="nav-collapse collapse">
					<ul class="nav" id="fun">
					</ul>
					<ul class="nav pull-right">
						<li><a href="#" onClick="openAccountInfo();" id="welcome">欢迎您</a></li>
						<li><a href="#" onclick="quit()">退出</a></li>
					</ul>

				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div><!-- onload="ifrHeight()" -->
	<iframe id="ifr" onload="ifrHeight()" style="margin-top: 41px"
		name="ifr" width="100%" frameborder="no" border="0" marginwidth="0"
		marginheight="0" scrolling="auto" allowtransparency="yes"
		src="index.html"></iframe>

	<div id="desktopFooter" class="ui-layout-south" style="height: 22px;">
		&copy; 2011-2015
		<!-- <a target="_blank" href="#">亚普软件（北京）有限公司</a> - <a id="licenseLink" href="#">www.upsoft.com</a> -->
	</div>

	<div id="dialog-form" title="帐户信息">
		<form >
			<fieldset class="fieldset" style="left:50%">
				<label for="oldpassword">原密码</label>
				<input type="password" name="oldpassword" id="oldpassword" />
				<label for="newpassword">密码</label>
				<input type="password" name="newpassword" id="newpassword" value="" />
				<label for="confirmpassword">重复密码</label>
				<input type="password" name="confirmpassword" id="confirmpassword" value="" />
			</fieldset>
		</form>
	</div>
	
</body>
</html>
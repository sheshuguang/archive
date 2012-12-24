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
		//var par = "";
		//$.post("menu.action",par,function(data){
		//	if (data != "error") {
		//		alert(data);
		//		var ddd = account;
		//		alert(ddd);
		//		$("#welcome").html("欢迎您："+ account);
		//	}
		//	else {
		//		
		//	}
		//$("#menu").html(data);
		//$("#menu").wijmenu({triggerEvent: "click" });
		//ifrHeight();
		//});

	});
	function quit() {
		us.openconfirm("真的要退出系统吗?", "系统提示", function() {
			window.location.href = "webpage/common/logout.jsp";
		});
	}
	function openAccountInfo() {
		$("#dialog-form").dialog("open");
	}
	function openAccount() {
        $('#dialog-form1').modal({
            backdrop:true,
            keyboard:true,
            show:true
        });
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
				<label for="name">帐户名</label>
				<input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all input" />
				<label for="email">密码</label>
				<input type="text" name="email" id="email" value="" class="text ui-widget-content ui-corner-all input" />
				<label for="password">重复密码</label>
				<input type="password" name="password" id="password" value="" class="text ui-widget-content ui-corner-all input" />
			</fieldset>
		</form>
	</div>

	<div id="dialog-form1" style="display: none;width:400px;left:60%;top:60%"   class="modal hide fade">
		<div class="modal-header">
			<a class="close" data-dismiss="modal">×</a>
			<h4>帐户信息</h4>
		</div>
		<div class="modal-body">
			<form >
				<label>帐户名</label><input type="text" id="name" name="name" class="span3" placeholder="请输入帐户名..."> <span class="help-inline">相关提示信息</span>
				<label>密码</label><input type="text" id="password" name="password" class="span3" placeholder="请输入密码..."> <span class="help-inline">相关提示信息</span>
				<label>重复密码</label><input type="text" id="password2" name="password2" class="span3" placeholder="请输入密码..."> <span class="help-inline">相关提示信息</span>
			</form>
		</div>
		<div class="modal-footer">
			<button type="submit" class="btn btn-primary" >Submit</button>
			<button class="btn" data-dismiss="modal">Submit</button>
		</div>
	</div>

	<div style="display: none;" id="myModal" class="modal hide fade">
		<div class="modal-header">
			<a class="close" data-dismiss="modal">×</a>
			<h3>对话框标题</h3>
		</div>
		<div class="modal-body">
			<h4>对话框文字</h4>
			<p>

				高考临近，湖北孝感惊现史上最刻苦“吊瓶班”。这是昨日孝感市第一高级中学某晚自习教室的一幕，"吊瓶高考班"轰动全校！不知台上的老师面对如此震撼的场
				面和沁人心脾的药水味，如何站稳脚跟hold住全场呢？该校学生表示，备战高考，补充能量挨几针也是值得的，你hold住了吗？</p>

			<h4>对话框中的弹出提示</h4>
			<p>
				<a data-original-title="花钱买座位" href="#" class="btn popover-test"
					data-content="青岛工学院最近下发通知，将考研自习室772个座位“有偿提供”，5元起售，目前已售出500多个座位。根据采光通风等条件不同，座位每月收费划分为5元、10元、15元、20元、25元五个档次，学生购买时要一次交纳10个月的费用。教务处长称， 为了给学生解决考研复习问题，只好“出此下策”。">学生真不易</a>
				把鼠标放上来试试？
			</p>

			<h4>对话框中的工具提示</h4>
			<p>
				5月1日，济南一名<a data-original-title="城管里面也有好人，把他们变成坏人的是恶的制度" href="#"
					class="tooltip-test">城管</a>要对一位抱着三四岁孩子的<a
					data-original-title="小商贩真的很辛苦，不要再欺负辛劳人了" href="#"
					class="tooltip-test">女商贩</a>扣留时候发生争执。争执中女商贩抱着孩子给城管下跪,城管见状也向女商贩跪下。事后城管称，下跪是为了与对方“平等对话”,也怕对孩子心理产生不良影响。
			</p>

			<hr>

			<h4>对溢出文本使用可选的滚动条</h4>
			<p>
				我们对
				<code>.modal-body</code>
				样式修正了
				<code>max-height</code>
				。对于超过高度的内容就会显示滚动条。
			</p>
			<p>南京街头某家麦当劳门口，一个外国人买了两包薯条，分给乞讨的老奶奶一包，两人席地而坐，开心地吃着聊着。</p>
			<p>@说书者一枚
				：《帝都日爆》射论：《从麦当劳事件看美国小伙的拙劣表演》：这位美国小伙拙劣的”亲民民主表演“，再次印证了中国的古语“黄鼠狼给鸡拜年没安好心”。这
				道貌岸然的潜伏”美国政客“，想用糖衣炮弹瓦解坚强的中国无产阶级老奶奶，必定遭到中国人民的唾弃！不要做自取其辱的丑事！（完，完蛋的完）</p>
			<p>4日晚，东莞东城海雅百货前，一女子被偷包，巴西籍男子MOZEN阻止，遭小偷团伙报复群殴。其间数十名路人在场，无
				人施救。MOZEN说，小偷打他，他觉得正常，可没有任何人帮他，让他觉得不正常，“这个社会是需要互相帮助的，我整天出没在君豪商业中心，中心几乎大部
				分人都认识我，却没有一个人帮我，这让我觉得很失望，很心寒”。</p>
			<p>MOZEN说，海雅百货这一带治安很乱，这已经不是他第一次阻止小偷行窃了，早在今年春节前，他就曾扑倒过一个小偷。可有了这一次的经历，如果下回再碰上类似情况，他不会也不敢再帮忙了。</p>
			<p>
				美国总统富兰克林·罗斯福1942年6月14日美国国旗纪念日广播演讲：“我们所有人都是地球的子孙，有些道理不言而喻，如果我们的兄弟在遭受压迫，我们也将遭受压迫，如果他们在忍饥挨饿，我们也将忍饥挨饿，如果他们的自由权利被剥夺，我们的自由也将不复存在......<a
					href="http://t.cn/aDoq5K"></a>http://t.cn/aDoq5K
			</p>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">关闭</a> <a href="#"
				class="btn btn-primary">保存更改</a>
		</div>
	</div>
</body>
</html>
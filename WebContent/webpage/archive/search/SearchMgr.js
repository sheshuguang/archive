
var searchCommon = new us.archive.Search();

$(function() {
	//同步取当前session帐户能查询的档案类别
	$.ajax({
		async : false,
		url : "getTemplet.action",
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				searchCommon.templetList = eval(data);
			} else {
				us.openalert('<span style="color:red">读取数据时出错.</span></br>请关闭浏览器，重新登录尝试或与管理员联系!',
						'系统提示',
						'alertbody alert_Information'
				);
			}
		}
	});
	//填充搜索档案类别select
	$("#searchDropdownBox").empty();
	if (searchCommon.templetList.length > 0) {
		$("#searchDropdownBox").append("<option selected='selected' value='all'>全部分类</option>");
		for (var i=0;i<searchCommon.templetList.length;i++) {
			if (searchCommon.templetList[i].parentid == 0) {
				$("#searchDropdownBox").append("<option value='"+searchCommon.templetList[i].treeid+"'>"+searchCommon.templetList[i].treename+"</option>");
			}
		}
	}
	
	var aa = createleftmenu();
	
	$("#leftmenu").html(aa);
	
	$(".nav").accordion({ 
        speed: 500, 
        closedSign: '[+]', 
        openedSign: '[-]' 
    }); 
});
//创建搜索页左侧档案类别菜单
function createleftmenu() {
	var menuList = "";
	for (var i=0;i<searchCommon.templetList.length;i++) {
		if (searchCommon.templetList[i].parentid == 0) {
			temp = "<ul>";
			var parentNode = searchCommon.templetList[i].treenode;
			for (var j=0;j<searchCommon.templetList.length;j++) {
				if (searchCommon.templetList[j].treeid != searchCommon.templetList[i].treeid) {
					if (searchCommon.templetList[j].treetype == "W" && searchCommon.templetList[j].treenode.indexOf(searchCommon.templetList[i].treenode) >= 0) {
						temp += "<li><a href=\"\">" + searchCommon.templetList[j].treename + "</a>";
					};
				};
			};
			
			if (temp != "<ul>") {
				if (i==0) {
					menuList += "<li class=\"active\"><a href=\"#\">"+searchCommon.templetList[i].treename+"</a>";
				}
				else {
					menuList += "<li><a href=\"#\">"+searchCommon.templetList[i].treename+"</a>";
				}
				
				menuList += temp + "</ul></li>";
			}
			else {
				menuList += "<li><a href=\"\">"+searchCommon.templetList[i].treename+"</a><li>";
			}
		};
	};
	
	return menuList;
};

function submitSearch() {
	var searchTxt = $("#searchTxt").val();
	var searchType = $("#searchDropdownBox").val();
//	if (searchAlias == "search-alias=all" && searchTxt == "") {
//		return;
//	};

	$.ajax({
		async : true,
		url : "search.action",
		type : 'post',
		dataType : 'script',
		data:"searchTxt=" + searchTxt + "&searchType=" + searchType,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		success : function(data) {
			if (data != "error") {
//				searchCommon.templetList = eval(data);
			} else {
				us.openalert('<span style="color:red">读取数据时出错.</span></br>请关闭浏览器，重新登录尝试或与管理员联系!',
						'系统提示',
						'alertbody alert_Information'
				);
			}
		}
	});
	
//	$.post("search.action",par,function(data){
//			if (data == "SUCCESS") {
//			}
//			else {
//			}
//		}
//	);
}

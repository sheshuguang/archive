
var searchCommon = new us.archive.Search();

$(function() {
	$('.pagination').css('display','none');
	
	$('.tabs a:last').tab('show');
	//同步取当前session帐户能查询的档案类别
	$.ajax({
		async : false,
		url : "getSearchTree.action",
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				searchCommon.treeList = eval(data);
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
	if (searchCommon.treeList.length > 0) {
		$("#searchDropdownBox").append("<option selected='selected' value='ALL'>全部分类</option>");
		for (var i=0;i<searchCommon.treeList.length;i++) {
			if (searchCommon.treeList[i].parentid == 0) {
				$("#searchDropdownBox").append("<option value='"+searchCommon.treeList[i].treeid+"'>"+searchCommon.treeList[i].treename+"</option>");
			}
		}
	}
	
	//创建搜索左侧菜单
	var menuTxt = createleftmenu("ALL");
	$("#refinements").html(menuTxt);
	
	$("#searchDropdownBox").change(function(){
		var menuTxt = createleftmenu($(this).val());
		$("#refinements").html(menuTxt);
	});
	
	
	
});
//创建搜索页左侧档案类别菜单
function createleftmenu(treeid) {
	var menuList = "";
	for (var i=0;i<searchCommon.treeList.length;i++) {
		if (treeid == "ALL" || treeid == searchCommon.treeList[i].treeid) {
			if (searchCommon.treeList[i].parentid == 0) {
				temp = "<h6>"+searchCommon.treeList[i].treename+"</h6>";
				temp += "<ul>";
//				var parentNode = searchCommon.treeList[i].treenode;
				for (var j=0;j<searchCommon.treeList.length;j++) {
					if (searchCommon.treeList[j].treeid != searchCommon.treeList[i].treeid) {
						if (searchCommon.treeList[j].treetype == "W" && searchCommon.treeList[j].treenode.indexOf(searchCommon.treeList[i].treenode) >= 0) {
							temp += "<li style=\"margin-left: -10px\"><a href=\"#\" onClick=\"searchByTreeid('"+searchCommon.treeList[j].treeid+"','')\"><span class=\"refinementLink\" id=\"" + searchCommon.treeList[j].treeid + "-name" + "\">" + searchCommon.treeList[j].treename + "</span><span class=\"narrowValue\" id=\""+searchCommon.treeList[j].treeid+"\"></span></a></li>";
						};
					};
				};
				if (temp != "<ul>") {
					menuList += temp + "</ul>";
				}
				else {
					menuList += "<li>无档案<li></ul>";
				}
			};
		}
	};
	return menuList;
};

//翻页
function selectPage(page) {
	//把搜索框复位
	$("#searchTxt").val(searchCommon.searchTxt);
	//得到当前点击翻页按钮  的css类
	var cssClass = $("#" + page).attr("class");
	if (cssClass == "disabled") {
		return ;
	}
	if (page == "next") {
		searchCommon.currentPage = searchCommon.currentPage + 1;
	}
	else if (page == "first") {
		searchCommon.currentPage = 1;
	}
	else if (page == "previous") {
		searchCommon.currentPage = searchCommon.currentPage - 1;
	}
	else if (page == "over") {
		searchCommon.currentPage = searchCommon.pages;
	}
	else {
		if (page > searchCommon.pages) {
			searchCommon.currentPage = searchCommon.pages;
		}
		else if (page < 0) {
			searchCommon.currentPage = 1;
		}
		else {
			searchCommon.currentPage = page;
		}
		
	}
	
	var list = getSearchData();
//	pageState();
	showSearchInfo(list);
	
}
//处理翻页按钮状态
function pageState() {
	$("#first").removeClass("disabled");
	$("#previous").removeClass("disabled");
	$("#next").removeClass("disabled");
	$("#over").removeClass("disabled");
	//如果是第一页
	if (searchCommon.currentPage == 1) {
		$("#first").addClass("disabled");
		$("#previous").addClass("disabled");
	}
	if (searchCommon.currentPage == searchCommon.pages){
		$("#next").addClass("disabled");
		$("#over").addClass("disabled");
	}
	
}

function getSearchData() {
	var list;
	//得到查询值
	$.ajax({
		async : false,
		url : "search.action",
		type : 'post',
		dataType : 'script',
		data:"searchTxt=" + searchCommon.searchTxt + "&treeid=" + searchCommon.treeid + "&currentPage=" + searchCommon.currentPage + "&tableType=" + searchCommon.tabletype,
		success : function(data) {
			if (data != "error") {
				list = eval(data);
			} else {
				us.openalert('<span style="color:red">读取数据时出错.</span></br>请关闭浏览器，重新登录尝试或与管理员联系!',
						'系统提示',
						'alertbody alert_Information'
				);
			}
		}
	});
	
	return list;
}

/**
 * 按档案库检索
 */
function submitSearch() {
	//如果关键字为空。就退出什么都不做
	//if the keyword is null , return and do nothing
	if ( $("#searchTxt").val() == "") {
		return;
	};
	searchCommon.searchTxt = $("#searchTxt").val();
	searchCommon.treeid = $("#searchDropdownBox").val();
	searchCommon.currentPage = 1;
	searchCommon.tabletype = "";
	//读取查询数量
	$.ajax({
		async : false,
		url : "searchNumber.action",
		type : 'post',
		dataType : 'script',
		data:"searchTxt=" + searchCommon.searchTxt + "&treeid=" + searchCommon.treeid + "&tableType=" + searchCommon.tabletype,
		success : function(data) {
			if (data != "error") {
				list = eval(data);
				//解析数量并显示在界面上
				for (var i=0;i<list.length;i++) {
					$('#' + list[i].treeid).html(" [" + list[i].num + "]");
				}
			} else {
				us.openalert('<span style="color:red">读取数据时出错.</span></br>请关闭浏览器，重新登录尝试或与管理员联系!',
						'系统提示',
						'alertbody alert_Information'
				);
			}
		}
	});
	
	var list = getSearchData();
	showSearchInfo(list);
	tabinfo("list","");
}

function showSearchInfo(list) {
	
	searchCommon.treename = list[0].TABLELABEL;
	searchCommon.tablename = list[0].TABLENAME;
	searchCommon.rowCount = list[0].ROWCOUNT;
	searchCommon.pages = list[0].PAGES;
	searchCommon.currentPage = list[0].CURRENTPAGE;
	searchCommon.templettype = list[0].TEMPLETTYPE;
	searchCommon.tabletype = list[0].TABLETYPE;
	searchCommon.treeid = list[0].TREEID;
	searchCommon.fields = list[0].FIELDS;
	searchCommon.data = list[0]["DATA"];
	
	var doc = showResultList(list);
	$("#searchList").html("");
	$("#searchList").append(doc);
	
	
	$("#currentPage").val(searchCommon.currentPage);
	$("#pageinfo").html("第  "+searchCommon.currentPage +" 页 / 共 "+searchCommon.pages+" 页");
	var searchInfo = "<strong>提示！</strong> 当前显示查询节点[ " + searchCommon.treename + " ]. 查询数量：[ " + searchCommon.rowCount + " ].";
	if (searchCommon.templettype == "A") {
		searchInfo += "  <strong>点击切换</strong>    【 <a href=\"javascript:void(0);\" onClick=\"searchByTreeid('"+searchCommon.treeid+"','01')\" >案卷级</a>】    【<a href=\"javascript:void(0);\" onClick=\"searchByTreeid('"+searchCommon.treeid+"','02')\">文件级</a>】";
	}
	else if (searchCommon.templettype == "F") {
//		searchInfo += "<strong>点击切换</strong> 【<a href='javascript:void(0);' onClick=''>文件级</a>】";
	}
	$("#searchInfo").html(searchInfo);
	$('.pagination').css('display','block');
	pageState();
}

//创建页面显示
function showResultList(list) {
	var list = list[0]["DATA"];
	var doc = "";
	for (var i=0;i<list.length;i++) {
		doc += "<table class=\"table table-bordered table-condensed\" width=\"100%\">";
		doc += "<tr><td width=\"70px\">所属档案库</td><td>"+$("#" + list[i].treeid + "-name").html()+"</td>";
		if (list[i].tabletype == "A") {
			doc += "<td width=\"70px\">类别</td><td width=\"250px\">案卷级</td></tr>";
		}
		else if (list[i].tabletype=="W" || list[i].tabletype=="F"){
			doc += "<td width=\"70px\">类别</td><td width=\"250px\">文件级</td></tr>";
		}
		else{
			doc += "<td width=\"70px\">类别</td><td width=\"250px\">未知</td></tr>";
		}
		
		var ajhOrWjh = "";
		if (searchCommon.tabletype == '01') {
			if (searchCommon.templettype == "A") {
				ajhOrWjh = list[i]["ajh"];
				doc += "<tr><td>案卷号</td><td>"+ajhOrWjh+"</td><td>责任者</td><td>"+list[i].zrz+"</td></tr>";
			}
			else {
				ajhOrWjh = list[i].wjh;
				doc += "<tr><td>文件号</td><td>"+ajhOrWjh+"</td><td>责任者</td><td>"+list[i].zrz+"</td></tr>";
			}
		}
		else {
			ajhOrWjh = list[i].wjh;
			doc += "<tr><td>文件号</td><td>"+ajhOrWjh+"</td><td>责任者</td><td>"+list[i].zrz+"</td></tr>";
		}
		doc += "<tr><td>归档单位</td><td>"+list[i].gddw+"</td><td>归档日期</td><td>"+list[i].gdrq+"</td></tr>";
		doc += "<tr><td>题名</td><td colspan=\"3\">"+list[i].tm+"</td></tr>";
		doc += "<tr><td colspan=\"4\"><button  class=\"btn btn-info btn-small\" onClick=\"tabinfo('content','"+list[i].id+"')\">查看详细</button>  <button class=\"btn btn-info btn-small\">查看全文</button>"+list[i].id+"</td></tr>";
		doc += "</table>";
	}
	return doc;
}

/**
 * 按树节点检索
 * @param treeid
 */
function searchByTreeid(treeid,tabletype) {
	searchCommon.treeid = treeid;
	searchCommon.currentPage = 1;
	searchCommon.tabletype = tabletype;
	var list;
	list = getSearchData();
	showSearchInfo(list);
	tabinfo("list","");
}

function tabinfo(tabType,selectid) {
	if (tabType == "list") {
		$("#listTab").addClass("active");
		$("#list").addClass("active");
		$("#contentTab").removeClass("active");
		$("#content").removeClass("active");
	}
	else {
		$("#listTab").removeClass("active");
		$("#list").removeClass("active");
		$("#contentTab").addClass("active");
		$("#content").addClass("active");
	}
	if (selectid != "") {
		var fields = searchCommon.fields;
		var datas = searchCommon.data;
		var data ;
//		//找到显示的数据
//		for (var i=0;i<datas.length;i++) {
//			if (datas[i].id == selectid) {
//				data = datas[i];
//			}
//		}
		
		//读取数据库。找到选择的记录
		$.ajax({
			async : false,
			url : "getSelectData.action",
			type : 'post',
			dataType : 'script',
			data:"selectid=" + selectid + "&tablename=" + searchCommon.tablename,
			success : function(d) {
				if (d != "error") {
					data = eval(d);
				} else {
					us.openalert('<span style="color:red">读取数据时出错.</span></br>请关闭浏览器，重新登录尝试或与管理员联系!',
							'系统提示',
							'alertbody alert_Information'
					);
				}
			}
		});
		
		var content = "";
		for (var i=0;i<fields.length;i++) {
			var field = fields[i];
			var a = field.englishname;//toLowerCase()
			var value = data[0][a];
			if (field.isgridshow == 1) {
				content += "<div class=\"control-group\">";
				content += "<label for=\""+field.englishname+"\" class=\"control-label\">"+field.chinesename+"</label>";
				content += "<div class=\"controls\">";
				content += "<input type=\"text\" id=\""+field.englishname+"\" value=\""+value+"\" readonly class=\"input-xlarge\">";
				content += "</div>";
				content += "</div>";
			}
		}
		$("#data").html(content);
	}
	
}
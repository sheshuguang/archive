
var archiveCommon = new us.archive.Archive();

$(function() {
	pageLayout = $('body').layout({
		applyDefaultStyles: false,
		west: {
//			size		:	"73",
			spacing_open:	4,
			closable	:	true,
			resizable	:	true
		},
		center__onresize:	function (pane, $pane, state, options) {
//			var gridHdrH	= $pane.children('.slick-header').outerHeight(),
//				$gridList	= $pane.children('.slick-viewport') ;
//			$gridList.height( state.innerHeight - gridHdrH );
//			$.fn.jerichoTab.resize();
//			var tabw = $('.archivetab').outerWidth();
//			var tabh = $('.archivetab').outerHeight();
//			$('.archivetab').width(state.innerWidth - 5);
//			$('.archivetab').height(state.outerHeight - 5);
			
		}
	});
//	using('tree', function () {
//		$('#archivetree').tree({
//			checkbox : false,
//			url : 'loadTreeData.action?parentid=root',
//			onBeforeExpand : function(node, param) {
//				$('#archivetree').tree('options').url = "loadTreeData.action?parentid="
//						+ node.id;
//			},
//			onSelect : function(node) {
//				showTreeList(node);
//			}
//		});
//	});
	//生成档案tree
	var tree = $("#archivetree").jstree({ 
		//生成的jstree包含哪些插件功能
		//json_data：采用json形式数据
		//crrm：允许增加、改名、移动等操作
		//search：允许检索
		"plugins" : ["themes","json_data","ui","types"],
		//jstree的主题样式，可以更换
		"themes" : {
			"theme" : "default"
		},
		json_data:{
			"ajax":{
	            url:"getTree.action",
	            async:false
	        }
		},
		"types" : {
				"valid_children" : [ "root" ],
				"types" : {
					//设置rel=root的参数
					"root" : {
						//图标
						"icon" : { 
							"image" : "../../images/icons/house.png"
						},
						"valid_children" : [ "folder","default" ],
						"max_depth" : 2,
						"hover_node" : false,
						"select_node" : function () {return false;}

					},
					"default" : {
						"valid_children" : [ "none" ],
						"icon" : { 
							"image" : "../../images/icons/page.png" 
						}
					},
					"folder" : {
						"valid_children" : [ "default", "folder" ],
						"icon" : {
							"image" : "../../images/icons/folder.png"
						}
					}
				}
		},
		"ui" : {
			// 设置初始化选择id为2的节点
//			"initially_select" : [ "0" ]
		},
		// the core plugin - not many options here
		"core" : { 
			//设置默认打开id为1和4的节点
			"initially_open" : [ "0"] 
		}
	})//单击事件
     .bind('click.jstree', function(event) {               
        var eventNodeName = event.target.nodeName;
        if (eventNodeName == 'INS') {                   
            return;               
        } else if (eventNodeName == 'A') {                   
            var $subject = $(event.target).parent();                   
            if ($subject.find('ul').length > 0) {            
            } else {
            	var id = $(event.target).parents('li').attr('id');
            	var text = $(event.target).text();
            	showTreeList(tree,id,text);
              //选择的id值
//               alert($(event.target).parents('li').attr('id'));
//               alert($(event.target).text());
            }               
        }           
    });

	$.fn.initJerichoTab({
        renderTo: '#tab',
        uniqueId: 'archiveTab',
        contentCss: { 'height': $('#center').height()-0 },
        tabs: [{
                title: '操作帮助',
                closeable: false,
//                iconImg: 'images/jerichotab.png',
                data: { dataType: 'formtag', dataLink: '#help' }
            }],
        activeTabIndex: 0,
        loadOnce: false,
        tabWidth:120
    });
	
	$("#docwindows").dialog({
		autoOpen: false,
		height: 420,
		width: 720,
		modal: true,
		buttons: {
			"打开": function() {
				alert("docwindows");
			},
			"关闭": function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			
		}
	});
});

function showTreeList(ob,id,text) {
	archiveCommon.selectTreeName = text;
	archiveCommon.selectTreeid = id;
	var url = "showArchiveList.action?treeid=" + id;
//	us.showtab($('#tab'),url, '档案管理', 'icon-page');
	us.addtab(ob,'档案管理','ajax', url);
}

/**
 * 打开文件页签
 * @param id		打开的文件所属案卷id
 * @param isAllWj	是否现在该节点下全部文件。
 */
function showWjTab(id,isAllWj) {
	archiveCommon.selectAid = id;
	archiveCommon.isAllWj = isAllWj;
	var url = "showArchiveWjList.action?treeid=" + archiveCommon.selectTreeid;
	us.addtab($("#wjtab"),'文件管理','ajax', url);
//	us.showtab($('#tab'),url, '文件管理', 'icon-page');
}

// 打开案卷导入tab
function showArchiveImportTab(tableType) {
	archiveCommon.tableType = tableType;
	var url = "dispatch.action?page=/webpage/archive/archive/ArchiveImportList.html";
//	us.showtab($('#tab'),url, '案卷导入', 'icon-page');
	us.addtab($("#importtab"),'案卷导入','ajax', url);
}

/*
 * 生成doc现实的列表
 */
function getDoclist(row) {
	var str = "<li class=\"docli\">";
	str += "<div class=\"docdiv\"><a href=\"downDoc.action?docId="+ row.docid +"\">";
	var docType = row.doctype;
	var typeCss = "";
	if (docType == "DOC" || docType == "XLS" || docType=="PPT") {
		typeCss = "file-icon-office";
	}
	else if (docType == "TXT") {
		typeCss = "file-icon-text";
	}
	else if (docType == "JPG" || docType == "GIF" || docType=="BMP" || docType=="JPEG" || docType=="TIF") {
		typeCss = "file-icon-image";
	}
	else if (docType == "PDF") {
		typeCss = "file-icon-pdf";
	}
	else if (docType == "ZIP") {
		typeCss = "file-icon-zip";
	}
	else if (docType == "RAR") {
		typeCss = "file-icon-rar";
	}
	else if (docType == "FLASH") {
		typeCss = "file-icon-flash";
	}
	else {
		
	}
	str += "<img class=\"file-icon "+typeCss+" \">";
	str += "</a></div>";
	var name = row.docoldname;
//	if (row.docoldname.length > 8) {
//		name = row.docoldname.substr(0,8) + "..." + "." + docType;
//	}
	str += "<div title=\""+row.docoldname+"\"><div class=\"docfilename\"><a href=\"downDoc.action?docId="+ row.docid +"\">" + name + "</a></div></div>";
	str += "</li>";
	return str;
}



// 打开电子全文windows
function showDocwindow(id, tableid) {
	archiveCommon.selectRowid = id;
	archiveCommon.selectTableid = tableid;
	
	var par = "selectRowid="+ id + "&tableid="+tableid;
	var rowList = [];
	//同步读取数据
	$.ajax({
		async : false,
		url : "listLinkDoc.action?"+ par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
//				authorityGridconfig.rows = rowList;
//				var rowList = [];
				rowList = eval(data);
				$("#doclist").html("");
				for (var i=0;i<rowList.length;i++) {
					$("#doclist").append(getDoclist(rowList[i]));
				}
			} else {
				us.openalert('读取数据时出错，请尝试重新操作或与管理员联系! ','系统提示','alertbody alert_Information');
			}
		}
	});
	
	$("#docwindows").dialog('option', 'title', '电子全文列表--(共 ' + rowList.length + "个文件)");
	$("#docwindows").dialog( "open" );
}

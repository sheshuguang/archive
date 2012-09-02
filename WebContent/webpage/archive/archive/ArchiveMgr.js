
var archiveCommon = new us.archive.Archive();

$(function() {
	pageLayout = $('body').layout({
		applyDefaultStyles: false,
		west: {
//			size		:	"73",
			spacing_open:	4,
			closable	:	true,
			resizable	:	true
		}
	});
	using('tree', function () {
		$('#archivetree').tree({
			checkbox : false,
			url : 'loadTreeData.action?parentid=root',
			onBeforeExpand : function(node, param) {
				$('#archivetree').tree('options').url = "loadTreeData.action?parentid="
						+ node.id;
			},
			onSelect : function(node) {
				showTreeList(node);
			}
		});
	});
	
//	$("#docwindows").dialog({
//		autoOpen: false,
//		height: 300,
//		width: 350,
//		modal: true,
//		buttons: {
//			"打开": function() {
//				alert("dd");
//			},
//			Cancel: function() {
//				$( this ).dialog( "close" );
//			}
//		},
//		close: function() {
//			
//		}
//	});
});

function showTreeList(node) {
	var b = $('#archivetree').tree('isLeaf', node.target);
	if (!b) {
		return;
	}
	if (node.id == 0) {
		return;
	}
	archiveCommon.selectTreeName = node.text;
	archiveCommon.selectTreeid = node.id;
	var url = "showArchiveList.action?treeid=" + node.id;
	us.showtab($('#tab'),url, '档案管理', 'icon-page');
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
	us.showtab($('#tab'),url, '文件管理', 'icon-page');
}

// 打开案卷导入tab
function showArchiveImportTab(tableType) {
	archiveCommon.tableType = tableType;
	var url = "dispatch.action?page=/webpage/archive/archive/ArchiveImportList.html";
	us.showtab($('#tab'),url, '案卷导入', 'icon-page');
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
				for (var i=0;i<rowList.length;i++) {
					$("#doclist").html("<li><a href=\"downDoc.action?docId="+ rowList[i].docid +"\">" + rowList[i].docoldname +"</a></li>");
				}
			} else {
				$.Zebra_Dialog('读取数据时出错，请尝试重新操作或与管理员联系! ', {
	                'type':     'information',
	                'title':    '系统提示',
	                'buttons':  ['确定']
	            });
			}
		}
	});
//	$( "#docwindows" ).dialog( "open" );
	$.window({
		showModal	: true,
		modalOpacity: 0.5,
	    title		: "电子全文列表",
	    content		: $("#docwindows"),
	    width		: 300,
	    height		: 200,
	    showFooter	: false,
	    showRoundCorner: true,
	    minimizable	: false,
	    maximizable	: false,
	    onShow		: function(wnd) {
	    	
	    }
	});
//	var url = "showDocListTab.action";
//	us.showtab($('#tab'),url, '电子文件管理', 'icon-page');
}

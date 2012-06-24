var selectTreeName = "";
var selectTreeid = "";
// 选择的案卷id
var selectAid = "";
var selectAJH = "";
// 关于电子文件，选择的行id
var selectRowid = "";
var selectTableid = "";
var isLoadUploadify = false;

$(function() {
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
// 打开、切换tab页签
function showtab(url, tabname, icon) {
	if ($('#tab').tabs('exists', tabname)) {
		$('#tab').tabs('select', tabname);
		var tab = $('#tab').tabs('getSelected');
		$('#tab').tabs('update', {
			tab : tab,
			options : {
				href : url
			}
		});
		tab.panel('refresh');

	} else {
		$('#tab').tabs('add', {
			title : tabname,
			iconCls : icon,
			href : url,
			closable : true
		});
	}
}
function showTreeList(node) {
	var b = $('#archivetree').tree('isLeaf', node.target);
	if (!b) {
		return;
	}
	if (node.id == 0) {
		return;
	}
	selectTreeName = node.text;
	selectTreeid = node.id;
	var url = "showArchiveList.action?treeid=" + node.id;
	showtab(url, '档案管理', 'icon-page');
}

function showWjTab(id) {
	selectAid = id;
	var url = "showArchiveWjList.action?treeid=" + selectTreeid;
	showtab(url, '文件管理', 'icon-page');
	$('#archivetable').datagrid('clearSelections');
}

// 打开案卷导入tab
function showArchiveImportTab(id, tableid) {
	selectRowid = id;
	selectTableid = tableid;
	// var url = "showImportArchive.action";
	var url = "dispatch.action?page=/webpage/archive/archive/ArchiveImportList.html";
	showtab(url, '案卷导入', 'icon-page');
}

// grid字段录入时创建字段验证是否为空
function requiredFieldValidator(value) {
	if (value == null || value == undefined || !value.length) {
		return {
			valid : false,
			msg : "请填写内容，不能为空。"
		};
	} else {
		return {
			valid : true,
			msg : null
		};
	}
}
//grid批量修改  
/*
 * grid  		是哪个grid对象
 * dataView  	是哪个view对象
 * isSave		批量修改时，是否保存到数据库。
 */
function batchUpdate(grid,dataView,isSave) {
	var selectRows = grid.getSelectedRows();
	selectRows.sort(function compare(a, b) {
		return b - a;
	});
	
	var selectFieldName = $("#selectfield").val(); 
	var updateTxt = $("#updatetxt").val();
	
	var batchUpdateItems = [];
	for ( var i = 0; i < selectRows.length; i++) {
		var item = dataView.getItem(selectRows[i]);
		batchUpdateItems.push(item);
	}
	if (isSave) {
		alert(JSON.stringify(batchUpdateItems));
		//同步读取字段
		var par = "importData=" + JSON.stringify(batchUpdateItems) + "&tableType=01";
		$.ajax({
			async : false,
			url : "updateImportArchive.action?" + par,
			type : 'post',
			dataType : 'script',
			success : function(data) {
				if (data != "保存完毕。") {
					$.Zebra_Dialog(data, {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
				}
				else {
					for ( var i = 0; i < batchUpdateItems.length; i++) {
						var item = batchUpdateItems[i];
						for(p in item){
//							alert(i);//i就是test的属性名
//							alert(item[i]);//test.i就是属性值
							if (p == selectFieldName) {
								item[p] = updateTxt;
								break;
							}
						}
						dataView.updateItem(item.id,item);
					}
				}
			}
		});
	}
	else {
		for ( var i = 0; i < batchUpdateItems.length; i++) {
			var item = batchUpdateItems[i];
			for(p in item){
//				alert(i);//i就是test的属性名
//				alert(item[i]);//test.i就是属性值
				if (p == selectFieldName) {
					item[p] = updateTxt;
					break;
				}
			}
			dataView.updateItem(item.id,item);
		}
	}
	
	
	
//	for ( var i = 0; i < selectRows.length; i++) {
//		var item = dataView.getItem(selectRows[i]);
//		for(p in item){
////			alert(i);//i就是test的属性名
////			alert(item[i]);//test.i就是属性值
//			if (p == selectFieldName) {
//				item[p] = updateTxt;
//				break;
//			}
//		}
//		dataView.updateItem(item.id,item);
//	}
}

// 打开电子全文tab
function showDocTab(id, tableid) {
	selectRowid = id;
	selectTableid = tableid;
	var url = "showDocListTab.action";
	showtab(url, '电子文件管理', 'icon-page');
}

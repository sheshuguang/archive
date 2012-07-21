
var archiveCommon = new us.archive.Archive();

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
//	archiveCommon.selectRowid = id;
//	archiveCommon.selectTableid = tableid;
	archiveCommon.tableType = tableType;
	var url = "dispatch.action?page=/webpage/archive/archive/ArchiveImportList.html";
	us.showtab($('#tab'),url, '案卷导入', 'icon-page');
}



// 打开电子全文tab
function showDocTab(id, tableid) {
	archiveCommon.selectRowid = id;
	archiveCommon.selectTableid = tableid;
	var url = "showDocListTab.action";
	us.showtab($('#tab'),url, '电子文件管理', 'icon-page');
}

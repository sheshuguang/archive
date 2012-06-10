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
	$('#archivetree')
			.tree(
					{
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
//打开、切换tab页签
function showtab(url,tabname,icon) {
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
//	if ($('#tab').tabs('exists', "档案管理")) {
//		$('#tab').tabs('select', "档案管理");
//		var tab = $('#tab').tabs('getSelected');
//		$('#tab').tabs('update', {
//			tab : tab,
//			options : {
//				href : url
//			}
//		});
//		tab.panel('refresh');
//
//	} else {
//		$('#tab').tabs('add', {
//			title : "档案管理",
//			iconCls : 'icon-page',
//			href : url,
//			closable : true
//		});
//	}
}

function showWjTab(id) {
	selectAid = id;
	var url = "showArchiveWjList.action?treeid=" + selectTreeid;
	showtab(url, '文件管理', 'icon-page');
//	if ($('#tab').tabs('exists', "文件管理")) {
//		$('#tab').tabs('select', "文件管理");
//		var tab = $('#tab').tabs('getSelected');
//		$('#tab').tabs('update', {
//			tab : tab,
//			options : {
//				href : url
//			}
//		});
//		tab.panel('refresh');
//	} else {
//		$('#tab').tabs('add', {
//			title : "文件管理",
//			iconCls : 'icon-page',
//			href : url,
//			closable : true
//		});
//	}
	$('#archivetable').datagrid('clearSelections');
}

// 打开案卷导入tab
function showArchiveImportTab(id, tableid) {
	selectRowid = id;
	selectTableid = tableid;
//	var url = "showImportArchive.action";
	var url = "dispatch.action?page=/webpage/archive/archive/ArchiveImportList.html";
	showtab(url, '案卷导入', 'icon-page');
//	if ($('#tab').tabs('exists', "案卷导入")) {
//		$('#tab').tabs('select', "案卷导入");
//		var tab = $('#tab').tabs('getSelected');
//		$('#tab').tabs('update', {
//			tab : tab,
//			options : {
//				href : url
//			}
//		});
//		tab.panel('refresh');
//	} else {
//		$('#tab').tabs('add', {
//			title : "案卷导入",
//			iconCls : 'icon-page',
//			href : url,
//			closable : true
//		});
//	}
}

// 打开电子全文tab
function showDocTab(id, tableid) {
	selectRowid = id;
	selectTableid = tableid;
	var url = "showDocListTab.action";
	showtab(url, '电子文件管理', 'icon-page');
//	if ($('#tab').tabs('exists', "电子文件管理")) {
//		$('#tab').tabs('select', "电子文件管理");
//		var tab = $('#tab').tabs('getSelected');
//		$('#tab').tabs('update', {
//			tab : tab,
//			options : {
//				href : url
//			}
//		});
//		tab.panel('refresh');
//	} else {
//		$('#tab').tabs('add', {
//			title : "电子文件管理",
//			iconCls : 'icon-page',
//			href : url,
//			closable : true
//		});
//	}
}

function uploadFiles() {
	$("#uploadWindow").show();
	if (!isLoadUploadify) {
		$("#uploadify").uploadify(
				{// 初始化函数
					'uploader' : '../../js/uploadify/uploadify.swf', // flash文件位置，注意路径
					'script' : 'uploadFile.action', // 后台处理的请求
					'cancelImg' : '../../js/uploadify/cancel.png', // 取消按钮图片
					// 'folder':'jquery/uploadsFolder',
					// //您想将文件保存到的路径，将auto设置为true里才有用，不然跳到类里去处理，那就那里说了算
					'fileDataName' : 'archive',
					'queueID' : 'fileQueue', // 与下面的上传文件列表id对应
					// 'queueSizeLimit':10, //上传文件的数量
					'scriptData' : {
						'selectRowid' : selectRowid,
						'selectTableid' : selectTableid
					}, // 向后台传的数据
					'fileDesc' : 'rar文件或zip文件', // 上传文件类型说明
					// 'fileExt' :'*.rar;*.zip;*txt,*doc,*docx',
					// //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
					'method' : 'get', // 如果向后台传输数据，必须是get
					'sizeLimit' : 100000000, // 文件上传的大小限制，单位是字节
					'auto' : false, // 是否自动上传
					'multi' : true,
					// 'simUploadLimit':10,
					// //同时上传文件的数量，设置了这个参数后，那么你会因设置multi:true和queueSizeLimit：8而可以多选8个文件，但如果一旦你将simUploadLimit也设置了，那么只会上传这个参数指定的文件个数，其它就上传不了
					'buttonText' : '上次文件', // 浏览按钮图片
					'buttonImg' : '../../js/uploadify/xzqwa.gif',
					'wmode' : 'transparent',
					'width' : 88,
					'height' : 25,
					'onComplete' : function(event, queueID, fileObj,
							serverData, data) {// 当上传完成后的回调函数，ajax方式哦~~
						// alert(serverData);
						// uploadifyCancel('NFJSHS');
						$("p[name='uploadfiles']").text(
								data.fileCount + " 文件等待上传");
					},
					'onSelectOnce' : function(event, data) {
						// alert(data.fileCount);
						$("p[name='selectfiles']").text(
								"共选择 " + data.fileCount + " 文件。");
						$("p[name='kb']").text(data.allBytesTotal + " 文件总字节数");
					},
					'onCancel' : function(event, queueId, fileObj, data) {
						$("p[name='selectfiles']").text(
								"共选择 " + data.fileCount + " 文件。");
						$("p[name='kb']").text(data.allBytesTotal + " 文件总字节数");
					},
					'onQueueFull' : function(event, queueSizeLimit) {
						alert('dd');
					},
					'onAllComplete' : function(event, data) {
						$("p[name='uploadfiles']").text(
								data.filesUploaded + " 文件成功上传");
						$("p[name='selectfiles']").text("共选择 0 文件。");
					},
					onError : function(event, queueID, fileObj, errorObj) {
						if (errorObj.type === "File Size") {
							alert("文件:" + fileObj.name + "超过文件上传大小限制！");
							countinue;
						} else {
							alert("文件:" + fileObj.name + "上传失败。请重新尝试或与管理员联系。");
						}
					}
				});
	}
	$('#uploadWindow').window({
		title : ' 电子全文上传',
		width : 700,
		height : 450,
		top : ($(window).height() - 450) * 0.5,
		left : ($(window).width() - 700) * 0.5,
		shadow : true,
		modal : true,
		iconCls : 'icon_user_go',
		closed : true,
		minimizable : false,
		maximizable : false,
		collapsible : false,
		onBeforeOpen : function() {

		}
	});
	isLoadUploadify = true; // 定义的全局变量，初始值为false为了解决重复加载Uploadify的问题。
	$("#uploadWindow").window('open');
}

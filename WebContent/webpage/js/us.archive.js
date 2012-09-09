/*
 *	命名空间	：us.archive
 *	描述		：档案管理通用js
 *	日期		：2012-07-08
 *			：wangf
*/

//判断us命名空间是否存在
if (us == null || us == undefined){
	var us = {};
}
if (us.archive == null || us.archive == undefined ) {
	us.archive = {};
}
//archive 
us.archive.Archive = function() {
	this.selectTreeName  = "";
	this.selectTreeid = "";
	// 选择的案卷id
	this.selectAid = "";
	this.selectAJH = "";
	// 关于电子文件，选择的行id
	this.selectRowid = "";
	this.selectTableid = "";
	this.sortcol = "title";
	this.sortdir = 1;
	this.searchString = "";
	this.clName = "";
	this.isAllWj = false;
	//点击导入按钮时，导入页面用来判断当前是案卷还是文件的导入。
	this.tableType = "";
	//点击批量挂接时，将选择的rows存在这里，打开批量挂接tab时，读取
	this.items = [];
	
	//批量挂接对应关系之档案字段
	this.archiveField = "ajh";
	//批量挂接对应关系之未挂接文件字段
	this.fileField = "docoldname";
	//存储批量挂接对应上关系的文件items。
	this.yesItems = [];
}

/**
 * 打开批量挂接tab
 */
us.archive.Archive.prototype.showBatchAttachment = function(grid,tableType,rows) {
	archiveCommon.tableType = tableType;
	archiveCommon.items.length = 0;
	for ( var i = 0; i < rows.length; i++) {
		var item = grid.dataView.getItem(rows[i]);
		archiveCommon.items.push(item);
	};
	var url = "dispatch.action?page=/webpage/archive/archive/ArchiveBatchAttachment.html";
	us.addtab($("#batchatttab"),'批量挂接','ajax', url);
//	us.showtab($('#tab'),url, '批量挂接', 'icon-page');
	
}

////私有函数
//us.archive.Archive.prototype._aaa = function() {
//	
//}
////public函数
//us.archive.Archive.prototype.aaa = function() {
//	
//}




/*
 * 权限管理专用对象
 */
us.archive.Authority = function() {
	this.selectNodeid = "";
	this.selectNodeText = "";
	//存储帐户组与树节点关系，因为保存新的对应关系时，要全部删除，所以之前的对应关系要补上，这里先读取
	this.orgOfTree = [];
	this.accountTree = [];
	
	//存储选中的帐户组与树关系表（sysorgtree）的orgtreeid
	this.orgTreeid = "";
	this.accountTreeid = "";
	//选择的帐户设置权限的id
	this.accountid = "";
	//选择设置权限的帐户名称
	this.accountcode = "";
}
/**
 * 点击帐户组节点，打开帐户组的权限信息页
 * @param node
 */
us.archive.Authority.prototype.showAuthorityList = function(node) {
	//记录选择的帐户组节点id
	authorityCommon.selectNodeid = node.id;
	authorityCommon.selectNodeText = node.text;
	var url = "dispatch.action?page=/webpage/system/authority/AuthorityList.html";
	us.showtab($('#tab'),url, '帐户组权限管理', 'icon-page');
}

/**
 * 点击帐户，打开帐户的权限信息页
 * @param node
 */
us.archive.Authority.prototype.showAccountAuthorityList = function(item) {
	authorityCommon.accountid = item.accountid;
	authorityCommon.accountcode = item.accountcode;
	var url = "dispatch.action?page=/webpage/system/authority/AccountAuthorityList.html";
	us.showtab($('#tab'),url, '帐户详细权限', 'icon-page');
}

/**
 * 点击帐户组的查看帐户，打开帐户的列表页
 * @param node
 */
us.archive.Authority.prototype.showAccountList = function() {
	var url = "dispatch.action?page=/webpage/system/authority/AccountList.html";
	us.showtab($('#tab'),url, '帐户列表', 'icon-page');
	
}

us.archive.Authority.prototype.getOrgTree = function() {
	$.ajax({
		async : false,
		url : "getOrgTree.action?orgid=" + authorityCommon.selectNodeid,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			authorityCommon.orgOfTree = eval(data);
		}
	});
}

us.archive.Authority.prototype.getAccountTree = function() {
	$.ajax({
		async : false,
		url : "getAccountTree.action?accountid=" + authorityCommon.accountid,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			authorityCommon.accountTree = eval(data);
		}
	});
}

us.archive.Authority.prototype.openSelectRoleWindows = function(target) {
	$.window({
		showModal	: true,
		modalOpacity: 0.5,
	    title		: "选择角色",
	    content		: $("#selectRoleWindows"),
	    width		: 500,
	    height		: 400,
	    z:800,
	    showFooter	: false,
	    showRoundCorner: true,
	    minimizable	: false,
	    maximizable	: false,
	    onShow		: function(wnd) {
	    	var container = wnd.getContainer(); // 抓到window裡最外層div物件
	    	//load 角色grid数据
			var gridDiv = container.find("#selectRoleDiv"); // 尋找container底下的指定grid div
			var bar = container.find("#toolbar"); // 尋找container底下的指定grid div
			
			var columns = [
			                {id: "rolename", name: "角色名称", field: "rolename",width:100},
			                {id: "rolememo", name: "角色说明", field: "rolememo",width:300}
			              ];
			//同步读取数据
			$.ajax({
				async : false,
				url : "listRoleForAuthority.action",
				type : 'post',
				dataType : 'script',
				success : function(data) {
					if (data != "error") {
						authorityGridconfig.rows = rowList;
					} else {
						$.Zebra_Dialog('读取数据时出错，请尝试重新操作或与管理员联系! ', {
			                'type':     'information',
			                'title':    '系统提示',
			                'buttons':  ['确定']
			            });
					}
				}
			});
			authorityGridconfig.options.editable = false;
			authorityGridconfig.options.enableAddRow = false;
			authorityGridconfig.grid = new Slick.Grid(gridDiv, authorityGridconfig.rows, columns, authorityGridconfig.options);
			authorityGridconfig.grid.setSelectionModel(new Slick.RowSelectionModel());
			
			//生成toolbar
			bar.toolbar({
				items:[{
					id:"select",
					iconCls:"icon-page-save",
					disabled:false,
					text:"保存",
					handler:function(){
						var selectRows = authorityGridconfig.grid.getSelectedRows();
						if (selectRows.length != 1) {
							$.Zebra_Dialog('只能选择一个角色.<br>请重新选择。 ', {
				                'type':     'information',
				                'title':    '系统提示',
				                'buttons':  ['确定']
				            });
						}
						else {
							var item = authorityGridconfig.grid.getDataItem(selectRows[0]);
							var par = "";
							var action = "";
							var showRoleObject;
							if (target == "org") {
								par = "orgid=" + authorityCommon.selectNodeid + "&roleid=" + item.roleid;
								action = "setOrgRole.action";
								showRoleObject = $("#orgOfRole");
							}
							else {
								par = "accountid=" + authorityCommon.accountid + "&roleid=" + item.roleid;
								action = "setAccountRole.action";
								showRoleObject = $("#accountRole");
							}
							

							$.post(action,par,function(data){
								if (data != "error") {
									showRoleObject.html(data);
									$.Zebra_Dialog("保存成功！", {
						                'type':     'information',
						                'title':    '系统提示',
						                'buttons':  ['确定']
						            });
								}
								else {
									$.Zebra_Dialog("保存失败，请重新操作或与管理员联系！", {
						                'type':     'information',
						                'title':    '系统提示',
						                'buttons':  ['确定']
						            });
								}
								
							});
						}
					}
				}]
			});
	    }
	});
}


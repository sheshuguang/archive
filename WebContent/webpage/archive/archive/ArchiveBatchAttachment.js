
//选择的档案条目grid
var attGridConfig = new us.archive.ui.Gridconfig();
//没有对应的全文grid
var attNoGridConfig = new us.archive.ui.Gridconfig();
//对应的全文grid
var attYesGridConfig = new us.archive.ui.Gridconfig();

archiveCommon.yesItems = [];


$(function() {
	
	pageLayout = $('#batchlayout').layout({
		applyDefaultStyles: false,
		north: {
			size		:	"230",
			spacing_open:	2,
			closable	:	false,
			resizable	:	true
		},
		center: {
			size		:	"30",
			spacing_open:	2,
			closable	:	false,
			resizable	:	true
		},
		east: {
			size		:	"300",
			spacing_open:	2,
			closable	:	false,
			resizable	:	true
		}
	});
	
	
	var par = "treeid=" + archiveCommon.selectTreeid + "&tableType=" + archiveCommon.tableType + "&importType=1";
	$.ajax({
		async : false,
		url : "getField.action?" + par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				attGridConfig.columns = fields;
				attGridConfig.fieldsDefaultValue = fieldsDefaultValue;
			} else {
				new $.Zebra_Dialog('读取字段信息时出错，请关闭浏览器，重新登录尝试或与管理员联系!', {
					'buttons' : false,
					'modal' : false,
					'position' : [ 'right - 20', 'top + 20' ],
					'auto_close' : 2500
				});
			}
		}
	});
	// 创建dataview
	attGridConfig.dataView = new Slick.Data.DataView({
		inlineFilters : true
	});
	attGridConfig.options.enableAddRow = false;
	attGridConfig.options.editable = false;
	// 创建grid
	attGridConfig.grid = new Slick.Grid("#archiveAttachmentDiv", attGridConfig.dataView, attGridConfig.columns, attGridConfig.options);

	// 设置grid的选择模式。行选择
	attGridConfig.grid.setSelectionModel(new Slick.RowSelectionModel());
	
	// 注册grid的自动提示插件。只在字段内容过长时出现省略号时提示
	attGridConfig.grid.registerPlugin(new Slick.AutoTooltips());
	
	attGridConfig.dataView.onRowCountChanged.subscribe(function(e, args) {
		attGridConfig.grid.updateRowCount();
		attGridConfig.grid.render();
	});

	attGridConfig.dataView.onRowsChanged.subscribe(function(e, args) {
		attGridConfig.grid.invalidateRows(args.rows);
		attGridConfig.grid.render();
	});
	
//	attGridConfig.grid.onClick.subscribe(function(e,args) {
//		 var item = attGridConfig.dataView.getItem(args.row);
//		 var attYesItems = attYesGridConfig.dataView.getItems();
//		 alert(item.id); 
//	});
	
	attGridConfig.grid.onSelectedRowsChanged.subscribe(function(e,args) {
		var attYesItems = archiveCommon.yesItems;  //attYesGridConfig.dataView.getItems();
		if (attYesItems.length > 0) {
			if (args.rows.length > 0) {
				attYesGridConfig.dataView.setItems([]);
				for (var i=0;i<args.rows.length;i++) {
					var item = attGridConfig.dataView.getItem(args.rows[i]);
					for (var j=0;j<attYesItems.length;j++) {
						if (item.id == attYesItems[j].fileid) {
							attYesGridConfig.dataView.addItem(attYesItems[j]);
							break;
						}
					}
				}
			}
		}
	});
	
	attGridConfig.dataView.beginUpdate();
	for ( var i = 0; i < archiveCommon.items.length; i++) {
		attGridConfig.dataView.addItem(archiveCommon.items[i]);
	};
	attGridConfig.dataView.endUpdate();
//	attGridConfig.grid.resizeCanvas();
	//=========没有对应的全文grid============
	
  //同步读取当前帐户上传到全文库中的电子文件数据
	$.ajax({
		async : false,
		url : "listNoLinkDocAsAccount.action",
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				attNoGridConfig.rows = eval(data);
			} else {
				$.Zebra_Dialog('读取全文库中未挂接数据时出错，请尝试重新操作或与管理员联系! ', {
	                'type':     'information',
	                'title':    '系统提示',
	                'buttons':  ['确定']
	            });
			}
		}
	});
	attNoGridConfig.columns = [
         {id: "docoldname", name: "文件名", field: "docoldname" },
         {id: "doctype", name: "文件类型", field: "doctype" },
         {id: "doclength", name: "文件大小", field: "doclength" },
         {id: "creater", name: "上传者", field: "creater" },
         {id: "createtime", name: "上传时间", field: "createtime" }
     ];
	// 创建dataview
	attNoGridConfig.dataView = new Slick.Data.DataView({
		inlineFilters : true,
		idField:"docid"
	});
	attNoGridConfig.options.enableAddRow = false;
	attNoGridConfig.options.editable = false;
	// 创建grid
	attNoGridConfig.grid = new Slick.Grid("#archiveAttachmentDiv-no", attNoGridConfig.dataView, attNoGridConfig.columns, attNoGridConfig.options);

	// 设置grid的选择模式。行选择
	attNoGridConfig.grid.setSelectionModel(new Slick.RowSelectionModel());
	// 注册grid的自动提示插件。只在字段内容过长时出现省略号时提示
	attNoGridConfig.grid.registerPlugin(new Slick.AutoTooltips());
	
	attNoGridConfig.dataView.onRowCountChanged.subscribe(function(e, args) {
		attNoGridConfig.grid.updateRowCount();
		attNoGridConfig.grid.render();
	});

	attNoGridConfig.dataView.onRowsChanged.subscribe(function(e, args) {
		attNoGridConfig.grid.invalidateRows(args.rows);
		attNoGridConfig.grid.render();
	});
	attNoGridConfig.dataView.beginUpdate();
	attNoGridConfig.dataView.setItems(attNoGridConfig.rows);
	attNoGridConfig.dataView.endUpdate();
//	attNoGridConfig.grid.resizeCanvas();
	//=====================
	
	//========已对应的全文grid=========
	attYesGridConfig.columns = [
                   {id: "docoldname", name: "文件名", field: "docoldname" },
                   {id: "doctype", name: "文件类型", field: "doctype" },
                   {id: "doclength", name: "文件大小", field: "doclength" },
                   {id: "creater", name: "上传者", field: "creater" },
                   {id: "createtime", name: "上传时间", field: "createtime" },
                   {id: "fileid", name: "id", field: "fileid" }
               ];
  	// 创建dataview
	attYesGridConfig.dataView = new Slick.Data.DataView({ 
  		inlineFilters : true,
  		idField:"docid"
  	});
	attYesGridConfig.options.enableAddRow = false;
	attYesGridConfig.options.editable = false;
  	// 创建grid
	attYesGridConfig.grid = new Slick.Grid("#archiveAttachmentDiv-yes", attYesGridConfig.dataView, attYesGridConfig.columns, attYesGridConfig.options);

  	// 设置grid的选择模式。行选择
	attYesGridConfig.grid.setSelectionModel(new Slick.RowSelectionModel());
  	// 注册grid的自动提示插件。只在字段内容过长时出现省略号时提示
	attYesGridConfig.grid.registerPlugin(new Slick.AutoTooltips());
          	
  	attYesGridConfig.dataView.onRowCountChanged.subscribe(function(e, args) {
  		attYesGridConfig.grid.updateRowCount();
  		attYesGridConfig.grid.render();
  	});
  
  	attYesGridConfig.dataView.onRowsChanged.subscribe(function(e, args) {
  		attYesGridConfig.grid.invalidateRows(args.rows);
  		attYesGridConfig.grid.render();
  	});
          	
	                  	
	//=======================
	
	//生成toolbar
	$('#toolbar-attachment').toolbar({
		items:[{
			id:"select",
			iconCls:"icon-page-add",
			disabled:false,
			text:"设置挂接条件",
			handler:function(){
				$.window({
					showModal	: true,
		   			modalOpacity: 0.5,
				    title		: "设置挂接条件",
				    content		: $("#setrequwindows"),
				    width		: 400,
				    height		: 200,
				    showFooter	: false,
				    showRoundCorner: true,
				    minimizable	: false,
				    maximizable	: false,
				    onShow		: function(wnd) {
//				    	var container = wnd.getContainer(); // 抓到window裡最外層div物件
//				    	//修改导入按钮点击事件
//				    	var importBtn = container.find("#importBtn"); // 尋找container底下的指定更改按钮
//						//给更改按钮赋予点击事件(因为存在多个grid，所以更改按钮的参数是临时赋予的)
//				    	importBtn.unbind("click"); 
//				    	importBtn.click( function(){
//				    		importArchive(wnd);
//						});
//						//修改关闭按钮事件
//						var closeBtn = container.find("#closeBtn"); // 尋找container底下的指定更改按钮
//						//给更改按钮赋予点击事件(因为存在多个grid，所以更改按钮的参数是临时赋予的)
//						closeBtn.unbind("click"); 
//						closeBtn.click( function(){
//							wnd.close();
//						});
				    }
				});
			}
		},{
			text:"移除档案",
			iconCls:"icon-page-delete",
			handler:function(){
				var selectRows = attGridConfig.grid.getSelectedRows();
				selectRows.sort(function compare(a, b) {
					return b - a;
				});
				if (selectRows.length > 0) {
					for ( var i = 0; i < selectRows.length; i++) {
    					var item = attGridConfig.dataView.getItem(selectRows[i]);
    					attGridConfig.dataView.deleteItem(item.id);
    				}
				}
				else {
					$.Zebra_Dialog('请选择要删除的数据。 ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
				}
			}
		},{
			text:"保存",
			iconCls:"icon-page-delete",
			handler:function(){
				var a = archiveCommon.yesItems;
				if (a.length > 0) {
					var par = "items=" + JSON.stringify(a) + "&tableType=" + archiveCommon.tableType + "&treeid=" + archiveCommon.selectTreeid;

					$.post("saveBatchAttArchive.action",par,function(data){
							new $.Zebra_Dialog(data, {
				  				'buttons':  false,
				   			    'modal': false,
				   			    'position': ['right - 20', 'top + 20'],
				   			    'auto_close': 2500
				            });
						}
					);
				}
				else {
					$.Zebra_Dialog('没有找到导入数据.<br>请重新挂接或与管理员联系。 ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
				}
			}
		}]
	});
	
	//生成toolbar
	$('#toolbar-attachment-no').toolbar({
		items:[{
			text:"批量挂接",
			iconCls:"icon-page-delete",
			handler:function(){
				//得到档案记录items
				var attItems = attGridConfig.dataView.getItems();
				if (attItems.length < 1) {
					$.Zebra_Dialog('没有找到要挂接的档案记录，请重新操作。 ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}
				//得到未挂接的全文items
				var attNoItems = attNoGridConfig.dataView.getItems();
				if (attNoItems.length < 1) {
					$.Zebra_Dialog('没有找到要挂接的全文记录，请重新操作。 ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}
				
				for (a in attItems) {
					for (var i=attNoItems.length-1;i>=0;i-- ) {
						if (attItems[a][archiveCommon.archiveField] != "") {
							if (attNoItems[i][archiveCommon.fileField].indexOf(attItems[a][archiveCommon.archiveField]) > -1) {
								//如果符合条件，则移入挂接全文grid
								var fileid = "";
								fileid = attItems[a].id;
								//添加到挂接全文grid
								var item = attNoItems[i];
								item.fileid = fileid;
								attYesGridConfig.dataView.addItem(item);
								//
								attNoGridConfig.dataView.deleteItem(item.docid);
							}
						}
						
					}
				}
				archiveCommon.yesItems = attYesGridConfig.dataView.getItems();
			}
		},{
			text:"手动挂接",
			iconCls:"icon-page-delete",
			handler:function(){
				var attSelectRows = attGridConfig.grid.getSelectedRows();
				if (attSelectRows.length != 1) {
					$.Zebra_Dialog('手动挂接只能选择一个档案记录，请重新选择。 ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}
				var fileid = "";
				var selectArchiveitem = attGridConfig.dataView.getItem(attSelectRows[0]);
				fileid = selectArchiveitem.id;
				var attNoSelectRows = attNoGridConfig.grid.getSelectedRows();
				attNoSelectRows.sort(function compare(a, b) {
					return a - b;
				});
				if (attNoSelectRows.length > 0) {
					attYesGridConfig.dataView.beginUpdate();
					for ( var i = 0; i < attNoSelectRows.length; i++) {
						var item = attNoGridConfig.dataView.getItem(attNoSelectRows[i]);
						item.fileid = fileid;
						attYesGridConfig.dataView.addItem(item);
					};
					attYesGridConfig.dataView.endUpdate();
					attNoSelectRows.sort(function compare(a, b) {
						return b - a;
					});
					for ( var i = 0; i < attNoSelectRows.length; i++) {
						var item = attNoGridConfig.dataView.getItem(attNoSelectRows[i]);
						attNoGridConfig.dataView.deleteItem(item.docid);
					};
				}
				else {
					$.Zebra_Dialog('请选择要手动挂接的电子全文数据。 ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
				}
				archiveCommon.yesItems = attYesGridConfig.dataView.getItems();
			}
		},{
			id:"select",
			iconCls:"icon-page-add",
			disabled:false,
			text:"上传",
			handler:function(){
				$.window({
					showModal	: true,
		   			modalOpacity: 0.5,
				    title		: "选择文件",
				    content		: $("#selectfilewindows"),
				    width		: 400,
				    height		: 200,
				    showFooter	: false,
				    showRoundCorner: true,
				    minimizable	: false,
				    maximizable	: false,
				    onShow		: function(wnd) {
				    	var container = wnd.getContainer(); // 抓到window裡最外層div物件
				    	//修改导入按钮点击事件
				    	var importBtn = container.find("#importBtn"); // 尋找container底下的指定更改按钮
						//给更改按钮赋予点击事件(因为存在多个grid，所以更改按钮的参数是临时赋予的)
				    	importBtn.unbind("click"); 
				    	importBtn.click( function(){
				    		importArchive(wnd);
						});
						//修改关闭按钮事件
						var closeBtn = container.find("#closeBtn"); // 尋找container底下的指定更改按钮
						//给更改按钮赋予点击事件(因为存在多个grid，所以更改按钮的参数是临时赋予的)
						closeBtn.unbind("click"); 
						closeBtn.click( function(){
							wnd.close();
						});
				    }
				});
			}
		},{
			text:"移除",
			iconCls:"icon-page-delete",
			handler:function(){
				var selectRows = attNoGridConfig.grid.getSelectedRows();
				selectRows.sort(function compare(a, b) {
					return b - a;
				});
				if (selectRows.length > 0) {
					for ( var i = 0; i < selectRows.length; i++) {
    					var item = attNoGridConfig.dataView.getItem(selectRows[i]);
    					attNoGridConfig.dataView.deleteItem(item.docid);
    				}
				}
				else {
					$.Zebra_Dialog('请选择要移除的未挂接全文数据。 ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
				}
			}
		}]
	});
	
	
	//生成toolbar
	$('#toolbar-attachment-yes').toolbar({
		items:[{
			text:"移除",
			iconCls:"icon-page-delete",
			handler:function(){
				var selectRows = attYesGridConfig.grid.getSelectedRows();
				selectRows.sort(function compare(a, b) {
					return a - b;
				});
				if (selectRows.length > 0) {
					//先在未挂接文件grid里增加
					for ( var i = 0; i < selectRows.length; i++) {
						var item = attYesGridConfig.dataView.getItem(selectRows[i]);
						attNoGridConfig.dataView.addItem(item);
					};
					selectRows.sort(function compare(a, b) {
						return b - a;
					});
					for ( var i = 0; i < selectRows.length; i++) {
    					var item = attYesGridConfig.dataView.getItem(selectRows[i]);
    					attYesGridConfig.dataView.deleteItem(item.docid);
    				}
				}
				else {
					$.Zebra_Dialog('请选择要移除的数据。 ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
				}
				archiveCommon.yesItems = attYesGridConfig.dataView.getItems();
			}
		}]
	});
});



function importArchive(w) {
	var wnds = $.window.getAll();
	var aa = wnds[wnds.length -1].getContainer();
	var bb = aa.find("#selectfile");
	
	
	var container = w.getContainer(); // 抓到window裡最外層div物件
	var fileContent = container.find("#selectfile"); // 尋找container底下的指定input element
	var importArchiveForm = container.find("#importArchiveForm");
	var filePath = fileContent.val();
	if (!filePath) {
		alert("请选择导入到文件。");
		return;
	}
	// 判断上传文件类型
	var a = selectfile_Validator(filePath);
	if (a) {
		var url = 'upload.action?treeid=' + archiveCommon.selectTreeid;
		if (archiveCommon.tableType == "01") {
			url += '&tableType=01';
		}
		else {
			url += '&tableType=02&selectAid=' + archiveCommon.selectAid;
		}
//		importArchiveForm.attr('action','upload.action?treeid=' + archiveCommon.selectTreeid + '&tableType=01');
		importArchiveForm.attr('action',url);
		importArchiveForm.submit();
	}
//	wnd.close();
}

function showCallback(backType, jsonStr) {

	if (backType == "failure") {
		alert(jsonStr);
	} else {
		var json = JSON.parse(jsonStr);
		importGridConfig.dataView.beginUpdate();
		importGridConfig.dataView.setItems(json);
		importGridConfig.dataView.endUpdate();
		importGridConfig.grid.registerPlugin(new Slick.AutoTooltips());
	}
	//关闭windows
//	$.modal.close();
//	selectFileWindow.close();
//	$.window.close();
	$.window.closeAll();
}

function selectfile_Validator(selectfile) {
	if (selectfile == " ") {
		alert("请选择Excel类型的导入文件！ ");
		return false;
	}
	var last = selectfile.match(/^(.*)(\.)(.{1,8})$/)[3]; // 检查上传文件格式
	last = last.toUpperCase();
	if (last == "XLS") {
	} else {
		alert("只能上传Excel文件,请重新选择！ ");
		return false;
	}
	return true;
}



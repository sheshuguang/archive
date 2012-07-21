

var importGridConfig = new us.archive.ui.Gridconfig();


$(function() {
	var par = "treeid=" + archiveCommon.selectTreeid + "&tableType=" + archiveCommon.tableType + "&importType=1";
	$.ajax({
		async : false,
		url : "getField.action?" + par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				importGridConfig.columns_fields = fields;
				importGridConfig.fieldsDefaultValue = fieldsDefaultValue;
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
	var importTitle = "";
	importTitle = archiveCommon.selectTreeName + '_文件导入';
//	if (archiveCommon.tableType == '01') {
//		importTitle = archiveCommon.selectTreeName + '_案卷导入';
//	}
//	else {
//		importTitle = archiveCommon.selectTreeName + '_文件导入';
//	}
	$("#grid-header").html(importTitle);
	
	// 创建checkbox列
	var checkboxSelector = new Slick.CheckboxSelectColumn({
		cssClass : "slick-cell-checkboxsel"
	});
	// 加入checkbox列
	importGridConfig.columns.push(checkboxSelector.getColumnDefinition());
	// 加入其他列
	for ( var i = 0; i < importGridConfig.columns_fields.length; i++) {
		importGridConfig.columns.push(importGridConfig.columns_fields[i]);
	}
	// 创建dataview
	importGridConfig.dataView = new Slick.Data.DataView({
		inlineFilters : true
	});
	importGridConfig.options.enableAddRow = false;
	// 创建grid
	importGridConfig.grid = new Slick.Grid("#archiveImportDiv", importGridConfig.dataView, importGridConfig.columns, importGridConfig.options);

//	grid.onValidationError.subscribe(handleValidationError);
	// 设置grid的选择模式。行选择
	// grid.setSelectionModel(new Slick.RowSelectionModel());
	importGridConfig.grid.setSelectionModel(new Slick.RowSelectionModel({
		selectActiveRow : false
	}));
	// 设置分页控件
	var pager = new Slick.Controls.Pager(importGridConfig.dataView, importGridConfig.grid, $("#pager"));
	// 注册grid的checkbox功能插件
	importGridConfig.grid.registerPlugin(checkboxSelector);
	// 注册grid的自动提示插件。只在字段内容过长时出现省略号时提示
	importGridConfig.grid.registerPlugin(new Slick.AutoTooltips());
	
	importGridConfig.grid.onSort.subscribe(function(e, args) {
		archiveCommon.sortdir = args.sortAsc ? 1 : -1;
		archiveCommon.sortcol = args.sortCol.field;
		importGridConfig.dataView.sort(us.comparer, args.sortAsc);
		
	});
	
	importGridConfig.dataView.onRowCountChanged.subscribe(function(e, args) {
		importGridConfig.grid.updateRowCount();
		importGridConfig.grid.render();
	});

	importGridConfig.dataView.onRowsChanged.subscribe(function(e, args) {
		importGridConfig.grid.invalidateRows(args.rows);
		importGridConfig.grid.render();
	});
	
	//生成toolbar
	$('#toolbar').toolbar({
		items:[{
			id:"select",
			iconCls:"icon-page-add",
			disabled:false,
			text:"选择文件",
			handler:function(){
//				importBatchWindow = $.window({
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
			text:"批量修改",
			iconCls:"icon-page-delete",
			handler:function(){
				var selectRows = importGridConfig.grid.getSelectedRows();
				selectRows.sort(function compare(a, b) {
					return b - a;
				});
				if (selectRows.length > 0) {
					$.window({
						showModal	: true,
			   			modalOpacity: 0.5,
					    title		: "批量修改",
					    content		: $("#batchwindows"),
					    width		: 300,
					    height		: 200,
					    showFooter	: false,
					    showRoundCorner: true,
					    minimizable	: false,
					    maximizable	: false,
					    onShow		: function(wnd) {
					    	var container = wnd.getContainer(); // 抓到window裡最外層div物件
							var selectContent = container.find("#selectfield"); // 尋找container底下的指定select框
							var batchBtn = container.find("#batchBtn"); // 尋找container底下的指定更改按钮
							//给更改按钮赋予点击事件(因为存在多个grid，所以更改按钮的参数是临时赋予的)
							batchBtn.unbind("click"); 
							batchBtn.click( function(){
								us.batchUpdate(importGridConfig.grid,importGridConfig.dataView,false,wnd,archiveCommon.tableType);
							});
							//修改关闭按钮事件
							var closeBtn = container.find("#closeBtn"); // 尋找container底下的指定更改按钮
							//给更改按钮赋予点击事件(因为存在多个grid，所以更改按钮的参数是临时赋予的)
							closeBtn.unbind("click"); 
							closeBtn.click( function(){
								wnd.close();
							});
//							var value = inputer.val(); // 取得值
							selectContent.empty();
							
							for (var i=0;i<importGridConfig.columns_fields.length;i++) {
								if (importGridConfig.columns_fields[i].id != "rownum" && importGridConfig.columns_fields[i].id != "isdoc" && importGridConfig.columns_fields[i].id != "files") {
									selectContent.append("<option value='"+importGridConfig.columns_fields[i].id+"'>"+importGridConfig.columns_fields[i].name+"</option>");
								}
							}
					    }
					});
				}
				else {
					$.Zebra_Dialog('请选择要修改的数据。 ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
				}
			}
		},{
			text:"删除",
			iconCls:"icon-page-delete",
			handler:function(){
				var selectRows = importGridConfig.grid.getSelectedRows();
				selectRows.sort(function compare(a, b) {
					return b - a;
				});
				if (selectRows.length > 0) {
					$.Zebra_Dialog('确定要删除选中的数据吗? ', {
						'type':     'question',
		                'title':    '系统提示',
		                'buttons':  ['确定', '取消'],
		                'onClose':  function(caption) {
		                	if (caption == '确定') {
		                		for ( var i = 0; i < selectRows.length; i++) {
		        					var item = importGridConfig.dataView.getItem(selectRows[i]);
		        					importGridConfig.dataView.deleteItem(item.id);
		        				}
		                	}
		                }
			        });
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
				importGridConfig.grid.getEditorLock().commitCurrentEdit();
				var a = importGridConfig.dataView.getItems();
				if (a.length > 0) {
					var par = "importData=" + JSON.stringify(a) + "&tableType=" + archiveCommon.tableType;

					$.post("saveImportArchive.action",par,function(data){
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
					$.Zebra_Dialog('没有找到导入数据.<br>请重新读取Excel导入文件或与管理员联系。 ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
				}
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





var wjGridconfig = new us.archive.ui.Gridconfig();

$(function(){
	//生成grid的toolbar_aj
	$( "#wjadd" ).button({
		text: false,
		icons: {
			primary: "ui-icon-plusthick"
		}
	})
	.click(function() {
		wjGridconfig.grid.setOptions({
			autoEdit : true
		});
		wjGridconfig.grid.gotoCell(wjGridconfig.dataView.getLength(),3,true);
	});
	$( "#wjdelete" ).button({
		text: false,
		icons: {
			primary: "ui-icon-trash"
		}
	})
	.click(function() {
		var selectRows = wjGridconfig.grid.getSelectedRows();
		selectRows.sort(function compare(a, b) {
			return b - a;
		});
		if (selectRows.length > 0) {
			$.Zebra_Dialog('确定要删除选中的 <span style="color:red">'+selectRows.length+'</span> 条文件记录吗? <br><span style="color:red">注意：删除案卷记录，将同时删除案卷及案卷下所有文件数据、电子全文，请谨慎操作！</span> ', {
				'type':     'question',
                'title':    '系统提示',
                'buttons':  ['确定', '取消'],
                'onClose':  function(caption) {
                	if (caption == '确定') {
                		var deleteRows = [];
                		
                		for ( var i = 0; i < selectRows.length; i++) {
        					var item = wjGridconfig.dataView.getItem(selectRows[i]);
        					deleteRows.push(item);
        				}
                		var par = "par=" + JSON.stringify(deleteRows) + "&tableType=02";
                		$.post("deleteArchive.action",par,function(data){
                				if (data == "SUCCESS") {
                					for ( var i = 0; i < selectRows.length; i++) {
    		        					var item = wjGridconfig.dataView.getItem(selectRows[i]);
    		        					wjGridconfig.dataView.deleteItem(item.id);
    		        				};
    		        				new $.Zebra_Dialog("删除成功。", {
    					  				'buttons':  false,
    					   			    'modal': false,
    					   			    'position': ['right - 20', 'top + 20'],
    					   			    'auto_close': 3500
    					            });
//                					$.Zebra_Dialog("删除成功。", {
//                		                'type':     'information',
//                		                'title':    '系统提示',
//                		                'buttons':  ['确定']
//                		            });
                				}
                				else {
                					new $.Zebra_Dialog(data, {
    					  				'buttons':  false,
    					   			    'modal': false,
    					   			    'position': ['right - 20', 'top + 20'],
    					   			    'auto_close': 3500
    					            });
                				}
                			}
                		);
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
	});
	$( "#wjupdate" ).button({
		text: false,
		icons: {
			primary: "ui-icon-wrench"
		}
	})
	.click(function() {
		wjGridconfig.grid.setOptions({
			autoEdit : true
		});
	});
	$( "#wjendupdate" ).button({
		text: false,
		icons: {
			primary: "ui-icon-grip-solid-horizontal"
		}
	})
	.click(function() {
		wjGridconfig.grid.setOptions({
			autoEdit : false
		});
	});
	$( "#wjbatchupdate" ).button({
		text: false,
		icons: {
			primary: "ui-icon-gear"
		}
	})
	.click(function() {
		var selectRows = wjGridconfig.grid.getSelectedRows();
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
							us.batchUpdate(wjGridconfig.grid,wjGridconfig.dataView,true,wnd,'02');
						});
						//修改关闭按钮事件
						var closeBtn = container.find("#closeBtn"); // 尋找container底下的指定更改按钮
						//给更改按钮赋予点击事件(因为存在多个grid，所以更改按钮的参数是临时赋予的)
						closeBtn.unbind("click"); 
						closeBtn.click( function(){
							wnd.close();
						});
//						var value = inputer.val(); // 取得值
						selectContent.empty();
						
						for (var i=0;i<wjGridconfig.columns_fields.length;i++) {
							if (wjGridconfig.columns_fields[i].id != "rownum" && wjGridconfig.columns_fields[i].id != "isdoc" && wjGridconfig.columns_fields[i].id != "files") {
								selectContent.append("<option value='"+wjGridconfig.columns_fields[i].id+"'>"+wjGridconfig.columns_fields[i].name+"</option>");
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
	});
	$( "#wjimport" ).button({
		text: false,
		icons: {
			primary: "ui-icon-calculator"
		}
	})
	.click(function() {
		showArchiveImportTab('02');
	});
	$( "#wjfilter" ).button({
		text: false,
		icons: {
			primary: "ui-icon-circle-zoomout"
		}
	})
	.click(function() {
		if ($(wjGridconfig.grid.getTopPanel()).is(":visible")) {
			wjGridconfig.grid.hideTopPanel();
		} else {
			wjGridconfig.grid.showTopPanel();
		}
	});
	$( "#wjbatchatt" ).button({
		text: false,
		icons: {
			primary: "ui-icon-flag"
		}
	})
	.click(function() {
		
	});
	
	$("#wjGrid").css({ width: $('.archivetab').width(), height: $('.archivetab').height()-36});
	var par = "treeid=" + archiveCommon.selectTreeid + "&tableType=02&importType=0";
	$.ajax({
		async: false,
		url: "getField.action?" + par,
		type:'post',
		dataType: 'script',
        success: function(data) {
			if (data != "error") {
				wjGridconfig.columns_fields = fields;
				wjGridconfig.fieldsDefaultValue= fieldsDefaultValue;
			}
			else {
				$.Zebra_Dialog('读取字段信息时出错，请关闭浏览器，重新登录尝试或与管理员联系! ', {
	                'type':     'information',
	                'title':    '系统提示',
	                'buttons':  ['确定']
	            });
			}
        }
	});
	
	//同步读取数据
	var par = "treeid=" + archiveCommon.selectTreeid + "&tableType=02&isAllWj=" + archiveCommon.isAllWj +"&selectAid=" + archiveCommon.selectAid;
	$.ajax({
		async : false,
		url : "listArchive.action?" + par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				wjGridconfig.rows = rowList;
			} else {
				$.Zebra_Dialog('读取数据时出错，请尝试重新操作或与管理员联系! ', {
	                'type':     'information',
	                'title':    '系统提示',
	                'buttons':  ['确定']
	            });
			}
		}
	});
	
	$("#grid_header_wj").html(archiveCommon.selectTreeName + '_文件列表');
	
	// 创建checkbox列
	var checkboxSelector = new Slick.CheckboxSelectColumn({
		cssClass : "slick-cell-checkboxsel"
	});
	// 加入checkbox列
	wjGridconfig.columns.push(checkboxSelector.getColumnDefinition());
	// 加入其他列
	for ( var i = 0; i < wjGridconfig.columns_fields.length; i++) {
		wjGridconfig.columns.push(wjGridconfig.columns_fields[i]);
	}
	// 创建dataview
	wjGridconfig.dataView = new Slick.Data.DataView({
		inlineFilters : true
	});
	
	if (archiveCommon.isAllWj == '1') {
		wjGridconfig.options.enableAddRow = false;
	}
	// 创建grid
	wjGridconfig.grid = new Slick.Grid("#wjdiv", wjGridconfig.dataView, wjGridconfig.columns, wjGridconfig.options);
	//设置录入错误时提示。例如不能为空的字段
	wjGridconfig.grid.onValidationError.subscribe(function(e, args) {
//		alert(args.validationResults.msg);
		$.Zebra_Dialog(args.validationResults.msg, {
            'type':     'information',
            'title':    '系统提示',
            'buttons':  ['确定']
        });
	});
	// 设置grid的选择模式。行选择
	// grid.setSelectionModel(new Slick.RowSelectionModel());
	wjGridconfig.grid.setSelectionModel(new Slick.RowSelectionModel({
		selectActiveRow : false
	}));
	
	//设置键盘监听。ctrl + a 全选
	wjGridconfig.grid.onKeyDown.subscribe(function(e) {
		// select all rows on ctrl-a
		if (e.which != 65 || !e.ctrlKey) {
			return false;
		}
		var rows = [];
		for ( var i = 0; i < wjGridconfig.dataView.getLength(); i++) {
			rows.push(i);
		}
		wjGridconfig.grid.setSelectedRows(rows);
		e.preventDefault();
	});
	
	// 设置分页控件
	var pager_wj = new Slick.Controls.Pager(wjGridconfig.dataView, wjGridconfig.grid, $("#pager_wj"));
	// 注册grid的checkbox功能插件
	wjGridconfig.grid.registerPlugin(checkboxSelector);
	// 注册grid的自动提示插件。只在字段内容过长时出现省略号时提示
	wjGridconfig.grid.registerPlugin(new Slick.AutoTooltips());
	
	$("#inlineFilterPanel_wj").appendTo(wjGridconfig.grid.getTopPanel()).show();
	
	$("#txtSearch_wj").keyup(function(e) {
		Slick.GlobalEditorLock.cancelCurrentEdit();
		// clear on Esc
		if (e.which == 27) {
			this.value = "";
		}
		archiveCommon.clName = $("#selectfield_wj").val();
		archiveCommon.searchString = this.value;
		us.updateFilter(wjGridconfig.dataView);
	});
	//生成过滤选择字段
	for (var i=0;i<wjGridconfig.columns_fields.length;i++) {
		if (wjGridconfig.columns_fields[i].id != "rownum" && wjGridconfig.columns_fields[i].id != "isdoc" && wjGridconfig.columns_fields[i].id != "files") {
			$("#selectfield_wj").append("<option value='"+wjGridconfig.columns_fields[i].id+"'>"+wjGridconfig.columns_fields[i].name+"</option>");
		}
	}
	
	//声明新建行的系统默认值
	var newItemTemplate = {
			treeid	: archiveCommon.selectTreeid,
			parentid: archiveCommon.selectAid,
			isdoc	: "0"
	};
	//新建行时，将系统必须的默认值与字段默认值合并
	newItemTemplate = $.extend({},newItemTemplate,wjGridconfig.fieldsDefaultValue);
	
	//grid的添加新行事件
	wjGridconfig.grid.onAddNewRow.subscribe(function(e, args) {
		var item = $.extend({}, newItemTemplate, args.item);
		item.id = UUID.prototype.createUUID ();
		item.rownum = (wjGridconfig.dataView.getLength() + 1).toString();
		wjGridconfig.dataView.addItem(item);
		
		var par = "importData=[" + JSON.stringify(item) + "]&tableType=02";
		$.post("saveImportArchive.action",par,function(data){
				if (data != "保存完毕。") {
					$.Zebra_Dialog(data, {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
				} 
			}
		);
	});
	
	//grid的列值变动事件
	wjGridconfig.grid.onCellChange.subscribe(function(e, args) {
		var item = args.item;
		var par = "importData=[" + JSON.stringify(item) + "]&tableType=02";
		$.post("updateImportArchive.action",par,function(data){
				if (data != "保存完毕。") {
					$.Zebra_Dialog(data, {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
				} 
			}
		);
	});
	
	wjGridconfig.grid.onSort.subscribe(function(e, args) {
		archiveCommon.sortdir = args.sortAsc ? 1 : -1;
		archiveCommon.sortcol = args.sortCol.field;
		wjGridconfig.dataView.sort(us.comparer, args.sortAsc);
		
	});
	
	wjGridconfig.dataView.onRowCountChanged.subscribe(function(e, args) {
		wjGridconfig.grid.updateRowCount();
		wjGridconfig.grid.render();
	});

	wjGridconfig.dataView.onRowsChanged.subscribe(function(e, args) {
		wjGridconfig.grid.invalidateRows(args.rows);
		wjGridconfig.grid.render();
	});
	wjGridconfig.dataView.beginUpdate();
	wjGridconfig.dataView.setItems(wjGridconfig.rows);
	wjGridconfig.dataView.setFilterArgs({
		searchString : archiveCommon.searchString
	});
	wjGridconfig.dataView.setFilter(us.myFilter);
	wjGridconfig.dataView.endUpdate();
	
	wjGridconfig.dataView.syncGridSelection(wjGridconfig.grid, true);
	
	//TODO 生成toolbar_aj 已去掉easyui toolbar 等待删除
	$('#toolbar_wj22').toolbar({
		items:[{
			id:"insert_wj",
			iconCls:"icon-page-add",
			disabled:false,
			text:"添加",
			handler:function(){
				wjGridconfig.grid.setOptions({
					autoEdit : true
				});
				wjGridconfig.grid.gotoCell(wjGridconfig.dataView.getLength(),3,true);
				
			}
		},{
			id:"update",
			iconCls:"icon-page-add",
			disabled:false,
			text:"修改",
			handler:function(){
				wjGridconfig.grid.setOptions({
					autoEdit : true
				});
			}
		},{
			id:"endupdate",
			iconCls:"icon-page-add",
			disabled:false,
			text:"取消修改",
			handler:function(){
				wjGridconfig.grid.setOptions({
					autoEdit : false
				});
			}
		},{
			text:"批量修改",
			iconCls:"icon-page-delete",
			handler:function(){
				var selectRows = wjGridconfig.grid.getSelectedRows();
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
									us.batchUpdate(wjGridconfig.grid,wjGridconfig.dataView,true,wnd,'02');
								});
								//修改关闭按钮事件
								var closeBtn = container.find("#closeBtn"); // 尋找container底下的指定更改按钮
								//给更改按钮赋予点击事件(因为存在多个grid，所以更改按钮的参数是临时赋予的)
								closeBtn.unbind("click"); 
								closeBtn.click( function(){
									wnd.close();
								});
//								var value = inputer.val(); // 取得值
								selectContent.empty();
								
								for (var i=0;i<wjGridconfig.columns_fields.length;i++) {
									if (wjGridconfig.columns_fields[i].id != "rownum" && wjGridconfig.columns_fields[i].id != "isdoc" && wjGridconfig.columns_fields[i].id != "files") {
										selectContent.append("<option value='"+wjGridconfig.columns_fields[i].id+"'>"+wjGridconfig.columns_fields[i].name+"</option>");
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
				var selectRows = wjGridconfig.grid.getSelectedRows();
				selectRows.sort(function compare(a, b) {
					return b - a;
				});
				if (selectRows.length > 0) {
					$.Zebra_Dialog('确定要删除选中的 <span style="color:red">'+selectRows.length+'</span> 条文件记录吗? <br><span style="color:red">注意：删除案卷记录，将同时删除案卷及案卷下所有文件数据、电子全文，请谨慎操作！</span> ', {
						'type':     'question',
		                'title':    '系统提示',
		                'buttons':  ['确定', '取消'],
		                'onClose':  function(caption) {
		                	if (caption == '确定') {
		                		var deleteRows = [];
		                		
		                		for ( var i = 0; i < selectRows.length; i++) {
		        					var item = wjGridconfig.dataView.getItem(selectRows[i]);
		        					deleteRows.push(item);
		        				}
		                		var par = "par=" + JSON.stringify(deleteRows) + "&tableType=02";
		                		$.post("deleteArchive.action",par,function(data){
		                				if (data == "SUCCESS") {
		                					for ( var i = 0; i < selectRows.length; i++) {
		    		        					var item = wjGridconfig.dataView.getItem(selectRows[i]);
		    		        					wjGridconfig.dataView.deleteItem(item.id);
		    		        				};
		    		        				new $.Zebra_Dialog("删除成功。", {
		    					  				'buttons':  false,
		    					   			    'modal': false,
		    					   			    'position': ['right - 20', 'top + 20'],
		    					   			    'auto_close': 3500
		    					            });
//		                					$.Zebra_Dialog("删除成功。", {
//		                		                'type':     'information',
//		                		                'title':    '系统提示',
//		                		                'buttons':  ['确定']
//		                		            });
		                				}
		                				else {
		                					new $.Zebra_Dialog(data, {
		    					  				'buttons':  false,
		    					   			    'modal': false,
		    					   			    'position': ['right - 20', 'top + 20'],
		    					   			    'auto_close': 3500
		    					            });
		                				}
		                			}
		                		);
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
			id:"importWjBtn",
			text:"导入",
			iconCls:"icon-page-copy",
//			disabled:true,
			handler:function(){
				showArchiveImportTab('02');
			}
		},{
			text:"过滤",
			iconCls:"icon-page-copy",
			handler:function(){
				if ($(wjGridconfig.grid.getTopPanel()).is(":visible")) {
					wjGridconfig.grid.hideTopPanel();
				} else {
					wjGridconfig.grid.showTopPanel();
				}
			}
		},{
			text:"刷新",
			iconCls:"icon-page-copy",
			handler:function(){
                wjGridconfig.dataView.refresh();
			}
		}]
	});
	//如果是显示全部文件。屏蔽导入按钮
	if (archiveCommon.isAllWj == '1') {
//		$("#importWjBtn").linkbutton('disable');
//		$("#insert_wj").linkbutton('disable');
		$('#wjimport').button("disable"); 
		$('#wjadd').button("disable"); 
		
	}
});

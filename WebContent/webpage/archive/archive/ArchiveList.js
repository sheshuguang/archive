

var ajGridconfig = new us.archive.ui.Gridconfig();

$(function(){
	//同步读取字段
	var par = "treeid=" + archiveCommon.selectTreeid + "&tableType=01&importType=0";
	$.ajax({
		async : false,
		url : "getField.action?" + par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				ajGridconfig.columns_fields = fields;
				ajGridconfig.fieldsDefaultValue = fieldsDefaultValue;
			} else {
				$.Zebra_Dialog('读取字段信息时出错，请关闭浏览器，重新登录尝试或与管理员联系! ', {
	                'type':     'information',
	                'title':    '系统提示',
	                'buttons':  ['确定']
	            });
			}
		}
	});
	//同步读取数据
	var par = "treeid=" + archiveCommon.selectTreeid + "&tableType=01";
	$.ajax({
		async : false,
		url : "listArchive.action?" + par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				ajGridconfig.rows = rowList;
			} else {
				$.Zebra_Dialog('读取数据时出错，请尝试重新操作或与管理员联系! ', {
	                'type':     'information',
	                'title':    '系统提示',
	                'buttons':  ['确定']
	            });
			}
		}
	});
	
	$("#grid_header_aj").html(archiveCommon.selectTreeName + '_档案列表');
	
	// 创建checkbox列
	var checkboxSelector = new Slick.CheckboxSelectColumn({
		cssClass : "slick-cell-checkboxsel"
	});
	// 加入checkbox列
	ajGridconfig.columns.push(checkboxSelector.getColumnDefinition());
	// 加入其他列
	for ( var i = 0; i < ajGridconfig.columns_fields.length; i++) {
		ajGridconfig.columns.push(ajGridconfig.columns_fields[i]);
	}
	// 创建dataview
	ajGridconfig.dataView = new Slick.Data.DataView({
		inlineFilters : true
	});
	// 创建grid
	ajGridconfig.grid = new Slick.Grid("#archivediv", ajGridconfig.dataView, ajGridconfig.columns, ajGridconfig.options);
	//设置录入错误时提示。例如不能为空的字段
	ajGridconfig.grid.onValidationError.subscribe(function(e, args) {
//		alert(args.validationResults.msg);
		$.Zebra_Dialog(args.validationResults.msg, {
            'type':     'information',
            'title':    '系统提示',
            'buttons':  ['确定']
        });
	});
	// 设置grid的选择模式。行选择
	// grid.setSelectionModel(new Slick.RowSelectionModel());
	ajGridconfig.grid.setSelectionModel(new Slick.RowSelectionModel({
		selectActiveRow : false
	}));
	
	//设置键盘监听。ctrl + a 全选
	ajGridconfig.grid.onKeyDown.subscribe(function(e) {
		// select all rows on ctrl-a
		if (e.which != 65 || !e.ctrlKey) {
			return false;
		}
		var rows = [];
		for ( var i = 0; i < ajGridconfig.dataView.getLength(); i++) {
			rows.push(i);
		}
		ajGridconfig.grid.setSelectedRows(rows);
		e.preventDefault();
	});
	
	// 设置分页控件
	var pager_aj = new Slick.Controls.Pager(ajGridconfig.dataView, ajGridconfig.grid, $("#pager_aj"));
	// 注册grid的checkbox功能插件
	ajGridconfig.grid.registerPlugin(checkboxSelector);
	// 注册grid的自动提示插件。只在字段内容过长时出现省略号时提示
	ajGridconfig.grid.registerPlugin(new Slick.AutoTooltips());
	$("#inlineFilterPanel_aj").appendTo(ajGridconfig.grid.getTopPanel()).show();
	
	$("#txtSearch_aj").keyup(function(e) {
		Slick.GlobalEditorLock.cancelCurrentEdit();
		// clear on Esc
		if (e.which == 27) {
			this.value = "";
		}
		archiveCommon.clName = $("#selectfield_aj").val();
		archiveCommon.searchString = this.value;
		us.updateFilter(ajGridconfig.dataView);
	});
	//生成过滤选择字段
	for (var i=0;i<ajGridconfig.columns_fields.length;i++) {
		if (ajGridconfig.columns_fields[i].id != "rownum" && ajGridconfig.columns_fields[i].id != "isdoc" && ajGridconfig.columns_fields[i].id != "files") {
			$("#selectfield_aj").append("<option value='"+ajGridconfig.columns_fields[i].id+"'>"+ajGridconfig.columns_fields[i].name+"</option>");
		}
	}
	//声明新建行的系统默认值
	var newItemTemplate = {
			treeid	: archiveCommon.selectTreeid,
			isdoc	: "0"
	};
	//新建行时，将系统必须的默认值与字段默认值合并
	newItemTemplate = $.extend({},newItemTemplate,ajGridconfig.fieldsDefaultValue);
	
	//grid的添加新行事件
	ajGridconfig.grid.onAddNewRow.subscribe(function(e, args) {
		var item = $.extend({}, newItemTemplate, args.item);
		item.id = UUID.prototype.createUUID ();
		item.rownum = (ajGridconfig.dataView.getLength() + 1).toString();
		ajGridconfig.dataView.addItem(item);
		
		var par = "importData=[" + JSON.stringify(item) + "]&tableType=01";
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
	ajGridconfig.grid.onCellChange.subscribe(function(e, args) {
		var item = args.item;
		var par = "importData=[" + JSON.stringify(item) + "]&tableType=01";
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
	
	ajGridconfig.grid.onSort.subscribe(function(e, args) {
		archiveCommon.sortdir = args.sortAsc ? 1 : -1;
		archiveCommon.sortcol = args.sortCol.field;
		ajGridconfig.dataView.sort(us.comparer, args.sortAsc);
		
	});
	
	ajGridconfig.dataView.onRowCountChanged.subscribe(function(e, args) {
		ajGridconfig.grid.updateRowCount();
		ajGridconfig.grid.render();
	});

	ajGridconfig.dataView.onRowsChanged.subscribe(function(e, args) {
		ajGridconfig.grid.invalidateRows(args.rows);
		ajGridconfig.grid.render();
	});
	ajGridconfig.dataView.beginUpdate();
	ajGridconfig.dataView.setItems(ajGridconfig.rows);
	ajGridconfig.dataView.setFilterArgs({
		searchString : archiveCommon.searchString
	});
	ajGridconfig.dataView.setFilter(us.myFilter);
	ajGridconfig.dataView.endUpdate();
	
	ajGridconfig.dataView.syncGridSelection(ajGridconfig.grid, true);
	
	//生成toolbar_aj
	$('#toolbar_aj').toolbar({
		items:[{
			id:"insert",
			iconCls:"icon-page-add",
			disabled:false,
			text:"添加",
			handler:function(){
				ajGridconfig.grid.setOptions({
					autoEdit : true
				});
				ajGridconfig.grid.gotoCell(ajGridconfig.dataView.getLength(),4,true);
				
			}
		},{
			id:"update",
			iconCls:"icon-page-add",
			disabled:false,
			text:"修改",
			handler:function(){
				ajGridconfig.grid.setOptions({
					autoEdit : true
				});
			}
		},{
			id:"endupdate",
			iconCls:"icon-page-add",
			disabled:false,
			text:"取消修改",
			handler:function(){
				ajGridconfig.grid.setOptions({
					autoEdit : false
				});
			}
		},{
			text:"批量修改",
			iconCls:"icon-page-delete",
			handler:function(){
				var selectRows = ajGridconfig.grid.getSelectedRows();
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
									us.batchUpdate(ajGridconfig.grid,ajGridconfig.dataView,true,wnd,'01');
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
								
								for (var i=0;i<ajGridconfig.columns_fields.length;i++) {
									if (ajGridconfig.columns_fields[i].id != "rownum" && ajGridconfig.columns_fields[i].id != "isdoc" && ajGridconfig.columns_fields[i].id != "files") {
										selectContent.append("<option value='"+ajGridconfig.columns_fields[i].id+"'>"+ajGridconfig.columns_fields[i].name+"</option>");
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
				var selectRows = ajGridconfig.grid.getSelectedRows();
				selectRows.sort(function compare(a, b) {
					return b - a;
				});
				if (selectRows.length > 0) {
					$.Zebra_Dialog('确定要删除选中的 <span style="color:red">'+selectRows.length+'</span> 条案卷记录吗? <br><span style="color:red">注意：删除案卷记录，将同时删除案卷及案卷下所有文件数据、电子全文，请谨慎操作！</span> ', {
						'type':     'question',
		                'title':    '系统提示',
		                'buttons':  ['确定', '取消'],
		                'onClose':  function(caption) {
		                	if (caption == '确定') {
		                		var deleteRows = [];
		                		
		                		for ( var i = 0; i < selectRows.length; i++) {
		        					var item = ajGridconfig.dataView.getItem(selectRows[i]);
		        					deleteRows.push(item);
		        				}
		                		var par = "par=" + JSON.stringify(deleteRows) + "&tableType=01";
		                		$.post("deleteArchive.action",par,function(data){
		                				if (data == "SUCCESS") {
		                					for ( var i = 0; i < selectRows.length; i++) {
		    		        					var item = ajGridconfig.dataView.getItem(selectRows[i]);
		    		        					ajGridconfig.dataView.deleteItem(item.id);
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
			text:"导入",
			iconCls:"icon-page-copy",
			handler:function(){
				showArchiveImportTab('01');
			}
		},{
			text:"过滤",
			iconCls:"icon-page-copy",
			handler:function(){
				if ($(ajGridconfig.grid.getTopPanel()).is(":visible")) {
					ajGridconfig.grid.hideTopPanel();
				} else {
					ajGridconfig.grid.showTopPanel();
				}
			}
		},{
			text:"全文件",
			iconCls:"icon-page-copy",
			handler:function(){
				showWjTab("","1");
			}
		},{
			text:"刷新",
			iconCls:"icon-page-copy",
			handler:function(){
                ajGridconfig.dataView.refresh();
			}
		}]
	});
});



//// 声明列的数组
//var columns = [];
//var columns_fields = [];
//// 声明数据视图
//var dataView;
//var rows = [];
//var grid;
//var fieldsDefaultValue;
//// 创建grid配置对象
//var options = {
//	editable : true,
//	enableAddRow : true,
//	enableCellNavigation : true,
//	autoEdit : false,
//	enableColumnReorder : true,
//	topPanelHeight : 25
//};

var ajGridConfig = new us.archive.GridConfig();

// 创建字段验证
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

$(function(){
	//同步读取字段
	var par = "treeid=" + selectTreeid + "&tableType=01&importType=0";
	$.ajax({
		async : false,
		url : "getField.action?" + par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				ajGridConfig.columns_fields = fields;
				ajGridConfig.fieldsDefaultValue = fieldsDefaultValue;
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
	var par = "treeid=" + selectTreeid + "&tableType=01";
	$.ajax({
		async : false,
		url : "listArchive.action?" + par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				ajGridConfig.rows = rowList;
			} else {
				$.Zebra_Dialog('读取数据时出错，请尝试重新操作或与管理员联系! ', {
	                'type':     'information',
	                'title':    '系统提示',
	                'buttons':  ['确定']
	            });
			}
		}
	});
	
	$("#grid-header").html(selectTreeName + '_档案列表');
	
	// 创建checkbox列
	var checkboxSelector = new Slick.CheckboxSelectColumn({
		cssClass : "slick-cell-checkboxsel"
	});
	// 加入checkbox列
	ajGridConfig.columns.push(checkboxSelector.getColumnDefinition());
	// 加入其他列
	for ( var i = 0; i < ajGridConfig.columns_fields.length; i++) {
		ajGridConfig.columns.push(ajGridConfig.columns_fields[i]);
	}
	// 创建dataview
	ajGridConfig.dataView = new Slick.Data.DataView({
		inlineFilters : true
	});
	// 创建grid
	ajGridConfig.grid = new Slick.Grid("#archivediv", ajGridConfig.dataView, ajGridConfig.columns, ajGridConfig.options);
	//设置录入错误时提示。例如不能为空的字段
	ajGridConfig.grid.onValidationError.subscribe(function(e, args) {
//		alert(args.validationResults.msg);
		$.Zebra_Dialog(args.validationResults.msg, {
            'type':     'information',
            'title':    '系统提示',
            'buttons':  ['确定']
        });
	});
	// 设置grid的选择模式。行选择
	// grid.setSelectionModel(new Slick.RowSelectionModel());
	ajGridConfig.grid.setSelectionModel(new Slick.RowSelectionModel({
		selectActiveRow : false
	}));
	
	//设置键盘监听。ctrl + a 全选
	ajGridConfig.grid.onKeyDown.subscribe(function(e) {
		// select all rows on ctrl-a
		if (e.which != 65 || !e.ctrlKey) {
			return false;
		}
		var rows = [];
		for ( var i = 0; i < ajGridConfig.dataView.getLength(); i++) {
			rows.push(i);
		}
		ajGridConfig.grid.setSelectedRows(rows);
		e.preventDefault();
	});
	
	// 设置分页控件
	var pager_aj = new Slick.Controls.Pager(ajGridConfig.dataView, ajGridConfig.grid, $("#pager_aj"));
	// 注册grid的checkbox功能插件
	ajGridConfig.grid.registerPlugin(checkboxSelector);
	// 注册grid的自动提示插件。只在字段内容过长时出现省略号时提示
	ajGridConfig.grid.registerPlugin(new Slick.AutoTooltips());
	//声明新建行的系统默认值
	var newItemTemplate = {
			treeid	: selectTreeid,
			isdoc	: "0"
	};
	//新建行时，将系统必须的默认值与字段默认值合并
	newItemTemplate = $.extend({},newItemTemplate,ajGridConfig.fieldsDefaultValue);
	
	//grid的添加新行事件
	ajGridConfig.grid.onAddNewRow.subscribe(function(e, args) {
		var item = $.extend({}, newItemTemplate, args.item);
		item.id = UUID.prototype.createUUID ();
		item.rownum = (ajGridConfig.dataView.getLength() + 1).toString();
		ajGridConfig.dataView.addItem(item);
		
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
	ajGridConfig.grid.onCellChange.subscribe(function(e, args) {
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
	
	ajGridConfig.dataView.onRowCountChanged.subscribe(function(e, args) {
		ajGridConfig.grid.updateRowCount();
		ajGridConfig.grid.render();
	});

	ajGridConfig.dataView.onRowsChanged.subscribe(function(e, args) {
		ajGridConfig.grid.invalidateRows(args.rows);
		ajGridConfig.grid.render();
	});
	ajGridConfig.dataView.beginUpdate();
	ajGridConfig.dataView.setItems(ajGridConfig.rows);
	ajGridConfig.dataView.endUpdate();
	
	ajGridConfig.dataView.syncGridSelection(ajGridConfig.grid, true);
	
	//生成toolbar_aj
	$('#toolbar_aj').toolbar({
		items:[{
			id:"insert",
			iconCls:"icon-page-add",
			disabled:false,
			text:"添加",
			handler:function(){
				ajGridConfig.grid.setOptions({
					autoEdit : true
				});
				ajGridConfig.grid.gotoCell(ajGridConfig.dataView.getLength(),4,true);
				
			}
		},{
			id:"update",
			iconCls:"icon-page-add",
			disabled:false,
			text:"修改",
			handler:function(){
				ajGridConfig.grid.setOptions({
					autoEdit : true
				});
			}
		},{
			id:"endupdate",
			iconCls:"icon-page-add",
			disabled:false,
			text:"取消修改",
			handler:function(){
				ajGridConfig.grid.setOptions({
					autoEdit : false
				});
			}
		},{
			text:"批量更改",
			iconCls:"icon-page-delete",
			handler:function(){
				var selectRows = ajGridConfig.grid.getSelectedRows();
				selectRows.sort(function compare(a, b) {
					return b - a;
				});
				if (selectRows.length > 0) {
					$("#batchBtn").unbind("click"); 
					$("#batchBtn").click( function(){
						batchUpdate(ajGridConfig.grid,ajGridConfig.dataView,true);
					});
					
					$("#batchwindows").modal({
						overlayId	: 'osx-overlay',
						containerId	: 'osx-container',
						closeHTML	: null,
						minHeight	: 100,
						minWidth	: 410,
						opacity		: 40,
						overlayClose: true,
						onOpen : function (dialog) {
							dialog.data.show();
						    dialog.container.show();
							dialog.overlay.fadeIn('slow');
							for (var i=0;i<ajGridConfig.columns_fields.length;i++) {
								if (ajGridConfig.columns_fields[i].id != "rownum" && ajGridConfig.columns_fields[i].id != "isdoc" && ajGridConfig.columns_fields[i].id != "files") {
									$("#selectfield").append("<option value='"+ajGridConfig.columns_fields[i].id+"'>"+ajGridConfig.columns_fields[i].name+"</option>");
								}
							}
						},
						onClose:function (dialog) {
							dialog.data.fadeOut('slow', function () {
								dialog.container.slideUp(50, function () {
								  dialog.overlay.fadeOut(50, function () {
									$.modal.close(); // must call this!
								  });
								});
							  });
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
				var selectRows = ajGridConfig.grid.getSelectedRows();
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
		        					var item = ajGridConfig.dataView.getItem(selectRows[i]);
		        					deleteRows.push(item);
		        				}
		                		var par = "par=" + JSON.stringify(deleteRows) + "&tableType=01";
		                		$.post("deleteArchive.action",par,function(data){
		                				if (data == "SUCCESS") {
		                					for ( var i = 0; i < selectRows.length; i++) {
		    		        					var item = ajGridConfig.dataView.getItem(selectRows[i]);
		    		        					ajGridConfig.dataView.deleteItem(item.id);
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
			text:"案卷导入",
			iconCls:"icon-page-copy",
			handler:function(){
				showArchiveImportTab(1,1);
			}
		},{
			text:"刷新",
			iconCls:"icon-page-copy",
			handler:function(){

			}
		}]
	});
});

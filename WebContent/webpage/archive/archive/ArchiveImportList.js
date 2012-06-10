// 动态字段添加时，附带条件。例如默认值
var fieldsDefaultValue = [];

// 声明列的数组
var columns = [];
var columns_fields = [];
// 声明数据视图
var dataView;
var data = [];
var grid;
// 创建grid配置对象
var options = {
	editable : true,
	enableAddRow : true,
	enableCellNavigation : true,
	autoEdit : true,
	enableColumnReorder : true,
	topPanelHeight : 25
};

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

$(function() {
	var par = "treeid=" + selectTreeid + "&tableType=01&importType=1";
	$.ajax({
		async : false,
		url : "getField.action?" + par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				columns_fields = fields;
				fieldsDefaultValue = fieldsDefaultValue;
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
	
	$("#grid-header").html(selectTreeName + '_案卷导入');
	
	// 创建checkbox列
	var checkboxSelector = new Slick.CheckboxSelectColumn({
		cssClass : "slick-cell-checkboxsel"
	});
	// 加入checkbox列
	columns.push(checkboxSelector.getColumnDefinition());
	// 加入其他列
	for ( var i = 0; i < columns_fields.length; i++) {
		columns.push(columns_fields[i]);
	}
	// 创建dataview
	dataView = new Slick.Data.DataView({
		inlineFilters : true
	});
	
	// 创建grid
	grid = new Slick.Grid("#archiveImportDiv", dataView, columns, options);

//	grid.onValidationError.subscribe(handleValidationError);
	// 设置grid的选择模式。行选择
	// grid.setSelectionModel(new Slick.RowSelectionModel());
	grid.setSelectionModel(new Slick.RowSelectionModel({
		selectActiveRow : false
	}));
	// 设置分页控件
	var pager = new Slick.Controls.Pager(dataView, grid, $("#pager"));
	// 注册grid的checkbox功能插件
	grid.registerPlugin(checkboxSelector);
	// 注册grid的自动提示插件。只在字段内容过长时出现省略号时提示
	grid.registerPlugin(new Slick.AutoTooltips());
	
	dataView.onRowCountChanged.subscribe(function(e, args) {
		grid.updateRowCount();
		grid.render();
	});

	dataView.onRowsChanged.subscribe(function(e, args) {
		grid.invalidateRows(args.rows);
		grid.render();
	});
	
	//生成toolbar
	$('#toolbar').toolbar({
		items:[{
			id:"select",
			iconCls:"icon-page-add",
			disabled:false,
			text:"选择文件",
			handler:function(){
				$("#selectfilewindows").modal({
					overlayId: 'osx-overlay',
					containerId: 'osx-container',
					closeHTML: null,
					minHeight: 100,
					minWidth: 410,
					opacity: 40,
					overlayClose: true,
					onOpen : function (dialog) {
						dialog.data.show();
					    dialog.container.show();
						dialog.overlay.fadeIn('slow');
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
		},{
			text:"批量修改",
			iconCls:"icon-page-delete",
			handler:function(){
				var selectRows = grid.getSelectedRows();
				selectRows.sort(function compare(a, b) {
					return b - a;
				});
				if (selectRows.length > 0) {
					$("#selectupdatewindows").modal({
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
							for (var i=0;i<columns_fields.length;i++) {
								if (columns_fields[i].id != "ROWNUM") {
									$("#selectfield").append("<option value='"+columns_fields[i].id+"'>"+columns_fields[i].name+"</option>");
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
				var selectRows = grid.getSelectedRows();
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
		        					var item = dataView.getItem(selectRows[i]);
		        					dataView.deleteItem(item.id);
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
				grid.getEditController().cancelCurrentEdit();
				var a = dataView.getItems();
				if (a.length > 0) {
					var par = "importData=" + JSON.stringify(a) + "&tableType=01";

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
})

function importArchive() {
	var file = $('#selectfile').val();
	if (!file) {
		alert("请选择导入到文件。");
		return;
	}
	// 判断上传文件类型
	var a = selectfile_Validator($('#selectfile').val());
	if (a) {
		$('#importArchiveForm').attr('action',
				'upload.action?treeid=' + selectTreeid + '&tableType=01');
		$('#importArchiveForm').submit();
	}
}

function showCallback(backType, jsonStr) {

	if (backType == "failure") {
		alert(jsonStr);
	} else {
		var json = JSON.parse(jsonStr);
		dataView.beginUpdate();
		dataView.setItems(json);
		dataView.endUpdate();
		grid.registerPlugin(new Slick.AutoTooltips());
	}
	//关闭windows
	$.modal.close();
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

function importUpdate() {
	var selectRows = grid.getSelectedRows();
	selectRows.sort(function compare(a, b) {
		return b - a;
	});
	
	var selectFieldName = $("#selectfield").val(); 
	var updateTxt = $("#updatetxt").val();
	for ( var i = 0; i < selectRows.length; i++) {
		var item = dataView.getItem(selectRows[i]);
		for(p in item){
//			alert(i);//i就是test的属性名
//			alert(item[i]);//test.i就是属性值
			if (p == selectFieldName) {
				item[p] = updateTxt;
				break;
			}
		}
		dataView.updateItem(item.id,item);
	}
}

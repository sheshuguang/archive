

var importGridConfig = new us.archive.ui.Gridconfig();
//select import file excel type
function selectfile() {
	$( "#selectfilewindows" ).dialog({
		autoOpen: false,
		height: 200,
		width: 400,
		title:'选择文件',
		modal: true,
		resizable:false,
		create: function(event, ui) {
			
		},
		open:function(event,ui) {
			$('#selectfile').val('');
		},
		buttons: {
			"提交": function() {
				importArchive();
				
			},
			"关闭": function() {
				$( this ).dialog( "close" );
			}
//			测试打开dialog里iframe加载其他页面
//			,
//			"test":function() {
//				$("<div></div>").append($("<iframe width='100%' height='100%'  id='Preview' src='dispatch.action?page=/webpage/archive/archive/ArchiveBatchAttachment.html' ></iframe>")).dialog({ 
//					autoOpen: true, 
//					modal: true, 
//					height: 500,
//					width: 800,
//					resizable:true ,
//					title: "打印预览如果需要打印请点击打印按钮"
//				 });
//			}
		},
		close: function() {
			
		}
	});
	$( "#selectfilewindows" ).dialog('open');
}
//import data batch update
function importbatchupdate() {
	var selectRows = importGridConfig.grid.getSelectedRows();
	selectRows.sort(function compare(a, b) {
		return b - a;
	});
	if (selectRows.length > 0) {
		$( "#batchwindows" ).dialog({
			autoOpen: false,
			height: 280,
			width: 500,
			title:'批量修改',
			modal: true,
			resizable:false,
			create: function(event, ui) {
				$("#selectfield").empty();
				for (var i=0;i<importGridConfig.columns_fields.length;i++) {
					if (importGridConfig.columns_fields[i].id != "rownum" && importGridConfig.columns_fields[i].id != "isdoc" && ajGridconfig.columns_fields[i].id != "files") {
						$("#selectfield").append("<option value='"+importGridConfig.columns_fields[i].id+"'>"+importGridConfig.columns_fields[i].name+"</option>");
					}
				}
			},
			open:function(event,ui) {
				$("#updatetxt").val("");
			},
			buttons: {
				"提交": function() {
					us.batchUpdate(importGridConfig.grid,importGridConfig.dataView,false,archiveCommon.tableType);
				},
				"关闭": function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				
			}
		});
		$( "#batchwindows" ).dialog('open');
		
	}
	else {
		us.openalert('请选择要修改的数据。 ','系统提示','alertbody alert_Information');
	}
}
//import data delete
function importdelete() {
	var selectRows = importGridConfig.grid.getSelectedRows();
	selectRows.sort(function compare(a, b) {
		return b - a;
	});
	if (selectRows.length > 0) {
		us.openconfirm('确定要删除选中的 <span style="color:red">'+selectRows.length+'</span>' + 
				' 条数据吗? ','系统提示',
				function() {
					for ( var i = 0; i < selectRows.length; i++) {
						var item = importGridConfig.dataView.getItem(selectRows[i]);
						importGridConfig.dataView.deleteItem(item.id);
					}
				},
				function() {
					
				}
		);
	}
	else {
		us.openalert('请选择要删除的数据。 ','系统提示','alertbody alert_Information');
	}
}
// import data save
function importsave() {
	importGridConfig.grid.getEditorLock().commitCurrentEdit();
	var a = importGridConfig.dataView.getItems();
	if (a.length > 0) {
		var par = "importData=" + JSON.stringify(a) + "&tableType=" + archiveCommon.tableType;

		$.post("saveImportArchive.action",par,function(data){
			us.openalert(data,'系统提示','alertbody alert_Information');
			}
		);
	}
	else {
		us.openalert('没有找到导入数据.<br>请重新读取Excel导入文件或与管理员联系。 ','系统提示','alertbody alert_Information');
	}
}


$(function() {
	setGridResize();
//	$("#importgrid").css({  height: $('#center').height()-25});
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
				us.openalert('读取字段信息时出错，请关闭浏览器，重新登录尝试或与管理员联系! ','系统提示','alertbody alert_Information');
			}
		}
	});
	var importTitle = "";
//	importTitle = archiveCommon.selectTreeName + '_文件导入';
	if (archiveCommon.tableType == '01') {
		importTitle = archiveCommon.selectTreeName + '_案卷导入';
	}
	else {
		importTitle = archiveCommon.selectTreeName + '_文件导入';
	}
	$("#grid-header_import").html(importTitle);
	
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
	var pager_import = new Slick.Controls.Pager(importGridConfig.dataView, importGridConfig.grid, $("#pager_import"));
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
	
});

function importArchive() {
//	var wnds = $.window.getAll();
//	var aa = wnds[wnds.length -1].getContainer();
//	var bb = aa.find("#selectfile");
//	
//	
//	var container = w.getContainer(); // 抓到window裡最外層div物件
//	var fileContent = container.find("#selectfile"); // 尋找container底下的指定input element
//	var importArchiveForm = container.find("#importArchiveForm");
	
	var importArchiveForm = $('#importArchiveForm');
	
	var filePath = $('#selectfile').val();
	if (!filePath) {
		us.openalert('请选择导入到文件。','系统提示','alertbody alert_Information');
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
		importArchiveForm.attr('action',url);
		importArchiveForm.submit();
	}
}

function showCallback(backType, jsonStr) {

	if (backType == "failure") {
		us.openalert(jsonStr,'系统提示','alertbody alert_Information');
	} else {
		var json = JSON.parse(jsonStr);
		importGridConfig.dataView.beginUpdate();
		importGridConfig.dataView.setItems(json);
		importGridConfig.dataView.endUpdate();
		importGridConfig.grid.registerPlugin(new Slick.AutoTooltips());
	}
//	$.window.closeAll();
}

function selectfile_Validator(selectfile) {
	if (selectfile == " ") {
		us.openalert('请选择Excel类型的导入文件！','系统提示','alertbody alert_Information');
		return false;
	}
	var last = selectfile.match(/^(.*)(\.)(.{1,8})$/)[3]; // 检查上传文件格式
	last = last.toUpperCase();
	if (last == "XLS") {
	} else {
		us.openalert('只能上传Excel文件,请重新选择！ ','系统提示','alertbody alert_Information');
		return false;
	}
	return true;
}



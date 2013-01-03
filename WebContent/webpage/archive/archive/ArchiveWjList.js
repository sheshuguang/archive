

var wjGridconfig = new us.archive.ui.Gridconfig();
//wj insert a new row
function wjadd() {
	wjGridconfig.grid.setOptions({
		autoEdit : true
	});
	wjGridconfig.grid.gotoCell(wjGridconfig.dataView.getLength(),3,true);
}
//wj data delete
function wjdelete() {
	var selectRows = wjGridconfig.grid.getSelectedRows();
	selectRows.sort(function compare(a, b) {
		return b - a;
	});
	if (selectRows.length > 0) {
		us.openconfirm('确定要删除选中的 <span style="color:red">'+selectRows.length+'</span>' + 
				' 条案卷记录吗? <br><span style="color:red">注意：删除文件记录，将同时删除文件及文件下所有数据、电子全文，请谨慎操作！</span> ','系统提示',
				function() {
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
		        				us.openalert('删除成功。 ','系统提示','alertbody alert_Information');
	        				}
	        				else {
	        					us.openalert(data,'系统提示','alertbody alert_Information');
	        				}
	        			}
	        		);
				},
				function() {
					
				}
		);
	}
	else {
		us.openalert("请选择要删除的数据",'系统提示','alertbody alert_Information');
	}
}
//open wj grid update mode
function wjupdate() {
	wjGridconfig.grid.setOptions({
		autoEdit : true
	});
}
//close wj grid update mode
function wjendupdate() {
	wjGridconfig.grid.setOptions({
		autoEdit : false
	});
}
//open wj batch update dialog
function wjbatchupdate() {
	var selectRows = wjGridconfig.grid.getSelectedRows();
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
				for (var i=0;i<wjGridconfig.columns_fields.length;i++) {
					if (wjGridconfig.columns_fields[i].id != "rownum" && wjGridconfig.columns_fields[i].id != "isdoc" && wjGridconfig.columns_fields[i].id != "files") {
						$("#selectfield").append("<option value='"+wjGridconfig.columns_fields[i].id+"'>"+wjGridconfig.columns_fields[i].name+"</option>");
					}
				}
			},
			open:function(event,ui) {
				$("#updatetxt").val("");
			},
			buttons: {
				"提交": function() {
					us.batchUpdate(wjGridconfig.grid,wjGridconfig.dataView,true,'02');
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
//open wj import data tab
function wjimport() {
	showArchiveImportTab('02');
}
//show wj grid filter div
function wjfilter() {
	if ($(wjGridconfig.grid.getTopPanel()).is(":visible")) {
		wjGridconfig.grid.hideTopPanel();
	} else {
		wjGridconfig.grid.showTopPanel();
	}
}
// wj Single archive link files
function wjlinkfile() {
	var selectRows = wjGridconfig.grid.getSelectedRows();
	if (selectRows.length == 0) {
		us.openalert('请选择要挂接的档案记录! ',
				'系统提示',
				'alertbody alert_Information'
		);
		return ;
	}
	else if (selectRows.length > 1) {
		us.openalert('只能选择一条要挂接的档案记录! ',
				'系统提示',
				'alertbody alert_Information'
		);
		return ;
	}
	var item = wjGridconfig.dataView.getItem(selectRows[0]);
	showDocwindow(item.id,wjGridconfig.selectTableid);
}

//open batch att tab
function wjbatchatt() {
	var selectRows = wjGridconfig.grid.getSelectedRows();
	if (selectRows.length > 0) {
		selectRows.sort(function compare(a, b) {
			return a - b;
		});
		archiveCommon.showBatchAttachment(wjGridconfig,'02',selectRows);
	}
	else {
		us.openalert('请选择要批量挂接的档案记录! ',
				'系统提示',
				'alertbody alert_Information'
		);
	}
}
//refresh wjdata
function wjrefresh() {
	var data = [];
	readwjdata();
	wjGridconfig.dataView.setItems(data);
	wjGridconfig.dataView.setItems(wjGridconfig.rows);
}

//read wj Archive data
function readwjdata() {
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
				us.openalert('<span style="color:red">读取数据时出错!<span></br>请尝试重新操作或与管理员联系。',
						'系统提示',
						'alertbody alert_Information'
				);
			}
		}
	});
}

$(function(){
	setGridResize();
//	$("#wjGrid").css({  height: $('#center').height()-25});
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
				wjGridconfig.selectTableid = tableid;
			}
			else {
				us.openalert('<span style="color:red">读取字段信息时出错!<span></br>请关闭浏览器，重新登录尝试或与管理员联系。',
						'系统提示',
						'alertbody alert_Information'
				);
			}
        }
	});
	readwjdata();
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
		us.openalert(args.validationResults.msg,
				'系统提示',
				'alertbody alert_Information'
		);
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
					us.openalert(data,
							'系统提示',
							'alertbody alert_Information'
					);
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
					us.openalert(data,
							'系统提示',
							'alertbody alert_Information'
					);
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
	
	setGridResize();
	
	//如果是显示全部文件。屏蔽导入按钮
	if (archiveCommon.isAllWj == '1') {
//		$("#importWjBtn").linkbutton('disable');
//		$("#insert_wj").linkbutton('disable');
		$('#wjimport').button("disable"); 
		$('#wjadd').button("disable"); 
		
	}
});

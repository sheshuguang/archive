

var ajGridconfig = new us.archive.ui.Gridconfig();

$(function(){
	//生成grid的toolbar_aj
	$( "#add" ).button({
		text: false,
		icons: {
			primary: "ui-icon-plusthick"
		}
	})
	.click(function() {
		ajGridconfig.grid.setOptions({
			autoEdit : true
		});
		ajGridconfig.grid.gotoCell(ajGridconfig.dataView.getLength(),4,true);
	});
	$( "#update" ).button({
		text: false,
		icons: {
			primary: "ui-icon-wrench"
		}
	}).click(function() {
		ajGridconfig.grid.setOptions({
			autoEdit : true
		});
	});
	$( "#endupdate" ).button({
		text: false,
		icons: {
			primary: "ui-icon-grip-solid-horizontal"
		}
	})
	.click(function() {
		ajGridconfig.grid.setOptions({
			autoEdit : false
		});
	});
	$( "#batchupdate" ).button({
		text: false,
		icons: {
			primary: "ui-icon-gear"
		}
	})
	.click(function() {
		var selectRows = ajGridconfig.grid.getSelectedRows();
		selectRows.sort(function compare(a, b) {
			return b - a;
		});
		if (selectRows.length > 0) {
			$( "#batchwindows" ).dialog({
				autoOpen: false,
				height: 250,
				width: 300,
				title:'批量修改',
				modal: true,
				resizable:false,
				create: function(event, ui) {
					$("#selectfield").empty();
					for (var i=0;i<ajGridconfig.columns_fields.length;i++) {
						if (ajGridconfig.columns_fields[i].id != "rownum" && ajGridconfig.columns_fields[i].id != "isdoc" && ajGridconfig.columns_fields[i].id != "files") {
							$("#selectfield").append("<option value='"+ajGridconfig.columns_fields[i].id+"'>"+ajGridconfig.columns_fields[i].name+"</option>");
						}
					}
				},
				open:function(event,ui) {
					$("#updatetxt").val("");
				},
				buttons: {
					"提交": function() {
						us.batchUpdate(ajGridconfig.grid,ajGridconfig.dataView,true,'01');
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
	});
	$( "#delete" ).button({
		text: false,
		icons: {
			primary: "ui-icon-trash"
		}
	}).click(function() {
		var selectRows = ajGridconfig.grid.getSelectedRows();
		selectRows.sort(function compare(a, b) {
			return b - a;
		});
		if (selectRows.length > 0) {
			us.openconfirm('确定要删除选中的 <span style="color:red">'+selectRows.length+'</span>' + 
					' 条案卷记录吗? <br><span style="color:red">注意：删除案卷记录，将同时删除案卷及案卷下所有文件数据、电子全文，请谨慎操作！</span> ','系统提示',
					function() {
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
			us.openalert('请选择要删除的数据。 ','系统提示','alertbody alert_Information');
		}
	});
	$( "#import" ).button({
		text: false,
		icons: {
			primary: "ui-icon-calculator"
		}
	}).click(function() {
		showArchiveImportTab('01');
	});
	$( "#Filter" ).button({
		text: false,
		icons: {
			primary: "ui-icon-circle-zoomout"
		}
	}).click(function() {
		if ($(ajGridconfig.grid.getTopPanel()).is(":visible")) {
			ajGridconfig.grid.hideTopPanel();
		} else {
			ajGridconfig.grid.showTopPanel();
		}
	});
	$( "#allwj" ).button({
		text: false,
		icons: {
			primary: "ui-icon-script"
		}
	}).click(function() {
		showWjTab("","1");
	});
	$( "#batchatt" ).button({
		text: false,
		icons: {
			primary: "ui-icon-flag"
		}
	}).click(function() {
		var selectRows = ajGridconfig.grid.getSelectedRows();
		if (selectRows.length > 0) {
			selectRows.sort(function compare(a, b) {
				return a - b;
			});
			archiveCommon.showBatchAttachment(ajGridconfig,'01',selectRows);
		}
		else {
			us.openalert('请选择要批量挂接的档案记录! ',
					'系统提示',
					'alertbody alert_Information'
			);
		}
	});
	
	
	$("#ajGrid").css({  height: $('#center').height()-25});
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
				var temptype = templettype;
				if (temptype == "F") {
					$('#allwj').button("disable"); 
				}
			} else {
				us.openalert('<span style="color:red">读取字段信息时出错.</span></br>请关闭浏览器，重新登录尝试或与管理员联系!',
						'系统提示',
						'alertbody alert_Information'
				);
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
				us.openalert('<span style="color:red">读取数据时出错.</span></br>请关闭浏览器，重新登录尝试或与管理员联系!',
						'系统提示',
						'alertbody alert_Information'
				);
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
		us.openalert(args.validationResults.msg,
				'系统提示',
				'alertbody alert_Information'
		);
	});
	// 设置grid的选择模式。行选择
	// grid.setSelectionModel(new Slick.RowSelectionModel());
	ajGridconfig.grid.setSelectionModel(new Slick.RowSelectionModel({
		selectActiveRow : false
	}));
//	ajGridconfig.grid.setSelectionModel(new Slick.CellSelectionModel());
	
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
					us.openalert(data,
							'系统提示',
							'alertbody alert_Information'
					);
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
					us.openalert(data,
							'系统提示',
							'alertbody alert_Information'
					);
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
	
});

function showDocDelectButton(b,id) {
	if (b) {
		$("#" + id).css("display","inline-block");
	}
	else {
		$("#" + id).css("display","none");
	}
}

/*
 * 生成doc现实的列表
 * onMouseOut=\"this.className='Off'\" onMouseOver=\"this.className='Up'\"
 */
function getDoclist(row) {
	var str = "<li class=\"docli\" onMouseOut=\"showDocDelectButton(false,'"+row.docid+"')\" onMouseOver=\"showDocDelectButton(true,'"+row.docid+"')\">";
	str += "<div class=\"docdiv\"><a href=\"downDoc.action?docId="+ row.docid +"\">";
	var docType = row.docext;
	var typeCss = "";
	if (docType == "DOC" || docType == "XLS" || docType=="PPT") {
		typeCss = "file-icon-office";
	}
	else if (docType == "TXT") {
		typeCss = "file-icon-text";
	}
	else if (docType == "JPG" || docType == "GIF" || docType=="BMP" || docType=="JPEG" || docType=="TIF") {
		typeCss = "file-icon-image";
	}
	else if (docType == "PDF") {
		typeCss = "file-icon-pdf";
	}
	else if (docType == "ZIP") {
		typeCss = "file-icon-zip";
	}
	else if (docType == "RAR") {
		typeCss = "file-icon-rar";
	}
	else if (docType == "FLASH") {
		typeCss = "file-icon-flash";
	}
	else {
		
	}
	str += "<img class=\"file-icon "+typeCss+" \">";
	str += "</a></div>";
	var name = row.docoldname;
//	if (row.docoldname.length > 8) {
//		name = row.docoldname.substr(0,8) + "..." + "." + docType;
//	}
	str += "<div title=\""+row.docoldname+"\"><div class=\"docfilename\"><a href=\"downDoc.action?docId="+ row.docid +"\">" + name + "</a></div></div>";
	str += "<div><button type=\"button\" id=\""+row.docid+"\" style=\"display:none\" onClick=\"delectDoc('"+row.docid+"')\" class=\"btn btn-danger btn-small\">删除</button></div>";
	str += "</li>";
	return str;
}



// 打开电子全文windows
function showDocwindow(id, tableid) {
	archiveCommon.selectRowid = id;
	archiveCommon.selectTableid = tableid;
	
	var par = "selectRowid="+ id + "&tableid="+tableid;
	var rowList = [];
	//同步读取数据
	$.ajax({
		async : false,
		url : "listLinkDoc.action?"+ par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				rowList = eval(data);
				$("#doclist").html("");
				for (var i=0;i<rowList.length;i++) {
					$("#doclist").append(getDoclist(rowList[i]));
				}
			} else {
				us.openalert('读取数据时出错，请尝试重新操作或与管理员联系! ','系统提示','alertbody alert_Information');
			}
		}
	});
	
	$("#docwindows").dialog('option', 'title', '电子全文列表--(共 ' + rowList.length + "个文件)");
	$("#docwindows").dialog( "open" );
}

function delectDoc(id) {
	//同步读取数据
	$.ajax({
		async : false,
		url : "docDelete.action?docId="+ id,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				us.openalert('删除文件完毕! ','系统提示','alertbody alert_Information');
			} else {
				us.openalert('删除文件时出错，请尝试重新操作或与管理员联系! ','系统提示','alertbody alert_Information');
			}
		}
	});
	
	var par = "selectRowid="+ archiveCommon.selectRowid + "&tableid="+archiveCommon.selectTableid;
	var rowList = [];
	//同步读取数据
	$.ajax({
		async : false,
		url : "listLinkDoc.action?"+ par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			alert("d  " + data);
			if (data != "error") {
				alert(data);
				rowList = eval(data);
				$("#doclist").html("");
				for (var i=0;i<rowList.length;i++) {
					$("#doclist").append(getDoclist(rowList[i]));
				}
			} else {
				us.openalert('读取数据时出错，请尝试重新操作或与管理员联系! ','系统提示','alertbody alert_Information');
			}
		}
	});
	
	$("#docwindows").dialog('option', 'title', '电子全文列表--(共 ' + rowList.length + "个文件)");
}


//选择的档案条目grid
var attGridConfig = new us.archive.ui.Gridconfig();
//没有对应的全文grid
var attNoGridConfig = new us.archive.ui.Gridconfig();
//对应的全文grid
var attYesGridConfig = new us.archive.ui.Gridconfig();

archiveCommon.yesItems = [];

//set att condition 
function setcondition() {
	$( "#setrequwindows" ).dialog({
		autoOpen: false,
		height: 280,
		width: 500,
		title:'设置挂接条件',
		modal: true,
		resizable:false,
		create: function(event, ui) {
//			$("#selectfield").empty();
//			for (var i=0;i<ajGridconfig.columns_fields.length;i++) {
//				if (ajGridconfig.columns_fields[i].id != "rownum" && ajGridconfig.columns_fields[i].id != "isdoc" && ajGridconfig.columns_fields[i].id != "files") {
//					$("#selectfield").append("<option value='"+ajGridconfig.columns_fields[i].id+"'>"+ajGridconfig.columns_fields[i].name+"</option>");
//				}
//			}
		},
		open:function(event,ui) {
//			$("#updatetxt").val("");
		},
		buttons: {
			"提交": function() {
//				us.batchUpdate(ajGridconfig.grid,ajGridconfig.dataView,true,'01');
			},
			"关闭": function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			
		}
	});
	$( "#setrequwindows" ).dialog('open');
}
//move archive
function deletearchive() {
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
		us.openalert('<span style="color:red">请选择要移除的数据!<span>',
				'系统提示',
				'alertbody alert_Information'
		);
	}
}
//save archive att 
function savearchive() {
	var a = archiveCommon.yesItems;
	if (a.length > 0) {
		var bb = encodeURIComponent(JSON.stringify(a));
		var par = "items=" + bb + "&tableType=" + archiveCommon.tableType + "&treeid=" + archiveCommon.selectTreeid;
		$.post("saveBatchAttArchive.action",par,function(data){
			us.openalert(data,
					'系统提示',
					'alertbody alert_Information'
			);
		});
	}
	else {
		us.openalert('<span style="color:red">没有找到导入数据!<span></br>请重新挂接或与管理员联系。',
				'系统提示',
				'alertbody alert_Information'
		);
	}
}
// atuo archive att file
function autofile() {
	//得到档案记录items
	var attItems = attGridConfig.dataView.getItems();
	if (attItems.length < 1) {
		us.openalert('没有找到要挂接的档案记录，请重新操作。',
				'系统提示',
				'alertbody alert_Information'
		);
		return;
	}
	//得到未挂接的全文items
	var attNoItems = attNoGridConfig.dataView.getItems();
	if (attNoItems.length < 1) {
		us.openalert('没有找到要挂接的全文记录，请重新操作。',
				'系统提示',
				'alertbody alert_Information'
		);
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
					item.treeid = archiveCommon.selectTreeid;
					attYesGridConfig.dataView.addItem(item);
                    archiveCommon.yesItems.push(item);
					//
					attNoGridConfig.dataView.deleteItem(item.docid);
				}
			}

		}
	}
//	archiveCommon.yesItems = attYesGridConfig.dataView.getItems();
}
//self archive att file
function selffile() {
	var attSelectRows = attGridConfig.grid.getSelectedRows();
	if (attSelectRows.length != 1) {
		us.openalert('手动挂接只能选择一个档案记录，请重新选择。',
				'系统提示',
				'alertbody alert_Information'
		);
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
			item.treeid = archiveCommon.selectTreeid;
			attYesGridConfig.dataView.addItem(item);
            archiveCommon.yesItems.push(item);
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
		us.openalert('请选择要手动挂接的电子全文数据。',
				'系统提示',
				'alertbody alert_Information'
		);
	}
//	archiveCommon.yesItems = attYesGridConfig.dataView.getItems();
}
//open upload file dialog
function uploadfile() {
	$("#uploader").pluploadQueue({
        // General settings
        runtimes : 'flash,html5,html4',
        url : 'docUpload.action',
        max_file_size : '200mb',
        //缩略图形式。
//        resize : {width :32, height : 32, quality : 90},
//        unique_names : true,
        chunk_size: '2mb',
        // Specify what files to browse for
//        filters : [
//                   {title : "所有文件", extensions : "*.*"},
//                   {title : "Image files", extensions : "jpg,gif,png"},
//		           {title : "rar files", extensions : "rar"},
//		           {title : "pdf files", extensions : "pdf"},
//		           {title : "office files", extensions : "docx,ppt,pptx,xls,xlsx"},
//		           {title : "exe files", extensions : "exe"},
//		           {title : "Zip files", extensions : "zip,rar,exe"}
//            
//        ],

        // Flash settings
        flash_swf_url : '../../js/plupload/js/plupload.flash.swf'
        // Silverlight settings
//        silverlight_xap_url : '/example/plupload/js/plupload.silverlight.xap'
    });
    $('#formId').submit(function(e) {
        var uploader = $('#uploader').pluploadQueue();
        if (uploader.files.length > 0) {
            // When all files are uploaded submit form
            uploader.bind('StateChanged', function() {
                if (uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {
                    $('#formId')[0].submit();
                }
            });
            uploader.start();
        } else {
        	us.openalert('请先上传数据文件。',
    				'系统提示',
    				'alertbody alert_Information'
    		);
        }
        return false;
    });
    $( "#uploadFile" ).dialog( "open");
}
//move no att
function deletefile() {
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
		us.openalert('请选择要移除的未挂接全文数据。',
				'系统提示',
				'alertbody alert_Information'
		);
	}
}
//move yes att file
function deleteyesfile() {
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
            for(var j=0;j<archiveCommon.yesItems.length;j++) {
                var tempItem = archiveCommon.yesItems[j];
                if (tempItem.docid == item.docid) {
                    archiveCommon.yesItems.splice(j,1);
                    break;
                }
            }
		}
	}
	else {
		us.openalert('请选择要移除的数据。',
				'系统提示',
				'alertbody alert_Information'
		);
	}
//	archiveCommon.yesItems = attYesGridConfig.dataView.getItems();
}
$(function() {
	$("#batchlayout").css({  height: $('#center').height()-25});
	//声明上传控件。#uploadFile，作为公共的资源，在archiveMgr.js里
	$("#uploadFile").dialog({
        autoOpen: false,
        height: 460,
        width: 630,
        modal: true,
        buttons: {
            '关闭': function() {
                $( this ).dialog( "close" );
            }
        },
        close: function() {
        	getNoAttData();
        	attNoGridConfig.dataView.beginUpdate();
        	attNoGridConfig.dataView.setItems(attNoGridConfig.rows);
        	attNoGridConfig.dataView.endUpdate();
        }
    });
	
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
	
	//生成档案grid
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
				us.openalert('请选择要移除的数据。',
						'系统提示',
						'alertbody alert_Information'
				);
				us.openalert('<span style="color:red">读取字段信息时出错!<span></br>请关闭浏览器，重新登录尝试或与管理员联系。',
						'系统提示',
						'alertbody alert_Information'
				);
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
	
	getNoAttData();
	attNoGridConfig.columns = [
         {id: "docoldname", name: "文件名", field: "docoldname" },
         {id: "docext", name: "文件类型", field: "docext" },
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
                   {id: "docext", name: "文件类型", field: "docext" },
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
          	
});

function getNoAttData() {
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
				us.openalert('<span style="color:red">读取全文库中未挂接数据时出错!<span></br>请尝试重新操作或与管理员联系。',
						'系统提示',
						'alertbody alert_Information'
				);
			}
		}
	});
}


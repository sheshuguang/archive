
//选择的档案条目grid
var attGridConfig = new us.archive.ui.Gridconfig();
//没有对应的全文grid
var attNoGridConfig = new us.archive.ui.Gridconfig();
//对应的全文grid
var attYesGridConfig = new us.archive.ui.Gridconfig();

archiveCommon.yesItems = [];


$(function() {
	$("#batchlayout").css({  height: $('#center').height()-25});
	//声明上传控件。#uploadFile，作为公共的资源，在archiveMgr.js里
	$("#uploadFile").dialog({
        autoOpen: false,
        height: 410,
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
	

	//生成grid的toolbar_archiveAttachment
    //设置挂接条件
	$( "#setcondition" ).button({
		text: false,
		icons: {
			primary: "ui-icon-info"
		}
	}).click(function() {
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
//		    	var container = wnd.getContainer(); // 抓到window裡最外層div物件
//		    	//修改导入按钮点击事件
//		    	var importBtn = container.find("#importBtn"); // 尋找container底下的指定更改按钮
//				//给更改按钮赋予点击事件(因为存在多个grid，所以更改按钮的参数是临时赋予的)
//		    	importBtn.unbind("click");
//		    	importBtn.click( function(){
//		    		importArchive(wnd);
//				});
//				//修改关闭按钮事件
//				var closeBtn = container.find("#closeBtn"); // 尋找container底下的指定更改按钮
//				//给更改按钮赋予点击事件(因为存在多个grid，所以更改按钮的参数是临时赋予的)
//				closeBtn.unbind("click");
//				closeBtn.click( function(){
//					wnd.close();
//				});
		    }
		});
	});
    //移除批量挂接档案
	$( "#deletearchive" ).button({
		text: false,
		icons: {
			primary: "ui-icon-closethick"
		}
	}).click(function() {
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
	});
    //保存挂接结果
	$( "#savearchive" ).button({
		text: false,
		icons: {
			primary: "ui-icon-disk"
		}
	}).click(function() {
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
	});

	//生成grid的toolbar_archiveAttachment_no
    //自动挂接
	$( "#autofile" ).button({
		text: false,
		icons: {
			primary: "ui-icon-flag"
		}
	}).click(function() {
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
                        archiveCommon.yesItems.push(item);
						//
						attNoGridConfig.dataView.deleteItem(item.docid);
					}
				}

			}
		}
//		archiveCommon.yesItems = attYesGridConfig.dataView.getItems();
	});
    //手动挂接
	$( "#selffile" ).button({
		text: false,
		icons: {
			primary: "ui-icon-circle-arrow-w"
		}
	}).click(function() {
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
			$.Zebra_Dialog('请选择要手动挂接的电子全文数据。 ', {
                'type':     'information',
                'title':    '系统提示',
                'buttons':  ['确定']
            });
		}
//		archiveCommon.yesItems = attYesGridConfig.dataView.getItems();
	});
    //上传文件
	$( "#uploadfile" ).button({
		text: false,
		icons: {
			primary: "ui-icon-circle-arrow-n"
		}
	}).click(function() {
            $("#uploader").pluploadQueue({
                // General settings
                runtimes : 'flash,html5,html4',
                url : 'docUpload.action',
                max_file_size : '200mb',
//                unique_names : true,
                chunk_size: '2mb',
                // Specify what files to browse for
                filters : [
                           {title : "所有文件", extensions : "*.*"},
		                   {title : "Image files", extensions : "jpg,gif,png"},
				           {title : "rar files", extensions : "rar"},
				           {title : "pdf files", extensions : "pdf"},
				           {title : "office files", extensions : "doc,docx,ppt,pptx,xls,xlsx"},
				           {title : "exe files", extensions : "exe"},
				           {title : "Zip files", extensions : "zip,rar,exe"}
		            
                ],

                // Flash settings
                flash_swf_url : '../../js/plupload/js/plupload.flash.swf'
                // Silverlight settings
//                silverlight_xap_url : '/example/plupload/js/plupload.silverlight.xap'
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
                    alert('请先上传数据文件.');
                }
                return false;
            });
            $( "#uploadFile" ).dialog( "open");
	});
    //移除未挂接文件
	$( "#deletefile" ).button({
		text: false,
		icons: {
			primary: "ui-icon-closethick"
		}
	}).click(function() {
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
	});

	//生成grid的toolbar_archiveAttachment_yes
    //移除已挂接的文件
	$( "#deleteyesfile" ).button({
		text: false,
		icons: {
			primary: "ui-icon-closethick"
		}
	}).click(function() {
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
			$.Zebra_Dialog('请选择要移除的数据。 ', {
                'type':     'information',
                'title':    '系统提示',
                'buttons':  ['确定']
            });
		}
//		archiveCommon.yesItems = attYesGridConfig.dataView.getItems();
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
				$.Zebra_Dialog('读取全文库中未挂接数据时出错，请尝试重新操作或与管理员联系! ', {
	                'type':     'information',
	                'title':    '系统提示',
	                'buttons':  ['确定']
	            });
			}
		}
	});
}


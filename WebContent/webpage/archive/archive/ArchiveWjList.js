

var wjGridconfig = new us.archive.ui.Gridconfig();

$(function(){
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
	var par = "treeid=" + archiveCommon.selectTreeid + "&tableType=02&selectAid=" + archiveCommon.selectAid;
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
	
	$("#grid_header_wj").html(archiveCommon.selectTreeName + '_档案列表');
	
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
	wjGridconfig.dataView.endUpdate();
	
	wjGridconfig.dataView.syncGridSelection(wjGridconfig.grid, true);
	
	//生成toolbar_aj
	$('#toolbar_wj').toolbar({
		items:[{
			id:"insert",
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
									batchUpdate(wjGridconfig.grid,wjGridconfig.dataView,true,wnd,'02');
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
			text:"导入",
			iconCls:"icon-page-copy",
			handler:function(){
				showArchiveImportTab('02');
			}
		},{
			text:"刷新",
			iconCls:"icon-page-copy",
			handler:function(){
                wjGridconfig.dataView.refresh();
			}
		}]
	});
	
	
	/*
	$('#archivewjtable').datagrid({
		url		: 'listArchive.action?treeid=' + selectTreeid + '&tableType=02&selectAid=' + selectAid ,
		title	: selectTreeName + '_' + selectAJH + '(案卷号)_文件列表',
		width	: 800,
		height	: 'auto',
		fitcolumns	: false,
		rownumbers	: true,
		loadMsg		: '数据加载中......',
		singleSelect: false,
		idField		: "ID",
		fit:true,
		columns:columnsWj,
		toolbar:[{
			text:'添加',
			iconCls:'icon-page-add',
			handler:function(){
				var ifValidateRow = $('#archivewjtable').datagrid('validateRow',lastWjIndex);
				if (!ifValidateRow) {
					$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再添加新行! ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}
				$('#archivewjtable').datagrid('endEdit', lastWjIndex);
				//得到所有列
				var fields =  $('#archivewjtable').datagrid('getColumnFields');
				//可以得到字段属性
				var addFields = {};
				addFields["ID"] = UUID.prototype.createUUID ();
				addFields["TREEID"] = selectTreeid;
                addFields["PARENTID"] = selectAid;
				addFields["ISDOC"] = 0;
				for (var i=0;i<fields.length;i++) {
					addFields[fields[i]] = "";
					for (var j=0;j<fieldsWjDefaultValue.length;j++) {
						if (fields[i] == fieldsWjDefaultValue[j].fieldname) {
							addFields[fields[i]] = fieldsWjDefaultValue[j].value;
							break;
						}
					}
				}
				$('#archivewjtable').datagrid('appendRow',addFields);
				lastWjIndex = $('#archivewjtable').datagrid('getRows').length-1;
				$('#archivewjtable').datagrid('selectRow', lastWjIndex);
				$('#archivewjtable').datagrid('beginEdit', lastWjIndex);
				$('#btnSaveWjArchive').linkbutton('enable');
				$('#btnRejectWjArchive').linkbutton('enable');
				isWjEdit = 1;
			}
		},{
			text:'删除',
			iconCls:'icon-page-delete',
			handler:function(){
				var rows = $('#archivewjtable').datagrid('getSelections');
				if (rows.length > 0){
					$.Zebra_Dialog('确定要删除选中的档案条目吗? <br><span style="color:red">注意：删除档案条目，将同时删除数据条目、电子全文，请谨慎操作！</span>', {
						'type':     'question',
		                'title':    '系统提示',
		                'buttons':  ['确定', '取消'],
		                'onClose':  function(caption) {
		                    if (caption == '确定') {
		                    	var c=new Array(rows.length);
		    					for (var j=0;j<rows.length;j++) {
		    						var n = 0;
		    						var indexJ = $('#archivewjtable').datagrid('getRowIndex', rows[j]);
		    						for (var z=0;z<rows.length;z++) {
		    							var indexZ = $('#archivewjtable').datagrid('getRowIndex', rows[z]);
		    							if (indexJ <= indexZ) {
		    								n = n + 1;
		    							}
		    						}
		    						c[n-1] = indexJ;
		    					}
		    					for (var i=0;i<c.length;i++ ) {
		    						$('#archivewjtable').datagrid('deleteRow', c[i]);
		    					}
		    					$('#btnSaveWjArchive').linkbutton('enable');
		    					$('#btnRejectWjArchive').linkbutton('enable');
		                    }
		                }
			        });
				}
				else {
					new $.Zebra_Dialog('请先选择要删除的条目!', {
		  				'buttons':  false,
		   			    'modal': false,
		   			    'position': ['right - 20', 'top + 20'],
		   			    'auto_close': 2500
		            });
				}
				
			}
		},{
			text:'编辑',
			iconCls:'icon-page-edit',
			handler:function(){
				var ifValidateRow = $('#archivewjtable').datagrid('validateRow',lastWjIndex);
				if (!ifValidateRow) {
					$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再编辑新行! ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}
				
				var row = $('#archivewjtable').datagrid('getSelected');
				if (row){
					var index = $('#archivewjtable').datagrid('getRowIndex', row);
					if (lastWjIndex != index){
						$('#archivewjtable').datagrid('endEdit', lastWjIndex);
						$('#archivewjtable').datagrid('beginEdit', index);
					}
					else {
						$('#archivewjtable').datagrid('beginEdit', index);
					}
					lastWjIndex = index;
					isWjEdit = 1;
					$('#btnSaveWjArchive').linkbutton('enable');
					$('#btnRejectWjArchive').linkbutton('enable');
				}
			}
		},'-',{
			id	:'btnCode',
			text:'代码',
			iconCls:'icon_page_copy',
			handler:function(){
				alert(fieldsWjDefaultValue[0].fieldname);
//				var ifValidateRow = $('#archivewjtable').datagrid('validateRow',lastWjIndex);
//				if (!ifValidateRow) {
//					$.Zebra_Dialog('正在编写的行包含不能为空的字段，请保存后再编写字段代码! ', {
//		                'type':     'information',
//		                'title':    '系统提示',
//		                'buttons':  ['确定']
//		            });
//					return;
//				}
//				var rows = $('#archivewjtable').datagrid('getSelections');
//				if (rows.length > 1){
//					$.Zebra_Dialog('您选择了多行进行代码管理，请只选择一行! ', {
//		                'type':     'information',
//		                'title':    '系统提示',
//		                'buttons':  ['确定']
//		            });
//					return;
//				}
//				else if (rows.length == 0){
//					$.Zebra_Dialog('请选择一行字段进行编辑代码! ', {
//		                'type':     'information',
//		                'title':    '系统提示',
//		                'buttons':  ['确定']
//		            });
//					return;
//				}
//				selectField = rows[0];
//				$("#addCodeWindow").show();
//				var $win;
//		   	    $win = $('#addCodeWindow').window({
//			   	    title:' 字段代码管理',
//		   	        width: 500,
//		   	        height: 430,
//		   	        top:($(window).height() - 400) * 0.5,
//		            left:($(window).width() - 430) * 0.5,
//		   	        shadow: true,
//		   	        modal:true,
//		   	        iconCls:'icon-application_form_add',
//		   	        closed:true,
//		   	        minimizable:false,
//		   	        maximizable:false,
//		   	        collapsible:false,
//		   	        onClose:function() {
//		   	    	$('#archivewjtable').datagrid('reload');
//		   	    	}
//		   	    });
//		   	    $("#addCodeWindow").window('open');
//		   	    codemanage();
			}
		},'-',{
			id:'btnSaveWjArchive',
			text:'保存',
			iconCls:'icon-page-save',
			disabled:true,
			handler:function(){
				var ifValidateRow = $('#archivewjtable').datagrid('validateRow',lastWjIndex);
				if (!ifValidateRow) {
					new $.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再保存!', {
		  				'buttons':  false,
		   			    'modal': false,
		   			    'position': ['right - 20', 'top + 20'],
		   			    'auto_close': 2500
		            });
					return;
				}
				if (isWjEdit == 1) {
					$('#archivewjtable').datagrid('endEdit', lastWjIndex);
				}
				var insertRows = $('#archivewjtable').datagrid('getChanges','inserted');
				var updateRows = $('#archivewjtable').datagrid('getChanges','updated');
				var deleteRows = $('#archivewjtable').datagrid('getChanges','deleted');
				if (insertRows.length == 0 && updateRows.length == 0 && deleteRows.length == 0) {
    				isWjEdit = 0;
					// 并且禁止保存、还原按钮
					$('#btnSaveWjArchive').linkbutton('disable');
					$('#btnRejectWjArchive').linkbutton('disable');
    				return;
				};
				var changesRows = {
	    				inserted : [],
	    				updated : [],
	    				deleted : []
	    				};
				if (insertRows.length>0) {
					for (var i=0;i<insertRows.length;i++) {
						changesRows.inserted.push(insertRows[i]);
					}
				}

				if (updateRows.length>0) {
					for (var k=0;k<updateRows.length;k++) {
						changesRows.updated.push(updateRows[k]);
					}
				}
				
				if (deleteRows.length>0) {
					for (var j=0;j<deleteRows.length;j++) {
						changesRows.deleted.push(deleteRows[j]);
					}
				}
				var par = "par=" + JSON.stringify(changesRows) + "&tableType=02";

				$.post("saveArchive.action",par,function(data){
						new $.Zebra_Dialog(data, {
			  				'buttons':  false,
			   			    'modal': false,
			   			    'position': ['right - 20', 'top + 20'],
			   			    'auto_close': 2500
			            });
					}
				);
				
				// 保存成功后，可以刷新页面，也可以：
				$('#archivewjtable').datagrid('acceptChanges');
				$('#archivewjtable').datagrid('clearSelections');
				isWjEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnSaveWjArchive').linkbutton('disable');
				$('#btnRejectWjArchive').linkbutton('disable');
			}
		},{
			id	:'btnRejectWjArchive',
			text:'还原',
			iconCls:'icon-reject',
			disabled:true,
			handler:function(){
				$('#archivewjtable').datagrid('rejectChanges');
				isWjEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnSaveWjArchive').linkbutton('disable');
				$('#btnRejectWjArchive').linkbutton('disable');
			}
		},{
//			id	:'btnrefresh',
			text:'刷新',
			iconCls:'icon_refresh',
			handler:function(){
				$('#archivewjtable').datagrid('reload');
				$('#archivewjtable').datagrid('clearSelections');
				isWjEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnSaveWjArchive').linkbutton('disable');
				$('#btnRejectWjArchive').linkbutton('disable');
			}
		}],
		onBeforeLoad:function(){
		},
		
	    onClickRow:function(rowIndex){
		    if (isWjEdit == 1) {
		    	if (lastWjIndex != rowIndex){
					$('#archivewjtable').datagrid('endEdit', lastWjIndex);
					$('#archivewjtable').datagrid('beginEdit', rowIndex);
				}
				lastWjIndex = rowIndex;
		    }
		    
		}
	});
	*/
});

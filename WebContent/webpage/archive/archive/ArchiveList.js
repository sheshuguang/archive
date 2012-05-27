var lastIndex = -1;
var isEdit = 0;
var columns =[[]];
//动态字段添加时，附带条件。例如默认值
var fieldsDefaultValue = [];

$(function(){
	var par = "treeid=" + selectTreeid + "&tableType=01&importType=0";
	$.ajax({
		async: false,
		url: "createField.action?" + par,
		type:'post',
		dataType: 'script',
        success: function(data) {
			if (data != "error") {
				columns = fields;
				fieldsDefaultValue= fieldsDefaultValue;
			}
			else {
				new $.Zebra_Dialog('读取字段信息时出错，请关闭浏览器，重新登录尝试或与管理员联系!', {
	  				'buttons':  false,
	   			    'modal': false,
	   			    'position': ['right - 20', 'top + 20'],
	   			    'auto_close': 2500
	            });
			}
        }
	});
	$('#archivetable').datagrid({
		url		: 'listArchive.action?treeid=' + selectTreeid + '&tableType=01',
		title	: selectTreeName + '_档案列表',
		width	: 800,
		height	: 'auto',
		fitColumns	: false,
		rownumbers	: true,
		loadMsg		: '数据加载中......',
		singleSelect: false,
		idField		: "ID",
		fit:true,
		columns:columns,
		toolbar:[{
			text:'添加',
			iconCls:'icon-page-add',
			handler:function(){
				var ifValidateRow = $('#archivetable').datagrid('validateRow',lastIndex);
				if (!ifValidateRow) {
					$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再添加新行! ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}
				$('#archivetable').datagrid('endEdit', lastIndex);
				//得到所有列
				var fields =  $('#archivetable').datagrid('getColumnFields');
				//可以得到字段属性
				var addFields = {};
				addFields["ID"] = UUID.prototype.createUUID ();
				addFields["TREEID"] = selectTreeid;
				addFields["ISDOC"] = 0;
				for (var i=0;i<fields.length;i++) {
					addFields[fields[i]] = "";
					for (var j=0;j<fieldsDefaultValue.length;j++) {
						if (fields[i] == fieldsDefaultValue[j].fieldname) {
							addFields[fields[i]] = fieldsDefaultValue[j].value;
							break;
						}
					}
				}
				$('#archivetable').datagrid('appendRow',addFields);
				lastIndex = $('#archivetable').datagrid('getRows').length-1;
				$('#archivetable').datagrid('selectRow', lastIndex);
				$('#archivetable').datagrid('beginEdit', lastIndex);
				$('#btnSaveArchive').linkbutton('enable');
				$('#btnRejectArchive').linkbutton('enable');
				isEdit = 1;
			}
		},{
			text:'删除',
			iconCls:'icon-page-delete',
			handler:function(){
				var rows = $('#archivetable').datagrid('getSelections');
				if (rows.length > 0){
					$.Zebra_Dialog('确定要删除选中的档案条目吗? <br><span style="color:red">注意：删除档案条目，将同时删除数据条目、电子全文，请谨慎操作！</span>', {
						'type':     'question',
		                'title':    '系统提示',
		                'buttons':  ['确定', '取消'],
		                'onClose':  function(caption) {
		                	if (caption == '确定') {
//		                		alert(rows.length);
//		                    	for (var i = rows.length - 1; i >= 0 ;i-- ) {
//		                    		var index = $('#archivetable').datagrid('getRowIndex', rows[i]);
//		                    		alert(index);
//		                    		$('#archivetable').datagrid('deleteRow', index);
//		                    	}
		                		for(var i =0;i<rows.length;i++){
		                			var index = $('#archivetable').datagrid('getRowIndex',rows[i]);//获取某行的行号
		                			$('#archivetable').datagrid('deleteRow',index);	//通过行号移除该行
		                		}
		                    }
//		                    if (caption == '确定') {
//		                    	var c=new Array(rows.length);
//		    					for (var j=0;j<rows.length;j++) {
//		    						var n = 0;
//		    						var indexJ = $('#archivetable').datagrid('getRowIndex', rows[j]);
//		    						for (var z=0;z<rows.length;z++) {
//		    							var indexZ = $('#archivetable').datagrid('getRowIndex', rows[z]);
//		    							if (indexJ <= indexZ) {
//		    								n = n + 1;
//		    							}
//		    						}
//		    						c[n-1] = indexJ;
//		    					}
//		    					for (var i=0;i<c.length;i++ ) {
//		    						$('#archivetable').datagrid('deleteRow', c[i]);
//		    					}
//		    					$('#btnSaveArchive').linkbutton('enable');
//		    					$('#btnRejectArchive').linkbutton('enable');
//		                    }
		                	$('#btnSaveArchive').linkbutton('enable');
	    					$('#btnRejectArchive').linkbutton('enable');
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
				var ifValidateRow = $('#archivetable').datagrid('validateRow',lastIndex);
				if (!ifValidateRow) {
					$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再编辑新行! ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}
				
				var row = $('#archivetable').datagrid('getSelected');
				if (row){
					var index = $('#archivetable').datagrid('getRowIndex', row);
					if (lastIndex != index){
						$('#archivetable').datagrid('endEdit', lastIndex);
						$('#archivetable').datagrid('beginEdit', index);
					}
					else {
						$('#archivetable').datagrid('beginEdit', index);
					}
					lastIndex = index;
					isEdit = 1;
					$('#btnSaveArchive').linkbutton('enable');
					$('#btnRejectArchive').linkbutton('enable');
				}
			}
		},'-',{
			id	:'btnArchiveImport',
			text:'案卷导入',
			iconCls:'icon_page_copy',
			handler:function(){
				showArchiveImportTab(1,1);
			}
		},'-',{
			id:'btnSaveArchive',
			text:'保存',
			iconCls:'icon-page-save',
			disabled:true,
			handler:function(){
				var ifValidateRow = $('#archivetable').datagrid('validateRow',lastIndex);
				if (!ifValidateRow) {
					new $.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再保存!', {
		  				'buttons':  false,
		   			    'modal': false,
		   			    'position': ['right - 20', 'top + 20'],
		   			    'auto_close': 2500
		            });
					return;
				}
				if (isEdit == 1) {
					$('#archivetable').datagrid('endEdit', lastIndex);
				}
				var insertRows = $('#archivetable').datagrid('getChanges','inserted');
				var updateRows = $('#archivetable').datagrid('getChanges','updated');
				var deleteRows = $('#archivetable').datagrid('getChanges','deleted');
				if (insertRows.length == 0 && updateRows.length == 0 && deleteRows.length == 0) {
    				isEdit = 0;
					// 并且禁止保存、还原按钮
					$('#btnSaveArchive').linkbutton('disable');
					$('#btnRejectArchive').linkbutton('disable');
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
				var par = "par=" + JSON.stringify(changesRows) + "&tableType=01";

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
				$('#archivetable').datagrid('acceptChanges');
				$('#archivetable').datagrid('clearSelections');
				isEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnSaveArchive').linkbutton('disable');
				$('#btnRejectArchive').linkbutton('disable');
			}
		},{
			id	:'btnRejectArchive',
			text:'还原',
			iconCls:'icon-reject',
			disabled:true,
			handler:function(){
				$('#archivetable').datagrid('rejectChanges');
				isEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnSaveArchive').linkbutton('disable');
				$('#btnRejectArchive').linkbutton('disable');
			}
		},{
//			id	:'btnrefresh',
			text:'刷新',
			iconCls:'icon_refresh',
			handler:function(){
				$('#archivetable').datagrid('reload');
				$('#archivetable').datagrid('clearSelections');
				isEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnSaveArchive').linkbutton('disable');
				$('#btnRejectArchive').linkbutton('disable');
			}
		}],
		onBeforeLoad:function(){
		},
		/*
		onBeforeEdit:function(index,row){  
	        row.editing = true;  
	        $('#tt').datagrid('refreshRow', index);
	    },  
	    onAfterEdit:function(index,row){  
	        row.editing = false;
	        
	        $('#tt').datagrid('refreshRow', index);
	    },  */
	    onClickRow:function(rowIndex,rowData){
            selectAJH = rowData.AJH;

		    if (isEdit == 1) {
		    	if (lastIndex != rowIndex){
					$('#archivetable').datagrid('endEdit', lastIndex);
					$('#archivetable').datagrid('beginEdit', rowIndex);
				}
				lastIndex = rowIndex;
		    }
		    /*
			if (lastIndex != rowIndex){
				$('#tt').datagrid('endEdit', lastIndex);
				$('#tt').datagrid('beginEdit', rowIndex);
			}
			lastIndex = rowIndex;
			*/
		}
	});
});

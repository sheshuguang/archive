var lastWjIndex = -1;
var isWjEdit = 0;
var columnsWj =[[]];
//动态字段添加时，附带条件。例如默认值
var fieldsWjDefaultValue = [];

$(function(){
	var par = "treeid=" + selectTreeid + "&tableType=02&importType=0";
	$.ajax({
		async: false,
		url: "createField.action?" + par,
		type:'post',
		dataType: 'script',
        success: function(data) {
			if (data != "error") {
				columnsWj = fields;
				fieldsWjDefaultValue= fieldsDefaultValue;
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
		/*
		onBeforeEdit:function(index,row){  
	        row.editing = true;  
	        $('#tt').datagrid('refreshRow', index);
	    },  
	    onAfterEdit:function(index,row){  
	        row.editing = false;
	        
	        $('#tt').datagrid('refreshRow', index);
	    },  */
	    onClickRow:function(rowIndex){
		    if (isWjEdit == 1) {
		    	if (lastWjIndex != rowIndex){
					$('#archivewjtable').datagrid('endEdit', lastWjIndex);
					$('#archivewjtable').datagrid('beginEdit', rowIndex);
				}
				lastWjIndex = rowIndex;
		    }
		    /*
			if (lastWjIndex != rowIndex){
				$('#tt').datagrid('endEdit', lastWjIndex);
				$('#tt').datagrid('beginEdit', rowIndex);
			}
			lastWjIndex = rowIndex;
			*/
		}
	});
});

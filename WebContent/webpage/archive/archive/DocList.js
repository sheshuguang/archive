
var lastDocIndex = -1;
var isDocEdit = 0;

$(function(){
	$('#doctable').datagrid({
		url		: 'listDoc.action?tableid='+ selectTableid +'&selectRowid=' + selectRowid ,
		title	: selectTreeName + '_' + selectAJH + '(案卷号)_电子文件列表',
		width	: 800,
		height	: 'auto',
		fitcolumns	: false,
		rownumbers	: true,
		loadMsg		: '数据加载中......',
		singleSelect: false,
		idField		: "DOCID",
		fit:true,
		columns:[[
            {
                field:'docnewname',title:'文件名称',width:70
            },{
                field:'doctype',title:'文件类型',width:70
            },{
                field:'doclength',title:'文件大小',width:80,align:'center'
            },{
                field:'creater',title:'上传者',width:50,align:'center'
            },{
                field:'createtime',title:'上传时间',width:80
            }
        ]],
		toolbar:[{
			text:'添加',
			iconCls:'icon-page-add',
			handler:function(){
                uploadFiles();
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
				var ifValidateRow = $('#archivewjtable').datagrid('validateRow',lastDocIndex);
				if (!ifValidateRow) {
					new $.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再保存!', {
		  				'buttons':  false,
		   			    'modal': false,
		   			    'position': ['right - 20', 'top + 20'],
		   			    'auto_close': 2500
		            });
					return;
				}
				if (isDocEdit == 1) {
					$('#archivewjtable').datagrid('endEdit', lastDocIndex);
				}
				var insertRows = $('#archivewjtable').datagrid('getChanges','inserted');
				var updateRows = $('#archivewjtable').datagrid('getChanges','updated');
				var deleteRows = $('#archivewjtable').datagrid('getChanges','deleted');
				if (insertRows.length == 0 && updateRows.length == 0 && deleteRows.length == 0) {
    				isDocEdit = 0;
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
				isDocEdit = 0;
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
				isDocEdit = 0;
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
				isDocEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnSaveWjArchive').linkbutton('disable');
				$('#btnRejectWjArchive').linkbutton('disable');
			}
		}],
		onBeforeLoad:function(){
		},
	    onClickRow:function(rowIndex){
		    if (isDocEdit == 1) {
		    	if (lastDocIndex != rowIndex){
					$('#archivewjtable').datagrid('endEdit', lastDocIndex);
					$('#archivewjtable').datagrid('beginEdit', rowIndex);
				}
				lastDocIndex = rowIndex;
		    }
		}
	});
});

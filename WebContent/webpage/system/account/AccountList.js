var lastIndex = -1;
var isEdit = 0;
$(function(){
	$('#tt').datagrid({
		url		: 'listAccount.action',
		title	: '帐户列表',
		width	: 800,
		//height	: $(window).height()-1,
		height	: 'auto',
		fitColumns	: true,
		rownumbers	: true,
		loadMsg		: '数据加载中......',
		singleSelect: false,
		idField		: "accountid",
		fit:true,
//		frozenColumns:[
//			[{
//				field:'accountid',
//				checkbox:true
//			}]
//		],
		columns:[[
			{field:'accountcode',title:'帐户名称',width:50,
				editor:{
					type:'validatebox',
					options:{
						required:true
					}
				}
			},
			{field:'accountstate',title:'状态',width:50,align:'center',
				editor:{
					type:'checkbox',
					options:{
						on:"<img alt='1' src='../../images/icons/status_online.png' title='已启用'>",
						off:"<img alt='0' src='../../images/icons/status_offline.png' title='已停用'>"
					}
				}
			},
			{field:'accountmemo',title:'描述',width:160,editor:'text'}
		]],
		toolbar:[{
			text:'添加',
			iconCls:'icon_user_add',
			handler:function(){
				var ifValidateRow = $('#tt').datagrid('validateRow',lastIndex);
				if (!ifValidateRow) {
					$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再添加新行! ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					//$.messager.alert('系统提示','正在编写的行包含不能为空的字段，请输入内容，再添加新行!','info');
					return;
				}
				$('#tt').datagrid('endEdit', lastIndex);
				$('#tt').datagrid('appendRow',{
					accountid:UUID.prototype.createUUID (),
					accountcode:'',
					accountstate:'',
					accountmemo:''
				});
				lastIndex = $('#tt').datagrid('getRows').length-1;
				$('#tt').datagrid('selectRow', lastIndex);
				$('#tt').datagrid('beginEdit', lastIndex);
				$('#btnsave').linkbutton('enable');
				$('#btnreject').linkbutton('enable');
				isEdit = 1;
			}
		},{
			text:'删除',
			iconCls:'icon_user_delete',
			handler:function(){
				var rows = $('#tt').datagrid('getSelections');
				if (rows.length > 0){
					var c=new Array(rows.length);
					for (var j=0;j<rows.length;j++) {
						var n = 0;
						var indexJ = $('#tt').datagrid('getRowIndex', rows[j]);
						for (var z=0;z<rows.length;z++) {
							var indexZ = $('#tt').datagrid('getRowIndex', rows[z]);
							if (indexJ <= indexZ) {
								n = n + 1;
							}
						}
						c[n-1] = indexJ;
					}
					for (var i=0;i<c.length;i++ ) {
						$('#tt').datagrid('deleteRow', c[i]);
					}
					$('#btnsave').linkbutton('enable');
					$('#btnreject').linkbutton('enable');
				}
				else {
					new $.Zebra_Dialog('请先选择要删除的帐户!', {
		  				'buttons':  false,
		   			    'modal': false,
		   			    'position': ['right - 20', 'top + 20'],
		   			    'auto_close': 2500
		            });
					//$.messager.alert('系统提示','请先选择要删除的帐户!','info');
				}
				
			}
		},{
			text:'编辑',
			iconCls:'icon_user_edit',
			handler:function(){
				var ifValidateRow = $('#tt').datagrid('validateRow',lastIndex);
				if (!ifValidateRow) {
					$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再编辑新行! ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					//$.messager.alert('系统提示','正在编写的行包含不能为空的字段，请输入内容，再编辑新行!','info');
					return;
				}
				var row = $('#tt').datagrid('getSelected');
				if (row){
					var index = $('#tt').datagrid('getRowIndex', row);
					if (lastIndex != index){
						$('#tt').datagrid('endEdit', lastIndex);
						$('#tt').datagrid('beginEdit', index);
					}
					else {
						$('#tt').datagrid('beginEdit', index);
					}
					lastIndex = index;
					isEdit = 1;
					$('#btnsave').linkbutton('enable');
					$('#btnreject').linkbutton('enable');
				}
			}
		},'-',{
			id	:'btnmove',
			text:'移动',
			iconCls:'icon_user_go',
			handler:function(){
				if (isEdit == 1) {
					new $.Zebra_Dialog('处于编辑状态不能移动，请先保存后再操作!', {
		  				'buttons':  false,
		   			    'modal': false,
		   			    'position': ['right - 20', 'top + 20'],
		   			    'auto_close': 2500
		            });
					return;
				}
				var rows = $('#tt').datagrid('getSelections');
				if (rows.length){
                    $("#moveAccountWindow").show();
					var $win;
			   	    $win = $('#moveAccountWindow').window({
				   	    title:' 移动帐户',
			   	        width: 300,
			   	        height: 450,
			   	        top:($(window).height() - 400) * 0.5,
			            left:($(window).width() - 430) * 0.5,
			   	        shadow: true,
			   	        modal:true,
			   	        iconCls:'icon_user_go',
			   	        closed:true,
			   	        minimizable:false,
			   	        maximizable:false,
			   	        collapsible:false,
			   	     	onBeforeOpen:function(){
				   	    	$('#accountmovetree').tree({   
				                checkbox: false,   
				                url: 'orgtreeAction.action?nodeId=0',   
				                onBeforeExpand:function(node,param){
				                    $('#accountmovetree').tree('options').url = "orgtreeAction.action?nodeId=" + node.id;// change the url                       
				                }  
				            });
				   	    } 
			   	    });
		   	    	$("#moveAccountWindow").window('open');
				}
				else {
					new $.Zebra_Dialog('请先选择要移动的帐户!', {
		  				'buttons':  false,
		   			    'modal': false,
		   			    'position': ['right - 20', 'top + 20'],
		   			    'auto_close': 2500
		            });
					//$.messager.alert('系统提示','请先选择要移动的帐户!','info');
				}
				
				//$('#tt').datagrid('reload');
			}
		},'-',{
			id:'btnsave',
			text:'保存',
			iconCls:'icon_save',
			disabled:true,
			handler:function(){
				var ifValidateRow = $('#tt').datagrid('validateRow',lastIndex);
				if (!ifValidateRow) {
					new $.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再保存!', {
		  				'buttons':  false,
		   			    'modal': false,
		   			    'position': ['right - 20', 'top + 20'],
		   			    'auto_close': 2500
		            });
					//$.messager.alert('系统提示','正在编写的行包含不能为空的字段，请输入内容，再保存!','info');
					return;
				}
				if (isEdit == 1) {
					$('#tt').datagrid('endEdit', lastIndex);
				}
				var insertRows = $('#tt').datagrid('getChanges','inserted');
				var updateRows = $('#tt').datagrid('getChanges','updated');
				var deleteRows = $('#tt').datagrid('getChanges','deleted');
				if (insertRows.length == 0 && updateRows.length == 0 && deleteRows.length == 0) {
    				isEdit = 0;
					// 并且禁止保存、还原按钮
					$('#btnsave').linkbutton('disable');
					$('#btnreject').linkbutton('disable');
    				return;
				};
				var changesRows = {
	    				inserted : [],
	    				updated : [],
	    				deleted : []
	    				};
				if (insertRows.length>0) {
					for (var i=0;i<insertRows.length;i++) {
						insertRows[i].accountstate=$(insertRows[i].accountstate).attr("alt");
						changesRows.inserted.push(insertRows[i]);
					}
				}

				if (updateRows.length>0) {
					for (var k=0;k<updateRows.length;k++) {
						updateRows[k].accountstate=$(updateRows[k].accountstate).attr("alt");
						changesRows.updated.push(updateRows[k]);
					}
				}
				
				if (deleteRows.length>0) {
					for (var j=0;j<deleteRows.length;j++) {
						deleteRows[j].accountstate=$(deleteRows[j].accountstate).attr("alt");
						changesRows.deleted.push(deleteRows[j]);
					}
				}
				var par = "par=" + JSON.stringify(changesRows);

				$.post("saveAccount.action",par,function(data){
						new $.Zebra_Dialog(data, {
			  				'buttons':  false,
			   			    'modal': false,
			   			    'position': ['right - 20', 'top + 20'],
			   			    'auto_close': 2500
			            });
					}
				);
				
				// 保存成功后，可以刷新页面，也可以：
				$('#tt').datagrid('acceptChanges');
				$('#tt').datagrid('clearSelections');
				//$('#tt').datagrid('reload');
				isEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnsave').linkbutton('disable');
				$('#btnreject').linkbutton('disable');
			}
		},{
			id	:'btnreject',
			text:'还原',
			iconCls:'icon-reject',
			disabled:true,
			handler:function(){
				$('#tt').datagrid('rejectChanges');
				isEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnsave').linkbutton('disable');
				$('#btnreject').linkbutton('disable');
			}
		},{
			id	:'btnrefresh',
			text:'刷新',
			iconCls:'icon_refresh',
			handler:function(){
				$('#tt').datagrid('reload');
				$('#tt').datagrid('clearSelections');
				isEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnsave').linkbutton('disable');
				$('#btnreject').linkbutton('disable');
			}
		}],
		onBeforeLoad:function(){
			//$(this).datagrid('rejectChanges');
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
		    if (isEdit == 1) {
		    	if (lastIndex != rowIndex){
					$('#tt').datagrid('endEdit', lastIndex);
					$('#tt').datagrid('beginEdit', rowIndex);
					$('#tt').datagrid('unselectRow',lastIndex);
					$('#tt').datagrid('selectRow',rowIndex);
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

function saveMoveAccount() {
	var move_target = $('#accountmovetree').tree('getSelected');
	if (!move_target){
		new $.Zebra_Dialog('请先选择目标帐户组，再移动!', {
				'buttons':  false,
			    'modal': false,
			    'position': ['right - 20', 'top + 20'],
			    'auto_close': 2500
        });
		 //$.messager.alert('系统提示','请先选择目标帐户组，再移动!','info');
		 return;
	 }
	var rows = $('#tt').datagrid('getSelections');
	if (rows){
		var selectAccountids = "";
		for (var j=0;j<rows.length;j++) {
			selectAccountids += rows[j].accountid + ",";
		}
		var par = "selectAccounts=" + selectAccountids + "&targetorgid=" + move_target.id; 
		$.post("moveAccount.action",par,function(data){
			if (data == "success") {
				new $.Zebra_Dialog('移动成功!', {
					'buttons':  false,
				    'modal': false,
				    'position': ['right - 20', 'top + 20'],
				    'auto_close': 2500
				});
				//$.messager.alert('系统提示','保存成功。','info');
   					
				$('#tt').datagrid('reload');
   				$("#moveAccountWindow").window('close');
   			}
   			else  {
   				new $.Zebra_Dialog('移动过程中出现错误，请重新尝试或与管理员联系!', {
					'buttons':  false,
				    'modal': false,
				    'position': ['right - 20', 'top + 20'],
				    'auto_close': 2500
				});
   				//$.messager.alert('系统提示','移动过程中出现错误，请重新尝试或与管理员联系。','info');
   			}
		});
	}
}
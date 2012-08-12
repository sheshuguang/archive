		

		var lastIndex = -1;
		var isEdit = 0;
		$(function(){
			$('#roletable').datagrid({
				url		: 'listRole.action',
				title	: '角色列表',
				width	: 800,
				height	: 'auto',
				fitColumns	: true,
				rownumbers	: true,
				loadMsg		: '数据加载中......',
				singleSelect: true,
				idField		: "roleid",
				fit:true,
				//frozenColumns:[
				//	[{
				//		field:'roleid',
				//		checkbox:true
				//	}]
				//],
				columns:[[
					{field:'rolename',title:'角色名称',width:50,
						editor:{
							type:'validatebox',
							options:{
								required:true
							}
						}
					},
					{field:'rolememo',title:'角色描述',width:160,editor:'text'}
				]],
				toolbar:[{
					text:'添加',
					iconCls:'icon_role_add',
					handler:function(){
						var ifValidateRow = $('#roletable').datagrid('validateRow',lastIndex);
						if (!ifValidateRow) {
							$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再添加新行! ', {
				                'type':     'information',
				                'title':    '系统提示',
				                'buttons':  ['确定']
				            });
							//$.messager.alert('系统提示','正在编写的行包含不能为空的字段，请输入内容，再添加新行!','info');
							return;
						}
						$('#roletable').datagrid('endEdit', lastIndex);
						$('#roletable').datagrid('appendRow',{
							roleid:UUID.prototype.createUUID (),
							rolename:'',
							rolememo:''
						});
						lastIndex = $('#roletable').datagrid('getRows').length-1;
						$('#roletable').datagrid('selectRow', lastIndex);
						$('#roletable').datagrid('beginEdit', lastIndex);
						$('#btnsave').linkbutton('enable');
						$('#btnreject').linkbutton('enable');
						isEdit = 1;
					}
				},'-',{
					text:'删除',
					iconCls:'icon_role_delete',
					handler:function(){
						var row = $('#roletable').datagrid('getSelected');
						if (row){
							var index = $('#roletable').datagrid('getRowIndex', row);
							$('#roletable').datagrid('deleteRow', index);
							$('#btnsave').linkbutton('enable');
							$('#btnreject').linkbutton('enable');
						}
						else {
							new $.Zebra_Dialog('请先选择要删除的角色!', {
				  				'buttons':  false,
				   			    'modal': false,
				   			    'position': ['right - 20', 'top + 20'],
				   			    'auto_close': 2500
				            });
							//$.messager.alert('系统提示','请先选择要删除的角色!','info');
						}
						
					}
				},'-',{
					text:'编辑',
					iconCls:'icon_role_edit',
					handler:function(){
						var ifValidateRow = $('#roletable').datagrid('validateRow',lastIndex);
						if (!ifValidateRow) {
							$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再编辑新行! ', {
				                'type':     'information',
				                'title':    '系统提示',
				                'buttons':  ['确定']
				            });
							//$.messager.alert('系统提示','正在编写的行包含不能为空的字段，请输入内容，再编辑新行!','info');
							return;
						}
						var row = $('#roletable').datagrid('getSelected');
						if (row){
							var index = $('#roletable').datagrid('getRowIndex', row);
							if (lastIndex != index){
								$('#roletable').datagrid('endEdit', lastIndex);
								$('#roletable').datagrid('beginEdit', index);
							}
							else {
								$('#roletable').datagrid('beginEdit', index);
							}
							lastIndex = index;
							isEdit = 1;
							$('#btnsave').linkbutton('enable');
							$('#btnreject').linkbutton('enable');
						}
					}
				},'-',{
					id	:'btnSetFun',
					text:'权限',
					iconCls:'icon_role_go',
					handler:function(){
						if (isEdit == 1) {
							new $.Zebra_Dialog('编辑状态下，不能设置角色权限.请先保存!', {
				  				'buttons':  false,
				   			    'modal': false,
				   			    'position': ['right - 20', 'top + 20'],
				   			    'auto_close': 2500
				            });
							//$.messager.alert('系统提示','编辑状态下，不能设置角色权限.请先保存!','info');
							return;
						}
						var row = $('#roletable').datagrid('getSelected');
						if (row){
							$("#setFunWindow").show();
							var $win;
					   	    $win = $('#setFunWindow').window({
						   	    title:' 设定角色操作的功能',
					   	        width: 300,
					   	        height: 450,
					   	        top:($(window).height() - 450) * 0.5,
						        left:($(window).width() - 300) * 0.5,
					   	        shadow: true,
					   	        modal:true,
					   	        iconCls:'icon_role_go',
					   	        closed:true,
					   	        minimizable:false,
					   	        maximizable:false,
					   	        collapsible:false,
					   	     	onBeforeOpen:function(){
						   	    	$('#funtree').tree({   
						                checkbox: true,   
						                url: 'listRoleFunction.action?roleid=' + row.roleid   
						            });
						   	    } 
					   	    });
				   	    	$("#setFunWindow").window('open');
						}
						else {
							new $.Zebra_Dialog('请先选择要设置权限的角色!', {
				  				'buttons':  false,
				   			    'modal': false,
				   			    'position': ['right - 20', 'top + 20'],
				   			    'auto_close': 2500
				            });
							//$.messager.alert('系统提示','请先选择要设置权限的角色!','info');
						}
						
					}
				},'-',{
					id:'btnsave',
					text:'保存',
					iconCls:'icon_save',
					disabled:true,
					handler:function(){
						var ifValidateRow = $('#roletable').datagrid('validateRow',lastIndex);
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
							$('#roletable').datagrid('endEdit', lastIndex);
						}
	    				var insertRows = $('#roletable').datagrid('getChanges','inserted');
	    				var updateRows = $('#roletable').datagrid('getChanges','updated');
	    				var deleteRows = $('#roletable').datagrid('getChanges','deleted');
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
	   					//alert(JSON.stringify(changesRows));
	   					var par = "par=" + JSON.stringify(changesRows);

	   					$.post("saveRole.action",par,function(data){
		   						new $.Zebra_Dialog(data, {
					  				'buttons':  false,
					   			    'modal': false,
					   			    'position': ['right - 20', 'top + 20'],
					   			    'auto_close': 2500
					            });
//		   		   				alert(data);
   							}
	   					);
	   					
	   					// 保存成功后，可以刷新页面，也可以：
						$('#roletable').datagrid('acceptChanges');
						$('#roletable').datagrid('clearSelections');

						isEdit = 0;
						// 并且禁止保存、还原按钮
						$('#btnsave').linkbutton('disable');
						$('#btnreject').linkbutton('disable');
					}
				},'-',{
					id	:'btnreject',
					text:'还原',
					iconCls:'icon-reject',
					disabled:true,
					handler:function(){
						$('#roletable').datagrid('rejectChanges');
						isEdit = 0;
						// 并且禁止保存、还原按钮
						$('#btnsave').linkbutton('disable');
						$('#btnreject').linkbutton('disable');
					}
				},'-',{
					id	:'btnrefresh',
					text:'刷新',
					iconCls:'icon_refresh',
					handler:function(){
						$('#roletable').datagrid('reload');
						isEdit = 0;
						lastIndex = -1;
						// 并且禁止保存、还原按钮
						$('#btnsave').linkbutton('disable');
						$('#btnreject').linkbutton('disable');
					}
				}],
				onBeforeLoad:function(){
					$(this).datagrid('rejectChanges');
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
							$('#roletable').datagrid('endEdit', lastIndex);
							$('#roletable').datagrid('beginEdit', rowIndex);
						}
						lastIndex = rowIndex;
				    }
				}
			});
		});
		
	    function GetNode(type){  
            var node = $('#funtree').tree('getChecked');
            var cnodes='';  
            var pnodes='';  
              
             var prevNode=''; //保存上一步所选父节点  
            for(var i=0;i<node.length;i++){  
                 
                if($('#funtree').tree('isLeaf',node[i].target)){
                    cnodes+=node[i].id+',';    
                      
                    var pnode = $('#funtree').tree('getParent',node[i].target); //获取当前节点的父节点  
                   if(prevNode!=pnode.id) //保证当前父节点与上一次父节点不同  
                   {  
                        pnodes+=pnode.id+',';  
                        prevNode = pnode.id; //保存当前节点 
                   }  
                }  
            }  
            cnodes = cnodes.substring(0,cnodes.length-1);  
            pnodes = pnodes.substring(0,pnodes.length-1);  
             
            if(type=='child'){return cnodes;}  
            else{return pnodes};  
        };  

		function saveSetFun() {
			//得到选中的角色id
			var row = $('#roletable').datagrid('getSelected');
			//得到选中的功能节点
			var nodes = $('#funtree').tree('getChecked');
			//计算出父节点
			var f = GetNode("fnode");
			//计算出子节点集合
			var c = GetNode("child");

			var functionids = f + "," + c;

			if (row){
				var par = "roleid=" + row.roleid + "&functionids=" + functionids; 
				$.post("saveRoleFun.action",par,function(data){
					if (data == "success") {
						new $.Zebra_Dialog('角色功能权限设置成功。', {
			  				'buttons':  false,
			   			    'modal': false,
			   			    'position': ['right - 20', 'top + 20'],
			   			    'auto_close': 2500
			            });
						//$.messager.alert('系统提示','角色功能权限设置成功。','info');
		   					
						//$('#roletable').datagrid('reload');
		   				$("#setFunWindow").window('close');
		   			}
		   			else  {
		   				new $.Zebra_Dialog('角色功能权限设置过程中出现错误.</br>请重新尝试或与管理员联系!', {
							'buttons':  false,
						    'modal': false,
						    'position': ['right - 20', 'top + 20'],
						    'auto_close': 2500
						});
		   				//$.messager.alert('系统提示','角色功能权限设置过程中出现错误，</br>请重新尝试或与管理员联系。','info');
		   			}
				});
			}
		}
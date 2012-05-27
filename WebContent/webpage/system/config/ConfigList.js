		

		var lastIndex = -1;
		$(function(){
			$('#configtable').datagrid({
				url		: 'listConfig.action',
				title	: '参数属性列表',
				width	: 800,
				height	: 'auto',
				loadMsg		: '数据加载中......',
				singleSelect: true,
				fitColumns	: false,
				fit:true,
				columns:[[
					{field:'configname',title:'属性名称',width:150,
						styler:function(value,row,index){
							return 'background-color:#efefef;';
						}
					},
					{field:'configvalue',title:'属性值',width:260,
						editor:{
							type:'validatebox',
							options:{
								required:true,
								missingMessage:'此处请填写内容，不能为空！'
							}
						}
					},
					{field:'configmemo',title:'属性描述',width:260,
						styler:function(value,row,index){
							return 'background-color:#efefef;';
						}
					}
				]],
				toolbar:[{
					id:'btnConfigSave',
					text:'保存',
					iconCls:'icon_save',
					disabled:true,
					handler:function(){
						$('#configtable').datagrid('endEdit', lastIndex);
						var rows = $('#configtable').datagrid('getChanges');
						if (rows.length == 0) {
							// 并且禁止保存、还原按钮
							$('#btnConfigSave').linkbutton('disable');
							$('#btnConfigReject').linkbutton('disable');
		    				return;
						}
						var changesRows = {
	    	    				updated : []
	    	    		};
						for(var i=0; i<rows.length; i++){
							changesRows.updated.push(rows[i]);
						}
						//alert(JSON.stringify(changesRows));
	   					var par = "par=" + JSON.stringify(changesRows);

	   					$.post("saveConfig.action",par,function(data){
		   						new $.Zebra_Dialog(data, {
					  				'buttons':  false,
					   			    'modal': false,
					   			    'position': ['right - 20', 'top + 20'],
					   			    'auto_close': 2500
					            });
   							}
	   					);
	   					
	   					// 保存成功后，可以刷新页面，也可以：
						//$('#configtable').datagrid('acceptChanges');
						$('#configtable').datagrid('reload');

						// 并且禁止保存、还原按钮
						$('#btnConfigSave').linkbutton('disable');
						$('#btnConfigReject').linkbutton('disable');
					}
				},'-',{
					id	:'btnConfigReject',
					text:'还原',
					iconCls:'icon-reject',
					disabled:true,
					handler:function(){
						$('#configtable').datagrid('rejectChanges');
						// 并且禁止保存、还原按钮
						$('#btnConfigSave').linkbutton('disable');
						$('#btnConfigReject').linkbutton('disable');
					}
				},'-',{
					id	:'btnConfigRefresh',
					text:'刷新',
					iconCls:'icon_refresh',
					handler:function(){
						$('#configtable').datagrid('reload');
						lastIndex = -1;
						// 并且禁止保存、还原按钮
						$('#btnConfigSave').linkbutton('disable');
						$('#btnConfigReject').linkbutton('disable');
					}
				}],
				onBeforeLoad:function(){
					$(this).propertygrid('rejectChanges');
				},
				onBeforeEdit:function(index,row){  
					$('#btnConfigSave').linkbutton('enable');
					$('#btnConfigReject').linkbutton('enable');
			    },
			    onClickRow:function(rowIndex){
			    	if (lastIndex != rowIndex){
						$('#configtable').datagrid('endEdit', lastIndex);
						$('#configtable').datagrid('beginEdit', rowIndex);
					}
					lastIndex = rowIndex;
				}
			});
		});
		
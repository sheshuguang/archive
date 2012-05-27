		

		var lastIndex = -1;
		var isEdit = 0;
		var serverType = [
		    		    {typeid:'FTP',name:'FTP'},
		    		    {typeid:'LOCAL',name:'本机'}
		    		];
		$(function(){
			$('#docservertable').datagrid({
				url		: 'listDocserver.action',
				title	: '电子全文服务器列表',
				width	: 800,
				height	: 'auto',
				fitColumns	: false,
				rownumbers	: true,
				loadMsg		: '数据加载中......',
				singleSelect: true,
				idField		: "docserverid",
				fit:true,
				columns:[[
					{field:'servername',title:'服务器名称',width:100,editor:'text'},
					{field:'serverip',title:'服务器IP',width:100,editor:'text'},
					{field:'ftpuser',title:'Ftp帐户名',width:80,editor:'text'},
					{field:'ftppassword',title:'Ftp密码',width:90,editor:'text'},
					{field:'serverport',title:'Ftp端口',width:50,align:'center',editor:'text'
//                        editor:{
//                            type:'validatebox',
//                            options:{
//                                validType:'number'
////                                missingMessage:'请填写端口，不能为空！'
//                            }
//                        }
                    },
					{field:'serverpath',title:'服务器路径',width:160,editor:'text'},
					{field:'servertype',title:'类型',width:80,align:'center',//formatter:'serverTypeFormatter',
						formatter:function(value){
							for(var i=0; i<serverType.length; i++){
								if (serverType[i].typeid == value) return serverType[i].name;
							}
						},
						editor:{
							type:'combobox',
							options:{
								valueField	:'typeid',
								textField	:'name',
								data		:serverType,
								required	:true
							}
						}
					},
					{field:'serverstate',title:'状态',width:50,
						formatter:function(value,rec){
							if (value == 1) {
								return '<span style="color:red">启用</span>';
							}
							else {
								return '备用';
							}
							
						}
					},
					{field:'servermemo',title:'服务器描述',width:160,editor:'text'}
				]],
				toolbar:[{
					text:'添加',
					iconCls:'icon_docserver_add',
					handler:function(){
						var ifValidateRow = $('#docservertable').datagrid('validateRow',lastIndex);
						if (!ifValidateRow) {
							$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再添加新行! ', {
				                'type':     'information',
				                'title':    '系统提示',
				                'buttons':  ['确定']
				            });
							return;
						}
						$('#docservertable').datagrid('endEdit', lastIndex);
						$('#docservertable').datagrid('appendRow',{
							docserverid:UUID.prototype.createUUID (),
							servername:'',
							serverip:'',
							ftpuser:'',
							ftppassword:'',
							serverport:'',
							serverpath:'',
							servertype:'FTP',
							servermemo:''
						});
						lastIndex = $('#docservertable').datagrid('getRows').length-1;
						$('#docservertable').datagrid('selectRow', lastIndex);
						$('#docservertable').datagrid('beginEdit', lastIndex);
						$('#btnDocserverSave').linkbutton('enable');
						$('#btnDocserverReject').linkbutton('enable');
						isEdit = 1;
					}
				},{
					text:'删除',
					iconCls:'icon_docserver_delete',
					handler:function(){
						var row = $('#docservertable').datagrid('getSelected');
						if (row){
							var index = $('#docservertable').datagrid('getRowIndex', row);
							$('#docservertable').datagrid('deleteRow', index);
							$('#btnDocserverSave').linkbutton('enable');
							$('#btnDocserverReject').linkbutton('enable');
						}
						else {
							new $.Zebra_Dialog('请先选择要删除的服务器!', {
				  				'buttons':  false,
				   			    'modal': false,
				   			    'position': ['right - 20', 'top + 20'],
				   			    'auto_close': 2500
				            });
						}
						
					}
				},{
					text:'编辑',
					iconCls:'icon_docserver_edit',
					handler:function(){
						var ifValidateRow = $('#docservertable').datagrid('validateRow',lastIndex);
						if (!ifValidateRow) {
							$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再编辑新行! ', {
				                'type':     'information',
				                'title':    '系统提示',
				                'buttons':  ['确定']
				            });
							return;
						}
						var row = $('#docservertable').datagrid('getSelected');
						if (row){
							var index = $('#docservertable').datagrid('getRowIndex', row);
							if (lastIndex != index){
								$('#docservertable').datagrid('endEdit', lastIndex);
								$('#docservertable').datagrid('beginEdit', index);
							}
							else {
								$('#docservertable').datagrid('beginEdit', index);
							}
							lastIndex = index;
							isEdit = 1;
							$('#btnDocserverSave').linkbutton('enable');
							$('#btnDocserverReject').linkbutton('enable');
						}
					}
				},'-',{
					id	:'btnLink',
					text:'测试',
					iconCls:'icon_docserver_link',
					handler:function(){
						if (isEdit == 1) {
							new $.Zebra_Dialog('编辑状态下，不能测试服务器连接.请先保存!', {
				  				'buttons':  false,
				   			    'modal': false,
				   			    'position': ['right - 20', 'top + 20'],
				   			    'auto_close': 2500
				            });
							return;
						}
						var row = $('#docservertable').datagrid('getSelected');
						if (row){
							var par = "docserverid=" + row.docserverid;
		   					$.post("testFtp.action",par,function(data){
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
							new $.Zebra_Dialog('请先选择要测试连接的服务器!', {
				  				'buttons':  false,
				   			    'modal': false,
				   			    'position': ['right - 20', 'top + 20'],
				   			    'auto_close': 2500
				            });
						}
						
					}
				},{
					id	:'btn',
					text:'设置状态',
					iconCls:'icon_docserver_go',
					handler:function(){
						if (isEdit == 1) {
							new $.Zebra_Dialog('编辑状态下，不能更改服务器状态.请先保存!', {
				  				'buttons':  false,
				   			    'modal': false,
				   			    'position': ['right - 20', 'top + 20'],
				   			    'auto_close': 2500
				            });
							return;
						}
						var row = $('#docservertable').datagrid('getSelected');
						if (row){
							$.Zebra_Dialog('<span style="color:red">注意：更改文件服务器的状态，涉及电子文件的存储位置。系统中只能有一个服务器状态为【启用】。</span><br>确定要更改选择的服务器状态为【启用】吗？', {
								 'type':     'question',
				                 'title':    '系统提示',
				                 'buttons':  ['确定', '取消'],
				                 'onClose':  function(caption) {
				                     if (caption == '确定') {
				                    	 var par = "docserverid=" + row.docserverid;
					   					 $.post("setDocserverState.action",par,function(data){
						   						new $.Zebra_Dialog(data, {
									  				'buttons':  false,
									   			    'modal': false,
									   			    'position': ['right - 20', 'top + 20'],
									   			    'auto_close': 2500
									            });
				   							}
					   					 );
					   					 $('#docservertable').datagrid('reload');
				                     }
				                 }
					         });
							
						}
						else {
							new $.Zebra_Dialog('请先选择要测试连接的服务器!', {
				  				'buttons':  false,
				   			    'modal': false,
				   			    'position': ['right - 20', 'top + 20'],
				   			    'auto_close': 2500
				            });
						}
						
					}
				},'-',{
					id:'btnDocserverSave',
					text:'保存',
					iconCls:'icon-page-save',
					disabled:true,
					handler:function(){
						var ifValidateRow = $('#docservertable').datagrid('validateRow',lastIndex);
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
							$('#docservertable').datagrid('endEdit', lastIndex);
						}
	    				var insertRows = $('#docservertable').datagrid('getChanges','inserted');
	    				var updateRows = $('#docservertable').datagrid('getChanges','updated');
	    				var deleteRows = $('#docservertable').datagrid('getChanges','deleted');
	    				if (insertRows.length == 0 && updateRows.length == 0 && deleteRows.length == 0) {
		    				isEdit = 0;
							// 并且禁止保存、还原按钮
							$('#btnDocserverSave').linkbutton('disable');
							$('#btnDocserverReject').linkbutton('disable');
		    				return;
	    				};
	    				var changesRows = {
	    	    				inserted : [],
	    	    				updated : [],
	    	    				deleted : []
	    	    				};
	   					if (insertRows.length>0) {
	   						for (var i=0;i<insertRows.length;i++) {
                                if (insertRows[i].serverport == "") {
                                    insertRows[i].serverport = 0;
                                }
	   							changesRows.inserted.push(insertRows[i]);
	   						}
	   					}
	
	   					if (updateRows.length>0) {
	   						for (var k=0;k<updateRows.length;k++) {
                               if (updateRows[k].serverport == "") {
                                   updateRows[k].serverport = 0;
                               }
	   							changesRows.updated.push(updateRows[k]);
	   						}
	   					}
	   					
	   					if (deleteRows.length>0) {
	   						for (var j=0;j<deleteRows.length;j++) {
                                   if (deleteRows[j].serverport == "") {
                                       deleteRows[j].serverport = 0;
                                   }
                                   changesRows.deleted.push(deleteRows[j]);
	   						}
	   					}
	   					var par = "par=" + JSON.stringify(changesRows);

	   					$.post("saveDocserver.action",par,function(data){
		   						new $.Zebra_Dialog(data, {
					  				'buttons':  false,
					   			    'modal': false,
					   			    'position': ['right - 20', 'top + 20'],
					   			    'auto_close': 2500
					            });
   							}
	   					);
	   					
	   					// 保存成功后，可以刷新页面，也可以：
						$('#docservertable').datagrid('acceptChanges');
//	   					$('#docservertable').datagrid('reload');
						$('#docservertable').datagrid('clearSelections');

						isEdit = 0;
						// 并且禁止保存、还原按钮
						$('#btnDocserverSave').linkbutton('disable');
						$('#btnDocserverReject').linkbutton('disable');
					}
				},{
					id	:'btnDocserverReject',
					text:'还原',
					iconCls:'icon-reject',
					disabled:true,
					handler:function(){
						$('#docservertable').datagrid('rejectChanges');
						isEdit = 0;
						// 并且禁止保存、还原按钮
						$('#btnDocserverSave').linkbutton('disable');
						$('#btnDocserverReject').linkbutton('disable');
					}
				},{
					id	:'btnDocserverRefresh',
					text:'刷新',
					iconCls:'icon_refresh',
					handler:function(){
						$('#docservertable').datagrid('reload');
						isEdit = 0;
						lastIndex = -1;
						// 并且禁止保存、还原按钮
						$('#btnDocserverSave').linkbutton('disable');
						$('#btnDocserverReject').linkbutton('disable');
					}
				}],
				onBeforeLoad:function(){
					$(this).datagrid('rejectChanges');
				},
			    onClickRow:function(rowIndex){
				    if (isEdit == 1) {
				    	if (lastIndex != rowIndex){
							$('#docservertable').datagrid('endEdit', lastIndex);
							$('#docservertable').datagrid('beginEdit', rowIndex);
						}
						lastIndex = rowIndex;
				    }
				}
			});
		});



function codemanage(){
	var lastIndex = -1;
	var isEdit = 0;
	$('#addcodetable').datagrid({
		url		: 'listCode.action?templetfieldid=' + selectField.templetfieldid,
		title	: '字段代码列表',
		width	: 800,
		height	: 'auto',
		fitColumns	: true,
		rownumbers	: true,
		loadMsg		: '数据加载中......',
		singleSelect: false,
		idField		: "codeid",
		fit:true,
		columns:[[
		    {
		    	field:'columnname',title:'代码名称',width:70,
					editor:{
						type:'validatebox',
						options:{
							required:true,
							missingMessage:'请填写代码名称，不能为空！'
							
						}
					}
			},{
				field:'columndata',title:'代码值',width:70,
				editor:{
					type:'validatebox',
					options:{
						required:true,
						missingMessage:'请填写代码值，不能为空！'
					}
				}
			},{
				field:'codeorder',title:'排序',width:80,align:'center',
				editor:{
					type:'numberbox',
					options:{
						required:true,
						validType:'number',
						missingMessage:'请填写排序，不能为空，只能填写数字！'
					}
				}
			}
		]],
		toolbar:[{
			text:'添加',
			iconCls:'icon-application_form_add',
			handler:function(){
				var ifValidateRow = $('#addcodetable').datagrid('validateRow',lastIndex);
				if (!ifValidateRow) {
					new $.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再添加新行!', {
		  				'buttons':  false,
		   			    'modal': false,
		   			    'position': ['right - 20', 'top + 20'],
		   			    'auto_close': 2500
		            });
					return;
				}
				$('#addcodetable').datagrid('endEdit', lastIndex);
				$('#addcodetable').datagrid('appendRow',{
					codeid:UUID.prototype.createUUID (),
					columnname:'',
					columndata:'',
					codeorder:0,
					templetfieldid:selectField.templetfieldid
				});
				lastIndex = $('#addcodetable').datagrid('getRows').length-1;
				$('#addcodetable').datagrid('selectRow', lastIndex);
				$('#addcodetable').datagrid('beginEdit', lastIndex);
				$('#btnSaveCode').linkbutton('enable');
				$('#btnrejectCode').linkbutton('enable');
				isEdit = 1;
			}
		},{
			text:'删除',
			iconCls:'icon-application_form_delete',
			handler:function(){
				var rows = $('#addcodetable').datagrid('getSelections');
				if (rows.length > 0){
					var c=new Array(rows.length);
					for (var j=0;j<rows.length;j++) {
						var n = 0;
						var indexJ = $('#addcodetable').datagrid('getRowIndex', rows[j]);
						for (var z=0;z<rows.length;z++) {
							var indexZ = $('#addcodetable').datagrid('getRowIndex', rows[z]);
							if (indexJ <= indexZ) {
								n = n + 1;
							}
						}
						c[n-1] = indexJ;
					}
					for (var i=0;i<c.length;i++ ) {
						$('#addcodetable').datagrid('deleteRow', c[i]);
					}
					$('#btnSaveCode').linkbutton('enable');
					$('#btnrejectCode').linkbutton('enable');
				}
				else {
					new $.Zebra_Dialog('请先选择要删除的代码!', {
		  				'buttons':  false,
		   			    'modal': false,
		   			    'position': ['right - 20', 'top + 20'],
		   			    'auto_close': 2500
		            });
				}
			}
		},{
			text:'编辑',
			iconCls:'icon-application_form_edit',
			handler:function(){
				var ifValidateRow = $('#addcodetable').datagrid('validateRow',lastIndex);
				if (!ifValidateRow) {
					$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再编辑新行! ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}
				var row = $('#addcodetable').datagrid('getSelected');
				if (row){
					var index = $('#addcodetable').datagrid('getRowIndex', row);
					if (lastIndex != index){
						$('#addcodetable').datagrid('endEdit', lastIndex);
						$('#addcodetable').datagrid('beginEdit', index);
					}
					else {
						$('#addcodetable').datagrid('beginEdit', index);
					}
					lastIndex = index;
					isEdit = 1;
					$('#btnSaveCode').linkbutton('enable');
					$('#btnrejectCode').linkbutton('enable');
				}
			}
		},'-',{
			id	:'btncopy',
			text:'复制',
			iconCls:'icon-page-copy',
			handler:function(){
				var rows = $('#addcodetable').datagrid('getSelections');
				if (rows.length > 0){
					if (copyMap.size() > 0) {
						copyMap.clear();
					}
					var fields =  $('#addcodetable').datagrid('getColumnFields');
					
					for (var i = 0;i< rows.length;i++) {
						var rowMap = new HashMap();
						for (var j=0;j<fields.length;j++) {
							rowMap.put(fields[j],rows[i][fields[j]]);
						}
						copyMap.put(i,rowMap);
					}
					if (copyMap.size() == rows.length) {
						new $.Zebra_Dialog('复制成功，可选择任意档案类型下的字段粘贴代码！', {
			  				'buttons':  false,
			   			    'modal': false,
			   			    'position': ['right - 20', 'top + 20'],
			   			    'auto_close': 2500
			            });
					}
//					for (var i=0;i<fields.length;i++ ) {
//						map.put(aa[i],rows[fields[i]]);
//					}
//					alert(map.get("chinesename"));
//					alert(map.get("isgridshow"));
//					alert(map.size());
		//			map.clear();
		//			alert(map.size());
				}
				else {
					new $.Zebra_Dialog('请先选择要复制的代码行,可多选!', {
		  				'buttons':  false,
		   			    'modal': false,
		   			    'position': ['right - 20', 'top + 20'],
		   			    'auto_close': 2500
		            });
				}
			}
		},{
			id	:'btnpaste',
			text:'粘贴',
			iconCls:'icon-page-attach',
			handler:function(){
				if (copyMap.size() == 0) {
					new $.Zebra_Dialog('剪贴板没有内容，请先复制！!', {
		  				'buttons':  false,
		   			    'modal': false,
		   			    'position': ['right - 20', 'top + 20'],
		   			    'auto_close': 2500
		            });
					return;
				}
				if (lastIndex > 0) {
					$('#addcodetable').datagrid('endEdit', lastIndex);
				}
				for (var i=0;i<copyMap.size();i++) {
					$('#addcodetable').datagrid('appendRow',{
						codeid:UUID.prototype.createUUID (),
						columnname:copyMap.get(i).get("columnname"),
						columndata:copyMap.get(i).get("columndata"),
						codeorder:copyMap.get(i).get("codeorder"),
						templetfieldid:selectField.templetfieldid
					});
				}
				lastIndex = $('#addcodetable').datagrid('getRows').length-1;
				$('#addcodetable').datagrid('selectRow', lastIndex);
				$('#addcodetable').datagrid('beginEdit', lastIndex);
				$('#btnSaveCode').linkbutton('enable');
				$('#btnrejectCode').linkbutton('enable');
				isEdit = 1;
			}
		},'-',{
			id:'btnSaveCode',
			text:'保存',
			iconCls:'icon-page-save',
			disabled:true,
			handler:function(){
				var ifValidateRow = $('#addcodetable').datagrid('validateRow',lastIndex);
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
					$('#addcodetable').datagrid('endEdit', lastIndex);
				}
				var insertRows = $('#addcodetable').datagrid('getChanges','inserted');
				var updateRows = $('#addcodetable').datagrid('getChanges','updated');
				var deleteRows = $('#addcodetable').datagrid('getChanges','deleted');
				if (insertRows.length == 0 && updateRows.length == 0 && deleteRows.length == 0) {
    				isEdit = 0;
					// 并且禁止保存、还原按钮
					$('#btnSaveCode').linkbutton('disable');
					$('#btnrejectCode').linkbutton('disable');
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
				var par = "par=" + JSON.stringify(changesRows);

				$.post("saveCode.action",par,function(data){
						new $.Zebra_Dialog(data, {
			  				'buttons':  false,
			   			    'modal': false,
			   			    'position': ['right - 20', 'top + 20'],
			   			    'auto_close': 2500
			            });
					}
				);
				
				// 保存成功后，可以刷新页面，也可以：
				$('#addcodetable').datagrid('acceptChanges');
				$('#addcodetable').datagrid('clearSelections');
				isEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnSaveCode').linkbutton('disable');
				$('#btnrejectCode').linkbutton('disable');
			}
		},{
			id	:'btnrejectCode',
			text:'还原',
			iconCls:'icon-reject',
			disabled:true,
			handler:function(){
				$('#addcodetable').datagrid('rejectChanges');
				isEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnSaveCode').linkbutton('disable');
				$('#btnrejectCode').linkbutton('disable');
			}
		},{
//			id	:'btnrefresh',
			text:'刷新',
			iconCls:'icon_refresh',
			handler:function(){
				$('#addcodetable').datagrid('reload');
				$('#addcodetable').datagrid('clearSelections');
				isEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnSaveCode').linkbutton('disable');
				$('#btnrejectCode').linkbutton('disable');
			}
		}],
	    onClickRow:function(rowIndex){
		    if (isEdit == 1) {
		    	if (lastIndex != rowIndex){
					$('#addcodetable').datagrid('endEdit', lastIndex);
					$('#addcodetable').datagrid('beginEdit', rowIndex);
					$('#addcodetable').datagrid('unselectRow',lastIndex);
					$('#addcodetable').datagrid('selectRow',rowIndex);
				}
				lastIndex = rowIndex;
		    }
		}
	});
}



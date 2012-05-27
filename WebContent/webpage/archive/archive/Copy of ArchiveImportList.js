var lastIndex = -1;
var isEdit = 0;
var columns = [ [] ];
// 动态字段添加时，附带条件。例如默认值
var fieldsDefaultValue = [];

$(function() {
	var par = "treeid=" + selectTreeid + "&tableType=01&importType=1";
	$.ajax({
		async : false,
		url : "createField.action?" + par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				columns = fields;
				fieldsDefaultValue = fieldsDefaultValue;
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
	$('#archiveimporttable').datagrid({
		// url : 'listArchive.action?treeid=' + selectTreeid + '&tableType=01',
		title : selectTreeName + '_案卷导入',
		width : 800,
		height : 'auto',
		fitColumns : false,
		pagination : true,
		rownumbers : true,
		loadMsg : '数据加载中......',
		singleSelect : false,
		idField : "ID",
		fit : true,
		columns : columns,
		toolbar : [ {
			text : '选择文件',
			iconCls : 'icon-page-add',
			handler : function() {
				$("#selectfileWindow").show();
				var $win;
				$win = $('#selectfileWindow').window({
					title : ' 字段代码管理',
					width : 400,
					height : 130,
					top : ($(window).height() - 130) * 0.5,
					left : ($(window).width() - 400) * 0.5,
					shadow : true,
					modal : true,
					iconCls : 'icon-application_form_add',
					closed : true,
					minimizable : false,
					maximizable : false,
					collapsible : false,
					onClose : function() {
					}
				});
				$("#selectfileWindow").window('open');
			}
		},{
			text:'删除',
			iconCls:'icon-page-delete',
			handler:function(){
				var rows = $('#archiveimporttable').datagrid('getSelections');
				if (rows.length > 0){
					$.Zebra_Dialog('确定要删除选中的档案条目吗?', {
						'type':     'question',
		                'title':    '系统提示',
		                'buttons':  ['确定', '取消'],
		                'onClose':  function(caption) {
		                    if (caption == '确定') {
		                    	for (var i = rows.length - 1; i >= 0 ;i-- ) {
		                    		$('#archiveimporttable').datagrid('deleteRow', rows[i]);
		                    	}
		                    }

//		                    	var c=new Array(rows.length);
//		    					for (var j=0;j<rows.length;j++) {
//		    						var n = 0;
//		    						var indexJ = $('#archiveimporttable').datagrid('getRowIndex', rows[j]);
//		    						for (var z=0;z<rows.length;z++) {
//		    							var indexZ = $('#archiveimporttable').datagrid('getRowIndex', rows[z]);
//		    							if (indexJ <= indexZ) {
//		    								n = n + 1;
//		    							}
//		    						}
//		    						c[n-1] = indexJ;
//		    					}
//		    					for (var i=0;i<c.length;i++ ) {
//		    						$('#archiveimporttable').datagrid('deleteRow', c[i]);
//		    					}
//		    					$('#btnSaveArchive').linkbutton('enable');
//		                    }
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
				//得到选中的行
				var row = $('#archiveimporttable').datagrid('getSelected');
				
				var rowNum = $('#archiveimporttable').datagrid('getRows');
				var ifValidateRow = false;
				if (rowNum){
					for (var i=0;i<rowNum.length;i++) {
						if (!$('#archiveimporttable').datagrid('validateRow',i)) {
							ifValidateRow = true;
							break;
						}
					}
				}
				if (ifValidateRow) {
					$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再编辑新行! ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}
				
				if (rowNum){
					for (var i=0;i<rowNum.length;i++) {
						$('#archiveimporttable').datagrid('endEdit', i);
						$('#archiveimporttable').datagrid('unselectRow',i);
					}
					isEdit = 0;
				}
				
				
//				var row = $('#archivetable').datagrid('getSelected');
				if (row){
					var index = $('#archiveimporttable').datagrid('getRowIndex', row);
					
					$('#archiveimporttable').datagrid('beginEdit', index);
					lastIndex = index;
					isEdit = 1;
				}
			}
		},'-',{
			id:'btnImportSaveArchive',
			text:'保存',
			iconCls:'icon-page-save',
			disabled:true,
			handler:function(){
				var rowNum = $('#archiveimporttable').datagrid('getRows');
				var ifValidateRow = false;
				if (rowNum){
					for (var i=0;i<rowNum.length;i++) {
						if (!$('#archiveimporttable').datagrid('validateRow',i)) {
							ifValidateRow = true;
							break;
						}
					}
				}
				if (ifValidateRow) {
					$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再编辑新行! ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}
				if (rowNum){
					for (var i=0;i<rowNum.length;i++) {
						$('#archiveimporttable').datagrid('endEdit', i);
						$('#archiveimporttable').datagrid('unselectRow',i);
					}
					isEdit = 0;
				}
//				var insertRows = $('#archiveimporttable').datagrid('getChanges','inserted');
				var insertRows = $('#archiveimporttable').datagrid('getRows');
//				var updateRows = $('#archiveimporttable').datagrid('getChanges','updated');
//				var deleteRows = $('#archiveimporttable').datagrid('getChanges','deleted');
				if (insertRows.length == 0) {
    				isEdit = 0;
					// 并且禁止保存、还原按钮
					$('#btnImportSaveArchive').linkbutton('disable');
    				return;
				}
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
				$('#archiveimporttable').datagrid('acceptChanges');
				$('#archiveimporttable').datagrid('clearSelections');
				isEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnImportSaveArchive').linkbutton('disable');
			}
		},{
			text:'结束编辑',
			iconCls:'icon-page-edit',
			handler:function(){
				var rowNum = $('#archiveimporttable').datagrid('getRows');
				var ifValidateRow = false;
				if (rowNum){
					for (var i=0;i<rowNum.length;i++) {
						var aa = $('#archiveimporttable').datagrid('validateRow',lastIndex);
						if (!$('#archiveimporttable').datagrid('validateRow',i)) {
							ifValidateRow = true;
							break;
						}
					}
				}
				if (ifValidateRow) {
					$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再编辑新行! ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}

				if (rowNum){
					for (var i=0;i<rowNum.length;i++) {
						$('#archiveimporttable').datagrid('endEdit', i);
						$('#archiveimporttable').datagrid('unselectRow',i);
					}
					isEdit = 0;
				}
			}
		}],
		onLoadSuccess:function (){
		    $("#archiveimporttable").datagrid("loaded");
		},
		onClickRow : function(rowIndex, rowData) {
//			$('#archiveimporttable').datagrid('endEdit');

			if (isEdit == 1) {
				if (lastIndex != rowIndex) {
					$('#archiveimporttable').datagrid('endEdit', lastIndex);
					$('#archiveimporttable').datagrid('beginEdit', rowIndex);
				}
				lastIndex = rowIndex;
			}
		}
	});
	
	//设置分页控件 
    var p = $('#archiveimporttable').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [10,20,30],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
        /*onBeforeRefresh:function(){
            $(this).pagination('loading');
            alert('before refresh');
            $(this).pagination('loaded');
        }*/ 
    });  

});

function importArchive() {
	//判断上传文件类型
	var a = selectfile_Validator($('#selectfile').val());
	if (a) {
		$('#selectfileWindow').window('close');
		$('#importArchiveForm').attr('action','upload.action?treeid='+selectTreeid+'&tableType=01');
		$('#importArchiveForm').submit();
	}
}

function showCallback(backType,jsonStr) {
	
	if (backType == "failure") {
		alert(jsonStr);
	}
	else {
		$('#archiveimporttable').datagrid('loading');
		var json = JSON.parse(jsonStr);
		
		//得到所有列
//		var fields =  $('#archiveimporttable').datagrid('getColumnFields');
		
//		for(var i=0; i<json.length; i++) {
//			var addFields = {};
//			addFields["ID"] = UUID.prototype.createUUID ();
//			addFields["TREEID"] = selectTreeid;
//			addFields["ISDOC"] = 0;
//			for(var key in json[i]){
//				addFields[key] = json[i][key];
//			}
//			
////			for (var j=0;j<fields.length;j++) {
////				for (var k=0;k<fieldsDefaultValue.length;k++) {
////					if (fields[j] == fieldsDefaultValue[k].fieldname) {
////						var b = false;
////						for(var key in json[i]){
////							if (fields[j] == key) {
////								if (json[i][key] != "") {
////									b = true;
////								}
////								break;
////							}
////						}
////						if (!b) {
////							addFields[fields[j]] = fieldsDefaultValue[k].value;
////						}
////					}
////				}
////			}
//			
//			$('#archiveimporttable').datagrid('appendRow',addFields);
//			lastIndex = $('#archiveimporttable').datagrid('getRows').length-1;
//			$('#archiveimporttable').datagrid('selectRow', lastIndex);
//			$('#archiveimporttable').datagrid('beginEdit', lastIndex);
//
////			isEdit = 1;
//		}
		
		$('#archiveimporttable').datagrid('loadData',json); 
		
		$('#btnImportSaveArchive').linkbutton('enable');
		isEdit = 1;
	}
	
}

function selectfile_Validator(selectfile) {
	if (selectfile == " ") {
		alert("请选择Excel类型的导入文件！ ");
		return false;
	}
	var last = selectfile.match(/^(.*)(\.)(.{1,8})$/)[3]; // 检查上传文件格式
	last = last.toUpperCase();
	if (last == "XLS") {
	} else {
		alert("只能上传Excel文件,请重新选择！ ");
		return false;
	}
	return true;
}

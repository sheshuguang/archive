var lastIndex = -1;
var isEdit = 0;

var fieldType = [
	    		    {typeid:'VARCHAR',name:'字符串型'},
	    		    {typeid:'INT',name:'整数型'}
	    		];
var selectField;

$(function(){
	$('#templetfieldtable').datagrid({
		url		: 'listTempletfield.action',
		title	: $('#selectTempletName').val() + '_字段属性列表',
		width	: 800,
		height	: 'auto',
		fitColumns	: false,
		rownumbers	: true,
		loadMsg		: '数据加载中......',
		singleSelect: false,
		idField		: "templetfieldid",
		fit:true,
		/*
		frozenColumns:[
			[{
				field:'templetfieldid',
				checkbox:true
			}]
		],
		*/
		columns:[[
		    {
		    	field:'chinesename',title:'名称',width:70,
					editor:{
						type:'validatebox',
						options:{
							required:true
						}
					}
			},{
				field:'englishname',title:'英文名',width:70
			},{
				field:'fieldtype',title:'类型',width:80,align:'center',
				formatter:function(value){
					for(var i=0; i<fieldType.length; i++){
						if (fieldType[i].typeid == value) return fieldType[i].name;
					}
				}
//					editor:{
//						type:'combobox',
//						options:{
//							valueField	:'typeid',
//							textField	:'name',
//							data		:fieldType,
//							required	:true
//						}
//					}
			},{
				field:'fieldsize',title:'长度',width:50,align:'center',
					editor:{
						type:'validatebox',
						options:{
							required:true,
							validType:'number',
							missingMessage:'请填写长度，不能为空！'
						}
					}
			},{
				field:'defaultvalue',title:'默认值',width:80,editor:'text'
			},{
				field:'isrequire',title:'必著',width:50,align:'center',
					editor:{
						type:'checkbox',
						options:{
							on:"<img alt='1' src='../../images/icons/accept.png' title='必须著录'>",
							off:"<img alt='0' src='../../images/icons/blank.gif'/>"
						}
					}
			},{
				field:'isunique',title:'唯一',width:50,align:'center',
					editor:{
						type:'checkbox',
						options:{
							on:"<img alt='1' src='../../images/icons/accept.png' title='字段属性值唯一，不能重复!'>",
							off:"<img alt='0' src='../../images/icons/blank.gif'/>"
						}
					}
			},{
				field:'issearch',title:'检索字段',width:60,align:'center',
					editor:{
						type:'checkbox',
						options:{
							on:"<img alt='1' src='../../images/icons/accept.png' title='这个字段可以模糊检索!'>",
							off:"<img alt='0' src='../../images/icons/blank.gif'>"
						}
					}
			},{
				field:'isgridshow',title:'列表显示',width:60,align:'center',
					editor:{
						type:'checkbox',
						options:{
							on:"<img alt='1' src='../../images/icons/accept.png' title='这个字段在列表上显示!'>",
							off:"<img alt='0' src='../../images/icons/blank.gif'>"
						}
					}
			},{
				field:'sort',title:'排序',width:60,editor:'numberbox',align:'center'
			},{
				field:'iscode',title:'代码',width:50,align:'center'
				/*	
				editor:{
						type:'checkbox',
						options:{
							on:"<img alt='1' src='../../images/icons/accept.png' title='这个字段是著录时是选择项!'>",
							off:"<img alt='0' src=''>"
						}
					}*/
			}
		]],
		rowStyler:function(index,row,css){
			if (row.sort < 0){
				return 'color:red;font-weight:bold;';
			}
		},
		toolbar:[{
			text:'添加',
			iconCls:'icon-application_form_add',
			handler:function(){
				$("#addTempletfieldWindow").show();
				var $win;
		   	    $win = $('#addTempletfieldWindow').window({
			   	    title:' 新建字段属性',
		   	        width: 400,
		   	        height: 430,
		   	        top:($(window).height() - 400) * 0.5,
		            left:($(window).width() - 430) * 0.5,
		   	        shadow: true,
		   	        modal:true,
		   	        iconCls:'icon-application_form_add',
		   	        closed:true,
		   	        minimizable:false,
		   	        maximizable:false,
		   	        collapsible:false
		   	    });
	   	    	$("#addTempletfieldWindow").window('open');
			}
		},{
			text:'删除',
			iconCls:'icon-application_form_delete',
			handler:function(){
				var rows = $('#templetfieldtable').datagrid('getSelections');
				if (rows.length > 0){
					var c=new Array(rows.length);
					for (var j=0;j<rows.length;j++) {
						var n = 0;
						if (rows[j].sort >= 0) {
							var indexJ = $('#templetfieldtable').datagrid('getRowIndex', rows[j]);
							for (var z=0;z<rows.length;z++) {
								var indexZ = $('#templetfieldtable').datagrid('getRowIndex', rows[z]);
								if (indexJ <= indexZ) {
									n = n + 1;
								}
							}
							c[n-1] = indexJ;
						}
					}
					for (var i=0;i<c.length;i++ ) {
						$('#templetfieldtable').datagrid('deleteRow', c[i]);
					}
					$('#btnsaveTempletfield').linkbutton('enable');
					$('#btnrejectTempletfield').linkbutton('enable');
				}
				else {
					new $.Zebra_Dialog('请先选择要删除的帐户!', {
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
				var ifValidateRow = $('#templetfieldtable').datagrid('validateRow',lastIndex);
				if (!ifValidateRow) {
					$.Zebra_Dialog('正在编写的行包含不能为空的字段，请输入内容，再编辑新行! ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}
				var row = $('#templetfieldtable').datagrid('getSelected');
				if (row){
					var index = $('#templetfieldtable').datagrid('getRowIndex', row);
					if (lastIndex != index){
						$('#templetfieldtable').datagrid('endEdit', lastIndex);
						$('#templetfieldtable').datagrid('beginEdit', index);
					}
					else {
						$('#templetfieldtable').datagrid('beginEdit', index);
					}
					lastIndex = index;
					isEdit = 1;
					$('#btnsaveTempletfield').linkbutton('enable');
					$('#btnrejectTempletfield').linkbutton('enable');
				}
			}
		},'-',{
			id	:'btnCode',
			text:'代码',
			iconCls:'icon-page-copy',
			handler:function(){
				var ifValidateRow = $('#templetfieldtable').datagrid('validateRow',lastIndex);
				if (!ifValidateRow) {
					$.Zebra_Dialog('正在编写的行包含不能为空的字段，请保存后再编写字段代码! ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}
				var rows = $('#templetfieldtable').datagrid('getSelections');
				if (rows.length > 1){
					$.Zebra_Dialog('您选择了多行进行代码管理，请只选择一行! ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}
				else if (rows.length == 0){
					$.Zebra_Dialog('请选择一行字段进行编辑代码! ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
					return;
				}
				selectField = rows[0];
				$("#addCodeWindow").show();
				var $win;
		   	    $win = $('#addCodeWindow').window({
			   	    title:' 字段代码管理',
		   	        width: 500,
		   	        height: 430,
		   	        top:($(window).height() - 400) * 0.5,
		            left:($(window).width() - 430) * 0.5,
		   	        shadow: true,
		   	        modal:true,
		   	        iconCls:'icon-application_form_add',
		   	        closed:true,
		   	        minimizable:false,
		   	        maximizable:false,
		   	        collapsible:false,
		   	        onClose:function() {
		   	    	$('#templetfieldtable').datagrid('reload');
		   	    	}
		   	    });
		   	    $("#addCodeWindow").window('open');
		   	    codemanage();
			}
		},'-',{
			id:'btnsaveTempletfield',
			text:'保存',
			iconCls:'icon-page-save',
			disabled:true,
			handler:function(){
				var ifValidateRow = $('#templetfieldtable').datagrid('validateRow',lastIndex);
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
					$('#templetfieldtable').datagrid('endEdit', lastIndex);
				}
				var insertRows = $('#templetfieldtable').datagrid('getChanges','inserted');
				var updateRows = $('#templetfieldtable').datagrid('getChanges','updated');
				var deleteRows = $('#templetfieldtable').datagrid('getChanges','deleted');
				if (insertRows.length == 0 && updateRows.length == 0 && deleteRows.length == 0) {
    				isEdit = 0;
					// 并且禁止保存、还原按钮
					$('#btnsaveTempletfield').linkbutton('disable');
					$('#btnrejectTempletfield').linkbutton('disable');
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
						updateRows[k].isrequire=$(updateRows[k].isrequire).attr("alt");
						updateRows[k].isunique=$(updateRows[k].isunique).attr("alt");
						updateRows[k].issearch=$(updateRows[k].issearch).attr("alt");
						updateRows[k].isgridshow=$(updateRows[k].isgridshow).attr("alt");
						updateRows[k].iscode=$(updateRows[k].iscode).attr("alt");
						changesRows.updated.push(updateRows[k]);
					}
				}
				
				if (deleteRows.length>0) {
					for (var j=0;j<deleteRows.length;j++) {
						deleteRows[j].isrequire=$(deleteRows[j].isrequire).attr("alt");
						deleteRows[j].isunique=$(deleteRows[j].isunique).attr("alt");
						deleteRows[j].issearch=$(deleteRows[j].issearch).attr("alt");
						deleteRows[j].isgridshow=$(deleteRows[j].isgridshow).attr("alt");
						deleteRows[j].iscode=$(deleteRows[j].iscode).attr("alt");
						changesRows.deleted.push(deleteRows[j]);
					}
				}
				//alert(JSON.stringify(changesRows));
				var par = "par=" + JSON.stringify(changesRows);

				$.post("saveTempletfield.action",par,function(data){
						new $.Zebra_Dialog(data, {
			  				'buttons':  false,
			   			    'modal': false,
			   			    'position': ['right - 20', 'top + 20'],
			   			    'auto_close': 2500
			            });
					}
				);
				
				// 保存成功后，可以刷新页面，也可以：
				$('#templetfieldtable').datagrid('acceptChanges');
				$('#templetfieldtable').datagrid('clearSelections');
				isEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnsaveTempletfield').linkbutton('disable');
				$('#btnrejectTempletfield').linkbutton('disable');
			}
		},{
			id	:'btnrejectTempletfield',
			text:'还原',
			iconCls:'icon-reject',
			disabled:true,
			handler:function(){
				$('#templetfieldtable').datagrid('rejectChanges');
				isEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnsaveTempletfield').linkbutton('disable');
				$('#btnrejectTempletfield').linkbutton('disable');
			}
		},{
//			id	:'btnrefresh',
			text:'刷新',
			iconCls:'icon_refresh',
			handler:function(){
				$('#templetfieldtable').datagrid('reload');
				$('#templetfieldtable').datagrid('clearSelections');
				isEdit = 0;
				// 并且禁止保存、还原按钮
				$('#btnsaveTempletfield').linkbutton('disable');
				$('#btnrejectTempletfield').linkbutton('disable');
			}
		}],
		onBeforeLoad:function(){
			//$(this).datagrid('rejectChanges');
		},
	    onClickRow:function(rowIndex){
		    if (isEdit == 1) {
		    	if (lastIndex != rowIndex){
					$('#templetfieldtable').datagrid('endEdit', lastIndex);
					$('#templetfieldtable').datagrid('beginEdit', rowIndex);
					$('#templetfieldtable').datagrid('unselectRow',lastIndex);
					$('#templetfieldtable').datagrid('selectRow',rowIndex);
				}
				lastIndex = rowIndex;
		    }
		}
	});
});

function saveAddTempletfield() {
	if(!$("#ff").form('validate')){
		return false;
	}
	var templetfieldid = UUID.prototype.createUUID();
	var chinesename = $("#chinesename").val();
	var englishname = $("#englishname").val();
	var fieldsize = $("#fieldsize").val();
	var fieldtype = $("#fieldtype").val();
	var defaultvalue = $("#defaultvalue").text();
	var sort = $("#sort").val();
	var isrequire = 0;
	var isunique = 0;
	var issearch = 0;
	var isgridshow = 0;
	
	if(document.getElementById("isrequire").checked) {
		isrequire= 1;
	}
	if(document.getElementById("isunique").checked) {
		isunique= 1;
	}
	if(document.getElementById("issearch").checked) {
		issearch= 1;
	}
	if(document.getElementById("isgridshow").checked) {
		isgridshow= 1;
	}
	
	var jsonStr = 'jsonStr={templetfieldid:"'+ templetfieldid +'",chinesename:"'+ chinesename +'",englishname:"'+ englishname +'",fieldsize:'+ fieldsize +',fieldtype:"'+ fieldtype +'"';
	jsonStr += ',defaultvalue:"'+defaultvalue+'",sort:"'+sort+'",isrequire:"'+isrequire+'",isunique:"'+isunique+'",issearch:"'+issearch+'",isgridshow:"'+isgridshow+'"}';
	
	$.post("addTempletfield.action",jsonStr,function(data){
		new $.Zebra_Dialog(data, {
			'buttons':  false,
		    'modal': false,
		    'position': ['right - 20', 'top + 20'],
		    'auto_close': 2500
		});
		$('#templetfieldtable').datagrid('reload');
	});
	
}
/*
 * 将输入中文的拼音首字母大写赋予字段标识
 */
function pinyin() {
	$("#englishname").val(query($("#chinesename").val()));
}


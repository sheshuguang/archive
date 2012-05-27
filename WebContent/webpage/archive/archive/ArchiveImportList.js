// 动态字段添加时，附带条件。例如默认值
var fieldsDefaultValue = [];

// 声明列的数组
var columns = [];
var columns_fields = [];
// 声明数据视图
var dataView;
var data = [];
var grid;
// 创建grid配置对象
var options = {
	editable : true,
	enableAddRow : true,
	enableCellNavigation : true,
	autoEdit : false,
	enableColumnReorder : true,
	topPanelHeight : 25
};

// 创建字段验证
function requiredFieldValidator(value) {
	if (value == null || value == undefined || !value.length) {
		return {
			valid : false,
			msg : "请填写内容，不能为空。"
		};
	} else {
		return {
			valid : true,
			msg : null
		};
	}
}

$(function() {
	var par = "treeid=" + selectTreeid + "&tableType=01&importType=1";
	$.ajax({
		async : false,
		url : "getField.action?" + par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				columns_fields = fields;
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
	
	$("#grid-header").html(selectTreeName + '_案卷导入');
	
	// 创建checkbox列
	var checkboxSelector = new Slick.CheckboxSelectColumn({
		cssClass : "slick-cell-checkboxsel"
	});
	// 加入checkbox列
	columns.push(checkboxSelector.getColumnDefinition());
	// 加入其他列
	for ( var i = 0; i < columns_fields.length; i++) {
		columns.push(columns_fields[i]);
	}

	// 生成数据
	// var data = [];
//	for ( var i = 0; i < 500; i++) {
//		data[i] = {
//			id : i + 1,
//			ROWNUM : i + 1,
//			AJH : "ADSF"
//
//		};
//	}
	data = [{id:'1',ROWNUM:'1',AJH:'ADsdf'},{id:'2',ROWNUM:'2',AJH:'ADsdf'}];

	// 创建dataview
	dataView = new Slick.Data.DataView({
		inlineFilters : true
	});
	
	// 创建grid
	grid = new Slick.Grid("#archiveImportDiv", dataView, columns, options);

//	grid.onValidationError.subscribe(handleValidationError);
	// 设置grid的选择模式。行选择
	// grid.setSelectionModel(new Slick.RowSelectionModel());
	grid.setSelectionModel(new Slick.RowSelectionModel({
		selectActiveRow : false
	}));
	// 设置分页控件
	var pager = new Slick.Controls.Pager(dataView, grid, $("#pager"));
	// 注册grid的checkbox功能插件
	grid.registerPlugin(checkboxSelector);
	// 注册grid的自动提示插件。只在字段内容过长时出现省略号时提示
	grid.registerPlugin(new Slick.AutoTooltips());
	
	dataView.onRowCountChanged.subscribe(function(e, args) {
		grid.updateRowCount();
		grid.render();
	});

	dataView.onRowsChanged.subscribe(function(e, args) {
		grid.invalidateRows(args.rows);
		grid.render();
	});
	
	dataView.beginUpdate();
	dataView.setItems(data);
	dataView.endUpdate();
	
	//生成toolbar
	$('#toolbar').toolbar({
		items:[{
			id:"test",
			iconCls:"icon-page-add",
			disabled:false,
			text:"测试",
			handler:function(){
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
		},"-",{
			text:"测试1",
			iconCls:"icon-page-delete",
			handler:function(){
				$("#facebox").overlay().load();
			}
		}]
	});
	
	$("#facebox").overlay({
	    // custom top position
	    top: 100,
	    // some mask tweaks suitable for facebox-looking dialogs
	    mask: {
	    // you might also consider a "transparent" color for the mask
	    color: '#fff',
	    // load mask a little faster
	    loadSpeed: 200,
	    // very transparent
	    opacity: 0.5
	    },
	    // disable this for modal dialog-type of overlays
	    closeOnClick: true,
	    // load it immediately after the construction
	    load: false
	});
	
});

function importArchive() {
	// 判断上传文件类型
	var a = selectfile_Validator($('#selectfile').val());
	if (a) {
//		$('#selectfileWindow').window('close');
		$('#importArchiveForm').attr('action',
				'upload.action?treeid=' + selectTreeid + '&tableType=01');
		$('#importArchiveForm').submit();
	}
}

function showCallback(backType, jsonStr) {

	if (backType == "failure") {
		alert(jsonStr);
	} else {
		//$('#archiveimporttable').datagrid('loading');
		var json = JSON.parse(jsonStr);
		alert(jsonStr);
		dataView.beginUpdate();
		dataView.setItems(jsonStr);
		dataView.endUpdate();
		
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

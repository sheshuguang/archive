
us.archive.GridConfig = function() {
	// 声明列的数组
	this.columns = [];
	this.columns_fields = [];
	// 声明数据视图
	this.dataView;
	this.rows = [];
	this.grid;
	this.fieldsDefaultValue = "asd";
	// 创建grid配置对象
	this.options = {
		editable : true,
		enableAddRow : true,
		enableCellNavigation : true,
		autoEdit : false,
		enableColumnReorder : true,
		topPanelHeight : 25
	};
}

//
//
//(function ($) {
//
//	GridConfig = function () {
//		// 声明列的数组
//		this.columns = [];
//		this.columns_fields = [];
//		// 声明数据视图
//		this.dataView;
//		this.rows = [];
//		this.grid;
//		this.fieldsDefaultValue = "asd";
//		// 创建grid配置对象
//		this.options = {
//			editable : true,
//			enableAddRow : true,
//			enableCellNavigation : true,
//			autoEdit : false,
//			enableColumnReorder : true,
//			topPanelHeight : 25
//		};
//		
//    };
//
//})(jQuery);

//function Common() {
//	// 声明列的数组
//	this.columns = [];
//	this.columns_fields = [];
//	// 声明数据视图
//	this.dataView;
//	this.rows = [];
//	this.grid;
//	this.fieldsDefaultValue = "asd";
//	// 创建grid配置对象
//	this.options = {
//		editable : true,
//		enableAddRow : true,
//		enableCellNavigation : true,
//		autoEdit : false,
//		enableColumnReorder : true,
//		topPanelHeight : 25
//	};
//	
//}
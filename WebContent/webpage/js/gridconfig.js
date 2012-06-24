

(function ($) {

	GridConfig = function () {
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
		
		 //this.testFun方法，加上了this，就是公有方法了，外部可以访问。
//        this.requiredFieldValidator = function (value) {    
//        	if (value == null || value == undefined || !value.length) {
//        		return {
//        			valid : false,
//        			msg : "请填写内容，不能为空。"
//        		};
//        	} else {
//        		return {
//        			valid : true,
//        			msg : null
//        		};
//        	}
//        };
    };

})(jQuery);

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
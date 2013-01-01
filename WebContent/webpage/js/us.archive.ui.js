/*
 *	命名空间	：us.archive
 *	描述		：档案管理通用js
 *	日期		：2012-07-08
 *			：wangf
*/

//判断us命名空间是否存在
if (us == null || us == undefined){
	var us = {};
}
if (us.archive == null || us.archive == undefined ) {
	us.archive = {};
}
if (us.archive.ui == null || us.archive.ui == undefined ) {
	us.archive.ui = {};
}

//grid
us.archive.ui.Gridconfig = function(a) {
	// 声明列的数组
	this.columns = [];
	this.columns_fields = [];
	// 声明数据视图
	this.dataView;
	this.rows = [];
	this.grid;
	this.fieldsDefaultValue = "";
//	this.sortcol = "title";
//	this.sortdir = 1;
	//过滤条件
	this.searchString = "";
	// 创建grid配置对象
	this.options = {
		editable : true,
		enableAddRow : true,
		enableCellNavigation : true,
		autoEdit : false,
		enableColumnReorder : true,
		topPanelHeight : 32
	};
}
//public function
us.archive.ui.Gridconfig.prototype.comparer = function(a, b) {
	var x = a[this.sortcol], y = b[this.sortcol];
	return (x == y ? 0 : (x > y ? 1 : -1));
}





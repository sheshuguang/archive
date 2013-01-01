/*
 *	命名空间	：us.archive.search
 *	描述		：档案查询管理通用js
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
//查询
us.archive.Search = function() {
	//当前帐户能查询的档案节点范围,其实就是档案树节点
	this.treeList = [];
	//当前查询关键字
	this.searchTxt = "";
	//当前选中查询的节点id
	this.treeid = "";
	//当前查询的中文表名-节点路径名
	this.treename = "";
	//当前查询的表名
	this.tablename = "";
	//当前查询的模板类型
	this.templettype= "";
	//当前查询的table类型。01表示当前显示的是案卷。02表示文件级
	this.tabletype = "";
	//当前字段
	this.fields = [];
	//当前查询出的数据
	this.data = [];
	//总行数
	this.rowCount = 0;
	//总页数
	this.pages = 0;
	//当前页
	this.currentPage = 1;
	//每页行数
	this.pageSize = 20;
	
};


////私有函数
//us.archive.Archive.prototype._aaa = function() {
//	
//}
////public函数
//us.archive.Archive.prototype.aaa = function() {
//	
//}




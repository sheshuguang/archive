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
//archive 
us.archive.Archive = function() {
	this.selectTreeName  = "";
	this.selectTreeid = "";
	// 选择的案卷id
	this.selectAid = "";
	this.selectAJH = "";
	// 关于电子文件，选择的行id
	this.selectRowid = "";
	this.selectTableid = "";
	this.sortcol = "title";
	this.sortdir = 1;
	this.searchString = "";
	this.clName = "";
	this.isAllWj = false;
	//点击导入按钮时，导入页面用来判断当前是案卷还是文件的导入。
	this.tableType = "";
}
////私有函数
//us.archive.Archive.prototype._aaa = function() {
//	
//}
////public函数
//us.archive.Archive.prototype.aaa = function() {
//	
//}


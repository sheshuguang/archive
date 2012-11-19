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
	this.templetList = [];
	
};


////私有函数
//us.archive.Archive.prototype._aaa = function() {
//	
//}
////public函数
//us.archive.Archive.prototype.aaa = function() {
//	
//}




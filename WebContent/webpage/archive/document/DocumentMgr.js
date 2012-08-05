jQuery(function($){
    var dataView = new Slick.Data.DataView({
        inlineFilters : true,
        idField:"docid"

    });
    var grid;
    var _data =[];
    var columns = [
        {id: "docid", name: "文件id", field: "docid" },
        {id: "docserverid", name: "服务器id", field: "docserverid" },
        {id: "docoldname", name: "原文件名", field: "docoldname" },
        {id: "docnewname", name: "现文件名", field: "docnewname" },
        {id: "doctype", name: "文件类型", field: "doctype" },
        {id: "doclength", name: "文件大小", field: "doclength" },
        {id: "docpath", name: "路径", field: "docpath" },
        {id: "creater", name: "上传者", field: "creater" },
        {id: "createtime", name: "上传时间", field: "createtime" },
        {id: "fileid", name: "fileid", field: "fileid" },
        {id: "tableid", name: "tableid", field: "tableid" }
    ];

    $('#toolbar_doc').toolbar({
        items:[{
            id:"insert",
            iconCls:"icon-page-add",
            disabled:false,
            text:"上传",
            handler:function(){
                $.window({
                    showModal	: true,
                    modalOpacity: 0.5,
                    title		: "选择文件",
                    url         : "./DocUploader.html",
                    width		: 600,
                    height		: 400,
                    showFooter	: false,
                    showRoundCorner: true,
                    minimizable	: false,
                    maximizable	: false,
                    onClose	: function(wnd) {
                        location.reload();
                    }
                });
            }
        },{
            id:"update",
            iconCls:"icon-page-delete",
            disabled:false,
            text:"删除",
            handler:function(){ }
        },{
            text:"刷新",
            iconCls:"icon-page-copy",
            handler:function(){
                location.reload();
            }
        }
        ]});
   $.ajax({
        async : false,
        url : "listDoc.action?r="+Math.random(),
        type : 'post',
        dataType : 'script',
        success : function(data) {
            if (data != "error") {
                _data=eval(data);

            } else {
                alert("获取服务器数据错误！");
            }
        }
    });
    grid= new Slick.Grid("#docdiv",  dataView, columns, {
        topPanelHeight : 25
    });
    grid.setSelectionModel(new Slick.RowSelectionModel());
    // 设置分页控件
    var pager_doc = new Slick.Controls.Pager( dataView, grid, $("#pager_doc"));
    dataView.onRowCountChanged.subscribe(function(e, args) {
        grid.updateRowCount();
        grid.render();
    });
    dataView.onRowsChanged.subscribe(function(e, args) {
        grid.invalidateRows(args.rows);
        grid.render();
    });
    dataView.beginUpdate();
    dataView.setItems(_data);
    dataView.endUpdate();

})
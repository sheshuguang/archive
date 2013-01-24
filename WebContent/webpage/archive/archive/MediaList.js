var mediaCommon = {
    selectMediaid: '',
    field: {},
    data: {},
    readField: function () {
        //同步读取当前节点的字段
        $.ajax({
            async: false,
            url: "getFieldList.action",
            type: 'post',
            dataType: 'json',
            data: {treeid: archiveCommon.selectTreeid, tableType: '01'},
            success: function (data) {
                if (data != "error") {
                    mediaCommon.field = eval(data);
                } else {
                    us.openalert('读取数据时出错，请尝试重新操作或与管理员联系! ', '系统提示', 'alertbody alert_Information');
                }
            }
        });
    },
    readData: function () {
        //同步读取当前节点的原始数据
        $.ajax({
            async: false,
            url: "listForMediaArchive.action",
            type: 'post',
            dataType: 'json',
            data: {treeid: archiveCommon.selectTreeid, tableType: '01'},
            success: function (data) {
                if (data != "error") {
                    mediaCommon.data = eval(data);
                } else {
                    us.openalert('读取数据时出错，请尝试重新操作或与管理员联系! ', '系统提示', 'alertbody alert_Information');
                }
            }
        });
    },
    showAddDialog: function () {
        //处理动态字段，创建dialog里的添加html
        if (this.field.length > 0) {
            var html = "";
            for (var i = 0; i < this.field.length; i++) {
                if (this.field[i].isgridshow == 1) {
                    html += "<div class=\"control-group\" id=\"" + this.field[i].englishname + "div\">";
                    html += "<label class=\"control-label\" for=\"" + this.field[i].englishname + "\">" + this.field[i].chinesename + "</label>";
                    html += "<div class=\"controls\">";
                    //弄有代码的
                    if (this.field[i].iscode == 1) {
                        var code;
                        $.ajax({
                            async: false,
                            url: "getFieldCode.action",
                            type: 'post',
                            dataType: 'json',
                            data: {templetfieldid: this.field[i].templetfieldid},
                            success: function (data) {
                                if (data != "error") {
                                    code = eval(data);
                                } else {
                                }
                            }
                        });
                        html += "<select id='" + this.field[i].englishname + "'>";
                        for (var j = 0; j < code.length; j++) {
                            html += "<option value='" + code[j].columnname + "'>" + code[j].columndata + "</option>";
                        }
                        html += "</select>";
                    }
                    else {
                        if (this.field[i].fieldtype == "INT") {
                            html += "<input type=\"text\" id=\"" + this.field[i].englishname + "\" value=\"0\">";
                        }
                        else {
                            html += "<input type=\"text\" id=\"" + this.field[i].englishname + "\" >";
                        }

                    }

                    html += "</div>";
                    html += "</div>";
                }
            }

            $('#addMediaDialog').html(html);
        }
    },
    picHandle: function () {
        var mw = 300;//设置页面中图片固定宽
        var mh = 200;//设置页面中图片固定高
        var pic = jQuery(".product_image");//获取元素对象
        pic.wrap('<div id="box" class="pp_box" style="cursor:pointer;background-color:#DCDCDC;width:' + mw + 'px;height:' + mh + 'px;"></div>');
        for (var i = 0; i < pic.length; i++) {
            var image = new Image()
            image.src = pic.eq(i).attr("src");
            var pw = image.width;
            var ph = image.height;
            if (pw / ph > mw / mh) {
                pic.eq(i).css("width", mw + "px");
                pic.eq(i).css("height", (mw / pw * ph) + "px");
                pic.eq(i).css("padding-top", parseInt((mh - parseInt(mw / pw * ph)) / 2) + "px")
            } else {
                pic.eq(i).css("height", mh + "px");
                pic.eq(i).css("width", (mh / ph * pw) + "px");
                pic.eq(i).css("padding-left", parseInt((mw - parseInt(mh / ph * pw)) / 2) + "px")
            }
        }
    },
    imageHandle: function (obj,width, height) {
        var img=new Image();
        img.src=obj.src;
        var scale=Math.max(width/img.width, height/img.height);
        var newWidth=img.width*scale;
        var newHeight=img.height*scale;
        var div=obj.parentNode;
        obj.width=newWidth;
        obj.height=newHeight;
        div.style.width=width+"px";
        div.style.height=height+"px";
        div.style.overflow="hidden";
        obj.style.marginLeft=(width-newWidth)/2+"px";
        obj.style.marginTop=(height-newHeight)/2+"px";
        obj.style.maxWidth="300%";

    },
    showData: function () {
        //    显示多媒体数据
        var html = "";
        for (var i = 0; i < mediaCommon.data.length; i++) {
            var row = mediaCommon.data[i];
            var n = (i) % 3;
            if (n == 0) {
                html += "<div class=\"row-fluid\">";
                html += "<ul class=\"thumbnails\">";
            }
            html += "<li class=\"span4\">";
            html += "<div class=\"thumbnail\" >";
            var slt = "";
            if (mediaCommon.data[i].SLT == "") {
                slt = "../../media/no_photo_135.png";
            }
            else {
                slt = "../../media/" + mediaCommon.data[i].SLT;
            }
            html += "<a class=\"thumbnail\" href=\"#\" onclick=\"showMediaWjTab('" + mediaCommon.data[i].ID + "')\">";;
            html += "<div><img class='product_image'  onload=\"mediaCommon.imageHandle(this,300,200)\" src='" + slt + "'></div>";
//            html += "<div><img class='product_image' src='" + slt + "'></div>";
            html += "</a>";

            html += "<div class=\"caption\">";
            var ajh = mediaCommon.data[i].AJH;
            var tm = mediaCommon.data[i].TM;
            if (mediaCommon.data[i].AJH.length > 20) {
                ajh = mediaCommon.data[i].AJH.substring(0, 20) + "....";
            }
            if (mediaCommon.data[i].TM.length > 20) {
                tm = mediaCommon.data[i].TM.substring(0, 20) + "....";
            }
            html += "<h6 ><a rel=\"tooltip\" href=\"#\" title="+mediaCommon.data[i].AJH+">" + ajh + "</a></h6>";
            html += "<p rel=\"tooltip\" title=" + mediaCommon.data[i].TM + " class=\"pp\">" + tm + "</p>";
            html += "<p><a class=\"btn btn-primary btn-small\" href=\"javascript:;\">查看</a> <a class=\"btn btn-small\" href=\"javascript:;\">编辑</a> <a class=\"btn btn-small\" href=\"javascript:;\">删除</a></p>";
            html += "</div>";
            html += "</div>";
            html += "</li>";
            if (n == 2 || n + 1 == mediaCommon.data.length) {
                html += "</ul>";
                html += "</div>";
            }
        }
        $('#meidadiv').html(html);
//        this.picHandle();
    },
    initMedia: function () {
        //初始化添加多媒体相册dialog
        $("#addMedia").dialog({
            autoOpen: false,
            height: 470,
            width: 560,
            modal: true,
            buttons: {
                "保存": function () {
                    var items = [];
                    var item = {};
                    item.id = UUID.prototype.createUUID();
                    item.isdoc = 0;
                    item.treeid = archiveCommon.selectTreeid;
                    item.slt = '';
                    for (var i = 0; i < mediaCommon.field.length; i++) {
                        var field = mediaCommon.field[i];
                        if (field.isgridshow == 1) {
                            $('#' + field.englishname + "div").removeClass("error");
                            if (null != $('#' + field.englishname).val() || "" != $('#' + field.englishname).val()) {
                                if (field.fieldtype == "INT") {
                                    //判断值是否正常
                                    var isnum = isNaN($('#' + field.englishname).val());
                                    if (isnum) {
                                        us.openalert('请填写正确的数字类型! ', '系统提示', 'alertbody alert_Information');
                                        $('#' + field.englishname).focus();
                                        $('#' + field.englishname + "div").addClass("error");
                                        return;
                                    }
                                }
                                item[field.englishname.toLowerCase()] = $('#' + field.englishname).val();
                            }
                            else {
                                if (field.fieldtype == "INT") {
                                    item[field.englishname.toLowerCase()] = '0';
                                }
                                else {
                                    item[field.englishname.toLowerCase()] = '';
                                }

                            }
                        }
                    }
                    items.push(item);
                    $.ajax({
                        async: false,
                        url: "saveImportArchive.action",
                        type: 'post',
                        dataType: 'text',
                        data: {importData: JSON.stringify(items), tableType: '01'},
                        success: function (data) {
                            us.openalert(data, '系统提示', 'alertbody alert_Information');
                        }
                    });

                },
                "关闭": function () {
                    $(this).dialog("close");
                }
            },
            close: function () {

            }
        });
    }
};
/**
 * 新建 相册 (案卷级)
 */
function addMedia() {
    mediaCommon.showAddDialog();
    $("#addMedia").dialog("open");
}

function showMediaWjTab(id) {
    mediaCommon.selectMediaid = id;
    url = "dispatch.action?page=/webpage/archive/archive/MediaWjList.html";
    us.addtab($('#mediawj'), '多媒体--文件管理', 'ajax', url);
}

$(function () {
    setGridResize();
    $('#grid_header_media').html(archiveCommon.selectTreeName + "_多媒体管理");

    mediaCommon.initMedia();
    mediaCommon.readField();
    mediaCommon.readData();
    mediaCommon.showData();

})
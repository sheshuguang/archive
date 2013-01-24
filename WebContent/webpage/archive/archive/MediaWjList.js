


var mediaWjCommon = {
    field:{},
    data:{},
    readField:function() {
        //同步读取当前节点的字段
        $.ajax({
            async : false,
            url : "getFieldList.action",
            type : 'post',
            dataType : 'json',
            data:{treeid:archiveCommon.selectTreeid,tableType:'02'},
            success : function(data) {
                if (data != "error") {
                    mediaWjCommon.field = eval(data);
                } else {
                    us.openalert('读取数据时出错，请尝试重新操作或与管理员联系! ','系统提示','alertbody alert_Information');
                }
            }
        });
    },
    readData:function() {
        //同步读取当前节点的原始数据
        $.ajax({
            async : false,
            url : "listForMediaArchive.action",
            type : 'post',
            dataType : 'json',
            data:{treeid:archiveCommon.selectTreeid,tableType:'02'},
            success : function(data) {
                if (data != "error") {
                    mediaWjCommon.data = eval(data);
                } else {
                    us.openalert('读取数据时出错，请尝试重新操作或与管理员联系! ','系统提示','alertbody alert_Information');
                }
            }
        });
    },
    showAddWjDialog:function() {
        //处理动态字段，创建dialog里的添加html
        if (this.field.length > 0) {
            var html = "";
            for (var i=0;i<this.field.length;i++) {
                if (this.field[i].isgridshow == 1) {
                    html += "<div class=\"control-group\" id=\""+this.field[i].englishname+"div\">";
                    html += "<label class=\"control-label\" for=\""+this.field[i].englishname+"\">"+this.field[i].chinesename+"</label>";
                    html += "<div class=\"controls\">";
                    //弄有代码的
                    if (this.field[i].iscode == 1) {
                        var code;
                        $.ajax({
                            async : false,
                            url : "getFieldCode.action",
                            type : 'post',
                            dataType : 'json',
                            data:{templetfieldid:this.field[i].templetfieldid},
                            success : function(data) {
                                if (data != "error") {
                                    code = eval(data);
                                } else {
                                }
                            }
                        });
                        html += "<select id='" + this.field[i].englishname +"'>";
                        for (var j=0;j<code.length;j++) {
                            html += "<option value='"+code[j].columnname+"'>"+code[j].columndata+"</option>";
                        }
                        html += "</select>";
                    }
                    else {
                        if (this.field[i].fieldtype == "INT") {
                            html += "<input type=\"text\" id=\""+this.field[i].englishname+"\" value=\"0\">";
                        }
                        else {
                            html += "<input type=\"text\" id=\""+this.field[i].englishname+"\" >";
                        }

                    }

                    html += "</div>";
                    html += "</div>";
                }
            }
            $('#mediaWjInfoForm').html(html);
            $('.mediainfo').attr("src","../../media/no_photo_135.png");
            $("#mediaWjInfo").dialog("open");
        }
    },
    picHandle:function() {
        var mw = $('.span3').width() - 12;
        var mh = $('.span3').height();
//        var mw=225;//设置页面中图片固定宽
//        var mh=180;//设置页面中图片固定高
        var pic=jQuery(".mediawj_image");//获取元素对象
        pic.wrap('<div id="box" class="pp_box" style="background-color:#DCDCDC;width:'+mw+'px;height:'+mh+'px;"></div>');
        for(var i=0 ;i<pic.length;i++){
            var image=new Image()
            image.src=pic.eq(i).attr("src");
            var pw=image.width;
            var ph=image.height;
            if(pw/ph > mw/mh){
                pic.eq(i).css("width",mw+"px");
                pic.eq(i).css("height",(mw/pw*ph)+"px");
                pic.eq(i).css("padding-top",parseInt((mh-parseInt(mw/pw*ph))/2)+"px")
            }else{
                pic.eq(i).css("height",mh+"px");
                pic.eq(i).css("width",(mh/ph*pw)+"px");
                pic.eq(i).css("padding-left",parseInt((mw-parseInt(mh/ph*pw))/2)+"px")
            }
        }
    },
    imageHandle: function () {
        var width = $('.span3').width() - 12;
        var height = $('.span3').height();
        var pic=jQuery(".mediawj_image");//获取元素对象
        for(var i=0 ;i<pic.length;i++){
            var img = new Image();
            img.src = pic.eq(i).attr("src");
            var scale = Math.max(width / img.width, height / img.height);
            var newWidth = img.width * scale;
            var newHeight = img.height * scale;
            var div = pic[i].parentNode;
            pic[i].width = newWidth;
            pic[i].height = newHeight;
            div.style.width = width + "px";
            div.style.height = height + "px";
            div.style.overflow = "hidden";
            pic[i].style.marginLeft = (width - newWidth) / 2 + "px";
            pic[i].style.marginTop = (height - newHeight) / 2 + "px";
            pic[i].style.maxWidth = "300%";
        }
    },
    showData:function() {
        //    显示多媒体数据
        var html = "";
        for (var i=0;i<mediaWjCommon.data.length;i++) {
            var row = mediaWjCommon.data[i];
            var n = (i)%4;
            if (n == 0) {
                html += "<div class=\"row-fluid\">";
                html += "<ul class=\"thumbnails\">";
            }
            html += "<li class=\"span3\">";
            html += "<a class=\"thumbnail\" href=\"#\">";
            var slt = "";
            if (mediaWjCommon.data[i].SLT == "") {
                slt = "";
            }
            else {
                var slt = "../../media/" + mediaWjCommon.data[i].SLT;
            }

//            html += "<div><img class='mediawj_image' style=\"width: 260px; height: 180px;\" onload=\"mediaWjCommon.imageHandle(this,260,180)\" src=\""+slt+"\"></div>";
            html += "<div><img class='mediawj_image' onclick='mediaWjCommon.showAddWjDialog()' style=\"width: 260px; height: 180px;\"  src=\""+slt+"\"></div>";
            html += "</a>";
            html += "</li>";
            if (n == 3 || n+1 == mediaWjCommon.data.length) {
                html += "</ul>";
                html += "</div>";
            }
        }

        $('#meidawjdiv').html(html);
//        this.imageHandle();
//        this.picHandle();
    },
    initMedia:function() {
        //声明上传控件。#uploadFile，作为公共的资源，在archiveMgr.js里
        $("#uploadFile").dialog({
            autoOpen: false,
            height: 460,
            width: 630,
            modal: true,
            buttons: {
                '关闭': function() {
                    var par = "selectRowid="+ archiveCommon.selectRowid + "&tableid="+archiveCommon.selectTableid;
                    var rowList = [];
                    //同步读取数据
                    $.ajax({
                        async : false,
                        url : "listLinkDoc.action?"+ par,
                        type : 'post',
                        dataType : 'script',
                        success : function(data) {
                            if (data != "error") {
                                rowList = eval(data);
                                $("#doclist").html("");
                                for (var i=0;i<rowList.length;i++) {
                                    $("#doclist").append(getDoclist(rowList[i]));
                                }
                            } else {
                                us.openalert('读取数据时出错，请尝试重新操作或与管理员联系! ','系统提示','alertbody alert_Information');
                            }
                        }
                    });

                    $("#docwindows").dialog('option', 'title', '电子全文列表--(共 ' + rowList.length + "个文件)");
                    $( this ).dialog( "close" );
                }
            },
            close: function() {
                mediaWjCommon.readData();
                mediaWjCommon.showData();
            }
        });
        //初始化多媒体文件信息dialog
        $("#mediaWjInfo").dialog({
            title:'多媒体档案信息',
            autoOpen: false,
            height: 670,
            width: 860,
            modal: true,
            buttons: {
                "保存":function() {
                    var items = [];
                    var item = {};
                    item.id=UUID.prototype.createUUID();
                    item.isdoc = 0;
                    item.treeid = archiveCommon.selectTreeid;
                    item.slt = '';
                    for (var i=0;i<mediaWjCommon.field.length;i++) {
                        var field = mediaWjCommon.field[i];
                        if (field.isgridshow == 1) {
                            $('#' + field.englishname + "div").removeClass("error");
                            if (null != $('#' + field.englishname).val() || "" != $('#' + field.englishname).val()) {
                                if (field.fieldtype == "INT") {
                                    //判断值是否正常
                                    var isnum = isNaN($('#' + field.englishname).val());
                                    if (isnum) {
                                        us.openalert('请填写正确的数字类型! ','系统提示','alertbody alert_Information');
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
                        async : false,
                        url : "saveImportArchive.action",
                        type : 'post',
                        dataType : 'text',
                        data:{importData:JSON.stringify(items),tableType:'01'},
                        success : function(data) {
                            us.openalert(data,'系统提示','alertbody alert_Information');
                        }
                    });

                },
                "关闭": function() {
                    $( this ).dialog( "close" );
                }
            },
            close: function() {

            }
        });
    },
    openUploadMedia:function() {
        $("#uploader").pluploadQueue({
            // General settings
            runtimes : 'flash,html5,html4',
            url : 'uploadMedia.action?parentid=' + mediaCommon.selectMediaid + '&tableType=02&treeid=' + archiveCommon.selectTreeid,
            max_file_size : '200mb',
            //缩略图形式。
//        resize : {width :32, height : 32, quality : 90},
//        unique_names : true,
            chunk_size: '2mb',
            filters : [
                {title : "Image files", extensions : "jpg,gif,png,bmp,tif"},
                {title : "Video files", extensions : "mp4,avi,flv"}
            ],
            // Flash settings
            flash_swf_url : '../../js/plupload/js/plupload.flash.swf'
            // Silverlight settings
//        silverlight_xap_url : '/example/plupload/js/plupload.silverlight.xap'
        });
        $('#formId').submit(function(e) {
            var uploader = $('#uploader').pluploadQueue();
            if (uploader.files.length > 0) {
                // When all files are uploaded submit form
                uploader.bind('StateChanged', function() {
                    if (uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {
                        $('#formId')[0].submit();
                    }
                });
                uploader.start();
            } else {
                us.openalert('请先上传数据文件! ','系统提示','alertbody alert_Information');
            }
            return false;
        });
        $( "#uploadFile" ).dialog( "open");
    },
    openMediaImg:function() {
        //初始化多媒体文件信息dialog
        $("#showMediaImg").dialog({
            title:'多媒体文件浏览',
            autoOpen: false,
            height: 470,
            width: 860,
            modal: true
        });
        $("#selectTreeid").val(archiveCommon.selectTreeid);;
        $('#ifr').attr('src','slideshow/test.html');
        $( "#showMediaImg" ).dialog( "open");
    }
};
/**
 *
 */
function addMediaWj() {
    mediaWjCommon.openUploadMedia();
}

function refreshWj() {
    mediaWjCommon.readData();
    mediaWjCommon.showData();
}

function showMediaWj() {
    mediaWjCommon.openMediaImg();
}

window.onresize = function() {
//    mediaWjCommon.imageHandle();
}

$(function() {
    setGridResize();
    $('#grid_header_mediawj').html(archiveCommon.selectTreeName + "_多媒体文件管理");

    mediaWjCommon.initMedia();
    mediaWjCommon.readField();
    mediaWjCommon.readData();
    mediaWjCommon.showData();

})
jQuery(function($) {
    $("#uploader").pluploadQueue({
        // General settings
        runtimes : 'gears,flash,silverlight,browserplus,html5',
        url : 'docUpload.action',
        max_file_size : '200mb',
        chunk_size : '1mb',
        //unique_names : true,

        // Resize images on clientside if we can
        // resize : {width : 320, height : 240, quality : 90},

        // Specify what files to browse for
        filters : [
            {title : "Image files", extensions : "jpg,gif,png"},
            {title : "rar files", extensions : "rar"},
            {title : "pdf files", extensions : "pdf"},
            {title : "office files", extensions : "doc,docx,ppt,pptx,xls,xlsx"},
            {title : "exe files", extensions : "exe"},
            {title : "Zip files", extensions : "zip,rar,exe"}
        ],

        // Flash settings
        flash_swf_url : '../../js/plupload/js/plupload.flash.swf',

        // Silverlight settings
        silverlight_xap_url : '../../js/plupload/js/plupload.silverlight.xap'
    });

    // Client side form validation
    $('form').submit(function(e) {
        var uploader = $('#uploader').pluploadQueue();

        // Files in queue upload them first
        if (uploader.files.length > 0) {
            // When all files are uploaded submit form
            uploader.bind('StateChanged', function() {
                if (uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {
                    $('form')[0].submit();
                }
            });

            uploader.start();
        } else {
            alert('请先上传数据文件.');
        }

        return false;
    });
});
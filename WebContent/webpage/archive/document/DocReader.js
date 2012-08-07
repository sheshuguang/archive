jQuery(function($) {
    var swfVersionStr = "9.0.124";
    var xiSwfUrlStr = "${expressInstallSwf}";
    var flashvars = {
        SwfFile : escape("docRead.action?docId="+us.request("docId")),
        PrintEnabled:false
    };
    var params = {}
    params.quality = "high";
    params.bgcolor = "#ffffff";
    params.allowscriptaccess = "sameDomain";
    params.allowfullscreen = "true";
    var attributes = {};
    attributes.id = "FlexPaperViewer";
    attributes.name = "FlexPaperViewer";
    swfobject.embedSWF(
        "../../swf/reader.swf",
        "SWF",
        "800",
        "600",
        swfVersionStr,
        xiSwfUrlStr,
        flashvars,
        params,
        attributes);
    swfobject.createCSS("#SWF", "display:block;text-align:left;");
})

var archiveCommon = new us.archive.Archive();

function setGridResize() {
	//当浏览器高度变化时。修改子对象的高
	$content = $('body').find('#center');
	var tab_pages = $content.find('.tab_pages').outerHeight();
	//修改tab页内grid 外层div的高 $jerTabContent.innerHeight
	$jerTabContent = $('div').find('#jerichotab_contentholder');
	$jerTabContent.height($content.innerHeight() - tab_pages);
	//修改tab页的高
	$jerTabPage = $jerTabContent.children('.curholder');
	$jerTabPage.height($jerTabContent.innerHeight() - 5);
	
	$jerTabGrid = $jerTabContent.find('.gridC');
	$jerTabGrid.height($jerTabPage.innerHeight() - 4);
	
	//修改grid本身div高
	var gridHeader = $jerTabGrid.children('.grid-header').outerHeight();  //标题栏
	var gridMenu = $jerTabGrid.children('.grid-menu').outerHeight();			//菜单栏
	$gridDiv = $jerTabGrid.children('.grid-div');			//griddiv
	var gridPager = $jerTabGrid.children('.grid-pager').outerHeight();			//分页
	
	$gridDiv.height( $jerTabGrid.innerHeight() - gridHeader -  gridMenu - gridPager - 8);
	
	
	//查找
}

var pageLayout;

$(function() {
	pageLayout = $('body').layout({
		applyDefaultStyles: false,
		west: {
//			size		:	"73",
			spacing_open:	4,
			closable	:	true,
			resizable	:	true,
			onresize_end:	function(){
//				resizeContent
//				alert($batchLayoutPan.length);
				if ($batchLayoutPan.length == 0) {
					alert("dd");
				}
				$batchLayoutPan.resizeAll();
				$batchLayoutPan.layout();
			}
		},
		center__onresize:	function (pane, $pane, state, options) {
			//当浏览器高度变化时。修改子对象的高
			// 公共的高度
			var tab_pages = $pane.find('.tab_pages').outerHeight();
			$jerTabContent = $pane.find('#jerichotab_contentholder');
			$jerTabContent.height( state.innerHeight - tab_pages);  //-8
			
			$jerTabContent.find('.curholder').height($jerTabContent.innerHeight() - 5);
			$jerTabContent.find('.holder').height($jerTabContent.innerHeight() - 5);
			
			$jerTabContent.find('.gridC').height($jerTabContent.innerHeight() - 9);
			
			//griddiv的高  -20 gridHeader /   -35   gridMenu  / 20  gridPager  /8 空隙
			$jerTabContent.find('.grid-div').height( $jerTabContent.innerHeight() - 20 -  35 - 20 - 29);
			
			$jerTabContent.find('.slick-viewport').height(  $jerTabContent.innerHeight() - 20 -  35 - 20 - 56);
			
//			//以下设置解决批量挂接单页面多grid的高
//			$batchlayout = $jerTabContent.find('#batchlayout');
//			//如果批量挂接不存在
//			if ($batchlayout.height() == null) {
//				return;
//			}
//			$batchlayout.height($jerTabContent.innerHeight() - 5);
			
			
			//修改tab页的高
//			$jerTabPage = $jerTabContent.children('.curholder');
//			$jerTabPage.height($jerTabContent.innerHeight() - 5);
			
//			$jerTabContent.find('.gridC').height();
			
			//修改tab页内grid 外层div的高 $jerTabContent.innerHeight
			
//			$jerTabGrid = $jerTabPage.find('.gridC');
//			$jerTabGrid.height($jerTabContent.innerHeight() - 7);
			
			//修改grid本身div高
//			var gridHeader = $jerTabGrid.children('.grid-header').outerHeight();  //标题栏
//			var gridMenu = $jerTabGrid.children('.grid-menu').outerHeight();			//菜单栏
////			$gridDiv = $jerTabGrid.children('.grid-div');			//griddiv
//			var gridPager = $jerTabGrid.children('.grid-pager').outerHeight();			//分页
//			
//			$gridDiv.height( $jerTabGrid.innerHeight() - gridHeader -  gridMenu - gridPager - 8);
			
//			$gridDiv.find('.slick-viewport').height($gridDiv.innerHeight() - $gridDiv.find('.slick-header').outerHeight());
//			$gridDiv.find('.slick-header').height('50');
//			var gridHdrH	= $gridDiv.find('.slick-header').outerHeight()
//			,	$gridList	= $gridDiv.find('.slick-viewport') ;
//			alert(gridHdrH);
//			$gridList.height( $gridDiv.innerHeight() - gridHdrH );
			
			
			//当浏览器高度变化时。修改子对象的高
			//设置tab的高
//			var tab_pages = $pane.find('.tab_pages').outerHeight();
//			$jerTabContent = $pane.find('#jerichotab_contentholder');
//			$jerTabContent.height( state.innerHeight - tab_pages);  //-8
//			//修改tab页的高
//			$jerTabPage = $jerTabContent.children('.curholder');
//			$jerTabPage.height($jerTabContent.innerHeight() - 5);
//			
//			//修改tab页内grid 外层div的高 $jerTabContent.innerHeight
//			$jerTabGrid = $jerTabPage.find('.gridC');
//			$jerTabGrid.height($jerTabPage.innerHeight() - 4);
//			
//			//修改grid本身div高
//			var gridHeader = $jerTabGrid.children('.grid-header').outerHeight();  //标题栏
//			var gridMenu = $jerTabGrid.children('.grid-menu').outerHeight();			//菜单栏
//			$gridDiv = $jerTabGrid.children('.grid-div');			//griddiv
//			var gridPager = $jerTabGrid.children('.grid-pager').outerHeight();			//分页
//			
//			$gridDiv.height( $jerTabGrid.innerHeight() - gridHeader -  gridMenu - gridPager - 8);
//			
//			var gridHdrH	= $gridDiv.find('.slick-header').outerHeight()
//			,	$gridList	= $gridDiv.find('.slick-viewport') ;
//			$gridList.height( $gridDiv.innerHeight() - gridHdrH );
		}
	});
	//生成档案tree
	var tree = $("#archivetree").jstree({ 
		//生成的jstree包含哪些插件功能
		//json_data：采用json形式数据
		//crrm：允许增加、改名、移动等操作
		//search：允许检索
		"plugins" : ["themes","json_data","ui","types"],
		//jstree的主题样式，可以更换
		"themes" : {
			"theme" : "default"
		},
		json_data:{
			"ajax":{
	            url:"getTree.action",
	            async:false
	        }
		},
		"types" : {
				"valid_children" : [ "root" ],
				"types" : {
					//设置rel=root的参数
					"root" : {
						//图标
						"icon" : { 
							"image" : "../../images/icons/house.png"
						},
						"valid_children" : [ "folder","default" ],
						"max_depth" : 2,
						"hover_node" : false,
						"select_node" : function () {return false;}

					},
					"default" : {
						"valid_children" : [ "none" ],
						"icon" : { 
							"image" : "../../images/icons/page.png" 
						}
					},
					"folder" : {
						"valid_children" : [ "default", "folder" ],
						"icon" : {
							"image" : "../../images/icons/folder.png"
						}
					}
				}
		},
		"ui" : {
			// 设置初始化选择id为2的节点
//			"initially_select" : [ "0" ]
		},
		// the core plugin - not many options here
		"core" : { 
			//设置默认打开id为1和4的节点
			"initially_open" : [ "0"] 
		}
	})//单击事件
     .bind('click.jstree', function(event) {               
        var eventNodeName = event.target.nodeName;
        if (eventNodeName == 'INS') {
            return;               
        } else if (eventNodeName == 'A') {                   
            var $subject = $(event.target).parent();                   
            if ($subject.find('ul').length > 0) {            
            } else {
            	var id = $(event.target).parents('li').attr('id');
            	var text = $(event.target).text();
            	showTreeList(tree,id,text);
            }               
        }           
    });

	$.fn.initJerichoTab({
        renderTo: '#tab',
        uniqueId: 'archiveTab',
        contentCss: { 'height': $('#center').height()-0 },
        tabs: [{
                title: '操作帮助',
                closeable: false,
//                iconImg: 'images/jerichotab.png',
                data: { dataType: 'formtag', dataLink: '#help' }
            }],
        activeTabIndex: 0,
        loadOnce: false,
        tabWidth:120
    });
	
});

function showTreeList(ob,id,text) {
    archiveCommon.selectTreeName = text;
    archiveCommon.selectTreeid = id;
    var archiveType = "";
    //同步获得当前所选档案类型。
    var par = "treeid=" + archiveCommon.selectTreeid;
    $.ajax({
        async : false,
        url : "getTempletType.action?" + par,
//        url : "listArchive.action?" + par,
        type : 'post',
        dataType : 'text',  //靠，这里的参数有很多种。如果是script。那么后台传来的string类型就没有反应
        success : function(data) {
            archiveType = data;
            if (data != null) {
                archiveType = data;
            } else {
                us.openalert('<span style="color:red">读取数据时出错.</span></br>请关闭浏览器，重新登录尝试或与管理员联系!',
                    '系统提示',
                    'alertbody alert_Information'
                );
            }
        }
    });
    var url = "";
    if (archiveType == "A" || archiveType == "F") {
        url  = "showArchiveList.action?treeid=" + id;
        us.addtab(ob,'档案管理','ajax', url);
    }
    else if (archiveType == "P") {
        url = "dispatch.action?page=/webpage/archive/archive/MediaList.html";
        us.addtab($('#media'),'多媒体管理','ajax', url);
    }
}

/**
 * 打开文件页签
 * @param id		打开的文件所属案卷id
 * @param isAllWj	是否现在该节点下全部文件。
 */
function showWjTab(id,isAllWj) {
	archiveCommon.selectAid = id;
	archiveCommon.isAllWj = isAllWj;
	var url = "showArchiveWjList.action?treeid=" + archiveCommon.selectTreeid;
	us.addtab($("#wjtab"),'文件管理','ajax', url);
//	us.showtab($('#tab'),url, '文件管理', 'icon-page');
}

// 打开案卷导入tab
function showArchiveImportTab(tableType) {
	archiveCommon.tableType = tableType;
	var url = "dispatch.action?page=/webpage/archive/archive/ArchiveImportList.html";
//	us.showtab($('#tab'),url, '案卷导入', 'icon-page');
	us.addtab($("#importtab"),'案卷导入','ajax', url);
}




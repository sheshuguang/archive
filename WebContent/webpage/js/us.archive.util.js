


/*===================================
 *               public
 ===================================*/
// 打开、切换tab页签
us.showtab=function(tabObject,url, tabname, icon) {
	if (tabObject.tabs('exists', tabname)) {
		tabObject.tabs('select', tabname);
		var tab = tabObject.tabs('getSelected');
		tabObject.tabs('update', {
			tab : tab,
			options : {
				href : url
			}
		});
		tab.panel('refresh');
	} else {
		tabObject.tabs('add', {
			title : tabname,
			iconCls : icon,
			href : url,
			closable : true
		});
	}
}


/*==============================================
					  grid
==============================================*/
// grid字段录入时创建字段验证是否为空
us.requiredFieldValidator = function(value) {
	if (value == null || value == undefined || !value.length) {
		return {
			valid : false,
			msg : "请填写内容，不能为空。"
		};
	} else {
		return {
			valid : true,
			msg : null
		};
	}
}

//grid 字段排序
us.comparer = function(a, b) {
  var x = a[archiveCommon.sortcol], y = b[archiveCommon.sortcol];
  return (x == y ? 0 : (x > y ? 1 : -1));
}

//grid 过滤功能
var clName = "ajh";
us.myFilter = function(item, args) {//archiveCommon.clName
	if (args.searchString != "" && item[archiveCommon.clName].indexOf(args.searchString) == -1) {
		return false;
	}
	return true;
}
//grid 过滤功能
us.updateFilter = function(dataView) {
	dataView.setFilterArgs({
		searchString : archiveCommon.searchString
	});
	dataView.refresh();
}

//grid批量修改  
/*
 * grid  		是哪个grid对象
 * dataView  	是哪个view对象
 * isSave		批量修改时，是否保存到数据库。
 */
us.batchUpdate = function(grid,dataView,isSave,w,tabletype) {
	var selectRows = grid.getSelectedRows();
	selectRows.sort(function compare(a, b) {
		return b - a;
	});
	if (w != null) {
		var container = w.getContainer(); // 抓到window裡最外層div物件
		var selectContent = container.find("#selectfield"); // 尋找container底下的指定input element
		var updateContent = container.find("#updatetxt"); 
		
		var selectFieldName = selectContent.val(); 
		var updateTxt = updateContent.val();
	}
	
	var batchUpdateItems = [];
	for ( var i = 0; i < selectRows.length; i++) {
		var item = dataView.getItem(selectRows[i]);
        //将更改的内容更新到item
        for(p in item){
            if (p == selectFieldName) {
                item[p] = updateTxt;
                break;
            }
        }
		batchUpdateItems.push(item);
	}
    //如果是保存到数据库
	if (isSave) {
		var par = "importData=" + JSON.stringify(batchUpdateItems) + "&tableType=" + tabletype;
        $.post("updateImportArchive.action",par,function(data){
                if (data != "保存完毕。") {
                    $.Zebra_Dialog(data, {
                        'type':     'information',
                        'title':    '系统提示',
                        'buttons':  ['确定']
                    });
                }
                else {
                    for ( var i = 0; i < batchUpdateItems.length; i++) {
                        var item = batchUpdateItems[i];
                        dataView.updateItem(item.id,item);
                    }
                }
            }
        );
	}
	else {
		for ( var i = 0; i < batchUpdateItems.length; i++) {
			var item = batchUpdateItems[i];
			dataView.updateItem(item.id,item);
		}
	}
}
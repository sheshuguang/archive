
var accountGridconfig = new us.archive.ui.Gridconfig();

$(function(){
	$("#grid-header-account").html("组[" + authorityCommon.selectNodeText + "]下帐户列表");
	var columns = [
	                {id: "accountcode", name: "帐户名称", field: "accountcode",width:200},
	                {id: "accountstate", name: "帐户状态", field: "accountstate",width:100,
	                	formatter:function(row,column,value) {
		    				if (value == 1){
		    					return "<img alt='1' src='../../images/icons/status_online.png' title='已启用'>";
		    				}
		    				else {
		    					return "<img alt='0' src='../../images/icons/status_offline.png' title='已停用'>";
		    				}
	                	}
	                },
	                {id: "accountmemo", name: "帐户说明", field: "accountmemo",width:400}
	              ];
	//同步读取数据
	$.ajax({
		async : false,
		url : "listAccountNewGrid.action?orgid=" + authorityCommon.selectNodeid,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			if (data != "error") {
				accountGridconfig.rows = rowList;
			} else {
				$.Zebra_Dialog('读取数据时出错，请尝试重新操作或与管理员联系! ', {
	                'type':     'information',
	                'title':    '系统提示',
	                'buttons':  ['确定']
	            });
			}
		}
	});
	accountGridconfig.options.editable = false;
	accountGridconfig.options.enableAddRow = false;
	accountGridconfig.grid = new Slick.Grid("#accountDiv", accountGridconfig.rows, columns, accountGridconfig.options);
	accountGridconfig.grid.setSelectionModel(new Slick.RowSelectionModel());
	
	
	
	//生成toolbar
	$('#toolbar-account').toolbar({
		items:[{
			id:"openAccountAuthority",
			iconCls:"icon-page",
			disabled:false,
			text:"设置帐户权限",
			handler:function(){
				var selectRows = accountGridconfig.grid.getSelectedRows();
				if (selectRows.length != 1) {
					$.Zebra_Dialog('只能选择一个帐户设置权限.<br>请重新选择。 ', {
		                'type':     'information',
		                'title':    '系统提示',
		                'buttons':  ['确定']
		            });
				}
				else {
					var item = accountGridconfig.grid.getDataItem(selectRows[0]);
					authorityCommon.showAccountAuthorityList(item);
				}
			}
		}]
	});
});

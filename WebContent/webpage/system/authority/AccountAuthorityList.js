
var authorityGridconfig = new us.archive.ui.Gridconfig();

$(function(){
	// 设置帐户名称
	$("#accountName").html("<font color=red>" + authorityCommon.accountcode + "</font>");
	
	var par = "accountid=" + authorityCommon.accountid; 
	
	$.ajax({
		async : false,
		url : "getAccountAuthorityInfo.action?" + par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			$("#accountRole").html(role);
		}
	});
	
	authorityCommon.getAccountTree();
	$('#accounttree').tree({   
        checkbox: true,   
        url: 'loadAccountOfTreeData.action?accountid=' + authorityCommon.accountid,
        onSelect: function(node){
        	authorityCommon.accountTreeid = "";
        	$("#account_filescan").attr("disabled", true);
			$("#account_filedown").attr("disabled", true);
			$("#account_fileprint").attr("disabled", true);
			$("#account_filescan").attr("checked", false); 
			$("#account_filedown").attr("checked", false); 
			$("#account_fileprint").attr("checked", false); 
        	if (authorityCommon.accountTree.length > 0) {
        		for (var i=0;i<authorityCommon.accountTree.length;i++) {
        			if (authorityCommon.accountTree[i].treeid == node.id && authorityCommon.accountTree[i].accountid == authorityCommon.accountid) {
        				authorityCommon.accountTreeid = authorityCommon.accountTree[i].accountTreeId;
        				$("#account_filescan").removeAttr("disabled");
        				$("#account_filedown").removeAttr("disabled");
        				$("#account_fileprint").removeAttr("disabled");
        				if (authorityCommon.accountTree[i].filescan == 1) {
        					$("#account_filescan").attr("checked",'true');
        				}
        				if (authorityCommon.accountTree[i].filedown == 1) {
        					$("#account_filedown").attr("checked",'true');
        				}
        				if (authorityCommon.accountTree[i].fileprint == 1) {
        					$("#account_fileprint").attr("checked",'true');
        				}
        				break;
        			}
        		}
        	}
        }
    });
	
	
	
	//生成toolbar
	$('#toolbar_account_tree').toolbar({
		items:[{
			id:"expandAll",
			iconCls:"icon-page-save",
			disabled:false,
			text:"展开",
			handler:function(){
				var node = $('#accounttree').tree('getSelected');
				if (node){
					$('#accounttree').tree('expandAll', node.target);
				} else {
					$('#accounttree').tree('expandAll');
				}
			}
		},{
			id:"collapseAll",
			iconCls:"icon-page-save",
			disabled:false,
			text:"合并",
			handler:function(){
				var node = $('#accounttree').tree('getSelected');
				if (node){
					$('#accounttree').tree('collapseAll', node.target);
				} else {
					$('#accounttree').tree('collapseAll');
				}
			}
		},{
			id:"save",
			iconCls:"icon-page-save",
			disabled:false,
			text:"保存",
			handler:function(){
				//得到选择的节点。
				var nodes = $('#accounttree').tree('getChecked');
				var nodes2 = [];
				 //得到选中的节点，但这个节点下的非全选时的父节点 不是选中状态
				$("#accounttree").find('.tree-checkbox2').each(function(){
					var node = $(this).parent(); 
				    nodes2.push($.extend({}, $.data(node[0], 'tree-node'), {
				    	target: node[0], 
				        checked: node.find('.tree-checkbox').hasClass('tree-checkbox2') 
				    })); 
				 });
				//合并2个选择的节点
				nodes2 = nodes2.concat(nodes);
				
				var str = "[";
				var d = "";
				var temp = "";
				for (var i=0;i<nodes2.length;i++) {
					d += "{'accountTreeId':'','accountid':'";
					d += authorityCommon.accountid;
					d += "','treeid':'";
					d += nodes2[i].id;
					if (authorityCommon.accountTree.length > 0) {
						for (var j=0;j<authorityCommon.accountTree.length;j++) {
							if (authorityCommon.accountTree[j].treeid == nodes2[i].id) {
								temp = "','filescan':" + authorityCommon.accountTree[j].filescan;
								temp += ",'filedown':" + authorityCommon.accountTree[j].filedown;
								temp += ",'fileprint':" + authorityCommon.accountTree[j].fileprint;
								break;
							}
						}
					}
					if (temp == "") {
						temp = "','filescan':0";
						temp += ",'filedown':0";
						temp += ",'fileprint':0";
					}
					d += temp;
					temp = "";
					d += "}";
//					str.push(d);
					str += d + ",";
					d = "";
				}
				if (str.length > 1) {
					str = str.substring(0, str.length -1) + "]";
				}
				else {
					str = "";
				}
				var par = "par=" + str + "&accountid=" + authorityCommon.accountid;
				$.post("setAccountTree.action",par,function(data){
    				if (data == "SUCCESS") {
    					authorityCommon.getAccountTree();
    					$.Zebra_Dialog('更新成功。 ', {
    		                'type':     'information',
    		                'title':    '系统提示',
    		                'buttons':  ['确定']
    		            });
    				}
    				else {
    					$.Zebra_Dialog(data, {
    		                'type':     'information',
    		                'title':    '系统提示',
    		                'buttons':  ['确定']
    		            });
    				}
    			});
			}
		}]
	});
	
	//生成toolbar
	$('#toolbar_account_doc').toolbar({
		items:[{
			id:"save",
			iconCls:"icon-page-save",
			disabled:false,
			text:"保存",
			handler:function(){
				if (authorityCommon.accountTreeid == "") {
					return;
				}
				
				var account_filescan = 0;
				var account_filedown = 0;
				var account_fileprint = 0;
				if($("#account_filescan").attr("checked")== "checked") {
					account_filescan = 1;
				}
				if($("#account_filedown").attr("checked")== "checked") {
					account_filedown = 1;
				}
				if($("#account_fileprint").attr("checked")== "checked") {
					account_fileprint = 1;
				}
				
				//判断选中的树节点，是夹还是叶子。是叶子就只为叶子本身赋予全文权限。如果是夹，询问是否把夹下面所有有权限的都赋予相同的电子文件权限
				var node = $('#accounttree').tree('getSelected');
				var b = $('#accounttree').tree('isLeaf', node.target);
				if (!b) {
					$.Zebra_Dialog('您选择的树节点是【文件夹】，确定要对该【文件夹】赋予相同的电子全文权限吗? <br><span style="color:red">说明：对【文件夹】设置电子全文权限，将赋予该【文件夹】下的所有有权限的节点都赋予相同的电子全文权限。</span>', {
						 'type':     'question',
		                 'title':    '系统提示',
		                 'buttons':  ['确定', '取消'],
		                 'onClose':  function(caption) {
		                     if (caption == '确定') {

		         				var par = "accountTreeid=" + authorityCommon.accountTreeid + "&filescan=" + account_filescan + "&filedown=" + account_filedown + "&fileprint=" + account_fileprint;
		         				$.post("updateAccountTree.action",par,function(data){
		             				if (data == "SUCCESS") {
		             					authorityCommon.getAccountTree();
		             					new $.Zebra_Dialog('更新成功。 ', {
		             		                'type':     'information',
		             		                'title':    '系统提示',
		             		                'buttons':  ['确定']
		             		            });
		             				}
		             				else {
		             					$.Zebra_Dialog(data, {
		             		                'type':     'information',
		             		                'title':    '系统提示',
		             		                'buttons':  ['确定']
		             		            });
		             				}
		         				});
		                     }
		                     else {
		                    	 return;
		                     }
		                 }
			         });
				}
				else {

					var par = "accountTreeid=" + authorityCommon.accountTreeid + "&filescan=" + account_filescan + "&filedown=" + account_filedown + "&fileprint=" + account_fileprint;
					$.post("updateAccountTree.action",par,function(data){
	    				if (data == "SUCCESS") {
	    					authorityCommon.getAccountTree();
	    					$.Zebra_Dialog('更新成功。 ', {
	    		                'type':     'information',
	    		                'title':    '系统提示',
	    		                'buttons':  ['确定']
	    		            });
	    				}
	    				else {
	    					$.Zebra_Dialog(data, {
	    		                'type':     'information',
	    		                'title':    '系统提示',
	    		                'buttons':  ['确定']
	    		            });
	    				}
					});
				}
			}
		}]
	});
});

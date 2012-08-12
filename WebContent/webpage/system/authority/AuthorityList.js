
var authorityGridconfig = new us.archive.ui.Gridconfig();

$(function(){
	// 设置帐户组名称
	$("#selectOrgName").html("<font color=red>" + authorityCommon.selectNodeText + "</font>");
	
	var par = "orgid=" + authorityCommon.selectNodeid; 
	
	$.ajax({
		async : false,
		url : "getOrgAuthorityInfo.action?" + par,
		type : 'post',
		dataType : 'script',
		success : function(data) {
			$("#orgOfAccount").html(accountnum);
			$("#orgOfRole").html(rolename);
		}
	});
	
	authorityCommon.getOrgTree();
	$('#orgoftree').tree({   
        checkbox: true,   
        url: 'loadOrgOfTreeData.action?orgid=' + authorityCommon.selectNodeid,
        onSelect: function(node){
        	authorityCommon.orgTreeid = "";
        	$("#filescan").attr("disabled", true);
			$("#filedown").attr("disabled", true);
			$("#fileprint").attr("disabled", true);
			$("#filescan").attr("checked", false); 
			$("#filedown").attr("checked", false); 
			$("#fileprint").attr("checked", false); 
        	if (authorityCommon.orgOfTree.length > 0) {
        		for (var i=0;i<authorityCommon.orgOfTree.length;i++) {
        			if (authorityCommon.orgOfTree[i].treeid == node.id && authorityCommon.orgOfTree[i].orgid) {
        				authorityCommon.orgTreeid = authorityCommon.orgOfTree[i].orgTreeId;
        				$("#filescan").removeAttr("disabled");
        				$("#filedown").removeAttr("disabled");
        				$("#fileprint").removeAttr("disabled");
        				if (authorityCommon.orgOfTree[i].filescan == 1) {
        					$("#filescan").attr("checked",'true');
        				}
        				if (authorityCommon.orgOfTree[i].filedown == 1) {
        					$("#filedown").attr("checked",'true');
        				}
        				if (authorityCommon.orgOfTree[i].fileprint == 1) {
        					$("#fileprint").attr("checked",'true');
        				}
        				break;
        			}
        		}
        	}
        }
    });
	
	
	
	//生成toolbar
	$('#toolbar_tree').toolbar({
		items:[{
			id:"expandAll",
			iconCls:"icon-page-save",
			disabled:false,
			text:"展开",
			handler:function(){
				var node = $('#orgoftree').tree('getSelected');
				if (node){
					$('#orgoftree').tree('expandAll', node.target);
				} else {
					$('#orgoftree').tree('expandAll');
				}
			}
		},{
			id:"collapseAll",
			iconCls:"icon-page-save",
			disabled:false,
			text:"合并",
			handler:function(){
				var node = $('#orgoftree').tree('getSelected');
				if (node){
					$('#orgoftree').tree('collapseAll', node.target);
				} else {
					$('#orgoftree').tree('collapseAll');
				}
			}
		},{
			id:"save",
			iconCls:"icon-page-save",
			disabled:false,
			text:"保存",
			handler:function(){
				//得到选择的节点。
				var nodes = $('#orgoftree').tree('getChecked');
				var nodes2 = [];
				 //得到选中的节点，但这个节点下的非全选时的父节点 不是选中状态
				$("#orgoftree").find('.tree-checkbox2').each(function(){
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
					d += "{'orgTreeId':'','orgid':'";
					d += authorityCommon.selectNodeid;
					d += "','treeid':'";
					d += nodes2[i].id;
					if (authorityCommon.orgOfTree.length > 0) {
						for (var j=0;j<authorityCommon.orgOfTree.length;j++) {
							if (authorityCommon.orgOfTree[j].treeid == nodes2[i].id) {
								temp = "','filescan':" + authorityCommon.orgOfTree[j].filescan;
								temp += ",'filedown':" + authorityCommon.orgOfTree[j].filedown;
								temp += ",'fileprint':" + authorityCommon.orgOfTree[j].fileprint;
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
				var par = "par=" + str + "&orgid=" + authorityCommon.selectNodeid;
				$.post("setOrgTree.action",par,function(data){
    				if (data == "SUCCESS") {
    					authorityCommon.getOrgTree();
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
	$('#toolbar_doc').toolbar({
		items:[{
			id:"save",
			iconCls:"icon-page-save",
			disabled:false,
			text:"保存",
			handler:function(){
				if (authorityCommon.orgTreeid == "") {
					return;
				}
				
				var filescan = 0;
				var filedown = 0;
				var fileprint = 0;
				if($("#filescan").attr("checked")== "checked") {
					filescan = 1;
				}
				if($("#filedown").attr("checked")== "checked") {
					filedown = 1;
				}
				if($("#fileprint").attr("checked")== "checked") {
					fileprint = 1;
				}
				
				//判断选中的树节点，是夹还是叶子。是叶子就只为叶子本身赋予全文权限。如果是夹，询问是否把夹下面所有有权限的都赋予相同的电子文件权限
				var node = $('#orgoftree').tree('getSelected');
				var b = $('#orgoftree').tree('isLeaf', node.target);
				if (!b) {
					$.Zebra_Dialog('您选择的树节点是【文件夹】，确定要对该【文件夹】赋予相同的电子全文权限吗? <br><span style="color:red">说明：对【文件夹】设置电子全文权限，将赋予该【文件夹】下的所有有权限的节点都赋予相同的电子全文权限。</span>', {
						 'type':     'question',
		                 'title':    '系统提示',
		                 'buttons':  ['确定', '取消'],
		                 'onClose':  function(caption) {
		                     if (caption == '确定') {

		         				var par = "orgTreeid=" + authorityCommon.orgTreeid + "&filescan=" + filescan + "&filedown=" + filedown + "&fileprint=" + fileprint;
		         				$.post("updateOrgTree.action",par,function(data){
		             				if (data == "SUCCESS") {
		             					authorityCommon.getOrgTree();
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

					var par = "orgTreeid=" + authorityCommon.orgTreeid + "&filescan=" + filescan + "&filedown=" + filedown + "&fileprint=" + fileprint;
					$.post("updateOrgTree.action",par,function(data){
	    				if (data == "SUCCESS") {
	    					authorityCommon.getOrgTree();
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

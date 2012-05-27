var oldTreetext = "";
$(function(){
	$('#tree').tree({   
        checkbox: false,   
        url: 'loadTreeData.action?parentid=root',
        onBeforeExpand:function(node,param){
	        $('#tree').tree('options').url = "loadTreeData.action?parentid=" + node.id;                       
	    },
	    onDblClick:function(node){
			editTree(node);
		},
		onAfterEdit:function(node) {
			if (node.text == oldTreetext) {
				return;
			}
			var par = "treeid=" + node.id + "&treename=" + node.text;
			 $.post("updateTree.action",par,function(data){
  					new $.Zebra_Dialog(data, {
  		  				'buttons':  false,
  		   			    'modal': false,
  		   			    'position': ['right - 20', 'top + 20'],
  		   			    'auto_close': 2500
  		            });
  					$('#tree').tree('reload',parentNode.target);
				}
			 );
		}
    });
	$('#p').panel({  
		  width:800,  
		  height:'auto',  
		  title: '档案树管理',
		  fit:true,
		  iconCls:'icon-application_side_list'
	});  
	
});
//新建档案夹
function addTreeFolder() {
	var node = $('#tree').tree('getSelected');
	if (node) {
		if (node.id == 0) {
			new $.Zebra_Dialog('根节点下不能创建档案夹，请在档案类型下创建! ', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
            });
			return;
		}
		if ($('#tree').tree('isLeaf', node.target)) {
			new $.Zebra_Dialog('节点下不能创建档案夹，请在档案类型下创建! ', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
            });
			return;
		}
//		$('#tree').tree('expand', node.target);
		var treeid = UUID.prototype.createUUID ();
		var par = "treename=新建档案夹&parentid=" + node.id + "&treetype=F&treeid="+treeid;
		 $.post("addTree.action",par,function(data){
			 if (data == "success") {
				 	var newNode = [{
				        "id":treeid,
				        "text":"新建档案夹",
				        iconCls:'',
						state:'closed',
						checked:false
				    }];
					$('#tree').tree('append',{
						parent: (node?node.target:null),
						data:newNode
					});
					$('#tree').tree('reload', node.target);
			}
		});
		
	}
	else {
		new $.Zebra_Dialog('请选择在档案类型下创建! ', {
				'buttons':  false,
			    'modal': false,
			    'position': ['right - 20', 'top + 20'],
			    'auto_close': 2500
        });
		return;
	}
}

//新建档案节点
function addTreeW() {
	var node = $('#tree').tree('getSelected');
	if (node) {
		if (node.id == 0) {
			new $.Zebra_Dialog('根节点下不能创建档案节点，请在档案类型下创建! ', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
            });
			return;
		}
		if ($('#tree').tree('isLeaf', node.target)) {
			new $.Zebra_Dialog('节点下不能创建档案节点，请在档案类型下创建! ', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
            });
			return;
		}
		var treeid = UUID.prototype.createUUID ();
		var par = "treename=新建档案节点&parentid=" + node.id + "&treetype=W&treeid="+treeid;
		 $.post("addTree.action",par,function(data){
			 if (data == "success") {
			 	var newNode = [{
			        "id":treeid,
			        "text":"新建档案节点",
			        iconCls:'icon-page',
					state:'open',
					checked:false
			    }];
				$('#tree').tree('append',{
					parent: (node?node.target:null),
					data:newNode
				});
				$('#tree').tree('reload', node.target);
//				$('#tree').tree('expand', node.target);
			}
		 });
		
	}
	else {
		new $.Zebra_Dialog('请选择在档案类型下创建! ', {
				'buttons':  false,
			    'modal': false,
			    'position': ['right - 20', 'top + 20'],
			    'auto_close': 2500
        });
		return;
	}
}

function deleteTree() {
	var node = $('#tree').tree('getSelected');
	if (node) {
		if (node.id == 0) {
			new $.Zebra_Dialog('根节点不能删除! ', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
            });
			return;
		}
		var parentNode = $('#tree').tree('getParent',node.target);
		
		if (parentNode.id == 0) {
			new $.Zebra_Dialog('只能删除档案夹和档案节点。<br><span style="color:red">提示：如果要删除档案库，请到档案库管理下操作！</span>', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
            });
			return;
		}
		var txt = "";
		if ($('#tree').tree('isLeaf', node.target)) {
			txt = "确定要删除选中的档案节点吗? <br><span style='color:red'>注意：删除档案节点，将同时删除档案节点下的所有档案资料（包括数据条目、电子全文），请谨慎操作！</span>";
		}
		else {
			txt = "确定要删除选中的档案夹吗? <br><span style='color:red'>注意：删除档案夹，将同时删除档案夹下的子节点及所有档案资料（包括数据条目、电子全文），请谨慎操作！</span>";
		}
		$.Zebra_Dialog(txt, {
			'type':     'question',
            'title':    '系统提示',
            'buttons':  ['确定', '取消'],
            'onClose':  function(caption) {
                if (caption == '确定') {
               	 var par = "treeid=" + node.id;
					 $.post("delTree.action",par,function(data){
		   					new $.Zebra_Dialog(data, {
		   		  				'buttons':  false,
		   		   			    'modal': false,
		   		   			    'position': ['right - 20', 'top + 20'],
		   		   			    'auto_close': 2500
		   		            });
		   					$('#tree').tree('reload',parentNode.target);
						}
					 );
                }
            }
        });
	}
	else {
		new $.Zebra_Dialog('请选择要删除的档案夹或档案节点! ', {
				'buttons':  false,
			    'modal': false,
			    'position': ['right - 20', 'top + 20'],
			    'auto_close': 2500
        });
		return;
	}
}

function editTree() {
	var node = $('#tree').tree('getSelected');
	if (node) {
		if (node.id == 0) {
			new $.Zebra_Dialog('根节点不能编辑! ', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
            });
			return;
		}
		var parentNode = $('#tree').tree('getParent',node.target);
		if (parentNode.id == 0) {
			new $.Zebra_Dialog('档案类型不能编辑！', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
            });
			return;
		}
		oldTreetext = node.text;
		$('#tree').tree('beginEdit', node.target);
	}
}

function reloadTree(){
	var node = $('#tree').tree('getSelected');
	if (node) {
		var parentNode = $('#tree').tree('getParent',node.target);
		if ($('#tree').tree('isLeaf', node.target)) {
			$('#tree').tree('reload',parentNode.target);
		}
		else {
			$('#tree').tree('reload', node.target);
		}
	}
	else {
		var rootnode = $('#tree').tree('getRoot');
		$('#tree').tree('reload',rootnode.target);
	}
}
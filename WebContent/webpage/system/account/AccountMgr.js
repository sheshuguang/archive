	$(function(){
          $('#orgtree').tree({
              checkbox: false,   
              url: 'orgtreeAction.action?nodeId=0',
              onBeforeExpand:function(node,param){
                  $('#orgtree').tree('options').url = "orgtreeAction.action?nodeId=" + node.id;// change the url                       
              },               
              //onClick:function(node){
              //    showAccountList(node);
              //},
              onSelect:function(node) {
            	  showAccountList(node);
              },
              onLoadSuccess: function(node, data) {
                  //alert(node.id);
            	  //onLoadSuccess(node, data);
                  //if (node.id == 1) {
            	  //	$('#orgtree').tree('expand',node.target);
                  //}
              }
          });
      });
     function showAccountList(node){
         var url = "showListAccount.action?orgid=" + node.id;
         //var content = '<iframe scrolling="auto" frameborder="0" src="'+url+'" style="width:100%;height:100%;"></iframe>';
         if ($('#tab').tabs('exists','帐户管理')){
             $('#tab').tabs('select', '帐户管理');
             var tab = $('#tab').tabs('getSelected');
 			 $('#tab').tabs('update', {
 				tab: tab,
 				options:{
 					href:url
 				}
 			 });
 			tab.panel('refresh');
			 
         } else {
        	 $('#tab').tabs('add',{ 
             	 title:'帐户管理',
             	 iconCls:'icon-user',
            	 //content:content,
            	 href:url,
            	 closable:true 
             });
         }
     }
     function openMoveOrgWin() {
    	 var node = $('#orgtree').tree('getSelected');
   	     if (node){
   	    	if (node.id == 1 || node.id == 2) {
                return;
            }
   	    	var $win;
	   	    $win = $('#moveOrgWindow').window({
		   	    title:' 移动帐户组',
	   	        width: 300,
	   	        height: 450,
	   	        shadow: true,
	   	        modal:true,
	   	        iconCls:'icon_group_go',
	   	        closed:true,
	   	        minimizable:false,
	   	        maximizable:false,
	   	        collapsible:false,
	   	     	onBeforeOpen:function(){
		   	    	$('#orgmovetree').tree({   
		                checkbox: false,   
		                url: 'orgtreeAction.action?nodeId=0',   
		                onBeforeExpand:function(node,param){
		                    $('#orgmovetree').tree('options').url = "orgtreeAction.action?nodeId=" + node.id;// change the url                       
		                }  
		            });
		   	    } 
	   	    });
   	    	$("#moveOrgWindow").window('open');
   	     }
   	     else {
   	    	new $.Zebra_Dialog('请先选择某个帐户组，再移动! ', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
            });
   	    	//$.messager.alert('系统提示','请先选择某个帐户组，再移动!','info');
   	     }
     }
     function saveMoveOrg() {
    	 var node = $('#orgtree').tree('getSelected');
    	 var movenode = $('#orgmovetree').tree('getSelected');

    	 if (!movenode){
    		 new $.Zebra_Dialog('请先选择目标帐户组，再移动! ', {
    			 'buttons':  false,
   			     'modal': false,
   			     'position': ['right - 20', 'top + 20'],
   			     'auto_close': 2000
             });
    		 //$.messager.alert('系统提示','请先选择目标帐户组，再移动!','info');
    		 return;
    	 }

    	 var par = "orgid=" + node.id + "&newParentid=" + movenode.id; 
		 $.post("moveOrg.action",par,function(data){
   				if (data == "success") {
   					new $.Zebra_Dialog('移动成功! ', {
   	   					'buttons':  false,
	   	   			    'modal': false,
	   	   			    'position': ['right - 20', 'top + 20'],
	   	   			    'auto_close': 2500
	   	             });
   					//$.messager.alert('系统提示','保存成功。','info');
   					
   					var pnode = $('#orgtree').tree('getParent', node.target);
   					$('#orgtree').tree('reload', pnode.target);
   					$("#moveOrgWindow").window('close');
   				}
   				else if(data == "error") {
   					new $.Zebra_Dialog('移动的目标帐户组不能是下级单位，请重新选择。 ', {
   	   					'buttons':  false,
	   	   			    'modal': false,
	   	   			    'position': ['right - 20', 'top + 20'],
	   	   			    'auto_close': 2500
	   	             });
   					//$.messager.alert('系统提示','移动的目标帐户组不能是下级单位，请重新选择。','info');
   				}
			}
		 );
    	 
     }
     var isEdit = 0;
     function addOrg() {
    	 var node = $('#orgtree').tree('getSelected');  
   	     if (node){
   	    	var $win;
	   	    $win = $('#addOrgWindow').window({
		   	    title:' 新建帐户组',
	   	        width: 400,
	   	        height: 200,
	   	        //top:($(window).height() - 820) * 0.5,  
	   	        //left:($(window).width() - 450) * 0.5,
	   	        shadow: true,
	   	        modal:true,
	   	        iconCls:'icon_group_add',
	   	        closed:true,
	   	        minimizable:false,
	   	        maximizable:false,
	   	        collapsible:false
	   	    });
	   	 	$('#orgname').val('');
   	    	$("#addOrgWindow").window('open');
   	     }
   	     else {
   	    	new $.Zebra_Dialog('请先选择某个帐户组，再新建! ', {
 				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
             });
   	    	//$.messager.alert('系统提示','请先选择某个帐户组，再新建!','info');
   	     }
     }
     //add a org to database
     function saveAddOrg() {
    	 var orgname = $('#orgname').val();
    	 if (orgname == "") {
    		 new $.Zebra_Dialog('请输入帐户组名称! ', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
              });
    		 //$.messager.alert('系统提示','请输入帐户组名称!','info');
    		 return;
    	 }
         var node = $('#orgtree').tree('getSelected');
         if (node){
             if (isEdit == 0) {
                 var par = "orgname=" + orgname + "&parentid=" + node.id + "&orgid="+UUID.prototype.createUUID (); 
				 $.post("addOrg.action",par,function(data){
		   				if (data == "success") {
		   					new $.Zebra_Dialog('保存成功! ', {
		   		  				'buttons':  false,
		   		   			    'modal': false,
		   		   			    'position': ['right - 20', 'top + 20'],
		   		   			    'auto_close': 2500
		   		              });
		   					//$.messager.alert('系统提示','保存成功。','info');
		   					//$("#addOrgWindow").window('close');
		   					reload();
		   				}
					}
				 );
             }
             else {
                 isEdit = 0;
                 if (orgname == node.text) {
                     return;
                 }
                 else {
                	 var par = "orgname=" + orgname + "&orgid=" + node.id; 
    				 $.post("editOrg.action",par,function(data){
    		   				if (data == "success") {
    		   					new $.Zebra_Dialog('保存成功! ', {
    		   		  				'buttons':  false,
    		   		   			    'modal': false,
    		   		   			    'position': ['right - 20', 'top + 20'],
    		   		   			    'auto_close': 2500
    		   		              });
    		   					//$.messager.alert('系统提示','保存成功。','info');
    		   					$("#addOrgWindow").window('close');
    		   					var pnode = $('#orgtree').tree('getParent', node.target);
    		   					$('#orgtree').tree('reload', pnode.target);
    		   				}
    		   				else {
    		   					new $.Zebra_Dialog('保存失败，请尝试重新操作或与管理员联系! ', {
    		   		  				'buttons':  false,
    		   		   			    'modal': false,
    		   		   			    'position': ['right - 20', 'top + 20'],
    		   		   			    'auto_close': 2500
    		   		              });
    		   					//$.messager.alert('系统提示','保存失败，请尝试重新操作或与管理员联系。','info');
    		   					var pnode = $('#orgtree').tree('getParent', node.target);
    		   					$('#orgtree').tree('reload', pnode.target);
    		   				}
    					}
    				 );
                 }
             }
	 	 } else {
	 		new $.Zebra_Dialog('没有得到帐户组节点编号，请尝试重新操作或与管理员联系! ', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
            });
	 		//$.messager.alert('系统提示','没有得到帐户组节点编号，请尝试重新操作或与管理员联系!','info');
	 	 }
     }
     function editOrg() {
    	 isEdit = 1;
    	 var node = $('#orgtree').tree('getSelected');
   	     if (node){
   	    	var $win;
	   	    $win = $('#addOrgWindow').window({
		   	    title:' 修改帐户组',
	   	        width: 400,
	   	        height: 200,
	   	        //top:($(window).height() - 820) * 0.5,  
	   	        //left:($(window).width() - 450) * 0.5,
	   	        shadow: true,
	   	        modal:true,
	   	        iconCls:'icon_group_edit',
	   	        closed:true,
	   	        minimizable:false,
	   	        maximizable:false,
	   	        collapsible:false
	   	    });
	   	 	$('#orgname').val(node.text);
   	    	$("#addOrgWindow").window('open');
	   	 }
	   	 else {
	   		new $.Zebra_Dialog('请先选择某个帐户组，再修改! ', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
            });
		   	 //$.messager.alert('系统提示','请先选择某个帐户组，再修改!','info');
		 }
     }
     function delOrg() {
         var node = $('#orgtree').tree('getSelected');
		 if (node){
			 if (node.id == 1 || node.id == 2) {
	             return;
	         }
	         
			 var pnode = $('#orgtree').tree('getParent', node.target);
			 $.Zebra_Dialog('确定要删除选中的帐户组吗? <br><span style="color:red">注意：帐户组下的帐户将被移动到【默认组】下。</span>', {
				 'type':     'question',
                 'title':    '系统提示',
                 'buttons':  ['确定', '取消'],
                 'onClose':  function(caption) {
                     if (caption == '确定') {
                    	 var par = "orgid=" + node.id;
    					 $.post("delOrg.action",par,function(data){
    			   				if (data == "success") {
    			   					new $.Zebra_Dialog('删除成功! ', {
    			   		  				'buttons':  false,
    			   		   			    'modal': false,
    			   		   			    'position': ['right - 20', 'top + 20'],
    			   		   			    'auto_close': 2500
    			   		            });
    			   					//$.messager.alert('系统提示','删除成功。','info');
    			   					$('#orgtree').tree('select', pnode.target);
    			   					$('#orgtree').tree('reload', pnode.target);
    			   					//$('#tt').datagrid('reload');
    			   				}
    						}
    					 );
                     }
                 }
	         });
		}
		else {
			new $.Zebra_Dialog('请先选择某个帐户组，再删除! ', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
            });
			//$.messager.alert('系统提示','请先选择某个帐户组，再删除!','info');
		}
     }
     function reload(){
		var node = $('#orgtree').tree('getSelected');
		if (node){
			$('#orgtree').tree('reload', node.target);
		} else {
			//$('#orgtree').tree('reload');
		}
	}
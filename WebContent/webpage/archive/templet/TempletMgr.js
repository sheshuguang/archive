
	$(function(){
          $('#templettree').tree({
              checkbox: false,   
              url: 'loadTempletTreeData.action',
              onSelect:function(node) {
            	  showTempletfieldList(node);
              }
          });
      });
     function showTempletfieldList(node){
    	 var b = $('#templettree').tree('isLeaf', node.target);
    	 if (!b) {
    		 return;
    	 }
         var url = "showListTempletfield.action?tableid=" + node.id;
         $('#selectTempletName').val(node.text);
         if ($('#tab').tabs('exists',"字段管理")){
             $('#tab').tabs('select', "字段管理");
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
             	 title:"字段管理",
             	 iconCls:'icon-application_form',
            	 href:url,
            	 closable:true 
             });
         }
     }
   //打开添加档案库windows  
   var isEdit = 0;
   function addTemplet() {
	   $("#templetWindow").show();
	   var $win;
	   $win = $('#templetWindow').window({
	   	    title:' 档案库',
	        width: 400,
	        height: 200,
	        top:($(window).height() - 200) * 0.5,
	        left:($(window).width() - 400) * 0.5,
	        shadow: true,
	        modal:true,
	        iconCls:'icon_group_add',
	        closed:true,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false
	   });
	   $('#templetname').val('');
	   $("#templetWindow").window('open');
	   var rootnode = $('#templettree').tree('getRoot');
	   var templettype = $('#templettree').tree('getChildren',rootnode.target);
	   var type = $("#templettype");
	   type.empty();
//	   type.append("<option value='0'>空模板</option>");
//	   type.append($("<option>").text('空模板').val("-1"));
	   for (var i=0;i<templettype.length;i++) {
		   var b = $('#templettree').tree('isLeaf', templettype[i].target);
		   if (!b) {
			   //为选择档案模板赋值     //$("<option>").text(templettype[i].text).val(templettype[i].id);
			   var option = "<option value='"+ templettype[i].id +"'>"+ templettype[i].text +"</option>"; 
			   type.append(option);
		   }
	   }
   }
   
   function treeManage() {
	   var url = "dispatch.action?page=/webpage/archive/templet/TreeList.html";
	   if ($('#tab').tabs('exists',"档案树管理")){
		   $('#tab').tabs('select', "档案树管理");
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
			   title:"档案树管理",
			   iconCls:'icon-application_side_list',
			   href:url,
			   closable:true 
		   });
	   }
   }

     function saveTemplet() {
    	 var templetname = $('#templetname').val();
    	 if (templetname == "") {
    		 new $.Zebra_Dialog('请输入档案库名称! ', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
              });
    		 return;
    	 }
         if (isEdit == 0) {
//             var par = "templetname=" + $('#templetname').val() + "&copyTempletid=" + $("#templettype").val() + "&templetid="+UUID.prototype.createUUID ();
             var par = "templetname=" + $('#templetname').val() + "&copyTempletid=" + $("#templettype").val();
			 $.post("addTemplet.action",par,function(data){
   					new $.Zebra_Dialog(data, {
   		  				'buttons':  false,
   		   			    'modal': false,
   		   			    'position': ['right - 20', 'top + 20'],
   		   			    'auto_close': 2500
   		              });
   					//$("#addOrgWindow").window('close');
				}
			 );
			 $('#templettree').tree('reload');
         }
         else {
         }
     }

     function delTemplet() {
         var node = $('#templettree').tree('getSelected');
		 if (node){
			 if (node.id == 0 || node.id == 1 || node.id == 2) {
				 new $.Zebra_Dialog('根节点和基础模板不能删除，请正确选择档案库，再删除! ', {
	  				'buttons':  false,
	   			    'modal': false,
	   			    'position': ['right - 20', 'top + 20'],
	   			    'auto_close': 2500
	            });
	             return;
	         }
			 if ($('#templettree').tree('isLeaf', node.target)) {
				 new $.Zebra_Dialog('请正确选择档案库，再删除! ', {
		  				'buttons':  false,
		   			    'modal': false,
		   			    'position': ['right - 20', 'top + 20'],
		   			    'auto_close': 2500
		            });
		             return;
			 }
	         
			 $.Zebra_Dialog('确定要删除选中的档案库吗? <br><span style="color:red">注意：删除档案库，将同时删除档案库下的所有档案资料（包括数据条目、电子全文），请谨慎操作！</span>', {
				 'type':     'question',
                 'title':    '系统提示',
                 'buttons':  ['确定', '取消'],
                 'onClose':  function(caption) {
                     if (caption == '确定') {
                    	 var par = "templetid=" + node.id;
    					 $.post("delTemplet.action",par,function(data){
			   					new $.Zebra_Dialog(data, {
			   		  				'buttons':  false,
			   		   			    'modal': false,
			   		   			    'position': ['right - 20', 'top + 20'],
			   		   			    'auto_close': 2500
			   		            });
			   					$('#templettree').tree('reload');
    						}
    					 );
                     }
                 }
	         });
		}
		else {
			new $.Zebra_Dialog('请先选择档案库，再删除! ', {
  				'buttons':  false,
   			    'modal': false,
   			    'position': ['right - 20', 'top + 20'],
   			    'auto_close': 2500
            });
		}
     }
     
    function reload(){
    	 $('#templettree').tree('reload');
 	}
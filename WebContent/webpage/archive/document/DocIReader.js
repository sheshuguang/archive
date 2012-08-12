jQuery(function($) {
        try{
            //以下属性必须设置，实始化iWebOffice
            webform.WebOffice.WebUrl="docAction_iwebRead.action?docId="+us.request("docId");
            webform.WebOffice.RecordID=us.request("docId");            //RecordID:本文档记录编号
        //    webform.WebOffice.Template="1344601837047";            //Template:模板编号
        //    webform.WebOffice.FileName="1343996329328.doc";            //FileName:文档名称
            webform.WebOffice.FileType=".doc";            //FileType:文档类型  .doc  .xls  .wps
            webform.WebOffice.UserName="administrator";            //UserName:操作用户名，痕迹保留需要
            webform.WebOffice.EditType="2,0";		        //EditType:编辑类型  方式一、方式二  <参考技术文档>
            //第一位可以为0,1,2,3 其中:0不可编辑;1可以编辑,无痕迹;2可以编辑,有痕迹,不能修订;3可以编辑,有痕迹,能修订；
            //第二位可以为0,1 其中:0不可批注,1可以批注。可以参考iWebOffice2006的EditType属性，详细参考技术白皮书
            webform.WebOffice.MaxFileSize = 4 * 1024;               //最大的文档大小控制，默认是8M，现在设置成4M。
            webform.WebOffice.Language="CH";                        //Language:多语言支持显示选择   CH 简体 TW繁体 EN英文
            //webform.WebOffice.ShowWindow = true;                  //控制显示打开或保存文档的进度窗口，默认不显示
            webform.WebOffice.AllowEmpty=false;                     //控制不允许保存空白内容的文档
            webform.WebOffice.ToolsSpace=0
            //   webform.WebOffice.PenColor="#FF0000";                   //PenColor:默认批注颜色
            //  webform.WebOffice.PenWidth="1";                         //PenWidth:默认批注笔宽
            //  webform.WebOffice.Print="1";                            //Print:默认是否可以打印:1可以打印批注,0不可以打印批注
            //   webform.WebOffice.ShowToolBar="1";                      //ShowToolBar:是否显示工具栏:1显示,0不显示

            //以下为自定义工具栏按钮↓ 参数一:Index按钮编号,参数二:Caption按钮显示内容,参数三:Icon图标名称
            //webform.WebOffice.AppendTools("11","隐藏手写批注",11);  //在OnToolsClick中的 vIndex=11 ,vCaption="隐藏手写批注";
            //   webform.WebOffice.AppendTools("12","显示手写批注",12);  //在OnToolsClick中的 vIndex=12 ,vCaption="显示手写批注";
            //   webform.WebOffice.AppendTools("13","-",0);
            //以上为自定义工具栏按钮↑

            // webform.WebOffice.ShowMenu="1";                         //控制整体菜单显示
            //以下为自定义菜单↓
            /* webform.WebOffice.AppendMenu("1","打开本地文件(&L)");
             webform.WebOffice.AppendMenu("2","保存本地文件(&S)");
             webform.WebOffice.AppendMenu("3","保存远程文件(&U)");
             webform.WebOffice.AppendMenu("4","-");
             webform.WebOffice.AppendMenu("5","签名印章(&Q)");
             webform.WebOffice.AppendMenu("6","验证签章(&Y)");
             webform.WebOffice.AppendMenu("7","-");
             webform.WebOffice.AppendMenu("8","保存版本(&B)");
             webform.WebOffice.AppendMenu("9","打开版本(&D)");
             webform.WebOffice.AppendMenu("10","-");
             webform.WebOffice.AppendMenu("11","保存并退出(&E)");
             webform.WebOffice.AppendMenu("12","-");
             webform.WebOffice.AppendMenu("13","打印文档(&P)");*/
            //以上为自定义菜单↑
            //webform.WebOffice.DisableMenu("宏(&M);选项(&O)...");    //禁止某个（些）菜单项

            //WebSetRibbonUIXML();                                  //控制OFFICE2007的选项卡显示
            webform.WebOffice.WebOpen();                            //打开该文档    交互OfficeServer  调出文档OPTION="LOADFILE"    调出模板OPTION="LOADTEMPLATE"     <参考技术文档>
            webform.WebOffice.FullSize()
            //  webform.WebOffice.ShowType=1;              //文档显示方式  1:表示文字批注  2:表示手写批注  0:表示文档核稿
            //  StatusMsg(webform.WebOffice.Status);                    //状态信息
        }catch(e){
            alert(e.description);                                   //显示出错误信息
        }
})
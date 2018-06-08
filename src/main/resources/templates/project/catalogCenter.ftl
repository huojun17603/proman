<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <#include  "../common/shareJs.ftl">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script type="text/javascript">
        var project_id = "${projectid}";
        var catalogQueryURL = "project/catalog/query";
        var catalogAddURL = "project/catalog/add";
        var catalogEditURL = "project/catalog/edit";

        $(function(){
            $("#datagrid").datagrid({
                url:basePath + catalogQueryURL + "?projectid=${projectid}",
                border:false,
                striped:true,
                fit:true,
                pageSize:50,
                pageList:[50,100],
                idField:'aId',
                loadMsg:'加载中……',
                rownumbers:true,//序号
                pagination:true,//显示底部分页工具栏
                singleSelect:true,//单选
                columns:[[
                    {field:'title',title:'标题',align:"center",width:200},
                    {field:'remark',title:'说明',align:"center",width:600}
                ]],
                toolbar:'#tool',
                onLoadSuccess:function(){
                    $("#datagrid").datagrid('scrollTo',0);
                }
            });
        });
        function doSearch(searchkey){
            $("#datagrid").datagrid("reload",{searchkey:searchkey});
        }
        function openAddPCatalogWindow(){
            $('#pcatalog_add_form').form("clear");
            $("#pcatalog_add_form").form("load",{
                projectid:project_id
            });
            $('#pcatalog_add_window').window('open');
            $('#pcatalog_add_window').window('center');
        }

        function addPCatalog(){
            $.ajax({
                url: basePath+catalogAddURL,
                data: $('#pcatalog_add_form').serialize(),
                success: function(data){
                    if(data.status==0){
                        $('#datagrid').datagrid('reload');
                        closeAddPCatalogWindow();
                        $.messager.alert("提示框","完成操作请求！");
                    }else if(data.status==1){
                        $.messager.alert("错误",data.msg,'warning');
                    }else if(data.status==3){
                        $.messager.alert("警告","无权访问",'warning');
                    }
                }
            });
        }

        function closeAddPCatalogWindow(){
            $('#pcatalog_add_window').window('close');
        }

        function openEditPCatalogWindow(){
            var row = $("#datagrid").datagrid("getSelected");
            if(isEmpty(row)){
                $.messager.alert("提示消息",'请选择一条记录！');
                return ;
            }
            $('#pcatalog_edit_form').form("clear");
            $("#pcatalog_edit_form").form("load",{
                id:row.id,
                title:row.title,
                remark:row.remark
            });
            $('#pcatalog_edit_window').window('open');
            $('#pcatalog_edit_window').window('center');
        }

        function editPCatalog(){
            $.ajax({
                url: basePath+catalogEditURL,
                data: $('#pcatalog_edit_form').serialize(),
                success: function(data){
                    if(data.status==0){
                        $('#datagrid').datagrid('reload');
                        closeEditPCatalogWindow();
                        $.messager.alert("提示框","完成操作请求！");
                    }else if(data.status==1){
                        $.messager.alert("错误",data.msg,'warning');
                    }else if(data.status==3){
                        $.messager.alert("警告","无权访问",'warning');
                    }
                }
            });
        }

        function closeEditPCatalogWindow(){
            $('#pcatalog_edit_window').window('close');
        }

        function copyPCatalog() {
            var row = $('#datagrid').datagrid('getSelected');
            if(isEmpty(row)){
                $.messager.alert("提示框","请选择一条数据记录！");
                return;
            }
            $.messager.prompt('新的标题', '请输入新的标题！', function(title){
                if (title){
                    $.ajax({
                        url:basePath+prototypeCatalogCopyURL,
                        data:{id:row.id,title:title},
                        success: function (data){
                            if(data.status==0){
                                $('#datagrid').datagrid('reload');
                                $.messager.alert("提示框","完成操作请求！");
                            }else if(data.status==1){
                                $.messager.alert("错误",data.msg,'warning');
                            }else if(data.status==3){
                                $.messager.alert("警告","无权访问",'warning');
                            }
                        }
                    });
                }
            });
        }

        function openPrototypeWindow() {
            var row = $('#datagrid').datagrid('getSelected');
            if(isEmpty(row)){
                $.messager.alert("提示框","请选择一条数据记录！");
                return;
            }
            window.location.href = basePath+"project/prototype/center?catalogid="+row.id;
        }

        function openPWindow(){
            window.location.href = basePath+"project/center"+"?id="+project_id;
        }
    </script>
  </head>

  <body style="width:100%;height:100%;">

    <table id="datagrid"></table>

    <div id="tool" style="padding:0px;height:auto;overflow: hidden;">
        <div style="margin-top: 5px;  padding: 8px 35px 8px 14px;margin-bottom: 10px;text-shadow: 0 1px 0 rgba(255,255,255,0.5);background-color: #fcf8e3;border: 1px solid #fbeed5;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;color: #666;">
            <div style="line-height:30px">页面说明：项目</div>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px"  onclick="openAddPCatalogWindow()">新增</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px"  onclick="openEditPCatalogWindow()">修改</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openPWindow()">返回项目</a>
        </div>
        <div class="sgtz_atn">
            <span style="font-weight: bold;">搜索筛选：</span>
            <input id="sk_searchkey" class="easyui-searchbox" data-options="prompt:'搜索关键字（目录标题）',searcher:doSearch" style="width:300px"></input>
        </div>
    </div>


    <div id="pcatalog_add_window" class="easyui-window" title="新增原型目录"
         data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
         style="width:500px;height:250px;">
        <form id="pcatalog_add_form">
            <input name="projectid" type="hidden">
            <ul class="fm_s" style="overflow: inherit;">
                <li class="fm_1l">
                    <label>标题：</label>
                    <input name="title" class="easyui-validatebox textbox vipt" >
                </li>
                <li class="fm_1l">
                    <label>备注：</label>
                    <input name="remark" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
                </li>
                <li class="fm_1l" style="text-align: center;padding-top: 20px">
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addPCatalog()">确认</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeAddPCatalogWindow()">关闭</a>
                </li>
            </ul>
        </form>
    </div>

    <div id="pcatalog_edit_window" class="easyui-window" title="修改原型目录"
         data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
         style="width:500px;height:250px;">
        <form id="pcatalog_edit_form">
            <input name="id" type="hidden">
            <ul class="fm_s" style="overflow: inherit;">
                <li class="fm_1l">
                    <label>标题：</label>
                    <input name="title" class="easyui-validatebox textbox vipt" >
                </li>
                <li class="fm_1l">
                    <label>备注：</label>
                    <input name="remark" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
                </li>
                <li class="fm_1l" style="text-align: center;padding-top: 20px">
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="editPCatalog()">确认</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeEditPCatalogWindow()">关闭</a>
                </li>
            </ul>
        </form>
    </div>

  </body>
</html>

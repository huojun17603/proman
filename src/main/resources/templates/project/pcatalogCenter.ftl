<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <#include  "../common/shareJs.ftl">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script type="text/javascript">
        var project_id = "${projectid}";
        var prototypeCatalogQueryURL = "project/catalog/query";
        var prototypeCatalogCopyURL = "project/catalog/copy";

        $(function(){
            $("#datagrid").datagrid({
                url:basePath + prototypeCatalogQueryURL + "?projectid=${projectid}",
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
            <div style="line-height:30px">页面说明：原型目录页，用于管理项目中的多套原型</div>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px"  onclick="openPrototypeWindow()">详细信息</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openPWindow()">返回项目</a>
        </div>
        <div class="sgtz_atn">
            <span style="font-weight: bold;">搜索筛选：</span>
            <input id="sk_searchkey" class="easyui-searchbox" data-options="prompt:'搜索关键字（目录标题）',searcher:doSearch" style="width:300px"></input>
        </div>
    </div>

  </body>
</html>

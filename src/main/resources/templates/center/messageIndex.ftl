<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <#include  "../common/shareJs.ftl">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<#--<script type="text/javascript" src="/static/js/employee/organizationIndex.js"></script>-->
	<script type="text/javascript">
        var messageMList = "message/mylist";
        $(function(){
            $("#message_datagrid").datagrid({
                url:basePath + messageMList,
                border:false,
                striped:true,
                fit:true,
                pageSize:50,
                pageList:[50,100],
                idField:'id',
                loadMsg:'加载中……',
                rownumbers:true,//序号
                pagination:true,//显示底部分页工具栏
                singleSelect:true,//单选
                columns:[[
                    {field:'createtime',title:'创建时间',align:"center",width:150,formatter:formatterTime},
                    {field:'content',title:'内容',width:1000}
                ]],
                //toolbar:'#tool',
                onLoadSuccess:function(){
                    $("#message_datagrid").datagrid('scrollTo',0);
                }
            });
        })


	</script>
	<style type="text/css">

	</style>
  </head>
  
  <body>
    <table id="message_datagrid" ></table>
  </body>
</html>

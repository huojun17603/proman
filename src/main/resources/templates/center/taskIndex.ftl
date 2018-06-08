<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <#include  "../common/shareJs.ftl">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
      <script type="text/javascript" src="/static/script/easyui/datagrid-detailview.js"></script>
	<script type="text/javascript">
        var taskMList = "project/task/mlist";
        var taskCompURL = "project/task/edit/complete";
        $(function(){
            $("#task_datagrid").datagrid({
                url:basePath + taskMList,
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
                    {field:'status',title:'状态',align:"center",width:100,formatter:function (value,row) {
                        if(value==1)return "待领取";
                        if(value==2)return "待完成";
                        if(value==3)return "完成";
                        if(value==4)return "删除";
                        return "错误";
                    }},
                    {field:'code',title:'任务编码',align:"center",width:100},
                    {field:'title',title:'标题',align:"center",width:300},
                    {field:'projectname',title:'所属项目',align:"center",width:200,formatter:function (value,row,index) {
                        return value + "(" + row.projectversion +")";
                    }},
                    {field:'modularname',title:'所属模块',align:"center",width:150},
                    {field:'createtime',title:'创建时间',align:"center",width:150,formatter:formatterTime},
                    {field:'receivetime',title:'领取时间',align:"center",width:150,formatter:formatterTime},
                    {field:'completetime',title:'完成时间',align:"center",width:150,formatter:formatterTime}

                ]],
                view: detailview,
                detailFormatter:function(index,row){
                    return '<div class="ddv" style="padding:5px 0"></div>';
                },
                onExpandRow: function(index,row){
                    var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
                    ddv.panel({
                        height:80,
                        border:false,
                        cache:false,
                        content:row.content,
                        onLoad:function(){
                            $('#task_datagrid').datagrid('fixDetailRowHeight',index);
                        }
                    });
                    $('#task_datagrid').datagrid('fixDetailRowHeight',index);
                },
                toolbar:'#task_tool',
                onLoadSuccess:function(){
                    $("#task_datagrid").datagrid('scrollTo',0);
                }
            });
        })
        function completeTask() {
            var row = $('#task_datagrid').datagrid('getSelected');
            if(isEmpty(row)){
                $.messager.alert("提示框","请选择一条数据记录！");
                return;
            }
            $.messager.confirm('提示', '确认更改为完成状态吗？', function(r){
                if (r){
                    $.ajax({
                        url:basePath+taskCompURL,
                        data:{id:row.id},
                        success: function (data){
                            if(data.status==0){
                                $('#task_datagrid').datagrid('reload');
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

	</script>
	<style type="text/css">

	</style>
  </head>
  
  <body>
    <table id="task_datagrid"></table>
    <div id="task_tool" style="padding:0px;height:auto;overflow: hidden;">
        <div class="sgtz_atn">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="completeTask()">完成</a>
        </div>
    </div>
  </body>
</html>

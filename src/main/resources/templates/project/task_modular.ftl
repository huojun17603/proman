<script type="text/javascript" src="/static/script/easyui/datagrid-detailview.js"></script>
<script type="text/javascript">
    var task_params = {

    }
    var taskCompURL = "project/task/edit/complete";
    var taskAppointURL = "project/task/edit/appoint";
    var taskReceiveURL = "project/task/edit/receive";
    var taskEditURL = "project/task/edit";
    var taskAddURL = "project/task/add";
    var taskDelURL = "project/task/del";
    var empURL = "admin/employee/query";
    $(function () {
        $("#task_datagrid").datagrid({
            border: false,
            striped: true,
            fit: true,
            idField: 'id',
            loadMsg: '加载中……',
            rownumbers: true,//序号
            singleSelect: true,//单选
            columns: [[
                {
                    field: 'status', title: '状态', align: "center", width: 100, formatter: function (value, row) {
                    if (value == 1) return "待领取";
                    if (value == 2) return "待完成";
                    if (value == 3) return "完成";
                    if (value == 4) return "删除";
                    return "错误";
                }
                },
                {field: 'receivename', title: '领取人', align: "center", width: 100},
                {field: 'code', title: '任务编码', align: "center", width: 100},
                {field: 'title', title: '标题', align: "center", width: 250},
                {field: 'createtime', title: '创建时间', align: "center", width: 150, formatter: formatterTime},
                {field: 'receivetime', title: '领取时间', align: "center", width: 150, formatter: formatterTime},
                {field: 'completetime', title: '完成时间', align: "center", width: 150, formatter: formatterTime}
            ]],
            toolbar: '#task_tool',
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
            }
        });
    });

    function openAddTaskWindow() {
        $('#task_add_window').window('open');
        $('#task_add_window').window('center');
        $('#task_add_form').form("clear");
        $('#task_add_projectid').val(task_params.projectid);
        $('#task_add_modularid').val(task_params.modularid);
    }

    function addTask() {
        $.ajax({
            url:basePath+taskAddURL,
            data:$("#task_add_form").serialize(),
            success: function (data){
                if(data.status==0){
                    $('#task_datagrid').datagrid('reload');
                    closeAddTaskWindow();
                    $.messager.alert("提示框","完成操作请求！");
                }else if(data.status==1){
                    $.messager.alert("错误",data.msg,'warning');
                }else if(data.status==3){
                    $.messager.alert("警告","无权访问",'warning');
                }
            }
        });
    }

    function closeAddTaskWindow(){
        $('#task_add_window').window('close');
    }

    function openEditTaskWindow() {
        var row = $('#task_datagrid').datagrid('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $('#task_edit_window').window('open');
        $('#task_edit_window').window('center');
        $('#task_edit_form').form("clear");
        $('#task_edit_id').val(row.id);
        $('#task_edit_title').val(row.title);
        $('#task_edit_projectid').val(task_params.projectid);
        $('#task_edit_content').textbox("setValue",row.content);
    }

    function editTask() {
        $.ajax({
            url:basePath+taskEditURL,
            data:$("#task_edit_form").serialize(),
            success: function (data){
                if(data.status==0){
                    $('#task_datagrid').datagrid('reload');
                    closeEditTaskWindow();
                    $.messager.alert("提示框","完成操作请求！");
                }else if(data.status==1){
                    $.messager.alert("错误",data.msg,'warning');
                }else if(data.status==3){
                    $.messager.alert("警告","无权访问",'warning');
                }
            }
        });
    }

    function closeEditTaskWindow(){
        $('#task_edit_window').window('close');
    }

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
    function receiveTask() {
        var row = $('#task_datagrid').datagrid('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $.messager.confirm('提示', '确认领取该任务？', function(r){
            if (r){
                $.ajax({
                    url:basePath+taskReceiveURL,
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
    function delTask() {
        var row = $('#task_datagrid').datagrid('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $.messager.confirm('提示', '确认删除该任务？', function(r){
            if (r){
                $.ajax({
                    url:basePath+taskDelURL,
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
    function appointTask(){
        var row = $('#task_datagrid').datagrid('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        var property={
            title:"员工列表",
            dataUrl:empURL,//数据连接
            singleSelect:true,//单选/多选
            searchPrompt:"请输入关键字",//搜索提示
            columns:[[
                {field:'logincode',title:'系统编号',align:"center",width:150,},
                {field:'name',title:'姓名',align:"center",width:350,}
            ]],
            searchCall:function(dg,value){//搜索值回调
                dg.datagrid('reload',{searchkey:value});
            },
            callback:function(data){//选择值回调
                $.each(data, function(index, item){
                    $.ajax({
                        url:basePath+taskAppointURL,
                        data:{id:row.id,userid:item.id,username:item.name},
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
                });
            }
        };
        $.createSelector($("#appoint_selector"),property);
    }
</script>
<div id="appoint_selector"></div>
<div id="task_window" class="easyui-window" title="任务管理"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:1000px;height:630px;">
    <table id="task_datagrid" style="height:565px;"></table>
    <div id="task_tool" style="padding:0px;height:auto;overflow: hidden;">
        <div class="sgtz_atn">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="completeTask()">完成</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="appointTask()">指派</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="receiveTask()">领取</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openAddTaskWindow()">新增</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openEditTaskWindow()">修改</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="delTask()">删除</a>
        </div>
    </div>
</div>


<div id="task_add_window" class="easyui-window" title="新增任务"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:600px;height:400px;">
    <form id="task_add_form">
        <input id="task_add_projectid" name="projectid" type="hidden">
        <input id="task_add_modularid" name="modularid" type="hidden">
        <ul class="fm_s" style="overflow: inherit;">
            <li class="fm_1l">
                <label>编码：</label>
                <input  name="code"  class="easyui-validatebox textbox vipt" >
            </li>
            <li class="fm_1l">
                <label>标题：</label>
                <input name="title"  class="easyui-validatebox textbox vipt" >
            </li>
            <li class="fm_1l">
                <label>内容：</label>
                <input name="content" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:150px">
            </li>
            <li class="fm_1l" style="text-align: center;padding-top: 20px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addTask()">确认</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeAddTaskWindow()">关闭</a>
            </li>
        </ul>
    </form>
</div>

<div id="task_edit_window" class="easyui-window" title="修改"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:600px;height:370px;">
    <form id="task_edit_form">
        <input id="task_edit_id" name="id" type="hidden">
        <input id="task_edit_projectid" name="projectid" type="hidden">
        <ul class="fm_s" style="overflow: inherit;">
            <li class="fm_1l">
                <label>标题：</label>
                <input id="task_edit_title" name="title"  class="easyui-validatebox textbox vipt" >
            </li>
            <li class="fm_1l">
                <label>内容：</label>
                <input id="task_edit_content" name="content" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:150px">
            </li>
            <li class="fm_1l" style="text-align: center;padding-top: 20px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="editTask()">确认</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeEditTaskWindow()">关闭</a>
            </li>
        </ul>
    </form>
</div>

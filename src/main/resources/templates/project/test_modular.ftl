<script type="text/javascript" src="/static/script/easyui/datagrid-detailview.js"></script>
<script type="text/javascript">
    var test_params = {

    }
    var testEditURL = "project/test/edit";
    var testAddURL = "project/test/add";
    var testDelURL = "project/test/del";
    $(function () {
        $("#test_datagrid").datagrid({
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
                        if (value == 1) return "正常";
                        if (value == 2) return "废弃";
                        return "错误";
                    }
                },
                {field: 'code', title: '编码', align: "center", width: 100},
                {field: 'title', title: '标题', align: "center", width: 250},
                {field: 'username', title: '创建人', align: "center", width: 100},
                {field: 'createtime', title: '创建时间', align: "center", width: 150, formatter: formatterTime}
            ]],
            toolbar: '#test_tool'
        });
    });

    function openAddTestWindow() {
        $('#test_add_window').window('open');
        $('#test_add_window').window('center');
        $('#test_add_form').form("clear");
        $('#test_add_projectid').val(test_params.projectid);
        $('#test_add_modularid').val(test_params.modularid);
    }

    function addTest() {
        $.ajax({
            url:basePath+testAddURL,
            data:$("#test_add_form").serialize(),
            success: function (data){
                if(data.status==0){
                    $('#test_datagrid').datagrid('reload');
                    closeAddTestWindow();
                    $.messager.alert("提示框","完成操作请求！");
                }else if(data.status==1){
                    $.messager.alert("错误",data.msg,'warning');
                }else if(data.status==3){
                    $.messager.alert("警告","无权访问",'warning');
                }
            }
        });
    }

    function closeAddTestWindow(){
        $('#test_add_window').window('close');
    }

    function openEditTestWindow() {
        var row = $('#test_datagrid').datagrid('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $('#test_edit_window').window('open');
        $('#test_edit_window').window('center');
        $('#test_edit_form').form("clear");
        $('#test_edit_id').val(row.id);
        $('#test_edit_title').val(row.title);
        $('#test_edit_projectid').val(test_params.projectid);
        $('#test_edit_process').textbox("setValue",row.process);
        $('#test_edit_results').textbox("setValue",row.results);
    }

    function editTest() {
        $.ajax({
            url:basePath+testEditURL,
            data:$("#test_edit_form").serialize(),
            success: function (data){
                if(data.status==0){
                    $('#test_datagrid').datagrid('reload');
                    closeEditTestWindow();
                    $.messager.alert("提示框","完成操作请求！");
                }else if(data.status==1){
                    $.messager.alert("错误",data.msg,'warning');
                }else if(data.status==3){
                    $.messager.alert("警告","无权访问",'warning');
                }
            }
        });
    }

    function closeEditTestWindow(){
        $('#test_edit_window').window('close');
    }


    function delTest() {
        var row = $('#test_datagrid').datagrid('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $.messager.confirm('提示', '确认删除该用例？', function(r){
            if (r){
                $.ajax({
                    url:basePath+testDelURL,
                    data:{id:row.id},
                    success: function (data){
                        if(data.status==0){
                            $('#test_datagrid').datagrid('reload');
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
    function openDetailTestWindow() {
        var row = $('#test_datagrid').datagrid('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $('#test_detail_window').window('open');
        $('#test_detail_window').window('center');
        $('#test_detail_code').val(row.code);
        $('#test_detail_title').val(row.title);
        $('#test_detail_process').textbox("setValue",row.process);
        $('#test_detail_results').textbox("setValue",row.results);
    }
</script>
<div id="test_window" class="easyui-window" title="用例管理"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:1000px;height:630px;">
    <table id="test_datagrid" style="height:565px;"></table>
    <div id="test_tool" style="padding:0px;height:auto;overflow: hidden;">
        <div class="sgtz_atn">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openAddTestWindow()">新增</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openEditTestWindow()">修改</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openDetailTestWindow()">明细</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="delTest()">删除</a>
        </div>
    </div>
</div>


<div id="test_add_window" class="easyui-window" title="新增用例"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:600px;height:400px;">
    <form id="test_add_form">
        <input id="test_add_projectid" name="projectid" type="hidden">
        <input id="test_add_modularid" name="modularid" type="hidden">
        <ul class="fm_s" style="overflow: inherit;">
            <li class="fm_1l">
                <label>编码：</label>
                <input name="code"  class="easyui-validatebox textbox vipt" >
            </li>
            <li class="fm_1l">
                <label>标题：</label>
                <input name="title"  class="easyui-validatebox textbox vipt" >
            </li>
            <li class="fm_1l">
                <label>过程：</label>
                <input name="process" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:120px">
            </li>
            <li class="fm_1l">
                <label>结果：</label>
                <input name="results" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
            </li>
            <li class="fm_1l" style="text-align: center;padding-top: 20px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addTest()">确认</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeAddTestWindow()">关闭</a>
            </li>
        </ul>
    </form>
</div>

<div id="test_edit_window" class="easyui-window" title="用例"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:600px;height:370px;">
    <form id="test_edit_form">
        <input id="test_edit_id" name="id" type="hidden">
        <input id="test_edit_projectid" name="projectid" type="hidden">
        <ul class="fm_s" style="overflow: inherit;">
            <li class="fm_1l">
                <label>标题：</label>
                <input id="test_edit_title" name="title"  class="easyui-validatebox textbox vipt" >
            </li>
            <li class="fm_1l">
                <label>过程：</label>
                <input id="test_edit_process" name="process" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:120px">
            </li>
            <li class="fm_1l">
                <label>结果：</label>
                <input id="test_edit_results" name="results" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
            </li>

            <li class="fm_1l" style="text-align: center;padding-top: 20px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="editTest()">确认</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeEditTestWindow()">关闭</a>
            </li>
        </ul>
    </form>
</div>


<div id="test_detail_window" class="easyui-window" title="用例"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:600px;height:370px;">
    <ul class="fm_s" style="overflow: inherit;">
        <li class="fm_1l">
            <label>编码：</label>
            <input id="test_detail_code" name="code"  class="easyui-validatebox textbox vipt" readonly="readonly">
        </li>
        <li class="fm_1l">
            <label>标题：</label>
            <input id="test_detail_title" name="title"  class="easyui-validatebox textbox vipt"  readonly="readonly" >
        </li>
        <li class="fm_1l">
            <label>过程：</label>
            <input id="test_detail_process" name="process" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:120px">
        </li>
        <li class="fm_1l">
            <label>结果：</label>
            <input id="test_detail_results" name="results" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
        </li>
    </ul>
</div>


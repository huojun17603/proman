<script type="text/javascript">
    var role_data = [{id:"1",text:"项目经理"},{id:"2",text:"原型设计"},
        {id:"3",text:"美工"},{id:"4",text:"JAVA开发"},{id:"5",text:"IOS开发"},{id:"6",text:"安卓开发"},{id:"7",text:"测试"}];
    function initCapitalType(value,row,index){
        if(value==1) {
            return "项目经理"
        } else if(value==2) {
            return "原型设计"
        } else if(value==3) {
            return "美工"
        } else if(value==4) {
            return "JAVA开发"
        } else if(value==5) {
            return "IOS开发"
        } else if(value==6) {
            return "安卓开发"
        } else if(value==7) {
            return "测试"
        } else {
            return "未知"
        }
    }
    var roleAddURL = "project/role/add";
    var roleDelURL = "project/role/del";
    var empURL = "admin/employee/query";
    $(function () {
        $("#role_datagrid").datagrid({
            border:false,
            striped:true,
            fit:true,
            idField:'id',
            loadMsg:'加载中……',
            rownumbers:true,//序号
            singleSelect:true,//单选
            columns:[[
                {field:'role',title:'职位',align:"center",width:350,formatter:initCapitalType},
                {field:'username',title:'人员名称',align:"center",width:350}
            ]],
            toolbar:'#role_tool'
        });
        $("#role_add_role").combobox({
            editable:false,
            data:role_data,
            valueField:'id',
            textField:'text'
        });
    });
    function openAddRoleWindow() {
        $('#role_add_window').window('open');
        $('#role_add_window').window('center');
        $('#role_add_form').form("clear");
        $('#role_add_projectid').val(project_id);
    }
    function closeAddRoleWindow(){
        $('#role_add_window').window('close');
    }

    function openRoleSelector(){
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
                var ids=[];
                var names = [];
                $.each(data, function(index, item){
                    ids.push(item.id);
                    names.push(item.name);
                });
                $('#role_add_userid').val(ids.join(","));
                $('#role_add_username').val(names.join(","));
            }
        };
        $.createSelector($("#role_selector"),property);
    }

    function addRole(){
        $.ajax({
            url:basePath+roleAddURL,
            data:$("#role_add_form").serialize(),
            success: function (data){
                if(data.status==0){
                    $('#role_datagrid').datagrid('reload');
                    closeAddRoleWindow();
                    $.messager.alert("提示框","完成操作请求！");
                }else if(data.status==1){
                    $.messager.alert("错误",data.msg,'warning');
                }else if(data.status==3){
                    $.messager.alert("警告","无权访问",'warning');
                }
            }
        });
    }

    function delRole() {
        var row = $('#role_datagrid').datagrid('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $.messager.confirm('提示', '确认要剔除这个参与者吗？', function(r){
            if (r){
                $.ajax({
                    url:basePath+roleDelURL,
                    data:{id:row.id},
                    success: function (data){
                        if(data.status==0){
                            $('#role_datagrid').datagrid('reload');
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
<div id="role_window" class="easyui-window" title="参与人员管理"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:1000px;height:630px;">
    <table id="role_datagrid" style="height:565px;"></table>
    <div id="role_tool" style="padding:0px;height:auto;overflow: hidden;">
        <div class="sgtz_atn">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openAddRoleWindow()">新增</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="delRole()">删除</a>
        </div>
    </div>
</div>

<div id="role_add_window" class="easyui-window" title="新增人员"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:400px;height:250px;">
    <form id="role_add_form">
        <input id="role_add_projectid" name="projectid" type="hidden">
        <ul class="fm_s" style="overflow: inherit;">
            <li class="fm_1l">
                <label>员工：</label>
                <input id="role_add_username" name="username" class="easyui-validatebox textbox vipt " data-options="required:true" readonly="readonly" onclick="openRoleSelector()">
                <input id="role_add_userid" name="userid" type="hidden">
            </li>
            <li class="fm_1l">
                <label>职位：</label>
                <input id="role_add_role" name="role" class="easyui-combobox vipt" >
            </li>
            <li class="fm_1l" style="text-align: center;padding-top: 20px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addRole()">确认</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeAddRoleWindow()">关闭</a>
            </li>
        </ul>
    </form>
</div>

<div id="role_selector"></div>

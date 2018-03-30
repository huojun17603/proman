<script type="text/javascript">
    var fileAddURL = "project/file/add";
    var fileEditURL = "project/file/edit";
    var fileHisURL = "project/file/hislist";
    var fileDelURL = "project/file/del";
    var fileUploadURL = "file/upload";
    var file_source = {

    }
    $(function () {
        $("#file_datagrid").datagrid({
            border:false,
            striped:true,
            fit:true,
            idField:'id',
            loadMsg:'加载中……',
            rownumbers:true,//序号
            singleSelect:true,//单选
            columns:[[
                {field:'createtime',title:'上传时间',align:"center",width:150,formatter:formatterTime},
                {field:'filename',title:'文件名称',align:"center",width:250},
                {field:'username',title:'上传人员',align:"center",width:150},
                {field:'source',title:'文件来源',align:"center",width:250,formatter:initSource},
                {field:'iterationcauses',title:'替换说明',align:"center",width:300,formatter:initVersionMsg}
            ]],
            toolbar:'#file_tool'
        });
        $("#file_his_datagrid").datagrid({
            border:false,
            striped:true,
            fit:true,
            idField:'id',
            loadMsg:'加载中……',
            rownumbers:true,//序号
            singleSelect:true,//单选
            columns:[[
                {field:'createtime',title:'上传时间',align:"center",width:150,formatter:formatterTime},
                {field:'filename',title:'文件名称',align:"center",width:250},
                {field:'username',title:'上传人员',align:"center",width:150},
                {field:'source',title:'文件来源',align:"center",width:250,formatter:initSource},
                {field:'groupstatus',title:'状态',align:"center",width:100,formatter:initVersion},
                {field:'groupid',title:'说明',align:"center",width:350,formatter:initVersionMsg}
            ]],
            toolbar:'#file_his_tool'
        });
    })
    function downFile(obj) {
        var row = $('#'+obj+'_datagrid').datagrid('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        window.open(row.file+"?downloadName="+row.filename);
    }
    function openOFileWindow(obj){
        var row = $('#'+obj+'_datagrid').datagrid('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        var i = row.file.lastIndexOf('.');
        var suffix = row.file.substring(i);
        if(suffix!=".doc"){
            $.messager.alert("提示框","目前只支持DOC格式的Word文档在线查看功能！");
            return;
        }
        var href = row.file.substring(0,i) + "/office.html";
        window.open(href);
    }
    function openAddFileWindow(){
        $('#file_add_window').window('open');
        $('#file_add_window').window('center');
        $("#file_add_form").form("clear");
        $("#file_add_projectid").val(file_source.projectid);
        $("#file_add_modularid").val(file_source.modularid);
        $("#file_add_source").val(file_source.source);
        $("#file_add_sourceid").val(file_source.sourceid);
        $("#file_add_sourceremark").val(file_source.sourceremark);
    }
    function addFile(){
        $.ajax({
            url:basePath + fileAddURL,
            data:$("#file_add_form").serialize(),
            success: function (data){
                if(data.status==0){
                    $('#file_datagrid').datagrid('reload');
                    closeAddFileWindow();
                    $.messager.alert("提示框","完成操作请求！");
                }else if(data.status==1){
                    $.messager.alert("错误",data.msg,'warning');
                }else if(data.status==3){
                    $.messager.alert("警告","无权访问",'warning');
                }
            }
        });
    }

    function closeAddFileWindow(){
        $('#file_add_window').window('close');
    }

    function openEditFileWindow(){
        var row = $('#file_datagrid').datagrid('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $('#file_edit_window').window('open');
        $('#file_edit_window').window('center');
        $("#file_edit_form").form("clear");
        $("#file_edit_id").val(row.id);
    }

    function editFile(){
        $.ajax({
            url:basePath + fileEditURL,
            data:$("#file_edit_form").serialize(),
            success: function (data){
                if(data.status==0){
                    $('#file_datagrid').datagrid('reload');
                    closeEditFileWindow();
                    $.messager.alert("提示框","完成操作请求！");
                }else if(data.status==1){
                    $.messager.alert("错误",data.msg,'warning');
                }else if(data.status==3){
                    $.messager.alert("警告","无权访问",'warning');
                }
            }
        });
    }

    function closeEditFileWindow(){
        $('#file_edit_window').window('close');
    }

    function delFile(){
        var row = $('#file_datagrid').datagrid('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $.messager.confirm('提示', '确认要把文件标记为废弃吗？', function(r){
            if (r){
                $.messager.prompt('输入', '请输入变更理由！', function(deletecauses){
                    if (deletecauses){
                        $.ajax({
                            url:basePath+fileDelURL,
                            data:{id:row.id,deletecauses:deletecauses},
                            success: function (data){
                                if(data.status==0){
                                    $('#file_datagrid').datagrid('reload');
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
        });
    }

    function openHisFileWindow() {
        var row = $('#file_datagrid').datagrid('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $('#file_his_window').window('open');
        $('#file_his_window').window('center');
        $("#file_his_datagrid").datagrid({
            url:basePath + fileHisURL + "?id=" + row.groupid
        });
    }

    function file_load(obj){
        var params = {
            uploadify:"file_"+obj+"_uploadify",
            url: basePath + fileUploadURL ,
            previews:false,
            crop:false,
            callUpload:function(file, response) {
                var json = eval('(' + response + ')');
                $("#file_"+obj+"_suffix").val(json.data[0].ext);
                $("#file_"+obj+"_filename").val(json.data[0].oldname);
                $("#file_"+obj+"_file").val(json.data[0].url);
            }
        }
        $.UPLOAD_IMG(params);
    }
</script>
<div id="file_window" class="easyui-window" title="项目文件管理"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:1000px;height:630px;">
    <table id="file_datagrid" style="height:565px;"></table>
    <div id="file_tool" style="padding:0px;height:auto;overflow: hidden;">
        <div class="sgtz_atn">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="downFile('file')">下载</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openOFileWindow('file')">打开</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openAddFileWindow()">新增</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openEditFileWindow()">替换</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="delFile()">删除</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openHisFileWindow()">历史列表</a>
        </div>
    </div>
</div>

<div id="file_his_window" class="easyui-window" title="历史文件管理"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:800px;height:630px;">
    <table id="file_his_datagrid" style="height:565px;"></table>
    <div id="file_his_tool" style="padding:0px;height:auto;overflow: hidden;">
        <div class="sgtz_atn">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="downFile('file_his')">下载</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openOFileWindow('file_his')">打开</a>
        </div>
    </div>
</div>

<div id="file_add_window" class="easyui-window" title="新增文件"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:400px;height:250px;">
    <input id="file_add_uploadify" name="file" type="file" style="display: none"/>
    <form id="file_add_form">
        <input id="file_add_projectid" name="projectid" type="hidden">
        <input id="file_add_modularid" name="modularid" type="hidden">
        <input id="file_add_source" name="source" type="hidden">
        <input id="file_add_sourceid" name="sourceid" type="hidden">
        <input id="file_add_sourceremark" name="sourceremark" type="hidden">
        <ul class="fm_s" style="overflow: inherit;">
            <li class="fm_1l">
                <label>文件名称：</label>
                <input id="file_add_filename" name="filename"  class="easyui-validatebox textbox vipt" readonly="readonly">
            </li>
            <li class="fm_1l">
                <label>文件上传：</label>
                <input id="file_add_file" name="file" type="hidden">
                <input id="file_add_suffix" name="suffix" type="hidden">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="file_load('add')">上传文件</a>
            </li>
            <li class="fm_1l" style="text-align: center;padding-top: 20px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addFile()">确认</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeAddFileWindow()">关闭</a>
            </li>
        </ul>
    </form>
</div>

<div id="file_edit_window" class="easyui-window" title="迭代文件"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:500px;height:350px;">
    <input id="file_edit_uploadify" name="file" type="file" style="display: none"/>
    <form id="file_edit_form">
        <input id="file_edit_id" name="id" type="hidden">
        <ul class="fm_s" style="overflow: inherit;">
            <li class="fm_1l">
                <label>文件名称：</label>
                <input id="file_edit_filename" name="filename"  class="easyui-validatebox textbox vipt"  disabled="disabled">
            </li>
            <li class="fm_1l">
                <label>文件上传：</label>
                <input id="file_edit_file" name="file" type="hidden">
                <input id="file_edit_suffix" name="suffix" type="hidden">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="file_load('edit')">上传文件</a>
            </li>
            <li class="fm_1l">
                <label>变更说明：</label>
                <input name="iterationcauses" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
            </li>
            <li class="fm_1l" style="text-align: center;padding-top: 20px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="editFile()">确认</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeEditFileWindow()">关闭</a>
            </li>
        </ul>
    </form>
</div>

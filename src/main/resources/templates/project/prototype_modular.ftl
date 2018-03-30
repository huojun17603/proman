<script type="text/javascript">
    var prototypeAddURL = "project/prototype/add";
    var prototypeEditTitleURL= "project/prototype/edit/title";
    var prototypeEditDisURL = "project/prototype/edit/dis";
    var prototypeEditImgURL = "project/prototype/edit/img";
    var prototypeHisListURL = "project/prototype/hislist";

    var prototypetagDetailURL = "project/prototype/tag/detail";
    var prototypetagListURL = "project/prototype/tag/list";
    var prototypetagImportsURL = "project/prototype/tag/imports";
    var prototypetagIterationURL = "project/prototype/tag/iteration";
    var prototypetagAddURL =  "project/prototype/tag/add";
    var prototypetagMapURL =  "project/prototype/tag/map";
    var prototypetagHisListURL =  "project/prototype/tag/hislist";
    $(function () {
        $('#prototype_layout').layout();
        $('#tag_layout').layout();
        $('#prototype_datalist').datalist({
            valueField:"id",
            textField:"title",
            checkbox: true,
            lines: true,
            onSelect:function(index,row){
                initPROCenter(row.id,row.img);
            },
            onLoadSuccess: function (data) {
                if (data.rows.length != 0) {
                    $('#prototype_datalist').datagrid("selectRow", 0);
                }
            }
        });
        $('#tag_datalist').datalist({
            valueField: "id",
            textField: "createtime",
            checkbox: true,
            lines: true,
            textFormatter: formatterTime,
            onSelect: function (index, row) {
                loadTAGDetail(row.id);
            },
            onLoadSuccess: function (data) {
                if (data.rows.length != 0) {
                    $('#tag_datalist').datagrid("selectRow", 0);
                }
            }
        });
    });
    function openAddPrototypeWindow(){
        $('#prototype_add_window').window('open');
        $('#prototype_add_window').window('center');
        $("#prototype_add_form").form("clear");
        $('#prototype_add_projectid').val(project_id);
        $('#prototype_add_modularid').val(modular_id);
    }
    function addPrototype(){
        $.ajax({
            url:basePath+prototypeAddURL,
            data:$("#prototype_add_form").serialize(),
            success: function (data){
                if(data.status==0){
                    $('#prototype_datalist').datalist('reload');
                    $('#prototype_add_window').window('close');
                    $.messager.alert("提示框","完成操作请求！");
                }else if(data.status==1){
                    $.messager.alert("错误",data.msg,'warning');
                }else if(data.status==3){
                    $.messager.alert("警告","无权访问",'warning');
                }
            }
        });
    }
    function closeAddPrototypeWindow(){
        $('#prototype_add_window').window('close');
    }
    function editPrototypeTitle() {
        var row = $('#prototype_datalist').datalist('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $.messager.prompt('新的标题', '请输入新的原型标题！', function(title){
            if (title){
                $.ajax({
                    url:basePath+prototypeEditTitleURL,
                    data:{id:row.id,title:title},
                    success: function (data){
                        if(data.status==0){
                            $('#prototype_datalist').datalist('reload');
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
    function editPrototypeDis() {
        var row = $('#prototype_datalist').datalist('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $.messager.confirm('提示', '确认要把原型标记为废弃吗？', function(r){
            if (r){
                $.messager.prompt('输入', '请输入废弃理由！', function(deletecauses){
                    if (deletecauses){
                        $.ajax({
                            url:basePath+prototypeEditDisURL,
                            data:{id:row.id,deletecauses:deletecauses},
                            success: function (data){
                                if(data.status==0){
                                    $('#prototype_datalist').datalist('reload');
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
    function openEditPrototypeWindow() {
        var row = $('#prototype_datalist').datalist('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $('#prototype_edit_window').window('open');
        $('#prototype_edit_window').window('center');
        $("#prototype_edit_form").form("clear");
        $('#prototype_edit_id').val(row.id);
        $('#prototype_edit_title').val(row.title);
        $("#prototype_edit_imports").prop("checked","checked");
    }
    function editPrototype() {
        $.ajax({
            url:basePath+prototypeEditImgURL,
            data:$("#prototype_edit_form").serialize(),
            success: function (data){
                if(data.status==0){
                    $('#prototype_datalist').datalist('reload');
                    $('#prototype_edit_window').window('close');
                    $.messager.alert("提示框","完成操作请求！");
                }else if(data.status==1){
                    $.messager.alert("错误",data.msg,'warning');
                }else if(data.status==3){
                    $.messager.alert("警告","无权访问",'warning');
                }
            }
        });
    }
    function closeEditPrototypeWindow(){
        $('#prototype_edit_window').window('close');
    }

    function openPrototypeHisWindow() {
        var row = $('#prototype_datalist').datalist('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $('#prototype_his_window').window('open');
        $('#prototype_his_window').window('center');
        $("#prototype_his_datagrid").datagrid({
            url:basePath + prototypeHisListURL + "?id=" + row.groupid,
            border:false,
            striped:true,
            fit:true,
            idField:'id',
            loadMsg:'加载中……',
            rownumbers:true,//序号
            singleSelect:true,//单选
            columns:[[
                {field:'createtime',title:'上传时间',align:"center",width:150,formatter:formatterTime},
                {field:'username',title:'上传人',align:"center",width:100},
                {field:'title',title:'标题',align:"center",width:200},
                {field:'groupstatus',title:'状态',align:"center",width:100,formatter:initVersion},
                {field:'id',title:'说明',align:"center",width:350,formatter:initVersionMsg}
            ]],
            toolbar:'#prototype_his_tool'
        });
    }
    function img_load(obj){
        var params = {
            uploadify:"prototype_"+obj+"_uploadify",
            url: basePath + "img/upload",
            previews:false,
            crop:false,
            callUpload:function(file, response) {
                var json = eval('(' + response + ')');
                $("#prototype_"+obj+"_img").val(json.data[0].url);
                $("#prototype_"+obj+"_upload").val("上传完成！");
            }
        }
        $("#prototype_"+obj+"_upload").val("上传中……");
        $.UPLOAD_IMG(params);
    }
</script>
<script type="text/javascript">
    function openAddTAGWindow() {
        var row = $('#prototype_datalist').datalist('getSelected');
        if(isEmpty(row)){
            $.messager.alert("提示框","请选择一条数据记录！");
            return;
        }
        $('#tag_add_window').window('open');
        $('#tag_add_window').window('center');
        $("#tag_add_form").form("clear");
        $("#tag_add_prototypeid").val(row.id);
        $("#tag_add_mapx").val(0);
        $("#tag_add_mapy").val(0);

    }
    function addTAG(){
        $.ajax({
            url:basePath+prototypetagAddURL,
            data:$("#tag_add_form").serialize(),
            success: function (data){
                if(data.status==0){
                    var stly = "top:0;left:0;";
                    var data_top = "data_top=\"0\"";
                    var data_left = "data_left=\"0\"";
                    var tag = "<div id=\""+data.data.id+"\" data_id=\""+data.data.id+"\" name=\"prototype_tag\" style=\""+stly+"\" " +
                            "class=\"easyui-draggable prototype_tag\" " + data_top + " " + data_left + " " +
                            "ondblclick=\"openTAGEditWindow(this)\"></div>"
                    $("#prototype_center").append(tag);
                    openTAGlock(true);
                    closeAddTAGWindow();
                }else if(data.status==1){
                    $.messager.alert("错误",data.msg,'warning');
                }else if(data.status==3){
                    $.messager.alert("警告","无权访问",'warning');
                }
            }
        });
    }
    function closeAddTAGWindow(){
        $('#tag_add_window').window('close');
    }
    function openImportsTAGWindow() {
        
    }
    function loadTAGDetail(id){
        $.ajax({
            url: basePath + prototypetagDetailURL,
            data: {id: id},
            success: function (data) {
                $("#tag_edit_id").val(data.data.id);
                $("#tag_edit_code").val(data.data.code);
                $("#tag_edit_content").textbox("setValue",data.data.content);
                $("#tag_edit_iterationcauses").textbox("setValue",data.data.iterationcauses);
                if(data.data.groupstatus==1){
                    $("#tag_edit_button").show();
                }else{
                    $("#tag_edit_button").hide();
                }
            }
        });
    }
    function openTAGEditWindow(obj){
        var id = $(obj).attr("data_id");
        $("#tag_edit_form").form("clear");
        $.ajax({
            url: basePath + prototypetagDetailURL,
            data: {id: id},
            success: function (data) {
                $("#tag_edit_id").val(data.data.id);
                $("#tag_edit_code").val(data.data.code);
                $("#tag_edit_content").textbox("setValue",data.data.content);
                $("#tag_edit_iterationcauses").textbox("setValue",data.data.iterationcauses);
                $("#tag_edit_button").show();
                $('#tag_datalist').datalist({
                    url:basePath + prototypetagHisListURL + "?id=" + data.data.groupid
                });
            }
        });
        $('#tag_edit_window').window('open');
        $('#tag_edit_window').window('center');
    }
    function editTAG(){
        $.ajax({
            url:basePath+prototypetagIterationURL,
            data:$("#tag_edit_form").serialize(),
            success: function (data){
                if(data.status==0){
                    closeEditTAGWindow();
                }else if(data.status==1){
                    $.messager.alert("错误",data.msg,'warning');
                }else if(data.status==3){
                    $.messager.alert("警告","无权访问",'warning');
                }
            }
        });
    }

    function closeEditTAGWindow(){
        $('#tag_edit_window').window('close');
    }
    function openTAGlock(flag) {
        if(flag){
            $("div[name='prototype_tag']").draggable({
                disabled:false,
                onDrag:onDrag
            });
            $("#tag_lock_button").show();
            $("#tag_unlock_button").hide();

        }else{
            $("div[name='prototype_tag']").draggable({
                disabled:true
            });
            $("#tag_unlock_button").show();
            $("#tag_lock_button").hide();
            $("div[name='prototype_tag']").each(function(j,item){
                // 你要实现的业务逻辑
                var id = $(item).attr("data_id");
                var mapy = $(item).attr("data_top");
                var mapx = $(item).attr("data_left");
                $.ajax({
                    url: basePath + prototypetagMapURL,
                    data: {id: id,mapy:mapy,mapx:mapx},
                    success: function (data) {

                    }
                });
            });
        }
    }
    function initPROCenter(id,img){
        openTAGlock(false);
        $("#prototype_center").empty();
        var img = "<img src=\""+img+"\">";
        $("#prototype_center").append(img);
        $.ajax({
            url:basePath+prototypetagListURL,
            data:{pid:id},
            success: function (data){
                $.each(data.rows, function(index, item){
                    var stly = "top:"+item.mapy+";left:"+item.mapx+";";
                    var data_top = "data_top=\""+item.mapy+"\"";
                    var data_left = "data_left=\""+item.mapx+"\"";
                    var tag = "<div data_id=\""+item.id+"\" name=\"prototype_tag\" style=\""+stly+"\" " +
                            "class=\"prototype_tag\" " + data_top + " " + data_left + " " +
                            "ondblclick=\"openTAGEditWindow(this)\"></div>";
                    $("#prototype_center").append(tag);

                });
            }
        });
    }
    // $(source).draggable('disable');
    //限制拖动区域
    function onDrag(e){
        var d = e.data;
        if (d.left < 0){d.left = 0}
        if (d.top < 0){d.top = 0}
        $(this).attr("data_top",d.top);
        $(this).attr("data_left",d.left);
    }
</script>

<div id="prototype_window" class="easyui-window" title="原型"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true,maximized:true">
    <div id="prototype_layout" style="height: 100%;width: 100%">
        <div data-options="region:'north'" style="height:40px;padding: 5px;">
            <div style="float: left">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openAddPrototypeWindow()">新增</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editPrototypeTitle()">修改标题</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openEditPrototypeWindow()">迭代</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editPrototypeDis()">废弃</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openPrototypeHisWindow()">查看历史</a>
            </div>
            <div style="float: right">
                <a id="tag_unlock_button" href="javascript:void(0)" class="easyui-linkbutton" onclick="openTAGlock(true)">解锁标记</a>
                <a id="tag_lock_button" href="javascript:void(0)" class="easyui-linkbutton" onclick="openTAGlock(false)" style="display: none">锁定标记</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openAddTAGWindow()">添加标记</a>
            </div>
        </div>
        <div data-options="region:'west'" style="width:250px;">
            <div id="prototype_datalist"></div >
        </div>
        <div id="prototype_center" data-options="region:'center'" style="position: relative">
<#--            <div dataid="1111" name="prototype_tag"
                 class="easyui-draggable prototype_tag" style="top:10;left: 20;"
                 data-options="onDrag:onDrag" ondblclick="openTAGEditWindow(this)"></div>-->
        </div>
    </div>
</div>

<div id="prototype_add_window" class="easyui-window" title="新增原型"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:400px;height:250px;">
    <input id="prototype_add_uploadify" name="file" type="file" style="display: none"/>
    <form id="prototype_add_form">
        <input id="prototype_add_projectid" name="projectid" type="hidden">
        <input id="prototype_add_modularid" name="modularid" type="hidden">
        <ul class="fm_s" style="overflow: inherit;">
            <li class="fm_1l">
                <label>原型标题：</label>
                <input id="prototype_add_title" name="title" class="easyui-validatebox textbox vipt" data-options="required:true">
            </li>
            <li class="fm_1l">
                <label>原型图：</label>
                <input id="prototype_add_upload" class="easyui-validatebox textbox vipt" disabled="disabled">
                <input id="prototype_add_img" name="img" type="hidden" >
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="img_load('add')">上传原型图</a>
            </li>
            <li class="fm_1l" style="text-align: center;padding-top: 20px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addPrototype()">确认</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeAddPrototypeWindow()">关闭</a>
            </li>
        </ul>
    </form>
</div>

<div id="prototype_edit_window" class="easyui-window" title="迭代原型"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:500px;height:350px;">
    <input id="prototype_edit_uploadify" name="file" type="file" style="display: none"/>
    <form id="prototype_edit_form">
        <input id="prototype_edit_id" name="id" type="hidden">
        <ul class="fm_s" style="overflow: inherit;">
            <li class="fm_1l">
                <label>原型标题：</label>
                <input id="prototype_edit_title" class="easyui-validatebox textbox vipt" disabled="disabled">
            </li>
            <li class="fm_1l">
                <label>原型图：</label>
                <input id="prototype_edit_upload" class="easyui-validatebox textbox vipt" disabled="disabled">
                <input id="prototype_edit_img" name="img" type="hidden" >
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="img_load('edit')">上传原型图</a>
            </li>
            <li class="fm_1l">
                <label>引入：</label>
                <input id="prototype_edit_imports" type="checkbox" name="imports" checked="checked">
                <span style="color: red">不勾选则不会引入前一个版本的原型标记</span>
            </li>
            <li class="fm_1l">
                <label>变更说明：</label>
                <input name="iterationcauses" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
            </li>
            <li class="fm_1l" style="text-align: center;padding-top: 20px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="editPrototype()">确认</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeEditPrototypeWindow()">关闭</a>
            </li>
        </ul>
    </form>
</div>

<div id="prototype_his_window" class="easyui-window" title="历史原型列表"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:1000px;height:630px;">
    <table id="prototype_his_datagrid" style="height:565px;"></table>
    <div id="prototype_his_tool" style="padding:0px;height:auto;overflow: hidden;">
        <div class="sgtz_atn">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="()">查看</a>
        </div>
    </div>
</div>

<!-- //////////////////////////////////////////////////////////////////////// -->

<div id="tag_add_window" class="easyui-window" title="新增标记"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:600px;height:450px;">
    <form id="tag_add_form">
        <input id="tag_add_prototypeid" name="prototypeid" type="hidden">
        <input id="tag_add_mapx" name="mapx" type="hidden">
        <input id="tag_add_mapy" name="mapy" type="hidden">
        <ul class="fm_s" style="overflow: inherit;">
            <li class="fm_1l">
                <label>编号：</label>
                <input id="tag_add_code" name="code" class="easyui-validatebox textbox vipt" >
            </li>
            <li class="fm_1l">
                <label>内容：</label>
                <input id="tag_add_content" name="content" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:250px">
            </li>
            <li class="fm_1l" style="text-align: center;padding-top: 20px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addTAG()">确认</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeAddTAGWindow()">关闭</a>
            </li>
        </ul>
    </form>
</div>

<div id="tag_edit_window" class="easyui-window" title="标记编辑"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:850px;height:600px;">
    <div id="tag_layout" style="height: 100%;width: 100%">
        <div data-options="region:'west'" style="width:250px;">
            <div id="tag_datalist"></div >
        </div>
        <div id="prototype_center" data-options="region:'center'" style="position: relative">
            <form id="tag_edit_form">
                <input id="tag_edit_id" name="id" type="hidden">
                <ul class="fm_s" style="overflow: inherit;">
                    <li class="fm_1l">
                        <label>编号：</label>
                        <input id="tag_edit_code" name="code" class="easyui-validatebox textbox vipt" disabled="disabled">
                    </li>
                    <li class="fm_1l">
                        <label>内容：</label>
                        <input id="tag_edit_content" name="content" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:250px">
                    </li>
                    <li class="fm_1l">
                        <label>迭代说明：</label>
                        <input id="tag_edit_iterationcauses" name="iterationcauses" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:100px">
                    </li>
                    <li class="fm_1l">
                        <label> </label>
                        <span style="color: red">请在更新时，重写迭代说明！！！！</span>
                    </li>
                    <li class="fm_1l" style="text-align: center;padding-top: 20px">
                        <a id="tag_edit_button" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="editTAG()">确认更新</a>
                    </li>
                </ul>
            </form>
        </div>
    </div>

</div>
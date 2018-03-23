<script type="text/javascript">
    $(function () {
        $('#prototype_layout').layout();
        $('#role_datalist').datalist({
            valueField:"id",
            textField:"title",
            checkbox: true,
            lines: true,
            onSelect:function(index,row){

            }
        });
    });
    function openAddPrototypeWindow(){
        $('#prototype_add_window').window('open');
        $('#prototype_add_window').window('center');
        $('#prototype_add_projectid').val(project_id);
        $('#prototype_add_modularid').val(modular_id);
    }
    function addPrototype(){

    }
    function closeAddPrototypeWindow(){
        $('#prototype_add_window').window('close');
    }
   // $(source).draggable('disable');
    //限制拖动区域
    function onDrag(e){
        var d = e.data;
        if (d.left < 0){d.left = 0}
        if (d.top < 0){d.top = 0}
        $(this).attr("data_top",d.top);
    }
    function onDragLeave(e) {
        alert();
    }

    function img_load(){
        var params = {
            uploadify:"prototype_uploadify",
            url: basePath + "management/file/img/upload",
            previews:false,
            crop:false,
            callUpload:function(file, response) {
                var json = eval('(' + response + ')');
                $("#prototype_add_img").val(json.data[0].url);
            }
        }
        $.UPLOAD_IMG(params);
    }
</script>
<div id="prototype_window" class="easyui-window" title="原型"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true,maximized:true">
    <div id="prototype_layout" style="height: 100%;width: 100%">
        <div data-options="region:'north'" style="height:40px;padding: 5px;">
            <div style="float: left">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openAddPrototypeWindow()">新增</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="()">修改标题</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="()">迭代</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="()">废弃</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="()">查看历史</a>
            </div>
            <div style="float: right">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="()">解锁标记</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="()">添加标记</a>
            </div>
        </div>
        <div data-options="region:'west'" style="width:150px;">
            <div id="role_datalist"></div >
        </div>
        <div  data-options="region:'center'" style="position: relative">
            <img src="/static/images/111.jpg">
            <div name="prototype_tag" class="easyui-draggable" data-options="onDrag:onDrag" ondblclick="onDragLeave()" style="position: absolute;top: 0;left: 0;z-index: 100;width:40px;height:40px;background: url('/static/images/look.png');background-size: 100%">
            </div>
        </div>
    </div>
</div>

<div id="prototype_add_window" class="easyui-window" title="新增原型"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:400px;height:250px;">
    <input id="prototype_uploadify" type="file" style="display: none"/>
    <form id="prototype_add_form">
        <input id="prototype_add_projectid" name="projectid" type="hidden">
        <input id="prototype_add_modularid" name="modularid" type="hidden">
        <ul class="fm_s" style="overflow: inherit;">
            <li class="fm_1l">
                <label>原型标题：</label>
                <input id="prototype_add_title" name="title" class="easyui-combobox vipt" data-options="required:true">
            </li>
            <li class="fm_1l">
                <label>原型图：</label>
                <input id="prototype_add_img" name="img" type="hidden" >
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="img_load()">上传原型图</a>
            </li>
            <li class="fm_1l" style="text-align: center;padding-top: 20px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addPrototype()">确认</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeAddPrototypeWindow()">关闭</a>
            </li>
        </ul>
    </form>
</div>
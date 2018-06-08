<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <#include  "../common/shareJs.ftl">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<#--<script type="text/javascript" src="/static/js/employee/organizationIndex.js"></script>-->
	<script type="text/javascript">
        var detailUrl = "project/center";
        var addUrl = "project/add";

		var projectList = "project/grouplist";
        var versionList = "project/verisons";
        $(function(){
            $('#project_list').combobox({
                url:basePath + projectList,
                valueField:'versionid',
                textField:'title',
                animate:true,
                lines:true,
                onSelect: function(node){
                    $('#version_list').combobox({
                        url:basePath + versionList + "?id=" + node.id,
                        valueField:'id',
                        textField:'version',
                        onSelect: function(node){

                        }
                    });
                }
            });
        })
		function openPWindow(){
            var id = $('#version_list').combobox("getValue");
            if(isEmpty(id)){
                $.messager.alert("提示","请选择项目信息",'warning');
                return;
			}
			window.location.href = basePath+detailUrl+"?id="+id;
		}

        function openAddWindow(){
            $('#add_form').form("clear");
            $('#add_window').window('open');
            $('#add_window').window('center');
        }
        function addProject(){
            if(!$('#add_form').form("validate")) return;
            $.ajax({
                url: basePath+addUrl,
                data: $('#add_form').serialize(),
                success: function(data){
                    if(data.status==0){
                        $.messager.confirm('提示', '完成操作请求！', function(){
                            window.location.reload();
                        });
                    }else if(data.status==1){
                        $.messager.alert("错误",data.msg,'warning');
                    }else{
                        $.messager.alert("警告","无权访问",'warning');
                    }
                }
            });
		}
        function closedAddWindow(){
            $('#add_window').window('close');
        }

	</script>
	<style type="text/css">

	</style>
  </head>
  
  <body>
  	<span>项目：</span><input id="project_list" class="easyui-combobox"/>
    <span>版本：</span><input id="version_list" class="easyui-combobox">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openPWindow()">进入项目</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openAddWindow()">新增项目</a>


    <div id="add_window" class="easyui-window" title="新增项表单"
         data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closable:false,closed:true"
         style="width:450px;height:300px;padding:10px;">
        <div style="padding:5px 20px 5px 20px">
            <form id="add_form">
                <ul class="fm_s">
                    <li>
                        <label>项目标题：</label>
                        <input id="title_input" name="title" class="easyui-validatebox textbox vipt" style="height: 20px;"
                               data-options="required:true">
                    </li>
                    <li>
                        <label>版本号：</label>
                        <input id="version_input" name="version" class="easyui-validatebox textbox vipt" style="height: 20px;"
                               data-options="required:true">
                    </li>
                </ul>
                <div class="sgtz_center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addProject()">确认</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closedAddWindow()">取消</a>
                </div>
            </form>
        </div>
    </div>

  </body>
</html>

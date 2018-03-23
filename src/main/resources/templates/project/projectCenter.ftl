<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <#include  "../common/shareJs.ftl">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<#--<script type="text/javascript" src="/static/js/employee/organizationIndex.js"></script>-->
	<script type="text/javascript">
        var project_id = "${project.id}";
        var editTitleURL = "project/edit/title";
        var editToHisURL = "project/edit/his";
        var editToDisURL = "project/edit/del";
        var exeversionURL = "";

        function openModularPage(modularid) {
            window.location.href = basePath+"project/modular/center?projectid="+project_id+"&modularid="+modularid;
        }
        function editTitle(){
            $.messager.prompt('新的标题', '请输入新的项目标题！', function(title){
                if (title){
                    $.ajax({
                        url:basePath+editTitleURL,
                        data:{id:project_id,title:title},
                        success: function (data){
                            if(data.status==0){
                                $.messager.confirm('提示', '完成操作请求！', function(){
                                    window.location.reload();
                                });
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
        function editToHis(){
            $.messager.confirm('提示', '确认要把项目标记为历史吗？', function(r){
                if (r){
                    $.messager.prompt('输入', '请输入变更理由！', function(title){
                        if (title){
                            $.ajax({
                                url:basePath+editToHisURL,
                                data:{id:project_id,iterationcauses:title},
                                success: function (data){
                                    if(data.status==0){
                                        $.messager.confirm('提示', '完成操作请求！', function(){
                                            window.location.reload();
                                        });
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
        function editToDis(){
            $.messager.confirm('提示', '确认要把项目标记为废弃吗？', function(r){
                if (r){
                    $.messager.prompt('输入', '请输入变更理由！', function(title){
                        if (title){
                            $.ajax({
                                url:basePath+editToDisURL,
                                data:{id:project_id,deletecauses:title},
                                success: function (data){
                                    if(data.status==0){
                                        $.messager.confirm('提示', '完成操作请求！', function(){
                                            window.location.reload();
                                        });
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
        function exeversion(){
            alert("等待开发!");
        }
        var fileURL = "";
        var source = "";
        var sourceid = "";
        function openFileWindow(){
            $('#file_window').window('open');
            $('#file_window').window('center');
            $("#file_datagrid").datagrid({
                url:basePath + fileURL + "?projectid=" + project_id
            });
        }
        var roleURL = "project/role/list";
        function openRoleWindow(){
            $('#role_window').window('open');
            $('#role_window').window('center');
            $("#role_datagrid").datagrid({
                url:basePath + roleURL + "?projectid=" + project_id
            });
        }

        var modularAddURL = "project/modular/add";
        function opneAddModularWindow() {
            $('#modular_add_window').window('open');
            $('#modular_add_window').window('center');
            $('#modular_add_form').form("clear");
            $('#modular_add_projectid').val(project_id);
        }
        function closeAddModularWindow() {
            $('#modular_add_window').window('close');
        }
        function addModular() {
            $.ajax({
                url:basePath+modularAddURL,
                data:$("#modular_add_form").serialize(),
                success: function (data){
                    if(data.status==0){
                        $.messager.confirm('提示', '完成操作请求！', function(){
                            window.location.reload();
                        });
                    }else if(data.status==1){
                        $.messager.alert("错误",data.msg,'warning');
                    }else if(data.status==3){
                        $.messager.alert("警告","无权访问",'warning');
                    }
                }
            });
        }
	</script>
  </head>
  
  <body>
  <#include "file_modular.ftl">
  <#include "role_modular.ftl">
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">项目基本信息</span>
                  <a onclick="openFileWindow()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >查看文件列表</a>
                  <a onclick="openRoleWindow()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >查看参与列表</a>
                  <a onclick="opneAddModularWindow()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >新增模块</a>
                  <a onclick="editTitle()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >修改标题</a>
                  <a onclick="editToHis()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >标记为历史</a>
                  <a onclick="editToDis()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >标记为废弃</a>
                  <a onclick="exeversion()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >版本分离</a>
              </div>
          </div>
          <div id="window_account_body" class="homeDivBody" style="overflow: auto; ">
              <div name="dataMsg" >
                  <ul class="fm_s">
                      <li>
                          <label>项目标题：</label>
                          <input id="title_dinput" class="easyui-validatebox textbox vipt" value="${project.title}" disabled="disabled">
                      </li>
                      <li >
                          <label>版本号：</label>
                          <input id="version_dinput" class="easyui-validatebox textbox vipt" value="${project.version}" disabled="disabled">
                      </li>
                      <li >
                          <label>当前标记：</label>
                          <input id="createtime_dinput" class="easyui-validatebox textbox vipt" value="<#if project.status == 1>正常<#elseif  project.status == 2>历史<#elseif  project.status == 3>废弃</#if>" disabled="disabled">
                      </li>
                      <li >
                          <label>迭代时间：</label>
                          <input id="createtime_dinput" class="easyui-validatebox textbox vipt" value="${project.iterationtime?string("yyyy-MM-dd HH:mm:ss")}" disabled="disabled">
                      </li>
                      <li >
                          <label>立项时间：</label>
                          <input id="createtime_dinput" class="easyui-validatebox textbox vipt" value="${project.createtime?string("yyyy-MM-dd HH:mm:ss")}" disabled="disabled">
                      </li>
                    <#if project.status == 2>
                      <li class="fm_1l">
                          <label>迭代说明：</label>
                          <input id="handleresult_input" name="handleresult" class="easyui-textbox" value="${project.iterationcauses}"  multiline="true" labelPosition="top" style="width:73%;height:60px">
                      </li>
                    </#if>
                    <#if project.status == 3>
                      <li class="fm_1l">
                          <label>删除说明：</label>
                          <input id="handleresult_input" name="handleresult" class="easyui-textbox" value="${project.deletecauses}"  multiline="true" labelPosition="top" style="width:73%;height:60px">
                      </li>
                    </#if>
                  </ul>
              </div>
          </div>
      </div>
  </div>
  <#list modulars as modular>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">${modular.modularname}</span>
                  <a onclick="openModularPage('${modular.id}')"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >进入</a>
              </div>
          </div>
      </div>
  </div>
  </#list>
  <div id="modular_add_window" class="easyui-window" title="新增模块"
       data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
       style="width:400px;height:200px;">
      <form id="modular_add_form">
          <input id="modular_add_projectid" name="projectid" type="hidden">
          <ul class="fm_s" style="overflow: inherit;">
              <li class="fm_1l">
                  <label>模块名称：</label>
                  <input id="modular_addname" name="modularname" class="easyui-validatebox textbox vipt ">
              </li>
              <li class="fm_1l" style="text-align: center;padding-top: 20px">
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addModular()">确认</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeAddModularWindow()">关闭</a>
              </li>
          </ul>
      </form>
  </div>
  </body>
</html>

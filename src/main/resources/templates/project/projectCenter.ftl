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
        var fileURL = "project/file/listbyp";
        function openFileWindow(){
            $('#file_window').window('open');
            $('#file_window').window('center');
            $("#file_datagrid").datagrid({
                url:basePath + fileURL + "?projectid=" + project_id
            });
            file_source.projectid = "${project.id}";
            file_source.modularid = "";
            file_source.source = "1";
            file_source.sourceremark = "${project.title}:${project.version}";
            file_source.sourceid = "${project.id}";
        }
        var roleURL = "project/role/list";
        function openRoleWindow(){
            $('#role_window').window('open');
            $('#role_window').window('center');
            $("#role_datagrid").datagrid({
                url:basePath + roleURL + "?projectid=" + project_id
            });
        }


	</script>
  </head>
  
  <body>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">项目基本信息</span>
<#--                  <a onclick="openFileWindow()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >查看文件列表</a>
                  <a onclick="openRoleWindow()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >查看参与列表</a>
                  <a onclick="opneAddModularWindow()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >新增模块</a>-->
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
  <script type="text/javascript">
      function openRolePage(){
          window.location.href = basePath+"project/role/center?projectid="+project_id;
      }
  </script>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">参与者管理</span>
                  <a onclick="openRolePage()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >进入</a>
              </div>
          </div>
      </div>
  </div>
  <script type="text/javascript">
      function openCatalogPage(){
          window.location.href = basePath+"project/catalog/center?projectid="+project_id;
      }
  </script>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">目录管理</span>
                  <a onclick="openCatalogPage()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >进入</a>
              </div>
          </div>
      </div>
  </div>
  <script type="text/javascript">
      function openModularPage(){
          window.location.href = basePath+"project/modular/center?projectid="+project_id;
      }
  </script>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">模块管理</span>
                  <a onclick="openModularPage()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >进入</a>
              </div>
          </div>
      </div>
  </div>
  <script type="text/javascript">
      //跳转至原型管理中心
      function openPrototypePage(){
          window.location.href = basePath+"project/pcatalog/center?projectid="+project_id;
      }
  </script>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">原型管理</span>
                  <a onclick="openPrototypePage()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >进入</a>
              </div>
          </div>
      </div>
  </div>
  <script type="text/javascript">
      function openTaskPage(){
          window.location.href = basePath+"project/task/center?projectid="+project_id;
      }
  </script>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">任务管理</span>
                  <a onclick="openTaskPage()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >进入</a>
              </div>
          </div>
      </div>
  </div>
  <script type="text/javascript">
      function openTestPage(){
          window.location.href = basePath+"project/test/center?projectid="+project_id;
      }
  </script>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">用例管理</span>
                  <a onclick="openTestPage()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >进入</a>
              </div>
          </div>
      </div>
  </div>
  <script type="text/javascript">
      function openBUGPage(){
          window.location.href = basePath+"project/bug/center?projectid="+project_id;
      }
  </script>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">BUG管理</span>
                  <a onclick="openBUGPage()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >进入</a>
              </div>
          </div>
      </div>
  </div>
  <script type="text/javascript">
      function openFilePage(){
          window.location.href = basePath+"project/file/center?projectid="+project_id;
      }
  </script>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">文件管理</span>
                  <a onclick="openFilePage()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >进入</a>
              </div>
          </div>
      </div>
  </div>

<#--  <#list modulars as modular>
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
  </#list>-->

  </body>
</html>

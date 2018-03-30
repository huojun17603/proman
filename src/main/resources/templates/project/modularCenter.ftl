<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <#include  "../common/shareJs.ftl">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
  </head>
  <script type="text/javascript">
      var project_id = "${projectid}";
      var modular_id = "${modular.id}";
      var modularDelURL = "project/modular/del";

      function gotoProject() {
          window.location.href = basePath+"project/center?id="+project_id;
      }
      function delModular(){
          $.messager.confirm('提示', '确认要删除此模块吗？', function(r){
              if (r){
                  $.ajax({
                      url:basePath+modularDelURL,
                      data:{id:modular_id},
                      success: function (data){
                          if(data.status==0){
                              $.messager.confirm('提示', '完成操作请求！', function(){
                                  gotoProject();
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
      var fileURL = "project/file/listbym";
      function openFileWindow(){
          $('#file_window').window('open');
          $('#file_window').window('center');
          $("#file_datagrid").datagrid({
              url:basePath + fileURL + "?modularid=" + modular_id
          });
          file_source.projectid = project_id;
          file_source.modularid = modular_id;
          file_source.source = "2";
          file_source.sourceremark = "${modular.modularname}";
          file_source.sourceid = modular_id;
      }
  </script>
  <body>
  <#include "file_modular.ftl">
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">${modular.modularname}<#if modular.isdefault>（默认）</#if></span>
                  <a onclick="gotoProject()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >返回项目</a>
                  <#if !modular.isdefault><a onclick="delModular()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >删除</a></#if>
              </div>
          </div>

      </div>
  </div>
  <#include "prototype_modular.ftl">
  <script type="text/javascript">
      var prototypeListURL = "project/prototype/list";
      function openPrototypeModular(){
          $('#prototype_window').window('open');
          $('#prototype_window').window('center');
          $('#prototype_datalist').datalist({
              url:basePath + prototypeListURL + "?modularid=" + modular_id
          });
      }
  </script>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">原型（${modular.prototypenum}）</span>
                  <a onclick="openPrototypeModular()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >列表</a>
              </div>
          </div>
      </div>
  </div>

  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">设计（${modular.designnum}）</span>
                  <a onclick=""  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >列表</a>
              </div>
          </div>
      </div>
  </div>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">任务（${modular.tasknum}）</span>
                  <a onclick=""  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >列表</a>
              </div>
          </div>
      </div>
  </div>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">用例（${modular.testnum}）</span>
                  <a onclick=""  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >列表</a>
              </div>
          </div>
      </div>
  </div>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">BUG（${modular.bugnum}）</span>
                  <a onclick=""  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >列表</a>
              </div>
          </div>
      </div>
  </div>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">文件（${modular.filenum}）</span>
                  <a onclick="openFileWindow()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >列表</a>
              </div>
          </div>
      </div>
  </div>
  </body>
</html>

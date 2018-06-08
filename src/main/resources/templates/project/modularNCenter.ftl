<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <#include  "../common/shareJs.ftl">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
      <script type="text/javascript">
          var project_id = "${projectid}";
          var modularAddURL = "project/modular/add";
          var modularDelURL = "project/modular/del";
          var modularListURL = "project/modular/list";
          var catalogURL = "project/catalog/query";
          $(function () {
              $("#modular_datagrid").datagrid({
                  url:basePath + modularListURL + "?projectid=${projectid}",
                  border:false,
                  striped:true,
                  fit:true,
                  idField:'id',
                  loadMsg:'加载中……',
                  rownumbers:true,//序号
                  singleSelect:true,//单选
                  columns:[[
                      {field:'catalogtitle',title:'目录',width:300},
                      {field:'modularname',title:'模块名称',width:1000}
                  ]],
                  toolbar:'#modular_tool'
              });
              $.ajax({
                  url:basePath + catalogURL + "?projectid=${projectid}",
                  success: function (data){
                      if(data.status==0){
                          $("#modular_catalogid").combobox({
                              data:data.rows,
                              editable:false,
                              valueField:"id",
                              textField:"title"
                          });
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }

              });


          });

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
                          $('#modular_datagrid').datagrid('reload');
                          $.messager.alert("提示框","完成操作请求！");
                          closeAddModularWindow();
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }
              });
          }
          function delModular(){
              var row = $('#modular_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $.messager.confirm('提示', '确认要删除此模块吗？', function(r){
                  if (r){
                      $.ajax({
                          url:basePath+modularDelURL,
                          data:{id:row.id},
                          success: function (data){
                              if(data.status==0){
                                  $('#modular_datagrid').datagrid('reload');
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
          function openPWindow(){
              window.location.href = basePath+"project/center"+"?id="+project_id;
          }
      </script>
  </head>

  <body style="width:100%;height:100%;">

    <table id="modular_datagrid"></table>
    <div id="modular_tool" style="padding:0px;height:auto;overflow: hidden;">
        <div style="padding: 8px 35px 8px 14px;margin-bottom: 10px;text-shadow: 0 1px 0 rgba(255,255,255,0.5);background-color: #fcf8e3;border: 1px solid #fbeed5;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;color: #666;">
            <div style="line-height:30px">页面说明：原型目录页，用于管理项目中的多套原型</div>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="opneAddModularWindow()">新增</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="delModular()">删除</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openPWindow()">返回项目</a>
        </div>
    </div>

    <div id="modular_add_window" class="easyui-window" title="新增模块"
         data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
         style="width:400px;height:200px;">
        <form id="modular_add_form">
            <input id="modular_add_projectid" name="projectid" type="hidden">
            <ul class="fm_s" style="overflow: inherit;">
                <li class="fm_1l">
                    <label>目录：</label>
                    <input id="modular_catalogid" name="catalogid" >
                </li>
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

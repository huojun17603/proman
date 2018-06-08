<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <#include  "../common/shareJs.ftl">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
      <script type="text/javascript" src="/static/script/easyui/datagrid-detailview.js"></script>
      <script type="text/javascript">
          var project_id = "${projectid}";
          function openPWindow(){
              window.location.href = basePath+"project/center"+"?id="+project_id;
          }

          var task_params = {
              projectid:project_id
          }
          var taskCompURL = "project/task/edit/complete";
          var taskAppointURL = "project/task/edit/appoint";
          var taskReceiveURL = "project/task/edit/receive";
          var taskEditURL = "project/task/edit";
          var taskAddURL = "project/task/add";
          var taskDelURL = "project/task/del";
          var empURL = "admin/employee/query";
          var taskListURL = "project/task/list";

          var cataloglist = "project/catalog/query";
          var terminallist = "project/terminal/list";
          $(function () {
              $("#task_datagrid").datagrid({
                  url:basePath + taskListURL + "?projectid=" + project_id,
                  border: false,
                  striped: true,
                  fit:true,
                  pageSize:20,
                  pageList:[20,50],
                  idField:'id',
                  loadMsg:'加载中……',
                  rownumbers:true,//序号
                  pagination:true,//显示底部分页工具栏
                  singleSelect:true,//单选
                  columns: [[
                      {
                          field: 'status', title: '状态', align: "center", width: 100, formatter: function (value, row) {
                          if (value == 1) return "待领取";
                          if (value == 2) return "待完成";
                          if (value == 3) return "完成";
                          if (value == 4) return "删除";
                          return "错误";
                      }
                      },
                      {field: 'catalogtitle', title: '所属目录', align: "center", width: 150},
                      {field: 'terminal', title: '所属终端', align: "center", width: 150},
                      {field: 'modularname', title: '所属模块', align: "center", width: 150},
                      {field: 'code', title: '任务编码', align: "center", width: 100},
                      {field: 'title', title: '标题', align: "center", width: 350},
                      {field: 'receivename', title: '领取人', align: "center", width: 100},
                      {field: 'power', title: '工时', align: "center", width: 80},
                      {field: 'createtime', title: '创建时间', align: "center", width: 150, formatter: formatterTime},
                      {field: 'receivetime', title: '领取时间', align: "center", width: 150, formatter: formatterTime},
                      {field: 'completetime', title: '完成时间', align: "center", width: 150, formatter: formatterTime}
                  ]],
                  toolbar: '#task_tool',
                  view: detailview,
                  detailFormatter:function(index,row){
                      return '<div class="ddv" style="padding:5px 0"></div>';
                  },
                  onExpandRow: function(index,row){
                      var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
                      ddv.panel({
                          height:80,
                          border:false,
                          cache:false,
                          content:row.content,
                          onLoad:function(){
                              $('#task_datagrid').datagrid('fixDetailRowHeight',index);
                          }
                      });
                      $('#task_datagrid').datagrid('fixDetailRowHeight',index);
                  }
              });
              $.ajax({
                  url:basePath+cataloglist+"?projectid="+project_id,
                  success: function (data){
                      if(data.status==0){
                          $("#task_down_catalog").combobox({
                              editable:false,
                              valueField:"id",
                              textField:"title",
                              multiple:true,
                              data:data.rows
                          });
                          $("#task_add_catalog").combobox({
                              editable:false,
                              valueField:"id",
                              textField:"title",
                              data:data.rows,
                              onClick:function (rec) {
                                  $.ajax({
                                      url:basePath+"project/modular/list?catalogid=" + rec.id,
                                      success: function (data){
                                          if(data.status==0){
                                              $("#task_add_modularid").combobox({
                                                  editable:false,
                                                  valueField:"id",
                                                  textField:"modularname",
                                                  data:data.rows
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
                          $("#sk_catalog").combobox({
                              valueField:"id",
                              textField:"title",
                              data:data.rows,
                              onClick:function (rec) {
                                  $.ajax({
                                      url:basePath+"project/modular/list?catalogid=" + rec.id,
                                      success: function (data){
                                          if(data.status==0){
                                              $("#sk_modular").combobox({
                                                  valueField:"id",
                                                  textField:"modularname",
                                                  data:data.rows
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
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }

              });
              $.ajax({
                  url:basePath+terminallist,
                  success: function (data){
                      if(data.status==0){
                          $("#task_add_terminal").combobox({
                              editable:false,
                              valueField:"name",
                              textField:"name",
                              data:data.rows
                          });
                          $("#sk_terminal").combobox({
                              valueField:"name",
                              textField:"name",
                              data:data.rows
                          });
                          $("#task_down_terminal").combobox({
                              editable:false,
                              valueField:"name",
                              textField:"name",
                              multiple:true,
                              data:data.rows
                          });
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }

              });


          });
          function doSearch(){
              var searchkey = $("#sk_searchkey").val();
              var modularid = $("#sk_modular").combobox("getValue");
              var catalogid = $("#sk_catalog").combobox("getValue");
              var terminal = $("#sk_terminal").combobox("getValue");
              $("#task_datagrid").datagrid("reload",{searchkey:searchkey,catalogid:catalogid,modularid:modularid,terminal:terminal});
          }

          function openAddTaskWindow() {
              $('#task_add_window').window('open');
              $('#task_add_window').window('center');
              $('#task_add_form').form("clear");
              $('#task_add_projectid').val(task_params.projectid);
          }

          function addTask() {
              $.ajax({
                  url:basePath+taskAddURL,
                  data:$("#task_add_form").serialize(),
                  success: function (data){
                      if(data.status==0){
                          $('#task_datagrid').datagrid('reload');
                          closeAddTaskWindow();
                          $.messager.alert("提示框","完成操作请求！");
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }
              });
          }

          function closeAddTaskWindow(){
              $('#task_add_window').window('close');
          }

          function openEditTaskWindow() {
              var row = $('#task_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $('#task_edit_window').window('open');
              $('#task_edit_window').window('center');
              $('#task_edit_form').form("clear");
              $('#task_edit_id').val(row.id);
              $('#task_edit_title').val(row.title);
              $('#task_edit_power').val(row.power);
              $('#task_edit_projectid').val(task_params.projectid);
              $('#task_edit_content').textbox("setValue",row.content);
          }

          function editTask() {
              $.ajax({
                  url:basePath+taskEditURL,
                  data:$("#task_edit_form").serialize(),
                  success: function (data){
                      if(data.status==0){
                          $('#task_datagrid').datagrid('reload');
                          closeEditTaskWindow();
                          $.messager.alert("提示框","完成操作请求！");
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }
              });
          }

          function closeEditTaskWindow(){
              $('#task_edit_window').window('close');
          }

          function completeTask() {
              var row = $('#task_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $.messager.confirm('提示', '确认更改为完成状态吗？', function(r){
                  if (r){
                      $.ajax({
                          url:basePath+taskCompURL,
                          data:{id:row.id},
                          success: function (data){
                              if(data.status==0){
                                  $('#task_datagrid').datagrid('reload');
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
          function receiveTask() {
              var row = $('#task_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $.messager.confirm('提示', '确认领取该任务？', function(r){
                  if (r){
                      $.ajax({
                          url:basePath+taskReceiveURL,
                          data:{id:row.id},
                          success: function (data){
                              if(data.status==0){
                                  $('#task_datagrid').datagrid('reload');
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
          function delTask() {
              var row = $('#task_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $.messager.confirm('提示', '确认删除该任务？', function(r){
                  if (r){
                      $.ajax({
                          url:basePath+taskDelURL,
                          data:{id:row.id},
                          success: function (data){
                              if(data.status==0){
                                  $('#task_datagrid').datagrid('reload');
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
          function appointTask(){
              var row = $('#task_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
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
                      $.each(data, function(index, item){
                          $.ajax({
                              url:basePath+taskAppointURL,
                              data:{id:row.id,userid:item.id,username:item.name},
                              success: function (data){
                                  if(data.status==0){
                                      $('#task_datagrid').datagrid('reload');
                                      $.messager.alert("提示框","完成操作请求！");
                                  }else if(data.status==1){
                                      $.messager.alert("错误",data.msg,'warning');
                                  }else if(data.status==3){
                                      $.messager.alert("警告","无权访问",'warning');
                                  }
                              }
                          });
                      });
                  }
              };
              $.createSelector($("#appoint_selector"),property);
          }

          function file_load(){
              var params = {
                  uploadify:"file_uploadify",
                  url: basePath + "project/task/file/upload?projectid="+project_id ,
                  previews:false,
                  crop:false,
                  callUpload:function(file, response) {
                      var json = eval('(' + response + ')');
                      $.messager.confirm('提示', json.msg, function(){
                          window.location.reload();
                      });
                  }
              }
              $.UPLOAD_IMG(params);
          }

          function openFileDownWindow() {
              $('#task_down_window').window('open');
              $('#task_down_window').window('center');
          }
          
          function file_down(){
              var catalogid = $("#task_down_catalog").combobox("getValues");
              var terminal = $("#task_down_terminal").combobox("getValues");
              window.open(basePath   + "project/task/file/down?projectid="+project_id+ "&catalogids="+catalogid+"&terminals="+terminal);
          }
      </script>
  </head>

  <body style="width:100%;height:100%;">
  <div id="appoint_selector"></div>
  <table id="task_datagrid" style="height:565px;"></table>
  <input id="file_uploadify" name="file" type="file" style="display: none"/>
  <div id="task_tool" style="padding:0px;height:auto;overflow: hidden;">
      <div class="sgtz_atn">
          <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="completeTask()">完成</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="appointTask()">指派</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="receiveTask()">领取</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openAddTaskWindow()">新增</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openEditTaskWindow()">修改</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="delTask()">删除</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="file_load()">上传进度表</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openFileDownWindow()">下载进度表</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openPWindow()">返回项目</a>
      </div>
      <div class="sgtz_atn">
          <span style="font-weight: bold;">搜索筛选：</span>
          <input id="sk_catalog" class="easyui-combobox">
          <input id="sk_modular" class="easyui-combobox">
          <input id="sk_terminal" class="easyui-combobox" >
          <input id="sk_searchkey" class="easyui-searchbox" data-options="prompt:'搜索关键字（目录标题）',searcher:doSearch" style="width:300px"></input>
      </div>
  </div>

  <div id="task_down_window" class="easyui-window" title="下载"
       data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
       style="width:600px;height: 250px;">
      <ul class="fm_s" style="overflow: inherit;">
          <li class="fm_1l">
              <label>目录（多选）：</label>
              <input id="task_down_catalog"  name="catalogid"  class="easyui-combobox" style="width:70%;">
          </li>
          <li class="fm_1l">
              <label>终端（多选）：</label>
              <input id="task_down_terminal"  name="terminal"  class="easyui-combobox" style="width:70%;" >
          </li>
          <li class="fm_1l" style="text-align: center;padding-top: 20px">
              <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="file_down()">下载</a>
              <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="$('#task_down_window').window('close')">关闭</a>
          </li>
      </ul>
  </div>


  <div id="task_add_window" class="easyui-window" title="新增任务"
       data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
       style="width:600px;height:550px;">
      <form id="task_add_form">
          <input id="task_add_projectid" name="projectid" type="hidden">
          <ul class="fm_s" style="overflow: inherit;">
              <li class="fm_1l">
                  <label>所属目录：</label>
                  <input id="task_add_catalog"  name="catalogid"  class="easyui-combobox" >
              </li>
              <li class="fm_1l">
                  <label>模块：</label>
                  <input id="task_add_modularid"  name="modularid"  class="easyui-combobox" >
              </li>
              <li class="fm_1l">
                  <label>终端：</label>
                  <input id="task_add_terminal"  name="terminal"  class="easyui-combobox" >
              </li>
              <li class="fm_1l">
                  <label>编码：</label>
                  <input  name="code"  class="easyui-validatebox textbox vipt" >
              </li>
              <li class="fm_1l">
                  <label>权重（工时）：</label>
                  <input name="power" class="easyui-numberbox textbox vipt" data-options="min:0,precision:0">
              </li>
              <li class="fm_1l">
                  <label>标题：</label>
                  <input name="title"  class="easyui-validatebox textbox vipt" >
              </li>
              <li class="fm_1l">
                  <label>内容：</label>
                  <input name="content" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:150px">
              </li>
              <li class="fm_1l" style="text-align: center;padding-top: 20px">
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addTask()">确认</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeAddTaskWindow()">关闭</a>
              </li>
          </ul>
      </form>
  </div>

  <div id="task_edit_window" class="easyui-window" title="修改"
       data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
       style="width:600px;height:370px;">
      <form id="task_edit_form">
          <input id="task_edit_id" name="id" type="hidden">
          <input id="task_edit_projectid" name="projectid" type="hidden">
          <ul class="fm_s" style="overflow: inherit;">
              <li class="fm_1l">
                  <label>工时：</label>
                  <input   id="task_edit_power" name="power" class="easyui-numberbox textbox vipt" data-options="min:0,precision:0">
              </li>
              <li class="fm_1l">
                  <label>标题：</label>
                  <input id="task_edit_title" name="title"  class="easyui-validatebox textbox vipt" >
              </li>
              <li class="fm_1l">
                  <label>内容：</label>
                  <input id="task_edit_content" name="content" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:150px">
              </li>
              <li class="fm_1l" style="text-align: center;padding-top: 20px">
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="editTask()">确认</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeEditTaskWindow()">关闭</a>
              </li>
          </ul>
      </form>
  </div>

  </body>
</html>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <#include  "../common/shareJs.ftl">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
      <script type="text/javascript">
          var project_id = "${projectid}";
          function openPWindow(){
              window.location.href = basePath+"project/center"+"?id="+project_id;
          }

          var bug_params = {
              projectid:project_id
          }
          var bugListURL = "project/bug/list";
          var bugDetail = "project/bug/detail";
          var bugAddURL = "project/bug/add";
          var bugOpenURL = "project/bug/open";
          var bugTestURL = "project/bug/test";
          var bugCompleteURL = "project/bug/complete";
          var bugReopenURL = "project/bug/reopen";
          var bugCloseURL = "project/bug/close";
          var bugAppointURL = "project/bug/appoint";
          var bugReceiveURL = "project/bug/receive";

          var empURL = "admin/employee/query";
          $(function () {
              $("#bug_datagrid").datagrid({
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
                      {field: 'catalogtitle', title: '所属目录', align: "center", width: 150},
                      {field: 'terminal', title: '所属终端', align: "center", width: 150},
                      {field: 'modularname', title: '所属模块', align: "center", width: 150},
                      {field: 'title', title: '标题', align: "center", width: 250},
                      {field: 'username', title: '创建人', align: "center", width: 100},
                      {field: 'repairname', title: '修复人', align: "center", width: 100},
                      {field: 'createtime', title: '创建时间', align: "center", width: 150, formatter: formatterTime},
                      {field: 'endtime', title: '完结时间', align: "center", width: 150, formatter: formatterTime}
                  ]],
                  toolbar: '#bug_tool'
              });
              gotoBUG1();
          });
          function gotoBUG1() {
              $('#bug_datagrid').datagrid({
                  url:basePath + bugListURL + "?projectid=" + project_id + "&status=0"
              });
              $("#div_tool_1").show();
              $("#div_tool_2").hide();
              $("#div_tool_3").hide();
          }
          function gotoBUG2() {
              $('#bug_datagrid').datagrid({
                  url:basePath + bugListURL + "?projectid=" + project_id + "&status=1"
              });
              $("#div_tool_1").hide();
              $("#div_tool_2").show();
              $("#div_tool_3").hide();
          }
          function gotoBUG3() {
              $('#bug_datagrid').datagrid({
                  url:basePath + bugListURL + "?projectid=" + project_id + "&status=2"
              });
              $("#div_tool_1").hide();
              $("#div_tool_2").hide();
              $("#div_tool_3").show();
          }
          function gotoBUG4() {
              $('#bug_datagrid').datagrid({
                  url:basePath + bugListURL + "?projectid=" + project_id + "&status=3"
              });
              $("#div_tool_1").hide();
              $("#div_tool_2").hide();
              $("#div_tool_3").hide();
          }
          function gotoBUG5() {
              $('#bug_datagrid').datagrid({
                  url:basePath + bugListURL + "?projectid=" + project_id + "&status=4"
              });
              $("#div_tool_1").hide();
              $("#div_tool_2").hide();
              $("#div_tool_3").hide();
          }

          function openBUGAddWindow() {
              $('#bug_add_window').window('open');
              $('#bug_add_window').window('center');
              $('#bug_add_form').form("clear");
              $('#bug_add_projectid').val(bug_params.projectid);
          }

          function addBUG() {
              $.ajax({
                  url:basePath+bugAddURL,
                  data:$("#bug_add_form").serialize(),
                  success: function (data){
                      if(data.status==0){
                          $('#bug_datagrid').datagrid('reload');
                          closeAddBUGWindow();
                          $.messager.alert("提示框","完成操作请求！");
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }
              });
          }

          function closeAddBUGWindow(){
              $('#bug_add_window').window('close');
          }

          function openBUGOpenWindow(){
              var row = $('#bug_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $('#bug_open_window').window('open');
              $('#bug_open_window').window('center');
              $('#bug_open_form').form("clear");
              $('#bug_open_id').val(row.id);
              $('#bug_open_title').val(row.title);
              $('#bug_open_process').textbox("setValue",row.process);
              $('#bug_open_results').textbox("setValue",row.results);
              $('#bug_open_expect').textbox("setValue",row.expect);
          }

          function BUGOpen(){
              $.ajax({
                  url:basePath+bugOpenURL,
                  data:$("#bug_open_form").serialize(),
                  success: function (data){
                      if(data.status==0){
                          $('#bug_datagrid').datagrid('reload');
                          closeBUGOpenWindow();
                          $.messager.alert("提示框","完成操作请求！");
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }
              });
          }
          function closeBUGOpenWindow(){
              $('#bug_open_window').window('close');
          }

          function openBUGTestWindow() {
              var row = $('#bug_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $('#bug_complete_window').window('open');
              $('#bug_complete_window').window('center');
              $('#bug_complete_form').form("clear");
              $('#bug_complete_id').val(row.id);
          }
          function BUGTest(){
              $.ajax({
                  url:basePath+bugTestURL,
                  data:$("#bug_complete_form").serialize(),
                  success: function (data){
                      if(data.status==0){
                          $('#bug_datagrid').datagrid('reload');
                          closeBUGTestWindow();
                          $.messager.alert("提示框","完成操作请求！");
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }
              });
          }
          function closeBUGTestWindow(){
              $('#bug_complete_window').window('close');
          }

          function openBUGEndWindow() {
              var row = $('#bug_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $('#bug_end_window').window('open');
              $('#bug_end_window').window('center');
              $('#bug_end_form').form("clear");
              $('#bug_end_id').val(row.id);
          }
          function BUGEnd(){
              var url = bugReopenURL;
              var type = $('#bug_end_type').combobox("getValue");
              if(!type){
                  $.messager.alert("提示框","请选择处理结果！");
                  return;
              }
              if(type==1) url = bugCompleteURL;
              $.ajax({
                  url:basePath+url,
                  data:$("#bug_end_form").serialize(),
                  success: function (data){
                      if(data.status==0){
                          $('#bug_datagrid').datagrid('reload');
                          closeBUGEndWindow();
                          $.messager.alert("提示框","完成操作请求！");
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }
              });
          }
          function closeBUGEndWindow(){
              $('#bug_end_window').window('close');
          }

          function closeBUG() {
              var row = $('#bug_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $.messager.confirm('提示', '确认关闭该BUG？', function(r){
                  if (r){
                      $.messager.prompt('输入', '请输入关闭理由！', function(title) {
                          if (title) {
                              $.ajax({
                                  url: basePath + bugCloseURL,
                                  data: {id: row.id,repairremark:title},
                                  success: function (data) {
                                      if (data.status == 0) {
                                          $('#bug_datagrid').datagrid('reload');
                                          $.messager.alert("提示框", "完成操作请求！");
                                      } else if (data.status == 1) {
                                          $.messager.alert("错误", data.msg, 'warning');
                                      } else if (data.status == 3) {
                                          $.messager.alert("警告", "无权访问", 'warning');
                                      }
                                  }
                              });
                          }
                      });
                  }
              });
          }


          function openBUGTestSelectorByAdd(){
              var callback_function = function(data){//选择值回调
                  $.each(data, function(index, item){
                      $('#bug_add_testid').val(item.id);
                      $('#bug_add_testname').val(item.title);
                      $('#bug_add_title').val(item.title);
                      $('#bug_add_process').textbox("setValue",item.process);
                      $('#bug_add_expect').textbox("setValue",item.results);
                  });

              }
              openBUGTestSelector(callback_function);
          }
          function openBUGTestSelectorByOpen(){
              var callback_function = function(data){//选择值回调
                  $.each(data, function(index, item){
                      $('#bug_open_testid').val(item.id);
                      $('#bug_open_testname').val(item.title);
                      $('#bug_open_title').val(item.title);
                      $('#bug_open_process').textbox("setValue",item.process);
                      $('#bug_open_expect').textbox("setValue",item.results);
                  });
              }
              openBUGTestSelector(callback_function);
          }
          function openBUGTestSelector(callback_function){
              var property={
                  title:"测试用例",
                  dataUrl:"project/test/list?projectid=" + project_id,//数据连接
                  singleSelect:true,//单选/多选
                  searchPrompt:"请输入关键字",//搜索提示
                  columns:[[
                      {field: 'code', title: '编码', align: "center", width: 100},
                      {field: 'title', title: '标题', align: "center", width: 250},
                      {field: 'username', title: '创建人', align: "center", width: 100},
                      {field: 'createtime', title: '创建时间', align: "center", width: 150, formatter: formatterTime}
                  ]],
                  searchCall:function(dg,value){//搜索值回调
                      dg.datagrid('reload',{searchkey:value});
                  },
                  callback:callback_function,
              };
              $.createSelector($("#bug_test_selector"),property);
          }

          function appointBUG(){
              var row = $('#bug_datagrid').datagrid('getSelected');
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
                              url:basePath+bugAppointURL,
                              data:{id:row.id,userid:item.id,username:item.name},
                              success: function (data){
                                  if(data.status==0){
                                      $('#bug_datagrid').datagrid('reload');
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
              $.createSelector($("#bug_appoint_selector"),property);
          }

          function receiveBUG() {
              var row = $('#bug_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $.messager.confirm('提示', '确认领取该BUG？', function(r){
                  if (r){
                      $.ajax({
                          url:basePath+bugReceiveURL,
                          data:{id:row.id},
                          success: function (data){
                              if(data.status==0){
                                  $('#bug_datagrid').datagrid('reload');
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
          function selectTestClass(val) {
              if(val==1) $("#bug_add_test_li").hide();
              if(val==2) $("#bug_add_test_li").show();
          }
      </script>
  </head>

  <body style="width:100%;height:100%;">
  <div id="bug_test_selector"></div>
  <div id="bug_appoint_selector"></div>

  <table id="bug_datagrid" style="height:565px;"></table>
  <div id="bug_tool" style="padding:0px;height:auto;overflow: hidden;">
      <div class="sgtz_atn">
          <a href="javascript:void(0)" class="easyui-linkbutton"  data-options="toggle:true,group:'g1',selected:true" style="width:100px" onclick="gotoBUG1()">待确认</a>
          <a href="javascript:void(0)" class="easyui-linkbutton"  data-options="toggle:true,group:'g1'" style="width:100px" onclick="gotoBUG2()">待修复</a>
          <a href="javascript:void(0)" class="easyui-linkbutton"  data-options="toggle:true,group:'g1'" style="width:100px" onclick="gotoBUG3()">待测试</a>
          <a href="javascript:void(0)" class="easyui-linkbutton"  data-options="toggle:true,group:'g1'" style="width:100px" onclick="gotoBUG4()">已完成</a>
          <a href="javascript:void(0)" class="easyui-linkbutton"  data-options="toggle:true,group:'g1'" style="width:100px" onclick="gotoBUG5()">已关闭</a>
          <div style="line-height:30px">------------------------------------------------------</div>
          <div id="div_tool_1" style="float: left;margin-bottom: 5px">
              <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openBUGAddWindow()">新增</a>
              <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openBUGOpenWindow()">确认</a>
              <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="closeBUG()">关闭</a>
          </div>
          <div id="div_tool_2" style="float: left;margin-bottom: 5px">
              <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="receiveBUG()">领取</a>
              <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="appointBUG()">指派</a>
              <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openBUGTestWindow()">完成修复</a>
          </div>
          <div id="div_tool_3" style="float: left;margin-bottom: 5px">
              <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openBUGEndWindow()">确认</a>
          </div>
          <div style="float: right;margin-bottom: 5px">
              <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openDetailBUGWindow()">明细</a>
              <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openPWindow()">返回项目</a>
          </div>
      </div>
  </div>



  <div id="bug_add_window" class="easyui-window" title="新增BUG"
       data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
       style="width:600px;height:550px;">
      <form id="bug_add_form">
          <input id="bug_add_projectid" name="projectid" type="hidden">
          <input id="bug_add_modularid" name="modularid" type="hidden">
          <ul class="fm_s" style="overflow: inherit;">
              <li class="fm_1l">
                  <label>添加类型：</label>
                  <select onclick="selectTestClass(this.value)">
                      <option value="1">直接添加（普通人员用）</option>
                      <option value="2">选择用例（测试人员用）</option>
                  </select>
              </li>
              <li id="bug_add_test_li" class="fm_1l" style="display: none">
                  <label>选择用例：</label>
                  <input id="bug_add_testname"  onclick="openBUGTestSelectorByAdd()" class="easyui-validatebox textbox vipt" readonly="readonly">
                  <input id="bug_add_testid" name="testid" type="hidden">
              </li>
              <li class="fm_1l">
                  <label>标题：</label>
                  <input id="bug_add_title" name="title"  class="easyui-validatebox textbox vipt" >
              </li>
              <li class="fm_1l">
                  <label>过程：</label>
                  <input id="bug_add_process"  name="process" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:120px">
              </li>
              <li class="fm_1l">
                  <label>预期结果：</label>
                  <input id="bug_add_expect"  name="expect" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
              </li>
              <li class="fm_1l">
                  <label>实际结果：</label>
                  <input name="results" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
              </li>
              <li class="fm_1l" style="text-align: center;padding-top: 20px">
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addBUG()">确认</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeAddBUGWindow()">关闭</a>
              </li>
          </ul>
      </form>
  </div>

  <div id="bug_open_window" class="easyui-window" title="确认BUG"
       data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
       style="width:600px;height:550px;">
      <form id="bug_open_form">
          <input id="bug_open_id" name="id" type="hidden">
          <ul class="fm_s" style="overflow: inherit;">
              <li class="fm_1l">
                  <label>选择用例：</label>
                  <input id="bug_open_testname"  onclick="openBUGTestSelectorByOpen()" class="easyui-validatebox textbox vipt" readonly="readonly">
                  <input id="bug_open_testid" name="testid" type="hidden">
              </li>
              <li class="fm_1l">
                  <label>标题：</label>
                  <input id="bug_open_title" name="title"  class="easyui-validatebox textbox vipt" >
              </li>
              <li class="fm_1l">
                  <label>过程：</label>
                  <input id="bug_open_process"  name="process" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:120px">
              </li>
              <li class="fm_1l">
                  <label>预期结果：</label>
                  <input id="bug_open_expect"  name="expect" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
              </li>
              <li class="fm_1l">
                  <label>实际结果：</label>
                  <input id="bug_open_results" name="results" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
              </li>
              <li class="fm_1l" style="text-align: center;padding-top: 20px">
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="BUGOpen()">确认</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeBUGOpenWindow()">关闭</a>
              </li>
          </ul>
      </form>
  </div>

  <div id="bug_complete_window" class="easyui-window" title="测试申请"
       data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
       style="width:600px;height:200px;">
      <form id="bug_complete_form">
          <input id="bug_complete_id" name="id" type="hidden">
          <ul class="fm_s" style="overflow: inherit;">
              <li class="fm_1l">
                  <label>修复说明：</label>
                  <input name="repairremark" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
              </li>
              <li class="fm_1l" style="text-align: center;padding-top: 20px">
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="BUGTest()">确认</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeBUGTestWindow()">关闭</a>
              </li>
          </ul>
      </form>
  </div>

  <div id="bug_end_window" class="easyui-window" title="完成确认"
       data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
       style="width:600px;height:200px;">
      <form id="bug_end_form">
          <input id="bug_end_id" name="id" type="hidden">
          <ul class="fm_s" style="overflow: inherit;">
              <li class="fm_1l">
                  <label>处理结果：</label>
                  <select id="bug_end_type" class="easyui-combobox" >
                      <option value="1">完成</option>
                      <option value="2">重新修复</option>
                  </select>
              </li>
              <li class="fm_1l" style="text-align: center;padding-top: 20px">
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="BUGEnd()">确认</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeBUGEndWindow()">关闭</a>
              </li>
          </ul>
      </form>
  </div>

  <div id="bug_detail_window" class="easyui-window" title="用例"
       data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
       style="width:600px;height:550px;">
      <ul class="fm_s" style="overflow: inherit;">
          <li class="fm_1l">
              <label>标题：</label>
              <input id="bug_detail_title" name="title"  class="easyui-validatebox textbox vipt"  readonly="readonly" >
          </li>
          <li class="fm_1l">
              <label>发起者：</label>
              <input id="bug_detail_username" name="username"  class="easyui-validatebox textbox vipt"  readonly="readonly" >
          </li>
          <li class="fm_1l">
              <label>修复者：</label>
              <input id="bug_detail_repairname" name="repairname"  class="easyui-validatebox textbox vipt"  readonly="readonly" >
          </li>
          <li class="fm_1l">
              <label>过程：</label>
              <input id="bug_detail_process" name="process" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:120px">
          </li>
          <li class="fm_1l">
              <label>预期结果：</label>
              <input id="bug_detail_expect" name="expect" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
          </li>
          <li class="fm_1l">
              <label>实际结果：</label>
              <input id="bug_detail_results" name="results" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
          </li>
          <li class="fm_1l">
              <label>处理结果：</label>
              <input id="bug_detail_repairremark" name="repairremark" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
          </li>
      </ul>
  </div>

  </body>
</html>

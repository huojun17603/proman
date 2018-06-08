<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <#include  "../common/shareJs.ftl">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
      <script type="text/javascript">
          var role_data = [{id:"1",text:"项目经理"},{id:"2",text:"原型设计"},
              {id:"3",text:"美工"},{id:"4",text:"JAVA开发"},{id:"5",text:"IOS开发"},{id:"6",text:"安卓开发"},{id:"7",text:"测试"}];
          function initCapitalType(value,row,index){
              if(value==1) {
                  return "项目经理"
              } else if(value==2) {
                  return "原型设计"
              } else if(value==3) {
                  return "美工"
              } else if(value==4) {
                  return "JAVA开发"
              } else if(value==5) {
                  return "IOS开发"
              } else if(value==6) {
                  return "安卓开发"
              } else if(value==7) {
                  return "测试"
              } else {
                  return "未知"
              }
          }
          var roleURL = "project/role/list";
          var roleAddURL = "project/role/add";
          var roleDelURL = "project/role/del";
          var empURL = "admin/employee/query";
          var project_id = "${projectid}";
          $(function () {
              $("#role_datagrid").datagrid({
                  url:basePath + roleURL + "?projectid=${projectid}",
                  border:false,
                  striped:true,
                  fit:true,
                  idField:'id',
                  loadMsg:'加载中……',
                  rownumbers:true,//序号
                  singleSelect:true,//单选
                  columns:[[
                      {field:'role',title:'职位',align:"center",width:350,formatter:initCapitalType},
                      {field:'username',title:'人员名称',align:"center",width:350}
                  ]],
                  toolbar:'#role_tool'
              });
              $("#role_add_role").combobox({
                  editable:false,
                  data:role_data,
                  valueField:'id',
                  textField:'text'
              });
          });
          function openAddRoleWindow() {
              $('#role_add_window').window('open');
              $('#role_add_window').window('center');
              $('#role_add_form').form("clear");
              $('#role_add_projectid').val(project_id);
          }
          function closeAddRoleWindow(){
              $('#role_add_window').window('close');
          }

          function openRoleSelector(){
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
                      var ids=[];
                      var names = [];
                      $.each(data, function(index, item){
                          ids.push(item.id);
                          names.push(item.name);
                      });
                      $('#role_add_userid').val(ids.join(","));
                      $('#role_add_username').val(names.join(","));
                  }
              };
              $.createSelector($("#role_selector"),property);
          }

          function addRole(){
              $.ajax({
                  url:basePath+roleAddURL,
                  data:$("#role_add_form").serialize(),
                  success: function (data){
                      if(data.status==0){
                          $('#role_datagrid').datagrid('reload');
                          closeAddRoleWindow();
                          $.messager.alert("提示框","完成操作请求！");
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }
              });
          }

          function delRole() {
              var row = $('#role_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $.messager.confirm('提示', '确认要剔除这个参与者吗？', function(r){
                  if (r){
                      $.ajax({
                          url:basePath+roleDelURL,
                          data:{id:row.id},
                          success: function (data){
                              if(data.status==0){
                                  $('#role_datagrid').datagrid('reload');
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

    <table id="role_datagrid"></table>
    <div id="role_tool" style="padding:0px;height:auto;overflow: hidden;">
        <div style="padding: 8px 35px 8px 14px;margin-bottom: 10px;text-shadow: 0 1px 0 rgba(255,255,255,0.5);background-color: #fcf8e3;border: 1px solid #fbeed5;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;color: #666;">
            <div style="line-height:30px">页面说明：原型目录页，用于管理项目中的多套原型</div>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openAddRoleWindow()">新增</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="delRole()">删除</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="openPWindow()">返回项目</a>
        </div>
    </div>

    <div id="role_add_window" class="easyui-window" title="新增人员"
         data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
         style="width:400px;height:250px;">
        <form id="role_add_form">
            <input id="role_add_projectid" name="projectid" type="hidden">
            <ul class="fm_s" style="overflow: inherit;">
                <li class="fm_1l">
                    <label>员工：</label>
                    <input id="role_add_username" name="username" class="easyui-validatebox textbox vipt " data-options="required:true" readonly="readonly" onclick="openRoleSelector()">
                    <input id="role_add_userid" name="userid" type="hidden">
                </li>
                <li class="fm_1l">
                    <label>职位：</label>
                    <input id="role_add_role" name="role" class="easyui-combobox vipt" >
                </li>
                <li class="fm_1l" style="text-align: center;padding-top: 20px">
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addRole()">确认</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeAddRoleWindow()">关闭</a>
                </li>
            </ul>
        </form>
    </div>

    <div id="role_selector"></div>

  </body>
</html>

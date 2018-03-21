<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <#include  "../common/shareJs.ftl">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<#--<script type="text/javascript" src="/static/js/employee/organizationIndex.js"></script>-->
	<script type="text/javascript">
    function openModularPage() {
        window.location.href = basePath+"modular/center?projectid=${id}";
    }
	</script>
	<style type="text/css">
    
	</style>
  </head>
  
  <body>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">项目基本信息</span>
                  <a onclick=""  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >查看文件列表</a>
                  <a onclick=""  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >修改标记</a>
                  <a onclick=""  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >修改标题</a>
                  <a onclick=""  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >版本分离</a>
              </div>
          </div>
          <div id="window_account_body" class="homeDivBody" style="overflow: auto; ">
              <div name="dataMsg" >
                  <ul class="fm_s">
                      <li>
                          <label>项目标题：</label>
                          <input id="name_dinput" class="easyui-validatebox textbox vipt"  disabled="disabled">
                      </li>
                      <li >
                          <label>版本号：</label>
                          <input id="createtime_dinput" class="easyui-validatebox textbox vipt" disabled="disabled">
                      </li>
                      <li >
                          <label>当前标记：</label>
                          <input id="createtime_dinput" class="easyui-validatebox textbox vipt" disabled="disabled">
                      </li>
                      <li >
                          <label>迭代时间：</label>
                          <input id="createtime_dinput" class="easyui-validatebox textbox vipt" disabled="disabled">
                      </li>
                      <li >
                          <label>立项时间：</label>
                          <input id="createtime_dinput" class="easyui-validatebox textbox vipt" disabled="disabled">
                      </li>
                      <li class="fm_1l">
                          <label>迭代说明：</label>
                          <input id="handleresult_input" name="handleresult" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:73%;height:60px">
                      </li>
                      <li class="fm_1l">
                          <label>删除说明：</label>
                          <input id="handleresult_input" name="handleresult" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:73%;height:60px">
                      </li>
                  </ul>
              </div>
          </div>
      </div>
  </div>
  <div class="homeDivBox">
      <div class="homeDivBorder">
          <div class="homeDivHead">
              <div>
                  <span class="htl">模块</span>
                  <a onclick="openModularPage()"  href="javascript:void(0)" class="easyui-linkbutton" style="float: right;margin-top: 8px;margin-right: 8px" >进入</a>
              </div>
          </div>
      </div>
  </div>
  </body>
</html>

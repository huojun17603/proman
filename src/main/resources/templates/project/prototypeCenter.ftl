<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <#include  "../common/shareJs.ftl">
        <meta http-equiv="pragma" content="no-cache"/>
        <meta http-equiv="cache-control" content="no-cache"/>
        <meta http-equiv="expires" content="0"/>
        <style type="text/css">
            /*.prototype_img{max-width: 100%;overflow: auto;margin: auto;position: absolute;left: 0; right: 0;}*/
            /*.prototype_img{max-width: 100%;overflow: auto;margin: auto;position: absolute;}*/
        </style>

        <script type="text/javascript">
            var catalogid = "${catalog.id}";
            var projectid = "${project.id}";
            var prototypeAddURL = "project/prototype/add";
            var prototypeEditTitleURL= "project/prototype/edit/title";
            var prototypeEditDisURL = "project/prototype/edit/dis";
            var prototypeEditImgURL = "project/prototype/edit/img";
            var prototypeEditDefaultURL = "project/prototype/edit/default";
            var prototypeListURL = "project/prototype/query";
            var prototypeHisListURL = "project/prototype/hislist";
            var prototypeDetailURL = "project/prototype/detail";
            var prototypeDefaultURL = "project/prototype/default";

            var prototypetagDetailURL = "project/prototype/tag/detail";
            var prototypetagListURL = "project/prototype/tag/list";
            var prototypetagIterationURL = "project/prototype/tag/iteration";
            var prototypetagAddURL =  "project/prototype/tag/add";
            var prototypetagMapURL =  "project/prototype/tag/map";
            var prototypetagHisListURL =  "project/prototype/tag/hislist";

            var prototypeLogListURL = "project/prototype/log/list";

            var _p_id = "";
            var _p_img = "";
            function lookHis() {
                var row = $('#prototype_his_datagrid').datalist('getSelected');
                if(isEmpty(row)){
                    $.messager.alert("提示框","请选择一条数据记录！");
                    return;
                }
                _p_id = row.id;
                _p_img = row.img;
                $("#dq_prototype").text(row.title + findVersions(row.groupstatus));
                initPROCenter(row.id,row.img,false);
                $("#prototype_window").window("close");
                $("#prototype_his_window").window("close");
            }

            function img_load(obj){
                var params = {
                    uploadify:"prototype_"+obj+"_uploadify",
                    url: basePath + "img/upload",
                    previews:false,
                    crop:false,
                    callUpload:function(file, response) {
                        var json = eval('(' + response + ')');
                        $("#prototype_"+obj+"_img").val(json.data[0].url);
                        $("#prototype_"+obj+"_upload").val("上传完成！");
                    }
                }
                $("#prototype_"+obj+"_upload").val("上传中……");
                $.UPLOAD_IMG(params);
            }

            function findVersions(status) {
                if (status == 1)return "(当前版本)";
                if (status == 2)return "(历史版本)";
                if (status == 3)return "(废弃版本)";
                return "(未知)";
            }

            $(function () {
                $('#prototype_layout').layout();
                $('#tag_layout').layout();

                $.ajax({
                    url:basePath+prototypeDefaultURL,
                    data:{catalogid:catalogid},
                    success: function (data){
                        if(data.data){
                            _p_id = data.data.id;
                            _p_img = data.data.img;
                            $("#dq_prototype").text(data.data.title + findVersions(data.data.groupstatus));
                            initPROCenter(data.data.id,data.data.img,false);
                        }else{
                            $.messager.alert("提示框","请设置默认页面！");
                        }
                    }
                });
                $('#tag_datalist').datalist({
                    valueField: "id",
                    textField: "createtime",
                    checkbox: true,
                    lines: true,
                    textFormatter: formatterTime,
                    onSelect: function (index, row) {
                        loadTAGDetail(row.id);
                    },
                    onLoadSuccess: function (data) {
                        if (data.rows.length != 0) {
                            $('#tag_datalist').datagrid("selectRow", 0);
                        }
                    }
                });
            });
            function openPListWindow() {
                $("#prototype_datagrid").datagrid({
                    url:basePath + prototypeListURL + "?status=1&catalogid=" + catalogid,
                    border:false,
                    striped:true,
                    fit:true,
                    pageSize:20,
                    pageList:[20,50],
                    idField:'id',
                    loadMsg:'加载中……',
                    rownumbers:true,//序号
                    pagination:true,//显示底部分页工具栏
                    singleSelect:true,//单选
                    columns:[[
                        {field:'title',title:'标题',align:"center",width:200},
                        {field:'isdefault',title:'首页',align:"center",width:100,formatter:function (value) {
                            return value==1?"是":"否";
                        }},
                        {field:'username',title:'上传人',align:"center",width:100},
                        {field:'createtime',title:'上传时间',align:"center",width:150,formatter:formatterTime},
                        {field:'id',title:'说明',align:"center",width:350,formatter:initVersionMsg}
                    ]],
                    toolbar:'#prototype_tool'
                });
                $('#prototype_window').window('open');
                $('#prototype_window').window('center');
            }
            function openPrototypeHisWindow() {
                var row = $('#prototype_datagrid').datagrid('getSelected');
                if(isEmpty(row)){
                    $.messager.alert("提示框","请选择一条数据记录！");
                    return;
                }
                $('#prototype_his_window').window('open');
                $('#prototype_his_window').window('center');
                $("#prototype_his_datagrid").datagrid({
                    url:basePath + prototypeHisListURL + "?id=" + row.groupid,
                    border:false,
                    striped:true,
                    fit:true,
                    idField:'id',
                    loadMsg:'加载中……',
                    rownumbers:true,//序号
                    singleSelect:true,//单选
                    columns:[[
                        {field:'createtime',title:'上传时间',align:"center",width:150,formatter:formatterTime},
                        {field:'username',title:'上传人',align:"center",width:100},
                        {field:'title',title:'标题',align:"center",width:200},
                        {field:'groupstatus',title:'状态',align:"center",width:100,formatter:initVersion},
                        {field:'id',title:'说明',align:"center",width:350,formatter:initVersionMsg}
                    ]],
                    toolbar:'#prototype_his_tool'
                });
            }
            function initPROCenter(id,img,flag){
                $("#prototype_center").empty();
                var img_div = "<img src=\""+img+"\" class='prototype_img'>";
                $("#prototype_center").append(img_div);
                $.ajax({
                    url:basePath+prototypetagListURL,
                    data:{pid:id},
                    success: function (data){
                        $.each(data.rows, function(index, item){
                            var stly = "top:"+item.mapy+";left:"+item.mapx+";";
                            var data_top = "data_top=\""+item.mapy+"\"";
                            var data_left = "data_left=\""+item.mapx+"\"";
                            if(item.classes==1){
                                var div1 = "<div class=\"prototype_look\" ondblclick=\"openTAGEditWindow('"+item.id+"')\"></div>";
                                var tag = "<div data_id=\""+item.id+"\" name=\"prototype_tag\" style=\""+stly+"\" " +
                                        "class=\"prototype_tag\" " + data_top + " " + data_left + " " +
                                        "onmouseover=\"onmouseover1(this)\" onmouseout=\"onmouseout1(this)\" >"+div1+"</div>";
                                $("#prototype_center").append(tag);
                            }else{
                                var div1 = "<div class=\"prototype_dw\" ondblclick=\"GOTOPrototype('"+item.content+"')\"></div>";
                                var div2 = "<div class=\"prototype_edit\" ondblclick=\"openTAGEditWindow2('"+item.id+"')\"></div>";
                                var tag = "<div data_id=\""+item.id+"\" name=\"prototype_tag\" style=\""+stly+"\" " +
                                        "class=\"prototype_tag\" " + data_top + " " + data_left + " " +
                                        "onmouseover=\"onmouseover2(this)\" onmouseout=\"onmouseout2(this)\" >"+div1+ div2 + "</div>";
                                $("#prototype_center").append(tag);
                            }

                        });
                        openTAGlock(flag);
                    }
                });
            }
            function openTAGlock(flag) {
                if(flag){
                    $("div[name='prototype_tag']").draggable({
                        disabled:false,
                        onDrag:onDrag
                    });
                    $("#tag_lock_button").show();
                    $("#tag_unlock_button").hide();

                }else{
                    $("div[name='prototype_tag']").draggable({
                        disabled:true
                    });
                    $("#tag_unlock_button").show();
                    $("#tag_lock_button").hide();
                    $("div[name='prototype_tag']").each(function(j,item){
                        // 你要实现的业务逻辑
                        var id = $(item).attr("data_id");
                        var mapy = $(item).attr("data_top");
                        var mapx = $(item).attr("data_left");
                        $.ajax({
                            url: basePath + prototypetagMapURL,
                            data: {id: id,mapy:mapy,mapx:mapx},
                            success: function (data) {

                            }
                        });
                    });
                }
            }
            //限制拖动区域
            function onDrag(e){
                var d = e.data;
                if (d.left < 0){d.left = 0}
                if (d.top < 0){d.top = 0}
                $(this).attr("data_top",d.top);
                $(this).attr("data_left",d.left);
            }
            function onmouseover1(obj) {

            }
            function onmouseout1(obj) {

            }
            function onmouseover2(obj) {
                $(obj).children(".prototype_edit").show();
            }
            function onmouseout2(obj) {
                $(obj).children(".prototype_edit").hide();
            }

            function GOTOPrototype(id) {
                $.ajax({
                    url: basePath + prototypeDetailURL,
                    data: {id: id},
                    success: function (data) {
                        _p_id = data.data.id;
                        _p_img = data.data.img;
                        $("#dq_prototype").text(data.data.title + findVersions(data.data.groupstatus));
                        initPROCenter(data.data.id,data.data.img,false);
                    }
                });
            }

        </script>
        <script>
            var ishavCatalog = ${hav_catalogs};
            var catalogtree = "project/pcatalog/tree";
            var catalogadd = "project/pcatalog/add";
            var catalogedit = "project/pcatalog/edit";
            var catalogdel = "project/pcatalog/del";
            $(function () {
                if(ishavCatalog==1) {
                    $('#catalog_tree').tree({
                        url: basePath + catalogtree + "?catalogid=${catalog.id}",
                        animate: true,
                        lines: true,
                        onClick:function(node){
                            var id = node.attributes[0];
                            $.ajax({
                                url:basePath+prototypeDetailURL,
                                data:{id:id},
                                success: function (data){
                                    if(data.data){
                                        _p_id = data.data.id;
                                        _p_img = data.data.img;
                                        $("#dq_prototype").text(data.data.title + findVersions(data.data.groupstatus));
                                        initPROCenter(data.data.id,data.data.img,false);
                                    }else{
                                        $.messager.alert("提示框","请设置默认页面！");
                                    }
                                }
                            });
                        }
                    });
                }
            })
            function openPCatalogWindow() {
                $("#catalog_datagrid").treegrid({
                    url: basePath + catalogtree + "?catalogid=${catalog.id}",
                    border:false,
                    fit:true,
                    rownumbers:true,
                    idField:"id",
                    treeField:"text",
                    columns:[[
                        {field:'id',title:'id',hidden:true},
                        {field:'text',title:'菜单名称',width:150},
                        {field:'prototypename',title:'原型标题',width:150},
                        {field:'remark',title:'说明',width:300}
                    ]],
                    toolbar:'#catalog_tool'
                });
                $('#catalog_window').window('open');
                $('#catalog_window').window('center');
            }
            function openAddCTJWindow(){
                $('#catalog_app_form').form('clear');
                var row = $('#catalog_datagrid').datagrid('getSelected');
                if(row != null){
                    $('#catalog_app_form').form('load',{
                        fid:row.parent_id,
                        projectid:projectid,
                        catalogid:catalogid
                    });
                }else{
                    $('#catalog_app_form').form('load',{
                        projectid:projectid,
                        catalogid:catalogid
                    });
                }
                $('#catalog_app_window').window('open');
                $('#catalog_app_window').window('center');
            }
            function openAddCZJWindow(){
                $('#catalog_app_form').form('clear');
                var row = $('#catalog_datagrid').datagrid('getSelected');
                if(row == null){
                    $.messager.alert("提示框","请选择一条记录！");
                    return;
                }
                $('#catalog_app_form').form('load',{
                    fid:row.id,
                    projectid:projectid,
                    catalogid:catalogid
                });
                $('#catalog_app_window').window('open');
                $('#catalog_app_window').window('center');
            }
            function openEditCWindow() {
                $('#catalog_app_form').form('clear');
                var row = $('#catalog_datagrid').datagrid('getSelected');
                if(row == null){
                    $.messager.alert("提示框","请选择一条记录！");
                    return;
                }
                $('#catalog_app_form').form('load',{
                    id:row.id,
                    fid:row.parent_id,
                    title:row.text,
                    remark:row.remark
                });
                $('#catalog_app_pid').val(row.prototypeid);
                $('#catalog_app_pname').val(row.prototypename);
                $('#catalog_app_window').window('open');
                $('#catalog_app_window').window('center');
            }
            function applyCatalog() {
                var url = catalogadd;
                if($("#catalog_app_id").val()){
                    url = catalogedit;
                }
                $.ajax({
                    url:basePath + url,
                    data:$("#catalog_app_form").serialize(),
                    success: function (data){
                        if(data.status==0){
                            $('#catalog_app_window').window('close');
                            $('#catalog_datagrid').treegrid('reload');
                            $.messager.alert("提示框","完成操作请求！");
                        }else if(data.status==1){
                            $.messager.alert("错误",data.msg,'warning');
                        }else if(data.status==3){
                            $.messager.alert("警告","无权访问",'warning');
                        }
                    }
                });
            }

            function closeWin() {
                $('#catalog_app_window').window('close');
            }

            function delCatalog(){
                var row = $('#catalog_datagrid').treegrid('getSelected');
                if(row == null){
                    $.messager.alert("提示框","请选择一条记录！");
                    return;
                }
                $.messager.confirm('提示','请确认：是否删除此记录?',function(r) {
                    if (r) {
                        $.ajax({
                            url:basePath + catalogdel,
                            data:{id:row.id},
                            success: function (data){
                                if(data.status==0){
                                    $('#catalog_datagrid').treegrid('reload');
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
            function openPSelector(){
                var property={
                    title:"原型列表",
                    dataUrl:prototypeListURL + "?status=1&catalogid=" + catalogid,
                    singleSelect:true,//单选/多选
                    searchPrompt:"请输入关键字",//搜索提示
                    columns:[[
                        {field:'title',title:'标题',align:"center",width:200},
                        {field:'username',title:'上传人',align:"center",width:100},
                        {field:'createtime',title:'上传时间',align:"center",width:150,formatter:formatterTime},
                    ]],
                    searchCall:function(dg,value){//搜索值回调
                        dg.datagrid('reload',{searchkey:value});
                    },
                    callback:function(data){//选择值回调
                        var ids=[];
                        var names = [];
                        $.each(data, function(index, item){
                            ids.push(item.id);
                            names.push(item.title);
                        });
                        $('#catalog_app_pid').val(ids.join(","));
                        $('#catalog_app_pname').val(names.join(","));
                    }
                };
                $.createSelector($("#p_selector"),property);
            }
        </script>
    </head>

  <body >
      <div id="prototype_layout" style="height: 100%;width: 100%">
          <div data-options="region:'north'" style="height:85px;">
              <div style="padding: 8px 35px 8px 14px;margin: 5px;text-shadow: 0 1px 0 rgba(255,255,255,0.5);background-color: #fcf8e3;border: 1px solid #fbeed5;-webkit-border-radius: 4px;-moz-border-radius: 4px;border-radius: 4px;color: #666;">
                  <a href="/project/center?id=${project.id}">${project.title}(${project.version})</a>>
                  <a href="/project/pcatalog/center?projectid=${project.id}">${catalog.title}</a>>
                  <span id="dq_prototype">当前原型名称及状态</span>
              </div>
              <div style="padding: 5px;">
                  <div style="float: left">
                      <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openPListWindow()">原型列表</a>
                      <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openPLogWindow()">最新改动</a>
                      <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openPCatalogWindow()">菜单设计</a>
                  </div>
                  <div style="float: right">
                      <a id="tag_unlock_button" href="javascript:void(0)" class="easyui-linkbutton" onclick="openTAGlock(true)">解锁标记</a>
                      <a id="tag_lock_button" href="javascript:void(0)" class="easyui-linkbutton" onclick="openTAGlock(false)" style="display: none">锁定标记</a>
                      <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openAddTAGWindow()">添加标记</a>
                  </div>
              </div>
          </div>
          <#if hav_catalogs==1>
              <div data-options="region:'west'" style="width:250px;">
                  <div id="catalog_tree"></div >
              </div>
          </#if>
          <div id="prototype_center"  data-options="region:'center'" style="position: relative;">
              <#--<div  style="max-width: 100%;overflow: auto;margin: auto;position: relative; display: inline-block;">-->
          <#--   <img src="/static/images/123.png" alt=""style="max-width: 100%;overflow: auto;margin: auto;position: absolute;left: 0; right: 0;">
           <img src="/static/images/111.jpg" alt=""style="max-width: 100%">-->
          <#--   <div dataid="1111" name="prototype_tag"
                     class="easyui-draggable prototype_tag" style="top:10;left: 20;"
                     data-options="onDrag:onDrag" onmouseover="onmouseover1(this)" onmouseout="onmouseout1(this)">
                    <div class="prototype_look" ondblclick="openTAGEditWindow(this)"></div>
                </div>
                <div dataid="1111" name="prototype_tag"
                     class="easyui-draggable prototype_tag" style="top:10;left: 20;"
                     data-options="onDrag:onDrag" onmouseover="onmouseover2(this)" onmouseout="onmouseout2(this)" >
                    <div  class="prototype_dw" ondblclick="GOTOPrototype(this)"></div>
                    <div  class="prototype_edit" ondblclick="openTAGEditWindow2(this)"></div>
                </div>-->
              <#--</div>-->
          </div>
      </div>

      <div id="catalog_window" class="easyui-window" title="菜单树"
           data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
           style="width:1000px;height:630px;">
          <table id="catalog_datagrid" style="height:565px;"></table>
          <div id="catalog_tool" style="padding:0px;height:auto;overflow: hidden;">
              <div class="sgtz_atn">
                  <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openAddCTJWindow()">新增同级</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openAddCZJWindow()">新增子级</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openEditCWindow()">修改</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" onclick="delCatalog()">删除</a>
              </div>
          </div>
      </div>
      <div id="catalog_app_window" class="easyui-window" title="菜单编辑"
           data-options="modal:true,closable:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
           style="width:550px;height:420px;padding:10px;">
          <form id="catalog_app_form" action="" method="post">
              <input id="catalog_app_id"  type="hidden"  name="id">
              <input type="hidden"  name="projectid">
              <input type="hidden"  name="catalogid">
              <input type="hidden"  name="fid">
              <ul class="fm_s" style="overflow: inherit;">
                  <li class="fm_1l">
                      <label>菜单名称：</label>
                      <input name="title" class="easyui-validatebox textbox vipt " data-options="required:true">
                  </li>
                  <li class="fm_1l">
                      <label>链接原型：</label>
                      <input id="catalog_app_pname" class="easyui-validatebox textbox vipt " data-options="required:true" readonly="readonly" onclick="openPSelector()">
                      <input id="catalog_app_pid" name="prototypeid" type="hidden">
                  </li>
                  <li class="fm_1l">
                      <label>说明：</label>
                      <input name="remark" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:130px">
                  </li>
              </ul>
          </form>
          <div style="text-align:center;padding:5px">
              <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px" onclick="applyCatalog()">提交</a>
              <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeWin()">关闭</a>
          </div>
      </div>
      <div id="p_selector"></div>

      <script type="text/javascript">
          function doSearch(){
              var searchkey = $("#sk_searchkey").val();
              $("#prototype_datagrid").datagrid("reload",{searchkey:searchkey});
          }
          function openAddPrototypeWindow(){
              $('#prototype_add_window').window('open');
              $('#prototype_add_window').window('center');
              $("#prototype_add_form").form("clear");
              $('#prototype_add_projectid').val(projectid);
              $('#prototype_add_catalogid').val(catalogid);
          }
          function addPrototype(){
              $.ajax({
                  url:basePath+prototypeAddURL,
                  data:$("#prototype_add_form").serialize(),
                  success: function (data){
                      if(data.status==0){
                          $('#prototype_datagrid').datagrid('reload');
                          $('#prototype_add_window').window('close');
                          $.messager.alert("提示框","完成操作请求！");
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }
              });
          }
          function closeAddPrototypeWindow(){
              $('#prototype_add_window').window('close');
          }
          function editPrototypeTitle() {
              var row = $('#prototype_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $.messager.prompt('新的标题', '请输入新的原型标题！', function(title){
                  if (title){
                      $.ajax({
                          url:basePath+prototypeEditTitleURL,
                          data:{id:row.id,title:title},
                          success: function (data){
                              if(data.status==0){
                                  $('#prototype_datagrid').datagrid('reload');
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
          function editPrototypeDis() {
              var row = $('#prototype_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $.messager.confirm('提示', '确认要把原型标记为废弃吗？', function(r){
                  if (r){
                      $.messager.prompt('输入', '请输入废弃理由！', function(deletecauses){
                          if (deletecauses){
                              $.ajax({
                                  url:basePath+prototypeEditDisURL,
                                  data:{id:row.id,deletecauses:deletecauses},
                                  success: function (data){
                                      if(data.status==0){
                                          $('#prototype_datagrid').datagrid('reload');
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
              });
          }
          function openEditPrototypeWindow() {
              var row = $('#prototype_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $('#prototype_edit_window').window('open');
              $('#prototype_edit_window').window('center');
              $("#prototype_edit_form").form("clear");
              $('#prototype_edit_id').val(row.id);
              $('#prototype_edit_title').val(row.title);
              $("#prototype_edit_imports").prop("checked","checked");
          }
          function editPrototype() {
              $.ajax({
                  url:basePath+prototypeEditImgURL,
                  data:$("#prototype_edit_form").serialize(),
                  success: function (data){
                      if(data.status==0){
                          $('#prototype_datagrid').datagrid('reload');
                          $('#prototype_edit_window').window('close');
                          $.messager.alert("提示框","完成操作请求！");
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }
              });
          }
          function closeEditPrototypeWindow(){
              $('#prototype_edit_window').window('close');
          }

          function editPrototypeDefault() {
              var row = $('#prototype_datagrid').datagrid('getSelected');
              if(isEmpty(row)){
                  $.messager.alert("提示框","请选择一条数据记录！");
                  return;
              }
              $.messager.confirm('提示', '确认要把原型设置为默认显示页吗？', function(r){
                  if (r){
                      $.ajax({
                          url:basePath+prototypeEditDefaultURL,
                          data:{id:row.id},
                          success: function (data){
                              if(data.status==0){
                                  $('#prototype_datagrid').datagrid('reload');
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

      </script>
      <div id="prototype_window" class="easyui-window" title="原型列表"
           data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
           style="width:1000px;height:630px;">
          <table id="prototype_datagrid" style="height:565px;"></table>
          <div id="prototype_tool" style="padding:0px;height:auto;overflow: hidden;">
              <div class="sgtz_atn">
                  <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openAddPrototypeWindow()">新增</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editPrototypeTitle()">修改标题</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editPrototypeDefault()">设置为默认显示页</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openEditPrototypeWindow()">迭代</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editPrototypeDis()">废弃</a>
                  <a href="javascript:void(0)" class="easyui-linkbutton" onclick="openPrototypeHisWindow()">查看历史</a>
              </div>
              <div class="sgtz_atn">
                  <span style="font-weight: bold;">搜索筛选：</span>
                  <input id="sk_searchkey" class="easyui-searchbox" data-options="prompt:'搜索关键字（标题）',searcher:doSearch" style="width:300px"></input>
              </div>
          </div>
      </div>

      <div id="prototype_his_window" class="easyui-window" title="历史原型列表"
           data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
           style="width:1000px;height:630px;">
          <table id="prototype_his_datagrid" style="height:565px;"></table>
          <div id="prototype_his_tool" style="padding:0px;height:auto;overflow: hidden;">
              <div class="sgtz_atn">
                  <a href="javascript:void(0)" class="easyui-linkbutton" style="width:120px"  onclick="lookHis()">查看</a>
              </div>
          </div>
      </div>

      <div id="prototype_add_window" class="easyui-window" title="新增原型"
           data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
           style="width:400px;height:250px;">
          <input id="prototype_add_uploadify" name="file" type="file" style="display: none"/>
          <form id="prototype_add_form">
              <input id="prototype_add_projectid" name="projectid" type="hidden">
              <input id="prototype_add_catalogid" name="catalogid" type="hidden">
              <ul class="fm_s" style="overflow: inherit;">
                  <li class="fm_1l">
                      <label>原型标题：</label>
                      <input id="prototype_add_title" name="title" class="easyui-validatebox textbox vipt" data-options="required:true">
                  </li>
                  <li class="fm_1l">
                      <label>原型图：</label>
                      <input id="prototype_add_upload" class="easyui-validatebox textbox vipt" disabled="disabled">
                      <input id="prototype_add_img" name="img" type="hidden" >
                      <a href="javascript:void(0)" class="easyui-linkbutton" onclick="img_load('add')">上传原型图</a>
                  </li>
                  <li class="fm_1l" style="text-align: center;padding-top: 20px">
                      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addPrototype()">确认</a>
                      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeAddPrototypeWindow()">关闭</a>
                  </li>
              </ul>
          </form>
      </div>

      <div id="prototype_edit_window" class="easyui-window" title="迭代原型"
           data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
           style="width:500px;height:350px;">
          <input id="prototype_edit_uploadify" name="file" type="file" style="display: none"/>
          <form id="prototype_edit_form">
              <input id="prototype_edit_id" name="id" type="hidden">
              <ul class="fm_s" style="overflow: inherit;">
                  <li class="fm_1l">
                      <label>原型标题：</label>
                      <input id="prototype_edit_title" class="easyui-validatebox textbox vipt" disabled="disabled">
                  </li>
                  <li class="fm_1l">
                      <label>原型图：</label>
                      <input id="prototype_edit_upload" class="easyui-validatebox textbox vipt" disabled="disabled">
                      <input id="prototype_edit_img" name="img" type="hidden" >
                      <a href="javascript:void(0)" class="easyui-linkbutton" onclick="img_load('edit')">上传原型图</a>
                  </li>
                  <li class="fm_1l">
                      <label>引入：</label>
                      <input id="prototype_edit_imports" type="checkbox" name="imports" checked="checked">
                      <span style="color: red">不勾选则不会引入前一个版本的原型标记</span>
                  </li>
                  <li class="fm_1l">
                      <label>变更说明：</label>
                      <input name="iterationcauses" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:60px">
                  </li>
                  <li class="fm_1l" style="text-align: center;padding-top: 20px">
                      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="editPrototype()">确认</a>
                      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeEditPrototypeWindow()">关闭</a>
                  </li>
              </ul>
          </form>
      </div>

      <!-- //////////////////////////////////////////////////////////////////////// -->
      <script type="text/javascript">
          function openAddTAGWindow() {
              if(isEmpty(_p_id)){
                  $.messager.alert("提示框","请选择一个原型界面！");
                  return;
              }
              $('#tag_add_window').window('open');
              $('#tag_add_window').window('center');
              $("#tag_add_form").form("clear");
              $("#tag_add_prototypeid").val(_p_id);
              $("#tag_add_mapx").val(0);
              $("#tag_add_mapy").val(0);
              $("#tag_add_contentname_li").hide();
              $("#tag_add_content_li").hide();
              $("#tag_add_code_li").hide();
          }
          function addTAG(){
              $.ajax({
                  url:basePath+prototypetagAddURL,
                  data:$("#tag_add_form").serialize(),
                  success: function (data){
                      if(data.status==0){
                          initPROCenter(_p_id,_p_img,true);
                          closeAddTAGWindow();
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }
              });
          }
          function closeAddTAGWindow(){
              $('#tag_add_window').window('close');
          }
          function loadTAGDetail(id){
              $.ajax({
                  url: basePath + prototypetagDetailURL,
                  data: {id: id},
                  success: function (data) {
                      $("#tag_edit_id").val(data.data.id);
                      $("#tag_edit_code").val(data.data.code);
                      $("#tag_edit_content").textbox("setValue",data.data.content);
                      $("#tag_edit_iterationcauses").textbox("setValue",data.data.iterationcauses);
                      if(data.data.groupstatus==1){
                          $("#tag_edit_button").show();
                      }else{
                          $("#tag_edit_button").hide();
                      }
                  }
              });
          }
          function openTAGEditWindow(id){
              $("#tag_edit_form").form("clear");
              $.ajax({
                  url: basePath + prototypetagDetailURL,
                  data: {id: id},
                  success: function (data) {
                      $("#tag_edit_id").val(data.data.id);
                      $("#tag_edit_code").val(data.data.code);
                      $("#tag_edit_content").textbox("setValue",data.data.content);
                      $("#tag_edit_iterationcauses").textbox("setValue",data.data.iterationcauses);
                      $("#tag_edit_button").show();
                      $('#tag_datalist').datalist({
                          url:basePath + prototypetagHisListURL + "?id=" + data.data.groupid
                      });
                  }
              });
              $('#tag_edit_window').window('open');
              $('#tag_edit_window').window('center');
          }
          function editTAG(){
              $.ajax({
                  url:basePath+prototypetagIterationURL,
                  data:$("#tag_edit_form").serialize(),
                  success: function (data){
                      if(data.status==0){
                          closeEditTAGWindow();
                      }else if(data.status==1){
                          $.messager.alert("错误",data.msg,'warning');
                      }else if(data.status==3){
                          $.messager.alert("警告","无权访问",'warning');
                      }
                  }
              });
          }

          function closeEditTAGWindow(){
              $('#tag_edit_window').window('close');
          }

          function classesOnSelect(rec) {

              if(rec.value==1){
                  $("#tag_add_contentname_li").hide();
                  $("#tag_add_content_li").show();
                  $("#tag_add_code_li").show();
              }else{
                  $("#tag_add_contentname_li").show();
                  $("#tag_add_content_li").hide();
                  $("#tag_add_code_li").hide();
              }
          }

          function openTAGTarget() {
              var property={
                  title:"原型列表",
                  dataUrl:prototypeListURL + "?status=1&catalogid=" + catalogid,//数据连接
                  singleSelect:true,//单选/多选
                  searchPrompt:"请输入关键字",//搜索提示
                  columns:[[
                      {field:'title',title:'标题',align:"center",width:200},
                      {field:'createtime',title:'上传时间',align:"center",width:150,formatter:formatterTime},
                      {field:'username',title:'上传人',align:"center",width:100}
                  ]],
                  searchCall:function(dg,value){//搜索值回调
                      dg.datagrid('reload',{searchkey:value});
                  },
                  callback:function(data){//选择值回调
                      $.each(data, function(index, item){
                          $("#tag_add_contentname").val(item.title);
                          $("#tag_add_content").textbox("setValue",item.id);
                      });
                  }
              };
              $.createSelector($("#tag_prototype_selector"),property);
          }
          function openTAGEditWindow2(id){
              var property={
                  title:"请重新选择原型",
                  dataUrl:prototypeListURL + "?status=1&catalogid=" + catalogid,//数据连接
                  singleSelect:true,//单选/多选
                  searchPrompt:"请输入关键字",//搜索提示
                  columns:[[
                      {field:'title',title:'标题',align:"center",width:200},
                      {field:'createtime',title:'上传时间',align:"center",width:150,formatter:formatterTime},
                      {field:'username',title:'上传人',align:"center",width:100}
                  ]],
                  searchCall:function(dg,value){//搜索值回调
                      dg.datagrid('reload',{searchkey:value});
                  },
                  callback:function(data){//选择值回调
                      $.each(data, function(index, item){
                          $.ajax({
                              url:basePath+prototypetagIterationURL,
                              data:{id:id,content:item.id},
                              success: function (data){
                                  if(data.status==0){
                                      initPROCenter(_p_id,_p_img,false);
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
              $.createSelector($("#tag_prototype_selector"),property);
          }
      </script>
      <div id="tag_prototype_selector"></div>

      <div id="tag_add_window" class="easyui-window" title="新增标记"
           data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
           style="width:600px;height:450px;">
          <form id="tag_add_form">
              <input id="tag_add_prototypeid" name="prototypeid" type="hidden">
              <input id="tag_add_mapx" name="mapx" type="hidden">
              <input id="tag_add_mapy" name="mapy" type="hidden">
              <ul class="fm_s" style="overflow: inherit;">
                  <li class="fm_1l">
                      <label>标记类型：</label>
                      <select name="classes" class="easyui-combobox vipt" data-options="onSelect:classesOnSelect">
                          <option value="1">注解标记</option>
                          <option value="2">跳转标记</option>
                      </select>
                  </li>
                  <li id="tag_add_code_li" class="fm_1l">
                      <label>编号：</label>
                      <input id="tag_add_code" name="code" class="easyui-validatebox textbox vipt" >
                  </li>
                  <li id="tag_add_contentname_li" class="fm_1l">
                      <label>跳转目标：</label>
                      <input id="tag_add_contentname" class="easyui-validatebox textbox vipt" readonly="readonly" onclick="openTAGTarget()">
                  </li>
                  <li id="tag_add_content_li" class="fm_1l">
                      <label>内容：</label>
                      <input id="tag_add_content" name="content" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:250px">
                  </li>
                  <li class="fm_1l" style="text-align: center;padding-top: 20px">
                      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="addTAG()">确认</a>
                      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  style="width:100px"  onclick="closeAddTAGWindow()">关闭</a>
                  </li>
              </ul>
          </form>
      </div>

      <div id="tag_edit_window" class="easyui-window" title="标记编辑"
           data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
           style="width:850px;height:600px;">
          <div id="tag_layout" style="height: 100%;width: 100%">
              <div data-options="region:'west'" style="width:250px;">
                  <div id="tag_datalist"></div >
              </div>
              <div id="prototype_center" data-options="region:'center'" style="position: relative">
                  <form id="tag_edit_form">
                      <input id="tag_edit_id" name="id" type="hidden">
                      <ul class="fm_s" style="overflow: inherit;">
                          <li class="fm_1l">
                              <label>编号：</label>
                              <input id="tag_edit_code" name="code" class="easyui-validatebox textbox vipt" disabled="disabled">
                          </li>
                          <li class="fm_1l">
                              <label>内容：</label>
                              <input id="tag_edit_content" name="content" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:250px">
                          </li>
                          <li class="fm_1l">
                              <label>迭代说明：</label>
                              <input id="tag_edit_iterationcauses" name="iterationcauses" class="easyui-textbox"  multiline="true" labelPosition="top" style="width:70%;height:100px">
                          </li>
                          <li class="fm_1l">
                              <label> </label>
                              <span style="color: red">请在更新时，重写迭代说明！！！！</span>
                          </li>
                          <li class="fm_1l" style="text-align: center;padding-top: 20px">
                              <a id="tag_edit_button" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  style="width:100px"  onclick="editTAG()">确认更新</a>
                          </li>
                      </ul>
                  </form>
              </div>
          </div>
      </div>
  
        <script type="">
            function openPLogWindow() {
                $("#prototype_log_datagrid").datagrid({
                    url:basePath + prototypeLogListURL + "?catalogid=" + catalogid,
                    border:false,
                    striped:true,
                    fit:true,
                    pageSize:20,
                    pageList:[20,50],
                    idField:'id',
                    loadMsg:'加载中……',
                    rownumbers:true,//序号
                    pagination:true,//显示底部分页工具栏
                    singleSelect:true,//单选
                    columns:[[
                        {field:'prototypetitle',title:'变更原型',align:"center",width:200},
                        {field:'username',title:'变更人',align:"center",width:100},
                        {field:'createtime',title:'变更时间',align:"center",width:150,formatter:formatterTime},
                        {field:'remark',title:'变更明细',align:"center",width:550}
                    ]],
                    toolbar:'#prototype_log_tool'
                });
                $('#prototype_log_window').window('open');
                $('#prototype_log_window').window('center');
            }
            
            function GOTOPrototypeWin() {
                var row = $('#prototype_log_datagrid').datagrid('getSelected');
                if(isEmpty(row)){
                    $.messager.alert("提示框","请选择一条数据记录！");
                    return;
                }
                GOTOPrototype(row.prototypeid);
                $("#prototype_log_window").window("close");
            }
        </script>
      <div id="prototype_log_window" class="easyui-window" title="原型变更列表"
           data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
           style="width:1000px;height:630px;">
          <table id="prototype_log_datagrid" style="height:565px;"></table>
          <div id="prototype_log_tool" style="padding:0px;height:auto;overflow: hidden;">
              <div class="sgtz_atn">
                  <a href="javascript:void(0)" class="easyui-linkbutton" onclick="GOTOPrototypeWin()">跳转查看</a>
              </div>
          </div>
      </div>
  </body>
</html>

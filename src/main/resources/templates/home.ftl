<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <#include  "common/shareJs.ftl">
    <!-- 窗口大小重置控制JS -->
    <script type="text/javascript" src="/static/script/jquery.resizeEnd.min.js"></script>
    <script type="text/javascript" src="/static/script/waterfall.js"></script>
    <script type="text/javascript" src="/static/js/environmental.js"></script>
    <style type="text/css">
        #header { display:block; overflow:hidden; height:auto; z-index:30}
        #header .headerNav { height:50px; background-repeat:no-repeat; background-position:100% -50px;}
        #header .logo { float:left; width:200px; height:58px;}
        #header .nav { display:block; height:21px; position:absolute; top:20px; right:0; z-index:31;margin-right: 20px;}
        #header .nav li { float:left; margin-left:-1px; padding:0 10px; line-height:11px; position:relative;}
        #header .nav li a {padding:6px 15px; width:100px;  height:40px; color: #fff; border-radius: 3px; background:#ff9933;text-decoration: none;}
        #header .nav li a:hover{ color: #fff; background: #e89848;}
        #header .nav ul { display:none; width:230px; border:solid 1px #06223e; overflow:hidden; background:#999; position:absolute; top:20px; right:0;}
        #header .nav ul li { margin-top:10px; height:21px;}
        #header .nav ul li a { color:#000;}
        #header .nav .selected ul {display:block;}
        .tree-icon {width: 0px;}
    </style>
</head>
<script type="text/javascript">
    var userMenuList = "/employeeMenus";
    function openSocketWin(){
        $('#socket_window').window('open');
        $('#socket_window').window('center');
    }
</script>
<script type="text/javascript" src="/static/js/index.js"></script>
<!-- 主框架最小尺寸1280*768。内框架最小尺寸1024*630 -->
<body class="easyui-layout"  style="width:100%;height:100%;margin: 0 auto;min-height: 768px;min-width:1280px;">
<#include "socket/socket.ftl">
<div data-options="region:'north',split:false" style="height:60px;">
    <div id="header">
        <div class="headerNav">
            <a class="logo" href="javascript:void(0);">
                <!-- LOGO：布局参数请自定义 -->
               <#-- <img style="margin:5 20;max-width: 160px;" alt="LOGO" src="">-->
            </a>
            <div style="float:left;height: 100%;vertical-align: middle;">
                <p style="line-height:1px;font-size:16px;color: #FF8C69;font-family:'微软雅黑'"></p>
                <p style="color:#999;"></p>
            </div>
            <ul class="nav">
                <li><a href="javascript:void(0)" onclick="openSocketWin()">消息</a></li>
                <li><a href="javascript:void(0)" onclick="openEditWin()">修改密码</a></li>
                <li><a href="/out" >安全退出</a></li>
            </ul>
        </div>
    </div>
</div>

<div data-options="region:'west'" style="width:200px;">
    <div style="width:100%;height:30px;background-color: #e8e8e8;text-align: center;line-height: 30px;">导航菜单</div>
    <div id="ac" class="easyui-accordion" data-options="border:false">
        <div title="因技术问题导致的权宜之计" data-options="" style="overflow:auto;padding:10px;"></div>
        <#list topMenu as item>
            <div title="${item.name}" style="overflow:auto;padding:10px;">
                <input id="menuId_${item_index}" type="hidden" value="${item.code}" >
                <ul id="user_menu_${item_index}"></ul>
            </div>
        </#list>
    </div>
</div>
<div data-options="region:'south'" style="height:40px;padding:10px; text-align:center;">
    Copyright©；All Rights Reserved
</div>
<div data-options="region:'center'" style="background-color: #e8e8e8">
    <div id="home_tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
        <div title="首页">
            <div id="home_context" class="homeBox" style="width: 100%;">
                <#include  "include/message.ftl">
            </div>
        </div>
    </div>
</div>

<!-- Tabs 右键菜单目录 -->
<div id="menu" class="easyui-menu" style="width:150px;">
    <div class="menu-sep"></div>
    <div id="m-closeall">全部关闭</div>
    <div id="m-closeother">除此之外全部关闭</div>
    <div class="menu-sep"></div>
    <div id="m-close">关闭</div>
</div>

<div id="edit_window" class="easyui-window" title="修改密码"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closable:false,closed:true"
     style="width:400px;height:280px;padding:10px;">
    <div style="padding:5px 20px 5px 20px">
        <form id="edit_form">
            <ul class="fm_s">
                <li>
                    <label>旧密码：</label>
                    <input name="oldkey" class="easyui-validatebox textbox vipt" type="password">
                </li>
                <li>
                    <label>新密码：</label>
                    <input name="newkey" class="easyui-validatebox textbox vipt" type="password">
                </li>
                <li>
                    <label>确认密码：</label>
                    <input name="rekey"  class="easyui-validatebox textbox vipt" type="password">
                </li>
            </ul>
            <div class="sgtz_center">
                <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px"  onclick="applyEdit()">确认</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px"  onclick="closeEditWin()">取消</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>

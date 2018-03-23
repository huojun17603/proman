<div class="homeDivBox">
  <div class="homeDivBorder">
      <div class="homeDivHead">
          <div>
              <span class="htl">我的未读消息(<span id="unreadnum_span"></span>)</span>
              <a onclick="doRest_UnreadList()" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" style="float: right;margin-top: 8px;" ></a>
          </div>
      </div>
      <div style="border-radius: 0 0 2px 2px;border-bottom: 1px solid #b3b3b3;overflow: auto; ">
          <div name="dataMsg" style="height:500px">
              <table id="message_datagrid"></table>
          </div>
      </div>
  </div>
</div>
<script type="text/javascript">
    $(function(){
        $("#message_datagrid").datagrid({
            url:basePath + UnreadListURL,
            border:false,
            striped:true,
            fit:true,
            idField:'id',
            loadMsg:'加载中……',
            rownumbers:true,//序号
            singleSelect:true,//单选
            columns:[[
                {field:'createtime',title:'注册时间',align:"center",width:150,formatter:formatterTime},
                //{field:'source',title:'来源',align:"center",width:200,formatter:initSource},
                {field:'content',title:'内容',width:1000},
                {field:'status',title:'操作',align:"center",width:80,
                    formatter:function(value,row,index){
                       return "<button style=\"color:red\" onclick=\"confirmMessage('"+row.id+"')\">确认</button>";
                    }
                }
            ]],
            toolbar:'#tool',
            onLoadSuccess:function(data){
                $("#datagrid").datagrid('scrollTo',0);
                $("#unreadnum_span").text(data.total);
            }
        });
        window.setInterval(doRest_UnreadList,15000);
    });
    var UnreadListURL = "message/unreadlist";
    var confirmMessageURL = "message/confirm";
    function confirmMessage(id){
        $.ajax({
            url:basePath + confirmMessageURL,
            data:{id:id},
            success: function (data){
                if(data.status==0){
                    doRest_UnreadList();
                    $.messager.alert("提示框","完成操作请求！");
                }else if(data.status==1){
                    $.messager.alert("错误",data.msg,'warning');
                }else if(data.status==3){
                    $.messager.alert("警告","无权访问",'warning');
                }
            }
        });
    }
    function doRest_UnreadList(){
        $("#message_datagrid").datagrid("reload",{});
    }
</script>

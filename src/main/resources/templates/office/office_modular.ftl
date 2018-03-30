<script type="text/javascript">

    function openOfficeWindow(href){
        $("#office_window").window({
            href:href
        });
        $('#office_window').window('open');
        $('#office_window').window('center');

    }

</script>
<div id="office_window" class="easyui-window" title="预览"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:1000px;height:630px;">
</div>

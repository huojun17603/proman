function openDetailBUGWindow() {
    var row = $('#bug_datagrid').datagrid('getSelected');
    if(isEmpty(row)){
        $.messager.alert("提示框","请选择一条数据记录！");
        return;
    }
    $('#bug_detail_window').window('open');
    $('#bug_detail_window').window('center');
    $('#bug_detail_title').val(row.title);
    $('#bug_detail_username').val(row.username);
    $('#bug_detail_repairname').val(row.repairname);
    $('#bug_detail_process').textbox("setValue",row.process);
    $('#bug_detail_results').textbox("setValue",row.results);
    $('#bug_detail_expect').textbox("setValue",row.expect);
    $('#bug_detail_repairremark').textbox("setValue",row.repairremark);
}
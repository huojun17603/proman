<script type="text/javascript">
    var websocket = null;

    //判断当前浏览器是否支持WebSocket
    if('WebSocket' in window){
        websocket = new WebSocket("ws://127.0.0.1:8080/websocket");
    }
    else{
        alert('Dont support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function(){
        setMessageInnerHTML("error");
    };

    //连接成功建立的回调方法
    websocket.onopen = function(event){
        setMessageInnerHTML("open");
    }

    //接收到消息的回调方法
    websocket.onmessage = function(event){
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function(){
        setMessageInnerHTML("close");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function(){
        websocket.close();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML){
        document.getElementById('socket_message').innerHTML += innerHTML + '<br/>';
    }

    //关闭连接
    function closeWebSocket(){
        websocket.close();
    }

    //发送消息
    function send(){
        var message = document.getElementById('socket_input').value;
        websocket.send(message);
    }
</script>
<div id="socket_window" class="easyui-window"
     data-options="modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"
     style="width:1000px;height:630px;">
    <div id="socket_layout" style="height: 100%;width: 100%">
        <div data-options="region:'west'" style="width:150px;">
            <div id="socket_datalist"></div >
        </div>
        <div data-options="region:'center'">
            <div id="socket_message"></div>
            <div>
                <input id="socket_input">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="send()">发送</a>
            </div>
        </div>
        <div  data-options="region:'east'" style="width:150px;">

        </div>
    </div>
</div>


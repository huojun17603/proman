<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 仅仅是简单满足当前需要的高德地图代码 -->
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.4.2&key=1da8b7c43ce15b7b8514778ce581ec4a&plugin=AMap.Autocomplete"></script>
<script>
var lbs_map;
var lbs_geocoder;
var lbs_marker;
$(function(){
	lbs_map = new AMap.Map('container', {
	    resizeEnable: true,
	    zoom:15
	});
	
	AMap.service('AMap.Geocoder',function(){//回调函数
	    //实例化Geocoder
	    lbs_geocoder = new AMap.Geocoder({});
	    //TODO: 使用geocoder 对象完成相关功能
	});
	lbs_map.on('click', function(e) {
		lbs_map_move(e.lnglat);
	});
	
	var auto = new AMap.Autocomplete({
        input:"lbs_map_sreach_input"
    });
	AMap.event.addListener(auto, "select", lbs_map_select);//注册监听，当选中某条记录时会触发
    function lbs_map_select(e) {
        if (e.poi && e.poi.location) {
        	lbs_map.setZoom(15);
        	lbs_map.setCenter(e.poi.location);
        }
    }
});

/** 根据区域文字查询，并执行定位操作 */
function lbs_map_address_search(address){
	lbs_geocoder.getLocation(address, function(status, result) {
	    if (status === 'complete' && result.info === 'OK') {
	        //TODO:获得了有效经纬度，可以做一些展示工作
	        //比如在获得的经纬度上打上一个Marker
	    	lbs_map_move(result.geocodes[0].location);
	    }else{
	        alert("未查询到对应区域信息！");
	    }
	}); 
}

/** 根据坐标移动地图并标记定位 */
function lbs_map_move(lnglatXY){
	lbs_map.setCenter(lnglatXY);
	var zoom = lbs_map.getZoom();
	if(zoom<15)lbs_map.setZoom(15);
	if(isEmpty(lbs_marker)){  // 实例化点标记
		lbs_marker = new AMap.Marker({
	        icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
	        position: lnglatXY
	    });
		lbs_marker.setMap(lbs_map);
	}else{
		lbs_marker.setPosition(lnglatXY); //更新点标记位置
	}
}
/** 返回标记对象 */
function get_lbs_marker(){
	return lbs_marker;
}
</script>


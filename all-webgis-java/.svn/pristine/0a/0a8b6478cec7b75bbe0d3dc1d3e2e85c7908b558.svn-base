<!DOCTYPE HTML><html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
  <head>
    <title>关联地图数据</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<%@ include file="/static/global.jsp"%>
	<base href="${ctx}/">
	<link rel="stylesheet" type="text/css" href="${ctx}/qtmap/style.css" />
	<script type="text/javascript" src="${ctx}/static/js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/qtmap/qtmap.min.js"></script>
	<style type="text/css">
	 html,body{
	    margin: 0;
	    padding: 0;
    }
	.map{
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0
	}
	</style>
	<script type="text/javascript">
	var contentHTML="<div class=\"pop\">"+
    "<div class=\"pop-item\"><div style=\"float:left;height:100%;width:50px;\"><a class=\"pop-info-title\">名称</a></div><div style=\"float:left;\"><a class=\"pop-info-content\">{name}</a></div><div style=\"clear:both;\"></div></div>"+
    "<div class=\"pop-item\"><div style=\"float:left;height:100%;width:50px;\"><a class=\"pop-info-title\">描述 </a></div><div style=\"float:left;\"><a class=\"pop-info-content\">{description}</a></div><div style=\"clear:both;\"></div></div>"+
    "<div class=\"pop-item\"><div style=\"float:left;height:100%;width:50px;\"><a class=\"pop-info-title\">时间</a></div><div style=\"float:left;\"><a class=\"pop-info-content\">{createTime}</a></div><div style=\"clear:both;\"></div></div>"+
//     "<div class=\"pop-item\"><div style=\"float:left;height:100%;width:50px;\"><a class=\"pop-info-title\">注释</a></div><div style=\"float:left;\"><a class=\"pop-info-content\">{label}</a></div><div style=\"clear:both;\"></div></div>"+
    "</div>";
	var maps,feature;
	var map,vectorLayer,selectControl,wkt = new OpenLayers.Format.WKT();
	  $(function(){
		  init();
	  });
	  
	  function init(){
		  $.ajax({
			  type : "post",
			  url : "${ctx}/mapedit/relationMapData",
			  data : {mapId:${mapId},featureId:${featureId}},
			  success : function(data){
				  data = eval("("+data+")");
				  if(data.result == "success"){
					  maps = data.maps;
					  feature = data.feature;
					  initMap();
					  initFeature();
				  }else{
					  layer.alert("加载数据失败");
				  }
			  }
		  });
	  }
	  
	  function initFeature(){
			var fea = wkt.read(feature.geom);
			if(fea.geometry.CLASS_NAME == "OpenLayers.Geometry.Polygon"){
			    fea.attributes = {
						stroke_color : feature.strokeColor || "#ccc",
						stroke_opacity : feature.strokeOpacity || 1.0,
						stroke_width : feature.strokeWidth || 1,
						fill_color : feature.fillColor || "#ccc",
						fill_opacity: feature.fillOpacity || 1.0,
						ftype : "POLYGON"
					};
			}else if(fea.geometry.CLASS_NAME == "OpenLayers.Geometry.Point"){
				fea.attributes = {
					color : feature.color || "red",
					scale : feature.radius || 3,
					ftype : "POINT"
					};
			}else if(fea.geometry.CLASS_NAME == "OpenLayers.Geometry.LineString"){
				fea.attributes = {
					stroke_color : feature.strokeColor || "red",
					stroke_opacity : feature.strokeOpacity || 1.0,
					stroke_width : feature.strokeWidth || 1,
					ftype:"LINE"
					};
			}
			feature.feature = fea;
			vectorLayer.addFeatures([fea]);
			selectControl.select(fea);
	  }
	  
	 function initMap(){
		 map = qtmap.basemap.getMap();
		 vectorLayer = new OpenLayers.Layer.Vector("vector");
		 vectorLayer.id = "vector";
		 
		 if(maps.length == 0){
			 maps.push({opacity: 1, basemapType: "彩色注记版"});
		 }
		 
		 for(var i in maps){
			 var obj = maps[i];
				var baseMapType = obj.basemapType;
				var opacity = obj.opacity || 1;
				var isBaseLayer = i == 0;
				if(baseMapType == "彩色版"){
					new qtmap.basemap.colormap(null , null , 12 , isBaseLayer,opacity);
				}else if(baseMapType == "冷色版"){
					new qtmap.basemap.coolmap(null , null , 12 , isBaseLayer,opacity);
				}else if(baseMapType == "天地图影像"){
					new qtmap.basemap.mapWorldImage(null , null , 12 , isBaseLayer,opacity);
				}else if(baseMapType == "灰色版"){
					new qtmap.basemap.graymap(null , null , 12 , isBaseLayer,opacity);
				}else if(baseMapType == "彩色注记版"){
					new qtmap.basemap.notemap(null , null , 12 , isBaseLayer,opacity);
				}else if(baseMapType == "暖色版"){
					new qtmap.basemap.warmmap(null , null , 12 , isBaseLayer,opacity);
				}else if(baseMapType == "天地图"){
					new qtmap.basemap.mapworld(null , null , 12 , isBaseLayer,opacity);
				}
		    }
		 map.addLayer(vectorLayer);
		 
		 selectControl = new OpenLayers.Control.SelectFeature(vectorLayer,{
			   clickout: false, toggle: false,
               multiple: false, hover: false,
               toggleKey: "ctrlKey", // ctrl key removes from selection
               multipleKey: "shiftKey", // shift key adds to selection
               box: false,
               onSelect : function(fea){
            	   createPop();
               },
               onUnselect : function(fea){
        		   if(fea.popup && fea.popup.blocks){
        			   fea.popup.destroy();
        			   fea.popup = null;
       				}
        		   }
		   });
	   map.addControl(selectControl);
	   selectControl.activate();
	 }
	 
	function createPop(){
		onClosePopup();
		var str = contentHTML.replace(/\{name\}/g,feature.gname)
	         .replace(/\{description\}/g,feature.gstyle)
			 .replace(/\{createTime\}/g,feature.gcreatedate);
// 			 .replace(/\{label\}/g,feature.label);
		var geom = feature.feature.geometry;
		var center = geom.getBounds().getCenterLonLat();
		map.setCenter(center);
        
        popUp = new qtmap.popup.CSSFramedCloud("featurePopup",
        		center,new OpenLayers.Size(100,100),str,
        	     null, true, onClosePopup);
        feature.feature.popup = popUp;
       	popUp.feature = feature.feature;
       	map.addPopup(popUp, true);
	}
		
	function onClosePopup(evt){
		var feature = this.feature;
		if(feature != null){
			if(feature.popup && feature.popup.blocks){
				feature.popup.destroy();
				feature.popup = null;
				selectControl.unselect(feature);
			}
		}
		
	}
	
	/**
	 * 时间对象的格式化;
	 */
	Date.prototype.format = function(format) {
	    /*
	     * eg:format="YYYY-MM-dd hh:mm:ss";
	     */
	    var o = {
	        "M+" :this.getMonth() + 1, // month
	        "d+" :this.getDate(), // day
	        "h+" :this.getHours(), // hour
	        "m+" :this.getMinutes(), // minute
	        "s+" :this.getSeconds(), // second
	        "q+" :Math.floor((this.getMonth() + 3) / 3), // quarter
	        "S" :this.getMilliseconds()
	    // millisecond
	    }
	    if (/(y+)/.test(format)) {
	        format = format.replace(RegExp.$1, (this.getFullYear() + "")
	                .substr(4 - RegExp.$1.length));
	    }
	    for ( var k in o) {
	        if (new RegExp("(" + k + ")").test(format)) {
	            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
	                    : ("00" + o[k]).substr(("" + o[k]).length));
	        }
	    }
	    return format;
	}
	</script>
  </head>
  <body>
   <div id="qtmap" class="map"></div>
  </body>
</html>

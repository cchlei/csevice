var map,selectControl;
var vectorLayer,features = [];
var contentHTML="<div class=\"pop\">"+
          "<div class=\"pop-item\"><div style=\"float:left;height:100%;width:50px;\"><a class=\"pop-info-title\">名称</a></div><div style=\"float:left;\"><a class=\"pop-info-content\">{name}</a></div><div style=\"clear:both;\"></div></div>"+
          "<div class=\"pop-item\"><div style=\"float:left;height:100%;width:50px;\"><a class=\"pop-info-title\">描述 </a></div><div style=\"float:left;\"><a class=\"pop-info-content\">{description}</a></div><div style=\"clear:both;\"></div></div>"+
          "<div class=\"pop-item\"><div style=\"float:left;height:100%;width:50px;\"><a class=\"pop-info-title\">添加</a></div><div style=\"float:left;\"><a class=\"pop-info-content\">{createTime}</a></div><div style=\"clear:both;\"></div></div>"+
//          "<div class=\"pop-item\"><div style=\"float:left;height:100%;width:50px;\"><a class=\"pop-info-title\">日期</a></div><div style=\"float:left;\"><a class=\"pop-info-content\">{occurtime}</a></div><div style=\"clear:both;\"></div></div>"+
          "</div>";
var baseMap,featureArray=[],totalNum = 0, mapId;
var popUp,wkt = new OpenLayers.Format.WKT();
var mapName,userName,page=1,rows=3;
$(function() {//初始化方法
	mapId = $("#mapid").val();
	$.ajax({
		type : 'post',
		url : ctx+"/mapedit/getPersonMapBaseMap",
		data : {mapid : mapId},
		success : function(data){
			baseMap = data;
			initMap();
			loadFeatures();
			
		},
		error : function(){
			layer.alert("error");
		}
	});
	
	$.ajax({
		type : 'post',
		url : ctx+"/mapedit/getMapInfo",
		data : {mapid:mapId},
		success : function(data){
			data = eval("("+data+")");
			if(data.result == "success"){
				mapName = data.mapInfo.mapName;
				userName = data.mapInfo.userName;
				desc = data.mapInfo.mapDescribe;
				$("#mapName").text(mapName);
				$("#userName").text(userName);
				$("#map_info").text(desc);
				$(".list-logo").css('background-image','url('+data.mapInfo.headimg+')');
			}
		}
	})
});

function initMap(){
	 map = qtmap.basemap.getMap();
	var baseMaps = baseMap;
	vectorLayer = new OpenLayers.Layer.Vector("vector");
	vectorLayer.id = "vector";
	if(baseMaps.length == 0){
		console.log("初始化底图数据-----默认底图数据:彩色主机版");
		baseMaps.push({opacity: 1, basemapType: "彩色注记版"});
	}
	for(var i in baseMaps){
		var obj = baseMaps[i];
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
               for(var i in featureArray){
            	   var obj = featureArray[i];
            	   if(obj.feature.id == fea.id){
            		   createPop(obj);
       				   break;
            	   }
               }
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

function initFeature(featureData){
	for(var i in featureData.content){
		var  obj = featureData.content[i];
		var fea = wkt.read(obj.geom);
		if(fea.geometry.CLASS_NAME == "OpenLayers.Geometry.Polygon"){
		    fea.attributes = {
				stroke_color : obj.strokeColor || "#ccc",
				stroke_opacity : obj.strokeOpacity || 1.0,
				stroke_width : obj.strokeWidth || 1,
				fill_color : obj.fillColor || "#ccc",
				fill_opacity: obj.fillOpacity || 1.0,
				name : obj.gname || "未命名",
				ftype : "POLYGON"
				};
		}else if(fea.geometry.CLASS_NAME == "OpenLayers.Geometry.Point"){
			fea.attributes = {
				color : obj.color || "#ccc",
				scale : obj.radius || 3,
				//name : obj.gname || "未命名",
				name : "",//点太小，名称会盖住点位置，所以不显示名称
				ftype : "POINT"
				};
			
		}else if(fea.geometry.CLASS_NAME == "OpenLayers.Geometry.LineString"){
 				fea.attributes = {
 					stroke_color : obj.strokeColor || "#ccc",
 					stroke_opacity : obj.strokeOpacity || 1.0,
 					stroke_width : obj.strokeWidth || 1,
 					name : obj.gname || "未命名",
 					ftype:"LINE"
 					};
		}
 			obj.feature = fea;
 			features.push(fea);
	}
	vectorLayer.addFeatures(features);
	vectorLayer.styleMap = new OpenLayers.StyleMap({
				"default":changeStyle()
	});
	vectorLayer.redraw();
	initFeatureList();
}

function loadFeatures(opt){
	var obj = {mapid:mapId,page:page,rows:rows};
	obj = $.extend(obj,opt);
	$.ajax({
		type : 'post',
		url : ctx+"/mapedit/getPersonMapFeature",
		data : obj,
		success  :function(dd){
			featureArray = featureArray.concat(dd.content);
			totalNum = dd.totalElements;
			initFeature(dd);
		},
		error : function(){
			layer.alert("error");
		}
	});
}

function initFeatureList(){
	var list = featureArray;
	$("#features").empty();
	if(!list||list[0]==undefined){
		$("#features").append("<div class=no_data>没有数据</div>");
		return;
	}
	for(var i in list){
		var data = list[i];
		var item
		if(data.feature){
			if(data.feature.geometry instanceof OpenLayers.Geometry.Point){//点
				item = $("<div class=\"list-item\" id=\""+data.id+"\"><div style=\"float:left;height:100%;\"><a class=\"item_poit\"></a></div><div style=\"float:left;padding-left:10px;\"><a class=\"item-name\">"+data.gname+"</a></div><div style=\"clear:both;\"></div></div>");
			}else if(data.feature.geometry instanceof OpenLayers.Geometry.LineString){//线
				item = $("<div class=\"list-item\" id=\""+data.id+"\"><div style=\"float:left;height:100%;\"><a class=\"item_line\"></a></div><div style=\"float:left;padding-left:10px;\"><a class=\"item-name\">"+data.gname+"</a></div><div style=\"clear:both;\"></div></div>");
			}else{//面
				item = $("<div class=\"list-item\" id=\""+data.id+"\"><div style=\"float:left;height:100%;\"><a class=\"item_polygon\"></a></div><div style=\"float:left;padding-left:10px;\"><a class=\"item-name\">"+data.gname+"</a></div><div style=\"clear:both;\"></div></div>");
			}
			item.bind("click",clickItemHandler);
			$("#features").append(item);
		}
	}
	
	if(totalNum > list.length){
		var more = $("<a class='more'>加载更多</a>");
		more.click(function(event){
			page++;
			loadFeatures();
			
		});
		$("#features").append(more);
	}
}

function clickItemHandler(event){
	var id = event.currentTarget.id;
	$("#"+id).addClass("list-item-select").siblings(".list-item").removeClass("list-item-select");
	for(var i in featureArray){
		var data = featureArray[i];
		if(data.id == id){
				selectControl.unselectAll();
				selectControl.select(data.feature);
			break;
		}
	}
}

function createPop(data){
	onClosePopup();
	var str = contentHTML.replace(/\{name\}/g,data.gname)
         .replace(/\{description\}/g,data.gstyle)
		 .replace(/\{createTime\}/g,data.gcreatedate);
//		 .replace(/\{occurtime\}/g,data.occurtime);
	var geom = data.feature.geometry;
	var center = geom.getBounds().getCenterLonLat();
	map.setCenter(center);
    
    popUp = new qtmap.popup.CSSFramedCloud("featurePopup",
    		center,new OpenLayers.Size(100,100),str,
    	     null, true, onClosePopup);
   	data.feature.popup = popUp;
   	popUp.feature = data.feature;
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

function clearSelectItem(){
	$("#features .list-item").removeClass("list-item-select");
}

function changeStyle(){
	var style = new OpenLayers.Style(
	        // the first argument is a base symbolizer
	        // all other symbolizers in rules will extend this one
	        {
	            label: "${name}", // label will be foo attribute value
	            fontColor: "#000000",
	            fontFamily:"微软雅黑",
	            labelAlign: "cm",
	            labelOutlineColor: "white",
	            labelOutlineWidth: 3
	        },
	        // the second argument will include all rules
	        {
	            rules: [
	                new OpenLayers.Rule({
	                    // a rule contains an optional filter
	                    filter: new OpenLayers.Filter.Comparison({
	                    	type: OpenLayers.Filter.Comparison.EQUAL_TO,
	                        property: "ftype", 
	                        value: "POINT"
	                    }),
	                    // if a feature matches the above filter, use this symbolizer
	                    symbolizer: {
	                    	pointRadius: "${scale}",
	                        graphicName: "circle",
	                    	fillColor: "${color}",
	                        fillOpacity: 1,
	                        strokeWidth: 1,
	                        strokeOpacity: 1,
	                        strokeColor: "red"
	                        
//		                    	graphicWidth: 16,
//		     		            graphicHeight: 26,
//		     		            graphicYOffset: -26,
//		                        externalGraphic: "jsp/map/images/map_marker.png"
	                    }
	                }),
	                new OpenLayers.Rule({
	                    filter: new OpenLayers.Filter.Comparison({
	                    	type: OpenLayers.Filter.Comparison.EQUAL_TO,
	                        property: "ftype", 
	                        value: "LINE"
	                    }),
	                    symbolizer: {
	                        strokeWidth: "${stroke_width}",
	                        strokeOpacity: "${stroke_opacity}",
	                        strokeColor: "${stroke_color}"
	                    }
	                }),
	                new OpenLayers.Rule({
	                    filter: new OpenLayers.Filter.Comparison({
	                    	type: OpenLayers.Filter.Comparison.EQUAL_TO,
	                        property: "ftype", 
	                        value: "POLYGON"
	                    }),
	                    symbolizer: {
	                        fillColor: "${fill_color}",
	                        fillOpacity: "${fill_opacity}",
	                        strokeWidth: "${stroke_width}",
	                        strokeOpacity: "${stroke_opacity}",
	                        strokeColor: "${stroke_color}"
	                    }
	                }),
	                new OpenLayers.Rule({
	                    // apply this rule if no others apply
	                    elseFilter: true,
	                    symbolizer: {
	                    	pointRadius: 2,
	                        graphicName: "circle",
	                        fillColor: "black",
	                        fillOpacity: 1,
	                        strokeWidth: 2,
	                        strokeOpacity: 1,
	                        strokeColor: "black"
	                    }
	                })
	            ]
	        }
	    );
	       
	return style;
}


function listChange(){
	selectControl.unselectAll();
	clearSelectItem();
	$(".info-content").toggle("normal").siblings().toggle("normal");
	//$(".list-content .hidden").removeClass("hidden").addClass("show").siblings(".show").removeClass("show").addClass("hidden");	
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


seajs.use("_/bdshare/index",function(BDS){

	var bdhtml =
		'<div id="sharebox">' +
			'<div class="bdsharebuttonbox" data-tag="share_1">' +
				'<span class="tx">分享:</span>'+
				'<a class="bds_tsina" data-cmd="tsina"></a>'+
				'<a class="bds_mshare" data-cmd="mshare"></a>'+
				'<a class="bds_qzone" data-cmd="qzone" href="#"></a>'+
				//'<a class="bds_baidu" data-cmd="baidu"></a>'+
				'<a class="bds_renren" data-cmd="renren"></a>'+
				'<a class="bds_tqq" data-cmd="tqq"></a>'+
				//'<a class="bds_more" data-cmd="more">更多</a>'+
				'<a class="bds_count" data-cmd="count"></a>'+
			'</div>' +
		'</div>'
	;


	var bd = $(bdhtml).appendTo("body");

    BDS("#sharebox");
});
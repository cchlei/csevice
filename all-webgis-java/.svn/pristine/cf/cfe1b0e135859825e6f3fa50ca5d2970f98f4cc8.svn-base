var pageSize = 4 , pageNo=1 , __mapid,__popup;
var totalCount,endPage,temp_index,active=false,selectControlActive=false,dragControlActive=false,modifyControlActive=false;
//基础底图，zoom，center
var __baseMap,__zoom,__lon,__lat;
//点线面的绘制图层
var __drawLayer,__popup,__searchResultLayer;
var __wfsUrl = "http://117.34.70.39/geocloud/wfs?";
var EL_POINT = "point",EL_LINE="line",EL_POLY="poly";

//几类底图
var __colormap , __coolmap, __graymap , __mapworld , __mapWorldImage, __notemap , __warmmap;

var wkt_c = new OpenLayers.Format.WKT();


$(document).ready(function(){
	// 初始化map
	OpenLayers.ProxyHost = "cgi/proxy.cgi?url=";
	//显示地图
	showMap();

	//给切换地图的DIV中的button添加单击事件
	$("#maptypeLst :button").bind("click", function(){
		var mapid = $(this).attr("mapid");
		//地图切换
		toggleMap(mapid , __lon , __lat , __zoom);
	});
	
	//给document绑定键盘事件
	$(document).bind("keyup",keyUp);
	

});

function keyUp(evt){
	//press esc
    if(evt.which == 27){
    	if(__drawLayer != null){
    		active = false;
    		__drawLayer.active=active;
    		__drawLayer.activate();
    	}
    	EC.emit(EV_CANCEL_DRAW);
//    	change_mpel({});
    }
};

function drawFeature(el){
	if(__drawLayer){
		active = false;
		__drawLayer.active=active;
		__drawLayer.activate();
	};
	initDrawlayer(el);
	
}

function changeStyle(){
	var style = new OpenLayers.Style(
	        // the first argument is a base symbolizer
	        // all other symbolizers in rules will extend this one
	        {
//	            label: "${name}" // label will be foo attribute value
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
	                    	cursor: "pointer",
	                    	pointRadius: "${scale}",
	                        graphicName: "circle",
	                    	fillColor: "${color}",
	                        fillOpacity: 1,
	                        strokeWidth: 1,
	                        strokeOpacity: 1,
	                        strokeColor: "red"

//	                    	graphicWidth: 16,
//	     		            graphicHeight: 26,
//	     		            graphicYOffset: -26,
//	                        externalGraphic: "jsp/map/images/map_marker.png"
	                    }
	                }),
	                new OpenLayers.Rule({
	                    filter: new OpenLayers.Filter.Comparison({
	                    	type: OpenLayers.Filter.Comparison.EQUAL_TO,
	                        property: "ftype", 
	                        value: "LINE"
	                    }),
	                    symbolizer: {
	                    	cursor: "pointer",
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
	                    	cursor: "pointer",
	                    	pointRadius: 20,
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
	                    	cursor: "pointer",
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

function changeSelectedStyle(){
	var style = new OpenLayers.Style(
	        // the first argument is a base symbolizer
	        // all other symbolizers in rules will extend this one
//	        {
//	            label: "${name}"
//	        },
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
	                    	cursor: "pointer",
	                    	pointRadius: "${scale}",
	                        graphicName: "circle",
	                    	fillColor: "${color}",
	                        fillOpacity: 0.5,
	                        strokeWidth: 1,
	                        strokeOpacity: 1,
	                        strokeColor: "red"
//	                    	graphicWidth: 16,
//	     		            graphicHeight: 26,
//	     		            graphicYOffset: -26,
//	                        externalGraphic: "jsp/map/images/map_marker.png"
	                    }
	                }),
	                new OpenLayers.Rule({
	                    filter: new OpenLayers.Filter.Comparison({
	                    	type: OpenLayers.Filter.Comparison.EQUAL_TO,
	                        property: "ftype", 
	                        value: "LINE"
	                    }),
	                    symbolizer: {
	                    	cursor: "pointer",
	                        strokeWidth: "${stroke_width}",
	                        strokeOpacity: 0.5,
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
	                    	cursor: "pointer",
	                        fillColor: "${fill_color}",
	                        fillOpacity: "${fill_opacity}",
	                        strokeWidth: "${stroke_width}",
	                        strokeOpacity: 0.5,
	                        strokeColor: "${stroke_color}",
	                        pointRadius: 20,
	                        graphicName: "circle"
	                    }
	                }),
	                new OpenLayers.Rule({
	                    // apply this rule if no others apply
	                    elseFilter: true,
	                    symbolizer: {
	                    	cursor: "pointer",
	                    	pointRadius: 20,
	                        graphicName: "circle",
	                        fillColor: "black",
	                        fillOpacity: 1,
	                        strokeWidth: 20,
	                        strokeOpacity: 1,
	                        strokeColor: "black"
	                    }
	                })
	            ]
	        }
	    );
	       
	return style;
}

/**
 * 初始化绘制图层
 * 并激活拖动和点击Control
 */
function initDrawlayer(el){
	if(el.el_data.type == EL_POINT){
		__drawLayer = new qtmap.draw.point();
	}else if(el.el_data.type == EL_LINE){
		__drawLayer = new qtmap.draw.line();
	}else if(el.el_data.type == EL_POLY){
		__drawLayer = new qtmap.draw.polygon();
	}

	active = !active;

	if(modifyControlActive){
		modifyControlActive = false;
	}else{
		modifyControlActive = true;
	}
	__drawLayer.modifyControlActive = modifyControlActive;
	__drawLayer.modifyControlActivate();
	//当绘制图层的选中控件被激活时，同时会激活Feature的选中和未选中事件。
	
	__drawLayer.active=active;
	__drawLayer.activate();
	//重新赋样式
	__drawLayer.__layer.styleMap = new OpenLayers.StyleMap({
		"default":changeStyle(),
		"select":changeSelectedStyle()
		});
}

/**
 * 地图初始化的时候，绘制feature数据
 */
function initLayerFeature(data){
	var lst = data.list;
	for(var i = 0; i< lst.length;i++){
		var bean = lst[i];
		var feature = wkt_c.read(bean.geom);
		feature.attributes.tmpid = bean.tmpid;
		feature.attributes.fid = bean.type +"_"+ bean.tmpid;
		feature.attributes.name = bean.name;
		feature.attributes.desc = bean.desc;
		feature.fid = bean.type +"_"+ bean.tmpid;
		feature.tmpid = bean.tmpid;
		if(bean.type =="point"){
			__drawLayer = new qtmap.draw.point();
			feature.attributes.color = bean.color;
			feature.attributes.scale = bean.scale;
			feature.attributes.ftype = "POINT";
		}else if(bean.type =="line"){
			__drawLayer = new qtmap.draw.line();
			feature.attributes.stroke_color = bean.stroke_color;
			feature.attributes.stroke_opacity = bean.stroke_opacity;
			feature.attributes.stroke_width = bean.stroke_width;
			feature.attributes.ftype = "LINE";
		}else if(bean.type =="poly"){
			__drawLayer = new qtmap.draw.polygon();
			feature.attributes.stroke_color = bean.stroke_color;
			feature.attributes.stroke_opacity = bean.stroke_opacity;
			feature.attributes.stroke_width = bean.stroke_width;
			feature.attributes.fill_color = bean.fill_color;
			feature.attributes.fill_opacity = bean.fill_opacity;
			feature.attributes.ftype = "POLYGON";
		}
		__drawLayer.__layer.addFeatures(feature);
	}
	__drawLayer.modifyControlActive = true;
	__drawLayer.modifyControlActivate();
	//当绘制图层的选中控件被激活时，同时会激活Feature的选中和未选中事件。
//	__drawLayer.active=true;
//	__drawLayer.activate();
	//重新赋样式
	__drawLayer.__layer.styleMap = new OpenLayers.StyleMap({
		"default":changeStyle(),
		"select":changeSelectedStyle()
	});
//	__drawLayer.__layer.redraw();
}

//todo:地图元素修改完成
function afterfeaturemodified_event(evt){
	feature = evt.feature;

	EC.emit(EV_EDIT_DRAW_COMPLETE,{
		geom:wkt_c.write(evt.feature)
	})
	
//	__drawLayer.modifyControlActive = modifyControlActive;
//	__drawLayer.modifyControlActivate();
	onClosePopup(evt);
}

function beforefeaturemodified_event(evt){
//	onClosePopup(evt);
	feature = evt.feature;
	var geom = feature.geometry+"";
	var tmpid = evt.feature.tmpid;
	var name = feature.attributes.name || "未命名标注";
	var desc = feature.attributes.desc || "未命名标注";
	//弹出popup
	__popup = new qtmap.popup.CSSFramedCloud("featurePopup",
			feature.geometry.getBounds().getCenterLonLat(),
            new OpenLayers.Size(100,100),
//            "<div style='width:150px;height:100px;padding-top:10px;padding-left:10px;'>"+
//            "<font style='font-family:\"微软雅黑\";font-size:14px;font-weight:bold;height:30px;'>"+name
//            +"</font><br/><hr style='width:80%;margin-left:0px;height:1px;background-color:#ddd;border:none;'/>" +
//            "<font style='font-family:\"微软雅黑\";font-size:small;width:120px;'>描述："+desc+"</font>"+
//            "</div>",
            "<div class=\"pop\">"+
            "<div class=\"pop-item\"><div style=\"float:left;height:100%;width:50px;\"><a class=\"pop-info-title\">名称</a></div><div style=\"float:left;\"><a class=\"pop-info-content\">"+name+"</a></div><div style=\"clear:both;\"></div></div>"+
            "<div class=\"pop-item\"><div style=\"float:left;height:100%;width:50px;\"><a class=\"pop-info-title\">描述 </a></div><div style=\"float:left;\"><a class=\"pop-info-content\">"+desc+"</a></div><div style=\"clear:both;\"></div></div>"+
            "</div>",
            null, true, onClosePopup);
	feature.popup = __popup;
	__popup.feature = feature;
	__baseMap.__map.addPopup(__popup, true);
	EC.emit(EV_FEATURE_CLICK,{tmpid:tmpid});
}

function sketchcomplete_event(evt){
	current_drawing_el.el_data.geom = wkt_c.write(evt.feature);
//	el.el_data.feature = evt.feature;
	evt.feature.fid = current_drawing_el.el_data.type+"_"+current_drawing_el.el_data.tmpid;
	evt.feature.tmpid = current_drawing_el.el_data.tmpid;
	evt.feature.attributes.name = current_drawing_el.el_data.name;
	evt.feature.attributes.desc = current_drawing_el.el_data.desc;
	evt.feature.attributes.fid = current_drawing_el.el_data.type+"_"+current_drawing_el.el_data.tmpid;
	
	if(evt.feature.geometry.CLASS_NAME == "OpenLayers.Geometry.Polygon"){
		evt.feature.attributes.stroke_color = current_drawing_el.el_data.stroke_color;
		evt.feature.attributes.stroke_opacity = current_drawing_el.el_data.stroke_opacity;
		evt.feature.attributes.stroke_width = current_drawing_el.el_data.stroke_width;
		evt.feature.attributes.fill_color = current_drawing_el.el_data.fill_color;
		evt.feature.attributes.fill_opacity = current_drawing_el.el_data.fill_opacity;
		evt.feature.attributes.ftype = "POLYGON";
	}else if(evt.feature.geometry.CLASS_NAME == "OpenLayers.Geometry.Point"){
		evt.feature.attributes.color = current_drawing_el.el_data.color;
		evt.feature.attributes.scale = current_drawing_el.el_data.scale;
		evt.feature.attributes.ftype = "POINT";
	}else if(evt.feature.geometry.CLASS_NAME == "OpenLayers.Geometry.LineString"){
		evt.feature.attributes.stroke_color = current_drawing_el.el_data.stroke_color;
		evt.feature.attributes.stroke_opacity = current_drawing_el.el_data.stroke_opacity;
		evt.feature.attributes.stroke_width = current_drawing_el.el_data.stroke_width;
		evt.feature.attributes.ftype = "LINE";
	}
	current_drawing_el.callback("");
	active = false;
	__drawLayer.active=active;
	__drawLayer.activate();
	
	//重新赋样式
	__drawLayer.__layer.styleMap = new OpenLayers.StyleMap({
		"default":changeStyle(),
		"select":changeSelectedStyle()
		});
}
/**
 * 获取已经存在的FeatureData数据信息
 */
function getFeatureData(mapid ,geom){
	debugger;
	$.ajax({
		type: "GET",
		url: getRootPath()+"/personMap/checkPersonFeatureData.do",
		data: {mapid:mapid,geom:geom},
		dataType: "json",
		success: function(data){
        	var flag = true;
        	if(data == null){
        		flag = false;
        	}
        	__popup = new qtmap.popup.CSSFramedCloud("featurePopup",
                    feature.geometry.getBounds().getCenterLonLat(),
                    new OpenLayers.Size(100,100),
                    getFormContent(flag , mapid , geom , data),
                    null, true, onClosePopup);
			feature.popup = __popup;
			__popup.feature = feature;
			__baseMap.__map.addPopup(__popup, true);
		},
	});
}

function removeFeature(data){
	var feature = __drawLayer.__layer.getFeatureByFid(data.mpel.type +"_"+data.mpel.tmpid);
	__drawLayer.__layer.removeFeatures([feature]);
	if (feature.layer) { // The feature is not destroyed
		__baseMap.__map.control.getSelectControl().unselect(feature);
		if(__popup && __popup.feature== feature){
			__baseMap.__map.removePopup(__popup);
		}
        this.destroy();
    } else {
    	if(__popup && __popup.feature== feature){
			__baseMap.__map.removePopup(__popup);
		}
        this.destroy();
    }
}

function positionFeature(data){
	var fid = data.type+"_"+data.tmpid;
	var feature = __drawLayer.__layer.getFeatureByFid(fid);
	__baseMap.__map.setCenter(new OpenLayers.LonLat(feature.geometry.getCentroid().x,feature.geometry.getCentroid().y));
	editFeatureAndPopup(data);
}

/**
 * 通过wfs请求去模糊查询
 * @param name
 */
function getFeaturesByName(name){
	var temp_bbox = __baseMap.__map.getExtent();
	var filter = new OpenLayers.Filter.Logical({
		type : OpenLayers.Filter.Logical.AND,
		// 比较操作符
		filters : [ new OpenLayers.Filter.Comparison({  
	                type : OpenLayers.Filter.Comparison.LIKE,  
	                property : "name",  
	                value : "*" + name + "*"  
	            }), 
		            new OpenLayers.Filter.Spatial({
					type : OpenLayers.Filter.Spatial.BBOX, 
					value : temp_bbox
				})]
	});
	var filter_1_0 = new OpenLayers.Format.Filter.v1_0_0();
	var xml = new OpenLayers.Format.XML();
	var xmlPara = "<?xml version='1.0' encoding='UTF-8'?>"
			+ "<wfs:GetFeature service='WFS' version='1.0.0' maxFeatures='10' "
			+ "xmlns:wfs='http://www.opengis.net/wfs' "
			+ "xmlns:gml='http://www.opengis.net/gml' "
			+ "xmlns:ogc='http://www.opengis.net/ogc' "
			+ "xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' "
			+ "xsi:schemaLocation='http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd'>"
			+ "<wfs:Query typeName='shaanxi:tr_p_poi' srsName='EPSG:4326' xmlns:shaanxi='http://www.trgis.com'>"
			+ xml.write(filter_1_0.write(filter)) + "</wfs:Query>"
			+ "</wfs:GetFeature>";
	var request = OpenLayers.Request
			.POST({
				url : this.__wfsUrl,
				data : xmlPara,
				headers : {
					"Content-Type" : "application/json"
				},
				callback : wfs_callback
			});
}

var ______wfs_callback = $.noop;
function wfs_callback(req){
	// openlayers的GML解析器
	var gmlParse = new OpenLayers.Format.GML();
	// 如果使用wfs1.1.0，则需要增加如下参数：{xy:false
	// }//更改x，y坐标的读取顺序
	var features = gmlParse.read(req.responseText);
	var sss = JSON.stringify(features);
	______wfs_callback(sss);

}


function request_key(key,callback){
	______wfs_callback = callback;
	if(key == '' || key == null){
		//如果没有录入值，清空地图上的信息
		__searchResultLayer.removeAllFeatures();
	}else{
		getFeaturesByName(key);
	}
}

//面板与地图的交互
function editFeatureAndPopup(data){
	var name = data.name.trim()=="请输入名称"?"":data.name;
	var desc = data.desc.trim()=="请输入描述"?"":data.desc;
	var feature = __drawLayer.__layer.getFeatureByFid(data.type +"_"+data.tmpid);
	if(name != "" || desc != ""){
		__popup = new qtmap.popup.CSSFramedCloud("featurePopup",
				feature.geometry.getBounds().getCenterLonLat(),
                new OpenLayers.Size(100,100),
                "<div class=\"pop\">"+
                "<div class=\"pop-item\"><div style=\"float:left;height:100%;width:50px;\"><a class=\"pop-info-title\">名称</a></div><div style=\"float:left;\"><a class=\"pop-info-content\">"+name+"</a></div><div style=\"clear:both;\"></div></div>"+
                "<div class=\"pop-item\"><div style=\"float:left;height:100%;width:50px;\"><a class=\"pop-info-title\">描述 </a></div><div style=\"float:left;\"><a class=\"pop-info-content\">"+desc+"</a></div><div style=\"clear:both;\"></div></div>"+
                "</div>",
                null, true, onClosePopup);
		feature.popup = __popup;
		__popup.feature = feature;
		__baseMap.__map.addPopup(__popup, true);
	}
	
	//判断绘制的类型
	var type = data.type;
	if(type == "point"){
//		feature.attributes.fid = data.type+"_"+data.tmpid;
		feature.attributes.color = data.color;
		feature.attributes.scale = data.scale;
//		__drawLayer.__layer.styleMap = getPointStyleMap();
//		__drawLayer.__layer.styleMap = new OpenLayers.StyleMap({"default":changeStyle(feature)});
		__drawLayer.__layer.redraw();
	}else if(type == "line"){
		feature.attributes.fid = data.type+"_"+data.tmpid;
		feature.attributes.stroke_color = data.stroke_color;
		feature.attributes.stroke_opacity = data.stroke_opacity;
		feature.attributes.stroke_width = data.stroke_width;
//		__drawLayer.__layer.styleMap = getLineAndPolyStyle();
		__drawLayer.__layer.redraw();
	}else if(type == "poly"){
		feature.attributes.fid = data.type+"_"+data.tmpid;
		feature.attributes.stroke_color = data.stroke_color;
		feature.attributes.stroke_opacity = data.stroke_opacity;
		feature.attributes.stroke_width = data.stroke_width;
		feature.attributes.fill_color = data.fill_color;
		feature.attributes.fill_opacity = data.fill_opacity;
//		__drawLayer.__layer.styleMap = getLineAndPolyStyle();
		__drawLayer.__layer.redraw();
	}
}

//页面加载时，地图初始化
function showMap(){
	__baseMap = new qtmap.basemap.mapworld(null,null,13);
	__zoom = __baseMap.__map.zoom;
	__lon = __baseMap.__map.getCenter().lon;
	__lat = __baseMap.__map.getCenter().lat;
	__baseMap.__map.events.register("moveend",null,function(e){
		__zoom = __baseMap.__map.zoom;
		__lon = __baseMap.__map.getCenter().lon;
		__lat = __baseMap.__map.getCenter().lat;
	});
	__searchResultLayer = new OpenLayers.Layer.Vector("searchResultLayer");
	var selectControl = new OpenLayers.Control.SelectFeature(__searchResultLayer);
	__baseMap.__map.addLayer(__searchResultLayer);
	__baseMap.__map.addControl(selectControl);
	//初始化__drawLayer
	__drawLayer = new qtmap.draw.point();

	var __last_modify_fea;
	__drawLayer.__layer.events.on({
		'sketchcomplete':function(evt){
			sketchcomplete_event(evt);
		},
        'beforefeaturemodified': function(evt){
			__last_modify_fea = evt.feature;
        	beforefeaturemodified_event(evt);
//        	getFeatureData(__mapid , geom);
        },
        'afterfeaturemodified': function(evt){
        	afterfeaturemodified_event(evt);
        },
        'vertexmodified': function(evt){
        	afterfeaturemodified_event(evt);
        }
    });

	//地图保存之前
	EC.on(EV_MAP_SAVE_BEFORE,function(){
		/*var evt = document.createEvent("MouseEvents");
		evt.initEvent("click", true, true);
		$("#qtmap .olLayerDiv").each(function(i){
			this.dispatchEvent(evt);
		});*/

		/*__drawLayer.active=false;
		__drawLayer.activate();
		if(__last_modify_fea) __drawLayer.__layer.events.triggerEvent("afterfeaturemodified", {
			feature: __last_modify_fea
		});*/
	})
	
	__baseMap.__map.setLayerIndex(__searchResultLayer , 10000);
	__baseMap.__map.setLayerIndex(__drawLayer.__layer , 9999);
	//页面编辑状态，加载时，获取该地图的feature
	EC.on(EV_USER_FEA_LOADED,function(data){
		initLayerFeature(data);
	});
}
//地图切换
function toggleMap(data){
//	$("#qtmap").empty();
//	__baseMap.removeBaseLayer();
	//获取所有选中的图层
	var lst = data.selected_list;
	for(var i=lst.length-1;i >= 0;i--){
		var name = lst[i].basemapType || lst[i].name;
		var opacity = lst[i].opacity;
		if(i==lst.length-1){
			getBaseLayer(name,opacity);
		}else{
			getOtherLayers(name,opacity);
		}
	}
}

//查询结果显示在地图上
function searchResult(data){
	for(var i = 0; i <data.list.length;i++){
		var temp = data.list[i];
		var feature = new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Point(temp.geometry.x , temp.geometry.y));
		var tempid = i+1;
		feature.attributes.name = temp.attributes.name;
		feature.attributes.type = temp.attributes.childtype;
		feature.attributes.defaultImage = getRootPath()+"/jsp/map/images/marker/"+tempid+".png";
		feature.attributes.selectedImage = getRootPath()+"/jsp/map/images/marker/"+tempid+"-1.png";
		__searchResultLayer.addFeatures([feature]);
		
	}
	if(__searchResultLayer.features.length != 0){
		__baseMap.__map.zoomToExtent(__searchResultLayer.getDataExtent());
	}
	__searchResultLayer.styleMap = new OpenLayers.StyleMap({
		"default": new OpenLayers.Style({
			graphicWidth: 28,
            graphicHeight: 38,
            graphicYOffset: -38,
            externalGraphic: "${defaultImage}"
        }),
        "select": new OpenLayers.Style({
        	graphicWidth: 28,
            graphicHeight: 38,
            graphicYOffset: -38,
            externalGraphic: "${selectedImage}"
        })
	});
	__searchResultLayer.redraw();
}

function getBaseLayer(name,opacity){
	var num = __baseMap.__map.layers.length;
	for(var i=num-1;i>=0;i--){
		var temp_layer = __baseMap.__map.layers[i];
		if(temp_layer.CLASS_NAME == "OpenLayers.Layer.TMS"){
			__baseMap.__map.removeLayer(temp_layer);
		}else if(temp_layer.CLASS_NAME == "OpenLayers.Layer.WMTS"){
			__baseMap.__map.removeLayer(temp_layer);
		}
	}
	
	if(name == "彩色版"){
		__baseMap = new qtmap.basemap.colormap(__lon , __lat , __zoom , true,opacity);
	}else if(name == "冷色版"){
		__baseMap = new qtmap.basemap.coolmap(__lon , __lat , __zoom , true,opacity);
	}else if(name == "天地图影像"){
		__baseMap = new qtmap.basemap.mapWorldImage(__lon , __lat , __zoom , true,opacity);
	}else if(name == "灰色版"){
		__baseMap = new qtmap.basemap.graymap(__lon , __lat , __zoom , true,opacity);
	}else if(name == "彩色注记版"){
		__baseMap = new qtmap.basemap.notemap(__lon , __lat , __zoom , true,opacity);
	}else if(name == "暖色版"){
		__baseMap = new qtmap.basemap.warmmap(__lon , __lat , __zoom , true,opacity);
	}else if(name == "天地图"){
		__baseMap = new qtmap.basemap.mapworld(__lon , __lat , __zoom , true,opacity);
	} 
	return __baseMap;
}

function getOtherLayers(name,opacity){
	if(name == "彩色版"){
		new qtmap.basemap.colormap(__lon , __lat , __zoom , false , opacity);
	}else if(name == "冷色版"){
		new qtmap.basemap.coolmap(__lon , __lat , __zoom , false , opacity);
	}else if(name == "天地图影像"){
		new qtmap.basemap.mapWorldImage(__lon , __lat , __zoom , false , opacity);
	}else if(name == "灰色版"){
		new qtmap.basemap.graymap(__lon , __lat , __zoom , false , opacity);
	}else if(name == "彩色注记版"){
		new qtmap.basemap.notemap(__lon , __lat , __zoom , false , opacity);
	}else if(name == "暖色版"){
		new qtmap.basemap.warmmap(__lon , __lat , __zoom , false , opacity);
	}else if(name == "天地图"){
		new qtmap.basemap.mapworld(__lon , __lat , __zoom , false , opacity);
	} 
	
	
}


/**
 * 弹出的popup内容
 * 判断该点的信息是否已经录入
 */
function getFormContent(flag , mapid , geom , data){
	var content;
	//已经包含数据
	if(flag == true){
		content="<form method='post' id='featureDataForm'>" +
	    "<table style='width:100%;height:100%;'><tbody>" +
	    "<tr>" +
	    "<th>名称：" +
	    "<input type='hidden' id='id' name='id' value='"+data.id+"' />" +
	    "<input type='hidden' id='mapid' name='mapid' value='"+mapid+"' />" +
	    "<input type='hidden' id='geom' name='geom' value='"+geom+"' /></th>" +
	    "<td><input type='text' class='test_text' id='name' name='name' value='"+data.name+"'  /></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<th>地址：</th>" +
	    "<td><input type='text' class='test_text' id='address' name='address' value='"+data.address+"'  /></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<th>电话：</th>" +
	    "<td><input type='text' class='test_text' id='phone' name='phone' value='"+data.phone+"'  /></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<th>标签：</th>" +
	    "<td><input type='text' class='test_text' id='label' name='label' value='"+data.label+"'  /></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<th>描述：</th>" +
	    "<td><textarea id='description' name='description' value=''	class='test_qianming test_text' style='width:320px; height:81px;'>"+data.description+"</textarea></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<th>附件上传：</th>" +
	    "<td><input type='text' id='pup_upload' name='pup_upload' value='"+data.pup_upload+"'  /></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td><input type='button' id='pup_btnConfirm' class='qunding' onclick='confirmFeatureData()' value='确定' /></td>" +
	    "<td><input type='button' id='pup_btnCancel' class='retrn' onclick='onClosePopup()'  value='取消' /></td>" +
	    "</tr>" +
	    "</tbody></table></form>";
	}else{
		content = "<form method='post' id='featureDataForm'>" +
	    "<table style='width:100%;height:100%;'><tbody>" +
	    "<tr>" +
	    "<th>名称：" +
	    "<input type='hidden' id='id' name='id' />" +
	    "<input type='hidden' id='mapid' name='mapid' value='"+mapid+"' />" +
	    "<input type='hidden' id='geom' name='geom' value='"+geom+"' /></th>" +
	    "<td><input type='text' class='test_text' id='name' name='name'  /></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<th>地址：</th>" +
	    "<td><input type='text' class='test_text' id='address' name='address'  /></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<th>电话：</th>" +
	    "<td><input type='text' class='test_text' id='phone' name='phone'  /></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<th>标签：</th>" +
	    "<td><input type='text' class='test_text' id='label' name='label'  /></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<th>描述：</th>" +
	    "<td><textarea id='description' name='description' value=''	class='test_qianming test_text' style='width:320px; height:81px;'></textarea></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<th>附件上传：</th>" +
	    "<td><input type='text' class='test_text' id='pup_upload' name='pup_upload'  /></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td><input type='button' id='pup_btnConfirm' class='qunding' onclick='confirmFeatureData()' value='确定' /></td>" +
	    "<td><input type='button' id='pup_btnCancel' class='retrn' onclick='onClosePopup()'  value='取消' /></td>" +
	    "</tr>" +
	    "</tbody></table></form>";
	}
	return content;
}

//改变一个feature的状态
function onClosePopup(evt) {
    // 'this' is the popup.
    var feature = this.feature;
    if (feature.layer) { // The feature is not destroyed
        qtmap.control.getSelectControl().unselect(feature);
        
        if(__popup && __popup.blocks){
        	__popup.destroy();
        	__popup = null;
        }
    } else { // After "moveend" or "refresh" events on POIs layer all 
             //     features have been destroyed by the Strategy.BBOX
    	if(__popup && __popup.blocks){
        	__popup.destroy();
        	__popup = null;
        }
    }
}


//获取项目路径。
function getRootPath(){ return ctx; }

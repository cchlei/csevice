var pageSize = 4 , pageNo=1 , __mapid,__popup;
var totalCount,endPage,temp_index,active=false,selectControlActive=false,dragControlActive=false,modifyControlActive=false;
//基础底图，zoom，center
var __baseMap,__zoom,__lon,__lat;
//点线面的绘制图层
var __drawLayer;
var EL_POINT = "point",EL_LINE="line",EL_POLY="poly";

//几类底图
var __colormap , __coolmap, __graymap , __mapworld , __mapWorldImage, __notemap , __warmmap;

var wkt_c = new OpenLayers.Format.WKT();
//默认样式
var externalGraphic_default = 'jsp/map/images/map_marker.png';
var graphicWidth_default = 16;
var graphicHeight_default = 26;
var graphicYOffset_default = 0 - graphicHeight_default;
var fillColor_default = "#FEF9BF";
var fillOpacity_default = 1;
var strokeWidth_default = 2;
var strokeOpacity_default = 1;
var strokeColor_default = "#f86767";
var pointRadius_default = 3;
//选中样式
var externalGraphic_select = 'jsp/map/images/map_marker.png';
var graphicWidth_select = 16;
var graphicHeight_select = 26;
var graphicYOffset_select = 0 - graphicHeight_select;
var fillColor_select = "#FEF9BF";
var fillOpacity_select = fillOpacity_default;
var strokeWidth_select = strokeWidth_default;
var strokeOpacity_select = 1;
var strokeColor_select = "#ff6347";
var pointRadius_select = pointRadius_default;

$(document).ready(function(){
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

function changeStyle(ftype){
	var style = new OpenLayers.Style(
	        // the first argument is a base symbolizer
	        // all other symbolizers in rules will extend this one
	        {
	            label: "${ftype}" // label will be foo attribute value
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
	                    	fillColor: fillColor_default,
	                        fillOpacity: fillOpacity_default,
	                        strokeWidth: strokeWidth_default,
	                        strokeOpacity: strokeOpacity_default,
	                        strokeColor: strokeColor_default
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
//	                    	pointRadius: pointRadius_default,
//	                        graphicName: "circle",
	                        fillColor: fillColor_default,
	                        fillOpacity: fillOpacity_default,
	                        strokeWidth: strokeWidth_default,
	                        strokeOpacity: strokeOpacity_default,
	                        strokeColor: strokeColor_default
	                    }
	                }),
	                new OpenLayers.Rule({
	                    filter: new OpenLayers.Filter.Comparison({
	                    	type: OpenLayers.Filter.Comparison.EQUAL_TO,
	                        property: "ftype", 
	                        value: "POLYGON"
	                    }),
	                    symbolizer: {
//	                    	pointRadius: pointRadius_default,
//	                        graphicName: "circle",
	                        fillColor: fillColor_default,
	                        fillOpacity: fillOpacity_default,
	                        strokeWidth: strokeWidth_default,
	                        strokeOpacity: strokeOpacity_default,
	                        strokeColor: strokeColor_default
	                    }
	                }),
	                new OpenLayers.Rule({
	                    // apply this rule if no others apply
	                    elseFilter: true,
	                    symbolizer: {
	                    	pointRadius: pointRadius_default,
	                        graphicName: "circle",
	                        fillColor: fillColor_default,
	                        fillOpacity: fillOpacity_default,
	                        strokeWidth: strokeWidth_default,
	                        strokeOpacity: strokeOpacity_default,
	                        strokeColor: strokeColor_default
	                    }
	                })
	            ]
	        }
	    );
	return style;
}

function changeSelectedStyle(feature){
	var style = new OpenLayers.Style(
	        // the first argument is a base symbolizer
	        // all other symbolizers in rules will extend this one
	        {
	            label: "${ftype}" // label will be foo attribute value
	        },
	        // the second argument will include all rules
	        {
	            rules: [
	                new OpenLayers.Rule({
	                    // a rule contains an optional filter
	                    filter: new OpenLayers.Filter.Logical({
	                		type : OpenLayers.Filter.Logical.AND,
	                		// 比较操作符
	                		filters : [ new OpenLayers.Filter.Comparison({
	                			type: OpenLayers.Filter.Comparison.EQUAL_TO,
	                			property: "ftype", 
		                        value: "POINT"
	                		})
	                		,new OpenLayers.Filter.Comparison({
	                			type: OpenLayers.Filter.Comparison.EQUAL_TO,
		                        property: "tmpid", 
		                        value:feature.attributes.tmpid
	                		})
	                		]
	                	}),
	                    // if a feature matches the above filter, use this symbolizer
	                    symbolizer: {
	                    	graphicWidth: 16,
	     		            graphicHeight: 26,
	     		            graphicYOffset: -26,
	                        externalGraphic: "jsp/map/images/map_marker_selected.png"
	                    }
	                }),
	                new OpenLayers.Rule({
	                    filter: new OpenLayers.Filter.Comparison({
	                    	type: OpenLayers.Filter.Comparison.EQUAL_TO,
	                        property: "ftype", 
	                        value: "LINE"
	                    }),
	                    symbolizer: {
//	                    	pointRadius: pointRadius_default,
//	                        graphicName: "circle",
	                        fillColor: fillColor_select,
	                        fillOpacity: fillOpacity_select,
	                        strokeWidth: strokeWidth_select,
	                        strokeOpacity: strokeOpacity_select,
	                        strokeColor: strokeColor_select
	                    }
	                }),
	                new OpenLayers.Rule({
	                    filter: new OpenLayers.Filter.Comparison({
	                    	type: OpenLayers.Filter.Comparison.EQUAL_TO,
	                        property: "ftype", 
	                        value: "POLYGON"
	                    }),
	                    symbolizer: {
//	                    	pointRadius: pointRadius_default,
//	                        graphicName: "circle",
	                        fillColor: fillColor_default,
	                        fillOpacity: fillOpacity_default,
	                        strokeWidth: strokeWidth_default,
	                        strokeOpacity: strokeOpacity_default,
	                        strokeColor: strokeColor_default
	                    }
	                }),
	                new OpenLayers.Rule({
	                    // apply this rule if no others apply
	                    elseFilter: true,
	                    symbolizer: {
	                    	pointRadius: pointRadius_default,
	                        graphicName: "circle",
	                        fillColor: fillColor_default,
	                        fillOpacity: fillOpacity_default,
	                        strokeWidth: strokeWidth_default,
	                        strokeOpacity: strokeOpacity_default,
	                        strokeColor: strokeColor_default
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
		//样式
//		__drawLayer.__layer.styleMap = getPointStyleMap();
//		__drawLayer.__layer.styleMap = new OpenLayers.StyleMap(changeStyle(__drawLayer.__layer.feature.attributes.ftype));
	}else if(el.el_data.type == EL_LINE){
		__drawLayer = new qtmap.draw.line();
		//样式
//		__drawLayer.__layer.styleMap = getLineAndPolyStyle();
//		__drawLayer.__layer.styleMap = new OpenLayers.StyleMap(changeStyle(__drawLayer.__layer.feature.attributes.ftype));
	}else if(el.el_data.type == EL_POLY){
		__drawLayer = new qtmap.draw.polygon();
		//样式
//		__drawLayer.__layer.styleMap = getLineAndPolyStyle();
//		__drawLayer.__layer.styleMap = new OpenLayers.StyleMap(changeStyle(__drawLayer.__layer.feature.attributes.ftype));
	}
	
	if(active){
		active = false;
	}else{
		active = true;
	}
	if(modifyControlActive){
		modifyControlActive = false;
	}else{
		modifyControlActive = true;
	}
	__drawLayer.modifyControlActive = modifyControlActive;
	__drawLayer.modifyControlActivate();
	//当绘制图层的选中控件被激活时，同时会激活Feature的选中和未选中事件。
	__drawLayer.__layer.events.on({
		'sketchcomplete':function(evt){
			el.el_data.geom = wkt_c.write(evt.feature);
//			el.el_data.feature = evt.feature;
			evt.feature.fid = el.el_data.type+"_"+el.el_data.tmpid;
			evt.feature.tmpid = el.el_data.tmpid;
			if(evt.feature.geometry.CLASS_NAME == "OpenLayers.Geometry.Polygon"){
				evt.feature.attributes.ftype = "POLYGON";
			}else if(evt.feature.geometry.CLASS_NAME == "OpenLayers.Geometry.Point"){
				evt.feature.attributes.ftype = "POINT";
			}else if(evt.feature.geometry.CLASS_NAME == "OpenLayers.Geometry.LineString"){
				evt.feature.attributes.ftype = "LINE";
			}
			el.callback("");
			active = false;
    		__drawLayer.active=active;
    		__drawLayer.activate();
    		
    		//重新赋样式
    		__drawLayer.__layer.styleMap = new OpenLayers.StyleMap({
    			"default":changeStyle(evt.feature.attributes.ftype),
    			"select":changeSelectedStyle(evt.feature.attributes.ftype)
    			});
		},
        'beforefeaturemodified': function(evt){
        	feature = evt.feature;
        	var geom = feature.geometry+"";
        	var tmpid = evt.feature.tmpid;
        	EC.emit(EV_FEATURE_CLICK,{tmpid:tmpid});
//        	getFeatureData(__mapid , geom);
        },
        'afterfeaturemodified': function(evt){
        	feature = evt.feature;
        	__drawLayer.modifyControlActive = modifyControlActive;
        	__drawLayer.modifyControlActivate();
        	onClosePopup();
        }
    });
	__drawLayer.active=active;
	__drawLayer.activate();
}

function getPointStyleMap(){
	var styleMap = new OpenLayers.StyleMap({
//		"default":new OpenLayers.Style({  
//			externalGraphic: externalGraphic_default,
//	        graphicWidth: graphicWidth_default,
//	        graphicHeight: graphicHeight_default, 
//	        graphicYOffset: graphicYOffset_default
//	    }),
//	    "select":new OpenLayers.Style({  
//	    	externalGraphic: externalGraphic_select,
//	        graphicWidth: graphicWidth_select,
//	        graphicHeight: graphicHeight_select, 
//	        graphicYOffset: graphicYOffset_select
//	    })
		"default": new OpenLayers.Style({  
	        pointRadius: pointRadius_default,
            graphicName: "circle",
            fillColor: fillColor_default,
            fillOpacity: fillOpacity_default,
            strokeWidth: strokeWidth_default,
            strokeOpacity: strokeOpacity_default,
            strokeColor: strokeColor_default
	    }),
	    "select": new OpenLayers.Style({  
	        pointRadius: pointRadius_select,
            graphicName: "circle",
            fillColor: fillColor_select,
            fillOpacity: fillOpacity_select,
            strokeWidth: strokeWidth_select,
            strokeOpacity: strokeOpacity_select,
            strokeColor: strokeColor_select
	    })
	});
	return styleMap;
}

function getLineAndPolyStyle(){
	var styleMap = new OpenLayers.StyleMap({
		"default": new OpenLayers.Style({  
			pointRadius: pointRadius_default,
            graphicName: "circle",
            fillColor: fillColor_default,
            fillOpacity: fillOpacity_default,
            strokeWidth: strokeWidth_default,
            strokeOpacity: strokeOpacity_default,
            strokeColor: strokeColor_default
	    }),
	    "select": new OpenLayers.Style({  
	    	pointRadius: pointRadius_select,
            graphicName: "circle",
            fillColor: fillColor_select,
            fillOpacity: fillOpacity_select,
            strokeWidth: strokeWidth_select,
            strokeOpacity: strokeOpacity_select,
            strokeColor: strokeColor_select
	    })
	});
	return styleMap;
}

/**
 * 获取已经存在的FeatureData数据信息
 */
function getFeatureData(mapid ,geom){
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

//面板与地图的交互
function editFeatureAndPopup(data){
	var name = data.name.trim()=="请输入名称"?"":data.name;
	var desc = data.desc.trim()=="请输入描述"?"":data.desc;
	var feature = __drawLayer.__layer.getFeatureByFid(data.type +"_"+data.tmpid);
	if(name != "" || desc != ""){
		__popup = new qtmap.popup.CSSFramedCloud("featurePopup",
				feature.geometry.getBounds().getCenterLonLat(),
                new OpenLayers.Size(100,100),
                "<div style='width:150px;height:100px;padding-top:10px;padding-left:10px;'>"+
                "<font style='font-family:\"微软雅黑\";font-size:large;'>"+name+"</font><br/>" +
                "<font style='font-family:\"微软雅黑\";font-size:small;width:120px;'>"+desc+"</font>"+
                "</div>",
                null, true, onClosePopup);
		feature.popup = __popup;
		__popup.feature = feature;
		__baseMap.__map.addPopup(__popup, true);
	}
	
	//判断绘制的类型
	var type = data.type;
	if(type == "point"){
		feature.attributes.tmpid = data.tmpid;
		feature.attributes.color = data.color;
		feature.attributes.scale = data.scale;
//		__drawLayer.__layer.styleMap = getPointStyleMap();
		__drawLayer.__layer.redraw();
	}else if(type == "line"){
		feature.attributes.tmpid = data.tmpid;
		feature.attributes.stroke_color = data.stroke_color;
		feature.attributes.stroke_opacity = data.stroke_opacity;
		feature.attributes.stroke_width = data.stroke_width;
//		__drawLayer.__layer.styleMap = getLineAndPolyStyle();
		__drawLayer.__layer.redraw();
	}else if(type == "poly"){
		feature.attributes.tmpid = data.tmpid;
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
	__baseMap = new qtmap.basemap.notemap(null,null,13);
	__zoom = __baseMap.__map.zoom;
	__lon = __baseMap.__map.getCenter().lon;
	__lat = __baseMap.__map.getCenter().lat;
	__baseMap.__map.events.register("moveend",null,function(e){
		__zoom = __baseMap.__map.zoom;
		__lon = __baseMap.__map.getCenter().lon;
		__lat = __baseMap.__map.getCenter().lat;
	});
}
//地图切换
function toggleMap(data){
//	$("#qtmap").empty();
//	__baseMap.removeBaseLayer();
	//获取所有选中的图层
	var lst = data.selected_list;
	for(var i=0;i < lst.length;i++){
		var name = lst[i].name;
		var opacity = lst[i].opacity;
		if(i==0){
			getBaseLayer(name,opacity);
		}else{
			getOtherLayers(name,opacity);
		}
	}
	
}

function getBaseLayer(name,opacity){
	__baseMap.removeBaseLayer();
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
        if(__popup){
        	__popup.destroy();
        }
    } else { // After "moveend" or "refresh" events on POIs layer all 
             //     features have been destroyed by the Strategy.BBOX
    	if(__popup){
        	__popup.destroy();
        }
    }
}


//获取项目路径。
function getRootPath(){
    //获取当前网址，如： http://localhost:8088/test/test.jsp
    var curPath=window.document.location.href;
    //获取主机地址之后的目录，如： test/test.jsp
    var pathName=window.document.location.pathname;
    var pos=curPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8088
    var localhostPaht=curPath.substring(0,pos);
    //获取带"/"的项目名，如：/test
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}

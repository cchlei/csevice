////扩展自定义地图控件
//window.trm = {};
//var trm = window.trm;

var wi = window;
wi.ctx = wi.ctx || "";
function _ctx(path){
    //if(ctx=="") return undefined;
    return path;
}

var dataprojection = ol.proj.get('EPSG:4326');
var projection = ol.proj.get('EPSG:3857');
var projectionExtent = projection.getExtent();
var size = ol.extent.getWidth(projectionExtent) / 256;
var resolutions = new Array(20);
var matrixIds = new Array(20);
//对应上面的坐标系的resolution
resolutions[z] = size / Math.pow(2, z)/2;
//matrixids就是缩放级别，但具体的体现不尽相同。比如geoserver中的TILEMATRIX要在前面拼上坐标系。
//geoserver:http://XX/geoserver/gwc/service/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=YUHANG&STYLE=default&TILEMATRIXSET=EPSG:4326&&TILEMATRIX=EPSG:4326:15&TILEROW=5422&TILECOL=27333&FORMAT=image/png
var gsmids = new Array(20);
for (var z = 0; z < 20; ++z) {
	resolutions[z] = size / Math.pow(2, z);
	matrixIds[z] = z;
	gsmids[z] = "EPSG:900913:"+z;
}

// 地图初始化显示的中心
var center = ol.proj.transform([108.94,34.26],dataprojection,projection);

// 显示天地图的版权信息
var tdtattribution = new ol.Attribution({
	html: '数据来源  &copy; <a href="http://www.tianditu.com">天地图</a>'
});

//天润云地图
var trmattribution = new ol.Attribution({
	html: ' <a href="http://www.trgis.com">天润云&copy;</a> 审图号：陕0001'
});

// 天地图矢量图层
var tdtlayerGroup = new ol.layer.Group({
	visible:true,
	layers : [new ol.layer.Tile({
		source : new ol.source.WMTS({
			attributions: [tdtattribution],
			url : 'http://t{0-5}.tianditu.com/vec_w/wmts',
			layer : 'vec',
			matrixSet : 'w',
			format : 'tiles',
			projection : projection,
			tileGrid : new ol.tilegrid.WMTS({
				origin : ol.extent.getTopLeft(projectionExtent),
				resolutions : resolutions,
				matrixIds : matrixIds
			}),
			style : 'default',
			wrapX : true //水平世界循环，默认为false不循环
		})
	}),new ol.layer.Tile({
		source : new ol.source.WMTS({
			attributions: [tdtattribution],
			url : 'http://t{0-5}.tianditu.com/cva_w/wmts',
			layer : 'cva',
			matrixSet : 'w',
			format : 'tiles',
			projection : projection,
			tileGrid : new ol.tilegrid.WMTS({
				origin : ol.extent.getTopLeft(projectionExtent),
				resolutions : resolutions,
				matrixIds : matrixIds
			}),
			style : 'default',
			wrapX : true
		})
	})]
});

//天地图影像图层
var tdtImglayerGroup = new ol.layer.Group({
	visible:false,
	layers : [new ol.layer.Tile({
		source : new ol.source.WMTS({
			attributions: [tdtattribution],
			url : 'http://t{0-5}.tianditu.com/img_w/wmts',
			layer : 'img',
			matrixSet : 'w',
			format : 'tiles',
			projection : projection,
			tileGrid : new ol.tilegrid.WMTS({
				origin : ol.extent.getTopLeft(projectionExtent),
				resolutions : resolutions,
				matrixIds : matrixIds
			}),
			style : 'default',
			wrapX : true //水平世界循环，默认为false不循环
		})
	}),new ol.layer.Tile({
		source : new ol.source.WMTS({
			attributions: [tdtattribution],
			url : 'http://t{0-5}.tianditu.com/cia_w/wmts',
			layer : 'cia',
			matrixSet : 'w',
			format : 'tiles',
			projection : projection,
			tileGrid : new ol.tilegrid.WMTS({
				origin : ol.extent.getTopLeft(projectionExtent),
				resolutions : resolutions,
				matrixIds : matrixIds
			}),
			style : 'default',
			wrapX : true
		})
	})]
});

var trmapLayerGroup = new ol.layer.Group({
	visible:false,
	layers : [new ol.layer.Tile({
//		source : new ol.source.TileWMS({
//			attributions: [trmattribution],
//			url : 'http://117.34.70.39:80/geocloud/wms?SERVICE=WMS&amp;',
//			params : {layers:'tr_shaanxi_bg',version:'1.1.1'},
//			projection : projection,
//			wrapX : true
//		})
		source : new ol.source.WMTS({
			attributions: [trmattribution],
			url : 'http://117.34.70.39/geocloud/gwc/service/wmts/',
			layer : "tr_shaanxi_bg",
			matrixSet : "EPSG:900913",
			format:'image/png8',
			projection : projection,
			tileGrid : new ol.tilegrid.WMTS({
				origin : ol.extent.getTopLeft(projectionExtent),
				resolutions : resolutions,
				matrixIds : gsmids
			}),
			style:'default',
			wrapX : true
		})
	}),new ol.layer.Tile({
		source : new ol.source.WMTS({
			attributions: [tdtattribution],
			url : 'http://t{0-5}.tianditu.com/cva_w/wmts',
			layer : 'cva',
			matrixSet : 'w',
			format : 'tiles',
			projection : projection,
			tileGrid : new ol.tilegrid.WMTS({
				origin : ol.extent.getTopLeft(projectionExtent),
				resolutions : resolutions,
				matrixIds : matrixIds
			}),
			style : 'default',
			wrapX : true
		})
	})]
});

// 矢量绘制层          临时绘制数据使用
var vector = new ol.layer.Vector({
	source : new ol.source.Vector(),
	style : new ol.style.Style({
		image: new ol.style.Icon({
			anchor: [0.5, 1],
			src: ctx+'/assets/images/icon_localtion_red.png'
		}),
		stroke : new ol.style.Stroke({
			color : 'rgba(78,165,255,1)',
			width : 3
		}),
		fill : new ol.style.Fill({
			color : 'rgba(255, 255, 0, 0.5)'
		})
	})
});
// 初始化地图1
var map = new ol.Map({
	layers : [tdtlayerGroup,tdtImglayerGroup,trmapLayerGroup,vector], //后面的图层会覆盖显示前面的图层
	target : 'map',
	interactions : ol.interaction.defaults({
		doubleClickZoom : false
	}),
	controls : ol.control.defaults({
		attributionOptions : /** @type {olx.control.AttributionOptions} */
			({
				collapsible : false
			}),
		zoomOptions:{
			zoomInTipLabel:"放大",
			zoomOutTipLabel:"缩小"
		},
	}).extend([
		new ol.control.ZoomSlider(),//缩放滚动条组件
		new ol.control.ScaleLine()//比例尺组件
	]),
	view : new ol.View({
		center : center,
		zoom : 12,
		minZoom:4,
		maxZoom:18
	})
});

/*
 * 2015-12-15 15:50:23
 * @author jger
 * 增加初始化地图时加载图层已有POI数据
 * 
 * normalPOI，heighLight样式引用自 libs/openlayers/trmap_styles.js
 */
//////////////////////////////////////////////////
//读取默认要素图层
var featureLayer;

var container = document.getElementById('popup');
var content = document.getElementById('popup-content');
var closer = document.getElementById('popup-closer');

var popup = new ol.Overlay({
	element: container,
    autoPan: false,
    positioning:'bottom-center',
    offset:[0, 0],
    autoPanAnimation: {
      duration: 250
    }
});
map.addOverlay(popup);

closer.onclick = function() {
	popup.setPosition(undefined);
    closer.blur();
    return false;
};

/**
 * 居中显示坐标
 * @param coordinates
 * @param fix 居中修正值 单位是projection单位
 */
function centerAt(coordinates,fix){
	var view = map.getView();
	var pan = ol.animation.pan({
        duration: 500,
        source: /** @type {ol.Coordinate} */ (view.getCenter())
      });
    map.beforeRender(pan);
    view.setCenter(coordinates);
}


var pop_gen = ({
	init:function(){
		var m = this;
		m.tpl =
			'<div class="pop_in_map">'+
			'   <div class="inner">'+
			'        <span class="lb">标题</span><a style="color:black;" href='+ctx+'/record/toRecord/{id}> {name} </a><i class="ico msg"></i>({num_msg}) <br>'+
			'        <span class="lb">时间</span> {occurtime} <br>'+
			'        <span class="lb">描述</span> {description}'+
			'        <div class="grid">{imglist}</div>'+
			'    </div>'+
			'</div>'
		;
		return m;
	},
	get:function(rec,callback){
		var m = this;
		use(["parseTpl","dateFormat","__/js/comm"],function(pt,d,comm){
			if(callback) {
				callback(
						pt(
							rec,
							m.tpl,
							function(v,name){
								var ret = v;
								if(name=="occurtime"){
									ret = d(v).format("%Y-%m-%d")
								}else if(name == "description"){
									return v.strleng("54");
								}else if(name == "imglist"){
									ret = pt(
										this.piclist.$model,
										'<span><i><img src="{src}"></i></span>',
										function(v,name){
											if(name == "src"){
												return comm.media_image_path + this;
											}
										}
									)
								}

								return ret;
							}
						)
				);
			}
		});
	}
}).init();


/**
 * 定位并弹出popup
 * @param id
 */
function locatAndPopup(ft,coord) {
	//var coordinate = ft.getGeometry().getFirstCoordinate();
	var feature = featureLayer.getSource().getFeatureById(ft);
	if (feature) {
		var coordinate = coord || getCordinate(feature);
		popup.setPosition(coordinate);

		use("__/js/theme_detail", function (ex) {
			var rec = ex.dic_id_poi[feature.get("id")];
			if (rec) {
				pop_gen.get(rec, function (html) {
					//feature.getProperties();
					content.innerHTML = html;
					centerAt(coordinate);
				})
			}

		});
	}

}
function getCordinate(ft){
	   var g = ft.getGeometry();
    var bound;
    if(/Point/.test(g.getType())){
        return g.getCoordinates();
    }else if(/LineString/.test(g.getType())){
        bound = ol.extent.boundingExtent(g.getCoordinates());
    }else{
        bound = ol.extent.boundingExtent(g.getCoordinates()[0]);
    }
    return [(bound[0] + bound[2])/2,(bound[1] + bound[3])/2];
}

/*
 * 两个数组逐个相加，用于修正位置显示
 */
function _sumArray(arr1,arr2){
	var arr = {};
	if(arr1.length==2){
		arr[0] = arr1[0] + arr2[0];
		arr[1] = arr1[1] + arr2[1];
	} else {
		for(var i=0;i<arr1.length;i++){
			arr[i] = arr1[i] + arr2[i];
		}
	}
	return arr;
}
//////////////////////////////////////////////////
//GeoJSON格式数据定义
var geoJsonFormat =  new ol.format.GeoJSON();
//vector样式
var vectorStyle = new ol.style.Style({
	image: new ol.style.Icon({
		anchor: [0.5, 1],
		src: ctx+'/assets/images/icon_localtion_red.png'
	}),
	stroke : new ol.style.Stroke({
		color : 'rgba(78,165,255,1)',
		width : 3
	}),
	fill : new ol.style.Fill({
		color : 'rgba(255, 255, 0, 0.5)'
	})
});

!function(){
	////////////////////////////////-- FeatureLayer BEGIN -- /////////////////////////////////////
	/*
	 * ===========================
	 * @auther jger
	 * @date 2015-12-24 15:41:35
	 * 
	 * 异步数据加载方法，替代原始数据加载方式。用于数据刷新
	 * ---------------------------
	 */
	//加载专题数据的方法
	function loaderFunction(extent, resolution, projection,para){
		featureSource.clear();
		popup.setPosition();
		var url = ctx+"/map/featuresByTopic/"+topicid;
		use("_/tr_util",function(comm){
		    comm.ajax(url,para || {"open":-1,"key":""},function(tooken,data){
		         if(tooken.vl()){
		        	 var features = geoJsonFormat.readFeatures(data,{
		        	        dataProjection: dataprojection,
		        	        featureProjection: projection,
		        	        style : vectorStyle
		        	      });

		        	 featureSource.addFeatures(features);
		        	 if(features.length>0){
			        	 locatAndPopup(features[0].get('id'),null);
		        	 }
		         }
		    })
		})
	}
	
	use("__/js/theme_detail",function(td){
		td.onSearch = function(para){
			loaderFunction(undefined,undefined,projection,para);
		}
		//初始化map是调整显示
		td.onMapShow = function(isfirst){
			if(isfirst){
				var m = $("#map");
				!function(){
					map.setSize([m.width(),m.height()])
				}.delayCall(180)
			}
		}
	})
	//////////////////////////////////////////////////////
	
	// 矢量图层数据源定义
	var featureSource = new ol.source.Vector({
		//loader : loaderFunction,
		format : geoJsonFormat
	});

	// 地图要素数据图层定义
	featureLayer = new ol.layer.Vector({
		source : featureSource,
		style : vectorStyle
	});
	
	map.addLayer(featureLayer);
	map.updateSize();
	////////////////////////////////-- FeatureLayer END -- /////////////////////////////////////
	// 点击feature
    map.on('click', function(evt) {
      var feature = map.forEachFeatureAtPixel(evt.pixel,
          function(feature) {
            return feature;
          });
      if (feature) {
    	  locatAndPopup( feature.get('id'),evt.coordinate);
      }
    });

    use("__/js/theme_detail",function(td){
    	//地图模式下单击记录
    	td.onRecClickOnMapMode = function(el){
    		locatAndPopup(el.id);
    	}
    	td.onMapShow=function(first){
        	if(!first){
        	}
        	//var fid = td.map_rec_list.vm.list[0].id;
        	if(td.map_rec_list.vm.list.length>0){
            	locatAndPopup(td.map_rec_list.vm.list[0].id);
        	}
        }
    })
}();
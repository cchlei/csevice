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
		fill : new ol.style.Fill({
			color : 'rgba(255, 255, 255, 0.2)'
		}),
		stroke : new ol.style.Stroke({
			color : '#ffcc33',
			width : 2
		}),
		image : new ol.style.Circle({
			radius : 7,
			fill : new ol.style.Fill({
				color : '#ffcc33'
			})
		})
	})
});

// 地图对象
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
    positioning:'top-center',
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

/**
 * 定位并弹出popup
 * @param id
 */
function locatAndPopup(id){
	var feature = featureLayer.getSource().getFeatureById(id);
	if(feature){
		var coordinate = feature.getGeometry().getFirstCoordinate();
		var fixCoord = _sumArray(coordinate,[0,-5000]);
		popup.setPosition(coordinate);
		content.innerHTML = feature.get('gname');
		centerAt(fixCoord);
	}
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

use("ctooj",function(cj){
	var size = [];
    cj.winResize(function(sw,sh){
		size[0] = sw;
		size[1] = sh;
        map.setSize(size);
    });
});



var bmsc = new trm.BaseMapSwitcherControl(
	{
		layerlist:[
			{
				name:"天地图·地图",
				layer:tdtlayerGroup,
				image:webroot + "images/tdt.png"
			},
			{
				name:"天地图·影像",
				layer:tdtImglayerGroup
			},
			{
				name:"天润云",
				layer:trmapLayerGroup
			}
		]
	}
)
//图层控制组件
map.addControl(bmsc);


//绘制组件
var drawControl = new trm.DrawControl({map:map,vector:vector,snap:true});
map.addControl(drawControl);


/**
 * 传入一个fid，则开始编辑该fid对应的ft
 */
//drawControl.bind_ft_by_fid();


/**
 * 创建一个ft并进入编辑状态
 * @type {trm.MapEditer}
 */
//drawControl.create_ft(geom,type,callback);



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
	//加载数量数据的方法
	function loaderFunction(extent, resolution, projection){
		featureSource.clear();
		var url = ctx+"/entmap/features/"+mapid;
		use("__/js/comm",function(comm){
		    comm.ajax(url,/*{"mapid":mapid}*/ null,function(tooken,data){
		         if(tooken.vl()){
		        	 var features = geoJsonFormat.readFeatures(data,{
		        	        dataProjection: dataprojection,
		        	        featureProjection: projection
		        	      });
		        	 featureSource.addFeatures(features);
		         }
		    })
		})
	}

	window.loaderFunction = function(){

	}

	// GeoJSON格式数据定义
	var geoJsonFormat =  new ol.format.GeoJSON();
	// 矢量图层数据源定义
	var featureSource = new ol.source.Vector({
		format : geoJsonFormat,
		loader : loaderFunction
	});
	// 地图要素数据图层定义
	featureLayer = new ol.layer.Vector({
		source : featureSource,
		style : defaultStyle
	});
	map.addLayer(featureLayer);
	////////////////////////////////-- FeatureLayer END -- /////////////////////////////////////


	//服务获取页面
	if(window.view == "service_getter"){
		return;
	}


	//地图编辑组件
	var mapediter = new trm.MapEditer({
		layer_field_add: 	_ctx(ctx + "/entattributemeta/addattributemeta"),//增加扩展属性url
		layer_field_del:	_ctx(ctx + "/entattributemeta/delete"),          //删除扩展属性url
		layer_field_get: 	_ctx(ctx+"/entattributemeta/getEntAttributemetaListField"),//获得基本属性  和扩展属性
		feature_create:		_ctx(ctx+"/entgraphics/editentgraphics"),
		feature_attr_save:	_ctx(ctx+"/entgraphics/editentgraphics"),
		feature_attr_get:	_ctx(ctx+"/entgraphics/getgraphics"),
		mapid:				mapid,

		//地图元素编辑界面，点击绘制按钮后执行到此处
		draw:function(type,tooken){
			//调用绘制组件启动绘制功能
			drawControl.start_draw(type,function(cancel,geom){
				
				//传递绘制的结果1取消绘制2完成绘制，此时geom有值
				tooken(cancel,geom,type);
			});
		},

		/**
		 * ft的geom修改
		 */
		ft_edit:function(ft,tooken){
			var vft = featureLayer.getSource().getFeatureById(ft.fid);
			if(!vft) {
				comm.msg("测试：未找到要编辑点，请刷新地图后尝试");
				return;
			}
			//调用绘制组件，进行矢量编辑
			drawControl.start_edit(vft,function(cancel,geom,stopEditFunc){
				//传递绘制的结果1取消绘制2完成绘制，此时geom有值

				console.log("Edit End:"+geom)
				tooken(cancel,geom,stopEditFunc);
			});

		},

		//数据更改了，通知其他界面同步
		dataChange:function(){
			featurelist.fresh();
			
			/*
			 * ============BEGIN============
			 * @auther jger
			 * @date 2015-12-24 15:41:35
			 * 
			 * 新增点完毕后清空编辑状态并刷新图层数据
			 * ---------------------------
			 */
			//清空临时编辑图层
			vector.getSource().clear({fast:true});
			//数据变更后刷新featureLayer
			loaderFunction(null,null,projection);
			/*
			 * =============END============
			 */
		}
	})
	map.addControl(mapediter);


	//元素列表界面
	var featurelist = new trm.FeatureListPanel({

		/**
		 * 图层id
		 */
		mapid:mapid,


		/**
		 * 地图名称
		 */
		mapname:mapname,// 前台传来的变量

		/**
		 * 初始化完成的回调
		 */
		init: $.noop,

		/**
		 * 矢量请求地址
		 */
		ajax_feature_list:_ctx(ctx+"/entgraphics/getgraphicsbymapid"),//获得所对应的 属性的值 由基本属性和扩展属性值组成

		/**
		 * 矢量字段请求地址
		 */
		ajax_column:_ctx(ctx+"/entattributemeta/getEntAttributemetaListField"),

		/**
		 * 批量导入文件上传地址，同时配置字段也在会提交到这里
		 */
		ajax_feature_import:_ctx(ctx+"/entfile/importExcel/"+mapid),

		/**
		 * 地图名称修改
		 */
		ajax_mapname_modify:_ctx(ctx+"/entmap/editEntUserMap"),

		/**
		 * 矢量字段删除
		 */
		ajax_feature_del:_ctx(ctx+"/entgraphics/deletebyid"),

		/**
		 * 删除时候执行的回调
		 * @param fid
		 */
		del:function(fid){
			loaderFunction(null,null,projection);
		},


		/**
		 * 选择某一行
		 * @param fid
		 * @param ft
		 */
		row_sel:function(fid,ft){
			//这里写高亮兴趣点，弹出pop的动作
			mapediter.bind_ft_by_fid(fid);
		},

		/**
		 * 当进行定位操作的时候
		 * @param ft 矢量对象
		 */
		locat:function(ft){
			//调用地图进行定位
			var id = ft.fid;
			locatAndPopup(id);
		},

		/***
		 * 每页请求个数
		 */
		req_rows:20,//每页显示20条

		/**
		 * 清除搜索
		 */
		clearSearch:function(geom){

			//清空临时编辑图层
			vector.getSource().clear({fast:true});
			//清空geom条件
		},


		/**
		 * 范围查询,勾选好了之后，调用tooken，进行搜索
		 * @param tooken
		 */
		scopeSearch:function(tooken,lastgeom){
			//保证一次查询只能有一个范围
			if(lastgeom){
				vector.getSource().clear({fast:true});
			}
			//调用绘制组件启动绘制功能
			drawControl.start_draw('Polygon',function(cancel,geom){

				tooken(geom);
			});
		},


		//矢量导入成功
		ft_imported:function(){
			loaderFunction(null,null,projection);
			mapediter.update_field();
		}
	});


	featurelist.getAllData(function(data){ });
	map.addControl(featurelist);

	/**
	 * 传入一个fid，会高亮当前fid对应的行，传入-1，会取消所有高亮。包括选择的高亮
	 */
	//featurelist.select_feature_by_fid(-1);
}();

/*
 * 初始化地图2
 */

var map2 = new ol.Map({
	layers : [tdtlayerGroup,tdtImglayerGroup,trmapLayerGroup,vector], //后面的图层会覆盖显示前面的图层
	target : 'map2',
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
	}).extend([]),
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
var featureLayer2;

var container2 = document.getElementById('popup2');
var content2 = document.getElementById('popup-content2');
var closer2 = document.getElementById('popup-closer2');

var popup2 = new ol.Overlay({
	element: container2,
    positioning:'top-center',
    offset:[0, -80]
});
map2.addOverlay(popup2);

/*closer2.onclick = function() {
	popup2.setPosition(undefined);
    closer2.blur();
    return false;
};*/



/**
 * 居中显示坐标
 * @param coordinates
 * @param fix 居中修正值 单位是projection单位
 */
function centerAt2(coordinates,fix){
	var view = map2.getView();
	var pan = ol.animation.pan({
        duration: 500,
        source: /** @type {ol.Coordinate} */ (view.getCenter())
      });
    map2.beforeRender(pan);
    view.setCenter(coordinates);
}
/**
 * 定位并弹出popup
 * @param id
 */
function locatAndPopup2(id,coord){
	var feature = featureLayer2.getSource().getFeatureById(id);
	if(feature){
		var coordinate = coord || getCordinate(feature);
		popup2.setPosition(coordinate);
		use("__/js/comm",function(co){
			$(content2).empty().append($(co.get_line_pop_html(feature.get('title'))));
			centerAt2(coordinate);
		})
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
	function loadRecord(extent, resolution, projection, para){
		featureSource2.clear();
		var url = ctx+"/map/featuresByRecord/"+para;
		use("_/tr_util",function(comm){
		    comm.ajax(url,null,function(tooken,data){
		         if(tooken.vl()){
		        	 var features = geoJsonFormat.readFeatures(data,{
		        	        dataProjection: dataprojection,
		        	        featureProjection: projection,
		        	        style : vectorStyle
		        	      });
		        	 featureSource2.addFeatures(features);
		        	 //定位
		        	 if(features.length){
		        		 locatAndPopup2(features[0].getId(), null);
		        	 }else{
		        		 popup2.setPosition(undefined);
		        	 }
		         }
		    })
		})
	}
	
	use("__/js/theme_detail",function(td){
		//当点击某个事件card时加载事件数据
		td.onRecClick = function(el){
			loadRecord(null,null,projection,el.id);
		}
		//初始化map是调整显示
//		td.onMapShow = function(isfirst){
//			if(isfirst){
//				var m = $("#map2");
//				!function(){
//					map2.setSize([m.width(),m.height()])
//				}.delayCall(180)
//			}
//		}
	})
	// 矢量图层数据源定义
	var featureSource2 = new ol.source.Vector({
		format : geoJsonFormat
	});
	var vectorStyle2 = new ol.style.Style({
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
	// 地图要素数据图层定义
	featureLayer2 = new ol.layer.Vector({
		source : featureSource2,
		style : vectorStyle2
	});
	
	map2.addLayer(featureLayer2);

    // 点击feature
    map2.on('click', function(evt) {
      var feature = map2.forEachFeatureAtPixel(evt.pixel,
          function(feature) {
            return feature;
          });
      if (feature) {
    	  locatAndPopup2(feature.get('id'),evt.coordinate);
      }
    });
}();



map2.fresh = function(){
    var m = this;
	var el = $(m.getTargetElement());
	m.setSize([el.width(),el.height()]);
}
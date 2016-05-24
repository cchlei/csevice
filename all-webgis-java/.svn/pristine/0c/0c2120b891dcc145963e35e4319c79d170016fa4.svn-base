/**
 * 地图包装
 * @type {{init: omap.init, centerAt: omap.centerAt}}
 */
define(function (require, exports, module) {

    //require("_/openlayers/ol-debug");
	//require("_/openlayers/trmap_styles");
	//require("_/openlayers/css/ol.css");

    var omap = ({
        __def:{
            /**
             * 初始化现显示的中心坐标
             */
            center:[108.94,34.26],
            /**
             * 要创建map的容器
             */
            mapid:"map",

            /**
             * popup的选择器或者html
             */
            popup_box:
                '<div class="ol-popup-nowrap">'+
                '   <a class="ol-popup-closer"></a>'+
                '   <div class="ol-pop-content"></div>'+
                '</div>'
        },
        init:function(config){
            var m = this;
            var sett = m.sett = $.extend({}, m.__def,config);
            m.projection = ol.proj.get('EPSG:3857');
            m.dataprojection = ol.proj.get('EPSG:4326');
            /* 矢量图层数据源定义*/
            m.geoJsonFormat =  new ol.format.GeoJSON();

            m.vectorStyle =  new ol.style.Style({
            	image: new ol.style.Icon({
            		anchor: [0.5, 1],
            		src: ctx+'/assets/images/icon_localtion.png'
            	}),
            	stroke : new ol.style.Stroke({
            		color : 'rgba(78,165,255,1)',
            		width : 3
            	}),
            	fill : new ol.style.Fill({
            		color : 'rgba(255, 255, 0, 0.5)'
            	})
            });

            m.featureSource = new ol.source.Vector({
                //format: m.geoJsonFormat,
                loader:function(extent, resolution, projection){}
            });
            // 地图要素数据图层定义
            m.featureLayer = new ol.layer.Vector({
                source : m.featureSource,
                style : m.vectorStyle
            });



            var projectionExtent = m.projection.getExtent();
            var size = ol.extent.getWidth(projectionExtent) / 256;
            var resolutions = new Array(20);
            var matrixIds = new Array(20);
            m.__matrixIds = matrixIds;

            m.__projectionExtent = projectionExtent;
            m.__resolutions = resolutions;

            //对应上面的坐标系的resolution
            resolutions[z] = size / Math.pow(2, z)/2;
            //matrixids就是缩放级别，但具体的体现不尽相同。比如geoserver中的TILEMATRIX要在前面拼上坐标系。
            //geoserver:http://XX/geoserver/gwc/service/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=YUHANG&STYLE=default&TILEMATRIXSET=EPSG:4326&&TILEMATRIX=EPSG:4326:15&TILEROW=5422&TILECOL=27333&FORMAT=image/png
            var gsmids = new Array(20);
            m.__gsmids = gsmids;
            for (var z = 0; z < 20; ++z) {
                resolutions[z] = size / Math.pow(2, z);
                matrixIds[z] = z;
                gsmids[z] = "EPSG:900913:"+z;
            }

            // 地图初始化显示的中心
            var center = ol.proj.transform(sett.center, m.dataprojection, m.projection);

            // 显示天地图的版权信息
            m.__tdtattribution = new ol.Attribution({
                html: '数据来源  &copy; <a href="http://www.tianditu.com">天地图</a>'
            });

            //天润云地图
            m.__trmattribution = new ol.Attribution({
                html: ' <a href="http://www.trgis.com">天润云&copy;</a> 审图号：陕0001'
            });


            // 矢量绘制层
            // 临时绘制数据使用
            var vector = new ol.layer.Vector({
                source : new ol.source.Vector(),
                style :  new ol.style.Style({
                	image: new ol.style.Icon({
                		anchor: [0.5, 1],
                		src: ctx+'/assets/images/icon_localtion.png'
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
            m.vector = vector;

            // 地图对象
            var map = new ol.Map({
                layers : m.__getLyaers([vector]), //后面的图层会覆盖显示前面的图层
                target : sett.mapid,
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

            // 点击feature
            map.on('click', function(evt) {
                var feature = map.forEachFeatureAtPixel(evt.pixel, function (feature) {
                        return feature;
                    });
                if (feature) {
                    m.onFeatureClick(feature);
                    //omap.popByFid(feature.getId(),feature.get('title'));
                }
            });

            m.format = new ol.format.WKT();

            m.map = map;
            m.map.addLayer(m.featureLayer);
            m.drawer = m.__getDrawer();
            m.popup = m.__getPopup();

            return m;
        },


        /**
         * 绘图组件相关class的定义
         * @returns {omap.__drawClass}
         * @private
         */
        __getDrawer:function(){
            var m = this;
            if(!m.__drawClass){
                m.__drawClass = function(opt_options) {

                    var ac = arguments.callee;
                    if(!ac.drawControl_def) {
                        ac.drawControl_def = { map: "", vector: ""}
                    }
                    var options = $.extend({},ac.drawControl_def,opt_options);
                    var mm = this;
                    var map_ = options['map'];
                    var vector_ = options['vector'];
                    var snapable = opt_options['snap'] || true;

                    /**
                     * 清除绘制和编辑状态
                     * jger 2016-05-17 
                     */
                    var cleanAllActive = function() {
                    	Draw.setActive(false);
                        Modify.setActive(false);
                    }
                    
                    
                    // 清除绘制状态 变为 可选择和编辑
                    var cleanDrawActive = function(){
                        Draw.setActive(null);
                        Modify.setActive(true);
                    }

                    /**
                     * @private
                     * 格式转换方法
                     *
                     * @param geom 转换对象
                     * @param source 源坐标系统 default EPSG:3857
                     * @param dist 目标坐标系统 default EPSG:4326
                     * @param type 转换类型 default wkt
                     *
                     * @author jger
                     * @date 2015-12-23
                     *
                     */
                    var _transformat = function(geom,source,dist,type){
                        if(!geom || !(geom instanceof ol.geom.Geometry)){
                            return "";
                        }
                        var formater;
                        var _source = source || "EPSG:3857";
                        var _dist = dist || "EPSG:4326";
                        var _type = type || 'wkt';

                        if(_type == "wkt"){
                            formater = new ol.format.WKT();
                        }
                        return formater.writeGeometry(geom.transform(_source,_dist));
                    };
                    mm.transformat = _transformat;

                    var Draw = {
                        init : function(){
                            map_.addInteraction(this.Point);
                            this.Point.setActive(false);
                            this.Point.on('drawend',function(e){
                            	//清除状态
                            	cleanAllActive();
                                var cloneFeature = e.feature.clone();//使用clone对象，保持原对象状态
                                var geometry = cloneFeature.getGeometry();
                                var wkt = mm.transformat(geometry);
                                mm._drawCallback.call(mm,false, wkt, e.feature);
                            });

                            map_.addInteraction(this.LineString);
                            this.LineString.setActive(false);
                            this.LineString.on('drawend',function(e){
                            	cleanAllActive();
                                var cloneFeature = e.feature.clone();//使用clone对象，保持原对象状态
                                var geometry = cloneFeature.getGeometry();
                                var wkt = mm.transformat(geometry);
                                mm._drawCallback.call(mm,false, wkt,e.feature);
                            });

                            map_.addInteraction(this.Polygon);
                            this.Polygon.setActive(false);
                            this.Polygon.on('drawend',function(e){
                            	cleanAllActive();
                                var cloneFeature = e.feature.clone();//使用clone对象，保持原对象状态
                                var geometry = cloneFeature.getGeometry();
                                var wkt = mm.transformat(geometry);
                                mm._drawCallback.call(mm,false, wkt,e.feature);
                            });
                        },
                        Point : new ol.interaction.Draw({
                            source : vector_.getSource(),
                            type : /** @type {ol.geom.GeometryType} */
                                ('Point')
                        }),
                        LineString : new ol.interaction.Draw({
                            source : vector_.getSource(),
                            type : /** @type {ol.geom.GeometryType} */
                                ('LineString')
                        }),
                        Polygon : new ol.interaction.Draw({
                            source : vector_.getSource(),
                            type : /** @type {ol.geom.GeometryType} */
                                ('Polygon')
                        }),
                        getActive : function() {
                            return this.activeType ? this[this.activeType].getActive() : false;
                        },
                        setActive : function(active,callback) {
                            if (active) {
                                this.activeType && this[this.activeType].setActive(false);
                                this[active].setActive(true);
                                this.activeType = active;
                            } else {
                                this.activeType && this[this.activeType].setActive(false);
                                this.activeType = null;
                            }
                        }
                    };
                    Draw.init();

                    //地图绘制和编辑控件
                    var Modify = {
                        init : function() {
                            this.select = new ol.interaction.Select();
                            map_.addInteraction(this.select);

                            this.modify = new ol.interaction.Modify({
                                features : this.select.getFeatures()
                            });
                            map_.addInteraction(this.modify);

                            this.setEvents();
                        },
                        setEvents : function() {
                            var selectedFeatures = this.select.getFeatures();

                            this.select.on('change:active',function() {
                                //selectedFeatures.forEach(selectedFeatures.remove,selectedFeatures);
                            });

                            this.modify.on('modifyend',function(){
                                var feature = selectedFeatures.item(0).clone();//获取clone的Feature
                                var geom_wkt = mm.transformat(feature.getGeometry());
                                mm._editCallback(false,geom_wkt,mm.callbacakToStopActive);
                            })
                        },
                        setActive : function(active) {
                            this.select.setActive(active);
                            this.modify.setActive(active);
                        },
                        setSelect : function(ft){
                            this.select.getFeatures().push(ft);
                        }
                    };
                    Modify.init();
                    Modify.setActive(false); //默认不激活可选择和可编辑状态
                    mm.callbacakToStopActive = function(){
                        Modify.setActive(false);
                    };


                    if(snapable){
                        //捕获插件
                        var snap = new ol.interaction.Snap({
                            source : vector_.getSource()
                        });
                        map_.addInteraction(snap);
                    }

                    var pointBtn = document.createElement('button');
                    pointBtn.className="float-left";
                    pointBtn.innerHTML = "○";

                    pointBtn.addEventListener('click', function(e) {
                        Modify.setActive(false);
                        Draw.setActive("Point");
                    })

                    var LineStringBtn = document.createElement('button');
                    LineStringBtn.className="float-left";
                    LineStringBtn.innerHTML = "/";

                    LineStringBtn.addEventListener('click', function(e) {
                        Modify.setActive(false);
                        Draw.setActive("LineString");
                    })

                    var PolygonBtn = document.createElement('button');
                    PolygonBtn.className="float-left";
                    PolygonBtn.innerHTML = "□";

                    PolygonBtn.addEventListener('click', function(e) {
                        Modify.setActive(false);
                        Draw.setActive("Polygon");
                    })

                    var modifyBtn = document.createElement('button');
                    modifyBtn.innerHTML = "M";

                    modifyBtn.addEventListener('click', function(e) {
                        Draw.setActive(null);
                        Modify.setActive(true);
                    })

                    // 图层切换stage
                    var element = document.createElement('div');
                    element.className = 'ol-control drawPanel';
                    element.appendChild(pointBtn);
                    element.appendChild(LineStringBtn);
                    element.appendChild(PolygonBtn);
                    element.appendChild(modifyBtn);

                    window.addEventListener('keydown',function(e){
                        if(e.which == 27) { //按下ESC取消编辑状态
                            cleanDrawActive();
                            mm._drawCallback.call(mm,true);
                        }
                    })

                    ol.control.Control.call(this, {
                        element : element,
                        target : options.target
                    });




                    //--------------------------------公开的方法---------------------------------
                    /**
                     * 绘制完成的回调
                     * @param cancel 如果为true，表示取消绘制
                     * @param geom 绘制结束时的geom
                     * @param feature 绘制结束时的 feature
                     * @private
                     */
                    mm._drawCallback = function(cancel,geom,feature){};

                    /**
                     * 矢量编辑完成的回调
                     * @param cancel 如果为true，表示确认编辑。false为取消编辑
                     * @param geom 编辑结束时的wkt_geom
                     * @private
                     */
                    mm._editCallback = function(cancel,geom){};


                    /**
                     * 开始绘制
                     * @param type 点线面  Point|LineString|Polygon
                     * callback 绘制完成后执行的回调
                     *      err 如果为true，意思是中途取消绘制
                     *      geom 绘制的结果
                     *      feature 绘制的结果
                     *      function(err,geom){}
                     */
                    mm.start_draw = function(type,callback){
                        if(!/^Point|LineString|Polygon$/.test(type)){
                            throw "传入错误的参数:type";
                            return;
                        }
                        mm._drawCallback = callback;
                        Modify.setActive(false);
                        Draw.setActive(type);
                    }

                    /**
                     * 编辑矢量
                     * @author jger
                     * @date 2015-12-22
                     */
                    mm.start_edit = function(ft,callback){
                        //开始编辑，开启选中编辑状态
                        mm._editCallback = callback;

                        //todo:创建完成之后，立即进入修改状态，屏蔽掉 2016年5月17日16:28:28
                        //Modify.setActive(true);
                        //Modify.setSelect(ft);
                    }


                    /**
                     * 退出编辑状态
                     */
                    mm.end_draw = function(){
                        cleanDrawActive();
                    }


                    /**
                     * 清除所绘制的东西
                     */
                    mm.clear_shape = function(){
                        options.vector.getSource().clear({fast:true});
                    }


                }
                ol.inherits(m.__drawClass, ol.control.Control);
            }

            return new m.__drawClass({
                map: m.map,
                vector: m.vector,
                snap:true
            });
        },

        /**
         * popup相关定义
         */
        __getPopup:function(){
            var m = this;
            m.pop_wrapper = $(m.sett.popup_box);

            if(!m.pop_wrapper.parent()){
                m.pop_wrapper.appendTo("body")
            }


            var popup = new ol.Overlay({
                element: m.pop_wrapper[0],
                autoPan: false,
                positioning:'bottom-center',
                offset:[0, 0],
                autoPanAnimation: {
                    duration: 250
                }
            });



            popup.content = m.pop_wrapper.find(".ol-pop-content");
            m.map.addOverlay(popup);

            return popup;
        },


        /**
         * 获取默认图层
         * @returns {Array}
         * @private
         */
        __getLyaers:function(other_layers){
            var m = this;
            var list = [];

            var trmattribution  = m.__trmattribution;
            var tdtattribution = m.__tdtattribution;
            var projectionExtent = m.__projectionExtent;
            var resolutions = m.__resolutions;
            var matrixIds = m.__matrixIds;
            var gsmids = m.__gsmids;



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
                        projection : m.projection,
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
                        projection : m.projection,
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
                        projection : m.projection,
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
                        projection : m.projection,
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
                        projection : m.projection,
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
                        projection : m.projection,
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


            list.push(tdtlayerGroup,tdtImglayerGroup,trmapLayerGroup);
            list = list.concat(other_layers);
            return list;
        },

        /**
         * 开始绘制
         * @param type 点线面  Point|LineString|Polygon
         * @param callback function(iscancel,geom,feature){}
         *      iscancel 是否属于中途取消绘制，geom 绘制完成后得到的数据字符串,feature
         *
         */
        startDraw:function(type,callback){
            var m = this;
            m.drawer.start_draw(type,callback);
        },


        /**
         * 开始编辑
         * @param ft
         * @param callback
         */
        startEdit:function(ft,callback){
            var m = this;
            m.drawer.start_edit(ft,callback);
        },

        /**
         * 结束绘制
         */
        endDraw:function(){
            var m = this;
            m.drawer.end_draw();
        },

        /**
         * 清除已经绘制的所有醒转
         * @param isIncludeLoaded 包含从数据加载的矢量
         */
        clearDraw:function(isIncludeLoaded){
            var m = this;
            m.drawer.clear_shape();
            if(isIncludeLoaded){
                m.featureSource.clear();
            }
        },

        /**
         * 居中显示坐标
         * @param coordinates {}
         * @param fix 居中修正值 单位是projection单位
         */
        centerAtByCoord:function(coordinates,fix){
            var m = this;
            var map = m.map;
            var view = map.getView();
            var pan = ol.animation.pan({
                duration: 360,
                source:view.getCenter()
            });
            map.beforeRender(pan);
            view.setCenter(coordinates);
        },


        /**
         * 通过fid获取feature
         * @param fid
         */
        getFeature:function(fid,callblack){
            var m = this;
            var feature = m.featureLayer.getSource().getFeatureById(fid);
            if(feature) (callblack || $.noop)(feature);
            return feature;
        },

        /**
         * 根据一个feature来居中地图
         * 其中feature必须是已经在图层中加载过的
         * @prarm fid featureid
         * @param callback(cordinate,ft)
         */
        centerAtByFtId:function(fid){
            var m = this;
            if(!callback) {
                callback = $.noop;
            }
            m.getFeature(fid,function(feature){
                var coordinate = feature.getGeometry().getFirstCoordinate();
                m.centerAtByCoord(coordinate);
            });
        },


        /**
         * 定位并弹出对应ft的pop
         * @param coordinate 经纬度 如果传入undefined则表示隐藏pop
         */
        pop :function(coordinate,content_html,doCenterAt){
            var m = this;
            m.popup.setPosition(coordinate);
            m.popup.content.empty().append(content_html);
            if(doCenterAt!==0 && doCenterAt!==0 && coordinate && coordinate.length==2){
            	m.centerAtByCoord(coordinate);
            }
           
        },


        /**
         * 获取ft或者geom的坐标
         * 如果geom或者ft的geom不是 Point，返回其中心点
         */
        getCoordinate:function(geom_ft){
            var m = this;
            var ft = geom_ft;
            if(typeof ft == "string"){
                ft = m.getFtByGeom(ft);
            }

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
        },
        
        
        popByFid:function(fid,html){
        	var m = this;
        	
        	m.pop(
        		m.getCoordinate(
        				m.getFeature(fid)
        		),
        		html
        	)
        },


        /**
         * 通过geom在地图上绘制
         * @param geom
         * "POLYGON((108.92034530639648 34.30118816655401,108.99106979370116 34.269984796992205,109.02471542358397 34.30742745002567,109.00239944458008 34.32132236979801,108.92034530639648 34.30118816655401))"、
         * @param doCenter 绘制完成之后是否进行居中
         */
        drawByGeom:function(geom,doCenter){
            var m = this;
            m.featureSource.clear();
            var ft = m.getFtByGeom(geom);
            m.featureSource.addFeature(ft);

            if( !doCenter) return;
            var coordinate = m.getCoordinate(ft);
            m.centerAtByCoord(coordinate);
        },
        /**
         * 通过geom在地图上绘制
         * @param data data 必有字段 id|geom geom形式如下
         *      "POLYGON((108.92034530639648 34.30118816655401,108.99106979370116 34.269984796992205,109.02471542358397 34.30742745002567,109.00239944458008 34.32132236979801,108.92034530639648 34.30118816655401))"、
         * @param doCenter 绘制完成之后是否进行居中
         * @param isClear 是否清除之前绘制的对象
         */
        drawByDataNoClear:function(data,doCenter,isClear){
            var m = this;
            var geom=data.geom;
            //m.featureSource.clear();
            var ft = m.getFtByGeom(geom);
            ft.setId(data.id);
            ft.setProperties(data);
            m.featureSource.addFeature(ft);
            ft.setStyle(defaultStyle);
            if(doCenter) {
                m.centerAtByCoord(m.getCoordinate(m.getFtByGeom(geom)));
                   //m.popByFid(data.id,data.title);
            }
        },
      
        /**
         * 转换geom到feature
         * @param geom
         */
        getFtByGeom:function(geom){
            var m = this;
            var f = m.format.readFeatureFromText(geom,{
                dataProjection: m.dataprojection,
                featureProjection: m.projection
            });
            f.setStyle(defaultStyle);
            return f;
        },


        /**
         * 通过数据地址
         * @param data ({"type":"FeatureCollection","features":[{"type":"Feature","id":"16063","properties":{"id":16063,"geom":"POINT(108.93321990966797 34.31451698217333)","addrname":"红庙坡"},"geometry":{"type":"Point","coordinates":[108.93321990966797,34.31451698217333]}},{"type":"Feature","id":"16064","properties":{"id":16064,"geom":"POINT(108.91502380371092 34.31111408145408)","addrname":"西北城角"},"geometry":{"type":"Point","coordinates":[108.91502380371092,34.31111408145408]}}]})
         */
        drawByData:function(data){
            var m = this;
            m.featureSource.clear();
            var features = m.geoJsonFormat.readFeatures(data,{
                dataProjection: m.dataprojection,
                featureProjection: m.projection,
                style : m.vectorStyle
            });
            m.featureSource.addFeatures(features);
        },

        /**
         * 通过远程地址加载数据
         * @param url
         * @param para
         */
        drawByUrl:function(url,para,callback){
            var m = this;
            //参数修正
            if(typeof para == "function"){
                callback = para;
                para = undefined;
            }

            if(!callback) callback = $.noop;

            use("_/tr_util",function(u){
                u.ajax(url,para,function(tooken,data){
                    if(tooken.vl()){
                        m.drawByData(data);
                        callback(data);
                    }
                })
            })
        },


        /**
         * 回调
         * 当ft被单击的时候执行
         * @param ft
         */
        onFeatureClick:function(ft){

        },


        /**
         * 设置地图尺寸或者刷新
         * @param size
         */
        setSize:function(size){
            var m = this;

            if(size === undefined){
                var el = $(m.map.getTargetElement());
                m.map.setSize([el.width(),el.height()]);
                return;
            }
            m.map.setSize(size);
        }
    });

    return omap;
});



//扩展自定义地图控件
window.trm = {};

/**
 * 底图切换控件
 *
 * @constructor
 * @extends ol.control.Control
 * @param control
 * options
 */
trm.BaseMapSwitcherControl = function(opt_options) {

    var def_map_type_img = webroot + "images/maptype.png";

    var options = opt_options || {};

    // 获取需要控制图层的指针
    var gplist = options.layerlist;

    var et = $("<div class='ol-control trm-mapswitcher'> <div class='btnCont feelmouse'></div> </div>");

    var btnCont = et.find(".btnCont");

    $.each(gplist,function(key,ele){
        var sel = $("<span></span>");
        sel.addClass("seq-" + key);
        sel.html(
            "<em>" + (ele.name || key) + "</em>" +
            "<img src="+ (ele.image||def_map_type_img) +" alt=''>"
        );
        btnCont.append(sel);
        sel.click(function(){
            var s = $(this);
            if(s.is(":nth-child(1)")) return;

            btnCont.prepend(s);
            $.each(gplist,function(key,ele){
                ele.layer.setVisible(false);
            });
            ele.layer.setVisible(true);

            btnCont.removeClass("feelmouse");
        });

        btnCont.delegate(":nth-child(1)","mouseover",function(){
            btnCont.addClass("feelmouse");
        })
    });

    ol.control.Control.call(this, {
        element : et[0]
    });
}
ol.inherits(trm.BaseMapSwitcherControl, ol.control.Control);


var drawControl_def = {
    /**
     * 地图
     */
    map:"",

    /**
     * 未知
     */
    vector:""
}


/**
 * 矢量绘制组件
 *
 * @constructor
 * @extends ol.control.Control
 * @param control
 * options
 */
trm.DrawControl = function(opt_options) {
    var options = $.extend({},drawControl_def,opt_options);
    var m = this;

    var map_ = options['map'];
    var vector_ = options['vector'];
    var snapable = opt_options['snap'] || true;

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
    m.transformat = _transformat;
    
    var Draw = {
        init : function(){
            map_.addInteraction(this.Point);
            this.Point.setActive(false);
            this.Point.on('drawend',function(e){
                cleanDrawActive();
                var cloneFeature = e.feature.clone();//使用clone对象，保持原对象状态
                var geometry = cloneFeature.getGeometry();
                var wkt = m.transformat(geometry);
                m._drawCallback.call(m,false, wkt);
            });

            map_.addInteraction(this.LineString);
            this.LineString.setActive(false);
            this.LineString.on('drawend',function(e){
                cleanDrawActive();
                var cloneFeature = e.feature.clone();//使用clone对象，保持原对象状态
                var geometry = cloneFeature.getGeometry();
                var wkt = m.transformat(geometry);
                m._drawCallback.call(m,false, wkt);
            });

            map_.addInteraction(this.Polygon);
            this.Polygon.setActive(false);
            this.Polygon.on('drawend',function(e){
                cleanDrawActive();
                var cloneFeature = e.feature.clone();//使用clone对象，保持原对象状态
                var geometry = cloneFeature.getGeometry();
                var wkt = m.transformat(geometry);
                m._drawCallback.call(m,false, wkt);
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
                selectedFeatures.forEach(selectedFeatures.remove,selectedFeatures);
            });
            
            this.modify.on('modifyend',function(){
            	var feature = selectedFeatures.item(0).clone();//获取clone的Feature
            	var geom_wkt = m.transformat(feature.getGeometry());
            	//console.log(geom_wkt);
            	m._editCallback(false,geom_wkt,m.callbacakToStopActive);
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
	m.callbacakToStopActive = function(){
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
            m._drawCallback.call(m,true);
        }
    })

    ol.control.Control.call(this, {
        element : element,
        target : options.target
    });


    /**
     * 绘制完成的回调
     * @param cancel 如果为true，表示取消绘制
     * @param geom 绘制结束时的geom
     * @private
     */
    m._drawCallback = function(cancel,geom){};
    
    /**
     * 矢量编辑完成的回调
     * @param cancel 如果为true，表示确认编辑。false为取消编辑
     * @param geom 编辑结束时的wkt_geom
     * @private
     */
    m._editCallback = function(cancel,geom){};


    /**
     * 开始绘制
     * @param type 点线面  Point|LineString|Polygon
     * callback 绘制完成后执行的回调
     *      err 如果为true，意思是中途取消绘制
     *      geom 绘制的结果
     *      function(err,geom){}
     */
    m.start_draw = function(type,callback){
        if(!/^Point|LineString|Polygon$/.test(type)){
            throw "传入错误的参数:type";
            return;
        }
        m._drawCallback = callback;
        Modify.setActive(false);
        Draw.setActive(type);
    }
    
    /**
     * 编辑矢量
     * @author jger
     * @date 2015-12-22
     */
    m.start_edit = function(ft,callback){
    	//开始编辑，开启选中编辑状态
    	m._editCallback = callback;
    	Modify.setActive(true);
    	Modify.setSelect(ft);
    }


    /**
     * 退出编辑状态
     */
    m.cleanDrawActive = function(){
        cleanDrawActive();
    }
}
ol.inherits(trm.DrawControl, ol.control.Control);


/**
 * 地图编辑
 * @constructor
 */
trm.MapEditer = function(config){
    var m = this;
    m.sett = $.extend({},trm.MapEditer.default,config);
    m.init();
}


/**
 * 地图编辑默认配置
 * @type {{}}
 */
trm.MapEditer.default = {

    mapid:"998998",

    /**
     * 数据更新了
     */
    dataChange:function(msg){ },

    /**
     * 地图字段类型数字到文本的映射
     */
    num_to_field_type: {
        "1": "文本",
        "2": "整数",
        "3": "小数",
        "4": "日期"
    },


    /**
     * 开始绘制的回调
     * @param type 绘制的类型 Point|LineString|Polygon
     * @param tooken 暂空
     */
    draw:function(type,tooken){ },

    /**
     * 矢量geom修改
     */
    ft_edit:function(ft,tooken){ },


    /**
     * 矢量批量导入成功
     */
    ft_imported:function(){ },


    /**
     * 图层字段增加
     *
     */
    layer_field_add: webroot + "data/sava_profile.json",

    /**
     * 图层字段删除
     */
    layer_field_del: webroot + "data/sava_profile.json",

    /**
     * 图层字段的获取
     */
    layer_field_get: webroot + "data/feature_fields.json",


    /**
     * 矢量创建
     */
    feature_create: webroot + "data/feature_single.json",

    /**
     * 矢量属性的保存
     */
    feature_attr_save: webroot + "data/sava_profile.json",


    /**
     * 矢量属性的获取
     */
    feature_attr_get: webroot + "data/feature_single.json"


};
ol.inherits(trm.MapEditer, ol.control.Control);


$.extend(trm.MapEditer.prototype,{
    init:function() {
        var m = this;



        var el = $("<div class='ol-control mapEditer'></div>");

        m.el = el;

        m.controllerid = "avalon_" + new Date().getTime();

        //地图编辑功能
        var tpl = "" +
            "<em class='loading'></em>" +
            "<div class='editor' ms-controller=" + m.controllerid + ">" +
            "   <div class='topbar'>" +
            "       <span class='btnCont'>" +
            "           <em class='huizhi cur'>对象绘制</em>" +
            "           <em class='ziduan'>图层字段</em>" +
            "       </span>" +
            "       <em class='collapse'></em>" +
            "   </div>" +
            "	<div class='feature_edit'>" +
            "		<div class='adder'><em class='dot'></em><em class='line'></em><em class='area'></em></div>" +
            "		<div class='editer' ms-class-1='ex:!ft_edited'>" +
            "			<p class='header'><em>属性名称</em> <span>属性值</span></p>" +
            "			<p ms-repeat-col='attrs'>" +
            "               <em>{{col.title}}</em> " +
            "               <span>" +
            "                   <input " +
            "                       ms-duplex='ft[col.id]' "   +
            "                       ms-attr-field='col.field' "    +
            "                       placeholder='请输入' " +
            "                       ms-attr-readonly='is_edit_mode?0:\"readonly\"' " +
            "                       ms-class='readonly:!is_edit_mode'" +
            "                       ms-class-2='laydate-icon type-date:col.type==4'" +
            "                       ms-class-3='type-number:col.type==3 || col.type==2'" +
            "                   />" +
            "               </span>" +
            "           </p>" +
            "		</div>" +
            "		<div class=botbar><em class='feature_attr_save' ms-visible='ft_edited'>保存</em></div>" +
            "	</div>" +
            "	<div class='fieldpanel'>" +
            "		<div class='botbar'><em class='attr_add'></em></div>" +
            "		<table class='header'>" +
            "			<tr>" +
            "				<th>字段名称</th>" +
            "				<th>字段别名</th>" +
            "				<th>字段类型</th>" +
            "				<th>操作</th>" +
            "			</tr>" +
            "		</table>" +
            "		<div class='scroller'>" +
            "			<table class='rows'>" +
            "				<tr ms-repeat=attrs>" +
            "					<td>{{el.field}}</td>" +
            "					<td>{{el.title}}</td>" +
            "					<td>{{el.type | field_type}}</td>" +
            "					<td><em ms-visible='!el.fixed' class='btn del' ms-click='attr_remove(el,$index)'></em></td>" +
            "				</tr>" +
            "				<tr>" +
            "					<td><input ms-duplex='f_add.field' placeholder='字段'/></td>" +
            "					<td><input ms-duplex='f_add.title' placeholder='字段名称'/></td>" +
            "					<td>" +
            "						<select ms-duplex='f_add.type'>" +
            "							<option value='1' selected='selected'>文本型</option>" +
            "							<option value='2'>数值型</option>" +
            "							<option value='3'>小数型</option>" +
            "							<option value='4'>日期型</option>" +
            "						</select>"+
            "					</td>" +
            "					<td><em class='btn ok' ms-click='field_add'></em></td>" +
            "				</tr>" +
            "				<tr class='add'><td colspan='4'><em class='add'></em></td></tr>" +
            "			</table>" +
            "		</div>" +
            "	</div>" +
            "</div>"
        ;


        el.append(tpl);

        ol.control.Control.call(this, {
            element: el[0]
        });

		var tab_body = el.find(".fieldpanel,.feature_edit");


        //折叠按钮
        el.delegate(".collapse","click",function(){
            el.toggleClass("collapse");
        });


        //tab切换
        el.delegate(".topbar>.btnCont>em","click",function(){
            var em = $(this);
            em.addClass("cur");
            em.siblings().removeClass("cur");
            var index = em.index();
            tab_body.hide();
            tab_body.eq(index).show();
        });


        //添加按钮
        m.adder_btns = m.el.find(".adder>*");

        /**
         * 绘制开始的时候，作为tooken传出，供设置geom
         * @param cancel 为true表示取消绘制
         * @param geom   几何数据
         * @param type   绘制的类型
         */
        m.setGeom = function(cancel,geom,type){
            m.adder_btns.removeClass("press");
            if(cancel){
                //取消绘制
                return;
            }
            m.create_ft(geom,type);
        }


        //单击点线面绘制
        m.el.delegate(".dot","click",function(){
            $(this).addClass("press");
            m.sett.draw("Point", m.setGeom);
        })

        m.el.delegate(".line","click",function(){
            $(this).addClass("press");
            m.sett.draw("LineString",m.setGeom);
        })

        m.el.delegate(".area","click",function(){
            $(this).addClass("press");
            m.sett.draw("Polygon",m.setGeom);
        })

        /**
         * 保存当前正在编辑的ft
         */
        el.delegate(".feature_attr_save","click",function(){
            m.save_ft();
        });


        use(["_/avalon/avalon.min","ctool","$/layer"],function(a,cl,l){
            /**
             * 增加自定义过滤器
             * @param str
             */
            avalon.filters.field_type = function(str){
                return m.sett.num_to_field_type[str];
            }

            var vm = m.vm = avalon.define({
                $id:m.controllerid,

                /**
                 * 当前图层的属性列表
                 */
                attrs:[],

                /**
                 * 正在增加的field
                 */
                f_add:{
                    field:"",
                    title:"",
                    type:1
                },

                /**
                 * 正在编辑的feature
                 */
                ft:{},

                /**
                 * 存在正在修改的元素，并且元素被修改过。用来展示或者隐藏保存按钮
                 */
                ft_edited:0,

                /**
                 * 控制和标识是否处于可编辑状态
                 */
                is_edit_mode:0,


                /**
                 * 删除图层属性（地图属性）
                 * @param el
                 * @param index
                 */
                attr_remove:function(el,index){
                    use(comm_url,function(c){
                        c.confirm("确定要删除该字段吗?",function(cancel){
                            if(cancel)  return;
                            c.ajax(m.sett.layer_field_del,{aid:el["id"],field:el.field,mapid: m.sett.mapid},function(t,data){
                                if(t.vl()){
                                    vm.attrs.removeAt(index);
                                    m.sett.dataChange("field_remove");
                                    //m._fresh_vm_ft_field();
                                }
                            })
                        })
                    })
                },


                /**
                 * ft属性被修改的时候调用到这里
                 * @param field
                 * @param input
                 */
                ft_modify:function(field,input){

                },

                /**
                 * 增加字段
                 */
                field_add:function(){
                    use(comm_url,function(c){
                        var mod = vm.f_add.$model;

                        if(!mod.field){
                            c.msg("字段必须填写");
                            return;
                        }

                        if(!mod.field.length>32){
                            c.msg("字段长度不能大于32个字符");
                            return;
                        }

                        if(/[^a-zA-Z\d]/.test(mod.field)){
                            c.msg("字段只能用英文和数字的组合");
                            return;
                        }

                        if(!mod.title){
                            c.msg("字段名称必须填写");
                            return;
                        }

                        if(!mod.title.length>32){
                            c.msg("字段名称长度不能大于32");
                            return;
                        }

                        mod.mapid = m.sett.mapid;

                        c.ajax(m.sett.layer_field_add,mod,function(t,d){
                            if(t.vl()){
                                mod.id = d.id;
                                vm.attrs.push(c.clone(mod));

                                vm.f_add.type   =   1;
                                vm.f_add.field  =   "";
                                vm.f_add.title  =   "";
                            }
                            m.sett.dataChange("field_add");
                            //m._fresh_vm_ft_field();

                        });
                    });
                }
            });


            m.vm.ft.$watch("$all",function(a,b,c){
                console.log(a,b,c);
            })



            avalon.scan();
            m.update_field();


            /**
             * 监控矢量属性文本框上按键操作
             */
            m.el.delegate(".editer input","keydown",function(){
                if(m.vm.ft.fid)
                    m.vm.ft_edited = 1;
            });


            /**
             * 监控ft的变化
             */
            use("ctool",function(cl){
                cl.object_diff(
                    1000,
                    function(){ return m.vm.ft; },
                    function(result){

                        //id的变化不认为是属性改变
                        if(result.fid) return;

                        //第一次出发 忽略
                        if(result.undefined === "") return;

                        m.vm.ft_edited = 1;
                    }
                );
            });


            /**
             * 限制数字类型只能输入数字
             */
            m.el.delegate(".type-number","keydown,keyup,blur",function(){
                var it = this;
                it.value = it.value.replace(/[^\d\.]/g,"");
            });




            //日期控件
            use("_/laydate/laydate.cmd",function(ld){
                m.el.delegate("input.laydate-icon","click",function(){
                    laydate();
                });
            });
        });


        /**
         * 刷新字段
         */
        m.update_field = function(){
            use(comm_url,function(comm){
                comm.ajax(m.sett.layer_field_get,{mapid: m.sett.mapid},function(tooken,data){
                    el.addClass("inited");
                    if(tooken.vl()){
                        m.vm.attrs = data.fields.filter(function(el){
                            return !el.hidden;
                        });

                        m._fresh_vm_ft_field();
                    }
                })
            })
        }



        /**
         * 绑定一个矢量（进行编辑状态）
         */
        m.bind_ft = function(ft,bind_new_ft){
            if(m.vm.ft_edited){
                use(comm_url,function(c){
                    c.confirm("当前矢量存在尚未保存的修改，是否保存？",function(cancel){
                        if(cancel){
                            m.vm.ft_edited = 0;
                            bind();
                        }else{
                            m.save_ft(function(){
                                bind();
                            });
                        }
                    });
                });
                return;
            }



           /* if(m.vm.ft_edited){
                m.save_ft(function(){
                    bind();
                });
                return;
            }*/

            bind();


            function bind(){
                m.vm.ft = ft;
                m.vm.is_edit_mode = 1;
            }
        }


        /**
         * 停止编辑的模式的方法
         */
        m.__stop_vector_edit = $.noop;

        /**
         * 当geom被修改的时候回执行到这里
         */
        function on_ft_geom_edit(cancel,geom,stopEditFunc){
            m.vm.ft_edited = 1;
            m.vm.ft.geom = geom;
            m.__stop_vector_edit = stopEditFunc;
        }

        /**
         * 通过一个fid绑定远程的ft
         */
        m.bind_ft_by_fid = function(fid){
            m.get_ft(fid,function(data){
                data.fid = fid;
                m.bind_ft(data);
                m.__stop_vector_edit();
            })
        }


        /**
         * 创建一个ft 并进入编辑状态
         */
        m.create_ft = function(geom,type,callback,ignore_ft_edited){
            callback = callback|| $.noop;

            //存在正在编辑的矢量
            if(m.vm.ft_edited && ignore_ft_edited){
                use(comm_url,function(c){
                    c.confirm("当前矢量存在尚未保存的修改，是否保存？",function(cancel){
                        if(cancel){
                            m.create_ft(geom,type,callback,true);
                            m.vm.ft_edited = 0;

                        }else{
                            m.save_ft(function(){
                                m.create_ft(geom,type,callback);
                            });
                        }
                    });
                });
                return;
            }

            var ft = {
                gname:"未命名标注",
                geom:geom,
                type:type||1,
                mapid: m.sett.mapid
            };

            use(comm_url,function(c){
                c.ajax(m.sett.feature_create,ft,function(t){
                    if(t.vl()){
                        ft.fid = t.data["id"];
                        m.bind_ft(ft,true);
                        m.sett.dataChange();
                        callback(t.data);
                        m.vm.is_edit_mode = 1;
                    }
                })
            })
        }


        /**
         * 获取一个ft
         * @param fid
         * @param callback
         */
        m.get_ft = function(fid,callback){
            callback = callback || $.noop;
            use(comm_url,function(c){
                c.ajax(m.sett.feature_attr_get,{mapid: m.sett.mapid, fid:fid},function(t){
                    if(t.vl()){
                        callback(t.data);
                    }
                })
            })
        }


        /**
         * 保存当前正在编辑的ft
         */
        m.save_ft = function(callback){
            callback = callback|| $.noop;
            use(comm_url,function(c){
                var para = {
                    mapid: m.sett.mapid
                };

                $.each(m.vm.attrs.$model,function(key,ele){
                    para[ele.id] = m.vm.ft[ele.id];
                });
                
                para.fid = m.vm.ft.fid;
                para.geom = m.vm.ft.geom;

                //$.extend(para,m.vm.ft.$model);
                c.ajax(m.sett.feature_attr_save,para,function(t){
                    if(t.vl()){
                        m.__stop_vector_edit();
                        c.msg("保存成功");
                        m.vm.ft_edited = 0;
                        m.vm.is_edit_mode = 0;
                        m.sett.dataChange("ft_saved");
                        callback(t.data);
                    }
                });
            });

        }


        /**
         * 刷新vm上面绑定的当前的ft的字段
         */
        m._fresh_vm_ft_field = function(col){
            var ft = m.vm.ft.$model;
            $.each(col || m.vm.attrs.$model,function(key,ele){
                ft[el.id] = "";
            });
            m.vm.ft = ft;
        }
    }
});







/**
 * 要素列表面板
 * @param config
 * @constructor
 */
trm.FeatureListPanel = function(config){
    var m = this;
    m.sett = m.config = $.extend({},trm.FeatureListPanel.default,config);
    m.init();
}

/**
 * 元素列表的默认配置
 * @type {{init: (jQuery.noop|Function)}}
 */
trm.FeatureListPanel.default = {
    /**
     * 地图id
     */
    mapid:"9999",

    mapname:"未命名地图",

    /**
     * 初始化完成
     */
    init: $.noop,

    /**
     * 矢量列表
     */
    ajax_feature_list:"../data/feature_list.json",

    /**
     * 分页页数量
     */
    req_rows:24,

    /**
     * 矢量删除
     */
    ajax_feature_del: webroot + "data/sava_profile.json",

    /**
     * 获取列
     */
    ajax_column:"../data/feature_fields.json",

    /**
     * 矢量批量导入
     */
    ajax_feature_import:"",

    /**
     * 地图名称更改
     */
    ajax_mapname_modify:webroot + "data/sava_profile.json",

    /**
     * 范围查询回调
     * @params tooken 使用tooken来传回范围参数，并 执行查询
     */
    scopeSearch:function(tooken){ },


    /**
     * 元素被删除的时候执行的回调
     * @param fid
     */
    del:function(fid){ },

    /**
     * 清除搜索条件
     */
    clearSearch:function(){ },


    /**
     * ft数据加载后执行回调，按页分
     * @param data
     */
    dataLoaded:function(data){ },

    /**
     * 列更新
     */
    columnUpdate:function(){},


    /**
     * 选择某行
     */
    row_sel:function(fid,ft){ },


    /**
     * 当进行定位操作的时候
     * @param ft
     */
    locat:function(ft){ }
}

ol.inherits(trm.FeatureListPanel, ol.control.Control);


var comm_url = "__/js/comm";

$.extend(trm.FeatureListPanel.prototype,{
    /**
     * 初始化
     */
    init:function(){
        var m = this;

        m.avalonid = "avalon_flp" + new Date().getTime();
        var el = $(
            "<div class='ol-control featureListPanel' ms-controller="+ m.avalonid +">" +
            "   <em class='loading'></em>" +
            "	<h3>" +
            "       <em>图层名称：</em>" +
            "       <i class='edit' ms-class-1='save:mapname_status_edit' ms-click='edit_or_sava_mapname()'></i> " +

            "       <input ms-css-width='mname_leng' ms-class-1='editing:mapname_status_edit' ms-attr-readonly='mapname_status_edit?\"\":\"true\"' class='layername' ms-duplex-strleng_mx_32='mapname'/> " +
            "       <span class='arrow'></span>" +
            "   </h3>" +
            "	<div class=tab>" +
            "       <ul class='btnCont'><li class='cur'><em>要素<br>查询</em></li><li><em>要素<br>导入</em></li></ul>" +
            "       <ul class=bodyCont>" +
            "           <li>" +
            "               <div class=condition>" +
            "                   <span class='input border'>" +
            "                       <input class='keyword' placeholder='字段'/>" +
            "                       <em class='bt dosearch'></em>" +
            "                       <em class='bt fresh'>刷新</em>" +
            "                   </span>" +
            "                   <em class='geo_search'>范围查询</em>" +
            "                   <em class='cancel_search'>×清除</em>" +
            "               </div>" +
            "               <div class='dtcont'><i class='ld'></i><i class='empty' ms-class-2='show:!current_data.totalCount'>矢量列表为空···</i></div>" +
            "               <div class='pagecont'><ul></ul></div>" +
            "           </li>" +
            "           <li class='feature_importer' style='display: none;' ms-controller='feature_importer'>" +
            "               <div class=arow>" +
            "                   <span class='label' style='margin-right: 2em'>选择文件</span> " +
            "                   <span class='choose' style='cursor: pointer;'>　选　择　</span>" +
            "                   <span class='label red'>模板</span>" +
            "               </div>" +
            "               <div class='crow'>" +
            "                   <span class='label blue'>格式说明</span> <br/>" +
            "					<span class=label>1、支持Excel文件格式；第一行为中文标题行，第二行为英文标题行（若没有请空一行），第三行开始为正式数据。</span>" +
            "					<span class=label>2、必须有编号、标题、经度、纬度，请将对应的列填写在下面的表格中。编号用于下次更新数据使用，若编号一样，则会替换原有数据，没有则会新增；标题为要素显示名称使用；经纬度用于地图定位。</span>" +
            "               </div>" +
            "               <div class='brow'>" +
            "                   <span class='label'>匹配字段</span> <br/>" +
            "					<span class=input><em class=lb>编号</em><input  ms-duplex=form.identifykey /><em class=lb>*</em></span>" +
            "					<span class=input><em class=lb>标题</em><input  ms-duplex=form.title       /><em class=lb>*</em></span>" +
            "					<span class=input><em class=lb>x坐标</em><input ms-duplex=form.x           /><em class=lb>*</em></span>" +
            "					<span class=input><em class=lb>y坐标</em><input ms-duplex=form.y           /><em class=lb>*</em></span> <br/>" +
            "                   <em class='do_feature_import gray'>确&nbsp;&nbsp;定</em>" +
            "               </div>" +
            "           </li>" +
            "       </ul>" +
            "   </div>" +
            "</div>"
        );


        m.vm;

        var def_current_data = {totalCount:-1,data:[]};

        use(["_/avalon/avalon.min",comm_url],function(a,c){
            var vm = m.vm = avalon.define({
                $id:m.avalonid,


                current_data:def_current_data,

                /**
                 * 当前高亮的ft
                 */
                fid_light:-1,

                mapname:m.sett.mapname,

                mapname_status_edit:false,
                mname_leng:m.sett.mapname.length + "em",

                /**
                 * 修改和保存地图名称
                 */
                edit_or_sava_mapname:function(){

                    if(vm.mapname_status_edit){
                        if(vm.mapname.length>11 || m.mapnaem_input.val().length>11){
                            return c.tips(m.mapnaem_input,"地图名称不能大于11个字");
                        }

                        if(!vm.mapname){
                            vm.mapname = "未命名地图";
                        }
                        c.ajax(m.sett.ajax_mapname_modify,{name:vm.mapname,mapid:m.sett.mapid},function(t){
                            if(t.vl()){
                                vm.mapname_status_edit = false;
                                c.msg("地图名称已修改");
                            }
                        })
                    }else{
                        vm.mapname_status_edit = true;
                    }
                }
            });



            //限制最大字符的拦截器
            avalon.duplexHooks.strleng_mx_32 = {
                get: function (val) {
                    if (val.length > 32) {
                        return val.substr(0, 32);
                    }
                    return val;
                }
            }

            var o_mapname;
            vm.$watch("mapname",function(v,ov){
                o_mapname = ov;
                vm.mname_leng = (v.replace(/[^\x00-\xff]/g,"**").length/2 + 2) + "em";
            })

            avalon.scan();
        });


        m.el = el;

        m.mapnaem_input = m.el.find(".layername");

        //展开和收缩
        el.delegate(">h3>.arrow","click",function(){
            el.toggleClass("expand");
        });

        el.find(">h3>.arrow").click();

        //tab切换
        el.tab_bodys = el.find(".bodyCont>li");
        el.delegate(".btnCont>li","click",function(){
            var m = $(this);
            var index = m.index();
            m.addClass("cur").siblings().removeClass("cur");
            el.tab_bodys.hide().eq(index).show();
        });

        //范围查询的令牌
        m.scopeSearchTooken = function(geom){
            m.reqpara.geom = geom;
            m.fresh();
        }

        //范围查询
        m.el.delegate(".geo_search","click",function(){
            m.sett.scopeSearch(m.scopeSearchTooken, m.reqpara.geom)
        });


        //tgrid的loading
        m.tgrid_loading = m.el.find(".dtcont>.ld");



        //关键字
        el.keyword = el.find(".keyword");

        //请求参数
        m.reqpara = {
            mapid: m.sett.mapid
        };

        //上传相关代码
        use(["_/webuploader/webuploader.min","ctool","$/layer"],function(a,cl,l){
            var uploader;

            var picker = el.find(".choose");

            cl.run_until(666,function(){
                if(picker.is(":visible")){

                    uploader =  WebUploader.create({

                        //swf: BASE_URL + '/js/Uploader.swf',

                        // 文件接收服务端。
                        server: m.sett.ajax_feature_import,
                        // 选择文件的按钮。可选。
                        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                        pick: {id:el.find(".choose"),multiple:false},
                        resize: false,
                        duplicate:true,
                        fileSizeLimit: 1 * 1024 * 1024,    // 1 M
                        fileSingleSizeLimit: 1 * 1024 * 1024,    // 1 M,
                        accept: {
                            title: 'Excel',
                            extensions: 'xls,xlsx',
                            mimeTypes: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                        }
                    });



                    var fname_label;
                    uploader.on("fileQueued",function(file){
                        var a = uploader.getFiles("inited");
                        if(a.length > 1){
                            uploader.removeFile(a[0],true);
                        }
                        if(!fname_label){
                            picker.before("<span class='fname label bd gray'><b></b><i></i><em>0%</em></span>");
                            fname_label = picker.prev();
                            fname_label.em = fname_label.find("em");
                            fname_label.i = fname_label.find("i");
                            fname_label.b = fname_label.find("b");
                            fname_label.setnum = function(n){
                            	fname_label.em.html("");
                            	if(n>0){
                            		fname_label.b.append("请稍等……");
                            	}
                                fname_label.i.css({
                                	width:n*100 + "%"
                                });
                            }

                            window.fname_label = fname_label;
                        }
                        fname_label.removeClass("err");
                        fname_label.b.html(file.name);
                        fname_label.setnum(0);
                        m.btn_do_feature_import.removeClass("gray");
                    });


                    // 文件上传过程中创建进度条实时显示。
                    uploader.on( 'uploadProgress', function( file, percentage ) {
                        fname_label.setnum(percentage);
                    });

                    uploader.on( 'uploadSuccess', function( file, response) {
                        $( '#'+file.id ).find('p.state').text('已上传');
                        m.fresh();
                        if(response.result=="success"){
                            fname_label.b.html("　上传成功　导入"+ response.count +"行");
//                            fname_label.em.html("");
                            //alert("已成功导入"+response.count+"行。");

                            m.sett.ft_imported();
                        }else{
                            var errmsg = "上传出错　请仔细查看所填字段是否为Excel文件中存在的的标题列";
                            fname_label.b.html(errmsg);
                            fname_label.addClass("err");
                            uploader.reset();

                            use(comm_url,function(comm){
                                comm.layer.tips(
                                    errmsg,
                                    fname_label,
                                    {
                                        tips: [1, '#f00'],
                                        time: 4000
                                    }
                                );
                            })

                        }

                        m.btn_do_feature_import.removeClass("gray");
                    });

                    uploader.on( 'uploadError', function( file ) {
                        fname_label.b.append("　上传出错");
                        fname_label.addClass("err");
                        m.fresh();
                    });

                    //确认上传按钮
                    m.btn_do_feature_import = m.el.find(".do_feature_import");


                    //确认上传
                    m.btn_do_feature_import.click(function(e){

                        var self = $(this);
                        if(self.is(".gray")){
                            return;
                        }

                        var fl = uploader.getFiles("inited");
                        if(fl.length<1){
                            l.msg("请先指定要上传的文件");
                            return;
                        }

                        var f = vm_feature_importer.form.$model;


                        if(!f.identifykey){
                            l.msg("编码字段不能为空");
                            return;
                        }

                        if(!f.title){
                            l.msg("名称字段不能为空");
                            return;
                        }

                        if(!f.x){
                            l.msg("x坐标字段不能为空");
                            return;
                        }

                        if(!f.y){
                            l.msg("y坐标字段不能为空");
                            return;
                        }

                        m.btn_do_feature_import.addClass("gray");
                        uploader.option('formData', f);
                        uploader.upload();
                    })

                    return true;
                }
            });
        });


        //
        var vm_feature_importer;
        seajs.use("_/avalon/avalon.min",function(){
            vm_feature_importer = avalon.define({
                $id:"feature_importer",
                form:{
                    identifykey:"",
                    title:"",
                    x:"",
                    y:""
                }
            });

            avalon.scan();
        });


        var dt = el.find(".dtcont");
        seajs.use(["__/js/enterprise_comm","_/tgrid/_","$/splitReq"],function(comm,tgrid,Sr){
            m.util = comm;
            var tg = m.tgrid = new tgrid(dt,{
                rowClick:function(index,el){
                    m.sett.row_sel(el.fid,el);
                },
                orderChange:function(by_index,way,by){
                    m.reqpara.sort = by;
                    m.reqpara.direction = way;
                    m.sr.tdoo();
                }
            });

            m.dic_ftid_ft = {};


            m.pagecont = m.el.find(".pagecont");

            var sr = m.sr = new Sr({
                container:".pagecont>ul",
                reqPath: m.sett.ajax_feature_list,
                rows: m.sett.req_rows,
                firstReqAuto:false,
                reqType:"POST",
                onReq:function(para){
                    m.reqpara.keyword = el.keyword.val();
                    $.extend(para,m.reqpara);
                    m.vm.current_data = def_current_data;
                },
                onRece:function(string){

                },
                onData:function(data){
                    m.tg_loading(false);
                    m.tgrid.data(data.data);
                    m.sett.dataLoaded(data.data);

                    if(data.data.length){
                        m.pagecont.show();
                    }else{
                        m.pagecont.hide();
                    }

                    m.vm.current_data = data;

                    $.each(data.data,function(key,ele){
                        m.dic_ftid_ft[ele.fid] = ele;
                    });
                }
            });

            sr.tdoo = m.util.cl.throttle(2000,function(){
                sr.doo();
            });


            /**
             * 矢量删除
             */
            tg.el.delegate(".option .del","click",function(){
                var tr = $(this).parents("tr:first");
                var fid = tr.attr("fid");
                use(comm_url,function(c){
                    c.confirm("确定要删除该标注吗？",function(no){
                        if(no) return;
                        c.ajax(m.sett.ajax_feature_del,{fid:fid},function(t){
                            if(t.vl()){
                                tg.delRowByFid(fid);
                                m.sett.del(fid);
                                if(!tg.data().length){
                                    m.fresh();
                                }

                            }
                        })
                    })
                })
            });


            /**
             * 矢量定位
             */
            tg.el.delegate(".option .loc","click",function(){
                var tr = $(this).parents("tr:first");
                var fid = tr.attr("fid");
                m.sett.locat( m.dic_ftid_ft[fid]);
            })

            m.fresh();
        });


        //单击搜索
        el.delegate(".dosearch","click",function(){
            m.fresh();
        });

        //清除查询
        m.el.delegate(".cancel_search","click",function(){
            m.sett.clearSearch(m.reqpara.geom);
            el.keyword.val("");
            m.reqpara.geom = "";
            m.reqpara.keyword = "";
            m.reqpara.sort = "";
            m.reqpara.direction = "";
            m.fresh();
        });

        el.keyword.keydown(function(e){
            if(e.keyCode == 13) {
                m.fresh();
            }

        });

        //刷新
        m.el.delegate(".fresh","click",function(){
            m.fresh();
        })

        /**
         * 一次获取所有分页数据
         */
        m.getAllData = function(callback){
            callback = callback || $.noop;
            use(comm_url,function(comm){
                comm.ajax(m.sett.ajax_feature_list, m.reqpara,function(t){
                    if(t.vl()){
                        callback = t.data;
                    }
                })
            });
        };

        //加入展示
        ol.control.Control.call(m, {
            element : el[0]
        });
    },
    /**
     * 展开
     */
    show:function(){
        var m = this;


    },
    /**
     * 收起
     */
    hide:function(){
        var m = this;
    },


    /**
     * 刷新列
     */
    freshColumn:function(callback){
        var m = this;
        $.post(m.sett.ajax_column,{mapid: m.sett.mapid})
            .fail(function(){
                alert("刷新列数据出错");
            })
            .done(function(data){
                data = m.util.cj.tojson(data);

                m.tgrid.column(data.fields);
                m.sett.columnUpdate(data);
                (callback|| $.noop)();
            })
        ;
    },

    /**
     * 刷新视图
     */
    fresh:function(){
        var m = this;
        m.tg_loading();
        m.freshColumn(function(){
            m.sr.doo();
            m.el.addClass("inited");
        });
    },


    /**
     * 根据fid高亮对应的行
     * @param fid
     */
    select_feature_by_fid:function(fid){
        var m = this;
        m.tgrid.lightByFid(fid);
    },


    /**
     * tgrid的加载
     * @param disable
     */
    tg_loading:function(enable){
        var m = this;
        if(enable === true || enable === undefined){
            m.tgrid_loading.show();
        }else{
            m.tgrid_loading.hide();
        }
    }
});

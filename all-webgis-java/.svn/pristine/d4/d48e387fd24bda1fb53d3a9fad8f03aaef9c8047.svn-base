/**
 * Created with PhpStorm.
 * User: Administrator
 * Date: 2015/5/20
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */

define(function (require, exports, module) {
    var cl = require("ctool");
    var cj = require("ctooj");
    var root = require.resolve("../");


    cj.rootCondiFunc({
        "@/*": function () {

        },
        "@/._map_search": function () {

            //编辑一半时刷新页面，保证不会重新新建一个项目
            if(/mapid=([^&/]+)/.test(location.hash)){
                var mapid = RegExp["$1"];
                if($("base").length){
                    location.href = location.href.replace(/(#.+)|(\?.+)/,"") + "?mapid="+mapid;
                }else{
                    location.href = location.href + "?mapid="+mapid;
                }
            }

            //用来管理地图元素
            var mpel_man = {
                /**
                 * 保存一个元素到管理器（如果元素已存在，则为编辑，如果是数组，则分别添加数组总的每一项，如果对象是空的，不添加）
                 * @param mpel mpel或者mpel数组
                 */
                add:function(mpel){
                    var m = this;

                    //处理添加多个
                    if($.isArray(mpel)){
                        $.each(mpel,function(key,ele){
                            m.add(ele);
                        });
                        return;
                    }

                    //空对象不添加
                    if(!object_leng(mpel))  return;

                    //至少需要有tmpid
                    if(!mpel.tmpid) return;

                    //判断是否已经保存,id或者tmpid与现有的重复
                    if(m.getById(mpel.id) || m.getByTmpId(mpel.tmpid)){
                        m.__edit(mpel);
                        return;
                    }

                    //存在全局id，认为已经保存到数据库
                    if(mpel.id){
                        m.__id__mpel[mpel.id] = mpel;
                        m.mpel_list_saved.push(mpel);
                    //认为是新建的未保存的
                    }else{
                        m.__tmpid__mpel[mpel.tmpid] = mpel;
                        m.mpel_list.push(mpel);
                        EC.emit(EV_MAP_EL_SAVE,{mpel:mpel});
                    }

                    mtookit.user_ft_list.push(mpel);
                },


                /**
                 * 判断如果id或者tmpid已经存在，就转入编辑模式
                 */
                __edit:function(new_mpel){
                    var m = this;
                    var el = m.getById(new_mpel.id) || m.getByTmpId(new_mpel.tmpid);
                    $.extend(el,new_mpel);

                    var index = indexOfArray(el,mtookit.user_ft_list.$model,"tmpid");

                    mtookit.user_ft_list.splice(index,1,el);

                    if(!el.id) return;
                    var i = indexOfArray(el, m.mpel_list_edited,"id");
                    if(i==-1){
                        m.mpel_list_edited.push(el);
                    }else{
                        m.mpel_list_edited.splice(i,1,el);
                    }
                },



                /**
                 * 从列表中删除一个元素
                 * @param mpel_id
                 */
                del:function(tmpid__id__mpel){
                    var m = this;
                    var mpel;
                    mtookit.proj_save_disable = false;
                    if(typeof tmpid__id__mpel != "string"){
                        mpel = tmpid__id__mpel;
                    }else{
                        m.del(m.getFeature(tmpid__id__mpel))
                        return;
                    }

                    //执行删除
                    //本地删除(没有id认为没有在数据库中存放)
                    if(!mpel.id){
                        del_form_array(mpel, m.mpel_list);
                        delete m.__tmpid__mpel[mpel.tmpid];
                        EC.emit(EV_REMOVE_FEATURE,{mpel:mpel});
                        mtookit.user_ft_list.removeAt(indexOfArray(mpel, mtookit.user_ft_list.$model,"tmpid"))
                        //远程删除
                    }else{

                        //del_form_array(mpel, m.mpel_list_saved);
                        //delete m.__id__mpel[mpel.id];

                        mpel.has_del = true;
                        EC.emit(EV_REMOVE_FEATURE,{mpel:mpel});
                        mtookit.user_ft_list.removeAt(indexOfArray(mpel, mtookit.user_ft_list.$model,"id"))
                    }
                },


                /**
                 * 把缓存中的列表放到，已保存列表
                 */
                commit:function(el_id_list){
                    var m = this;

                    //已标记删除，从缓存中清理
                    $.each(m.mpel_list,function(k,el){
                        if(el.has_del){
                           delete m.__id__mpel.el.id;
                        }
                    })

                    //删除已经标记删除的mpel
                    m.mpel_list = m.mpel_list.filter(function(el,i){
                        return !el.has_del;
                    });

                    //需要处理id_list
                    $.each(m.mpel_list,function(key,ele){
                        delete ele.tmpid;

                        //给临时ft关联全局唯一id
                        ele["id"] = el_id_list[key]["id"];
                        m.__id__mpel[ele.id] = ele;
                    });

                    m.mpel_list_saved.concat(m.mpel_list);
                    m.mpel_list.length = 0;
                    m.__tmpid__mpel = {};
                },




                /**
                 * 通过真实id获取元素
                 * @param id
                 * @returns {*}
                 */
                getById:function(id){
                    return this.__id__mpel[id];
                },

                /**
                 * 通过本地临时id获取元素
                 * @param tmpid
                 * @returns {*}
                 */
                getByTmpId:function(tmpid){
                    return this.__tmpid__mpel[tmpid];
                },


                /**
                 * 从已提交的ft列表中提取已标记为删除的ft的id列表
                 */
                getHasDelIdList:function(){
                    var m = this;
                    return m.mpel_list_saved
                        .filter(function(el,i){
                            return el.has_del;
                        })
                        .map(function(el,i){
                            return el.id;
                        })
                    ;
                },

                /**
                 * 获取已经编辑的ft的列表，并清空
                 */
                getMpel_list_edited:function(){
                    var m = this;
                    var l = m.mpel_list_edited.concat();
                    m.mpel_list_edited.length = 0;
                    return l;
                },


                /**
                 * 获取一个feature,替代getByTmpId和getById
                 */
                getFeature:function(id_tmpid){
                    var m = this;
                    if(tmpid_rg.test(id_tmpid)){
                        return m.getByTmpId(id_tmpid)
                    }else{
                        return m.getById(id_tmpid);
                    }
                },

                __tmpid__mpel:{},
                __id__mpel:{},

                /**
                 * 已经入并已修改
                 */
                mpel_list_edited:[],

                /**
                 * 已绘制未保存的feature
                 */
                mpel_list:[],

                /**
                 * 已经保存的feature
                 */
                mpel_list_saved:[],

                /**
                 * 获取所有未保存的ft
                 */
                get_list_nosave:function(){
                    return mpel_man.mpel_list;
                },

                get_list:function(){
                    var m =this;
                    return m.mpel_list_saved.concat(m.mpel_list);
                }
            };

            exports.mpel_man = mpel_man;


            /**
             * 地图绘制完成后处理
             */
            EC.on(EV_MAP_INIT,function(){
                mpel_man.pull();
            })



            var mtookit = avalon.define("map_view_all", function(vm){


                //第一层tab配置
                vm.main_tab_cfg = [
                    {
                        name:"搜索",
                        name:"&#xe600; 搜索",
                        type:"tpl_search"

                    },
                    {
                        name:"图层",
                        name:"&#xe603; 图层",
                        type:"tpl_layer"
                    },
                    {
                        name:"标记",
                        name:"&#xe601; 标记",
                        type:"tpl_mark"

                    },
                    {
                        name:"设置",
                        name:"&#xe602; 设置",
                        type:"tpl_setting"
                    }
                ];

                /**
                 * 点线面类型
                 * @type {Function}
                 */
                vm.EL_POINT = EL_POINT;
                vm.EL_LINE = EL_LINE;
                vm.EL_POLY = EL_POLY;

                //主面板切换
                /**
                 * tab切换主控
                 * @param levelIndex 定位要切换tab的层级
                 * @param tab_index
                 */
                vm.tab_switch = function(level_index){
                    vm.tb_index =  level_index + "0000" + (Math.random()*100>>0);
                };

                /**
                 * 多级tab索引
                 * @type {{}}
                 */
                vm.tb_index = "2000";


                /**
                 * 切换
                 * @param i
                 */
                vm.create_mpel = function(i,obj){
                    var type = obj.$model.type;

                    if(!type)   return;

                    var el_data;

                    if(type==EL_POINT){
                        el_data = get_map_el_point();
                    }else if(type==EL_LINE){
                        el_data = get_map_el_line();
                    }else if(type==EL_POLY){
                        el_data = get_map_el_poly();
                    }

                    change_mpel(el_data, true);


                    var emit_para = {
                        el_data:el_data,
                        callback:function(msg){
                            emit_para.callback = $.noop;
                            change_mpel(el_data);
                        }
                    };

                    //发送创建元素事件，并附加新创建的元素
                    EC.emit(EV_CREATE_EL,emit_para);
                }

                /**
                 * 取消绘制
                 */
                vm.cancel_draw = function(e){
                    var mpel = vm.mp_el.$model;
                    EC.emit(EV_CANCEL_DRAW,{ el: mpel});
                }

                //地图的id
                vm.mapid="";

                //mark面板tab配置

                vm.mark_cfg=[
                    {
                        "name":"提示tab",
                        tpl:"tpl_feature_tip_pan"
                    },
                    {
                        name:"&#xe605; 点",
                        type:EL_POINT,
                        tabs:[
                            {
                                type:"&#xe609; 名称",
                                tpl:"tpl_text_form"
                            },
                            {
                                type:"&#xe60c; 外观",
                                tpl:"tpl_style_form"
                            },
                            {
                                type:"&#xe60e; 图形",
                                tpl:"tpl_symbol_form"
                            },
                            {
                                type:"&#xe60e; 附件",
                                tpl:"tpl_files_form"
                            }
                            //, {type: "latlon", tpl: "tpl_latlon_form"}
                        ]
                    },
                    {
                        name:"&#xe608; 线",
                        type:EL_LINE,
                        tabs:[
                            {
                                type:"&#xe609; 名称",
                                tpl:"tpl_text_form"
                            },
                            {
                                type:"&#xe60b; 描边",
                                tpl:"tpl_stroke_form"
                            },
                            {
                                type:"&#xe60e; 附件",
                                tpl:"tpl_files_form"
                            }
                        ]
                    },
                    {
                        name:"&#xe604; 面",
                        type:EL_POLY,
                        tabs:[
                            {
                                type:"&#xe609; 名称",
                                tpl:"tpl_text_form"
                            },
                            {
                                type:"&#xe60b; 描边",
                                tpl:"tpl_stroke_form"
                            },
                            {
                                type:"&#xe60a; 填充",
                                tpl:"tpl_fill_form"
                            },
                            {
                                type:"&#xe60e; 附件",
                                tpl:"tpl_files_form"
                            }
                        ]
                    },
                    {
                        name:"&#xe606; 列表",
                        tpl:"tpl_feature_list"
                    }


                ]

                //当前与界面绑定的feature data
                vm.mp_el = {
                    name:""
                };

                //维护当前的列表
                vm.user_ft_list = [];


                //颜色选择色值
                vm.color_picker_data = def_colors;


                //更换color
                vm.color_change = function(cl){
                    vm.mp_el.color = cl;
                }

                //更换stroke_color
                vm.stroke_color_change = function(cl){
                    vm.mp_el.stroke_color = cl;
                }

                //更换fill_color
                vm.fill_color_change = function(cl){
                    vm.mp_el.fill_color = cl;
                }


                //元素大小
                vm.el_scale = def_scale;

                //元素大小改变
                vm.scale_change = function(sc){
                    vm.mp_el.scale = sc;
                }



                vm.def_symbol_src = root + def_symbol_src;
                //符号列表入住
                vm.symbol_list = symbol_list;
                //符号改变
                vm.symbol_change = function(sid){
                    vm.mp_el.symbol = sid;
                }


                //图层数组
                vm.layer_list = layers_man.getLayers();


                //项目信息
                vm.proj_attr = {
                    name:"",
                    desc:"",
                    mapid:"",
                    share:false
                };

                //搜索结果数据
                vm.search_result = [];
                vm.search_list_click = function(data){
                    EC.emit(EV_SEARCH_RESULT_CLICK,{
                        el:data.$model
                    })
                }
                //搜索框文字
                vm.search_key="";

                //项目保存
                vm.proj_save = function(){
                    if(vm.proj_save_disable){
                        tlayer(function(l){
                            l.msg("地图未做修改，不能保存")
                        })
                        return;
                    }
                    mpel_man.add(mtookit.mp_el.$model);
                    save_data();
                }
                vm.proj_save_disable = true;


                //地图保存按钮文本
                vm.map_save_btn_tx = "保存地图";
            });
            avalon.scan();


            window.getMPTookit = function(){
            	return mtookit;
            }


            /**
             * 监控项目表述等发生变化
             */
            mtookit.proj_attr.$watch("$all",function(name,val){
                mtookit.proj_save_disable = false;
                if(name=="name"){
                    mtookit.proj_attr.name = val.substr(0,30);
                }else if(name=="desc"){
                    mtookit.proj_attr.desc = val.substr(0,200);
                }
            })

            exports.mtookit = mtookit;

            /**
             * 初始化，jqui控件
             */
            require.async(["_/jui/jquery-ui.min","_/jui/jquery-ui.min.css"],function(){
                function calc(){
                    var _self = $(this);
                    var val = _self.val();

                    if(_self.is(".stroke_opacity")){
                        mtookit.mp_el.stroke_opacity = val;
                    }else if(_self.is(".fill_opacity")){
                        mtookit.mp_el.fill_opacity = val;
                    }else if(_self.is(".stroke_width")){
                        mtookit.mp_el.stroke_width = val;
                    }
                }

                $(".stroke_opacity").spinner({
                    step:0.1,
                    max:1,
                    min:0,
                    stop:calc,
                    change:calc
                });



                $(".fill_opacity").spinner({
                    step:0.1,
                    max:1,
                    min:0,
                    stop:calc,
                    change:calc
                });

                $(".stroke_width").spinner({
                    setp:1,
                    max:6,
                    min:1,
                    stop:calc,
                    change:calc
                });
            });

            //tab切换了
            mtookit.$watch("tb_index",function(val,oldval){
            	if(!/^(21|22|23)/.test(val)){//切换时除了点线面关闭绘图工具
            		__drawLayer.active=false;
            		__drawLayer.activate();
                }
            	if(/^(213|222|233)/.test(val)){//附件面板创建上传实例
            		myupload.init(mtookit.mp_el.$model.tmpid,mtookit);
                }
            });

            window.mt  = mtookit;


            /**
             * 绑定地图元素到编辑器
             */
            EC.on(EV_BIND_MP_ELEMENT,function(data){

            });


            /**
             * 更换视图绑定的对象
             * @param newmapel
             * @param no_add 更换，但是不添加到列表
             */
            function change_mpel(newmapel,no_add){
                mtookit.proj_save_disable = false;
                if(!newmapel.type){
                    mtookit.tb_index = "200000";
                    return;
                }else{
                    mtookit.tb_index = "2" + El__INDEX[newmapel.type] + "0000";
                }

                EC.emit(EV_BIND_MP_ELEMENT,{
                    mpel:newmapel,
                    old_mpel:mtookit.mp_el.$model
                });

                //监控mp_el属性变化
                mtookit.mp_el.$unwatch("$all",mp_el_watch_handler);
                mtookit.mp_el = newmapel;
                mtookit.mp_el.$watch("$all",mp_el_watch_handler);


                //创建ft后立即存入列表（之前是新的绑定保存旧的）
                no_add || mpel_man.add(mtookit.mp_el.$model);
            }

            /**
             * 对mp_el属性操作监控的处理函数
             * @param name
             * @param val
             * @param oval
             */
            function mp_el_watch_handler(name,val,oval){
                if(name == "name"){
                    if(val.length>20){
                        mtookit.mp_el.name = val.substr(0,20);
                        tlayer(function(l){
                            l.msg("字数不能大于20");
                        })
                    }
                }else if(name == "desc"){
                    if(val.length>200){
                        mtookit.mp_el.desc = val.substr(0,200);
                        tlayer(function(l){
                            l.msg("描述字数不能大于200");
                        })
                    }
                }else if(name == "occurtime"){

                }
            }
            
            /**
             * input输入日期格式校验
             */
            var date_invalie = true;
            EC.on("checkDate",function(){
            	var val = mtookit.mp_el.occurtime;
            	var reg=/^((((19|20)\d{2})-(0?(1|[3-9])|1[012])-(0?[1-9]|[12]\d|30))|(((19|20)\d{2})-(0?[13578]|1[02])-31)|(((19|20)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-29))$/;
            	if(val){
            		if(!val.match(reg)){//正则判断是否为日期格式yyyy-MM-dd
                		date_invalie = false;
                        mtookit.mp_el.occurtime = "";//置空
                        tlayer(function(l){
                            l.msg("请输入标准的日期格式，例：2015-03-30");
                        })
                    }else{
                    	date_invalie = true;
                    }
            	}else{//可以不填
            		date_invalie = true;
            	}
            });
            
            /**
             * 监控元素属性哪些被改变，并派发事件
             */
            !function(){
                var before = {};
                function step(){

                    var def = object_change(before,mtookit.mp_el.$model);
                    before = $.extend({},mtookit.mp_el.$model);

                    if(object_leng(def)){
                        EC.emit(EV_EL_PROP_EDIT,{
                            props_edit:def,
                            mpel:mtookit.mp_el.$model
                        });
                    }
                    setTimeout(step,150);
                }

                step();
            }();


            /**
             * 处理地图元素属性被改变
             */
            EC.on(EV_EL_PROP_EDIT,function(data){
                //名称改变
                if(data.props_edit.name){
                    mpel_man.add(data.mpel)
                }
            });


            /**
             * 处理地图元素单击事件
             */
            EC.on(EV_FEATURE_CLICK,function(data){
                change_mpel(mpel_man.getFeature(data.tmpid || data["id"]));
            });


            /**
             * 地图元素修改开始处理
             */
            EC.on(EV_EDIT_DRAW_BEFORE,function(data){
                mtookit.mp_el.geom = data.geom;
            })

            /**
             * 地图元素修改完成处理
             */
            EC.on(EV_EDIT_DRAW_COMPLETE,function(data){
                mtookit.mp_el.geom = data.geom;
            })


            /**
             * 图层选择 和切换部分
             */
            $(document).delegate("#map_tookit .layer_list li","click",function(e){
                var li = $(this);

                //保证必须有一个图层处于选中状态
                if(li.is(".selected") && !li.siblings(".selected").length){
                    return;
                }

                mtookit.proj_save_disable = false;

                //多选模式
                //li.toggleClass("selected");


                var last_selecteed_li = li.parent().children(".selected");

                //单选模式
                li.addClass("selected").siblings().removeClass("selected");

                //layer_selected_change(last_selecteed_li);
                layer_selected_change(li);
            });


            /**
             * 图层选择状态发生改变
             * @param li
             */
            function layer_selected_change(li){
                var lid = li.attr("lid");
                var ul = li.parent();

                //处理已选择和未选择图层
                ul.children().each(function(i){
                    var _li=$(this);
                    var _lid = _li.attr("lid");
                    layers_man.getLayerById(_lid).selected = _li.is(".selected");
                });

                var newlist = ul.children().map(function (i) {
                    return layers_man.getLayerById(this.getAttribute("lid"))
                }).toArray();

                var sel_list = newlist.filter(function(el,i){
                    return el.selected;
                })

                layer_status_cache = sel_list;

                EC.emit(EV_LAYER_SEL_CHANGE,{
                    layer:layers_man.getLayerById(lid),
                    layerid:lid,
                    newlist:newlist,
                    selected_list:sel_list,
                    selected:li.is(".selected")
                });
                
            }


            /**
             * 处理图层数据加载完成(页面打时)
             */
            EC.on(EV_LAYER_LOADED,function(data){
                var layer_li = $(".layer_list li");
                $.each(data.layer_status,function(key,ele){
                    //layer_li.filters("")
                    EC.emit(EV_LAYER_SEL_CHANGE,{
                        layer:layers_man.getLayerById(ele.id),
                        layerid:ele.id,
                        newlist:data.list,
                        selected_list:data.layer_status,
                        selected:true
                    });
                });
            });

            //拖动排序插件
            require.async("_/Sortable.min",function(){
                var ul = $(".layer_list>ul");
                Sortable.create(ul[0], {
                    group: "omega",
                    onEnd:function(e){
                        //e.item;
                        //evt.oldIndex;
                        //evt.newIndex;  //
                        var ul = $(e.item).parent();
                        var newlist = ul.children().map(function (i) {
                            return layers_man.getLayerById(this.getAttribute("lid"))
                        }).toArray();

                        e.new_list = newlist;
                        e.selected_list = newlist.filter(function(el,i){
                            return el.selected;
                        })

                        layer_status_cache = e.selected_list;
                        EC.emit(EV_LAYER_SEQ_CHANGE,e);
                    }
                });
            })



            /**
             * 缓存图层状态
             */
            var layer_status_cache;

            /**
             * 保存所有的数据到数据
             */
            function save_data(callback){
            	if(date_invalie){
            		EC.emit(EV_MAP_SAVE_BEFORE);

                    callback = callback|| $.noop;
                    var jsonStr = {};



                    var list = mpel_man.get_list();

                    $.each(list,function(k,el){
                    	el.bgcolor = "#fff";
                    })


                    var mtookit_mdl = mtookit.$model;
                    //未入库的ft list
                    jsonStr.feature = mpel_man.get_list_nosave();
                    //jsonStr.feature = mtookit.user_ft_list.$model.concat();

                    //已经入并修改过的ft
                    jsonStr.feature_edited = mpel_man.getMpel_list_edited();

                    //图层信息
                    jsonStr.layer_status =
                        (
                            layer_status_cache||(function(){
                                var ul = $(".layer_list>ul");
                                return selected_list =
                                    ul.children()
                                        .map(function (i) {
                                            return layers_man.getLayerById(this.getAttribute("lid"))
                                    })
                                    .toArray()
                                    .filter(function(el,i){
                                        return el.selected;
                                    })
                                ;
                            })()
                        )
                        .concat()
                    ;

                    //在界面上已经进行删除操作的ft id列表（仅包括已入库的ft，未入库的直接删除）
                    jsonStr.del_idlist = mpel_man.getHasDelIdList();

                    //项目信息
                    jsonStr.project = mtookit.proj_attr.$model;

                    //如果不填写项目名称，自动添加项目名称
                    if($.trim(jsonStr.project.name) == ""){
                        mtookit.proj_attr.name = "未命名项目";
                        jsonStr.project.name = "未命名项目";
                    }

                    jsonStr = JSON.stringify(jsonStr);
                    mtookit.map_save_btn_tx = "地图数据保存中...";
                    $.post(action_project_save(),{mapid: mtookit_mdl.mapid ||"",jsonStr:jsonStr})
                        .fail(function(){
                            !function(){
                                mtookit.map_save_btn_tx = "保存地图";
                            }.delayCall(555);

                            tlayer(function(l){
                                l.msg("地图保存出错...")
                            });
                            //throw "保存数据出错"
                        })
                        .done(function(data){
                            data = cj.tojson(data);
                            if(data.mapid){
                                mtookit.proj_attr.mapid = data.mapid;

                                if(!cl.urlParas().mapid){
                                    location.hash="#mapid="+data.mapid;
                                }

                            }

                            callback.call();
                            EC.emit(EV_PROJECT_SAVED);
                            //需要返回id列表
                            mpel_man.commit(data.featureids);

                            !function(){
                                mtookit.map_save_btn_tx = "保存地图";
                                mtookit.proj_save_disable = true;
                            }.delayCall(555);
                        })
                    ;
            	}
            }

            /**
             * 取消绘制处理
             */
            EC.on(EV_CANCEL_DRAW,function(data){
                mpel_man.del(data.el)
                change_mpel({});
            });


            /**
             * 单击用户ft列表
             */
            $(document)
                .delegate(".feature_list_panel li","click",function(e){
                    var self = $(this);
                    if($(e.target).is("a")) return;
                    var __id = self.attr("__id");
                    EC.emit(EV_CLICK_FT_LIST_ITEM,{
                        id:__id,
                        el:mpel_man.getFeature(__id)
                    });
                })
                .delegate(".feature_list_panel li>a","click",function(e){
                    e.preventDefault();
                    var li = $(this).parent();
                    mpel_man.del(li.attr("__id"))
                })
            ;


            /**
             * 处理列表被单击
             */
            EC.on(EV_CLICK_FT_LIST_ITEM,function(data){
                change_mpel(data.el);
            })

            /**
             * 处理用户列表加载完成
             */
            EC.on(EV_USER_FEA_LOADED,function(data){
                mpel_man.add(data.list);
            });

            //搜索框自动完成
            require.async(["_/auto_complete/jquery.autocomplete","_/auto_complete/jquery.autocomplete.css"],function(){
                //搜索文本框
                var search_input = $("#search_input");

                //单击搜索按钮
                var dosearch_btn = search_input.next("em").click(function(){
                    var key = search_input.val();
//                    if(!key){
//                        //请输入关键字进行搜索
//                        return;
//                    }


                    request_key(search_input.val(),function(data){
                        data = cj.tojson(data);
                        EC.emit(EV_SEARCH_COMPLETE,{
                            list:data
                        })

                        mtookit.search_result = data.map(function(el){
                            return {
                                name:el.data.name,
                                geo:el.geometry
                            }
                        });
                    })

                });
                

                //回车搜索
                search_input.keydown(function(e){
                    if(e.keyCode == 13){
                        dosearch_btn.click();
                    }
                });

                mtookit.$watch("search_key",function(val){
//                    if(val){
                    dosearch();
//                    }
                })

                var dosearch = cl.throttle(300,function(){
                    dosearch_btn.click();
                });


                var states = [
                    'Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California',
                    'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii',
                    'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana',
                    'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota',
                    'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire',
                    'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota',
                    'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island',
                    'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont',
                    'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'
                ];
                0 && $("#search_input").autocomplete({
                    valueKey:"text",
                    source:[
                        states
                        /*,{
                         url:"/test.php?query=%QUERY%",
                         type:'remote',
                         getValue:function(item){
                         return item.title
                         },
                         ajax:{
                         dataType : 'jsonp'
                         }
                         }*/
                    ]
                });

                //测试数据
                //mtookit.search_result = states;
            })


        }

    });

});



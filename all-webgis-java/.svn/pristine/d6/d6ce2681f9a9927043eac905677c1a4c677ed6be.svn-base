/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2016/2/25
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    var u = require("_/tr_util");
    var co = require("__/js/comm");
    var ht = $("html");
    require("$/laydate.dev.custom");
    require("_/avalon.min");
    var pt = require("parseTpl");
    var dd = require("dateFormat");
    var lunar = require("_/chinese_df");

    var ec = require("little-event-center");

    /**
     * 当前记录改变的事件
     * @type {string}
     */
    var EV_CURRENT_RECODER_CHANGE = "EV_CURRENT_RECODER_CHANGE";

    //var init_date = dd("2016-03-08");
    var init_date = new Date();

    var hash_mgr = ({
        hash_tpl:"!date={year}+{month}+{day}+{recindex}",
        date_rg:/!date=(\d+)\+(\d+)\+?(\d+)?\+?(\d+)?/,

        /**
         * 标识url识别生效
         */
        actived:false,

        init:function(){
            var m = this;

            if(top.hash_parse){
                m.hp = top.hash_parse;
                var hash = m.hp.hash();

                if(m.date_rg.test(hash)){
                    m._year = RegExp["$1"];
                    m._month = RegExp["$2"];
                    m._day = RegExp["$3"] || 1;
                    m._recindex = RegExp["$4"] || 0;
                    init_date = dd(m._year + "-" + m._month + "-" + m._day);
                    m.actived = true;
                }
            }

            return m;
        },

        /**
         * 设置hash的recindex
         */
        fresh_hash:function(){
            var m = this;

            if(!m.hp)   return;
            var dt = main_vm.date;

            var dhash = pt({
                year:dt.getFullYear(),
                month:dt.getMonth()+1,
                day:dt.getDate(),
                recindex:rec_shower.vm.current_rec_index
            }, m.hash_tpl);

            var hash = m.hp.hash();

            if(m.date_rg.test(hash)){
                hash = hash.replace(m.date_rg,"");
            }

            //.replace(m.date_rg,dhash.trim());
            m.hp.hash(hash + dhash.trim());
        }
    }).init();


    var dd_format = '%Y年%m月';
    var ddd_format = '%Y年%m月%d日';

    var year_month_rg = /\d+/g;

    //现在
    var now  = new Date();

    /**
     * 获取该月的天数
     * @returns {number|*}0
     */
    Date.prototype.get_date_num = function(){
        var d = this.clone().add(1,"month");
        d.setDate(0);
        return d.getDate();
    }

    /**
     * 获取该月第一天的周
     */
    Date.prototype.get_first_days_week = function(){
        var d = this.clone();
        d.setDate(1);
        return d.getDay();
    }

    /**
     * 设置年月
     * n年n月或者y-m
     */
    Date.prototype.set_ym = function(ym_str){
        var d = this;
        var a = ym_str.match(year_month_rg);
        d.setFullYear(a[0]);
        d.setMonth(a[1] - 1);
    }

    /**
     * 获取x年x月形式的字符串
     */
    Date.prototype.get_ym = function(){
        return this.format(dd_format);
    }


    /**
     * 获取x年x月x日形式的字符串
     */
    Date.prototype.get_ymd = function(){
        return this.format(ddd_format);
    }



    /**
     * 生成一个月初时刻的时间对象
     * @param ym_str n年n月形式的月份
     * @returns {Date}
     */
    Date.ym = function(ym_str){
        var d = new Date();
        d.set_ym(ym_str);
        d.set_to_month_first_time();
        return d;
    }


    /**
     * 设置时间对象到月初
     */
    Date.prototype.set_to_month_first_time = function(){
        var d = this;
        d.setDate(1);
        d.setHours(0,0,0,0,0);
        return d;
    }

    /**
     * 设置时间对象到月末
     */
    Date.prototype.set_to_month_last_time = function(){
        var d = this;
        var day_num = d.get_date_num();
        d.setDate(day_num);
        d.setHours(23,59,59,999);
        return d;
    }


    /**
     * 总体的vm
     */
    var main_vm = avalon.define({
        $id:"main",

        /**
         * 当前
         */
        date:init_date,

        /**
         * 现在
         */
        now:now,


        /**
         * 今天
         */
        today:now.get_ymd(),


        /**
         * 跳转当前到上一月
         */
        month_prev:function(){
            rec_shower.pos = 0;
            main_vm.date = main_vm.date.clone().add(-1,"month");
        },

        /**
         * 跳转当前到下一月
         */
        month_next:function(){
            rec_shower.pos = 0;
            main_vm.date = main_vm.date.clone().add(1,"month");
        },


        /**
         * 视图状态
         * 1表示日历模式
         * 2表示地图模式
         */
        view:"1",

        view_toggle:function(vindex){
            main_vm.view = vindex;
        },


        /**
         * 选择某一天的时候
         */
        day_select:function(day_el,day_index){
            if(!day_el.outMonth){
                //单击的是当月的
                rec_shower.pos = 0;
                main_vm.date = day_el.date;
            }else{
                //选择的时候前月或者后月
                if(day_el.outMonth == -1){
                    main_vm.month_prev();
                }else if(day_el.outMonth == 1){
                    main_vm.month_next();
                }
            }
        },



        /**
         * avalon计算属性
         */
        $computed:{

            //年月
            year_month:{
                get:function(){
                    return this.date.format(dd_format);
                },

                /**
                 * n年n月
                 * @param v
                 */
                set:function(v){
                    var d = this.date.clone();
                    d.set_ym(v);
                    this.date = d;
                }
            },

            //单独设置天
            day:{
                get:function(){
                    return this.date.format("%d");
                },
                set:function(v){
                    var d = this.date;
                    var max = d.get_date_num();
                    if(v>max){
                        v = max;
                    }
                    this.date = d.clone().setDate(v);
                }
            },

            yyyy_mm_dd:{
                get:function(){
                    return this.date.format("%Y-%m-%d");
                }
            },

            /**
             * m年m月m日形式的日期
             */
            ymd:{
                get:function(){
                    return this.date.format(ddd_format);
                }
            },


            /**
             * 现在的字符串形式
             */
            now_str:{
                get:function(){
                    return this.now.format(ddd_format);
                }
            },

            /**
             * 现在的农历形式
             */
            now_str_lunar:{
                get:function(){
                    return lunar.solarToLunar(this.now,"YMD")
                }
            },


            /**
             * 路径
             */
            p:{
                get:function(){
                    return co.media_image_path;
                }
            },

            blankimg:{
                get:function(){
                    return co.theme_thumb_default;
                }
            }
        }
    });


    window.mvm = main_vm;


    /**
     * 地图管理
     */
    var map_mgr = ({
        /**
         * poi对象的字典
         */
        dic_id_poi:{},

        /**
         * 当前可见的poi的记录
         */
        current_poi_list:[],
        init:function(omap){
            var m = this;
            m.omap = omap;

            omap.init({ mapid: "mapCont"});

            /**
             * 当ft被单击的时候执行
             * @param ft
             */
            omap.onFeatureClick = function(ft){
                rec_shower.show_by_rec(ft.getProperties());
            }

            //当使用或者日期改变展示recoder时会进入该回调
            ec.on(EV_CURRENT_RECODER_CHANGE, function(rec){
                if(!rec)        return;
                if(!rec.geom)   return;

                omap.centerAtByCoord.lazyCall(360,omap,[omap.getCoordinate(rec.geom)])
            });

            return m;
        },

        fresh:function(){
            var m = this;
            m._fresh.lazyCall(120,m);
        },
        _fresh:function(){
            var m = this;
            if(main_vm.view!=2) return;
            var omap = m.omap;
            omap.setSize();
            omap.clearDraw(true);
            m.clear_poi();

            $.each(recordMgr.data.data, function(i,el) {
                if(el.geom){

                    var poi = m.rec(el.id);

                    if(!poi){
                        poi = $.extend({},el);
                        m.rec(el.id,poi);
                        poi.dom = $(co.get_line_pop_html(el.title));
                        poi.overlay = new ol.Overlay({
                            position:omap.getCoordinate(el.geom),
                            positioning:"top-center",
                            offset:[0,-80],
                            element:poi.dom[0]
                        });
                        poi.cont = poi.dom.parent();
                    }

                    m.add_poi(poi);

                    //自动绘制点和多边形
                    omap.drawByDataNoClear(el,i==0,true);
                }
            });



            if(m.current_poi_list.length){
                var poi = m.current_poi_list[0];
                omap.centerAtByCoord(
                    omap.getCoordinate(poi.geom)
                );
            }
        },

        /**
         * 缓存或者获取rec
         * @param rid
         * @param rec
         */
        rec:function(rid,rec){
            var m = this;
            var dic = m.dic_id_poi;

            if(rec === undefined){
                return dic[rid];
            }
            dic[rid] = rec;
        },


        clear_poi:function(){
            var m = this;
            $.each(m.current_poi_list,function(key, ele){
                m.omap.map.removeOverlay(ele.overlay);
            });
            m.current_poi_list.length = 0;
        },

        add_poi:function(poi){
            var m = this;
            m.current_poi_list.push(poi);
            m.omap.map.addOverlay(poi.overlay);
        }
    });

    require.async("_/omap/omap",function(omap){
        map_mgr.init(omap);
    });


    var month_dire = 0;

    var map_rec_shower = "";

    main_vm.$watch("view",function(v){
        if(main_vm.view==2){
            map_mgr.fresh();
    	}

        if(v == 1){
            map_rec_shower = rec_shower.vm.map_rec;
            rec_shower.vm.map_rec = "";
        }else{
            //rec_shower.vm.map_rec = map_rec_shower;
        }
    });


    /**
     * 当前日期改变的时候（包括时分秒日）
     */
    main_vm.$watch("date",function(newval,oldval){

        //月份改变的时候
        //console.log(main_vm.year_month);
        //console.log(newval);

        month_dire = newval.getTime() > oldval.getTime()?1:-1;

        rec_shower.fresh_data();


        //月份变化的时候才重新请求
        if(newval.get_ym() == oldval.get_ym())  return;
        
        
        cal.setMonth(main_vm.year_month);
        /**
         * 刷新当月数据
         */
        recordMgr.fresh_current_data(function(data){
            map_mgr.fresh();
        });
    });


    /**
     * 中心日历
     */
    var cal = ({
        _cell_num:35,
        _month_dic:{},
        init:function(){
            var m = this;
            m.el = $(".calendar");

            m.vm = avalon.define({
                $id:"day_view"
                //daylist: m.get_day_list(new Date().get_ym()).concat()
            });

            return m;
        },


        /**
         * 修改月份
         */
        setMonth:function(v){
            var m = this;
            if(typeof v != "string"){
                v = v.get_ym()
            }
            recordMgr.vm.datelist = m.get_day_list(v);
        },


        /**
         * 获取本月可见的日期列表（会出现上月的末尾和下月的开头）
         * @param ym_str
         * @returns {*}
         */
        get_day_list:function(ym_str){
            var m = this;
            if(!m._month_dic[ym_str]){
                m._month_dic[ym_str] = gen_day_list(ym_str);
            }

            return m._month_dic[ym_str];



            /**
             * 生成月份日期列表
             * @param ym_str
             * @returns {Array}
             */
            function gen_day_list(ym_str){
                var list = [];
                var month = Date.ym(ym_str);
                var month_start = month.clone().set_to_month_first_time();
                var month_end   = month.clone().set_to_month_last_time();
                var daynum = month.get_date_num();
                var cellnum = m._cell_num;
                var week_f = month.get_first_days_week();

                var first_cell_date = month.clone().add(-(week_f-1),"day");
                if(week_f == 0){
                    first_cell_date = month.clone().add(-6,"day");
                    cellnum = m._cell_num+7;
                }


                var i = first_cell_date.clone();

                for(;cellnum;){
                    cellnum -=1;
                    var dt = i.clone();
                    list.push({
                        date        :   dt,
                        label       :   dt.getDate(),
                        ymd         :   dt.get_ymd(),
                        outMonth    :   outMonth(dt),
                        //ch_date     :   lunar.solarToLunar(dt,"MD")
                        ch_date     :   lunar.solarToLunar(dt,"D"),
                        reclist     :   []
                    });
                    i.add(1,"day");
                }

                return list;

                /**
                 * 是否是本月外的天
                 * @param dt
                 * @returns {number}
                 */
                function outMonth(dt){
                    if(dt.getTime()<month_start.getTime()){
                        return -1
                    }else if(dt.getTime() > month_end.getTime()){
                        return 1;
                    }else{
                        return 0;
                    }
                }
            }
        }
    }).init();


    /**
     * 记录展示控件
     */
    var rec_shower = ({

        /**
         * 0    切换天之后默认显示当天第一个记录
         * -1   最后一个
         */
        pos:0,
        
        


        init:function(){
            var m = this;

            m.el = $(".recoder_shower");

            m.vm = avalon.define({
                $id:"rec_shower",


                /**
                 * 是否显示loading
                 */
                loading:false,

                /**
                 * 当前天的列表
                 */
                day_rec_list:[],


                map_rec:"",


                path:co.media_image_path,

                /**
                 *点击进入专题去掉日历cur
                 */
                removecur:function(){
                    if(top.nav){
                        top.nav.set_current(0)
                    }
                },

                /**
                 * 当前记录
                 */
                current_rec_index:0,

                change_index:function(index){
                    m.vm.current_rec_index = index;
                    m.vm.loading = true;
                },

                /**
                 * 上一条
                 */
                prev:function(){
                    if(m.vm.current_rec_index<=0){

                        //跳天
                        rec_shower.pos = -1;
                        recordMgr.jump_day_has_rec(-1);

                        return;
                    }
                    //m.vm.loading = true;
                    m.vm.current_rec_index--;
                },

                /**
                 * 下一条
                 */
                next:function(){
                    if(m.vm.current_rec_index >= m.leng() - 1) {
                        //跳天
                        rec_shower.pos = 0;
                        recordMgr.jump_day_has_rec(1);
                        return;
                    }

                    //m.vm.loading = true;
                    m.vm.current_rec_index++;
                },

                $computed:{
                    rec:{
                        get:function(){
                            if(this.map_rec){
                                return this.map_rec;
                            }
                            return this.day_rec_list[this.current_rec_index] || {};
                        }
                    }
                }
            });




            $(window).keyup(function(e){
                console.log(e.keyCode)
                if(e.keyCode == 37){
                    m.vm.prev();
                }else if(e.keyCode == 39){
                    m.vm.next();
                }
            });

            m.vm.$watch("day_rec_list",rec_change);
            m.vm.$watch("current_rec_index",rec_change);


            function rec_change(){
                //
                //map_mgr.fresh();
                !function(){
                	ec.emit(EV_CURRENT_RECODER_CHANGE, m.vm.rec.$model);
                }.delayCall(210);
                hash_mgr.fresh_hash();
            }
            return m;
        },


        leng:function(){
            var m = this;
            return m.vm.day_rec_list.length;
        },



        /**
         * 展示某一天的数据
         */
        show_by_day:function(day_reclist,index){

            var m = this;

            if(!day_reclist || !day_reclist.length) {
                //显示空
                m.vm.day_rec_list = [];
                return;
            }
            m.vm.loading = false;
            m.vm.day_rec_list = day_reclist;


            if(index === undefined){
                index = (m.pos==-1?(day_reclist.length - 1):0);
            }

            m.vm.current_rec_index = index;
        },


        /**
         * 根据记录直接展示
         * @param rec
         */
        show_by_rec:function(rec){
            var m = this;

            //var ymd = $D(rec.occurtime).get_ymd();
            //
            //var list = recordMgr.get_reclist_by_ymd(ymd);
            //
            //m.show_by_day(list,rec.__index);

            main_vm.date = $D(rec.occurtime);
            rec_shower.index(rec.__index);

            //hash_mgr.fresh_hash();
        },


        /**
         * 根据日历和数据的改变刷新数据
         */
        fresh_data:function(){
            var m = this;
            var day_reclist = recordMgr.get_reclist_by_ymd(main_vm.date.get_ymd())
            m.show_by_day(day_reclist);
        },


        index:function(index){
            var m = this;
            if(index === undefined) return m.vm.current_rec_index;
            index = parseInt(index) || 0;
            if(index>m.vm.day_rec_list.length -1){
                index = m.vm.day_rec_list.length -1;
            }
            m.vm.current_rec_index = index;
        }
    }).init();


    /**
     * 专题列表
     */
    var topiclist = ({
        init:function(){
            var m = this;
            
            u.ajax(
                u.pagevar("getTopicList","__/data/calendar_topiclist.json".p()),
                function(t){
                    ht.addClass("no_cl_loading");

                    if(!t.vl()) return;

                    m.vm = avalon.define({
                        $id:"topiclist",
                        d: t.data,


                        //切换选择状态
                        toggleLight:function(e,el){
                            if($(e.target).is("i")) return;

                            u.ajax(
                                u.pagevar("setSelectStatus","__/data/success.json".p()),
                                {
                                    id:el.id,
                                    status:!el.selectStatus
                                },
                                function(t){
                                    if(!t.vl()) return;
                                    el.selectStatus = el.selectStatus==0?1:0;
                                    recordMgr.fresh_current_data();
                                    map_mgr.fresh();

                                }
                            );

                        },



                        change_cl:function(e,el){
                            if(!el.selectStatus)    return;
                            var s = $(this);
                            var p = s.parent();
                            color_pad.show(p,function(cl){
                                u.ajax(
                                    u.pagevar("setTopicColor","__/data/success.json".p()),
                                    {
                                        id:el.id,
                                        color:cl
                                    },
                                    function(t){
                                        if(!t.vl()) return;
                                        el.topiccolor = cl;
                                        recordMgr.fresh_current_data();
                                    }
                                );
                            });

                        }
                    });

                    avalon.scan();
                }
            );
            
            
            m.el = $(".calendar_nav");


            //折叠
            m.el.delegate("li>h3","click",function(){
                var h3 = $(this);
                var li = h3.parent();
                li.toggleClass("collapse");
                var h = 0;
                if(li.is(".collapse")){
                    h = h3.height();
                }else{
                    h = li.prop("scrollHeight");
                }

                li.stop(true).animate({height:h},300);
            });


            return m;
        }
    }).init();


    /**
     * 颜色盘
     */
    var color_pad = ({
        init:function(){
            var m = this;

            m.el = $(
                "<div class='color_pad'></div>"
            );

            m.el.appendTo("body");

            u.ajax(
                u.pagevar("getColorlist","__/data/co_subject.json".p()),
                function(t){
                    if(!t.vl()) return;
                    m.el.append(
                        pt(t.data.rows, "<a cl='{color}' style='background-color: {color}'></a>")
                    );

                }
            )

            m._callback = $.noop;

            $(document).click(function(e){
                var t = $(e.target);

                if(t.is(".calendar_nav a>i")){
                    return;
                }

                if(!t.is(".color_pad")){
                    m.hide();
                }
            });

            m.el.delegate(">a","click",function(e){
                var c = $(this);
                m._callback(c.attr("cl"));
            });

            return m;
        },

        show:function($host,callabck){
            var m = this;
            var rec = u.cl.getRect($host);
            m.el.css({
                left:rec.left + 46,
                top:rec.top
            });

            m._callback = callabck;

        },
        hide:function(){
            var m = this;
            m.el.css({
                top:"-100%",
                left:"-100%"
            });
        }
    }).init();


    /**
     * 记录管理
     * 负责记录的查询获取请求整理
     */
    var recordMgr = ({
        init:function(){
            var m = this;

            //最新的记录数据
            m.data;

            use("little-event-center",function(ee){
                m.ec = ee;
            });
            m.vm = avalon.define({
                $id:"root",

                date:main_vm.date,

                /**
                 * 当月日历
                 */
                datelist:cal.get_day_list(init_date.get_ym()).concat(),


                $computed:{
                    monlist:{
                        get:function(){
                            var dl = this.datelist;
                            var le = dl.length;
                            for(var i =0;i<le;i++) {
                                var el = dl[i];
                                el.reclist.removeAll();
                            }
                            return dl;
                        }
                    },
                    /**
                     * 当前他
                     */
                    daylist:{
                        get:function(){
                            var t = this;
                            return this.date;
                        }
                    }
                }
            });

            return m;
        },


        /**
         * 按月获取记录
         * @param date
         */
        getReclistByMonth:function(date,callback){
            var m = this;
            rec_shower.vm.loading = true;
            m._reqReslistByMonth(date,function(data){
                callback(data);

            })
        },


        /**
         * 按月请求数据
         * @private
         */
        _reqReslistByMonth:function(date,callback){
            var m = this;
            u.ajax(
                u.pagevar("getReclistByMonth","__/data/recoder_list_group_by_month.json".p()),
                {
                    date:date.format("%Y%m"),
                    dire:month_dire
                },
                function(t){
                    rec_shower.vm.loading = false;

                    if(t.vl){
                        callback(t.data);
                    }
                }
            );
        },


        /**
         * 获取邻月的记录(存在记录的月)
         * @param date      当前月份的日期对象
         * @param direct    1|-1
         * @param callaback
         * @private
         */
        _reqNeighborMonthReclist:function(date,direct,callaback){
            var m = this;
            rec_shower.vm.loading = true;
            u.ajax(
                u.pagevar("getNeighborMonthReclist","__/data/xxx.json".p()),
                {
                    dire:direct,
                    date:date.format("%Y%m")
                },
                function(t){
                    if(t.vl()){
                        callaback(t.data);
                    }else{
                        rec_shower.vm.loading = false;
                    }
                }
            );
        },



        __ymd_cache:{},




        /**
         * 获取当日的记录列表
         * @param ymd
         * @returns {*}
         */
        get_reclist_by_ymd:function(ymd){
            var m = this;
            return m.__ymd_cache[ymd];
        },


        /**
         * 刷新或者获取当前月份视图上的数据
         */
        fresh_current_data:function(callback){
            var m = this;
            callback = callback || $.noop;
            m.__ymd_cache = {};
            var cache = m.__ymd_cache;
            recordMgr.getReclistByMonth(main_vm.date,function(data){
                //按天分拣数据
                $.each(data.data,function(key,el){
                    var day = new Date(el.occurtime).get_ymd();
                    if(!cache[day]){
                        cache[day] = [];
                    }
                    var cac = cache[day];
                    el.__index = cac.length;
                    cac.push(el);
                });


                $.each(recordMgr.vm.datelist,function(key,ele){
                    var ls = cache[ele.ymd];
                    if(ls) {
                        ele.reclist = ls;
                    }else{
                        ele.reclist = [];
                    }
                });


                rec_shower.fresh_data();
                m.data = data;
                m.ec.emit("month_date",data);
                callback(data);
            });
        },

        /**
         * 按左右次序跳转到存在记录的天
         * @param dire -1|1 正方向还是负方向
         */
        jump_day_has_rec:function(dire){
            var m = this;

            var dt = main_vm.date;

            var list = m.vm.datelist.$model;

            var counter = 0;
            var c_index = -1;
            var day_index_in_cal = -1;
            var datelist_has_rec = list.filter(function(el,index){
                var flag = el.reclist && el.reclist.length;
                el.index = index;
                if(flag) {
                    if(el.ymd == dt.get_ymd()){
                        c_index = counter;
                        day_index_in_cal = index;
                    }
                    counter++;
                }
                return flag;
            });


            var tar_index = c_index + dire;
            if(tar_index<0 ){
                //跳月
                m._reqNeighborMonthReclist(dt,-1,function(t){
                    main_vm.date = new Date(t.data.date);
                })
                return;
            } if (tar_index>datelist_has_rec.length - 1) {
                //跳月
                m._reqNeighborMonthReclist(dt,1,function(t){
                    main_vm.date = new Date(t.data.date);
                })
                return;
            }

            //跳天
            var tar_date = datelist_has_rec[tar_index];
            main_vm.date = tar_date.date;
        }
    }).init();


    $(function(){
        setTimeout(function(){
            laydate({
                isclear:false,
                //start:new Date(),
                format: 'YYYY-MM-DD',
                elem: '#date_picker', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                //event: 'click' //响应事件。如果没有传入event，则按照默认的click
                choose:function(v){
                    main_vm.year_month = v;
                }
            });
        },100);

        avalon.scan();


        //初始化
        /*获取本月数据*/
        !function(){
            recordMgr.fresh_current_data(function(data){
                var list = data.data;
                if(list && list.length){
                    if(hash_mgr.actived){
                        !function(){
                            rec_shower.index(hash_mgr._recindex*1 || 0);
                            map_mgr.fresh();
                        }.delayCall(100);
                    }else{
                        var rec = list[list.length - 1];
                        main_vm.date = new Date(rec.occurtime);
                        map_mgr.fresh();
                    }
                }
            })
        }.delayCall(300);



        var mt = $(".main_table_cont");
        var elele = $("td.ll>div,td.rr>div");
        u.cj.winResize(500,function(sw,sh){
            elele.css({
                height:mt.height() - 12
            });
        })


        $(document).delegate(".recoder_shower a.lk","click",function(){
            var aa = $(this);
            co.add_formpara_to_href(aa);
        });
    });

    return {};
});



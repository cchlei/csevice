/**
 * Created by Administrator on 2015/3/19.
 */
define(function(require){
    var $ = require("jq");
    require("./ascroller.css");
    var cl = require("ctool");

    var def = {
        //width:"auto",
        //height:300,


        //根据view内容的大小来强制设置view的大小
        isCalcViewSize:true,

        //根据哪个对象了来计算鼠标位置
        host:null,

        //h标识仅横向，hv标识横向竖向
        direction:"hv",

        //如果使用数字，标识使用定时器
        throDelay:0,

        //平滑动画开启时候，动画间隔
        animDelay:1200,


        //根据el的尺寸按照某个比例来设置view的尺寸
        viewSizeRatio:undefined,

        //当viewSize的尺寸由于内部设置而改变时
        viewSizeChange:function(w,h){ },

        //当窗口大小改变的时候重新计算位置等
        recalcOnWindowResize:true,



        /**
         * 运动时候调用，两个参数取值范文均为-1到-1
         * @param xratio
         * @param yratio
         */
        move:function(xratio,yratio){}
    };

    $.fn.autoScroll = function(config){
        var sett = $.extend({},def,config);
        sett.oldie = cl.bro("ie7,ie8,ie9");
        var me = this;
        return me.each(function(){
            var m = $(this);
            m.addClass("auto_scroll");
            var view = m.find(">.view");



            var sizecss = {};
            if(sett.width){
                sizecss.width = sett.width;
            }
            if(sett.height){
                sizecss.height = sett.height;
            }
            m.css(sizecss);


            view.img = view.find(">img:first");

            if(sett.viewSizeRatio === undefined && sett.isCalcViewSize){
                //根据view内部尺寸设置尺寸
                view.css({
                    width   :  view.prop("scrollWidth"),
                    height  :   view.prop("scrollHeight")
                });
            }

            //判定根据哪个对象的鼠标位置来计算比率
            var mouseHost = $(sett.host);
            if(!sett.host || !mouseHost.length){
                mouseHost = m;
            }


            //缓存尺寸和比率数据
            var mh_w, mh_h, perc_x, perc_y;

            //缓存view的尺寸
            var view_w,view_h;


            //设置css
            var css = {};

            var last_mousemove_e;
            var mousemoveHandler = function(e){
                last_mousemove_e = e;
                mh_w = mouseHost.width();
                mh_h = mouseHost.height();

                //如果是document,offset()返回undefined
                var offset = mh.offset() || {left:0,top:0};

                perc_x = (e.clientX - offset.left)/mh_w;
                perc_y = (e.clientY - offset.top )/mh_h;

                if(sett.viewSizeRatio !== undefined){
                    view.width(m.width() * sett.viewSizeRatio);
                    view.height(m.height() * sett.viewSizeRatio);
                    if(view.img.length){ view.img.maxonLite(); }
                }
                view_w = view.outerWidth();
                view_h = view.outerHeight();

                if(sett.viewSizeRatio !== undefined){
                    sett.viewSizeChange.call(m,view_w,view_h)
                }

                dddomove();
            }


            //计算比率
            var mh = mouseHost.mousemove(mousemoveHandler);


            sett.recalcOnWindowResize && $(window).resize(cl.throttle(333,function(){
                mousemoveHandler(last_mousemove_e || {clientX:0,clientY:0});
            }));

            var dddomove = cl.throttle(sett.throDelay,domove);

            //执行移动
            function domove(){


                if(sett.direction=="h"){
                    css.left = - perc_x * (view_w - m.width());
                }else if(sett.direction == "v"){
                    css.top  = - perc_y * (view_h - m.height());
                }else{
                    css.left = - perc_x * (view_w - m.width());
                    css.top  = - perc_y * (view_h - m.height());
                }

                if(sett.throDelay){
                    view.stop(true).animate(css,sett.animDelay);
                }else{

                    sett.oldie?
                    view.css(css):
                    view.css({
                        transform: "translate3d("+css.left+"px, "+css.top+"px, 0px)"
                    })
                }

                sett.move.call(m,perc_x*2-1,perc_y*2-1);
            }

            //窗口尺寸变化 重绘
            $(window).resize(function(){
                d.update();
            });

            //提供外部调用方法
            var d = m.data();
            d.update = function(){
                domove();
            }

            /**
             * 更新尺寸位置等
             */
            me.update = function(){
                domove();
                return me;
            }


            /**
             * 更换图片
             * @param src
             */
            me.setSrc = function(src){
                me.find(".view>img").attr("src",src);

                cl.imgready(src,function(){
                    view_w = view.outerWidth();
                });
                return me;
            }
        });
    }
});
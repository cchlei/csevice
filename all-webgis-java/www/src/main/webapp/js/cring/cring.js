/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2015/9/17
 * Time: 14:29
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {

    require("./cring.css");
    var ctimer = require("ctimer");


    var CRing = function($el,config){
        this.init($el,config);
    }

    CRing.prototype = {
        _def:{
            width:660,
            height:360,

            ease:undefined,

            child_width:240,
            child_height:undefined,

            radius:undefined,

            //动画间隔
            dura:300,

            blur_ratio:18,

            h_ratio:0.5,


            //当前改变的时候执行
            change: function(index,last_inidex){ },
            before_change: function(target_index,last_index){ },


            aotuplay:false,
            autoplay_delay:2700
        },
        init:function($el,config){
            var m = this;
            m.sett = $.extend({}, m._def, config);



            m.el = $($el);

            //用作动画的代理
            m.proxy = $("<i></i>");

            m.childs = m.el.find(">*");

            m.childs.first().addClass("cur");

            m._leng = m.childs.length;
            m._ang_step = 360/m._leng;


            //存储当前旋转的角度
            m._rotate = 0;

            m.setSize();

            m.el.delegate(">*","click",function(e){
                m.frontEl(this)
            });

            m._index = 0;


            //时间相关处理
            m._timer = new ctimer({
                autoStart:false,
                delay: m.sett.autoplay_delay,
                callback:function(){
                    m.next();
                }
            });

            if(m.sett.autoplay){
                m._timer.start();
            }

        },



        /**
         * 改变尺寸
         * @param sizeObject 有效字段width heiht child_width child_height radius
         */
        setSize:function(sizeObject){
            var m = this;

            $.extend(m.sett,sizeObject);

            if(!m.sett.child_height)  m.sett.child_height = m.sett.child_width;

            if(!m.sett.radius){
                m.sett.radius = (m.sett.width - m.sett.child_width)/2;
            }

            m.el.css({
                width: m.sett.width,
                height: m.sett.height
            });


            m.childs.each(function(i){
                var me=$(this);
                var d = me.data();

                d.init_ang = -i* m._ang_step;
                me.css({
                    width: m.sett.child_width,
                    height: m.sett.child_height
                });
                d.init_marginLeft = -me.outerWidth()/2;
                d.init_marginHeight = -me.outerHeight()/2;
                me.css({
                    marginLeft:d.init_marginLeft,
                    marginTop:d.init_marginHeight
                });
            });

            m.rotate();
        },

        rotate:function(ro){
            var m = this;
            if(ro === undefined){
                ro = m._rotate
            }else{
                if(ro>360){ro-=360}
                else if(ro<-360){ro+=360}
                m._rotate = ro;
            }

            m.childs.each(function(i){
                var me=$(this);
                var d = me.data();
                var ang = d.init_ang - ro + 90;
                var sin =  Math.sin(deg_rad(ang));
                var ml = Math.cos(deg_rad(ang)) * m.sett.radius + d.init_marginLeft;
                var mh = sin * m.sett.radius + d.init_marginHeight;

                var scale = 1/(2-sin) * 1;
                var blur = (1-scale) * m.sett.blur_ratio;
                mh = m.sett.h_ratio * mh;

                var css = {
                    marginLeft:ml,
                    marginTop:mh,
                    zIndex:((sin + m.sett.radius)*300)>>0,
                    transform:"scale(" + scale +")",
                }

                if(m.sett.blur_ratio) css["-webkit-filter"] = "blur("+ blur +"px)";

                me.css(css);

            });
        },

        transision_rotate:function(ro,callback){
            var m = this;
            callback = callback || $.noop;
            var dd = modp(ro - m._rotate,360);
            if(dd>180){
                dd = dd - 360;
            }else if(dd<-180){
                dd = dd + 360;
            }
            var _rotate = m._rotate;
            m.proxy.fadeTo(0,0).stop(true).animate({opacity:1},{
                speed: m.sett.dura,
                duration: m.sett.dura,
                easing: m.sett.ease,
                complete:function(){
                    m.rotate(dd + _rotate);
                    callback.call(null);
                },
                callback:function(){
                    m.rotate(dd + _rotate);
                    callback.call(null);
                },
                step:function(val){
                    m.rotate(val*dd + _rotate);
                }
            });
        },


        frontEl:function(el_index){
            var m = this;
            var el;
            if(typeof el_index == "number"){
                el = m.childs.eq(modp(el_index, m._leng));
            }else{
                el = $(el_index);
            }

            var target_index = el.index();

            m.sett.before_change.call(m,target_index,m._index);

            m.childs.removeClass("cur");
            el.addClass("cur");

            var d = el.data();
            m.transision_rotate(d.init_ang,function(){
                m.sett.change.call(m,target_index, m._index);
                m._index = target_index;
            });

            m._timer.reCount();
        },

        next:function(){
            var m = this;
            m.frontEl(modp(m._index+1, m._leng));
        },

        prev:function(){
            var m = this;
            m.frontEl(modp(m._index-1, m._leng));
        },

        start:function(){
            this._timer.start();
        },

        pause:function(){
            this._timer.pause();
        }
    };


    function deg_rad(n){
        return Math.PI/180 * n;
    }

    function modp(be_mod,mod){
        return be_mod>=0?(be_mod % mod):(((be_mod%mod)+mod)%mod);
    }

    return CRing;
});
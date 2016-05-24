define(function(require){

    var cl = require("ctool");
    var cj = require("ctooj");
    var layer = require("$/layer");
    var c = {

        /**
         * 如果p空，返回默认地址。否则返回normal
         * @param p
         * @param normal
         */
        _def_map_thumb:function(p,normal){

            if(c.isnull(p)){
                return webroot + "images/default_map_thumb.png";
            }else{
                return normal
            }
        },


        /**
         * 默认用户头像
         * @returns {*}
         * @private
         */
        _def_user_icon:webroot + "images/fwfail.png",


        /**
         * 默认资源前缀
         */
        media_url:"http://oss.trmap.cn/rs/download/",


        /**
         * mortuopian
         */
        def_img:webroot + "images/p_bg2.jpg",


        /**
         * 订单状态到文本
         */
        dic_order_status:{
            "0":"待审批",
            "1":"通过",
            "2":"驳回",
            "3":"过期",
            "4":"已撤销",
            "5":"异常"
        }

    };


    /**
     * 检测是否空
     * @param p
     * @returns {boolean}
     */
    c.isnull = function(p){
        return !p || p == "undefined" || p == "null";
    }


    /**
     * 判断是否是空媒体资源
     */
    c.isBlackMedia = function(res){
        res = res.replace(c.media_url,"");
        res = $.trim(res);
        return !res;
    }


    /**
     * 替换空图为指定图片
     */
    $.fn.blankImg = function(){
        return this.each(function(i){
            var me=$(this);
            me.attr("src",function(i,tx){
                if(c.isBlackMedia(tx)){
                    return c._def_user_icon;
                }else{
                    return tx;
                }
            });
        });
    }






    var Tooken = function(data){
        if(data){
            this.iserr = /^error|failt|fail$/.test(data.result);
            this.msg = this.errmsg = data.msg;
            this.data = data;
        }
    }

    $.extend(Tooken.prototype,{
        /**
         * 判定tooken是否正确，如果错误默认会给出提示，见参数
         * @param notip 如果为true，不会自动给出提示
         */
        vl:function(notip){
            var m = this;

            if(m.iserr && !notip){
                c.alert(m.errmsg);
            }

            return !m.iserr;
        }
    })

    /**
     * ajax                 ajax，错误提示封装
     * @param url           请求路径
     * @param para          请求参数 可以省略
     * @param callback      回调
     */
    c.ajax = function(url,para,callback){
        callback = callback || $.noop;

        if(typeof para === "function"){
            callback = para;
            para = {};
        }

        var tooken;
        $.post(url,para)
            .fail(function(){
                tooken = new Tooken();
                tooken.iserr = true;
                tooken.errmsg = "服务出错";
                callback(tooken);
            })
            .done(function(data){
                data = cj.tojson(data);
                tooken = new Tooken(data);
                callback(tooken,data);
            })
        ;
    }

    /**
     * 验证是否错误
     * @param data 服务器返回的数据对象
     */
    c.valid = function(data){
        var tk = new Tooken(data);
        return tk.vl();
    }


    c.layer = layer;
    c.cl = cl;
    c.cj=cj;


    /**
     *
     * @param ele
     * @param msg
     * @param delay
     */
    c.tips = function(ele,msg,delay_config){
    	
    	if(typeof delay_config == "number"){
    		delay = delay_config;
    	}else{
    		delay_config = delay_config || {};
    	}
        
        layer.tips(
            msg,
            ele,
            {
                tips: [delay_config.dire||1, delay_config.color||"#e74c3c"],
                time: delay_config.delay || 3600
            }
        );
    }



    /**
     * tip提示
     * @param msg 提示信息
     * @param delay 延迟时间
     */
    c.msg =  function(msg,delay){
        layer.msg(msg,{time:delay || 3000});
    };


    /**
     * alert
     * @param msg
     * @param callback 点击确定时候执行的回调
     */
    c.alert = function(msg,callback,title){
        callback=callback|| $.noop;
        layer.alert(msg,{title:title},function(index){
            layer.close(index);
            callback();
        })
    }


    /**
     * 确认框
     * @param msg 提示信息
     * @param callback 回调 function(err){} 如果点击取消err为true*
     */
    c.confirm = function(msg,callback,title){
        callback = callback||$.noop;
        layer.confirm(
            msg,
            {
                title:title,
                btn: ['确定','取消'] //按钮
            },
            function(index){
                var c = callback();
                if(c !== false){
                    layer.close(index);
                }
            },
            function(){
                callback(true);
            }
        );
    }


    /**
     * 克隆一个object
     */
    c.clone = function(obj){
        return $.extend({},obj);
    }

    /**
     * 清理服务器返回的object
     * @param object
     */
    c.clear_o = function(object){
        delete object.result;
        delete object.msg;
        return object;
    }


    /**
     * 重置一个object
     * @param reset_val 要重制为某值
     * @param target 要重制的目标
     * @param def 按照该对象来重置
     */
    c.reset = function(reset_val,target,def){
        def = def||{};
        if(typeof reset_val != "string"){
            def = target;
            target = reset_val;
            reset_val = undefined;
        }

        for(var key in target) {
            var ele = target[key];
            target[key] = def[key] || reset_val;
        }

        return target;
    }



    /**
     * 对象转换成字符串
     * @param object        对象
     * @param callback
     */
    c.stringify = function(object,callback){
        callback = callback || $.noop;

        //字符串化geom
        if(typeof JSON == "undefined"){
            use("_/json2",function(JSON){
                callback(JSON.stringify(object));
            });
        }else{
            callback(JSON.stringify(object));
        }
    }


    c.ctx = function(){
        if(window.ctx === undefined){
            return window.proot.replace(/\/$/,"")
        }
        return window.ctx;
    }





    //全局顶部
    $(function(){
        var lo =  $(".fwtop>span,.fwquit,.ztop>span,.top span:contains(退出)");
        lo.click(function(){
            top.logout();
        });
    })


    /**
     * 注销
     */
    if(top == window){
        window.logout = function(callback){
            c.confirm("确认要注销吗?",function(cancel){
                if(cancel){
                    return;
                }
                top.location.href = proot + "logout";
            })
        }
    }


    /**
     * 获取当前用户信息
     * @param callback
     */
    c.getUser = function(callback){
        c.ajax(proot + "entaccount/getinfo",{},function(tk){
            var data;
            if(!tk.vl()){
                data = {};
            }else{
                data = tk.data.user;
            }

            if(!data.headimg){
                data.headimg = c._def_user_icon
            }else{
                data.headimg = c.media_url + data.headimg;
            }

            (callback || $.noop)(data);
        })
    }


    window.comm = c;

    return c;
});
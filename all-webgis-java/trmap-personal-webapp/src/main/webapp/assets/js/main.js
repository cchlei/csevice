/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2016/2/25
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    var co = require("__/js/comm");
    var u = require("_/tr_util");
    var ht = $("html");

    require("_/messenger.js");
    var messenger = new Messenger('main', 'trmap');

    $(function(){
        var nav = ({
            init:function(index){
                var m = this;
                m.el = $(".nav");

                m.btns = m.el.find(">a")

                m.el.delegate(">a","click",function(e){
                    var  a = $(this);
                    a.addClass("cur").siblings().removeClass("cur");
                });
                m.btns.click(function(){
                    var me = $(this);
                    location.hash = "#|" + me.attr("href");
                })
                m.iframe  = $(".col3>iframe")
                return m;
            },

            /**
             * 设置导航当前位置
             * @param index
             * @param jump 同时跳转
             */
            set_current:function(index,jump){
                var m = this;
                if(index === undefined) {
                    index = 0 ;
                }
                var cur = m.btns.removeClass("cur").eq(index).addClass("cur");
                if(jump){
                    cur.click();
                }
            },

            /**
             * xianhsi hongdian
             */
            show_notice:function(index,isClear){
                var m = this;

                var btn = m.btns.eq(index || 0);

                var dot_element = btn.find(".dot");
                
                if(!dot_element.length){
                    btn.append(dot_element = $("<em class='dot'></em>"))
                }

                if(isClear){
                	dot_element.remove();
                }
            },

            /**
             * 选择菜单，并跳转页面
             * @param menuindex
             * @param src
             */
            suburl:function(src){
                var m = this;
                m.iframe.attr("src",src);

            }
        }).init();

        window.nav = nav;


        var uo = u.cl.urlParas();

        if(uo.navindex){
            nav.set_current(uo.set_current)
        }

        if(uo.suburl) {
            nav.suburl(decodeURIComponent(uo.suburl))
        }


        /**
         * 间隔刷新消息提示
         */
        !function(){
            var ac = arguments.callee;
            u.ajax(
                u.pagevar("msgcenter","../data/msgcenter.json"),function(t,data){
                    if(t.vl(true)){
                        if(data.wyd !=0) {
                            nav.show_notice(3);
                        }
                    }
                    ac.delayCall(10*1000);
                }
            )
        }()


        /**
         * 用户信息刷新
         */
        var user_profile = ({
            init:function(){
                var m = this;
                messenger.listen(function(msg){
                    m.update();
                });
                return m;
            },

            update:function(){

                $.post("#").done(function(data){
                    var $page = $(data);
                    $(".user_header").replaceWith($page.find(".user_header"));
                });

            }
        }).init();


        window.user_profile = user_profile;

        //用来解析hast
        var hash_parse = ({
            /**
             * iframe selecter
             */
            sel:".mainCont>iframe,.col3>iframe",

            _forbid_hash_change_test:false,

            init:function(){
                var m = this;

                require.async("_/jquery.ba-hashchange.js",function(){
                    $(window).hashchange(function(){
                        if(m._forbid_hash_change_test)   return;
                        m.do_route();
                    });
                });

                m.do_route();
                ht.addClass("no_cl_loading");
                return m;
            },

            do_route:function(){
                var m = this;

                var ha = location.hash.replace(/^#/,"");

                var route_str;
                var route_array;
                if(/^\|(.+)/.test(ha)) {
                    route_str = RegExp["$1"];
                    if(!route_str){
                        nav.set_current(0,1);
                        return;
                    }
                    route_array = route_str.split("||");
                    set_iframe_url(route_array,window);
                }else{
                    nav.set_current(0,1);
                    m.do_route();
                }

                function set_iframe_url(urllist,current_window){
                    if(!urllist.length) return;
                    var url = urllist.shift();
                    if(!url) return;

                    url = url.replace(/!(.+)?/,"");

                    u.cl.run_until(300,-15,function(flag){
                        var ifm = $(m.sel,current_window.document);
                        var ifm_window;
                        if(ifm.length && (ifm_window = ifm.prop("contentWindow"))) {
                            ifm_window.location = url;
                            set_iframe_url(urllist,ifm_window)
                            return true;
                        }
                    });
                }

            },

            /**
             * 获取或者设置当前hash
             * @returns {string}
             */
            hash:function(hash){
                var m = this;
                if(hash === undefined){
                    return location.hash;
                }

                m.revert_hash_change_test.killDelayCall();
                m.revert_hash_change_test.delayCall(120,m);
                m._forbid_hash_change_test = true;
                location.hash = hash;
            },


            revert_hash_change_test:function(){
                var m = this;
                m._forbid_hash_change_test = false;
            }

        }).init();

        window.hash_parse = hash_parse;
    });
    return {};
});
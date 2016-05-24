/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2015/12/2
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    var comm = require("__/js/enterprise_comm");
    var cl = require("ctool");
    var cj = require("ctooj");
    var sr = require("$/splitReq");
    var pt = require("parseTpl");
    var comm = require("__/js/comm");




    $(function(){


        /**
         * 面板
         */
        var pan = ({
            init:function(){
                var m = this;
                m.el = $(".apply_panel");
                m.list = m.el.find(".ft_list");
                m.page = m.el.find(".page");

                m.el.find("i.date").show().html(function(i,tx){
                    return tx.replace(/\.\d+$/,"")
                })

                m.reqpara = {};


                m.sr = new sr({
                    container:m.page,
                    continuous_page:2,
                    edge_page:1,
                    firstReqAuto:false,
                    rows:18,
                    reqPath:window.action_list || "../data/service_getter_list.json",
                    onReq:function(para){
                        $.extend(para, m.reqpara);
                    },
                    onData:function(data){
                        m.list.html(
                            pt(
                                data.rows,
                                '<li fid="{id}">{gname}</li>'
                            )
                        );
                    }
                });


                m.el.find(".applic_btn").click(function(){
                    comm.ajax(window.applic_action,{},function(t){
                        if(t.vl()){
                            //成功
                        	window.location=ctx+'/entrelmap/toApply/'+mapid+'/null';
                        }
                    });
                });


                return m;
            },

            /**
             * 执行搜索
             * @param key
             */
            search:function(key,isClear){
                var m = this;
                if(isClear){
                    m.el.removeClass("search_list_mode");
                    return;
                }


                m.el.addClass("search_list_mode");
                m.reqpara.key = key;
                m.sr.doo();
            }

        }).init();



        var searchbox = ({
            init:function(){
                var m = this;
                m.el = $(".search_bar");
                m.input = m.el.find("input");
                //m.bt = m.el.find(".bt");
                m.bt = m.el.find(".sure");
                m.clear = m.el.find(".cls,.clear");
                return m;
            }

        }).init();


        //搜索绑定
        searchbox.bt.click(function(){
            //if(!searchbox.input.val())  return;

            pan.search(searchbox.input.val())
        });

        //清除绑定
        searchbox.clear.click(function(){
            searchbox.input.val("");
            pan.search("",true);
        });


        pan.el.delegate("li","click",function(){
            var m = $(this);
            m.addClass("cur");
            m.siblings().removeClass("cur");

            var fid = m.attr("fid");


            locatAndPopup(fid);
            //在这里进行高亮地图操作
            //comm.msg("高亮:" + fid);
        });


        //loaderFunction(null,null,projection);


    });







    return {};   
});
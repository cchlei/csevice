/**
 * 底图服务瀑布流
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2015/10/13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    var cl = require("ctool");
    var cj = require("ctooj");
    var SR = require("$/splitReq");
    var parseTpl = require("parseTpl");
    require("./style.css");

    var dir = require.resolve("./");


    var Maplist = function(el,config){this.init(el,config) }


    var def = {
        itemTpl:"   <li class='item {isshare}' mid={id} shareurl={shareurl}>" +
        "               <div class=inner>" +
        "                   <div class='img_menu'>" +
        "						<a href="+ctx+"/mapedit/toEdit?mapid={id}>"+
        "                       	<img src={umicon}  />" +
        "						</a>" +
        "                       <p>" +
        "                           <em class='chart'>&#xe61a;</em>" +
        "                           <em class='share'>&#xe622;</em>" +
        "                           <em class='share ed'>&#xe623;</em>" +
        "                           <em class='del'>&#xe619;</em>" +
        "                       </p>" +
        "                   </div>" +
        "                   <h4>{mapname}</h4>" +
        "                   <p class='umcreatetime'>{umcreatetime}</p>" +
        "                   <p class='mapdesc'>{mapdesc}</p>" +
        "               </div>" +
        "           </li>",
        listContTpl:"<ul class='list'></ul>",
        pageTpl:"<ul class='mppage'></ul>",
        infoContTpl:"" +
            "   <div class='infoCont closing'>" +
            "       <div class='cont'></div>" +
            "       <em class='close'>&#xe61c;</em>" +
            "   </div>",
        imgroot:dir,
        reqpath:"./testdata.json",

        windowBotAutoNext:true,
        rows:21,

        classScope:"_cmd_cc_maplist",
        onItemDel:function(itemid,tooken){ tooken()},
        onChartsShow:function(itemid,infoCont,isFirst,tooken){tooken()},
        onItemShare:function(itemid,tooken){ tooken()},

        infoHeight:360,
        infopan_dura:300,

        reqType:"POST",

        width:1000,
        colNum:4
    };

    $.extend(Maplist.prototype,{
        init:function(el,config){
            var m = this;
            m.sett = $.extend({},def,config);
            m.el = $(el).addClass(m.sett.classScope);

            m.el.css({
                width: m.sett.width
            });

            m.el.append(m.sett.listContTpl);
            m.el.append(m.sett.pageTpl);

            m.listCont = m.el.find(">ul:first");
            m.page = m.el.find(">.mppage");


            //存放所有当前的items
            m.curitems = $();;

            //信息面板，用你来展示条目的详细信息
            m.infoCont = $(m.sett.infoContTpl);
            m.infoCont.isfirst = true;
            m.infoCont.find(">.cont").height(m.sett.infoHeight);
            



            m.infoCont.delegate(">.close","click",function(){
                m.infoCont_close();
            })
            
            

            //打开infoCont的元素
            m.infoCont_host;

            //所有参与换行，或者存放显示信息的容器
            m.gaps;


            /**
             * 单击图层面板
             */
            m.listCont.delegate(".item .chart","click",function(e){
                var self = $(this).parents(".item:first");
                var index = m.curitems.index(self);

                m.curitems.removeClass("active");
                //self.addClass("active");


                m.infoCont_close(false,function(){
                    m.infoCont_open(self);
                })
            });
            
            
            m.listCont.delegate(".item .share","click",function(e){
            	var self = $(this).parents(".item:first");
                m.shareItem(self);
            });
            


            /**
             * 删除条目
             */
            m.listCont.delegate(".item .del","click",function(e){
                var self = $(this).parents(".item:first");
                m.deleItem(self);
            })

            m.sq = new SR({
                container:m.page,
                reqPath: m.sett.reqpath,
                reqType: m.sett.reqType,
                rows: m.sett.rows,
                onReq:function(para){
                    $.extend(para, m._reqpara);
                },
                onData:function(data){
                    m.setData(cj.tojson(data));
                }
            });


            if(m.sett.windowBotAutoNext){
                //当滑动到最底下。加载下一页
                $(window).scroll(function(){
                    if($(window).scrollTop() == $(document).height() - $(window).height()){
                        m.sq.next();
                    }
                });
            }


        },

        /**
         * 重新建立gaps元素
         */
        rebuild_gap:function(){
            var m = this;
            var re_append;

            //重整之前打开着
            if(!m.infoCont.is(".closing")){
                re_append = true;
            }


            //分离信息面板
            m.infoCont.detach();

            //清楚
            if(m.gaps){
                m.gaps.remove();
            }

            m.curitems.each(function(i){
                var me=$(this);
                if(i % m.sett.colNum == 3 || (i==m.curitems.length - 1)){
                    if(!me.next("li:not(.item)").length){
                        me.after("<li></li>")
                    }
                }
            });

            if(re_append){
                m.infoCont_open(m.infoCont_host,true);
            }


            m.gaps = m.listCont.find("li:not(.item)");
        },

        /**
         * 关闭信息面板
         * @param callback
         */
        infoCont_close:function(isfast,callback){
            var m = this;
            callback = callback || $.noop;

            //已经处于关闭状态
            if(m.infoCont.is(".closing")){
                callback.call(m);
                return;
            }

            //新的信息容器和目标信息容器是一个
            /*if(m.infoCont.parent().is(m.infoCont_li)){
                callback.call(m);
                return;
            }*/

            var dura = isfast?0:m.sett.infopan_dura;

            m.infoCont.stop().animate({
                height:0
            },dura,function(){
                m.infoCont.addClass("closing");
                m.curitems.removeClass("active");
                //m.infoCont.detach();
                callback.call(m);
            });
        },

        /**
         * 打开信息面板
         */
        infoCont_open:function(item,isfast,callback){
            var m  = this;
            callback = callback || $.noop;
            
            m.infoCont_li = item.nextAll("li:not(.item):first");
            m.infoCont_host = item;

            if(!m.infoCont.is(".closing")){
                callback.call(m);
                return;
            }

            m.sett.onChartsShow.call(m, item.attr("mid"), m.infoCont, m.infoCont.isfirst,function(){
            	
            	var dura = isfast?0:m.sett.infopan_dura;

                m.infoCont_li.append(m.infoCont);
                m.infoCont_host.addClass("active");
            	

                

                m.infoCont.height(0);
                m.infoCont.removeClass("closing");
                m.infoCont.animate({
                    height:m.infoCont.prop("scrollHeight")
                }, dura, function () {
                    callback.call(m);
                });
            });
            
            m.infoCont.isfirst = false;
            
        },

        /**
         * 删除item
         */
        deleItem:function(item){
            var m = this;
            var _id = item.attr("mid");
            m.sett.onItemDel.call(m,_id,function(){
                //执行删除
                item.remove();
                m.curitems = m.listCont.find(".item");
                m.rebuild_gap();
            })
        },
        //分享
        shareItem:function(item){
        	var m = this;
            var _id = item.attr("mid");
            m.sett.onItemShare.call(m,_id,function(){
                //执行分享
                item.remove();
                m.curitems = m.listCont.find(".item");
                m.rebuild_gap();
            })
        },

        setData:function(data){
            var m = this;
            /*m.listCont.find(".item").remove();*/

            var newindex = m.curitems.length;

            var last = m.listCont.children(":last");
            var fixlast = false;

            if(!last.is(".item") && (newindex-1) % m.sett.colNum != 3){
                last.detach();
                fixlast = true;
            }

            $.each(data.rows,function(key,ele){
                var item = parseTpl(ele, m.sett.itemTpl,function(val,name){
                    if(name == "umicon"){
//                    	return m.sett.imgroot + val;
                        return val; //直接由后台控制完整路径
                    }else if(name == "isshare"){
                    	if(val == 1){
                    		return "shared"
                    	}else{
                    		return "";
                    	}
                    	
                    }
                });
                m.listCont.append(item);
                if(newindex % m.sett.colNum == 3){
                    if(fixlast){
                        fixlast = false;
                        m.listCont.append(last);
                    }
                }
                newindex ++ ;
            });

            m.curitems = m.listCont.find(".item");
            m.rebuild_gap();
        },

        /**
         * 刷新视图
         * @param para
         */
        fresh:function(para){
            var m = this;



            m._reqpara = para;


            if(!m.__fresh){
                m.__fresh = cl.throttle(2000,function(){
                    //分离信息面板
                    m.infoCont.detach();

                    m.listCont.empty();


                    m.sq.doo();
                })
            }


            m.__fresh();
            

        }
    });

    return Maplist;
});

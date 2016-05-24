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
    require("dateFormat");
    var ht = $("html");
    var para = u.cl.urlParas();



    var ha = top.hash_parse;
    if(ha){
        ha.hash("#|" + co.getPath())
    }


    if(para.from){
        $(function(){
            $(".back>a").attr("href","script:;").click(function(){
                top.location.href = decodeURIComponent(para.from);
            });
        });

    }


    $(document).delegate(".cardCont h2>a","click",function(){
        var aa = $(this);
        co.add_formpara_to_href(aa);
    });


    var ex = {

        /**
         * 记录id到的记录的字典
         */
        dic_id_poi:{},

        /**
         * 当滚动到某个记录
         * @param el
         */
        onRecScrollTo:function(el){

        },

        /**
         * 当单击某条记录
         * @param el
         */
        onRecClick:function(el){

        },

        /**
         * 地图模式下单击记录
         */
        onRecClickOnMapMode:function(el){

        },
        
        onSearch:function(param){
        	
        },
        
        onMapShow:function(first){
        	
        }
    };

    var main = ({
        init:function(){
            var m = this;
            m.vm =avalon.define({
                $id:"main",
                view:0,
                topic:{},
                /*可视权限*/
                permissions:'',
                /*好友人数*/
                total:"",
                /*是否公开*/
                open:"",
                /*是否收藏*/
                self_topic:"",
                /*收藏数量*/
                num_of_collect:"",
                do_collect:function(topic){
                    var del_url = u.pagevar("recdel","");



                    if(del_url.trim()){
                        u.msg("不允许收藏自己");
                        return;
                    }

                    u.ajax(
                        u.pagevar("do_collect"),
                        {collect:!topic.iscollect,id:topicid},
                        function(t){
                            if(!t.vl()) return;


                            if(topic.iscollect){
                                topic.num_of_collect --;
                            }else{
                                topic.num_of_collect ++;
                            }
                            topic.iscollect = !topic.iscollect;

                            u.msg(t.msg);
                        }
                    )
                },
                default_img:co.theme_thumb_default,
                //图片文件路径前缀
                ibpath:co.media_image_path,
                isopen:para.open * 1,
                /*addthemeurl:function(){
                    if(para.type === "0"){
                        return "<a href=ctx+'/topic/toadd?open=0' class='add'>添加专题</a>";
                    }else{
                        return "<a href=ctx+'/topic/toadd?open=1' class='add'>添加专题</a>"
                    }
                }(),*/
                f:{
                    open:-1,
                    key:""
                },
                view_toggle:function(v){
                    m.vm.view = v;
                },

                clear_key:function(){
                    m.vm.f.key = "";
                    reclist.search();
                },
                dosearch:function() {
                    reclist.search();
                },
                refreshback:function(){
                	location.href=ctx+"/topic/totopic"
                },
                date_visible:false,
                $computed:{
                    p:{
                		get:function(){
                			return co.media_image_path;
                		}
                	}
                }
            });
          //搜索
            var sear=$("input[name='serch']");

            m.vm.$watch("f.open",function(v){
                reclist.search();
            });

            sear.keydown(function(e){
                if(e.keyCode==13){
                	m.vm.dosearch();
                }
            });
            var first_vm_view_1 = true;
            m.vm.$watch("view",function(view){

            	if(view == 1){
            		if(first_vm_view_1){
                		ex.onMapShow(true)
                	}else{
                		ex.onMapShow();
                	}
            		first_vm_view_1 = false;
            	}
            })

            m.freshTopicInfo();
            return m;
        },
        freshTopicInfo:function(){
        	var m = this;
        	 u.ajax(
                 u.pagevar("topic","__/data/topic.json".p()),
                 function(t){
                     if(t.vl()) {
                         m.vm.topic = t.data;
                         m.vm.permissions = t.data.permissions;
                         m.vm.open = t.data.shareflag;
                         m.vm.self_topic=t.data.self_topic;
                         m.vm.num_of_collect=t.data.num_of_collect;
                         if(t.data.permissions==-1){
                             m.vm.permissions="所有";
                         }else if(t.data.permissions==0){
                             m.vm.permissions="我的好友"
                         }else{
                             m.vm.permissions="指定好友"
                         }
                     }
                     ht.addClass("no_cl_loading");
                 }
             );
        }
    }).init();


    var reclist = ({
        init:function(){
            var m = this;

            m.vm = avalon.define({
                $id:"reclist",
                list:[],


                cardClick:function(e,el){
                    if($(e.target).not("img,.del,.info>em,a")){
                        ex.onRecClick(el.$model);
                    }
                },


                recdele:function(el){
                    u.confirm("是否要删除该记录:[" + el.name + "]",function(cancel){
                        if(!cancel) {
                            u.ajax(
                                u.pagevar("recdel","__/data/success.json".p()),
                                {id:el.id},
                                function(t){
                                    if(t.vl()) {
                                        m.vm.list.remove(el);
                                        u.msg("记录已经删除");
                                        main.freshTopicInfo();
                                    }
                                }
                            );
                        }
                    });

                }
            });


            m.datebox = $(".date_box");
            m.el = $(".reclist");

            m.cardCont = m.el.find(".cardCont");

            m.card_pos_arr = [];
            m.fresh_pos_arr = function(){
                m.card_pos_arr.length = 0;
                m.cardCont.find(".card").each(function(i){
                    var me=$(this);
                    m.card_pos_arr.push({
                        top:me.position().top + me.outerHeight(true),
                        date:new Date(me.attr("date") * 1).format("%Y/%m"),
                        el: m.vm.list[i]
                    });
                });
            }


            m.cardCont.scroll(function(e){
                var pos = m.cardCont.prop("scrollTop");
                $.each(m.card_pos_arr,function(key,ele){
                    if(ele.top>pos){
                        m.datebox.html(ele.date);
                        ex.onRecScrollTo(ele.el);
                        return false;
                    }
                });
                m.datebox.html();
            });
            return m;
        },

        search:function(callback){
            var m = this;

            !function(){
                ex.onSearch(main.vm.f.$model);
            }.delayCall(500);

            u.ajax(
                u.pagevar("reclist","__/data/recoder_list.json".p()),
                main.vm.f.$model,
                function(t){
                    if(t.vl()) {
                    	 $.each(t.data.rows,function(k,el){
                    		 el.datef = new Date(el.occurtime*1).format("%Y年%m月%d日")
                    	 });
                        m.vm.list = t.data.rows;
                        main.vm.date_visible = t.data.rows.length;
                        main.vm.topic.num_of_public=t.data.num_of_public;
						main.vm.topic.num_of_private=t.data.num_of_private;
                        $.each(t.data.rows,function(key,ele){
                            ex.dic_id_poi[ele.id] = ele;
                        });

                        !function(){
                            m.fresh_pos_arr();
                            m.cardCont.prop("scrollTop",0);
                            m.cardCont.scroll();

                            if(callback){
                                callback();
                            }

                        }.delayCall(300);

                        map_rec_list.vm.list = t.data.rows;
                    }
                }
            );
        }
    }).init();


    var map_rec_list = ({
        init:function(){
            var m = this;
            m.el = $(".map_rec_list");
            m.aa = m.el.find("a");

            m.aa.each(function(i){
                var me=$(this);

                me.mouseenter(function(){
                    if(!me.find("li").length)   return;
                    me.addClass("hover");
                    mouse_leave_a.killDelayCall();
                });

                me.mouseleave(function(){
                    mouse_leave_a.delayCall(100);
                });

                function mouse_leave_a(){
                    me.removeClass("hover");
                }
            });

            m.vm = avalon.define({
                $id:"map_rec_list",
                list:[],
                itemclick:function(el){
                    ex.onRecClickOnMapMode(el);
                }
            });

            return m;
        }
    }).init();

    ex.map_rec_list = map_rec_list;

    $(function(){

        reclist.search(function(){
            map2.fresh();
        });

        avalon.scan()

    });
    return ex;
});
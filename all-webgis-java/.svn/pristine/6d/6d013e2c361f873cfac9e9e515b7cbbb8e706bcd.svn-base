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
    var co = require("./comm");

    var root = require.resolve("./");

    cl.rootCondiFunc({
        "@/*":function(){



        },
        "@/._index":function(){
            require.async("$/cslid",function(CSL){
                var slid = new CSL({
                    cont:"#slider",
                    size:{
                        h:750,
                        w:"auto"
                    }
                });


                //标记点
                var dots = $("#f_dot li");
                require.async("$/scrollspy",function(){
                    $(".row")
                        .on("scrollSpy:enter",function(e){
                            var num = this.getAttribute("id").replace(/[^\d]/g,"");

                            dots.removeClass("cur").eq(num).addClass("cur");
                        })
                        .scrollSpy()
                    ;
                });
            });


            var vi = $("#bg_video_a");
            with(vi){
                vi.size = attr("psize").split(",");
                vi.pw = size[0] * 1;
                vi.ph = size[1] * 1;
            }
            var rows_back = $(".row .bkimg").unblockImg();
            var r1 = vi.parent();
            var row_bgs = $(".row>img");

            var arr1 = [],arr2=[];
            var w = cj.winResize(90,function(sw,sh){
                arr1[0] = r1.width();
                arr1[1] = r1.height();
                arr2[0] = vi.pw;
                arr2[1] = vi.ph;
                vi.css(cl.max_on_container(arr1,arr2).css);
                row_bgs.maxonLite();
            });


            $("#f_dot").delegate("li","click",function(e){
                var self = $(this);
                self.addClass("cur").siblings().removeClass("cur");

                var row = $("#row" + self.index());
                $("body,html")
                    .animate({
                        scrollTop:row.position().top
                    })
                ;
            });

            $("#lswh").click(function(e){
                e.preventDefault();
                require.async("$/layer",function(layer){
                    layer.ready(function(){
                        //捕获页
                        layer.open({
                            skin:"ly_skin",
                            type: 1,
                            //shade: false,
                            title: false, //不显示标题
                            content: $('#link_pop') //捕获的元素
                        });
                    });
                })
            });
        }
    });



    return {};
});
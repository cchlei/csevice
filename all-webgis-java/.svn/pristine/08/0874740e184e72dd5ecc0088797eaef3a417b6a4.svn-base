/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2015/9/17
 * Time: 9:03
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    var cl = require("ctool");
    var cj = require("ctooj");
    var comm =require("./comm");
    var  ht = $("html");

    cl.rootCondiFunc(true,{
        "*":function(){
            //require.async("rt/libs/prefixfree.min");
        },
        "._index":function(){
            var animage_w = 1980;
            var animate_h = 720;
            AdobeEdge.loadComposition('AN/index_1', 'IndexPage1', {
                scaleToFit: "none",
                centerStage: "none",
                minW: "0",
                maxW: "undefined",
                width: animage_w + "px",
                height: animate_h + "px"
            }, {
                dom: [
                    {
                        id: 'preloader4',
                        type: 'image',
                        tag: 'img',
                        rect: ['930', '300', '120px', '120px', 'auto', 'auto'],
                        fill: ["rgba(0,0,0,0)", 'AN/images/preloader4.gif', '0px', '0px']
                    }
                ],
                style: {
                    '${symbolSelector}': {
                        isStage: true,
                        rect: [undefined, undefined, '1980px', '720px'],
                        fill: ["rgba(255,255,255,1)"]
                    }
                }
            }, {dom: []});


            var ip1 = $(".IndexPage1");
            ip1.css({
                marginLeft:-animage_w/2,
                marginTop:-animate_h/2,
                background:"",
                position:"absolute"
            });


            //设置初始效果
            cl.run_until(function(){
               if(ip1.css("position") == "relative"){
                   ip1.css({
                       position:"absolute",
                       backgroundColor:"transparent"
                   });
                   return true;
               }
            });



            require.async([
                "rt/libs/fullpage/jquery.fullPage.min",
                "rt/libs/fullpage/jquery.fullPage.css"
            ],function(){

                ht.removeClass("loading");

                //返回到顶部
                var back_top_btn = $(".back_top").click(function(){
                    $.fn.fullpage.moveTo(1, 0);
                });
                var pls = $('#pagelist').fullpage({

                    sectionsColor: ['#1E87CF', '#4BBFC3', '#126599', 'whitesmoke', '#ccddff'],

                    //anchors: ['firstPage', 'secondPage', '3rdPage'],

                    //导航相关
                    navigation: true,
                    navigationPosition: 'right',
                    navigationTooltips: ['封面', '个人应用', '企业服务',"行业应用","云服务","联系我们"],


                    onLeave:function(index,next_index,direction){
                        //对第一屏的控制
                        if(next_index == 1){
                            !function(){
                                $("body").trigger("page_in");
                            }.delayCall(100);
                        }else if (index == 1) {
                            $("body").trigger("page_leave");
                            back_top_btn.show();
                        }


                        //第二屏
                        if(next_index==2){
                            sec2.init();
                        }else{

                        }


                        //控制第三屏
                        if(sec3.cr){
                            if(index == 3){
                                sec3.cr.pause();
                            }else if(next_index == 3){
                                sec3.cr.start();
                            }

                            sec3.liticons.each(function(i){
                                var me=$(this);
                                var delay = cl.random(0.2,1.6);
                                me.css({
                                    "animation-delay":delay+"s",
                                    "-webkit-animation-delay":delay+"s"
                                });
                            });;
                            
                        }
                    },




                    //加载完成
                    afterLoad: function(anchorLink, index){

                        //section 2
                        if(index == 2){
                            //moving the image
                            $('#section1').find('img').delay(500).animate({
                                left: '0%'
                            }, 1500, 'easeOutExpo');

                            $('#section1').find('p').first().fadeIn(1800, function(){
                                $('#section1').find('p').last().fadeIn(1800);
                            });
                        }

                        //section 3
                        if(anchorLink == '3rdPage'){
                            //moving the image
                            $('#section2').find('.intro').delay(500).animate({
                                left: '0%'
                            }, 1500, 'easeOutExpo');
                        }
                    }
                });

            });


            //第二屏
            var sec2 = {
                isinited:false,
                el:$("#sec2"),
                init:function(){
                    if(sec2.isinited)   return;
                    sec2.isinited = true;
                    require.async("_/autoScroller/autoScroller",function(){
                        $(".stage",sec2.el).autoScroll({
                            host:sec2.el,
                            //throDelay:300, animDelay: 750,
                            isCalcViewSize:false,
                            viewSizeRatio:1.13
                        });



                        var scr = $("#ipad_screen").autoScroll({
                            host:sec2.el,
                            //throDelay:150, animDelay: 360,
                            isCalcViewSize:false,
                            //viewSizeRatio:1.6,
                            move:function(xr,yr){
                                ipad.css({ marginLeft: ipad.ml + 90 * xr});
                                ipad_cont.css({ top: 15 * (yr + 1) / 2});
                            }
                        });

                        var ipad = scr.parent();
                        ipad.ml = parseInt(ipad.css("marginLeft"));
                        var ipad_cont = ipad.parent();


                        sec2.el.unblockImg();

                    });
                }

            };







            var sec3 = {
                liticons:$(".carousel-container i"),
                liticons_cont:$(".carousel-container>div").each(function(i){
                    var me=$(this);
                    var delay = cl.random(-30,2);
                    me.css({
                        "animation-delay":delay+"s",
                        "-webkit-animation-delay":delay+"s"
                    });
                })
            };
            require.async(["_/cring/cring","$/easing"],function(CRing){
                sec3.cr = new CRing(".cring",{
                    width:1400,
                    height:900,
                    //ease:"easeOutBack",
                    ease:"easeInOutExpo",

                    dura:720,

                    blur_ratio:0,
                    h_ratio:0.08,

                    child_width:454,
                    child_height:360,

                    autoplay:true,
                    autoplay_delay:5000,

                    change:function(index){

                    },
                    before_change:function(index){

                    }
                });


                with(sec3.cr.el){
                    css({
                        marginLeft:-outerWidth()/2
                    })
                }


            });
        }
    })
    return {};



});
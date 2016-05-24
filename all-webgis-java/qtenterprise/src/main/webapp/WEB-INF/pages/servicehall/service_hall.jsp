<!doctype html><html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <%@ include file="/assets/global.jsp"%>
    <title>服务大厅</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/service_hall.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"  main="${ctx}/assets/js/service_hall"></script>
    <script>
        /*类型加载*/
        var service_hall_type =ctx+"/assets/data/service_hall_type.json";
        /*内容加载*/
        var service_hall_ajax =ctx+"/entrelmap//getalllist";
        /*入驻企业信息加载 */
        var hall_index_qy_ajax =ctx+"/entcainfo/service_hall";
    </script>
</head>
<body>
<div class="navright">
    <div class="fwtop">
        <img src="${ctx}/assets/images/fw_logo.png" alt="" class="fwlogo">
        <i class="wx"></i>
        <img src="${ctx}/assets/images/fw_dt.png" alt="" class="fwdt">
        <i class="topbj"></i>
        <div class="user">
            <img src="${ctx}/assets/images/fw_people.png" alt="">
            <span class="name">用户名:潇湘雨</span>
            <span class="fwquit">退出</span>
        </div>
    </div>
    <div class="service_hall">
        <!--服务大厅查询开始-->
        <div class="service_search">
            <div class="service_search_top">
                <!--行政区开始-->
                <span id="distpicker" class="distpicker">
                    行政区域选择：
                  <select name="province"></select>
                  <select name=""></select>
                </span>
                <input type="hidden" name="area">
                <script>
                    var default_area = {
                        province: "全国",
                        city: "所有",
                        placeholder:false
                    };
                    var arae = $("input:last");
                    var selecs = $("#distpicker>select");
                    selecs.change(function(){
                        setTimeout(area_field_digest,888);
                    });

                    setTimeout(area_field_digest,1500);

                    function area_field_digest(){
                        arae.val(selecs.map(function(i,el){
                            return el.value
                        }).toArray().join(" "));
                    }
                </script>
                <!--行政区结束-->
                <!--搜索框开始-->
                <i></i>
                <input class="fw_value" type="text" />
                <span class="fw_search">搜索</span>
                <!--搜索框结束-->
                <div class="clear"></div>
            </div>
        </div>
        <!--服务大厅查询结束-->

        <div class="service_hall_type">
            <div class="hall_type"></div>
            <div class="hall_branch"></div>
        </div>
        <!--服务大厅查询内容显示开始-->
        <div class="servicemap">
            <ul class="grid">

            </ul>
            <div class="clear"></div>
        </div>
        <div class="page"></div>
        <!--服务大厅查询内容显示结束-->

        <!--服务大厅轮播以及新闻内容开始-->
        <div class="hallcenter">
            <!--轮播开始-->
            <div class="banner">
                <div id="cslid">
                    <img src="${ctx}/assets/images/banner1.jpg" alt="First slide"  class="bannerimg">
                    <img src="${ctx}/assets/images/banner2.jpg" alt="Second slide" class="bannerimg">
                </div>
                <script>
                    use("$/cslid",function(CL){
                        $("#cslid").cslid({
                            size:{w:"auto",h:"579px"},
                            scaleWH:0.588,
                            fademode:true
                        })
                    })
                </script>
            </div>
            <!--轮播结束-->
            <!--右侧快报内容以及入驻企业开始-->
            <div class="news">
                <div class="newsflash">
                    <h3>天润云地图快报</h3>
                    <ul>

                    </ul>
                </div>
                <div class="business">
                    <h3>入驻企业</h3>
                    <div class="it">
                        <ul>
                        </ul>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="clear"></div>
            </div>
            <!--右侧快报内容以及入驻企业结束-->
        </div>
        <!--服务大厅轮播以及新闻内容开始-->
    </div>
</div>
</body>
</html>
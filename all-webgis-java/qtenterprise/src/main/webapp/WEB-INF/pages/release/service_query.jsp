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
	<title>已发布管理</title>
	<link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
	<link rel="stylesheet" href="${ctx}/assets/css/service_query.css"/>
	<script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"  main="${ctx}/assets/js/service_query"></script>
    <script>
        var service_query_ajax = ctx+"/entrelmap/getreleaselist";
    </script>
</head>
<body>
<div class="navright">
    <div class="fwtop"><em>已发布管理</em><span>退出</span></div>
    <div class="service_query">
        <!--服务查询开始-->
        <div class="service_search">
            <div class="service_search_top">
                服务查询<i></i>
                <input class="fw_value" type="text" />
                <span class="fw_search">搜索</span>
            </div>
            <div class="service_type">
                <div class="fwtype">服务类型
                    <span><i></i>餐饮</span>
                    <span><i></i>购物</span>
                    <span><i></i>住宿</span>
                    <span><i></i>出行</span>
                    <span><i></i>文体娱乐</span>
                    <span><i></i>金融服务</span>
                    <span><i></i>生活服务</span>
                    <span><i></i>其他</span>
                </div>
                <br/>
                <div class="senior">高级选择
                    <span>发布时间
                        <select name="fbtime">
                            <option value=3>三个月内</option>
                            <option value=12>一年以内</option>
                            <option value=36>三年以内</option>
                            <option value=60>五年以内</option>
                            <option value=-1>全部</option>
                        </select>
                    </span>
                    <span>更新时间
                        <select  name="gxtime">
                            <option value=3>三个月内</option>
                             <option value=12>一年以内</option>
                            <option value=36>三年以内</option>
                            <option value=60>五年以内</option>
                            <option value=-1>全部</option>
                        </select>
                    </span>
                    <span>运行情况
                        <select name="yxqk">
                            <option value=1>在线</option>
                            <option value=2>挂起</option>
                        </select>
                    </span>
                   
                    <span class="jiekou">
                        <span><i></i>REST</span>
                        <span><i></i>OGC</span>
                    </span>
                </div>
            </div>
        </div>
        <!--服务查询结束-->

        <!--服务查询地图显示开始-->
        <div class="servicemap">
            <ul class="grid">
                <!--<li><img src="../images/h_map.jpg" alt=""><span>公交站点：西安市2号环线</span>
                    <div class="map_describes">
                        <p>服务编码：12345789</p>
                        <p>服务名称：站点289</p>
                        <p>有效期：2015-11-27</p>
                    </div>
                    <div class="handlemap">
                        <span class="xiaxian">下线</span>
                        <span class="yulan">预览</span>
                    </div>
                </li>
                <li><img src="../images/h_map.jpg" alt=""><span>公交站点：西安市2号环线</span>
                    <div class="map_describes">
                        <p>服务编码：12345789</p>
                        <p>服务名称：站点289</p>
                        <p>有效期：2015-11-27</p>
                    </div>
                    <div class="handlemap">
                        <span class="xiaxian">下线</span>
                        <span class="yulan">预览</span>
                    </div>
                </li>
                <li><img src="../images/h_map.jpg" alt=""><span>公交站点：西安市2号环线</span>
                    <div class="map_describes">
                        <p>服务编码：12345789</p>
                        <p>服务名称：站点289</p>
                        <p>有效期：2015-11-27</p>
                    </div>
                    <div class="handlemap">
                        <span class="xiaxian">下线</span>
                        <span class="yulan">预览</span>
                    </div>
                </li>
                <li><img src="../images/h_map.jpg" alt=""><span>公交站点：西安市2号环线</span>
                    <div class="map_describes">
                        <p>服务编码：12345789</p>
                        <p>服务名称：站点289</p>
                        <p>有效期：2015-11-27</p>
                    </div>
                    <div class="handlemap">
                        <span class="xiaxian">下线</span>
                        <span class="yulan">预览</span>
                    </div>
                </li>
                <li><img src="../images/h_map.jpg" alt=""><span>公交站点：西安市2号环线</span>
                    <div class="map_describes">
                        <p>服务编码：12345789</p>
                        <p>服务名称：站点289</p>
                        <p>有效期：2015-11-27</p>
                    </div>
                    <div class="handlemap">
                        <span class="xiaxian">下线</span>
                        <span class="yulan">预览</span>
                    </div>
                </li>
                <li><img src="../images/h_map.jpg" alt=""><span>公交站点：西安市2号环线</span>
                    <div class="map_describes">
                        <p>服务编码：12345789</p>
                        <p>服务名称：站点289</p>
                        <p>有效期：2015-11-27</p>
                    </div>
                    <div class="handlemap">
                        <span class="xiaxian">下线</span>
                        <span class="yulan">预览</span>
                    </div>
                </li>
                <li><img src="../images/h_map.jpg" alt=""><span>公交站点：西安市2号环线</span>
                    <div class="map_describes">
                        <p>服务编码：12345789</p>
                        <p>服务名称：站点289</p>
                        <p>有效期：2015-11-27</p>
                    </div>
                    <div class="handlemap">
                        <span class="xiaxian">下线</span>
                        <span class="yulan">预览</span>
                    </div>
                </li>
                <li><img src="../images/h_map.jpg" alt=""><span>公交站点：西安市2号环线</span>
                    <div class="map_describes">
                        <p>服务编码：12345789</p>
                        <p>服务名称：站点289</p>
                        <p>有效期：2015-11-27</p>
                    </div>
                    <div class="handlemap">
                        <span class="xiaxian">下线</span>
                        <span class="yulan">预览</span>
                    </div>
                </li>-->
            </ul>
            <div class="clear"></div>
        </div>
        <div class="page"></div>
        <!--服务查询地图显示结束-->
    </div>
</div>
</body>
</html>
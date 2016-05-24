<!doctype html><html class="_map_search">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
  <head>
    <title>地图编辑页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<%@ include file="/static/global.jsp"%>
	<base href="${ctx}/">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/map/css/map.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/map/css/all.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/map/css/font_icon/iconfont.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/qtmap/style.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/skin/layer.css">
	<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/layer.js"></script>
	
    <script type="text/javascript" src="${ctx}/static/map/js/avalon/avalon.js"></script>
    <script type="text/javascript" src="${ctx}/static/map/js/cseajs/csea$.js" id="seajsnode" main="_/common"></script>
	<script type="text/javascript" src="${ctx}/qtmap/qtmap.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/map/js/map.js"></script>
    <script type="text/javascript" src="${ctx}/static/map/js/define.js"></script>
    <script type="text/javascript" src="${ctx}/static/map/js/b.js"></script>
    
	<!-- 上传勿删 -->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/webuploader/webuploader.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/webuploader/style.css" />
    <script type="text/javascript" src="${ctx}/static/webuploader/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/webuploader/webuploader.js"></script>
    <script type="text/javascript" src="${ctx}/static/webuploader/upload.js"></script>
  </head>
<body>

<div  class="warp">
    <div class="map_cont" ms-controller="map_view_all">
        <div id="map_tookit" ms-include="tpl_map_tookit" class="map_tookit"></div>
        <!-- 基础底图 -->
   		<div id="qtmap" style="width:100%;position:absolute;top: 0px;bottom: 0px;"></div>
    </div>
</div>

<div id="tpl" style="display: none;">
    <!--地图工具 dep0-->
    <!--类别选择-->
        <textarea id="tpl_map_tookit" type="avalon">
            <em class='save_feature' ms-click="proj_save" ms-class-disable="proj_save_disable"> <em>&#xe60f;</em> {{map_save_btn_tx}}</em>
            <div class="tab_btn_cont">
                <em ms-repeat="main_tab_cfg" ms-class-cur="tb_index[0]==$index" ms-attr-class="el.type" ms-click="tab_switch($index)">{{el.name|html}}</em>
            </div>
            <ul>
                <li ms-repeat="main_tab_cfg" ms-attr-class="{{el.type}} li_level0" ms-class-showing="tb_index[0]==$index">
                    <div class="inn" ms-include="{{el.type}}"></div>
                </li>
            </ul>
        </textarea>


    <!--标记面板-->
    <!--点线面类型选择层-->
        <textarea id="tpl_mark" type="avalon">
            <div class="tab_btn_cont">
                <em
                        ms-repeat="mark_cfg"
                        ms-click-1="tab_switch($outer.$index + '' +$index)"
                        ms-click-2="create_mpel($index,el)"
                        ms-class-cur="tb_index[1]==$index"
                        >{{el.name|html}}</em>
            </div>
            <!--地图标记元素设置面板容器-->
            <ul>
                <!--点线面单独属性修改层-->
                <li ms-repeat="mark_cfg" ms-class-showing="tb_index[0]==2 && tb_index[1]==$index">
                    <!--存在tabs并且对象存在geom(即经过绘制)||text|style等-->
                    <div ms-if="el.tabs">
                        <div class="tab_btn_cont obj_prop_tab" ms-visible="mp_el.geom">
                            <em
                                    ms-repeat="el.tabs"
                                    ms-click-1="tab_switch($outer.$outer.$index + '' + $outer.$index +''+ $index);"
                                    ms-class-cur="tb_index[2]==$index"
                                    >
                                {{el.type|html}}
                            </em>
                        </div>
                        <ul>
                            <li
                                    ms-repeat="el.tabs"
                                    ms-class-showing="tb_index[0]==2 && tb_index[1]==$outer.$index && tb_index[2]==$index"
                                    >
                                <div class="inn" ms-include="el.tpl"></div>
                            </li>
                        </ul>
                    </div>
                    <div ms-if="el.tpl" ms-include="el.tpl"></div>

                </li>
            </ul>
        </textarea>

    <!--dep1-->
    <!--搜索面板-->
    <noscript id="tpl_search" type="avalon">
        <div class="search_box">
            <span><input type="" id="search_input" ms-duplex="search_key" placeholder="输入关键字进行搜索"/><em class="se_btn">搜索</em></span>
            <ul ms-class-showing="search_result.length>0" ms-visible="search_key">
                <li ms-repeat="search_result" ms-click="search_list_click(el)">
                    <em>{{$index+1}}. {{el.name}}</em>
                </li>
            </ul>
            <p ms-visible="search_key && !search_result.length">搜索结果为空</p>
        </div>
    </noscript>

    <!--图层面板-->
        <textarea id="tpl_layer" type="avalon">
            <div class="layer_list">
                <ul>
                    <li
                            class="layer_el"
                            ms-class-selected="el.selected==true"
                            ms-repeat="layer_list"
                            ms-attr-lid="el.id"
                    >
                        <span ms-css-background-color="color_picker_data[$index]"><i></i></span>
                        <em>{{el.name}}</em>
                    </li>
                </ul>
            </div>
        </textarea>

    <!--设置面板-->
    <noscript id="tpl_setting" type="avalon">
        <div class="setting_panel">
            <p class="r_label">地图名称 <i ms-visible="proj_attr.name.length>29"> (不能大于30字符)</i></p>
            <input type="text" placeholder="请输入项目名称" ms-duplex="proj_attr.name" ms-class-error = "proj_attr.name.length>29"/> <br/>
            <p class="r_label">地图描述<i ms-visible="proj_attr.desc.length>199"> (不能大于200字符)</i></p>
            <textarea ms-duplex="proj_attr.desc" placeholder="请输入项目描述" ms-class-error="proj_attr.desc.length>199"></textarea>
            <p class="r_label"> <input id="project_is_shared" type="checkbox" ms-duplex-checked="proj_attr.share"/> <label for="project_is_shared">共享</label> </p>
        </div>
    </noscript>


    <!--ft列表面板-->
    <noscript id="tpl_feature_list" type="avalon">
        <div class="feature_list_panel">
            <ul ms-visible="user_ft_list.length">
                <li ms-repeat="user_ft_list" ms-attr-__id="el.id||el.tmpid">
                    <a href="javascript:;"></a>
                    <em>
                        <i>{{el.type|ft_type_icon_chart|html}}</i>
                        <b>{{el.name||"未命名标注"|html}}</b>
                    </em>
                </li>
            </ul>
            <p ms-visible="!user_ft_list.length" style="text-align: center;">列表为空</p>
        </div>
    </noscript>


    <!--dep1-->
    <!--通用--文本设置text-->
    <noscript id="tpl_text_form" type="avalon">
										
        <div class="draw_tip_pan" ms-visible="!mp_el.geom">
            <div class="tip_line" ms-visible="mp_el.type==EL_POINT">单击以画点</div>
            <div class="tip_" ms-visible="mp_el.type==EL_LINE">单击最后的点结束绘制线条</div>
            <div class="tip_line" ms-visible="mp_el.type==EL_POLY">单击第一个点结束绘制多边形</div>
            <em class="btn_cancel_draw" ms-click="cancel_draw">取消绘制</em>
        </div>
        <div class="mt_infotx_panel feature_attr_apn" ms-visible="mp_el.geom">
            <p class="r_label">标注名称 <i ms-visible="mp_el.name.length>19">(字数不能大于20字)</i></p>
            <input type="text" placeholder="请输入标注名称" ms-duplex="mp_el.name" ms-class-error="mp_el.name.length>19"/> <br/>
            
            
            <p class="r_label">发生时间<i ms-visible="mp_el.occurtime.length>10">(格式如：2015-03-30)</i></p>
            <input type="text" onblur="EC.emit('checkDate')" placeholder="请输入发生时间，格式如：2015-03-30" ms-duplex="mp_el.occurtime" ms-class-error="mp_el.occurtime.length>10"/> <br/>
            
            <p class="r_label">标注描述 <i ms-visible="mp_el.desc.length>199">(字数不能大于200字)</i></p>
            <textarea ms-duplex="mp_el.desc" placeholder="请输入标注描述" ms-class-error="mp_el.name.length>199"> </textarea>
        </div>
    </noscript>

    <!--符号--外观设置style）-->
    <textarea id="tpl_style_form" type="avalon">

        <div class="mt_style_sett_panel feature_attr_apn">
            <!--大小设置-->
            <p class="scale check_box">
                <em ms-repeat="el_scale" ms-class-cur="mp_el.scale == el.value" ms-click="scale_change(el.value)">{{el.name}}</em>
            </p>

            <!--符号颜色设置-->
            <p class="color_picker">
                <i
                        ms-repeat="color_picker_data"
                        ms-class-cur="el==mp_el.color"
                        ms-css-background-color="el"
                        ms-css-width="100/(color_picker_data.length/2) + '%'"
                        ms-click="color_change(el)"
                        > </i>
                <em
                        class="cl_priview"
                        ms-class-black="mp_el.color=='#ffffff'"
                        ms-css-background-color="mp_el.color">颜色预览({{mp_el.fill_color}})
                </em>
            </p>
        </div>
    </textarea>

    <!--符号图形设置 symbol）-->
    <textarea id="tpl_symbol_form" type="avalon">
        <div class="mt_symbols_panel feature_attr_apn">
            <p class="symbols check_box">
                <em
                        ms-repeat="symbol_list"
                        ms-class-cur="mp_el.symbol == el.sid"
                        ms-click="symbol_change(el.sid)"
                        ms-css-background-image="url({{el.src || def_symbol_src}})"> {{el.name}}
                </em>
            </p>
        </div>
    </textarea>

    <!--附件上传-->
    <textarea id="tpl_files_form" type="avalon">
    	
    	<div class=uploaderContainer></div>
    	<div class=uploaderGener style="display:none;">
	    	<div ms-attr-__id="uploader{{mp_el.tmpid}}" class="uploader">
	            <div class="queueList">
	                <div ms-attr-__id="dndArea{{mp_el.tmpid}}" class="placeholder">
	                    <div ms-attr-__id="filePicker{{mp_el.tmpid}}"></div>
	                    <p>或将照片拖到这里，单次最多可选9张</p>
	                </div>
	            </div>
	            <div class="statusBar" style="display:none;">
	                <div class="progress">
	                    <span class="text">0%</span>
	                    <span class="percentage"></span>
	                </div><div class="info"></div>
	                <div class="btns">
	                    <div class="filePicker" ms-attr-__id="filePicker_{{mp_el.tmpid}}"></div><div class="uploadBtn">开始上传</div>
	                </div>
	            </div>
	        </div>
    	</div>	
		<div style="padding-bottom: 40px;padding-left: 20px;">{{mp_el.alreadyfiles | parsefiles | html}}</div>
    </textarea>
    
    <!--符号-坐标设置-->
    <textarea id="tpl_latlon_form" type="avalon">
        <em>lat</em> <br/>
        <input type=""/> <br/>
        <em>lon</em> <br/>
        <input type=""/>
    </textarea>


    <!--线条和填充--线设置-->
    <textarea id="tpl_stroke_form" type="avalon">
            <div class="mt_linestyle_panel feature_attr_apn">
                <p class="r_label">线透明度</p>
                <input ms-duplex="mp_el.stroke_opacity" class="stroke_opacity"/>

                <p class="r_label">线宽(px)</p>
                <input ms-duplex="mp_el.stroke_width" class="stroke_width"/>

                <p class="r_label">线颜色</p>
                <p class="color_picker">
                    <i
                            ms-repeat="color_picker_data"
                            ms-class-cur="el==mp_el.stroke_color"
                            ms-css-background-color="el"
                            ms-css-width="100/(color_picker_data.length/2) + '%'"
                            ms-click="stroke_color_change(el)"
                            > </i>
                    <em
                            class="cl_priview"
                            ms-class-black="mp_el.stroke_color=='#ffffff'"
                            ms-css-background-color="mp_el.stroke_color">颜色预览({{mp_el.fill_color}})
                    </em>
                </p>
            </div>
        </textarea>


    <!--填充--填充设置-->
    <textarea id="tpl_fill_form" type="avalon">
            <div class="mt_fillstyle_panel feature_attr_apn">
                <p class="r_label">填充透明度</p>
                <input ms-duplex="mp_el.fill_opacity" class="fill_opacity"/>
                <p class="r_label">填充颜色</p>
                <p class="color_picker">
                    <i
                            ms-repeat="color_picker_data"
                            ms-class-cur="el==mp_el.fill_color"
                            ms-css-background-color="el"
                            ms-css-width="100/(color_picker_data.length/2) + '%'"
                            ms-click="fill_color_change(el)"
                            > </i>
                    <em
                            class="cl_priview"
                            ms-class-black="mp_el.fill_color=='#ffffff'"
                            ms-css-background-color="mp_el.fill_color">颜色预览({{mp_el.fill_color}})
                    </em>
                </p>
            </div>
        </textarea>
    <noscript id="tpl_feature_tip_pan" type="avalon">
        <p class="mt_feature_tip_pan">请选择要绘制的元素。。。</p>
    </noscript>
</div>
</body>
</html>
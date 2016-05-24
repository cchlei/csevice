<!doctype html><html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/assets/global.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>元数据编辑_地图已发布</title>
<link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
<link rel="stylesheet" href="${ctx}/assets/css/metadata_release.css"/>
<script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"  main="__/js/metadata_release"></script>
<script>
    var uploader_conf = [
        //图层图片
        {server:ctx + '/entfile/upload'}
    ];
</script>
</head>
<body>
<div class="navright">
    <div class="maptop"><em>元数据编辑</em><span>退出</span></div>
    <form class="mapupdata" action_save="${ctx}/entmap/updateMap" action_release="${ctx}/entmap/releaseMap" method="post">
    	<input type="hidden" name="savemode" value="1" id="savemode"/>
    	<input type="hidden" name="id" value="${entmap.id}" />
    	    	<input type="hidden" name="isshare" value="${entmap.isshare}" />
    	<input type="hidden" name="shareurl" value="${entmap.shareurl}" />
    	<input type="hidden" name="user.id" value="${entmap.user.id}" />
    	<input type="hidden" name="validityregion" value="${entmap.validityregion}" />
        <p>
            <em>图层名称：</em>
            <span><input type="text" name="mapname" value="${entmap.mapname}"/></span>
        </p>
        <p>
        <input type="hidden" id="umicon" name="umicon" value="${entmap.umicon}"/>
            <em>图层图片：</em>
            <span class="info">
                <a href="javascript:;" id="" class="uploader">上传</a>
            </span>
        </p>
        <p><span class="preview"><span class="tip" style="display: none;">上传中...</span><img id="imgumicon"></span></p>
   			<script>
				if(!/^(undefined|null|)$/.test("${entmap.umicon}")){
					$('#imgumicon').attr('src','http://oss.trmap.cn/rs/download/${entmap.umicon}');
				}
			</script>
        <p>
            <em class="fwmsname">服务描述：</em>
            <span><textarea name="mapdesc">${entmap.mapdesc}</textarea></span>
            <em class="grey">描述不超过200个字符</em>
        </p>
        <p>
            <em>接口类型：</em>
            <span>
                <input name="serviceType" class="interfacetype" id="typerest" checked type="radio" value="REST">REST 
                <input name="serviceType" class="interfacetype" id="typeogc" type="radio" value="OGC">OGC
            </span>
        </p>
        <p class="biaoqian">
            <em>标签：</em>
            <span class="span">
                <input name="tags" class="label" type="checkbox" value="餐饮" ><label>餐饮</label>
                <input name="tags" class="label" type="checkbox" value="购物" ><label>购物</label>
                <input name="tags" class="label" type="checkbox" value="住宿" ><label>住宿</label>
                <input name="tags" class="label" type="checkbox" value="出行" ><label>出行</label>
                <input name="tags" class="label" type="checkbox" value="文体娱乐" ><label>文体娱乐</label>
                <input name="tags" class="label" type="checkbox" value="金融服务" ><label>金融服务</label>
                <input name="tags" class="label" type="checkbox" value="生活服务" ><label>生活服务</label>
                <input name="tags" class="label" type="checkbox" value="其他" ><label>其他 </label>
            </span>
        </p>
        <p><em>行政区划级别：</em>

            <span id="distpicker">
                  <select name="province"></select>
                  <select name=""></select>
            </span>

            <input type="hidden" name="area">
            <script>
            	var tags = "${entmap.tags}";
            	$.each(tags.split(','),function(k,v){
					$("input[value="+v+"]").attr("checked","checked").prev().addClass("jqTransformChecked");
            	})
				$("input[name='serviceType'][value='${entmap.serviceType}']").attr("checked", true);
				
				
            	var erea = "${entmap.area}";
                var default_area = {
                    province: erea.split(' ')[0],
                    city: erea.split(' ')[1],
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
        </p>
        
        <p><em>有效期至：</em><span><fmt:formatDate value="${entrelmap.validitytime}" pattern="yyyy年MM月dd日 HH:mm:ss" /></span></p>
        <p><em>要素数量：</em><span>${entrelmap.featurecount}</span></p>
        <p><em>范围：</em><span>${entrelmap.scope}</span></p>
        <p><em>坐标系：</em><span>${entrelmap.coordinate}</span></p>
        <p><em>发布单位：</em><span>${entmap.user.username}</span></p>
        <p><em>发布时间：</em><span><fmt:formatDate value="${entrelmap.releasetime}" pattern="yyyy年MM月dd日 HH:mm:ss" /></span></p>
        <div class="greybg"><p><em>REST服务：</em><span><a href="${entrelmap.shareurl}" target="_blank">${entrelmap.shareurl}</a></span></p></div>
<!--         <div class="greybg"><p><em>OGC服务</em><span>http://www.tiraingis.com/rest?mapid=12321321321</span></p></div> -->
        <div class="saveupdate">
            <span class="mapsave" type="submit" __onclick="$(this).parents('form').submit()">保存</span>
            <span class="mapup"  __onclick="$(this).parents('form').submit()">更新</span>
        </div>
    </form>
    <div class="updatelayer layersuccess" >
        <img src="${ctx}/assets/images/p_success.png" height="45" width="45" alt="">
        <div class="uptishi">
            <h3>恭喜您，已经更新成功！</h3>
            <p><span class="minclose"></span>秒以后关闭并跳转到地图制作与发布页面！</p>
        </div>
        <div class="clear"></div>
    </div>
    <div class="updatelayer layerfail" >
        <img src="${ctx}/assets/images/p_fail.png" height="45" width="45" alt="">
        <div class="uptishi">
            <h3>对不起，您发布失败</h3>
            <p>请重新发布</p>
        </div>
        <div class="clear"></div>
    </div>
</div>
</body>
</html>
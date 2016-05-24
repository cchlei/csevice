<!doctype html><html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/assets/global.jsp"%>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>元数据编辑_地图未发布</title>
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
        <p><em>有效期：</em>
            <span>
                <select name="validityregion">
                    <option value="">请选择有效期</option>
                    <option value="1">一年</option>
                    <option value="2">两年</option>
                    <option value="3">三年</option>
                    <option value="4">五年</option>
                </select>
                <script>
                	$("select:last").val('${entmap.validityregion}')
                </script>
            </span>
        </p>
        <div class="saveupdate">
            <span class="mapsave" type="submit" __onclick="$(this).parents('form').submit()">保存</span>
            <span class="maprelease"  __onclick="$(this).parents('form').submit()">发布</span>
        </div>
    </form>
    <div class="releaselayer" >
            <h3>发布协议</h3>
            <p>【审慎阅读】<span>当您在发布前，应当认真阅读以下协议。</span>请您务必审慎阅读、充分理解协议中相关条款内容，其中包括：</p>
            <p>1、发布协议发布协议发布协议发布协议发布协议发布协；</p>
            <p>2、发布协议发布协议发布协议发布协议发布协议发布协；</p>
            <p>3、发布协议发布协议发布协议发布协议发布协议发布协。</p>
            <p>1、发布协议发布协议发布协议发布协议发布协议发布协；</p>
            <p>2、发布协议发布协议发布协议发布协议发布协议发布协；</p>
            <p>3、发布协议发布协议发布协议发布协议发布协议发布协。</p>
            <p><span>如果您对协议有任何疑问，可向平台客服咨询。</span></p>
            <p>【特别提示】<span>当您按照编辑页面提示信息填写编辑后，阅读并同意协议且完成所有程序有，即表示您已充分阅读、理解并接受协议的全部内容。</span></p>
            <p>阅读协议的过程中，如果您不同意相关协议或其中任何条款约定，您应理解停止发布程序。</p>
            <p class="agreebutton"><span class="calcenagree">取消协议</span><span  class="agreement">同意协议</span></p>
    </div>

</div>
</body>
</html>
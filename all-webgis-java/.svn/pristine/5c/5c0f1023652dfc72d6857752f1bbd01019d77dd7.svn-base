<!DOCTYPE html><html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
<meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>个人地图服务</title>
    <%@ include file="/static/global.jsp"%>
	<base href="${ctx}/">
    <link rel="stylesheet" href="${ctx}/static/css/maplist.css"/>
    <script src="${ctx}/static/libs/cseajs/csea$.js"></script>
    <script src="${ctx}/static/libs/echarts-all.js"></script>
    <script>
    var option = {
            title: {
                text: "所选地图访问点击量",
                x: "center"
            },
            tooltip: {
                trigger: "item",
                formatter: "{a} <br/>{b} : {c}"
            },
            xAxis: [
                {
                    type: "category",
                    name: "日期",
                    splitLine: {show: false},
                    data: ["2015-10-10", "2015-10-11", "2015-10-12", "2015-10-13", "2015-10-14", "2015-10-15", "2015-10-16", "2015-10-17", "2015-10-18"]
                }
            ],
            yAxis: [
                {
                    type: "log",
                    name: "次"
                }
            ],
            calculable: true,
            series: [
                {
                    name: "点击量",
                    type: "line",
                    data: [1, 3, 9, 27, 81, 247, 741, 2223, 6669]

                }
            ]
        };
    
    
        seajs.usep("_/maplist/_,ctooj,$/layer",function(Maplist,cj,layer){

            var ec;
            var ml = new Maplist("#mapcont",{
                reqpath:"${ctx}/mapedit/userMapList/"+${isshare},
                rows:24,
                //删除
                onItemDel:function(itemid,tooken){
                	var m = this;
                	var item = m.listCont.find("[mid="+ itemid +"]");
                	var confirmStr = "";
                	var btnStr = "";
                	if(item.is(".shared")){//已分享状态删除确认
                		confirmStr = "该地图为已分享状态，删除该地图会级联删除引用到该地图的矢量及附件信息，您确认要删除？";
                		btnStr = "确定";
                	}else{//为分享状态删除确认
                		confirmStr ="确认删除？";
                		btnStr = "确定";
                	}
                	layer.confirm(confirmStr,{btn: [btnStr, '取消']},function(index){
                		layer.close(index);
                    	$.ajax({
	                		url:"${ctx}/mapedit/deleteMap?mapid=" + itemid,
	                		success:function(data){
	                			var result = eval("(" + data + ")");
	                			if(result.result=="success"){
	                				 tooken();
	                				 layer.msg('删除成功');
	                			}
	                		}
	                	})
                	})
                },
                //分享
                onItemShare:function(itemid,tooken){
                	var m = this;
                	var item = m.listCont.find("[mid="+ itemid +"]");
                	var confirmStr = "";
                	var btnStr = "";
                	if(item.is(".shared")){//取消分享操作
                		confirmStr = "分享链接</br><a target='_blank' href='"+item.attr('shareurl')+"'>"+item.attr('shareurl')+"</a>";
                		btnStr = "取消分享";
                	}else{//分享
                		confirmStr ="确认分享?";
                		btnStr = "确定";
                	}
                	layer.confirm(confirmStr,{btn: [btnStr, '取消']},function(index){
                		layer.close(index);
	                	$.ajax({
	                		url:"${ctx}/mapedit/publicMap",
               				data:{
	                			mapid:itemid,
	                			timelabel:new Date().getTime()
	                		},
	                		success:function(data){
	                			var result = eval("(" + data + ")");
	                			if(result.result=="success"){
	                				if(${isshare}==0||${isshare}==1){
	                		        	tooken();
	                				}
	                				item.toggleClass("shared");
	               		        	layer.msg(result.action+'分享成功');
	                			}
	                		}
	                	})
                	})
                },
				//统计
                onChartsShow:function(itemid,infocont,first,tooken){
                	var m = this;
                    if(first){
                        ec = echarts.init(infocont.find(".cont")[0]);
                    }
					$.ajax({
						url:"${ctx}/record/countByMapId/" + itemid,
						data:{
                			timelabel:new Date().getTime()
						},
						success:function(data){
							if(data.result=="success"){
								if(data.x.length==0){
									layer.msg('暂无数据');
									return;
								}
								option.series[0].data = data.y;
								option.xAxis[0].data = data.x;
								option.title.text =data.mapname+"地图服务总访问量为："+data.totalNum+"次";
							}else{
								layer.msg(data.result);
								return;
							}
							
	                    	tooken();
 							ec.clear();
		                    //展示数据
	                    	ec.setOption.delayCall(m.sett.infopan_dura + 200,ec,[option]);	
		                    ec.resize.delayCall(m.sett.infopan_dura + 100,ec)
		                
                		}
					})
                }
            })

            cj.winResize(function(){
                if(ec) ec.resize();
            });
            
            $(function(){
                //顺序 逆序切换
                var topem = $(".topbar p>em").click(function(){
                    topem.toggleClass("show");
                    dosearch();
                });
                topem.first().addClass("show");
                //顶部工具条
                var topbar = $(".topbar");
                topbar.input = topbar.find("input");
                topbar.find(".glass").click(function(){
                    dosearch();
                });

                topbar.input.keydown(function(e){
                    if(e.keyCode == 13){
                        dosearch();
                    }
                })


                //执行搜索方法
                function dosearch(){
                    ml.fresh({
                        searchText:topbar.input.val(),
                        timeindex:topbar.find("[order]:visible").attr("order")
                    });
                }
            });
            
            
        });
        

</script>
</head>
<body>
	<div class="topbar">
        <p>
            <em order="desc">时间 <b>&#xe61f;</b></em>
            <em order="asc">时间 <b>&#xe61d;</b></em>
            <input type=""/>
            <span class="glass">&#xe600;</span>
        </p>
        <a class="btn" href="${ctx}/mapedit/toEdit"><em>&#xe61e;</em>添加地图</a>
    </div>
    <div id="mapcont"></div>
    <style>
	    
    	._cmd_cc_maplist .item .img_menu em.share{
    		display:block;
    	}
    	._cmd_cc_maplist .item .img_menu em.share.ed{
    		display:none;
    	}
    	._cmd_cc_maplist .item.shared .img_menu em.share{
    		display:none;
    	}
    	._cmd_cc_maplist .item.shared .img_menu em.share.ed{
    		display:block;
    	}
    </style>
</body>
</html>
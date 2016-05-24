<!DOCTYPE html><html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>
<title>资料修改或重置密码</title>
<link rel="stylesheet" type="text/css" href="${ctx }/assets/libs/jquery-easyui-1.4.5/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx }/assets/libs/jquery-easyui-1.4.5/themes/icon.css">
<script type="text/javascript" src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx }/assets/libs/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx }/assets/libs/echarts/echarts.min.js"></script>
<script type="text/javascript">
//柱状图
$(function() {
	$.ajax({
		url : ctx+"/count/sumregister",
		type : "post",
		data : {
			begindate : "",
			enddate : ""
		},
		dataType : "json",
		success : function(data) {
			//最近12月变化曲线
	        var total = echarts.init(document.getElementById('total'));
	        option = {
        	    title: {
        	        text: '最近12个月用户注册总量',
        	        subtext: '天润云地图'
        	    },
        	    tooltip: {
        	        trigger: 'axis'
        	    },
        	    legend: {
        	        data:['个人版','企业版']
        	    },
        	    toolbox: {
        	        show: true,
        	        feature: {
        	            dataZoom: {},
        	            dataView: {readOnly: false},
        	            magicType: {type: ['line', 'bar']},
        	            restore: {},
        	            saveAsImage: {}
        	        }
        	    },
        	    xAxis:  {
        	        type: 'category',
        	        boundaryGap: false,
        	        data: data.data.xStr
        	    },
        	    yAxis: {
        	        type: 'value',
        	        axisLabel: {
        	            formatter: '{value} 人'
        	        }
        	    },
        	    series: [
        	        {
        	            name:'个人版',
        	            type:'line',
        	            data: data.data.p,
        	            markPoint: {
        	                data: [
        	                    {type: 'max', name: '最大值'},
        	                    {type: 'min', name: '最小值'}
        	                ]
        	            },
        	            markLine: {
        	                data: [
        	                    {type: 'average', name: '平均值'}
        	                ]
        	            }
        	        },
        	        {
        	            name:'企业版',
        	            type:'line',
        	            data:data.data.e,
        	            markPoint: {
        	            	data: [
           	                    {type: 'max', name: '最大值'},
        	                    {type: 'min', name: '最小值'}
           	                ]
        	            },
        	            markLine: {
        	                data: [
        	                    {type: 'average', name: '平均值'}
        	                ]
        	            }
        	        }
        	    ]
        	};
	        total.setOption(option);
	        //最近12月用户注册增量
	        var increment = echarts.init(document.getElementById('increment'));
	        option = {
        	    title: {
        	        text: '最近12个月用户注册增量',
        	        subtext: '天润云地图'
        	    },
        	    tooltip: {
        	        trigger: 'axis'
        	    },
        	    legend: {
        	        data:['个人版','企业版']
        	    },
        	    toolbox: {
        	        show: true,
        	        feature: {
        	            dataZoom: {},
        	            dataView: {readOnly: false},
        	            magicType: {type: ['line', 'bar']},
        	            restore: {},
        	            saveAsImage: {}
        	        }
        	    },
        	    xAxis:  {
        	        type: 'category',
        	        boundaryGap: false,
        	        data: data.data.xStr
        	    },
        	    yAxis: {
        	        type: 'value',
        	        axisLabel: {
        	            formatter: '{value} 人'
        	        }
        	    },
        	    series: [
        	        {
        	            name:'个人版',
        	            type:'line',
        	            data: data.data.pi,
        	            markPoint: {
        	                data: [
        	                    {type: 'max', name: '最大值'},
        	                    {type: 'min', name: '最小值'}
        	                ]
        	            },
        	            markLine: {
        	                data: [
        	                    {type: 'average', name: '平均值'}
        	                ]
        	            }
        	        },
        	        {
        	            name:'企业版',
        	            type:'line',
        	            data:data.data.ei,
        	            markPoint: {
        	            	data: [
           	                    {type: 'max', name: '最大值'},
        	                    {type: 'min', name: '最小值'}
           	                ]
        	            },
        	            markLine: {
        	                data: [
        	                    {type: 'average', name: '平均值'}
        	                ]
        	            }
        	        }
        	    ]
        	};
	        // 使用刚指定的配置项和数据显示图表。
	        increment.setOption(option);
		}
	});
});
</script>
</head>
<body>
<style>
	html,body{height:98.6%;}
</style>
    <div id="total" style="width: 100%;height:50%;"></div>
    <div id="increment" style="width: 100%;height:50%;"></div>
</body>
</html>
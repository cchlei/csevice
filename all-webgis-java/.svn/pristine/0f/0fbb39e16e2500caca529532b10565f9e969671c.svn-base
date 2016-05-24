<!DOCTYPE html><html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>
<title>用户管理列表</title>
<link rel="stylesheet" type="text/css" href="${ctx }/assets/libs/jquery-easyui-1.4.5/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx }/assets/libs/jquery-easyui-1.4.5/themes/icon.css">
<script type="text/javascript" src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx }/assets/libs/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
function doSearch() {
	$('#dg').datagrid('load',{  
        searchVal: $("#searchVal").searchbox("getValue")+":"+$("#searchVal").searchbox("getName"),
        entity : $('input[name="entity"]:checked ').val()
    }); 
}
//冻结
function locked(){
	var row=$('#dg').datagrid("getSelected"); 
	if (row){
		if(row.status=='CLOSED'){
			$.messager.show({title: '提示', msg: "该用户已被封停，不能再被冻结！" });
		}else{
			$.messager.confirm('提示','确定要冻结所选用户?',function(r){
				if (r){
					$.post(ctx+"/user/locked", {id:row.id}, function(res){
						if (res.status==0){
							$('#dg').datagrid('reload');	// reload the user data
							$.messager.show({title: '提示', msg: "操作成功" });
						} else {
							$.messager.show({title: '提示', msg: res.msg });
						}
					});
				}
			});
		}
	}else{
		$.messager.show({title: '提示', msg: "请选择要冻结的用户！" });
	}
}
//解冻
function unlock(uid){
	$.messager.confirm('提示','确定要解冻所选用户?',function(r){
		if (r){
			$.post(ctx+"/user/unlock/"+uid, null, function(res){
				if (res.status==0){
					$('#dg').datagrid('reload');	// reload the user data
					$.messager.show({title: '提示', msg: "操作成功" });
				} else {
					$.messager.show({title: '提示', msg: res.msg });
				}
			});
		}
	});
}
//封停
function closed(){
	var row=$('#dg').datagrid("getSelected"); 
	if (row){
		if(row.status=='CLOSED'){
			$.messager.show({title: '提示', msg: "该用户已被封停！" });
		}else{
			$.messager.confirm('提示','确定要封停所选用户?',function(r){
				if (r){
					$.post(ctx+"/user/closed", {id:row.id}, function(res){
						if (res.status==0){
							$('#dg').datagrid('reload');	// reload the user data
							$.messager.show({title: '提示', msg: "操作成功" });
						} else {
							$.messager.show({title: '提示', msg: res.msg });
						}
					});
				}
			});
		}
	}else{
		$.messager.show({title: '提示', msg: "请选择要封停的用户！" });
	}
}
//单选按钮触发查询
function radioCheck(){
	doSearch();
}
</script>
</head>
<body>
<style type="text/css">
html,body{height:98.6%}
a.viewInfo {
   color: #fff;
   background-color: #428BCA;
   padding: 2px 9px;
   text-decoration: none;
   display: inline-block;
   border-radius: 6px;
}
</style>
<form id="fm" method="post" style="height: 100%">
    <div style="width: 100%;height: 100%;">
    <table id="dg" class="easyui-datagrid" title="用户管理" style="height: 100%;" toolbar="#toolbar"
		sortOrder="desc" pagination="true" rownumbers="true" fitColumns="true" singleSelect="true" striped="true" pageSize="20"
		url="${ctx}/user/userlist" data-options="singleSelect:true,collapsible:true,method:'post'">
		<thead>
			<tr>
<!-- 				<th field="id" width="50px">编号</th> -->
				<th field="username" width="50px">用户名</th>
				<th field="name" width="50px">姓名</th>
				<th field="mobile" width="50px">电话</th>
				<th field="email" width="50px">邮箱</th>
				<th field="createDate" width="50px">注册时间</th>
				<th field="status" width="50px">状态</th>
				<th field="entity" width="50px">状态</th>
				<th field="operation" width="50px">操作</th>
			</tr>
		</thead>
	</table>
	</div>
	<div id="toolbar" style="width: 100%;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-lock" plain="true" onclick="locked()">冻结</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-man" plain="true" onclick="closed()">封停</a>
		<input id="searchVal" class="easyui-searchbox" data-options="prompt:'请输入查询条件',menu:'#mm',searcher:function(value,name){doSearch()}"style="width: 300px;vertical-align: bottom;"></input>
		<div id="mm" style="width: 120px">
			<div data-options="name:'all'">全部</div>
			<div data-options="name:'username'">用户名</div>
			<div data-options="name:'name'">姓名</div>
			<div data-options="name:'mobile'">电话</div>
			<div data-options="name:'email'">邮箱</div>
			<div data-options="name:'status'">状态</div>
		</div>
		<input type="hidden" id="searchVal"> 
		<input name="entity" type="radio" value=0 onchange="radioCheck();" checked="checked"/>个人版</label>
		<input name="entity" type="radio" value=1 onchange="radioCheck();"/>企业版</label>
	</div>
</form>
</body>
</html>
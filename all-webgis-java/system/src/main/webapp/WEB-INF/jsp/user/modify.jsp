<!DOCTYPE html><html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>
<title>重置密码</title>
<link rel="stylesheet" type="text/css" href="${ctx }/assets/libs/jquery-easyui-1.4.5/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx }/assets/libs/jquery-easyui-1.4.5/themes/icon.css">
<script type="text/javascript" src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx }/assets/libs/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
function doSearch(val) {
	$('#searchVal').val(val);
	$('#dg').datagrid('load',{  
        searchVal: $('#searchVal').val()
    }); 
}
//弹出修改密码窗口
function resetPwd(){
	var row=$('#dg').datagrid("getSelected"); 
	if (row){
		$.messager.confirm('提示','确定要重置此用户的登录密码?',function(r){
			if (r){
				$.post(ctx+"/user/resetPwd",{id:row.id},function(res){
					if (res.status==0){
						$.messager.show({title: '提示', msg: "密码重置成功，新密码为："+res.msg+",请及时登录并修改。" });
					} else {
						$.messager.show({title: '提示', msg: res.msg });
					}
				});
			}
		});
	}else{
		$.messager.show({ title: '提示', msg: "请选择要重置密码的用户！"});
	}
}
</script>
</head>
<body>
<style>
	html,body{height:98.6%}
</style>
<form id="fm" method="post" style="height: 100%">
    <div style="width: 100%;height: 100%;">
    <table id="dg" class="easyui-datagrid" title="重置资料管理" style="height: 100%;" toolbar="#toolbar"
		sortOrder="desc" pagination="true" rownumbers="true" fitColumns="true" singleSelect="true" striped="true" pageSize="20"
		url="${ctx }/user/userlist" data-options="singleSelect:true,collapsible:true,method:'post'">
		<thead>
			<tr>
<!-- 				<th field="id" width="50px">编号</th> -->
				<th field="username" width="50px">用户名</th>
				<th field="name" width="50px">姓名</th>
				<th field="mobile" width="50px">电话</th>
				<th field="email" width="50px">邮箱</th>
				<th field="status" width="50px">状态</th>
			</tr>
		</thead>
	</table>
	</div>
	<div id="toolbar" style="width: 100%;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="resetPwd()">重置密码</a>
		<input id="searchVal" class="easyui-searchbox" data-options="prompt:'请输入查询条件',menu:'#mm',searcher:function(value,name){doSearch(value+':'+name)}"style="width: 300px;vertical-align: bottom;"></input>
		<div id="mm" style="width: 120px">
			<div data-options="name:'all'">全部</div>
			<div data-options="name:'username'">用户名</div>
			<div data-options="name:'name'">姓名</div>
			<div data-options="name:'mobile'">电话</div>
			<div data-options="name:'email'">邮箱</div>
			<div data-options="name:'status'">状态</div>
		</div>
		<input type="hidden" id="searchVal"> 
	</div>
</form>
</body>
</html>
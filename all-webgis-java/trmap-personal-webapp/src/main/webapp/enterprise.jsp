<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh" content="5;url=http://ent.trmap.cn" /> 
<title>用户登录错误</title>
</head>
<script> 
//定时器 异步运行 
function hello(){ 
	window.location.href="http://ent.trmap.cn/";
} 
//使用方法名字执行方法 
var t1 = window.setTimeout(hello,5000); 
window.clearTimeout(t1);//去掉定时器 
</script> 
<body>
	您的账号是企业用户，系统将在5秒后为您自动跳转到企业中心。
</body>
</html>
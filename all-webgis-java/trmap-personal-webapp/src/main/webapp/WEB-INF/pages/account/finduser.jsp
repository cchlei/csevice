<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head lang="en">
<meta charset="UTF-8">
<title>账号找回</title>
<%@ include file="/static/global.jsp"%>
<link rel="stylesheet" href="//cdn.staticfile.org/twitter-bootstrap/3.3.1/css/bootstrap.min.css" />
<style>
body {
	font-family: "微软雅黑"
}

span.error{
	color: red;
}
</style>
</head>
<body>
	<div class="topbar">
		<div class="mc">
			<a href="#" class="logo"></a>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-xs-4"></div>
			<div class="col-xs-4">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">新用户注册</h4>
				</div>
				<div class="modal-body">
					<form id="registerForm" role="form" method="post" action="${ctx}/account/register">
					
						<div id="username-group" class="form-group">
							<label for="username">账号</label> 
							<span id="username-error" class="error"></span>
							<div class="input-group">
								<span class="input-group-addon">
									<span class="glyphicon glyphicon-user"></span>
								</span> 
								<input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名">
								<span id="username-feedback" class="glyphicon form-control-feedback" aria-hidden="true"></span>
							</div>
							<span>提示：账号长度为6-20个字符</span>
						</div>

						<div id="email-group" class="form-group">
							<label for="email">邮箱</label>
							<span id="email-error" class="error"></span>
							<div class="input-group">
								<span class="input-group-addon">
									<span class="glyphicon glyphicon-envelope"></span>
								</span> 
								<input type="email" class="form-control" id="email" name="email" placeholder="输入邮箱账号">
								<span id="email-feedback" class="glyphicon form-control-feedback" aria-hidden="true"></span>
							</div>
							<span>提示：邮箱格式为**@**</span>
						</div>

						<div id="password-group" class="form-group">
							<label for="password">密码</label>
							<span id="password-error" class="error"></span>
							<div class="input-group">
								<span class="input-group-addon">
									<span class="glyphicon glyphicon-lock"></span>
								</span>
								<input type="password" class="form-control" id="password" name="password" placeholder="输入密码">
								<span id="password-feedback" class="glyphicon form-control-feedback" aria-hidden="true"></span>
							</div>
							<span>提示：密码长度为6-32个字符</span>
						</div>
						<div id="confirmpass-group" class="form-group">
							<label for="confirmpass">确认密码</label>
							<span id="confirmpass-error" class="error"></span>
							<div class="input-group">
								<span class="input-group-addon"> 
									<span class="glyphicon glyphicon-lock"></span>
								</span>
								<input type="password" class="form-control" id="confirmpass" name="confirmpass" placeholder="重复输入密码">
								<span id="confirmpass-feedback" class="glyphicon form-control-feedback" aria-hidden="true"></span>
							</div>
							<span>提示：重复密码须和注册密码相同</span>
						</div>

						<div style="text-align: center;">
							<button type="submit" id="reg" class="btn btn-success"
								style="width: 200px;">注册</button>
						</div>
					</form>
				</div>

			</div>
			<div class="col-xs-4"></div>
		</div>
	</div>

	<script type="text/javascript" src="//cdn.staticfile.org/jquery/1.11.1/jquery.min.js"></script>
	<script type="text/javascript" src="//cdn.staticfile.org/jquery-validate/1.13.1/jquery.validate.min.js"></script>
	<script type="text/javascript" src="//cdn.staticfile.org/jquery-validate/1.13.1/additional-methods.min.js"></script>
	<script type="text/javascript" src="//cdn.staticfile.org/twitter-bootstrap/3.3.1/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/validationMethods.js"></script>
	<script type="text/javascript">
		eval(function(p,a,c,k,e,d){e=function(c){return(c<a?"":e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)d[e(c)]=k[c]||e(c);k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1;};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p;}('$(K).M(8(){$("#L").N({P:{c:{3:9,4:[6,O],f:{b:"r",s:q+"/t/x?b=c",p:{w:8(){u $("#c").v()}}}},5:{3:9,5:9,f:{b:"r",s:q+"/t/x?b=5",p:{w:8(){u $("#5").v()}}}},n:{3:9,4:[6,y]},z:{3:9,4:[6,y],B:"#n"}},10:{c:{3:"11",4:$.k.l("T{0}-{1}m"),f:"S，j。"},5:{3:"R",5:"U",f:"W，j。"},n:{3:"V",4:$.k.l("F{0}-{1}m"),},z:{3:"X",4:$.k.l("F{0}-{1}m"),B:"Z，j"}},Y:8(7,h){a 2=h[0].2;a I=7[0].Q;$("#"+2+"-A").i("d-o").g("d-7");$("#"+2+"-D").i("e-C").g("e-E");$("#"+2+"-7").G(I)},o:8(h){a H=h[0];a 2=H.J;$("#"+2+"-A").i("d-7").g("d-o");$("#"+2+"-D").i("e-E").g("e-C");$("#"+2+"-7").G("")}})});',62,64,'||id|required|rangeLength|email||error|function|true|var|type|username|has|glyphicon|remote|addClass|element|removeClass|请重新输入|validator|format|位|password|success|data|ctx|POST|url|account|return|val|value|validation|32|confirmpass|group|equalTo|ok|feedback|remove|密码长度必须在|html|ele|msg|htmlFor|document|registerForm|ready|validate|20|rules|innerText|请输入Email|账号已存在|账号长度必须在|请填写有效的Email地址|请输入密码|Email已注册|请确认密码|errorPlacement|两次输入的密码不一致|messages|请输入账号'.split('|'),0,{}))
	</script>
</body>
</html>
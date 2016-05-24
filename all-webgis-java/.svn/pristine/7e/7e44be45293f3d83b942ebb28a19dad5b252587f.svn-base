$(document).ready(function() {
			$("#registerForm").validate({
				rules:{
					username:{
						required:true,
						rangeLength:[6,20],
						remote:{
							type:"POST",
							url:ctx+"/account/validation?type=username",
							data:{
								value:function(){
									return $('#username').val();
								}
							}
						}
					},
					email:{
						required:true,
						email:true,
						remote:{
							type:"POST",
							url:ctx+"/account/validation?type=email",
							data:{
								value:function(){
									return $('#email').val();
								}
							}
						}
					},
					password:{
						required:true,
						rangeLength:[6,32]
						
					},
					confirmpass:{
						required:true,
						rangeLength:[6,32],
						equalTo:"#password"
					}
				},
				messages:{
					username:{
						required:"请输入账号",
						rangeLength:$.validator.format("账号长度必须在{0}-{1}位"),
						remote:"账号已存在，请重新输入。"
					},
					email:{
						required:"请输入Email",
						email:"请填写有效的Email地址",
						remote:"Email已注册，请重新输入。"
					},
					password:{
						required:"请输入密码",
						rangeLength:$.validator.format("密码长度必须在{0}-{1}位"),
					},
					confirmpass:{
						required:"请确认密码",
						rangeLength:$.validator.format("密码长度必须在{0}-{1}位"),
						equalTo:"两次输入的密码不一致，请重新输入"
					}
				},
				errorPlacement:function(error,element){
					var id = element[0].id;
					var msg = error[0].innerText;
					$("#"+id+"-group").removeClass("has-success").addClass("has-error");
					$("#"+id+"-feedback").removeClass("glyphicon-ok").addClass("glyphicon-remove");
					$('#'+id+"-error").html(msg);
				},
				success:function(element){
					var ele = element[0];
					var id = ele.htmlFor;
					$("#"+id+"-group").removeClass("has-error").addClass("has-success");
					$("#"+id+"-feedback").removeClass("glyphicon-remove").addClass("glyphicon-ok");
					$('#'+id+"-error").html('');
				}
			});
		});
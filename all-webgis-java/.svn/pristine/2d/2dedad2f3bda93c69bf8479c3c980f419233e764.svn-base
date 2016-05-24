<!DOCTYPE html><html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>
<title>认证企业列表</title>
<script type="text/javascript" src="http://ent.trmap.cn/assets/libs/webuploader/new/jquery-1.10.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx }/assets/libs/jquery-easyui-1.4.5/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx }/assets/libs/jquery-easyui-1.4.5/themes/icon.css">
<script type="text/javascript" src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx }/assets/libs/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/assets/libs/webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/assets/libs/webuploader/entcainfo/style.css" />
<script type="text/javascript" src="${ctx}/assets/libs/webuploader/webuploader.js"></script>
<script type="text/javascript" src="${ctx}/assets/libs/webuploader/entcainfo/getting-started2.js"></script>
<script type="text/javascript" src="${ctx}/assets/libs/webuploader/entcainfo/getting-started3.js"></script>
<script type="text/javascript" src="${ctx}/assets/libs/webuploader/entcainfo/getting-started4.js"></script>
<script type="text/javascript">
function doSearch(val) {
	$('#searchVal').val(val);
	$('#dg').datagrid('load',{  
        searchVal: $('#searchVal').val()
    }); 
}

//审核通过
function pass(id){
	$.messager.confirm('提示','确定要通过所选的企业认证信息?',function(r){
		if (r){
			$.post(ctx+"/entcainfo/audit/0/"+id, null, function(res){
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
//审核驳回
var url;
function fail(id){
	$('#dlg').dialog('open').dialog('setTitle', '驳回意见');
	$('#fr').form('clear');
	url = ctx+"/entcainfo/audit/1/"+id;
}
//驳回处理
function auditfail(){
	$('#fr').form('submit',{
		url: url,
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(data){
			var res = eval('(' + data + ')');
			if (res.status==0){
				$('#dg').datagrid('reload');
				$('#dlg').dialog('close');
				$.messager.show({title: '提示', msg: "操作成功" });
			} else {
				$.messager.show({title: '提示', msg: res.msg });
			}
		}
	});
}
//预览详情
function view(id){
	$.post(ctx+"/entcainfo/toView/"+id, null, function(res){
		var form = $('#ff').form('load',res);
		//支持标签扩展name
		$.each(res,function(key,value){
			var e = form.find("[name="+ key +"]");
			if(e.is("img")){
				e.attr("src","http://oss.trmap.cn/thumb/300_200/"+value)
			}else if(e.is("span")){
				e.text(value==null?"":value);
			}
		})
	},'json');
	$('#w').window('open');
	$('#w').window('vcenter');
}
//关联账户
function account(id){
	$.post(ctx+"/entcainfo/account/"+id, null, function(res){
		var form = $('#fu').form('load',res);
		//支持标签扩展name
		$.each(res,function(key,value){
			var e = form.find("[name="+ key +"]");
			if(e.is("img")){
				e.attr("src","http://oss.trmap.cn/thumb/300_200/"+value)
			}else if(e.is("span")){
				e.text(value==null?"":value);
			}
		})
	},'json');
	$('#wu').window('open');
	$('#wu').window('vcenter');
}
//修改弹出修改窗口并赋值
var cainfoid;
function openEdit(id){
	$('#dlgModify').dialog('open').dialog('setTitle', '修改资料');
	$('#ffModify').form('clear');
	cainfoid = id;
	$.post(ctx+"/entcainfo/toView/"+id, null, function(res){
		var form = $('#ffModify').form('load',res);
		//支持标签扩展name
		$.each(res,function(key,value){
			var e = form.find("[name="+ key +"]");
			if(e.is("img")){
				e.attr("src","http://oss.trmap.cn/thumb/300_200/"+value)
			}else if(e.is("span")){
				e.text(value==null?"":value);
			}
		})
		
		uploader2.inject();
	},'json');
	
}
//修改提交
function editInfo(){
	$.post(ctx+"/entcainfo/validation/"+cainfoid, {value: $('#enterpriseName').val()}, function(res){
		if (res.status!=0){
			return;
		}
	});
	$('#ffModify').form('submit',{
		url: ctx+"/entcainfo/modify/"+cainfoid,
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(data){
			var res = eval('(' + data + ')');
			if (res.status==0){
				$('#dg').datagrid('reload');
				$('#dlgModify').dialog('close');
				$.messager.show({title: '提示', msg: "操作成功" });
			} else {
				$.messager.show({title: '提示', msg: res.msg });
			}
		}
	});
}

//扩展easyui表单的验证
$.extend($.fn.validatebox.defaults.rules, {
  //手机号码验证
  mobile: {//value值为文本框中的值
    validator: function (value) {
      var reg = /^1[3|4|5|8|9]\d{9}$/;
      return reg.test(value);
    },
    message: '请输入正确的手机号码'
  },
  //数字
  number: {
    validator: function (value) {
      var reg =/^[0-9]*$/;
      return reg.test(value);
    },
    message: '请输入数字'
  },
  //身份证
  idcard: {
    validator: function (value) {
      var reg =/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
      return reg.test(value);
    },
    message: '请输入正确的18位身份证号'
  }
})
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
#ff span{
	font-weight: bold;
}
</style>
<form id="fm" method="post" style="height: 100%">
    <div style="width: 100%;height: 100%;">
    <table id="dg" class="easyui-datagrid" title="企业认证管理" style="height: 100%;" toolbar="#toolbar"
		sortOrder="desc" pagination="true" rownumbers="true" fitColumns="true" singleSelect="true" striped="true" pageSize="20"
		url="${ctx}/entcainfo/list" data-options="singleSelect:true,method:'post'">
		<thead>
			<tr>
<!-- 				<th field="id" width="50px">编号</th> -->
				<th field="entname" width="50px">企业名称</th>
				<th field="address" width="50px">地址</th>
				<th field="bossName" width="50px">法人</th>
				<th field="managerPhone" width="50px">联系电话</th>
				<th field="castatusStr" width="50px">状态</th>
				<th field="account" width="50px">关联账号</th>
				<th field="operation" width="50px">操作</th>
			</tr>
		</thead>
	</table>
	</div>
	<div id="toolbar" style="width: 100%;">
		<input id="searchVal" class="easyui-searchbox" data-options="prompt:'请输入查询条件',menu:'#mm',searcher:function(value,name){doSearch(value+':'+name)}"style="width: 300px;vertical-align: bottom;"></input>
		<div id="mm" style="width: 120px">
			<div data-options="name:'all'">全部</div>
			<div data-options="name:'enterpriseName'">企业名称</div>
			<div data-options="name:'address'">地址</div>
			<div data-options="name:'bossName'">法人</div>
			<div data-options="name:'managerPhone'">联系电话</div>
		</div>
		<input type="hidden" id="searchVal"> 
	</div>
</form>

<!-- 详情窗口 -->
<div id="w" class="easyui-window" title="企业认证信息" style="width:90%;height:600px;padding:10px;"
	data-options="closed:'true', minimizable:false, iconCls:'icon-save', modal:true, inline:true,
    onResize:function(){ $(window).resize(); $(this).window('hcenter'); }">
	<form id="ff" method="post" style="width: 100%;height: 100%">
		<div class="easyui-panel" title="" style="position: relative;" data-options="fit:true">
			<table cellpadding="5" border="0" style="width: 100%;height: 100%">
				<tr>
					<td>企业名称:</td>
					<td><span name="enterpriseName"></span></td>
					<td>地址:</td>
					<td><span name="address"></span></td>
					<td>管理员联系电话:</td>
					<td><span name="managerPhone"></span></td>
				</tr>
				<tr>
					<td>认证状态:</td>
					<td><span name="castatusStr"></span></td>
					<td>法人姓名:</td>
					<td><span name="bossName"></span></td>
					<td>管理员姓名:</td>
					<td><span name="managerName"></span></td>
				</tr>
				<tr>
					<td>有效截止日期:</td>
					<td><span name="utilValidDate"></span></td>
					<td>法人身份证有效期:</td>
					<td><span name="bossIdentifyDate"></span></td>
					<td>管理员身份证身份证有效期:</td>
					<td><span name="managerIdentifyDate"></span></td>
				</tr>
				<tr>
					<td>营业执照:</td>
					<td><span name="enterpriseId"></span></td>
					<td>法人身份证:</td>
					<td><span name="bossIdentifyId"></span></td>
					<td>管理员身份证:</td>
					<td><span name="managerIdentifyId"></span></td>
				</tr>
				<tr>
					<td colspan="2"><img name="enterpriseAttachUrl"/></td>
					<td colspan="2"><img name="bossIdentifyUrl"/></td>
					<td colspan="2"><img name="managerIdentifyUrl"/></td>
				</tr>
				<tr>
					<td>简 介:</td>
					<td colspan="5"><span name="comment"></span></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<!-- 关联账户窗口 -->
<div id="wu" class="easyui-window" title="关联账户" style="width:50%;height:400px;padding:10px;"
	data-options="closed:'true', minimizable:false, iconCls:'icon-save', modal:true, inline:true,
    onResize:function(){ $(window).resize(); $(this).window('hcenter'); }">
	<form id="fu" method="post" style="width: 100%;height: 100%">
		<div class="easyui-panel" title="" style="position: relative;" data-options="fit:true">
			<table cellpadding="5" border="0" style="width: 100%;height: 100%">
				<tr>
					<td>用户名:</td>
					<td><h5><span name="username"></span></h5></td>
					<td>注册日期:</td>
					<td><span name="createDate"></span></td>
					<td>姓名:</td>
					<td><span name="name"></span></td>
				</tr>
				<tr>
					<td>昵称:</td>
					<td><span name="nickname"></span></td>
					<td>性别:</td>
					<td><span name="gender"></span></td>
					<td>电话:</td>
					<td><span name="mobile"></span></td>
				</tr>
				<tr>
					<td>邮箱:</td>
					<td><span name="email"></span></td>
					<td>头像:</td>
					<td colspan="3"><img name="headimg"/></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<!-- 驳回意见窗口 -->
<div id="dlg" class="easyui-dialog" style="width: 400px; height: 200px; padding: 10px 20px" closed="true"
buttons="#dlg-buttons" align="center">
	<form id="fr" method="post">
			<table border="0" cellpadding="0" cellspacing="5">
				<tr>
					<td>
					<input id="remarks" name = "remarks" class="easyui-textbox" data-options="multiline:true,
					prompt:'请填写驳回意见...',required:true,validType:['length[1,255]']" style="width:300px;height:100px">
					</td>
				</tr>
			</table>
		<div id="btn" style="text-align: center;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="auditfail()">提交</a>
		</div>
	</form>
</div>

<!-- 修改资料隐藏窗口 -->
<div id="dlgModify" class="easyui-dialog" style="width:90%;height:600px;padding:10px;" closed="true" align="center"
data-options="onClose:function(){uploader2.uninject();},iconCls:'icon-save',resizable:true,modal:true,buttons: [{text:'提交', iconCls:'icon-ok', handler:editInfo},
{text:'取消', handler:function(){ $('#dlgModify').dialog('close');}}]">
	<form id="ffModify" method="post" style="width: 100%;height: 100%">
		<div class="easyui-panel" title="" style="position: relative;" data-options="fit:true">
			<table cellpadding="5" border="0" style="width: 100%;height: 100%">
				<tr>
					<td>企业名称:</td>
					<td>
						<input id="enterpriseName" name="enterpriseName" required="true" class="easyui-validatebox textbox"
						data-options="required:true,validType:['length[2,25]']"
						onkeyup="value=value.replace(/\s|input|script/g,'')" onpaste="value=value.replace(/\s|input|script/g,'')"
						oncontextmenu = "value=value.replace(/\s|input|script/g,'')"/>
					</td>
					<td>地址:</td>
					<td>
						<input name="address" required="true" class="easyui-validatebox textbox" maxlength="255"/>
					</td>
					<td>管理员联系电话:</td>
					<td>
						<input name="managerPhone" class="easyui-validatebox textbox" 
						data-options="required:true,validType:'mobile'" />
					</td>
				<tr>
					<td>认证状态:</td>
					<td><span name="castatusStr"></span></td>
					<td>法人姓名:</td>
					<td>
						<input name="bossName" required="true" class="easyui-validatebox textbox"
						data-options="required:true,validType:['length[2,10]']"
						onkeyup="value=value.replace(/\s|input|script/g,'')" onpaste="value=value.replace(/\s|input|script/g,'')"
						oncontextmenu = "value=value.replace(/\s|input|script/g,'')"/>
					</td>
					<td>管理员姓名:</td>
					<td>
						<input name="managerName" required="true" class="easyui-validatebox textbox" 
						data-options="required:true,validType:['length[2,10]']"
						onkeyup="value=value.replace(/\s|input|script/g,'')" onpaste="value=value.replace(/\s|input|script/g,'')"
						oncontextmenu = "value=value.replace(/\s|input|script/g,'')"/>
					</td>
				</tr>
				<tr>
					<td>有效截止日期:</td>
					<td><span name="utilValidDate"></span></td>
					<td>法人身份证有效期:</td>
					<td>
						<input class="easyui-datebox" type="text" name="bossIdentifyDate" data-options="required:true"></input>
					</td>
					<td>管理员身份证有效期:</td>
					<td>
						<input class="easyui-datebox" type="text" name="managerIdentifyDate" data-options="required:true"></input>
					</td>
				</tr>
				<tr>
					<td>营业执照:</td>
					<td>
						<input name="enterpriseId" class="easyui-validatebox textbox" 
						data-options="required:true,validType:['number','length[15,15]']" />
					</td>
					<td>法人身份证:</td>
					<td>
						<input name="bossIdentifyId" class="easyui-validatebox textbox"
						data-options="required:true,validType:['idcard','length[18,18]']" />
					</td>
					<td>管理员身份证:</td>
					<td>
						<input name="managerIdentifyId" class="easyui-validatebox textbox"
						data-options="required:true,validType:['idcard','length[18,18]']" />
					</td>
				</tr>
				<tr>
					<td colspan="2"><img name="enterpriseAttachUrl"/>
						<input type="hidden" id="enterpriseAttachUrl" name="enterpriseAttachUrl"/>
					</td>
					<td colspan="2"><img name="bossIdentifyUrl"/>
						<input type="hidden" id="bossIdentifyUrl" name="bossIdentifyUrl"/>
					</td>
					<td colspan="2"><img name="managerIdentifyUrl"/>
						<input type="hidden" id="managerIdentifyUrl" name="managerIdentifyUrl"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" id="uploader-demo_cont2"></td>
					<td colspan="2" id="uploader-demo_cont3"></td>
					<td colspan="2" id="uploader-demo_cont4"></td>
				</tr>
				<tr>
					<td>简 介:</td>
					<td colspan="5">
						<input name="comment" class="easyui-validatebox textbox" maxlength="255" 
						style="width:550px;height:100px"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>


<div id="uploader-demo_cont_no2" style="width:0;height:0;overflow:hidden">
	<div id="uploader-demo2">
	    <div id="filePicker2">更换营业执照</div>
	    <div id="fileList2" class="uploader-list"></div>
	</div>
</div>
<div id="uploader-demo_cont_no3" style="width:0;height:0;overflow:hidden">
	<div id="uploader-demo3">
	    <div id="filePicker3">更换法人身份证</div>
	    <div id="fileList3" class="uploader-list"></div>
	</div>
</div>
<div id="uploader-demo_cont_no4" style="width:0;height:0;overflow:hidden">
	<div id="uploader-demo4">
	    <div id="filePicker4">更换管理员身份证</div>
	    <div id="fileList4" class="uploader-list"></div>
	</div>
</div>

</body>
</html>
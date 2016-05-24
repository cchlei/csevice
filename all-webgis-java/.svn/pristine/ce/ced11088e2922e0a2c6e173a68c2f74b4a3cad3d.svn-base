<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<title>用户管理</title>

<link rel="stylesheet" href="${ctx}/assets/libs/ace/1.3.2/css/datepicker.min.css" />

<!-- content -->
<div class="row">
	<!-- search star -->
		<form class="form-search form-inline">

		<select id="userStatus" class="input-sm form-control">
			<option value="">状态</option>
			<option value="0">未激活</option>
			<option value="1">已激活</option>
			<option value="2">已锁定</option>
			<option value="3">已冻结</option>
		</select>
	
		<div class="input-group pull-right col-sm-8">
			<div class="row">
				<div class="col-sm-4">
					<div class="btn-group pull-right">
						<button class="btn btn-primary btn-white btn-sm dropdown-toggle"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span id="filterName">全部&nbsp;</span><i
								class="ace-icon fa fa-angle-down icon-on-right"></i>
						</button>
						<ul id="filterGroup" class="dropdown-menu">
							<li><a href="#">全部</a></li>
							<li role="separator" class="divider"></li>
							<li><a href="#">姓名</a></li>
							<li><a href="#">账号</a></li>
							<li><a href="#">邮箱</a></li>
						</ul>
					</div>
				</div>
				
				<div class="col-sm-8">
					<input type="text" class="form-control search-query"
						placeholder="Type your query"> <span class="input-group-btn">
						<button type="button" class="btn btn-purple btn-sm">
							<span class="ace-icon fa fa-search icon-on-right"></span> 查询
						</button>
					</span>
				</div>
			</div>
		</div>
	</form>

</div>

<div class="row">
	<!-- search end -->
	<table id="userList"
		class="table table-striped table-bordered table-hover" width="100%">
		<thead>
			<tr>
				<th>头像</th>
				<th>姓名</th>
				<th>账号</th>
				<th>邮箱</th>
				<th>注册时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
<script type="text/javascript" src="${ctx}/assets/libs/ace/1.3.2/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/libs/ace/1.3.2/js/jquery.dataTables.bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/libs/ace/1.3.2/js/date-time/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/libs/ace/1.3.2/js/date-time/locals/bootstrap-datepicker.zh-CN.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/userlist.js"></script>
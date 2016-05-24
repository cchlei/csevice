<%@ include file="/static/global.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<link href="${ctx}/static/css/datatables/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/datatables/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/datatables/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/css3/animate.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/fonts.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/process/stylesheets/ui.css"  rel="stylesheet" >
<link href="${ctx}/static/css/process/stylesheets/ui.progress-bar.css" rel="stylesheet">
<link media="only screen and (max-device-width: 480px)" href="${ctx}/static/css/process/stylesheets/ios.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/libs/cseajs/csea$.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.min.js"></script>
<script src="http://libs.useso.com/js/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/datatables/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/static/js/datatables/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.bootstrap-pureClearButton.js"></script>
<script type="text/javascript" src="${ctx}/static/js/attachcount.js"></script>
<title>用户存储统计</title>
</head>
<body class="p0">
	<div class="container-fluid">
		<div class="row-fluid">
			<!-- content -->
			<div class="col-xs-12">
				<div class="wrapper wrapper-content animated fadeInUp">
					<div class="ibox">
						<!-- ibox-title Start -->
						<div class="ibox-title">
							<h5>
							<i class="im-user">空间使用情况:</i>
								<div class="row">
									<div class="col-xs-10">
									 <!-- Progress bar -->
								    <div id="progress_bar" class="ui-progress-bar ui-container">
								      <div class="ui-progress" style="width: 50%;">
								        <span class="ui-label" style="display:none;"><b class="value">50%</b></span>
								      </div>
								    </div>
								    </div>
								     <div class="col-xs-2" style="text-align: left; margin-top: 9px">
								    	<lable id="usedCount"></lable>M/<lable id="totalCount"></lable>M
								    </div>
								    </div>
							</h5>
						</div>
						
						<!-- ibox-title end -->

						<!-- ibox-content Start -->
						<div class="ibox-content">
							<div class="content">
							<div class="row">
							<!-- search start -->
							<div class="col-xs-8">
							</div>
							<div class="col-xs-4" style="text-align: right;">
							
							<div class="form-horizontal clearfix">
								<div class="col-sm-10">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group">
												<!-- /btn-group -->
												<input id="searchText" type="email" placeholder="请输入文件名"
													class="input-sm form-control" style="width: 263px;">
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-2">
									<span class="input-group-btn">
										<button id="search" type="button"
											class="btn btn-sm btn-primary">搜索</button>
									</span>
								</div>
							</div>
							</div>
							</div>
							</div>
							
							<!-- end of form-horizontal -->
							<!-- search end -->
							<table id="attachList"
								class="table table-striped table-bordered table-hover dataTables-example dataTable"
								cellspacing="0" width="100%" style="text-align:center;">
								<thead>
									<tr>
										<th style="text-align:center; width:300px;">文件名</th>
										<th style="text-align:center;">文件类型</th>
										<th style="text-align:center;">文件大小</th>
										<th style="text-align:center; width:150px;">上传时间</th>
										<th style="text-align:center;">查看地图</th>
										<th style="text-align:center;">操作</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<!-- ibox-content end -->
					</div>
					<!-- end of ibox -->
				</div>
				<!-- end of wrapper -->
			</div>
			<!-- end of col-xs-10 -->

			<!-- Modals -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				style="margin-top: 200px;">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title" id="myModalLabel">附件管理</h4>
						</div>
						<div class="modal-body">
						确定删除附件？
						</div>
						<div class="modal-footer"
						>
							<button type="button" class="btn btn-info" id="initData">确定</button>
							<button type="button" class="btn btn-primary"
								data-dismiss="modal">取消</button>
						</div>
					</div>
				</div>
			</div>
</body>
</html>
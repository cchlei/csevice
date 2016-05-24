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
<script type="text/javascript" src="${ctx}/static/js/graphicscount.js"></script>

<title>数据使用统计</title>
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
								<i class="im-user">数据使用情况:</i>
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
								    	<lable id="usedCount"></lable>条/<lable id="totalCount"></lable>条
								    </div>
								    </div>
							</h5>
						</div>
						
						<!-- ibox-title end -->

						<!-- ibox-content Start -->
						<div class="ibox-content">
							<div class="content">
							<div class="row" style="text-align:center;" >
							<!-- search start -->
							<div class="col-xs-1">
								<label class="checkbox" for="point">
								<input type="checkbox" id="point"> 
								<img alt="" src="${ctx}/static/images/icon/point.png" style="margin-top: -5px;margin-left: 3px;">
								</label>
							</div>
							<div class="col-xs-1">
							<label class="checkbox" for="line">
							<input type="checkbox" id="line"> 
								<img alt="" src="${ctx}/static/images/icon/line.png" style="margin-top: -5px;margin-left: 3px;"></label>
							</div>
							<div class="col-xs-1">
							<label class="checkbox" for="surface">
							<input type="checkbox" id="surface" >
								<img alt="" src="${ctx}/static/images/icon/surface.png" style="margin-top: -5px;margin-left: 3px;">
									 </label>
							</div>
							<div class="col-xs-5">
							
							</div>
							<div class="col-xs-4" style="text-align: right;">
							
							<div class="form-horizontal clearfix">
								<div class="col-sm-10">
									<div class="form-group">
										<div class="input-group">
											<div class="input-group">
												<!-- /btn-group -->
												<input id="searchText" type="email" placeholder="请输入数据名称"
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
							<table id="graphicsList"
								class="table table-striped table-bordered table-hover dataTables-example dataTable"
								cellspacing="0" width="100%" style="text-align:center;">
								<thead>
									<tr>
										<th style="text-align:center; width:300px;">数据名称</th>
										<th style="text-align:center;">数据类型</th>
										<th style="text-align:center; width:150px;">创建时间</th>
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

</body>
</html>
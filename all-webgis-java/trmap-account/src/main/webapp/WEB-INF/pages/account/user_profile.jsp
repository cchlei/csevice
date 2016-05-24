<!DOCTYPE html><html >
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/global.jsp"%>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>资料修改</title>
    <link rel="stylesheet" href="${ctx}/assets/css/common.css"/>
    <script src="http://libs.useso.com/js/jquery/1.9.1/jquery.min.js"></script>
    <link href="${ctx}/assets/libs/bs/less/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/assets/css/user_profile.css"/>

    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" main="${ctx}/assets/js/user_profile"></script>

    <script>
        var headimg = "${user.headimg}"
    </script>
    <script>
        var page_cfg = {
                edit_email:"${ctx}/p/sendmail",
                edit_phone:"${ctx}/p/update",
                edit_pwd:"${ctx}/p/changepass",
                get_other_prop:"",
                edit_other_prop:"${ctx}/p/update",
                edit_thumb:"${ctx}/entfile/upload",
                edit_url:"${ctx}/p/update",
                yz_email:"${ctx}/yzemail",
                imglist:"http://os.trmap.cn/imagestorage/imglist/0"
            };
    </script>

</head>
<body>

<div class="page mc">
    <div class="container-fluid" style="padding-top: 24px;">
        <div class="row" ms-controller="headimg">

            <div class="col-xs-3 text-center">
               <span href="#" class="thumbnail header-img">
                    <%-- <img id="headimg" src="${ctx}/assets/images/hd.png"> --%>
                    <img id="headimg" ms-attr-src="{{img}}">
                </span>
                <div class="help-block header_img_label changeimg"> <span class="lb">点击头像上传</span></div>

                <div class="headimg">
                    <ul>
                        <li ms-repeat="list"><img ms-attr-src="ipth+el" ms-click="clickimg(el)"></li>
                    </ul>
                </div>

                <input type="hidden" id="userid" value="${user.id}" ms-duplex="img" name="img">
            </div>

            <form class="col-xs-7 col-xs-offset-0 form-horizontal panel panel-default form">
                <div class="page-header">
                    <span class="glyphicon glyphicon-th-list text-success" aria-hidden="true"></span>
                </div>
                <div class="panel-body">
                    <div class="form-group ep_email" ms-controller="ep_email">
                        <label class="col-xs-2 control-label">邮箱</label>
                        <div class="col-xs-6">
                            <div class="input-group">
                                <input type="text" class="form-control input-lg" ms-duplex="v" readonly="readonly" placeholder="${user.email}" aria-describedby="sizing-addon1">
                                <a class="input-group-addon" ms-click="pop()">修改</a>
                            </div>

                            <div class="pop-el container-fluid">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <br>
                                        <div class="input-group">
                                            <span class="input-group-addon">新邮箱</span>
                                            <input type="text" class="form-control" ms-keydown="anjian" ms-duplex="v_edit" >
                                        </div>
                                        <br>
                                    </div>
                                    <div class="col-xs-12 text-right">
                                        <a class="btn btn-primary" ms-click="pop_close()">确 认 </a>
                                        <br>&nbsp;
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="form-group ep_pwd" ms-controller="ep_pwd">

                        <label class="col-xs-2 control-label">密码</label>
                        <div class="col-xs-5">
                            <div class="input-group">
                                <input type="text" class="form-control input-lg" readonly="readonly" placeholder="********" aria-describedby="sizing-addon1">
                                <a class="input-group-addon" ms-click="pop()">修改</a>
                            </div>
                            <div class="pop-el container-fluid">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <br>
                                        <div class="input-group">
                                            <span class="input-group-addon">　　原密码</span>
                                            <input type="password" class="form-control pp" ms-duplex="pp">
                                        </div>
                                        <br>
                                        <div class="input-group">
                                            <span class="input-group-addon">　　新密码</span>
                                            <input type="password" class="form-control p1" ms-duplex="p1">
                                        </div>
                                        <br>
                                        <div class="input-group">
                                            <span class="input-group-addon">重复新密码</span>
                                            <input type="password" class="form-control p2" ms-duplex="p2" ms-click="anjian()">
                                        </div>
                                        <br>
                                    </div>
                                    <div class="col-xs-12 text-right">
                                        <a class="btn btn-primary" ms-click="pop_close()"> 确 认</a>
                                        <br>&nbsp;
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </form>
        </div>

        <div class="row">
            <form class="col-xs-7 col-xs-offset-3 form-horizontal panel panel-default form form_2 other_form" ms-controller="other_form">
                <div class="page-header">
                    <span class="glyphicon glyphicon-home text-success" aria-hidden="true"></span>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="col-xs-2 control-label">用户名</label>
                        <div class="col-xs-5">
                            <input type="text" readonly="readonly" name="user_name" value="${user.username}" class="form-control input-lg" >   
                        </div>
                        
                        <label class="col-xs-2 control-label">　性     别</label>
                        
                         <div class="col-xs-2" > 
                            <select class="user_sex form-control input-lg" ms-duplex="d.user_sex" name="user_sex" valu="${user.gender}">
	                            <option value = "男" >男</option>
	                            <option value = "女" >女</option>
	                            
                            </select> 
                        </div> 
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">昵    称</label>
                        <div class="col-xs-9">
                            <input type="text" ms-duplex="d.ep_name" value="${user.name}" name="ep_name" class="form-control input-lg" placeholder="昵称" aria-describedby="sizing-addon1"/>
                        </div>
                    </div>

                    <a class="btn btn-lg btn-primary col-xs-3 col-xs-offset-2" type="submit" ms-click="submit()">　保　存　</a>
                </div>
            </form>
        </div>


    </div>
</div>

<script src="${ctx}/assets/libs/bs/js/bootstrap.min.js"></script>
</body>
</html>
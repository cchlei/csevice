<!doctype html><html>
<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <%@ include file="/assets/global.jsp"%>
    <title>企业认证页面</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/enterprise_auth.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/libs/webuploader/new/webuploader.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/libs/webuploader/new/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/libs/webuploader/new/style.css">
	<style>
		.suc{
			color:#08c;
		}
		.auth_info_table .bbody .input select{
			width:24em;line-height:1em;height:3.5em;
		}
		.webuploader-pick{
			top:20px;
		}
	</style>
	<script type="text/javascript" src="${ctx}/assets/libs/webuploader/new/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/libs/webuploader/new/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/libs/webuploader/new/webuploader.js"></script>
	<script type="text/javascript" src="${ctx}/assets/libs/webuploader/new/getting-started.js"></script>
<%-- 	<script type="text/javascript" src="${ctx}/assets/libs/webuploader/new/getting-started2.js"></script> --%>
	<script type="text/javascript" src="${ctx}/assets/libs/webuploader/new/getting-started3.js"></script>
	<script type="text/javascript" src="${ctx}/assets/libs/webuploader/new/getting-started4.js"></script>
	<script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" main="__/js/enterprise_auth"></script>
    <script>
        var uploader_conf = [
            //营业执照
            {server:"http://2betop.net/fileupload.php"},

            //组织机构
//             {server:"http://2betop.net/fileupload.php"},

            //法人身份证
            {server:"http://2betop.net/fileupload.php"},

            //负责人身份证
            {server:"http://2betop.net/fileupload.php"}
        ];
    </script>
    <script type="text/javascript" src="${ctx}/assets/libs/laydate/laydate.js"></script>
</head>
<body>
    <!-- <div class="topbar"></div> -->
    <h2 class="page_tit_1">企业用户认证信息修改</h2>


    <form action="${ctx}/entcainfo/doupdate" class="auth_info_table" method="post">
        <div class="box">
            <em class="seqno">1</em>
            <div class="bbody">
                <h3>企业信息：</h3>

                <p>
                    <em>企业名称:</em>
                    <span class="input"><input ajax_valid_errmsg="您的企业名称已经被注册过了" type="text" name="enterpriseName" value="${cainfo.enterpriseName}"/></span>
                    <span class="info"></span>
                </p>
                <p>
                    <em>企业地址:</em>
                    <span class="input"><input type="text" name="address" value="${cainfo.address}"/></span>
                    <span class="info"></span>
                </p>
                <p>
                    <em>企业简介:</em>
                    <span class="input"><input type="text" name="comment" value="${cainfo.comment}"/></span>
                    <span class="info"></span>
                </p>
                <p>
                    <em>营业执照注册号:</em>
                    <span class="input"><input type="text" name="enterpriseId" value="${cainfo.enterpriseId}"/></span>
                    <span class="info">
                        <a id="filePicker">上传</a>
                    </span>
                </p>
					<div style="margin-left: 60px;" id="fileList" class="uploader-list"></div>

<!--                 <p> -->
<!--                     <em>组织机构代码:</em> -->
<%--                     <span class="input"><input type="text" name="orgId" value="${cainfo.orgId}"/></span> --%>
<!--                     <span class="info"> -->
<!--                         <a id="filePicker2">上传</a> -->
<!--                     </span> -->
<!--                 </p> -->
<!--                 <div style="margin-left: 60px;" id="fileList2" class="uploader-list"></div> -->
            </div>
        </div>
        <div class="box">
            <em class="seqno" style="background-color: #8acc72;">2</em>

            <div class="bbody">
                <h3>法人代表信息</h3>
                <p>
                    <em>法人代表姓名:</em>
                    <span class="input"><input type="text" name="bossName" value="${cainfo.bossName}"/></span>
                    <span class="info"></span>
                </p>

                <p>
                    <em>法人代表身份证号码:</em>
                    <span class="input"><input type="text" name="bossIdentifyId" value="${cainfo.bossIdentifyId}"/></span>
                    <span class="info">
                        <a id="filePicker3">上传</a>
                    </span>
                </p>
                <div style="margin-left: 60px;" id="fileList3" class="uploader-list"></div>

                <p>
                    <em>证件有效期:</em>
                    <span class="input">
                    	<input type="text" name="bossIdentifyDate"  value="${cainfo.bossIdentifyDate}" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"/>
                    	<!-- <span class="laydate-icon" onclick="laydate({elem: '#hello1'});"></span> -->
                    </span>
                    <span class="info"></span>
                </p>
            </div>
        </div>

        <div class="box">
            <em class="seqno" style="background-color: #fd8921;">3</em>

            <div class="bbody">
                <div class="bbody">
                    <h3>运维负责人信息</h3>
                    <p>
                        <em>负责人姓名:</em>
                        <span class="input"><input type="text" name="managerName" value="${cainfo.managerName}"/></span>
                        <span class="info"></span>
                    </p>

                    <p>
                        <em>负责人身份证号:</em>
                        <span class="input"><input type="text" name="managerIdentifyId" value="${cainfo.managerIdentifyId}"/></span>
                        <span class="info">
                            <a id="filePicker4">上传</a>
                        </span>
                    </p>
					<div style="margin-left: 60px;" id="fileList4" class="uploader-list"></div>

                    <p>
                        <em>证件有效期:</em>
                        <span class="input"><input type="text" name="managerIdentifyDate"  value="${cainfo.managerIdentifyDate}" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"/>
                        	<!-- <span class="laydate-icon" onclick="laydate({elem: '#hello1'});"></span> -->
                        </span>
                        <span class="info"></span>
                    </p>

                    <p>
                        <em>联系电话:</em>
                        <span class="input"><input type="text" name="managerPhone" value="${cainfo.managerPhone}"/></span>
                        <span class="info"></span>
                    </p>
                </div>
            </div>
        </div>

        <div class="box">
            <em class="seqno" style="background-color: #9a54fa;">4</em>

            <div class="bbody">
                <h3>申请期限</h3>
                <p>
                    <em>申请期限:</em>
                    <span class="input">
                        <select class="year" name="year">
                            <option value="1">1年</option>
                            <option value="2">2年</option>
                            <option value="3">3年</option>
                        </select>
                    </span>
                    <span class="info"></span>
                </p>
            </div>
        </div>


        <div class="box boxfooter">
            <em class="seqno" style="background-color: #77c75a;">√</em>
            <div class="bbody" style="padding-left: 0; background-color: transparent;">
                <br>
                <br>
                <input type="checkbox" id="accept" checked="checked">
                <label for="accept">我已阅读并接受</label>
                <a href="${ctx}/assets/pages/service_protocols.html" target="_blank">《天润云地图服务协议》</a>
                <br><br>
                <span class="submit" onclick="">提交</span>
            </div>
        </div>
        <div>
	        <input id="u1" type="hidden" name="enterpriseAttachUrl" value="${cainfo.enterpriseAttachUrl}">
	        <input id="u2" type="hidden" name="orgAttachUrl" value="${cainfo.orgAttachUrl}">
	        <input id="u3"  type="hidden" name="bossIdentifyUrl" value="${cainfo.bossIdentifyUrl}">
	        <input id="u4"  type="hidden" name="managerIdentifyUrl" value="${cainfo.managerIdentifyUrl}">
	        <input  type="hidden" value="${cainfo.id}" name="id"  />
    	</div>
    </form>

    <div class="footer">
        <p>陕ICP备12000810-2号　版权所有：陕西天润科技股份有限公司</p>
    </div>

</body>
</html>
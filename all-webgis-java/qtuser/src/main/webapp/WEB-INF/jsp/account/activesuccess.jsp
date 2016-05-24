<%@page import="com.trgis.qtmap.qtuser.common.ApplicationPropertiesHolder"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/static/global.jsp"%>
<!doctype html><html>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <link rel="stylesheet" href="http://www.trmap.cn/css/qtmap.css"/>
    <link rel="stylesheet" href="http://www.trmap.cn/css/notcie_page_comm.css"/>
    <script src="http://www.trmap.cn/js/cseajs/csea$.js" id="seajsnode" main="http://www.trmap.cn/js/notice_page_comm"></script>
    <title></title>
</head>
<body>
    <div class="navbar">
        <div class="mc">
            <a href="#" class="logo"></a>
            <span class="webname">天润云地图</span>
        </div>
    </div>
    <div id="page_cont">
        <div class="midcont mc">
            <table style="width: 100%; margin: auto;">
                <tr>

                    <td style="width: 56%; position: relative;">
                        <img style="position: absolute; left: 100px; top: -10px;" src="http://www.trmap.cn/images/illu_102.png" alt=""  >
                    </td>
                    <td style="line-height: 3em; text-align: left; position: relative;">
                        <div class="indent">
                            <h3>恭喜您激活成功！</h3>
                            	您的账号<a>${username}</a>已激活成功<br>
                            	请您妥善保管好您的资料，不要泄露给他人。 <br>
                            <a href="${ctx}/logout" style=" margin-top: 40px;" class="btn">立即登录</a> &nbsp;&nbsp;&nbsp; <br>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <div class="bott">
        <p class="mc botmenu">
            <a href="#">关于天润云</a> <i></i>
            <a href="#">常见问题</a> <i></i>
            <a href="#">服务保障</a> <i></i>
            <a href="#">加入我们</a>
        </p>

        <p class="copyright">
            陕ICP备12000810-2号 版权所有：
            <a href="//trgis.com" target="_blank">陕西天润科技股份有限公司</a> &nbsp;&nbsp;
            地址:西安市雁塔北路8号 &nbsp;&nbsp;
            邮箱<a href="mailto:contact@trgis.com">contact@trgis.com</a>
        </p>

    </div>

</body>
</html>
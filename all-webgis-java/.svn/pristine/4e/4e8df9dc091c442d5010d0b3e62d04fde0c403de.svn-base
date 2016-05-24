<!DOCTYPE html> <html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>Title</title>
    <link rel="stylesheet" href="../css/comm.css"/>
    <link rel="stylesheet" href="../css/addsubject.css"/>
    <script src="../libs/cseajs/csea$.js" id="seajsnode" main="__/js/addsubject"></script>
</head>
<body>
    <i class="cl_loading page"></i>
    <div class="root">
        <jsp:include page="/assets/top.jsp"></jsp:include>
        <div class="col2">
            <ul class="sub_nav">
                <li>
                    <h3><i class="add">＋</i><i class="triangle"></i>我的专题 </h3>
                    <a href="#" class="bg-color-1">所有专题 <em>(6)</em></a>
                    <a href="#" class="bg-color-2">公开专题 <em>(6)</em></a>
                    <a href="#" class="bg-color-3">私密专题 <em>(6)</em></a>
                </li>
                <li>
                    <h3><i class="triangle"></i>发现 <i class="add"></i></h3>
                    <a href="#" class="bg-color-4">所有专题 <em>(6)</em></a>
                    <a href="#" class="bg-color-5">公开专题 <em>(6)</em></a>
                    <a href="#" class="bg-color-6">私密专题 <em>(6)</em></a>
                </li>
            </ul>
        </div>
        <div class="mainCont col3">
            <div class="inner">
                <h1 class="page_name">我的专题</h1>

                <form action="" class="addsubform">
                    <table>
                        <tr>
                            <td><em>专题名称</em></td>
                            <td ms-cotroller="subname">
                                <input type="text" name="subjectname" value="请输入专题名称" onfocus="if(this.value=='请输入专题名称'){this.value=''};this.style.color='black';" onblur="if(this.value==''||this.value=='请输入专题名称'){this.value='请输入专题名称';this.style.color='#dedede';}">
                                <div class="prompt" ms-each-el="name">{{el.name}}</div>
                            </td>
                        </tr>
                        <tr>
                            <td><em>是否公开</em></td>
                            <td ms-controller="isopen">
                                <div class="switch" ms-click='toggle'>
                                    <span class="open" ms-if="data.toggle"></span><span ms-if="data.toggle">公开</span>
                                    <span ms-if="data.notoggle">私密</span><span class="close" ms-if="data.notoggle"></span>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td><em>专题描述</em></td>
                            <td><textarea name="describe" id="describe" onfocus="if(value=='请输入专题描述'){value='';document.getElementById('describe').style.color='black'}" onblur="if (value ==''){value='请输入专题描述';document.getElementById('describe').style.color='#dedede'}">请输入专题描述</textarea></td>
                        </tr>
                        <tr>
                            <td><em>标签颜色</em></td>
                            <td  ms-controller='s_color'>
                                <div  ms-each-el="color_list">
                                    <span class="test" ms-class-1="gray:el.index == current_color_index" ms-click="click_co(el)"><em ms-css-background-color="el.color"></em></span>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td><em>专题封面</em></td>
                            <td></td>
                        </tr>
                        <tr class="bth">
                            <td><span class="sub_cancel">取消</span><span class="sub_save">保存</span></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
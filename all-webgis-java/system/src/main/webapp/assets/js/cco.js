
$(function(){
    if($("html").is(".framePage")){
        initFramePage()
    }

    if($("html").is(".indexFrame")){
        initIndexFrame()
    }

    //所有页面执行
    var locaMeta = $("meta[loca]");
    var rg =/(.+)\{(.+)\}$/;
    if(locaMeta.length && top.freshPageLoca){
        var str = locaMeta.attr("loca");

        var html="";
        $.each(str.split("|"),function(k,el){
            html+="<a class=dark href=";
            var sign = "&gt; ";
            if(k==0) sign = "";

            if(rg.test(el)){
                html+= "'" + RegExp["$2"] +"'>" + sign + RegExp["$1"];
            }else{
                html+="'#'>" + sign + el;
            }
            html+="</a>";
        });
        top.freshPageLoca(html);
    }
});




/**
 * 初始化主框架
 */
function initIndexFrame(){
    var main = $("#main");
    var loca = $("#loca");

    var mpadd = $("i.sname").css("marginLeft").replace("px","")*1;

    var main = $("#main");
    var ifm = $("iframe",main);
    var ifCont = $("#ifCont");


    var ofTop = loca.offset().top + loca.outerHeight();

    var win = $(window).resize(resize);
    function resize(){
        var sw = win.width();
        var sh = win.height();
        var mainHi = sh - ofTop - 0;
        main.height(mainHi);
        ifCont.height(mainHi - mpadd*2);
    }
    resize();



    var inClass = "scaleIn animated";
    var outClass = "scaleOut animated"
    //
    var navi = $("#navi");
    var links = $(">a",navi);
    links.first().click();
    links.click(function(e){
        e.preventDefault();
    	var last = links.filter(".cur");
    	if(last.length){
            last.data().locaHtml = locaCont.html();
        }
        links.removeClass("cur");
        var t = $(this);
        var verify = t.attr("verify");
        if(verify=="false"){//正常跳转
        	  showIfm(t);
        }else{
        	$.messager.show({
				title: '提示',
				msg: "只有已登录用户才能进行此项操作，请您先登录，谢谢！"
			});
        }
    });
    
    function showIfm($btn){
    	t = $btn;
        t.addClass("cur");
        var d= t.data();
        if(d.locaHtml){
            locaCont.html(d.locaHtml);
        }

        if(!d.frame){
            d.frame = $('<iframe src="about:blank" frameborder="0"></iframe>').attr({src:t.attr("_href")}).appendTo(ifCont);
        }

        //rotateOut animated
        ifCont.children().not(d.frame).attr("class", outClass);
        d.frame.attr("class", inClass + " cur");
    }


    var locaCont = $("#loca .pos");
    //处理当前地址
    window.freshPageLoca = function(html){
        //$("iframe.cur");
        locaCont.html(html);
    }

    links.first().click();
}


/**
 *  初始化框架二级页面
 */
function initFramePage(){
    var header = $("#header");
    header.h = header.height();

    var mainTable = $("#main");

    var infoDiv = $("#infoDiv");
    infoDiv.h = infoDiv.height();

    var ifmCont = $("#ifmCont");
    //初始化页面
    var ifm = $('<iframe name="I2" scrolling="no" src='+ctx+'/user/userlist height="100%" width="100%" border="0" frameborder="0"> 浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe>').appendTo(ifmCont);

    var nav = $("#navi");
    $.fn.zTree.init(
        nav,
        {
            view:{
                dblClickExpand:false
            },
            callback:{
                onClick:function(e,treenId,treeNode,clickFlag){
                    if(treeNode.href){
                        ifm.attr("src",treeNode.href);
                    }
                },
                //被创建
                onNodeCreated:function(e,treeId,treeNode){
                    //console.log(treeId);
                }

            }
        },
        zNodes
    );


    nav.delegate("li>a","click",function(){
        $(this).prev("span").click();;
    });



    var navCont = nav.parent();

    var hfix = 0;

    var resize = function(){
        var sw = win.width();
        var sh = win.height();
        var mainh = sh- header.h - hfix;
        mainTable.height(mainh);
        ifmCont.height(sh- header.h - infoDiv.h - hfix);
        navCont.height(mainh);

    }

    var win = $(window).resize(resize);
    resize();
}



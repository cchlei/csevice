/**
 * Created by Shinelon on 2015/10/23.
 */
define(function(require){

    var def_html =
        '<div class="bdsharebuttonbox" data-tag="share_1">'+
            '<a class="bds_mshare" data-cmd="mshare"></a>'+
            '<a class="bds_qzone" data-cmd="qzone" href="#"></a>'+
            '<a class="bds_tsina" data-cmd="tsina"></a>'+
            '<a class="bds_baidu" data-cmd="baidu"></a>'+
            '<a class="bds_renren" data-cmd="renren"></a>'+
            '<a class="bds_tqq" data-cmd="tqq"></a>'+
            '<a class="bds_more" data-cmd="more">更多</a>'+
            '<a class="bds_count" data-cmd="count"></a>'+
        '</div>'
    ;


    require("./bdshare_fix.css");

    var def_bd_share_config = {
        common : {
            //此处放置通用设置
            bdText : '',
            bdDesc : '',
            bdUrl : '',
            bdPic : ''
        },
        share : [
            //此处放置分享按钮设置
        ],
        slide : [
            //此处放置浮窗分享设置
        ],
        image : [
            //此处放置图片分享设置
        ],
        selectShare : [
            //此处放置划词分享设置
        ]
    }



    return function(shareel,config){
        var sett = $.extend({},config,def_bd_share_config);

        window._bd_share_config = sett;

        shareel = $(shareel);

        if(!shareel.children().length){
            shareel.html(def_html);
        }

        require.async('http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion=' + ~(-new Date() / 36e5),function(){ });


    }
});
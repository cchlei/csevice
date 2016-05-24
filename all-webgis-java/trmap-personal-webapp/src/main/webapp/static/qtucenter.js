define(function(require){
    var cl = require("ctool");
    var cj = require("ctooj");
    var parseTpl = require("parseTpl");
    require("rt/css/animate.css");

    //不允许嵌套
    //parent.location.href = location.href;

    var root = require.resolve("rt/#");

    $(function(){
        var menu_show = $(".menu_show");
        var frame = $("#frame");
        
        menu_show.delegate("a","click",function(e){
            e.preventDefault();

            var m = $(this);
            if(/^undefined$/.test(m.attr("href"))) {
                return;
            }

            frame.attr("src", m.attr("href"));

            var subli;
            var topli;

            //顶级栏目
            if(m.is(".menu_show>li>a")){
                topli = m.parent();
                m.find("em").empty();
            }else if(m.is(".subcont a")){
                subli = m.parent();
                subli.addClass("cur").siblings().removeClass("cur");
                topli = subli.parents("li:first");
                topli.find("a>em").html(m.find("i").html() + " " +m.find("span").text());
            }

            var topsib = topli.siblings();

            topsib.find("a>em").html("");
            topsib.find(".cur").removeClass("cur");
            topsib.find(">a>em").empty();
            topli.addClass("cur").siblings().removeClass("cur");

        });

        $.get(root + "data/menu.json")
            .fail(function(){
                throw "导航菜单获取失败"
            })
            .done(function(data){
                data = cj.tojson(data);
                $.each(data.menus,function(ke,el){

                    var $el = parseTpl(
                        el,
                        '<li> ' +
                        '<a href="{href}"> ' +
                        '   <i>{icon}</i>' +
                        '   <span>{label}</span>' +
                        '   <em></em>' +
                        '</a> ' +
                        '</li>'
                    );

                    $el = $($el);
                    var subcont = $el.find(".subcont");
                    if(!subcont.length) {
                        subcont = $("<ul class=subcont></ul>");
                        subcont.appendTo($el);
                    }

                    el.menus && $.each(el.menus,function(k,e){
                        var $c = parseTpl(
                            e,
                            '<li><a href="{href}">' +
                            '   <i>{icon}</i>' +
                            '   <span>{label}</span>' +
                            '</a></li>'
                        );

                        $c = $($c);
                        /*if(e.active){
                            $c.addClass("cur");
                            $el.find("em").html(e.icon + "&nbsp;" +e.label);
                            $el.addClass("cur");
                        }*/
                        subcont.append($c);
                    });

                    $el.appendTo(menu_show);
                });

                menu_show.find("a:first").click();
            })
        ;
    });
});

function $isBrowser(t) {
    if (!$isBrowser.att) {
        t = t.toLowerCase();
        var e = navigator.userAgent.toLowerCase(),
        n = [];
        n.firefox = -1 != e.indexOf("firefox"),
        n.opera = -1 != e.indexOf("opera"),
        n.safari = -1 != e.indexOf("safari"),
        n.chrome = -1 != e.indexOf("chrome"),
        n.gecko = !n.opera && !n.safari && e.indexOf("gecko") > -1,
        n.ie = !n.opera && -1 != e.indexOf("msie"),
        n.ie6 = !n.opera && -1 != e.indexOf("msie 6"),
        n.ie7 = !n.opera && -1 != e.indexOf("msie 7"),
        n.ie8 = !n.opera && -1 != e.indexOf("msie 8"),
        n.ie9 = !n.opera && -1 != e.indexOf("msie 9"),
        n.ie10 = !n.opera && -1 != e.indexOf("msie 10"),
        $isBrowser.att = n
    }
    return $isBrowser.att[t]
}
function $namespace(name) {
    for (var arr = name.split(","), r = 0, len = arr.length; len > r; r++) for (var i = 0,
    k, name = arr[r].split("."), parent = {}; k = name[i]; i++) 0 === i ? eval("(typeof " + k + ')==="undefined"?(' + k + '={}):"";parent=' + k) : parent = parent[k] = parent[k] || {}
}
$namespace("iunios.indexU3"),
function(t) {
    function e(e) {
        var n = e || window.event,
        i = [].slice.call(arguments, 1),
        a = 0,
        o = 0,
        r = 0;
        return e = t.event.fix(n),
        e.type = "mousewheel",
        n.wheelDelta && (a = n.wheelDelta / 120),
        n.detail && (a = -n.detail / 3),
        r = a,
        void 0 !== n.axis && n.axis === n.HORIZONTAL_AXIS && (r = 0, o = -1 * a),
        void 0 !== n.wheelDeltaY && (r = n.wheelDeltaY / 120),
        void 0 !== n.wheelDeltaX && (o = -1 * n.wheelDeltaX / 120),
        i.unshift(e, a, o, r),
        (t.event.dispatch || t.event.handle).apply(this, i)
    }
    var n = ["DOMMouseScroll", "mousewheel"];
    if (t.event.fixHooks) for (var i = n.length; i;) t.event.fixHooks[n[--i]] = t.event.mouseHooks;
    t.event.special.mousewheel = {
        setup: function() {
            if (this.addEventListener) for (var t = n.length; t;) this.addEventListener(n[--t], e, !1);
            else this.onmousewheel = e
        },
        teardown: function() {
            if (this.removeEventListener) for (var t = n.length; t;) this.removeEventListener(n[--t], e, !1);
            else this.onmousewheel = null
        }
    },
    t.fn.extend({
        mousewheel: function(t) {
            return t ? this.bind("mousewheel", t) : this.trigger("mousewheel")
        },
        unmousewheel: function(t) {
            return this.unbind("mousewheel", t)
        }
    })
} (jQuery),
iunios.indexU3.data = {
    sum: 0,
    pos: 0,
    result: !0,
    winheight: 0,
    mains: null,
    slide: null,
    noMousewheel: !0,
    mainsTop: [],
    heights: []
},
iunios.indexU3.dom = {
    mainShow: $("#mainShow"),
    s11_slide: $("#s11_slide"),
    top: $("#top"),
    video: $("#video"),
    s1_video: $("#s1_video"),
    s1_video_close: $("#s1_video_close"),
    navFixed: $("#navFixed"),
    homeNav: $("#homeNav")
},
iunios.indexU3.init = function() {
    this.data.noMousewheel = this.scrollWay(),
    this.initDate(),
    this.data.slide = this.slides(),
    this.data.noMousewheel ? ($("html").css("overflow", "auto"), this.feature2(), this.initScrollClass(), $(window).on("resize", this.initScrollClass), $(window).on("scroll", this.mobieEvent), this.data.slide.play()) : (this.dom.navFixed.show(), this.feature()),
    this.bindEvent()
},
iunios.indexU3.initDate = function() {
    var t = this.dom,
    e = this.data;
    e.mains = t.mainShow.children(),
    e.sum = e.mains.length,
    e.winheight = $(window).height()
},
iunios.indexU3.initScrollClass = function(t) {
    var e = iunios.indexU3;
    t && (e.data.mainsTop.length = 0, e.data.heights.length = 0),
    e.data.mains.each(function(t) {
        var n, i, a = $(this);
        0 == t ? (n = a.offset().top, i = a.height(), e.data.mainsTop.push(n), e.data.heights.push(i), e.data.fixedTop = n + i) : (n = a.offset().top - .7 * e.data.heights[t - 1], e.data.mainsTop.push(n), e.data.heights.push(a.height()))
    })
},
iunios.indexU3.mobieEvent = function() {
    for (var t = $(window).scrollTop(), e = $("body"), n = iunios.indexU3.data, i = 0, a = n.mainsTop.length; a - 1 > i; i++) {
        if (t >= n.mainsTop[i] && n.mainsTop[i + 1] >= t) {
            e.hasClass("show" + (i + 1)) || (e.attr("class", ""), e.attr("class", "show" + (i + 1)));
            break
        }
        if (t > n.mainsTop[a - 1]) {
            e.hasClass("show" + a) || (e.attr("class", ""), e.attr("class", "show" + a));
            break
        }
    }
},
iunios.indexU3.bindEvent = function() {
    var t = this,
    e = this.dom,
    n = this.data,
    i = function() {
        n.pos = 0,
        t.setStatus(),
        t.move()
    },
    a = function() {
        $("html,body").animate({
            scrollTop: 0
        },
        500)
    },
    o = function() {
        e.s1_video.animate({
            left: 0
        },
        500)
    },
    r = function() {
        e.s1_video.animate({
            left: "-100%"
        },
        500)
    },
    s = function() {
        if (n.result) {
            n.result = !1,
            clearTimeout(i);
            var i = setTimeout(function() {
                n.result = !0
            },
            1e3);
            arguments[1] > 0 ? n.pos--:n.pos++,
            t.setPos(),
            10 == n.pos ? n.slide.play() : clearTimeout(n.slide.timeout),
            t.setStatus(),
            e.mainShow.css("transition", "all 1000ms cubic-bezier(0.86, 0, 0.07, 1)"),
            t.move()
        }
    },
    c = function() {
        n.winheight = $(window).height(),
        e.mainShow.css("transition", "0s"),
        t.move()
    },
    l = function() {
        n.pos = $(this).index(),
        t.setStatus(),
        t.move()
    },
    u = function() {
        var t = $(window).scrollTop();
        e.mainShow.children().eq(0),
        $("body"),
        t > 1e3 ? e.top.show("slow") : e.top.hide("slow")
    },
    d = function(e) {
        var n = e.target,
        i = $(this).find('[data-action="feature"]');
        n == i[0] && (t.data.pos = 1, t.setStatus(), t.move())
    },
    f = function(t) {
        var e = t.target,
        i = $(this).find('[data-action="feature"]'),
        a = n.mains.eq(1).offset().top;
        e == i[0] && $("html").animate({
            scrollTop: a
        },
        800)
    };
    this.dom.video.on("click", o),
    this.dom.s1_video_close.on("click", r),
    n.noMousewheel ? ($("body"), $("html").attr("class", "showScroll"), $(window).on("scroll", u), this.dom.top.on("click", a), this.dom.homeNav.on("click", f)) : ($("body").on("mousewheel", s), $(window).on("resize", c), this.dom.top.on("click", i), this.dom.navFixed.on("click", "a", l), this.dom.homeNav.on("click", d))
},
iunios.indexU3.setPos = function() {
    var t = this.data;
    0 > t.pos ? t.pos = 0 : t.pos >= t.sum ? t.pos = t.sum - 1 : 0
},
iunios.indexU3.setStatus = function() {
    var t = this.data,
    e = this.dom;
    e.navFixed.find("a").eq(t.pos).addClass("cur").siblings().removeClass("cur"),
    t.pos > 3 ? e.top.show("slow") : e.top.hide("slow")
},
iunios.indexU3.setStatus2 = function() {
    var t = this.data,
    e = this.dom;
    t.pos > 3 ? e.top.show("slow") : e.top.hide("slow")
},
iunios.indexU3.bodyClass = function() {
    var t = $("body");
    t.addClass("class", ""),
    t.addClass("show" + (this.data.pos + 1))
},
iunios.indexU3.move = function() {
    var t = this.dom,
    e = this.data;
    if (this.bodyClass(), e.pos == e.sum - 1) {
        var n = $(e.mains[e.sum - 1]).height(),
        i = (e.pos - 1) * e.winheight + n;
        t.mainShow.css({
            "-Webkit-transform": "translate3d(0px, " + -i + "px, 0px)",
            "-moz-transform": "translate3d(0px, " + -i + "px, 0px)",
            transform: "translate3d(0px, " + -i + "px, 0px)"
        })
    } else t.mainShow.css({
        "-Webkit-transform": "translate3d(0px, " + -e.pos * e.winheight + "px, 0px)",
        "-moz-transform": "translate3d(0px, " + -e.pos * e.winheight + "px, 0px)",
        transform: "translate3d(0px, " + -e.pos * e.winheight + "px, 0px)"
    })
},
iunios.indexU3.scrollWay = function() {
    var t = this,
    e = function() {
        var t = navigator.userAgent.toLowerCase(),
        e = t.indexOf("msie"),
        n = parseInt(t.substring(e + 5, e + 7)),
        i = !1;
        return 10 > n && (i = !0),
        i
    };
    return $isBrowser("opera") || e() || t.mobile() ? !0 : !1
},
iunios.indexU3.mobile = function() {
    var t = navigator.userAgent;
    return !! t.match(/AppleWebKit.*Mobile.*/)
},
iunios.indexU3.feature = function() {
    var t = window.location.href,
    e = /#feature/.test(t),
    n = this;
    return e && "http://www.iunios.com/" != document.referrer ? (n.data.pos = 1, n.setStatus(), n.move(), !0) : !1
},
iunios.indexU3.feature2 = function() {
    var t = window.location.href,
    e = /#feature/.test(t),
    n = this;
    if (e) {
        var i = n.data.mains.eq(1).offset().top;
        return $("html").animate({
            scrollTop: i
        },
        800),
        !0
    }
    return ! 1
},
iunios.indexU3.slides = function() {
    function t(t) {
        this.option = {
            imgId: $("#s11_slide"),
            width: 0,
            height: 0,
            pos: 0,
            times: 0,
            speed: 400,
            delay: 5e3,
            autoPlay: !0,
            moveWay: "moveWidth",
            delayLoad: !0,
            col: 3,
            seleSlide: "",
            onBeforeMove: null,
            onAfterMove: null
        };
        for (var e in t) this.option[e] = t[e];
        this.option.imgId && (this.imgslide = this.option.imgId.find('[data-action="imgslide"]'), this.items = this.imgslide.children(), this.itemsLen = this.items.length, this.moveWayFun = this.moveWay[this.option.moveWay], this.init())
    }
    return t.prototype.init = function() {
        function t(t) {
            var e = t.getMonth() + 1;
            return 10 > e && (e = "0" + e),
            e
        }
        var e = this.option,
        n = new Date,
        i = {
            0 : "日",
            1 : "一",
            2 : "二",
            3 : "三",
            4 : "四",
            5 : "五",
            6 : "六"
        },
        a = n.getHours(),
        o = Math.floor(a / 2);
        e.times = 2 * o,
        e.pos = e.col > o ? this.itemsLen + o - e.col - 1 : o - e.col - 1,
        this.items.clone().appendTo(this.imgslide),
        this.monthDom = e.imgId.find('[data-action="month"]'),
        this.hoursDom = e.imgId.find('[data-action="hours"]'),
        this.secondesDom = e.imgId.find('[data-action="secondes"]'),
        this.hoursDom.html(e.times),
        this.monthDom.html(t(n) + "-" + n.getDate() + "&nbsp;&nbsp;" + "星期" + i[n.getDay()]),
        this.imgslide.css("left", 100 * -(e.pos + e.col) + "%"),
        this.play = function() {
            var t = this;
            t.timeout = setTimeout(function() {
                t.autoPlay()
            },
            e.delay)
        }
    },
    t.prototype.move = function() {
        this.option,
        this.setPos(),
        this.moveWayFun()
    },
    t.prototype.moveWay = {
        moveWidth: function() {
            var t = this.option,
            e = this;
            this.imgslide.animate({
                left: 100 * -(t.pos + t.col) + "%"
            },
            t.speed,
            function() {
                var t = e.option;
                t.times = parseInt(t.times) + 2,
                t.times > 24 && (t.times = 2),
                10 > t.times && (t.times = "0" + t.times),
                e.hoursDom.html(t.times)
            })
        }
    },
    t.prototype.initPos = function() {
        var t = this,
        e = t.option;
        e.isRandom && (e.pos = Math.floor(t.itemsLen * Math.random()))
    },
    t.prototype.autoPlay = function() {
        var t = this.option,
        e = this;
        clearTimeout(e.timeout),
        t.pos++,
        e.move(),
        e.timeout = setTimeout(function() {
            e.autoPlay()
        },
        t.delay)
    },
    t.prototype.setPos = function() {
        var t = this.option;
        0 > t.pos && (t.pos = 0),
        t.pos >= this.itemsLen && (this.imgslide.css("left", 100 * -(t.col - 1) + "%"), t.pos = 0)
    },
    t.prototype.setStatus = function() {},
    new t
},
iunios.indexU3.init();
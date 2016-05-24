/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2016/2/25
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    var u = require("_/tr_util");
    var co = require("__/js/comm");
    var ht = $("html");


    var numbertheme;
    $(function(){

        //专题统计数
        numbertheme =avalon.define({
            $id:"number",
            number:{},
            load_topic:function(shareflag){
                co.ec.emit("loadtopic",shareflag);
            }
        })





        u.ajax(
            u.pagevar("themes_nubmer","__/data/themes_number".p()),{},function(t,data){
                if(t.vl){
                    numbertheme.number = data;
                }
                ht.addClass("no_cl_loading");

            }
        )


        avalon.scan();



        if(/^#(-?\d)$/.test(location.hash)){
            co.ec.emit("loadtopic",RegExp["$1"]);
        }



    })

    return {};
});
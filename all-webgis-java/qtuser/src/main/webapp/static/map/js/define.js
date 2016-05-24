var EL_POINT = "point";
var EL_LINE = "line";
var EL_POLY = "poly";

var El__INDEX = {};
El__INDEX[EL_POINT] = 1;
El__INDEX[EL_LINE] = 2
El__INDEX[EL_POLY] = 3;

/**
 * 工程保存地址
 * @type {string}
 */
var action_project_save = function(){
    return getRootPath() + "/mapedit/editMapInfo";
}


/**
 * 删除一个feature
 * @returns {string}
 */
var action_del_feature =  function(){
    return getRootPath() + "/mapedit/editMapInfo";
}

/**
 * 获取项目属性
 */
var action_get_project_prop = function(){
	return getRootPath() + "/mapedit/showMapInfo";
}


/**
 * 自动补全的地址
 */
var action_auto_complete = function(){
    return ""
}

/**
 * 搜索结果地址
 * @returns {string}
 */
var action_search_result = function(val){

}




/**
 * 创建地图元素事件
 * @type {string}
 */
var EV_CREATE_EL = "EV_CREATE_EL";

/**
 * 地图元素绘制完成
 * @type {string}
 */
var EV_DRAW_COMPLETE = "EV_DRAW_COMPLETE";



/**
 * 地图元素修改之前
 * @type {string}
 */
var EV_EDIT_DRAW_BEFORE = "EV_EDIT_DRAW_BEFORE";


/**
 * 地图元素修改完成
 * @type {string}
 */
var EV_EDIT_DRAW_COMPLETE = "EV_EDIT_DRAW_COMPLETE";




/**
 * 绑定地图元素到编辑视图
 * @type {string}
 */
var EV_BIND_MP_ELEMENT = "EV_BIND_MP_ELEMENT";


/**
 * 保存一个元素
 * @type {string}
 */
var EV_MAP_EL_SAVE = "EV_MAP_EL_SAVE";


/**
 * 元素被编辑
 * @type {string}
 */
var EV_EL_PROP_EDIT="EV_EL_PROP_EDIT";


/**
 * 地图上的元素被点击
 * @type {string}
 */
var EV_FEATURE_CLICK = "EV_FEATURE_CLICK";

/**
 * 有图层被选中/取消选中
 * @type {string}
 */
var EV_LAYER_SEL_CHANGE = "EV_LAYER_SEL_CHANGE";

/**
 * 图层顺序发生改变
 * @type {string}
 */
var EV_LAYER_SEQ_CHANGE = "EV_LAYER_SEQ_CHANGE"


/**
 * 用户的feature加载完成
 * @type {string}
 */
var EV_USER_FEATURE_LOADED = "EV_USER_FEATURE_LOADED";

/**
 * 地图初始化完成
 * @type {string}
 */
var EV_MAP_INIT = "EV_MAP_INIT";

/**
 * 删除了一个feature
 * @type {string}
 */
var EV_REMOVE_FEATURE = "EV_REMOVE_FEATURE";

/**
 * 取消绘制
 * @type {string}
 */
var EV_CANCEL_DRAW = "EV_CANCEL_DRAW";


/**
 * 项目状态已保存
 * @type {string[]}
 */
var EV_PROJECT_SAVED="EV_PROJECT_SAVED";


/**
 * 单击用户ft列表
 * @type {string}
 */
var EV_CLICK_FT_LIST_ITEM = "EV_CLICK_FT_LIST_ITEM";


/**
 * 用户地图元素列表加载完成
 * @type {string}
 */
var EV_USER_FEA_LOADED = "EV_USER_FEA_LOADED";


/**
 * 用户图层状态发生改变
 * @type {string}
 */
var EV_LAYER_LOADED =  "EV_LAYER_LOADED";


/**
 * 地图保存之前
 * @type {string}
 */
var EV_MAP_SAVE_BEFORE = "EV_MAP_SAVE_BEFORE";


/**
 * 搜索完成
 * @type {string}
 */
var EV_SEARCH_COMPLETE = "EV_SEARCH_COMPLETE";

/**
 * 单击搜索结果
 * @type {string}
 */
var EV_SEARCH_RESULT_CLICK = "EV_SEARCH_RESULT_CLICK";


avalon.filters.ft_type_icon_chart = function(val){
    if(val == EL_POINT){
        return "&#xe605;";
    }else if(val == EL_LINE){
        return "&#xe60d;";
    }else if(val == EL_POLY){
        return "&#xe604;";
    }
}



//颜色选择器 预设颜色
var def_colors = ["#f1f075" ,"#eaf7ca" ,"#c5e96f" ,"#a3e46b" ,"#7ec9b1" ,"#b7ddf3" ,"#63b6e5" ,"#3ca0d3" ,"#1087bf" ,"#548cba" ,"#677da7" ,"#9c89cc" ,"#c091e6" ,"#d27591" ,"#f86767" ,"#e7857f" ,"#fa946e" ,"#f5c272" ,"#ede8e4" ,"#ffffff" ,"#cccccc" ,"#6c6c6c" ,"#1f1f1f" ,"#000000" ];

//默认图
var def_symbol_src="images/icon_user_black.png";


/**
 * 符号列表
 * @type {{sid: number, src: string}[]}
 */
var symbol_list = [
    {sid:1,src:""},
    {sid:2,src:""},
    {sid:3,src:""},
    {sid:4,src:""},
    {sid:5,src:""},
    {sid:6,src:""},
    {sid:7,src:""},
    {sid:8,src:""},
    {sid:9,src:""},
    {sid:10,src:""},
    {sid:11,src:""},
    {sid:12,src:""},
    {sid:1,src:""},
    {sid:2,src:""},
    {sid:3,src:""},
    {sid:4,src:""},
    {sid:5,src:""},
    {sid:6,src:""},
    {sid:7,src:""},
    {sid:8,src:""},
    {sid:9,src:""},
    {sid:10,src:""},
    {sid:11,src:""},
    {sid:13,src:""}
];

/**
 * 图层管理
 */
var layers_man = {
    layers:[
        //{name:"彩色版", id:"1",  opacity:1,   selected:false},
        ///{name:"灰色版", id:"2",  opacity:1,   selected:false},
        //{name:"冷色版", id:"3",  opacity:1,   selected:false},
        //{name:"暖色版", id:"4",  opacity:1,   selected:false},
        {name:"天地图地图", id:"6",  opacity:1,   selected:true},
        {name:"天地图影像", id:"7",  opacity:1,   selected:false},
        {name:"彩色注记版", id:"5",  opacity:1,   selected:false}
    ],

    id__layer_dic:{},

    /**
     * 通过id获取
     * @param id
     * @returns {*}
     */
    getLayerById:function(id){
        if(!object_leng(this.id__layer_dic)){
            for(var k =0; k<this.layers.length; k++){
                var lay = this.layers[k];
                this.id__layer_dic[lay.id] = lay;
            }
        }
        return this.id__layer_dic[id];
    },

    getLayers:function(){
        return this.layers;
    },

    /**
     * 添加已选择状态的图层
     * @param array
     */
    fresh_selected:function(array){
        var id_cache ={};
        $.each(layers_man.layers,function(k,e){
        	$.each(array,function(k1,e1){
        		if(e.id === e1.z_index){
        			e.selected = true;
        		}
        	})
        })
    }
};


/**
 * 公共属性
 * @type {{type: string, name: string, desc: string, fill_color: string, opacity: number}}
 */
var map_el = {
    /**
     * 临时id
     * 在页面刷新之前唯一
     */
    tmpid:-1,
    /**
     *
     * 全局id，数据库唯一
     */
    id:null,
    type:"",
    name:"",
    occurtime:"",
    desc:"",
    geom:"",
    feature:"",
    has_del:false,
    alreadyfiles:"",
    files:'',
    $uploader_dom:null
    
}

/**
 * 获取一个点的描述
 * @returns {*|void}
 */
var get_map_el_point = function(){
    return $.extend({},map_el,{
        type:EL_POINT,
        scale:3,
        color:def_colors[14],
        //符号的编号，或者路径
        symbol:symbol_list[0].sid,
        tmpid:get_tempId()
    })
}

/**
 * 获取线的描述
 * @returns {*|void}
 */
var get_map_el_line = function(){
    return $.extend({},map_el,{
        type:EL_LINE,
        stroke_width:1,
        stroke_color:def_colors[14],
        stroke_opacity:1,
        tmpid:get_tempId()
    })
}

/**
 * 获取多边形的描述
 * @returns {*|void}
 */
var get_map_el_poly = function(){
    return $.extend({},map_el,{
        type:EL_POLY,
        stroke_width:1,
        stroke_opacity:1,
        stroke_color:def_colors[14],
        fill_color:def_colors[0],
        fill_opacity:1,
        tmpid:get_tempId()
    })
}


var __tempIdCounter = 0;

/**
 * 创建一个临时id,必须不和id相同
 * @returns {number}
 */
function get_tempId(){
    __tempIdCounter = __tempIdCounter - 1;
    return "tmpid" + __tempIdCounter;
}

/**
 * 用来验证一个id是否是tmpid
 * @type {RegExp}
 */
var tmpid_rg = /^tmpid/;


/**
 * 缩放级别的控制
 * @type {{name: string, value: number}[]}
 */
var def_scale = [
    { name: "小",value:3},
    { name: "中",value:5},
    { name: "大",value:7}
];



/**
 * 初始化项目
 */
seajs.usep("_/common,ctool,ctooj",function(co,cl,cj){

    window.co = co;
    var up = cl.urlParas()
    if(up.mapid){
        //编辑状态
        $.get(action_get_project_prop(),{mapid:up.mapid})
            .fail(function(){
                throw "获取项目信息出错";
            })
            .done(function(data){
                data = cj.tojson(data);

                //派发用户列表加载完成事件
                EC.emit(EV_USER_FEA_LOADED,{
                    list:data.feature
                });

                //项目信息
                data.project.mapid = up.mapid;

                $.each(data.project,function(key,ele){
                    co.mtookit.proj_attr[key] = ele;
                });
                //	暂时使用默认图层
                //标识用户已选图层
                layers_man.fresh_selected(data.layer_status);


                var layers_list = layers_man.getLayers()

                //高亮用户已选图层
                co.mtookit.layer_list = layers_list;

                EC.emit(EV_LAYER_LOADED,{
                    list:layers_list,
                    layer_status:data.layer_status
                })
            })
        ;
    }

});




function  object_leng(obj){
    var le = 0;
    for(var k in obj){
        le++;
    }
    return le;
}

/**
 * 比较两个 feature
 * @param o1
 * @param o2
 * @returns {{}}
 */
function object_change(o1,o2){

	
    var deff = {};
    if(o1.tmpid!=o2.tmpid)	return deff;
    if(!o1 || !o2 |!object_leng(o1) || !object_leng(o2))
        return deff;

    for(var k in o1){
        if(o1[k] != o2[k]){
            deff[k] = o2[k];
        }
    }

    return deff;
}


/**
 * 从数组中删除一个元素
 * @param el
 * @param array
 */
function del_form_array(el,array){
    var le = array.length;
    for(var k =0; k<le; k++){
        if(el==array[k]){
            array.splice(k,1);
            return k;
        }
    }
    return -1;
}


/**
 * 获取元素在数组里面的索引
 */
function indexOfArray(el,array,eql_field){
    for (var k = 0; k <array .length; k++) {
        var ek = array[k];
        if(ek[eql_field] == el[eql_field]){
            return k;
        }
    }

    return -1;
}

/**
 * 执行layer
 * @param calback
 */
function tlayer(calback){
    seajs.use("$/layer",function(layer){
        layer.ready(function(){
            calback && calback(layer);
        })
    })
}

/**
 * 处理附件列表拼接
 */
avalon.filters.parsefiles = function(str){
	var html = "";
	if(str){
		var list = str.split(",");
	    $.each(list,function(ke,el){
	    	var keyvalue = el.split(":");
	    	html += "<p id = "+ keyvalue[0] +"><a target=_blank href=http://oss.trmap.cn/rs/download/"+keyvalue[0]+" >"+ keyvalue[1] +"</a> " +
	    			"<a href=javascript:void(0) onclick=delAlready('"+keyvalue[0]+"','"+keyvalue[1]+"');><span style='font-family:iconfont;color: #F38542;'>&#xe619;</span></a>";
	    });
	}
	return html;
}
//已上传附件列表的删除操作
function delAlready(ossid,name){
    layer.confirm("确定要删除吗？",{btn: ["确定", '取消']},function(index){
		layer.close(index);
		getMPTookit().mp_el.$model.alreadyfiles = getMPTookit().mp_el.$model.alreadyfiles.replace(ossid+":"+name,'');
		//更新页面
		$("#" +ossid ).remove();
    })
}
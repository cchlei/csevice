EC.on(EV_CREATE_EL,function(el){
    var el_data = el.el_data;
    !function(){
        try{
        	current_drawing_el = el;
            drawFeature(el);
        }catch(e){
            el_data.geom = "1111" + el_data.tmpid;
            el.callback.delayCall(900);
        }

    }.delayCall(100)
});

var current_drawing_el = null;

EC.on(EV_EL_PROP_EDIT,function(data){
//    console.log(data.props_edit);
//    console.log(data.mpel);

    //todo 测试部分内容
    try{
        editFeatureAndPopup(data.mpel);
    }catch(e){}

//    if(data.props_edit)
});

//有图层被选中/取消选中
EC.on(EV_LAYER_SEL_CHANGE, function(data){
	toggleMap(data);
});
//拖动排序插件
EC.on(EV_LAYER_SEQ_CHANGE, function(data){
	toggleMap(data);
});

//删除绘制好的数据
EC.on(EV_REMOVE_FEATURE,function(data){
    //todo 调试代码
    try{
        removeFeature(data);
    }catch (e){}
});

//点击Feature列表的某一项，定位
EC.on(EV_CLICK_FT_LIST_ITEM,function(data){
    //todo 调试代码
    try{
        positionFeature(data.el);
    }catch (e){}

});

//项目保存成功处理
EC.on(EV_PROJECT_SAVED,function(){
	tlayer(function(layer){
		layer.msg("保存数据成功");
	})
});

//取消画点
EC.on(EV_CANCEL_DRAW,function(){
	if(__drawLayer != null){
		active = false;
		__drawLayer.active=active;
		__drawLayer.activate();
	}
});

//搜索完成之后，在地图上显示搜索结果
EC.on(EV_SEARCH_COMPLETE,function(data){
	__searchResultLayer.removeAllFeatures();
	searchResult(data);
});

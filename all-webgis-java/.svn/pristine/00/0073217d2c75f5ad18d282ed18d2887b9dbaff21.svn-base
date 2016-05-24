use(["_/omap/omap","__/js/comm"],function(omap,co){
	omap.init({
        popup_box:
            '<div class="ol-popup-nowrap2">'+
            '   <div class="ol-pop-content"></div>'+
            '</div>'
        ,
        mapid:"map"
    });

    omap.popup.setOffset([0,-80]);
    omap.popup.setPositioning("top-center");


	omap.drawByUrl(ctx+"/map/featuresByRecord/"+recordId,function(data){
		if(data.features.length){
			var ft = data.features[0];
			omap.popByFid(
                ft.id,
                co.get_line_pop_html(ft.properties.title)
            )
        }
	})
})

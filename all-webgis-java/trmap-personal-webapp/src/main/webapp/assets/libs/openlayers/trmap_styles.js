//正常状态
var defaultStyle = new ol.style.Style({
	image: new ol.style.Icon({
		anchor: [0.5, 1],
		src: ctx+'/assets/images/icon_localtion.png'
	}),
	stroke : new ol.style.Stroke({
		color : 'rgba(78,165,255,1)',
		width : 3
	}),
	fill : new ol.style.Fill({
		color : 'rgba(255, 255, 0, 0.5)'
	})
});
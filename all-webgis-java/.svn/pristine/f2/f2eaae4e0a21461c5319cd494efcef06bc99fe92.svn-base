/***********************地图绘制***********************************/
// 矢量图层数据源定义
var source = new ol.source.Vector({wrapX: false});
var typeSelect = document.getElementById('type');
var draw; // global so we can remove it later
function addInteraction() {
    var value = typeSelect.value;
    if (value !== 'None') {
        var geometryFunction, maxPoints;
        if (value === 'Square') {
            value = 'Circle';
            geometryFunction = ol.interaction.Draw.createRegularPolygon(4);
        } else if (value === 'Box') {
            value = 'LineString';
            maxPoints = 2;
            geometryFunction = function (coordinates, geometry) {
                if (!geometry) {
                    geometry = new ol.geom.Polygon(null);
                }
                var start = coordinates[0];
                var end = coordinates[1];
                geometry.setCoordinates([
                    [start, [start[0], end[1]], end, [end[0], start[1]], start]
                ]);
                return geometry;
            };
        }
        draw = new ol.interaction.Draw({
            source: source,
            type: /** @type {ol.geom.GeometryType} */ (value),
            geometryFunction: geometryFunction,
            maxPoints: maxPoints
        });
        map.addInteraction(draw);
    }
}


/**
 * Handle change event.
 */
typeSelect.onchange = function () {
    map.removeInteraction(draw);
    addInteraction();
};
addInteraction();
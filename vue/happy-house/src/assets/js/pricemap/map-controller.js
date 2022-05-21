const method = function () {
  kakao.maps.event.addListener(map, "tilesloaded", function () {
    removeMarker();
    updateMarkers();
  });
};
const map_controller_js = { method };
export default map_controller_js;

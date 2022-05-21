let mapContainer = document.getElementById("indexMap"), // 지도를 표시할 div
  mapOption = {
    //center: new kakao.maps.LatLng(127.03659614051297, 37.50069600356891), // 지도의 중심좌표
    center: new kakao.maps.LatLng(0, 0), // 지도의 중심좌표
    level: 5, // 지도의 확대 레벨
  };

let map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

let geocoder = new kakao.maps.services.Geocoder();

// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
let mapTypeControl = new kakao.maps.MapTypeControl();

// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
let zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

function setCenter(lat, lng) {
  // 이동할 위도 경도 위치를 생성합니다
  let moveLatLon = new kakao.maps.LatLng(lat, lng);

  // 지도 중심을 이동 시킵니다
  map.setCenter(moveLatLon);
}

function panTo(lat, lng) {
  // 이동할 위도 경도 위치를 생성합니다
  let moveLatLon = new kakao.maps.LatLng(lat, lng);

  // 지도 중심을 부드럽게 이동시킵니다
  // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
  map.panTo(moveLatLon);
}
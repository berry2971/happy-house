const method = function () {
  setTimeout(() => {
    moveMapWithAddress("서울특별시 강남구 테헤란로 지하 156");
    map.setLevel(5);
  }, 1500);
};
const map_init_js = { method };
export default map_init_js;

<template>
  <div>
    <!--주소 선택-->
    <div id="indexDropdownBox">
      <form>
        <select id="inputMetropolis" class="bg-dark indexDropdown">
          <option>선택</option>
          <option>서울특별시</option>
          <option>부산광역시</option>
          <option>대구광역시</option>
          <option>인천광역시</option>
          <option>광주광역시</option>
          <option>대전광역시</option>
          <option>울산광역시</option>
          <option>세종특별자치시</option>
          <option>경기도</option>
          <option>강원도</option>
          <option>충청북도</option>
          <option>충청남도</option>
          <option>전라북도</option>
          <option>전라남도</option>
          <option>경상북도</option>
          <option>경상남도</option>
          <option>제주특별자치도</option>
        </select>
        <select id="inputCity" class="bg-dark indexDropdown">
          <option>선택</option>
        </select>
        <select id="inputTown" class="bg-dark indexDropdown">
          <option>선택</option>
        </select>
      </form>
    </div>
    <!--지도-->
    <div style="display: flex">
      <div class="map_wrap" style="width: 70%; display: inline-block">
        <div
          class="map"
          ref="kakaomap"
          style="width: 100%; height: 100%; position: relative"
        ></div>
        <div id="menu_wrap" class="bg_white">
          <ul id="placesList"></ul>
          <div id="pagination"></div>
        </div>
      </div>
      <div
        id="map_result"
        class="row"
        style="width: 30%; display: inline-block"
      ></div>
    </div>
    <b-button class="btn" @click="test()">테스트</b-button>
  </div>
</template>
<script>
import test from "@/assets/js/test.js";

export default {
  name: "KaKaoMap",
  data() {
    return {
      mapInstance: null,
    };
  },
  mounted() {
    var container = this.$refs.kakaomap;
    var options = {
      center: new window.kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
      level: 3, //지도의 레벨(확대, 축소 정도)
    };

    this.mapInstance = new window.kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
  },
  methods: {
    test() {
      test.method();
    },
  },
};
</script>

<style></style>

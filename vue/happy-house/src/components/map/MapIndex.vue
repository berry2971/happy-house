<template>
  <div>
    <!--주소 선택-->
    <div id="indexDropdownBox">
      <form>
        <select
          id="inputMetropolis"
          class="bg-dark indexDropdown"
          v-model="addrLv1"
        >
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
        <select id="inputCity" class="bg-dark indexDropdown" v-model="addrLv2">
          <option>강남구</option>
          <option>강서구</option>
          <option>마포구</option>
          <option>관악구</option>
        </select>
        <select id="inputTown" class="bg-dark indexDropdown" v-model="addrLv3">
          <option>대치동</option>
          <option>개포동</option>
        </select>
      </form>
    </div>

    <!--지도-->
    <KakaoMap ref="kakaoMap"
      :addrLv1="addrLv1"
      :addrLv2="addrLv2"
      :addrLv3="addrLv3"
    ></KakaoMap>
  </div>
</template>

<script>
import KakaoMap from "./KakaoMap.vue";

export default {
  data() {
    return {
      addrLv1: "선택",
      addrLv2: "",
      addrLv3: "",
    };
  },
  components: {
    KakaoMap,
  },
  watch: {
    addrLv1() {
      // TODO: load <select> of addrLv2
      // TODO: init <select> of addrLv3
    },
    addrLv2() {
      // TODO: load <select> of addrLv2
    }
  }
};
</script>

<style></style>

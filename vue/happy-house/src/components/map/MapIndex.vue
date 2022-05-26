<template>
  <div>
    <!--주소 선택-->
    <div id="indexDropdownBox">
      <form>
        <select
          id="inputMetropolis"
          class="indexDropdown"
          v-model="addrLv1"
        >
          <option id="defaultOption1">선택</option>
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
        <select id="inputCity" class="indexDropdown" v-model="addrLv2">
          <option id="defaultOption2">선택</option>
        </select>
        <select id="inputTown" class="indexDropdown" v-model="addrLv3">
          <option id="defaultOption3">선택</option>
        </select>
      </form>
    </div>

    <!--지도-->
    <KakaoMap
      ref="kakaoMap"
      :addrLv1="addrLv1"
      :addrLv2="addrLv2"
      :addrLv3="addrLv3"
    ></KakaoMap>
  </div>
</template>

<script>
import KakaoMap from "./KakaoMap.vue";
import axios from "axios";

export default {
  data() {
    return {
      addrLv1: "선택",
      addrLv2: "선택",
      addrLv3: "선택",
    };
  },
  components: {
    KakaoMap,
  },
  watch: {
    addrLv1() {
      const inputCityEl = document.getElementById("inputCity");
      const inputTownEl = document.getElementById("inputTown");
      axios
        .get("http://localhost:8090/districts", {
          params: {
            addr_lv1: this.addrLv1,
          },
        })
        .then((res) => {
          inputCityEl.innerHTML = "<option selected id='defaultOption2'>선택</option>";
          inputTownEl.innerHTML = "<option selected id='defaultOption3'>선택</option>";
          res.data.forEach((item) => {
            inputCityEl.innerHTML += "<option>" + item + "</option>";
          });
        });
      const defaultOption1El = document.getElementById("defaultOption1");
      if (defaultOption1El) defaultOption1El.remove();
    },
    addrLv2() {
      const inputTownEl = document.getElementById("inputTown");
      axios
        .get("http://localhost:8090/districts", {
          params: {
            addr_lv1: this.addrLv1,
            addr_lv2: this.addrLv2,
          },
        })
        .then((res) => {
          inputTownEl.innerHTML = "<option selected id='defaultOption3'>선택</option>";
          res.data.forEach((item) => {
            inputTownEl.innerHTML += "<option>" + item + "</option>";
          });
        });
      const defaultOption2El = document.getElementById("defaultOption2");
      if (defaultOption2El) defaultOption2El.remove();
    },
    addrLv3() {
      const defaultOption3El = document.getElementById("defaultOption3");
      if (defaultOption3El) defaultOption3El.remove();
    }
  },
};
</script>

<style>
#indexDropdownBox {
  background-color: rgb(255,95,0);
}

.indexDropdown {
  background-color: rgb(255,95,0);
}

</style>

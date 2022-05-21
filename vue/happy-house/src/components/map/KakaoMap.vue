<template>
  <div>
    <div id="kakao-map" ref="map"></div>
    <div id="deals-list" ref="list">
      <div v-for="deal in deals">
        <div>
          <div>{{deal.addr_lv3}} {{deal.bunji}}</div>
          <div>{{deal.addr_road}}</div>
          <div>{{deal.apt_name}}</div>
          <div>{{deal.deal_year_month}}</div>
          <div>{{deal.price}}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

let kakao = window.kakao;
export default {
  props: ["addrLv1", "addrLv2", "addrLv3"],
  data() {
    return {
      mapInstance: null,
      deals: null,
      mapLat: 33.450701,
      mapLng: 126.570667,
    };
  },
  mounted() {
    kakao = kakao || window.kakao;

    const container = this.$refs.map;
    const options = {
      center: {
        lat: this.mapLat,
        lng: this.mapLng,
      },
      level: 8,
    };
    const { center, level } = options;
    this.mapInstance = new kakao.maps.Map(container, {
      center: new kakao.maps.LatLng(center.lat, center.lng),
      level,
    });

    kakao.maps.event.addListener(this.mapInstance, "tilesloaded", () => {
      const center = this.mapInstance.getCenter();
      this.mapLng = center.getLng();
      this.mapLat = center.getLat();
      const geocoder = new kakao.maps.services.Geocoder();
      const coord = new kakao.maps.LatLng(center.getLat(), center.getLng());
      geocoder.coord2RegionCode(coord.getLng(), coord.getLat(), (result, status) => {
        if (status === kakao.maps.services.Status.OK) {
          // this.addrLv1 = result[0].region_1depth_name;
          // this.addrLv2 = result[0].region_2depth_name;
          // this.addrLv3 = result[0].region_3depth_name;
          this.$emit("change-addr-by-map", result[0]);
        }
      });

      this.displayAptList();
    });
  },
  methods: {
    briefStateNameToFullStateName(stateName) {
      if (["부산", "인천", "대구", "대전", "광주", "울산"].includes(stateName)) return stateName + "광역시";
      else if (["경기", "강원", "제주"].includes(stateName)) return stateName + "도";
      else if (stateName == "서울") return "서울특별시";
      else if (stateName == "경남") return "경상남도";
      else if (stateName == "경북") return "경상북도";
      else if (stateName == "충남") return "충청남도";
      else if (stateName == "충북") return "충청북도";
      else if (stateName == "전남") return "전라남도";
      else if (stateName == "전북") return "전라북도";
      else if (stateName == "세종") return "세종특별자치시";
      else return stateName;
    },
    displayAptList() {
      const places = new kakao.maps.services.Places();
      places.setMap(this.mapInstance);

      // callback after keywordSearch
      const callback = (result, stat) => {
        if (stat == kakao.maps.services.Status.OK) {
          for (let i in result) {
            const apt = result[i];
            if (apt["category_name"] !== "부동산 > 주거시설 > 아파트") {
              continue;
            }
            displayApt(apt);
          }
        }
      };

      const displayApt = (apt) => {
        const dealsListEl = document.getElementById("deals-list");

        const splitedAddress = apt.address_name.split(" ");
        const addrLv1_ = this.briefStateNameToFullStateName(splitedAddress[0]);
        const addrLv2_ = splitedAddress[1];
        const addrLv3_ = splitedAddress[2];
        const splitedBunji = splitedAddress[3].split("-");
        const bunji_main_ = splitedBunji[0];
        const bunji_sub_ = splitedBunji.length > 1 ? splitedBunji[1] : "0";

        axios
          .get("http://localhost:8090/deals/where", {
            params: {
              addr_lv1: addrLv1_,
              addr_lv2: addrLv2_,
              addr_lv3: addrLv3_,
              bunji_main: bunji_main_,
              bunji_sub: bunji_sub_,
            },
          })
          .then((res) => {
            this.deals = res.data;
          });
      };

      const searchOptions = {
        useMapCenter: true,
        useMapBounds: true,
        sort: kakao.maps.services.SortBy.DISTANCE,
      };
      places.keywordSearch("아파트", callback, searchOptions);
    },
    printCenter() {
      console.log(this.mapInstance.getCenter());
    },
    moveMapWithKeyword(keyword) {
      const geocoder = new kakao.maps.services.Geocoder();
      geocoder.addressSearch(keyword, (result, stat) => {
        if (stat == kakao.maps.services.Status.OK) {
          const targetX = result[0].x;
          const targetY = result[0].y;
          this.mapInstance.setCenter(new kakao.maps.LatLng(targetY, targetX));
        }
      });
    },
  },
  watch: {
    addrLv1() {
      this.moveMapWithKeyword(this.addrLv1);
    },
    addrLv2() {
      this.moveMapWithKeyword(`${this.addrLv1} ${this.addrLv2}`);
    },
    addrLv3() {
      this.moveMapWithKeyword(`${this.addrLv1} ${this.addrLv2} ${this.addrLv3}`)
    }
  }
};
</script>

<style>
#kakao-map {
  width: 500px;
  height: 500px;
}
</style>

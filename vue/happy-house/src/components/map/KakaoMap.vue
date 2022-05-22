<template>
  <div>
    <div id="kakao-map" ref="map"></div>
    <div id="deals-list" ref="list">
      <div class="apt-item" v-for="apt in apts">
        <div>
          <div>{{apt.name}}</div>
          <div>{{apt.addr}}</div>
          <div>{{apt.roadAddr}}</div>
          <div>{{apt.built_year}}</div>
          <div class="deal-item" v-for="deal in apt.deals">
            <div>가격: {{ new Intl.NumberFormat('kr-KR', {maximumSignificantDigits:3}).format( parseInt(deal.price)*10000 ) }}</div>
            <div>면적: {{deal.square}}</div>
            <div>연월: {{deal.deal_year_month}}</div>
            <div>일: {{deal.deal_day}}</div>
            <div>층: {{deal.floor}}</div>
          </div>
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
      apts: null,
      mapLat: 37.50092120938686,
      mapLng: 127.03674326873177,
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
      this.apts = [];

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

      const searchOptions = {
        useMapCenter: true,
        radius: 20000,
        sort: kakao.maps.services.SortBy.DISTANCE,
      };
      places.keywordSearch("아파트", callback, searchOptions);


      const displayApt = (apt) => {
        const splitedAddress = apt.address_name.split(" ");
        const addrLv1_ = this.briefStateNameToFullStateName(splitedAddress[0]);
        const addrLv2_ = splitedAddress[1];
        const addrLv3_ = splitedAddress[2];
        const splitedBunji = splitedAddress[3].split("-");
        const bunji_main_ = splitedBunji[0];
        const bunji_sub_ = splitedBunji.length > 1 ? splitedBunji[1] : "0";

        const aptData = {
          name: apt.place_name,
          addr: apt.address_name,
          roadAddr: apt.road_address_name,
          builtYear: apt.built_year,
          deals: [],
        };

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
            console.log(res);
            for (let i in res.data) {
              aptData.deals.push(res.data[i]);
            }
            this.apts.push(aptData);
          });
      };
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

.apt-item {
  border-style: solid;
  border-width: 1px;
  padding:5px;
  margin:5px;
}

.deal-item {
  border-style: solid;
  border-width: 1px;
  padding:5px;
  margin:5px;
}
</style>

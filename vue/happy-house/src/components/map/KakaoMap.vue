<template>

  <div id="indexMap">
    <div id="kakao-map" ref="map"></div>
    <div id="deal-list-wrap">
      <div id="deals-list" ref="list">
        <div class="apt-item" v-for="(apt, idx) in apts">
          <div @click="moveToDetailPage($event)" :id="idx" @mouseover="emphaMarker(apt, $event)" @mouseout="disEmphaMarker(apt, $event)">
            <div>
              <img src="./apt_blue.png" style="height:16px; width:16px; margin-right: 3px;">
              <span class="apt-item-name">{{ apt.name }}</span>
            </div>
            <div class="apt-item-addr">{{ apt.addr }}</div>
            <div class="apt-item-roadaddr">{{ apt.roadAddr }}</div>
<!--            <div class="deal-item" v-for="deal in apt.deals">-->
<!--              <div>-->
<!--                가격:-->
<!--                {{-->
<!--                  new Intl.NumberFormat("kr-KR", {-->
<!--                    maximumSignificantDigits: 3,-->
<!--                  }).format(parseInt(deal.price) * 10000)-->
<!--                }}-->
<!--              </div>-->
<!--              <div>면적: {{ deal.square }}</div>-->
<!--              <div>연월: {{ deal.deal_year_month }}</div>-->
<!--              <div>일: {{ deal.deal_day }}</div>-->
<!--              <div>층: {{ deal.floor }}</div>-->
<!--            </div>-->
            <div v-if="apt.numDeal > 0">
              <div class="apt-item-num-deal">실거래 {{apt.numDeal}}건</div>
              <div class="apt-item-price-range">최저 {{apt.minPrice/10000}}억 ~ 최고 {{apt.maxPrice/10000}}억</div>
            </div>
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
      markers: [],
      overlays: [],
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
      level: 5,
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
    moveToDetailPage(ev) {
      const addr =
        ev.currentTarget.getElementsByClassName("apt-item-addr")[0].innerText;
      const splitedAddress = addr.split(" ");
      const addrLv1_ = this.briefStateNameToFullStateName(splitedAddress[0]);
      const addrLv2_ = splitedAddress[1];
      const addrLv3_ = splitedAddress[2];
      const splitedBunji = splitedAddress[3].split("-");
      const bunji_main_ = splitedBunji[0];
      const bunji_sub_ = splitedBunji.length > 1 ? splitedBunji[1] : "0";

      this.$router.push({
        name: "apt-detail",
        query: {
          apt: this.apts[parseInt(ev.currentTarget.id)],
        },
      });
    },
    briefStateNameToFullStateName(stateName) {
      if (["부산", "인천", "대구", "대전", "광주", "울산"].includes(stateName))
        return stateName + "광역시";
      else if (["경기", "강원", "제주"].includes(stateName))
        return stateName + "도";
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
      const callback = (result, stat, pagination) => {
        if (stat == kakao.maps.services.Status.OK) {
          /* marker, overlay */
          for (let i = 0; i < this.markers.length; i++) {
            this.markers[i].setMap(null);
          }
          this.markers = [];
          for (let i = 0; i < this.overlays.length; i++) {
            this.overlays[i].setMap(null);
          }
          this.overlays = [];
          /* marker, overlay end */

          for (let i in result) {
            const apt = result[i];

            if (apt["category_name"] !== "부동산 > 주거시설 > 아파트") {
              continue;
            }

            //this.displayMarker(apt);
            displayApt(apt);
          }
        }
        if (pagination.hasNextPage) {
          searchApts();
        }
      };

      const searchApts = () => {
        const searchOptions = {
          useMapCenter: true,
          radius: 20000,
          //sort: kakao.maps.services.SortBy.DISTANCE,
          page: this.searchPage++,
        };
        places.keywordSearch("아파트", callback, searchOptions);
      };
      searchApts();

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
          addrLv1: addrLv1_,
          addrLv2: addrLv2_,
          addrLv3: addrLv3_,
          bunjiMain: bunji_main_,
          bunjiSub: bunji_sub_,
          roadAddr: apt.road_address_name,
          builtYear: apt.built_year,
          x: apt.x,
          y: apt.y,
          deals: [],
          numDeal: 0,
          minPrice: 100000000000,
          maxPrice: 0,
          marker: null,
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
            for (let i in res.data) {
              aptData.deals.push(res.data[i]);
              aptData.numDeal++;
              aptData.minPrice = Math.min(aptData.minPrice, res.data[i].price);
              aptData.maxPrice = Math.max(aptData.maxPrice, res.data[i].price);
            }

            this.displayMarker(aptData);
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
    emphaMarker(apt, event) {
      this.emphasizeDealListItem(event.currentTarget.parentNode);
      const position = new kakao.maps.LatLng(apt.y, apt.x);
      let overlayContent = this.createOverlayContent(apt.numDeal, apt.name, apt.minPrice, apt.maxPrice);
      const overlay = new kakao.maps.CustomOverlay({
        content: overlayContent,
        // map: this.mapInstance,
        position: position,
      });
      overlay.setMap(this.mapInstance);
      this.overlays.push(overlay);
    },
    disEmphaMarker(apt, event) {
      this.disEmphasizeDealListItem(event.currentTarget.parentNode);
      for (let i = 0; i < this.overlays.length; i++) {
        this.overlays[i].setMap(null);
      }
      this.overlays = [];
    },
    /*marker*/
    createOverlayContent(numDeal, name, minPrice, maxPrice) {
      if (numDeal != 0) {
        return `
          <div style="border-radius:5px; padding:5px; width=50px; background-color: white;">
            <div style="font-size:16px;">${name}</div>
            <div style="font-size:12px;">${minPrice/10000}억~${maxPrice/10000}억</div>
          </div>
        `;
      } else {
        return `
          <div style="border-radius:5px; padding:5px; width=50px; background-color: white;">
            <div>${name}</div>
          </div>
        `;
      }
    },
    displayMarker(apt) {
      const position = new kakao.maps.LatLng(apt.y, apt.x);
      const marker = new kakao.maps.Marker({
        position: position,
      });
      marker.setMap(this.mapInstance);
      this.markers.push(marker);

      let overlayContent = this.createOverlayContent(apt.numDeal, apt.name, apt.minPrice, apt.maxPrice);
      apt.marker = marker;

      const overlay = new kakao.maps.CustomOverlay({
        content: overlayContent,
        // map: this.mapInstance,
        position: position,
      });
      this.overlays.push(overlay);

      kakao.maps.event.addListener(marker, 'mouseover', () => {
        overlay.setMap(this.mapInstance);
        const dealsListItem = document.getElementsByClassName("apt-item-name");
        for (let i = 0; i < dealsListItem.length; i++) {
          if (dealsListItem[i].innerHTML == apt.name) {
            const targetBoldItem = dealsListItem[i].parentElement.parentElement.parentElement;
            this.emphasizeDealListItem(targetBoldItem);
            break;
          }
        }
      });

      kakao.maps.event.addListener(marker, 'mouseout', () => {
        overlay.setMap(null);
        const dealsListItem = document.getElementsByClassName("apt-item-name");
        for (let i = 0; i < dealsListItem.length; i++) {
          if (dealsListItem[i].innerHTML == apt.name) {
            const targetBoldItem = dealsListItem[i].parentElement.parentElement.parentElement;
            this.disEmphasizeDealListItem(targetBoldItem);
            break;
          }
        }
      });
    },
    emphasizeDealListItem(targetBoldItem) {
      targetBoldItem.style.color="white";
      targetBoldItem.style.backgroundColor="rgba(15, 15, 255, 0.8)";
      targetBoldItem.style.borderRadius="5px";
      targetBoldItem.scrollIntoView({
        behavior: "smooth",
        block: "nearest",
      });

      const t1 = targetBoldItem.getElementsByClassName("apt-item-price-range");
      Array.prototype.forEach.call(t1, (el) => {
        el.style.color="white";
      })
      const t2 = targetBoldItem.getElementsByClassName("apt-item-num-deal");
      Array.prototype.forEach.call(t2, (el) => {
        el.style.color="white";
      })

    },
    disEmphasizeDealListItem(targetBoldItem) {
      targetBoldItem.style.color="black";
      targetBoldItem.style.backgroundColor="rgba(255, 255, 255, 0)";
      targetBoldItem.style.borderRadius="0px";

      const t1 = targetBoldItem.getElementsByClassName("apt-item-price-range");
      Array.prototype.forEach.call(t1, (el) => {
        el.style.color="firebrick";
      })
      const t2 = targetBoldItem.getElementsByClassName("apt-item-num-deal");
      Array.prototype.forEach.call(t2, (el) => {
        el.style.color="firebrick";
      })
    },
  },
  watch: {
    addrLv1() {
      this.moveMapWithKeyword(this.addrLv1);
      this.mapInstance.setLevel(7);
    },
    addrLv2() {
      this.moveMapWithKeyword(`${this.addrLv1} ${this.addrLv2}`);
      this.mapInstance.setLevel(5);
    },
    addrLv3() {
      this.moveMapWithKeyword(
        `${this.addrLv1} ${this.addrLv2} ${this.addrLv3}`
      );
      this.mapInstance.setLevel(3);
    },
  },
};
</script>

<style>
#indexMap {
  width: 100%;
  height: 100vh;
  margin: 0px;
  padding: 0px;
  display: inline-block;
}

#kakao-map {
  margin: 0px;
  width: 100%;
  height: 100%;
  padding: 0px;
  float: left;
  position: absolute;
  z-index: 10;
  /*display: inline-block;*/
}

#deal-list-wrap {
  height: 100vh;
  padding: 1%;
  width: 20%;
  z-index: 15;
  vertical-align: top;
  float: right;
  position: relative;
  min-height: 100px;
}

#deals-list {
  overflow-y: auto;
  height: 100%;
  margin: 0px;
  padding: 5px;
  min-height: 100px;
  background-color: rgba(255, 255, 255, 0.7);
}

.apt-item {
  margin-bottom: 5px;
  padding: 5px;
  border-style: solid;
  border-width: 3px;
  border-color: rgba(255,255,255,0);
}

.deal-item {
  margin-bottom: 5px;
  padding: 5px;
  border-style: solid;
  border-width: 3px;
}

.apt-item-name {
  font-weight: bold;
  font-size: 16px;
}

.apt-item-addr {
  font-size: 13px;
}

.apt-item-roadaddr {
  font-size: 13px;
}

.apt-item-num-deal {
  color: firebrick;
  font-size: 13px;
}

.apt-item-price-range {
  color: firebrick;
  font-size: 13px;
}

.hidden {
  display: block;
}


</style>

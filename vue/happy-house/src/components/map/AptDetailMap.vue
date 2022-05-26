<template>
  <div class="apt-detail-map-root">
    <div id="map" ref="map"></div>
    <div id="categorySelectBox" ref="category">
      <div id="categorySelectBoxMart" class="categorySelect">
        <div class="category-img"><img src="@/assets/img/map-cate/mart.png" /></div>
        <div class="category-label">마트</div>
      </div>
      <div id="categorySelectBoxConv" class="categorySelect">
        <div class="category-img"><img src="@/assets/img/map-cate/conv.png" /></div>
        <div class="category-label">편의점</div>
      </div>
      <div id="categorySelectBoxEdu" class="categorySelect">
        <div class="category-img"><img src="@/assets/img/map-cate/edu.png" /></div>
        <div class="category-label">학교</div>
      </div>
      <div id="categorySelectBoxAcademy" class="categorySelect">
        <div class="category-img"><img src="@/assets/img/map-cate/academy.png" /></div>
        <div class="category-label">학원</div>
      </div>
      <div id="categorySelectBoxBank" class="categorySelect">
        <div class="category-img"><img src="@/assets/img/map-cate/bank.png" /></div>
        <div class="category-label">은행</div>
      </div>
      <div id="categorySelectBoxClinic" class="categorySelect">
        <div class="category-img"><img src="@/assets/img/map-cate/clinic.png" /></div>
        <div class="category-label">병원</div>
      </div>
      <div id="categorySelectBoxPharm" class="categorySelect">
        <div class="category-img"><img src="@/assets/img/map-cate/pharm.png" /></div>
        <div class="category-label">약국</div>
      </div>
      <div id="categorySelectBoxSubway" class="categorySelect">
        <div class="category-img"><img src="@/assets/img/map-cate/subway.png" /></div>
        <div class="category-label">지하철</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

let kakao = window.kakao;
export default {
  name: "AptDetailMap",
  props: ["aptX", "aptY"],
  data() {
    return {
      mapInstance: null,
      categories: ["mart", "conv", "edu", "academy", "bank", "clinic", "pharm", "subway"],
      markers: [[], [], [], [], [], [], [], []],
      enableds: [false, false, false, false, false, false, false, false],
      kakaoPlaceSearch: null,
      selectedCategory: "",
      categoryCode: {
        "mart": "MT1",
        "conv": "CS2",
        "edu": "SC4",
        "academy": "AC5",
        "bank": "BK9",
        "clinic": "HP8",
        "pharm": "PM9",
        "subway": "SW8",
      },
    };
  },
  mounted() {
    this.createMap();
    this.markCenterApartment();
    this.addEventsToCategorySelectBoxWrapper();
  },
  methods: {
    createMap() {
      kakao = kakao || window.kakao;
      const container = this.$refs.map;
      const options = {
        center: {
          lat: this.aptY,
          lng: this.aptX,
        },
        level: 3,
      };
      const { center, level } = options;
      this.mapInstance = new kakao.maps.Map(container, {
        center: new kakao.maps.LatLng(center.lat, center.lng),
        level: level,
      });
      this.kakaoPlaceSearch = new kakao.maps.services.Places(this.mapInstance);
    },
    markCenterApartment() {
      const marker = new kakao.maps.Marker({
        position: new kakao.maps.LatLng(this.aptY, this.aptX),
        image: new kakao.maps.MarkerImage(
          require("@/components/map/apt_blue.png"),
          new kakao.maps.Size(50, 50)
        ),
      });
      marker.setMap(this.mapInstance);
    },
    addEventsToCategorySelectBoxWrapper() {
      for (let i = 0; i < this.categories.length; i++) {
        const category = this.categories[i];
        this.addEventsToCategorySelectBox(category, i);
      }
    },
    addEventsToCategorySelectBox(category, categoryIdx) {
      document.getElementById(`categorySelectBox${category[0].toUpperCase() + category.substring(1)}`).addEventListener("click", () => {
        this.selectedCategory = category;
          if (this.enableds[categoryIdx]) {
            this.removeMarkersFromMap(this.markers[categoryIdx]);
            this.enableds[this.getIdxFromCategory(category)] = false;
            return;
          }
          this.enableds[this.getIdxFromCategory(category)] = true;
          this.searchPlaces(this.categoryCode[category]);
        });
    },
    removeMarkersFromMap(markersList) {
      for (let i = 0; i < markersList.length; i++) {
        markersList[i].setMap(null);
      }
      markersList = [];
    },
    searchPlaces(targetCategory) {
      const addMarker = (position) => {
        const markerImgSrc = require(`@/assets/img/map-cate/${this.selectedCategory}.png`);
        const markerImgSize = new kakao.maps.Size(25, 25);
        const markerImg = new kakao.maps.MarkerImage(markerImgSrc, markerImgSize);
        const marker = new kakao.maps.Marker({
          position: position,
          image: markerImg,
          map: this.mapInstance,
        });
        this.markers[this.getIdxFromCategory(this.selectedCategory)].push(marker);
        return marker;
      };
      const displayPlaces = (places) => {
        for (let i = 0; i < places.length; i++) {
          const place = places[i];
          addMarker(new kakao.maps.LatLng(place.y, place.x));
          // TODO: add listeners to markers here
        }
      };
      const callback = (data, status, pagination) => {
        if (status === kakao.maps.services.Status.OK) {
          displayPlaces(data);
        }
      };
      this.kakaoPlaceSearch.categorySearch(targetCategory, callback, {
        useMapCenter: true,
        radius: 20000,
        sort: kakao.maps.services.SortBy.DISTANCE,
      });
    },
    getIdxFromCategory(category) {
      if (category === "mart") return 0;
      else if (category === "conv") return 1;
      else if (category === "edu") return 2;
      else if (category === "academy") return 3;
      else if (category === "bank") return 4;
      else if (category === "clinic") return 5;
      else if (category === "pharm") return 6;
      else if (category === "subway") return 7;
    },
  },
};
</script>

<style scoped>
.apt-detail-map-root {
  position: relative;
}

#map {
  width: 100%;
  height: 500px;
}

#categorySelectBox {
  position: absolute;
  display: flex;
  top: 10px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 99;
  display: flex;
  list-style: none;
  padding: 5px 5px 5px 5px;
  font-size: 12px;
  height: 50px;
}

.categorySelect {
  display: flex;
  flex-direction: column;
  width: 50px;
  height: 50px;
  z-index: 300;
}

#categorySelectBox div {
  z-index: 100;
}

#categorySelectBox img {
  height: 25px;
  width: 25px;
}

#categorySelectBox #categorySelectBoxMart {
}

#categorySelectBox #categorySelectBoxConv {
}

#categorySelectBox #categorySelectBoxEdu {
}

#categorySelectBox #categorySelectBoxAcademy {
}

#categorySelectBox #categorySelectBoxBank {
}

#categorySelectBox #categorySelectBoxClinic {
}

#categorySelectBox #categorySelectBoxPharm {
}

#categorySelectBox #categorySelectBoxSubway {
}

.category-label {
  font-size: 12px;
  text-align: center;
  padding: 0px;
}

.category-img {
  display: flex;
  justify-content: center;
  padding: 0px;
}


</style>

<template>
  <div>
    <div id="apt-infos-wrapper">
      <div id="apt-info-name">{{ apt.name }}</div>
      <div id="apt-info-addr">{{ apt.addr }}</div>
      <div id="apt-info-roadaddr">{{ apt.roadAddr }}</div>
      <br />
      <div v-if="aptDetail">
        <div class="aptDetailLabel">
          <div class="apt-type-box" id="apt-heat-type">{{ aptDetail.heat_type }}</div>
          <div class="apt-type-box" id="apt-hall-type">{{ aptDetail.hall_type }}</div>
        </div>
        <div class="aptDetailTable">
          <div class="apt-detail-content"><div class="head">준공</div><div class="content">{{ `${aptDetail.accepted_date.substring(0,4)}년 ${aptDetail.accepted_date.substring(4,6)}월 ${aptDetail.accepted_date.substring(6)}일` }}</div></div>
          <div class="apt-detail-content"><div class="head">건설사</div><div class="content">{{ aptDetail.builder }}</div></div>
          <div class="apt-detail-content"><div class="head">세대수</div><div class="content">{{ aptDetail.num_house }}세대</div></div>
          <div class="apt-detail-content"><div class="head">주차대수</div><div class="content">{{ aptDetail.num_parking_total }}대(지상{{ aptDetail.num_parking_floor }}대, 지하{{ aptDetail.num_parking_basement }}대)</div></div>
          <div class="apt-detail-content"><div class="head">세대수</div><div class="content">{{ aptDetail.num_house }}세대</div></div>
          <div class="apt-detail-content"><div class="head">편의시설</div><div class="content">{{ aptDetail.amenities }}</div></div>
        </div>
        <div style="">

        </div>
      </div>
      <div v-else>아파트 상세 정보를 찾을 수 없습니다.</div>
    </div>

    <apt-detail-map :aptX="apt.x" :aptY="apt.y"></apt-detail-map>

    <div id="apt-chart-wrapper">
      <div class="chart-square" style="display: inline-block">
        <canvas id="mchart-square"></canvas>
      </div>
      <div class="chart-timeline" style="display: inline-block">
        <canvas id="mchart-timeline"></canvas>
      </div>
    </div>

    <div id="apt-deals-wrapper" class="deals">
      <table>
        <thead>
          <th>거래일자</th>
          <th>가격</th>
          <th>층</th>
          <th>면적</th>
        </thead>
        <tbody>
          <tr
            class="deal-row"
            v-for="deal in apt.deals"
            @mouseenter="rowMouseEnter($event)"
            @mouseleave="rowMouseLeave($event)"
          >
            <td class="deal-intersection">
              {{ deal.deal_year_month.substring(0, 4) }}년
              {{ parseInt(deal.deal_year_month.substring(4, 6)) }}월
              {{ deal.deal_day }}일
            </td>
            <td class="deal-intersection deal-intersection-price">
              {{ (deal.price * 10000).toLocaleString("ko-KR") }}원
            </td>
            <td class="deal-intersection">{{ deal.floor }}층</td>
            <td class="deal-intersection">{{ deal.square }}㎡</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import KakaoMap from "@/components/map/KakaoMap";
export default {
  components: {KakaoMap}
}
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/date-fns/1.30.1/date_fns.min.js"></script>
<script>
import AptDetailMap from "@/components/map/AptDetailMap";
import axios from "axios";

export default {
  components: { AptDetailMap },
  data() {
    return {
      apt: this.$route.query.apt,
      dataDeal: {},
      chartSquareLabels: [],
      chartSquareCalcs: [],
      chartTimelineItems: [],
      aptDetail: null,
    };
  },
  created() {},
  mounted() {
    const doact = () => {
      this.initData(this.apt);

      /* CHART-SQUARE */
      const data1 = {
        labels: this.chartSquareLabels,
        datasets: [
          {
            label: "제곱미터당 시세",
            backgroundColor: "rgb(255,95,0)",
            borderColor: "rgb(255,95,0)",
            data: this.chartSquareCalcs,
          },
        ],
      };
      const config1 = {
        type: "bar",
        data: data1,
        options: {},
      };
      const myChart1 = new Chart(
        document.getElementById("mchart-square"),
        config1
      );

      /* CHART-TIMELINE */
      const data2 = {
        // labels: ['2020-01-01','2020-06-01','2021-01-01','2021-06-01','2022-01-01','2022-06-01','2023-01-01'],
        datasets: [
          {
            label: "실거래가",
            //labels: ["2021-01-01", "2022-01-01"],
            data: this.chartTimelineItems,
            backgroundColor: "rgb(255,99,132)",
          },
        ],
      };
      const config2 = {
        type: "scatter",
        data: data2,
        options: {
          scales: {
            x: {
              type: "time",
              time: {
                unit: "month",
                displayFormats: {
                  month: "yy년 M월",
                },
                stepSize: 6,
              },
              ticks: {
                source: "auto",
                autoSkip: true,
                minRotation: 45,
                maxRotation: 45,
              },
              tick: {
                backdropPadding: 5,
              },
            },
            y: {
              ticks: {
                callback: function (value, index, ticks) {
                  return value + "억";
                },
              },
            },
          },
        },
        responsive: true,
        plugins: {
          legend: {
            position: "top",
          },
          title: {
            display: true,
            text: "실거래가",
          },
        },
      };

      const myChart2 = new Chart(
        document.getElementById("mchart-timeline"),
        config2
      );
    };

    let script1 = document.createElement("script");
    script1.setAttribute(
      "src",
      "https://cdn.jsdelivr.net/npm/chart.js@3.2.1/dist/chart.min.js"
    );
    document.head.appendChild(script1);
    script1.onload = () => {
      let script2 = document.createElement("script");
      script2.setAttribute(
        "src",
        "https://cdn.jsdelivr.net/npm/chartjs-adapter-date-fns/dist/chartjs-adapter-date-fns.bundle.min.js"
      );
      document.head.appendChild(script2);
      script2.onload = () => {
        let script3 = document.createElement("script");
        script3.setAttribute(
          "src",
          "https://cdnjs.cloudflare.com/ajax/libs/date-fns/1.30.1/date_fns.min.js"
        );
        document.head.appendChild(script3);
        script3.onload = doact;
      };
    };

    /* color table */
    const dealIntersectionPriceEls = document.getElementsByClassName(
      "deal-intersection-price"
    );
    for (let i = 0; i < dealIntersectionPriceEls.length; i++) {
      const el = dealIntersectionPriceEls[i];
      const price = parseInt(
        el.innerHTML.substring(0, el.innerHTML.length - 1).replaceAll(",", "")
      );
      this.toggleTableRow(el.parentElement, price);
    }

    axios
      .get("http://localhost:8090/complex/where", {
        params: {
          addr_lv1: this.apt.addrLv1,
          addr_lv2: this.apt.addrLv2,
          addr_lv3: this.apt.addrLv3,
          bunji_main: this.apt.bunjiMain,
          bunji_sub: this.apt.bunjiSub,
        },
      })
      .then((res) => {
        this.aptDetail = res.data;
      });
  },
  methods: {
    initData(data) {
      for (let i in data.deals) {
        const deal = data.deals[i];

        /* CHART-SQUARE */
        if (!this.dataDeal.hasOwnProperty(deal.square)) {
          this.dataDeal[deal.square] = { cnt: 0, sum: 0 };
        }
        this.dataDeal[deal.square].cnt++;
        this.dataDeal[deal.square].sum += deal.price * 10000;

        /* CHART-TIMELINE */
        const year = deal.deal_year_month.substring(0, 4);
        const month = deal.deal_year_month.substring(4, 6);
        const day = deal.deal_day.padStart(2, "0");
        const dateString = `${year}-${month}-${day}`;

        const price = parseInt(deal.price) / 10000;

        this.chartTimelineItems.push({
          x: dateString,
          y: price,
        });
      }

      const entries = Object.entries(this.dataDeal);
      for (let i in entries) {
        const entry = entries[i];
        this.chartSquareLabels.push(entry[0]);
        this.chartSquareCalcs.push(entry[1].sum / entry[1].cnt / entry[0]);
      }
    },
    rowMouseEnter(event) {
      const priceElem = event.currentTarget.getElementsByClassName(
        "deal-intersection-price"
      )[0];
      const price = parseInt(
        priceElem.innerHTML
          .substring(0, priceElem.innerHTML.length - 1)
          .replaceAll(",", "")
      );
      this.toggleTableRow(event.currentTarget, price);
      event.currentTarget.classList.add("row-hover");
    },
    rowMouseLeave(event) {
      event.currentTarget.classList.remove("row-hover");
      const priceElem = event.currentTarget.getElementsByClassName(
        "deal-intersection-price"
      )[0];
      const price = parseInt(
        priceElem.innerHTML
          .substring(0, priceElem.innerHTML.length - 1)
          .replaceAll(",", "")
      );
      this.toggleTableRow(event.currentTarget, price);
    },
    toggleTableRow(el, price) {
      if (price <= 300000000) {
        el.classList.toggle("lv1");
      } else if (price <= 600000000) {
        el.classList.toggle("lv2");
      } else if (price <= 1000000000) {
        el.classList.toggle("lv3");
      } else if (price <= 1500000000) {
        el.classList.toggle("lv4");
      } else if (price <= 2000000000) {
        el.classList.toggle("lv5");
      } else if (price <= 3000000000) {
        el.classList.toggle("lv6");
      } else {
        el.classList.toggle("lv7");
      }
    },
  },
};
</script>

<style>
@import url('https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/variable/pretendardvariable.css');

* {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
    "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
    "Malgun Gothic", sans-serif;
}

#apt-infos-wrapper {
  padding: 15px 15px 15px 15px;
}

.deal-row {
  padding: 5px 0px 5px 0px;
}

.deal-intersection {
  padding: 7px 0px 7px 0px;
  margin: 0px;
}

.row-hover {
  background-color: lightgray;
}

#apt-deals-wrapper {
  width: 100%;
  padding: 0px 10% 0px 10%;
}

#apt-deals-wrapper table {
  width: 100%;
}

#apt-deals-wrapper th {
  text-align: center;
  padding: 10px 0px 10px 0px;
  background-color: #cb8282;
  color: white;
}

#apt-deals-wrapper td {
  text-align: center;
}

#apt-chart-wrapper {
  height: 300px;
}

#mchart-square {
  height: 300px;
}

#mchart-timeline {
  height: 300px;
  margin-left: 100px;
}

.lv1 {
  background: linear-gradient(
    to right,
    rgba(193, 249, 247, 0.86),
    rgba(255, 255, 255, 0)
  );
}

.lv2 {
  background: linear-gradient(
    to right,
    rgba(232, 247, 205, 0.8),
    rgba(255, 255, 255, 0)
  );
}

.lv3 {
  background: linear-gradient(
    to right,
    rgba(255, 223, 171, 0.86),
    rgba(255, 255, 255, 0)
  );
}

.lv4 {
  background: linear-gradient(
    to right,
    rgba(255, 173, 120, 0.44),
    rgba(255, 255, 255, 0)
  );
}

.lv5 {
  background: linear-gradient(
    to right,
    rgba(255, 203, 203, 0.59),
    rgba(255, 255, 255, 0)
  );
}

.lv6 {
  background: linear-gradient(
    to right,
    rgba(234, 193, 255, 0.8),
    rgba(255, 255, 255, 0)
  );
}

.lv7 {
  background: linear-gradient(
    to right,
    rgb(219, 156, 255),
    rgba(255, 255, 255, 0)
  );
}

#apt-info-name {
  font-size: 40px;
  font-weight: 800;
}

#apt-info-addr {
  font-size: 22px;
  font-weight: 600;
  color: gray;
}

#apt-info-roadaddr {
  font-size: 22px;
  font-weight: 600;
  color: gray;
}

.apt-type-box {
  text-align: center;
  color: white;
  width: 60px;
  border-radius: 3px;
  margin: 0px 5px 0px 0px;
  background-color: green;
  display: inline-block;
}

.aptDetailTable .head {
  min-width: 100px;
}

.aptDetailTable .head,.content {
  display: inline-block;
}

.aptDetailLabel {
  margin-bottom: 10px;
}

</style>

<template>
  <div>
    <div
      class="deals"
      style="min-height: 300px; display: inline-block; width: 400px;"
    >
      <div>{{ apt.name }}</div>
      <div>{{ apt.addr }}</div>
      <div>{{ apt.roadAddr }}</div>
      <div v-for="deal in apt.deals">
        <div style="border: solid; border-width: 1px; margin: 5px;">
          <div>거래일자 {{ deal.deal_year_month }}{{ deal.deal_day }}</div>
          <div>층 {{ deal.floor }}</div>
          <div>가격 {{ deal.price }}</div>
          <div>면적 {{ deal.square }}</div>
        </div>
      </div>
    </div>

    <div style="display: inline-block; width:400px;">
      <div class="chart-square">
        <canvas id="mchart-square"></canvas>
      </div>
      <div class="chart-timeline">
        <canvas id="mchart-timeline"></canvas>
      </div>
    </div>
  </div>
</template>
<script src="https://cdn.jsdelivr.net/npm/chart.js@3.2.1/dist/chart.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-adapter-date-fns/dist/chartjs-adapter-date-fns.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/date-fns/1.30.1/date_fns.min.js"></script>
<script>
export default {
  data() {
    return {
      apt: this.$route.query.apt,
      dataDeal: {},
      chartSquareLabels: [],
      chartSquareCalcs: [],
      chartTimelineItems: [],
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
            backgroundColor: "rgb(255, 99, 132)",
            borderColor: "rgb(255, 99, 132)",
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
              type: 'time',
              time: {
                unit: 'month',
                displayFormats: {
                  'month': 'yy년 M월',
                },
                stepSize: 6,
              },
              ticks: {
                source: 'auto',
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
                callback: function(value, index, ticks) {
                  return value+'억';
                }
              }
            }
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
  },
  methods: {
    initData(data) {
      for (let i in data.deals) {
        const deal = data.deals[i];
        console.log(deal);

        /* CHART-SQUARE */
        if (!this.dataDeal.hasOwnProperty(deal.square)) {
          this.dataDeal[deal.square] = { cnt: 0, sum: 0 };
        }
        this.dataDeal[deal.square].cnt++;
        this.dataDeal[deal.square].sum += deal.price * 10000;

        /* CHART-TIMELINE */
        const year = deal.deal_year_month.substring(0,4);
        const month = deal.deal_year_month.substring(4,6);
        const day = deal.deal_day.padStart(2, '0');
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
  },
};
</script>

<style></style>

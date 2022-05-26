<template>
  <div class="bmk-root">
    <div class="bmk-my-info-wrapper">
      <div class="bmk-my-info-id">{{ currentUser.id }}님의 관심지역</div>
      <div class="bmk-my-info-addr">{{ currentUser.bmk_addr_lv1 }} {{ currentUser.bmk_addr_lv2 }} {{ currentUser.bmk_addr_lv3 }}</div>
    </div>
    <div style="height: 3vh"></div>
    <div class="bmk-news-items-wrapper">
      <div v-for="newsItem in newsItems">
        <div class="bmk-news-item-wrapper">
          <div class="bmk-ntitle" v-html="newsItem.title"></div>
          <div class="bmk-ndesc" v-html="newsItem.description"></div>
          <div class="bmk-nd">{{ newsItem.pubDate | formatDate }}</div>
          <div class="bmk-ltn" @click="openlink($event)">
            <div>네이버에서 보기</div>
            <div class="lsrc" style="display: none">{{ newsItem.link }}</div>
          </div>
          <div class="bmk-lto" @click="openlink($event)">
            <div>언론사에서 보기</div>
            <div class="lsrc" style="display: none">{{ newsItem.originallink }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapState, mapActions, mapMutations } from "vuex";
import moment from "moment";
const userStore = "userStore";

export default {
  data() {
    return {
      newsItems: null,
      newsPage: 1,
    };
  },
  name: "BookmarkHome",
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo", "currentUser"]),
  },
  mounted() {
    this.loadNews();
  },
  methods: {
    loadNews() {
      axios
        .get("http://localhost:8090/news", {
          params: {
            keyword: this.currentUser.bmk_addr_lv3,
            page: this.newsPage,
            itemPerPage: 30,
          },
          headers: {
            Authorization: `Bearer ` + sessionStorage.getItem("token"),
          },
        })
        .then((res) => {
          this.newsItems = res.data.items;
        });
    },
    openlink(event) {
      const link =
        event.currentTarget.getElementsByClassName("lsrc")[0].innerHTML;
      window.open(link);
    },
  },
  filters: {
    formatDate(regtime) {
      let date = new Date(Date.parse(regtime) - 1000 * 60 * 60 * 9);
      return moment(date).format("YYYY. M. D ");
    },
  },
};
</script>

<style scoped>
.bmk-root {
  padding: 3%;
}

.bmk-my-info-wrapper {
}

.bmk-my-info-id {
  font-size: 130%;
  font-weight: 500;
}

.bmk-my-info-addr {
  font-size: 160%;
  font-weight: 800;
}

.bmk-news-items-wrapper {
}

.bmk-news-item-wrapper {
  margin-bottom: 30px;
}

.bmk-ntitle {
  font-size: 300%;
  font-weight: 700;
  width: 50%;
  line-height: 100%;
}

.bmk-ndesc {
  font-size: 130%;
  font-weight: 500;
  width: 30%;
}

.bmk-nd {
  color: gray;
}

.bmk-ltn {
  color: gray;
}

.bmk-lto {
  color: gray;
}
</style>

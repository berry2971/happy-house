<template>
  <div>
    <div>
    <div>
      <div class="bmk-my-info-id">{{ currentUser.id }}님의 관심지역</div>
      <div class="bmk-my-info-addr">{{ currentUser.bmk_addr_lv1 }} {{ currentUser.bmk_addr_lv2 }} {{ currentUser.bmk_addr_lv3 }}</div>
    </div>
    <div>
      <div v-for="newsItem in newsItems">
        <div>{{newsItem.title}}</div>
        <div>{{newsItem.description}}</div>
        <div>{{newsItem.pubDate}}</div>
        <div>{{newsItem.link}}</div>
        <div>{{newsItem.originallink}}</div>
      </div>
    </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapState, mapActions, mapMutations } from "vuex";
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
  },
};
</script>

<style scoped>
.bmk-my-info-id {

}

.bmk-my-info-addr {

}

</style>

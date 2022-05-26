<template>
  <div class="regist">
    <div style="text-align: right" v:if="currentUser.role=='ROLE_ADMIN'">
      <b-button class="board-btn" @click="moveWrite">글 등록</b-button>
    </div>
    <div v-if="notices.length">
      <table id="board-list">
        <colgroup>
          <col style="width: 15%" />
          <col style="width: 35%" />
          <col style="width: 25%" />
          <col style="width: 30%" />
        </colgroup>
        <thead>
          <tr>
            <th>구분</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
          </tr>
        </thead>
        <tbody>
          <notice-list-item
            v-for="notice in notices"
            :key="notice.id"
            :notice="notice"
          ></notice-list-item>
        </tbody>
      </table>
    </div>
    <div v-else class="text-center"></div>
  </div>
</template>
<script>
import http from "@/api/http";
import NoticeListItem from "@/components/notice/NoticeListItem.vue";
import { mapState, mapMutations } from "vuex";
const userStore = "userStore";

export default {
  name: "NoticeList",
  components: {
    NoticeListItem,
  },
  data() {
    return {
      notices: [],
    };
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo", "currentUser"]),
  },
  created() {
    http
      .get("/articles?boardName=notice&page=1", {
        headers: {
          Authorization: `Bearer ` + sessionStorage.getItem("token"),
        },
      })
      .then(({ data }) => {
        this.notices = data;
      });
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "noticeWrite" });
    },
  },
};
</script>

<style></style>

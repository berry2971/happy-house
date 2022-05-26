<template>
  <div>
    <div class="regist">
      <div class="regist_form">
        <div class="board-a">
          <span class="board-group" v-if="article.author == currentUser.id">
            <a @click="boardDelete()">삭제</a>
            <a @click="moveModify(article.id)">수정</a>
          </span>
          <a @click="moveList()">목록</a>
        </div>
        <span class="subject-span">{{ article.subject }}</span>
        <span class="title-span">{{ article.title }}</span>
        <br />
        <span class="author-span">{{ article.author }}</span>
        |
        <span class="write_time-span">{{ article.write_time | formatDate }}</span>
        <hr />
        <span class="content-span" v-html="article.content"></span>
      </div>
    </div>
    <br />
    <comment-list></comment-list>
  </div>
</template>

<script>
import http from "@/api/http";
import moment from "moment";
import CommentList from "@/components/board/comments/CommentList.vue";
import { mapState, mapMutations } from "vuex";
const userStore = "userStore";

export default {
  name: "BoardDetail",
  components: {
    CommentList,
  },

  computed: {
    ...mapState(userStore, ["isLogin", "userInfo", "currentUser"]),
  },
  data: function () {
    return {
      article: {},
      article_id: this.$route.params.id,
    };
  },
  created() {
    http
      .get(`/articles/${this.$route.params.id}`, {
        headers: {
          Authorization: `Bearer ` + sessionStorage.getItem("token"),
        },
      })
      .then(({ data }) => {
        this.article = data;
      });
  },
  methods: {
    boardDelete() {
      http
        .delete(`/articles/${this.$route.params.id}`, {
          headers: {
            Authorization: `Bearer ` + sessionStorage.getItem("token"),
          },
        })
        .then(({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data != null) {
            msg = "삭제가 완료되었습니다.";
          }
          alert(msg);
          this.$router.push({ name: "list" });
        });
    },
    moveModify(id) {
      this.$router.push({ name: "modify", params: { id: id } });
    },
    moveList() {
      this.$router.push({ name: "list" });
    },
  },
  filters: {
    formatDate(regtime) {
      let date = new Date(Date.parse(regtime) - 1000 * 60 * 60 * 9);
      return moment(date).format("YYYY년 MM월 DD일");
    },
  },
};
</script>

<style></style>

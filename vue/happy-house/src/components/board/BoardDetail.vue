<template>
  <div>
    <div class="regist">
      <div class="regist_form">
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
    <div class="board-Btn">
      <b-button variant="outline-secondary" class="btn" @click="boardDelete()"> 삭제 </b-button>
      <b-button variant="outline-secondary" class="btn" @click="moveModify(article.id)">
        수정
      </b-button>
      <b-button variant="outline-secondary" class="btn" @click="moveList()">목록</b-button>
    </div>
    <br />
    <hr style="clear: both" />
    <div>{{ article_id }}</div>
    <comment-list></comment-list>
    <comment-write></comment-write>
  </div>
</template>

<script>
import http from "@/api/http";
import moment from "moment";
import CommentWrite from "./comments/CommentWrite.vue";
import CommentList from "./comments/CommentList.vue";
export default {
  name: "BoardDetail",
  components: {
    CommentList,
    CommentWrite,
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
          if (data != "null") {
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
      return moment(new Date(regtime)).add(-1, "day").format("YYYY.MM.DD");
    },
  },
};
</script>

<style></style>

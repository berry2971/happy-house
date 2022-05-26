<template>
  <div>
    <b-card class="regist">
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
      <div class="board-Btn">
        <b-button variant="outline-secondary" class="btn" @click="boardDelete()"> 삭제 </b-button>
        <b-button variant="outline-secondary" class="btn" @click="moveModify(article.id)">
          수정
        </b-button>
        <b-button variant="outline-secondary" class="btn" @click="moveList()">목록</b-button>
      </div>
    </b-card>
    <br />
    <comment-list></comment-list>
    <hr />
    <comment-write></comment-write>
  </div>
</template>

<script>
import http from "@/api/http";
import moment from "moment";
import CommentWrite from "@/components/board/comments/CommentWrite.vue";
import CommentList from "@/components/board/comments/CommentList.vue";
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
      let date = new Date(Date.parse(regtime) - 1000 * 60 * 60 * 9);
      return moment(date).format("YYYY.MM.DD HH:mm:ss");
    },
  },
};
</script>

<style></style>

<template>
  <div class="regist">
    <h1>QnA 상세보기</h1>
    <div class="regist_form">
      <label for="title">제목</label>
      <div class="view">{{ article.title }}</div>
      <label for="author">작성자</label>
      <div class="view">{{ article.author }}</div>
      <label for="write_time">작성일</label>
      <div class="view">{{ article.write_time | formatDate }}</div>
      <label for="content">내용</label>
      <div class="view" v-html="article.content"></div>
      <div style="padding-top: 15px">
        <button class="btn" @click="checkAuthor(article.author, 0)">
          삭제
        </button>
        <button class="btn" @click="checkAuthor(article.author, article.id)">
          수정
        </button>
        <button class="btn" @click="moveList()">목록</button>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/api/http";
import moment from "moment";

export default {
  name: "BoardDetail",
  data: function () {
    return {
      article: {},
    };
  },
  created() {
    http.get(`/articles/${this.$route.params.id}`).then(({ data }) => {
      this.article = data;
    });
  },
  methods: {
    checkAuthor(author, id) {
      console.log(author);
      let inputAuthor = prompt("작성자의 이름을 입력해주세요.");
      if (author == inputAuthor) {
        if (id == 0) {
          this.boardDelete();
        } else this.moveModify(id);
      } else alert("잘못된 입력입니다.");
    },
    boardDelete() {
      http.delete(`/articles/${this.$route.params.id}`).then(({ data }) => {
        let msg = "삭제 처리시 문제가 발생했습니다.";
        if (data != "null") {
          msg = "삭제가 완료되었습니다.";
        }
        alert(msg);
        // 현재 route를 /list로 변경.
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
      return moment(new Date(regtime)).format("YYYY.MM.DD");
    },
  },
};
</script>

<style></style>

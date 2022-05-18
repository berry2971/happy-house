<template>
  <div class="regist">
    <h1>QnA 등록</h1>
    <div class="regist_form">
      <label for="author">작성자</label>
      <input
        type="text"
        id="author"
        name="author"
        v-model="author"
        ref="author"
      /><br />
      <label for="password">비밀번호</label>
      <input
        type="password"
        id="password"
        name="password"
        v-model="password"
        ref="password"
      /><br />
      <label for="title">제목</label>
      <input
        type="text"
        id="title"
        name="title"
        v-model="title"
        ref="title"
      /><br />
      <label for="content">내용</label><br />
      <textarea
        id="content"
        name="content"
        v-model="content"
        ref="content"
        cols="35"
        rows="5"
      ></textarea
      ><br />
      <button @click="checkValue">등록</button>
      <button @click="moveList">목록</button>
    </div>
  </div>
</template>

<script>
import http from "@/api/http";

export default {
  name: "BoardWrite",
  data() {
    return {
      author: "",
      password: "",
      title: "",
      content: "",
      board_name: "qna",
    };
  },
  methods: {
    // 입력값 체크하기 - 체크가 성공하면 registBook 호출
    checkValue() {
      // 사용자 입력값 체크하기
      // isbn, 제목, 저자, 가격, 설명이 없을 경우 각 항목에 맞는 메세지를 출력
      let err = true;
      let msg = "";
      !this.author &&
        ((msg = "작성자 입력해주세요"),
        (err = false),
        this.$refs.author.focus());
      err &&
        !this.password &&
        ((msg = "비밀번호를 입력해주세요"),
        (err = false),
        this.$refs.password.focus());
      err &&
        !this.title &&
        ((msg = "제목 입력해주세요"), (err = false), this.$refs.title.focus());
      err &&
        !this.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) alert(msg);
      // 만약, 내용이 다 입력되어 있다면 registBook 호출
      else this.registArticle();
    },

    registArticle() {
      http
        .post("/articles/", {
          author: this.author,
          password: this.password,
          title: this.title,
          content: this.content,
          board_name: "qna",
        })
        .then(({ data }) => {
          // 서버에서 정상적인 값이 넘어 왔을경우 실행.
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data != null) {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });
    },
    moveList() {
      this.$router.push({ name: "list" });
    },
  },
};
</script>

<style></style>

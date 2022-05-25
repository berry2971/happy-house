<template>
  <div class="regist">
    <b-form class="regist_form" @submit.stop.prevent>
      <b-form-input
        readonly
        v-model="subject"
        class="mb-3 regist-select"
        style="width: auto; margin: 10px"
        ref="subject"
      >
      </b-form-input
      ><br />
      <b-form-input
        type="text"
        class="title"
        name="title"
        v-model="title"
        ref="title"
        style="border: none"
      />
      <hr />
      <b-form-textarea
        class="content"
        name="content"
        v-model="content"
        ref="content"
        rows="20"
        max-rows="100"
        style="border: none"
      />
      <b-button @click="checkValue">등록</b-button>
      <b-button @click="moveList">목록</b-button>
    </b-form>
  </div>
</template>

<script>
import http from "@/api/http";

export default {
  name: "BoardModify",
  data() {
    return {
      author: "",
      title: "",
      content: "",
      board_name: "community",
      subject: "null",
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
        this.id = data.id;
        this.author = data.author;
        this.subject = data.subject;
        this.title = data.title;
        this.content = data.content;
      });
  },
  methods: {
    // 입력값 체크하기 - 체크가 성공하면 registBook 호출
    checkValue() {
      // 사용자 입력값 체크하기
      // isbn, 제목, 저자, 가격, 설명이 없을 경우 각 항목에 맞는 메세지를 출력
      let err = true;
      let msg = "";
      !this.author && ((msg = "작성자를 입력해주세요"), (err = false), this.$refs.author.focus());
      err &&
        !this.subject &&
        ((msg = "지역을 선택해주세요"), (err = false), this.$refs.subject.focus());
      err &&
        !this.title &&
        ((msg = "제목을 입력해주세요"), (err = false), this.$refs.title.focus());
      err &&
        !this.content &&
        ((msg = "내용을 입력해주세요"), (err = false), this.$refs.content.focus());

      if (!err) alert(msg);
      // 만약, 내용이 다 입력되어 있다면 registBook 호출
      else this.modifyBook();
    },

    modifyBook() {
      http
        .put(
          `/articles/${this.$route.params.id}`,

          {
            id: this.id,
            author: this.author,
            subject: this.subject,
            title: this.title,
            content: this.content,
            board_name: "community",
          },
          {
            headers: {
              Authorization: `Bearer ` + sessionStorage.getItem("token"),
            },
          }
        )
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data != null) {
            msg = "수정이 완료되었습니다.";
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

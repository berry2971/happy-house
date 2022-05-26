<template>
  <div class="regist">
    <b-form class="regist-form" @submit.stop.prevent>
      <b-form-input hidden v-model="author" name="author" />
      <b-form-select
        v-model="subject"
        :options="options"
        class="mb-3 regist-select"
        style="width: auto; margin: 10px"
      >
        <template #first>
          <b-form-select-option value="null" disabled>공지종류</b-form-select-option>
        </template> </b-form-select
      ><br />
      <b-form-input
        type="text"
        class="title"
        name="title"
        v-model="title"
        placeholder="제목"
        style="border: none"
      />
      <hr />
      <b-form-textarea
        class="content"
        name="content"
        v-model="content"
        placeholder="공지 내용 작성"
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
  name: "NoticeWrite",
  data() {
    return {
      author: "",
      title: "",
      content: "",
      board_name: "notice",
      subject: "null",
      options: [
        { value: "안내", text: "안내" },
        { value: "해결완료", text: "해결완료" },
        { value: "긴급", text: "긴급" },
        { value: "점검", text: "점검" },
        { value: "이벤트", text: "이벤트" },
        { value: "발표", text: "발표" },
      ],
    };
  },
  created() {
    if (this.$store.getters.isLogin != null) {
      http
        .get("/users/login/current", {
          headers: {
            Authorization: `Bearer ` + sessionStorage.getItem("token"),
          },
        })
        .then(({ data }) => {
          this.author = data.id;
        });
    }
  },
  methods: {
    checkValue() {
      // 사용자 입력값 체크하기
      // isbn, 제목, 저자, 가격, 설명이 없을 경우 각 항목에 맞는 메세지를 출력
      let err = true;
      let msg = "";
      err &&
        !this.subject &&
        this.subject == null &&
        ((msg = "공지종류를 선택해주세요"), (err = false), this.$refs.subject.focus());
      err && !this.title && ((msg = "제목 입력해주세요"), (err = false), this.$refs.title.focus());
      err &&
        !this.content &&
        ((msg = "내용 입력해주세요"), (err = false), this.$refs.content.focus());
      if (!err) alert(msg);
      // 만약, 내용이 다 입력되어 있다면 registBook 호출
      else this.registArticle();
    },

    registArticle() {
      http
        .post(
          "/articles",
          {
            author: this.author,
            subject: this.subject,
            title: this.title,
            content: this.content,
            board_name: "notice",
          },
          {
            headers: {
              Authorization: `Bearer ` + sessionStorage.getItem("token"),
            },
          }
        )
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
      this.$router.push({ name: "noticeList" });
    },
    findAuthor() {},
  },
};
</script>

<style></style>

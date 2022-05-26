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
          <b-form-select-option value="null" disabled>지역선택</b-form-select-option>
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
        placeholder="글 내용 작성"
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
  name: "BoardWrite",
  data() {
    return {
      author: "",
      title: "",
      content: "",
      board_name: "community",
      subject: "null",
      options: [
        { value: "서울", text: "서울특별시" },
        { value: "경기", text: "경기도" },
        { value: "부산", text: "부산광역시" },
        { value: "인천", text: "인천광역시" },
        { value: "대구", text: "대구광역시" },
        { value: "광주", text: "광주광역시" },
        { value: "대전", text: "대전광역시" },
        { value: "울산", text: "울산광역시" },
        { value: "세종", text: "세종특별자치시" },
        { value: "강원", text: "강원도" },
        { value: "충북", text: "충청북도" },
        { value: "충남", text: "충청남도" },
        { value: "전북", text: "전라북도" },
        { value: "전남", text: "전라남도" },
        { value: "경북", text: "경상북도" },
        { value: "경남", text: "경상남도" },
        { value: "제주", text: "제주특별자치도" },
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
        ((msg = "지역을 선택해주세요"), (err = false), this.$refs.subject.focus());
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
            board_name: "community",
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
      this.$router.push({ name: "list" });
    },
    findAuthor() {},
  },
};
</script>

<style></style>

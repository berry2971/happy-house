<template>
  <div>
    <b-textarea placeholder="댓글을 입력해주세요." v-model="content"> </b-textarea>
    <b-button variant="outline-secondary" @click="commentWrite()">댓글 작성</b-button>
  </div>
</template>
<script>
import http from "@/api/http";

export default {
  name: "CommentWrite",
  data() {
    return {
      content: "",
      article_id: "",
    };
  },
  methods: {
    commentWrite() {
      console.log(this.content);
      console.log(this.$parent.article_id);
      http
        .post(
          "/comments",
          {
            content: this.content,
            article_id: this.$parent.article_id,
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
        });
    },
  },
};
</script>

<template>
  <div>
    <b-textarea v-model="content"> </b-textarea>
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
  props: {
    articleNum: String,
  },
  methods: {
    commentWrite() {
      http
        .post(
          "/comments",
          {
            content: this.content,
            article_id: this.$route.params.id,
          },
          {
            headers: {
              Authorization: `Bearer ` + sessionStorage.getItem("token"),
            },
          }
        )
        .then(({ data }) => {
          let msg = "댓글 등록 처리시 문제가 발생했습니다.";
          if (data != null) {
            msg = "댓글 등록이 완료되었습니다.";
            this.$emit("show-comments");
          }
          this.content = "";
          alert(msg);
        });
    },
  },
};
</script>

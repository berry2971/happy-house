<template>
  <div>
    <div v-if="comments.length">
      <h5>댓글 {{ comments.length }}</h5>
      <hr class="comments-hr" />
      <comment-list-item v-for="comment in comments" :key="comment.id" :comment="comment">
      </comment-list-item>
    </div>
    <div v-else class="text-center">
      <p style="font-size: 35px">댓글을 작성해주세요. &#128075;</p>
    </div>
    <comment-write v-on:show-comments="printComments"></comment-write>
  </div>
</template>
<script>
import http from "@/api/http";
import CommentListItem from "./CommentListItem.vue";
import CommentWrite from "./CommentWrite.vue";

export default {
  name: "CommentList",
  components: {
    CommentListItem,
    CommentWrite,
  },
  data() {
    return {
      comments: [],
      articleNum: this.$parent.article_id,
    };
  },

  created() {
    let url = "/comments?articleId=" + this.$parent.article_id + "&page=1";
    console.log(url);
    http
      .get(url, {
        headers: {
          Authorization: `Bearer ` + sessionStorage.getItem("token"),
        },
      })
      .then(({ data }) => {
        this.comments = data;
      });
  },
  methods: {
    printComments: function () {
      let url = "/comments?articleId=" + this.$parent.article_id + "&page=1";
      console.log(url);
      http
        .get(url, {
          headers: {
            Authorization: `Bearer ` + sessionStorage.getItem("token"),
          },
        })
        .then(({ data }) => {
          this.comments = data;
        });
    },
  },
};
</script>

<style></style>

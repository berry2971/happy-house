<template>
  <div>
    <div v-if="comments.length">
      <comment-list-item
        v-for="comment in comments"
        :key="comment.id"
        :comment="comment"
      ></comment-list-item>
    </div>
    <div v-else class="text-center"></div>
  </div>
</template>
<script>
import http from "@/api/http";
import CommentListItem from "./CommentListItem.vue";

export default {
  name: "CommentList",
  components: {
    CommentListItem,
  },
  data() {
    return {
      comments: [],
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
        console.log("load comments");
      });
  },
};
</script>

<style></style>

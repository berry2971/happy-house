<template>
  <div>
    <div class="regist_form">
      <span class="author-span"></span>
      |
      <span class="write_time-span">{{ comment.write_time | formatDate }}</span>
      <hr />
      <span class="content-span" v-html="comment.content"></span>
    </div>
    <hr />
  </div>
</template>

<script>
import moment from "moment";

export default {
  name: "CommentListItem",
  props: {
    comment: Object,
  },
  filters: {
    formatDate(regtime) {
      return moment(new Date(regtime)).format("YYYY.MM.DD");
    },
  },
};
</script>

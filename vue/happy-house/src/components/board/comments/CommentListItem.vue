<template>
  <div>
    <div class="regist_form">
      <span class="author-span">{{ comment.author }}</span>
      |
      <span class="write_time-span">{{ comment.write_time | formatDate }}</span>
      <span v-if="comment.author == currentUser.id" class="comment-span">
        <a class="comment-a" @click="deleteComment(comment.id)">삭제</a>
      </span>
      <span class="content-span" v-html="comment.content"></span>
    </div>
    <hr style="clear: both" />
  </div>
</template>

<script>
import http from "@/api/http";
import moment from "moment";
import { mapState, mapMutations } from "vuex";
const userStore = "userStore";

export default {
  name: "CommentListItem",
  props: {
    comment: Object,
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo", "currentUser"]),
  },
  filters: {
    formatDate(regtime) {
      let date = new Date(Date.parse(regtime) - 1000 * 60 * 60 * 9);
      return moment(date).format("YYYY.MM.DD HH:mm:ss");
    },
  },
  methods: {
    deleteComment(id) {
      let url = "/comments/" + id;
      console.log(id);
      http
        .delete(url, {
          headers: {
            Authorization: `Bearer ` + sessionStorage.getItem("token"),
          },
        })
        .then(({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data != "null") {
            msg = "삭제가 완료되었습니다.";
          }
          alert(msg);
        });
    },
  },
};
</script>

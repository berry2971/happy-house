<template>
  <div class="regist">
    <div class="regist_form">
      <span class="subject-span">{{ notice.subject }}</span>
      <span class="title-span">{{ notice.title }}</span>
      <br />
      <span class="author-span">{{ notice.author }}</span>
      |
      <span class="write_time-span">{{ notice.write_time | formatDate }}</span>
      <hr />
      <span class="content-span" v-html="notice.content"></span>
      <hr />
      <div style="padding-top: 15px">
        <b-button variant="outline-secondary" class="btn" @click="boardDelete()"> 삭제 </b-button>
        <b-button variant="outline-secondary" class="btn" @click="moveModify(notice.id)">
          수정
        </b-button>
        <b-button variant="outline-secondary" class="btn" @click="moveList()">목록</b-button>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/api/http";
import moment from "moment";

export default {
  name: "NoticeDetail",
  data: function () {
    return {
      notice: {},
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
        this.notice = data;
      });
  },
  methods: {
    boardDelete() {
      http
        .delete(`/articles/${this.$route.params.id}`, {
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
          this.$router.push({ name: "list" });
        });
    },
    moveModify(id) {
      this.$router.push({ name: "modify", params: { id: id } });
    },
    moveList() {
      this.$router.push({ name: "list" });
    },
  },
  filters: {
    formatDate(regtime) {
      return moment(new Date(regtime)).format("YYYY.MM.DD");
    },
  },
};
</script>

<style></style>

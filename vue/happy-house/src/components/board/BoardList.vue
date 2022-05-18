<template>
  <div class="regist">
    <div style="text-align: right">
      <button @click="moveWrite">글 등록</button>
    </div>
    <div v-if="articles.length">
      <table id="book-list">
        <colgroup>
          <col style="width: 45%" />
          <col style="width: 25%" />
          <col style="width: 25%" />
        </colgroup>
        <thead>
          <tr>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
          </tr>
        </thead>
        <tbody>
          <board-list-item
            v-for="article in articles"
            :key="article.id"
            :article="article"
          ></board-list-item>
        </tbody>
      </table>
    </div>
    <div v-else class="text-center">게시글이 없습니다.</div>
  </div>
</template>
<script>
import http from "@/api/http";
import BoardListItem from "@/components/board/BoardListItem.vue";

export default {
  name: "BoardList",
  components: {
    BoardListItem,
  },
  data() {
    return {
      articles: [],
    };
  },
  created() {
    http.get("/articles/?boardName=qna&page=1").then(({ data }) => {
      console.log(data);
      this.articles = data;
    });
  },
  methods: {
    moveWrite() {
      console.log("글쓰기 가자");
      this.$router.push({ name: "write" });
    },
  },
};
</script>

<style></style>

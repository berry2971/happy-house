<template>
  <tr>
    <td class="subject">{{ article.subject }}</td>
    <td>
      <!-- <router-link :to="`/board/detail?articleno=${article.articleno}`">{{
        article.subject
      }}</router-link> -->
      <router-link :to="{ name: 'detail', params: { id: article.id } }" class="board-title">{{
        article.title
      }}</router-link>
    </td>
    <td>{{ article.author }}</td>
    <td>{{ article.write_time | formatDate }}</td>
  </tr>
</template>

<script>
import moment from "moment";

export default {
  name: "BoardListItem",
  props: {
    article: Object,
  },
  filters: {
    formatDate(regtime) {
      return moment(new Date(regtime)).format("YYYY.MM.DD");
    },
  },
};
</script>

<style></style>

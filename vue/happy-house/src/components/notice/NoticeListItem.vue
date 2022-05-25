<template>
  <tr>
    <td class="subject">{{ notice.subject }}</td>
    <td>
      <!-- <router-link :to="`/board/detail?articleno=${article.articleno}`">{{
        article.subject
      }}</router-link> -->
      <router-link :to="{ name: 'detail', params: { id: notice.id } }" class="board-title">{{
        notice.title
      }}</router-link>
    </td>
    <td>{{ notice.author }}</td>
    <td>{{ notice.write_time | formatDate }}</td>
  </tr>
</template>

<script>
import moment from "moment";

export default {
  name: "NoticeListItem",
  props: {
    notice: Object,
  },
  filters: {
    formatDate(regtime) {
      return moment(new Date(regtime)).format("YYYY.MM.DD");
    },
  },
};
</script>

<style></style>

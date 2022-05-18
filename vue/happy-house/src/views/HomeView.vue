<template>
  <div><main-content></main-content></div>
</template>

<script>
import MainContent from "../components/MainContent.vue";

export default {
  components: { MainContent },
  data() {
    return {
      msg: "임시 메인 페이지",
    };
  },
};
</script>

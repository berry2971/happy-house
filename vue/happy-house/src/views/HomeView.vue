<template>
  <div><ka-kao-map></ka-kao-map></div>
</template>

<script>
import KaKaoMap from "../components/map/KaKaoMap.vue";

export default {
  components: { KaKaoMap },
  data() {
    return {
      msg: "메인 페이지",
    };
  },
};
</script>

<style></style>

<template>
  <div ref="mapIndexRoot" class="map-index-root"><MapIndex></MapIndex></div>
</template>

<script>
import MapIndex from "../components/map/MapIndex.vue";

export default {
  components: { MapIndex },
  mounted() {
  },
  data() {
    return {
      msg: "메인 페이지",
    };
  },
};
</script>

<style></style>

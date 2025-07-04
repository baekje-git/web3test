<template>
  <div class="">
    <q-pagination
      class="q-pagination_icon"
      v-model="current"
      :max="5"
      direction-links
      boundary-links
      flat
      color="grey"
      active-design="outline"
      active-color="unelevated"
      active-text-color="grey"
      icon-prev="img:assets/icons/arrow-prev01.svg"
      icon-next="img:assets/icons/arrow-next01.svg"
      icon-first="img:assets/icons/arrow-prev02.svg"
      icon-last="img:assets/icons/arrow-next02.svg"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";

const current = ref(3);
</script>

<style lang="scss" scoped></style>

<template>
  <div class="Filter_btn">
    <!-- <img src="/src/assets/icons/sliders-solid.svg" alt="icon" /> -->
    <img src="assets/icons/filter.svg" alt="icon" />
    <q-badge
      class="Filter_badge"
      color="#e53030"
      v-if="filterCheck === true"
      rounded
      floating
    >
      <img class="Filter_check" src="assets/icons/check.svg" alt="icon" />
      <!-- C:\Project\2017-BAEKJE\workspace\Web3\src\main\bjp-mobile\public\assets\icons\check.svg -->
    </q-badge>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  filterCheck: {
    type: Boolean,
    default: false,
  },
});
</script>

<style lang="scss" scoped></style>

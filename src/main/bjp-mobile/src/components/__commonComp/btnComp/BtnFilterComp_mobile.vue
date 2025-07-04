<template>
  <div class="Filter_btn">
    <img src="/public/assets/icons/sliders-solid.svg" alt="icon">
    <q-badge class="Filter_badge" color="red"
      v-if="filterCheck === true"
      rounded
      floating
    >
      <img class="Filter_check" src="/public/assets/icons/check.svg" alt="icon">
      <!-- C:\Project\2017-BAEKJE\workspace\Web3\src\main\bjp-mobile\public\assets\icons\check.svg -->
    </q-badge>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  filterCheck: {
    type: Boolean,
    default: false
  }
});


</script>

<style lang="scss" scoped>

</style>

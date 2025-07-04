<template>
  <div class="Btn_purple_wrap">
    <q-btn class="btn_purple" :label="btn_purpleLabel" dense />
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  btn_purpleLabel: String,
});
</script>

<style lang="scss">
.btn_purple {
  padding: 0 4px;
  color: $w;
  background-color: $b4;
  border-radius: 5px;
  &::before,
  &::after {
    box-shadow: none !important;
  }
}
.btn_purple span {
  @include fs-5;
}
</style>

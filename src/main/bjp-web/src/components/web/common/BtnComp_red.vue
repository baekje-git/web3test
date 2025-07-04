<template>
  <div class="Btn_purple_wrap">
    <q-btn class="btn_purple" :label="btn_redLabel" dense />
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  btn_redLabel: String,
});
</script>

<style lang="scss">
.btn_purple {
  padding: 0 16px;
  color: $w;
  background-color: $red-300;
  border-radius: 0;
  height: 32px;
  &::before,
  &::after {
    box-shadow: none !important;
  }
}
.btn_purple span {
  @include fs-5;
}
</style>

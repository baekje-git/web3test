<template>
  <div class="Btn_active_wrap">
    <q-btn
      class="btn_active_samll"
      :label="btn_activeLabel"
      dense
      size="xs"
      tabindex="-1"
    >
      <slot></slot>
    </q-btn>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  btn_activeLabel: String,
});
</script>

<style lang="scss">
.btn_active_samll {
  padding: 0 8px;
  height: 24px;
  color: $w;
  background-color: $gray-9;
  border-radius: 0;
  box-sizing: border-box;
  &::before,
  &::after {
    box-shadow: none !important;
  }
}
.btn_active_samll.bold span {
  font-weight: 700;
}
.btn_active_samll span {
  @include fs-5;
  line-height: 1em;
}
// .btn_active_samll:active,
// .btn_active_samll:hover,
// .btn_active_samll:focus {
//   @include gradation;
//   border-radius: 5px;
//   color: $w;
// }
.Btn_active_wrap.active {
  background: none;
  .q-btn {
    // font-weight: 600;
    background-color: #f5f5f5 !important;
  }
}
</style>

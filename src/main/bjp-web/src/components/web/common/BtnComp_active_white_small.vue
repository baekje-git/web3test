<template>
  <div class="Btn_active_wrap">
    <q-btn
      class="btn_active_white_samll"
      :label="btn_activeWhiteLabel"
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
  btn_activeWhiteLabel: String,
});
</script>

<style lang="scss">
.btn_active_white_samll {
  // @include fw-7;
  color: $b3;
  // background-color: #fff;
  background-color: transparent;
  border-radius: 0;
  padding: 0 8px;
  height: 24px;
  &::before,
  &::after {
    box-shadow: none !important;
  }
}
.btn_active_white_samll span {
  @include fs-5;
  line-height: 1em;
  color: black;
}
// .btn_active_white_samll:active,
// .btn_active_white_samll:hover,
// .btn_active_white_samll:focus {
//   @include gradation;
//   color: $w;
// }
.btn-line {
  .q-btn {
    border-style: solid;
    border-width: 1px;
  }
  &.default .q-btn {
    background-color: #fff;
    border-color: $gray-a;
    span {
      color: $gray-1;
    }
  }
  &.dark .q-btn {
    background-color: $gray-3;
    border-color: $gray-3;
  }
}
.btn-pd5 .q-btn {
  padding: 0 5px;
}

.pd-0 .btn_active_white_samll {
  padding: 0;
}
.btn-prd .btn_active_white_samll {
  height: auto;
}
</style>

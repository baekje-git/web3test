<template>
  <div class="Btn_default_wrap">
    <q-btn
      class="btn_default_small"
      :label="btn_defaultLabel"
      dense
      :disable="isDisable"
      size="xs"
    />
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  btn_defaultLabel: String,
  isDisable: { type: Boolean, default: false },
});
</script>

<style lang="scss">
.btn_default_small {
  // @include gradation;
  padding: 0 8px;
  height: 24px;
  color: #111;
  background-image: none !important;
  background-color: #fff;
  border-radius: 0;
  &::before,
  &::after {
    box-shadow: none !important;
  }
}
.btn_default_small span {
  @include fs-5;
  line-height: 1;
}
.strong span {
  font-weight: 600;
}
.btn-line {
  .q-btn {
    border-style: solid;
    border-width: 1px;
  }
  &.default .q-btn {
    // background-color: #fff;
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
.Btn_default_wrap.active .q-btn {
  background-color: $th-bg;
}
</style>

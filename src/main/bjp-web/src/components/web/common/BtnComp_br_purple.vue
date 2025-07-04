<template>
  <div class="Btn_br_purple_wrap">
    <q-btn class="btn_br_purple" dense>
      {{ btn_br_top }}<br />{{ btn_br_bottom }}
      <slot></slot>
    </q-btn>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  btn_br_top: {
    type: String,
    default: "TopText",
  },
  btn_br_bottom: {
    type: String,
    default: "BottomText",
  },
});
</script>

<style lang="scss">
.btn_br_purple {
  padding: 2px 8px;
  color: #fff;
  background-color: $gray-a;
  border: 1px solid $gray-a;
  border-radius: 0;
  box-shadow: none;
  &::after,
  &::before {
    display: none;
  }
}
.btn_br_purple span {
  @include fs-5;
  line-height: 1.35em;
}
</style>

<template>
  <div class="Btn_br_wrap">
    <q-btn :to="btn_br_link" class="btn_br" dense>
      {{ btn_br_top }}<br />{{ btn_br_bottom }}
    </q-btn>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  btn_br_link: {
    type: String,
    default: "",
  },
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
.btn_br {
  padding: 2px 8px;
  background-color: #fff;
  border: 1px solid $gray-a;
  border-radius: 0;
  box-shadow: none;
  &::after,
  &::before {
    display: none;
  }
}
.btn_br span {
  @include fs-5;
  line-height: 1.35em;
}
</style>

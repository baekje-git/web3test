<template>
  <div class="Input_default_wrap">
    <q-input
      input-class="input_default"
      borderless
      dense
      :placeholder="placeholder1"
    />
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  placheholder1: {
    type: String,
    required: false,
  },
});
</script>

<style lang="scss">
.input_default {
  @include fs-5;
  padding: 4px 6px;
  height: 32px;
  text-align: left;
  border: 1px solid $line-400;
  // background-color: #fff;
  border-radius: 0px;
}
.date_input_wrap .input_default {
  border: none;
}
.Input_default_wrap {
  @include flex_start;
  flex-wrap: wrap;
  width: 160px;

  .q-field__control {
    height: 32px;
    &::after {
      display: none;
    }
  }

  .input_default {
    padding: 0 4px;
  }

  .q-field__native > span {
    display: none;
  }
}
</style>

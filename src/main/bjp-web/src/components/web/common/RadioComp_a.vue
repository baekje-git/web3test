<template>
  <div class="">
    <q-option-group
      :modelValue="modelValue"
      :options="radio_options"
      size="xs"
      inline
      dense
      @update:modelValue="$emit('update:modelValue', $event)"
    />
  </div>
</template>
<script setup>
import { defineProps } from "vue";

const props = defineProps({
  modelValue: String,
  radio_options: Array,
});
</script>
<style lang="scss">
.q-radio--dense .q-radio__label {
  @include fs-5;
  padding-left: 4px;
}
.q-radio {
  &:not(.disabled) .q-radio__inner::before {
    transform: scale3d(0, 0, 0) !important;
  }
  &:focus .q-radio__inner::before,
  &:hover .q-radio__inner::before {
    transform: scale3d(1, 1, 1) !important;
  }

  .q-radio__inner {
    width: 16px;
    height: 16px;
    font-size: 12px !important;
  }
  .q-radio__bg {
    top: 0;
    left: 0;
    padding: 5%;
    width: 100%;
    height: 100%;
    border-radius: 50%;
    border: 1px solid $line-400;
    transition: 0.3s;
    path {
      fill: transparent;
    }
    .q-radio__check {
      fill: #fff;
    }
  }

  &[aria-checked="true"] .q-radio__inner .q-radio__bg {
    background-color: $primary;
    border-color: $primary;
  }
}
</style>

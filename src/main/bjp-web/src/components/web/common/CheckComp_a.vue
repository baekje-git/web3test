<template>
  <div class="">
    <!-- <q-option-group
      :modelValue="modelValue"
      :options="checkbox_options"
      type="checkbox"
      color="blue-10"
      size="xs"
      inline
      dense
      @update:modelValue="$emit('update:modelValue', $event)"
    /> -->
    <q-option-group
      :modelValue="modelValue"
      :options="checkbox_options"
      type="checkbox"
      color="blue-10"
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
  checkbox_options: Array,
});
</script>
<style lang="scss">
.q-checkbox__label {
  @include fs-6;
  @include fw-7;
}
</style>

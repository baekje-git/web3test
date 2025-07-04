<template>
  <div class="SearchComp_wrap">
    <q-input
      input-class="input_search"
      v-model="text1"
      borderless
      dense
      :placeholder="placeholder1"
    />
    <q-input
      input-class="input_search"
      v-model="text2"
      borderless
      dense
      :placeholder="placeholder2"
    />
  </div>
</template>
<script setup>
import { defineProps, ref } from "vue";

const text1 = ref("");
const text2 = ref("");

const props = defineProps({
  // modelValue: String,
  placeholder1: String,
  placeholder2: String,
});
</script>
<style lang="scss">
.SearchComp_wrap {
  display: flex;
  gap: 8px;
}
.input_search {
  @include fs-5;
  width: 120px;
  height: 28px;
  border: 1px solid $line-400;
  padding: 4px 6px;
  border-radius: 0;
}
.q-field__control-container {
  @include flex_center;
}
</style>

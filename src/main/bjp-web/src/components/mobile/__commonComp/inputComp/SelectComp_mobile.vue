<template>
  <div>
    <q-select
      :class="[selectCommon, selectClass]"
      :options="options"
      v-model="select_model"
      behavior="menu"
      borderless
      dense
      flat
      @update:model-value="(val) => onChangeValue(val)"
    />
  </div>
</template>

<script setup>
import { ref, defineProps } from "vue";

const emit = defineEmits(["onChangeValue"]);

const select_model = ref("2025");

const options = [
  "2025",
  "2024",
  "2023",
  "2022",
  "2021",
  "2020",
  "2019",
  "2018",
  "2017",
];

const props = defineProps({
  selectOption: {
    type: Array,
  },
  selectCommon: {
    type: String,
    default: "select-common",
  },
  selectStyle: {
    type: String,
    default: "default",
  },
});

const selectClass = ref("");

selectClass.value += ` select-${props.selectStyle}`;

function onChangeValue(val) {
  emit("onChangeValue", val);
}
</script>

<style lang="scss"></style>

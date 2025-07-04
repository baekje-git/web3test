<template>
  <div>
    <q-input
      :class="[inputCommon, inputClass]"
      :placeholder="placeholder"
      :maxlength="maxlength"
      borderless
      dense
      flat
      :disable="isDisable"
      v-model="input"
      @keyup.enter="enter"
      @update:model-value="$emit('update:input_model_props', $event)"
    />
  </div>
</template>

<script setup>
import { ref, defineProps,defineEmits } from "vue";

const input = defineModel("input_model_props");

const emits = defineEmits(['update:input_model_props', 'enter']);

const enter = ()=>{
  emits('enter');
};

const props = defineProps({
  inputCommon: {
    type: String,
    default: "input-common",
  },
  inputStyle: {
    type: String,
    default: "default",
  },
  placeholder: {
    type: String,
    default: "",
  },
  input_model_props: {
    type: String,
  },
  maxlength: {
    type: String,
  },
  isDisable: {
    type: Boolean,
    default: false,
  },
});

const inputClass = ref("");

inputClass.value += ` input-${props.inputStyle}`;
</script>

<style lang="scss"></style>

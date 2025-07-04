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
      type="search"
      @keyup.enter="enter"
      @update:model-value="$emit('update:input_model_props', $event)"
      @focus="(input) => input.target.select()"
      ref="refInput"
    />
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from "vue";
import { Keyboard } from "@capacitor/keyboard";
import { Capacitor } from "@capacitor/core";

const refInput = ref();

//엔터키 이벤트
const emits = defineEmits(["update:input_model_props", "enter"]);

const enter = () => {
  refInput.value.blur();
  emits("enter");
};

const input = defineModel("input_model_props");

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

<style lang="scss">
.input-common {
  border: 1px solid $line-400;
  border-radius: 0;
  overflow: hidden;
  .q-field__native {
    @include fs-5;
    padding: 0 8px;
  }
}
.input-default {
  // height: 28px;
  height: 30px;
  .q-field__native {
    height: 30px;
  }
  .q-field__control {
    height: 30px;
  }
}
</style>

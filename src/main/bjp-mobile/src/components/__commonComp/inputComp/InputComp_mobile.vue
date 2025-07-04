<template>
  <div>
    <q-input
      :class="[inputCommon, inputClass]"
      :placeholder="placeholder"
      :maxlength="maxlength"
      borderless
      dense
      flat
      v-model="input"
    />

  </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';

const input = defineModel('input_model_props');

const props = defineProps({
  inputCommon: {
    type: String,
    default: 'input-common',
  },
  inputStyle: {
    type: String,
    default: 'default',
  },
  placeholder: {
    type: String,
    default: '',
  },
  input_model_props: {
    type: String,
  },
  maxlength: {
    type: String,
  }
});


const inputClass = ref('');

inputClass.value += ` input-${props.inputStyle}`;

</script>

<style lang="scss">


</style>

<template>
  <div>
    <q-input
      class="input-common"
      :class="inputClass"
      borderless
      dense
      flat
      v-model="count"
    >
      <template v-slot:prepend>
        <div class="EAInput_minus" @click="minus">
          <img src="/public/assets/icons/minus-solid.svg" alt="minus">
        </div>
      </template>
      <template v-slot:append>
        <div class="EAInput_plus" @click="plus">
          <img src="/public/assets/icons/plus-solid.svg" alt="plus">
        </div>
      </template>
    </q-input>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from 'vue';

const props = defineProps({
  inputStyle: {
    type: String,
    default: 'EA'
  },
  modelValue: {
    type: [String, Number],
    default: ''
  },

});

const count = ref(props.modelValue);
const emit = defineEmits(['update:modelValue']);

const plus = () => {
  count.value++;
  emit('update:modelValue', count.value);
};

const minus = () => {
  if (count.value > 0) {
    count.value--;
    emit('update:modelValue', count.value);
  }
};

// const update = () => {
//   emit('update:value', count.value);
// };
//v-model로의 변화
// <!-- -   :model-value="count"
//      -   @input="update"
//      +   v-model="count"  --> (vue 3.3부터 defineModel 가능)

const inputClass = ref('')
inputClass.value += ` input-${props.inputStyle}`
</script>

<style lang="scss" scoped>

</style>

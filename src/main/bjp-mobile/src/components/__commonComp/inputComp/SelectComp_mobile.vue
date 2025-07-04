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
    />
  </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';

const select_model = ref('2024')

const options = ['2024', '2023', '2022', '2021', '2020', '2019', '2018', '2017']

const props = defineProps({
  selectOption: {
    type: Array
  },
  selectCommon: {
    type: String,
    default: 'select-common',
  },
  selectStyle: {
    type: String,
    default: 'default',
  },
});


const selectClass = ref('');

selectClass.value += ` select-${props.selectStyle}`;

</script>

<style lang="scss">


</style>

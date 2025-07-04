<template>
  <div>
    <q-btn
      :class="[btnCommon, btnClass]"
      :size="btnSize"
      :align="btnAlign"
      :icon-right="btnIcon"
      @click="$emit('btnClickEvent')"
      flat
      dense
      no-caps

    >
      <div class="">
        <span>{{ buttonText }} </span> <br/>
        <span>{{ sub_buttonText }} </span>
      </div>
    </q-btn>
  </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';

// const emits = defineEmits(['btnClickEvent'])

const props = defineProps({
  btnCommon: {
    type: String,
    default: 'btn-common',
  },
  btnStyle: {
    type: String,
    default: 'defalut',
  },
  buttonText: {
    type: String,
    default: '버튼',
  },
  sub_buttonText: {
    type: String,
    default: '',
  },
  btnSize: {
    type: String,
    default: 'sm',
  },
  btnAlign: {
    type: String,
    default: 'center',
  },
  btnIcon: {
    type: String,
  },
  btnClickEvent: {
    type: String,
    default: '',
  }
});


const btnClass = ref('');

btnClass.value += ` btn-${props.btnStyle}`;

</script>

<style lang="scss">

</style>

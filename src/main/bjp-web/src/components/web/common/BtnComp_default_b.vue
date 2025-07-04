<template>
  <div class="Btn_default_wrap">
    <q-btn class="btn_default" :to="btn_defaultLink" :label="btn_defaultLabel" dense><slot></slot></q-btn>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  btn_defaultLabel: String,
  btn_defaultLink: {
    type: String,
    default: '',
  }
})

</script>

<style lang="scss" >

.btn_default {
  @include gradation;
  padding: 0 16px;
  color: $w;
  border-radius: 5px;
}
.btn_default span {
  @include fs-5;
  color: $w !important;
}

</style>

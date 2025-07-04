<template>
  <div class="Filter_wrap">
    <div v-for="(group, i) in btnGroups" :key="i" class="Filter_Btn_wrap">
      <div class="Filter_Title" :class="group.btnTitle">
        <span>{{ group.title }}</span>
        <span>{{ group.subtitle }}</span>
      </div>
      <div class="Filter_Btn_Grid_wrap">
        <div class="Filter_Btn_Grid" :style="{ gridTemplateColumns: 'repeat(' + group.gridStyle + ', 1fr)' }">
          <BtnComp_mobile
            v-for="(btn, j) in group.buttons"
            :key="j"
            :buttonText="btn.title"
            :sub_buttonText="btn.subtitle"
            :btnClickEvent="btn.Click"
            btnStyle="active"
            btnSize="lg"
            class="Filter_Btn"
          />
        </div>
      </div>
      <div class="Filter_DatePicker_wrap" :class="group.btnDatePicker">
        <div class="flex_between" style="padding: 16px 0 0 0;">
          <DatePickerComp_mobile
            style="width: 148px;"
          />
          <span style="font-size: 12px; color: #999999; margin: 0 4px 0 1px;">-</span>
          <DatePickerComp_mobile
            style="width: 148px;"
          />
        </div>
      </div>
    </div>
    <BtnComp_mobile
      :class="btnResult"
      buttonText="적용"
      btnStyle="default"
      btnSize="md"
      style="padding-top: 20px;"
    />
  </div>
</template>

<script setup>
import {  defineProps } from 'vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import DatePickerComp_mobile from 'src/components/__commonComp/datepickerComp/DatePickerComp_mobile.vue';

const emits = defineEmits(['btnClickEvent']);

const { props } = defineProps({
  btnGroups: Array,
  btnResult: {
    type: String,
    default: 'off'
  },
});

</script>

<style lang="scss" scoped>

</style>

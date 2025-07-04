<template>
  <div class="Record_Title">
    <slot></slot>
  </div>
  <div class="flex_between" style="margin-bottom: 16px;">
    <DatePickerComp_mobile
      placeholder="유효기간"
      style="width: 105px;"
    />
    <div class="flex_center" style="gap: 4px;">
      <InputComp_mobile
        placeholder="제조번호"
        style="width: 72px;"
      />
      <InputComp_mobile
        placeholder="일련번호"
        style="width: 72px;"
      />
    </div>
    <BtnComp_mobile
      buttonText="검색"
      btnStyle="default"
      style="width: 64px;"
    />
  </div>
  <div class="" style="margin-bottom: 16px;">
    <BtnGroupComp_mobile
      :buttons="['당월', '전월', '3개월', '6개월', '1년', '2년', '3년']"
      btnStyle="active"
    />
  </div>

  <div class="Record_Table_wrap">
    <ReturnRecordTable_a_mobile/>
  </div>



</template>

<script setup>
import { ref } from 'vue';

import ReturnRecordTable_a_mobile from 'src/components/DialogComp/TableDialogComp/2.returnTableComp/ReturnRecordTable_a_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import InputComp_mobile from 'src/components/__commonComp/inputComp/InputComp_mobile.vue';
import DatePickerComp_mobile from 'src/components/__commonComp/datepickerComp/DatePickerComp_mobile.vue';
import BtnGroupComp_mobile from 'src/components/__commonComp/btnComp/BtnGroupComp_mobile.vue';



</script>

<style lang="scss" scoped>


</style>

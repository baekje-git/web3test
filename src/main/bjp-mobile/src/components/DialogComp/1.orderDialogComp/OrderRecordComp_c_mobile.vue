<template>
  <div class="Record_Title">
    <slot></slot>
  </div>
  <div class="flex_center" style="gap: 24px; margin-bottom: 16px">
    <InputComp_mobile
      style="width: 240px;"
    />
    <BtnComp_mobile
      buttonText="변경"
      btnStyle="default"
      style="width: 64px;"
    />
  </div>
  <FilterComp_mobile
    :btnGroups="filterbtnGroups"
    :btnGridStyle="gridStyle"
  />
  <div class="Record_Table_wrap">
    <OrderRecordTable_c_mobile/>
  </div>
</template>

<script setup>
import FilterComp_mobile from 'src/components/DialogComp/FilterDialogComp/FilterComp_mobile.vue';
import OrderRecordTable_c_mobile from 'src/components/DialogComp/TableDialogComp/1.orderTableComp/OrderRecordTable_c_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import InputComp_mobile from 'src/components/__commonComp/inputComp/InputComp_mobile.vue';


const filterbtnGroups = [
  {
    title: '기간 설정',
    subtitle: '',
    gridStyle: 4,
    buttons: [
      { title: '당월' },
      { title: '전월' },
      { title: '3개월' },
      { title: '6개월' },
      { title: '1년' },
      { title: '2년' },
      { title: '3년' },
    ]
  },
];
</script>

<style lang="scss" scoped>





</style>

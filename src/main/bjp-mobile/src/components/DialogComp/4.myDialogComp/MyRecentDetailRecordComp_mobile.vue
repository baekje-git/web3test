<template>
  <div class="Record_Title">
    <slot></slot>
  </div>
  <FilterComp_mobile
    :btnGroups="filterbtnGroups"
    :btnGridStyle="gridStyle"
  />
  <div class="Record_Table_wrap">
    <MyRecentDetailRecordTable_mobile/>
  </div>
</template>

<script setup>
import FilterComp_mobile from 'src/components/DialogComp/FilterDialogComp/FilterComp_mobile.vue';
import MyRecentDetailRecordTable_mobile from 'src/components/DialogComp/TableDialogComp/4.myTableComp/MyRecentDetailRecordTable_mobile.vue';


const filterbtnGroups = [
  {
    title: '기간 설정',
    subtitle: '',
    gridStyle: 4,
    btnDatePicker: 'off',
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

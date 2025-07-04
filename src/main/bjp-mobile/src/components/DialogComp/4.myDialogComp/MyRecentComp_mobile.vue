<template>
  <div class="Recent_wrap">
    <div class="BtnGroup_wrap">
      <BtnGroupComp_mobile
        :buttons="['전체', '매출', '반품']"
        btnStyle="active"
        style="margin-bottom: 16px;"
      />
      <div class="flex_between" style="margin-bottom: 16px;">
        <DatePickerComp_mobile
          style="width: 142px; gap: 8px;"
        />
        <span style="font-size: 12px; color: #999999; margin: 0 4px 0 1px;">-</span>
        <DatePickerComp_mobile
          style="width: 142px; gap: 8px;"
        />
      </div>
      <BtnGroupComp_mobile
        :buttons="['당월', '전월', '3개월', '6개월', '1년', '2년', '3년']"
        btnStyle="active"
        style="margin-bottom: 16px;"
      />
    </div>
    <div class="Recent_Table_wrap">
      <MyRecentTable_mobile/>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import MyRecentTable_mobile from 'src/components/DialogComp/TableDialogComp/4.myTableComp/MyRecentTable_mobile.vue';
import BtnGroupComp_mobile from 'src/components/__commonComp/btnComp/BtnGroupComp_mobile.vue';
import DatePickerComp_mobile from 'src/components/__commonComp/datepickerComp/DatePickerComp_mobile.vue';


</script>

<style lang="scss" scoped>


</style>

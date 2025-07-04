<template>
  <div class="QA_wrap">
    <div class="InputGroup_wrap">
      <div class="flex_between" style="margin-bottom: 16px;">
        <InputComp_mobile
          placeholder="제약사 입력"
          style="width: 240px;"
        />
        <BtnComp_mobile
          buttonText="검색"
          btnStyle="default"
          style="width: 64px;"
        />
      </div>
      <div class="flex_between" style="margin-bottom: 16px;">
        <DatePickerComp_mobile
          style="width: 142px; gap: 8px;"
        />
        <span style="font-size: 12px; color: #999999; margin: 0 4px 0 1px;">-</span>
        <DatePickerComp_mobile
          style="width: 142px; gap: 8px;"
        />
      </div>
      <div class="">
        <BtnGroupComp_mobile
          :buttons="['당월', '전월', '3개월']"
          btnStyle="active"
          style="margin-bottom: 16px;"
        />
      </div>
    </div>
    <div class="QA_Table_wrap">
      <MyQATable_mobile/>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import InputComp_mobile from 'src/components/__commonComp/inputComp/InputComp_mobile.vue';
import DatePickerComp_mobile from 'src/components/__commonComp/datepickerComp/DatePickerComp_mobile.vue';
import BtnGroupComp_mobile from 'src/components/__commonComp/btnComp/BtnGroupComp_mobile.vue';
import MyQATable_mobile from 'src/components/DialogComp/TableDialogComp/4.myTableComp/MyQATable_mobile.vue';

</script>

<style lang="scss" scoped>


</style>

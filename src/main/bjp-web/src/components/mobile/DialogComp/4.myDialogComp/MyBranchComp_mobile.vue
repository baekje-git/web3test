<template>
  <div class="Branch_wrap">
    <div class="table_Top_wrap">
      <div class="table_Title_mobile" style="padding: 0 0 16px">
        <span>지점 공지사항</span>
      </div>
    </div>
    <div class="Branch_Table_wrap">
      <MyBranchTable_mobile :rowSno="props.rowSno"/>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import MyBranchTable_mobile from "src/components/mobile/DialogComp/TableDialogComp/4.myTableComp/MyBranchTable_mobile.vue";
const props = defineProps({
  rowSno: {
    type: Number,
  },
});

</script>

<style lang="scss" scoped></style>

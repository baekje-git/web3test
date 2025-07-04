<template>
  <div class="Notice_wrap">
    <div class="table_Top_wrap">
      <div class="table_Title" style="padding: 0 0 16px;">
        <span>공지사항</span>
      </div>
    </div>
    <div class="Notice_Table_wrap">
      <MyNoticeTable_mobile/>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import MyNoticeTable_mobile from 'src/components/DialogComp/TableDialogComp/4.myTableComp/MyNoticeTable_mobile.vue';



</script>

<style lang="scss" scoped>


</style>

<template>
  <div>
    <ClientComp_mobile />
    <MyPageViewTable_mobile />
    <MyActivityViewTable_mobile />
    <MyRecentViewTable_mobile />
    <MyNoticeViewTable_mobile />
    <MyBranchViewTable_mobile />
    <MyQAViewTable_mobile v-if="userInfo.USER_GB_CD !== '30'" />
  </div>
</template>
<script setup>
import * as util from "src/support/util";

import ClientComp_mobile from "src/components/mobile/DialogComp/ClientDialogComp/ClientComp_mobile.vue";
import MyPageViewTable_mobile from "src/components/mobile/TableComp/my/MyPageViewTable_mobile.vue";
import MyActivityViewTable_mobile from "src/components/mobile/TableComp/my/MyActivityViewTable_mobile.vue";
import MyRecentViewTable_mobile from "src/components/mobile/TableComp/my/MyRecentViewTable_mobile.vue";
import MyNoticeViewTable_mobile from "src/components/mobile/TableComp/my/MyNoticeViewTable_mobile.vue";
import MyBranchViewTable_mobile from "src/components/mobile/TableComp/my/MyBranchViewTable_mobile.vue";
import MyQAViewTable_mobile from "src/components/mobile/TableComp/my/MyQAViewTable_mobile.vue";

const userInfo = util.getUserData();
</script>
<style lang="scss" scoped></style>

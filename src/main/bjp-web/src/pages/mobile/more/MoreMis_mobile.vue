<template>
  <div>
    <ClientComp_mobile />
    <MoreMisTable_mobile TableTitle="전지점 매출현황" />
  </div>
</template>

<script setup>
import ClientComp_mobile from "src/components/mobile/DialogComp/ClientDialogComp/ClientComp_mobile.vue";
import MoreMisTable_mobile from "src/components/mobile/TableComp/5.more/MoreMisTable_mobile.vue";
</script>

<style lang="scss" scoped></style>

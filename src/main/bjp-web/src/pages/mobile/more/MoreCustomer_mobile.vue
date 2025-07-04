<template>
  <div>
    <ClientComp_mobile />
    <MoreCustomerTable_mobile TableTitle="거래처별 영업현황" />
  </div>
</template>

<script setup>
import ClientComp_mobile from "src/components/mobile/DialogComp/ClientDialogComp/ClientComp_mobile.vue";
import MoreCustomerTable_mobile from "src/components/mobile/TableComp/5.more/MoreCustomerTable_mobile.vue";
</script>

<style lang="scss" scoped></style>

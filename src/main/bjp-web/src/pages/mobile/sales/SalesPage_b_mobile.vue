<template>
  <div>
    <ClientComp_mobile />
    <SalesTable_b_mobile TableTitle="품목수불" />
  </div>
</template>
<script setup>
import ClientComp_mobile from "src/components/mobile/DialogComp/ClientDialogComp/ClientComp_mobile.vue";
import SalesTable_b_mobile from "src/components/mobile/TableComp/sales/SalesTable_b_mobile.vue";
</script>
<style lang="scss" scoped></style>

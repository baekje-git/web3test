<template>
  <div>
    <ClientComp_mobile />
    <ReturnTable_b_mobile TableTitle="반품 장바구니" />
  </div>
</template>
<script setup>
import ClientComp_mobile from "src/components/mobile/DialogComp/ClientDialogComp/ClientComp_mobile.vue";
import ReturnTable_b_mobile from "src/components/mobile/TableComp/return/ReturnTable_b_mobile.vue";
</script>
<style lang="scss" scoped></style>

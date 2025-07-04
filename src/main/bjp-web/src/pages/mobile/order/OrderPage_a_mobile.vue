<template>
  <div>
    <ClientComp_mobile />
    <OrderTable_a_mobile TableTitle="주문" />
  </div>
</template>
<script setup>
import ClientComp_mobile from "src/components/mobile/DialogComp/ClientDialogComp/ClientComp_mobile.vue";
import OrderTable_a_mobile from "src/components/mobile/TableComp/order/OrderTable_a_mobile.vue";
</script>
<style lang="scss" scoped></style>

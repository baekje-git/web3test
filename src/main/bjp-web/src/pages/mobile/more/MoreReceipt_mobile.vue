<template>
  <div>
    <ClientComp_mobile />
    <MoreReceiptTable_mobile TableTitle="제품수불내역" />
  </div>
</template>

<script setup>
import ClientComp_mobile from "src/components/mobile/DialogComp/ClientDialogComp/ClientComp_mobile.vue";
import MoreReceiptTable_mobile from 'src/components/mobile/TableComp/5.more/MoreReceiptTable_mobile.vue';

</script>

<style lang="scss" scoped></style>

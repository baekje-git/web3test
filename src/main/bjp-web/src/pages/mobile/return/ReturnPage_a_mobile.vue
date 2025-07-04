<template>
  <div>
    <ClientComp_mobile />
    <ReturnTable_a_mobile TableTitle="반품" />
  </div>
</template>
<script setup>
import ClientComp_mobile from "src/components/mobile/DialogComp/ClientDialogComp/ClientComp_mobile.vue";
import ReturnTable_a_mobile from "src/components/mobile/TableComp/return/ReturnTable_a_mobile.vue";
</script>
<style lang="scss" scoped></style>

<template>
  <div>
    <ClientComp_mobile/>
    <SalesTable_a_mobile
      TableTitle="매출원장"
    />
  </div>
</template>
<script setup>
import ClientComp_mobile from 'src/components/DialogComp/ClientDialogComp/ClientComp_mobile.vue';
import SalesTable_a_mobile from 'src/components/TableComp/3.sales/SalesTable_a_mobile.vue';

</script>
<style lang="scss" scoped>

</style>

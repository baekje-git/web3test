<template>
  <div>
    <ClientComp_mobile/>
    <SalesTable_c_mobile
      TableTitle="반품현황"
    />
  </div>
</template>
<script setup>
import ClientComp_mobile from 'src/components/DialogComp/ClientDialogComp/ClientComp_mobile.vue';
import SalesTable_c_mobile from 'src/components/TableComp/3.sales/SalesTable_c_mobile.vue';

</script>
<style lang="scss" scoped>

</style>

<template>
  <div>
    <ClientComp_mobile/>
    <OrderTable_c_mobile
      TableTitle="장바구니"
    />
  </div>
</template>
<script setup>
import ClientComp_mobile from 'src/components/DialogComp/ClientDialogComp/ClientComp_mobile.vue';
import OrderTable_c_mobile from 'src/components/TableComp/1.order/OrderTable_c_mobile.vue';


</script>
<style lang="scss" scoped>

</style>

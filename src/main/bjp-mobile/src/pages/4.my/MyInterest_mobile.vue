<template>
  <div>
    <ClientComp_mobile/>
    <MyInterestTable_mobile
      TableTitle="관심상품"
    />
  </div>
</template>
<script setup>
import ClientComp_mobile from 'src/components/DialogComp/ClientDialogComp/ClientComp_mobile.vue';
import MyInterestTable_mobile from 'src/components/TableComp/4.my/MyInterestTable_mobile.vue';

</script>
<style lang="scss" scoped>

</style>

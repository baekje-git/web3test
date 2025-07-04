<template>
  <div>
    <ClientComp_mobile/>
    <MoreDailyTable_mobile
      TableTitle="일일거래내역서"
    />
  </div>
</template>
<script setup>
import ClientComp_mobile from 'src/components/DialogComp/ClientDialogComp/ClientComp_mobile.vue';
import MoreDailyTable_mobile from 'src/components/TableComp/5.more/MoreDailyTable_mobile.vue';

</script>
<style lang="scss" scoped>

</style>

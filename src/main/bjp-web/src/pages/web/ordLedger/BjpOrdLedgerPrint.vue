<template>
  <div style="padding: 20px;">
    <div class="flex_start" style="gap:20px; margin-bottom: 20px;">
      <SalesLedger1/>
    </div>
    <div class="flex_start" style="gap: 20px">
      <SalesLedger2 />
    </div>
  </div>
</template>
<script setup>
import SalesLedger1 from './SalesLedger1.vue';
import SalesLedger2 from './SalesLedger2.vue';

</script>
<style lang="scss" scoped>


</style>

<template>
  <div class="Tax_wrap">
    <div class="Select_wrap">
      <SelectComp_mobile
        selectStyle="default"
        style="width: 80px; margin-bottom: 16px;"
      />
    </div>
    <div class="Tax_Table_wrap">
      <MyTaxTable_mobile/>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import SelectComp_mobile from 'src/components/__commonComp/inputComp/SelectComp_mobile.vue';
import MyTaxTable_mobile from 'src/components/DialogComp/TableDialogComp/4.myTableComp/MyTaxTable_mobile.vue';


</script>

<style lang="scss" scoped>


</style>

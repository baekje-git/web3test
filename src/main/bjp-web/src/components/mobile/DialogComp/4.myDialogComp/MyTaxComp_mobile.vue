<template>
  <div class="Tax_wrap">
    <div class="Select_wrap">
      <SelectComp_mobile
        selectStyle="default"
        style="width: 80px; margin-bottom: 16px"
        @onChangeValue="(val) => onChangeYear(val)"
      />
    </div>
    <div class="Tax_Table_wrap">
      <MyTaxTable_mobile ref="myTaxTable" :year="year" />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

import SelectComp_mobile from "src/components/mobile/__commonComp/inputComp/SelectComp_mobile.vue";
import MyTaxTable_mobile from "src/components/mobile/DialogComp/TableDialogComp/4.myTableComp/MyTaxTable_mobile.vue";

const myTaxTable = ref(null);
const year = ref("");

async function onChangeYear(val) {
  year.value = val;

  await myTaxTable.value.fetchData();
}
</script>

<style lang="scss" scoped></style>

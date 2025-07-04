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
      <MyActivityTable_mobile ref="myActivityTable" />
    </div>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref } from "vue";
import SelectComp_mobile from "src/components/mobile/__commonComp/inputComp/SelectComp_mobile.vue";
import MyActivityTable_mobile from "src/components/mobile/DialogComp/TableDialogComp/4.myTableComp/MyActivityTable_mobile.vue";

const myActivityTable = ref(null);

//---------------------------- Function ------------------------------//

async function onChangeYear(val) {
  await myActivityTable.value.setDate(val);
  console.log("년도고정확인", myActivityTable.value.setDate(val));
}
</script>

<style lang="scss" scoped></style>

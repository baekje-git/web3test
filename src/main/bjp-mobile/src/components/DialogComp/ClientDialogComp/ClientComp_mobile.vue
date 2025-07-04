<template>
  <div class="Client_wrap">
    <span class="client_title">{{custNm}}</span>
    <BtnComp_mobile
      buttonText="거래처변경"
      btnStyle="white"
      @click="dialog_model = true"
      style="width: 88px;"
    />
  </div>
  <div class="client_line"></div>



  <DialogLayout_mobile
    dialog_title="거래처 선택"
    v-model:dialog_model_prop="dialog_model"
    :dialog_full="dialog_full"
    @update:model-value="input_model_props = $event"
  >
    <div class="">
      <ClientTable_mobile/>
    </div>
  </DialogLayout_mobile>


</template>


<script setup>
import { ref, computed } from 'vue';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import ClientTable_mobile from 'src/components/DialogComp/TableDialogComp/ClientTableComp/ClientTable_mobile.vue';
import * as util from "src/support/util";

const userInfo = util.getUserData();
const dialog_model = ref(false)
const dialog_full = ref(true)
const custNm = computed(() =>
  userInfo.CUST_NM != ("" || undefined) ? userInfo.CUST_NM : "(거래처를 선택해주세요.)"
);
</script>

<style lang="scss" scoped>
.Client_wrap {
  @include flex_between;
  width: 100%;
  padding: 4px 0;
}
.client_title {
  @include fw-7;
}
.client_line {
  @include border-b;
  margin: 0 -16px;
}
</style>

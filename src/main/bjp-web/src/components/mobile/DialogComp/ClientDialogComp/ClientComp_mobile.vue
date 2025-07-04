<template>
  <div class="Client_wrap">
    <span class="client_title">
      <img src="assets/images/ico_store.svg" class="ico_store" />
      {{ custNm }}
    </span>
    <BtnComp_mobile
      v-if="userInfo.USER_GB_CD !== '30'"
      class="btn-white fw-mid"
      buttonText="거래처변경"
      @click="dialog_model = true"
    />
  </div>
  <!-- <div class="client_line"></div> -->

  <DialogLayout_mobile
    dialog_title="거래처 선택"
    v-model:dialog_model_prop="dialog_model"
    :dialog_full="dialog_full"
    :close-btn-visible="isVisible"
    @update:model-value="input_model_props = $event"
  >
    <div class="">
      <ClientTable_mobile :isOpen="dialog_model" />
    </div>
  </DialogLayout_mobile>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";

import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import ClientTable_mobile from "src/components/mobile/DialogComp/TableDialogComp/ClientTableComp/ClientTable_mobile.vue";
import * as util from "src/support/util";

const userInfo = util.getUserData();
const dialog_model = ref(false);
const dialog_full = ref(true);
const custNm = computed(() =>
  userInfo.CUST_NM != ("" || undefined)
    ? userInfo.CUST_NM
    : "(거래처를 선택해주세요.)"
);

const isVisible = ref(true);

onMounted(() => {
  if (userInfo.CUST_CD === "") {
    dialog_model.value = true;
    isVisible.value = false;
  }
});
</script>

<style lang="scss" scoped>
.Client_wrap {
  position: relative;
  @include flex_between;
  // margin-top: -1px;
  width: 100%;
  padding: 16px 20px 20px;
  background-color: $header-bg;
  &::before {
    content: "";
    position: absolute;
    top: 0;
    left: 50%;
    transform: translateX(-50%);
    width: calc(100% - 40px);
    height: 1px;
    background-color: rgba(#fff, 0.2);
  }
}
.client_title {
  @include fw-7;
  color: #fff;
}
.client_line {
  margin: 0 -16px;
  border-bottom: 1px solid rgba(#fff, 0.2);
}
</style>

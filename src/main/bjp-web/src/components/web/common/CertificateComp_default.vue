<template>
  <q-dialog v-model="isOpen" persistent>
    <q-card style="padding: 32px">
      <div class="dialog_header" style="width: 700px">
        <span>인수증</span>
      </div>
      <div>
        <div style="margin-top: 32px; height: 700px">
          <img
            :src="getImageUrl()"
            alt="img"
            style="
              max-width: 640px;
              max-height: 100%;
              width: auto;
              height: auto;
            "
          />
        </div>
        <div>
          <form>
            <div align="right" style="margin: 10px 8px 0">
              <BtnComp_active btnLabel="닫기" v-close-popup />
              <!-- <q-btn
                class="btn_close"
                label="닫기"
                color="primary"
                @click="onClickClose"
              /> -->
            </div>
          </form>
        </div>
      </div>
    </q-card>
  </q-dialog>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, computed, defineEmits, inject } from "vue";
import * as util from "src/support/util";
import { api } from "boot/axios";

import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
const isOpen = ref(false);
const emit = defineEmits(["close"]);
const bus = inject("bus");
const dataParams = ref({
  dlvBrchCd: "",
  aprvDt: "",
  aprvNo: "",
  invoiceNo: "",
});
bus.off("acceptanceFile");
bus.on("acceptanceFile", async (data) => {
  isOpen.value = true;
  dataParams.value.dlvBrchCd = data.DLV_BRCH_CD;
  dataParams.value.aprvDt = data.A_APRV_DT;
  dataParams.value.aprvNo = data.APRV_NO;
});

function getImageUrl(type) {
  //https://ibjp.co.kr/ordLedger/popupAcceptance.act?DLV_BRCH_CD=1&APRV_DT=20241218&APRV_NO=1311
  //return `http://ibjp.co.kr/ordLedger/agreementFileDownLoad.act?DLV_BRCH_CD=${dataParams.value.dlvBrchCd}&APRV_DT=${dataParams.value.aprvDt}&APRV_NO=${dataParams.value.aprvNo}&invoiceNo=${dataParams.value.dlvBrchCd}${dataParams.value.aprvDt}${dataParams.value.aprvNo}`;
  // return `${process.env.API_URL}/ordLedger/acceptanceFile?DLV_BRCH_CD=${dataParams.value.dlvBrchCd}&APRV_DT=${dataParams.value.aprvDt}&APRV_NO=${dataParams.value.aprvNo}&invoiceNo=${dataParams.value.dlvBrchCd}${dataParams.value.aprvDt}${dataParams.value.aprvNo}`;
  return `${process.env.API_URL}/cmmnFile/agreementFileDownLoad2?DLV_BRCH_CD=${dataParams.value.dlvBrchCd}&APRV_DT=${dataParams.value.aprvDt}&APRV_NO=${dataParams.value.aprvNo}&invoiceNo=${dataParams.value.dlvBrchCd}${dataParams.value.aprvDt}${dataParams.value.aprvNo}`;
}

//---------------------------- Function ------------------------------//

async function onClickClose() {
  emit("close");
}
</script>
<style lang="scss" scoped>
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid $line-300;
}

.btn_trans {
  width: 100%;
  background-color: #1f3f82 !important;
  border-radius: 5px !important;
}

.btn_trans::v-deep span {
  color: #ffffff !important;
}

.btn_close {
  background-color: #999 !important;
  border-radius: 0 !important;
  &::after,
  &::before {
    display: none;
  }
}

.q-input {
  padding: 0 10px 0 5px;
}

.table_wrap {
  padding: 0 9px 0 9px;
}
</style>

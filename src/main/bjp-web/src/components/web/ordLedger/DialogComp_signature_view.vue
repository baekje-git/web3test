<template>
  <div class="signature_view_DialogComp_wrap">
    <div class="" @click="signature_view = true">
      <slot></slot>
    </div>

    <q-dialog v-model="signature_view">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="q-card_signature_view_title">
            <p>잔고확인</p>
            <img src="assets/icons/x-solid.svg" v-close-popup />
          </div>
        </q-card-section> -->
        <div class="dialog_header"><span>잔고확인</span></div>

        <div class="signature_view_wrap">
          <div class="signature_text">
            <p>{{ dataParam.custNm }}</p>
            <p>
              <span>잔액</span>
              <span>{{ util.getNumberFormat(dataParam.balanceAAmt) }}</span>
            </p>
            <p>
              <span>확인일자</span>
              <span>{{ dataParam.checkDt }}</span>
            </p>
            <p>
              <span>잔고일자</span>
              <span>{{ dataParam.aprvDt }}</span>
            </p>
          </div>
          <div class="signature">
            <img :src="srcUrl" />
          </div>
        </div>

        <q-card-actions class="q-card_btn_wrap">
          <div class="">
            <BtnComp_active btnLabel="닫기" v-close-popup />
          </div>
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, watch } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import BtnComp_active from "src/components/web/common/BtnComp_active.vue";

const userInfo = util.getUserData();
const signature_view = ref(false);

const props = defineProps({
  data: { type: Object },
});

const dataParam = ref({
  custNm: userInfo.CUST_NM,
  sn: "",
  balanceAAmt: "",
  aprvDt: "",
  checkDt: "",
});

const srcUrl = ref("");

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/ordLedger/showSign", {
    params: { SN: dataParam.value.sn },
  });

  //srcUrl.value = `${res.FILE_PATH}${res.FILE_NM}`;
  //2024-04-30
  srcUrl.value = "data:image/;base64," + res;
}

watch(signature_view, async () => {
  if (signature_view.value) {
    dataParam.value.sn = props.data.SN;
    dataParam.value.balanceAAmt = props.data.BALANCE_A_AMT;
    dataParam.value.aprvDt = props.data.APRV_DT;

    await fetchData();
  }
});
</script>

<style lang="scss" scoped>
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  margin-bottom: 10px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid $line-300;
}

.q-card_signature_view_title {
  @include flex_between;
  padding: 0 0 16px 0;
  border-bottom: solid 1px #999 !important;
  p {
    @include fw-7;
  }
  img {
    cursor: pointer;
  }
}

.signature_view_wrap {
  min-width: 720px;
  padding: 0 16px;
  .signature_text {
    @include flex_between;
    gap: 40px;
    p {
      @include fs-5;
      span {
        @include fs-5;
      }
      span:nth-child(1) {
        @include fs-5;
        @include fw-7;
        margin-right: 16px;
      }
    }
    p:nth-child(1) {
      @include fw-7;
      color: $b3;
    }
  }

  .signature {
    @include flex_center;
    width: 100%;
    height: 240px;
    margin: 16px 0 8px;
    background-color: rgb(245, 245, 245);
    img {
      width: 300px;
    }
  }
}

.q-card_btn_wrap {
  @include flex_center;
  margin: 8px;
  div {
    @include flex_center;
    border-radius: 2.4px;

    gap: 8px;
  }
}
</style>

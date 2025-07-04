<template>
  <div class="DocumentDialogComp_wrap">
    <!-- <div class="" @click="alert = true">
      <slot></slot>
    </div> -->

    <q-dialog v-model="isOpenDocumentPopup">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="q-card_document_title">
            <p>거래약정서확인</p>
            <img src="/src/assets/icons/x-solid.svg" v-close-popup>
          </div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>거래약정서확인</span>
        </div>

        <div class="document_img_wrap">
          <div class="">
            <div class="document_img_title">
              <span>거래약정서</span>
            </div>
            <div class="first_img">
              <img :src="getImageUrl('1')" alt="img" height="600" />
            </div>
          </div>
          <div class="">
            <div class="document_img_title">
              <span>개인정보이용동의서</span>
            </div>
            <div class="second_img">
              <img :src="getImageUrl('3')" alt="img" height="600" />
            </div>
          </div>
        </div>
        <q-card-actions class="q-card_btn_wrap">
          <div class="">
            <BtnComp_default
              class="btn-fill gray"
              btnLabel="닫기"
              v-close-popup
            />
          </div>
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, inject } from "vue";
import * as util from "src/support/util";

import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";

const userInfo = util.getUserData();
const bus = inject("bus");
const isOpenDocumentPopup = ref(false);

function getImageUrl(type) {
  return `${process.env.API_URL}/cmmnFile/agreementFileDownLoad?custCd=${userInfo.CUST_CD}&colGb2=${type}`;
}
bus.off("documentInfo");
bus.on("documentInfo", async () => {
  isOpenDocumentPopup.value = true;
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

.q-card_document_title {
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

.document_img_wrap {
  @include flex_center;
  padding: 0 40px;
  gap: 30px;

  .document_img_title {
    margin-bottom: 12px;
    span {
      @include fs-5;
      @include fw-7;
      color: $b3;
    }
  }

  .first_img,
  .second_img {
    @include boxshadow;
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

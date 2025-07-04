<template>
  <div class="zoom2-container">
    <img
      ref="imageRef"
      :src="imgUrl"
      alt="Image"
      style="pointer-events: auto"
    />
    <div class="zoom-icon" @click="zoomImage">
      <img src="assets/icons/magnify.svg" alt="icon" />
    </div>
  </div>
  <div class="zoom-btn">
    <BtnComp_mobile
      buttonText="확인"
      btnStyle="default"
      btnSize="lg"
      style="width: 160px"
      v-close-popup
    />
  </div>
</template>

<script setup>
import { ref, onBeforeUnmount, inject, defineProps, onMounted } from "vue";
import { api } from "boot/axios";
import mediumZoom from "medium-zoom";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";

const bus = inject("bus");
const imageRef = ref(null);

const props = defineProps({
  DLV_BRCH_CD: {
    type: String,
    default: "",
  },
  A_APRV_DT: {
    type: String,
    default: "",
  },
  APRV_NO: {
    type: String,
    default: "",
  },
});

const searchParam = ref({
  dlvBrchCd: props.DLV_BRCH_CD,
  aprvDt: props.A_APRV_DT,
  aprvNo: props.APRV_NO,
  invoiceNo: `${props.DLV_BRCH_CD}${props.A_APRV_DT}${props.APRV_NO}`,
});
console.log("searchParam,,,,,,,,", searchParam);
onMounted(async () => {
  const res = await api.get("/ordLedger/acceptanceFile", {
    params: searchParam.value,
  });
  const dataParams = ref({
    dlvBrchCd: "",
    aprvDt: "",
    aprvNo: "",
    invoiceNo: "",
  });
  dataParams.value.dlvBrchCd = props.DLV_BRCH_CD;
  dataParams.value.aprvDt = props.A_APRV_DT;
  dataParams.value.aprvNo = props.APRV_NO;
  imageRef.value = `${process.env.API_URL}/cmmnFile/agreementFileDownLoad2?DLV_BRCH_CD=${dataParams.value.dlvBrchCd}&APRV_DT=${dataParams.value.aprvDt}&APRV_NO=${dataParams.value.aprvNo}&invoiceNo=${dataParams.value.dlvBrchCd}${dataParams.value.aprvDt}${dataParams.value.aprvNo}`;
  console.log("imageRef.value,,,,,,,,", imageRef.value);
});

// 이미지 요소를 medium-zoom으로 초기화합니다.
const zoom = mediumZoom(imageRef.value);

// 컴포넌트가 해체되기 전에 medium-zoom 이벤트를 정리합니다.
onBeforeUnmount(() => {
  zoom.detach();
});

const zoomImage = () => {
  // 이미지를 확대합니다.
  zoom.zoom();
};
</script>

<style lang="scss" scoped>
.zoom-container {
  @include flex_center;
  position: relative;
  padding: 0 16px;
  img {
    width: 100%;
  }
  .zoom-icon {
    @include flex_center;
    position: absolute;
    bottom: 7%;
    right: 7%;
    img {
      width: 32px;
    }
  }
}
.zoom-btn {
  @include flex_center;
  margin-top: 16px;
}
</style>

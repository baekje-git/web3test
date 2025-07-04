<template>
  <div class="Record_Title">
    <slot></slot>
  </div>
  
  <FilterSingleComp_mobile_a
    :btnGroups="filterbtnGroups"
    :isActiveBtn="isActiveBtn"
    @selectedSingleBtn="(val) => onClickPeriod(val)"
  />
  <div class="Record_Table_wrap">
    <MyRecentDetailRecordTable_mobile :historyData="rows" />
  </div>
  <div class="m-loading_wrap_a" v-if="loading === true">
    <q-inner-loading class="loading" :showing="loading" color="primary"/>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import FilterComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterComp_mobile.vue";
import MyRecentDetailRecordTable_mobile from "src/components/mobile/DialogComp/TableDialogComp/4.myTableComp/MyRecentDetailRecordTable_mobile.vue";
import FilterSingleComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile.vue";
import FilterSingleComp_mobile_a from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile_a.vue";


//로딩
const loading = ref(true);
const show = ref(false)
const nodata_show = ref(false)

//필터표시
const model = ref();
const isActiveBtn = computed(() => {
  if (util.isEmpty(model.value)) {
    // return filterbtnGroups[0].buttons.findIndex((obj) => obj.btnLabel === "6개월");
    return '6개월'
  }
  return model.value;
});

const userInfo = util.getUserData();
const rows = ref([]);
const filterbtnGroups = [
  {
    title: "기간 설정",
    subtitle: "",
    gridStyle: 4,
    buttons: [
      { btnLabel: "당월" },
      { btnLabel: "전월" },
      { btnLabel: "3개월" },
      { btnLabel: "6개월" },
      { btnLabel: "1년" },
      { btnLabel: "2년" },
      { btnLabel: "3년" },
    ],
  },
];

const props = defineProps({
  itemcd: String,
});

const searchHistoryParam = ref({
  custCd: userInfo.CUST_CD,
  selItemCd: props.itemcd,
  sellMonth: "",
});

async function fetchItemList() {  
  loading.value = true;
  const res = await api.get("/ord/history", {
    params: searchHistoryParam.value,
  });

  rows.value = res;
  loading.value = false;
}

//기간 설정
function setDate(val) {
  if (val === "당월") {
    searchHistoryParam.value.sellMonth = "0";
  } else if (val === "전월") {
    searchHistoryParam.value.sellMonth = "-1";
  } else if (val === "3개월") {
    searchHistoryParam.value.sellMonth = "-3";
  } else if (val === "6개월") {
    searchHistoryParam.value.sellMonth = "-6";
  } else if (val === "1년") {
    searchHistoryParam.value.sellMonth = "-12";
  } else if (val === "2년") {
    searchHistoryParam.value.sellMonth = "-24";
  } else if (val === "3년") {
    searchHistoryParam.value.sellMonth = "-36";
  }

}

// 기간 버튼 클릭
async function onClickPeriod(val) {
  setDate(val);

  //기간표시
  // model.value = filterbtnGroups[0].buttons.findIndex(
  //   (obj) => obj.btnLabel === val
  // );
  model.value = val;

  await fetchItemList();
}

//내가 선택한 필터로 조회되게
onMounted(() => {
  let val = isActiveBtn.value;  
  setDate(val);
  //조회
  fetchItemList();
});
</script>

<style lang="scss" scoped>

/* loading_style */
.m-loading_wrap_a {
  height: 150px;
  .loading {
    justify-content: start !important;
    background: transparent;
    top: 200px;
    bottom: 0;
    left: 0;
    right: 0;

}
  //상단이 큰 넓은 다른 화면은 위에 130px주면 짤려서 나와서 일단
  //커스텀 하나 만듦
  &.custom {
    svg {
      margin-top: 200px !important;
    }
  }
}

</style>

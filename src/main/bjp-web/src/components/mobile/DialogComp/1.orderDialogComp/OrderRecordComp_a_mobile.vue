<template>
  <div class="Record_Title">
    <div class="table_badge_wrap" style="padding: 0">
      <!-- <q-badge class="table_badge_red">{{ mednm }}</q-badge>
      <q-badge class="table_badge_blue">{{ payitemnm }}</q-badge> -->
      <q-badge class="bdg-line normal">
        {{ mednm === "일" ? "일반" : mednm === "전" ? "전문" : mednm }}
      </q-badge>
      <q-badge
        class="bdg-line"
        :class="payitemnm === '비' ? 'nonpay' : payitemnm === '급' ? 'pay' : ''"
      >
        {{
          payitemnm === "비"
            ? "비급여"
            : payitemnm === "급"
            ? "급여"
            : payitemnm
        }}
      </q-badge>
      <!-- <template v-if="badge === 1 || badge === 3">
        <q-badge class="table_badge_red">{{ mednm }}</q-badge>
      </template>
      <template v-else>
        <q-badge class="table_badge_red">일</q-badge>
      </template>
      <template v-if="badge === 1 || badge === 2">
        <q-badge class="table_badge_blue">급</q-badge>
      </template>
      <template v-else>
        <q-badge class="table_badge_blue">비</q-badge>
      </template> -->
    </div>
    <span>{{ itemnm }}</span>
    <span>{{ unit }}</span>
  </div>
  <FilterSingleComp_mobile
    :btnGroups="filtergroup1"
    :isActiveBtn="isActiveBtn"
    @selectedSingleBtn="(val) => onClickPeriod(val)"
  />
  <div class="Record_Table_wrap">
    <OrderRecordTable_a_mobile :historyData="rows" />
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import FilterComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterComp_mobile.vue";
import OrderRecordTable_a_mobile from "src/components/mobile/DialogComp/TableDialogComp/1.orderTableComp/OrderRecordTable_a_mobile.vue";
import FilterSingleComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile.vue";

const userInfo = util.getUserData();

const props = defineProps({
  mednm: String,
  payitemnm: String,
  itemnm: String,
  unit: String,
  itemcd: String,
});

//필터표시
const model = defineModel();
const isActiveBtn = computed(() => {
  if (util.isEmpty(model.value)) {
    return filtergroup1[0].buttons.findIndex((obj) => obj.btnLabel === "6개월");
  }

  return model.value;
});

const rows = ref([]);

// const filterbtnGroups = [
//   {
//     title: "기간 설정",
//     subtitle: "",
//     gridStyle: 4,
//     btnDatePicker: "off",
//     buttons: [
//       { title: "당월" },
//       { title: "전월" },
//       { title: "3개월" },
//       { title: "6개월" },
//       { title: "1년" },
//       { title: "2년" },
//       { title: "3년" },
//     ],
//   },
// ];

const filtergroup1 = [
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

const searchHistoryParam = ref({
  custCd: userInfo.CUST_CD,
  selItemCd: props.itemcd,
  sellMonth: "-6",
});

async function fetchItemList() {
  const res = await api.get("/ord/history", {
    params: searchHistoryParam.value,
  });

  rows.value = res;
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
  model.value = filtergroup1[0].buttons.findIndex(
    (obj) => obj.btnLabel === val
  );

  await fetchItemList();
}

//내가 선택한 필터로 조회되게
onMounted(() => {
  let val = filtergroup1[0].buttons[isActiveBtn.value]?.btnLabel;
  setDate(val);
  //조회
  fetchItemList();
});
</script>

<style lang="scss" scoped></style>

<template>
  <div class="QA_wrap">
    <div class="InputGroup_wrap">
      <div class="flex_between" style="margin-bottom: 16px">
        <InputComp_mobile
          v-model:input_model_props="searchParam.qTitle"
          placeholder="제목"
          style="width: 240px"
          @keyup.enter="onClickSearch"
        />
        <BtnComp_mobile
          buttonText="검색"
          btnStyle="default"
          style="width: 64px"
          @click="onClickSearch"
        />
      </div>
      <div class="flex_between" style="margin-bottom: 16px">
        <DatePickerComp_mobile
          v-model:input_model_props="searchParam.startDt"
          style="width: 142px; gap: 8px"
        />
        <span style="font-size: 12px; color: #999999; margin: 0 4px 0 1px">
          -
        </span>
        <DatePickerComp_mobile
          v-model:input_model_props="searchParam.endDt"
          style="width: 142px; gap: 8px"
        />
      </div>
      <div class="">
        <FilterSingleComp_mobile_a
          :btnGroups="filtergroup1"
          :isActiveBtn="isActiveBtn"
          @selectedSingleBtn="(val) => onClickPeriod(val)"
        />
      </div>
    </div>
    <div class="QA_Table_wrap">
      <MyQATable_mobile :QAdata="rows" @refreshQAList="fetchItemList" />
    </div>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted, computed } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";

import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import InputComp_mobile from "src/components/mobile/__commonComp/inputComp/InputComp_mobile.vue";
import DatePickerComp_mobile from "src/components/mobile/__commonComp/datepickerComp/DatePickerComp_mobile.vue";
import MyQATable_mobile from "src/components/mobile/DialogComp/TableDialogComp/4.myTableComp/MyQATable_mobile.vue";
import FilterSingleComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile.vue";
import FilterSingleComp_mobile_a from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile_a.vue";

//필터표시
const model = ref("당월");
const isActiveBtn = computed(() => model.value);

const userInfo = util.getUserData();
const rows = ref([]);
const period = ref("");

const searchParam = ref({
  qTitle: "",
  startDt: "",
  endDt: "",
  custCd: userInfo.CUST_CD,
  loginDeptCd: userInfo.LOGIN_DEPT_CD,
  userGbCd: userInfo.USER_GB_CD, //30:약국개정
  loginBrchCd: userInfo.LOGIN_BRCH_CD,
});

const filtergroup1 = [
  {
    title: "",
    subtitle: "",
    gridStyle: 3,
    buttons: [
      { btnLabel: "당월" },
      { btnLabel: "전월" },
      { btnLabel: "3개월" },
    ],
  },
];

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
  const startDt = searchParam.value.startDt.replace(match, "");
  const endDt = searchParam.value.endDt.replace(match, "");

  searchParam.value.startDt = startDt;
  searchParam.value.endDt = endDt;

  const res = await api.post("/myPage/qnaList", searchParam.value);

  res.forEach((i) => {
    if (isVisibleNew(i)) {
      Object.assign(i, { NEW: "Y" });
    } else {
      Object.assign(i, { NEW: "N" });
    }
  });

  rows.value = res;
}

// 검색 버튼 클릭
async function onClickSearch() {
  await fetchItemList();
}

// 조회 조건 클릭(기간)
async function onClickPeriod(val) {
  if (val === "당월") {
    setDate(0);
  } else if (val === "전월") {
    setDate(1);
  } else if (val === "3개월") {
    setDate(3);
  }

  //필터표시
  // model.value = filtergroup1[0].buttons.findIndex((obj) => obj.btnLabel == val);

  model.value = val;

  await fetchItemList();
}

function getYYYYMMDD(date) {
  return (
    date.getFullYear() +
    "." +
    (date.getMonth() + 1).toString().padStart(2, "0") +
    "." +
    date.getDate().toString().padStart(2, "0")
  );
}

async function setDate(val) {
  if (!val) {
    val = 1;
  } else {
    val++;
  }

  const now = new Date();
  const year = now.getFullYear();
  const month = now.getMonth() + 1;
  const strtDt = new Date(year, month - val, 1);
  const endDt = new Date(year, month, 0);

  searchParam.value.startDt = getYYYYMMDD(strtDt);
  searchParam.value.endDt = getYYYYMMDD(endDt);
}

function isVisibleNew(row) {
  //문의일 기준
  const insDtm = moment(row.Q_DT.replace(/[^0-9]/g, "")).format("YYYYMMDD");
  const today = moment().format("YYYYMMDD");

  //답변일 기준
  const insDtm2 = moment(row.A_DT.replace(/[^0-9]/g, "")).format("YYYYMMDD");
  const today2 = moment().format("YYYYMMDD");

  const day = today - insDtm;
  const day2 = today2 - insDtm2;

  if (day <= 7 || day2 <= 7) {
    return true;
  } else {
    return false;
  }
}

onMounted(async () => {
  // let val = filtergroup1[0].buttons[model.value].btnLabel;

  let val = model.value;

  if (val === "당월") {
    setDate(0);
  } else if (val === "전월") {
    setDate(1);
  } else if (val === "3개월") {
    setDate(3);
  } else {
    setDate(0);
  }

  await fetchItemList();
});
</script>

<style lang="scss" scoped></style>

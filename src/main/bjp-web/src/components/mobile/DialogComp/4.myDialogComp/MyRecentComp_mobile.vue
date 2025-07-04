<template>
  <div class="Recent_wrap">
    <div class="BtnGroup_wrap">
      <FilterSingleComp_mobile_a
        :btnGroups="filtergroup1"
        :isActiveBtn="isActiveBtn"
        @selectedSingleBtn="(val) => onClickRcptBakBtn(val)"
      />
      <div class="flex_between" style="margin-bottom: 18px">
        <DatePickerComp_mobile
          v-model:input_model_props="searchParam.startDt"
          style="width: 110px; gap: 8px"
          @enter="onClickSearch"
        />
        <span style="font-size: 12px; color: #999999; margin: 0 4px 0 1px">
          -
        </span>
        <DatePickerComp_mobile
          v-model:input_model_props="searchParam.endDt"
          style="width: 110px; gap: 8px"
          @enter="onClickSearch"
        />
        <BtnComp_mobile
          buttonText="검색"
          btnStyle="default"
          style="width: 64px"
          @click="onClickSearch"
        />
      </div>
      <div class="" style="margin-bottom: 16px">
        <div class="btn-group-wrap">
          <div class="button-container">
            <q-btn
              v-model="searchParam.sellMonth"
              :class="thisMnth"
              label="당월"
              size="sm"
              style="min-width: 64px; line-height: normal"
              flat
              dense
              @click="onClickPeriod('당월')"
            />
            <q-btn
              v-model="searchParam.sellMonth"
              :class="bfrMnth"
              label="전월"
              size="sm"
              style="min-width: 64px; line-height: normal"
              flat
              dense
              @click="onClickPeriod('전월')"
            />
            <q-btn
              v-model="searchParam.sellMonth"
              :class="bfr3Mnth"
              label="3개월"
              size="sm"
              style="min-width: 64px; line-height: normal"
              flat
              dense
              @click="onClickPeriod('3개월')"
            />
            <q-btn
              v-model="searchParam.sellMonth"
              :class="bfr6nth"
              label="6개월"
              size="sm"
              style="min-width: 64px; line-height: normal"
              flat
              dense
              @click="onClickPeriod('6개월')"
            />
            <q-btn
              v-model="searchParam.sellMonth"
              :class="bfr1Year"
              label="1년"
              size="sm"
              style="min-width: 64px; line-height: normal"
              flat
              dense
              @click="onClickPeriod('1년')"
            />
            <q-btn
              v-model="searchParam.sellMonth"
              :class="bfr2Year"
              label="2년"
              size="sm"
              style="min-width: 64px; line-height: normal"
              flat
              dense
              @click="onClickPeriod('2년')"
            />
            <q-btn
              v-model="searchParam.sellMonth"
              :class="bfr3Year"
              label="3년"
              size="sm"
              style="min-width: 64px; line-height: normal"
              flat
              dense
              @click="onClickPeriod('3년')"
            />
          </div>
        </div>
        <!-- <BtnDateGroupComp_mobile btnStyle="active" /> -->
      </div>
      <!-- <BtnGroupComp_mobile
        :buttons="['당월', '전월', '3개월', '6개월', '1년', '2년', '3년']"
        btnStyle="active"
        style="margin-bottom: 16px"
        @onClickBtn="(val) => onClickPeriod(val)"
      /> -->
    </div>
    <div class="Recent_Table_wrap">
      <MyRecentTable_mobile :recentData="rows" :loading="loading" />
    </div>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted, computed, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import MyRecentTable_mobile from "src/components/mobile/DialogComp/TableDialogComp/4.myTableComp/MyRecentTable_mobile.vue";
import DatePickerComp_mobile from "src/components/mobile/__commonComp/datepickerComp/DatePickerComp_mobile.vue";
import FilterSingleComp_mobile_a from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile_a.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";

//로딩표시
const loading = ref(false);

//필터표시
const activeBtnModel = defineModel("activeBtnModel");
const periodBtnModel = defineModel("periodBtnModel");

const isActiveBtn = computed(() => {
  if (util.isEmpty(activeBtnModel.value)) {
    // return filtergroup1[0].buttons.findIndex((obj) => obj.btnLabel === "매출");
    return "매출";
  }

  return activeBtnModel.value;
});
const bus = inject("bus");
const userInfo = util.getUserData();
const rows = ref([]);

const filtergroup1 = [
  {
    title: "기간 설정",
    subtitle: "",
    gridStyle: 3,
    buttons: [{ btnLabel: "전체" }, { btnLabel: "매출" }, { btnLabel: "반품" }],
  },
];

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  searchGb: "0",
  startDt: "",
  endDt: "",
  sellMonth: "1",
});

//기간표시
const thisMnth = computed(() =>
  searchParam.value.sellMonth === "0"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfrMnth = computed(() =>
  searchParam.value.sellMonth === "1"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr3Mnth = computed(() =>
  searchParam.value.sellMonth === "3"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr6nth = computed(() =>
  searchParam.value.sellMonth === "6"
    ? " btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr1Year = computed(() =>
  searchParam.value.sellMonth === "12"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr2Year = computed(() =>
  searchParam.value.sellMonth === "24"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr3Year = computed(() =>
  searchParam.value.sellMonth === "36"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

//---------------------------- Function ------------------------------//
// 검색 버튼 클릭
async function onClickSearch() {
  await fetchItemList();
}

async function fetchItemList() {
  loading.value = true;
  const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
  const startDt = searchParam.value.startDt.replace(match, "");
  const endDt = searchParam.value.endDt.replace(match, "");

  searchParam.value.startDt = startDt;
  searchParam.value.endDt = endDt;

  const res = await api.get("/recently/searchItem", {
    params: searchParam.value,
  });

  rows.value = res;
  loading.value = false;
}

// 조회 조건 클릭(전체, 매출, 반품)
async function onClickRcptBakBtn(val) {
  if (val === "전체") {
    searchParam.value.searchGb = "0";
  } else if (val === "매출") {
    searchParam.value.searchGb = "1";
  } else if (val === "반품") {
    searchParam.value.searchGb = "2";
  }

  //조회필터표시
  if (!util.isEmpty(val)) {
    // activeBtnModel.value = filtergroup1[0].buttons.findIndex(
    //   (obj) => obj.btnLabel == val
    // );
    activeBtnModel.value = val;
  }
  await fetchItemList();
}

// 조회 조건 클릭(기간)
async function onClickPeriod(val) {
  if (val === "당월") {
    setDate(0);
    searchParam.value.sellMonth = "0";
    periodBtnModel.value = "0";
  } else if (val === "전월") {
    setDate(1);
    searchParam.value.sellMonth = "1";
    periodBtnModel.value = "1";
  } else if (val === "3개월") {
    setDate(3);
    searchParam.value.sellMonth = "3";
    periodBtnModel.value = "3";
  } else if (val === "6개월") {
    setDate(6);
    searchParam.value.sellMonth = "6";
    periodBtnModel.value = "6";
  } else if (val === "1년") {
    setDate(12);
    searchParam.value.sellMonth = "12";
    periodBtnModel.value = "12";
  } else if (val === "2년") {
    setDate(24);
    searchParam.value.sellMonth = "24";
    periodBtnModel.value = "24";
  } else if (val === "3년") {
    setDate(36);
    searchParam.value.sellMonth = "36";
    periodBtnModel.value = "36";
  }

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

bus.off("refSearch");
bus.on("refSearch", async () => {
  await fetchItemList();
});

onMounted(() => {
  let sellMonth = "1";
  if (!util.isEmpty(periodBtnModel.value)) {
    sellMonth = periodBtnModel.value;
  }
  searchParam.value.sellMonth = sellMonth;
  setDate(sellMonth);

  let val = filtergroup1[0].buttons[isActiveBtn.value]?.btnLabel;
  if (val === "전체") {
    searchParam.value.searchGb = "0";
  } else if (val === "매출") {
    searchParam.value.searchGb = "1";
  } else if (val === "반품") {
    searchParam.value.searchGb = "2";
  }

  setTimeout(() => {
    fetchItemList();
  }, 500);
});
</script>

<style lang="scss" scoped>
//기간 표시 스타일
.button-container {
  // .btn-focus {
  //   background-image: linear-gradient(to bottom, #1f3f82, #003853) !important;
  //   color: #ffffff !important;
  // }
}
</style>

<template>
  <div class="Record_Title">
    <div class="">
      <p class="flex_start">
        <slot name="title"></slot>
      </p>
      <p style="margin-top: 10px">
        <slot name="sub-title"></slot>
      </p>
    </div>
  </div>
  <div class="flex_between" style="margin-bottom: 16px">
    <DatePickerComp_mobile
      v-model:input_model_props="searchHistoryParam.expDt"
      placeholder="유효기간"
      style="width: 105px"
    />
    <div class="flex_center" style="gap: 8px">
      <InputComp_mobile
        v-model:input_model_props="searchHistoryParam.prodNo"
        placeholder="제조번호"
        style="width: 120px"
        @keyup.enter="onClickSearch"
      />
      <!-- <InputComp_mobile placeholder="일련번호" style="width: 72px" /> -->
    </div>
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
          v-model="searchHistoryParam.sellMonth"
          :class="thisMnth"
          label="당월"
          size="sm"
          style="min-width: 64px; line-height: normal"
          flat
          dense
          @click="onClickPeriod('당월')"
        />
        <q-btn
          v-model="searchHistoryParam.sellMonth"
          :class="bfrMnth"
          label="전월"
          size="sm"
          style="min-width: 64px; line-height: normal"
          flat
          dense
          @click="onClickPeriod('전월')"
        />
        <q-btn
          v-model="searchHistoryParam.sellMonth"
          :class="bfr3Mnth"
          label="3개월"
          size="sm"
          style="min-width: 64px; line-height: normal"
          flat
          dense
          @click="onClickPeriod('3개월')"
        />
        <q-btn
          v-model="searchHistoryParam.sellMonth"
          :class="bfr6nth"
          label="6개월"
          size="sm"
          style="min-width: 64px; line-height: normal"
          flat
          dense
          @click="onClickPeriod('6개월')"
        />
        <q-btn
          v-model="searchHistoryParam.sellMonth"
          :class="bfr1Year"
          label="1년"
          size="sm"
          style="min-width: 64px; line-height: normal"
          flat
          dense
          @click="onClickPeriod('1년')"
        />
        <q-btn
          v-model="searchHistoryParam.sellMonth"
          :class="bfr2Year"
          label="2년"
          size="sm"
          style="min-width: 64px; line-height: normal"
          flat
          dense
          @click="onClickPeriod('2년')"
        />
        <q-btn
          v-model="searchHistoryParam.sellMonth"
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
    <!-- <BtnGroupComp_mobile
      :buttons="['당월', '전월', '3개월', '6개월', '1년', '2년', '3년']"
      btnStyle="active"
      @on-click-btn="(val) => onClickPeriod(val)"
    /> -->
  </div>

  <div class="Record_Table_wrap">
    <ReturnRecordTable_a_mobile
      ref="returnRecordTableA"
      :search-param="searchHistoryParam"
      :return-data="returnData"
      :medCd="medCd"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import * as util from "src/support/util";

import ReturnRecordTable_a_mobile from "src/components/mobile/DialogComp/TableDialogComp/2.returnTableComp/ReturnRecordTable_a_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import InputComp_mobile from "src/components/mobile/__commonComp/inputComp/InputComp_mobile.vue";
import DatePickerComp_mobile from "src/components/mobile/__commonComp/datepickerComp/DatePickerComp_mobile.vue";
import BtnGroupComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnGroupComp_mobile.vue";

const props = defineProps({
  itemcd: String,
  medCd: String,
  returnData: Array,
});

//필터표시
const model = defineModel();
const isActiveSellMonth = computed(() => {
  return model.value;
});

const userInfo = util.getUserData();
const returnRecordTableA = ref(null);

const searchHistoryParam = ref({
  custCd: userInfo.CUST_CD,
  selItemCd: props.itemcd,
  sellMonth: "-6",
  prodNo: "",
  expDt: "",
});

//기간표시
const thisMnth = computed(() =>
  searchHistoryParam.value.sellMonth === "0"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfrMnth = computed(() =>
  searchHistoryParam.value.sellMonth === "-1"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr3Mnth = computed(() =>
  searchHistoryParam.value.sellMonth === "-3"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr6nth = computed(() =>
  searchHistoryParam.value.sellMonth === "-6"
    ? " btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr1Year = computed(() =>
  searchHistoryParam.value.sellMonth === "-12"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr2Year = computed(() =>
  searchHistoryParam.value.sellMonth === "-24"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr3Year = computed(() =>
  searchHistoryParam.value.sellMonth === "-36"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

// 기간 버튼 클릭
function onClickPeriod(val) {
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

  model.value = searchHistoryParam.value.sellMonth;
}

// 검색 버튼 클릭
async function onClickSearch() {
  await returnRecordTableA.value.fetchHistoryList();
}

onMounted(() => {
  if (util.isEmpty(isActiveSellMonth.value)) {
    searchHistoryParam.value.sellMonth = "-6";
  } else {
    searchHistoryParam.value.sellMonth = isActiveSellMonth.value;
  }

  setTimeout(() => {
    returnRecordTableA.value.fetchHistoryList();
  }, 500);
});
</script>

<style lang="scss" scoped>
//기간 표시 스타일
// .button-container {
//   .btn-focus {
//     background-image: linear-gradient(to bottom, #1f3f82, #003853) !important;
//     color: #ffffff !important;
//   }
// }
</style>

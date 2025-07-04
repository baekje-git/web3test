<template>
  <div class="">
    <q-table
      class="salesTable_1e my-sticky-header-table"
      :rows="rows"
      :columns="columns"
      row-key="ROWNUMBER"
      table-header-class="table_header"
      dense
      virtual-scroll
      v-model:pagination="pagination"
      :loading="loading"
      :selected="selected"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <div class="row justify-center q-mt-md">
        <q-pagination
          v-model="pagination.page"
          color="grey-8"
          :max="pagesNumber"
          size="sm"
        />
      </div>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="flex_between">
            <div class="q-table_header_wrap">
              <div class="q-table_header_title">
                <p>보상내역</p>
              </div>
            </div>
          </div>
          <div class="q-table_header_sub_wrap flex_between">
            <div class="flex_start">
              <!-- <SearchComp
                placeholder1="보험코드/품목 입력"
                placeholder2="제약회사 입력"
              /> -->
              <div class="SearchComp_wrap">
                <!-- <q-select
                  v-model="searchParam.keyword"
                  use-input
                  fill-input
                  hide-dropdown-icon
                  input-class="input_search"
                  borderless
                  dense
                  placeholder="품목명/보험코드 입력"
                  :options="autoCompleteData"
                  @input-value="setKeyword"
                  @keyup.enter="onClickSearch"
                /> -->
                <q-input
                  v-model="searchParam.keyword"
                  input-class="input_search"
                  borderless
                  dense
                  placeholder="품목명/보험코드 입력"
                  @keyup.enter="onClickSearch"
                  @focus="(input) => input.target.select()"
                  autofocus
                />
                <q-input
                  v-model="searchParam.makerNm"
                  input-class="input_search"
                  borderless
                  dense
                  placeholder="제약회사명 입력"
                  @keyup.enter="onClickSearch"
                  @focus="(input) => input.target.select()"
                />
              </div>
              <div class="flex_center">
                <!-- <DatePickerComp_b /> -->
                <div class="flex_between">
                  <div class="DatePickerComp_b_wrap">
                    <div class="date_input_wrap">
                      <!-- <p>
                        {{ date1 }}
                      </p> -->
                      <q-input
                        input-class="input_default"
                        v-model="searchParam.startDt"
                        borderless
                        dense
                        @blur="validDate"
                        @focus="(input) => input.target.select()"
                        mask="####-##-##"
                      />
                    </div>
                    <div class="date_btn_wrap">
                      <q-btn class="calendar_icon" unelevated dense>
                        <img
                          src="assets/icons/calendar-days-solid.svg"
                          alt="calendar"
                        />
                        <q-popup-proxy
                          @before-show="updateProxy1"
                          cover
                          transition-show="scale"
                          transition-hide="scale"
                        >
                          <q-date
                            v-model="proxyDate1"
                            @click="updateStartDt"
                            :default-date="today"
                          >
                            <div
                              class="row items-center justify-end q-gutter-sm"
                            >
                              <q-btn
                                label="취소"
                                color="primary"
                                flat
                                v-close-popup
                              />
                              <q-btn
                                label="확인"
                                color="primary"
                                flat
                                @click="save1"
                                v-close-popup
                              />
                            </div>
                          </q-date>
                        </q-popup-proxy>
                      </q-btn>
                    </div>
                  </div>
                  <p>~</p>
                  <div class="DatePickerComp_b_wrap">
                    <div class="date_input_wrap">
                      <!-- <p>
                        {{ date2 }}
                      </p> -->
                      <q-input
                        input-class="input_default"
                        v-model="searchParam.endDt"
                        borderless
                        dense
                        @blur="validDate"
                        @focus="(input) => input.target.select()"
                        mask="####-##-##"
                      />
                    </div>
                    <div class="date_btn_wrap">
                      <q-btn class="calendar_icon" unelevated dense>
                        <img
                          src="assets/icons/calendar-days-solid.svg"
                          alt="calendar"
                        />
                        <q-popup-proxy
                          @before-show="updateProxy2"
                          cover
                          transition-show="scale"
                          transition-hide="scale"
                        >
                          <q-date
                            v-model="proxyDate2"
                            @click="updateEndDt"
                            :default-date="today"
                          >
                            <div
                              class="row items-center justify-end q-gutter-sm"
                            >
                              <q-btn
                                label="취소"
                                color="primary"
                                flat
                                v-close-popup
                              />
                              <q-btn
                                label="확인"
                                color="primary"
                                flat
                                @click="save2"
                                v-close-popup
                              />
                            </div>
                          </q-date>
                        </q-popup-proxy>
                      </q-btn>
                    </div>
                  </div>
                </div>
                <BtnComp_default btnLabel="검색" @click="onClickSearch" />
              </div>
              <!-- <BtnComp_options /> -->
              <div class="Btn_options_wrap">
                <q-btn
                  :class="periodClassA"
                  label="당월"
                  dense
                  @click="onClickPeriod(0)"
                />
                <q-btn
                  :class="periodClassB"
                  label="전월"
                  dense
                  @click="onClickPeriod(1)"
                />
                <q-btn
                  :class="periodClassC"
                  label="3개월"
                  dense
                  @click="onClickPeriod(3)"
                />
                <q-btn
                  :class="periodClassD"
                  label="6개월"
                  dense
                  @click="onClickPeriod(6)"
                />
                <q-btn
                  :class="periodClassE"
                  label="1년"
                  dense
                  @click="onClickPeriod(12)"
                />
                <q-btn
                  :class="periodClassF"
                  label="2년"
                  dense
                  @click="onClickPeriod(24)"
                />
                <q-btn
                  :class="periodClassG"
                  label="3년"
                  dense
                  @click="onClickPeriod(36)"
                />
              </div>
            </div>
            <div class="flex_center">
              <BtnComp_purple btn_purpleLabel="엑셀" @click="exportTable" />
            </div>
          </div>
        </div>
      </template>

      <template v-slot:header="props">
        <q-tr :props="props" class="table_header">
          <q-th key="APPLY_DT" :props="props" style="border-bottom: none">
            {{ props.cols[0].label }}
          </q-th>
          <q-th key="SALE_DT" :props="props" style="border-bottom: none">
            {{ props.cols[1].label }}
          </q-th>
          <q-th key="MAKER_NM" :props="props" style="border-bottom: none">
            {{ props.cols[2].label }}
          </q-th>
          <q-th key="ITEM_NM_UNIT" :props="props" style="border-bottom: none">
            {{ props.cols[3].label }}
          </q-th>
          <q-th key="BOHUM_CD" :props="props" style="border-bottom: none">
            {{ props.cols[4].label }}
          </q-th>
          <q-th style="width: 232px; padding: 0; border-bottom-width: 0">
            <p class="table_header_sub_top">
              <q-th> 보험약가 </q-th>
            </p>
            <p class="">
              <q-td style="width: 116px; border: none">변경 전</q-td>
              <q-td style="width: 116px; border-bottom: none">변경 후</q-td>
            </p>
          </q-th>
          <q-th key="PRICE_AMT" :props="props" style="border-bottom: none">
            {{ props.cols[7].label }}
          </q-th>
          <q-th key="ITEM_QTY" :props="props" style="border-bottom: none">
            {{ props.cols[8].label }}
          </q-th>
          <q-th key="AMT" :props="props" style="border-bottom: none">
            {{ props.cols[9].label }}
          </q-th>
        </q-tr>
      </template>

      <template v-slot:top-row v-if="rows.length === 0 && searched">
        <q-tr class="no-date">
          <q-td colspan="100%">
            <span>검색된 결과가 없습니다.</span>
          </q-td>
        </q-tr>
      </template>

      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td
            key="APPLY_DT"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ props.row.APPLY_DT }}
          </q-td>
          <q-td
            key="SALE_DT"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ props.row.SALE_DT }}
          </q-td>
          <q-td
            key="MAKER_NM"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ props.row.MAKER_NM }}
          </q-td>
          <q-td
            key="ITEM_NM_UNIT"
            :props="props"
            style="text-align: left; cursor: pointer"
            @click="onClickRow(props.row)"
          >
            <div class="q-table_item" style="margin-left: 8px">
              <div class="q-table_badge_wrap">
                <!-- <q-badge class="q-table_badge_red">
                  {{ props.row.MED_NM }}</q-badge
                >
                <q-badge class="q-table_badge_blue">{{
                  props.row.PAY_ITEM_NM
                }}</q-badge> -->

                <q-badge class="bdg-line normal">
                  {{
                    props.row.MED_NM === "일"
                      ? "일반"
                      : props.row.MED_NM === "전"
                      ? "전문"
                      : props.row.MED_NM
                  }}
                </q-badge>
                <q-badge
                  class="bdg-line"
                  :class="props.row.PAY_ITEM_NM === '비' ? 'nonpay' : 'pay'"
                >
                  {{
                    props.row.PAY_ITEM_NM === "급"
                      ? "급여"
                      : props.row.PAY_ITEM_NM === "비"
                      ? "비급여"
                      : props.row.PAY_ITEM_NM
                  }}
                </q-badge>
              </div>
              <div class="q-table_itemTitle ellipsis" style="max-width: 380px">
                {{ props.row.ITEM_NM_UNIT }}
              </div>
            </div>
          </q-td>
          <q-td
            key="BOHUM_CD"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ props.row.BOHUM_CD }}
          </q-td>
          <q-td style="padding: 0 16px 0 16px">
            <q-td
              key="WP2_OLD"
              :props="props"
              style="
                padding: 0;
                border-bottom: none;
                cursor: pointer;
                background-color: transparent;
              "
              @click="onClickRow(props.row)"
            >
              <p style="width: 92px; padding: 9.5px 0">
                {{ util.getNumberFormat(props.row.WP2_OLD) }}
              </p>
            </q-td>
            <q-td
              key="WP2_NEW"
              :props="props"
              style="
                padding: 0;
                border-bottom: none;
                cursor: pointer;
                background-color: transparent;
              "
              @click="onClickRow(props.row)"
            >
              <p style="width: 92px; padding: 9.5px 0 9.5px 8px">
                {{ util.getNumberFormat(props.row.WP2_NEW) }}
              </p>
            </q-td>
          </q-td>
          <q-td
            key="PRICE_AMT"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ util.getNumberFormat(props.row.PRICE_AMT) }}
          </q-td>
          <q-td
            key="ITEM_QTY"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ props.row.ITEM_QTY }}
          </q-td>
          <q-td
            key="AMT"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ util.getNumberFormat(props.row.AMT) }}
          </q-td>
        </q-tr>

        <q-tr
          v-if="isShowSumRow(props)"
          :props="props"
          style="padding: 0; background-color: rgba(31, 63, 130, 0.1)"
        >
          <q-td style="text-align: center; font-weight: 700" colspan="8">
            예정일 소계
          </q-td>

          <q-td key="AMT" :props="props">
            {{ util.getNumberFormat(totalAmtSum(props)) }}
          </q-td>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, computed, onMounted, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";
import { exportFile, useQuasar } from "quasar";
import * as XLSX from "xlsx";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import DatePickerComp_b from "src/components/web/common/DatePickerComp_b.vue";
import BtnComp_purple from "src/components/web/common/BtnComp_purple.vue";
import BtnComp_options from "src/components/web/common/BtnComp_options.vue";
import SearchComp_select from "../common/SearchComp_select.vue";

const bus = inject("bus");
const userInfo = util.getUserData();
const itemData = [];
const options = ref([]);

const model = ref(null);
const loading = ref(false);
const searched = ref(false);
const selected = ref([]);

const rowval = () => {
  return rows.value.length;
};

const rows = ref([]);
const period = ref("");

const pagination = ref({
  sortBy: "desc",
  descending: false,
  page: 1,
  rowsPerPage: 15,
  // rowsNumber: xx if getting data from a server
});

const pagesNumber = computed(() =>
  Math.ceil(rows.value.length / pagination.value.rowsPerPage)
);

const periodClassA = computed(() =>
  period.value === 0 ? "btn_options btn_active" : "btn_options"
);
const periodClassB = computed(() =>
  period.value === 1 ? "btn_options btn_active" : "btn_options"
);
const periodClassC = computed(() =>
  period.value === 3 ? "btn_options btn_active" : "btn_options"
);
const periodClassD = computed(() =>
  period.value === 6 ? "btn_options btn_active" : "btn_options"
);
const periodClassE = computed(() =>
  period.value === 12 ? "btn_options btn_active" : "btn_options"
);
const periodClassF = computed(() =>
  period.value === 24 ? "btn_options btn_active" : "btn_options"
);
const periodClassG = computed(() =>
  period.value === 36 ? "btn_options btn_active" : "btn_options"
);

const searchParam = ref({
  keyword: "",
  makerNm: "",
  startDt: "",
  endDt: "",
  brchCd: userInfo.BRCH_CD,
  custCd: userInfo.CUST_CD,
});

const autoCompleteData = ref([]);
const itemNmData = ref([]);

const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  if (startDt === "" || endDt === "") {
    alert("기간을 입력해 주시기 바랍니다.");
    return;
  }

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  loading.value = true;
  const res = await api.get("/cmpnsDtl/searchItem", {
    params: searchParam.value,
  });
  searched.value = true;
  loading.value = false;

  rows.value = res;
}

// 검색버튼 클릭
async function onClickSearch() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  if (!moment(startDt).isBefore(endDt) && !moment(startDt).isSame(endDt)) {
    alert("입력한 끝 날짜가 시작 날짜보다 빠릅니다");
    searchParam.value.startDt = "";
    date1.value = "";

    return;
  }
  await fetchItemList();
}

// 조회기간 버튼 클릭
async function onClickPeriod(val) {
  period.value = val;

  await setDate(val);
  await fetchItemList();
}

// 그리드 row 클릭
async function onClickRow(row) {
  const index = selected.value.indexOf(row);

  if (index === -1) {
    selected.value = [];
    selected.value.push(row);
  } else {
    selected.value.splice(index, 1);
  }
}

function wrapCsvValue(val, formatFn, row) {
  let formatted = formatFn !== void 0 ? formatFn(val, row) : val;

  formatted =
    formatted === void 0 || formatted === null ? "" : String(formatted);

  formatted = formatted.split('"').join('""');

  return `"${formatted}"`;
}

function exportTable() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  api
    .get("excel/cmpnsDtl", {
      params: searchParam.value,
      responseType: "arraybuffer",
    })
    .then(function (res) {
      const url = window.URL.createObjectURL(new Blob([res]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute(
        "download",
        "보상내역_" +
          searchParam.value.startDt +
          "_" +
          searchParam.value.endDt +
          ".xls"
      );
      document.body.appendChild(link);
      link.click();
      link.remove();
    })
    .catch(function (error) {
      console.log(error);
    });
}

function totalAmtSum(props) {
  if (rows.value.length > 0) {
    let nowKey = props.row.APPLY_DT;

    const tempList = rows.value.filter((row) => row.APPLY_DT === nowKey);

    return tempList.reduce((total, row) => total + row.AMT, 0);
  }
}

function isShowSumRow(props) {
  if (rows.value.length > 0) {
    let nowKey = props.row.APPLY_DT;

    if (rows.value[props.rowIndex + 1]) {
      let nextKey = rows.value[props.rowIndex + 1].APPLY_DT;

      if (nowKey !== nextKey) {
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  }
}

function filterFn(val, update, abort) {
  if (val !== "") {
    const needle = val.toLocaleLowerCase();
    options.value = itemData.value.filter(
      (v) => v.toLocaleLowerCase().indexOf(needle) > -1
    );
  } else {
    options.value = [];
  }
}

/*********** datepicker ***********/
const date1 = ref("");
const date2 = ref("");
const proxyDate1 = ref("");
const proxyDate2 = ref("");

const updateProxy1 = () => {
  proxyDate1.value = date1.value;
};
const save1 = () => {
  date1.value = proxyDate1.value;
};
const updateProxy2 = () => {
  proxyDate2.value = date2.value;
};
const save2 = () => {
  date2.value = proxyDate2.value;
};

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

  date1.value = getYYYYMMDD(strtDt);
  date2.value = getYYYYMMDD(endDt);
  proxyDate1.value = getYYYYMMDD(strtDt);
  proxyDate2.value = getYYYYMMDD(endDt);

  searchParam.value.startDt = date1.value.replace(match, "");
  searchParam.value.endDt = date2.value.replace(match, "");
}

function updateStartDt() {
  searchParam.value.startDt = proxyDate1.value.replace(match, "");
}

function updateEndDt() {
  searchParam.value.endDt = proxyDate2.value.replace(match, "");
}

function validDate() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  if (startDt) {
    if (!moment(startDt, "YYYY-MM-DD", true).isValid()) {
      alert("유효하지 않은 날짜 형식입니다.");
      searchParam.value.startDt = "";
      date1.value = "";

      return;
    }
  }

  if (endDt) {
    if (!moment(endDt, "YYYY-MM-DD", true).isValid()) {
      alert("유효하지 않은 날짜 형식입니다.");
      searchParam.value.endDt = "";
      date2.value = "";

      return;
    }
  }
}
/**********************************/

// 자동완성
bus.on("getAutoCompleteData", async (data) => {
  if (data) {
    itemNmData.value = await data.map((obj) => obj.ITEM_NM);
  }
});

function setKeyword(val) {
  searchParam.value.keyword = val;

  if (val !== "") {
    const needle = val.toLocaleLowerCase();
    autoCompleteData.value = itemNmData.value.filter(
      (v) => v.toLocaleLowerCase().indexOf(needle) > -1
    );
  } else {
    autoCompleteData.value = [];
  }
}

onMounted(async () => {
  await onClickPeriod(3);
  await bus.emit("onLoadGetAutoCompleteData");
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "APPLY_DT",
    label: "시행일",
    align: "center",
    required: true,
    field: "APPLY_DT",
  },
  {
    name: "SALE_DT",
    label: "승인일자",
    align: "center",
    field: "SALE_DT",
  },
  { name: "MAKER_NM", label: "제약회사", field: "MAKER_NM", align: "center" },
  {
    name: "ITEM_NM_UNIT",
    label: "품목 및 규격",
    field: "ITEM_NM_UNIT",
    align: "center",
  },
  { name: "BOHUM_CD", label: "보험코드", field: "BOHUM_CD", align: "center" },
  { name: "WP2_OLD", label: "변경 전", field: "WP2_OLD", align: "center" },
  { name: "WP2_NEW", label: "변경 후", field: "WP2_NEW", align: "center" },
  {
    name: "PRICE_AMT",
    label: "차액",
    field: "PRICE_AMT",
    align: "center",
  },
  {
    name: "ITEM_QTY",
    label: "수량",
    field: "ITEM_QTY",
    align: "center",
  },
  {
    name: "AMT",
    label: "보상금액",
    field: "AMT",
    align: "center",
  },
]);
</script>

<style lang="scss" scoped>
.salesTable_1e {
  // height: 736px;
  height: 690px;
  //overflow-y: auto;
}
.flex_center {
  @include flex_center;
  gap: 8px;
}
.flex_end {
  @include flex_end;
  gap: 8px;
}
.flex_start {
  @include flex_start;
  gap: 40px;
}
.flex_between {
  @include flex_between;
}

.btn_active {
  @include gradation;
  border-radius: 5px;
  color: $w;
  padding: 0 16px;
}
</style>

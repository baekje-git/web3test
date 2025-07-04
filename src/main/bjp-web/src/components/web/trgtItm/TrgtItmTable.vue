<template>
  <div class="">
    <q-table
      class="salesTable_1e my-sticky-header-table"
      :rows="rows"
      :columns="columns"
      row-key="ROWNUMBER"
      table-header-class="table_header"
      dense
      v-model:pagination="pagination"
      :loading="loading"
      :selected="selected"
      :rows-per-page-options="rowsPerPageOptions"
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
          <!-- <div class="flex_between">
            <div class="q-table_header_wrap">
              <div class="q-table_header_title">
                <p>대상품목</p>
              </div>
            </div>
          </div> -->
          <!-- <div class="q-table_header_sub_wrap flex_between"> -->
          <div class="q-table_header_wrap flex_between">
            <div class="flex_start">
              <!-- <SearchComp
                placeholder1="보험코드/품목 입력"
                placeholder2="제약사 입력"
              /> -->
              <!-- <div class="q-table_radio_wrap">
                <RadioComp_a
                  v-model="searchParam.selItemYn"
                  :radio_options="salesRadioOptions"
                ></RadioComp_a>
              </div> -->
              <div class="q-table_radio_wrap">
                <q-option-group
                  v-model="searchParam.selItemYn"
                  :options="salesRadioOptions"
                  color="blue-10"
                  size="xs"
                  inline
                  dense
                  @update:model-value="changeRadio"
                />
              </div>
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
                  input-class="input_search1"
                  borderless
                  dense
                  placeholder="품목명/보험코드 입력"
                  @keyup.enter="onClickSearch"
                  @focus="(input) => input.target.select()"
                  autofocus
                />
                <q-input
                  v-model="searchParam.makerNm"
                  input-class="input_search2"
                  borderless
                  dense
                  placeholder="제약회사명 입력"
                  @keyup.enter="onClickSearch"
                  @focus="(input) => input.target.select()"
                />
                <BtnComp_default btnLabel="검색" @click="onClickSearch" />
              </div>
              <!-- <div class="flex_center">
                <div class="flex_between">
                  <div class="DatePickerComp_b_wrap">
                    <div class="date_input_wrap">
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
                          <q-date v-model="proxyDate1" @click="updateStartDt">
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
                          <q-date v-model="proxyDate2" @click="updateEndDt">
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
              </div> -->
              <!-- <BtnComp_options /> -->
              <!-- <div class="Btn_options_wrap">
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
              </div> -->
            </div>
            <div class="flex_center">
              <BtnComp_default
                class="btn-line default btn-pd12 btn-excel"
                btn-label="엑셀"
                @click="exportTable"
              />
            </div>
          </div>
        </div>
      </template>

      <template v-slot:header="props">
        <q-tr :props="props" class="table_header">
          <q-th key="APPLY_DT" :props="props">
            {{ props.cols[0].label }}
          </q-th>
          <q-th key="APPLY_DT" :props="props">
            {{ props.cols[1].label }}
          </q-th>
          <q-th key="MAKER_NM" :props="props">
            {{ props.cols[2].label }}
          </q-th>
          <q-th key="ITEM_NM_UNIT" :props="props">
            {{ props.cols[3].label }}
          </q-th>
          <q-th key="BOHUM_CD" :props="props">
            {{ props.cols[4].label }}
          </q-th>
          <q-th class="td-multi">
            <p class="table_header_sub_top">
              <q-th> 보험약가 </q-th>
            </p>
            <p class="table_header_sub_bottom">
              <q-th>변경 전</q-th>
              <q-th>변경 후</q-th>
            </p>
          </q-th>
          <q-th key="PRICE_AMT" :props="props">
            {{ props.cols[7].label }}
          </q-th>
          <q-th class="td-multi">
            <p class="table_header_sub_top">
              <q-th> 최근출하수량 </q-th>
            </p>
            <p class="table_header_sub_bottom">
              <q-th key="THIS_MONTH" :props="props">{{
                props.cols[8].label
              }}</q-th>
              <q-th key="BEFORE_MONTH" :props="props">{{
                props.cols[9].label
              }}</q-th>
            </p>
          </q-th>
          <q-th key="AVAIL_QTY" :props="props"> 보상수량 </q-th>
          <q-th key="ESTM_AMT" :props="props">
            {{ props.cols[11].label }}
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
          <q-td key="ROWNUMBER" :props="props" @click="onClickRow(props.row)">
            <div>{{ props.row.ROWNUMBER }}</div>
          </q-td>
          <q-td key="APPLY_DT" :props="props" @click="onClickRow(props.row)">
            <div>{{ props.row.APPLY_DT }}</div>
          </q-td>
          <q-td key="MAKER_NM" :props="props" @click="onClickRow(props.row)">
            <div class="q-table_itemTitleSub ellipsis">
              {{ props.row.MAKER_NM }}
              <q-tooltip>{{ props.row.MAKER_NM }}</q-tooltip>
            </div>
          </q-td>
          <q-td
            key="ITEM_NM_UNIT"
            :props="props"
            @click="onClickRow(props.row)"
          >
            <div class="q-table_item">
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
              <div class="q-table_itemTitle ellipsis td-prd_name">
                {{ props.row.ITEM_NM_UNIT }}
              </div>
            </div>
          </q-td>
          <q-td key="BOHUM_CD" :props="props" @click="onClickRow(props.row)">
            <div>{{ props.row.BOHUM_CD }}</div>
          </q-td>
          <q-td class="td-multi">
            <q-td key="WP2_OLD" :props="props" @click="onClickRow(props.row)">
              <div>
                {{ util.getNumberFormat(props.row.WP2_OLD) }}
              </div>
            </q-td>
            <q-td key="WP2_NEW" :props="props" @click="onClickRow(props.row)">
              <div>
                {{ util.getNumberFormat(props.row.WP2_NEW) }}
              </div>
            </q-td>
          </q-td>
          <q-td key="PRICE_AMT" :props="props" @click="onClickRow(props.row)">
            <div>{{ util.getNumberFormat(props.row.PRICE_AMT) }}</div>
          </q-td>
          <q-td class="td-multi">
            <q-td
              key="THIS_MONTH"
              :props="props"
              @click="onClickRow(props.row)"
            >
              <p>
                {{ props.row.THIS_MONTH }}
              </p>
            </q-td>
            <q-td
              key="BEFORE_MONTH"
              :props="props"
              @click="onClickRow(props.row)"
            >
              <p>
                {{ props.row.BEFORE_MONTH }}
              </p>
            </q-td>
          </q-td>
          <q-td key="AVAIL_QTY" :props="props" @click="onClickRow(props.row)">
            <div>{{ props.row.AVAIL_QTY }}</div>
          </q-td>
          <q-td key="ESTM_AMT" :props="props" @click="onClickRow(props.row)">
            <div>{{ util.getNumberFormat(props.row.ESTM_AMT) }}</div>
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
import { exportFile, useQuasar } from "quasar";
import * as XLSX from "xlsx";
import moment from "moment";

import RadioComp_a from "src/components/web/common/RadioComp_a.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import DatePickerComp_b from "src/components/web/common/DatePickerComp_b.vue";
import BtnComp_purple from "src/components/web/common/BtnComp_purple.vue";
import BtnComp_options from "src/components/web/common/BtnComp_options.vue";
import SearchComp_select from "../common/SearchComp_select.vue";

const userInfo = util.getUserData();
const bus = inject("bus");
const loading = ref(false);
const selected = ref([]);

const rowval = () => {
  return rows.value.length;
};

const rows = ref([]);
const period = ref("");
const searched = ref(false);

const pagination = ref({
  sortBy: "desc",
  descending: false,
  page: 1,
  rowsPerPage: 15,
  // rowsNumber: xx if getting data from a server
});

const rowsPerPageOptions = computed(() => {
  return [10, 15, 20, 25, 50];
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
  selItemYn: "0",
  brchCd: userInfo.BRCH_CD,
  custCd: userInfo.CUST_CD,
});

const salesRadioOptions = [
  {
    label: "전체",
    value: "0",
  },
  {
    label: "매출",
    value: "1",
  },
];

const autoCompleteData = ref([]);
const itemNmData = ref([]);

const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  // const startDt = searchParam.value.startDt;
  // const endDt = searchParam.value.endDt;

  // if (startDt === "" || endDt === "") {
  //   alert("기간을 입력해 주시기 바랍니다.");
  //   return;
  // }

  // searchParam.value.startDt = startDt.replace(match, "");
  // searchParam.value.endDt = endDt.replace(match, "");

  console.log(searchParam.value);

  loading.value = true;
  const res = await api.get("/trgtItem/searchItem", {
    params: searchParam.value,
  });
  loading.value = false;
  rows.value = res;
  searched.value = true;
}

// 검색버튼 클릭
async function onClickSearch() {
  // const startDt = searchParam.value.startDt;
  // const endDt = searchParam.value.endDt;

  // if (!moment(startDt).isBefore(endDt) && !moment(startDt).isSame(endDt)){
  //   alert("입력한 끝 날짜가 시작 날짜보다 빠릅니다");
  //   searchParam.value.startDt = "";
  //   date1.value = "";

  //   return;
  // }
  await fetchItemList();
}

// 조회기간 버튼 클릭
// async function onClickPeriod(val) {
//   period.value = val;

//   await setDate(val);
//   // await fetchItemList();
// }

function wrapCsvValue(val, formatFn, row) {
  let formatted = formatFn !== void 0 ? formatFn(val, row) : val;

  formatted =
    formatted === void 0 || formatted === null ? "" : String(formatted);

  formatted = formatted.split('"').join('""');

  return `"${formatted}"`;
}

// 엑셀 다운로드
async function excelDownload() {
  const data = await api.get("/trgtItem/searchItem", {
    params: searchParam.value,
  });

  const ws = XLSX.utils.json_to_sheet(data);
  const wb = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(wb, ws, "대상품목");

  XLSX.writeFile(wb, "대상품목.xlsx");
}

function exportTable() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  api
    .get("excel/trgtItm", {
      params: searchParam.value,
      responseType: "arraybuffer",
    })
    .then(function (res) {
      const url = window.URL.createObjectURL(new Blob([res]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute(
        "download",
        "대상품목_" + moment().format("YYYYMMDD") + ".xls"
      );
      document.body.appendChild(link);
      link.click();
      link.remove();
    })
    .catch(function (error) {
      console.log(error);
    });
}

const changeRadio = (val) => {
  searchParam.value.selItemYn = val;
};

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
  const strtDt = new Date(year, month - val, 2);
  const endDt = new Date(year, month, 1);

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

      return;
    }
  }

  if (endDt) {
    if (!moment(endDt, "YYYY-MM-DD", true).isValid()) {
      alert("유효하지 않은 날짜 형식입니다.");
      searchParam.value.endDt = "";

      return;
    }
  }
}
/**********************************/

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
  // await onClickPeriod(3);
  await fetchItemList();
  await bus.emit("onLoadGetAutoCompleteData");
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "ROWNUMBER",
    label: "순번",
    field: "ROWNUMBER",
    align: "center",
    classes: "td-num",
  },
  {
    name: "APPLY_DT",
    label: "예정일",
    align: "center",
    required: true,
    field: "APPLY_DT",
    classes: "td-date",
  },
  {
    name: "MAKER_NM",
    label: "제약회사",
    field: "MAKER_NM",
    align: "left",
    classes: "td-comp",
  },
  {
    name: "ITEM_NM_UNIT",
    label: "품목 및 규격",
    field: "ITEM_NM_UNIT",
    align: "left",
    classes: "td-prd",
  },
  {
    name: "BOHUM_CD",
    label: "보험코드",
    field: "BOHUM_CD",
    align: "center",
    classes: "td-code",
  },
  {
    name: "WP2_OLD",
    label: "변경 전",
    field: "WP2_OLD",
    align: "center",
    classes: "td-price_insu",
  },
  {
    name: "WP2_NEW",
    label: "변경 후",
    field: "WP2_NEW",
    align: "center",
    classes: "td-price_insu",
  },
  {
    name: "PRICE_AMT",
    label: "변동금액",
    field: "PRICE_AMT",
    align: "center",
    classes: "td-price_vari",
  },
  {
    name: "THIS_MONTH",
    label: "당월",
    field: "THIS_MONTH",
    align: "center",
    classes: "td-ord_mnt",
  },
  {
    name: "BEFORE_MONTH",
    label: "전월",
    field: "BEFORE_MONTH",
    align: "center",
    classes: "td-ord_mnt",
  },

  {
    name: "AVAIL_QTY",
    label: "보상예정수량",
    field: "AVAIL_QTY",
    align: "center",
    classes: "td-qty",
  },
  {
    name: "ESTM_AMT",
    label: "보상예정금액",
    field: "ESTM_AMT",
    align: "right",
    classes: "td-price",
  },
]);
</script>

<style lang="scss" scoped>
.salesTable_1e {
  // height: 736px;
  height: 690px;
  //overflow-y: auto;
}
.q-table_radio_wrap {
  padding: 0 8px;
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
  gap: 8px;
}
.flex_between {
  @include flex_between;
}
.q-table_header_wrap {
  margin-bottom: 8px;
}
// .table_header_sub_top th {
//   padding: 8px 0px;
//   border-bottom: none !important;
// }
// .table_header_sub_bottom {
//   td {
//     // background-color: #e9efff;
//     padding: 4px 4px 8px !important;
//   }
// }
// .td-comp {
//   max-width: 200px;
// }
// .td-prd {
//   width: 100%;
//   min-width: 250px;
// }
</style>

<template>
  <div class="bakReqDetail_wrap">
    <q-table
      class="salesTable_a"
      :rows="rows"
      :columns="columns"
      row-key="SN"
      table-header-class="table_header"
      dense
      virtual-scroll
      v-model:pagination="pagination"
      :selected="selected"
      :rows-per-page-options="rowsPerPageOptions"
    >
      <div class="row justify-center q-mt-md">
        <q-pagination
          v-model="pagination.page"
          color="grey-8"
          :max="pagesNumber"
          size="sm"
        />
      </div>
      <template v-slot:top>
        <div class="q-table_wrap" style="margin-bottom: 10px">
          <div class="q-table_header_wrap">
            <div class="q-table_header_title">
              <p>품목검색</p>
              <div class="q-table_radio_wrap" style="margin-left: 15px">
                <!-- <RadioComp_a
                    v-model="searchParam.subdivYn"
                    :radio_options="salesRadioOptions"
                  ></RadioComp_a> -->
                <q-option-group
                  v-model="searchParam.subdivYn"
                  :options="salesRadioOptions"
                  color="blue-10"
                  size="xs"
                  inline
                  dense
                />
              </div>
            </div>
            <div class="q-table_header_title_sub">
              <div class="q-table_btn_option_wrap">
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
                    :class="btn_options"
                    label="3년"
                    dense
                    @click="onClickPeriod(36)"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="flex_between">
            <div class="flex_start" style="gap: 0px">
              <div class="q-table_input_wrap">
                <div class="SearchComp_wrap">
                  <!-- <q-select
                      v-model="searchParam.itemNm"
                      use-input
                      fill-input
                      hide-dropdown-icon
                      input-class="input_search"
                      borderless
                      dense
                      placeholder="품목명입력"
                      :options="autoCompleteData"
                      @input-value="setKeyword"
                      @keyup.enter="onClickSearch"
                    /> -->
                  <q-input
                    v-model="searchParam.itemNm"
                    class="input_search1"
                    borderless
                    dense
                    placeholder="품목명/보험코드 입력"
                    autofocus
                    @keyup.enter="onClickSearch"
                    @focus="(input) => input.target.select()"
                  />
                  <q-input
                    v-model="searchParam.makerNm"
                    class="input_search2"
                    borderless
                    dense
                    placeholder="제약회사명 입력"
                    @keyup.enter="onClickSearch"
                    @focus="(input) => input.target.select()"
                  />
                </div>
              </div>
              <div class="q-table_calrendar_wrap">
                <!-- <DatePickerComp_b /> -->
                <div class="flex_between" style="gap: 4px">
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
                            mask="YYYY-MM-DD"
                            @update:model-value="updateStartDt"
                            :default-date="today"
                            :title="selectedDate1"
                            :subtitle="selectedYear1"
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
                            mask="YYYY-MM-DD"
                            @update:model-value="updateEndDt"
                            :default-date="today"
                            :title="selectedDate2"
                            :subtitle="selectedYear2"
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
                <BtnComp_default
                  class="btn_srch"
                  btn-label="검색"
                  @click="onClickSearch"
                />
                <!-- </div>
            <div class="q-table_btn_export_wrap"> -->
              </div>
            </div>
            <BtnComp_default
              class="btn-line default btn-pd12 btn-excel"
              btn-label="엑셀"
              @click="exportTable"
            />
          </div>
        </div>
      </template>

      <template v-slot:top-row v-if="rows.length === 0">
        <q-tr class="no-date">
          <q-td colspan="100%">
            <span>검색된 결과가 없습니다.</span>
          </q-td>
        </q-tr>
      </template>

      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td key="BACK_DT" :props="props" @click="onRowClick(props.row)">
            {{ props.row.BACK_DT }}
          </q-td>
          <q-td
            key="EXAM_DT"
            :props="props"
            style="cursor: pointer"
            @click="onRowClick(props.row)"
          >
            {{ props.row.EXAM_DT }}
          </q-td>

          <!-- 제약회사/ -->
          <q-td key="MAKER_NM" :props="props" @click="onRowClick(props)">
            <div class="q-table_itemTitleSub ellipsis">
              {{ props.row.MAKER_NM
              }}<q-tooltip>{{ props.row.MAKER_NM }}</q-tooltip>
            </div>
          </q-td>

          <q-td
            key="ITEM_NM_UNIT"
            :props="props"
            @click="onRowClick(props.row)"
          >
            <div class="q-table_item">
              <!-- <q-badge class="q-table_badge_red" style="margin-right: 4px">
                {{ props.row.MED_NM }}
              </q-badge>
              <q-badge class="q-table_badge_blue">
                {{ props.row.PAY_ITEM_NM }}
              </q-badge> -->
              <div class="q-table_badge_wrap">
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
              <div class="td-prd_name">{{ props.row.ITEM_NM_UNIT }}</div>
            </div>
          </q-td>
          <q-td
            key="BACK_EXAM_NM"
            :props="props"
            @click="onRowClick(props.row)"
          >
            <p
              :class="{
                'red-400': props.row.BACK_EXAM_NM === '취소완료',
                'gray-5': props.row.BACK_EXAM_NM === '미등록',
              }"
            >
              {{ props.row.BACK_EXAM_NM }}
            </p>
          </q-td>
          <q-td
            key="ITEM_QTY_REQ"
            :props="props"
            @click="onRowClick(props.row)"
          >
            {{ util.getNumberFormat(props.row.ITEM_QTY_REQ) }}
          </q-td>
          <q-td key="ITEM_QTY" :props="props" @click="onRowClick(props.row)">
            {{ util.getNumberFormat(props.row.ITEM_QTY) }}
          </q-td>
          <q-td
            key="ITEM_QTY_NO"
            :props="props"
            style="cursor: pointer"
            @click="onRowClick(props.row)"
          >
            {{ util.getNumberFormat(props.row.ITEM_QTY_NO) }}
          </q-td>
          <q-td key="ORD_WP2_AMT" :props="props" @click="onRowClick(props.row)">
            {{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}
          </q-td>
          <q-td key="PRICE" :props="props" @click="onRowClick(props.row)">
            {{ util.getNumberFormat(props.row.PRICE) }}
          </q-td>
          <q-td
            key="EXAM_RSLT_NM"
            :props="props"
            @click="onRowClick(props.row)"
          >
            {{ props.row.EXAM_RSLT_NM }}
          </q-td>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { computed, ref, onMounted, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";
import { exportFile, useQuasar } from "quasar";

import RadioComp_a from "src/components/web/common/RadioComp_a.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_purple from "src/components/web/common/BtnComp_purple.vue";
import SearchComp_select from "src/components/web/common/SearchComp_select.vue";
import BtnComp_options from "src/components/web/common/BtnComp_options.vue";
import DatePickerComp_b from "src/components/web/common/DatePickerComp_b.vue";

const userInfo = util.getUserData();
const rows = ref([]);
const bus = inject("bus");
const selected = ref([]);

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

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  itemNm: "",
  makerNm: "",
  orderVal: "0",
  subdivYn: "",
  startDt: "",
  endDt: "",
});

const salesRadioOptions = [
  {
    label: "전체",
    value: "",
  },
  {
    label: "소분제외",
    value: "N",
  },
  {
    label: "소분만",
    value: "Y",
  },
];

const period = ref("");

const periodClassA = computed(() =>
  period.value === 0 ? "btn" : "btn_options"
);
const periodClassB = computed(() =>
  period.value === 1 ? "btn" : "btn_options"
);
const periodClassC = computed(() =>
  period.value === 3 ? "btn" : "btn_options"
);
const periodClassD = computed(() =>
  period.value === 6 ? "btn" : "btn_options"
);
const periodClassE = computed(() =>
  period.value === 12 ? "btn" : "btn_options"
);
const periodClassF = computed(() =>
  period.value === 24 ? "btn" : "btn_options"
);
const periodClassG = computed(() =>
  period.value === 36 ? "btn" : "btn_options"
);

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

  const res = await api.get("/bakReqDtl/listSearch", {
    params: searchParam.value,
  });

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
async function onRowClick(row) {
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
    .get("excel/bakReqDetail", {
      params: searchParam.value,
      responseType: "arraybuffer",
    })
    .then(function (res) {
      const url = window.URL.createObjectURL(new Blob([res]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute(
        "download",
        "반품상세_" +
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

/*********** datepicker ***********/
// 컴포넌트로 사용할수가 없어
// DatePickerComp_b.vue 이벤트 여기로 뻄

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
// 오늘 날짜와 기본 날짜 설정 250307
const today = new Date();
// 캘린더 title 250307
const selectedDate1 = computed(() => {
  const date = new Date(proxyDate1.value);
  const month = date.getMonth() + 1; // 0-11에서 1-12로 변경
  const day = date.getDate();
  const weekday = date.toLocaleDateString("ko-KR", { weekday: "short" }); // 요일 가져오기

  return proxyDate1.value
    ? `${month}월 ${day}일 (${weekday[0].toUpperCase() + weekday.slice(1)})`
    : `${new Date(today).getMonth() + 1}월 ${new Date(today).getDate()}일 (${
        weekday[0].toUpperCase() + weekday.slice(1)
      })`;
});
// 캘린더 subtitle 250307
const selectedYear1 = computed(() => {
  return proxyDate1.value
    ? new Date(proxyDate1.value).getFullYear()
    : new Date(today).getFullYear();
});

// 캘린더 title 250307
const selectedDate2 = computed(() => {
  const date = new Date(proxyDate2.value);
  const month = date.getMonth() + 1; // 0-11에서 1-12로 변경
  const day = date.getDate();
  const weekday = date.toLocaleDateString("ko-KR", { weekday: "short" }); // 요일 가져오기

  return proxyDate2.value
    ? `${month}월 ${day}일 (${weekday[0].toUpperCase() + weekday.slice(1)})`
    : `${new Date(today).getMonth() + 1}월 ${new Date(today).getDate()}일 (${
        weekday[0].toUpperCase() + weekday.slice(1)
      })`;
});
// 캘린더 subtitle 250307
const selectedYear2 = computed(() => {
  return proxyDate2.value
    ? new Date(proxyDate2.value).getFullYear()
    : new Date(today).getFullYear();
});

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
  searchParam.value.itemNm = val;

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
  await bus.emit("onLoadGetAutoCompleteData");
  await setDate(3);
  period.value = 3;
  await fetchItemList();
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "BACK_DT",
    label: "반품일자",
    align: "center",
    required: true,
    field: "BACK_DT",
    classes: "td-date",
  },
  {
    name: "EXAM_DT",
    label: "검수일자",
    field: "EXAM_DT",
    align: "center",
    classes: "td-date",
  },
  {
    name: "MAKER_NM",
    label: "제약사",
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
    name: "BACK_EXAM_NM",
    label: "진행현황",
    field: "BACK_EXAM_NM",
    align: "center",
    classes: "td-exam",
  },
  {
    name: "ITEM_QTY_REQ",
    label: "반품수량",
    field: "ITEM_QTY_REQ",
    align: "center",
    classes: "td-qty",
  },
  {
    name: "ITEM_QTY",
    label: "검수수량",
    field: "ITEM_QTY",
    align: "center",
    classes: "td-qty",
  },
  {
    name: "ITEM_QTY_NO",
    label: "불가수량",
    field: "ITEM_QTY_NO",
    align: "center",
    classes: "td-qty",
  },
  {
    name: "ORD_WP2_AMT",
    label: "단가",
    field: "ORD_WP2_AMT",
    align: "right",
    classes: "td-price",
  },
  {
    name: "PRICE",
    label: "금액",
    field: "PRICE",
    align: "right",
    classes: "td-price",
  },
  {
    name: "EXAM_RSLT_NM",
    label: "검수결과",
    field: "EXAM_RSLT_NM",
    align: "center",
    classes: "td-exam_rslt",
  },
]);

// rows.value = [
//   {
//     name: "2023.12.25",
//     승인번호: 429,
//     종류: "매출",
//     주문서종류: "일반주문(인터넷)",
//     적요: "프로스",
//     품목수: "외 9종",
//     판매금액: 93300,
//     입금액: 0,
//     잔고: 61000000,
//     메모: "",
//     인수증: "",
//     잔고확인: "",
//   },
// ];
</script>

<style lang="scss" scoped>
.salesTable_a {
  // height: 716px;
  height: 690px;
  // overflow-y: auto;
}
.q-table_header_wrap {
  justify-content: space-between;
  flex-wrap: wrap;
  margin-bottom: 8px;
  gap: 2px 0;
}
.q-table_header_title {
  display: flex;
  flex-wrap: wrap;
  align-items: flex-start;
}
.q-table_header_title_sub {
  // margin-bottom: 8px;
  gap: 8px;
}

.q-table_input_wrap {
  // @include flex_center;
  display: flex;
  gap: 8px;
  // margin: 12px 44px;
  // padding: 0 8px;
  padding-right: 8px;
}
.top-left_input {
  @include fs-5;
  width: 120px;
  height: 28px;
  border: 1px solid #666666;
  padding: 2px 6px;
  border-radius: 5px;
}
.top-left_input::placeholder {
  @include fs-5;
}
.top-left_input_wrap .q-field--dense .q-field__control {
  height: 28px !important;
}
.q-field__control-container {
  @include flex_center;
}

.flex_between {
  @include flex_between;
}
.q-table_calrendar_wrap,
.q-table_btn_export_wrap {
  @include flex_center;
  gap: 8px;
}
// .q-table_btn_option_wrap {
//   // margin-left: 15px;
//   margin-top: 8px;
// }
.q-table_header_sub_wrap {
  @include flex_start;
  gap: 40px;
  // margin: 8px 0;
  // padding: 8px 15px;
  background-color: rgba(31, 63, 130, 0.1);
  gap: 8px;
  // margin: 8px 0;
  @media screen and (max-width: 830px) {
    gap: 8px 4px;
  }
}
.q-table_header_sub_wrap p span {
  @include fs-5;
}
.q-table_header_sub_wrap p span:nth-child(2) {
  @include fw-7;
  margin-left: 15px;
}
.q-table_bottom_sub_wrap {
  @include flex_start;
  gap: 40px;
  margin: 8px 0;
  padding: 8px 15px;
  background-color: rgba(31, 63, 130, 0.1);
  width: 100%;
}
.q-table_bottom_sub_wrap p span {
  @include fw-5;
  // margin-left: 15px;
}

.DatePickerComp_b_wrap {
  @include flex_start;
  gap: 4px;
  width: 126px;
  height: 32px;
  border: 1px solid $line-400;
  .q-input {
    width: 100%;
  }
  .q-field__native {
    width: 100%;
    border: none;
  }
}
.date_input_wrap,
.date_btn_wrap {
  display: inline-flex;
  align-items: center;
}
.date_input_wrap {
  width: calc(100% - 30px);
  padding: 0 4px;
  border-radius: 0;
}
.date_btn_wrap {
  position: relative;
  width: 30px;
  .q-btn {
    width: 100%;
    border-radius: 0;
  }
  &::before {
    content: "";
    position: absolute;
    top: 50%;
    left: -1px;
    transform: translateY(-50%);
    width: 1px;
    height: 12px;
    background-color: $line-400;
  }
}

.bakReqDetail_wrap {
  -ms-overflow-style: none;
  scrollbar-width: none;
}
.bakReqDetail_wrap::-webkit-scrollbar {
  display: none;
}
.Btn_options_wrap .q-btn {
  @include flex_center;
  padding: 0 5px;
  width: 60px;
  height: 32px;
  color: $gray-9 !important;
  background-color: #fff;
  background-image: none !important;
  border: 1px solid $line-400;
  border-radius: 0;
  transition: none;
  &::before {
    display: none;
  }
  &:nth-child(1) {
    padding-left: 12.5px;
    width: 70px;
    border-radius: 15px 0 0 15px !important;
  }
  &:nth-last-child(1) {
    padding-right: 12.5px;
    width: 70px;
    border-radius: 0 15px 15px 0 !important;
  }
  span {
    @include fs-5;
  }
}
.Btn_options_wrap {
  @include flex_center;
  gap: 0;
  .q-btn + .q-btn {
    border-left: none;
  }
  .btn,
  .btn_active,
  .btn_options:active,
  .btn_options:hover,
  .btn_options:focus {
    color: #fff !important;
    background-color: $primary;
    border-top-color: $primary;
    border-bottom-color: $primary;
    &:nth-child(1),
    &:nth-last-child(1) {
      border-color: $primary;
    }
  }
}
.flex_start {
  display: flex;
  align-items: flex-start;
}
@media screen and (max-width: 905px) {
  .q-table_header_wrap + .flex_between {
    flex-wrap: wrap;
    gap: 8px;

    .SearchComp_wrap {
      .input_search1,
      .input_search2 {
        max-width: 150px;
      }
    }
  }
}
</style>

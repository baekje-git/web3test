<template>
  <div class="">
    <q-table
      class="salesTable_1g"
      :rows="rows"
      :columns="columns"
      row-key="SN"
      table-header-class="table_header"
      dense
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
      :selected="selected"
      @row-click="onRowClick"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="q-table_header_sub_wrap flex_between">
            <div class="flex_start">
              <div class="q-table_radio_wrap">
                <q-option-group
                  v-model="searchParam.searchGb"
                  :options="options"
                  color="blue-10"
                  size="xs"
                  inline
                  dense
                />
              </div>
              <div class="q-table_calendar_wrap flex_start">
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
                          <q-date
                            v-model="proxyDate1"
                            @click="updateStartDt"
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
              </div>
            </div>

            <div class="flex_end" style="gap: 8px">
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
              </div>
              <BtnComp_default
                v-if="btnChk"
                class="btn-line default"
                btn-label="주문취소"
                @click="onClickCnclRe"
              />
            </div>
          </div>
        </div>
      </template>

      <template v-slot:top-row v-if="rows.length === 0 && searched">
        <q-tr class="no-date">
          <q-td colspan="100%">
            <span>검색된 결과가 없습니다.</span>
          </q-td>
        </q-tr>
      </template>
      <template v-slot:body-cell-ORD_DT_FM="props">
        <q-td style="cursor: pointer" class="td-date">
          <div>
            {{ props.row.ORD_DT_FM }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-APRV_DT_FM="props">
        <q-td style="cursor: pointer" class="td-date">
          <div>
            {{ props.row.APRV_DT_FM }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-GUBUN="props">
        <q-td style="cursor: pointer; text-align: center" class="td-type">
          <div>{{ props.value }}</div>
        </q-td>
      </template>

      <template v-slot:body-cell-BACK_GB_NM="props">
        <q-td style="cursor: pointer; text-align: center" class="td-cate">
          <div>{{ props.value }}</div>
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_NM_UNIT="props">
        <q-td
          :class="{ 'q-tr--selected': isSelected(props.row) }"
          style="cursor: pointer"
          class="td-prd"
        >
          <div class="ellipsis td-prd_name bold">
            {{ props.row.ITEM_NM_UNIT }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_CNT="props">
        <q-td style="cursor: pointer; text-align: center" class="td-qty">
          <div>{{ props.value }}</div>
        </q-td>
      </template>

      <template v-slot:body-cell-TOTAL_AMT="props">
        <q-td style="text-align: right; cursor: pointer" class="td-price">
          <div :class="Number(props.row.TOTAL_AMT) < 0 ? 'red-400' : ''">
            {{ util.getNumberFormat(props.row.TOTAL_AMT) }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-STATS="props">
        <q-td
          class="q-table_sales_product_wrap td-exam"
          style="cursor: pointer; text-align: center"
        >
          <div>
            <p v-if="props.row.STATS === '등록'" class="red-400">
              {{ props.row.STATS }}
            </p>
            <p v-else-if="props.row.STATS === '접수'" class="blue-400">
              {{ props.row.STATS }}
            </p>
            <p v-else>
              {{ props.row.STATS }}
            </p>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ORD_MEMO="props">
        <q-td class="td-memo" style="cursor: pointer; text-align: center">
          <MemoComp_default
            v-if="props.row.ORD_MEMO !== ''"
            :memo="props.row.ORD_MEMO"
            :is-disable="true"
            class="flex_center"
          >
            <img src="assets/icons/file-lines-solid.svg" alt="file" />
          </MemoComp_default>
        </q-td>
      </template>

      <template v-slot:bottom>
        <div class="q-table_bottom_total_wrap">
          <div class="flex_end">
            <p>합계</p>
          </div>
          <div class="flex_center">
            <p>{{ subtotal() }}</p>
          </div>
        </div>
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

import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import DatePickerComp_b from "src/components/web/common/DatePickerComp_b.vue";
import MemoComp_default from "src/components/web/common/MemoComp_default.vue";
import RadioComp_a from "src/components/web/common/RadioComp_a.vue";
import BtnComp_options from "src/components/web/common/BtnComp_options.vue";

const userInfo = util.getUserData();
const bus = inject("bus");
const loading = ref(false);
const searched = ref(false);
const selected = ref([]);
const btnChk = ref(false);

const options = [
  {
    label: "전체",
    value: "0",
  },
  {
    label: "매출",
    value: "1",
  },
  {
    label: "반품",
    value: "2",
  },
];

const period = ref("");

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

const rows = ref([]);

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  searchGb: "0",
  startDt: "",
  endDt: "",
});

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

  bus.emit("initRecentlyDtls");
  loading.value = true;
  selected.value = [];

  const res = await api.get("/recently/searchItem", {
    params: searchParam.value,
  });

  searched.value = true;
  rows.value = res;
  loading.value = false;
}

function subtotal() {
  const total = rows.value.reduce((sum, row) => sum + row.TOTAL_AMT, 0);
  return total.toLocaleString();
}

// 조회기간 버튼 클릭
async function onClickPeriod(val) {
  period.value = val;

  await setDate(val);
  await fetchItemList();
}

// 검색 버튼 클릭
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

const isSelected = (row) => {
  return selected.value.includes(row);
};

// 그리드 row 클릭
async function onRowClick(evt, row) {
  const index = selected.value.indexOf(row);
  if (index === -1) {
    selected.value = [];
    selected.value.push(row);
  } else {
    selected.value.splice(index, 1);
  }

  if (row.GUBUN == "매출" && row.STATS == "등록") {
    btnChk.value = true;
  } else {
    btnChk.value = false;
  }

  await bus.emit("searchRecentlyDtls", row);
}

bus.on("onRecentlyList", async () => {
  if (rows.value.length > 0) {
    await fetchItemList();
  }
});

onMounted(async () => {
  period.value = 1;
  await setDate(1); // 날짜 세팅
  await fetchItemList(); // 목록 조회

  if (rows.value.length > 0) {
    await bus.emit("searchRecentlyDtls", rows.value[0]); // 디테일에게 전달 (단 1번만!)
  }
});

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

// 주문취소 버튼 클릭
async function onClickCnclRe() {
  let row = selected.value[0];

  if (row.STATS !== "등록") {
    alert("처리상태가 [등록]인 경우만 주문취소가 가능합니다.");
    return;
  }
  if (!confirm("주문 취소하시겠습니까?")) return;

  try {
    Object.assign(row, { USER_ID: userInfo.USER_ID });

    await api.delete("/recently/cancelRcently", { params: row });

    util.showNotify("취소되었습니다.");

    await fetchItemList();
  } catch (error) {
    // alert(error.response.data);
    alert("오류가 발생했습니다.");
  }
}

/**********************************/

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "ORD_DT_FM",
    label: "주문일자",
    field: "ORD_DT_FM",
    align: "center",
    classes: "td-date",
  },
  {
    name: "APRV_DT_FM",
    label: "승인일자",
    field: "APRV_DT_FM",
    align: "center",
    classes: "td-date",
  },
  {
    name: "GUBUN",
    label: "종류",
    field: "GUBUN",
    align: "center",
    classes: "td-type",
  },
  {
    name: "BACK_GB_NM",
    label: "구분",
    field: "BACK_GB_NM",
    align: "center",
    classes: "td-cate",
  },
  {
    name: "ITEM_NM_UNIT",
    label: "대표품목",
    field: "ITEM_NM_UNIT",
    align: "center",
    classes: "td-prd",
  },
  {
    name: "ITEM_CNT",
    label: "품목수",
    field: "ITEM_CNT",
    align: "center",
    classes: "td-qty",
  },
  {
    name: "TOTAL_AMT",
    label: "주문금액",
    field: "TOTAL_AMT",
    align: "right",
    classes: "td-price",
    format: (val) => util.getNumberFormat(val),
  },
  {
    name: "STATS",
    label: "처리상태",
    field: "STATS",
    align: "center",
    classes: "td-exam",
  },
  {
    name: "ORD_MEMO",
    label: "메모",
    field: "ORD_MEMO",
    align: "center",
    classes: "td-memo",
  },
]);
</script>

<style lang="scss" scoped>
.salesTable_1g {
  // height: 480px;
  height: 425px;
  overflow-y: auto;
  @media screen and (max-width: 1025px) and (min-width: 831px) {
    height: 485px;
  }
}
.q-table_header_sub_wrap {
  flex-wrap: wrap;
}
.flex_center {
  @include flex_center;
  gap: 8px;
}
.flex_between {
  @include flex_between;
}
.q-table_calendar_wrap p {
  @include fs-5;
}

.q-table_sales_product_wrap {
  max-width: 160px;
  p {
    // overflow: hidden;
    // white-space: nowrap;
    // text-overflow: ellipsis;
    white-space: wrap;
  }
}

// .btn_active {
//   @include gradation;
//   border-radius: 5px;
//   color: $w;
//   padding: 0 16px;
// }

.flex_between {
  @include flex_between;
  gap: 8px;
}
// .DatePickerComp_b_wrap {
//   @include flex_center;
//   width: 130px;
//   gap: 8px;
// }
.salesTable_1g {
  .q-table_header_sub_wrap .flex_start {
    flex-wrap: wrap;
  }
  .q-table_calendar_wrap {
    .flex_between {
      gap: 4px;
    }
    .DatePickerComp_b_wrap {
      gap: 4px 0;
      width: 126px;
    }
    // .date_input_wrap0 {
    //   padding: 0;
    // }
  }
  // .td-prd {
  //   max-width: 200px;
  //   .td-prd_name {
  //     width: inherit;
  //   }
  // }
}
</style>

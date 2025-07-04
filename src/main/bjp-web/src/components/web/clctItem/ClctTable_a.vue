<template>
  <div class="">
    <q-table
      class="salesTable_1e"
      :rows="rows"
      :columns="columns"
      row-key="ROW_NUM"
      table-header-class="table_header"
      dense
      virtual-scroll
      v-model:pagination="pagination"
      :loading="loading"
      :selected="selected"
      :rows-per-page-options="rowsPerPageOptions"
    >
      <!-- <q-table
      class="salesTable_1e"
      :rows="rows"
      :columns="columns"
      row-key="name"
      table-header-class="table_header"
      dense
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
    > -->
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
                <p>회수품목 고시정보</p>
              </div>
            </div>
            <!-- <div class="flex_center">
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
              </div>
            </div> -->
          </div>
          <div class="q-table_header_sub_wrap">
            <div class="flex_start">
              <div class="q-table_calendar_wrap flex_center">
                <p>고시일자</p>
                <!-- <DatePickerComp_b /> -->
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
                          <div class="row items-center justify-end q-gutter-sm">
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
                          <div class="row items-center justify-end q-gutter-sm">
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
              <div class="flex_center">
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
            </div>
          </div>
          <div class="q-table_header_sub_wrap">
            <div class="flex_between">
              <div class="flex_start">
                <!-- <SearchComp
                  placeholder1="보험코드/품목 입력"
                  placeholder2="제약회사 입력"
                /> -->
                <div class="SearchComp_wrap">
                  <!-- <q-select
                    v-model="searchParam.itemNm"
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
                <BtnComp_default btnLabel="검색" @click="onClickSearch" />
                <div class="flex_start chk">
                  <q-checkbox
                    v-model="searchParam.outTm"
                    true-value="Y"
                    false-value="N"
                    label="고시일자 없는 경우 포함"
                  />
                </div>
              </div>
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

      <template v-slot:body-cell-COLL_DT="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <div>
            {{
              props.value == "" ? "" : moment(props.value).format("YYYY-MM-DD")
            }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-MAKER_NM="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <div class="q-table_itemTitleSub ellipsis">
            {{ props.value }}
            <q-tooltip>{{ props.value }}</q-tooltip>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_NM_UNIT="props">
        <q-td
          :props="props"
          style="text-align: left; cursor: pointer"
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
      </template>

      <template v-slot:body-cell-PROD_NO="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <div>{{ props.value }}</div>
        </q-td>
      </template>

      <template v-slot:body-cell-NOTE="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <MemoComp_default_a :memo="props.row.NOTE">
            <BtnComp_active_small
              class="btn-line default active"
              v-if="props.row.NOTE === ''"
              btn_activeLabel="메모"
            />
            <BtnComp_default_small
              class="btn-line default"
              v-if="props.row.NOTE !== ''"
              btn_default-label="메모"
            />
          </MemoComp_default_a>
        </q-td>
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

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
// import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import DatePickerComp_b from "src/components/web/common/DatePickerComp_b.vue";
// import SearchComp from "src/components/web/common/SearchComp.vue";
import BtnComp_purple from "src/components/web/common/BtnComp_purple.vue";
import SearchComp_select from "../common/SearchComp_select.vue";
import BtnComp_options from "src/components/web/common/BtnComp_options.vue";
import MemoComp_default_a from "src/components/web/common/MemoComp_default_a.vue";
import BtnComp_active_small from "src/components/web/common/BtnComp_active_small.vue";
import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";

const userInfo = util.getUserData();
const rows = ref([]);
const bus = inject("bus");
const loading = ref(false);
const searched = ref(false);
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
  startDt: "",
  endDt: "",
  itemNm: "",
  makerNm: "",
  outTm: "N",
  custCd: userInfo.CUST_CD,
  custGbCd: userInfo.CUST_GB_CD,
  ordMakerNm: userInfo.ORD_MAKER_CD,
  userGbCd: userInfo.USER_GB_CD,
});

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
const periodClassF = computed(() =>
  period.value === 24 ? "btn_options btn_active" : "btn_options"
);
const periodClassG = computed(() =>
  period.value === 36 ? "btn_options btn_active" : "btn_options"
);

const autoCompleteData = ref([]);
const itemNmData = ref([]);

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  if (startDt === "" || endDt === "") {
    alert("기간을 입력해 주시기 바랍니다.");
    return;
  }

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  loading.value = true;
  const res = await api.get("/clctItm/ntfctInfo", {
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
  // await fetchItemList();
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

onMounted(async () => {
  // await setDate(6);
  // period.value = 6;
});

function wrapCsvValue(val, formatFn, row) {
  let formatted = formatFn !== void 0 ? formatFn(val, row) : val;

  formatted =
    formatted === void 0 || formatted === null ? "" : String(formatted);

  formatted = formatted.split('"').join('""');

  return `"${formatted}"`;
}

/*********** datepicker ***********/
const date1 = ref("");
const date2 = ref("");
const proxyDate1 = ref("");
const proxyDate2 = ref("");
const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

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
  await onClickPeriod(6);
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "COLL_DT",
    label: "고시일자",
    field: "COLL_DT",
    align: "center",
    classes: "td-date",
  },
  {
    name: "MAKER_NM",
    label: "제약회사",
    field: "MAKER_NM",
    align: "center",
    classes: "td-comp",
  },
  {
    name: "ITEM_NM_UNIT",
    label: "품목 및 규격",
    field: "ITEM_NM_UNIT",
    align: "center",
    classes: "td-prd",
  },
  {
    name: "PROD_NO",
    label: "제조번호",
    field: "PROD_NO",
    align: "center",
    classes: "td-serial",
  },
  {
    name: "NOTE",
    label: "회수사유",
    field: "NOTE",
    align: "center",
    classes: "td-note",
  },
]);
</script>
<style lang="scss" scoped>
.salesTable_1e {
  // height: 736px;
  height: 690px;
  //overflow-y: auto;
}
.flex_start {
  @include flex_start;
  gap: 8px;
  flex-wrap: wrap;
  &.chk {
    // align-items: flex-start;
    height: 32px;
  }
}
.flex_center {
  @include flex_center;
  gap: 8px;
}
.flex_between {
  @include flex_between;
}
.q-table_calendar_wrap {
  gap: 4px;
}
.q-table_calendar_wrap p {
  @include fs-5;
}
.q-table_header_sub_wrap:nth-last-child(1) {
  padding: 8px 0 16px;
}
.q-table_header_sub_wrap > div {
  @media screen and (max-width: 1540px) {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
}
// .Btn_options_wrap {
//   @include flex_center;
//   gap: 8px;
// }
// .btn_options {
//   color: $w;
//   background-color: #999999;
//   border-radius: 5px;
//   padding: 0 16px;
// }
// .btn_options span {
//   @include fs-5;
// }
// .btn_options:active,
// .btn_options:hover,
// .btn_options:focus {
//   @include gradation;
//   border-radius: 5px;
//   color: $w;
//   padding: 0 16px;
// }
// .btn_active {
//   @include gradation;
//   border-radius: 5px;
//   color: $w;
//   padding: 0 16px;
// }

// .SearchComp_wrap {
//   display: flex;
//   gap: 8px;
// }
// .input_search {
//   @include fs-5;
//   width: 120px;
//   height: 28px;
//   border: 1px solid #666666;
//   padding: 2px 6px;
//   border-radius: 5px;
// }
.q-field__control-container {
  @include flex_center;
}
// .DatePickerComp_b_wrap {
//   @include flex_center;
//   width: 130px;
//   gap: 8px;
// }

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
  @media screen and (max-width: 980px) {
    width: 50px;
    &:nth-child(1),
    &:nth-last-child(1) {
      width: 60px;
    }
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
.q-checkbox {
  display: inline-flex;
  gap: 4px;
}
</style>

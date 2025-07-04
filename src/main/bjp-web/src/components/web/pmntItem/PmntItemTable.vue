<template>
  <div class="">
    <q-table
      class="salesTable_a"
      :rows="rows"
      :columns="columns"
      row-key="SEQ"
      table-header-class="table_header"
      dense
      virtual-scroll
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
        <div class="q-table_wrap" style="margin-bottom: 8px">
          <div class="q-table_header_wrap flex_between">
            <div class="q-table_header_title flex_start">
              <p>품목구분</p>
              <div class="q-table_header_sub_wrap tbl-header_bg">
                <p>
                  <span style="font-weight: 400">수불건수</span>
                  <span>{{ util.getNumberFormat(pmntCnt ?? 0) }}건</span>
                </p>
                <p>
                  <span style="font-weight: 400">수불금액</span>
                  <span>{{ util.getNumberFormat(pmntAmt ?? 0) }}원</span>
                </p>
              </div>
            </div>

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
                  :class="periodClassG"
                  label="3년"
                  dense
                  @click="onClickPeriod(36)"
                />
              </div>
            </div>
          </div>
          <div class="q-table_header_title_sub" style="margin-bottom: 8px">
            <div class="q-table_radio_wrap">
              <q-checkbox
                v-model="searchParam.searchAll"
                color="blue-10"
                size="xs"
                inline
                dense
                true-value="Y"
                false-value="N"
                label="전체"
                @update:model-value="onClickSearchParamCheck('all')"
              />
              <q-checkbox
                v-model="searchParam.professionalYn"
                color="blue-10"
                size="xs"
                style="margin-left: 8px"
                inline
                dense
                true-value="Y"
                false-value="N"
                label="전문"
                @update:model-value="onClickSearchParamCheck"
              />
              <q-checkbox
                v-model="searchParam.generalYn"
                color="blue-10"
                size="xs"
                style="margin-left: 8px"
                inline
                dense
                true-value="Y"
                false-value="N"
                label="일반"
                @update:model-value="onClickSearchParamCheck"
              />
              <q-checkbox
                v-model="searchParam.paymentYn"
                color="blue-10"
                size="xs"
                style="margin-left: 8px"
                inline
                dense
                true-value="Y"
                false-value="N"
                label="급여"
                @update:model-value="onClickSearchParamCheck"
              />
              <q-checkbox
                v-model="searchParam.nonPaymentYn"
                color="blue-10"
                size="xs"
                style="margin-left: 8px"
                inline
                dense
                true-value="Y"
                false-value="N"
                label="비급여"
                @update:model-value="onClickSearchParamCheck"
              />
              <q-checkbox
                v-model="searchParam.nonVatYn"
                color="blue-10"
                size="xs"
                style="margin-left: 8px"
                inline
                dense
                true-value="Y"
                false-value="N"
                label="비과세"
                @update:model-value="onClickSearchParamCheck"
              />
              <q-checkbox
                v-model="searchParam.discountYn"
                color="blue-10"
                size="xs"
                style="margin-left: 8px"
                inline
                dense
                true-value="Y"
                false-value="N"
                label="수금할인"
                @update:model-value="onClickSearchParamCheck"
              />
            </div>
          </div>
          <div class="flex_between">
            <div class="q-table_header_title_sub">
              <div class="q-table_calrendar_wrap">
                <!-- <SearchComp /> -->
                <!-- <SelectComp_default
                  :selectModel="monthModel"
                  :selectOption="monthOption"
                /> -->
                <div class="Select_default_wrap">
                  <q-select
                    v-model="searchParam.gubunNm"
                    class="select_default"
                    :options="monthOption"
                    borderless
                    dense
                    emit-value
                    map-options
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
                </div>
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
                  btnLabel="조회"
                  @click="onClickSearch"
                />
              </div>
            </div>
            <div class="flex_end">
              <div class="q-table_btn_export_wrap">
                <BtnComp_default
                  class="btn-line default btn-pd12 btn-print"
                  btn-label="출력"
                  @click="exportPrint"
                />
                <BtnComp_default
                  class="btn-line default btn-pd12 btn-excel"
                  btn-label="엑셀"
                  @click="exportTable"
                />
              </div>
            </div>
          </div>
        </div>
      </template>

      <template v-slot:top-row v-if="rows.length === 0 && searched">
        <q-tr class="no-date">
          <q-td colspan="100%">
            <span>조회된 결과가 없습니다.</span>
          </q-td>
        </q-tr>
      </template>

      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td
            key="APRV_DT_FM"
            :props="props"
            style="width: 93px; cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{
              moment(props.row.APRV_DT_FM, "YYYY-MM-DD HH:mm:ss").format(
                "YYYY-MM-DD"
              )
            }}
          </q-td>

          <q-td
            key="APRV_NO"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            <div>{{ props.row.APRV_NO }}</div>
          </q-td>
          <q-td
            key="TYPE_NM"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            <div>{{ props.row.TYPE_NM }}</div>
          </q-td>
          <q-td
            key="ITEM_NM_UNIT"
            :props="props"
            style="text-align: left; cursor: pointer"
            @click="onClickRow(props.row)"
          >
            <div class="q-table_item">
              <div
                class="q-table_badge_wrap"
                v-if="props.row.MAKER_NM != '수금할인'"
              >
                <q-badge
                  class="bdg-line"
                  :class="props.row.MED_NM ? 'normal' : 'none'"
                >
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
                  :class="{
                    'bdg-line': true,
                    pay: props.row.PAY_ITEM_NM === '급',
                    nonpay: props.row.PAY_ITEM_NM === '비',
                    none: !props.row.PAY_ITEM_NM,
                  }"
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
          <q-td
            key="MAKER_NM"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            <div class="ellipsis">
              {{ props.row.MAKER_NM == "수금할인" ? "" : props.row.MAKER_NM }}
              <q-tooltip>{{ props.row.MAKER_NM }}</q-tooltip>
            </div>
          </q-td>
          <q-td
            key="ITEM_QTY"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            <div>
              {{
                props.row.MAKER_NM == "수금할인"
                  ? ""
                  : util.getNumberFormat(props.row.ITEM_QTY)
              }}
            </div>
          </q-td>
          <q-td
            key="ORD_WP2_AMT"
            :props="props"
            style="text-align: right; cursor: pointer"
            @click="onClickRow(props.row)"
          >
            <div>{{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}</div>
          </q-td>
          <q-td
            key="SALE_AMT"
            :props="props"
            style="text-align: right; cursor: pointer"
            @click="onClickRow(props.row)"
          >
            <div>
              <p v-if="props.row.SALE_AMT < 0" class="red-400 bold">
                {{ util.getNumberFormat(props.row.SALE_AMT) }}
              </p>
              <p v-if="props.row.SALE_AMT >= 0">
                {{ util.getNumberFormat(props.row.SALE_AMT) }}
              </p>
            </div>
          </q-td>
          <q-td
            key="ITEM_PYOJUN_CD"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            <div>{{ props.row.ITEM_PYOJUN_CD }}</div>
          </q-td>
          <q-td
            key="BOHUM_CD"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            <div>{{ props.row.BOHUM_CD }}</div>
          </q-td>
          <q-td
            key="BUTTON"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            <BtnComp_active_small
              v-if="props.row.EXP_YN == 'Y'"
              class="btn-line default"
              btn_activeLabel="상세보기"
              @click="showDetailPop(props)"
            />
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <PmntItemDtl_Popup
      :isOpen="popupOpenInfo.isOpenDetail"
      @close="closeDetail"
    />
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { computed, ref, onMounted, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
import BtnComp_active_small from "src/components/web/common/BtnComp_active_small.vue";
import BtnComp_options from "src/components/web/common/BtnComp_options.vue";
import BtnComp_purple from "src/components/web/common/BtnComp_purple.vue";
import PmntItemDtl_Popup from "src/components/web/pmntItem/PmntItemDtlP.vue";
import SelectComp_default from "src/components/web/common/SelectComp_default.vue";
import SearchComp_select from "src/components/web/common/SearchComp_select.vue";

const userInfo = util.getUserData();
const bus = inject("bus");
const rows = ref([]);
const loading = ref(false);
const searched = ref(false);
const selected = ref([]);

const monthOption = [
  { label: "전체", value: "" },
  { label: "매출", value: "매출" },
  { label: "반품", value: "반품" },
];

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  custNm: userInfo.CUST_NM,
  keyword: "",
  makerNm: "",
  orderVal: "0",
  startDt: "",
  endDt: "",
  searchAll: "Y",
  professionalYn: "N",
  generalYn: "N",
  paymentYn: "N",
  nonPaymentYn: "N",
  nonVatYn: "N",
  discountYn: "N",
  gubunNm: "",
  sortType: "Web",
});

const period = ref("");

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

const isHideBottom = ref(true);
const companyCds = ["49"]; // TODO: 추가해야함
const isShowCrtfBtn = computed(
  () => userInfo.ACCPT_YN === "Y" && !companyCds.includes(userInfo.COMPANY_CD)
);

const popupOpenInfo = ref({
  isOpenDetail: false,
});

const pmntCnt = ref(0);
const pmntAmt = ref(0);

const company = [
  "dhhorim",
  "tspharm",
  "kaymed",
  "charmacist",
  "gfpharm",
  "reinpharm",
  "nurip",
  "mediplankorea",
  "ansimpharm",
];

const autoCompleteData = ref([]);
const itemNmData = ref([]);

const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;
  const searchAll = searchParam.value.searchAll;
  const professionalYn = searchParam.value.professionalYn;
  const generalYn = searchParam.value.generalYn;
  const paymentYn = searchParam.value.paymentYn;
  const nonPaymentYn = searchParam.value.nonPaymentYn;
  const nonVatYn = searchParam.value.nonVatYn;
  const discountYn = searchParam.value.discountYn;

  if (searchParam.value.gubunNm == "전체") {
    searchParam.value.gubunNm = "";
  }

  if (startDt === "" || endDt === "") {
    alert("기간을 입력해 주시기 바랍니다.");
    return;
  }

  if (
    searchAll == "N" &&
    professionalYn == "N" &&
    generalYn == "N" &&
    paymentYn == "N" &&
    nonPaymentYn == "N" &&
    nonVatYn == "N" &&
    discountYn == "N"
  ) {
    alert("품목구분 종류를 선택해 주시기 바랍니다.");
    return;
  }

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  loading.value = true;
  const { count, list } = await api.get("/pmntItem/listSearch", {
    params: searchParam.value,
  });
  loading.value = false;
  pmntCnt.value = count.CNT;
  pmntAmt.value = count.TOTAL_AMT;

  rows.value = list;
  searched.value = true;
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

// 유/제 버튼 클릭 시 상세정보 팝업
async function showDetailPop(props) {
  popupOpenInfo.value.isOpenDetail = true;

  await bus.emit("pmntItemDetail", props.row);
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

function closeDetail() {
  popupOpenInfo.value.isOpenDetail = false;
}

// 체크박스 클릭
function onClickSearchParamCheck(val) {
  const searchAll = searchParam.value.searchAll;
  const professionalYn = searchParam.value.professionalYn;
  const generalYn = searchParam.value.generalYn;
  const paymentYn = searchParam.value.paymentYn;
  const nonPaymentYn = searchParam.value.nonPaymentYn;
  const nonVatYn = searchParam.value.nonVatYn;
  const discountYn = searchParam.value.discountYn;

  if (val === "all") {
    if (searchAll === "Y") {
      searchParam.value.professionalYn = "N";
      searchParam.value.generalYn = "N";
      searchParam.value.paymentYn = "N";
      searchParam.value.nonPaymentYn = "N";
      searchParam.value.nonVatYn = "N";
      searchParam.value.discountYn = "N";
    } else {
      searchParam.value.professionalYn = "N";
      searchParam.value.generalYn = "N";
      searchParam.value.paymentYn = "N";
      searchParam.value.nonPaymentYn = "N";
      searchParam.value.nonVatYn = "N";
      searchParam.value.discountYn = "N";
    }
  } else {
    if (
      [
        professionalYn,
        generalYn,
        paymentYn,
        nonPaymentYn,
        nonVatYn,
        discountYn,
      ].includes("Y")
    ) {
      searchParam.value.searchAll = "N";
    } else {
      searchParam.value.searchAll = "Y";
    }
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
    .get("excel/pmntItem", {
      params: searchParam.value,
      responseType: "arraybuffer",
    })
    .then(function (res) {
      const url = window.URL.createObjectURL(new Blob([res]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute(
        "download",
        "품목수불_" +
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

function exportPrint() {
  var openParam = "";
  var cw = screen.availWidth; // 화면 넓이
  var ch = screen.availHeight; // 화면 높이
  var sw = 825; // 띄울 창의 넓이
  var sh = 760; // 띄울 창의 높이
  var ml = (cw - sw) / 2; // 가운데 띄우기위한 창의 x위치
  var mt = (ch - sh) / 2; // 가운데 띄우기위한 창의 y위치

  /** 위수탁업체 여부 파라미터 추가 **/
  var companyYn = "N";

  const url = window.location.href;

  company.forEach((m) => {
    if (url.includes(m)) {
      companyYn = "Y";
    }
  });

  if (navigator.userAgent.indexOf("Chrome") > 0) {
    sw = sw + 20;
    sh = sh + 10;
    openParam = "width=" + sw + ",height=" + sh + ",top=" + mt + ",left=" + ml;
  } else if (navigator.userAgent.indexOf("MSIE") > 0) {
    /*IE10미만*/
    openParam = "width=" + sw + ",height=" + sh + ",top=" + mt + ",left=" + ml;
  } else {
    /*IE10이상*/
    openParam = "width=" + sw + ",height=" + sh + ",top=" + mt + ",left=" + ml;
  }
  var parmeter = "";

  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  for (var [key, val] of Object.entries(searchParam.value)) {
    parmeter += key + "=" + val + "&";
  }

  /** 위수탁업체 여부 파라미터 추가 **/
  parmeter += "companyYn=" + companyYn + "&";

  window.open(
    process.env.PRINT_URL + "/pmntItem?" + parmeter,
    "_blank",
    openParam
  );
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

// 변경 버튼 diable 여부
function updateBtnDisable(row) {
  if ("") {
    return false;
  } else {
    return true;
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
  await bus.emit("onLoadGetAutoCompleteData");
  await setDate(3);
  period.value = 3;
  await fetchItemList();
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "APRV_DT_FM",
    label: "일자",
    align: "center",
    required: true,
    field: "APRV_DT_FM",
    sortable: true,
    classes: "td-date",
  },
  {
    name: "APRV_NO",
    label: "승인번호",
    field: "APRV_NO",
    align: "center",
    classes: "td-aprv_no",
  },
  {
    name: "TYPE_NM",
    label: "종류",
    field: "TYPE_NM",
    align: "center",
    classes: "td-type",
  },
  {
    name: "ITEM_NM_UNIT",
    label: "품목 및 규격",
    field: "ITEM_NM_UNIT",
    align: "left",
    classes: "td-prd",
  },
  {
    name: "MAKER_NM",
    label: "제약회사",
    field: "MAKER_NM",
    align: "left",
    classes: "td-comp",
  },
  {
    name: "ITEM_QTY",
    label: "수량",
    field: "ITEM_QTY",
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
    name: "SALE_AMT",
    label: "금액",
    field: "SALE_AMT",
    align: "right",
    classes: "td-price",
  },
  {
    name: "ITEM_PYOJUN_CD",
    label: "표준코드",
    field: "ITEM_PYOJUN_CD",
    align: "center",
    classes: "td-code_st",
  },
  {
    name: "BOHUM_CD",
    label: "보험코드",
    field: "BOHUM_CD",
    align: "center",
    classes: "td-code",
  },
  {
    name: "BUTTON",
    label: "유/제",
    field: "BUTTON",
    align: "center",
    classes: "td-detail",
  },
]);
</script>

<style lang="scss" scoped>
.salesTable_a {
  // height: 736px;
  height: 690px;
  //overflow-y: auto;
}
.q-table_header_wrap {
  justify-content: space-between;
  flex-wrap: wrap;
  margin-bottom: 8px;
  gap: 8px;
}

.q-table_radio_wrap {
  // padding-bottom: 8px;
  @include flex_start;
  // padding: 8px 0;
  flex-wrap: wrap;
  gap: 8px;
}
// .Btn_wrap.btn_srch {
//   margin: 0 8px;
// }
// .q-table_btn_option_wrap {
//   margin-top: 8px;
// }
// .top-left_input {
//   @include fs-5;
//   width: 120px;
//   height: 28px;
//   border: 1px solid #666666;
//   padding: 2px 6px;
//   border-radius: 5px;
// }
// .top-left_input::placeholder {
//   @include fs-5;
// }
// .top-left_input_wrap .q-field--dense .q-field__control {
//   height: 28px !important;
// }
.q-field__control-container {
  @include flex_center;
}

.flex_between {
  @include flex_between;
  flex-wrap: wrap;
}
.q-table_header_title_sub + .flex_between {
  gap: 8px;
}
.q-table_calrendar_wrap,
.q-table_btn_export_wrap {
  @include flex_start;
  gap: 8px;
  flex-wrap: wrap;
  @media screen and (max-width: 920px) {
    gap: 8px 4px;
    .SearchComp_wrap {
      gap: 4px;
    }
  }
}
// .q-table_btn_option_wrap {
//   margin-left: 15px;
// }
.q-table_header_sub_wrap {
  @include flex_start;
  gap: 40px;
  margin: 0;
  padding: 0;
  // background-color: rgba(31, 63, 130, 0.1);
}

// .flex_end {
//   width: 100%;
// }
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
  padding: 0 4px;
  width: calc(100% - 30px);
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
</style>

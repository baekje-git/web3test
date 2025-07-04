<template>
  <div class="">
    <q-table
      class=""
      row-key="APRV_NO"
      :rows="rows"
      flat
      hide-header
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
    >
      <template v-slot:loading>
        <div class="m-loading_wrap" v-if="loading === true">
          <q-inner-loading class="loading" :showing="loading" color="primary" />
        </div>
      </template>
      <template v-slot:top>
        <div class="table_Top_wrap">
          <div class="table_Title_mobile">
            <span>{{ TableTitle }}</span>
          </div>
          <div class="filter_area">
            <div class="flex_between" style="gap: 8px">
              <InputComp_mobile
                v-model:input_model_props="searchParam.itemNm"
                placeholder="보험코드/품명"
                style="width: 50%"
                @keyup.enter="onClickSearch"
              />
              <InputComp_mobile
                v-model:input_model_props="searchParam.makerNm"
                placeholder="제약회사"
                style="width: 50%"
                @keyup.enter="onClickSearch"
              />
            </div>
            <BtnComp_mobile
              buttonText="검색"
              btnStyle="default"
              class="btn_srch"
              @click="onClickSearch"
            />
            <div class="table_Filter" @click="dialog_filter = true">
              <!-- <img src="/src/assets/icons/sliders-solid.svg" alt="icon"> -->
              <BtnFilterComp_mobile :filterCheck="isFilterChecked" />
              <DialogLayout_mobile
                dialog_title="검색 필터"
                v-model:dialog_model_prop="dialog_filter"
                :dialog_full="dialog_full"
              >
                <div class="">
                  <FilterSingleComp_mobile_a
                    :btnGroups="filterbtnGroups1"
                    :isActiveBtn="isActiveBtn1"
                    @selected-single-btn="(val) => selectedSortType1(val)"
                  />
                  <FilterSingleComp_mobile_date
                    :btnGroups="filterbtnGroups2"
                    :isActiveBtn="isActiveBtn2"
                    @selected-single-btn="(val) => selectedSortType2(val)"
                  />
                  <div class="Filter_wrap">
                    <div class="Filter_Btn_wrap">
                      <div class="Filter_DatePicker_wrap" :class="'on'">
                        <div class="flex_between" style="padding: 0 0 0 0">
                          <DatePickerComp_mobile
                            style="width: 50%"
                            v-model:input_model_props="searchParam.dateStartYmd"
                            @on-change-date="onOnChangeDate"
                            :placeholder="
                              sortType2 === '직접입력'
                                ? ''
                                : moment().format('YYYY-MM-DD')
                            "
                          />
                          <span
                            style="
                              font-size: 12px;
                              color: #999999;
                              margin: 0 4px;
                            "
                          >
                            ~
                          </span>
                          <DatePickerComp_mobile
                            style="width: 50%"
                            v-model:input_model_props="searchParam.dateEndYmd"
                            @on-change-date="onOnChangeDate"
                            :placeholder="
                              sortType2 === '직접입력'
                                ? ''
                                : moment().format('YYYY-MM-DD')
                            "
                          />
                        </div>
                      </div>
                    </div>
                  </div>
                  <BtnComp_mobile
                    :class="btnResult"
                    buttonText="적용"
                    btnStyle="default"
                    btnSize="lg"
                    class="btn-full"
                    style="padding-top: 20px"
                    @click="onClickFilterApply"
                  />
                </div>
              </DialogLayout_mobile>
            </div>
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
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item table_item_mobile">
              <div class="table_badge_wrap">
                <!-- <q-badge class="table_badge_red">
                  {{ props.row.MED_NM }}
                </q-badge>
                <q-badge class="table_badge_blue">
                  {{ props.row.PAY_ITEM_NM }}
                </q-badge> -->
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
              <q-td class="table-td_mobile">
                <p>
                  {{ props.row.ITEM_NM_UNIT }}
                </p>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.BACK_EXAM_NM }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">등록일</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.BACK_DT }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">등록</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ util.getNumberFormat(props.row.ITEM_QTY_REQ) }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">단가</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">검수일</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.EXAM_DT }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">검수</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ util.getNumberFormat(props.row.ITEM_QTY) }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">금액</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ util.getNumberFormat(props.row.PRICE) }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">불가</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td
                class="table-td_mobile red-400"
                v-if="props.row.ITEM_QTY_NO > 0"
              >
                {{ util.getNumberFormat(props.row.ITEM_QTY_NO) }}
              </q-td>
              <q-td class="table-td_mobile" v-else>
                {{ util.getNumberFormat(props.row.ITEM_QTY_NO) }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td
                v-if="props.row.EXAM_RSLT_NM === '정상'"
                class="table-td_mobile"
              >
                {{ props.row.EXAM_RSLT_NM }}
              </q-td>
              <q-td v-else class="red-400 table-td_mobile">
                {{ props.row.EXAM_RSLT_NM }}
              </q-td>
            </div>
          </div>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, inject, onMounted, computed } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";

import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BtnDateGroupComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnDateGroupComp_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import InputComp_mobile from "src/components/mobile/__commonComp/inputComp/InputComp_mobile.vue";
import FilterComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterComp_mobile.vue";
import BtnFilterComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnFilterComp_mobile.vue";
import DatePickerComp_mobile from "src/components/mobile/__commonComp/datepickerComp/DatePickerComp_mobile.vue";
import FilterSingleComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile.vue";
import FilterSingleComp_mobile_a from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile_a.vue";
import FilterSingleComp_mobile_date from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile_date.vue";

const btnResult = ref("on");

//필터 체크
const isFilterChecked = ref(false);
const isActiveBtn1 = ref("전체");
const isActiveBtn2 = ref("3개월");

//로딩
const loading = ref(true);
const show = ref(false);
const nodata_show = ref(false);

const dialog_full = ref(true);
const dialog_filter = ref(false);

const bus = inject("bus");
const userInfo = util.getUserData();
const rows = ref([]);

const props = defineProps({
  TableTitle: {
    type: String,
    default: "TableTitle",
  },
});

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  itemNm: "",
  makerNm: "",
  orderVal: "0",
  subdivYn: "", //조회필터 N: 소분제외, Y: 소분만, "": 전체
  startDt: "",
  endDt: "",
  dateStartYmd: moment()
    .add(-3, "months")
    .startOf("month")
    .format("YYYY-MM-DD"), //필터 시작 기간설정
  dateEndYmd: moment().format("YYYY-MM-DD"), //필터 종료 기간설정
  sellMonth: "-3",
});

//조회필터
const filterbtnGroups1 = [
  {
    title: "조회필터",
    subtitle: "",
    gridStyle: 3,
    btnDatePicker: "off",
    buttons: [
      { btnLabel: "전체" },
      { btnLabel: "소분제외" },
      { btnLabel: "소분만" },
    ],
  },
];

//기간설정
const filterbtnGroups2 = [
  {
    title: "기간설정",
    subtitle: "",
    gridStyle: 4,
    btnDatePicker: "off",
    buttons: [
      { btnLabel: "당월" },
      { btnLabel: "전월" },
      { btnLabel: "3개월" },
      { btnLabel: "6개월" },
      { btnLabel: "1년" },
      { btnLabel: "2년" },
      { btnLabel: "3년" },
      { btnLabel: "직접입력" },
    ],
  },
];

//조회필터
const sortType1 = ref("전체");
function selectedSortType1(val) {
  sortType1.value = val;
}

//기간표시
const thisMnth = computed(() =>
  searchParam.value.sellMonth === "0"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfrMnth = computed(() =>
  searchParam.value.sellMonth === "-1"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr3Mnth = computed(() =>
  searchParam.value.sellMonth === "-3"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr6nth = computed(() =>
  searchParam.value.sellMonth === "-6"
    ? " btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr1Year = computed(() =>
  searchParam.value.sellMonth === "-12"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr2Year = computed(() =>
  searchParam.value.sellMonth === "-24"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr3Year = computed(() =>
  searchParam.value.sellMonth === "-36"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

//기간설정
const sortType2 = ref("3개월");
async function selectedSortType2(val) {
  sortType2.value = val;
  if (val === "당월") {
    searchParam.value.sellMonth = "0";
  } else if (val === "전월") {
    searchParam.value.sellMonth = "-1";
  } else if (val === "3개월") {
    searchParam.value.sellMonth = "-3";
  } else if (val === "6개월") {
    searchParam.value.sellMonth = "-6";
  } else if (val === "1년") {
    searchParam.value.sellMonth = "-12";
  } else if (val === "2년") {
    searchParam.value.sellMonth = "-24";
  } else if (val === "3년") {
    searchParam.value.sellMonth = "-36";
  }

  await setDt();
}

//기간 설정
const setDt = async () => {
  if (sortType2.value !== "직접입력") {
    searchParam.value.dateStartYmd = moment()
      .add(searchParam.value.sellMonth, "month")
      .startOf("month")
      .format("YYYYMMDD");
    searchParam.value.dateEndYmd = moment().format("YYYYMMDD");
  } else {
    searchParam.value.dateStartYmd = "";
    searchParam.value.dateEndYmd = "";
  }
};

// 기간 버튼 클릭
const onClickPeriod = async (val) => {
  sortType2.value = val;
  if (val === "당월") {
    searchParam.value.sellMonth = "0";
  } else if (val === "전월") {
    searchParam.value.sellMonth = "-1";
  } else if (val === "3개월") {
    searchParam.value.sellMonth = "-3";
  } else if (val === "6개월") {
    searchParam.value.sellMonth = "-6";
  } else if (val === "1년") {
    searchParam.value.sellMonth = "-12";
  } else if (val === "2년") {
    searchParam.value.sellMonth = "-24";
  } else if (val === "3년") {
    searchParam.value.sellMonth = "-36";
  }
  //필터표시
  // isActiveBtn2.value = filterbtnGroups2[0].buttons.findIndex(
  //   (obj) => obj.btnLabel == val
  // );
  isActiveBtn2.value = val;
  await setDt();
};

//필터 적용
function onClickFilterApply() {
  let sYmd = searchParam.value.dateStartYmd;
  let eYmd = searchParam.value.dateEndYmd;
  if (!util.isEmpty(sYmd) && !util.isEmpty(eYmd)) {
    if (moment(sYmd).isAfter(eYmd)) {
      util.errorNotify("시작날짜와 종료날짜를 확인해 주세요.");
      return false;
    }

    //날짜 포멧
    if (!util.isEmpty(sYmd)) {
      searchParam.value.startDt = moment(searchParam.value.dateStartYmd).format(
        "YYYYMMDD"
      );
    }
    if (!util.isEmpty(eYmd)) {
      searchParam.value.endDt = moment(searchParam.value.dateEndYmd).format(
        "YYYYMMDD"
      );
    }
  } else {
    //시작종료 또는 종료날짜를 빼먹고 적용버튼을 누를 수도 있을까봐 혹시 몰라서 유효성 추가
    if (util.isEmpty(sYmd)) {
      util.errorNotify("시작날짜를 확인해 주세요.");
      return false;
    }
    if (util.isEmpty(eYmd)) {
      util.errorNotify("종료날짜를 확인해 주세요.");
      return false;
    }
  }

  //필터표시
  if (!util.isEmpty(sortType2.value)) {
    // isActiveBtn2.value = filterbtnGroups2[0].buttons.findIndex(
    //   (obj) => obj.btnLabel == sortType2.value
    // );
    isActiveBtn2.value = sortType2.value;
  } else {
    isActiveBtn2.value = "";
  }

  //조회필터
  if (sortType1.value === "전체") {
    searchParam.value.subdivYn = "";
  } else if (sortType1.value === "소분제외") {
    searchParam.value.subdivYn = "N";
  } else if (sortType1.value === "소분만") {
    searchParam.value.subdivYn = "Y";
  }

  //필터표시
  if (!util.isEmpty(sortType1.value)) {
    // isActiveBtn1.value = filterbtnGroups1[0].buttons.findIndex(
    //   (obj) => obj.btnLabel == sortType1.value
    // );
    isActiveBtn1.value = sortType1.value;
  } else {
    isActiveBtn1.value = "";
  }

  dialog_filter.value = false;
  isFilterChecked.value = true;
  onClickSearch();
}

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  searchParam.value.startDt = moment(searchParam.value.dateStartYmd).format(
    "YYYYMMDD"
  );
  searchParam.value.endDt = moment(searchParam.value.dateEndYmd).format(
    "YYYYMMDD"
  );

  //조회필터 체크
  if (isActiveBtn1.value === -1) {
    alert("조회 조건을 선택해 주시기 바랍니다.");
    return;
  }

  loading.value = true;
  const res = await api.get("/bakReqDtl/listSearch", {
    params: searchParam.value,
  });
  loading.value = false;
  rows.value = res;
}

// 검색버튼 클릭
async function onClickSearch() {
  let sYmd = searchParam.value.dateStartYmd;
  let eYmd = searchParam.value.dateEndYmd;
  if (!util.isEmpty(sYmd) && !util.isEmpty(eYmd)) {
    //날짜 포멧
    if (!util.isEmpty(sYmd)) {
      searchParam.value.startDt = moment(searchParam.value.dateStartYmd).format(
        "YYYYMMDD"
      );
    }
    if (!util.isEmpty(eYmd)) {
      searchParam.value.endDt = moment(searchParam.value.dateEndYmd).format(
        "YYYYMMDD"
      );
    }
  }
  await fetchItemList();
}

//기간 클릭
bus.off("onClickPeriod");
bus.on("onClickPeriod", async (params) => {
  searchParam.value.startDt = params.value.startDt;
  searchParam.value.endDt = params.value.endDt;
  await fetchItemList();
});

// onMounted(async () => {
//   await setDate(3);
//   period.value = 3;
//   await fetchItemList();
// });

//날짜 임의변경
const onOnChangeDate = () => {
  // let index = filterbtnGroups2[0].buttons.findIndex(
  //   (obj) => obj.btnLabel == "직접입력"
  // );
  sortType2.value = "직접입력";
  searchParam.value.sellMonth = "";
  bus.emit("onOnChangeDate", sortType2.value);
};

// bus.off("closeDialogPopup");
// bus.on("closeDialogPopup", async () => {
//   //직전 선택 값으로 설정
//   selectedSortType2(filterbtnGroups2[0].buttons[isActiveBtn2.value]?.btnLabel);
// });

onMounted(async () => {
  //필터표시
  // isActiveBtn2.value = filterbtnGroups2[0].buttons.findIndex(
  //   (obj) => obj.btnLabel == "3개월"
  // );
  isActiveBtn2.value = "3개월";
  searchParam.value.sellMonth = "-3";
  setDt();
  await fetchItemList();
  setTimeout(() => {
    loading.value = false;
    show.value = true;
    nodata_show.value = true;
  }, 3000);
});
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  // padding: 6px 0 !important;
  gap: 8px;
  .table_item:nth-child(1) {
    @include flex_start;
    @include fw-6;
    align-items: flex-start;
    grid-column: span 10;
    gap: 4px;
    height: auto !important;
    td {
      // font-size: 16px;
      // overflow: hidden;
      // text-overflow: ellipsis;
      height: auto !important;
      white-space: normal;
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 2;
    td {
      @include flex_end;
      // @include fs-5;
      @include fw-6;
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 1;
    td {
      @include flex_start;
      // @include fs-6;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 4;
    td {
      @include flex_start;
      // @include fs-6;
      // font-size: 12px !important;
      // color: #666666;
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 1;
    td {
      @include flex_start;
      // @include fs-6;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 2;
    td {
      @include flex_start;
      // @include fs-6;
      // font-size: 12px !important;
      // color: #666666;
    }
  }
  .table_item:nth-child(7) {
    grid-column: span 1;
    td {
      @include flex_end;
      // @include fs-6;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(8) {
    grid-column: span 3;
    td {
      @include flex_end;
      // @include fs-6;
      // font-size: 12px !important;
      // color: #666666;
    }
  }
  .table_item:nth-child(9) {
    grid-column: span 1;
    td {
      @include flex_start;
      // @include fs-6;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(10) {
    grid-column: span 4;
    td {
      @include flex_start;
      // @include fs-6;
      // font-size: 12px !important;
      // color: #666666;
    }
  }
  .table_item:nth-child(11) {
    grid-column: span 1;
    td {
      @include flex_start;
      // @include fs-6;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(12) {
    grid-column: span 2;
    td {
      @include flex_start;
      // @include fs-6;
      // font-size: 12px !important;
      // color: #666666;
    }
  }
  .table_item:nth-child(13) {
    grid-column: span 1;
    td {
      @include flex_end;
      // @include fs-6;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(14) {
    grid-column: span 3;
    td {
      @include flex_end;
      // @include fs-6;
      // font-size: 12px !important;
      // color: #666666;
    }
  }
  .table_item:nth-child(15) {
    grid-column: span 5;
    td {
      @include flex_start;
      // @include fs-6;
      // font-size: 12px !important;
      overflow: hidden;
      text-overflow: ellipsis;
      display: block;
    }
  }
  .table_item:nth-child(16) {
    grid-column: span 1;
    td {
      @include flex_start;
      // @include fs-6;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(17) {
    grid-column: span 2;
    td {
      @include flex_start;
      // @include fs-6;
      // font-size: 12px !important;
      // color: #666666;
    }
  }
  .table_item:nth-child(18) {
    grid-column: span 4;
    td {
      @include flex_start;
      // @include fs-6;
      // font-size: 12px !important;
      margin-left: 2px;
    }
    .text-red {
      color: $red-400;
    }
  }
}

//기간 표시 스타일
// .button-container {
//   .btn-focus {
//     background-image: linear-gradient(to bottom, #1f3f82, #003853) !important;
//     color: #ffffff !important;
//   }
// }
</style>

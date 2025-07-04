<template>
  <div class="">
    <q-table
      class=""
      row-key="CUST_CD"
      :rows="rows"
      flat
      hide-header
      hide-bottom
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
    >
      <template v-slot:top>
        <div class="table_Top_wrap">
          <div class="table_Title_mobile">
            <span>{{ TableTitle }}</span>
          </div>
          <div class="filter_area">
            <div class="flex_between" style="gap: 8px">
              <InputComp_mobile
                placeholder="품목명"
                style="width: 50%"
                v-model:input_model_props="searchParam.itemNm"
                @keyup.enter="searchList"
              />
              <InputComp_mobile
                placeholder="부서명"
                style="width: 50%"
                v-model:input_model_props="searchParam.deptNm"
                @keyup.enter="searchList"
              />
            </div>
            <BtnComp_mobile
              buttonText="검색"
              btnStyle="default"
              class="btn_srch"
              @click="searchList"
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
                    :btnGroups="schFilterbtnGroups"
                    :isActiveBtn="isActiveFilterBtn"
                    @selected-single-btn="(val) => selectedSchGroup(val)"
                  />
                  <!-- <FilterComp_mobile
                    :btnGroups="dtFilterbtnGroups"
                    @selected-multi-btn="(val) => selectedDtGroup(val)"
                  /> -->
                  <div class="Filter_wrap">
                    <div class="Filter_Btn_wrap">
                      <div class="Filter_Title">
                        <span>기간설정</span>
                      </div>
                      <div class="Filter_DatePicker_wrap" :class="'on'">
                        <div class="flex_between" style="padding: 0 0 0 0">
                          <DatePickerComp_mobile
                            style="width: 50%"
                            v-model:input_model_props="searchParam.dateStartYmd"
                            @on-change-date="onOnChangeDate"
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
                          />
                        </div>
                      </div>
                    </div>
                  </div>
                  <FilterSingleComp_mobile_a
                    :btnGroups="sortFilterbtnGroups"
                    :isActiveBtn="isActiveSortBtn"
                    @selected-single-btn="(val) => selectedSortGroup(val)"
                  />
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
          <div class="" style="padding: 0 0 16px 0">
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
            <!-- <BtnGroupComp_mobile
              :buttons="periodBtnGroup"
              btnStyle="active"
              @on-click-btn="(val) => onClickPeriod(val)"
            /> -->
          </div>
          <div class="table_totalVal">
            <span class="table_total_Val">{{ rowval() }}</span>
            <span class="table_total_Text">개가 검색되었습니다.</span>
          </div>
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr v-show="show">
          <div class="table_wrap">
            <div class="table_item table_item_mobile">
              <div class="table_badge_wrap">
                <template v-if="props.row.MED_NM">
                  <!-- <q-badge class="table_badge_red">
                    {{ props.row.MED_NM }}
                  </q-badge>
                </template>
                <template v-if="props.row.PAY_ITEM_NM">
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
                </template>
                <template v-if="props.row.PAY_ITEM_NM">
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
                </template>
              </div>
              <q-td class="table-td_mobile">
                <p>
                  {{ props.row.ITEM_NM }}
                </p>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <BtnComp_mobile
                  buttonText="상세"
                  btnStyle="line_default"
                  @click="props.row.dialog_detail = true"
                />
                <DialogLayout_mobile
                  dialog_title="제품수불내역 > 제품수불내역 상세"
                  v-model:dialog_model_prop="props.row.dialog_detail"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <MoreReceiptComp_mobile
                      :stock="props.row.STOCK"
                      :makerNm="props.row.MAKER_NM"
                      :itemCd="props.row.ITEM_CD"
                      :startYmd="searchParam.dateStartYmd"
                      :endYmd="searchParam.dateEndYmd"
                      :sortType="searchParam.sortType"
                      :deptNm="searchParam.deptNm"
                    >
                      <div
                        class="flex_start"
                        style="
                          font-weight: 600;
                          gap: 8px;
                          padding: 8px 0;
                          align-items: flex-start;
                        "
                      >
                        <div class="table_badge_wrap">
                          <template v-if="props.row.MED_NM">
                            <!-- <q-badge class="table_badge_red">
                              {{ props.row.MED_NM }}
                            </q-badge>
                          </template>
                          <template v-if="props.row.PAY_ITEM_NM">
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
                          </template>
                          <template v-if="props.row.PAY_ITEM_NM">
                            <q-badge
                              class="bdg-line"
                              :class="
                                props.row.PAY_ITEM_NM === '비'
                                  ? 'nonpay'
                                  : 'pay'
                              "
                            >
                              {{
                                props.row.PAY_ITEM_NM === "급"
                                  ? "급여"
                                  : props.row.PAY_ITEM_NM === "비"
                                  ? "비급여"
                                  : props.row.PAY_ITEM_NM
                              }}
                            </q-badge>
                          </template>
                        </div>
                        <q-td class="table-td_mobile">{{
                          props.row.ITEM_NM
                        }}</q-td>
                        <q-td class="table-td_mobile">{{
                          props.row.UNIT
                        }}</q-td>
                      </div>
                    </MoreReceiptComp_mobile>
                  </div>
                </DialogLayout_mobile>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">규격</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.UNIT }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile"
                >{{ util.getNumberFormat(props.row.BRCH_WP2_AMT) }} 원</q-td
              >
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">재고</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{
                util.getNumberFormat(props.row.STOCK)
              }}</q-td>
            </div>
          </div>
        </q-tr>
        <!-- <q-inner-loading class="loading" :showing="loading" color="primary" /> -->
      </template>
      <template v-slot:loading>
        <div class="m-loading_wrap" v-if="loading === true">
          <q-inner-loading class="loading" :showing="loading" color="primary" />
        </div>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";

import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BtnFilterComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnFilterComp_mobile.vue";
import BtnGroupComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnGroupComp_mobile.vue";
import FilterSingleComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile.vue";
import FilterSingleComp_mobile_a from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile_a.vue";
import InputComp_mobile from "src/components/mobile/__commonComp/inputComp/InputComp_mobile.vue";
import MoreReceiptComp_mobile from "src/components/mobile/DialogComp/5.moreDialogComp/MoreReceiptComp_mobile.vue";
import FilterComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterComp_mobile.vue";
import DatePickerComp_mobile from "../../__commonComp/datepickerComp/DatePickerComp_mobile.vue";

const props = defineProps({
  TableTitle: {
    type: String,
    default: "TableTitle",
  },
});

//적용 CLASS
const btnResult = ref("on");

//로딩
const loading = ref(true);
const show = ref(false);

//필터 체크
const isFilterChecked = ref(false);

//사용자정보
const userInfo = util.getUserData();

//검색조건
const searchParam = ref({
  dlvBrchCd: userInfo.DLV_BRCH_CD, //배송지점코드
  itemNm: "", //품목명
  deptNm: "", //부서명
  sellMonth: "-3",
  subdivYn: "N", //조회필터 N: 소분제외, Y: 소분만, "": 전체
  startYmd: moment().add(-3, "months").startOf("month").format("YYYYMMDD"), //시작 기간설정
  endYmd: moment().format("YYYYMMDD"), //종료 기간설정
  dateStartYmd: moment()
    .add(-3, "months")
    .startOf("month")
    .format("YYYY-MM-DD"), //필터 시작 기간설정
  dateEndYmd: moment().format("YYYY-MM-DD"), //필터 종료 기간설정
  sortType: "R", //정렬 R: 최신순, P: 과거순
});

//필터 visible
const dialog_full = ref(true);
const dialog_filter = ref(false);

//제품수불내역 그리드
const rows = ref([]);

//조회 건수
const rowval = () => {
  return rows.value.length;
};

//기간 버튼 그룹
const periodBtnGroup = ["당월", "전월", "3개월", "6개월", "1년", "2년", "3년"];

//기간 설정
const setDt = async () => {
  searchParam.value.dateStartYmd = moment()
    .add(searchParam.value.sellMonth, "month")
    .startOf("month")
    .format("YYYYMMDD");
  searchParam.value.dateEndYmd = moment().format("YYYYMMDD");
};

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

// 기간 버튼 클릭
const onClickPeriod = async (val) => {
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

  await searchList();
};

//조회필터
const schFilterType = ref("소분제외");
const schFilterbtnGroups = [
  {
    title: "조회필터",
    subtitle: "",
    gridStyle: 3,
    btnDatePicker: "off",
    buttons: [
      { btnLabel: "소분제외" },
      { btnLabel: "소분만" },
      { btnLabel: "전체" },
    ],
  },
];

//기간설정 필터
const dtFilterType = ref("");
const dtFilterbtnGroups = [
  {
    title: "기간설정",
    subtitle: "",
    gridStyle: 0,
    btnDatePicker: "on",
  },
];

//정렬순서 필터
const sortType = ref("최신순");
const sortFilterbtnGroups = [
  {
    title: "정렬순서",
    subtitle: "",
    gridStyle: 2,
    btnDatePicker: "off",
    buttons: [{ btnLabel: "최신순" }, { btnLabel: "과거순" }],
  },
];

//조회필터 선택
const selectedSchGroup = (val) => {
  schFilterType.value = val;
};

//기간설정 선택
// const selectedDtGroup = (val) => {
// };

//정렬순서 선택
const selectedSortGroup = (val) => {
  sortType.value = val;
};

//조회필터 표시
const isActiveFilterBtn = ref("소분제외");
//정렬순서 표시
const isActiveSortBtn = ref("최신순");
// 필터 적용 버튼 클릭
const onClickFilterApply = () => {
  //기간설정에 값이 있다면 혹시모르니 유효성 체크한번 해주자
  let sYmd = searchParam.value.dateStartYmd;
  let eYmd = searchParam.value.dateEndYmd;
  if (!util.isEmpty(sYmd) && !util.isEmpty(eYmd)) {
    if (moment(sYmd).isAfter(eYmd)) {
      util.errorNotify("시작날짜와 종료날짜를 확인해 주세요.");
      return false;
    }

    //기간설정 값 있는지 일단 체크하고 기간설정 날짜 포맷
    if (!util.isEmpty(sYmd)) {
      searchParam.value.startYmd = moment(sYmd).format("YYYYMMDD");
    }

    if (!util.isEmpty(eYmd)) {
      searchParam.value.endYmd = moment(eYmd).format("YYYYMMDD");
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

  //기간표시
  const diffMonths = moment(sYmd).diff(moment(eYmd), "months");
  if (searchParam.value.sellMonth !== "") {
    searchParam.value.sellMonth = diffMonths.toString();
  }

  //조회필터
  if (schFilterType.value === "소분제외") {
    searchParam.value.subdivYn = "N";
  } else if (schFilterType.value === "소분만") {
    searchParam.value.subdivYn = "Y";
  } else if (schFilterType.value === "전체") {
    searchParam.value.subdivYn = "";
  }

  //정렬순서 선택
  if (sortType.value === "최신순") {
    searchParam.value.sortType = "R";
  } else if (sortType.value === "과거순") {
    searchParam.value.sortType = "P";
  }

  //조회필터표시
  if (!util.isEmpty(schFilterType.value)) {
    // isActiveFilterBtn.value = schFilterbtnGroups[0].buttons.findIndex(
    //   (obj) => obj.btnLabel == schFilterType.value
    // );
    isActiveFilterBtn.value = schFilterType.value;
  }

  //정렬필터표시
  if (!util.isEmpty(sortType.value)) {
    // isActiveSortBtn.value = sortFilterbtnGroups[0].buttons.findIndex(
    //   (obj) => obj.btnLabel == sortType.value
    // );
    isActiveSortBtn.value = sortType.value;
  }

  dialog_filter.value = false;
  isFilterChecked.value = true;
  searchList();
};

//기간 선택 디폴트 값 당월
const onClicksellMonth = async (val) => {
  //선택하면 바로 조회되게?
  searchParam.value.sellMonth = val;
  rows.value = [];
  await searchList();
};

//사용자 임의 변경
const onOnChangeDate = () => {
  searchParam.value.sellMonth = "";
};

//제품수불내역 조회
const searchList = async () => {
  searchParam.value.startYmd = moment(searchParam.value.dateStartYmd).format(
    "YYYYMMDD"
  );
  searchParam.value.endYmd = moment(searchParam.value.dateEndYmd).format(
    "YYYYMMDD"
  );

  //품목명, 부서명 유효성 체크
  if (util.isEmpty(searchParam.value.itemNm)) {
    util.errorNotifyTop("품목명을 입력해 주세요.");
    return false;
  }

  loading.value = true;
  // if (util.isEmpty(searchParam.value.deptNm)) {
  //   util.errorNotify("부서명을 입력해 주세요.");
  //   return false;
  // }

  return api
    .get("/ordLedger/selectItemReceivePayList", {
      params: searchParam.value,
    })
    .then((response) => {
      show.value = true;
      rows.value = response;
      loading.value = false;
    })
    .finally(() => {});
};

onMounted(() => {
  setTimeout(() => {
    loading.value = false;
    show.value = true;
  }, 3000);
  searchParam.value.sellMonth = "-3";
});
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  // padding: 8px 0 !important;
  gap: 8px;
  .table_item:nth-child(1) {
    @include flex_start;
    @include fw-7;
    align-items: flex-start;
    grid-column: span 9;
    gap: 4px;
    height: auto !important;
    td {
      // font-size: 15.5px;
      // overflow: hidden;
      // text-overflow: ellipsis;
      white-space: normal;
      height: auto !important;
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 8;
    td {
      @include flex_start;
      @include fs-6;
      // color: #666666;
      overflow: hidden;
      text-overflow: ellipsis;
      display: block;
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 1;
    td {
      @include flex_center;
      @include fs-5;
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 1;
    td {
      @include flex_end;
      // @include fs-5;
      // color: #666666;
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 8;
    td {
      @include flex_start;
      @include fs-5;
      @include fw-7;
    }
  }
  .table_item:nth-child(7) {
    grid-column: span 1;
    td {
      @include flex_center;
      @include fs-5;
      @include fw-7;
    }
  }
  .table_item:nth-child(8) {
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
      @include fw-7;
      // color: #666666;
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

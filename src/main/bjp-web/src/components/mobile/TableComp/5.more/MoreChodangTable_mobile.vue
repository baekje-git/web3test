<template>
  <div>
    <TabLayout_mobile
      TabTitle="초당약품 매출현황"
      First_btn="일반현황"
      Second_btn="거래처별"
      @on-change-tab="(val) => gridClear(val)"
    >
      <template v-slot:table_top>
        <div class="table_Top_wrap">
          <div class="filter_area">
            <template v-if="tabSe === 'first_tab'">
              <div class="flex_start" style="width: calc(100% - 67px)">
                <DatePickerComp_mobile
                  style="width: 50%"
                  v-model:input_model_props="searchParam.dateStartYmd"
                  @enter="searchList('N')"
                />
                <span style="font-size: 12px; color: #999999; margin: 0 4px"
                  >~</span
                >
                <DatePickerComp_mobile
                  style="width: 50%"
                  v-model:input_model_props="searchParam.dateEndYmd"
                  @enter="searchList('N')"
                />
              </div>
              <BtnComp_mobile
                buttonText="검색"
                btnStyle="default"
                class="btn_srch"
                @click="searchList('N')"
              />
            </template>
            <template v-else>
              <div class="flex_between">
                <DatePickerComp_mobile
                  style="width: 50%"
                  v-model:input_model_props="searchParam.dateStartYmd"
                  @enter="searchList('C')"
                />
                <span style="font-size: 12px; color: #999999; margin: 0 4px"
                  >~</span
                >
                <DatePickerComp_mobile
                  style="width: 50%"
                  v-model:input_model_props="searchParam.dateEndYmd"
                  @enter="searchList('C')"
                />
              </div>
              <BtnComp_mobile
                buttonText="검색"
                btnStyle="default"
                class="btn_srch"
                @click="searchList('C')"
              />
              <div class="table_Filter" @click="dialog_filter = true">
                <BtnFilterComp_mobile :filterCheck="isFilterChecked" />
                <DialogLayout_mobile
                  dialog_title="검색 필터"
                  v-model:dialog_model_prop="dialog_filter"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <FilterSingleComp_mobile_a
                      :btnGroups="filterbtnGroups"
                      :isActiveBtn="isActiveBtn"
                      @selected-single-btn="(val) => selectedSortType(val)"
                    />
                    <BtnComp_mobile
                      :class="btnResult"
                      buttonText="적용"
                      btnStyle="default"
                      btnSize="lg"
                      class="btn-full"
                      style="padding-top: 20px"
                      @click="onClickFilterApply('C')"
                    />
                  </div>
                </DialogLayout_mobile>
              </div>
            </template>
          </div>
        </div>
      </template>
      <template v-slot:first_tab>
        <div class="">
          <q-table
            class=""
            row-key="DEPT_NM"
            :rows="rows"
            :columns="columns"
            flat
            hide-bottom
            :rows-per-page-options="[0]"
            :virtual-scroll-sticky-size-start="0"
            :loading="loading"
            style="padding-bottom: 30px"
          >
            <template v-slot:header="props">
              <div class="first_table_header_wrap">
                <div class="table_header_item">
                  <span>{{ props.cols[0].label }}</span>
                </div>
                <div class="table_header_item">
                  <span>{{ props.cols[1].label }}</span>
                </div>
                <div class="table_header_item">
                  <span>{{ props.cols[2].label }}</span>
                </div>
                <div class="table_header_item">
                  <span>{{ props.cols[3].label }}</span>
                </div>
                <div class="table_header_item">
                  <span>{{ props.cols[4].label }}</span>
                </div>
              </div>
            </template>

            <template v-slot:body="props">
              <q-tr>
                <div class="first_table_wrap">
                  <div class="table_item">
                    <q-td>{{ props.row.DEPT_NM }}</q-td>
                  </div>
                  <div class="table_item">
                    <q-td>{{ util.getNumberFormat(props.row.AIM_AMT) }}</q-td>
                  </div>
                  <div class="table_item">
                    <q-td>{{ props.row.SAL_PERCENT }}</q-td>
                  </div>
                  <div class="table_item">
                    <q-td>{{
                      util.getNumberFormat(props.row.OTC_YMD_AMT)
                    }}</q-td>
                  </div>
                  <div class="table_item">
                    <q-td>{{
                      util.getNumberFormat(props.row.OTC_YM_AMT)
                    }}</q-td>
                  </div>
                </div>
              </q-tr>
              <!-- <q-inner-loading
                class="loading"
                :showing="loading"
                color="primary"
              /> -->
            </template>
            <template v-slot:loading>
              <div class="m-loading_wrap" v-if="loading === true">
                <q-inner-loading
                  class="loading"
                  :showing="loading"
                  color="primary"
                />
              </div>
            </template>
          </q-table>
        </div>
      </template>
      <template v-slot:second_tab>
        <div class="">
          <q-table
            class=""
            row-key="DEAL_DT"
            :rows="rows"
            :columns="custColumns"
            flat
            hide-bottom
            :rows-per-page-options="[0]"
            :virtual-scroll-sticky-size-start="0"
            :loading="loading"
          >
            <template v-slot:header="props">
              <div class="second_table_header_wrap">
                <div class="table_header_item">
                  <p>{{ props.cols[0].label }}</p>
                </div>
                <div class="table_header_item">
                  <div class="table_header_grid_item">
                    <p>{{ props.cols[1].label }}</p>
                  </div>
                  <div class="table_header_grid_item">
                    <p>{{ props.cols[2].label }}</p>
                  </div>
                  <div class="table_header_grid_item">
                    <p>{{ props.cols[3].label }}</p>
                  </div>
                  <div class="table_header_grid_item">
                    <p>{{ props.cols[4].label }}</p>
                  </div>
                </div>
              </div>
            </template>

            <template v-slot:body="props">
              <q-tr v-show="cshow">
                <div class="second_table_wrap">
                  <div class="table_item">
                    <q-td>{{
                      util.isEmpty(props.row.SALE_DT)
                        ? ""
                        : moment(
                            props.row.SALE_DT,
                            "YYYY-MM-DD HH:mm:ss"
                          ).format("YYYY-MM-DD")
                    }}</q-td>
                  </div>
                  <div class="table_item">
                    <div class="table_grid_item">
                      <q-td>{{ props.row.ITEM_NM_UNIT }}</q-td>
                    </div>
                    <div class="table_grid_item">
                      <q-td>{{ props.row.CUST_NM }}</q-td>
                    </div>
                    <div class="table_grid_item">
                      <q-td>{{
                        util.getNumberFormat(props.row.ITEM_QTY)
                      }}</q-td>
                    </div>
                    <div class="table_grid_item">
                      <q-td>{{
                        util.getNumberFormat(props.row.ITEM_AMT)
                      }}</q-td>
                    </div>
                  </div>
                </div>
              </q-tr>
              <!-- <q-inner-loading
                class="loading"
                :showing="loading"
                color="primary"
              /> -->
            </template>
            <template v-slot:loading>
              <div class="m-loading_wrap" v-if="loading === true">
                <q-inner-loading
                  class="loading"
                  :showing="loading"
                  color="primary"
                />
              </div>
            </template>
          </q-table>
        </div>
      </template>
    </TabLayout_mobile>
  </div>
</template>
<script setup>
import { onMounted, ref } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";

import TabLayout_mobile from "src/layouts/mobile/TabLayouts/TabLayout_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import DatePickerComp_mobile from "src/components/mobile/__commonComp/datepickerComp/DatePickerComp_mobile.vue";
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BtnFilterComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnFilterComp_mobile.vue";
import FilterSingleComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile.vue";
import FilterSingleComp_mobile_a from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile_a.vue";

//적용 CLASS
const btnResult = ref("on");

//로딩
//로딩
const loading = ref(true);
const show = ref(false);
const nodata_show = ref(false);

const nshow = ref(false);
const cshow = ref(false);

//tab구분
const tabSe = ref("first_tab");

//필터 체크
const isFilterChecked = ref(false);

//사용자정보
const userInfo = util.getUserData();

//검색조건
const searchParam = ref({
  brchCd: userInfo.BRCH_CD, //지점코드
  startYmd: moment().startOf("month").format("YYYYMMDD"), //시작날짜 디폴트 값:현재월의 첫날
  endYmd: moment().format("YYYYMMDD"), //종료날짜 디폴트 값:현재일자
  dateStartYmd: moment().startOf("month").format("YYYY-MM-DD"), //검색필터 시작날짜
  dateEndYmd: moment().format("YYYY-MM-DD"), //검색필터 종료날짜
  type: "N", //N:일반현황, C: 거래처별
  sortType: "B", //정렬 B: 기본정렬, D: 일자순, C: 거래처순, I: 품목명순
});

//필터 visible
const dialog_filter = ref(false);
const dialog_full = ref(true);

//초당약품 매출현황 그리드
const rows = ref([]);

//초당약품 매출현황 일반현황 컬럼
const columns = ref([
  { name: "DEPT_NM", label: "부서명", align: "left" },
  { name: "AIM_AMT", label: "목표액", align: "right" },
  { name: "SAL_PERCENT", label: "매출%", align: "right" },
  { name: "OTC_YMD_AMT", label: "일매출(일반)", align: "right" },
  { name: "OTC_YM_AMT", label: "월매출(일반)", align: "right" },
]);

//초당약품 매출현황 거래처별 컬럼
const custColumns = ref([
  { name: "SALE_DT", label: "거래일자", align: "center" },
  { name: "ITEM_NM_UNIT", label: "품목명", align: "center" },
  { name: "CUST_NM", label: "거래처명", align: "center" },
  { name: "ITEM_QTY", label: "수량", align: "right" },
  { name: "ITEM_AMT", label: "금액", align: "right" },
]);

//정렬타입
const sortType = ref("기본정렬");

//초당약품 매출현황 필터 종류
const filterbtnGroups = [
  {
    title: "정렬순서",
    subtitle: "",
    gridStyle: 4,
    btnDatePicker: "off",
    buttons: [
      { btnLabel: "기본정렬" },
      { btnLabel: "일자순" },
      { btnLabel: "거래처순" },
      { btnLabel: "품목명순" },
    ],
  },
];

//정렬 선택
const selectedSortType = (val) => {
  sortType.value = val;
};

//현재 적용중인 버튼 표시
const isActiveBtn = ref("기본정렬");
// 필터 적용 버튼 클릭
const onClickFilterApply = (type) => {
  dialog_filter.value = false;

  if (sortType.value === "기본정렬") {
    searchParam.value.sortType = "B";
  } else if (sortType.value === "일자순") {
    searchParam.value.sortType = "D";
  } else if (sortType.value === "거래처순") {
    searchParam.value.sortType = "C";
  } else if (sortType.value === "품목명순") {
    searchParam.value.sortType = "I";
  }

  //필터표시
  // isActiveBtn.value = filterbtnGroups[0].buttons.findIndex(
  //   (obj) => obj.btnLabel == sortType.value
  // );
  isActiveBtn.value = sortType.value;
  //필터 체크 표시
  isFilterChecked.value = true;

  searchList(type);
};

//날짜 유효성검사
const checkDateValid = (sYmd, eYmd) => {
  if (moment(sYmd).isAfter(eYmd)) {
    util.errorNotify("시작날짜가 종료날짜보다 클 수 없습니다.");
    return false;
  }

  if (moment(eYmd).isBefore(sYmd)) {
    util.errorNotify("종료날짜가 시작날짜보다 작을 수 없습니다.");
    return false;
  }
};

//초당약품 매출현황 검색 N:일반, C:거래처
const searchList = (type) => {
  loading.value = true;

  let url = "";
  if (type === "C") {
    url = "/ordLedger/selectChoSalesStateCustList";
    cshow.value = true;
  } else {
    url = "/ordLedger/selectChoSalesStateNorList";
    nshow.value = true;
  }

  let sYmd = searchParam.value.dateStartYmd; //시작날짜
  let eYmd = searchParam.value.dateEndYmd; //종료날짜

  //혹시몰라서 날짜 유효성 체크
  if (util.isEmpty(sYmd)) {
    util.errorNotify("시작날짜를 선택해 주세요.");
    return false;
  }
  if (util.isEmpty(eYmd)) {
    util.errorNotify("종료날짜를 선택해 주세요.");
    return false;
  }

  //혹시몰라서 시작일자가 종료일자보다 뒤거나 혹은 종료일자가 시작일자보다 앞이면 안되게 유효성 추가
  if (moment(sYmd).isAfter(eYmd)) {
    util.errorNotify("시작날짜와 종료날짜를 확인해 주세요.");
    return false;
  }

  //날짜 포멧팅
  searchParam.value.startYmd = moment(sYmd).format("YYYYMMDD");
  searchParam.value.endYmd = moment(eYmd).format("YYYYMMDD");

  return api
    .get(url, {
      params: searchParam.value,
    })
    .then((response) => {
      rows.value = response
        .filter((x) => x.CUST_NM !== "")
        .concat(response.filter((x) => x.CUST_NM === ""));
      loading.value = false;
    });
};

//Tab 변경할 때마다 그리드 초기화 - 기존 모바일 화면 보니 Tab변경할 때마다 초기화 되길래 추가함
const gridClear = (val) => {
  loading.value = true;

  let type = "N";
  val == "first_tab" ? (type = "N") : (type = "C");

  let tab = val;

  if (tab === "first_tab") {
    searchParam.value.sortType = "";
  } else {
    searchParam.value.sortType = "B";
  }
  isFilterChecked.value = false;
  rows.value = [];
  tabSe.value = val;
  isActiveBtn.value = "기본정렬";
  searchList(type);
};

onMounted(async () => {
  setTimeout(() => {
    loading.value = false;
    show.value = true;
    nodata_show.value = true;
  }, 3000);
  await searchList("N");
});
</script>

<style lang="scss" scoped>
.table_Top_wrap {
  margin-left: 20px;
  width: calc(100% - 40px);
}
.first_table_header_wrap {
  display: grid;
  grid-template-columns: repeat(5, minmax(calc(100% / 5), 1fr));
  .table_header_item {
    text-align: center;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    padding: 8px 1px !important;
    word-break: keep-all;
    span {
      @include mfs-5;
      @include fw-7;
    }
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    /* text-align: center;
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    p {
      @include fs-5;
      @include fw-7;
    } */
  }

  /* .table_header_item:nth-child(1) {
    @include flex_center;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  }
  .table_header_item:nth-child(2),
  .table_header_item:nth-child(3),
  .table_header_item:nth-child(4),
  .table_header_item:nth-child(5) {
    p {
      @include flex_center;
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }
  } */

  .table_header_item:nth-child(4),
  .table_header_item:nth-child(5) {
    span {
      @include mfs-6;
    }
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  }

  .table_header_item:last-child {
    border-right: none;
  }
}

.first_table_wrap {
  display: grid;
  grid-template-columns: repeat(5, minmax(calc(100% / 5), 1fr));
  border: none;
  .table_item {
    /* padding: 4px 0 !important; */
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    td {
      @include flex_start;
      @include fs-6;
      white-space: normal;
      word-break: break-all;
    }
  }
  .table_item:nth-child(1) {
    td {
      @include flex_start;
      @include mfs-6;
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }
  }
  .table_item:nth-child(2),
  .table_item:nth-child(3),
  .table_item:nth-child(4),
  .table_item:nth-child(5) {
    td {
      @include flex_end;
      @include mfs-6;
      font-size: 11px !important;
      padding: 12px 4px !important;
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
      text-align: right;
    }
  }
  .table_item:last-child {
    border: none;
  }
}

.second_table_header_wrap {
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  .table_header_item {
    text-align: center;
    /* padding: 6px 0 !important; */
    display: inline-flex;
    align-items: center;
    justify-content: center;
    word-break: keep-all;
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    p {
      padding: 8px 0;
      @include fs-5;
      @include fw-7;
    }
    .table_header_grid_item {
      border-right: 1px solid rgba(0, 0, 0, 0.1);
    }
    .table_header_grid_item:last-child {
      border: none;
    }
  }
  .table_header_item:nth-child(1) {
    @include flex_center;
    grid-column: span 2;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  }
  .table_header_item:nth-child(2) {
    grid-column: span 6;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    .table_header_grid_item {
      /* padding: 2px 0 !important; */
      text-align: center;
    }
    .table_header_grid_item:nth-child(1) {
      grid-column: span 3 !important;
      border: none;
    }
    p {
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }
  }
  .table_header_item:last-child {
    border: none;
  }
}

.second_table_wrap {
  display: grid;
  grid-template-columns: repeat(8, minmax(calc(100% / 8), 1fr));
  border: none;
  .table_item {
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    td {
      @include flex_center;
      @include mfs-6;
    }
    &:not(:nth-child(1)) td {
      white-space: normal;
      word-break: break-all;
    }
  }
  .table_item:nth-child(1) {
    @include flex_center;
    grid-column: span 2;
    /* border-bottom: 1px solid rgba(0, 0, 0, 0.1); */
  }
  .table_item:nth-child(2) {
    grid-column: span 6;
    display: grid;
    grid-template-columns: repeat(3, minmax(calc(100% / 3), 1fr));
    /* margin-left: 4px; */
    .table_grid_item {
      td {
        padding: 2px 4px 2px 2px !important;
        text-align: center;
        /* border-right: 1px solid rgba(0, 0, 0, 0.1); */
        border-bottom: 1px solid rgba(0, 0, 0, 0.1);
        word-break: break-all;
        white-space: normal;
      }
    }
    .table_grid_item:nth-child(1) {
      grid-column: span 3 !important;
      border-right: none;
      td {
        @include flex_start;
        text-align: left;
        white-space: normal;
        /* border-bottom: 1px solid rgba(0, 0, 0, 0.1); */
      }
    }
    .table_grid_item:nth-child(2) {
      grid-column: span 1;
      td {
        text-align: left !important;
        @include fw-7;
        font-size: 11px !important;
        border-right: 1px solid rgba(0, 0, 0, 0.1);
        /* border-bottom: 1px solid rgba(0, 0, 0, 0.1); */
        white-space: normal !important;
        /* word-break: break-all; */
      }
    }
    .table_grid_item:nth-child(3) {
      td {
        @include flex_end;
        border-right: 1px solid rgba(0, 0, 0, 0.1);
        /* border-bottom: 1px solid rgba(0, 0, 0, 0.1); */
        text-align: right;
      }
    }
    .table_grid_item:nth-child(4) {
      td {
        @include flex_end;
        /* border-bottom: 1px solid rgba(0, 0, 0, 0.1); */
        text-align: right;
      }
    }
  }
  .table_item:last-child {
    border: none;
  }
}
</style>

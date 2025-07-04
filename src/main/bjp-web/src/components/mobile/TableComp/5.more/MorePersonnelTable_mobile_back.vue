<template>
  <div>
    <TabLayout_mobile
      TabTitle="담당자별 영업현황"
      First_btn="일별"
      Second_btn="월별"
      @on-change-tab="(val) => gridClear(val)"
    >
      <template v-slot:table_top>
        <div class="table_Top_wrap" style="margin: 3px 0">
          <div class="filter_area">
            <template v-if="tabSe === 'first_tab'">
              <div class="table_Filter" @click="dialog_filter = true">
                <!-- <img src="/public/assets/icons/sliders-solid.svg" alt="icon"> -->
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
                      @click="onClickFilterApply('D')"
                    />
                  </div>
                </DialogLayout_mobile>
              </div>
              <div class="flex_between" style="width: calc(100% - 92px)">
                <DatePickerComp_mobile
                  style="width: 100%"
                  v-model:input_model_props="searchParam.dateYmd"
                  @enter="searchList('D')"
                />
              </div>
              <BtnComp_mobile
                buttonText="검색"
                class="btn_srch"
                @click="searchList('D')"
              />
            </template>
            <template v-else>
              <div class="table_Filter" @click="dialog_filter = true">
                <!-- <img src="/public/assets/icons/sliders-solid.svg" alt="icon" /> -->
                <BtnFilterComp_mobile :filterCheck="isMonthFilterChecked" />
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
              <div class="flex_between" style="width: calc(100% - 92px)">
                <DatePickerMonthComp_mobile
                  style="width: 100%"
                  v-model:input_model_props="searchParam.dateYm"
                  @enter="searchList('M')"
                />
              </div>
              <BtnComp_mobile
                buttonText="검색"
                btnStyle="default"
                class="btn_srch"
                @click="searchList('M')"
              />
            </template>
          </div>
        </div>
      </template>
      <template v-slot:first_tab>
        <div class="">
          <q-table
            row-key="DEPT_NM"
            :rows="rows"
            :columns="dColumns"
            flat
            :hide-bottom="!rows || rows.length === 0 ? false : true"
            :rows-per-page-options="[0]"
            :virtual-scroll-sticky-size-start="0"
            :loading="loading"
            style="padding-bottom: 30px"
          >
            <template v-slot:header="props">
              <div class="first_table_header_wrap">
                <div class="table_header_item">
                  <p>{{ props.cols[0].label }}</p>
                </div>
                <div class="table_header_item">
                  <p>{{ props.cols[1].label }}</p>
                </div>
                <div class="table_header_item">
                  <p>{{ props.cols[2].label }}</p>
                </div>
                <div class="table_header_item">
                  <p>{{ props.cols[3].label }}</p>
                </div>
              </div>
            </template>

            <template v-slot:body="props">
              <q-tr v-show="dshow">
                <div class="first_table_wrap">
                  <div class="table_item">
                    <q-td class="text_normal">{{ props.row.DEPT_NM }}</q-td>
                  </div>
                  <div class="table_item">
                    <q-td>{{ util.getNumberFormat(props.row.SALE_AMT) }}</q-td>
                  </div>
                  <div class="table_item">
                    <q-td>{{ util.getNumberFormat(props.row.BACK_AMT) }}</q-td>
                  </div>
                  <div class="table_item">
                    <q-td>{{ util.getNumberFormat(props.row.SALE_TOT) }}</q-td>
                  </div>
                </div>
              </q-tr>
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

            <template v-slot:no-data>
              <div class="table_nodata" v-if="nodata_show === true">
                <!-- <span>데이터가 없습니다.</span> -->
              </div>
            </template>
          </q-table>
        </div>
      </template>
      <template v-slot:second_tab>
        <div class="">
          <q-table
            class=""
            row-key="DEPT_NM"
            :rows="rows"
            :columns="mColumns"
            flat
            hide-bottom
            :rows-per-page-options="[0]"
            :virtual-scroll-sticky-size-start="0"
            :loading="loading"
            style="padding-bottom: 30px"
          >
            <!-- <template v-slot:top>
              <div class="table_Top_wrap" style="margin-bottom: 3px">
                <div class="flex_between" style="padding: 0 0 14px 0">
                  <div class="flex_start" style="gap: 10px">
                    <div class="table_Filter" @click="dialog_filter = true">
                      <img src="/public/assets/icons/sliders-solid.svg" alt="icon">
                      <BtnFilterComp_mobile
                        :filterCheck="isMonthFilterChecked"
                      />
                      <DialogLayout_mobile
                        dialog_title="검색 필터"
                        v-model:dialog_model_prop="dialog_filter"
                        :dialog_full="dialog_full"
                      >
                        <div class="">
                          <FilterSingleComp_mobile
                            :btnGroups="filterbtnGroups"
                            @selected-single-btn="
                              (val) => selectedSortType(val)
                            "
                          />
                          <BtnComp_mobile
                            :class="btnResult"
                            buttonText="적용"
                            btnStyle="default"
                            btnSize="lg"
                            style="padding-top: 20px"
                            @click="onClickFilterApply('C')"
                          />
                        </div>
                      </DialogLayout_mobile>
                    </div>
                    <DatePickerMonthComp_mobile
                      style="width: 105px"
                      v-model:input_model_props="searchParam.dateYm"
                    />
                  </div>
                  <BtnComp_mobile
                    buttonText="검색"
                    btnStyle="default"
                    style="width: 64px"
                    @click="searchList('M')"
                  />
                </div>
              </div>
            </template> -->

            <template v-slot:header="props">
              <div class="second_table_header_wrap">
                <div class="table_header_item">
                  <p>{{ props.cols[0].label }}</p>
                </div>
                <div class="table_header_item">
                  <p>{{ props.cols[1].label }}</p>
                  <p>{{ props.cols[2].label }}</p>
                </div>
                <div class="table_header_item">
                  <p>{{ props.cols[3].label }}</p>
                  <p>{{ props.cols[4].label }}</p>
                </div>
                <div class="table_header_item">
                  <p>{{ props.cols[5].label }}</p>
                  <p>{{ props.cols[6].label }}</p>
                </div>
              </div>
            </template>

            <template v-slot:body="props">
              <q-tr v-show="mshow">
                <div class="second_table_wrap">
                  <div class="table_item">
                    <q-td class="text_normal">{{ props.row.DEPT_NM }}</q-td>
                  </div>
                  <div class="table_item">
                    <q-td>{{
                      util.getNumberFormat(props.row.SALE_AIM_AMT)
                    }}</q-td>
                    <q-td>{{ util.getNumberFormat(props.row.SALE_AMT) }}</q-td>
                  </div>
                  <div class="table_item">
                    <q-td>{{
                      util.getNumberFormat(props.row.SALE_AIM_RATE)
                    }}</q-td>
                    <q-td>{{ props.row.M_PROFIT1_RATE }}</q-td>
                  </div>
                  <div class="table_item">
                    <q-td>{{ util.getNumberFormat(props.row.SALE_TOT) }}</q-td>
                    <q-td>{{ util.getNumberFormat(props.row.BACK_AMT) }}</q-td>
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
import { ref, onMounted } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";

import TabLayout_mobile from "src/layouts/mobile/TabLayouts/TabLayout_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import DatePickerComp_mobile from "src/components/mobile/__commonComp/datepickerComp/DatePickerComp_mobile.vue";
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BtnFilterComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnFilterComp_mobile.vue";
import DatePickerMonthComp_mobile from "src/components/mobile/__commonComp/datepickerComp/DatePickerMonthComp_mobile.vue";
import FilterSingleComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile.vue";
import FilterSingleComp_mobile_a from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile_a.vue";

//적용 CLASS
const btnResult = ref("on");
const dshow = ref(false);
const mshow = ref(false);

//로딩
const loading = ref(true);
const show = ref(false);
const nodata_show = ref(false);

//tab구분
const tabSe = ref("first_tab");

//필터 체크
const isFilterChecked = ref(false);
const isMonthFilterChecked = ref(false);

//사용자정보
const userInfo = util.getUserData();

//검색조건
const searchParam = ref({
  brchCd: userInfo.DLV_BRCH_CD, //지점코드
  saleYmd: moment().format("YYYYMMDD"), //검색날짜
  dateYmd: moment().format("YYYY-MM-DD"), //검색필터날짜
  dateYm: moment().format("YYYY-MM"), //검색필터날짜
  sortType: "C", //정렬 C: 거래처, I: 매출순, S: 판매순
});

//필터 visible
const dialog_filter = ref(false);
const dialog_full = ref(true);

//일별,월별 그리드
const rows = ref([]);

//일별 컬럼
const dColumns = ref([
  { name: "DEPT_NM", label: "부서명", align: "center" },
  {
    name: "SALE_AMT",
    label: "매출",
    align: "right",
  },
  {
    name: "BACK_AMT",
    label: "반품",
    align: "right",
  },
  {
    name: "SALE_TOT",
    label: "판매",
    align: "right",
  },
]);

//월별 컬럼
const mColumns = ref([
  { name: "DEPT_NM", label: "부서명", align: "center" },
  {
    name: "SALE_AIM_AMT",
    label: "매출목표",
    align: "right",
  },
  {
    name: "SALE_AMT",
    label: "매출",
    align: "right",
  },
  {
    name: "SALE_AIM_RATE",
    label: "매출%",
    align: "right",
  },
  {
    name: "M_PROFIT1_RATE",
    label: "이익률",
    align: "right",
  },
  {
    name: "SALE_TOT",
    label: "판매",
    align: "right",
  },
  {
    name: "BACK_AMT",
    label: "반품",
    align: "right",
  },
]);

//정렬타입
const sortType = ref("기본정렬");

//일별,월별 필터 종류
const filterbtnGroups = [
  {
    title: "정렬순서",
    subtitle: "",
    gridStyle: 3,
    btnDatePicker: "off",
    buttons: [
      { btnLabel: "기본정렬" },
      { btnLabel: "매출순" },
      { btnLabel: "판매순" },
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
    searchParam.value.sortType = "C";
  } else if (sortType.value === "매출순") {
    searchParam.value.sortType = "I";
  } else if (sortType.value === "판매순") {
    searchParam.value.sortType = "S";
  }

  //필터표시
  // isActiveBtn.value = filterbtnGroups[0].buttons.findIndex(
  //   (obj) => obj.btnLabel == sortType.value
  // );
  isActiveBtn.value = sortType.value;

  //필터 체크표시
  if (type === "D") {
    isFilterChecked.value = true;
  } else {
    isMonthFilterChecked.value = true;
  }

  searchList(type);
};

//일별, 월별 검색
const searchList = (type = "D") => {
  loading.value = true;

  //일별
  let url = "/ordLedger/rptnSalesDeptSalesSchDayList";

  if (type === "D") {
    dshow.value = true;
    //유효성검사 - 혹시몰라서 추가
    if (util.isEmpty(searchParam.value.dateYmd)) {
      util.errorNotify("날짜를 선택해 주세요.");
      return false;
    }
    //일별
    searchParam.value.saleYmd = moment(searchParam.value.dateYmd).format(
      "YYYYMMDD"
    );
  } else {
    mshow.value = true;
    //유효성검사 - 혹시몰라서 추가
    if (util.isEmpty(searchParam.value.dateYm)) {
      util.errorNotify("날짜를 선택해 주세요.");
      return false;
    }
    //월별
    searchParam.value.saleYmd = moment(searchParam.value.dateYm).format(
      "YYYYMM"
    );

    url = "/ordLedger/rptnSalesDeptSalesSchMonthList";
  }
  // loading.value = true;
  return api
    .get(url, {
      params: searchParam.value,
    })
    .then((response) => {
      loading.value = false;
      rows.value = response
        .filter((x) => x.DEPT_CD === "")
        .concat(response.filter((x) => x.DEPT_CD !== ""));
    })
    .finally(() => {});
};

//Tab 변경할 때마다 그리드 초기화 - 기존 모바일 화면 보니 Tab변경할 때마다 초기화 되길래 추가함
const gridClear = (val) => {
  let type = "D";
  val == "first_tab" ? (type = "D") : (type = "M");

  tabSe.value = val;
  rows.value = [];
  isFilterChecked.value = false;
  isMonthFilterChecked.value = false;
  searchParam.value.sortType = "C";
  isActiveBtn.value = "기본정렬";
  searchList(type);
};

onMounted(() => {
  setTimeout(() => {
    loading.value = false;
    show.value = true;
    nodata_show.value = true;
  }, 3000);
  searchList("D");
});
</script>

<style lang="scss" scoped>
.first_table_header_wrap {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  .table_header_item {
    text-align: center;
    /* padding-bottom: 8px !important; */
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    p {
      @include fs-5;
      @include fw-7;
    }
  }
  .table_header_item:nth-child(1) {
    @include flex_center;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  }
  .table_header_item:nth-child(2),
  .table_header_item:nth-child(3),
  .table_header_item:nth-child(4) {
    p {
      @include flex_center;
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }
  }
  .table_header_item:last-child {
    border: none;
  }
}

.first_table_wrap {
  display: grid;
  grid-template-columns: repeat(4, minmax(calc(100% / 4), 1fr));
  border: none;
  .table_item {
    padding: 4px 0 !important;
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    td {
      @include flex_center;
      @include mfs-6;
    }
  }

  .table_item:nth-child(1) {
    td {
      @include flex_center;
      @include mfs-6;
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }
  }

  .table_item:nth-child(2) {
    td {
      @include flex_end;
      @include mfs-6;
      font-size: 10px !important;
    }
  }
  .table_item:nth-child(3) {
    td {
      @include flex_end;
      @include mfs-6;
      font-size: 10px !important;
    }
  }
  .table_item:nth-child(4) {
    td {
      @include flex_end;
      @include mfs-6;
      font-size: 10px !important;
    }
  }

  .table_item:nth-child(2),
  .table_item:nth-child(3),
  .table_item:nth-child(4) {
    td {
      padding: 12px 4px !important;
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }
  }

  .table_item:last-child {
    border: none;
  }
}

.second_table_header_wrap {
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  .table_header_item {
    text-align: center;
    //padding-bottom: 8px !important;
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    p {
      @include fs-5;
      @include fw-7;
    }
  }
  .table_header_item:nth-child(1) {
    @include flex_center;
    grid-column: span 2;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    white-space: normal !important;
    word-break: break-all;
  }
  .table_header_item:nth-child(2) {
    grid-column: span 3;
  }
  .table_header_item:nth-child(3) {
    grid-column: span 2;
  }
  .table_header_item:nth-child(4) {
    grid-column: span 3;
  }
  .table_header_item:nth-child(2),
  .table_header_item:nth-child(3),
  .table_header_item:nth-child(4) {
    p {
      padding: 4px 0 !important;
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }
  }
  .table_header_item:last-child {
    border: none;
  }
}

.second_table_wrap {
  display: grid;
  grid-template-columns: repeat(10, minmax(calc(100% / 10), 1fr));
  border: none;
  .table_item {
    //padding: 4px 0 !important;
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    td {
      @include flex_center;
      @include mfs-6;
    }
  }
  .table_item:nth-child(1) {
    @include flex_center;
    grid-column: span 2;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  }
  .table_item:nth-child(2) {
    grid-column: span 3;
    td {
      @include flex_end;
      /* margin-right: 8px; */
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 2;
    td {
      @include flex_end;
      /* margin-right: 8px; */
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 3;
    td {
      @include flex_end;
    }
  }

  .table_item:nth-child(2),
  .table_item:nth-child(3),
  .table_item:nth-child(4) {
    td {
      padding: 12px 4px !important;
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }
  }

  .table_item:last-child {
    border: none;
  }
}
</style>

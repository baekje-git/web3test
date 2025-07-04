<template>
  <div>
    <div class="">
      <q-table
        class=""
        row-key="CUST_CD"
        :rows="rows"
        :columns="columns"
        flat
        hide-bottom
        :rows-per-page-options="[0]"
        :virtual-scroll-sticky-size-start="0"
        :loading="loading"
        style="padding-bottom: 30px"
      >
        <template v-slot:top>
          <div class="table_Top_wrap">
            <div class="table_Title_mobile">
              <span>{{ TableTitle }}</span>
            </div>
            <div class="filter_area">
              <div class="flex_start" style="gap: 10px">
                <DatePickerComp_mobile
                  v-model:input_model_props="searchParam.dateYmd"
                  style="width: 100%"
                  @enter="searchList"
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
                      @click="onClickFilterApply"
                    />
                  </div>
                </DialogLayout_mobile>
              </div>
            </div>
          </div>
        </template>

        <template v-slot:header="props">
          <div class="table_header_wrap">
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
          </div>
        </template>

        <template v-slot:body="props">
          <q-tr v-show="show">
            <div class="table_wrap">
              <div class="table_item">
                <q-td>{{ props.row.CUST_NM }}</q-td>
                <!-- <q-td v-if="props.row.sub주소 !== ''">{{
                  props.row.sub주소
                }}</q-td> -->
                <!-- <q-td v-if="props.row.주소 !== ''">{{ props.row.주소 }}</q-td> -->
              </div>
              <div class="table_item">
                <div>
                  <q-td>{{ util.getNumberFormat(props.row.SALE_AMT) }}</q-td>
                  <q-td>{{ util.getNumberFormat(props.row.BACK_AMT) }}</q-td>
                </div>
              </div>
              <div class="table_item">
                <div>
                  <q-td>{{ props.row.YMD_PROFIT1 }}</q-td>
                  <q-td>{{ util.getNumberFormat(props.row.PAY_AMT) }}</q-td>
                </div>
              </div>
            </div>
          </q-tr>
          <!-- <q-inner-loading class="loading" :showing="loading" color="primary" /> -->
        </template>
        <template v-slot:loading>
          <q-inner-loading class="loading" :showing="loading" color="primary" />
        </template>
      </q-table>
    </div>
  </div>
</template>
<script setup>
import { ref, defineProps, onMounted } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";

import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import DatePickerComp_mobile from "src/components/mobile/__commonComp/datepickerComp/DatePickerComp_mobile.vue";
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BtnFilterComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnFilterComp_mobile.vue";
import FilterSingleComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile.vue";
import FilterSingleComp_mobile_a from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile_a.vue";

const props = defineProps({
  TableTitle: {
    type: String,
    default: "TableTitle",
  },
});

//적용 CLASS
const btnResult = ref("on");

//로딩
const loading = ref(false);
const show = ref(false);

//필터 체크
const isFilterChecked = ref(false);

//사용자정보
const userInfo = util.getUserData();

//검색조건
const searchParam = ref({
  curYmd: moment().format("YYYYMMDD"), //일자
  brchCd: userInfo.BRCH_CD, //지점코드
  deptCd: userInfo.USER_GB_CD != "10" ? userInfo.DEPT_CD : "", //부서코드 AS-IS 처럼 동일하게
  dateYmd: moment().format("YYYY-MM-DD"), //검색필터날짜
  sortType: "C", //정렬 C: 거래처순, S: 판매순, R: 반품순, D: 입금순
});

//필터 visible
const dialog_filter = ref(false);
const dialog_full = ref(true);

//일일거래내역서 그리드
const rows = ref([]);

//일일거래내역서 컬럼
const columns = ref([
  { name: "CUST_NM", label: "거래처명", align: "left" },
  { name: "SALE_AMT", label: "매출", align: "right" },
  { name: "BACK_AMT", label: "반품", align: "right" },
  { name: "YMD_PROFIT1", label: "이익율", align: "right" },
  { name: "PAY_AMT", label: "입금", align: "right" },
]);

//정렬타입
const sortType = ref("거래처순");

//일일거래내역서 필터 종류
const filterbtnGroups = [
  {
    title: "정렬 순서",
    subtitle: "",
    gridStyle: 4,
    btnDatePicker: "off",
    buttons: [
      { btnLabel: "거래처순" },
      { btnLabel: "매출순" },
      { btnLabel: "반품순" },
      { btnLabel: "입금순" },
    ],
  },
];

//정렬 선택
const selectedSortType = (val) => {
  sortType.value = val;
};

//현재 적용중인 버튼 표시
const isActiveBtn = ref("거래처순");
// 필터 적용 버튼 클릭
const onClickFilterApply = () => {
  dialog_filter.value = false;

  if (sortType.value === "거래처순") {
    searchParam.value.sortType = "C";
  } else if (sortType.value === "매출순") {
    searchParam.value.sortType = "S";
  } else if (sortType.value === "반품순") {
    searchParam.value.sortType = "R";
  } else if (sortType.value === "입금순") {
    searchParam.value.sortType = "D";
  }

  //필터표시
  // isActiveBtn.value = filterbtnGroups[0].buttons.findIndex(
  //   (obj) => obj.btnLabel == sortType.value
  // );
  isActiveBtn.value = sortType.value;

  isFilterChecked.value = true;
  searchList();
};

//일일거래내역서 검색
const searchList = () => {
  loading.value = true;

  //날짜 포멧팅
  if (util.isEmpty(searchParam.value.dateYmd)) {
    util.errorNotify("날짜를 선택해 주세요.");
    return false;
  }

  //날짜
  searchParam.value.curYmd = moment(searchParam.value.dateYmd).format(
    "YYYYMMDD"
  );

  return api
    .get("/ordLedger/selectRptnSalesDailyCustomSales", {
      params: searchParam.value,
    })
    .then((response) => {
      show.value = true;
      rows.value = response
        .filter((x) => x.DEPT_CD === "")
        .concat(response.filter((x) => x.DEPT_CD !== "" && x.CUST_NM !== ""))
        .concat(response.filter((x) => x.DEPT_CD !== "" && x.CUST_NM === ""));
    })
    .finally(() => {
      loading.value = false;
    });
};

onMounted(async () => {
  setTimeout(() => {
    loading.value = false;
    show.value = true;
  }, 3000);
  await searchList();
});
</script>

<style lang="scss" scoped>
.table_header_wrap {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  .table_header_item {
    text-align: center;
    /* padding-bottom: 8px !important; */
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    p {
      padding: 8px 0;
      @include fs-5;
      @include fw-7;
    }
  }
  .table_header_item:nth-child(1) {
    @include flex_center;
    grid-column: span 2;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  }
  .table_header_item:nth-child(2) {
    grid-column: span 2;
    p:nth-child(1) {
      @include fw-7;
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }
  }
  .table_header_item:nth-child(3) {
    grid-column: span 2;
    p:nth-child(1) {
      @include fw-7;
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }
  }
  .table_header_item:nth-child(2),
  .table_header_item:nth-child(3) {
    p {
      @include flex_center;
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }
  }
  .table_header_item:last-child {
    border: none;
  }
}

.table_wrap {
  display: grid;
  grid-template-columns: repeat(6, minmax(calc(100% / 6), 1fr));
  border: none;
  padding: 0;
  .table_item {
    /* padding: 8px 0 !important; */
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    td {
      @include flex_center;
      @include mfs-6;
      white-space: normal;
      word-break: break-all;
    }
  }
  .table_item:nth-child(1) {
    grid-column: span 2;
    td {
      @include flex_start;
      flex-direction: column;
      white-space: normal;
    }
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  }
  .table_item:nth-child(2),
  .table_item:nth-child(3) {
    grid-column: span 2;
    td {
      @include flex_end;
      padding: 12px 4px !important;
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
      text-align: right;
    }
  }
  .table_item:last-child {
    border: none;
  }
}
</style>

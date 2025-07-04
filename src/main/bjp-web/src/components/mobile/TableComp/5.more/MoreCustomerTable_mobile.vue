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
              <DatePickerMonthComp_mobile
                style="width: 136px !important"
                v-model:input_model_props="searchParam.dateCurYm"
                @enter="searchList"
              />
              <InputComp_mobile
                placeholder="거래처명"
                style="width: 34%"
                v-model:input_model_props="searchParam.custNm"
              />
              <BtnComp_mobile
                buttonText="검색"
                btnStyle="default"
                class="btn_srch"
                @click="searchList"
              />
              <div class="table_Filter" @click="dialog_filter = true">
                <!-- <img src="/public/assets/icons/sliders-solid.svg" alt="icon" /> -->
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
                <!-- <q-td v-if="props.row.주소 !== ''">{{ props.row.주소 }}</q-td> -->
              </div>
              <div class="table_item">
                <q-td>{{ util.getNumberFormat(props.row.BEF_SALE_2) }}</q-td>
                <q-td>{{ util.getNumberFormat(props.row.CUR_SALE_2) }}</q-td>
              </div>
              <div class="table_item">
                <q-td>{{ util.getNumberFormat(props.row.TRN_LIM_AMT) }}</q-td>
                <q-td>{{
                  util.getNumberFormat(props.row.CUR_BALANCE_A_AMT)
                }}</q-td>
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
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import InputComp_mobile from "src/components/mobile/__commonComp/inputComp/InputComp_mobile.vue";
import BtnFilterComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnFilterComp_mobile.vue";
import FilterSingleComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile.vue";
import FilterSingleComp_mobile_a from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile_a.vue";
import DatePickerMonthComp_mobile from "src/components/mobile/__commonComp/datepickerComp/DatePickerMonthComp_mobile.vue";

//적용 CLASS
const btnResult = ref("on");

//로딩
const loading = ref(false);
const show = ref(false);

const props = defineProps({
  TableTitle: {
    type: String,
    default: "TableTitle",
  },
});

//필터 체크
const isFilterChecked = ref(false);

//사용자정보
const userInfo = util.getUserData();

//검색조건
const searchParam = ref({
  befYm: moment().add(-1, "month").format("YYYYMM"), //전월
  curYm: moment().format("YYYYMM"), //당월
  custNm: "", //거래처명
  brchCd: userInfo.BRCH_CD, //지점코드
  deptCd: userInfo.LOGIN_DEPT_CD, //부서코드
  dateCurYm: moment().format("YYYYMM"), //검색필터날짜
  sortType: "C", //정렬 C: 거래처순, BS: 전월매출순, CS: 금월매출순
});

//필터 visible
const dialog_filter = ref(false);
const dialog_full = ref(true);

//거래처별 영업현황 그리드
const rows = ref([]);

//거래처별 영업현황 컬럼
const columns = ref([
  { name: "CUST_NM", label: "거래처명", align: "center" },
  { name: "BEF_SALE_2", label: "전월매출", align: "right" },
  { name: "CUR_SALE_2", label: "금월매출", align: "right" },
  { name: "TRN_LIM_AMT", label: "여신한도액", align: "right" },
  { name: "CUR_BALANCE_A_AMT", label: "금월잔고", align: "right" },
]);

//정렬타입
const sortType = ref("거래처순");

//거래처별 영업현황 필터 종류
const filterbtnGroups = [
  {
    title: "정렬 순서",
    subtitle: "",
    gridStyle: 3,
    btnDatePicker: "off",
    buttons: [
      { btnLabel: "거래처순" },
      { btnLabel: "전월매출순" },
      { btnLabel: "금월매출순" },
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
  } else if (sortType.value === "전월매출순") {
    searchParam.value.sortType = "BS";
  } else if (sortType.value === "금월매출순") {
    searchParam.value.sortType = "CS";
  }

  //필터표시
  // isActiveBtn.value = filterbtnGroups[0].buttons.findIndex(
  //   (obj) => obj.btnLabel == sortType.value
  // );
  isActiveBtn.value = sortType.value;

  isFilterChecked.value = true;
  searchList();
};

//거래처별 영업현황 검색
const searchList = () => {
  loading.value = true;

  //유효성검사 - 혹시몰라서 추가
  if (util.isEmpty(searchParam.value.dateCurYm)) {
    util.errorNotify("날짜를 선택해 주세요.");
    return false;
  }

  //if (util.isEmpty(searchParam.value.custNm)) {
  //util.errorNotify("거래처명을 입력해 주세요.");
  //return false;
  //}

  //날짜 포멧팅
  searchParam.value.befYm = moment(searchParam.value.dateCurYm)
    .add(-1, "month")
    .format("YYYYMM"); //전월
  searchParam.value.curYm = moment(searchParam.value.dateCurYm).format(
    "YYYYMM"
  ); //당월

  return api
    .get("/ordLedger/rptnSalesCustomSalesNorList", {
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
    }
  }
  .table_header_item:nth-child(1) {
    @include flex_center;
    grid-column: span 2;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    p {
      @include fw-7;
    }
  }
  .table_header_item:nth-child(2) {
    grid-column: span 2;
    p {
      @include fw-7;
    }
    p:nth-child(1) {
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }
  }
  .table_header_item:nth-child(3) {
    grid-column: span 2;
    p {
      @include fw-7;
    }
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
  padding: 0 !important;
  border: none;
  .table_item {
    /* padding: 8px 0 !important; */
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    td {
      @include flex_start;
      @include mfs-6;
      white-space: normal;
      word-break: break-all;
    }
  }
  .table_item:nth-child(1) {
    grid-column: span 2;
    td {
      flex-direction: column;
      @include flex_start;
      white-space: normal;
      /* word-break: break-all; */
    }
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  }
  .table_item:nth-child(2) {
    grid-column: span 2;
  }
  .table_item:nth-child(3) {
    grid-column: span 2;
  }
  .table_item:nth-child(2),
  .table_item:nth-child(3) {
    td {
      @include flex_end;
      @include mfs-6;
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

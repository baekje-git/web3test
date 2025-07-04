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
              <div class="flex_between" style="width: calc(100% - 92px)">
                <DatePickerComp_mobile
                  style="width: 100%"
                  v-model:input_model_props="searchParam.dateYmd"
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
                </DialogLayout_mobile>
              </div>
            </div>
          </div>
        </template>

        <template v-slot:header="props">
          <div class="table_header_wrap">
            <!-- 첫 번째 줄 -->
            <div class="table_header_row">
              <div class="table_header_item" style="grid-row: span 2">
                <p>{{ props.cols[0].label }}</p>
                <!-- 지점 -->
              </div>
              <div class="table_header_item" style="grid-column: span 2">
                <p>{{ props.cols[1].label }}</p>
                <!-- 목표 -->
              </div>
              <div class="table_header_item" style="grid-column: span 2">
                <p>{{ props.cols[2].label }}</p>
                <!-- 매출액 -->
              </div>
            </div>

            <!-- 두 번째 줄 -->
            <div class="table_header_row">
              <div class="table_header_item">
                <p>{{ props.cols[3].label }}</p>
                <!-- 달성율 -->
              </div>
              <div class="table_header_item">
                <p>{{ props.cols[4].label }}</p>
                <!-- 금일매출 -->
              </div>
            </div>
          </div>
        </template>

        <template v-slot:body="props">
          <q-tr
            v-show="show"
            :class="{ 'highlight-row': props.row.BRCH_SNM === '<합계>' }"
          >
            <div class="table_wrap">
              <div class="table_item">
                <q-td>{{ props.row.BRCH_SNM }}</q-td>
                <!-- <q-td v-if="props.row.sub주소 !== ''">{{
                  props.row.sub주소
                }}</q-td> -->
                <!-- <q-td v-if="props.row.주소 !== ''">{{ props.row.주소 }}</q-td> -->
              </div>
              <div class="table_item">
                <div>
                  <q-td>{{
                    util.getNumberFormat(props.row.SALE_AIM_AMT)
                  }}</q-td>
                  <q-td>{{ util.getNumberFormat(props.row.TARGET_RATE) }}</q-td>
                </div>
              </div>
              <div class="table_item">
                <div>
                  <q-td>{{ util.getNumberFormat(props.row.M_NET_AMT1) }}</q-td>
                  <q-td>{{
                    util.getNumberFormat(props.row.DAY_NET_AMT1)
                  }}</q-td>
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
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BtnFilterComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnFilterComp_mobile.vue";
import FilterSingleComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile.vue";
import FilterSingleComp_mobile_a from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile_a.vue";
import DatePickerComp_mobile from "src/components/mobile/__commonComp/datepickerComp/DatePickerComp_mobile.vue";

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
  CUR_YMD1: moment().format("YYYYMMDD"), //당월
  brchCd: userInfo.BRCH_CD, //지점코드
  deptCd: userInfo.LOGIN_DEPT_CD, //부서코드
  CUR_YMD: moment().format("YYYY-MM-DD"), //검색필터날짜
  sortType: "", //정렬 "": 전체, N: 사용안함, A: 말일입금, B: 15일입금
});

//필터 visible
const dialog_filter = ref(false);
const dialog_full = ref(true);

//수금 계획 그리드
const rows = ref([]);

//수금 계획 컬럼
const columns = ref([
  { name: "BRCH_SNM", label: "지점", align: "left" },
  { name: "SALE_AIM_AMT", label: "목표", align: "right" },
  { name: "M_NET_AMT1", label: "매출액", align: "right" },
  { name: "TARGET_RATE", label: "달성율", align: "right" },
  { name: "DAY_NET_AMT1", label: "금일매출", align: "right" },
]);

//정렬타입
const sortType = ref("전체");

//정렬 선택
const selectedSortType = (val) => {
  sortType.value = val;
};

//현재 적용중인 버튼 표시
const isActiveBtn = ref("전체");

//수금 계획 검색
const searchList = () => {
  loading.value = true;

  //날짜
  searchParam.value.CUR_YMD = moment(searchParam.value.dateYmd).format(
    "YYYY-MM-DD"
  );
  searchParam.value.CUR_YMD1 = moment(searchParam.value.dateYmd).format(
    "YYYYMMDD"
  );

  return api
    .get("/ordLedger/selectSalesBranchMobile", {
      params: searchParam.value,
    })
    .then((response) => {
      show.value = true;
      rows.value = response;
    })
    .finally(() => {
      // loading.value = false;
      loading.value = false;
    });
};
onMounted(async () => {
  await searchList();
});
</script>

<style lang="scss" scoped>
.highlight-row {
  color: #0000ff; // 텍스트 컬러도 조정 가능
}
.table_header_wrap {
  display: grid;
  grid-template-rows: repeat(2, auto);
  grid-template-columns: repeat(6, 1fr);
  text-align: center;
  .table_header_row {
    display: contents;
  }
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
  padding: 0;
  border: none;
  .table_item {
    /* padding: 8px 0 !important; */
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    td {
      @include mfs-6;
      word-break: break-all;
      white-space: normal;
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

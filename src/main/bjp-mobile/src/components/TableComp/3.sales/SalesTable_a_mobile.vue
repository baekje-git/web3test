<template>
  <div class="">
    <q-table
      class=""
      row-key="APRV_DT"
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
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="table_Top_wrap">
          <div class="table_Title" style="padding: 16px 0 12px">
            <span>{{ TableTitle }}</span>
          </div>
          <div class="flex_between" style="padding: 0 0 14px 0">
            <div class="table_Filter" @click="dialog_filter = true">
              <!-- <img src="/public/assets/icons/sliders-solid.svg" alt="icon"> -->
              <BtnFilterComp_mobile :filterCheck="false" />
              <!-- sales filter -->
              <DialogLayout_mobile
                dialog_title="검색 필터"
                v-model:dialog_model_prop="dialog_filter"
                :dialog_full="dialog_full"
              >
                <div class="">
                  <FilterComp_mobile
                    :btnGroups="filterbtnGroups"
                    btnResult="on"
                  />
                </div>
              </DialogLayout_mobile>
            </div>
            <div class="flex_between">
              <DatePickerComp_mobile
                style="width: 105px"
                v-model:input_model_props="searchParam.startDt"
              />
              <span style="font-size: 12px; color: #999999; margin: 0 4px 0 1px"
                >-</span
              >
              <DatePickerComp_mobile
                style="width: 105px"
                v-model:input_model_props="searchParam.endDt"
              />
            </div>
            <BtnComp_mobile
              buttonText="검색"
              btnStyle="default"
              style="width: 64px"
              @click="onClickSearch"
            />
          </div>
          <div class="" style="padding: 0 0 16px 0">
            <BtnDateGroupComp_mobile btnStyle="active"
              />
          </div>
          <div class="table_totalVal">
            <span class="table_total_Text">
              현금잔고 {{ util.getNumberFormat(cashBlnc ?? 0) }}원 | 판매금액 {{ util.getNumberFormat(salAmt ?? 0) }}원 | 입금액 {{ util.getNumberFormat(dpstAmt ?? 0) }}원
            </span>
          </div>
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr class="table_container" @click="props.expand = !props.expand">
          <div class="table_wrap">
            <div class="table_item">
              <q-td>{{ props.row.APRV_DT }}</q-td>
            </div>
            <div class="table_item">
              <q-td>({{ util.getDay(props.row.APRV_DT) }})</q-td>
            </div>
            <div class="table_item">
              <img src="/public/assets/icons/sort-down-solid.svg" alt="icon" />
            </div>
            <div class="table_item">
              <q-td>일판매합계</q-td>
            </div>
            <div class="table_item">
              <q-td class="table_item_plus" v-if="props.row.TOTAL_AMT > 0"
                >+{{ util.getNumberFormat(props.row.TOTAL_AMT) }}</q-td
              >
              <q-td class="table_item_minus" v-else>{{ util.getNumberFormat(props.row.TOTAL_AMT) }}</q-td>
            </div>
          </div>
        </q-tr>

        <q-tr v-show="props.expand" :props="props">
          <div class="expand_table_wrap" v-for="(items, i) in props.row.DATA" :key="i">
            <div class="expand_table_item">
              <div class="table_badge_wrap">
                <q-badge class="table_badge_red">{{ items.MED_NM }}</q-badge>
                <q-badge class="table_badge_blue">{{ items.PAY_ITEM_NM }}</q-badge>
              </div>
              <q-td>{{ items.ITEM_NM_UNIT }}</q-td>
            </div>
            <div class="expand_table_item">
              <q-td>{{ items.ITEM_CNT_TXT }}</q-td>
            </div>
            <div class="expand_table_item">
              <q-td>승인{{ items.APRV_NO }}</q-td>
            </div>
            <div class="expand_table_item">
              <q-td>{{ items.GUBUN_NM }}</q-td>
            </div>
            <div class="expand_table_item">
              <q-td class="expand_table_item_plus" v-if="items.TOTAL_AMT > 0">+{{ util.getNumberFormat(items.TOTAL_AMT) }}</q-td>
              <q-td class="expand_table_item_minus" v-else>{{ util.getNumberFormat(items.TOTAL_AMT) }}</q-td>
            </div>
            <div class="expand_table_item">
              <q-td>
                <BtnComp_mobile
                  buttonText="상세"
                  btnStyle="default"
                  @click="onViewDtl(props.row, i)"
                  style="width: 32px;"
                />
                <DialogLayout_mobile
                  dialog_title="매출원장 > 매출원장상세"
                  v-model:dialog_model_prop="props.row.DATA[i].dtl_model"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <SalesDetailComp_a_mobile
                      :APRV_DT="items.APRV_DT"
                      :ORD_TYPE_NM="items.ORD_TYPE_NM"
                      :GUBUN_NM="items.GUBUN_NM"
                      :APRV_NO="items.APRV_NO"
                      :TOTAL_AMT="util.getNumberFormat(items.TOTAL_AMT)"
                      :BALANCE_A_AMT="util.getNumberFormat(items.BALANCE_A_AMT)"
                      :SN="items.SN"
                    />
                  </div>
                </DialogLayout_mobile>


                <BtnComp_mobile
                  v-if="isShowCrtfBtn && items.INVOICE_NO && items.GUBUN !== '2'"
                  buttonText="인수증"
                  btnStyle="default"
                  @click="onViewCrtf(props.row, i)"
                  style="width: 64px;"
                />
                <DialogLayout_mobile
                  dialog_title="인수증"
                  v-model:dialog_model_prop="props.row.DATA[i].crtf_model"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <SalesReceiptComp_a_mobile
                      :DLV_BRCH_CD="items.DLV_BRCH_CD"
                      :A_APRV_DT="items.A_APRV_DT"
                      :APRV_NO="items.APRV_NO"
                    />
                  </div>
                </DialogLayout_mobile>


                <BtnComp_mobile
                  buttonText="메모"
                  btnStyle="default"
                  @click="onViewMemo(props.row, i)"
                  style="width: 32px;"
                />
                <DialogLayout_mobile
                  dialog_title="매출원장 > 메모"
                  v-model:dialog_model_prop="props.row.DATA[i].memo_model"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <TextAreaComp_mobile
                      submit="메모등록"
                      :is-visible="false" :memo="items.MEMO" :sn="items.SN"
                    />
                  </div>
                </DialogLayout_mobile>
              </q-td>
            </div>
            <div class="expand_table_item">
              <q-td>잔고</q-td>
            </div>
            <div class="expand_table_item">
              <q-td>{{ util.getNumberFormat(items.BALANCE_A_AMT) }}</q-td>
            </div>
          </div>
        </q-tr>

      </template>
    </q-table>
  </div>
</template>

<script setup>
import { computed, ref, inject } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";

import DialogLayout_mobile from "src/layouts/DialogLayouts/DialogLayout_mobile.vue";
import DatePickerComp_mobile from "src/components/__commonComp/datepickerComp/DatePickerComp_mobile.vue";
import BtnComp_mobile from "src/components/__commonComp/btnComp/BtnComp_mobile.vue";
import BtnGroupComp_mobile from "src/components/__commonComp/btnComp/BtnGroupComp_mobile.vue";
import BtnDateGroupComp_mobile from "src/components/__commonComp/btnComp/BtnDateGroupComp_mobile.vue";

import SalesDetailComp_a_mobile from "src/components/DialogComp/3.salesDialogComp/SalesDetailComp_a_mobile.vue";
import SalesReceiptComp_a_mobile from "src/components/DialogComp/3.salesDialogComp/SalesReceiptComp_a_mobile.vue";
import FilterComp_mobile from "src/components/DialogComp/FilterDialogComp/FilterComp_mobile.vue";
import TextAreaComp_mobile from "src/components/DialogComp/__commonDialogComp/TextAreaComp_mobile.vue";
import BtnFilterComp_mobile from "src/components/__commonComp/btnComp/BtnFilterComp_mobile.vue";

const dialog_full = ref(true);
const dialog_filter = ref(false);
const loading = ref(false);

const userInfo = util.getUserData();
const bus = inject("bus");
const rows = ref([]);

const companyCds = ["49"]; // TODO: 추가해야함
const isShowCrtfBtn = computed(
  () => userInfo.ACCPT_YN === "Y" && !companyCds.includes(userInfo.COMPANY_CD)
);

const cashBlnc = ref("0");
const salAmt = ref("0");
const dpstAmt = ref("0");

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  custNm: userInfo.CUST_NM,
  orderVal: "0",
  startDt: `${moment().format("YYYY.MM")}.01`,
  endDt: moment().format("YYYY.MM.DD"),
  searchAll: "Y",
  salesYn: "Y",
  returnYn: "Y",
  paymentYn: "Y",
  compensationYn: "Y",
});

const props = defineProps({
  TableTitle: {
    type: String,
    default: "TableTitle",
  },
});

//---------------------------- Function ------------------------------//

// 검색버튼 클릭
async function onClickSearch() {
  await fetchItemList();
}

async function fetchItemList() {
  loading.value = true;

  searchParam.value.startDt = moment(searchParam.value.startDt).format("YYYYMMDD");
  searchParam.value.endDt = moment(searchParam.value.endDt).format("YYYYMMDD");

  const { masterData, masterCustData, list } = await api.get(
    "/ordLedger/listSearch",
    { params: searchParam.value }
  );
  searchParam.value.startDt = moment(searchParam.value.startDt).format("YYYY.MM.DD");
  searchParam.value.endDt = moment(searchParam.value.endDt).format("YYYY.MM.DD");

  cashBlnc.value = masterCustData.BALANCE_A_AMT;
  salAmt.value = masterData.TOTAL_AMT;
  dpstAmt.value = masterData.PAY_AMT;

  let nowKey = "";
  let childList = [];
  let parentList  = [];
  let idx = 0;
  let dayTotalAmt = 0;
  for(let i of list){
    childList.push(i);
    dayTotalAmt = dayTotalAmt + i.TOTAL_AMT;
    if(nowKey === "" || idx === (list.length-1) ||nowKey !== list[idx+1].APRV_DT){
        nowKey = i.APRV_DT;
        parentList.push({
          APRV_DT : i.APRV_DT , DATA : childList, TOTAL_AMT : dayTotalAmt
        });

        dayTotalAmt = 0;

        childList = [];
        if(idx !== (list.length-1)){
          nowKey = list[idx+1].APRV_DT;
        }
    }
    idx++;
  };
  rows.value = parentList;
  loading.value = false;
}

bus.off("onClickPeriod");
bus.on("onClickPeriod", async (params) => {
  searchParam.value.startDt = params.value.startDt
  searchParam.value.endDt = params.value.endDt
  await fetchItemList();
});

async function onViewDtl(row, key) {
  for (var i = 0; i < rows.value.length; i++) {
      for (var k = 0; k < rows.value[i].DATA.length; k++) {
        rows.value[i].DATA[k].dtl_model = false;
      }
    }
    row.DATA[key].dtl_model = true;

    //await bus.emit("pmntItemDetail", props.row);
}

async function onViewCrtf(row, key) {
  for (var i = 0; i < rows.value.length; i++) {
      for (var k = 0; k < rows.value[i].DATA.length; k++) {
        rows.value[i].DATA[k].crtf_model = false;
      }
    }
    row.DATA[key].crtf_model = true;

    //await bus.emit("pmntItemDetail", props.row);
}

async function onViewMemo(row, key) {
  for (var i = 0; i < rows.value.length; i++) {
      for (var k = 0; k < rows.value[i].DATA.length; k++) {
        rows.value[i].DATA[k].memo_model = false;
      }
    }
    row.DATA[key].memo_model = true;

    //await bus.emit("pmntItemDetail", props.row);
}

const filterbtnGroups = [
  {
    title: "조회 필터",
    subtitle: "",
    gridStyle: 5,
    btnDatePicker: "off",
    buttons: [
      { title: "전체" },
      { title: "매출" },
      { title: "반품" },
      { title: "입금" },
      { title: "약가보상" },
    ],
  },
  {
    title: "정렬순서",
    subtitle: "",
    gridStyle: 2,
    btnDatePicker: "off",
    buttons: [{ title: "최신순" }, { title: "과거순" }],
  },
  {
    title: "알자상세",
    subtitle: "",
    gridStyle: 2,
    btnDatePicker: "off",
    buttons: [{ title: "모두 펼치기" }, { title: "모두 접기" }],
  },
];
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  align-items: center;
  justify-content: space-around;
  grid-template-columns: 25% 7% 3% 20% 35%;
  padding: 6px 0 !important;

  .table_item:nth-child(1) {
    td {
      @include fw-7;
    }
  }
  .table_item:nth-child(2) {
    td {
      @include fw-7;
    }
  }
  .table_item:nth-child(3) {
    margin-left: 2px;
  }
  .table_item:nth-child(4) {
  }
  .table_item:nth-child(5) {
    @include flex_end;
    td {
    }
  }
}

.expand_table_wrap {
  @include border-b;
  display: grid;
  grid-template-columns: repeat(11, 1fr);
  padding: 6px 0 !important;
  &:hover {
    background-color: $b5;
  }
  .expand_table_item:nth-child(1) {
    @include flex_start;
    @include fw-7;
    max-width: 290px;
    grid-column: span 10;
    gap: 1px;
    td {
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .expand_table_item:nth-child(2) {
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
    }
  }
  .expand_table_item:nth-child(3) {
    grid-column: span 5;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .expand_table_item:nth-child(4) {
    grid-column: span 2;
    td {
      @include flex_end;
      @include fs-5;
    }
  }
  .expand_table_item:nth-child(5) {
    grid-column: span 4;
    td {
      @include flex_end;
      @include fs-5;
      @include fw-7;
    }
  }
  .expand_table_item:nth-child(6) {
    grid-column: span 5;
    td {
      @include flex_start;
      gap: 4px;
    }
  }
  .expand_table_item:nth-child(7) {
    grid-column: span 2;
    td {
      @include flex_end;
      @include fs-5;
    }
  }
  .expand_table_item:nth-child(8) {
    grid-column: span 4;
    td {
      @include flex_end;
      @include fs-5;
      @include fw-7;
    }
  }
}
</style>

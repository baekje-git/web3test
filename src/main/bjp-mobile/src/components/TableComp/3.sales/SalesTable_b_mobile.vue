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
    >
      <template v-slot:top>
        <div class="table_Top_wrap">
          <div class="table_Title" style="padding: 16px 0 12px;">
            <span>{{ TableTitle }}</span>
          </div>
          <div class="flex_between" style="padding: 0 0 14px 0;">
            <div class="table_Filter" @click="dialog_filter = true">
              <!-- <img src="/public/assets/icons/sliders-solid.svg" alt="icon"> -->
              <BtnFilterComp_mobile
                :filterCheck="true"
              />
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
            <div class="flex_between" style="gap: 8px;">
              <InputComp_mobile
                v-model:input_model_props="searchParam.keyword"
                placeholder="보험코드/품목 입력"
                style="width: 148px;"
              />
              <InputComp_mobile
                v-model:input_model_props="searchParam.makerNm"
                placeholder="제약회사 입력"
                style="width: 100px;"
              />
            </div>
            <BtnComp_mobile
              buttonText="검색"
              btnStyle="default"
              style="width: 64px;"
              @click="onClickSearch"
            />
          </div>
          <div class="" style="padding: 0 0 16px 0;">
            <BtnDateGroupComp_mobile
              btnStyle="active"
            />
          </div>
          <div class="table_totalVal">
            <span class="table_total_Text">
              수불건수 {{ util.getNumberFormat(pmntCnt ?? 0) }}건 | 수불금액 {{ util.getNumberFormat(pmntAmt ?? 0) }}원
            </span>
          </div>
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item">
              <div class="table_badge_wrap">
                <template v-if="props.row.MED_NM">
                  <q-badge class="table_badge_red">{{ props.row.MED_NM }}</q-badge>
                </template>
                <template v-if="props.row.PAY_ITEM_NM">
                  <q-badge class="table_badge_blue">{{ props.row.PAY_ITEM_NM }}</q-badge>
                </template>
              </div>
              <q-td>{{ props.row.ITEM_NM_UNIT }}</q-td>
            </div>
            <div class="table_item">
              <q-td>
                <BtnComp_mobile
                  buttonText="상세"
                  btnStyle="default"
                  style="width: 30px;"
                  @click="props.dialog_filter_model = onViewDtl(props.row)"
                />
                <DialogLayout_mobile
                  dialog_title="품목수불 > 품목수불상세"
                  v-model:dialog_model_prop="props.row.dtl_model"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <SalesDetailComp_b_mobile
                      :BOHUM_CD="props.row.BOHUM_CD"
                      :ITEM_PYOJUN_CD="props.row.ITEM_PYOJUN_CD"
                      :SN="props.row.SN"
                      :SNO="props.row.SNO"
                    >
                    <div class="flex_start" style="font-weight: bold; gap: 4px; padding: 8px 0;">
                      <div class="table_badge_wrap">
                        <template v-if="props.row.MED_NM">
                          <q-badge class="table_badge_red">{{ props.row.MED_NM }}</q-badge>
                        </template>
                        <template v-if="props.row.PAY_ITEM_NM">
                          <q-badge class="table_badge_blue">{{ props.row.PAY_ITEM_NM }}</q-badge>
                        </template>
                      </div>
                      <q-td>{{ props.row.ITEM_NM_UNIT }}</q-td>
                    </div>
                    </SalesDetailComp_b_mobile>
                  </div>
                </DialogLayout_mobile>
              </q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.APRV_DT_FM }}</q-td>
            </div>
            <div class="table_item">
              <q-td >{{ props.row.APRV_NO }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.ITEM_QTY }}개</q-td>
            </div>
            <div class="table_item">
              <q-td>단가</q-td>
            </div>
            <div class="table_item">
              <q-td><span>{{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}</span><span>원</span></q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.상태 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>금액</q-td>
            </div>
            <div class="table_item">
              <q-td><span :class="{ 'text-red': props.row.SALE_AMT < 0 }">{{ util.getNumberFormat(props.row.SALE_AMT) }}</span><span>원</span></q-td>
            </div>
          </div>
        </q-tr>



      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue';
import { api } from "boot/axios";
import * as util from "src/support/util";

import { mdiCheck, mdiHeart, mdiHeartOutline } from '@mdi/js';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import BottomComp_mobile from 'src/components/DialogComp/BottomDialogComp/BottomComp_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import InputComp_mobile from 'src/components/__commonComp/inputComp/InputComp_mobile.vue';
import ReturnRecordComp_a_mobile from 'src/components/DialogComp/2.returnDialogComp/ReturnRecordComp_a_mobile.vue';
import FilterComp_mobile from 'src/components/DialogComp/FilterDialogComp/FilterComp_mobile.vue';
import BtnGroupComp_mobile from 'src/components/__commonComp/btnComp/BtnGroupComp_mobile.vue';
import SalesDetailComp_b_mobile from 'src/components/DialogComp/3.salesDialogComp/SalesDetailComp_b_mobile.vue';
import BtnFilterComp_mobile from 'src/components/__commonComp/btnComp/BtnFilterComp_mobile.vue';
import BtnDateGroupComp_mobile from 'src/components/__commonComp/btnComp/BtnDateGroupComp_mobile.vue';


const dialog_full = ref(true)
const dialog_filter = ref(false)
const dialog_상세 = ref(false)

const userInfo = util.getUserData();
const loading = ref(false);

const bus = inject("bus");
const rows = ref([]);
const rowval = () => {
  return rows.value.length;
};

const pmntCnt = ref(0);
const pmntAmt = ref(0);

const props = defineProps({
  TableTitle: {
    type: String,
    default: 'TableTitle'
  }
})

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  keyword : "",
  makerNm: "",
  startDt: "20240101",
  endDt: "20240430",
  searchAll: "Y",
  professionalYn: "N",
  generalYn: "N",
  paymentYn: "N",
  nonPaymentYn: "N",
  nonVatYn: "N",
  discountYn: "N",
});

const filterbtnGroups = [
  {
    title: '조회필터',
    subtitle: '',
    gridStyle: 4,
    btnDatePicker: 'off',
    buttons: [
      { title: '전체' },
      { title: '전문급여' },
      { title: '전문비급여' },
      { title: '일반급여' },
      { title: '일반비급여' },
      { title: '비과세' },
      { title: '수금할인' },
    ]
  },
  {
    title: '조회필터',
    subtitle: '',
    gridStyle: 4,
    btnDatePicker: 'on',
    buttons: [
      { title: '당월' },
      { title: '전월' },
      { title: '3개월' },
      { title: '6개월' },
      { title: '1년' },
      { title: '2년' },
      { title: '3년' },
      { title: '직접입력' },
    ]
  },
  {
    title: '정렬순서',
    subtitle: '',
    gridStyle: 2,
    btnDatePicker: 'off',
    buttons: [
      { title: '최신순' },
      { title: '과거순' },
    ]
  },
];



//---------------------------- Function ------------------------------//
// 검색버튼 클릭
async function onClickSearch() {
  await fetchItemList();
}

async function fetchItemList() {
  loading.value = true;
  const { count, list } = await api.get("/pmntItem/listSearch", {
    params: searchParam.value,
  });
  pmntCnt.value = count.CNT;
  pmntAmt.value = count.TOTAL_AMT;

  rows.value = list;
  loading.value = false;
}
// 조회기간 버튼 클릭
//기간 클릭
bus.off("onClickPeriod");
bus.on("onClickPeriod", async (params) => {
  searchParam.value.startDt = params.value.startDt
  searchParam.value.endDt = params.value.endDt

  await fetchItemList();
});

async function onViewDtl(row) {
  for (var i = 0; i < rows.value.length; i++) {
      rows.value[i].dtl_model = false;
    }
    row.dtl_model = true;

    //await bus.emit("pmntItemDetail", props.row);
}


</script>

<style lang="scss" scoped>

.table_wrap {
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  padding: 8px 0 !important;
  gap: 4px;
  .table_item:nth-child(1){
    @include flex_start;
    @include fw-7;
    grid-column: span 9;
    gap: 2px;
    td {
      font-size: 15.5px;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .table_item:nth-child(2){
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
  .table_item:nth-child(3){
    grid-column: span 2;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(4){
    grid-column: span 2;
    td {
      @include flex_center;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(5){
    grid-column: span 2;
    td {
      @include flex_end;
      @include fs-5;
      color: #666666;
    }
  }
  .table_item:nth-child(6){
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
    }
  }
  .table_item:nth-child(7){
    grid-column: span 3;
    td {
      @include flex_end;
      @include fs-5;
      gap: 4px;
      span {
        @include fs-5;
      }
    }
  }
  .table_item:nth-child(8){
    grid-column: span 4;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(9){
    grid-column: span 2;
    td {
      @include flex_end;
      @include fs-5;
      color: #666666;
    }
  }
  .table_item:nth-child(10){
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
    }
  }
  .table_item:nth-child(11){
    grid-column: span 3;
    td {
      @include flex_end;
      gap: 4px;
      span {
        @include fs-5;
      }
      .text-red {
        color: $r1;
      }
    }
  }
}





</style>

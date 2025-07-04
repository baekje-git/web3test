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
                v-model:input_model_props="searchParam.itemNm"
                placeholder="보험코드/품명 입력"
                style="width: 110px;"
              />
              <InputComp_mobile
                v-model:input_model_props="searchParam.makerNm"
                placeholder="제약회사 입력"
                style="width: 110px;"
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
            <BtnDateGroupComp_mobile btnStyle="active" />
          </div>
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item">
              <div class="table_badge_wrap">
                <q-badge class="table_badge_red">{{ props.row.MED_NM }}</q-badge>
                <q-badge class="table_badge_blue">{{ props.row.PAY_ITEM_NM }}</q-badge>
              </div>
              <q-td>{{ props.row.ITEM_NM_UNIT }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.BACK_EXAM_NM }}</q-td>
            </div>
            <div class="table_item">
              <q-td>등록일</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.BACK_DT }}</q-td>
            </div>
            <div class="table_item">
              <q-td>등록</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ util.getNumberFormat(props.row.ITEM_QTY_REQ) }}</q-td>
            </div>
            <div class="table_item">
              <q-td>단가</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}</q-td>
            </div>
            <div class="table_item">
              <q-td>검수일</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.EXAM_DT }}</q-td>
            </div>
            <div class="table_item">
              <q-td>검수</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ util.getNumberFormat(props.row.ITEM_QTY) }}</q-td>
            </div>
            <div class="table_item">
              <q-td>금액</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ util.getNumberFormat(props.row.PRICE) }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item">
              <q-td>불가</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ util.getNumberFormat(props.row.ITEM_QTY_NO) }}</q-td>
            </div>
            <div class="table_item">
              <q-td v-if="props.row.EXAM_RSLT_NM === '정상'">{{ props.row.EXAM_RSLT_NM }}</q-td>
              <q-td v-else class="text-red">{{ props.row.EXAM_RSLT_NM }}</q-td>
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

import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import BtnDateGroupComp_mobile from'src/components/__commonComp/btnComp/BtnDateGroupComp_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import InputComp_mobile from 'src/components/__commonComp/inputComp/InputComp_mobile.vue';
import FilterComp_mobile from 'src/components/DialogComp/FilterDialogComp/FilterComp_mobile.vue';
import BtnFilterComp_mobile from 'src/components/__commonComp/btnComp/BtnFilterComp_mobile.vue';

const dialog_full = ref(true)
const dialog_filter = ref(false)


const bus = inject("bus");
const userInfo = util.getUserData();
const rows = ref([]);

const props = defineProps({
  TableTitle: {
    type: String,
    default: 'TableTitle'
  }
})

const filterbtnGroups = [
  {
    title: '기간설정',
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
];

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  itemNm: "",
  makerNm: "",
  orderVal: "0",
  subdivYn: "",
  startDt: "",
  endDt: "",
});

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  const res = await api.get("/bakReqDtl/listSearch", {
    params: searchParam.value,
  });

  rows.value = res;
}

// 검색버튼 클릭
async function onClickSearch() {
  await fetchItemList();
}

//기간 클릭
bus.off("onClickPeriod");
bus.on("onClickPeriod", async (params) => {
  searchParam.value.startDt = params.value.startDt
  searchParam.value.endDt = params.value.endDt
  await fetchItemList();
});

// onMounted(async () => {
//   await setDate(3);
//   period.value = 3;
//   await fetchItemList();
// });

</script>

<style lang="scss" scoped>

.table_wrap {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  padding: 6px 0 !important;
  gap: 0px 4px;
  .table_item:nth-child(1){
    @include flex_start;
    @include fw-7;
    grid-column: span 10;
    gap: 2px;
    td {
      font-size: 16px;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .table_item:nth-child(2){
    grid-column: span 2;
    td {
      @include flex_end;
      @include fs-5;
      @include fw-7;
    }
  }
  .table_item:nth-child(3){
    grid-column: span 1;
    td {
      @include flex_start;
      @include fs-6;
    }
  }
  .table_item:nth-child(4){
    grid-column: span 4;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(5){
    grid-column: span 1;
    td {
      @include flex_start;
      @include fs-6;
    }
  }
  .table_item:nth-child(6){
    grid-column: span 2;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(7){
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-6;
    }
  }
  .table_item:nth-child(8){
    grid-column: span 3;
    td {
      @include flex_end;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(9){
    grid-column: span 1;
    td {
      @include flex_start;
      @include fs-6;
    }
  }
  .table_item:nth-child(10){
    grid-column: span 4;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(11){
    grid-column: span 1;
    td {
      @include flex_start;
      @include fs-6;
    }
  }
  .table_item:nth-child(12){
    grid-column: span 2;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(13){
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-6;
    }
  }
  .table_item:nth-child(14){
    grid-column: span 3;
    td {
      @include flex_end;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(15){
    grid-column: span 5;
    td {
      @include flex_start;
      @include fs-6;
    }
  }
  .table_item:nth-child(16){
    grid-column: span 1;
    td {
      @include flex_start;
      @include fs-6;
    }
  }
  .table_item:nth-child(17){
    grid-column: span 2;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(18){
    grid-column: span 4;
    td {
      @include flex_start;
      @include fs-6;
      margin-left: 2px;
    }
    .text-red {
      color: $r1;
    }
  }
}





</style>

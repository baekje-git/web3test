<template>
  <div class="DialogComp_wrap">
    <q-dialog v-model="isOpen" persistent>
      <q-card>
        <q-table
          class="orderTable_3a"
          :rows="rows"
          :columns="columns"
          row-key="name"
          table-header-class="table_header"
          dense
          hide-bottom
          virtual-scroll
          :rows-per-page-options="[0]"
          :virtual-scroll-sticky-size-start="0"
          style="padding: 10px; width: 1000px; height: 600px"
        >
          <template v-slot:top>
            <div class="q-table_wrap">
              <div
                class="q-table_header_wrap flex_between"
                style="margin-bottom: 8px"
              >
                <div class="q-table_header_title flex_center">
                  <p>제품 출고 이력</p>
                  <div class="q-table_header_accumulate">
                    <p style="font-weight: 400">
                      {{
                        `누적 출하 수량 : 매출 ${accShpmntQtyData.SALE_CNT} / 반품 ${accShpmntQtyData.BACK_CNT}`
                      }}
                    </p>
                  </div>
                </div>
                <div class="q-table_header_title_sub">
                  <div class="q-table_header_btnOption">
                    <!-- <BtnComp_options /> -->
                    <div class="Btn_options_wrap">
                      <!-- <q-btn
                      v-model="searchHistoryParam.sellMonth"
                      :class="thisMnth"
                      label="당월"
                      dense
                      @click="onClicksellMonth('0')"
                    />
                    <q-btn
                      v-model="searchHistoryParam.sellMonth"
                      :class="bfrMnth"
                      label="전월"
                      dense
                      @click="onClicksellMonth('-1')"
                    />
                    <q-btn
                      v-model="searchHistoryParam.sellMonth"
                      :class="bfr3Mnth"
                      label="3개월"
                      dense
                      @click="onClicksellMonth('-3')"
                    />
                    <q-btn
                      v-model="searchHistoryParam.sellMonth"
                      :class="bfr6nth"
                      label="6개월"
                      dense
                      @click="onClicksellMonth('-6')"
                    />
                    <q-btn
                      v-model="searchHistoryParam.sellMonth"
                      :class="bfr1Year"
                      label="1년"
                      dense
                      @click="onClicksellMonth('-12')"
                    />
                    <q-btn
                      v-model="searchHistoryParam.sellMonth"
                      :class="bfr2Year"
                      label="2년"
                      dense
                      @click="onClicksellMonth('-24')"
                    /> -->
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </template>

          <template v-slot:top-row v-if="rows.length === 0 && searched">
            <q-tr class="no-date">
              <q-td colspan="100%">
                <span>검색된 결과가 없습니다.</span>
              </q-td>
            </q-tr>
          </template>

          <template v-slot:body-cell-ITEM_NM="props">
            <q-td :props="props" style="font-weight: bold">
              {{ props.value }}
            </q-td>
          </template>

          <template v-slot:body-cell-WP2_AMT="props">
            <q-td :props="props" style="text-align: right">
              {{ props.value }}
            </q-td>
          </template>
        </q-table>
        <q-card-actions class="flex_end">
          <BtnComp_active btn-label="닫기" @click="onClickClose" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject, computed, defineEmits } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import BtnComp_active from "../common/BtnComp_active.vue";

const emit = defineEmits(["close"]);
const userInfo = util.getUserData();
const bus = inject("bus");
const rows = ref([]);
const historyYn = ref("");
const isHideBottom = computed(() => rows.value.length > 0);
const loading = ref(false);
const searched = ref(false);

const props = defineProps({
  isOpen: { type: Boolean },
});

const isOpen = computed(() => {
  return props.isOpen;
});

const searchHistoryParam = ref({
  custCd: "",
  selItemCd: "",
  sellMonth: "-36",
});

// 누적 출하 수량
const accShpmntQtyData = ref({
  SALE_CNT: 0, // 매출
  BACK_CNT: 0, // 반품
});

//---------------------------- Function ------------------------------//

// 누적 출하 수량 조회
async function fetchAccShpmntQty() {
  const res = await api.get("/ord/accShpmntQty", {
    params: searchHistoryParam.value,
  });

  accShpmntQtyData.value.SALE_CNT = res.SALE_CNT;
  accShpmntQtyData.value.BACK_CNT = res.BACK_CNT;
  loading.value = false;
}

// 이력 목록 조회
async function fetchHistoryList() {
  loading.value = true;
  const res = await api.get("/ord/history", {
    params: searchHistoryParam.value,
  });

  searched.value = true;
  rows.value = res;

  await fetchAccShpmntQty();
}

// 닫기
async function onClickClose() {
  emit("close");

  searched.value = false;
  rows.value = [];
  accShpmntQtyData.value.BACK_CNT = 0;
  accShpmntQtyData.value.SALE_CNT = 0;
}

bus.on("basketHistory", async (data) => {
  searchHistoryParam.value.custCd = userInfo.CUST_CD;
  searchHistoryParam.value.selItemCd = data.ITEM_CD;

  await fetchHistoryList();
});

//-------------------------- Grid Setting ----------------------------//

const columns = [
  {
    name: "ITEM_NM",
    label: "품목 및 규격",
    field: "ITEM_NM",
    align: "left",
    sortable: true,
  },
  { name: "ORD_DT", label: "주문일자", field: "ORD_DT", align: "center" },
  {
    name: "WP2_AMT",
    label: "단가",
    field: "WP2_AMT",
    align: "center",
    format: (val) => util.getNumberFormat(val),
  },
  { name: "ITEM_QTY", label: "주문수량", field: "ITEM_QTY", align: "center" },
  { name: "SERIAL_NO", label: "제조번호", field: "SERIAL_NO", align: "center" },
  { name: "EXP_DT", label: "유효기간", field: "EXP_DT", align: "center" },
];
</script>

<style lang="scss" scoped>
.orderTable_3a {
  height: 190px;
  overflow-y: auto;
}
.flex_start {
  @include flex_start;
  gap: 8px;
}
.flex_between {
  @include flex_between;
}

// .Btn_options_wrap {
//   @include flex_center;
//   gap: 8px;
// }
// .btn_options {
//   color: $w;
//   background-color: #999999;
//   border-radius: 5px;
//   padding: 0 16px;
// }
// .btn_options span {
//   @include fs-5;
// }
// .btn_options:active,
// .btn_options:hover,
// .btn_options:focus {
//   @include gradation;
//   border-radius: 5px;
//   color: $w;
//   padding: 0 16px;
// }
</style>

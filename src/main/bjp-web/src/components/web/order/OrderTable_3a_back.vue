<template>
  <div class="tbl_wrap lt">
    <q-table
      class="orderTable_3a"
      :rows="rows"
      :columns="columns"
      row-key="SEQ"
      table-header-class="table_header"
      dense
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :selected="selected"
      @row-click="onRowClick"
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
                  <q-btn
                    v-model="searchHistoryParam.sellMonth"
                    :class="thisMnth"
                    label="당월"
                    dense
                    size="xs"
                    @click="onClicksellMonth('0')"
                  />
                  <q-btn
                    v-model="searchHistoryParam.sellMonth"
                    :class="bfrMnth"
                    label="전월"
                    dense
                    size="xs"
                    @click="onClicksellMonth('-1')"
                  />
                  <q-btn
                    v-model="searchHistoryParam.sellMonth"
                    :class="bfr3Mnth"
                    label="3개월"
                    dense
                    size="xs"
                    @click="onClicksellMonth('-3')"
                  />
                  <q-btn
                    v-model="searchHistoryParam.sellMonth"
                    :class="bfr6nth"
                    label="6개월"
                    dense
                    size="xs"
                    @click="onClicksellMonth('-6')"
                  />
                  <q-btn
                    v-model="searchHistoryParam.sellMonth"
                    :class="bfr1Year"
                    label="1년"
                    dense
                    size="xs"
                    @click="onClicksellMonth('-12')"
                  />
                  <q-btn
                    v-model="searchHistoryParam.sellMonth"
                    :class="bfr2Year"
                    label="2년"
                    dense
                    size="xs"
                    @click="onClicksellMonth('-24')"
                  />
                  <q-btn
                    v-model="searchHistoryParam.sellMonth"
                    :class="bfr3Year"
                    label="3년"
                    dense
                    size="xs"
                    @click="onClicksellMonth('-36')"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </template>

      <template v-slot:top-row v-if="rows.length === 0 && searched">
        <q-tr class="no-date">
          <q-td colspan="100%">
            <span>조회기간 내의 이력이 없습니다.</span>
          </q-td>
        </q-tr>
      </template>

      <template v-slot:body-cell-ITEM_NM="props">
        <q-td :props="props" style="font-weight: bold; cursor: pointer">
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-ORD_DT="props">
        <q-td :props="props" style="cursor: pointer">
          {{ moment(props.value).format("YYYY-MM-DD") }}
        </q-td>
      </template>

      <template v-slot:body-cell-WP2_AMT="props">
        <q-td :props="props" style="text-align: right">
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_QTY="props">
        <q-td :props="props" style="cursor: pointer">
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-SERIAL_NO="props">
        <q-td :props="props" style="cursor: pointer">
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-EXP_DT="props">
        <q-td :props="props" style="cursor: pointer">
          {{
            props.value != "" ? moment(props.value).format("YYYY-MM-DD") : ""
          }}
        </q-td>
      </template>
    </q-table>
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject, computed } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";

import BtnComp_options from "src/components/web/common/BtnComp_options.vue";

const userInfo = util.getUserData();
const bus = inject("bus");
const rows = ref([]);
const historyYn = ref("");
const isHideBottom = computed(() => rows.value.length > 0);
const loading = ref(false);
const searched = ref(false);
const selected = ref([]);

const searchHistoryParam = ref({
  custCd: "",
  selItemCd: "",
  sellMonth: "-6",
});

// 누적 출하 수량
const accShpmntQtyData = ref({
  SALE_CNT: 0, // 매출
  BACK_CNT: 0, // 반품
});

const thisMnth = computed(() =>
  searchHistoryParam.value.sellMonth === "0" ? "btn" : "btn_options"
);

const bfrMnth = computed(() =>
  searchHistoryParam.value.sellMonth === "-1" ? "btn" : "btn_options"
);

const bfr3Mnth = computed(() =>
  searchHistoryParam.value.sellMonth === "-3" ? "btn" : "btn_options"
);

const bfr6nth = computed(() =>
  searchHistoryParam.value.sellMonth === "-6" ? "btn" : "btn_options"
);

const bfr1Year = computed(() =>
  searchHistoryParam.value.sellMonth === "-12" ? "btn" : "btn_options"
);

const bfr2Year = computed(() =>
  searchHistoryParam.value.sellMonth === "-24" ? "btn" : "btn_options"
);

const bfr3Year = computed(() =>
  searchHistoryParam.value.sellMonth === "-36" ? "btn" : "btn_options"
);

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

// 이력 기간버튼 클릭
async function onClicksellMonth(val) {
  searchHistoryParam.value.sellMonth = val;

  if (historyYn.value === "Y") {
    await fetchHistoryList();
  }
}

// 그리드 row 클릭
function onRowClick(evt, row) {
  const index = selected.value.indexOf(row);

  if (index === -1) {
    selected.value = [];
    selected.value.push(row);
  } else {
    selected.value.splice(index, 1);
  }
}

// 주문 그리드에서 row 클릭 시 이력 조회
bus.on("historyList", async (data) => {
  historyYn.value = data.HISTORY_YN;

  searchHistoryParam.value.custCd = userInfo.CUST_CD;
  searchHistoryParam.value.selItemCd = data.ITEM_CD;

  await fetchHistoryList();
});

// 주문 그리드에서 검색 시 초기화
bus.on("init", () => {
  rows.value = [];
  searched.value = false;

  searchHistoryParam.value.custCd = "";
  searchHistoryParam.value.selItemCd = "";
  accShpmntQtyData.value.SALE_CNT = 0;
  accShpmntQtyData.value.BACK_CNT = 0;
});

// 장바구니 그리드에서 row 클릭 시 이력 조회
bus.on("basketHistoryList", async (data) => {
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
  {
    name: "ITEM_QTY",
    label: "주문수량",
    field: "ITEM_QTY",
    align: "center",
    format: (val) => util.getNumberFormat(val),
  },
  { name: "SERIAL_NO", label: "제조번호", field: "SERIAL_NO", align: "center" },
  { name: "EXP_DT", label: "유효기간", field: "EXP_DT", align: "center" },
];
</script>

<style lang="scss" scoped>
.orderTable_3a {
  height: 170px;
  overflow-y: auto;
}
.flex_start {
  @include flex_start;
  gap: 8px;
}
.flex_between {
  @include flex_between;
}

.Btn_options_wrap {
  @include flex_center;
  gap: 8px;
}
.btn_options {
  color: $w;
  background-color: #999999;
  border-radius: 5px;
  padding: 0 16px;
  height: 24px;
}
.btn_options span {
  @include fs-5;
}
.btn_options:active,
.btn_options:hover,
.btn_options:focus {
  @include gradation;
  border-radius: 5px;
  color: $w;
  padding: 0 16px;
}
</style>

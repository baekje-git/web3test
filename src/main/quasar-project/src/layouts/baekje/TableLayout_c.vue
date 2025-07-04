<template>
  <TableComp_left style="height: 270px">
    <div class="table_header">
      <div class="header_text">
        <p>제품 출고 이력</p>
        <p>누적 출하 수량 : 매출 32 / 반품 3</p>
      </div>
      <div class="table_header_btn">
        <!-- <BtnComp_active btnLabel="당월" />
        <BtnComp_active btnLabel="전월" />
        <BtnComp_active btnLabel="3개월" />
        <BtnComp_active btnLabel="6개월" />
        <BtnComp_active btnLabel="1년" />
        <BtnComp_active btnLabel="2년" />
        <BtnComp_active btnLabel="3년" /> -->

        <q-btn
          v-model="searchHistoryParam.sellMonth"
          :class="thisMnth"
          label="당월"
          @click="onClicksellMonth('0')"
        />
        <q-btn
          v-model="searchHistoryParam.sellMonth"
          :class="bfrMnth"
          label="전월"
          @click="onClicksellMonth('-1')"
        />
        <q-btn
          v-model="searchHistoryParam.sellMonth"
          :class="bfr3Mnth"
          label="3개월"
          @click="onClicksellMonth('-3')"
        />
        <q-btn
          v-model="searchHistoryParam.sellMonth"
          :class="bfr6nth"
          label="6개월"
          @click="onClicksellMonth('-6')"
        />
        <q-btn
          v-model="searchHistoryParam.sellMonth"
          :class="bfr1Year"
          label="1년"
          @click="onClicksellMonth('-12')"
        />
        <q-btn
          v-model="searchHistoryParam.sellMonth"
          :class="bfr2Year"
          label="2년"
          @click="onClicksellMonth('-24')"
        />
        <q-btn
          v-model="searchHistoryParam.sellMonth"
          :class="bfr3Year"
          label="3년"
          @click="onClicksellMonth('-36')"
        />
      </div>
    </div>

    <div class="table_c">
      <!-- <TableComp_c /> -->
      <div class="table_sec">
        <q-table
          class="table_wrap"
          :rows="rows"
          :columns="columns"
          row-key="name"
          hide-bottom
          table-header-class="table-header-class"
        >
          <template v-slot:body-cell-name="props">
            <q-td :props="props" style="padding: 0">
              <div
                class="table_font-size-14"
                style="font-weight: bold; padding: 0 10px"
              >
                {{ props.value }}
              </div>
            </q-td>
          </template>
        </q-table>
      </div>
    </div>
  </TableComp_left>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject, computed } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";
import { mdiHeart, mdiHeartOutline, mdiCheck } from "@mdi/js";
import BtnComp_active from "src/components/baekje/BtnComp_active.vue";
import TableComp_left from "src/components/baekje/TableComp_left.vue";

const userInfo = util.getUserData();
const bus = inject("bus");
const rows = ref([]);
const historyYn = ref("");

const searchHistoryParam = ref({
  custCd: "",
  selItemCd: "",
  sellMonth: "-6",
});

const thisMnth = computed(() =>
  searchHistoryParam.value.sellMonth === "0" ? "btn" : "btn_active"
);

const bfrMnth = computed(() =>
  searchHistoryParam.value.sellMonth === "-1" ? "btn" : "btn_active"
);

const bfr3Mnth = computed(() =>
  searchHistoryParam.value.sellMonth === "-3" ? "btn" : "btn_active"
);

const bfr6nth = computed(() =>
  searchHistoryParam.value.sellMonth === "-6" ? "btn" : "btn_active"
);

const bfr1Year = computed(() =>
  searchHistoryParam.value.sellMonth === "-12" ? "btn" : "btn_active"
);

const bfr2Year = computed(() =>
  searchHistoryParam.value.sellMonth === "-24" ? "btn" : "btn_active"
);

const bfr3Year = computed(() =>
  searchHistoryParam.value.sellMonth === "-36" ? "btn" : "btn_active"
);

//---------------------------- Function ------------------------------//

// 이력 목록 조회
async function fetchHistoryList() {
  const res = await api.get("/ord/history", {
    params: searchHistoryParam.value,
  });

  rows.value = res;
  console.log(rows.value);
}

// 이력 기간버튼 클릭
async function onClicksellMonth(val) {
  searchHistoryParam.value.sellMonth = val;

  if (historyYn.value === "Y") {
    await fetchHistoryList();
  }
}

bus.on("historyList", async (data) => {
  if (data.HISTORY_YN === "Y") {
    historyYn.value = data.HISTORY_YN;
    searchHistoryParam.value.custCd = userInfo.CUST_CD;
    searchHistoryParam.value.selItemCd = data.ITEM_CD;

    await fetchHistoryList();
  } else {
    rows.value = [];
  }
});

//-------------------------- Grid Setting ----------------------------//

const columns = [
  {
    name: "name",
    label: "품목 및 규격",
    sortable: true,
    align: "left",
    required: true,
    field: (row) => row.ITEM_NM,
    format: (val) => `${val}`,
  },
  {
    name: "주문일자",
    label: "주문일자",
    field: "ORD_DT",
    align: "center",
    format: (val) => moment(val).format("YYYY.MM.DD"),
  },
  {
    name: "단가",
    label: "단가",
    field: "WP2_AMT",
    align: "center",
    format: (val) => util.getNumberFormat(val),
  },
  { name: "주문수량", label: "주문수량", field: "ITEM_QTY", align: "center" },
  { name: "제조번호", label: "제조번호", field: "SERIAL_NO", align: "center" },
  {
    name: "유효기간",
    label: "유효기간",
    field: "EXP_DT",
    align: "center",
    format: (val) => (val !== "" ? moment(val).format("YYYY.MM.DD") : ""),
  },
];
</script>

<style lang="scss">
.table_header {
  @include flex_between;
}
.header_text {
  @include flex_center;
  gap: 8px;
}
.header_text p:nth-child(1) {
  @include fw-7;
}
.header_text p:nth-child(2) {
  @include fs-6;
  @include fw-4;
}
.table_c {
  padding-top: 10px;
}

.table_header_btn {
  @include flex_around;
  gap: 8px;
}

.table_c_wrap {
  border: none;
}

/* 버튼 */
.btn_active {
  background-color: #999999;
  border-radius: 5px !important;
  padding: 0 16px;
}
.btn_active span {
  @include fs-5;
  color: #ffffff !important;
}
.btn_active:active,
.btn_active:hover,
.btn_active:focus {
  @include gradation;
  border-radius: 5px;
  color: $w;
  padding: 0 16px;
}

.btn {
  @include gradation;
  border-radius: 5px !important;
  color: $w;
  padding: 0 16px;
}
.btn span {
  @include fs-5;
  color: #ffffff !important;
}
</style>

<template>
  <div class="">
    <q-table
      class="paymentTable_4a"
      :rows="rows"
      :columns="columns"
      row-key="ROWNUMBER"
      table-header-class="table_header"
      dense
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      v-model:pagination="pagination"
      :loading="loading"
      :selected="selected"
    >
      <div class="row justify-center q-mt-md">
        <q-pagination
          v-model="pagination.page"
          color="grey-8"
          :max="pagesNumber"
          size="sm"
        />
      </div>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="">
            <div class="q-table_header_wrap">
              <div class="q-table_header_title">
                <p>입금현황</p>
              </div>
            </div>
            <div class="flex_between">
              <div class="q-table_day_wrap">
                <span>{{
                  `${moment(proxyDate1, "YYYY-MM-DD HH:mm:ss").format(
                    "YYYY-MM-DD"
                  )} ~ ${moment(proxyDate2, "YYYY-MM-DD HH:mm:ss").format(
                    "YYYY-MM-DD"
                  )}`
                }}</span>
              </div>
              <div class="q-table_btn_option_wrap">
                <!-- <BtnComp_options_month_sub /> -->
                <div class="Btn_options_wrap">
                  <q-btn
                    :class="periodClassA"
                    label="당일"
                    dense
                    @click="onClickPeriod('today')"
                  />
                  <q-btn
                    :class="periodClassB"
                    label="당월"
                    dense
                    @click="onClickPeriod(0)"
                  />
                  <q-btn
                    :class="periodClassC"
                    label="전월"
                    dense
                    @click="onClickPeriod(1)"
                  />
                  <q-btn
                    :class="periodClassD"
                    label="3개월"
                    dense
                    @click="onClickPeriod(3)"
                  />
                  <q-btn
                    :class="periodClassE"
                    label="6개월"
                    dense
                    @click="onClickPeriod(6)"
                  />
                  <q-btn
                    :class="periodClassF"
                    label="1년"
                    dense
                    @click="onClickPeriod(12)"
                  />
                </div>
              </div>
              <!-- <div class="q-table_btn_wrap">
                <BtnComp_default btnLabel="조회" @click="onClickSearch" />
              </div> -->
            </div>
          </div>
          <div class="q-table_header_sub_wrap">
            <p>
              <span>입금건수</span>
              <span>{{ dpstData.dpstCnt }}건</span>
            </p>
            <p>
              <span>입금금액</span>
              <span
                >{{
                  dpstData.payAmtSum === "" ? 0 : dpstData.payAmtSum
                }}원</span
              >
            </p>
            <p>
              <span>수금할인</span>
              <span
                >{{
                  dpstData.paybackAmtSum === "" ? 0 : dpstData.paybackAmtSum
                }}원</span
              >
            </p>
          </div>
        </div>
      </template>

      <template v-slot:top-row v-if="rows.length === 0 && searched">
        <q-tr class="no-date">
          <q-td colspan="100%">
            <span>조회된 결과가 없습니다.</span>
          </q-td>
        </q-tr>
      </template>

      <template v-slot:body-cell-ROWNUMBER="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          class="td-card1"
          @click="onClickRow(props.row)"
        >
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-APRV_DT="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          class="td-card2"
          @click="onClickRow(props.row)"
        >
          {{ moment(props.value, "YYYY-MM-DD HH:mm:ss").format("YYYY-MM-DD") }}
        </q-td>
      </template>

      <template v-slot:body-cell-APRV_TM="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          class="td-card3"
          @click="onClickRow(props.row)"
        >
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-PAY_AMT="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          class="td-card4"
          @click="onClickRow(props.row)"
        >
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-PAYBACK_AMT="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          class="td-card5"
          @click="onClickRow(props.row)"
        >
          {{ props.value }}
        </q-td>
      </template>

      <!-- <template v-slot:bottom>
        <div class="q-table_bottom_pagination_wrap">
          <div class="">
            <Pagination_default />
          </div>
        </div>
      </template> -->
    </q-table>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, computed, onMounted } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_options_month_sub from "src/components/web/common/BtnComp_options_month_sub.vue";
import BtnComp_options from "src/components/web/common/BtnComp_options.vue";
import Pagination_default from "src/components/web/common/Pagination_default.vue";

const userInfo = util.getUserData();
const selected = ref([]);
const loading = ref(false);
const searched = ref(false);

const pagination = ref({
  sortBy: "desc",
  descending: false,
  page: 1,
  rowsPerPage: 10,
  // rowsNumber: xx if getting data from a server
});

const pagesNumber = computed(() =>
  Math.ceil(top_rows.value.length / pagination.value.rowsPerPage)
);

const searchParam = ref({
  startDt: "",
  endDt: "",
  custCd: userInfo.CUST_CD,
  brchCd: userInfo.BRCH_CD,
});

const dpstData = ref({
  dpstCnt: 0,
  payAmtSum: 0,
  paybackAmtSum: 0,
});

const rows = ref([]);
const period = ref("");

const periodClassA = computed(() =>
  period.value === "today" ? "btn_options btn_active" : "btn_options"
);
const periodClassB = computed(() =>
  period.value === 0 ? "btn_options btn_active" : "btn_options"
);
const periodClassC = computed(() =>
  period.value === 1 ? "btn_options btn_active" : "btn_options"
);
const periodClassD = computed(() =>
  period.value === 3 ? "btn_options btn_active" : "btn_options"
);
const periodClassE = computed(() =>
  period.value === 6 ? "btn_options btn_active" : "btn_options"
);
const periodClassF = computed(() =>
  period.value === 12 ? "btn_options btn_active" : "btn_options"
);
//---------------------------- Function ------------------------------//

async function fetchItemList() {
  const { list, masterData } = await api.get("/payment/dpstList", {
    params: searchParam.value,
  });

  dpstData.value.dpstCnt = masterData.DPST_CNT;
  dpstData.value.payAmtSum = util.getNumberFormat(masterData.PAY_AMT_SUM);
  dpstData.value.paybackAmtSum = util.getNumberFormat(
    masterData.PAYBACK_AMT_SUM
  );

  rows.value = list;
  loading.value = false;
  searched.value = true;
}

// 조회기간 버튼 클릭
async function onClickPeriod(val) {
  period.value = val;

  await setDate(val);
  await fetchItemList();
}

// 조회버튼 클릭
// async function onClickSearch() {
//   await fetchItemList();
// }

// 그리드 row 클릭
async function onClickRow(row) {
  const index = selected.value.indexOf(row);

  if (index === -1) {
    selected.value = [];
    selected.value.push(row);
  } else {
    selected.value.splice(index, 1);
  }
}

/*********** datepicker ***********/
const date1 = ref("");
const date2 = ref("");
const proxyDate1 = ref("");
const proxyDate2 = ref("");
const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

function getYYYYMMDD(date) {
  return (
    date.getFullYear() +
    "." +
    (date.getMonth() + 1).toString().padStart(2, "0") +
    "." +
    date.getDate().toString().padStart(2, "0")
  );
}

async function setDate(val) {
  if (val !== "today") {
    if (!val) {
      val = 1;
    } else {
      val++;
    }

    const now = new Date();
    const year = now.getFullYear();
    const month = now.getMonth() + 1;
    const strtDt = new Date(year, month - val, 1);
    const endDt = new Date(year, month, 0);

    date1.value = getYYYYMMDD(strtDt);
    date2.value = getYYYYMMDD(endDt);
    proxyDate1.value = getYYYYMMDD(strtDt);
    proxyDate2.value = getYYYYMMDD(endDt);

    searchParam.value.startDt = date1.value.replace(match, "");
    searchParam.value.endDt = date2.value.replace(match, "");
  } else {
    const today = moment().format("YYYY.MM.DD");
    date1.value = today;
    date2.value = today;
    proxyDate1.value = today;
    proxyDate2.value = today;

    searchParam.value.startDt = date1.value.replace(match, "");
    searchParam.value.endDt = date2.value.replace(match, "");
  }
}
/**********************************/

onMounted(() => {
  setDate(0);
  period.value = 0;
  fetchItemList();
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "ROWNUMBER",
    label: "순번",
    field: "ROWNUMBER",
    align: "center",
  },
  { name: "APRV_DT", label: "승인일자", field: "APRV_DT", align: "center" },
  { name: "APRV_TM", label: "승인시간", field: "APRV_TM", align: "center" },
  {
    name: "PAY_AMT",
    label: "입금금액",
    field: "PAY_AMT",
    align: "center",
    format: (val) => util.getNumberFormat(val),
  },
  {
    name: "PAYBACK_AMT",
    label: "수금할인",
    field: "PAYBACK_AMT",
    align: "center",
    format: (val) => util.getNumberFormat(val),
  },
]);
</script>

<style lang="scss" scoped>
.paymentTable_4a {
  // height: 350px;
  height: 360px;
  overflow-y: auto;
}
.q-table--dense .q-table__bottom {
  padding: 5px 0 0 0 !important;
}
.flex_between {
  @include flex_between;
  flex-wrap: wrap;
  gap: 5px 0;
}

.q-table_day_wrap span {
  @include fs-5;
  color: #959595;
}

.q-table_header_sub_wrap {
  @include flex_start;
  gap: 40px;
  margin: 8px 0;
  padding: 8px 15px;
  background-color: #f8f8f8;
  // border: 1px solid #000;
  // border-radius: 5px;
}
.q-table_header_sub_wrap p span {
  @include fs-5;
}
.q-table_header_sub_wrap p span:nth-child(2) {
  @include fw-7;
  margin-left: 15px;
}

.btn_active {
  @include gradation;
  border-radius: 5px;
  color: $w;
  padding: 0 5px;
}
.td-card1,
.td-card3 {
  width: 70px;
}
.td-card2 {
  width: 100px;
}
.td-card4,
.td-card5 {
  width: 26%;
  min-width: 100px;
}
</style>

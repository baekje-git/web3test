<template>
  <div class="">
    <q-table
      class="paymentTable_3a"
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
                <p>카드결제현황</p>
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
                <!-- <BtnComp_options_month /> -->
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
              <span>결제건수</span>
              <span>{{ pmntData.pmntCnt }}건</span>
            </p>
            <p>
              <span>결제금액</span>
              <span
                >{{ pmntData.pmntAmt === "" ? 0 : pmntData.pmntAmt }}원</span
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
          @click="onClickRow(props.row)"
          class="td-card1"
        >
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-APPROVEDATE="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          class="td-card2"
          @click="onClickRow(props.row)"
        >
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-APRV_INS="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          class="td-card3"
          @click="onClickRow(props.row)"
        >
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-TRANAMT="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          class="td-card4"
          @click="onClickRow(props.row)"
        >
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-CARDNAMEM="props">
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
import BtnComp_options from "src/components/web/common/BtnComp_options.vue";
import BtnComp_options_month from "src/components/web/common/BtnComp_options_month.vue";
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

const pmntData = ref({
  pmntCnt: 0,
  pmntAmt: 0,
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

const monthOption = [
  { label: "", value: "99" },
  { label: "일시불", value: "00" },
  { label: "2", value: "02" },
  { label: "3", value: "03" },
  { label: "4", value: "04" },
  { label: "5", value: "05" },
  { label: "6", value: "06" },
  { label: "7", value: "07" },
  { label: "8", value: "08" },
  { label: "9", value: "09" },
  { label: "10", value: "10" },
  { label: "11", value: "11" },
  { label: "12", value: "12" },
];

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  const { list, masterData } = await api.get("/payment/listSearch", {
    params: searchParam.value,
  });

  pmntData.value.pmntCnt = masterData.PMNT_CNT;
  pmntData.value.pmntAmt = util.getNumberFormat(masterData.PMNT_AMT);
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
  {
    name: "APPROVEDATE",
    label: "승인일자",
    field: "APPROVEDATE",
    align: "center",
  },
  // { name: "구분", label: "구분", field: "구분", align: "center" },
  { name: "APRV_INS", label: "할부", field: "APRV_INS", align: "center" },
  {
    name: "TRANAMT",
    label: "결제금액",
    field: "TRANAMT",
    align: "center",
    format: (val) => util.getNumberFormat(val),
  },
  {
    name: "CARDNAMEM",
    label: "카드회사명",
    field: "CARDNAMEM",
    align: "center",
  },
]);
</script>

<style lang="scss" scoped>
.paymentTable_3a {
  // height: 350px;
  height: 310px;
  overflow-y: auto;
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
  // border: 1px solid #000;
  background-color: #f8f8f8;
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
  padding: 0 16px;
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

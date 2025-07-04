<template>
  <div class="table_Top_wrap">
    <div class="table_totalVal" style="min-width: 360px">
      <span class="table_total_Text">총</span>
      <span class="table_total_Val">{{ rows.length }}</span>
      <span class="table_total_Text">건이 조회되었습니다.</span>
    </div>
    <div class="table_totalVal">
      <span class="table_total_Text" style="min-width: 360px">
        공급가
        {{
          util.getNumberFormat(
            rows.reduce((total, row) => total + row.PAY_SUM_SUPPLY_AMT, 0)
          )
        }}원 | 부가세
        {{
          util.getNumberFormat(
            rows.reduce((total, row) => total + row.PAY_SUM_VAT_AMT, 0)
          )
        }}원 | 총계
        {{
          util.getNumberFormat(
            rows.reduce((total, row) => total + row.PAY_SUM_TOTAL_AMT, 0)
          )
        }}원
      </span>
    </div>
  </div>

  <q-markup-table
    v-for="(row, i) in rows"
    :key="i"
    class="table_wrap"
    flat
    dense
    separator="vertical"
  >
    <thead>
      <tr class="table_header_top">
        <th colspan="5">
          <span>{{ moment(row.PUB_DT).format("YYYY-MM") }} 발행</span>
          <span>|</span>
          <span>{{ row.TAX_TYPE_NM }}</span>
        </th>
      </tr>
      <tr>
        <th></th>
        <th>공급가</th>
        <th>부가세</th>
        <th>총합계</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>합계</td>
        <td>
          {{
            row.PAY_SUM_SUPPLY_AMT == 0
              ? "-"
              : util.getNumberFormat(row.PAY_SUM_SUPPLY_AMT)
          }}
        </td>
        <td>
          {{
            row.PAY_SUM_VAT_AMT == 0
              ? "-"
              : util.getNumberFormat(row.PAY_SUM_VAT_AMT)
          }}
        </td>
        <td>
          {{
            row.PAY_SUM_TOTAL_AMT == 0
              ? "-"
              : util.getNumberFormat(row.PAY_SUM_TOTAL_AMT)
          }}
        </td>
      </tr>
      <tr>
        <td>일반비급여</td>
        <td>
          {{
            row.NOPAY_SUPPLY_AMT == 0
              ? "-"
              : util.getNumberFormat(row.NOPAY_SUPPLY_AMT)
          }}
        </td>
        <td>
          {{
            row.NOPAY_VAT_AMT == 0
              ? "-"
              : util.getNumberFormat(row.NOPAY_VAT_AMT)
          }}
        </td>
        <td>
          {{
            row.NOPAY_TOTAL_AMT == 0
              ? "-"
              : util.getNumberFormat(row.NOPAY_TOTAL_AMT)
          }}
        </td>
      </tr>
      <tr>
        <td>전문비급여</td>
        <td>
          {{
            row.ETC_NOPAY_SUPPLY_AMT == 0
              ? "-"
              : util.getNumberFormat(row.ETC_NOPAY_SUPPLY_AMT)
          }}
        </td>
        <td>
          {{
            row.ETC_NOPAY_VAT_AMT == 0
              ? "-"
              : util.getNumberFormat(row.ETC_NOPAY_VAT_AMT)
          }}
        </td>
        <td>
          {{
            row.ETC_NOPAY_TOTAL_AMT == 0
              ? "-"
              : util.getNumberFormat(row.ETC_NOPAY_TOTAL_AMT)
          }}
        </td>
      </tr>
      <tr>
        <td>일반급여</td>
        <td>
          {{
            row.PAY_SUPPLY_AMT == 0
              ? "-"
              : util.getNumberFormat(row.PAY_SUPPLY_AMT)
          }}
        </td>
        <td>
          {{
            row.PAY_VAT_AMT == 0 ? "-" : util.getNumberFormat(row.PAY_VAT_AMT)
          }}
        </td>
        <td>
          {{
            row.PAY_TOTAL_AMT == 0
              ? "-"
              : util.getNumberFormat(row.PAY_TOTAL_AMT)
          }}
        </td>
      </tr>
      <tr>
        <td>전문급여</td>
        <td>
          {{
            row.ETC_PAY_SUPPLY_AMT == 0
              ? "-"
              : util.getNumberFormat(row.ETC_PAY_SUPPLY_AMT)
          }}
        </td>
        <td>
          {{
            row.ETC_PAY_VAT_AMT == 0
              ? "-"
              : util.getNumberFormat(row.ETC_PAY_VAT_AMT)
          }}
        </td>
        <td>
          {{
            row.ETC_PAY_TOTAL_AMT == 0
              ? "-"
              : util.getNumberFormat(row.ETC_PAY_TOTAL_AMT)
          }}
        </td>
      </tr>
    </tbody>
  </q-markup-table>
  <div class="m-loading_wrap" v-if="loading === true">
    <q-inner-loading class="loading" :showing="loading" color="primary" />
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, computed, onMounted, watch } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";

const userInfo = util.getUserData();

const props = defineProps({
  year: { type: String, default: util.getToday("YYYY") },
});

const rows = ref([]);

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  taxYm: util.getToday("YYYY"),
});

//로딩
const loading = ref(true);
const show = ref(false);
const nodata_show = ref(false);

//---------------------------- Function ------------------------------//

async function fetchData() {
  loading.value = true;
  if (props.year !== "") {
    searchParam.value.taxYm = props.year;
  }

  const res = await api.get("/myPage/taxBill", {
    params: searchParam.value,
  });

  //ios 날짜형식
  let newList = res.map((i) => {
    return { ...i, PUB_DT: i.PUB_DT.replace(/\./g, "/") + "/01" };
  });
  rows.value = newList;

  loading.value = false;
}

defineExpose({ fetchData });

watch(
  () => props.year,
  async (year) => {
    searchParam.value.taxYm = year;
    await fetchData();
  }
);

onMounted(async () => {
  await fetchData();
});

//-------------------------- Grid Setting ----------------------------//

//  const allRows = ref([...rows.value]);

// const totals = computed(() => {
//   let total공급가 = 0;
//   let total부가세 = 0;
//   let total총합계 = 0;

//   allRows.value.forEach((row) => {
//     total공급가 += parseFloat(row.공급가.replace(/,/g, ""));
//     total부가세 += parseFloat(row.부가세.replace(/,/g, ""));
//     total총합계 += parseFloat(row.총합계.replace(/,/g, ""));
//   });

//   const total공급가Str =
//     total공급가 >= 0
//       ? total공급가.toLocaleString()
//       : "-" + Math.abs(total공급가).toLocaleString();
//   const total부가세Str =
//     total부가세 >= 0
//       ? total부가세.toLocaleString()
//       : "-" + Math.abs(total부가세).toLocaleString();
//   const total총합계Str =
//     total총합계 >= 0
//       ? total총합계.toLocaleString()
//       : "-" + Math.abs(total총합계).toLocaleString();

//   return {
//     total공급가: total공급가Str,
//     total부가세: total부가세Str,
//     total총합계: total총합계Str,
//   };
// });
</script>

<style lang="scss" scoped>
.table_wrap {
  padding-bottom: 12px;
  // background-color: transparent;
  .table_header_top {
    text-align: left;
    gap: 8px;
    th {
      padding: 12px 0 !important;
      span {
        @include fs-5;
        margin-right: 4px;
      }
      span:nth-child(3) {
        @include fw-7;
      }
    }
  }
  thead {
    tr {
      th {
        @include fs-5;
        border-bottom: none;
        width: calc(100% / 4);
      }
      th:nth-child(1) {
        min-width: 80px;
        white-space: normal;
      }
    }
  }
  tbody {
    tr {
      td {
        @include fs-6;
        padding: 8px 6px !important;
      }
      th:nth-child(1) {
        white-space: normal;
      }
      td:not(:first-child) {
        text-align: right;
      }
    }
    tr:first-child {
      @include fw-7;
    }
  }
}
</style>

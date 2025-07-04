<template>
  <div class="TaxDialogComp_wrap">
    <div class="" @click="isPopupOpen = true">
      <slot></slot>
    </div>

    <q-dialog v-model="isPopupOpen">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="q-card_tax_title">
            <p>세금계산서</p>
            <img src="/src/assets/icons/x-solid.svg" v-close-popup />
          </div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>세금계산서</span>
        </div>

        <div class="q-card_tax_section_wrap">
          <!-- <div class="q-card_tax_table_header_wrap">
            <p>세금계산서</p>
          </div> -->
          <div class="flex_between">
            <div class="flex_start" style="gap: 12px">
              <!-- <SelectComp_default
                :selectModel="selectModel" :selectOption="selectOption"
                style="width: 100px;"
              /> -->
              <div class="Select_default_wrap">
                <q-select
                  class="select_default"
                  v-model="searchParam.taxYm"
                  :options="selectOption"
                  borderless
                  dense
                  @update:model-value="(val) => onChangeYear(val)"
                  style="width: 100px"
                />
              </div>
            </div>
            <BtnComp_default
              class="btn-line default btn-pd12 btn-excel"
              btn-label="엑셀"
              @click="exportTable"
            />
          </div>
          <div class="">
            <q-table
              class="taxTable"
              row-key="ROWNUMBER"
              :rows="rows"
              :columns="columns"
              table-header-class="table_header"
              flat
              hide-bottom
              virtual-scroll
              :rows-per-page-options="[0]"
              :virtual-scroll-sticky-size-start="0"
              style="padding-bottom: 12px; border: none"
            >
              <template v-slot:top>
                <div class="tax_table_top_wrap">
                  <div class="tax_table_top">
                    <p>
                      총 <span>{{ rows.length }}건</span>이 조회되었습니다.
                    </p>
                    <p>
                      <span>공급가액</span>
                      <span>
                        {{
                          util.getNumberFormat(
                            rows.reduce(
                              (total, row) => total + row.PAY_SUM_SUPPLY_AMT,
                              0
                            )
                          )
                        }}원
                      </span>
                    </p>
                    <p>
                      <span>부가세</span>
                      <span
                        >{{
                          util.getNumberFormat(
                            rows.reduce(
                              (total, row) => total + row.PAY_SUM_VAT_AMT,
                              0
                            )
                          )
                        }}원</span
                      >
                    </p>
                    <p>
                      <span>총계</span>
                      <span
                        >{{
                          util.getNumberFormat(
                            rows.reduce(
                              (total, row) => total + row.PAY_SUM_TOTAL_AMT,
                              0
                            )
                          )
                        }}원</span
                      >
                    </p>
                  </div>
                </div>
              </template>

              <template v-slot:header="props">
                <q-tr class="">
                  <q-th style="padding: 0">
                    <div class="tax_table_header_wrap">
                      <div class="table_header_item">
                        <span>{{ props.cols[0].label }}</span>
                      </div>
                      <div class="table_header_item">
                        <span>{{ props.cols[1].label }}</span>
                      </div>
                      <div class="table_header_item">
                        <span>{{ props.cols[2].label }}</span>
                      </div>
                      <div
                        class="table_header_item"
                        v-for="(column, i) in props.cols.slice(3)"
                        :key="i"
                      >
                        <div class="table_header_grid_item">
                          <span>{{ column.label }}</span>
                        </div>
                      </div>
                    </div>
                  </q-th>
                </q-tr>
              </template>

              <template v-slot:body="props">
                <q-tr style="height: 38px !important">
                  <div class="tax_table_wrap">
                    <div class="table_item">
                      <q-td>{{ props.row.ROWNUMBER }}</q-td>
                    </div>
                    <div class="table_item">
                      <q-td>{{ props.row.PUB_DT }}</q-td>
                    </div>
                    <div class="table_item">
                      <q-td>{{ props.row.TAX_TYPE_NM }}</q-td>
                    </div>
                    <div class="table_item" style="background-color: #f8f8f8">
                      <q-td
                        >{{
                          util.getNumberFormat(props.row.PAY_SUM_SUPPLY_AMT) ==
                          0
                            ? "-"
                            : util.getNumberFormat(props.row.PAY_SUM_SUPPLY_AMT)
                        }}
                      </q-td>
                    </div>
                    <div class="table_item" style="background-color: #f8f8f8">
                      <q-td>{{
                        util.getNumberFormat(props.row.PAY_SUM_VAT_AMT) == 0
                          ? "-"
                          : util.getNumberFormat(props.row.PAY_SUM_VAT_AMT)
                      }}</q-td>
                    </div>
                    <div class="table_item" style="background-color: #f8f8f8">
                      <q-td>{{
                        util.getNumberFormat(props.row.PAY_SUM_TOTAL_AMT) == 0
                          ? "-"
                          : util.getNumberFormat(props.row.PAY_SUM_TOTAL_AMT)
                      }}</q-td>
                    </div>
                    <div class="table_item">
                      <q-td>{{
                        util.getNumberFormat(props.row.ETC_PAY_SUPPLY_AMT) == 0
                          ? "-"
                          : util.getNumberFormat(props.row.ETC_PAY_SUPPLY_AMT)
                      }}</q-td>
                    </div>
                    <div class="table_item">
                      <q-td>{{
                        util.getNumberFormat(props.row.ETC_PAY_VAT_AMT) == 0
                          ? "-"
                          : util.getNumberFormat(props.row.ETC_PAY_VAT_AMT)
                      }}</q-td>
                    </div>
                    <div class="table_item">
                      <q-td>{{
                        util.getNumberFormat(props.row.ETC_PAY_TOTAL_AMT) == 0
                          ? "-"
                          : util.getNumberFormat(props.row.ETC_PAY_TOTAL_AMT)
                      }}</q-td>
                    </div>
                    <div class="table_item">
                      <q-td>{{
                        util.getNumberFormat(props.row.ETC_NOPAY_SUPPLY_AMT) ==
                        0
                          ? "-"
                          : util.getNumberFormat(props.row.ETC_NOPAY_SUPPLY_AMT)
                      }}</q-td>
                    </div>
                    <div class="table_item">
                      <q-td>{{
                        util.getNumberFormat(props.row.ETC_NOPAY_VAT_AMT) == 0
                          ? "-"
                          : util.getNumberFormat(props.row.ETC_NOPAY_VAT_AMT)
                      }}</q-td>
                    </div>
                    <div class="table_item">
                      <q-td>{{
                        util.getNumberFormat(props.row.ETC_NOPAY_TOTAL_AMT) == 0
                          ? "-"
                          : util.getNumberFormat(props.row.ETC_NOPAY_TOTAL_AMT)
                      }}</q-td>
                    </div>
                    <div class="table_item">
                      <q-td>{{
                        util.getNumberFormat(props.row.PAY_SUPPLY_AMT) == 0
                          ? "-"
                          : util.getNumberFormat(props.row.PAY_SUPPLY_AMT)
                      }}</q-td>
                    </div>
                    <div class="table_item">
                      <q-td>{{
                        util.getNumberFormat(props.row.PAY_VAT_AMT) == 0
                          ? "-"
                          : util.getNumberFormat(props.row.PAY_VAT_AMT)
                      }}</q-td>
                    </div>
                    <div class="table_item">
                      <q-td>{{
                        util.getNumberFormat(props.row.PAY_TOTAL_AMT) == 0
                          ? "-"
                          : util.getNumberFormat(props.row.PAY_TOTAL_AMT)
                      }}</q-td>
                    </div>
                    <div class="table_item">
                      <q-td>{{
                        util.getNumberFormat(props.row.NOPAY_SUPPLY_AMT) == 0
                          ? "-"
                          : util.getNumberFormat(props.row.NOPAY_SUPPLY_AMT)
                      }}</q-td>
                    </div>
                    <div class="table_item">
                      <q-td>{{
                        util.getNumberFormat(props.row.NOPAY_VAT_AMT) == 0
                          ? "-"
                          : util.getNumberFormat(props.row.NOPAY_VAT_AMT)
                      }}</q-td>
                    </div>
                    <div class="table_item">
                      <q-td>{{
                        util.getNumberFormat(props.row.NOPAY_TOTAL_AMT) == 0
                          ? "-"
                          : util.getNumberFormat(props.row.NOPAY_TOTAL_AMT)
                      }}</q-td>
                    </div>
                  </div>
                </q-tr>
              </template>
            </q-table>
          </div>
        </div>

        <q-card-actions class="q-card_btn_wrap">
          <div class="">
            <BtnComp_default
              class="btn-fill gray"
              btnLabel="닫기"
              v-close-popup
            />
          </div>
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import BtnComp_active from "src/components/web/common//BtnComp_active.vue";
import SelectComp_default from "src/components/web/common//SelectComp_default.vue";
import BtnComp_default from "src/components/web/common//BtnComp_default.vue";

const isPopupOpen = ref(false);

const userInfo = util.getUserData();
const bus = inject("bus");

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  taxYm: "",
});

const selectModel = ref();
// const selectOption = [
//   "2030",
//   "2029",
//   "2028",
//   "2027",
//   "2026",
//   "2025",
//   "2024",
//   "2023",
//   "2022",
//   "2021",
//   "2020",
//   "2019",
//   "2018",
//   "2017",
//   "2016",
//   "2015",
//   "2014",
//   "2013",
//   "2012",
// ];

const currentYear = new Date().getFullYear();

const selectOption = [];
for (let year = currentYear; year >= 2012; year--) {
  selectOption.push(year);
}

const columns = [
  { name: "ROWNUMBER", label: "순번", align: "center" },
  { name: "PUB_DT", label: "발급일자", align: "center" },
  { name: "TAX_TYPE_NM", label: "과세구분", align: "left" },

  { name: "합계", label: "합계", align: "center" },
  { name: "전문급여", label: "전문급여", align: "center" },
  { name: "전문비급여", label: "전문비급여", align: "center" },
  { name: "일반급여", label: "일반급여", align: "center" },
  { name: "일반비급여", label: "일반비급여", align: "center" },

  // 합계
  { name: "PAY_SUM_SUPPLY_AMT", label: "공급가액", align: "center" },
  { name: "PAY_SUM_VAT_AMT", label: "부가세", align: "center" },
  { name: "PAY_SUM_TOTAL_AMT", label: "합계", align: "center" },

  // 전문급여
  { name: "ETC_PAY_SUPPLY_AMT", label: "공급가액", align: "center" },
  { name: "ETC_PAY_VAT_AMT", label: "부가세", align: "center" },
  { name: "ETC_PAY_TOTAL_AMT", label: "합계", align: "center" },

  // 전문비급여
  { name: "ETC_NOPAY_SUPPLY_AMT", label: "공급가액", align: "center" },
  { name: "ETC_NOPAY_VAT_AMT", label: "부가세", align: "center" },
  { name: "ETC_NOPAY_TOTAL_AMT", label: "합계", align: "center" },

  // 일반급여
  { name: "PAY_SUPPLY_AMT", label: "공급가액", align: "center" },
  { name: "PAY_VAT_AMT", label: "부가세", align: "center" },
  { name: "PAY_TOTAL_AMT", label: "합계", align: "center" },

  // 일반비급여
  { name: "NOPAY_SUPPLY_AMT", label: "공급가액", align: "center" },
  { name: "NOPAY_VAT_AMT", label: "부가세", align: "center" },
  { name: "NOPAY_TOTAL_AMT", label: "합계", align: "center" },
];

const rows = ref([]);

//---------------------------- Function ------------------------------//

bus.on("texBillInfo", async () => {
  searchParam.value.taxYm = util.getToday("YYYY");

  await fetchData();
});

async function fetchData() {
  const res = await api.get("/myPage/taxBill", {
    params: searchParam.value,
  });
  rows.value = res;
}

async function onChangeYear(newValue) {
  await fetchData();
}

function exportTable() {
  api
    .get("excel/taxBill", {
      params: searchParam.value,
      responseType: "arraybuffer",
    })
    .then(function (res) {
      const url = window.URL.createObjectURL(new Blob([res]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute(
        "download",
        "세금계산서_" + searchParam.value.taxYm + ".xls"
      );
      document.body.appendChild(link);
      link.click();
      link.remove();
    })
    .catch(function (error) {
      console.log(error);
    });
}
</script>

<style lang="scss">
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  margin-bottom: 10px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid $line-300;
}
.q-card_tax_title {
  @include flex_between;
  padding: 0 0 16px 0;
  border-bottom: solid 1px #999 !important;
  p {
    @include fw-7;
  }
  img {
    cursor: pointer;
  }
}
.q-card_tax_section_wrap {
  // padding: 0 16px;
  // width: 1600px;
  width: calc(90vw - 64px);
  max-width: 1200px;
  .q-card_tax_table_header_wrap {
    padding-bottom: 8px;
    p {
      @include fs-5;
      @include fw-7;
      color: $b3;
    }
  }
}
.q-card_btn_wrap {
  @include flex_center;
  margin: 8px;
  div {
    @include flex_center;
    border-radius: 2.4px;

    gap: 8px;
  }
}

.taxTable {
  height: calc(70vh - 65px);
  overflow-y: auto;

  .tax_table_top_wrap {
    width: 100%;
    .tax_table_top {
      @include flex_start;
      gap: 40px;
      margin: 12px 0;
      padding: 8px 15px;
      // background-color: rgba(31, 63, 130, 0.1);
      background-color: #f8f8f8;
      p:nth-child(1) {
        @include fs-5;
        span {
          @include fs-5;
          @include fw-7;
          color: $b3;
        }
      }
      p {
        span {
          @include fs-5;
        }
        span:nth-child(2) {
          @include fw-7;
          margin-left: 15px;
        }
      }
    }
  }
  .q-table tr,
  .q-table th {
    border: none !important;
  }
  .tax_table_header_wrap {
    // background-color: #eee;
    // border-top: 2px solid $b3;
    border-top: 1px solid $th-line;
    border-bottom: 1px solid $th-line;
    background-color: $th-bg;
    display: grid;
    grid-template-columns: 50px 70px repeat(16, 100px);
    .table_header_item {
      text-align: center;
      span {
        @include fs-5;
        @include fw-7;
      }
      .table_header_grid_item {
        padding: 6px 0;
        // border-left: 1px solid $th-line;
      }
    }
    .table_header_item:nth-child(1),
    .table_header_item:nth-child(2),
    .table_header_item:nth-child(3) {
      @include flex_center;
      grid-column: span 1;
      grid-row: span 2;
    }
    .table_header_item:nth-child(4),
    .table_header_item:nth-child(5),
    .table_header_item:nth-child(6),
    .table_header_item:nth-child(7),
    .table_header_item:nth-child(8) {
      grid-column: span 3 !important;
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      border-bottom: 1px solid $th-line;
      .table_header_grid_item:nth-child(1) {
        grid-column: span 3 !important;
      }
    }
  }
  .q-table tr {
    border-bottom: none;
  }
  .tax_table_wrap {
    display: grid;
    grid-template-columns: 50px 70px repeat(16, 100px);
    .table_item {
      td {
        text-align: right !important;
        font-size: 14px;
        padding: 0;
        height: 38px !important;
        border-bottom: 1px solid $td-line;
      }
    }
    .table_item:nth-child(4),
    .table_item:nth-child(7),
    .table_item:nth-child(10),
    .table_item:nth-child(13),
    .table_item:nth-child(16) {
      td {
        // border-left: 1px solid $td-line;
      }
    }

    .table_item:nth-child(n + 1):nth-child(-n + 3) {
      td {
        @include flex_center;
      }
    }
    .table_item:nth-child(n + 4):nth-child(-n + 18) {
      td {
        @include flex_end;
        padding-right: 4px;
      }
    }
  }
}
</style>

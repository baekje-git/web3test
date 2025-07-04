<template>
  <div class="">
    <q-table
      class="salesTable_a"
      :rows="rows"
      :columns="columns"
      row-key="BALANCE_YM"
      table-header-class="table_header"
      hide-bottom
      dense
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
      :selected="selected"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="q-table_wrap" style="margin-bottom: 10px; padding-top: 1px">
          <div class="flex_between">
            <div class="q-table_header_wrap">
              <div class="q-table_header_title_sub">
                <div class="q-table_input_wrap">
                  <!-- <q-input
                    v-model="searchParam.year"
                    class="top-left_input"
                    borderless
                    dense
                    type="number"
                    @update:model-value="(val) => setDate(val)"
                  /> -->
                  <div class="SelectYearComp_wrap">
                    <q-input
                      v-model="searchParam.year"
                      input-class="input_Year"
                      suffix="년"
                      borderless
                      dense
                      maxlength="4"
                      @update:model-value="(val) => setDate(val)"
                      oninput="this.value = this.value.replace(/[^0-9.]/g, '')"
                    >
                      <template v-slot:prepend>
                        <div class="input_Year_icon" @click="minus">
                          <img src="assets/icons/arrow-prev01.svg" alt="icon" />
                        </div>
                      </template>
                      <template v-slot:append>
                        <div class="input_Year_icon" @click="plus">
                          <img src="assets/icons/arrow-next01.svg" alt="icon" />
                        </div>
                      </template>
                    </q-input>
                  </div>
                </div>
              </div>
            </div>
            <BtnComp_default
              class="btn-fill gray"
              btnLabel="뒤로가기"
              @click="onClickMyPage"
            />
            <!-- <div class="q-table_header_sub_wrap">
              <div class="flex_center">
                <div class="flex_center">
                  <BtnComp_default
                    class="btn-fill gray"
                    btnLabel="뒤로가기"
                    @click="onClickMyPage"
                  />
                </div>
              </div>
            </div> -->
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

      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td
            key="BALANCE_YM"
            :props="props"
            style="width: 10%; cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ moment(props.row.BALANCE_YM).format("YYYY-MM") }}
          </q-td>
          <q-td
            key="SALE_AMT"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ util.getNumberFormat(props.row.SALE_AMT) }}
          </q-td>
          <q-td
            key="PAYBACK_AMT"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ util.getNumberFormat(props.row.PAYBACK_AMT) }}
          </q-td>
          <q-td
            key="BACK_AMT"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ util.getNumberFormat(props.row.BACK_AMT) }}
          </q-td>
          <q-td
            key="SUBDIV_BACK_AMT"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ util.getNumberFormat(props.row.SUBDIV_BACK_AMT) }}
          </q-td>
          <q-td
            key="TOTAL_AMT"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ util.getNumberFormat(props.row.TOTAL_AMT) }}
          </q-td>
          <q-td
            key="PURE_BACK_AMT"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ util.getNumberFormat(props.row.PURE_BACK_AMT) }}
          </q-td>
          <q-td
            key="PRE_TOTAL_AMT"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ util.getNumberFormat(props.row.PRE_TOTAL_AMT) }}
          </q-td>
          <q-td
            key="PAY_CASH_AMT"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ util.getNumberFormat(props.row.PAY_CASH_AMT) }}
          </q-td>
          <q-td
            key="PAY_ETC_AMT"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ util.getNumberFormat(props.row.PAY_ETC_AMT) }}
          </q-td>
          <q-td
            key="BALANCE_A_AMT"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ util.getNumberFormat(props.row.BALANCE_A_AMT) }}
          </q-td>
          <q-td
            key="ROTATE_DAY"
            :props="props"
            style="cursor: pointer"
            @click="onClickRow(props.row)"
          >
            {{ props.row.ROTATE_DAY }}
          </q-td>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { computed, ref, onMounted } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

//import SelectYearComp from "src/components/web/common/SelectYearComp.vue";
import SelectYearComp from "src/components/web/common/SelectYearComp.vue";
// import DatePickerComp_b from "src/components/web/common/DatePickerComp_b.vue";
import { useRouter } from "vue-router";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import moment from "moment";

const router = useRouter();
const userInfo = util.getUserData();
const rows = ref([]);
const loading = ref(false);
const searched = ref(false);
const selected = ref([]);

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  year: "",
  endDt: "",
});

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  loading.value = true;
  const res = await api.get("/custMonth/listSearch", {
    params: searchParam.value,
  });
  searched.value = true;
  rows.value = res;

  loading.value = false;
}

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

const date = ref("");

const plus = async () => {
  const dateValue = Number(searchParam.value.year) + 1;
  await setDate(dateValue.toString());
};

const minus = async () => {
  if (searchParam.value.year > 0) {
    const dateValue = Number(searchParam.value.year) - 1;
    await setDate(dateValue.toString());
  }
};

async function setDate(val) {
  if (val) {
    searchParam.value.year = val;
    searchParam.value.endDt = `${val}1231`;

    if (searchParam.value.year.length === 4) {
      await fetchItemList();
    }
  }
}

async function onClickMyPage() {
  await router.push({ path: "/myPage" });
}

onMounted(async () => {
  await setDate(util.getToday("YYYY"));
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "BALANCE_YM",
    label: "월별",
    field: "BALANCE_YM",
    align: "center",
  },
  { name: "SALE_AMT", label: "매출", field: "SALE_AMT", align: "right" },
  {
    name: "PAYBACK_AMT",
    label: "수금할인",
    field: "PAYBACK_AMT",
    align: "right",
  },
  {
    name: "BACK_AMT",
    label: "반품",
    field: "BACK_AMT",
    align: "right",
  },
  {
    name: "SUBDIV_BACK_AMT",
    label: "소분반품",
    field: "SUBDIV_BACK_AMT",
    align: "right",
  },
  {
    name: "TOTAL_AMT",
    label: "순매출액",
    field: "TOTAL_AMT",
    align: "right",
  },
  {
    name: "PURE_BACK_AMT",
    label: "순반품액",
    field: "PURE_BACK_AMT",
    align: "right",
  },
  {
    name: "PRE_TOTAL_AMT",
    label: "매출(16_15)",
    field: "PRE_TOTAL_AMT",
    align: "right",
  },
  {
    name: "PAY_CASH_AMT",
    label: "입금",
    field: "PAY_CASH_AMT",
    align: "right",
  },
  {
    name: "PAY_ETC_AMT",
    label: "잔고대체",
    field: "PAY_ETC_AMT",
    align: "right",
  },
  {
    name: "BALANCE_A_AMT",
    label: "잔고액",
    field: "BALANCE_A_AMT",
    align: "right",
  },
]);
</script>

<style lang="scss" scoped>
.salesTable_a {
  // height: 736px;
  height: 690px;
  overflow-y: auto;
}
.q-table_input_wrap {
  // @include flex_center;
  display: flex;
  gap: 8px;
  // margin: 12px 44px;
  padding: 0 8px;
}
.top-left_input {
  @include fs-5;
  width: 120px;
  height: 28px;
  border: 1px solid #666666;
  padding: 2px 6px;
  border-radius: 5px;
}
.top-left_input::placeholder {
  @include fs-5;
}
.top-left_input_wrap .q-field--dense .q-field__control {
  height: 28px !important;
}
.q-field__control-container {
  @include flex_center;
}

.flex_between {
  @include flex_between;
}
.q-table_calrendar_wrap,
.q-table_btn_export_wrap {
  @include flex_center;
  gap: 8px;
}
// .q-table_btn_option_wrap {
//   margin-left: 15px;
// }

.q-table_bottom_sub_wrap {
  @include flex_start;
  gap: 40px;
  margin: 8px 0;
  padding: 8px 15px;
  background-color: rgba(31, 63, 130, 0.1);
  width: 100%;
}
.q-table_bottom_sub_wrap p span {
  @include fw-5;
  // margin-left: 15px;
}

.Btn_options_wrap {
  @include flex_center;
  gap: 8px;
}
.btn_options {
  color: $w;
  background-color: #999999;
  border-radius: 5px;
  padding: 0 5px;
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
  padding: 0 5px;
}
.btn_active {
  @include gradation;
  border-radius: 5px;
  color: $w;
  padding: 0 5px;
}

// .DatePickerComp_b_wrap {
//   @include flex_center;
//   gap: 8px;
// }
// .DatePickerComp_b_wrap .date_input_wrap {
//   width: 120px;
//   height: 24px;
//   padding: 0 4px;
//   border: 1px solid #666666;
//   border-radius: 2.4px;
// }
// .date_input_wrap p {
//   @include fs-5;
//   text-align: center;
// }
.DatePickerComp_b_wrap {
  @include flex_start;
  // gap: 8px;
  width: 126px;
  height: 32px;
  border: 1px solid $line-400;
  .q-input {
    width: 100%;
  }
  .q-field__native {
    width: 100%;
    border: none;
  }
}
.date_input_wrap,
.date_btn_wrap {
  display: inline-flex;
  align-items: center;
}
.date_input_wrap {
  padding: 0 4px;
  width: calc(100% - 30px);
  border-radius: 0;
}
.date_btn_wrap {
  position: relative;
  width: 30px;
  .q-btn {
    width: 100%;
    border-radius: 0;
  }
  &::before {
    content: "";
    position: absolute;
    top: 50%;
    left: -1px;
    transform: translateY(-50%);
    width: 1px;
    height: 12px;
    background-color: $line-400;
  }
}
</style>

<template>
  <div class="">
    <q-table
      class="myOrderTable_view"
      :rows="rows"
      :columns="columns"
      row-key="SN"
      table-header-class="table_header"
      dense
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :selected="selected"
    >
      <template v-slot:top>
        <div class="q-table_wrap">
          <div
            class="flex_between q-table_noheader"
            style="border-bottom: none"
          >
            <div class="q-table_header_wrap">
              <div class="q-table_header_title">
                <p style="margin-bottom: 5px">최근 주문 내역</p>
              </div>
            </div>
            <div class="q-table_btn_wrap">
              <BtnComp_default_small
                class="btn-line default"
                btn_defaultLabel="+ more"
                @click="router.push('/recently')"
              />
            </div>
          </div>
        </div>
      </template>

      <template v-slot:body-cell-ORDDTTM="props">
        <q-td
          :props="props"
          style="
            font-weight: bold;
            cursor: pointer;
            width: 106px;
            text-align: right;
          "
          @click="onClickRow(props.row)"
        >
          {{ moment(props.value).format("YYYY-MM-DD") }}
          <br />&nbsp;&nbsp;&nbsp;&nbsp;
          {{ moment(props.value).format("HH:mm:ss") }}
        </q-td>
      </template>

      <template v-slot:body-cell-APRVDTTM="props">
        <q-td
          :props="props"
          style="cursor: pointer; width: 106px; text-align: right"
          @click="onClickRow(props.row)"
        >
          {{
            props.value == "" ? "" : moment(props.value).format("YYYY-MM-DD")
          }}
          <br />
          {{ props.value == "" ? "" : moment(props.value).format("HH:mm:ss") }}
        </q-td>
      </template>

      <template v-slot:body-cell-GUBUN="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-STATS="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_NM_UNIT="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <p class="ellipsis td-prd_name">
            {{ props.value }}
          </p>
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_CNT="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-TOTAL_AMT="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-ORD_MEMO="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <p class="ellipsis">
            <!-- style="
              display: block;
              width: 130px;
              max-width: 130px;
              text-align: left;
            " -->
            {{ props.value }}
          </p>
        </q-td>
      </template>
    </q-table>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { useRouter } from "vue-router";

import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
import moment from "moment";

const router = useRouter();
const userInfo = util.getUserData();
const rows = ref([]);
const selected = ref([]);

const searchParam = ref({
  custCd: userInfo.CUST_CD,
});

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/myPage/rcntlyOrd", { params: searchParam.value });

  rows.value = res;
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

onMounted(async () => {
  await fetchData();
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "ORDDTTM",
    label: "주문일자",
    field: "ORDDTTM",
    align: "cemter",
    classes: "td-date",
  },
  {
    name: "APRVDTTM",
    label: "승인일시",
    field: "APRVDTTM",
    align: "center",
    classes: "td-date",
  },
  {
    name: "GUBUN",
    label: "구분",
    field: "GUBUN",
    align: "center",
    classes: "td-type",
  },
  {
    name: "STATS",
    label: "상태",
    field: "STATS",
    align: "center",
    classes: "td-type",
  },
  {
    name: "ITEM_NM_UNIT",
    label: "품목 및 규격",
    field: "ITEM_NM_UNIT",
    align: "left",
    classes: "td-prd",
  },
  {
    name: "ITEM_CNT",
    label: "품목수",
    field: "ITEM_CNT",
    align: "center",
    classes: "td-qty",
  },
  {
    name: "TOTAL_AMT",
    label: "총 주문금액",
    field: "TOTAL_AMT",
    align: "right",
    classes: "td-price",
    format: (val) => util.getNumberFormat(val),
  },
  {
    name: "ORD_MEMO",
    label: "메모",
    field: "ORD_MEMO",
    align: "left",
    classes: "td-comp",
  },
]);
</script>

<style lang="scss">
.myOrderTable_view {
  // height: 375px;
  height: 400px;
  overflow-y: auto;
}
.myOrderTable_view tr {
  height: 40px !important;
}

.flex_between {
  @include flex_between;
}
</style>

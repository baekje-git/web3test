<template>
  <q-dialog v-model="isOpen" @escape-key="onClickClose">
    <q-card style="padding: 32px">
      <div class="dialog_header">
        <span>매출원장상세</span>
      </div>
      <q-table
        class="salesTable_a"
        :rows="rows"
        :columns="columns"
        row-key="name"
        table-header-class="table_header"
        dense
        hide-bottom
        virtual-scroll
        :rows-per-page-options="[0]"
        :virtual-scroll-sticky-size-start="0"
        :loading="loading"
      >
        <template v-slot:loading>
          <q-inner-loading showing color="primary" />
        </template>
        <template v-slot:top>
          <div class="q-table_wrap">
            <!-- <div class="q-table_header_wrap">
              <div class="q-table_header_title">
                <p>매출원장상세</p>
              </div>
            </div> -->

            <div class="q-table_header_sub_wrap">
              <p>
                <span>주문일자</span>
                <span>{{ moment(detailData.ordDt).format("YYYY-MM-DD") }}</span>
              </p>
              <p>
                <span>주문서구분</span>
                <span>{{ detailData.ordTypeNm }}</span>
              </p>
              <p>
                <span>종류</span>
                <span>{{ detailData.gubunNm }}</span>
              </p>
              <p>
                <span>승인번호</span>
                <span>{{ detailData.aprvNo }}</span>
              </p>
              <!-- </div>
            <div class="q-table_header_sub_wrap" style="margin-top: 0px"> -->
              <p>
                <span>품목명</span>
                <span class="ellipsis">{{
                  `${detailData.itemNmUnit} ${detailData.itemCntTxt}`
                }}</span>
              </p>
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
            <q-td key="ROWNUMBER" :props="props">
              <div>{{ props.row.ROWNUMBER }}</div>
            </q-td>
            <q-td key="PAY_ITEM_NM" :props="props">
              <div>{{ props.row.PAY_ITEM_NM }}</div>
            </q-td>
            <q-td key="MAKER_NM" :props="props">
              <div>{{ props.row.MAKER_NM }}</div>
            </q-td>
            <q-td key="ITEM_NM_UNIT" :props="props">
              <div class="td-prd_name">{{ props.row.ITEM_NM_UNIT }}</div>
            </q-td>
            <q-td key="BOHUM_CD" :props="props">
              <div>{{ props.row.BOHUM_CD }}</div>
            </q-td>
            <q-td key="ITEM_QTY" :props="props">
              <div>{{ props.row.ITEM_QTY }}</div>
            </q-td>
            <q-td key="ORD_WP2_AMT" :props="props">
              <div>{{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}</div>
            </q-td>
            <q-td key="TOTAL_AMT" :props="props">
              <div>
                <p v-if="props.row.TOTAL_AMT < 0" class="red-400">
                  {{ util.getNumberFormat(props.row.TOTAL_AMT) }}
                </p>
                <p v-if="props.row.TOTAL_AMT >= 0">
                  {{ util.getNumberFormat(props.row.TOTAL_AMT) }}
                </p>
              </div>
            </q-td>
            <q-td key="EXP_DT" :props="props">
              <!-- {{ props.row.EXP_DT }} -->
              <div>
                {{
                  props.row.EXP_DT != ""
                    ? moment(props.row.EXP_DT, "YYYY-MM-DD HH:mm:ss").format(
                        "YYYY-MM-DD"
                      )
                    : ""
                }}
              </div>
            </q-td>
            <q-td key="PROD_NO" :props="props">
              <div>{{ props.row.PROD_NO }}</div>
            </q-td>
          </q-tr>
        </template>
      </q-table>
      <div>
        <div align="right" style="margin: 10px 8px 10px">
          <BtnComp_active btn-label="닫기" @click="onClickClose" />
        </div>
      </div>
    </q-card>
  </q-dialog>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, computed, defineEmits, inject } from "vue";
import * as util from "src/support/util";
import { api } from "boot/axios";
import moment from "moment";

import BtnComp_active from "../common/BtnComp_active.vue";

const emit = defineEmits(["close"]);
const bus = inject("bus");
const loading = ref(false);
const searched = ref(false);

const rows = ref([]);

const props = defineProps({
  isOpen: { type: Boolean },
});

const isOpen = computed(() => {
  return props.isOpen;
});

const dataParams = ref({
  sn: "",
});

const detailData = ref({
  ordDt: "",
  ordTypeNm: "",
  gubunNm: "",
  aprvNo: "",
  itemNmUnit: "",
  itemCntTxt: "",
});

//---------------------------- Function ------------------------------//

function onClickClose() {
  rows.value = [];

  detailData.value.ordDt = "";
  detailData.value.ordTypeNm = "";
  detailData.value.gubunNm = "";
  detailData.value.aprvNo = "";
  detailData.value.itemNmUnit = "";
  detailData.value.itemCntTxt = "";

  emit("close");
}

bus.on("ordLedgerDetail", async (data) => {
  loading.value = true;

  dataParams.value.sn = data.SN;

  const { custList, detailMaster } = await api.get("/ordLedger/detailSearch", {
    params: dataParams.value,
  });

  rows.value = custList;

  detailData.value.ordDt = detailMaster.ORD_DT;
  detailData.value.ordTypeNm = detailMaster.ORD_TYPE_NM;
  detailData.value.gubunNm = detailMaster.GUBUN_NM;
  detailData.value.aprvNo = detailMaster.APRV_NO;
  detailData.value.itemNmUnit = detailMaster.ITEM_NM_UNIT;
  detailData.value.itemCntTxt = detailMaster.ITEM_CNT_TXT;

  searched.value = true;
  loading.value = false;
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "ROWNUMBER",
    label: "순번",
    align: "center",
    required: true,
    classes: "td-num",
    field: "ROWNUMBER",
  },
  {
    name: "PAY_ITEM_NM",
    label: "급여",
    align: "center",
    required: true,
    classes: "td-pay",
    field: "PAY_ITEM_NM",
  },
  {
    name: "MAKER_NM",
    label: "제약사",
    field: "MAKER_NM",
    align: "left",
    classes: "td-comp_dtl",
  },
  {
    name: "ITEM_NM_UNIT",
    label: "품목 및 규격",
    field: "ITEM_NM_UNIT",
    align: "left",
    classes: "td-prd",
  },
  {
    name: "BOHUM_CD",
    label: "보험코드",
    field: "BOHUM_CD",
    align: "center",
    classes: "td-code",
  },
  {
    name: "ITEM_QTY",
    label: "수량",
    field: "ITEM_QTY",
    align: "center",
    classes: "td-qty",
  },
  {
    name: "ORD_WP2_AMT",
    label: "단가",
    field: "ORD_WP2_AMT",
    align: "center",
    classes: "td-price",
  },
  {
    name: "TOTAL_AMT",
    label: "금액",
    field: "TOTAL_AMT",
    align: "center",
    classes: "td-price",
  },
  {
    name: "EXP_DT",
    label: "유효기간",
    field: "EXP_DT",
    align: "center",
    classes: "td-exp",
  },
  {
    name: "PROD_NO",
    label: "제조번호",
    field: "PROD_NO",
    align: "center",
    classes: "td-serial",
  },
]);
</script>

<style lang="scss" scoped>
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid $line-300;
}
.salesTable_a {
  width: calc(90vw - 64px);
  max-width: 1200px;
  height: 756px;
  height: 65vh;
  overflow-y: auto;
  // padding: 32px 0 10px;
}
.q-table_radio_wrap {
  padding: 0 8px;
}
.flex_between {
  @include flex_between;
}
.q-table_calrendar_wrap,
.q-table_btn_export_wrap {
  @include flex_center;
  gap: 8px;
}
.q-table_btn_option_wrap {
  margin-left: 20px;
}
.q-table_header_sub_wrap {
  @include flex_start;
  gap: 10px 40px;
  margin-bottom: 8px;
  // margin: 8px 0;
  // padding: 8px 15px;
  // background-color: #f8f8f8;
}
.q-table_header_sub_wrap p span {
  @include fs-5;
}
.q-table_header_sub_wrap p span:nth-child(2) {
  @include fw-7;
  margin-left: 15px;
}
</style>

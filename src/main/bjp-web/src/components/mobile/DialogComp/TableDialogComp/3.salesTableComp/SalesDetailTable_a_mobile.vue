<template>
  <div class="" style="padding: 10px 0">
    <q-table
      class=""
      row-key="날짜"
      :rows="rows"
      flat
      hide-header
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
    >
      <template v-slot:top>
        <div class="border-top"></div>
      </template>
      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item table_item_mobile">
              <div class="table_badge_wrap">
                <!-- <q-badge class="table_badge_red">
                  {{ props.row.MED_NM }}
                </q-badge>
                <q-badge class="table_badge_blue">
                  {{ props.row.PAY_ITEM_NM_MOBILE }}
                </q-badge> -->
                <q-badge class="bdg-line normal">
                  {{
                    props.row.MED_NM === "일"
                      ? "일반"
                      : props.row.MED_NM === "전"
                      ? "전문"
                      : props.row.MED_NM
                  }}
                </q-badge>
                <q-badge
                  class="bdg-line"
                  :class="
                    props.row.PAY_ITEM_NM_MOBILE === '비' ? 'nonpay' : 'pay'
                  "
                >
                  {{
                    props.row.PAY_ITEM_NM_MOBILE === "급"
                      ? "급여"
                      : props.row.PAY_ITEM_NM_MOBILE === "비"
                      ? "비급여"
                      : props.row.PAY_ITEM_NM_MOBILE
                  }}
                </q-badge>
              </div>
              <q-td class="table-td_mobile">
                <p>
                  {{ props.row.ITEM_NM_UNIT }}
                </p>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.규격 }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                단가
                <p>{{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}</p>
                원
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                유효기간
                {{
                  props.row.EXP_DT === ""
                    ? ""
                    : moment(props.row.EXP_DT, "YYYY-MM-DD HH:mm:ss").format(
                        "YYYY-MM-DD"
                      )
                }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile"
                >{{ util.getNumberFormat(props.row.ITEM_QTY) }}개</q-td
              >
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                금액
                <p>{{ util.getNumberFormat(props.row.TOTAL_AMT) }}</p>
                원
              </q-td>
            </div>
          </div>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, defineProps, onMounted } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";

const props = defineProps({
  SN: {
    type: String,
    default: "",
  },
});

const searchParam = ref({
  sn: props.SN,
});

const rows = ref([]);

onMounted(async () => {
  const { custList, detailMaster } = await api.get("/ordLedger/detailSearch", {
    params: searchParam.value,
  });

  rows.value = custList;
});
</script>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap");

.table_wrap {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  padding: 6px 0 !important;
  gap: 8px;
  .table_item:nth-child(1) {
    @include flex_start;
    @include fw-7;
    grid-column: span 6;
    max-width: 328px;
    gap: 8px;
    height: auto !important;
    td {
      // overflow: hidden;
      // text-overflow: ellipsis;
      height: auto !important;
      white-space: normal;
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 3;
    td {
      @include flex_start;
      align-items: flex-start;
      // @include fs-6;
      // font-size: 12px !important;
      // color: #666666;
      overflow: hidden;
      text-overflow: ellipsis;
      display: block;
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
      // color: #666666;
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 2;
    td {
      @include flex_end;
      @include fs-5;

      // p {
      //   font-family: "Malgun Gothic";
      // }
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 3;
    td {
      @include flex_start;
      // @include fs-6;
      // font-size: 12px !important;
      // color: #666666;
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 1;
    td {
      @include flex_end;
      // @include fs-5;
      // color: #666666;
    }
  }
  .table_item:nth-child(7) {
    grid-column: span 2;
    td {
      @include flex_end;
      @include fs-5;

      // p {
      //   font-family: "Malgun Gothic";
      // }
    }
  }
}
</style>

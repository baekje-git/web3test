<template>
  <div class="" style="padding: 10px 0">
    <q-table
      class=""
      row-key="약"
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
              <q-checkbox
                v-if="cnclYn == 'Y'"
                class="table_checkbox_gray"
                v-model="table_checkbox"
                :checked-icon="mdiCheckboxOutline"
                :unchecked-icon="mdiCheckboxBlankOutline"
                :val="props.row"
                dense
              />
              <div class="table_badge_wrap">
                <!-- <q-badge class="table_badge_red">
                  {{ props.row.MED_NM }}
                </q-badge>
                <q-badge class="table_badge_blue">
                  {{ props.row.PAY_ITEM_NM }}
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
                  :class="props.row.PAY_ITEM_NM === '비' ? 'nonpay' : 'pay'"
                >
                  {{
                    props.row.PAY_ITEM_NM === "급"
                      ? "급여"
                      : props.row.PAY_ITEM_NM === "비"
                      ? "비급여"
                      : props.row.PAY_ITEM_NM
                  }}
                </q-badge>
              </div>
              <q-td class="table-td_mobile">{{ props.row.ITEM_NM_UNIT }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">등록</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ util.getNumberFormat(props.row.ITEM_QTY_REQ) }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">단가</q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>{{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}</span>
                <span>원</span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span>결과 : </span>
                <span>{{ props.row.EXAM_RSLT_NM }}</span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">검수</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ util.getNumberFormat(props.row.ITEM_QTY) }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">금액</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span>{{ util.getNumberFormat(props.row.PRICE) }}</span>
                <span>원</span>
              </q-td>
            </div>
          </div>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, inject, onMounted, defineProps, watch } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";
import { mdiCheckboxOutline, mdiCheckboxBlankOutline } from "@mdi/js";

const bus = inject("bus");
const props = defineProps({
  sn: {
    type: String,
    default: "",
  },
  cnclYn: {
    type: String,
    default: "",
  },
});

const searchParam = ref({
  sn: props.sn,
});

const table_checkbox = ref([]);

const rows = ref([]);

//체크된 값 보내기
watch(table_checkbox, () => {
  bus.emit("chkTotal", table_checkbox);
  bus.emit("loadTotal", rows.value);
});

async function fetchDtlItemList() {
  const res = await api.get("/bakReq/detailSearch", {
    params: searchParam.value,
  });
  rows.value = res;
}

onMounted(async () => {
  await fetchDtlItemList();
});

bus.off("fetchDtlItemList");
bus.on("fetchDtlItemList", async () => {
  await fetchDtlItemList();
});
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  padding: 8px 0 !important;
  gap: 8px;
  .table_item:nth-child(1) {
    @include flex_start;
    @include fw-6;
    grid-column: span 10;
    gap: 4px;
    height: auto !important;
    td {
      // overflow: hidden;
      // text-overflow: ellipsis;
      height: auto !important;
      white-space: normal;
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 5;
    td {
      @include flex_start;
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
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
      // color: #666666;
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 1;
    td {
      @include flex_center;
      @include fs-5;
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 2;
    td {
      @include flex_end;
      span {
        @include fs-5;
      }
    }
  }
  .table_item:nth-child(7) {
    grid-column: span 5;
    td {
      @include flex_start;
      span {
        @include fs-5;
      }
    }
  }
  .table_item:nth-child(8) {
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
    }
  }
  .table_item:nth-child(9) {
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
      // color: #666666;
    }
  }
  .table_item:nth-child(10) {
    grid-column: span 1;
    td {
      @include flex_center;
      @include fs-5;
    }
  }
  .table_item:nth-child(11) {
    grid-column: span 2;
    td {
      @include flex_end;
      span {
        @include fs-5;
      }
    }
  }
}
</style>

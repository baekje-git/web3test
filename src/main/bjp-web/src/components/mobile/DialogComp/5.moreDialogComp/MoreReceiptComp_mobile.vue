<template>
  <div class="MoreReceipt_wrap">
    <q-markup-table separator="noen" flat dense>
      <tbody>
        <tr>
          <td>가용재고</td>
          <td>{{ stock }}</td>
        </tr>
        <tr>
          <td>기간</td>
          <td>
            {{ moment(startYmd).format("YYYY-MM-DD") }}
            <span style="font-size: 12px; color: #999999; margin: 0 8px"
              >~</span
            >
            {{ moment(endYmd).format("YYYY-MM-DD") }}
          </td>
        </tr>
        <tr>
          <td>제약사</td>
          <td>{{ makerNm }}</td>
        </tr>
      </tbody>
    </q-markup-table>
    <div class="">
      <slot></slot>
    </div>
  </div>
  <div class="MoreReceipt_wrap">
    <MoreReceiptDetailTable_mobile :item-info="props" />
  </div>
</template>

<script setup>
import moment from "moment";
import * as util from "src/support/util";

import MoreReceiptDetailTable_mobile from "src/components/mobile/DialogComp/TableDialogComp/5.moreTableComp/MoreReceiptDetailTable_mobile.vue";

const props = defineProps({
  stock: {
    type: Number,
    default: 0,
  },
  startDt: {
    type: String,
    default: "",
  },
  endDt: {
    type: String,
    default: "",
  },
  makerNm: {
    type: String,
    default: "제약사",
  },
  itemCd: {
    type: String,
    default: "품목코드",
  },
  startYmd: {
    type: String,
    default: "시작일자",
  },
  endYmd: {
    type: String,
    default: "종료일자",
  },
  deptNm: {
    type: String,
    default: "",
  },
  sortType: {
    type: String,
    default: "R",
  },
});
</script>

<style lang="scss" scoped>
.MoreReceipt_wrap {
  tr {
    height: 28px !important;
  }

  td {
    @include fs-5;
  }
  td:nth-child(1),
  td:nth-child(3) {
    @include fw-7;
    padding-right: 2px !important;
    padding-left: 1px;
  }
  tr:last-child td:last-child {
    @include fw-7;
  }
}
</style>

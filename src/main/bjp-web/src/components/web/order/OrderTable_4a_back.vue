<template>
  <div class="tbl_wrap rt" style="height: 170px">
    <div class="orderTable_4a_title_wrap">
      <div class="orderTable_4a_title">
        <p>제품정보</p>
        <p>{{ itemDetailInfo.ITEM_NM }}</p>
      </div>
    </div>

    <div class="orderTable_4a">
      <q-markup-table separator="none" flat dense>
        <tbody class="orderTable_4a_tbody">
          <tr>
            <td style="width: 70px" class="text-left">규격</td>
            <td class="text-left">
              {{ itemDetailInfo.UNIT ?? "-" }}
            </td>
            <td style="width: 70px" class="text-left">박스입수</td>
            <td class="text-left">
              <span style="font-weight: bold">(소)</span>
              {{
                itemDetailInfo.SMALLBOX_QTY > 0
                  ? `${itemDetailInfo.SMALLBOX_QTY}`
                  : itemDetailInfo.SMALLBOX_QTY
              }}
              <span style="font-weight: bold">(중)</span>
              {{
                itemDetailInfo.MIDBOX_QTY > 0
                  ? `${itemDetailInfo.MIDBOX_QTY}`
                  : itemDetailInfo.MIDBOX_QTY
              }}
              <span style="font-weight: bold">(대)</span>
              {{
                itemDetailInfo.LARGEBOX_QTY > 0
                  ? `${itemDetailInfo.LARGEBOX_QTY}`
                  : itemDetailInfo.LARGEBOX_QTY
              }}
            </td>
          </tr>
          <tr>
            <td class="text-left">제약회사</td>
            <td class="text-left">{{ itemDetailInfo.MAKER_NM ?? "-" }}</td>
            <td class="text-left">최소주문</td>
            <td class="text-left">
              {{
                itemDetailInfo.MIN_ORD_QTY === 1
                  ? "-"
                  : itemDetailInfo.MIN_ORD_QTY
              }}
            </td>
          </tr>
          <tr>
            <td class="text-left">보험코드</td>
            <td class="text-left">{{ itemDetailInfo.BOHUM_CD ?? "-" }}</td>
            <td class="text-left">최대주문</td>
            <td class="text-left">{{ itemDetailInfo.MAX_ORD_QTY ?? "" }}</td>
          </tr>
          <tr>
            <td class="text-left">반품</td>
            <td class="text-left" style="font-weight: bold; color: #384dd6">
              <!-- {{ itemDetailInfo.TAKEBACK ?? "-" }} -->
              <p
                v-if="itemDetailInfo.WEB_BACK_USE_YN === 'Y'"
                style="color: #f5536d"
              >
                불가
              </p>
              <p
                v-if="itemDetailInfo.TAKEBACK_LIMIT_YN === 'Y'"
                style="color: blue"
              >
                제한
              </p>
            </td>
            <td class="text-left">포장형태</td>
            <td class="text-left">
              {{ itemDetailInfo.PACK_NM }}
            </td>
          </tr>
          <tr>
            <td class="text-left">반품조건</td>
            <td class="text-left">{{ itemDetailInfo.WEB_BACK_MEMO ?? "-" }}</td>
            <td class="text-left">입고일정</td>
            <td class="text-left">{{ itemDetailInfo.SOLDOUT_NOTE ?? "-" }}</td>
          </tr>
        </tbody>
      </q-markup-table>
    </div>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject } from "vue";
import _ from "lodash";

const bus = inject("bus");

const itemDetailInfo = ref({
  ITEM_NM: undefined,
  UNIT: undefined,
  MAKER_NM: undefined,
  BOHUM_CD: undefined,
  TAKEBACK: undefined,
  // 미정의 항목
  WEB_BACK_MEMO: undefined, // 반품조건
  SMALLBOX_QTY: undefined, // 박스입수(소)
  MIDBOX_QTY: undefined, // 박스입수(중)
  LARGEBOX_QTY: undefined, // 박스입수(대)
  MIN_ORD_QTY: undefined, // 최소주문
  MAX_ORD_QTY: undefined, // 최대주문
  PRICE_DOWN_WP2: undefined, // 보험약가(금액)
  PRICE_DOWN_DT: undefined, // 보험약가(일정)
  SOLDOUT_NOTE: undefined, // 입고예정일
  TAKEBACK_LIMIT_YN: undefined,
  WEB_BACK_USE_YN: undefined,
  PACK_NM: undefined,
});

//---------------------------- Function ------------------------------//

// 주문 그리드에서 row 클릭 시 제품정보 조회
bus.on("itemDetailInfo", async (data) => {
  itemDetailInfo.value = _.cloneDeep(data);
});

// 주문 그리드에서 검색 시 초기화
bus.on("init", () => {
  itemDetailInfo.value.ITEM_NM = "";
  itemDetailInfo.value.UNIT = undefined;
  itemDetailInfo.value.MAKER_NM = undefined;
  itemDetailInfo.value.BOHUM_CD = undefined;
  itemDetailInfo.value.TAKEBACK = undefined;
  itemDetailInfo.value.TAKEBACK_COND = undefined;
  itemDetailInfo.value.BOX_PROCURE_S = undefined;
  itemDetailInfo.value.BOX_PROCURE_M = undefined;
  itemDetailInfo.value.BOX_PROCURE_L = undefined;
  itemDetailInfo.value.MIN_ORD_QTY = undefined;
  itemDetailInfo.value.MAX_ORD_QTY = undefined;
  itemDetailInfo.value.POLICY_PRICE = undefined;
  itemDetailInfo.value.INCMNG_SCHDL = undefined;
  itemDetailInfo.value.TAKEBACK_LIMIT_YN = undefined;
  itemDetailInfo.value.WEB_BACK_USE_YN = undefined;
  itemDetailInfo.value.PACK_NM = undefined;
});
</script>

<style lang="scss" scoped>
.orderTable_4a_title_wrap {
  @include flex_between;
  border-bottom: 2px solid $b3;
  padding-bottom: 8px;
}
.orderTable_4a_title {
  @include flex_start;
  gap: 8px;
}
.orderTable_4a_title p {
  @include fw-7;
}
.orderTable_4a_title p:nth-child(2) {
  color: $b3;
}
.orderTable_4a td {
  padding: 0 !important;
}
.orderTable_4a tr td:nth-child(1),
.orderTable_4a tr td:nth-child(3) {
  @include fw-7;
}
.orderTable_4a tr td span {
  @include fs-5;
}
.orderTable_4a_tbody tr {
  height: 24px !important;
}
.orderTable_4a_tbody td {
  height: 30px !important;
  border: none !important;
}
</style>

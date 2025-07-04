<template>
  <div ref="itmDtlRef" class="Table_d_wrap">
    <div class="table_layout_header">
      <div class="table_title">
        <p>제품정보</p>
        <p>{{ itemDetailInfo.ITEM_NM }}</p>
      </div>
    </div>

    <div class="table_sec">
      <q-markup-table separator="none" flat dense>
        <tbody>
          <tr>
            <td style="width: 70px" class="text-left">규격</td>
            <td class="text-left" style="width: 50%">
              {{ itemDetailInfo.UNIT ?? "-" }}
            </td>
            <td style="width: 70px" class="text-left">박스입수</td>
            <td class="text-left spanbold" style="width: 50%">
              <span>(소)</span>
              {{
                itemDetailInfo.SMALLBOX_QTY > 0
                  ? `${itemDetailInfo.SMALLBOX_QTY}`
                  : itemDetailInfo.SMALLBOX_QTY
              }}
              <span>(중)</span>
              {{
                itemDetailInfo.MIDBOX_QTY > 0
                  ? `${itemDetailInfo.MIDBOX_QTY}`
                  : itemDetailInfo.MIDBOX_QTY
              }}
              <span>(대)</span>
              {{
                itemDetailInfo.LARGEBOX_QTY > 0
                  ? `${itemDetailInfo.LARGEBOX_QTY}`
                  : itemDetailInfo.LARGEBOX_QTY
              }}
            </td>
          </tr>
          <tr>
            <td class="text-left">제약사</td>
            <td class="text-left">{{ itemDetailInfo.MAKER_NM ?? "-" }}</td>
            <td class="text-left">최소주문</td>
            <td class="text-left">
              {{ itemDetailInfo.MIN_ORD_QTY ?? "" }}
            </td>
          </tr>
          <tr>
            <td class="text-left">보험코드</td>
            <td class="text-left">{{ itemDetailInfo.BOHUM_CD ?? "-" }}</td>
            <td class="text-left">최대주문</td>
            <td class="text-left">
              {{
                itemDetailInfo.MAX_ORD_QTY === 0 ||
                itemDetailInfo.MAX_ORD_QTY == null
                  ? ""
                  : itemDetailInfo.MAX_ORD_QTY
              }}
            </td>
          </tr>
          <tr>
            <td class="text-left">반품</td>
            <td class="text-left" style="font-weight: bold; color: #384dd6">
              {{ itemDetailInfo.TAKEBACK ?? "-" }}
            </td>
            <td class="text-left">보험약가</td>
            <td class="text-left">
              {{
                `${itemDetailInfo.PRICE_DOWN_DT ?? "-"} |
                ${itemDetailInfo.PRICE_DOWN_WP2 ?? "-"}
                `
              }}
            </td>
          </tr>
          <tr>
            <td class="text-left">반품조건</td>
            <td class="text-left">
              {{ itemDetailInfo.WEB_BACK_MEMO ?? "-" }}
            </td>
            <td class="text-left">입고일정</td>
            <td class="text-left">
              {{ itemDetailInfo.SOLDOUT_NOTE ?? "-" }}
            </td>
          </tr>
        </tbody>
      </q-markup-table>
    </div>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject } from "vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import { api } from "boot/axios";

const bus = inject("bus");

const searchParam = ref({
  selItemCd: "",
});

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
});

//---------------------------- Function ------------------------------//

// 주문 그리드에서 row 클릭 시 제품정보 조회
bus.on("itemDetailInfo", async (data) => {
  itemDetailInfo.value = data;
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

  searchParam.value.selItemCd = "";
});
</script>

<style lang="scss" scoped>
.Table_d_wrap {
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.33);
}
.table_layout_header {
  @include flex_between;
  width: 100%;
  padding-bottom: 8px;
  border-bottom: 1px solid $th-line;
}
.table_title {
  @include flex_start;
}
.table_title p {
  @include fw-7;
}
.table_title p:nth-child(2) {
  color: $b3;
  margin-left: 10px;
}
.spanbold span,
.table_sec tr td:nth-child(1),
.table_sec tr td:nth-child(3) {
  @include fs-5;
  @include fw-7;
  line-height: 24px;
}
.table_sec {
  padding: 10px 0;
}
</style>

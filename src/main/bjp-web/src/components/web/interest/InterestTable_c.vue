<template>
  <div class="">
    <div class="interestTable_c_title_wrap">
      <div class="interestTable_c_title">
        <p>제품정보</p>
        <!-- <p>{{ itemNmUnit }}</p> -->
      </div>
    </div>

    <div class="interestTable_c">
      <div class="prd_wrap prdinfo">
        <div class="prd_img">
          <span>{{ itemNmUnit ? itemNmUnit : "품목 및 규격" }}</span>
        </div>
        <div class="prd_detail">
          <q-markup-table separator="none" flat dense>
            <tbody class="interestTable_c_tbody">
              <tr>
                <td style="width: 70px" class="text-left">박스입수</td>
                <td class="text-left">
                  <span>소 ({{ dataParam.smallboxQty }})</span>
                  <span style="padding-left: 10px"
                    >중 ({{ dataParam.midboxQty }})</span
                  >
                  <span style="padding-left: 10px"
                    >대 ({{ dataParam.largeboxQty }})</span
                  >
                </td>
              </tr>
              <tr>
                <td class="text-left">최대주문</td>
                <td class="text-left">{{ dataParam.maxOrdQty }}</td>
              </tr>
              <tr>
                <td class="text-left">반품</td>
                <td class="text-left" style="color: #384dd6; font-weight: bold">
                  <!-- {{ itemDetailInfo.TAKEBACK ?? "-" }} -->
                  <p v-if="dataParam.WEB_BACK_USE_YN === 'Y'" class="red-400">
                    불가
                  </p>
                  <p
                    v-if="dataParam.TAKEBACK_LIMIT_YN === 'Y'"
                    class="blue-400"
                  >
                    제한
                  </p>
                </td>
              </tr>
              <tr>
                <td class="text-left">반품조건</td>
                <td class="text-left">{{ dataParam.webBackMemo }}</td>
              </tr>
              <tr>
                <td class="text-left">입고일정</td>
                <td class="text-left">{{ dataParam.soldoutNote }}</td>
              </tr>
            </tbody>
          </q-markup-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject } from "vue";

const bus = inject("bus");

const dataParam = ref({
  itemNm: "",
  smallboxQty: "",
  midboxQty: "",
  largeboxQty: "",
  minOrdQty: "",
  maxOrdQty: "",
  priceDownDt: "",
  priceDownWp2: "",
  webBackMemo: "",
  soldoutNote: "",
  takeback: "",
  TAKEBACK_LIMIT_YN: "",
  WEB_BACK_USE_YN: "",
  PACK_NM: "",
});

const itemNmUnit = ref("");

//---------------------------- Function ------------------------------//

bus.on("interestItemDtl", (data) => {
  dataParam.value.itemNm = data.ITEM_NM;
  dataParam.value.smallboxQty = data.SMALLBOX_QTY;
  dataParam.value.midboxQty = data.MIDBOX_QTY;
  dataParam.value.largeboxQty = data.LARGEBOX_QTY;
  dataParam.value.minOrdQty = data.MIN_ORD_QTY;
  dataParam.value.maxOrdQty = data.MAX_ORD_QTY;
  dataParam.value.priceDownDt = data.PRICE_DOWN_DT;
  dataParam.value.priceDownWp2 = data.PRICE_DOWN_WP2;
  dataParam.value.webBackMemo = data.WEB_BACK_MEMO;
  dataParam.value.soldoutNote = data.SOLDOUT_NOTE;
  dataParam.value.takeback = data.TAKEBACK;
  dataParam.value.unit = data.UNIT;
  dataParam.value.TAKEBACK_LIMIT_YN = data.TAKEBACK_LIMIT_YN;
  dataParam.value.WEB_BACK_USE_YN = data.WEB_BACK_USE_YN;
  itemNmUnit.value = `${data.ITEM_NM} ${data.UNIT}`;
});

bus.on("initInterestItemDtl", () => {
  dataParam.value.itemNm = "";
  dataParam.value.smallboxQty = "";
  dataParam.value.midboxQty = "";
  dataParam.value.largeboxQty = "";
  dataParam.value.minOrdQty = "";
  dataParam.value.maxOrdQty = "";
  dataParam.value.priceDownDt = "";
  dataParam.value.priceDownWp2 = "";
  dataParam.value.webBackMemo = "";
  dataParam.value.soldoutNote = "";
  dataParam.value.takeback = "";
  dataParam.value.unit = "";
  dataParam.value.TAKEBACK_LIMIT_YN = "";
  dataParam.value.WEB_BACK_USE_YN = "";

  itemNmUnit.value = "";
});
</script>

<style lang="scss" scoped>
@media screen and (max-width: 830px) {
  .interestTable_c {
    margin-top: 15px;
  }
}
.interestTable_c_title_wrap {
  @include flex_between;
  // border-bottom: 2px solid $b3;
  padding-bottom: 8px;
}
.interestTable_c_title {
  @include flex_start;
  gap: 8px;
  p {
    @include tblTit;
  }
}

.interestTable_c_title p:nth-child(2) {
  color: $b3;
}
.interestTable_c td {
  padding: 0 !important;
}
.interestTable_c tr td:nth-child(1),
.interestTable_c tr td:nth-child(3) {
  @include fw-7;
}
.interestTable_c tr td span {
  @include fs-5;
}
.interestTable_c_tbody tr {
  height: 24px !important;
}
.interestTable_c_tbody td {
  height: 30px !important;
  border: none !important;
}
</style>

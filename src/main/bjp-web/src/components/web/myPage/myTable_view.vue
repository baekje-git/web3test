<template>
  <div class="">
    <div class="myTable_view_title_wrap">
      <div class="myTable_view_title">
        <p>MY 활동</p>
      </div>
      <div class="">
        <BtnComp_default_small
          class="btn-line default"
          btn_defaultLabel="+ more"
          @click="router.push('/custMonth')"
        />
      </div>
    </div>

    <div class="myTable_view">
      <q-markup-table separator="none" flat dense>
        <tbody class="myTable_view_tbody">
          <tr>
            <td style="width: 90px">매출원장</td>
            <td class="myTable_td_wrap">
              <div class="">
                <p>
                  <span>잔고</span>
                  <span>{{ util.getNumberFormat(data.balanceAAmt) }}</span
                  >원
                </p>
              </div>
              <div class="">
                <p>
                  <span>순판매금액</span>
                  <span>{{ util.getNumberFormat(data.totalAmt) }} </span>
                  원
                </p>
              </div>
            </td>
          </tr>
          <tr>
            <td>주문건수</td>
            <td class="myTable_td_wrap">
              <div class="">
                <p>
                  <span>전체</span>
                  <span>{{ util.getNumberFormat(data.saleCnt) }}</span
                  >건
                </p>
              </div>
              <div class="">
                <p>
                  <span>주문금액</span>
                  <span>{{ util.getNumberFormat(data.saleAmt) }}</span
                  >원
                </p>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div class="tooltip_wrap">
                반품현황
                <q-badge
                  rounded
                  style="position: absolute; top: -1px; right: -7px"
                >
                  ?
                  <q-tooltip anchor="top right" self="top left">
                    <p style="font-size: 14px">
                      반품현황은 전월부터 현재까지의 반품진행 현황 통계가
                      표시됩니다.
                    </p>
                  </q-tooltip>
                </q-badge>
              </div>
            </td>
            <td class="myTable_td_wrap">
              <div class="">
                <p>
                  <span>미등록</span>
                  <span>{{ util.getNumberFormat(data.notReg) }}</span
                  >건
                </p>
              </div>
              <div class="">
                <p>
                  <span>담당자</span>
                  <span>{{ util.getNumberFormat(data.charge) }}</span
                  >건
                </p>
              </div>
              <div class="">
                <p>
                  <span>처리중</span>
                  <span>{{ util.getNumberFormat(data.processing) }}</span
                  >건
                </p>
              </div>
              <div class="">
                <p>
                  <span>검수완료</span>
                  <span>{{ util.getNumberFormat(data.complete) }}</span
                  >건
                </p>
              </div>
            </td>
          </tr>
          <tr>
            <td>결제현황</td>
            <td class="myTable_td_wrap">
              <div class="">
                <p>
                  <span>결제건수</span>
                  <span>{{ util.getNumberFormat(data.pmntCnt) }}</span
                  >건
                </p>
              </div>
              <div class="">
                <p>
                  <span>금액</span>
                  <span>{{ util.getNumberFormat(data.pmntAmt) }}</span
                  >원
                </p>
              </div>
            </td>
          </tr>
          <tr>
            <td>입금현황</td>
            <td class="myTable_td_wrap">
              <div class="">
                <p>
                  <span>입금건수</span>
                  <span>{{ util.getNumberFormat(data.dpstCnt) }}</span
                  >건
                </p>
              </div>
              <div class="">
                <p>
                  <span>금액</span>
                  <span>{{ util.getNumberFormat(data.payAmtSum) }}</span
                  >원
                </p>
              </div>
              <div class="">
                <p>
                  <span>수금할인</span>
                  <!-- <span
                    style="
                      display: inline-flex;
                      width: 35px;
                      max-width: 35px;
                      overflow: hidden;
                      text-overflow: ellipsis;
                    "> -->
                  <span> {{ util.getNumberFormat(data.paybackAmtSum) }}</span
                  >원
                </p>
              </div>
            </td>
          </tr>
        </tbody>
      </q-markup-table>
    </div>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { defineProps } from "vue";
import { useRouter } from "vue-router";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";

const router = useRouter();
const userInfo = util.getUserData();
const props = defineProps({
  btn_defaultLink: {
    type: String,
    default: "",
  },
});

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  brchCd: userInfo.BRCH_CD,
  startDt: "",
  startDt3Month: "",
  endDt: "",
});

const data = ref({
  totalAmt: "",
  saleAmt: "",
  saleCnt: "",
  balanceAAmt: "",
  notReg: "",
  processing: "",
  complete: "",
  charge: "",
  pmntCnt: "",
  pmntAmt: "",
  dpstCnt: "",
  payAmtSum: "",
  paybackAmtSum: "",
});

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/myPage/myActivity", {
    params: searchParam.value,
  });

  data.value.totalAmt = res.TOTAL_AMT;
  data.value.saleAmt = res.SALE_AMT;
  data.value.saleCnt = res.SALE_CNT;
  data.value.balanceAAmt = res.BALANCE_A_AMT;
  data.value.notReg = res.NOT_REG;
  data.value.processing = res.PROCESSING;
  data.value.complete = res.COMPLETE;
  data.value.charge = res.CHARGE;
  data.value.pmntCnt = res.PMNT_CNT;
  data.value.pmntAmt = res.PMNT_AMT;
  data.value.dpstCnt = res.DPST_CNT;
  data.value.payAmtSum = res.PAY_AMT_SUM;
  data.value.paybackAmtSum = res.PAYBACK_AMT_SUM;
}

function getYYYYMMDD(date) {
  return (
    date.getFullYear() +
    (date.getMonth() + 1).toString().padStart(2, "0") +
    date.getDate().toString().padStart(2, "0")
  );
}

onMounted(async () => {
  const now = new Date();
  const year = now.getFullYear();
  const month = now.getMonth() + 1;
  const strtDt = new Date(year, month - 1, 1);
  const startDt3Month = new Date(year, month - 2, 1);
  const endDt = new Date(year, month, 0);

  searchParam.value.startDt = getYYYYMMDD(strtDt);
  searchParam.value.startDt3Month = getYYYYMMDD(startDt3Month);
  searchParam.value.endDt = getYYYYMMDD(endDt);

  await fetchData();
});
</script>

<style lang="scss" scoped>
.myTable_view_title_wrap {
  @include flex_between;
  border-bottom: 1px solid $gray-9;
  padding-bottom: 6px;
}
.myTable_view_title {
  @include flex_start;
  gap: 8px;
}
.myTable_view_title p {
  font-size: 1.18rem;
  @include fw-6;
}
.myTable_view tr td:nth-child(1) {
  @include fw-7;
  // padding: 0 0 0 4px;
}
.myTable_view tr td span {
  @include fs-5;
}
.myTable_view_tbody tr {
  height: 36px !important;
}
.myTable_view_tbody td {
  border-bottom: 1px solid $td-line;
}

.myTable_td_wrap {
  @include flex_start;
  gap: 10px;
  height: 36px !important;
}
.myTable_td_wrap span:nth-child(2) {
  @include fw-7;
  margin-left: 5px;
}
</style>

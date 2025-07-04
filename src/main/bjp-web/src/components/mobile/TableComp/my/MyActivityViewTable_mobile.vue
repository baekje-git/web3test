<template>
  <div>
    <ExpandLayout_mobile
      v-model:expanded_model="expanded_model"
      expand_title="My활동"
      expand_btn="+ more"
      :expand_btnWidth="68"
      @expanded_btnClick="expanded_btn"
    >
      <DialogLayout_mobile
        dialog_title="월간조회"
        v-model:dialog_model_prop="dialog_MyPage"
      >
        <div class="">
          <MyActivityComp_mobile />
        </div>
      </DialogLayout_mobile>

      <!-- <div class="table_list_wrap">
        <div class="table_list" v-for="(items, i) in row" :key="i">
          <div class="table_list_label">
            <ul>
              <li>{{ items.Label }}</li>
              <li>{{ items.mon }}</li>
            </ul>
          </div>
          <div class="table_list_data">
            <ul v-for="(subItems, j) in items.subData" :key="j">
              <li>{{ subItems.subLabel }}</li>
              <li>
                <span>
                  {{ subItems.subData }}
                  <template v-if="typeof subItems.subData === 'string'">
                    건
                  </template>
                  <template v-else> 원 </template>
                </span>
              </li>
            </ul>
          </div>
        </div>
      </div> -->

      <div class="table_list_wrap">
        <div class="table_list">
          <div class="table_list_label">
            <ul>
              <li>매출원장</li>
              <!-- <li>2월</li> -->
            </ul>
          </div>
          <div class="table_list_data">
            <ul>
              <li>잔고</li>
              <li>
                <span>{{ util.getNumberFormat(data.balanceAAmt) }}</span>
                원
              </li>
            </ul>
            <ul>
              <li>순판매금액</li>
              <li>
                <span>{{ util.getNumberFormat(data.totalAmt) }}</span>
                원
              </li>
            </ul>
          </div>
        </div>
        <div class="table_list">
          <div class="table_list_label">
            <ul>
              <li>주문건수</li>
              <!-- <li>2월</li> -->
            </ul>
          </div>
          <div class="table_list_data">
            <ul>
              <li>전체</li>
              <li>
                <span>{{ util.getNumberFormat(data.saleCnt) }}</span>
                건
              </li>
            </ul>
            <ul>
              <li>주문금액</li>
              <li>
                <span>{{ util.getNumberFormat(data.saleAmt) }}</span>
                원
              </li>
            </ul>
          </div>
        </div>
        <div class="table_list">
          <div class="table_list_label">
            <ul>
              <li>반품현황</li>
              <!-- <li>3개월</li> -->
            </ul>
          </div>
          <div class="table_list_data">
            <ul>
              <li>미등록</li>
              <li>
                <span>{{ util.getNumberFormat(data.notReg) }}</span>
                건
              </li>
            </ul>
            <ul>
              <li>담당자</li>
              <li>
                <span>{{ util.getNumberFormat(data.charge) }}</span>
                건
              </li>
            </ul>
            <ul>
              <li>처리 중</li>
              <li>
                <span>{{ util.getNumberFormat(data.processing) }}</span>
                건
              </li>
            </ul>
            <ul>
              <li>검수완료</li>
              <li>
                <span>{{ util.getNumberFormat(data.complete) }}</span>
                건
              </li>
            </ul>
          </div>
        </div>
        <div class="table_list">
          <div class="table_list_label">
            <ul>
              <li>결제현황</li>
              <!-- <li>2월</li> -->
            </ul>
          </div>
          <div class="table_list_data">
            <ul>
              <li>결제건수</li>
              <li>
                <span>{{ util.getNumberFormat(data.pmntCnt) }}</span>
                건
              </li>
            </ul>
            <ul>
              <li>금액</li>
              <li>
                <span>{{ util.getNumberFormat(data.pmntAmt) }}</span>
                원
              </li>
            </ul>
          </div>
        </div>
        <div class="table_list">
          <div class="table_list_label">
            <ul>
              <li>입금현황</li>
              <!-- <li>2월</li> -->
            </ul>
          </div>
          <div class="table_list_data">
            <ul>
              <li>입금건수</li>
              <li>
                <span>{{ util.getNumberFormat(data.dpstCnt) }}</span>
                건
              </li>
            </ul>
            <ul>
              <li>금액</li>
              <li>
                <span>{{ util.getNumberFormat(data.payAmtSum) }}</span>
                원
              </li>
            </ul>
            <ul>
              <li>수금할인</li>
              <li>
                <span>{{ util.getNumberFormat(data.paybackAmtSum) }}</span>
                원
              </li>
            </ul>
          </div>
        </div>
      </div>
    </ExpandLayout_mobile>
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import ExpandLayout_mobile from "src/layouts/mobile/ExpandLayouts/ExpandLayout_mobile.vue";
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import MyActivityComp_mobile from "src/components/mobile/DialogComp/4.myDialogComp/MyActivityComp_mobile.vue";

const expanded_model = ref(true);
const userInfo = util.getUserData();

const dialog_MyPage = ref(false);
const expanded_btn = () => {
  dialog_MyPage.value = true;
};

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
.table_list_wrap {
  // padding: 0 8px;
  margin-top: 10px;
  // background-color: #eeeeee;
  // border-radius: 5px;
  border-bottom: 1px solid $td-line;
  .table_list {
    display: flex;
    padding: 10px 0;
    // @include border-b;
    border-bottom: 1px solid $td-line;
    li {
      @include fs-5;
      color: #111;
    }
    .table_list_label {
      width: 50%;
      ul {
        @include flex_start;
        li:nth-child(1) {
          @include fw-7;
          width: 40%;
        }
        li:nth-child(2) {
          width: 60%;
        }
      }
    }
    .table_list_data {
      width: 50%;
      ul {
        @include flex_end;
        margin-bottom: 4px;
        gap: 14px;
        li {
          @include flex_end;
        }
        li:nth-child(1) {
          width: 50%;
        }
        li:nth-child(2) {
          @include fw-7;
          width: 65%;
          gap: 4px;
          span {
            @include fs-5;
            white-space: normal;
            word-break: break-all;
          }
        }
      }
      ul:last-child {
        margin-bottom: 0;
      }
    }
  }
  .table_list:last-child {
    border: none;
  }
}
</style>

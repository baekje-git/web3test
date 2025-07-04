<template>
  <div class="paymentTable_2a">
    <div class="q-table_wrap">
      <div class="q-table_header_wrap">
        <div class="q-table_header_title">
          <p>결제금액 입력</p>
        </div>
      </div>

      <div class="payAmount_wrap">
        <q-option-group
          v-model="payAmount"
          :options="payAmountOption"
          class="payAmount_group"
          color="blue-10"
          size="xs"
          @update:model-value="onChangePayAmount"
        >
          <template v-slot:label="opt">
            <div class="flex_between payAmount_label">
              <div class="">
                {{ opt.label }}
              </div>
              <p>{{ opt.pay }} 원</p>
            </div>
          </template>
        </q-option-group>
      </div>

      <div class="payTotal_wrap">
        <span class="payTotal_text">결제금액</span>
        <div class="payTotal">
          <!-- <p>{{ selectedPayAmount }}</p> -->
          <q-input
            v-model="amt"
            input-class="payTotal_input"
            borderless
            maxlength="30"
            dense
            reverse-fill-mask
            mask="###,###,###,###,###,###"
            @update:model-value="onUpdate"
          />
          <span>원</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, computed, onMounted, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

const userInfo = util.getUserData();
const bus = inject("bus");

// const balanceBAmt = computed(() => paymentData.value.balanceBAmt);
// const within15 = computed(() => paymentData.value.within15);
// const beforMonth = computed(() => paymentData.value.beforMonth);
// const pmntReqAmt = computed(() => paymentData.value.pmntReqAmt);

const balanceBAmt = ref("0");
const within15 = ref("0");
const beforMonth = ref("0");
const pmntReqAmt = ref("0");

const payAmount = ref();
const payAmountOption = ref([
  {
    value: "1",
    label: "현재 잔고",
    pay: balanceBAmt,
  },
  {
    value: "2",
    label: "당월15일 잔고",
    pay: within15,
  },
  {
    value: "3",
    label: "전월말 잔고",
    pay: beforMonth,
  },
  {
    value: "4",
    label: "결제 요청금액",
    pay: pmntReqAmt,
  },
]);

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  brchCd: userInfo.BRCH_CD,
});

const paymentData = ref({
  balanceBAmt: 0,
  balanceBM1: 0,
  balanceBM2: 0,
  serviceNo: "",
  brServiceNo: "",
  brchNm: "",
  balanceYm: "",
  etaxbillYn: "",
  within15: 0,
  beforMonth: 0,
  pmntReqAmt: 0,
});

const amt = ref(null);

//---------------------------- Function ------------------------------//

async function onChangePayAmount(val) {
  if (val === "1") {
    amt.value = paymentData.value.balanceBAmt;
  } else if (val === "2") {
    amt.value = paymentData.value.within15;
  } else if (val === "3") {
    amt.value = paymentData.value.beforMonth;
  } else if (val === "4") {
    amt.value = paymentData.value.pmntReqAmt;
  }

  await sendAmount(util.getNumberFormat(amt.value));
}

// 결제금액 조회
async function fetchData() {
  const res = await api.get("/payment/paymentAmount", {
    params: searchParam.value,
  });

  if (res.length > 0) {
    paymentData.value.balanceBAmt = util.getNumberFormat(res[0].BALANCE_B_AMT);
    paymentData.value.balanceBM1 = res[0].BALANCE_B_M1;
    paymentData.value.balanceBM2 = res[0].BALANCE_B_M2;
    paymentData.value.serviceNo = res[0].SERVICE_NO;
    paymentData.value.brServiceNo = res[0].BR_SERVICE_NO;
    paymentData.value.brchNm = res[0].BRCH_NM;
    paymentData.value.balanceYm = res[0].BALANCE_YM;
    paymentData.value.etaxbillYn = res[0].ETAXBILL_YN;
    paymentData.value.within15 = util.getNumberFormat(res[0].WITHIN_15);
    paymentData.value.beforMonth = util.getNumberFormat(res[0].BEFOR_MONTH);
    paymentData.value.pmntReqAmt = util.getNumberFormat(res[0].PMNT_REQ_AMT);

    balanceBAmt.value = paymentData.value.balanceBAmt;
    within15.value = paymentData.value.within15;
    beforMonth.value = paymentData.value.beforMonth;
    pmntReqAmt.value = paymentData.value.pmntReqAmt;
  }
}

// 카드결제 그리드로 결제금액 전달
async function sendAmount(amt) {
  // const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
  // const rplcAmt = Number(amt.replace(match, ""));

  bus.emit("pmntAmt", amt);
}

// // 결제금액에 숫자 외 입력 방지
// async function updateAmt(val) {
//   const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
//   const newValue = val.target.value.replace(match, "");

//   if (!isNaN(newValue)) {
//     amt.value = util.getNumberFormat(newValue);
//     await sendAmount(amt.value);
//   } else {
//     amt.value = undefined;
//     val = null;
//   }
// }

const onUpdate = async (val) => {
  let newValue = val.replace(/,/g, "");
  newValue = newValue.replace(/^0+(?!$)/, "");
  newValue = parseInt(newValue);

  if (!isNaN(newValue)) {
    amt.value = newValue;
    await sendAmount(amt.value);
  } else {
    amt.value = undefined;
    val = null;
  }

  // console.log(amt.value);
  // await sendAmount(amt.value);
};

bus.on("initPmntAmt", () => {
  amt.value = null;
});

onMounted(async () => {
  await fetchData();
});
</script>

<style lang="scss" scoped>
.flex_between {
  @include flex_between;
}

.paymentTable_2a {
  // height: 350px;
  height: 310px;
  @media screen and (max-width: 830px) {
    height: auto;
  }
}
.payAmount_wrap {
  // margin-top: 20px;
  // padding: 47px 0;
  // padding: 8px 0 47px;
  width: 100%;
  padding: 8px 0 70px;
  border-bottom: 1px solid $line-300;
}
.payAmount_group {
  width: 100%;
  @include flex_start;
  align-items: flex-start;
  flex-direction: column;
  gap: 28px;
  @media screen and (max-width: 830px) {
    flex-direction: row;
    flex-wrap: wrap;
  }
}
.q-option-group > *,
.q-option-group > * > *,
.q-option-group > * > * > *:nth-last-child(1) {
  width: 100%;
}
@media screen and (max-width: 830px) {
  .q-option-group > * {
    width: calc(50% - 28px);
  }
}
.payAmount_label {
  // width: clamp(471px, 100%, 595px);
  // width: 595px;
}
.payAmount_label div {
  @include fs-5;
  // margin-bottom: 45px;
}
.payAmount_label div:nth-child(1) {
  color: #666666;
  margin-left: 5px;
}
.payAmount_label div:nth-child(2) {
  color: #000;
}

.payTotal_wrap {
  @include flex_between;
  padding-top: 15px;
}
.payTotal_text {
  @include fs-3;
  @include fw-7;
}

.payTotal {
  @include flex_center;
  gap: 8px;
}
.payTotal p {
  @include fs-3;
  @include fw-7;
  padding: 0 7px;
  color: $b3;
  min-width: 160px;
  text-align: right;
  border: 1px solid;
  border-radius: 5px;
}
</style>

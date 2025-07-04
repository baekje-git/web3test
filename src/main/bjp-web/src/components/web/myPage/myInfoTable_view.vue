<template>
  <div class="">
    <div class="myInfoTable_view_title_wrap">
      <div class="myInfoTable_view_title">
        <p>기본정보</p>
      </div>
      <div class="myInfo_btn_wrap">
        <DialogComp_a>
          <BtnComp_default_small
            class="btn-line default"
            btn_defaultLabel="SMS 전송"
            @click="onClickSms"
          />
        </DialogComp_a>
        <TaxInvoicePupup class="Btn_default_wrap btn-line default">
          <q-btn
            class="btn_default_small"
            size="xs"
            label="세금계산서"
            dense
            @click="onClickTaxBill"
          >
            <q-badge v-if="isVisibleDot" color="red" rounded floating />
          </q-btn>

          <!-- <BtnComp_default btnLabel="세금계산서" @click="onClickTaxBill">
            <q-badge v-if="isVisibleDot" color="red" rounded floating />
          </BtnComp_default> -->
        </TaxInvoicePupup>
        <MemberInfoPopup>
          <BtnComp_default_small
            class="btn-line default"
            btn_defaultLabel="회원정보수정"
            @click="onClickMemberInfo"
          />
        </MemberInfoPopup>
      </div>
    </div>

    <div class="myInfoTable_view">
      <q-markup-table separator="noen" flat dense>
        <tbody class="myInfoTable_view_tbody">
          <tr>
            <td style="width: 90px">거래처명</td>
            <td class="myInfoTable_td_wrap">{{ userInfo.CUST_NM }}</td>
          </tr>
          <tr>
            <td>대표약사</td>
            <td class="myInfoTable_td_wrap">{{ userInfo.CEO_NM }}님</td>
          </tr>
          <tr>
            <td>영업지점</td>
            <td class="myInfoTable_td_wrap">{{ data.brchNm }}</td>
          </tr>
          <tr>
            <td>지점 연락처</td>
            <td class="myInfoTable_td_wrap">{{ data.brchTelNo }}</td>
          </tr>
          <tr>
            <td>영업사원</td>
            <td class="myInfoTable_td_wrap">
              {{ `${data.empNm} ${data.empHpNo}` }}
            </td>
          </tr>
          <tr>
            <td>배송기사</td>
            <td class="myInfoTable_td_wrap">
              {{ `${data.driverNm} ${data.driverHpPhone}` }}
            </td>
          </tr>
          <tr>
            <td>마약류 식별번호</td>
            <td class="myInfoTable_td_wrap">{{ userInfo.DRUG_CD }}</td>
          </tr>
        </tbody>
      </q-markup-table>
    </div>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted, inject, computed } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_default_b from "src/components/web/common/BtnComp_default_b.vue";
import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
import BtnComp_purple from "src/components/web/common/BtnComp_purple.vue";
import DialogComp_a from "../common/SmsPopupComp.vue";
import TaxInvoicePupup from "src/components/web/myPage/TaxInvoicePopup.vue";
import MemberInfoPopup from "./MemberInfoPopup.vue";

const userInfo = util.getUserData();
const bus = inject("bus");

const searchParam = ref({
  custCd: userInfo.CUST_CD,
});

const data = ref({
  brchCd: "",
  brchNm: "",
  brchTelNo: "",
  empHpNo: "",
  empId: "",
  empNm: "",
  driverNm: "",
  driverHpPhone: "",
});

const taxBillData = ref({
  cnt: 0,
  pubDt: "",
  today: "",
  yn: "",
});

const isVisibleDot = computed(() => {
  const cnt = taxBillData.value.cnt;

  if (
    cnt > 0 &&
    taxBillData.value.pubDt !== "" &&
    taxBillData.value.yn !== "Y"
  ) {
    const pubDt = moment(taxBillData.value.pubDt);
    const today = moment(taxBillData.value.today);
    const day = today.diff(pubDt, "days");

    if (day <= 7) {
      return true;
    } else {
      return false;
    }
  }

  return false;
});

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/myPage/custInfo", { params: searchParam.value });

  data.value.brchCd = res.BRCH_CD;
  data.value.brchNm = res.BRCH_NM;
  data.value.brchTelNo = res.BRCH_TEL_NO;
  data.value.empHpNo = res.EMP_HP_NO;
  data.value.empId = res.EMP_ID;
  data.value.empNm = res.EMP_NM;
  data.value.driverNm = res.DRIVER_NM;
  data.value.driverHpPhone = res.DRIVER_HP_PHONE;

  const taxBillRes = await api.get("/myPage/taxBillCount", {
    params: searchParam.value,
  });

  taxBillData.value.cnt = taxBillRes.CNT;
  taxBillData.value.pubDt = taxBillRes.PUB_DT;
  taxBillData.value.today = taxBillRes.TODAY;
  taxBillData.value.yn = taxBillRes.WEB_READ_YN;
}

function onClickSms() {
  bus.emit("sendSms");
}

function onClickMemberInfo() {
  bus.emit("memberInfo");
}

async function onClickTaxBill() {
  if (taxBillData.value.yn == "N") {
    try {
      await api.post("/myPage/taxBillCountUp", searchParam.value);

      await fetchData();
    } catch (error) {
      error.response.data;
    }
  }
  bus.emit("texBillInfo");
}

onMounted(async () => {
  await fetchData();
});
</script>

<style lang="scss" scoped>
.myInfoTable_view_title_wrap {
  @include flex_between;
  // border-bottom: 1px solid $gray-9;
  padding-bottom: 6px;
}
.myInfoTable_view_title {
  @include flex_start;
  gap: 8px;
}
.myInfoTable_view_title p {
  font-size: 1.18rem;
  @include fw-6;
}
.myInfo_btn_wrap {
  @include flex_end;
  // gap: 8px;
  > * + *:has(div) {
    margin-left: 4px;
  }
}
.myInfoTable_view tr td:nth-child(1) {
  @include fw-7;
  // padding: 0;
}
.myInfoTable_view_tbody tr {
  height: 36px !important;
}
.myInfoTable_view_tbody td {
  padding: 0 8px;
  border-bottom: 1px solid $td-line;
}
</style>

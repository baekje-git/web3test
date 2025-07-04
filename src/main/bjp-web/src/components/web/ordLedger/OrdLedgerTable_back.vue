<template>
  <div class="">
    <q-table
      class="salesTable_a"
      :rows="rows"
      :columns="columns"
      row-key="SEQ"
      table-header-class="table_header"
      :hide-bottom="isHideBottom"
      dense
      :rows-per-page-options="[0]"
      :loading="loading"
      :selected="selected"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="flex_between">
            <div class="q-table_header_wrap">
              <div class="q-table_header_title">
                <p>매출구분</p>
              </div>
              <div class="q-table_header_title_sub">
                <div class="q-table_radio_wrap">
                  <!-- <RadioComp_a
                  v-model="orderRadio"
                  :radio_options="orderRadioOptions"
                ></RadioComp_a> -->
                  <q-checkbox
                    v-model="searchParam.searchAll"
                    color="blue-10"
                    size="xs"
                    inline
                    dense
                    true-value="Y"
                    false-value="N"
                    label="전체"
                    @update:model-value="onClicksearchParamCheck('all')"
                  />
                  <q-checkbox
                    v-model="searchParam.salesYn"
                    color="blue-10"
                    size="xs"
                    style="margin-left: 8px"
                    inline
                    dense
                    true-value="Y"
                    false-value="N"
                    label="매출"
                    @update:model-value="onClicksearchParamCheck"
                  />
                  <q-checkbox
                    v-model="searchParam.returnYn"
                    color="blue-10"
                    size="xs"
                    style="margin-left: 8px"
                    inline
                    dense
                    true-value="Y"
                    false-value="N"
                    label="반품"
                    @update:model-value="onClicksearchParamCheck"
                  />
                  <q-checkbox
                    v-model="searchParam.paymentYn"
                    color="blue-10"
                    size="xs"
                    style="margin-left: 8px"
                    inline
                    dense
                    true-value="Y"
                    false-value="N"
                    label="입금"
                    @update:model-value="onClicksearchParamCheck"
                  />
                  <q-checkbox
                    v-model="searchParam.compensationYn"
                    color="blue-10"
                    size="xs"
                    style="margin-left: 8px"
                    inline
                    dense
                    true-value="Y"
                    false-value="N"
                    label="약가보상"
                    @update:model-value="onClicksearchParamCheck"
                  />
                </div>
                <div class="q-table_calrendar_wrap">
                  <!-- <DatePickerComp_b /> -->
                  <div class="flex_between">
                    <div class="DatePickerComp_b_wrap">
                      <div class="date_input_wrap">
                        <!-- <p>
                          {{ date1 }}
                        </p> -->
                        <q-input
                          input-class="input_default"
                          v-model="searchParam.startDt"
                          borderless
                          dense
                          @blur="validDate"
                          @focus="(input) => input.target.select()"
                          mask="####-##-##"
                        />
                      </div>
                      <div class="date_btn_wrap">
                        <q-btn class="calendar_icon" unelevated dense>
                          <img
                            src="assets/icons/calendar-days-solid.svg"
                            alt="calendar"
                          />
                          <q-popup-proxy
                            @before-show="updateProxy1"
                            cover
                            transition-show="scale"
                            transition-hide="scale"
                          >
                            <q-date
                              v-model="proxyDate1"
                              mask="YYYY-MM-DD"
                              @update:model-value="updateStartDt"
                            >
                              <div
                                class="row items-center justify-end q-gutter-sm"
                              >
                                <q-btn
                                  label="Cancel"
                                  color="primary"
                                  flat
                                  v-close-popup
                                />
                                <q-btn
                                  label="OK"
                                  color="primary"
                                  flat
                                  @click="save1"
                                  v-close-popup
                                />
                              </div>
                            </q-date>
                          </q-popup-proxy>
                        </q-btn>
                      </div>
                    </div>
                    <p>~</p>
                    <div class="DatePickerComp_b_wrap">
                      <div class="date_input_wrap">
                        <!-- <p>
                          {{ date2 }}
                        </p> -->
                        <q-input
                          input-class="input_default"
                          v-model="searchParam.endDt"
                          borderless
                          dense
                          @blur="validDate"
                          @focus="(input) => input.target.select()"
                          mask="####-##-##"
                        />
                      </div>
                      <div class="date_btn_wrap">
                        <q-btn class="calendar_icon" unelevated dense>
                          <img
                            src="assets/icons/calendar-days-solid.svg"
                            alt="calendar"
                          />
                          <q-popup-proxy
                            @before-show="updateProxy2"
                            cover
                            transition-show="scale"
                            transition-hide="scale"
                          >
                            <q-date
                              v-model="proxyDate2"
                              mask="YYYY-MM-DD"
                              @update:model-value="updateEndDt"
                            >
                              <div
                                class="row items-center justify-end q-gutter-sm"
                              >
                                <q-btn
                                  label="Cancel"
                                  color="primary"
                                  flat
                                  v-close-popup
                                />
                                <q-btn
                                  label="OK"
                                  color="primary"
                                  flat
                                  @click="save2"
                                  v-close-popup
                                />
                              </div>
                            </q-date>
                          </q-popup-proxy>
                        </q-btn>
                      </div>
                    </div>
                  </div>
                  <BtnComp_default btnLabel="검색" @click="onClickSearch" />
                </div>
                <div class="q-table_btn_option_wrap">
                  <!-- <BtnComp_options /> -->
                  <div class="Btn_options_wrap">
                    <q-btn
                      :class="periodClassA"
                      label="당월"
                      dense
                      @click="onClickPeriod(0)"
                    />
                    <q-btn
                      :class="periodClassB"
                      label="전월"
                      dense
                      @click="onClickPeriod(1)"
                    />
                    <q-btn
                      :class="periodClassC"
                      label="3개월"
                      dense
                      @click="onClickPeriod(3)"
                    />
                    <q-btn
                      :class="periodClassD"
                      label="6개월"
                      dense
                      @click="onClickPeriod(6)"
                    />
                    <q-btn
                      :class="periodClassE"
                      label="1년"
                      dense
                      @click="onClickPeriod(12)"
                    />
                    <q-btn
                      :class="periodClassF"
                      label="2년"
                      dense
                      @click="onClickPeriod(24)"
                    />
                    <q-btn
                      :class="periodClassG"
                      label="3년"
                      dense
                      @click="onClickPeriod(36)"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="q-table_btn_export_wrap">
              <BtnComp_purple btn_purpleLabel="출력" @click="exportPrint" />
              <BtnComp_purple btn_purpleLabel="엑셀" @click="exportTable" />
            </div>
          </div>
          <div class="q-table_header_sub_wrap">
            <p>
              <span>현재잔고</span>
              <span>{{ util.getNumberFormat(cashBlnc ?? 0) }}원</span>
            </p>
            <p>
              <span>판매금액</span>
              <span>{{ util.getNumberFormat(salAmt ?? 0) }}원</span>
            </p>
            <p>
              <span>입금액</span>
              <span>{{ util.getNumberFormat(dpstAmt ?? 0) }}원</span>
            </p>
          </div>
        </div>
      </template>

      <template v-slot:top-row v-if="rows.length === 0 && searched">
        <q-tr class="no-date">
          <q-td colspan="100%">
            <span>검색된 결과가 없습니다.</span>
          </q-td>
        </q-tr>
      </template>

      <template v-slot:body="props">
        <!-- <q-tr
          :props="props"
          @click="props.expand = !props.expand"
          :icon="props.expand ? 'remove' : 'add'"
        > -->
        <q-tr :props="props">
          <q-td key="일자" :props="props" @click="onClickRow(props)">
            {{
              moment(props.row.APRV_DT, "YYYY-MM-DD HH:mm:ss").format(
                "YYYY-MM-DD"
              )
            }}
          </q-td>
          <q-td
            key="승인번호"
            :props="props"
            @click="onClickRow(props)"
            style="cursor: pointer"
          >
            {{ props.row.APRV_NO }}
          </q-td>
          <q-td
            key="종류"
            :props="props"
            @click="onClickRow(props)"
            style="cursor: pointer"
          >
            {{ props.row.GUBUN_NM }}
          </q-td>
          <q-td
            key="광역구분"
            :props="props"
            @click="onClickRow(props)"
            style="cursor: pointer"
          >
            {{ props.row.DLV_GUBUN }}
          </q-td>
          <q-td
            key="구분"
            :props="props"
            @click="onClickRow(props)"
            style="cursor: pointer"
          >
            {{ props.row.BACK_GB_NM }}
          </q-td>
          <q-td
            key="주문서종류"
            :props="props"
            @click="onClickRow(props)"
            style="cursor: pointer"
          >
            {{ props.row.ORD_TYPE_NM }}
          </q-td>
          <q-td
            key="적요"
            :props="props"
            @click="onClickRow(props)"
            style="text-align: left; cursor: pointer"
          >
            <div class="q-table_item" style="margin-left: 8px">
              <div class="q-table_badge_wrap">
                <q-badge class="q-table_badge_red">
                  {{ props.row.MED_NM }}</q-badge
                >
                <q-badge class="q-table_badge_blue">{{
                  props.row.PAY_ITEM_NM
                }}</q-badge>
              </div>
              <div class="q-table_itemTitle ellipsis" style="max-width: 380px">
                {{ props.row.ITEM_NM_UNIT }}
              </div>
            </div>
          </q-td>
          <q-td
            key="품목수"
            :props="props"
            @click="onClickRow(props)"
            style="cursor: pointer"
          >
            {{ props.row.ITEM_CNT_TXT }}
          </q-td>
          <q-td
            key="판매금액"
            :props="props"
            @click="onClickRow(props)"
            style="cursor: pointer; text-align: right"
          >
            <p v-if="props.row.TOTAL_AMT < 0" style="color: red">
              {{ util.getNumberFormat(props.row.TOTAL_AMT) }}
            </p>
            <p v-if="props.row.TOTAL_AMT >= 0">
              {{ util.getNumberFormat(props.row.TOTAL_AMT) }}
            </p>
          </q-td>
          <q-td
            key="입금액"
            :props="props"
            @click="onClickRow(props)"
            style="cursor: pointer; text-align: right"
          >
            <p v-if="props.row.PAY_AMT < 0" style="color: red">
              {{ util.getNumberFormat(props.row.PAY_AMT) }}
            </p>
            <p v-if="props.row.PAY_AMT >= 0">
              {{ util.getNumberFormat(props.row.PAY_AMT) }}
            </p>
          </q-td>
          <q-td
            key="잔고"
            :props="props"
            @click="onClickRow(props)"
            style="cursor: pointer; text-align: right"
          >
            <p v-if="props.row.BALANCE_A_AMT < 0" style="color: red">
              {{ util.getNumberFormat(props.row.BALANCE_A_AMT) }}
            </p>
            <p v-if="props.row.BALANCE_A_AMT >= 0">
              {{ util.getNumberFormat(props.row.BALANCE_A_AMT) }}
            </p>
          </q-td>
          <q-td key="메모" :props="props" style="padding: 0 20px; width: 30px">
            <MemoComp_default
              :is-visible="false"
              :is-disable="true"
              :memo="props.row.MEMO"
            >
              <img
                v-if="props.row.MEMO !== ''"
                src="assets/icons/file-lines-solid.svg"
                alt="file"
                style="cursor: pointer"
              />
            </MemoComp_default>
          </q-td>
          <q-td key="인수증" :props="props">
            <BtnComp_default_small
              v-if="
                isShowCrtfBtn && props.row.INVOICE_NO && props.row.GUBUN !== '2'
              "
              btn_defaultLabel="보기"
              @click="onClickCrtfBtn(props.row)"
            />
          </q-td>
          <q-td key="잔고확인" :props="props">
            <!-- <BtnComp_default_small
              btn_defaultLabel="서명"
              @click="onClickSignWeb(props.row)"
            /> -->
            <DialogComp_signature :data="props.row">
              <BtnComp_active_white_small
                v-if="
                  isShowCrtfBtn &&
                  props.row.SIGN_YN === 'N' &&
                  userInfo.USER_GB_CD !== '30'
                "
                btn_activeWhiteLabel="서명"
              />
            </DialogComp_signature>
            <DialogComp_signature_view :data="props.row">
              <BtnComp_active_small
                v-if="props.row.SIGN_YN === 'Y'"
                btn_activeLabel="보기"
              />
            </DialogComp_signature_view>
          </q-td>
        </q-tr>

        <!-- <q-tr
          v-if="props.row.APRV_DT !== rows[props.rowIndex + 1].APRV_DT ?? ''"
          :props="props"
        > -->
        <q-tr
          v-if="isShowSumRow(props)"
          :props="props"
          style="padding: 0; background-color: rgba(31, 63, 130, 0.1)"
        >
          <!-- <q-td
            colspan="100%"
            style="padding: 0; background-color: rgba(31, 63, 130, 0.1)"
          > -->
          <!-- <table class="full-width">
              <tbody>
                <tr> -->
          <q-td style="text-align: center; font-weight: 700" colspan="8">
            일 합계
          </q-td>

          <q-td key="판매금액" :props="props" style="text-align: right">
            <p v-if="totalAmtSum(props) < 0" style="color: red">
              {{ util.getNumberFormat(totalAmtSum(props)) }}
            </p>
            <p v-if="totalAmtSum(props) >= 0">
              {{ util.getNumberFormat(totalAmtSum(props)) }}
            </p>
          </q-td>
          <q-td key="입금액" :props="props" style="text-align: right">
            <p v-if="payAmtSum(props) < 0" style="color: red">
              {{ util.getNumberFormat(payAmtSum(props)) }}
            </p>
            <p v-if="payAmtSum(props) >= 0">
              {{ util.getNumberFormat(payAmtSum(props)) }}
            </p>
          </q-td>
          <q-td key="잔고" :props="props" style="text-align: right">
            <p v-if="balanceAAmtSum(props) < 0" style="color: red">
              {{ util.getNumberFormat(balanceAAmtSum(props)) }}
            </p>
            <p v-if="balanceAAmtSum(props) >= 0">
              {{ util.getNumberFormat(balanceAAmtSum(props)) }}
            </p>
          </q-td>
          <q-td />
          <q-td />
          <q-td />
          <!-- </tr>
              </tbody>
            </table> -->
          <!-- </q-td> -->
        </q-tr>
      </template>

      <!-- <template v-slot:bottom>
        <div class="q-table_bottom_sub_wrap">
          <div style="margin: 0 220px">
            <p>
              <span>{{ `${dailySumDate} 합계` }}</span>
            </p>
          </div>
          <div style="margin-left: 355px">
            <p>
              <span>{{ util.getNumberFormat(dailySalSum) }}</span>
            </p>
          </div>
          <div>
            <p>
              <span>{{ util.getNumberFormat(dailyPaySum) }}</span>
            </p>
          </div>
          <div>
            <p>
              <span>{{ util.getNumberFormat(dailyBalanceSum) }}</span>
            </p>
          </div>
        </div>
      </template> -->
    </q-table>
    <OrdLedgerDetail_popup
      :isOpen="popupOpenInfo.isOpenDetail"
      @close="closeDetail"
    />
    <CertificateComp_default
      :isOpen="popupOpenInfo.isOpenCertificate"
      @close="closeCertificate"
    />
    <!-- <OrdLedgerSignWebVue
      :isOpen="popupOpenInfo.isOpenSign"
      @close="closeSign"
    /> -->
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { computed, ref, onMounted, inject, toRaw } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";
// import { exportFile, useQuasar } from "quasar";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
import BtnComp_active_small from "../common/BtnComp_active_small.vue";
import BtnComp_active_white_small from "../common/BtnComp_active_white_small.vue";
import BtnComp_purple from "src/components/web/common/BtnComp_purple.vue";
import MemoComp_default from "src/components/web/common/MemoComp_default.vue";
import CertificateComp_default from "../common/CertificateComp_default.vue";
import OrdLedgerDetail_popup from "src/components/web/ordLedger/OrdLedgerDetailP.vue";
// import OrdLedgerSignWebVue from "./OrdLedgerSignWeb.vue";
import DialogComp_signature from "./DialogComp_signature.vue";
import DialogComp_signature_view from "./DialogComp_signature_view.vue";

// import DatePickerComp_b from "src/components/web/common/DatePickerComp_b.vue";

const userInfo = util.getUserData();
const bus = inject("bus");
const rows = ref([]);
const loading = ref(false);
const searched = ref(false);
const selected = ref([]);

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  custNm: userInfo.CUST_NM,
  orderVal: "0",
  startDt: `${moment().format("YYYY.MM")}.01`,
  endDt: moment().format("YYYY-MM-DD"),
  searchAll: "Y",
  salesYn: "N",
  returnYn: "N",
  paymentYn: "N",
  compensationYn: "N",
  sortType: "Web",
});

const cashBlnc = ref("0");
const salAmt = ref("0");
const dpstAmt = ref("0");
const period = ref("");

const periodClassA = computed(() =>
  period.value === 0 ? "btn_options btn_active" : "btn_options"
);
const periodClassB = computed(() =>
  period.value === 1 ? "btn_options btn_active" : "btn_options"
);
const periodClassC = computed(() =>
  period.value === 3 ? "btn_options btn_active" : "btn_options"
);
const periodClassD = computed(() =>
  period.value === 6 ? "btn_options btn_active" : "btn_options"
);
const periodClassE = computed(() =>
  period.value === 12 ? "btn_options btn_active" : "btn_options"
);
const periodClassF = computed(() =>
  period.value === 24 ? "btn_options btn_active" : "btn_options"
);
const periodClassG = computed(() =>
  period.value === 36 ? "btn_options btn_active" : "btn_options"
);

const dailySalSum = ref(0);
const dailyPaySum = ref(0);
const dailyBalanceSum = ref(0);
const dailySumDate = ref("");
const isHideBottom = ref(true);
const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

const companyCds = ["49"]; // TODO: 추가해야함
const isShowCrtfBtn = computed(
  () => userInfo.ACCPT_YN === "Y" && !companyCds.includes(userInfo.COMPANY_CD)
);

const popupOpenInfo = ref({
  isOpenCertificate: false,
  isOpenDetail: false,
  isOpenSign: false,
});

const company = [
  "dhhorim",
  "tspharm",
  "kaymed",
  "charmacist",
  "gfpharm",
  "reinpharm",
  "nurip",
  "mediplankorea",
  "ansimpharm",
];

//---------------------------- Function ------------------------------//

function onClicksearchParamCheck(val) {
  const searchAll = searchParam.value.searchAll;
  const salesYn = searchParam.value.salesYn;
  const returnYn = searchParam.value.returnYn;
  const paymentYn = searchParam.value.paymentYn;
  const compensationYn = searchParam.value.compensationYn;

  if (val === "all") {
    if (searchAll === "Y") {
      searchParam.value.salesYn = "N";
      searchParam.value.returnYn = "N";
      searchParam.value.paymentYn = "N";
      searchParam.value.compensationYn = "N";
    } else {
      searchParam.value.salesYn = "N";
      searchParam.value.returnYn = "N";
      searchParam.value.paymentYn = "N";
      searchParam.value.compensationYn = "N";
    }
  } else {
    if ([salesYn, returnYn, paymentYn, compensationYn].includes("Y")) {
      searchParam.value.searchAll = "N";
    } else {
      searchParam.value.searchAll = "Y";
    }
  }
}

function balanceAAmtSum(props) {
  if (rows.value.length > 0) {
    let nowKey = props.row.APRV_DT;

    const tempList = rows.value.filter((row) => row.APRV_DT === nowKey);

    return tempList[0].BALANCE_A_AMT;
  }
}

function payAmtSum(props) {
  if (rows.value.length > 0) {
    let nowKey = props.row.APRV_DT;

    const tempList = rows.value.filter((row) => row.APRV_DT === nowKey);

    return tempList.reduce((total, row) => total + row.PAY_AMT, 0);
  }
}

function totalAmtSum(props) {
  if (rows.value.length > 0) {
    let nowKey = props.row.APRV_DT;

    const tempList = rows.value.filter((row) => row.APRV_DT === nowKey);

    return tempList.reduce((total, row) => total + row.TOTAL_AMT, 0);
  }
}

function isShowSumRow(props) {
  if (rows.value.length > 0) {
    let nowKey = props.row.APRV_DT;

    if (rows.value[props.rowIndex + 1]) {
      let nextKey = rows.value[props.rowIndex + 1].APRV_DT;

      if (nowKey !== nextKey) {
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  }
}

// 그리드 row 클릭 시 상세정보 팝업
async function showDetailPop(props) {
  popupOpenInfo.value.isOpenDetail = true;

  await bus.emit("ordLedgerDetail", props.row);
}

// 그리드 row 클릭
async function onClickRow(props) {
  // await getDailySum(props);

  const index = selected.value.indexOf(props.row);
  if (index === -1) {
    selected.value = [];
    selected.value.push(props.row);
  } else {
    selected.value.splice(index, 1);
  }

  if (props.row.GUBUN !== "") {
    await showDetailPop(props);
  }
}

async function fetchItemList() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  if (startDt === "" || endDt === "") {
    alert("기간을 입력해 주시기 바랍니다.");
    return;
  }

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  const searchAll = searchParam.value.searchAll;
  const salesYn = searchParam.value.salesYn;
  const returnYn = searchParam.value.returnYn;
  const paymentYn = searchParam.value.paymentYn;
  const compensationYn = searchParam.value.compensationYn;

  if (
    searchAll === "N" &&
    salesYn === "N" &&
    returnYn === "N" &&
    paymentYn === "N" &&
    compensationYn === "N"
  ) {
    alert("매출구분 종류를 선택해 주시기 바랍니다.");
    return;
  }

  loading.value = true;
  const { masterData, masterCustData, list } = await api.get(
    "/ordLedger/listSearch",
    { params: searchParam.value }
  );

  cashBlnc.value = masterCustData.BALANCE_A_AMT;
  salAmt.value = masterData.TOTAL_AMT;
  dpstAmt.value = masterData.PAY_AMT;

  rows.value = list;
  loading.value = false;
  searched.value = true;
}

// 검색버튼 클릭
async function onClickSearch() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  if (!moment(startDt).isBefore(endDt) && !moment(startDt).isSame(endDt)) {
    alert("입력한 끝 날짜가 시작 날짜보다 빠릅니다");
    searchParam.value.startDt = "";
    date1.value = "";

    return;
  }

  await fetchItemList();
}

// 조회기간 버튼 클릭
async function onClickPeriod(val) {
  period.value = val;

  await setDate(val);
  await fetchItemList();
}

function closeCertificate() {
  popupOpenInfo.value.isOpenCertificate = false;
}

function closeDetail() {
  popupOpenInfo.value.isOpenDetail = false;
}

function closeSign() {
  popupOpenInfo.value.isOpenSign = false;
}

// 인수증 보기버튼 클릭
async function onClickCrtfBtn(row) {
  popupOpenInfo.value.isOpenCertificate = true;

  await bus.emit("acceptanceFile", row);
}

function wrapCsvValue(val, formatFn, row) {
  let formatted = formatFn !== void 0 ? formatFn(val, row) : val;

  formatted =
    formatted === void 0 || formatted === null ? "" : String(formatted);

  formatted = formatted.split('"').join('""');

  return `"${formatted}"`;
}

// 사인 버튼 클릭
async function onClickSignWeb(row) {
  popupOpenInfo.value.isOpenSign = true;

  await bus.emit("signWeb", row);
}

function exportTable() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  api
    .get("excel/ordLedger", {
      params: searchParam.value,
      responseType: "arraybuffer",
    })
    .then(function (res) {
      const url = window.URL.createObjectURL(new Blob([res]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute(
        "download",
        "매출원장_" +
          searchParam.value.startDt +
          "_" +
          searchParam.value.endDt +
          ".xls"
      );
      document.body.appendChild(link);
      link.click();
      link.remove();
    })
    .catch(function (error) {
      console.log(error);
    });
}

function exportPrint() {
  var openParam = "";
  var cw = screen.availWidth; // 화면 넓이
  var ch = screen.availHeight; // 화면 높이
  var sw = 825; // 띄울 창의 넓이
  var sh = 760; // 띄울 창의 높이
  var ml = (cw - sw) / 2; // 가운데 띄우기위한 창의 x위치
  var mt = (ch - sh) / 2; // 가운데 띄우기위한 창의 y위치

  /** 위수탁업체 여부 파라미터 추가 **/
  var companyYn = "N";

  const url = window.location.href;

  company.forEach((m) => {
    if (url.includes(m)) {
      companyYn = "Y";
    }
  });

  if (navigator.userAgent.indexOf("Chrome") > 0) {
    sw = sw + 20;
    sh = sh + 10;
    openParam = "width=" + sw + ",height=" + sh + ",top=" + mt + ",left=" + ml;
  } else if (navigator.userAgent.indexOf("MSIE") > 0) {
    /*IE10미만*/
    openParam = "width=" + sw + ",height=" + sh + ",top=" + mt + ",left=" + ml;
  } else {
    /*IE10이상*/
    openParam = "width=" + sw + ",height=" + sh + ",top=" + mt + ",left=" + ml;
  }
  var parmeter = "";

  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  for (var [key, val] of Object.entries(searchParam.value)) {
    parmeter += key + "=" + val + "&";
  }

  /** 위수탁업체 여부 파라미터 추가 **/
  parmeter += "companyYn=" + companyYn + "&";

  window.open(
    process.env.PRINT_URL + "/ordLedger?" + parmeter,
    "_blank",
    openParam
  );
}

bus.on("refreshOrdLedger", async () => {
  await fetchItemList();
});

/*********** datepicker ***********/
// 컴포넌트로 사용할수가 없어
// DatePickerComp_b.vue 이벤트 여기로 뻄

const date1 = ref("");
const date2 = ref("");
const proxyDate1 = ref("");
const proxyDate2 = ref("");

const updateProxy1 = () => {
  proxyDate1.value = date1.value;
};
const save1 = () => {
  date1.value = proxyDate1.value;
};
const updateProxy2 = () => {
  proxyDate2.value = date2.value;
};
const save2 = () => {
  date2.value = proxyDate2.value;
};

function getYYYYMMDD(date) {
  return (
    date.getFullYear() +
    "." +
    (date.getMonth() + 1).toString().padStart(2, "0") +
    "." +
    date.getDate().toString().padStart(2, "0")
  );
}

async function setDate(val) {
  if (!val) {
    val = 1;
  } else {
    val++;
  }

  const now = new Date();
  const year = now.getFullYear();
  const month = now.getMonth() + 1;
  const strtDt = new Date(year, month - val, 1);
  const endDt = new Date(year, month, 0);

  date1.value = getYYYYMMDD(strtDt);
  date2.value = getYYYYMMDD(endDt);
  proxyDate1.value = getYYYYMMDD(strtDt);
  proxyDate2.value = getYYYYMMDD(endDt);

  searchParam.value.startDt = date1.value.replace(match, "");
  searchParam.value.endDt = date2.value.replace(match, "");
}

function updateStartDt() {
  searchParam.value.startDt = proxyDate1.value.replace(match, "");
}

function updateEndDt() {
  searchParam.value.endDt = proxyDate2.value.replace(match, "");
}

function validDate() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  if (startDt) {
    if (!moment(startDt, "YYYY-MM-DD", true).isValid()) {
      alert("유효하지 않은 날짜 형식입니다.");
      searchParam.value.startDt = "";
      date1.value = "";

      return;
    }
  }

  if (endDt) {
    if (!moment(endDt, "YYYY-MM-DD", true).isValid()) {
      alert("유효하지 않은 날짜 형식입니다.");
      searchParam.value.endDt = "";
      date2.value = "";

      return;
    }
  }
}
/**********************************/

onMounted(async () => {
  await setDate();
  period.value = 0;
  await fetchItemList();
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "일자",
    label: "일자",
    align: "center",
    required: true,
    field: "APRV_DT",
    format: (val) => `${val}`,
  },
  { name: "승인번호", label: "승인번호", field: "APRV_NO", align: "center" },
  { name: "종류", label: "종류", field: "GUBUN_NM", align: "center" },
  { name: "광역구분", label: "광역구분", field: "DLV_GUBUN", align: "center" },
  { name: "구분", label: "구분", field: "BACK_GB_NM", align: "center" },
  {
    name: "주문서종류",
    label: "주문서종류",
    field: "ORD_TYPE_NM",
    align: "center",
  },
  { name: "적요", label: "적요", field: "ITEM_NM_UNIT", align: "center" },
  { name: "품목수", label: "품목수", field: "ITEM_CNT_TXT", align: "center" },
  { name: "판매금액", label: "판매금액", field: "TOTAL_AMT", align: "center" },
  { name: "입금액", label: "입금액", field: "PAY_AMT", align: "center" },
  { name: "잔고", label: "잔고", field: "BALANCE_A_AMT", align: "center" },
  { name: "메모", label: "메모", field: "MEMO", align: "center" },
  { name: "인수증", label: "인수증", field: "인수증", align: "center" },
  { name: "잔고확인", label: "잔고확인", field: "SIGN_YN", align: "center" },
]);

// rows.value = [
//   {
//     name: "2023.12.25",
//     승인번호: 429,
//     종류: "매출",
//     주문서종류: "일반주문(인터넷)",
//     적요: "프로스",
//     품목수: "외 9종",
//     판매금액: 93300,
//     입금액: 0,
//     잔고: 61000000,
//     메모: "",
//     인수증: "",
//     잔고확인: "",
//   },
// ];
</script>

<style lang="scss" scoped>
.salesTable_a {
  // height: 736px;
  height: 690px;
  // overflow-y: auto;
}
.q-table_radio_wrap {
  padding: 0 8px;
}
.flex_between {
  @include flex_between;
}
.q-table_calrendar_wrap,
.q-table_btn_export_wrap {
  @include flex_center;
  gap: 8px;
}
.q-table_btn_option_wrap {
  margin-left: 20px;
}
.q-table_header_sub_wrap {
  @include flex_start;
  gap: 40px;
  margin: 8px 0;
  padding: 8px 15px;
  background-color: rgba(31, 63, 130, 0.1);
}
.q-table_header_sub_wrap p span {
  @include fs-5;
}
.q-table_header_sub_wrap p span:nth-child(2) {
  @include fw-7;
  margin-left: 15px;
}
.q-table_bottom_sub_wrap {
  @include flex_start;
  gap: 40px;
  margin: 8px 0;
  padding: 8px 15px;
  background-color: rgba(31, 63, 130, 0.1);
  width: 100%;
}
.q-table_bottom_sub_wrap p span {
  @include fw-5;
  // margin-left: 15px;
}

// .Btn_options_wrap {
//   @include flex_center;
//   gap: 8px;
// }
// .btn_options {
//   color: $w;
//   background-color: #999999;
//   border-radius: 5px;
//   padding: 0 16px;
// }
// .btn_options span {
//   @include fs-5;
// }
// .btn_options:active,
// .btn_options:hover,
// .btn_options:focus {
//   @include gradation;
//   border-radius: 5px;
//   color: $w;
//   padding: 0 16px;
// }
// .btn_active {
//   @include gradation;
//   border-radius: 5px;
//   color: $w;
//   padding: 0 16px;
// }

.flex_between {
  //@include flex_center;
  justify-content: space-between;
  gap: 8px;
}
.DatePickerComp_b_wrap {
  @include flex_center;
  width: 136px;
  gap: 8px;
}
// .DatePickerComp_b_wrap .date_input_wrap {
//   width: 120px;
//   height: 24px;
//   padding: 0 4px;
//   //border: 1px solid #666666;
//   border-radius: 2.4px;
// }
.date_input_wrap {
  width: 100%;
  height: 24px;
  padding: 0 4px;
  border: 1px solid #666666;
  //text-align: center;
  border-radius: 2.4px;
}
</style>

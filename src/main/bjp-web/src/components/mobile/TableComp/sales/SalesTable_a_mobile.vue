<template>
  <div class="">
    <q-table
      class=""
      row-key="APRV_DT"
      :rows="rows"
      flat
      hide-header
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
    >
      <template v-slot:loading>
        <div class="m-loading_wrap" v-if="loading === true">
          <q-inner-loading class="loading" :showing="loading" color="primary" />
        </div>
      </template>
      <template v-slot:top>
        <div class="table_Top_wrap">
          <div class="table_Title_mobile">
            <span>{{ TableTitle }}</span>
          </div>
          <div class="filter_area">
            <div class="flex_between">
              <DatePickerComp_mobile
                style="width: 50%"
                v-model:input_model_props="searchParam.startDt"
                @on-change-date="onOnChangeDate"
                @enter="onClickSearch"
              />
              <span style="font-size: 12px; color: #999999; margin: 0 4px"
                >-</span
              >
              <DatePickerComp_mobile
                style="width: 50%"
                v-model:input_model_props="searchParam.endDt"
                @on-change-date="onOnChangeDate"
                @enter="onClickSearch"
              />
            </div>
            <BtnComp_mobile
              buttonText="조회"
              btnStyle="default"
              class="btn_srch"
              @click="onClickSearch"
            />
            <div class="table_Filter" @click="dialog_filter = true">
              <!-- <img src="/src/assets/icons/sliders-solid.svg" alt="icon"> -->
              <BtnFilterComp_mobile :filterCheck="isFilterChecked" />
              <!-- sales filter -->
              <DialogLayout_mobile
                dialog_title="검색 필터"
                v-model:dialog_model_prop="dialog_filter"
                :dialog_full="dialog_full"
              >
                <div class="">
                  <!-- <FilterComp_mobile
                    :btnGroups="filterbtnGroups"
                    btnResult="on"
                  /> -->
                  <FilterMultipulComp_mobile
                    :btnGroups="filterbtnGroups1"
                    :isActiveBtns="isActiveBtns"
                    @selected-multi-btn="(val) => selectedSortType1(val)"
                  />
                  <FilterSingleComp_mobile_a
                    :btnGroups="filterbtnGroups2"
                    :isActiveBtn="isActiveBtn1"
                    @selected-single-btn="(val) => selectedSortType2(val)"
                  />
                  <FilterSingleComp_mobile_a
                    :btnGroups="filterbtnGroups3"
                    :isActiveBtn="isActiveBtn2"
                    @selected-single-btn="(val) => selectedSortType3(val)"
                  />
                  <BtnComp_mobile
                    :class="btnResult"
                    buttonText="적용"
                    btnStyle="default"
                    btnSize="lg"
                    class="btn-full"
                    style="padding-top: 20px"
                    @click="onClickFilterApply"
                  />
                </div>
              </DialogLayout_mobile>
            </div>
          </div>
          <div class="" style="margin-bottom: 16px">
            <div class="btn-group-wrap">
              <div class="button-container">
                <q-btn
                  v-model="searchParam.sellMonth"
                  :class="thisMnth"
                  label="당월"
                  size="sm"
                  style="min-width: 64px; line-height: normal"
                  flat
                  dense
                  @click="onClickPeriod('당월')"
                />
                <q-btn
                  v-model="searchParam.sellMonth"
                  :class="bfrMnth"
                  label="전월"
                  size="sm"
                  style="min-width: 64px; line-height: normal"
                  flat
                  dense
                  @click="onClickPeriod('전월')"
                />
                <q-btn
                  v-model="searchParam.sellMonth"
                  :class="bfr3Mnth"
                  label="3개월"
                  size="sm"
                  style="min-width: 64px; line-height: normal"
                  flat
                  dense
                  @click="onClickPeriod('3개월')"
                />
                <q-btn
                  v-model="searchParam.sellMonth"
                  :class="bfr6nth"
                  label="6개월"
                  size="sm"
                  style="min-width: 64px; line-height: normal"
                  flat
                  dense
                  @click="onClickPeriod('6개월')"
                />
                <q-btn
                  v-model="searchParam.sellMonth"
                  :class="bfr1Year"
                  label="1년"
                  size="sm"
                  style="min-width: 64px; line-height: normal"
                  flat
                  dense
                  @click="onClickPeriod('1년')"
                />
                <q-btn
                  v-model="searchParam.sellMonth"
                  :class="bfr2Year"
                  label="2년"
                  size="sm"
                  style="min-width: 64px; line-height: normal"
                  flat
                  dense
                  @click="onClickPeriod('2년')"
                />
                <q-btn
                  v-model="searchParam.sellMonth"
                  :class="bfr3Year"
                  label="3년"
                  size="sm"
                  style="min-width: 64px; line-height: normal"
                  flat
                  dense
                  @click="onClickPeriod('3년')"
                />
              </div>
            </div>
            <!-- <BtnDateGroupComp_mobile btnStyle="active" /> -->
          </div>
          <div class="table_totalVal">
            <span class="table_total_Text">
              현재잔고 {{ util.getNumberFormat(cashBlnc ?? 0) }}원 | 판매금액
              {{ util.getNumberFormat(salAmt ?? 0) }}원 | 입금액
              {{ util.getNumberFormat(dpstAmt ?? 0) }}원
            </span>
          </div>
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr
          class="table_container"
          @click="props.row.expand = !props.row.expand"
        >
          <div class="table_wrap">
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <p>
                  {{
                    moment(props.row.APRV_DT, "YYYY-MM-DD HH:mm:ss").format(
                      "YYYY-MM-DD"
                    )
                  }}
                </p>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <p>({{ util.getDay(props.row.APRV_DT) }})</p>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <img
                :src="
                  props.row.expand
                    ? 'assets/images/arrw_up.svg'
                    : 'assets/images/arrw_dwn.svg'
                "
                alt="icon"
              />
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile"> 일판매합계 </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td
                class="table_item_plus table-td_mobile"
                v-if="props.row.TOTAL_AMT > 0"
              >
                +{{ util.getNumberFormat(props.row.TOTAL_AMT) }}
              </q-td>
              <q-td class="table_item_minus table-td_mobile" v-else>
                {{ util.getNumberFormat(props.row.TOTAL_AMT) }}
              </q-td>
            </div>
          </div>
        </q-tr>

        <q-tr v-show="props.row.expand" :props="props">
          <div
            class="expand_table_wrap"
            v-for="(items, i) in props.row.DATA"
            :key="i"
          >
            <div class="expand_table_item table_item_mobile">
              <div class="table_badge_wrap">
                <!-- <q-badge class="table_badge_red">
                  {{ items.MED_NM }}
                </q-badge>
                <q-badge class="table_badge_blue">
                  {{ items.PAY_ITEM_NM }}
                </q-badge> -->
                <q-badge class="bdg-line normal">
                  {{
                    items.MED_NM === ""
                      ? "일반"
                      : items.MED_NM === "전"
                      ? "전문"
                      : items.MED_NM
                  }}
                </q-badge>
                <q-badge
                  class="bdg-line"
                  :class="items.PAY_ITEM_NM === '비' ? 'nonpay' : 'pay'"
                >
                  {{
                    items.PAY_ITEM_NM === ""
                      ? "급여"
                      : items.PAY_ITEM_NM === "비"
                      ? "비급여"
                      : items.PAY_ITEM_NM
                  }}
                </q-badge>
              </div>
              <q-td class="table-td_mobile">
                {{ items.ITEM_NM_UNIT }}
              </q-td>
            </div>
            <div class="expand_table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ items.ITEM_CNT_TXT }}</q-td>
            </div>
            <div class="expand_table_item table_item_mobile">
              <q-td class="table-td_mobile">승인{{ items.APRV_NO }}</q-td>
            </div>
            <div class="expand_table_item table_item_mobile">
              <q-td class="table-td_mobile">{{
                items.ORD_TYPE_NM.includes("약가보상")
                  ? "약가보상"
                  : items.GUBUN_NM
              }}</q-td>
            </div>
            <div class="expand_table_item table_item_mobile">
              <q-td
                class="expand_table_item_plus table-td_mobile"
                v-if="items.TOTAL_AMT > 0"
              >
                +{{ util.getNumberFormat(items.TOTAL_AMT) }}
              </q-td>
              <q-td class="expand_table_item_minus table-td_mobile" v-else>
                {{
                  items.ITEM_NM_UNIT.includes("입금")
                    ? util.getNumberFormat(items.PAY_AMT)
                    : util.getNumberFormat(items.TOTAL_AMT)
                }}
              </q-td>
            </div>
            <div class="expand_table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <BtnComp_mobile
                  v-if="items.ITEM_NM_UNIT.includes('입금') == true"
                  buttonText="상세"
                  btnStyle="transparent-black"
                  @click="onViewDtl(props.row, i)"
                  :is-disable="true"
                />
                <BtnComp_mobile
                  v-else
                  buttonText="상세"
                  btnStyle="transparent-black"
                  @click="onViewDtl(props.row, i)"
                />
                <DialogLayout_mobile
                  dialog_title="거래원장 > 매출원장상세"
                  v-model:dialog_model_prop="props.row.DATA[i].dtl_model"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <SalesDetailComp_a_mobile
                      :APRV_DT="items.APRV_DT"
                      :ORD_TYPE_NM="items.ORD_TYPE_NM"
                      :GUBUN_NM="items.GUBUN_NM"
                      :APRV_NO="items.APRV_NO"
                      :TOTAL_AMT="util.getNumberFormat(items.TOTAL_AMT)"
                      :BALANCE_A_AMT="util.getNumberFormat(items.BALANCE_A_AMT)"
                      :SN="items.SN"
                    />
                  </div>
                </DialogLayout_mobile>

                <BtnComp_mobile
                  v-if="
                    isShowCrtfBtn && items.INVOICE_NO && items.GUBUN !== '2'
                  "
                  buttonText="인수증"
                  btnStyle="transparent-black"
                  @click="onViewCrtf(props.row, i)"
                />
                <DialogLayout_mobile
                  dialog_title="거래원장 > 인수증"
                  v-model:dialog_model_prop="props.row.DATA[i].crtf_model"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <SalesReceiptComp_a_mobile
                      :DLV_BRCH_CD="items.DLV_BRCH_CD"
                      :A_APRV_DT="items.A_APRV_DT"
                      :APRV_NO="items.APRV_NO"
                    />
                  </div>
                </DialogLayout_mobile>

                <BtnComp_mobile
                  v-if="items.MEMO == ''"
                  buttonText="메모"
                  btnStyle="transparent"
                  @click="onViewMemo(props.row, i)"
                  :is-disable="true"
                />
                <BtnComp_mobile
                  v-else
                  buttonText="메모"
                  btnStyle="default"
                  @click="onViewMemo(props.row, i)"
                />
                <DialogLayout_mobile
                  dialog_title="거래원장 > 메모"
                  v-model:dialog_model_prop="props.row.DATA[i].memo_model"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <TextAreaComp_mobile
                      submit="메모등록"
                      :is-visible="false"
                      :memo="items.MEMO"
                      :sn="items.SN"
                    />
                  </div>
                </DialogLayout_mobile>
              </q-td>
            </div>
            <div class="expand_table_item table_item_mobile">
              <q-td class="table-td_mobile">잔고</q-td>
            </div>
            <div class="expand_table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ util.getNumberFormat(items.BALANCE_A_AMT) }}
              </q-td>
            </div>
          </div>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { computed, ref, inject, onMounted, watch } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";

import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import DatePickerComp_mobile from "src/components/mobile/__commonComp/datepickerComp/DatePickerComp_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import BtnGroupComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnGroupComp_mobile.vue";
import BtnDateGroupComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnDateGroupComp_mobile.vue";

import SalesDetailComp_a_mobile from "src/components/mobile/DialogComp/3.salesDialogComp/SalesDetailComp_a_mobile.vue";
import SalesReceiptComp_a_mobile from "src/components/mobile/DialogComp/3.salesDialogComp/SalesReceiptComp_a_mobile.vue";
import FilterComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterComp_mobile.vue";
import TextAreaComp_mobile from "src/components/mobile/DialogComp/__commonDialogComp/TextAreaComp_mobile.vue";
import BtnFilterComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnFilterComp_mobile.vue";
import FilterSingleComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile.vue";
import FilterSingleComp_mobile_a from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile_a.vue";
import FilterMultipulComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterMultipulComp_mobile.vue";

const btnResult = ref("on");
//필터 체크
const isFilterChecked = ref(false);

//필터표시
const isActiveBtns = ref([]);
const isActiveBtn1 = ref("최신순");
const isActiveBtn2 = ref("모두 접기");

const dialog_full = ref(true);
const dialog_filter = ref(false);
//로딩
const loading = ref(true);
const show = ref(false);
const nodata_show = ref(false);

const userInfo = util.getUserData();
const bus = inject("bus");
const rows = ref([]);

const companyCds = ["49"]; // TODO: 추가해야함
const isShowCrtfBtn = computed(
  () => userInfo.ACCPT_YN === "Y" && !companyCds.includes(userInfo.COMPANY_CD)
);

const cashBlnc = ref("0");
const salAmt = ref("0");
const dpstAmt = ref("0");

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  custNm: userInfo.CUST_NM,
  orderVal: "0",
  startDt: `${moment().format("YYYY-MM")}-01`,
  endDt: moment().format("YYYY-MM-DD"),
  searchAll: "Y",
  salesYn: "Y",
  returnYn: "Y",
  paymentYn: "Y",
  compensationYn: "Y",
  sortType: "R", //R : 최신순, P: 과거순
  sellMonth: "0",
});

const props = defineProps({
  TableTitle: {
    type: String,
    default: "TableTitle",
  },
});

//기간표시
const thisMnth = computed(() =>
  searchParam.value.sellMonth === "0"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfrMnth = computed(() =>
  searchParam.value.sellMonth === "-1"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr3Mnth = computed(() =>
  searchParam.value.sellMonth === "-3"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr6nth = computed(() =>
  searchParam.value.sellMonth === "-6"
    ? " btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr1Year = computed(() =>
  searchParam.value.sellMonth === "-12"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr2Year = computed(() =>
  searchParam.value.sellMonth === "-24"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const bfr3Year = computed(() =>
  searchParam.value.sellMonth === "-36"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

//---------------------------- Function ------------------------------//

// 검색버튼 클릭
async function onClickSearch() {
  await fetchItemList();
}

async function fetchItemList() {
  //조회필터 체크
  if (isActiveBtns.value.length === 0) {
    alert("조회 조건을 선택해 주시기 바랍니다.");
    return;
  }
  rows.value = [];
  loading.value = true;

  searchParam.value.startDt = moment(searchParam.value.startDt).format(
    "YYYYMMDD"
  );
  searchParam.value.endDt = moment(searchParam.value.endDt).format("YYYYMMDD");

  const { masterData, masterCustData, list } = await api.get(
    "/ordLedger/listSearch",
    { params: searchParam.value }
  );
  searchParam.value.startDt = moment(searchParam.value.startDt).format(
    "YYYY-MM-DD"
  );
  searchParam.value.endDt = moment(searchParam.value.endDt).format(
    "YYYY-MM-DD"
  );

  cashBlnc.value = masterCustData.BALANCE_A_AMT;
  salAmt.value = masterData.TOTAL_AMT;
  dpstAmt.value = masterData.PAY_AMT;

  let nowKey = "";
  let childList = [];
  let parentList = [];
  let idx = 0;
  let dayTotalAmt = 0;

  //ios 날짜형식으로 인해 변경 로직
  let newList = list.map((i) => {
    return { ...i, APRV_DT: i.APRV_DT.replace(/\./g, "/") };
  });

  for (let i of newList) {
    childList.push(i);
    dayTotalAmt = dayTotalAmt + i.TOTAL_AMT;
    nowKey = i.APRV_DT;
    if (
      nowKey === "" ||
      idx === newList.length - 1 ||
      nowKey !== newList[idx + 1].APRV_DT
    ) {
      nowKey = i.APRV_DT;
      parentList.push({
        APRV_DT: i.APRV_DT,
        DATA: childList,
        TOTAL_AMT: dayTotalAmt,
      });

      dayTotalAmt = 0;

      childList = [];
      if (idx !== newList.length - 1) {
        nowKey = newList[idx + 1].APRV_DT;
      }
    }
    idx++;
  }

  rows.value = parentList;
  //일자상세 필터 적용
  if (sortType3.value === "모두 펼치기") {
    setExpand(true);
  } else if (sortType3.value === "모두 접기") {
    setExpand(false);
  }

  loading.value = false;
}

bus.off("onClickPeriod");
bus.on("onClickPeriod", async (params) => {
  searchParam.value.startDt = params.value.startDt;
  searchParam.value.endDt = params.value.endDt;
  await fetchItemList();
});

async function onViewDtl(row, key) {
  for (var i = 0; i < rows.value.length; i++) {
    for (var k = 0; k < rows.value[i].DATA.length; k++) {
      rows.value[i].DATA[k].dtl_model = false;
    }
  }
  row.DATA[key].dtl_model = true;

  //await bus.emit("pmntItemDetail", props.row);
}

async function onViewCrtf(row, key) {
  for (var i = 0; i < rows.value.length; i++) {
    for (var k = 0; k < rows.value[i].DATA.length; k++) {
      rows.value[i].DATA[k].crtf_model = false;
    }
  }
  row.DATA[key].crtf_model = true;

  //await bus.emit("pmntItemDetail", props.row);
}

async function onViewMemo(row, key) {
  for (var i = 0; i < rows.value.length; i++) {
    for (var k = 0; k < rows.value[i].DATA.length; k++) {
      rows.value[i].DATA[k].memo_model = false;
    }
  }
  row.DATA[key].memo_model = true;

  //await bus.emit("pmntItemDetail", props.row);
}

//조회필터
const filterbtnGroups1 = [
  {
    title: "조회 필터",
    subtitle: "복수선택",
    gridStyle: 5,
    btnDatePicker: "off",
    buttons: [
      { btnLabel: "전체" },
      { btnLabel: "매출" },
      { btnLabel: "반품" },
      { btnLabel: "입금" },
      { btnLabel: "약가보상" },
    ],
  },
];

//정렬순서
const filterbtnGroups2 = [
  {
    title: "정렬순서",
    subtitle: "",
    gridStyle: 2,
    btnDatePicker: "off",
    buttons: [{ btnLabel: "최신순" }, { btnLabel: "과거순" }],
  },
];

//일자상세
const filterbtnGroups3 = [
  {
    title: "일자상세",
    subtitle: "",
    gridStyle: 2,
    btnDatePicker: "off",
    buttons: [{ btnLabel: "모두 펼치기" }, { btnLabel: "모두 접기" }],
  },
];

//조회필터
const group1List = ref(["전체", "매출", "반품", "입금", "약가보상"]);
function selectedSortType1(val) {
  group1List.value = val;
}

//정렬순서
const sortType2 = ref("최신순");
function selectedSortType2(val) {
  sortType2.value = val;
}

//일자상세
const sortType3 = ref("모두 접기");
function selectedSortType3(val) {
  sortType3.value = val;
}

//필터 적용
function onClickFilterApply() {
  dialog_filter.value = false;

  //필터표시 초기화
  isActiveBtns.value.splice(0);

  //조회필터
  if (group1List.value.length > 0) {
    searchParam.value.searchAll =
      group1List.value.indexOf("전체") !== -1 ? "Y" : "N";
    searchParam.value.salesYn =
      group1List.value.indexOf("매출") !== -1 ? "Y" : "N";
    searchParam.value.returnYn =
      group1List.value.indexOf("반품") !== -1 ? "Y" : "N";
    searchParam.value.paymentYn =
      group1List.value.indexOf("입금") !== -1 ? "Y" : "N";
    searchParam.value.compensationYn =
      group1List.value.indexOf("약가보상") !== -1 ? "Y" : "N";

    if (
      [
        searchParam.value.salesYn,
        searchParam.value.returnYn,
        searchParam.value.paymentYn,
        searchParam.value.compensationYn,
      ].includes("N")
    ) {
      searchParam.value.searchAll = "N";
    } else {
      searchParam.value.searchAll = "Y";
    }

    //필터표시
    for (let i = 0; i < filterbtnGroups1[0].buttons.length; i++) {
      if (
        group1List.value.indexOf(filterbtnGroups1[0].buttons[i].btnLabel) !== -1
      ) {
        isActiveBtns.value.push(i);
      }
    }
  }

  //정렬순서
  if (sortType2.value === "최신순") {
    searchParam.value.sortType = "R";
  } else if (sortType2.value === "과거순") {
    searchParam.value.sortType = "P";
  }

  //필터표시
  if (!util.isEmpty(sortType2.value)) {
    // isActiveBtn1.value = filterbtnGroups2[0].buttons.findIndex(
    //   (obj) => obj.btnLabel == sortType2.value
    // );
    isActiveBtn1.value = sortType2.value;
  } else {
    // isActiveBtn1.value = -1;
    isActiveBtn1.value = "";
  }

  //일자상세
  if (sortType3.value === "모두 펼치기") {
    setExpand(true);
  } else if (sortType3.value === "모두 접기") {
    setExpand(false);
  }

  //필터표시
  if (!util.isEmpty(sortType3.value)) {
    // isActiveBtn2.value = filterbtnGroups3[0].buttons.findIndex(
    //   (obj) => obj.btnLabel == sortType3.value
    // );
    isActiveBtn2.value = sortType3.value;
  } else {
    isActiveBtn2.value = "";
  }

  isFilterChecked.value = true;

  fetchItemList();
}

//상세 펼치기/접기
function setExpand(val) {
  for (let i = 0; i < rows.value.length; i++) {
    rows.value[i]["expand"] = val;
  }
}

//기간 설정
const setDt = async () => {
  searchParam.value.startDt = moment()
    .add(searchParam.value.sellMonth, "month")
    .startOf("month")
    .format("YYYYMMDD");
  searchParam.value.endDt = moment().format("YYYYMMDD");
};

// 기간 버튼 클릭
const onClickPeriod = async (val) => {
  if (val === "당월") {
    searchParam.value.sellMonth = "0";
  } else if (val === "전월") {
    searchParam.value.sellMonth = "-1";
  } else if (val === "3개월") {
    searchParam.value.sellMonth = "-3";
  } else if (val === "6개월") {
    searchParam.value.sellMonth = "-6";
  } else if (val === "1년") {
    searchParam.value.sellMonth = "-12";
  } else if (val === "2년") {
    searchParam.value.sellMonth = "-24";
  } else if (val === "3년") {
    searchParam.value.sellMonth = "-36";
  }

  await setDt();
};

onMounted(async () => {
  //필터표시
  filterbtnGroups1[0].buttons.forEach((el, index) => {
    isActiveBtns.value.push(index);
  });
  searchParam.value.sellMonth = "0";
  setDt();
  await fetchItemList();
  setTimeout(() => {
    loading.value = false;
    show.value = true;
    nodata_show.value = true;
  }, 3000);
});

//사용자 임의 변경
const onOnChangeDate = () => {
  searchParam.value.sellMonth = "";
};
</script>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap");

.table_wrap {
  display: grid;
  align-items: center;
  justify-content: space-around;
  grid-template-columns: 25% 7% 3% 20% 35%;
  // padding: 6px 0 !important;

  .table_item:nth-child(1) {
    td {
      @include fw-6;
      // font-family: 'Malgun Gothic';
      p {
        // font-family: "Malgun Gothic";
      }
    }
  }
  .table_item:nth-child(2) {
    td {
      // @include fw-7;
    }
  }
  .table_item:nth-child(3) {
    margin-left: 2px;
  }
  // .table_item:nth-child(4) {
  // }
  .table_item:nth-child(5) {
    @include flex_end;
    td {
      // font-family: "Malgun Gothic";
    }
  }
}

.expand_table_wrap {
  @include border-b;
  display: grid;
  grid-template-columns: repeat(11, 1fr);
  padding: 8px 15px;
  gap: 8px;
  // padding: 6px 0 !important;
  // &:hover {
  //   background-color: $b5;
  // }
  .expand_table_item:nth-child(1) {
    @include flex_start;
    @include fw-6;
    align-items: flex-start;
    max-width: 290px;
    grid-column: span 10;
    gap: 8px;
    height: auto !important;
    td {
      // overflow: hidden;
      // text-overflow: ellipsis;
      height: auto !important;
      white-space: normal;
    }
  }
  .expand_table_item:nth-child(2) {
    grid-column: span 1;
    td {
      @include flex_end;
      // @include fs-5;
    }
  }
  .expand_table_item:nth-child(3) {
    grid-column: span 5;
    td {
      @include flex_start;
      // @include fs-6;
      // font-size: 12px !important;
      // color: #666666;
    }
  }
  .expand_table_item:nth-child(4) {
    grid-column: span 2;
    td {
      @include flex_end;
      // @include fs-5;
    }
  }
  .expand_table_item:nth-child(5) {
    grid-column: span 4;
    td {
      @include flex_end;
      // @include fs-5;
      // @include fw-7;
      // font-family: "Malgun Gothic";
    }
  }
  .expand_table_item:nth-child(6) {
    grid-column: span 5;
    td {
      @include flex_start;
      gap: 8px;
    }
  }
  .expand_table_item:nth-child(7) {
    grid-column: span 2;
    td {
      @include flex_end;
      // @include fs-5;
    }
  }
  .expand_table_item:nth-child(8) {
    grid-column: span 4;
    td {
      @include flex_end;
      // @include fs-5;
      // @include fw-7;
      // font-family: "Malgun Gothic";
    }
  }
}

//기간 표시 스타일
// .button-container {
//   .btn-focus {
//     background-image: linear-gradient(to bottom, #1f3f82, #003853) !important;
//     color: #ffffff !important;
//   }
// }
</style>

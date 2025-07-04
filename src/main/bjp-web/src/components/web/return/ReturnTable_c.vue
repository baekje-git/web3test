<template>
  <div class="">
    <q-table
      class="returnTable_c"
      :rows="rows"
      :columns="columns"
      row-key="name"
      table-header-class="table_header"
      dense
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="q-table_header_wrap">
            <div class="q-table_header_title">
              <p>제품 출고 이력</p>
              <div class="q-table_header_accumulate">
                <p>
                  {{
                    `누적 출하 수량 : 매출 ${accShpmntQtyData.SALE_CNT} / 반품 ${accShpmntQtyData.BACK_CNT}`
                  }}
                </p>
              </div>
            </div>
            <!-- <div class="q-table_header_sub_wrap"> -->
            <!-- <div v-if="isVisibleAddBtn" class="flex_center" style="gap: 8px">
              <EAComp @update-req-qty="updateReqQty" />
              <BtnComp_default_small
                btn_defaultLabel="담기"
                style="padding: 0"
                @click="onClickSmallBak"
              />
            </div> -->
            <!-- </div> -->
            <div class="q-table_header_sub_wrap">
              <div class="q-table_header_accumulate_wrap">
                <div class="q-table_header_btnOption">
                  <!-- <BtnComp_options /> -->
                  <div class="Btn_options_wrap">
                    <q-btn
                      v-model="searchHistoryParam.sellMonth"
                      :class="thisMnth"
                      label="당월"
                      dense
                      @click="onClicksellMonth('0')"
                    />
                    <q-btn
                      v-model="searchHistoryParam.sellMonth"
                      :class="bfrMnth"
                      label="전월"
                      dense
                      @click="onClicksellMonth('-1')"
                    />
                    <q-btn
                      v-model="searchHistoryParam.sellMonth"
                      :class="bfr3Mnth"
                      label="3개월"
                      dense
                      @click="onClicksellMonth('-3')"
                    />
                    <q-btn
                      v-model="searchHistoryParam.sellMonth"
                      :class="bfr6nth"
                      label="6개월"
                      dense
                      @click="onClicksellMonth('-6')"
                    />
                    <q-btn
                      v-model="searchHistoryParam.sellMonth"
                      :class="bfr1Year"
                      label="1년"
                      dense
                      @click="onClicksellMonth('-12')"
                    />
                    <q-btn
                      v-model="searchHistoryParam.sellMonth"
                      :class="bfr2Year"
                      label="2년"
                      dense
                      @click="onClicksellMonth('-24')"
                    />
                    <q-btn
                      v-model="searchHistoryParam.sellMonth"
                      :class="bfr3Year"
                      label="3년"
                      dense
                      @click="onClicksellMonth('-36')"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="flex_between">
            <div class="q-table_header_title_sub prd-history">
              <p class="q-table_header_title_sub_text">{{ itemNm }}</p>
              <q-tooltip>{{ itemNm }}</q-tooltip>
            </div>
            <div class="q-table_header_itemSearch_wrap">
              <div class="itemSearch_input">
                <!-- <p>제조번호</p> -->
                <q-input
                  v-model="searchHistoryParam.prodNo"
                  input-class="itemSearch_input"
                  borderless
                  dense
                  placeholder="제조번호"
                  @focus="(input) => input.target.select()"
                  @keyup.enter="onClickSearch"
                />
              </div>
              <div class="itemSearch_input_wrap">
                <!-- <p>유효기간</p> -->
                <!-- <DatePickerComp style="width: 146px" /> -->
                <div class="DatePickerComp_wrap">
                  <!-- <p>
                    {{ searchHistoryParam.expDt }}
                  </p> -->
                  <q-input
                    v-model="searchHistoryParam.expDt"
                    input-class="itemSearch_input"
                    mask="####-##-##"
                    borderless
                    dense
                    placeholder="유효기간"
                    @blur="validDate"
                    @focus="(input) => input.target.select()"
                    @keyup.enter="onClickSearch"
                  />
                  <div class="date_btn_wrap">
                    <q-btn class="calendar_icon" unelevated dense>
                      <img
                        src="assets/icons/calendar-days-solid.svg"
                        alt="calendar"
                      />
                      <q-popup-proxy
                        @before-show="updateProxy"
                        cover
                        transition-show="scale"
                        transition-hide="scale"
                      >
                        <q-date
                          v-model="proxyDate"
                          mask="YYYY-MM-DD"
                          :default-date="today"
                          :title="selectedDate"
                          :subtitle="selectedYear"
                        >
                          <div class="row items-center justify-end q-gutter-sm">
                            <q-btn
                              label="취소"
                              color="primary"
                              flat
                              v-close-popup
                            />
                            <q-btn
                              label="확인"
                              color="primary"
                              flat
                              @click="save"
                              v-close-popup
                            />
                          </div>
                        </q-date>
                      </q-popup-proxy>
                    </q-btn>
                  </div>
                </div>
              </div>
              <div class="itemSearch_btn_wrap">
                <BtnComp_default
                  class="btn_srch"
                  btn-label="검색"
                  @click="onClickSearch"
                />
              </div>
            </div>
          </div>
        </div>
      </template>

      <template v-slot:top-row v-if="rows.length === 0 && searched">
        <q-tr class="no-date">
          <q-td colspan="100%">
            <span>조회기간 내의 이력이 없습니다.</span>
          </q-td>
        </q-tr>
      </template>

      <template v-slot:body-cell-주문일자="props">
        <q-td :props="props">
          {{ moment(props.value).format("YYYY-MM-DD") }}
        </q-td>
      </template>

      <template v-slot:body-cell-단가="props">
        <q-td :props="props">
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-유효기간="props">
        <q-td :props="props">
          {{
            props.value == "" ? "" : moment(props.value).format("YYYY-MM-DD")
          }}
        </q-td>
      </template>

      <template v-slot:body-cell-제조번호="props">
        <q-td :props="props">
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-출하수량="props">
        <q-td :props="props">
          {{ props.value }}
        </q-td>
      </template>

      <!-- <template v-slot:body-cell-수량="props">
        <q-td :props="props" style="padding: 0">
          <div
            v-if="
              limitQty > 0 &&
              webBackUseYn !== 'Y' &&
              props.row.ITEM_QTY > 0 &&
              isProVal(props.row)
            "
            class="q-table_EA_wrap"
          >
            <div class="EAComp_wrap">
              <div class="EA_minus" @click="minus(props)">
                <img src="assets/icons/minus-solid.svg" alt="minus" />
              </div>
              <div class="">
                <q-input
                  v-model="props.row.REQ_QTY"
                  class="EA_input"
                  input-class="text-center"
                  borderless
                  reverse-fill-mask
                  mask="###,###,###"
                  @update:model-value="onUpdate(props)"
                  @focus="(input) => input.target.select()"
                />
              </div>
              <div class="EA_plus" @click="plus(props)">
                <img src="assets/icons/plus-solid.svg" alt="plus" />
              </div>
            </div>
          </div>
        </q-td>
      </template> -->

      <!-- <template v-slot:body-cell-담기="props">
        <q-td :props="props" style="padding: 0">
          <div
            v-if="
              limitQty > 0 &&
              webBackUseYn !== 'Y' &&
              props.row.ITEM_QTY > 0 &&
              isProVal(props.row)
            "
            class="q-table_cart_wrap"
          >
            <BtnComp_default_small
              btn_defaultLabel="담기"
              style="padding: 0"
              @click="onClickAddBakBasket(props.row)"
            />
          </div>
        </q-td>
      </template> -->
    </q-table>
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject, computed, reactive } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";
import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
import BtnComp_options from "src/components/web/common/BtnComp_options.vue";
import SearchComp_select from "src/components/web/common/SearchComp_select.vue";
import DatePickerComp_b from "src/components/web/common/DatePickerComp_b.vue";
import EAComp from "../common/EAComp.vue";

const userInfo = util.getUserData();
const bus = inject("bus");
const rows = ref([]);
const historyYn = ref("");
const loading = ref(false);
const searched = ref(false);

const searchHistoryParam = ref({
  custCd: "",
  selItemCd: "",
  sellMonth: "-6",
  prodNo: "",
  expDt: "",
});

const basketData = ref({
  basketGbCd: "02",
  expDt: "",
  prodNo: "",
  saveItemCd: "",
  subdivYn: "",
  itemGbCd: "",
  saveItemQty: "",
  custCd: userInfo.CUST_CD,
  userId: userInfo.USER_ID,
});

// 누적 출하 수량
const accShpmntQtyData = ref({
  SALE_CNT: 0, // 매출
  BACK_CNT: 0, // 반품
});

const thisMnth = computed(() =>
  searchHistoryParam.value.sellMonth === "0" ? "btn" : "btn_options"
);

const bfrMnth = computed(() =>
  searchHistoryParam.value.sellMonth === "-1" ? "btn" : "btn_options"
);

const bfr3Mnth = computed(() =>
  searchHistoryParam.value.sellMonth === "-3" ? "btn" : "btn_options"
);

const bfr6nth = computed(() =>
  searchHistoryParam.value.sellMonth === "-6" ? "btn" : "btn_options"
);

const bfr1Year = computed(() =>
  searchHistoryParam.value.sellMonth === "-12" ? "btn" : "btn_options"
);

const bfr2Year = computed(() =>
  searchHistoryParam.value.sellMonth === "-24" ? "btn" : "btn_options"
);

const bfr3Year = computed(() =>
  searchHistoryParam.value.sellMonth === "-36" ? "btn" : "btn_options"
);

const itemNm = ref("");
const returnCat = ref("");
const subdivYn = ref("");
const limitQty = ref(0);
const webBackUseYn = ref("N");
const medCd = ref("");

const isVisibleAddBtn = ref(false);
const smallReturnData = ref([]);
const smallReturnQty = ref(0);

//---------------------------- Function ------------------------------//

// // 담기
// async function onClickAddBakBasket(row) {
//   if (util.getNumberFormat2(row.REQ_QTY) === 0) {
//     alert("수량을 선택해 주시기 바랍니다.");
//     return;
//   }

//   if (row.LIMIT_QTY < util.getNumberFormat2(row.REQ_QTY)) {
//     alert("반품 가능한 수량을 초과할 수 없습니다.");
//     return;
//   }

//   if (row.BASKET_QTY !== "") {
//     const qtySum = row.BASKET_QTY + util.getNumberFormat2(row.REQ_QTY);

//     if (row.LIMIT_QTY < qtySum) {
//       alert("반품 가능한 수량을 초과할 수 없습니다.");
//       return;
//     }
//   }

//   if (returnCat.value === "2" || returnCat.value === "6") {
//     basketData.value.basketGbCd = "03";
//   } else {
//     basketData.value.basketGbCd = "02";
//   }

//   basketData.value.expDt = row.EXP_DT;
//   basketData.value.prodNo = row.PROD_NO;
//   basketData.value.saveItemCd = row.ITEM_CD;
//   basketData.value.subdivYn = subdivYn.value;
//   basketData.value.itemGbCd = "01";
//   basketData.value.saveItemQty = util.getNumberFormat2(row.REQ_QTY);

//   await api.post("/bakRcpt/addBasket", basketData.value);
//   bus.emit("refreshHeaderBasket"); // 헤더 카운트 새로고침

//   util.showNotify(
//     `${row.ITEM_NM} ${basketData.value.saveItemQty}개를 장바구니에 담았습니다.`
//   );

//   await fetchHistoryList();
//   await bus.emit("refreshRetrunBasket");
// }

// // 소분반품 담기
// async function onClickSmallBak() {
//   const data = smallReturnData.value;
//   const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

//   if (smallReturnData.value.length === 0) {
//     alert("선택된 항목이 없습니다.");
//     return;
//   }

//   if (smallReturnQty.value === 0) {
//     alert("수량을 선택해 주시기 바랍니다.");
//     return;
//   }

//   if (searchHistoryParam.value.prodNo === "") {
//     alert("제조번호를 입력해 주시기 바랍니다.");
//     return;
//   }

//   if (searchHistoryParam.value.expDt === "") {
//     alert("유효기간을 입력해 주시기 바랍니다.");
//     return;
//   }

//   if (data.LIMIT_QTY < smallReturnQty.value) {
//     alert("반품 가능한 수량을 초과할 수 없습니다.");
//     return;
//   }

//   if (data.RETURN_CAT === "2" || data.RETURN_CAT === "6") {
//     basketData.value.basketGbCd = "03";
//   } else {
//     basketData.value.basketGbCd = "02";
//   }

//   basketData.value.expDt = searchHistoryParam.value.expDt.replace(match, "");
//   basketData.value.prodNo = searchHistoryParam.value.prodNo;
//   basketData.value.saveItemCd = data.ITEM_CD;
//   basketData.value.subdivYn = data.SUBDIV_YN;
//   basketData.value.itemGbCd = "01";
//   basketData.value.saveItemQty = smallReturnQty.value;

//   await api.post("/bakRcpt/addBasket", basketData.value);
//   bus.emit("refreshHeaderBasket"); // 헤더 카운트 새로고침

//   util.showNotify(
//     `${data.ITEM_NM} ${basketData.value.saveItemQty}개를 장바구니에 담았습니다.`
//   );

//   await fetchHistoryList();
//   await bus.emit("refreshRetrunBasket");
// }

// 누적 출하 수량 조회
async function fetchAccShpmntQty() {
  const res = await api.get("/ord/accShpmntQty", {
    params: searchHistoryParam.value,
  });

  accShpmntQtyData.value.SALE_CNT = res.SALE_CNT;
  accShpmntQtyData.value.BACK_CNT = res.BACK_CNT;
  loading.value = false;
}

// 이력 목록 조회
async function fetchHistoryList() {
  rows.value = [];
  loading.value = true;

  const res = await api.get("/bakRcpt/history", {
    params: searchHistoryParam.value,
  });

  searched.value = true;
  rows.value = res;

  await fetchAccShpmntQty();
}

// 이력 기간버튼 클릭
async function onClicksellMonth(val) {
  searchHistoryParam.value.sellMonth = val;

  // if (historyYn.value === "Y") {
  await fetchHistoryList();
  // }
}

// 검색버튼 클릭
async function onClickSearch() {
  if (
    searchHistoryParam.value.prodNo !== "" &&
    searchHistoryParam.value.prodNo.length < 2
  ) {
    alert("제조번호로 검색 시 2자리 이상 입력해야 합니다.");
    return;
  }

  await fetchHistoryList();
}

// 반품 그리드에서 row 클릭 시 이력 조회
bus.on("bakRcptHistoryList", async (data) => {
  const row = data.row;
  console.log(row);
  searchHistoryParam.value.expDt = "";
  searchHistoryParam.value.prodNo = "";
  itemNm.value = `${row.ITEM_NM} ${row.UNIT}`;
  webBackUseYn.value = row.WEB_BACK_USE_YN;
  medCd.value = row.MED_CD;

  if (row.HISTORY_YN === "Y") {
    returnCat.value = row.RETURN_CAT;
    subdivYn.value = row.SUBDIV_YN;
    historyYn.value = row.HISTORY_YN;
    searchHistoryParam.value.custCd = userInfo.CUST_CD;
    searchHistoryParam.value.selItemCd = row.ITEM_CD;

    await fetchHistoryList();
  } else {
    rows.value = [];
  }

  if (data.returnCat === "2" || data.returnCat === "4") {
    smallReturnData.value = data.row;
  }
});

// 반품 장바구니에서 row 클릭 시 이력 조회
bus.on("bakRcptBasketHistoryList", async (data) => {
  searchHistoryParam.value.expDt = "";
  searchHistoryParam.value.prodNo = "";
  searchHistoryParam.value.sellMonth = "-6";
  itemNm.value = `${data.ITEM_NM} ${data.UNIT}`;
  webBackUseYn.value = data.WEB_BACK_USE_YN;
  medCd.value = data.MED_CD;

  returnCat.value = data.RETURN_CAT;
  subdivYn.value = data.SUBDIV_YN;
  historyYn.value = data.HISTORY_YN;
  searchHistoryParam.value.custCd = userInfo.CUST_CD;
  searchHistoryParam.value.selItemCd = data.ITEM_CD;

  await fetchHistoryList();
});

// 반품 그리드에서 검색 시 초기화
bus.on("bakRcptInit", () => {
  rows.value = [];
  itemNm.value = "";
  searchHistoryParam.value.custCd = "";
  searchHistoryParam.value.selItemCd = "";
  searchHistoryParam.value.prodNo = "";
  searchHistoryParam.value.expDt = "";
});

// 회수품목내용에서 담기 버튼 클릭 시
bus.on("addShpmntItem", async (data) => {
  if (returnCat.value === "2" || returnCat.value === "6") {
    basketData.value.basketGbCd = "03";
  } else {
    basketData.value.basketGbCd = "02";
  }

  basketData.value.expDt = data.EXP_DT;
  basketData.value.prodNo = data.PROD_NO;
  basketData.value.saveItemCd = data.ITEM_CD;
  basketData.value.subdivYn = data.SUBDIV_YN;
  basketData.value.itemGbCd = data.ITEM_GB_CD;
  basketData.value.saveItemQty = data.REQ_QTY;

  await api.post("/bakRcpt/addBasket", basketData.value);
  await bus.emit("refreshHeaderBasket");

  util.showNotify(
    `${data.ITEM_NM_UNIT} ${basketData.value.saveItemQty}개를 장바구니에 담았습니다.`
  );
  await bus.emit("refreshRetrunBasket");
});

// 반품바구니에서 삭제 시 제품 출고 이력 재조회
bus.on("refreshHistoryList", async () => {
  await fetchHistoryList();
});

bus.on("checkReturnCat", (val) => {
  if (val === "2" || val === "4") {
    isVisibleAddBtn.value = true;
  } else {
    isVisibleAddBtn.value = false;
  }
});

bus.on("initSmallBakQty", () => {
  smallReturnQty.value = 0;
});

// 전문의약품 유효성 체크
const isProVal = (row) => {
  const expDt = row.EXP_DT;
  const prodNo = row.PROD_NO;

  if (medCd.value === "02") {
    if (expDt !== "" && prodNo !== "") {
      return true;
    } else {
      return false;
    }
  } else {
    return true;
  }
};

// 수량 event //
function plus(props) {
  props.row.REQ_QTY++;
  // console.log(props.row.REQ_QTY);
}

function minus(props) {
  if (props.row.REQ_QTY > 0) {
    props.row.REQ_QTY--;
    // console.log(props.row.REQ_QTY);
  }
}

// function updateCount(props, evt) {
//   const isNotNull = evt.target.value !== "" ? true : false;

//   if (!isNaN(evt.target.value) && isNotNull) {
//     props.row.REQ_QTY = parseInt(evt.target.value);
//   } else {
//     props.row.REQ_QTY = 0;
//   }
// }

// datepicker event //
const proxyDate = ref();

const updateProxy = () => {
  proxyDate.value = searchHistoryParam.value.expDt;
};

const save = () => {
  searchHistoryParam.value.expDt = proxyDate.value;
};
// 오늘 날짜와 기본 날짜 설정 250307
const today = new Date();
// 캘린더 title 250307
const selectedDate = computed(() => {
  const date = new Date(proxyDate.value);
  const month = date.getMonth() + 1; // 0-11에서 1-12로 변경
  const day = date.getDate();
  const weekday = date.toLocaleDateString("ko-KR", { weekday: "short" }); // 요일 가져오기

  return proxyDate.value
    ? `${month}월 ${day}일 (${weekday[0].toUpperCase() + weekday.slice(1)})`
    : `${new Date(today).getMonth() + 1}월 ${new Date(today).getDate()}일`;
});
// 캘린더 subtitle 250307
const selectedYear = computed(() => {
  return proxyDate.value
    ? new Date(proxyDate.value).getFullYear()
    : new Date(today).getFullYear();
});

function validDate() {
  const expDt = searchHistoryParam.value.expDt;

  if (expDt) {
    if (!moment(expDt, "YYYY-MM-DD", true).isValid()) {
      alert("유효하지 않은 날짜 형식입니다.");
      searchHistoryParam.value.expDt = "";
      return;
    }
  }
}

function updateReqQty(val) {
  smallReturnQty.value = val;
}

const onUpdate = (val) => {
  if (val.col.name == "수량") {
    let newValue = val.row.REQ_QTY.replace(/,/g, "");
    newValue = newValue.replace(/^0+(?!$)/, "");
    newValue = parseInt(newValue);
    val.row.REQ_QTY = newValue;
    // console.log(val.row.REQ_QTY);
  }
};

//-------------------------- Grid Setting ----------------------------//

const columns = [
  {
    name: "주문일자",
    label: "주문일자",
    align: "center",
    sortable: true,
    field: "APRV_DT",
    classes: "td-date",
    format: (val) => (val !== "" ? moment(val).format("YYYY.MM.DD") : ""),
  },
  {
    name: "단가",
    label: "단가",
    field: "WP2_AMT",
    align: "right",
    classes: "td-price",
    format: (val) => util.getNumberFormat(val),
  },
  {
    name: "유효기간",
    label: "유효기간",
    field: "EXP_DT",
    align: "center",
    classes: "td-exp",
    format: (val) => (val !== "" ? moment(val).format("YYYY.MM.DD") : ""),
  },
  {
    name: "제조번호",
    label: "제조번호",
    field: "PROD_NO",
    align: "center",
    classes: "td-serial",
  },
  {
    name: "출하수량",
    label: "출하수량",
    field: "ITEM_QTY",
    align: "center",
    classes: "td-qty",
    format: (val) => util.getNumberFormat(val),
  },
  // { name: "수량", label: "수량", field: "REQ_QTY", align: "center" },
  // { name: "담기", label: "담기", field: "ADD_BASKET", align: "center" },
];
</script>

<style lang="scss" scoped>
.returnTable_c {
  // height: 319px;
  height: 240px;
  overflow-y: auto;
  @media screen and (max-width: 830px) {
    height: auto;
    max-height: 340px;
  }
}
.q-table_wrap {
  margin-bottom: 8px;
}
.q-table_header_wrap {
  justify-content: space-between;
  // align-items: flex-start;
  .q-table_header_title {
    @include flex_start;
    min-width: 380px;
  }
  .q-table_header_sub_wrap {
    display: inline-flex;
    width: auto;
  }
  @media screen and (max-width: 1540px) {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
}
.q-table_header_itemSearch_wrap {
  @include flex_end;
  // align-items: flex-start;
  gap: 8px;
  // margin-bottom: 8px;
  @media screen and (max-width: 1440px) and (min-width: 831px) {
    gap: 8px 4px;
  }
}
// .itemSearch_input_wrap {
//   @include flex_center;
//   // align-items: flex-start;
// }
// .itemSearch_input {
//   @include fs-5;
//   width: 120px;
//   height: 32px;
//   border: 1px solid $line-400;
//   padding: 4px 6px;
//   border-radius: 0;
// }

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

.EAComp_wrap {
  @include flex_center;
  width: 56px;
  height: 24px;
  padding: 0 2px;
  border: 1px solid #666666;
  border-radius: 2.4px;
}
.EA_minus,
.EA_plus {
  @include flex_center;
  cursor: pointer;
  height: 24px;
}
.EA_input {
  @include fs-5;
  width: 32px;
  text-align: center;
  border: none;
}

// .DatePickerComp_wrap {
//   @include flex_center;
//   gap: 8px;
// }
// .date_input_wrap {
//   width: 100%;
//   height: 24px;
//   padding: 0 4px;
//   border: 1px solid $line-400;
//   border-radius: 2.4px;
// }
// .date_input_wrap p {
//   @include fs-5;
//   text-align: center;
// }

// .DatePickerComp_wrap {
//   @include flex_start;
//   gap: 8px;
//   width: 136px;
//   height: 32px;
//   border: 1px solid $line-400;
//   .q-input {
//     width: calc(100% - 30px);
//     padding: 4px 6px;
//   }
//   .q-field__native {
//     width: 100%;
//     border: none;
//   }
// }
// .date_input_wrap,
// .date_btn_wrap {
//   display: inline-flex;
//   align-items: center;
// }
// .date_input_wrap {
//   padding: 0 4px;

//   border-radius: 0;
// }
// .date_btn_wrap {
//   position: relative;
//   width: 30px;
//   .q-btn {
//     width: 100%;
//     border-radius: 0;
//   }
//   &::before {
//     content: "";
//     position: absolute;
//     top: 50%;
//     left: -1px;
//     transform: translateY(-50%);
//     width: 1px;
//     height: 12px;
//     background-color: $line-400;
//   }
// }
.Btn_options_wrap .q-btn {
  @include flex_center;
  padding: 0 5px;
  width: 60px;
  height: 32px;
  color: $gray-9 !important;
  background-color: #fff;
  background-image: none !important;
  border: 1px solid $line-400;
  border-radius: 0;
  transition: none;
  &::before {
    display: none;
  }
  &:nth-child(1) {
    padding-left: 12.5px;
    width: 70px;
    border-radius: 15px 0 0 15px !important;
  }
  &:nth-last-child(1) {
    padding-right: 12.5px;
    width: 70px;
    border-radius: 0 15px 15px 0 !important;
  }
  span {
    @include fs-5;
  }
}
.Btn_options_wrap {
  @include flex_center;
  gap: 0;
  .q-btn + .q-btn {
    border-left: none;
  }
  .btn,
  .btn_active,
  .btn_options:active,
  .btn_options:hover,
  .btn_options:focus {
    color: #fff !important;
    background-color: $primary;
    border-top-color: $primary;
    border-bottom-color: $primary;
    &:nth-child(1),
    &:nth-last-child(1) {
      border-color: $primary;
    }
  }
}

.returnTable_c {
  .itemSearch_input {
    @include fs-5;
    width: 136px;
    height: 32px;
    border: 1px solid $line-400;
    padding: 4px;
    border-radius: 0;
  }
  .DatePickerComp_wrap {
    > div:nth-child(1) {
      width: auto !important;
    }
    .q-input {
      width: 100% !important;
    }
  }
  .itemSearch_input_wrap {
    .DatePickerComp_wrap {
      @include flex_start;
      gap: 8px 0;
      padding: 0 0 0 4px;
      width: 136px;
      height: 32px;
      border: 1px solid $line-400;
      .q-input {
        width: calc(100% - 30px);
        padding: 0;
      }
      .q-field__native {
        width: 100%;
        padding: 0;
        border: none;
      }
    }
    .date_input_wrap,
    .date_btn_wrap {
      display: inline-flex;
      align-items: center;
    }
    .date_input_wrap {
      padding: 0 4px;

      border-radius: 0;
    }
    .date_btn_wrap {
      position: relative;
      .q-btn {
        width: 30px;
        border-radius: 0;
      }
      &::before {
        content: "";
        position: absolute;
        top: 50%;
        left: -1px;
        transform: translateY(-50%);
        width: 1px;
        height: 12px;
        background-color: $line-400;
      }
    }
  }
}
</style>

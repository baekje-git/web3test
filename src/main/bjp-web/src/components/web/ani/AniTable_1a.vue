<template>
  <div class=" ">
    <q-table
      class="orderTable_1a"
      ref="tableRef"
      :rows="rows"
      :columns="computedColumns"
      row-key="SEQ"
      table-header-class="table_header"
      table-class="tbl_all"
      dense
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
      v-model:selected="selected"
      @focusin="activateNavigation"
      @focusout="deactivateNavigation"
      @keydown="onKey"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="q-table_header_wrap" style="margin-bottom: 8px">
            <div class="q-table_header_sub_wrap">
              <div class="srch_form">
                <!-- <SearchComp
                  placeholder1="보험코드/품명입력"
                  placeholder2="제약사 입력"
                /> -->
                <div class="slct-wrap">
                  <q-select
                    class="select_default"
                    v-model="searchParam.searchOption"
                    :options="listOption"
                    borderless
                    dense
                    emit-value
                    map-options
                  />
                </div>
                <div class="SearchComp_wrap">
                  <!-- <q-select
                    use-input
                    hide-dropdown-icon
                    fill-input
                    ref="qSelectRef"
                    input-class="input_search"
                    borderless
                    input-debounce="0"
                    placeholder="품목명/보험코드 입력"
                    v-model="modeldummy"
                    :value="model"
                    :options="options"
                    @input-value="updateVal"
                  >
                  </q-select> -->
                  <!-- AutoComplete 샘플 소스  -->
                  <div>
                    <input
                      type="text"
                      placeholder="품목명/보험코드 입력"
                      :value="filterText"
                      @input="onInput"
                      @keydown.down.prevent="onArrowDown"
                      @keydown.up.prevent="onArrowUp"
                      @keydown.enter.prevent="onEnter"
                      @keydown.esc="onClose"
                      @keyup.enter="onClickItemSearch"
                      class="input_search1"
                      autofocus
                      @focus="(input) => input.target.select()"
                      ref="inputRef"
                    />
                  </div>

                  <!-- <q-input
                    v-model="searchParam.keyword"
                    input-class="input_search"
                    borderless
                    dense
                    placeholder="품목명/보험코드 입력"
                    @keyup.enter="onClickItemSearch"
                    autofocus
                    @focus="(input) => input.target.select()"
                  /> -->
                  <q-input
                    v-model="searchParam.makerNm"
                    class="input_search2"
                    borderless
                    dense
                    placeholder="제약회사명 입력"
                    @keyup.enter="onClickItemSearch"
                    @focus="(input) => input.target.select()"
                  />
                </div>
                <!-- <CheckComp_a
                  v-model="orderCheckbox"
                  :checkbox_options="orderCheckboxOptions"
                /> -->
                <div class="chk-wrap">
                  <q-checkbox
                    v-model="searchParam.history"
                    class="chk-custom"
                    true-value="Y"
                    false-value="N"
                    label="이력"
                  />
                  <q-checkbox
                    v-model="searchParam.excludingOutOfOtock"
                    class="chk-custom"
                    true-value="Y"
                    false-value="N"
                    label="품절제외"
                  />
                </div>
                <BtnComp_default
                  btn-label="검색"
                  class="btn_srch"
                  @click="onClickItemSearch"
                />
              </div>
            </div>
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
      <!-- 품목/ -->
      <template v-slot:body-cell-ITEM_NM="props">
        <q-td
          :props="props"
          style="cursor: pointer; text-align: left"
          @click="onRowClick(props)"
          :class="getRowClass(props.row)"
          :ref="(el) => setRowRef(props.rowIndex, el)"
        >
          <!--
        위에 q-td 태그안에 class 선언 (그리드 줄 포커싱 부분 - 20241007)
        :class="getRowClass(props.row)" -->
          <div class="q-table_item">
            <div class="q-table_badge_wrap">
              <q-badge class="bdg-line normal">
                {{
                  props.row.MED_NM === "일"
                    ? "일반"
                    : props.row.MED_NM === "전"
                    ? "전문"
                    : props.row.MED_NM === "동"
                    ? "동물"
                    : props.row.MED_NM
                }}
              </q-badge>
              <q-badge
                class="bdg-line"
                :class="props.row.PAY_ITEM_NM === '비' ? 'nonpay' : 'pay'"
              >
                {{
                  props.row.PAY_ITEM_NM === "급"
                    ? "급여"
                    : props.row.PAY_ITEM_NM === "비"
                    ? "비급여"
                    : props.row.PAY_ITEM_NM
                }}
              </q-badge>
            </div>
            <div class="q-table_itemTitle td-prd_name">
              <BtnComp_active_white_small class="pd-0 btn-prd">
                <span style="text-align: left">{{ props.value }}</span>
              </BtnComp_active_white_small>
            </div>
          </div>
        </q-td>
      </template>

      <!-- 규격/ -->
      <template v-slot:body-cell-UNIT="props">
        <q-td :props="props" style="cursor: pointer" @click="onRowClick(props)">
          <div>
            {{ props.value }}
          </div>
        </q-td>
      </template>

      <!-- 반품/ -->
      <template v-slot:body-cell-WEB_BACK_USE_YN="props">
        <q-td :props="props" @click="onRowClick(props)">
          <div>
            <div
              v-if="props.row.WEB_BACK_USE_YN === 'Y'"
              class="q-table_order red-400"
            >
              불가
            </div>
            <div
              v-if="props.row.TAKEBACK_LIMIT_YN === 'Y'"
              class="q-table_limit blue-400"
            >
              제한
            </div>
          </div>
        </q-td>
      </template>

      <!-- 제약회사/ -->
      <template v-slot:body-cell-MAKER_NM="props">
        <q-td :props="props" style="cursor: pointer" @click="onRowClick(props)">
          <div class="q-table_itemTitleSub ellipsis">
            {{ props.value }}<q-tooltip>{{ props.value }}</q-tooltip>
          </div>
        </q-td>
      </template>
      <!-- 단가/ -->
      <template v-slot:body-cell-ORD_WP2_AMT="props">
        <q-td
          :props="props"
          style="cursor: pointer; text-align: right"
          @click="onRowClick(props)"
        >
          <div class="q-table_stock_wrap">
            <p>
              {{ props.value }}
            </p>
          </div>
        </q-td>
      </template>
      <!-- 재고/ -->
      <template v-slot:body-cell-AVAIL_STOCK="props">
        <q-td :props="props" style="cursor: pointer" @click="onRowClick(props)">
          <div class="q-table_stock_wrap">
            <p v-if="props.row.AVAIL_STOCK > 0">
              {{ props.value }}
            </p>
            <p class="q-table_stock" v-else>품절</p>
          </div>
        </q-td>
      </template>

      <!-- 수량/ -->
      <template v-slot:body-cell-REQ_QTY="props">
        <q-td :props="props">
          <div v-if="props.row.AVAIL_STOCK > 0" class="q-table_EA_wrap">
            <!-- <EAComp v-model="props.row.REQ_QTY" /> -->
            <div class="qty_wrap">
              <button
                type="button"
                class="btn_qty"
                @click="props.row.ITEM_QTY > 0 ? props.row.REQ_QTY-- : null"
              >
                <i class="ico qty-minus"></i>
              </button>
              <q-input
                v-model="props.row.REQ_QTY"
                class="q-table_number"
                borderless
                dense
                bg-color="white"
                input-class="text-center"
                reverse-fill-mask
                mask="###,###,###"
                @update:model-value="onUpdate(props)"
                @focus="(input) => input.target.select()"
              />
              <button
                type="button"
                class="btn_qty"
                @click="props.row.REQ_QTY++"
              >
                <i class="ico qty-plus"></i>
              </button>
            </div>
          </div>
        </q-td>
      </template>

      <!-- 담기/ -->
      <template v-slot:body-cell-ADD_BASKET="props">
        <q-td :props="props">
          <div class="q-table_cart_wrap" v-if="props.row.AVAIL_STOCK > 0">
            <BtnComp_active_white_small
              class="btn-line default inTbl-add"
              v-if="props.row.G_DLV_BRCH_FLAG !== 'G'"
              btn_activeWhiteLabel="담기"
              @click="onRowClick(props)"
            />
            <BtnComp_active_small
              class="btn-line default inTbl-dlv"
              v-if="props.row.G_DLV_BRCH_FLAG === 'G'"
              btn_active-label="광역"
              @click="onRowClick(props)"
            />
          </div>
        </q-td>
      </template>
      <!-- 이력/ -->
      <template v-slot:body-cell-HISTORY_YN="props">
        <q-td :props="props">
          <div>
            <q-checkbox
              class="q-table_checkbox chk-history"
              :checked-icon="mdiCheck"
              v-model="props.row.HISTORY_YN"
              true-value="Y"
              false-value="N"
              size="xs"
              disable
            />
          </div>
        </q-td>
      </template>
      <!-- 관심/ -->
      <template v-slot:body-cell-FAVORITE_YN="props">
        <q-td :props="props">
          <div>
            <q-checkbox
              v-model="props.row.FAVORITE_YN"
              class="q-table_checkbox_heart"
              :checked-icon="mdiHeart"
              :unchecked-icon="mdiHeart"
              size="xs"
              true-value="Y"
              false-value="N"
              @update:model-value="(val) => onCheckInterest(props.row, val)"
            />
          </div>
        </q-td>
      </template>
    </q-table>
    <RetrunMemoPopup
      :is-open="isOpenRetrunMemo"
      :text="takebackInfo"
      @close="isOpenRetrunMemo = false"
    />
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import {
  ref,
  inject,
  computed,
  onMounted,
  onBeforeMount,
  nextTick,
  toRaw,
  unref,
} from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { mdiCheck, mdiHeart, mdiHeartOutline } from "@mdi/js";

// import RadioComp_a from "src/components/web/common/RadioComp_a.vue";
// import SearchComp from "src/components/web/common/SearchComp.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_active_small from "src/components/web/common/BtnComp_active_small.vue";
import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
// import CheckComp_a from "src/components/web/common/CheckComp_a.vue";
// import EAComp from "src/components/web/common/EAComp.vue";
import RetrunMemoPopup from "src/components/web/order/ReturnMemoPopup.vue";
import SearchComp_select from "src/components/web/common/SearchComp_select.vue";
import BtnComp_active_white_small from "src/components/web/common/BtnComp_active_white_small.vue";
import _ from "lodash";

const bus = inject("bus");
const userInfo = util.getUserData();
const isHideBottom = computed(() => rows.value.length > 0);
const loading = ref(false);
const isOpenRetrunMemo = ref(false);
const takebackInfo = ref("");
const selected = ref([]);
const searched = ref(false);
const tableRef = ref(null);
const navigationActive = ref(false);

const listOption = [
  { label: "전체", value: "0" },
  { label: "전문급여", value: "1" },
  { label: "전문비급여", value: "2" },
  { label: "일반급여", value: "3" },
  { label: "일반비급여", value: "4" },
];
onMounted(async () => {
  await fetchItemList();
  await bus.emit("onLoadGetAutoCompleteData");
});

const searchParam = ref({
  keyword: "",
  makerNm: "",
  history: "N",
  excludingOutOfOtock: "N",
  custCd: userInfo.CUST_CD,
  custGbCd: userInfo.CUST_GB_CD,
  ordMakerCd: userInfo.ORD_MAKER_CD,
  userGbCd: userInfo.USER_GB_CD,
  ing: "N",
  eff: "N",
  ingno: "",
  effno: "",
  searchAll: "Y",
  professionalYn: "N",
  generalYn: "N",
  paymentYn: "N",
  nonPaymentYn: "N",
  searchOption: "0",
});

const basketData = ref({
  basketGbCd: "01",
  saveItemCd: "",
  saveItemGbCd: "",
  dlvBrchCd: "",
  saveItemQty: "",
  userId: userInfo.USER_ID,
  custCd: userInfo.CUST_CD,
});
const rowRefs = ref([]);
//---------------------------- Function ------------------------------//

async function fetchItemList() {
  loading.value = true;
  const res = await api.get("/ani/itemSearch_ani", {
    params: searchParam.value,
  });
  rows.value = res;
  searched.value = true;
  loading.value = false;

  // 출고이력, 제품정보 그리드 초기화
  bus.emit("init");

  // 검색결과 있으면 첫번째 row 정보 뿌려줌(출고이력, 제품정보)
  if (rows.value.length > 0) {
    await bus.emit("historyList", rows.value[0]);
    await bus.emit("itemDetailInfo", rows.value[0]);

    searchParam.value.ingno = rows.value[0].ITEM_INGREDIENT_CD;
    searchParam.value.effno = rows.value[0].ITEM_KIND_NO;

    const index = selected.value.indexOf(rows.value[0]);

    if (index === -1) {
      selected.value = [];
      selected.value.push(rows.value[0]);
    } else {
      selected.value.splice(index, 1);
    }
  }
}

// 동일성분 버튼 클릭
async function onClickIng() {
  searchParam.value.ing = "Y";
  searchParam.value.eff = "N";
  searchParam.value.keyword = "";

  if (searchParam.value.ingno === "") {
    searchParam.value.ingno = "AAAAAAAAAAAAAA";
  }

  await fetchItemList();
}

// 동일효능 버튼 클릭
async function onClickEff() {
  searchParam.value.ing = "N";
  searchParam.value.eff = "Y";
  searchParam.value.keyword = "";

  if (searchParam.value.effno === "") {
    searchParam.value.effno = "AAAAAAAAAAAA";
  }

  await fetchItemList();
}

// 검색 버튼 클릭
async function onClickItemSearch() {
  //2024-05-27 자동완성기능 추가 searchParam.value.keyword = filterText.value;
  searchParam.value.keyword = filterText.value;
  const keyword = searchParam.value.keyword;
  const makerNm = searchParam.value.makerNm;
  const searchAll = searchParam.value.searchAll;
  const generalYn = searchParam.value.generalYn;
  const paymentYn = searchParam.value.paymentYn;
  const nonPaymentYn = searchParam.value.nonPaymentYn;
  const professionalYn = searchParam.value.professionalYn;

  if (
    searchAll === "N" &&
    generalYn === "N" &&
    paymentYn === "N" &&
    nonPaymentYn === "N" &&
    professionalYn === "N"
  ) {
    alert("검색 유형을 하나 이상 체크해주세요.");
    return;
  }

  searchParam.value.ing = "N";
  searchParam.value.ingno = "AAAAAAAAAAAAAA";
  searchParam.value.eff = "N";
  searchParam.value.effno = "AAAAAAAAAAAA";

  await fetchItemList();
}

// 주문 영역 체크박스 클릭
function onClicksearchParamCheck(val) {
  const searchAll = searchParam.value.searchAll;
  const professionalYn = searchParam.value.professionalYn;
  const generalYn = searchParam.value.generalYn;
  const paymentYn = searchParam.value.paymentYn;
  const nonPaymentYn = searchParam.value.nonPaymentYn;

  if (val === "all") {
    if (searchAll === "Y") {
      searchParam.value.professionalYn = "N";
      searchParam.value.generalYn = "N";
      searchParam.value.paymentYn = "N";
      searchParam.value.nonPaymentYn = "N";
    } else {
      searchParam.value.professionalYn = "N";
      searchParam.value.generalYn = "N";
      searchParam.value.paymentYn = "N";
      searchParam.value.nonPaymentYn = "N";
    }
  } else {
    if ([professionalYn, generalYn, paymentYn, nonPaymentYn].includes("Y")) {
      searchParam.value.searchAll = "N";
    } else {
      searchParam.value.searchAll = "Y";
    }
  }
}

// 장바구니 담기
async function addBasket(row) {
  basketData.value.saveItemCd = row.ITEM_CD;
  basketData.value.saveItemGbCd = row.ITEM_GB_CD;
  basketData.value.dlvBrchCd = row.G_DLV_BRCH_CD;

  basketData.value.saveItemQty =
    util.getNumberFormat2(row.REQ_QTY) === 0
      ? 1
      : util.getNumberFormat2(row.REQ_QTY);

  if (basketData.value.saveItemQty == 0) {
    alert("수량을 선택해 주시기 바랍니다.");
    return;
  }

  await api.post("/ani/addBasket_ani", basketData.value);

  util.showNotify(
    `${row.ITEM_NM} ${basketData.value.saveItemQty}개를 장바구니에 담았습니다.`
  );
  await bus.emit("refreshHeaderBasket");
  await bus.emit("refreshBasket");
  inputRef.value.focus();
}

const isSelected = (row) => {
  return selected.value.includes(row);
};

// 그리드 클릭
async function onRowClick(row) {
  const fieldNm = row.col.field;

  const index = selected.value.indexOf(row.row);
  if (index === -1) {
    selected.value = [];
    selected.value.push(row.row);
  } else {
    selected.value.splice(index, 1);
  }

  // 담기
  if (fieldNm === "ADD_BASKET") {
    row.row.REQ_QTY = util.getNumberFormat2(row.row.REQ_QTY);

    if (row.row.REQ_QTY === 0 || row.row.REQ_QTY === "") {
      alert("수량을 선택해 주시기 바랍니다.");
      return;
    }

    if (row.row.AVAIL_STOCK < row.row.REQ_QTY) {
      alert(
        `${row.row.ITEM_NM} 재고수량(${row.row.AVAIL_STOCK})이 초과되었습니다.`
      );
      row.row.REQ_QTY = util.getNumberFormat(row.row.REQ_QTY);

      return;
    }

    //담기 시 장바구니 체크
    const res = await api.post("/ani/getBasketDupOrd2_ani", {
      custCd: userInfo.CUST_CD,
      basketGbCd: "01",
      saveItemCd: row.row.ITEM_CD,
      gDlvBrchCd: row.row.G_DLV_BRCH_CD,
    });

    if (res.ITEM_CNT == 0) {
      if (basketQty.value >= 100) {
        alert(
          "장바구니에 100개 품목을 초과하여 담을 수 없습니다. 기존 품목 주문등록 후 다시 담기 바랍니다"
        );
        row.row.REQ_QTY = 0;
        return;
      }
    } else {
      const confirmMsg =
        "장바구니에 이미 담은 품목입니다. 수량 추가하시겠습니까?";
      if (!confirm(confirmMsg)) return;
    }

    if (row.row.G_DLV_BRCH_FLAG === "G") {
      alert("광역 주문 상품의 경우 배송에 2일 이내 소요될 수 있습니다.");

      const res = await api.post("/ani/getBasketDupOrd_ani", {
        custCd: userInfo.CUST_CD,
        brchCd: userInfo.BRCH_CD,
        basketGbCd: "01",
        saveItemCd: row.row.ITEM_CD,
        saveItemGbCd: row.row.ITEM_GB_CD,
        gDlvBrchCd: row.row.G_DLV_BRCH_CD,
      });

      if (res.CNT > 0) {
        alert(
          "동일한 품목이 장바구니에 담겼습니다.\n다시 확인하시기 바랍니다."
        );

        row.row.REQ_QTY = 0;
        return;
      }
    }

    await addBasket(row.row);

    row.row.REQ_QTY = 0;
  }
  // 반품
  else if (fieldNm === "WEB_BACK_USE_YN") {
    if (row.row.WEB_BACK_USE_YN === "Y" || row.row.TAKEBACK_LIMIT_YN === "Y") {
      takebackInfo.value = row.row.TAKEBACK_INFO;
      isOpenRetrunMemo.value = true;
    } else {
      return;
    }
  } else {
    searchParam.value.ingno = row.row.ITEM_INGREDIENT_CD;
    searchParam.value.effno = row.row.ITEM_KIND_NO;

    await bus.emit("historyList", row.row); // 제품출고이력
    await bus.emit("itemDetailInfo", row.row); // 제품정보
  }
}

// 관심
async function onCheckInterest(row, val) {
  try {
    const interestItem = {
      userId: userInfo.USER_ID,
      custCd: userInfo.CUST_CD,
      itemCd: row.ITEM_CD,
    };

    if (val === "Y") {
      await api.post("/interest/addInterest", interestItem);
      util.showNotify("관심상품이 추가되었습니다.");
    } else {
      await api.delete("/interest/removeInterest", { params: interestItem });
      util.showNotify("관심상품이 삭제되었습니다.");
    }

    await bus.emit("refreshBasket");

    // await fetchBasketList();
  } catch (error) {
    alert(error.response.data);
  }
}

/******** 수량 event ********/
// 수량에 숫자 외 입력 방지
function updateCount(props, evt) {
  const isNotNull = evt.target.value !== "" ? true : false;

  if (!isNaN(evt.target.value) && isNotNull) {
    props.row.REQ_QTY = util.getNumberFormat(parseInt(evt.target.value));
  } else {
    props.row.REQ_QTY = 0;
  }
}

const onUpdate = (val) => {
  if (val.col.name == "REQ_QTY") {
    let newValue = val.row.REQ_QTY.replace(/,/g, "");
    newValue = newValue.replace(/^0+(?!$)/, "");
    newValue = parseInt(newValue);
    val.row.REQ_QTY = newValue;
  }
};

// 수량 증가
function increment(props) {
  const index = selected.value.indexOf(props.row);
  if (index === -1) {
    selected.value = [];
    selected.value.push(props.row);
  } else {
    selected.value.splice(index, 1);
  }
  if (props.row.REQ_QTY > 0) {
    props.row.REQ_QTY++;
  } else {
    props.row.REQ_QTY = 0;
    props.row.REQ_QTY++;
  }
}

// 수량 감소
function decrement(props) {
  const index = selected.value.indexOf(props.row);
  if (index === -1) {
    selected.value = [];
    selected.value.push(props.row);
  } else {
    selected.value.splice(index, 1);
  }

  if (props.row.REQ_QTY > 0) {
    props.row.REQ_QTY--;
  }
}

// 수량 disable(재고 품절일 경우)
function isDisableReqQty(val) {
  if (val === 0) {
    return true;
  } else {
    return false;
  }
}

bus.on("refreshOrderList", async () => {
  if (rows.value.length > 0) {
    await fetchItemList();
  }
});

// 다빈도에서 동일성분, 동일효능 클릭 시
bus.on("clickIng", async (data) => {
  searchParam.value.ingno = data.ingno === "" ? "AAAAAAAAAAAAAA" : data.ingno;
  searchParam.value.ing = "Y";
  searchParam.value.eff = "N";

  await fetchItemList();
});

bus.on("clickEff", async (data) => {
  searchParam.value.effno = data.effno === "" ? "AAAAAAAAAAAA" : data.effno;
  searchParam.value.ing = "N";
  searchParam.value.eff = "Y";

  await fetchItemList();
});

//자동완성
bus.on("getAutoCompleteData", async (data) => {
  if (data) {
    //2024-05-27 자동완성기능 추가 itemNmData => itmes
    items.value = await data.map((obj) => obj.ITEM_NM);
  }
});

const inputRef = ref("");

const basketQty = ref("");
onBeforeMount(async () => {
  await bus.emit("onLoadGetAutoCompleteData");

  bus.on("BasketQty", (data) => {
    basketQty.value = data;
    // console.log("마운트시 장바구니 품목수:  "+basketQty.value)
  });
  inputRef.value.focus();
});

//-------------------------- Grid Setting ----------------------------//

const columns = [
  {
    name: "ITEM_NM",
    label: "품목",
    sortable: true,
    align: "left",
    field: "ITEM_NM",
    classes: "td-prd",
  },
  {
    name: "UNIT",
    label: "규격",
    field: "UNIT",
    align: "right",
    classes: "td-unit",
  },
  {
    name: "WEB_BACK_USE_YN",
    label: "반품",
    field: "WEB_BACK_USE_YN",
    align: "center",
    classes: "td-return",
  },
  {
    name: "MAKER_NM",
    label: "제약회사",
    field: "MAKER_NM",
    align: "left",
    classes: "td-comp",
    sortable: true,
  },
  {
    name: "ORD_WP2_AMT",
    label: "단가",
    field: "ORD_WP2_AMT",
    align: "right",
    classes: "td-price",
    format: (val) => util.getNumberFormat(val),
  },
  {
    name: "AVAIL_STOCK",
    label: "재고",
    field: "AVAIL_STOCK",
    align: "right",
    classes: "td-inven",
    format: (val) => util.getNumberFormat(val),
  },
  {
    name: "REQ_QTY",
    label: "수량",
    field: "REQ_QTY",
    align: "center",
    classes: "td-qty",
  },
  {
    name: "ADD_BASKET",
    label: "담기",
    field: "ADD_BASKET",
    align: "center",
    classes: "td-add",
  },
  {
    name: "HISTORY_YN",
    label: "이력",
    field: "HISTORY_YN",
    align: "center",
    classes: "td-hist",
    sortable: true,
  },
  {
    name: "FAVORITE_YN",
    label: "관심",
    field: "FAVORITE_YN",
    align: "center",
    classes: "td-wish",
    sortable: true,
  },
];

const rows = ref([]);
// 1024이하일 때 테이블 필드 순서 변경 추가 240214
const smScrn = ref(false);
const tnScrn = ref(false);
const computedColumns = computed(() => {
  if (smScrn.value) {
    // 1024px 이하에서 열 순서 변경
    return [
      {
        name: "ITEM_NM",
        label: "품목",
        sortable: true,
        align: "left",
        field: "ITEM_NM",
        classes: "td-prd",
      },
      {
        name: "UNIT",
        label: "규격",
        field: "UNIT",
        align: "right",
        classes: "td-unit",
      },
      {
        name: "REQ_QTY",
        label: "수량",
        field: "REQ_QTY",
        align: "center",
        classes: "td-qty",
      },
      {
        name: "ADD_BASKET",
        label: "담기",
        field: "ADD_BASKET",
        align: "center",
        classes: "td-add",
      },
      {
        name: "AVAIL_STOCK",
        label: "재고",
        field: "AVAIL_STOCK",
        align: "right",
        classes: "td-inven",
        format: (val) => util.getNumberFormat(val),
      },
      {
        name: "MAKER_NM",
        label: "제약회사",
        field: "MAKER_NM",
        align: "left",
        classes: "td-comp",
        sortable: true,
      },
      {
        name: "ORD_WP2_AMT",
        label: "단가",
        field: "ORD_WP2_AMT",
        align: "right",
        classes: "td-price",
        format: (val) => util.getNumberFormat(val),
      },
      {
        name: "WEB_BACK_USE_YN",
        label: "반품",
        field: "WEB_BACK_USE_YN",
        align: "center",
        classes: "td-return",
      },
      {
        name: "HISTORY_YN",
        label: "이력",
        field: "HISTORY_YN",
        align: "center",
        classes: "td-hist",
        sortable: true,
      },
      {
        name: "FAVORITE_YN",
        label: "관심",
        field: "FAVORITE_YN",
        align: "center",
        classes: "td-wish",
        sortable: true,
      },
    ];
  }
  // 1024px 초과의 경우 원래 열 배열을 반환
  return columns;
});
// 화면 크기를 체크하는 함수를 정의
const updateWindowSize = () => {
  smScrn.value = window.innerWidth <= 1400; // 화면 크기 체크
  tnScrn.value = window.innerWidth <= 830; // 화면 크기 체크
};
// 컴포넌트가 마운트될 때와 소멸될 때 이벤트 리스너를 설정
onMounted(() => {
  updateWindowSize(); // 초기 화면 크기 확인
  window.addEventListener("resize", updateWindowSize); // 크기 변화 감지
});
onBeforeMount(() => {
  window.removeEventListener("resize", updateWindowSize); // 클린업
});

//2024-05-27 자동완성기능
const itemRefs = ref([]);
const filterText = ref("");
const items = ref([]);
const filteredList = ref([]);
const selectedIndex = ref(-1);

const setItemRef = (el, index) => {
  if (el) {
    itemRefs.value[index] = el.$el; // q-item의 실제 DOM 요소 참조
  }
};

const onInput = (e) => {
  filterText.value = e.target.value; // 중요!!
  const query = filterText.value.toLowerCase();
  if (query.length < 2) {
    filteredList.value = [];
    selectedIndex.value = -1;
    return;
  }
  filteredList.value = items.value.filter((item) =>
    item.toLowerCase().includes(query)
  );
  selectedIndex.value = -1; // Reset the selected index whenever the input changes
};

const onArrowDown = () => {
  if (selectedIndex.value < filteredList.value.length - 1) {
    selectedIndex.value++;
    scrollToSelectedItem();
  }
};

const onArrowUp = () => {
  if (selectedIndex.value > 0) {
    selectedIndex.value--;
    scrollToSelectedItem();
  }
};

const onEnter = () => {
  if (
    selectedIndex.value >= 0 &&
    selectedIndex.value < filteredList.value.length
  ) {
    filterText.value = filteredList.value[selectedIndex.value];
    filteredList.value = [];
    selectedIndex.value = -1;
    onClickItemSearch();
  } else {
    filteredList.value = [];
    selectedIndex.value = -1;
  }
};

const onClose = () => {
  filterText.value = "";
  filteredList.value = [];
  selectedIndex.value = -1;
};

const onItemClick = (item, index) => {
  // console.log('mouse click===>', item)
  filterText.value = item;
  filteredList.value = [];
  selectedIndex.value = -1;
};

const onFocusOut = (event) => {
  if (!event.currentTarget.contains(event.relatedTarget)) {
    // console.log('Focus out of autocomplete container')
    filteredList.value = [];
    selectedIndex.value = -1;
  }
};

const scrollToSelectedItem = () => {
  nextTick(() => {
    const selectedItem = itemRefs.value[selectedIndex.value];
    // console.log("scrollToSelectedItem===>", selectedIndex.value, selectedItem);
    if (
      selectedItem /* && typeof selectedItem.scrollIntoView === 'function' */
    ) {
      selectedItem.scrollIntoView({ behavior: "smooth", block: "nearest" });
    }
  });
};

const activateNavigation = () => {
  navigationActive.value = true;
};

const deactivateNavigation = () => {
  navigationActive.value = false;
};

const onKey = (evt) => {
  if (
    navigationActive.value !== true ||
    [38, 40].indexOf(evt.keyCode) === -1 ||
    tableRef.value === null
  ) {
    return;
  }

  evt.preventDefault();

  const { computedRowsNumber, computedRows } = tableRef.value;

  if (computedRows.length === 0) {
    return;
  }

  const currentIndex =
    selected.value.length > 0
      ? computedRows.indexOf(toRaw(selected.value[0]))
      : -1;
  const lastIndex = computedRows.length - 1;
  let index = currentIndex;
  // (그리드 줄 포커싱 부분 - 20241007)
  switch (evt.keyCode) {
    case 38: // ArrowUp
      if (currentIndex <= 0) {
        index = 0;
      } else {
        index = currentIndex - 1;
      }
      break;
    case 40: // ArrowDown
      if (currentIndex >= lastIndex) {
        index = 0;
      } else {
        index = currentIndex + 1;
      }
      break;
  }

  selected.value = [computedRows[index]];
  scrollToRow(index);
  debouncedOnKey();
};

const debouncedOnKey = _.debounce(async function () {
  const row = selected.value[0];

  searchParam.value.ingno = row.ITEM_INGREDIENT_CD;
  searchParam.value.effno = row.ITEM_KIND_NO;

  await bus.emit("historyList", row); // 제품출고이력
  await bus.emit("itemDetailInfo", row); // 제품정보
}, 300);

const setRowRef = (index, el) => {
  //console.log("setRowRef===>", index, el);
  if (el) {
    rowRefs.value[index] = el;
  }
};

const scrollToRow = (index) => {
  const rowElement = rowRefs.value[index];
  //console.log("scrollToRow===>", rowElement);
  // Proxy 객체 내부의 실제 DOM 요소에 접근
  const actualElement = rowElement?.$el || rowElement;

  // 해당 인덱스의 행이 존재하면 스크롤 이동
  if (actualElement && actualElement.scrollIntoView) {
    actualElement.scrollIntoView({
      behavior: "smooth",
      block: "center",
    });
  }
};

// (그리드 줄 포커싱 부분 - 20241007)
// 선택된 행에 대한 클래스를 반환하는 함수
const getRowClass = (row) => {
  return selected.value.length > 0 &&
    toRaw(selected.value[0]).ITEM_CD === row.ITEM_CD &&
    toRaw(selected.value[0]).G_DLV_BRCH_FLAG === row.G_DLV_BRCH_FLAG
    ? "highlighted-row"
    : "";
};
</script>

<style scoped>
/* (그리드 줄 포커싱 부분 - 20241007)
 강조된 행에 대해 스타일을 지정 */
.highlighted-row {
  /* background-color: #eaeaea; */
  font-weight: bold;
}
</style>
<style lang="scss" scoped>
.flex_start {
  @include flex_start;
}
.orderTable_1a {
  // height: 490px;
  height: 385px;
  overflow-y: auto;
  @media screen and (max-width: 1025px) and (min-width: 831px) {
    height: 485px;
  }
  @media screen and (max-width: 830px) {
    height: auto;
    max-height: 385px;
  }
}
.q-table_radio_wrap {
  padding: 0 8px;
}
.q-table_header_sub_wrap {
  @include flex_between;
  width: 100%;
  // margin-left: 44px;
  // gap: 8px;

  @media screen and (max-width: 1540px) {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
}

.q-field__control-container {
  @include flex_center;
}
::v-deep .q-field__native {
  // font-size: 14px;
  @include fs-5;
}

::v-deep .q-checkbox__label {
  @include fs-6;
  @include fw-7;
  font-size: 15px;
}

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
  background-color: transparent;
}

// //자동완성기능 샘플 소스 Css
// .autocomplete-container {
//   position: relative;
//   display: inline-block;
//   width: 50%;
// }

// .autocomplete-list {
//   position: absolute;
//   // top: 100%;
//   // left: 0;
//   // right: 0;
//   z-index: 99;
//   background-color: white;
//   border: 1px solid #ccc;
//   border-top: none;
//   max-height: 300px;
//   max-width: 150px;
//   overflow-y: auto;
// }

.selected-item {
  background-color: #f0f0f0;
}

.custom-input {
  width: 80%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  transition: border-color 0.3s, box-shadow 0.3s;
}

.btn_area {
  @include flex_end;
  gap: 4px;
  @supports (-webkit-touch-callout: none) and (not (translate: none)) {
    > * + * {
      margin-left: 4px;
    }
  }
}

.q-table--dense .tbl_all .q-table {
  thead th,
  tbody td:not(.q-virtual-scroll__padding td) {
    padding: 0 4px !important;
  }
  th:first-child,
  td:first-child {
    padding-left: 5px !important;
  }
  th:last-child,
  td:last-child {
    padding-right: 5px !important;
  }
}

.q-table_header_sub_wrap {
  // $btn_area: 162px;
  // .srch_form {
  //   width: calc(100% - #{$btn_area});
  // }
  // .btn_area {
  //   width: $btn_area;
  // }
  gap: 8px;
  @media screen and (max-width: 1440px) and (min-width: 831px) {
    // $btn_area: 80px;
    .srch_form {
      // width: calc(100% - #{$btn_area});
      gap: 8px 4px;
      @supports (-webkit-touch-callout: none) and (not (translate: none)) {
        > * + * {
          margin-left: 4px;
        }
      }
    }
    .input_search1,
    .input_search2 {
      width: 100%;
    }
  }
}
</style>

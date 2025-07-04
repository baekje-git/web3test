<template>
  <div class="">
    <q-table
      class="returnTable_a"
      :rows="rows"
      :columns="computedColumns"
      row-key="ITEM_CD"
      table-header-class="table_header"
      dense
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
      :selected="selected"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="q-table_header_wrap">
            <!-- <div class="q-table_header_title">
              <p>반품</p>
            </div> -->
            <div class="q-table_header_sub_wrap">
              <div class="q-table_radio_wrap">
                <RadioComp_a
                  v-model="searchParam.returnCat"
                  :radio_options="returnRadioOptions"
                  @update:modelValue="(val) => onClickReturnCat(val)"
                ></RadioComp_a>
              </div>
              <div class="srch_form">
                <!-- <SearchComp
                v-model="searchParam.keyword"
                placeholder1="보험코드/품명입력"
                placeholder2="제약사 입력"
              /> -->
                <div class="SearchComp_wrap">
                  <!-- <q-select
                  v-model="searchParam.keyword"
                  use-input
                  fill-input
                  hide-dropdown-icon
                  input-class="input_search"
                  borderless
                  dense
                  placeholder="품목명/보험코드 입력"
                  :options="autoCompleteData"
                  @input-value="setKeyword"
                  @keyup.enter="onClickItemSearch"
                /> -->
                  <q-input
                    class="input_search1"
                    v-model.trim="searchParam.keyword"
                    borderless
                    dense
                    placeholder="품목명/보험코드 입력"
                    @keyup.enter="onClickItemSearch"
                    @focus="(input) => input.target.select()"
                    autofocus
                    ref="inputRef"
                  />
                  <!-- <div
                    class="autocomplete-container"
                    @focusout="onFocusOut"
                    tabindex="-1"
                  >
                    <input
                      type="text"
                      placeholder="품목명/보험코드 입력"
                      :value="filterText"
                      @input="onInput"
                      @keydown.down.prevent="onArrowDown"
                      @keydown.up.prevent="onArrowUp"
                      @keydown.enter.prevent="onEnter"
                      @keydown.esc="onClose"
                      class="input_search"
                      @focus="(input) => input.target.select()"
                      ref="inputRef"
                    />
                    <q-list v-if="filteredList.length" class="autocomplete-list">
                      <q-item
                        v-for="(item, index) in filteredList"
                        :key="item"
                        :ref="(el) => setItemRef(el, index)"
                        :class="{ 'selected-item': index === selectedIndex }"
                        clickable
                        @click="onItemClick(item, index)"
                      >
                        <q-item-section>
                          <q-item-label>{{ item }}</q-item-label>
                        </q-item-section>
                      </q-item>
                    </q-list>
                  </div> -->

                  <q-input
                    class="input_search2"
                    v-model.trim="searchParam.makerNm"
                    borderless
                    dense
                    placeholder="제약회사명 입력"
                    @keyup.enter="onClickItemSearch"
                    @focus="(input) => input.target.select()"
                  />
                </div>
                <div class="chk-wrap">
                  <q-checkbox
                    v-model="searchParam.history"
                    true-value="Y"
                    false-value="N"
                    label="이력"
                  />
                </div>
                <BtnComp_default
                  class="btn_srch"
                  btn-label="검색"
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

      <template v-slot:body-cell-품목="props">
        <q-td
          :props="props"
          :class="{ 'q-tr--selected': isSelected(props.row) }"
          @click="onRowClick(props)"
        >
          <div class="q-table_item">
            <div class="q-table_badge_wrap">
              <!-- <q-badge class="q-table_badge_red">
                {{ props.row.MED_NM }}
              </q-badge>
              <q-badge class="q-table_badge_blue">
                {{ props.row.PAY_ITEM_NM }}
              </q-badge> -->
              <q-badge class="bdg-line normal">
                {{
                  props.row.MED_NM === "일"
                    ? "일반"
                    : props.row.MED_NM === "전"
                    ? "전문"
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
              {{ props.value }}
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-규격="props">
        <q-td :props="props" @click="onRowClick(props)">
          <div class="q-table_itemTitleSub">
            {{ props.value }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-반품="props">
        <q-td :props="props" @click="onRowClick(props)">
          <div>
            <div
              v-if="props.row.WEB_BACK_USE_YN === 'Y'"
              class="q-table_return red-400"
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

      <template v-slot:body-cell-제약회사="props">
        <q-td :props="props" style="cursor: pointer" @click="onRowClick(props)">
          <div class="q-table_itemTitleSub ellipsis">
            {{ props.value }}
            <q-tooltip>{{ props.value }}</q-tooltip>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-단가="props">
        <q-td :props="props" @click="onRowClick(props)">
          <div style="font-size: 14px; text-align: right">
            {{ props.value }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-이력="props">
        <q-td :props="props" @click="onRowClick(props)">
          <div>
            <q-checkbox
              class="q-table_checkbox chk-history"
              :checked-icon="mdiCheck"
              v-model="props.row.HISTORY_YN"
              size="xs"
              true-value="Y"
              false-value="N"
              disable
            />
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-반품가능수량="props">
        <q-td :props="props" style="cursor: pointer" @click="onRowClick(props)">
          <div class="q-table_itemTitleSub">
            {{ props.value == "999" ? "" : props.value }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-수량="props">
        <q-td :props="props">
          <div
            v-if="
              props.row.LIMIT_QTY >= 999 ||
              props.row.SUBDIV_YN == 'Y' ||
              (props.row.HISTORY_YN == 'Y' &&
                props.row.LIMIT_QTY > 0 &&
                props.row.WEB_BACK_USE_YN != 'Y')
            "
          >
            <div class="qty_wrap">
              <button type="button" class="btn_qty" @click="minus(props)">
                <i class="ico qty-minus"></i>
              </button>
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
              <button type="button" class="btn_qty" @click="plus(props)">
                <i class="ico qty-plus"></i>
              </button>
            </div>
          </div>
          <div v-else></div>
        </q-td>
      </template>

      <template v-slot:body-cell-담기="props">
        <q-td :props="props">
          <div
            v-if="
              props.row.LIMIT_QTY >= 999 ||
              props.row.SUBDIV_YN == 'Y' ||
              (props.row.HISTORY_YN == 'Y' &&
                props.row.LIMIT_QTY > 0 &&
                props.row.WEB_BACK_USE_YN != 'Y')
            "
            class="q-table_cart_wrap td-add"
          >
            <BtnComp_active_white_small
              class="btn-line default inTbl-add"
              btn_activeWhiteLabel="담기"
              @click="onClickAddBakBasket(props.row)"
            />
          </div>
          <div v-else></div>
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

import { ref, inject, computed, onMounted, onBeforeMount, nextTick } from "vue";
import { mdiCheck } from "@mdi/js";
import { api } from "boot/axios";
import * as util from "src/support/util";

import RadioComp_a from "src/components/web/common/RadioComp_a.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import RetrunMemoPopup from "src/components/web/order/ReturnMemoPopup.vue";
import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
import BtnComp_active_white_small from "src/components/web/common/BtnComp_active_white_small.vue";
import SearchComp_select from "src/components/web/common/SearchComp_select.vue";
import EAComp from "../common/EAComp.vue";

const userInfo = util.getUserData();
const loading = ref(false);
const bus = inject("bus");
const isOpenRetrunMemo = ref(false);
const takebackInfo = ref("");
const selected = ref([]);
const searched = ref(false);

const searchParam = ref({
  keyword: "",
  makerNm: "",
  history: "Y",
  itemGbCd: "01",
  returnCat: "1",
  subdivYn: "",
  taxYn: "",
  retYn: "",
  custCd: userInfo.CUST_CD,
  custGbCd: userInfo.CUST_GB_CD,
  ordMakerCd: userInfo.ORD_MAKER_CD,
  userGbCd: userInfo.USER_GB_CD,
});

const returnRadioOptions = computed(() => {
  if (
    (userInfo.SUBDIV_BACK_YN === "Y" &&
      userInfo.CASH_LIMIT_BACK_YN === "Y" &&
      userInfo.CUST_GB_CD !== "03") ||
    userInfo.CLOSE_READY_YN === "Y"
  ) {
    return [
      { label: "일반반품", value: "1" },
      { label: "비과세반품", value: "3" },
      { label: "소분반품", value: "2" },
      { label: "소분반품(회수)", value: "4" },
    ];
  }

  return [
    { label: "일반반품", value: "1" },
    { label: "비과세반품", value: "3" },
    { label: "소분반품(회수)", value: "4" },
  ];

  // return [
  //   { label: "일반반품", value: "1" },
  //   { label: "비과세반품", value: "3" },
  //   { label: "소분반품", value: "2" },
  //   { label: "소분반품(회수)", value: "4" },
  // ];
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

const rows = ref([]);

const autoCompleteData = ref([]);
const itemNmData = ref([]);

const limitQty = ref(0);
const webBackUseYn = ref("N");
const subYN = ref(0);

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  loading.value = true;
  const res = await api.get("/bakRcpt/itemSearch", {
    params: searchParam.value,
  });

  searched.value = true;
  rows.value = res;
  loading.value = false;

  limitQty.value = res.LIMIT_QTY;

  // 출고이력, 제품정보 그리드 초기화
  await bus.emit("bakRcptInit");
  await bus.emit("init");

  // 검색결과 있으면 첫번째 row 정보 뿌려줌(출고이력, 제품정보)
  if (rows.value.length > 0) {
    rows.value.forEach((m) =>
      Object.assign(m, {
        RETURN_CAT: searchParam.value.returnCat,
        SUBDIV_YN: searchParam.value.subdivYn,
      })
    );

    await bus.emit("bakRcptHistoryList", {
      row: rows.value[0],
      returnCat: searchParam.value.returnCat,
    });
    await bus.emit("itemDetailInfo", rows.value[0]);
  }
}

// 검색 버튼 클릭
async function onClickItemSearch() {
  //자동완성기능
  //searchParam.value.keyword = filterText.value;
  const keyword = searchParam.value.keyword;
  const makerNm = searchParam.value.makerNm;
  const returnCat = searchParam.value.returnCat;

  bus.emit("initSmallBakQty");
  bus.emit("initEAount");

  if (keyword.length === 0 && makerNm.length === 0) {
    alert("보험코드/품명을 입력하세요.");
    return;
  } else if (keyword.length === 0 && makerNm.length !== 0) {
    alert("보험코드/품명과 같이 입력해야합니다.");
    return;
  } else if (keyword.length < 2) {
    alert("검색어는 2글자 이상이어야 합니다.");
    return;
  }

  if (returnCat === "1") {
    // 일반반품
    searchParam.value.subdivYn = "N";
    searchParam.value.taxYn = "Y";
    searchParam.value.retYn = "N";
  } else if (returnCat === "2") {
    // 소분반품
    searchParam.value.subdivYn = "Y";
    searchParam.value.taxYn = "Y";
    searchParam.value.retYn = "N";
  } else if (returnCat === "3") {
    // 비과세반품
    searchParam.value.subdivYn = "N";
    searchParam.value.taxYn = "N";
    searchParam.value.retYn = "N";
  } else if (returnCat === "4") {
    // 소분반품(회수)
    searchParam.value.subdivYn = "Y";
    searchParam.value.taxYn = "Y";
    searchParam.value.retYn = "Y";
  } else if (returnCat === "6") {
    searchParam.value.subdivYn = "Y";
    searchParam.value.taxYn = "Y";
    searchParam.value.retYn = "N";
  }

  await fetchItemList();
}

const isSelected = (row) => {
  return selected.value.includes(row);
};

// 그리드 클릭
async function onRowClick(row) {
  bus.emit("initSmallBakQty");
  bus.emit("initEAount");

  const fieldNm = row.col.field;

  const index = selected.value.indexOf(row.row);
  if (index === -1) {
    selected.value = [];
    selected.value.push(row.row);
  } else {
    selected.value.splice(index, 1);
  }

  if (fieldNm === "WEB_BACK_USE_YN") {
    if (row.row.WEB_BACK_USE_YN === "Y" || row.row.TAKEBACK_LIMIT_YN === "Y") {
      takebackInfo.value = row.row.TAKEBACK_INFO;
      isOpenRetrunMemo.value = true;
    }
  }

  Object.assign(row.row, {
    RETURN_CAT: searchParam.value.returnCat,
    SUBDIV_YN: searchParam.value.subdivYn,
  });

  await bus.emit("bakRcptHistoryList", {
    row: row.row,
    returnCat: searchParam.value.returnCat,
  });
  await bus.emit("itemDetailInfo", row.row);
}

// 자동완성
bus.on("getAutoCompleteData", async (data) => {
  if (data) {
    items.value = await data.map((obj) => obj.ITEM_NM);
  }
});

// function setKeyword(val) {
//   searchParam.value.keyword = val;

//   if (val !== "") {
//     const needle = val.toLocaleLowerCase();
//     autoCompleteData.value = itemNmData.value.filter(
//       (v) => v.toLocaleLowerCase().indexOf(needle) > -1
//     );
//   } else {
//     autoCompleteData.value = [];
//   }
// }

// 소분반품, 소분반품(회수) 선택 시 이력 체크 해제
function onClickReturnCat(val) {
  bus.emit("initSmallBakQty");
  bus.emit("initEAount");

  searchParam.value.returnCat = val;

  if (val === "2" || val === "4") {
    searchParam.value.history = "N";
  } else {
    searchParam.value.history = "Y";
  }

  bus.emit("checkReturnCat", val);
}
// watch(searchParam.value, () => {
//   const returnCat = searchParam.value.returnCat;

//   if (returnCat === "2" || returnCat === "4") {
//     searchParam.value.history = "N";
//   } else {
//     searchParam.value.history = "Y";
//   }
// });

// 담기
async function onClickAddBakBasket(row) {
  if (isNaN(row.REQ_QTY) || util.getNumberFormat2(row.REQ_QTY) == 0) {
    alert("수량을 선택해 주시기 바랍니다.");
    row.REQ_QTY = 0;
    return;
  }

  if (row.LIMIT_QTY < util.getNumberFormat2(row.REQ_QTY)) {
    alert("반품 가능한 수량을 초과할 수 없습니다.");
    return;
  }

  if (row.BASKET_QTY != "") {
    const qtySum =
      Number(util.getNumberFormat2(row.BASKET_QTY)) +
      Number(util.getNumberFormat2(row.REQ_QTY));
    // console.log(qtySum);

    if (row.LIMIT_QTY < qtySum) {
      alert("반품 가능한 수량을 초과할 수 없습니다.");
      return;
    }
  }

  if (row.RETURN_CAT === "2" || row.RETURN_CAT === "6") {
    basketData.value.basketGbCd = "03";
  } else {
    basketData.value.basketGbCd = "02";
  }

  basketData.value.saveItemCd = row.ITEM_CD;
  basketData.value.subdivYn = row.SUBDIV_YN;
  basketData.value.itemGbCd = "01";
  basketData.value.saveItemQty = util.getNumberFormat2(row.REQ_QTY);

  await api.post("/bakRcpt/addBasket", basketData.value);
  bus.emit("refreshHeaderBasket"); // 헤더 카운트 새로고침

  util.showNotify(
    `${row.ITEM_NM} ${basketData.value.saveItemQty}개를 장바구니에 담았습니다.`
  );

  await fetchItemList();
  await bus.emit("refreshRetrunBasket");
}
// 반품바구니에서 삭제 시 제품 출고 이력 재조회
bus.on("refreshReturnList", async () => {
  await fetchItemList();
});

// 수량 event //
function plus(props) {
  props.row.REQ_QTY++;
}

function minus(props) {
  if (props.row.REQ_QTY > 0) {
    props.row.REQ_QTY--;
  }
}
const onUpdate = (val) => {
  if (val.col.name == "수량") {
    let newValue = val.row.REQ_QTY.replace(/,/g, "");
    newValue = newValue.replace(/^0+(?!$)/, "");
    newValue = parseInt(newValue);
    val.row.REQ_QTY = newValue;
  }
};
const inputRef = ref("");

onMounted(async () => {
  await bus.emit("onLoadGetAutoCompleteData");
  inputRef.value.focus();
});

//-------------------------- Grid Setting ----------------------------//

const columns = [
  {
    name: "품목",
    label: "품목",
    align: "left",
    sortable: true,
    required: true,
    field: "ITEM_NM",
    classes: "td-prd",
  },
  {
    name: "규격",
    label: "규격",
    field: "UNIT",
    align: "right",
    classes: "td-unit",
  },
  {
    name: "반품",
    label: "반품",
    field: "WEB_BACK_USE_YN",
    align: "center",
    classes: "td-return",
  },
  {
    name: "제약회사",
    label: "제약회사",
    field: "MAKER_NM",
    align: "left",
    classes: "td-comp",
    sortable: true,
  },
  {
    name: "단가",
    label: "단가",
    field: "ORD_WP2_AMT",
    align: "right",
    classes: "td-price",
    format: (val) => util.getNumberFormat(val),
  },
  {
    name: "이력",
    label: "이력",
    field: "HISTORY_YN",
    align: "center",
    classes: "td-hist",
    sortable: true,
  },
  {
    name: "반품가능수량",
    label: "반품 가능수량",
    field: "LIMIT_QTY",
    align: "center",
    classes: "td-qty_limit",
  },
  {
    name: "수량",
    label: "수량",
    field: "REQ_QTY",
    align: "center",
    classes: "td-qty",
  },
  {
    name: "담기",
    label: "담기",
    field: "ADD_BASKET",
    align: "center",
    classes: "td-add",
  },
];
// 1024이하일 때 테이블 필드 순서 변경 추가 240214
const smScrn = ref(false);
const tnScrn = ref(false);
const computedColumns = computed(() => {
  if (smScrn.value) {
    // 1024px 이하에서 열 순서 변경
    return [
      {
        name: "품목",
        label: "품목",
        align: "left",
        sortable: true,
        required: true,
        field: "ITEM_NM",
        classes: "td-prd",
      },
      {
        name: "규격",
        label: "규격",
        field: "UNIT",
        align: "right",
        classes: "td-unit",
      },
      {
        name: "수량",
        label: "수량",
        field: "REQ_QTY",
        align: "center",
        classes: "td-qty",
      },
      {
        name: "담기",
        label: "담기",
        field: "ADD_BASKET",
        align: "center",
        classes: "td-add",
      },
      {
        name: "반품",
        label: "반품",
        field: "WEB_BACK_USE_YN",
        align: "center",
        classes: "td-return",
      },
      {
        name: "제약회사",
        label: "제약회사",
        field: "MAKER_NM",
        align: "left",
        classes: "td-comp",
        sortable: true,
      },
      {
        name: "단가",
        label: "단가",
        field: "ORD_WP2_AMT",
        align: "right",
        classes: "td-price",
        format: (val) => util.getNumberFormat(val),
      },
      {
        name: "이력",
        label: "이력",
        field: "HISTORY_YN",
        align: "center",
        classes: "td-hist",
        sortable: true,
      },
      {
        name: "반품가능수량",
        label: "반품 가능수량",
        field: "LIMIT_QTY",
        align: "center",
        classes: "td-qty_limit",
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

//자동완성기능
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
</script>

<style lang="scss" scoped>
.q-field__control-container {
  @include flex_center;
}
.returnTable_a {
  // height: 340px;
  // height: 490px;
  height: 385px;
  overflow-y: auto;
  @media screen and (max-width: 830px) {
    height: auto;
    max-height: 385px;
  }
}
.q-table_header_wrap {
  // display: grid;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
  // grid-template-columns: repeat(2, auto);
  @media screen and (max-width: 1500px) {
    flex-direction: column;
    align-items: flex-start;
  }
}
.q-table_radio_wrap {
  padding: 0;
}

// .selected-item {
//   background-color: #f0f0f0;
// }

.custom-input {
  width: 80%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  transition: border-color 0.3s, box-shadow 0.3s;
}

.EAComp_wrap {
  @include flex_center;
  width: 75px;
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
.input_search {
  @include fs-5;
  width: 140px;
  font-size: 14px;
  height: 32px;
  border: 1px solid $line-400;
  background-color: #fff;
  padding: 4px 6px;
  border-radius: 0px;
}

.q-table_header_sub_wrap {
  // flex-wrap: nowrap;
  justify-content: flex-start;
  align-items: center;
  gap: 8px;
  .srch_form {
    width: auto;
  }
  @media screen and (max-width: 1440px) {
    flex-wrap: wrap;
    .SearchComp_wrap {
      width: calc(100% - 144px);
    }
  }
}
</style>

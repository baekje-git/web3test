<template>
  <div class="">
    <q-table
      class="orderTable_1b"
      :rows="rows"
      :columns="columns"
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
            <div class="q-table_header_title">
              <p>다빈도</p>
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
                  size="40px"
                  inline
                  dense
                  true-value="Y"
                  false-value="N"
                  label="전체"
                  @update:model-value="onClicksearchParamCheck('all')"
                />
                <q-checkbox
                  v-model="searchParam.professionalYn"
                  color="blue-10"
                  size="40px"
                  style="margin-left: 8px"
                  inline
                  dense
                  true-value="Y"
                  false-value="N"
                  label="전문"
                  @update:model-value="onClicksearchParamCheck"
                />
                <q-checkbox
                  v-model="searchParam.generalYn"
                  color="blue-10"
                  size="40px"
                  style="margin-left: 8px"
                  inline
                  dense
                  true-value="Y"
                  false-value="N"
                  label="일반"
                  @update:model-value="onClicksearchParamCheck"
                />
                <q-checkbox
                  v-model="searchParam.paymentYn"
                  color="blue-10"
                  size="40px"
                  style="margin-left: 8px"
                  inline
                  dense
                  true-value="Y"
                  false-value="N"
                  label="급여"
                  @update:model-value="onClicksearchParamCheck"
                />
                <q-checkbox
                  v-model="searchParam.nonPaymentYn"
                  color="blue-10"
                  size="40px"
                  style="margin-left: 8px"
                  inline
                  dense
                  true-value="Y"
                  false-value="N"
                  label="비급여"
                  @update:model-value="onClicksearchParamCheck"
                />
              </div>
            </div>
          </div>
          <div
            class="q-table_header_sub_wrap"
            style="justify-content: space-between"
          >
            <div class="flex_start">
              <!-- <SearchComp
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
                  v-model="searchParam.keyword"
                  input-class="input_search"
                  borderless
                  dense
                  placeholder="품목명/보험코드 입력"
                  @keyup.enter="onClickItemSearch"
                  autofocus
                  @focus="(input) => input.target.select()"
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
                    autofocus
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
                  v-model="searchParam.makerNm"
                  input-class="input_search"
                  borderless
                  dense
                  placeholder="제약회사명 입력"
                  @keyup.enter="onClickItemSearch"
                  @focus="(input) => input.target.select()"
                />
              </div>
              <BtnComp_default
                style="margin-left: 8px"
                btn-label="검색"
                @click="onClickItemSearch"
              />
              <!-- <div>
                <q-checkbox
                  v-model="searchParam.excludingOutOfOtock"
                  true-value="Y"
                  false-value="N"
                  label="품절제외"
                />
              </div> -->
            </div>
            <div class="flex_center" style="gap: 8px">
              <BtnComp_default btn-label="동일성분" @click="onClickIng" />
              <BtnComp_default btn-label="동일효능" @click="onClickEff" />
              <BtnComp_default btn-label="엑셀" @click="exportTable" />
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

      <template v-slot:header-cell-AVG_QTY="props">
        <q-th class="">
          {{ props.cols[4].label }}<br />
          주문량
        </q-th>
      </template>

      <template v-slot:header-cell-ITEM_QTY_3="props">
        <q-th class="">
          {{ props.cols[5].label }}<br />
          주문량
        </q-th>
      </template>

      <template v-slot:header-cell-ITEM_QTY_2="props">
        <q-th class="">
          {{ props.cols[6].label }}<br />
          주문량
        </q-th>
      </template>

      <template v-slot:header-cell-ITEM_QTY_1="props">
        <q-th class="">
          {{ props.cols[7].label }}<br />
          주문량
        </q-th>
      </template>

      <template v-slot:body-cell-ITEM_NM="props">
        <q-td
          :props="props"
          :class="{ 'q-tr--selected': isSelected(props.row) }"
          style="padding: 0; cursor: pointer"
          @click="onRowClick(props)"
        >
          <div class="q-table_item">
            <div class="q-table_badge_wrap">
              <q-badge class="q-table_badge_red">
                {{ props.row.MED_NM }}
              </q-badge>
              <q-badge class="q-table_badge_blue">
                {{ props.row.PAY_ITEM_NM }}
              </q-badge>
            </div>
            <div
              class="q-table_itemTitle ellipsis"
              style="min-width: 95px; min-width: 150px; max-width: 200px"
            >
              {{ props.value }}
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-UNIT="props">
        <q-td
          :props="props"
          style="padding: 0; cursor: pointer"
          @click="onRowClick(props)"
        >
          <div style="font-size: 14px">
            {{ props.value }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-MAKER_NM="props">
        <q-td
          :props="props"
          style="padding: 0; cursor: pointer; text-align: center"
          @click="onRowClick(props)"
        >
          <div class="q-table_item">
            <div class="q-table_itemTitleSub ellipsis" style="max-width: 60px">
              {{ props.value }}
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ORD_WP2_AMT="props">
        <q-td
          :props="props"
          style="padding: 0; text-align: right; cursor: pointer"
          @click="onRowClick(props)"
        >
          {{ props.value }}
        </q-td>
      </template>

      <template v-slot:body-cell-AVG_QTY="props">
        <q-td :props="props" style="cursor: pointer" @click="onRowClick(props)">
          <div class="q-table_item">
            <div class="q-table_itemTitleSub">
              {{ props.value }}
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_QTY_3="props">
        <q-td :props="props" style="cursor: pointer" @click="onRowClick(props)">
          <div class="q-table_item">
            <div class="q-table_itemTitleSub">
              {{ props.value }}
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_QTY_2="props">
        <q-td :props="props" style="cursor: pointer" @click="onRowClick(props)">
          <div class="q-table_item">
            <div class="q-table_itemTitleSub">
              {{ props.value }}
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_QTY_1="props">
        <q-td :props="props" style="cursor: pointer" @click="onRowClick(props)">
          <div class="q-table_item">
            <div class="q-table_itemTitleSub">
              {{ props.value }}
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-AVAIL_STOCK="props">
        <q-td
          :props="props"
          style="padding: 0; cursor: pointer"
          @click="onRowClick(props)"
        >
          <div class="q-table_stock_wrap">
            <p v-if="props.row.AVAIL_STOCK > 0">
              {{ props.value }}
            </p>
            <p class="q-table_stock" v-else>품절</p>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-REQ_QTY="props">
        <q-td :props="props" style="padding: 0">
          <div class="q-table_EA_wrap">
            <!-- <EAComp v-model="props.row.REQ_QTY" /> -->
            <div class="EAComp_wrap">
              <div class="EA_minus" @click="decrement(props)">
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
              <div class="EA_plus" @click="increment(props)">
                <img src="assets/icons/plus-solid.svg" alt="plus" />
              </div>
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ADD_BASKET="props">
        <q-td :props="props" style="padding: 0">
          <div class="q-table_cart_wrap">
            <BtnComp_active_small
              v-if="props.row.G_DLV_BRCH_FLAG !== 'G'"
              btn_activeLabel="담기"
              style="padding: 0"
              @click="onRowClick(props)"
            />
            <BtnComp_default_small
              v-if="props.row.G_DLV_BRCH_FLAG === 'G'"
              btn_defaultLabel="광역"
              @click="onRowClick(props)"
            />
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-FAVORITE_YN="props">
        <q-td :props="props">
          <q-checkbox
            v-model="props.row.FAVORITE_YN"
            class="q-table_checkbox_heart"
            :checked-icon="mdiHeart"
            :unchecked-icon="mdiHeartOutline"
            size="xs"
            true-value="Y"
            false-value="N"
            @update:model-value="(val) => onCheckInterest(props.row, val)"
          />
        </q-td>
      </template>
    </q-table>
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject, computed, onMounted, onBeforeMount, nextTick } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { mdiHeart, mdiHeartOutline } from "@mdi/js";
import moment from "moment";
import { useRouter } from "vue-router";

// import RadioComp_a from "src/components/web/common/RadioComp_a.vue";
// import SearchComp from "src/components/web/common/SearchComp.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_active_small from "src/components/web/common/BtnComp_active_small.vue";
import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
// import CheckComp_a from "src/components/web/common/CheckComp_a.vue";
// import EAComp from "src/components/web/common/EAComp.vue";
import SearchComp_select from "../common/SearchComp_select.vue";

const bus = inject("bus");
const userInfo = util.getUserData();
const isHideBottom = computed(() => rows.value.length > 0);
const loading = ref(false);
const router = useRouter();
const selected = ref([]);
const searched = ref(false);

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
  searchAll: "N",
  professionalYn: "Y",
  generalYn: "N",
  paymentYn: "Y",
  nonPaymentYn: "N",
  today: moment().format("YYYYMMDD"),
});

const basketData = ref({
  basketGbCd: "01",
  saveItemCd: "",
  saveItemGbCd: "01",
  dlvBrchCd: "",
  saveItemQty: "",
  userId: userInfo.USER_ID,
  custCd: userInfo.CUST_CD,
});

const autoCompleteData = ref([]);
const itemNmData = ref([]);

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  loading.value = true;

  //searchParam.value.keyword = filterText.value;

  const res = await api.get("/freOrd/itemSearch", {
    params: searchParam.value,
  });

  searched.value = true;
  rows.value = res;
  loading.value = false;

  // 출고이력, 제품정보 그리드 초기화
  bus.emit("init");

  // 검색결과 있으면 첫번째 row 정보 뿌려줌(출고이력, 제품정보)
  if (rows.value.length > 0) {
    await bus.emit("historyList", rows.value[0]);
    await bus.emit("itemDetailInfo", rows.value[0]);

    searchParam.value.ingno = rows.value[0].ITEM_INGREDIENT_CD;
    searchParam.value.effno = rows.value[0].ITEM_KIND_NO;
  }

  const index = selected.value.indexOf(rows.value[0]);

  if (index === -1) {
    selected.value = [];
    selected.value.push(rows.value[0]);
  } else {
    selected.value.splice(index, 1);
  }
}

// 동일성분 버튼 클릭
async function onClickIng() {
  // searchParam.value.ing = "Y";
  // searchParam.value.eff = "N";
  // await fetchItemList();
  searchParam.value.keyword = "";

  await router.push({ path: "/comOrd" });
  await bus.emit("clickIng", searchParam.value);
}

// 동일효능 버튼 클릭
async function onClickEff() {
  // searchParam.value.ing = "N";
  // searchParam.value.eff = "Y";
  // await fetchItemList();
  searchParam.value.keyword = "";

  await router.push({ path: "/comOrd" });
  await bus.emit("clickEff", searchParam.value);
}

// 검색 버튼 클릭
async function onClickItemSearch() {
  searchParam.value.ing = "N";
  searchParam.value.ingno = "";
  searchParam.value.eff = "N";
  searchParam.value.effno = "";
  //searchParam.value.keyword = filterText.value;
  const keyword = searchParam.value.keyword;
  const makerNm = searchParam.value.makerNm;

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
      searchParam.value.professionalYn = "Y";
      searchParam.value.generalYn = "Y";
      searchParam.value.paymentYn = "Y";
      searchParam.value.nonPaymentYn = "Y";
    } else {
      searchParam.value.professionalYn = "N";
      searchParam.value.generalYn = "N";
      searchParam.value.paymentYn = "N";
      searchParam.value.nonPaymentYn = "N";
    }
  } else {
    if ([professionalYn, generalYn, paymentYn, nonPaymentYn].includes("N")) {
      searchParam.value.searchAll = "N";
    } else {
      searchParam.value.searchAll = "Y";
    }
  }
}

// 장바구니 담기
async function addBasket(row) {
  basketData.value.saveItemCd = row.ITEM_CD;
  basketData.value.dlvBrchCd = row.DLV_BRCH_CD;
  // basketData.value.saveItemQty = row.REQ_QTY === 0 ? 1 : row.REQ_QTY;

  if (row.REQ_QTY === 0 || row.REQ_QTY === "") {
    alert("수량을 선택해 주시기 바랍니다.");
    return;
  }

  //담기 시 장바구니 체크
  const res = await api.post("/ord/getBasketDupOrd2", {
    custCd: userInfo.CUST_CD,
    basketGbCd: "01",
    saveItemCd: row.ITEM_CD,
    gDlvBrchCd: row.G_DLV_BRCH_CD,
  });

  if (res.ITEM_CNT == 0) {
    if (basketQty.value >= 100) {
      alert(
        "장바구니에 100개 품목을 초과하여 담을 수 없습니다. 기존 품목 주문등록 후 다시 담기 바랍니다"
      );
      row.REQ_QTY = 0;
      return;
    }
  } else {
    const confirmMsg =
      "장바구니에 이미 담은 품목입니다. 수량 추가하시겠습니까?";
    if (!confirm(confirmMsg)) return;
  }

  basketData.value.saveItemQty = util.getNumberFormat2(row.REQ_QTY);
  try {
    await api.post("/freOrd/addBasket", basketData.value);
    await bus.emit("refreshHeaderBasket");
    await bus.emit("refreshBasket");

    util.showNotify(
      `${row.ITEM_NM} ${basketData.value.saveItemQty}개를 장바구니에 담았습니다.`
    );
  } catch (error) {
    alert(error.response.data);
  }
}

const isSelected = (row) => {
  return selected.value.includes(row);
};

// 그리드 클릭
async function onRowClick(row) {
  const fieldNm = row.col.name;

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

    if (row.row.REQ_QTY == 0 || row.row.REQ_QTY == "") {
      alert("수량을 선택해 주시기 바랍니다.");
      return;
    }

    if (row.row.AVAIL_STOCK < row.row.REQ_QTY) {
      alert(
        `${row.row.ITEM_NM} 재고수량(${row.row.AVAIL_STOCK})이 초과되었습니다.`
      );
      return;
    }

    await addBasket(row.row);

    row.row.REQ_QTY = 0;
  }

  // 반품
  else if (fieldNm == "WEB_BACK_USE_YN") {
    if (row.row.WEB_BACK_USE_YN === "Y") {
      // alert(fieldNm);
      // TODO: 팝업 띄워야함
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

function exportTable() {
  api
    .get("excel/freOrd", {
      params: searchParam.value,
      responseType: "arraybuffer",
    })
    .then(function (res) {
      const url = window.URL.createObjectURL(new Blob([res]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute(
        "download",
        "다빈도_" + moment().format("YYYYMMDD") + ".xls"
      );
      document.body.appendChild(link);
      link.click();
      link.remove();
    })
    .catch(function (error) {
      console.log(error);
    });
}

/******** 수량 event ********/
// 수량에 숫자 외 입력 방지
// function updateCount(props, evt) {
//   const isNotNull = evt.target.value !== "" ? true : false;

//   if (!isNaN(evt.target.value) && isNotNull) {
//     props.row.REQ_QTY = parseInt(evt.target.value);
//   } else {
//     props.row.REQ_QTY = 0;
//   }
// }

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
  await fetchItemList();
});

// 자동완성
bus.on("getAutoCompleteData", async (data) => {
  if (data) {
    items.value = await data.map((obj) => obj.ITEM_NM);
  }
});

onMounted(async () => {
  await fetchItemList();
  await bus.emit("onLoadGetAutoCompleteData");
});

//-------------------------- Grid Setting ----------------------------//

const columns = [
  {
    name: "ITEM_NM",
    label: "품목",
    sortable: true,
    align: "left",
    field: "ITEM_NM",
  },
  { name: "UNIT", label: "규격", field: "UNIT", align: "center" },
  {
    name: "MAKER_NM",
    label: "제약회사",
    field: "MAKER_NM",
    align: "left",
    sortable: true,
  },
  {
    name: "ORD_WP2_AMT",
    label: "단가",
    field: "ORD_WP2_AMT",
    align: "center",
    sortable: true,
    format: (val) => util.getNumberFormat(val),
  },
  {
    name: "AVG_QTY",
    label: "평균",
    field: "AVG_QTY",
    align: "center",
  },
  {
    name: "ITEM_QTY_3",
    label: "전전월",
    field: "ITEM_QTY_3",
    align: "center",
  },
  {
    name: "ITEM_QTY_2",
    label: "전월",
    field: "ITEM_QTY_2",
    align: "center",
  },
  {
    name: "ITEM_QTY_1",
    label: "당월",
    field: "ITEM_QTY_1",
    align: "center",
  },
  {
    name: "AVAIL_STOCK",
    label: "재고",
    field: "AVAIL_STOCK",
    align: "center",
    sortable: true,
    format: (val) => util.getNumberFormat(val),
  },
  { name: "REQ_QTY", label: "수량", field: "REQ_QTY", align: "center" },
  { name: "ADD_BASKET", label: "담기", field: "ADD_BASKET", align: "center" },
  { name: "FAVORITE_YN", label: "관심", field: "FAVORITE_YN", align: "center" },
];

const rows = ref([]);

const inputRef = ref("");

const basketQty = ref("");
onBeforeMount(async () => {
  bus.on("BasketQty2", (data) => {
    basketQty.value = data;
    // console.log("마운트시 다빈도 장바구니 품목수:  "+basketQty.value);
  });
});

onMounted(() => {
  inputRef.value.focus();
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
.flex_start {
  @include flex_start;
}
.orderTable_1b {
  height: 490px;
  overflow-y: auto;
}
.q-table_radio_wrap {
  padding: 0 8px;
}
.q-table_header_sub_wrap {
  @include flex_start;
  margin-left: 44px;
  gap: 8px;
}

.SearchComp_wrap {
  display: flex;
  gap: 8px;
}
.input_search {
  @include fs-5;
  width: 120px;
  height: 28px;
  border: 1px solid #666666;
  padding: 0 6px;
  border-radius: 5px;
}
.q-field__control-container {
  @include flex_center;
}
::v-deep .q-field__native {
  font-size: 14px;
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
</style>

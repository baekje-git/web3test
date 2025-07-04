<template>
  <div class="">
    <q-table
      class="orderTable_2a cart_wrap"
      :rows="filteredRows"
      :columns="computedColumns"
      row-key="ITEM_CD"
      table-header-class="table_header"
      table-class="tbl_cart"
      dense
      selection="multiple"
      v-model:selected="selected"
      color="blue-10"
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      @keydown="onKey"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="q-table_header_wrap flex_between">
            <div class="q-table_header_title">
              <p>장바구니</p>
            </div>
            <div class="q-table_header_sub">
              <!-- 
              <a
                class="cart_topLink"
                v-if="userInfo.G_DLV_YN != 'N'"
                @click="onClickBtnWideArea"
              >
                <span
                  >광역 <b>{{ wideAreaCntOnly }}</b
                  >건</span
                >
                <q-badge rounded>
                  ?
                  <q-tooltip anchor="top right" self="bottom right">
                    <p>
                      광역주문은 해당 품목의 재고가 부족하여, <br />
                      타 물류로부터 영업일 기준 2일 이내 배송됩니다.
                    </p>
                  </q-tooltip>
                </q-badge>
              </a>
              -->
              <!-- <q-btn
                v-if="userInfo.G_DLV_YN != 'N'"
                :label="btnWideAreaLabel"
                dense
                @click="onClickBtnWideArea"
              >
                <q-badge rounded floating>
                  ?
                  <q-tooltip
                    anchor="top right"
                    self="top left"

                  >
                    <p>
                      광역주문은 해당 품목의 재고가 부족하여, <br />
                      타 물류로부터 영업일 기준 2일 이내 배송됩니다.
                    </p>
                  </q-tooltip>
                </q-badge>
              </q-btn> -->
            </div>
          </div>
          <div class="q-table_header_sub_wrap">
            <div class="radioTab_wrap">
              <!-- <RadioComp_a
                v-model="orderRadio"
                :radio_options="orderRadioOptions"
                /> -->
              <q-radio
                v-model="filterCond"
                size="xs"
                val="all"
                class="radioTab"
                :label="radioAll"
                @click="radioClick(0)"
              />
              <q-radio
                v-model="filterCond"
                size="xs"
                inline
                val="01"
                class="radioTab"
                :label="radioPrfssnlPmnt"
                @click="radioClick(1)"
              />
              <q-radio
                v-model="filterCond"
                size="xs"
                inline
                val="02"
                class="radioTab"
                :label="radioPrfssnlNonPmnt"
                @click="radioClick(2)"
              />
              <q-radio
                v-model="filterCond"
                size="xs"
                inline
                val="03"
                class="radioTab"
                :label="radioGnrlPmnt"
                @click="radioClick(3)"
              />
              <q-radio
                v-model="filterCond"
                size="xs"
                inline
                val="04"
                class="radioTab"
                :label="radioGnrlNonPmnt"
                @click="radioClick(4)"
              />
            </div>
          </div>
        </div>
      </template>

      <template v-slot:top-row v-if="filteredRows.length === 0 && searched">
        <q-tr class="no-date">
          <q-td colspan="100%">
            <span>검색된 결과가 없습니다.</span>
          </q-td>
        </q-tr>
      </template>
      <!-- 품목 및 규격/ -->
      <template v-slot:body-cell-ITEM_NM="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <!-- <div class="" style="width: 100px"> -->
          <div>
            <div class="q-table_itemTitle td-prd_name">
              {{ props.value }}
            </div>
            <div
              class="table_widearea bdg-line red"
              v-if="props.row.G_DLV_BRCH_FLAG === 'G'"
            >
              <span>광역</span>
            </div>
          </div>
        </q-td>
      </template>
      <!-- 제약 회사/ -->
      <template v-slot:body-cell-MAKER_NM="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <div class="q-table_itemTitleSub ellipsis">
            {{ props.value }}
            <q-tooltip>{{ props.value }}</q-tooltip>
          </div>
        </q-td>
      </template>
      <!-- 단가/ -->
      <template v-slot:body-cell-ORD_WP2_AMT="props">
        <q-td :props="props" @click="onClickRow(props.row, 'price')">
          <div>
            <p
              v-if="
                userInfo.USER_GB_CD === '30' || props.row.PAY_ITEM_YN === 'Y'
              "
              style="cursor: pointer"
            >
              {{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}
            </p>
            <div
              v-if="
                userInfo.USER_GB_CD !== '30' && props.row.PAY_ITEM_YN === 'N'
              "
            >
              <q-input
                v-model="props.row.ORD_WP2_AMT"
                class="q-table_number"
                borderless
                dense
                bg-color="white"
                input-class="text-right"
                input-style="padding: 0 2px;"
                reverse-fill-mask
                mask="###,###,###"
                @update:model-value="onUpdate(props)"
                @focus="(input) => input.target.select()"
              />
            </div>
          </div>
        </q-td>
      </template>
      <!-- 현 재고/ -->
      <template v-slot:body-cell-AVAIL_STOCK="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <div>
            <p
              v-if="
                util.getNumberFormat2(props.row.ITEM_QTY) >
                props.row.AVAIL_STOCK
              "
              class="red-400 bold"
            >
              {{ util.getNumberFormat(props.row.AVAIL_STOCK) }}
            </p>
            <p
              v-if="
                util.getNumberFormat2(props.row.ITEM_QTY) <=
                props.row.AVAIL_STOCK
              "
            >
              {{ util.getNumberFormat(props.row.AVAIL_STOCK) }}
            </p>
          </div>
        </q-td>
      </template>
      <!-- 수량/ -->
      <template v-slot:body-cell-ITEM_QTY="props">
        <q-td :props="props">
          <div class="q-table_number_wrap flex_center">
            <div class="qty_wrap">
              <!-- 수량 감소 버튼 -->
              <button
                type="button"
                class="btn_qty"
                @click="
                  () => {
                    if (props.row.ITEM_QTY > 0) {
                      props.row.ITEM_QTY--;
                      onClickUpdateQty(props.row);
                    }
                  }
                "
              >
                <i class="ico qty-minus"></i>
              </button>
              <q-input
                :model-value="formatNumber(props.row.ITEM_QTY)"
                class="q-table_number"
                borderless
                dense
                bg-color="white"
                input-class="text-center"
                @update:model-value="(value) => updateItemQty(value, props.row)"
                @focus="() => onInputFocus(props.row)"
                @keyup.enter="onQtyEnterFromCart"
                :ref="(el) => setQtyInputRef(el, props.row.ITEM_CD)"
              />
              <button
                type="button"
                class="btn_qty"
                @click="
                  () => {
                    props.row.ITEM_QTY++;
                    onClickUpdateQty(props.row);
                  }
                "
              >
                <i class="ico qty-plus"></i>
              </button>
            </div>
          </div>
        </q-td>
      </template>
      <!-- 관심/ -->
      <template v-slot:body-cell-FAVORITE_YN="props">
        <q-td :props="props">
          <div>
            <q-checkbox
              class="q-table_checkbox_heart"
              :checked-icon="mdiHeart"
              :unchecked-icon="mdiHeart"
              v-model="props.row.FAVORITE_YN"
              size="xs"
              true-value="Y"
              false-value="N"
              @update:model-value="(val) => onCheckInterest(props.row, val)"
            />
          </div>
        </q-td>
      </template>
      <!-- 삭제/ -->
      <template v-slot:body-cell-삭제="props">
        <q-td :props="props" @click="onClickDelete(props.row)">
          <div>
            <img src="assets/images/ico_delete.svg" alt="삭제" />
          </div>
        </q-td>
      </template>

      <!-- 하단/ -->
      <template v-slot:bottom>
        <div class="all_price">
          <div class="all_left">
            <span>총 주문 품목</span>
            <b>{{ JSON.stringify(selected.length) }}</b>
            <span>건</span>
          </div>
          <div class="all_right">
            <b>{{ totalCost.toLocaleString() }}</b>
            <span>원</span>
          </div>
          <BtnComp_default_c class="btn-ord" @click="onClickOrderReg">
            <b class="fs-5">주문등록</b><i class="ico h12 arrw-rt go"></i>
          </BtnComp_default_c>
        </div>
        <div class="memo flex-beteween">
          <q-input
            v-model="ordMemo"
            type="textarea"
            class="q-table_bottom_input"
            outlined
            maxlength="300"
            placeholder="메모"
          />
          <h4 class="label">
            <p class="name"><b>메모</b></p>
            <p class="length">
              <b>{{ ordMemo.length }}</b
              >/ 300
            </p>
          </h4>
        </div>
      </template>
    </q-table>
  </div>
  <HistoryPopup :is-open="isOpenHistory" @close="isOpenHistory = false" />
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import {
  computed,
  ref,
  onMounted,
  inject,
  toRaw,
  onUnmounted,
  nextTick,
  defineExpose,
} from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { mdiHeart, mdiHeartOutline } from "@mdi/js";

// import RadioComp_a from "src/components/web/common/RadioComp_a.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_default_d from "src/components/web/common/BtnComp_default_d.vue";
import BtnComp_active_small from "src/components/web/common/BtnComp_active_small.vue";
import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
import HistoryPopup from "src/components/web/order/HistoryPopup.vue";
import BtnComp_default_c from "src/components/web/common/BtnComp_default_c.vue";

const userInfo = util.getUserData();
const bus = inject("bus");
const selected = ref([]);
const ordMemo = ref("");
const rows = ref([]);
const loading = ref(false);
const isOpenHistory = ref(false);
const filterCond = ref("all");
const searched = ref(false);
const rowInfo = ref({
  userId: userInfo.USER_ID,
  custCd: userInfo.CUST_CD,
  itemQty: "",
  ordWp2Amt: "",
  basketGbCd: "01",
  saveItemGbCd: "",
  saveItemCd: "",
  dlvBrchCd: "",
});

//라디오 클릭시 해당 품목 셀렉트
const radioClick = (val) => {
  selected.value = [];

  fetchBasketList2();

  if (val == 0) {
    if (rows.value.length > 0) {
      selected.value = rows.value;
    }
    //fetchBasketList2();
  } else if (val == 1) {
    const newRows = toRaw(rows.value).filter(
      (item) => item.PAY_ITEM_YN === "Y" && item.MED_CD === "02"
    );
    if (newRows.length > 0) {
      selected.value = newRows;
    }
    //fetchBasketList3();
  } else if (val == 2) {
    const newRows = toRaw(rows.value).filter(
      (item) => item.PAY_ITEM_YN === "N" && item.MED_CD === "02"
    );
    if (newRows.length > 0) {
      selected.value = newRows;
    }
    //fetchBasketList4();
  } else if (val == 3) {
    const newRows = toRaw(rows.value).filter(
      (item) => item.PAY_ITEM_YN === "Y" && item.MED_CD === "01"
    );
    if (newRows.length > 0) {
      selected.value = newRows;
    }
    //fetchBasketList5();
  } else if (val == 4) {
    const newRows = toRaw(rows.value).filter(
      (item) => item.PAY_ITEM_YN === "N" && item.MED_CD === "01"
    );
    if (newRows.length > 0) {
      selected.value = newRows;
    }
    //fetchBasketList6();
  }
};

const filteredRows = computed(() => {
  if (rows.value.length > 0) {
    if (filterCond.value === "01") {
      // 전문급여
      return rows.value.filter(
        (item) => item.PAY_ITEM_YN === "Y" && item.MED_CD === "02"
      );
    } else if (filterCond.value === "02") {
      // 전문비급
      return rows.value.filter(
        (item) => item.PAY_ITEM_YN === "N" && item.MED_CD === "02"
      );
    } else if (filterCond.value === "03") {
      // 일반급여
      return rows.value.filter(
        (item) => item.PAY_ITEM_YN === "Y" && item.MED_CD === "01"
      );
    } else if (filterCond.value === "04") {
      // 일반비급
      return rows.value.filter(
        (item) => item.PAY_ITEM_YN === "N" && item.MED_CD === "01"
      );
    }
  }

  // 전체
  return rows.value;
});

const radioAll = computed(() => {
  return `전체 ${rows.value.length}건`;
});

const radioPrfssnlPmnt = computed(() => {
  const prfssnlPmntCnt = toRaw(rows.value).filter(
    (item) => item.PAY_ITEM_YN === "Y" && item.MED_CD === "02"
  ).length;
  return `전문급여 ${prfssnlPmntCnt}건`;
});

const radioPrfssnlNonPmnt = computed(() => {
  const prfssnlNonPmntCnt = toRaw(rows.value).filter(
    (item) => item.PAY_ITEM_YN === "N" && item.MED_CD === "02"
  ).length;
  return `전문비급여 ${prfssnlNonPmntCnt}건`;
});

const radioGnrlPmnt = computed(() => {
  const gnrlPmntCnt = toRaw(rows.value).filter(
    (item) => item.PAY_ITEM_YN === "Y" && item.MED_CD === "01"
  ).length;
  return `일반급여 ${gnrlPmntCnt}건`;
});

const radioGnrlNonPmnt = computed(() => {
  const prfssnlPmntCnt = toRaw(rows.value).filter(
    (item) => item.PAY_ITEM_YN === "N" && item.MED_CD === "01"
  ).length;
  return `일반비급여 ${prfssnlPmntCnt}건`;
});

const btnWideAreaLabel = computed(() => {
  const wideAreaCnt = toRaw(rows.value).filter(
    (item) => item.G_DLV_BRCH_FLAG === "G"
  ).length;
  return `광역 ${wideAreaCnt}건`;
});
const wideAreaCntOnly = computed(() => {
  return toRaw(rows.value).filter((item) => item.G_DLV_BRCH_FLAG === "G")
    .length;
});

const basketSearchParam = ref({
  userGbCd: util.getUserData().USER_GB_CD,
  custCd: util.getUserData().CUST_CD,
  basketGbCd: "01",
  gDlvBrchFlag: "",
});

const btnAllClass = computed(() =>
  basketSearchParam.value.gDlvBrchFlag === "G" ? "btn_active" : "btn"
);

const btnWideAreaClass = computed(() =>
  basketSearchParam.value.gDlvBrchFlag === "G" ? "btn" : "btn_active"
);

const totalCost = computed(() => {
  let cost = 0;

  if (selected.value.length > 0) {
    selected.value.forEach((item) => {
      cost +=
        Number(util.getNumberFormat2(item.ORD_WP2_AMT)) *
        Number(util.getNumberFormat2(item.ITEM_QTY) ?? item.ITEM_QTY_ORG);
    });
  }

  return cost;
});

const intervalId = ref(null);

//---------------------------- Function ------------------------------//
const pendingAddItemCds = ref([]);

// AddToCart 이벤트 → 우선 큐에 담아둔다
bus.on("AddToCart", (itemCd) => {
  if (!pendingAddItemCds.value.includes(itemCd)) {
    pendingAddItemCds.value.push(itemCd);
  }
});
bus.on("getCartItemCount", (callback) => {
  const count = new Set(rows.value.map((item) => item.ITEM_CD)).size;
  callback(count);
});
// 장바구니 목록 조회
async function fetchBasketList(filterVal) {
  let selectChk = "all";
  if (filterVal !== undefined) {
    selectChk = filterVal;
  }

  // 기존 선택 상태 기억
  const prevSelected = selected.value.map((row) => row.ITEM_CD);

  selected.value = [];
  loading.value = true;

  const res = await api.get("/ord/basketList", {
    params: basketSearchParam.value,
  });

  ordMemo.value = "";
  searched.value = true;
  rows.value = res;
  loading.value = false;

  if (selectChk === "all") {
    if (rows.value.length > 0) {
      // ✅ 처음 로딩이거나 prevSelected가 비어있으면 전체 선택
      if (prevSelected.length === 0) {
        selected.value = [...rows.value];
      } else {
        selected.value = rows.value.filter((row) =>
          prevSelected.includes(row.ITEM_CD)
        );
      }
      filterCond.value = selectChk;
    }
  } else {
    if (filteredRows.value.length > 0) {
      selected.value = filteredRows.value;
      filterCond.value = selectChk;
    }
  }

  bus.emit("BasketQty", rows.value.length);

  pendingAddItemCds.value.forEach((itemCd) => {
    const alreadySelected = selected.value.map((row) => row.ITEM_CD);
    const foundItem = rows.value.find((row) => row.ITEM_CD === itemCd);

    if (foundItem && !alreadySelected.includes(itemCd)) {
      selected.value.push(foundItem);
    }
  });

  // 큐 비우기
  pendingAddItemCds.value = [];
}

// 장바구니 목록 조회
async function fetchBasketList2(filterVal) {
  //필터 체크
  let selectChk = "all";

  if (filterVal != undefined) {
    selectChk = filterVal;
  }
  selected.value = [];
  loading.value = true;

  const res = await api.get("/ord/basketList2", {
    params: basketSearchParam.value,
  });

  searched.value = true;
  rows.value = res;
  loading.value = false;

  if (selectChk == "all") {
    if (rows.value.length > 0) {
      //selected.value = rows.value;
      //filterCond.value = selectChk;
    }
  } else {
    if (filteredRows.value.length > 0) {
      selected.value = filteredRows.value;
      filterCond.value = selectChk;
    }
  }

  // 장바구니 품목 수 전달
  bus.emit("BasketQty", rows.value.length);
}

// 장바구니 목록 조회
async function fetchBasketList3(filterVal) {
  //필터 체크
  let selectChk = "all";

  if (filterVal != undefined) {
    selectChk = filterVal;
  }
  selected.value = [];
  loading.value = true;

  const res = await api.get("/ord/basketList3", {
    params: basketSearchParam.value,
  });

  searched.value = true;
  rows.value = res;
  loading.value = false;

  if (selectChk == "all") {
    if (rows.value.length > 0) {
      selected.value = rows.value;
      filterCond.value = selectChk;
    }
  } else {
    if (filteredRows.value.length > 0) {
      selected.value = filteredRows.value;
      filterCond.value = selectChk;
    }
  }

  // 장바구니 품목 수 전달
  bus.emit("BasketQty", rows.value.length);
}

// 장바구니 목록 조회
async function fetchBasketList4(filterVal) {
  //필터 체크
  let selectChk = "all";

  if (filterVal != undefined) {
    selectChk = filterVal;
  }
  selected.value = [];
  loading.value = true;

  const res = await api.get("/ord/basketList4", {
    params: basketSearchParam.value,
  });

  searched.value = true;
  rows.value = res;
  loading.value = false;

  if (selectChk == "all") {
    if (rows.value.length > 0) {
      selected.value = rows.value;
      filterCond.value = selectChk;
    }
  } else {
    if (filteredRows.value.length > 0) {
      selected.value = filteredRows.value;
      filterCond.value = selectChk;
    }
  }

  // 장바구니 품목 수 전달
  bus.emit("BasketQty", rows.value.length);
}

// 장바구니 목록 조회
async function fetchBasketList5(filterVal) {
  //필터 체크
  let selectChk = "all";

  if (filterVal != undefined) {
    selectChk = filterVal;
  }
  selected.value = [];
  loading.value = true;

  const res = await api.get("/ord/basketList5", {
    params: basketSearchParam.value,
  });

  searched.value = true;
  rows.value = res;
  loading.value = false;

  if (selectChk == "all") {
    if (rows.value.length > 0) {
      selected.value = rows.value;
      filterCond.value = selectChk;
    }
  } else {
    if (filteredRows.value.length > 0) {
      selected.value = filteredRows.value;
      filterCond.value = selectChk;
    }
  }

  // 장바구니 품목 수 전달
  bus.emit("BasketQty", rows.value.length);
}

// 장바구니 목록 조회
async function fetchBasketList6(filterVal) {
  //필터 체크
  let selectChk = "all";

  if (filterVal != undefined) {
    selectChk = filterVal;
  }
  selected.value = [];
  loading.value = true;

  const res = await api.get("/ord/basketList6", {
    params: basketSearchParam.value,
  });

  searched.value = true;
  rows.value = res;
  loading.value = false;

  if (selectChk == "all") {
    if (rows.value.length > 0) {
      selected.value = rows.value;
      filterCond.value = selectChk;
    }
  } else {
    if (filteredRows.value.length > 0) {
      selected.value = filteredRows.value;
      filterCond.value = selectChk;
    }
  }

  // 장바구니 품목 수 전달
  bus.emit("BasketQty", rows.value.length);
}
// 주문수량 변경 버튼
async function onClickUpdateQty(row) {
  //필터값
  let filterVal = filterCond.value;

  row.ITEM_QTY = util.getNumberFormat2(row.ITEM_QTY);
  rowInfo.value.itemQty = row.ITEM_QTY;
  rowInfo.value.ordWp2Amt = util.getNumberFormat2(row.ORD_WP2_AMT);
  rowInfo.value.saveItemGbCd = row.ITEM_GB_CD;
  rowInfo.value.saveItemCd = row.ITEM_CD;
  rowInfo.value.dlvBrchCd = row.DLV_BRCH_CD;

  if (rowInfo.value.itemQty == 0 || rowInfo.value.itemQty == " ") {
    alert("수량을 입력해 주시기 바랍니다.");
    row.ITEM_QTY = row.ITEM_QTY_ORG;
    return;
  }

  if (rowInfo.value.ordWp2Amt == 0 || rowInfo.value.ordWp2Amt == " ") {
    alert("단가를 입력해 주시기 바랍니다.");

    fetchBasketList();
  }

  if (!checkBasket2(row)) {
    return;
  }

  await api.put("/ord/updateItemQty", rowInfo.value);
  // 변경되었는지 확인하기 애매해서 추가함.. 요청 있을경우 삭제

  await fetchBasketList(filterVal);
}

//선택 삭제(전체 삭제)
const onDelete = async () => {
  if (selected.value.length == 0) {
    alert("선택한 품목이 없습니다.");
    return;
  }

  let confirmMsg =
    "선택한 품목(" +
    selected.value.length +
    "건)에 대해 장바구니에서 삭제하시겠습니까?";
  if (!confirm(confirmMsg)) return;

  selected.value.forEach(async (m) => {
    rowInfo.value.saveItemGbCd = m.ITEM_GB_CD;
    rowInfo.value.saveItemCd = m.ITEM_CD;
    rowInfo.value.dlvBrchCd = m.DLV_BRCH_CD;

    await api.delete("/ord/deleteComOrdBasket", {
      params: rowInfo.value,
    });

    //필터값
    let filterVal = filterCond.value;

    await bus.emit("refreshHeaderBasket");
    await fetchBasketList(filterVal);
  });
  util.showNotify("선택한 품목에 대해 장바구니에서 삭제되었습니다.");
};

// 장바구니 목록 단건 삭제
async function onClickDelete(row) {
  // lint 정렬때문에 컨펌 메세지 틀어져서 이렇게 처리함..
  let confirmMsg = `품목 : ${row.ITEM_NM}\n`;
  confirmMsg += `수량 : ${row.ITEM_QTY}\n`;
  confirmMsg += `취소하시겠습니까?`;

  if (!confirm(confirmMsg)) return;

  //필더값
  let filterVal = filterCond.value;

  rowInfo.value.saveItemGbCd = row.ITEM_GB_CD;
  rowInfo.value.saveItemCd = row.ITEM_CD;
  rowInfo.value.dlvBrchCd = row.DLV_BRCH_CD;

  await api.delete("/ord/deleteComOrdBasket", {
    params: rowInfo.value,
  });

  await bus.emit("refreshHeaderBasket");
  await fetchBasketList(filterVal);
}

// 주문 품목 유효성 체크
function checkBasket(obj) {
  const itemCd = obj.ITEM_CD;
  const wp2ChgYn = obj.WP2_CHG_YN;
  const itemNm = obj.ITEM_NM;
  const sumItemQty = Number(obj.SUM_ITEM_QTY); // 숫자 변환
  const minOrdQty = obj.MIN_ORD_QTY;
  const maxOrdQty = obj.MAX_ORD_QTY;
  const itemQty = Number(util.getNumberFormat2(obj.ITEM_QTY)); // 숫자 변환
  const availStock = obj.AVAIL_STOCK;
  const itemQtyOrg = obj.ITEM_QTY_ORG;
  const ordWp2AmtOrg = obj.ORD_WP2_AMT_ORG;
  const minWp2Amt = obj.MIN_WP2_AMT;
  const ordWp2Amt = util.getNumberFormat2(obj.ORD_WP2_AMT);
  const saleUnitQty = obj.SALE_UNIT_QTY;
  const maxAmt = obj.MAX_AMT;
  if (itemQty === null || itemQty === "" || itemQty === "0") {
    alert(itemNm + "\n주문수량을 확인해 주세요.");
    return false;
  }

  if (availStock < itemQty) {
    alert(itemNm + "\n재고수량(" + availStock + ")이 초과되었습니다.");
    return false;
  }
  if (minOrdQty > itemQty) {
    // 최소 수량보다 주문하려고 하는 수량이 더 적을 때 return;
    alert(itemNm + "\n최소주문수량(" + minOrdQty + ") 보다 작습니다.");
    return false;
  }
  if (maxOrdQty < itemQty) {
    // 최대 수량보다 주문하려고 하는 수량이 더 많을 때 return;
    alert(itemNm + "\n최대주문가능수량(" + maxOrdQty + ")을 초과하였습니다.");
    return false;
  }
  if (
    saleUnitQty > 0 &&
    itemQty != saleUnitQty * parseInt(itemQty / saleUnitQty)
  ) {
    // 주문단위수량의 곱으로 입력하지 않았을 경우 return;
    alert(
      itemNm +
        "\n주문단위수량(" +
        saleUnitQty +
        ")의 배수로 주문수량을 입력해주세요."
    );
    return false;
  }
  if (maxOrdQty < sumItemQty + itemQty) {
    alert(
      itemNm + "\n일누적 최대주문가능수량(" + maxOrdQty + ")을 초과하였습니다."
    );
    obj.ITEM_QTY = obj.ITEM_QTY_ORG;
    return false;
  }

  // if (ordWp2Amt == null || ordWp2Amt == "" || ordWp2Amt == "0") {
  //   alert(itemNm + "\n단가를 확인해 주세요.");
  //   return false;
  // }

  if (wp2ChgYn == "Y" && minWp2Amt > ordWp2Amt) {
    alert(
      itemNm +
        "\n입력한 단가가 최소 단가(" +
        util.getNumberFormat(minWp2Amt) +
        ") 보다 적습니다."
    );

    obj.ORD_WP2_AMT = ordWp2AmtOrg;
    return false;
  }

  //20190117 최대단가 체크로직 추가
  if (maxAmt < ordWp2Amt) {
    alert(itemNm + "\n입력한 단가가 최대 단가(" + maxAmt + ") 보다 많습니다.");
    return false;
  }

  return true;
}

// 변경 품목 유효성 체크
function checkBasket2(obj) {
  const wp2ChgYn = obj.WP2_CHG_YN;
  const itemNm = obj.ITEM_NM;
  const sumItemQty = Number(obj.SUM_ITEM_QTY); // 숫자 변환
  const maxOrdQty = obj.MAX_ORD_QTY;
  const itemQty = Number(util.getNumberFormat2(obj.ITEM_QTY)); // 숫자 변환
  const minWp2Amt = obj.MIN_WP2_AMT;
  const ordWp2Amt = util.getNumberFormat2(obj.ORD_WP2_AMT);
  const maxAmt = obj.MAX_AMT;
  const ordWp2AmtOrg = obj.ORD_WP2_AMT_ORG;
  if (maxOrdQty < sumItemQty + itemQty) {
    alert(
      itemNm + "\n일누적 최대주문가능수량(" + maxOrdQty + ")을 초과하였습니다."
    );
    obj.ITEM_QTY = obj.ITEM_QTY_ORG;
    return false;
  }

  // if (ordWp2Amt == null || ordWp2Amt == "" || ordWp2Amt == "0") {
  //   alert(itemNm + "\n단가를 확인해 주세요.");
  //   return false;
  // }

  //20190117 최대단가 체크로직 추가
  if (maxAmt < ordWp2Amt) {
    alert(itemNm + "\n입력한 단가가 최대 단가(" + maxAmt + ") 보다 많습니다.");
    fetchBasketList();
  }

  return true;
}

// 주문등록 클릭
async function onClickOrderReg() {
  let errorCnt = 0;
  let wideOrderCnt = 0;

  if (selected.value.length === 0) {
    alert("주문할 품목을 선택해 주시기 바랍니다.");
    return;
  }

  selected.value.forEach((item) => {
    // 약국개설일 등록 여부 체크
    const medOpenDt = item.MED_OPEN_DT;
    const custGbCd = item.CUST_GB_CD;

    if (
      custGbCd !== "09" &&
      (medOpenDt === undefined || medOpenDt === null || medOpenDt === "")
    ) {
      alert("거래처마스터의 약국개설일이 등록되어야 주문입력이 가능합니다.");
      errorCnt++;
      return;
    }

    if (medOpenDt > util.getToday("YYYYMMDD")) {
      alert("거래처마스터의 약국개설일이 지나야 주문입력이 가능합니다.");
      errorCnt++;
      return;
    }

    if (!checkBasket(item)) {
      errorCnt++;
      return;
    }

    const chkDlvCdstr = item.DLV_BRCH_CD;
    if (chkDlvCdstr !== userInfo.BRCH_CD) {
      wideOrderCnt++;
    }
  });

  if (errorCnt === 0) {
    let orderMsg = `총 ${selected.value.length}건을 주문하시겠습니까?`;

    if (confirm(orderMsg)) {
      selected.value.forEach((m) =>
        Object.assign(m, {
          ORD_MEMO: ordMemo.value,
          BRCH_CD: userInfo.BRCH_CD,
          CUST_CD: userInfo.CUST_CD,
          DEPT_CD: userInfo.DEPT_CD ?? "",
          EMP_ID: userInfo.EMP_ID,
          USER_ID: userInfo.USER_ID,
        })
      );

      try {
        await api.post("/ord/orderReg", selected.value);

        util.showNotify("주문접수 처리되었습니다.");
        await bus.emit("refreshHeaderBasket");
        await fetchBasketList();
        ordMemo.value = "";
      } catch (error) {
        const msg =
          error?.response?.data?.message ||
          error?.response?.data ||
          error.message ||
          "알 수 없는 오류가 발생했습니다.";

        alert(msg); // 또는 this.$q.notify({ type: 'negative', message: msg });
      }
    }
  }
}

// 전체 버튼 클릭
async function onClickBtnAll() {
  basketSearchParam.value.gDlvBrchFlag = "";
  await fetchBasketList();
}

// 광역 버튼 클릭
async function onClickBtnWideArea() {
  basketSearchParam.value.gDlvBrchFlag = "G";
  await fetchBasketList();
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

    //await fetchBasketList();
    await bus.emit("refreshOrderList");
  } catch (error) {
    alert(error.response.data);
  }
}

// 제품 출고 이력 팝업호출
async function onClickRow(row, type = "") {
  focusedRow.value = row;

  const skipFocus = type === "price";

  await bus.emit("basketHistoryList", row);
  await bus.emit("itemDetailInfo", row);

  await nextTick();

  if (!skipFocus) {
    focusQtyInput(row);
  }
}

// 5초마다 재고 갱신 2025-03-25 임시주석
// async function refreshAvailStock() {
//   intervalId.value = setInterval(async () => {
//     const res = await api.post("/ord/getAvailStock", {
//       custCd: userInfo.CUST_CD,
//     });

//     if (rows.value.length > 0) {
//       rows.value.forEach((row) => {
//         res.forEach((m) => {
//           if (row.ITEM_CD === m.ITEM_CD) {
//             row.AVAIL_STOCK = m.AVAIL_STOCK;
//           }
//         });
//       });
//     }
//   }, 60000);
// }

// 변경 버튼 diable 여부
function updateBtnDisable(row) {
  if (
    util.getNumberFormat2(row.ITEM_QTY) != row.ITEM_QTY_ORG ||
    util.getNumberFormat2(row.ORD_WP2_AMT) != row.ORD_WP2_AMT_ORG
  ) {
    return false;
  } else {
    return true;
  }
}

// 주문 그리드에서 담기 버튼 클릭 시 장바구니 목록 재조회
bus.on("refreshBasket", async () => {
  await fetchBasketList();
});

onMounted(async () => {
  await fetchBasketList();
});

onUnmounted(() => {
  clearInterval(intervalId.value);
});

// 숫자 포맷 변환 함수
const formatNumber = (value) => {
  return value ? util.getNumberFormat(value) : "0";
};

// 값 업데이트 함수
const updateItemQty = (value, row) => {
  let newValue = value.replace(/,/g, "").replace(/^0+(?!$)/, ""); // ',' 제거 및 0 제거
  newValue = parseInt(newValue) || 0; // 숫자로 변환
  row.ITEM_QTY = newValue; // 원본 데이터 업데이트
  onClickUpdateQty(row);
};

const onUpdate = (val) => {
  if (val.col.name == "ITEM_QTY") {
    let newValue = val.row.ITEM_QTY.replace(/,/g, "");
    newValue = newValue.replace(/^0+(?!$)/, "");
    newValue = parseInt(newValue);
    val.row.ITEM_QTY = newValue;
  } else if (val.col.name == "ORD_WP2_AMT") {
    let newValue = val.row.ORD_WP2_AMT.replace(/,/g, "");
    newValue = newValue.replace(/^0+(?!$)/, "");
    newValue = parseInt(newValue);
    val.row.ORD_WP2_AMT = newValue;
  }
  onClickUpdateQty(val.row);
};

//-------------------------- Grid Setting ----------------------------//

const columns = [
  {
    name: "ITEM_NM",
    required: true,
    label: "품목 및 규격",
    align: "left",
    field: "ITEM_NM",
    classes: "td-prd_cart",
    sortable: true,
  },
  {
    name: "MAKER_NM",
    label: "제약회사",
    field: "MAKER_NM",
    align: "left",
    classes: "td-comp_cart",
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
    name: "ITEM_QTY",
    label: "수량",
    field: "ITEM_QTY",
    align: "right",
    classes: "td-qty",
  },

  {
    name: "FAVORITE_YN",
    label: "관심",
    field: "FAVORITE_YN",
    align: "center",
    classes: "td-wish",
  },
  {
    name: "삭제",
    label: "삭제",
    field: "삭제",
    align: "center",
    classes: "td-del",
  },
];
// 1024이하일 때 테이블 필드 순서 변경 추가 240214
const smScrn = ref(false);
const computedColumns = computed(() => {
  if (smScrn.value) {
    // 1024px 이하에서 열 순서 변경
    return [
      {
        name: "ITEM_NM",
        required: true,
        label: "품목 및 규격",
        align: "left",
        field: "ITEM_NM",
        classes: "td-prd_cart",
        sortable: true,
      },
      {
        name: "ITEM_QTY",
        label: "수량",
        field: "ITEM_QTY",
        align: "right",
        classes: "td-qty",
      },
      {
        name: "MAKER_NM",
        label: "제약회사",
        field: "MAKER_NM",
        align: "left",
        classes: "td-comp_cart",
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
        name: "FAVORITE_YN",
        label: "관심",
        field: "FAVORITE_YN",
        align: "center",
        classes: "td-wish",
      },
      {
        name: "삭제",
        label: "삭제",
        field: "삭제",
        align: "center",
        classes: "td-del",
      },
    ];
  }
  // 1024px 초과의 경우 원래 열 배열을 반환
  return columns;
});
// 화면 크기를 체크하는 함수를 정의
const updateWindowSize = () => {
  smScrn.value = window.innerWidth <= 1400; // 화면 크기 체크
};

// 컴포넌트가 마운트될 때와 소멸될 때 이벤트 리스너를 설정
onMounted(() => {
  // 기존 onMounted 내용 유지하면서 이 부분 추가
  bus.on("AddToCart", (itemCd) => {
    const alreadySelected = selected.value.map((row) => row.ITEM_CD);
    const foundItem = rows.value.find((row) => row.ITEM_CD === itemCd);

    if (foundItem && !alreadySelected.includes(itemCd)) {
      selected.value.push(foundItem);
    }
  });
});

onUnmounted(() => {
  // 기존 onUnmounted 내용 유지하면서 이벤트 해제 추가
  bus.off("AddToCart");
});

const onQtyEnterFromCart = () => {
  bus.emit("focusItemSearchInput");
};
const navigationActive = ref(true); // 항상 true로 그냥 두자
const focusedRow = ref(null);
const onKey = async (evt) => {
  if (
    navigationActive.value !== true ||
    (evt.key !== "ArrowUp" && evt.key !== "ArrowDown")
  ) {
    return;
  }

  evt.preventDefault();

  const currentRows = filteredRows.value;
  if (currentRows.length === 0) return;

  const currentFocusedItemCd = focusedRow.value
    ? focusedRow.value.ITEM_CD
    : selected.value.length > 0
    ? selected.value[0].ITEM_CD
    : null;

  const currentIndex = currentFocusedItemCd
    ? currentRows.findIndex((row) => row.ITEM_CD === currentFocusedItemCd)
    : -1;

  let nextIndex = currentIndex;

  if (evt.key === "ArrowUp") {
    nextIndex = currentIndex > 0 ? currentIndex - 1 : 0;
  } else if (evt.key === "ArrowDown") {
    nextIndex =
      currentIndex < currentRows.length - 1
        ? currentIndex + 1
        : currentRows.length - 1;
  }

  if (nextIndex !== -1) {
    focusedRow.value = currentRows[nextIndex]; // 🔥 focusedRow만 이동시켜
    await nextTick();
    focusQtyInput(focusedRow.value);
  }
};
const qtyInputRefs = ref({}); // 이미 있을거야
const setQtyInputRef = (el, itemCd) => {
  if (el) {
    qtyInputRefs.value[itemCd] = el;
  }
};

const focusQtyInput = (row) => {
  if (!row) return;
  const inputEl = qtyInputRefs.value[row.ITEM_CD]?.$el?.querySelector("input");
  if (inputEl) {
    inputEl.focus();
    inputEl.select();
  }
};
const onInputFocus = (row) => {
  focusedRow.value = row;
  nextTick(() => {
    const inputEl =
      qtyInputRefs.value[row.ITEM_CD]?.$el?.querySelector("input");
    if (inputEl) {
      inputEl.select();
    }
  });
};
const skipQtyFocus = ref(false);
</script>

<style lang="scss" scoped>
.flex_center {
  @include flex_center;
  gap: 8px;
}

.orderTable_2a {
  // height: 490px;
  height: 385px;
  overflow-y: auto;
  @media screen and (max-width: 1025px) and (min-width: 831px) {
    height: 485px;
  }
}
.tbl_cart .q-table {
  border-collapse: separate;
  border-spacing: 10px 10px !important;
}
.selected td:first-child {
  padding: 20px;
}

.table_widearea {
  margin-left: 5px;
}

.cart_topLink {
  position: relative;
  cursor: pointer;
  .q-badge {
    background-color: $q-badge;
  }
  span {
    display: inline-block;
    margin-right: 5px;
    @include fs-5;
    color: #000;
    vertical-align: middle;
    b {
      color: #39549e;
    }
  }
}
.q-table_header_sub_wrap {
  margin-top: 13px;
  padding: 0;
  overflow: auto;
}

// @media screen and (max-width: 1025px) {
//   .cart_wrap .q-table__bottom .memo {
//     width: calc(50% + 36px - 10px);
//   }
//   .cart_wrap .q-table__bottom .all_price {
//     width: calc(40% + 17px - 10px);

//     .btn-ord {
//       width: 87%;
//     }
//     .all_right {
//       margin-right: 0 !important;
//     }
//   }
// }

.cart_wrap .q-table__bottom .all_price {
  .all_right {
    margin-right: 0 !important;
  }
  // .all_left {
  //   margin-right: 20px;
  // }
}
.highlight-special {
  color: red;
  margin-left: 4px;
  font-weight: 500;
}
</style>

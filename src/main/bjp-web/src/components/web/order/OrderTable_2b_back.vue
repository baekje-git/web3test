<template>
  <div class="tbl_wrap rt">
    <q-table
      class="orderTable_2a"
      :rows="filteredRows"
      :columns="columns"
      row-key="ITEM_CD"
      table-class="tbl_cart"
      table-header-class="table_header"
      dense
      selection="multiple"
      v-model:selected="selected"
      color="blue-10"
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="q-table_header_wrap">
            <div class="q-table_header_title">
              <p>장바구니</p>
            </div>
            <div class="flex_between" style="width: 88%">
              <div class="flex_between" style="gap: 8px">
                <!-- <BtnComp_default btn-label="전체" />
                <BtnComp_active btn-label="광역"> -->
                <q-btn
                  :class="btnAllClass"
                  label="전체"
                  dense
                  @click="onClickBtnAll"
                />
                <q-btn
                  v-if="userInfo.G_DLV_YN != 'N'"
                  :class="btnWideAreaClass"
                  :label="btnWideAreaLabel"
                  dense
                  @click="onClickBtnWideArea"
                >
                  <q-badge
                    rounded
                    floating
                    style="background-color: #384dd6; position: "
                  >
                    ?
                    <q-tooltip anchor="top right" self="top left">
                      <p style="font-size: 14px">
                        광역주문은 해당 품목의 재고가 부족하여, <br />
                        타 물류로부터 영업일 기준 2일 이내 배송됩니다.
                      </p>
                    </q-tooltip>
                  </q-badge>
                </q-btn>
                <!-- </BtnComp_active> -->
              </div>
              <!--<div v-if="filterCond === 'all'" class="flex_between" style="float: right">-->
              <div class="flex_between" style="float: right">
                <BtnComp_default_d btn-label="선택삭제" @click="onDelete" />
              </div>
            </div>
          </div>
          <div class="q-table_header_sub_wrap">
            <div class="q-table_radio_wrap">
              <!-- <RadioComp_a
                v-model="orderRadio"
                :radio_options="orderRadioOptions"
              /> -->
              <q-radio
                v-model="filterCond"
                olor="blue-10"
                size="xs"
                inline
                dense
                val="all"
                :label="radioAll"
                @click="radioClick(0)"
              />
              <q-radio
                v-model="filterCond"
                color="blue-10"
                size="xs"
                inline
                dense
                val="01"
                style="margin-left: 8px"
                :label="radioPrfssnlPmnt"
                @click="radioClick(1)"
              />
              <q-radio
                v-model="filterCond"
                olor="blue-10"
                size="xs"
                inline
                dense
                val="02"
                style="margin-left: 8px"
                :label="radioPrfssnlNonPmnt"
                @click="radioClick(2)"
              />
              <q-radio
                v-model="filterCond"
                olor="blue-10"
                size="xs"
                inline
                dense
                val="03"
                style="margin-left: 8px"
                :label="radioGnrlPmnt"
                @click="radioClick(3)"
              />
              <q-radio
                v-model="filterCond"
                olor="blue-10"
                size="xs"
                inline
                dense
                val="04"
                style="margin-left: 8px"
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

      <template v-slot:body-cell-ITEM_NM="props">
        <q-td
          :props="props"
          style="padding: 0; cursor: pointer"
          @click="onClickItemRow(props.row)"
        >
          <div class="" style="margin-left: 8px">
            <div class="q-table_itemTitle ellipsis" style="max-width: 340px">
              {{ props.value }}
              <div
                class="table_widearea"
                v-if="props.row.G_DLV_BRCH_FLAG === 'G'"
                style="display: inline-block"
              >
                <span>광역</span>
              </div>
            </div>
            <!-- <p v-if="props.row.수량 === 0" class="q-table_itemTitleSub">
              (제약사품절)
            </p>
            <p v-if="props.row.광역 === true" class="q-table_itemTitleSub">
              광역
            </p> -->
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ORD_WP2_AMT="props">
        <q-td :props="props" style="text-align: right">
          <p
            v-if="userInfo.USER_GB_CD === '30' || props.row.PAY_ITEM_YN === 'Y'"
            style="cursor: pointer"
          >
            {{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}
          </p>
          <div
            v-if="userInfo.USER_GB_CD !== '30' && props.row.PAY_ITEM_YN === 'N'"
          >
            <q-input
              v-model="props.row.ORD_WP2_AMT"
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
          </div>
        </q-td>

        <!-- <q-td
          :props="props"
          style="text-align: right; cursor: pointer"
          @click="onClickItemRow(props.row)"
        >
          <div class="q-table_itemTitleSub">
            {{ props.value }}
          </div>
        </q-td> -->
      </template>

      <template v-slot:body-cell-MAKER_NM="props">
        <q-td
          :props="props"
          style="text-align: center; cursor: pointer"
          @click="onClickItemRow(props.row)"
        >
          <div class="" style="margin-left: 8px">
            <div class="q-table_itemTitleSub ellipsis" style="max-width: 60px">
              {{ props.value }}
            </div>
            <!-- <p v-if="props.row.수량 === 0" class="q-table_itemTitleSub">
              (제약사품절)
            </p>
            <p v-if="props.row.광역 === true" class="q-table_itemTitleSub">
              광역
            </p> -->
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_QTY="props">
        <q-td
          :props="props"
          style="padding: 0"
          @click="onClickItemRow(props.row)"
        >
          <div class="q-table_number_wrap flex_center">
            <q-input
              v-model="props.row.ITEM_QTY"
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
            <!-- <BtnComp_default_small
              btn_defaultLabel="변경"
              @click="onClickUpdateQty(props.row)"
            /> -->
            <BtnComp_default_small
              v-if="
                util.getNumberFormat2(props.row.ITEM_QTY) !=
                  props.row.ITEM_QTY_ORG ||
                util.getNumberFormat2(props.row.ORD_WP2_AMT) !=
                  props.row.ORD_WP2_AMT_ORG
              "
              btn_defaultLabel="변경"
              :is-disable="updateBtnDisable(props.row)"
              @click="onClickUpdateQty(props.row)"
            />
            <BtnComp_active_small
              v-if="
                util.getNumberFormat2(props.row.ITEM_QTY) ==
                  props.row.ITEM_QTY_ORG &&
                util.getNumberFormat2(props.row.ORD_WP2_AMT) ==
                  props.row.ORD_WP2_AMT_ORG
              "
              btn_activeLabel="변경"
            />
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-AVAIL_STOCK="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickItemRow(props.row)"
        >
          <p
            v-if="
              util.getNumberFormat2(props.row.ITEM_QTY) > props.row.AVAIL_STOCK
            "
            style="color: red"
          >
            {{ util.getNumberFormat(props.value) }}
          </p>
          <p
            v-if="
              util.getNumberFormat2(props.row.ITEM_QTY) <= props.row.AVAIL_STOCK
            "
          >
            {{ util.getNumberFormat(props.value) }}
          </p>
        </q-td>
      </template>

      <template v-slot:body-cell-FAVORITE_YN="props">
        <q-td :props="props">
          <q-checkbox
            class="q-table_checkbox_heart"
            :checked-icon="mdiHeart"
            :unchecked-icon="mdiHeartOutline"
            v-model="props.row.FAVORITE_YN"
            size="xs"
            true-value="Y"
            false-value="N"
            @update:model-value="(val) => onCheckInterest(props.row, val)"
          />
        </q-td>
      </template>

      <template v-slot:body-cell-삭제="props">
        <q-td
          :props="props"
          style="padding: 0"
          @click="onClickDelete(props.row)"
        >
          <div class="q-table_del">
            <img src="assets/icons/x-solid.svg" alt="x" />
          </div>
        </q-td>
      </template>

      <template v-slot:bottom>
        <q-input
          v-model="ordMemo"
          class="q-table_bottom_input"
          outlined
          maxlength="2000"
          placeholder="메모"
        />
        <div class="all_price">
          <div class="all_left">
            <p>총 주문 품목</p>
            <p>
              <span>{{ JSON.stringify(selected.length) }}</span
              >건
            </p>
          </div>
          <div class="all_right">
            <p>
              {{ totalCost.toLocaleString() }}
              <span>원</span>
            </p>
            <!-- <BtnComp_default btn-label="주문등록" @click="onClickOrderReg" /> -->
            <BtnComp_default_c @click="onClickOrderReg">
              <span style="font-size: 20px"> 주문등록 </span>
            </BtnComp_default_c>
          </div>
        </div>
      </template>
    </q-table>
  </div>
  <HistoryPopup :is-open="isOpenHistory" @close="isOpenHistory = false" />
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { computed, ref, onMounted, inject, toRaw } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { mdiHeart, mdiHeartOutline } from "@mdi/js";

// import RadioComp_a from "src/components/web/common/RadioComp_a.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_default_d from "src/components/web/common/BtnComp_default_d.vue";
import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
import BtnComp_active_small from "src/components/web/common/BtnComp_active_small.vue";
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

  if (val == 0) {
    if (rows.value.length > 0) {
      selected.value = rows.value;
    }
  } else if (val == 1) {
    const newRows = toRaw(rows.value).filter(
      (item) => item.PAY_ITEM_YN === "Y" && item.MED_CD === "02"
    );
    if (newRows.length > 0) {
      selected.value = newRows;
    }
  } else if (val == 2) {
    const newRows = toRaw(rows.value).filter(
      (item) => item.PAY_ITEM_YN === "N" && item.MED_CD === "02"
    );
    if (newRows.length > 0) {
      selected.value = newRows;
    }
  } else if (val == 3) {
    const newRows = toRaw(rows.value).filter(
      (item) => item.PAY_ITEM_YN === "Y" && item.MED_CD === "01"
    );
    if (newRows.length > 0) {
      selected.value = newRows;
    }
  } else if (val == 4) {
    const newRows = toRaw(rows.value).filter(
      (item) => item.PAY_ITEM_YN === "N" && item.MED_CD === "01"
    );
    if (newRows.length > 0) {
      selected.value = newRows;
    }
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
  return `전문비급 ${prfssnlNonPmntCnt}건`;
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
  return `일반비급 ${prfssnlPmntCnt}건`;
});

const btnWideAreaLabel = computed(() => {
  const wideAreaCnt = toRaw(rows.value).filter(
    (item) => item.G_DLV_BRCH_FLAG === "G"
  ).length;
  return `광역 ${wideAreaCnt}건`;
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

//---------------------------- Function ------------------------------//

// 장바구니 목록 조회
async function fetchBasketList(filterVal) {
  //필터 체크
  let selectChk = "all";

  if (filterVal != undefined) {
    selectChk = filterVal;
  }

  selected.value = [];
  loading.value = true;

  const res = await api.get("/ord/basketList", {
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

  // 다빈도 장바구니 품목 수 전달
  bus.emit("BasketQty2", rows.value.length);
}

// 주문수량 변경 버튼
async function onClickUpdateQty(row) {
  //필터값
  let filterVal = filterCond.value;

  rowInfo.value.itemQty = util.getNumberFormat2(row.ITEM_QTY);
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
    row.ORD_WP2_AMT = row.ORD_WP2_AMT_ORG;
    return;
  }

  if (!checkBasket2(row)) {
    return;
  }

  await api.put("/ord/updateItemQty", rowInfo.value);

  // 변경되었는지 확인하기 애매해서 추가함.. 요청 있을경우 삭제
  util.showNotify(
    `${row.ITEM_NM} 단가 ${util.getNumberFormat(
      row.ORD_WP2_AMT_ORG
    )}원 -> ${util.getNumberFormat(row.ORD_WP2_AMT)}원
    / 수량${util.getNumberFormat(row.ITEM_QTY_ORG)}개 -> ${util.getNumberFormat(
      row.ITEM_QTY
    )}개 변경되었습니다.`
  );

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
  const sumItemQty = obj.SUM_ITEM_QTY;
  const minOrdQty = obj.MIN_ORD_QTY;
  const maxOrdQty = obj.MAX_ORD_QTY;
  const itemQty = util.getNumberFormat2(obj.ITEM_QTY);
  const availStock = obj.AVAIL_STOCK;
  const itemQtyOrg = obj.ITEM_QTY_ORG;
  const ordWp2AmtOrg = obj.ORD_WP2_AMT_ORG;
  const minWp2Amt = obj.MIN_WP2_AMT;
  const ordWp2Amt = util.getNumberFormat2(obj.ORD_WP2_AMT);
  const saleUnitQty = obj.SALE_UNIT_QTY;
  const maxAmt = obj.MAX_AMT;

  if (itemQty == null || itemQty == "" || itemQty == "0") {
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
    // 최대 수량보다 기주문수량 + 주문하려고 하는 수량이 더 많을 때 return;
    alert(
      itemNm + "\n일누적 최대주문가능수량(" + maxOrdQty + ")을 초과하였습니다."
    );
    return false;
  }

  if (ordWp2Amt == null || ordWp2Amt == "" || ordWp2Amt == "0") {
    alert(itemNm + "\n단가를 확인해 주세요.");
    return false;
  }

  if (wp2ChgYn == "Y" && minWp2Amt > ordWp2Amt) {
    alert(itemNm + "\n입력한 단가가 최소 단가보다 적습니다.");
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
  const sumItemQty = obj.SUM_ITEM_QTY;
  const maxOrdQty = obj.MAX_ORD_QTY;
  const itemQty = util.getNumberFormat2(obj.ITEM_QTY);
  const ordWp2AmtOrg = obj.ORD_WP2_AMT_ORG;
  const minWp2Amt = obj.MIN_WP2_AMT;
  const ordWp2Amt = util.getNumberFormat2(obj.ORD_WP2_AMT);
  const maxAmt = obj.MAX_AMT;

  if (maxOrdQty < sumItemQty + itemQty) {
    // 최대 수량보다 기주문수량 + 주문하려고 하는 수량이 더 많을 때 return;
    alert(
      itemNm + "\n일누적 최대주문가능수량(" + maxOrdQty + ")을 초과하였습니다."
    );
    obj.ITEM_QTY = obj.ITEM_QTY_ORG;
    return false;
  }

  if (ordWp2Amt == null || ordWp2Amt == "" || ordWp2Amt == "0") {
    alert(itemNm + "\n단가를 확인해 주세요.");
    return false;
  }

  if (wp2ChgYn == "Y" && minWp2Amt > ordWp2Amt) {
    alert(itemNm + "\n입력한 단가가 최소 단가 보다 적습니다.");
    obj.ORD_WP2_AMT = ordWp2AmtOrg;
    return false;
  }

  //20190117 최대단가 체크로직 추가
  if (maxAmt < ordWp2Amt) {
    alert(itemNm + "\n입력한 단가가 최대 단가(" + maxAmt + ") 보다 많습니다.");
    obj.ORD_WP2_AMT = ordWp2AmtOrg;
    return false;
  }

  return true;
}

// 주문등록 클릭
async function onClickOrderReg() {
  let errorCnt = 0;
  let wideOrderCnt = 0;

  if (selected.value.length > 100) {
    alert(
      `100개 품목을 초과하였습니다. 품목 (${
        rows.value.length - 100
      }개)을 삭제하여 주시기 바랍니다.  `
    );
    return;
  }

  if (selected.value.length === 0) {
    alert("주문할 품목을 선택해 주시기 바랍니다.");
    return;
  }

  selected.value.forEach((item) => {
    /*2018.08.21 약국개설일 등록여부 체크*/
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

    //2018.10.30 약국개설일이 현재일보다 같거나 작으면 주문입력이 가능합니다.
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

    // if (wideOrderCnt > 0) {
    //   orderMsg += `\n\n광역 주문 상품의 경우\n 배송에 2일 이내 소요될 수 있습니다.`;
    // }

    if (confirm(orderMsg)) {
      selected.value.forEach((m) =>
        Object.assign(m, {
          ORD_MEMO: ordMemo.value,
          BRCH_CD: userInfo.BRCH_CD,
          CUST_CD: userInfo.CUST_CD,
          DEPT_CD: userInfo.DEPT_CD,
          EMP_ID: userInfo.EMP_ID,
          USER_ID: userInfo.USER_ID,
        })
      );

      await api.post("/ord/orderReg", selected.value);

      util.showNotify("주문접수 처리되었습니다.");

      await bus.emit("refreshHeaderBasket");
      await fetchBasketList();

      selected.value = [];
      ordMemo.value = "";
    }
  }
}

// 수량에 숫자 외 입력 방지 q-input에서 @input 작동하지않음
// function updateCount(props, evt) {
//   const colNm = props.col.name;
//   const isNumber = !isNaN(evt.target.value);
//   const isNotNull = evt.target.value !== "" ? true : false;

//   if (colNm === "ORD_WP2_AMT") {
//     if (isNumber && isNotNull) {
//       props.row.ORD_WP2_AMT = parseInt(evt.target.value);
//     } else {
//       props.row.ORD_WP2_AMT = 0;
//     }
//   } else if (colNm === "ITEM_QTY") {
//     if (isNumber && isNotNull) {
//       props.row.ITEM_QTY = parseInt(evt.target.value);
//     } else {
//       props.row.ITEM_QTY = 0;
//     }
//   }
// }

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

    // await fetchBasketList();
    await bus.emit("refreshOrderList");
  } catch (error) {
    alert(error.response.data);
  }
}

// 제품 출고 이력 팝업호출
async function onClickItemRow(row) {
  // bus.emit("basketHistory", row);
  // isOpenHistory.value = true;

  await bus.emit("basketHistoryList", row); // 제품출고이력
  await bus.emit("itemDetailInfo", row); // 제품정보
}

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
};

//-------------------------- Grid Setting ----------------------------//

const columns = [
  {
    name: "ITEM_NM",
    required: true,
    label: "품목 및 규격",
    align: "left",
    field: "ITEM_NM",
    sortable: true,
  },
  {
    name: "MAKER_NM",
    label: "제약회사",
    field: "MAKER_NM",
    align: "left",
  },
  {
    name: "ORD_WP2_AMT",
    label: "단가",
    field: "ORD_WP2_AMT",
    align: "center",
    format: (val) => util.getNumberFormat(val),
  },
  { name: "ITEM_QTY", label: "수량", field: "ITEM_QTY", align: "center" },
  { name: "FAVORITE_YN", label: "관심", field: "FAVORITE_YN", align: "center" },
  { name: "삭제", label: "삭제", field: "삭제", align: "center" },
];
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
}
.q-table_radio_wrap {
  padding: 2px;
}
.selected td:first-child {
  padding: 20px;
}
.q-table--col-auto-width {
  padding: 0 0 0 16px !important;
}
.q-checkbox--dense .q-checkbox__inner {
  width: 0.4em;
  min-width: 0.4em;
  height: 0.4em;
}

.table_widearea {
  span {
    @include fs-5;
    @include fw-7;
    border: 1px solid $r1;
    border-radius: 20px;
    padding: 2px 4px;
    color: $r1;
    font-size: 10px;
  }
}
</style>

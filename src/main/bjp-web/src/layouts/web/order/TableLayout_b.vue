<template>
  <div class="Table_wrap" style="width: 660px">
    <div class="table_b_header">
      <div class="header_left">
        <p>장바구니</p>
        <div class="table_btn">
          <q-btn
            :class="btnAllClass"
            label="전체"
            dense
            @click="onClickBtnAll"
          />
          <q-btn
            :class="btnWideAreaClass"
            :label="btnWideAreaLabel"
            dense
            @click="onClickBtnWideArea"
          >
            <q-badge rounded floating>
              ?
              <q-tooltip
                class="tooltip"
                anchor="center right"
                self="center left"
              >
                <p style="font-size: 14px">
                  광역주문은 해당 품목의 재고가 부족하여, <br />
                  타 물류로부터 영업일 기준 2일 이내 배송됩니다.
                </p>
              </q-tooltip>
            </q-badge>
          </q-btn>
        </div>
      </div>
      <div class="header_right">
        <BtnComp_default btnLabel="주문등록" @click="onClickOrderReg" />
      </div>
    </div>

    <!-- <TableComp_b /> -->
    <div class="table_sec">
      <q-radio
        v-model="filterCond"
        olor="blue-10"
        size="xs"
        inline
        dense
        val="all"
        :label="radioAll"
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
      />
      <q-table
        class="table_b_wrap"
        :rows="filteredRows"
        :columns="columns"
        row-key="ITEM_CD"
        :selected-rows-label="getSelectedString"
        selection="multiple"
        color="blue-10"
        v-model:selected="selectedItems"
        table-header-class="table-header-class"
        table-class="table-class_b"
        dense
        virtual-scroll
        :rows-per-page-options="[0]"
        :virtual-scroll-sticky-size-start="48"
        :loading="loading"
      >
        <template v-slot:loading>
          <q-inner-loading showing color="primary" />
        </template>
        <template v-slot:body-cell-name="props">
          <q-td
            :props="props"
            style="padding: 0; width: 140px"
            @click="onClickRow(props)"
          >
            <div
              class="my-table-details table_font-size-14"
              style="font-weight: bold"
            >
              {{ props.value }}
            </div>
            <p v-if="props.row.수량 === 0" class="table_font-size-12">
              (제약사품절)
            </p>
          </q-td>
        </template>

        <HistoryPop :isOpen="isOpenHistory" @close="closeHistory" />

        <template v-slot:body-cell-수량="props">
          <q-td :props="props">
            <div class="quantity_flex_wrap">
              <input
                v-model="props.row.ITEM_QTY"
                @input="updateCount(props)"
                style="width: 58px; text-align: center"
              />
              <q-btn
                class="quantity_btn"
                dense
                label="변경"
                @click="onClickUpdateQty(props.row)"
              />
            </div>
          </q-td>
        </template>

        <template v-slot:body-cell-관심="props">
          <q-td :props="props">
            <div class="">
              <q-checkbox
                class="like_wrap"
                v-model="props.row.FAVORITE_YN"
                :checked-icon="mdiHeart"
                :unchecked-icon="mdiHeartOutline"
                true-value="Y"
                false-value="N"
                @update:model-value="(val) => onCheckInterest(props.row, val)"
              />
            </div>
          </q-td>
        </template>

        <template v-slot:body-cell-삭제="props">
          <q-td :props="props" @click="onClickDelete(props.row)">
            <svg-icon
              type="mdi"
              :path="mdiClose"
              style="color: #999999; width: 18px; height: 18px"
            ></svg-icon>
          </q-td>
        </template>

        <template v-slot:bottom>
          <q-input
            v-model="ordMemo"
            class="bottom_input"
            outlined
            dense
            maxlength="2000"
            placeholder="메모"
            type="textarea"
          />
          <div class="all_price">
            <div class="all_left">
              <p>총 주문 품목</p>
              <p>
                <span>{{ selectedItems.length }}</span
                >건
              </p>
            </div>
            <div class="all_right">
              <p>
                {{ totalCost.toLocaleString() }}
                <span>원</span>
              </p>
              <BtnComp_default btnLabel="주문등록" @click="onClickOrderReg" />
            </div>
          </div>
        </template>
      </q-table>
    </div>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { computed, ref, onMounted, inject, toRaw } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { mdiHeart, mdiHeartOutline, mdiClose } from "@mdi/js";
import SvgIcon from "@jamescoyle/vue-icon";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import HistoryPop from "src/pages/web/BjpItemHistoryP.vue";

const userInfo = util.getUserData();
const bus = inject("bus");
const selectedItems = ref([]);
const ordMemo = ref("");
const rows = ref([]);
const loading = ref(false);
const isOpenHistory = ref(false);

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

const btnAllClass = computed(() =>
  basketSearchParam.value.gDlvBrchFlag === "G" ? "btn_active" : "btn"
);

const btnWideAreaClass = computed(() =>
  basketSearchParam.value.gDlvBrchFlag === "G" ? "btn" : "btn_active"
);

const basketSearchParam = ref({
  userGbCd: util.getUserData().USER_GB_CD,
  custCd: util.getUserData().CUST_CD,
  basketGbCd: "01",
  gDlvBrchFlag: "",
});

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

const filterCond = ref("all");

const filteredRows = computed(() => {
  if (rows.value.length > 0) {
    if (filterCond.value === "01") {
      // 전문급여
      return toRaw(rows.value).filter(
        (item) => item.PAY_ITEM_YN === "Y" && item.MED_CD === "02"
      );
    } else if (filterCond.value === "02") {
      // 전문비급
      return toRaw(rows.value).filter(
        (item) => item.PAY_ITEM_YN === "N" && item.MED_CD === "02"
      );
    } else if (filterCond.value === "03") {
      // 일반급여
      return toRaw(rows.value).filter(
        (item) => item.PAY_ITEM_YN === "Y" && item.MED_CD === "01"
      );
    } else if (filterCond.value === "04") {
      // 일반비급
      return toRaw(rows.value).filter(
        (item) => item.PAY_ITEM_YN === "N" && item.MED_CD === "01"
      );
    }
  }

  // 전체
  return rows.value;
});

const totalCost = computed(() => {
  let cost = 0;

  if (selectedItems.value.length > 0) {
    selectedItems.value.forEach((item) => {
      cost +=
        Number(item.ORD_WP2_AMT) * Number(item.ITEM_QTY ?? item.ITEM_QTY_ORG);
    });
  }

  return cost;
});

//---------------------------- Function ------------------------------//

function getSelectedString() {
  return selectedItems.value.length === 0
    ? ""
    : `${selectedItems.value.length} record${
        selectedItems.value.length > 1 ? "s" : ""
      } selected of ${rows.value.selectedItems.length}`;
}

// 장바구니 목록 조회
async function fetchBasketList() {
  loading.value = true;

  const res = await api.get("/ord/basketList", {
    params: basketSearchParam.value,
  });

  rows.value = res;
  loading.value = false;
}

// 주문수량 변경 버튼
async function onClickUpdateQty(row) {
  rowInfo.value.itemQty = row.ITEM_QTY;
  rowInfo.value.ordWp2Amt = row.ORD_WP2_AMT;
  rowInfo.value.saveItemGbCd = row.ITEM_GB_CD;
  rowInfo.value.saveItemCd = row.ITEM_CD;
  rowInfo.value.dlvBrchCd = row.DLV_BRCH_CD;

  await api.put("/ord/updateItemQty", rowInfo.value);

  // 변경되었는지 확인하기 애매해서 추가함.. 요청 있을경우 삭제
  util.showNotify(
    `${row.ITEM_NM} ${row.ITEM_QTY_ORG}개 -> ${row.ITEM_QTY}개 변경되었습니다.`
  );

  await fetchBasketList();
}

// 장바구니 목록 단건 삭제
async function onClickDelete(row) {
  // lint 정렬때문에 컨펌 메세지 틀어져서 이렇게 처리함..
  let confirmMsg = `품목 : ${row.ITEM_NM}\n`;
  confirmMsg += `수량 : ${row.ITEM_QTY}\n`;
  confirmMsg += `취소하시겠습니까?`;

  if (!confirm(confirmMsg)) return;

  rowInfo.value.saveItemGbCd = row.ITEM_GB_CD;
  rowInfo.value.saveItemCd = row.ITEM_CD;
  rowInfo.value.dlvBrchCd = row.DLV_BRCH_CD;

  await api.delete("/ord/deleteComOrdBasket", {
    params: rowInfo.value,
  });

  await fetchBasketList();
}

// 수량에 숫자 외 입력 방지
function updateCount(props) {
  const newValue = parseInt(props.row.ITEM_QTY);
  // const orgnlItemQty = parseInt(props.row.ORGNL_ITEM_QTY);

  if (!isNaN(newValue)) {
    return newValue;
  } else {
    props.row.ITEM_QTY = undefined;
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

// 주문 품목 유효성 체크
function checkBasket(obj) {
  const itemCd = obj.ITEM_CD;
  const wp2ChgYn = obj.WP2_CHG_YN;
  const itemNm = obj.ITEM_NM;
  const sumItemQty = obj.SUM_ITEM_QTY;
  const minOrdQty = obj.MIN_ORD_QTY;
  const maxOrdQty = obj.MAX_ORD_QTY;
  const itemQty = obj.ITEM_QTY;
  const availStock = obj.AVAIL_STOCK;
  const itemQtyOrg = obj.ITEM_QTY_ORG;
  const ordWp2AmtOrg = obj.ORD_WP2_AMT_ORG;
  const minWp2Amt = obj.MIN_WP2_AMT;
  const ordWp2Amt = obj.ORD_WP2_AMT;
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
    alert(itemNm + "\n입력한 단가가 최소 단가 보다 적습니다.");
    return false;
  }

  //20190117 최대단가 체크로직 추가
  if (maxAmt < ordWp2Amt) {
    alert(itemNm + "\n입력한 단가가 최대 단가(" + maxAmt + ") 보다 많습니다.");
    return false;
  }

  return true;
}

// 주문등록 클릭
async function onClickOrderReg() {
  let errorCnt = 0;
  let wideOrderCnt = 0;

  if (selectedItems.value.length === 0) return;

  selectedItems.value.forEach((item) => {
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
    let orderMsg = `총 ${selectedItems.value.length}건을 주문하시겠습니까?`;

    if (wideOrderCnt > 0) {
      orderMsg += `\n\n광역 주문 상품의 경우\n 배송에 2일 이내 소요될 수 있습니다.`;
    }

    if (confirm(orderMsg)) {
      selectedItems.value.forEach((m) =>
        Object.assign(m, {
          ORD_MEMO: ordMemo.value,
          BRCH_CD: userInfo.BRCH_CD,
          CUST_CD: userInfo.CUST_CD,
          DEPT_CD: userInfo.DEPT_CD,
          EMP_ID: userInfo.EMP_ID,
          USER_ID: userInfo.USER_ID,
        })
      );

      await api.post("/ord/orderReg", selectedItems.value);

      util.showNotify("주문접수 처리되었습니다.");

      await fetchBasketList();

      selectedItems.value = [];
    }
  }
}

// 그리드 row 클릭
function onClickRow(props) {
  isOpenHistory.value = true;
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

    await fetchBasketList();
  } catch (error) {
    alert(error.response.data);
  }
}

// 주문 그리드에서 담기 버튼 클릭 시 장바구니 목록 재조회
bus.on("refreshBasket", async () => {
  await fetchBasketList();
});

onMounted(async () => {
  await fetchBasketList();
});

//-------------------------- Grid Setting ----------------------------//

const columns = [
  {
    name: "name",
    required: true,
    label: "품목 및 규격",
    align: "left",
    field: "ITEM_NM",
    format: (val) => util.textShortter(val, 10, 9),
    sortable: true,
  },
  {
    name: "제약사",
    label: "제약사",
    field: "MAKER_NM",
    align: "center",
    format: (val) => util.textShortter(val, 5, 3),
  },
  {
    name: "단가",
    label: "단가",
    field: "ORD_WP2_AMT",
    align: "center",
    format: (val) => util.getNumberFormat(val),
  },
  { name: "수량", label: "수량", field: "ITEM_QTY", align: "center" },
  { name: "현재고", label: "현재고", field: "AVAIL_STOCK", align: "center" },
  { name: "관심", label: "관심", field: "FAVORITE_YN", align: "center" },
  { name: "삭제", label: "삭제", field: "삭제", align: "center" },
];
</script>

<style lang="scss">
.Table_wrap {
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.33);
}

.table_b_header {
  @include flex_between;
}
.table_b_header p {
  @include fw-7;
}
.header_left,
.table_btn {
  @include flex_center;
}
.header_left {
  gap: 14px;
}
.table_btn {
  gap: 8px;
}

.quantity_btn {
  @include gradation;
  border-radius: 5px !important;
  color: $w;
  padding: 0 3px;
}
.quantity_btn span {
  @include fs-5;
}

.table_b_wrap {
  height: 455px;
  border: none;
}
.table_font-size-14 {
  @include fs-5;
}
.table_font-size-12 {
  @include fs-6;
}
.table_sec div {
  padding: 0;
  box-shadow: none !important;
}
.table-header-class th {
  @include fw-7;
  background-color: #eee;
  border-top: 2px solid $b4 !important;
  height: 48px;
}
.q-radio--dense .q-radio__label {
  font-size: 14px !important;
  padding: 10px 5px;
}

.table_b_wrap thead tr th {
  position: sticky;
  z-index: 1;
}
.table_b_wrap thead tr:last-child th {
  top: 48px;
}
.table_b_wrap thead tr:first-child th {
  top: 0;
}
.table_b_wrap tbody {
  scroll-margin-top: 48px;
}

.quantity_subWrap p {
  @include flex_center;
  border: 1px solid #666666;
  border-radius: 2.4px;
  padding: 2px 24px !important;
}
.quantity_flex_wrap {
  @include flex_center;
  gap: 8px;
}
.quantity_btn {
  @include gradation;
  border-radius: 5px;
  color: $w;
  padding: 0 3px;
}
.quantity_btn span {
  @include fs-5;
}

.like_wrap .q-icon {
  font-size: 20px;
}
.like_wrap .q-icon {
  // color: $r2;
}

.x_img {
  cursor: pointer;
}

.bottom_input .q-table--dense .q-table__bottom {
  height: 40px;
}
.q-table__bottom {
  // @include flex_between;
  @include flex_end;
  padding-top: 40px !important;
}
.all_price,
.all_right {
  @include flex_center;
  gap: 8px;
}
.all_left {
  text-align: right;
}
.all_left p {
  @include fs-5;
  @include fw-7;
}
.all_left p span {
  @include fs-5;
  @include fw-7;
  color: $b3;
}
.all_right {
  padding: 6px 8px 6px 28px !important;
  background-color: $b5;
}
.all_right p {
  @include fs-3;
  @include fw-7;
  color: $b3;
}
.all_right p span {
  font-size: 16px;
  color: $b;
  margin-left: 2px;
}

.btn_active {
  background-color: #999999;
  border-radius: 5px !important;
  padding: 0 5px;
}
.btn_active span {
  @include fs-5;
  color: #ffffff !important;
}
.btn_active:active,
.btn_active:hover,
.btn_active:focus {
  @include gradation;
  border-radius: 5px;
  color: $w;
  padding: 0 5px;
}

.btn {
  @include gradation;
  border-radius: 5px !important;
  color: $w;
  padding: 0 16px;
}
.btn span {
  @include fs-5;
  color: #ffffff !important;
}
</style>

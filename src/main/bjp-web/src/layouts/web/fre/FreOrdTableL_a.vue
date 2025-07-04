<template>
  <div class="Table_wrap" style="width: 900px">
    <div class="table_a_header">
      <p>다빈도</p>
      <div class="table_check">
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
          v-model="searchParam.professionalYn"
          color="blue-10"
          size="xs"
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
          size="xs"
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
          size="xs"
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
          size="xs"
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

    <!-- <TableComp_a /> -->
    <div class="table_a_sec">
      <q-table
        class="table_a_wrap"
        :rows="rows"
        :columns="columns"
        row-key="name"
        :hide-bottom="isHideBottom"
        table-header-class="table-header-class"
        table-class="table-class_a"
        dense
        virtual-scroll
        :rows-per-page-options="[0]"
        :virtual-scroll-sticky-size-start="48"
        no-data-label="검색된 결과가 없습니다."
        v-model:pagination="pagination"
        :loading="loading"
      >
        <template v-slot:loading>
          <q-inner-loading showing color="primary" />
        </template>

        <template v-slot:top-left>
          <div class="top-left_input_wrap">
            <q-input
              v-model="searchParam.keyword"
              input-class="top-left_input"
              borderless
              dense
              placeholder="보험코드/품명 입력"
            />
            <q-input
              v-model="searchParam.makerNm"
              input-class="top-left_input"
              borderless
              dense
              placeholder="제약사 입력"
            />
            <BtnComp_default btnLabel="검색" @click="onClickItemSearch" />
            <q-checkbox
              v-model="searchParam.excludingOutOfOtock"
              class="top-left_check"
              true-value="Y"
              false-value="N"
              label="품절제외"
            />
            <!-- <q-option-group
            class="top-left_check"
            v-model="top_check"
            :options="searchParam"
            type="checkbox"
            color="blue-10"
            size="xs"
            inline
            dense
          /> -->
          </div>
        </template>

        <template v-slot:top-right>
          <div class="top-right_btn">
            <BtnComp_default btnLabel="동일성분" @click="onClickIng" />
            <BtnComp_default btnLabel="동일효능" @click="onClickEff" />
          </div>
        </template>

        <template v-slot:body-cell-ITEM_NM="props">
          <q-td
            :props="props"
            style="padding: 0; width: 240px"
            @click="onRowClick(props)"
          >
            <div class="cell_name">
              <div class="cell_name_badge">
                <q-badge text-color="red" color="white">
                  {{ props.row.MED_NM }}
                </q-badge>
                <q-badge text-color="blue" color="white">
                  {{ props.row.PAY_ITEM_NM }}
                </q-badge>
              </div>
              <div
                class="my-table-details table_font-size-14"
                style="font-weight: bold"
              >
                {{ props.value }}
              </div>
            </div>
          </q-td>
        </template>

        <template v-slot:body-cell-UNIT="props">
          <q-td
            :props="props"
            style="padding: 0; width: 100px"
            @click="onRowClick(props)"
          >
            {{ props.value }}
          </q-td>
        </template>

        <template v-slot:body-cell-ORD_WP2_AMT="props">
          <q-td
            :props="props"
            style="padding: 0; width: 65px"
            @click="onRowClick(props)"
          >
            <div class="my-table-details table_font-size-14">
              {{ props.value }}
            </div>
          </q-td>
        </template>

        <template v-slot:body-cell-AVAIL_STOCK="props">
          <q-td :props="props" style="padding: 0" @click="onRowClick(props)">
            <template v-if="isNumber(props.value) && props.value !== 0">
              {{ props.value }}
            </template>
            <template v-else>
              <div
                class="my-table-details table_font-size-14"
                style="color: #f5536c; font-weight: bold"
              >
                품절
              </div>
            </template>
          </q-td>
        </template>

        <template v-slot:body-cell-REQ_QTY="props">
          <q-td :props="props" style="width: 83px">
            <div
              v-if="!isDisableReqQty(props.row.AVAIL_STOCK)"
              class="quantity_wrap"
            >
              <div class="quantity_img_wrap" @click="decrement(props)">
                <svg-icon
                  type="mdi"
                  :path="mdiMinus"
                  style="width: 14px; height: 14px; color: #666666"
                />
              </div>
              <p class="table_font-size-14">
                <!-- <input
                  class=""
                  v-model="count"
                  @input="updateCount"
                  style="border: none; width: 16px; text-align: center"
                /> -->
                <input
                  v-model="props.row.REQ_QTY"
                  @input="updateCount(props)"
                  style="border: none; width: 16px; text-align: center"
                  :disabled="isDisableReqQty(props.row.AVAIL_STOCK)"
                />
              </p>
              <div class="quantity_img_wrap" @click="increment(props)">
                <svg-icon
                  type="mdi"
                  :path="mdiPlus"
                  style="width: 14px; height: 14px; color: #666666"
                />
              </div>
            </div>
          </q-td>
        </template>

        <template v-slot:body-cell-ADD_BASKET="props">
          <q-td :props="props" style="width: 50px" @click="onRowClick(props)">
            <div
              v-if="!isDisableReqQty(props.row.AVAIL_STOCK)"
              :class="
                props.row.G_DLV_BRCH_FLAG === 'G'
                  ? 'add_wrap table_font-size-14'
                  : 'minus_wrap table_font-size-14'
              "
            >
              {{ props.row.G_DLV_BRCH_FLAG === "G" ? "광역" : "담기" }}
            </div>
            <!-- <div class="minus_wrap table_font-size-14">
              {{ props.row.담기 === "담기" ? "담기" : "" }}
            </div> -->
          </q-td>
        </template>

        <template v-slot:body-cell-FAVORITE_YN="props">
          <q-td :props="props" style="width: 70px">
            <div class="">
              <q-checkbox
                class="like_wrap"
                v-model="props.row.FAVORITE_YN"
                :checked-icon="mdiHeart"
                :unchecked-icon="mdiHeartOutline"
                true-value="Y"
                false-value="N"
              />
            </div>
          </q-td>
        </template>
      </q-table>
    </div>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject, computed, onMounted } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";
import {
  mdiHeart,
  mdiHeartOutline,
  mdiCheck,
  mdiPlus,
  mdiMinus,
} from "@mdi/js";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import SvgIcon from "@jamescoyle/vue-icon";

const bus = inject("bus");
const userInfo = util.getUserData();
const pagination = ref({ rowsPerPage: 0 });
const isHideBottom = computed(() => rows.value.length > 0);
const loading = ref(false);

const searchParam = ref({
  today: moment().format("YYYYMMDD"),
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

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  loading.value = true;

  const res = await api.get("/freOrd/itemSearch", {
    params: searchParam.value,
  });

  rows.value = res;
  loading.value = false;

  // 출고이력, 제품정보 그리드 초기화
  bus.emit("init");

  // 검색결과 있으면 첫번째 row 정보 뿌려줌(출고이력, 제품정보)
  if (rows.value.length > 0) {
    await bus.emit("freOrdHistoryList", rows.value[0]);
    await bus.emit("itemDetailInfo", rows.value[0].ITEM_CD);
  }
}

// 검색 버튼 클릭
async function onClickItemSearch() {
  searchParam.value.ing = "N";
  searchParam.value.ingno = "";
  searchParam.value.eff = "N";
  searchParam.value.effno = "";

  await fetchItemList();
}

// 동일성분 버튼 클릭
async function onClickIng() {
  searchParam.value.ing = "Y";
  searchParam.value.eff = "N";

  await fetchItemList();
}

// 동일효능 버튼 클릭
async function onClickEff() {
  searchParam.value.ing = "N";
  searchParam.value.eff = "Y";

  await fetchItemList();
}

function isNumber(value) {
  return typeof value === "number" && !isNaN(value);
}

// 수량에 숫자 외 입력 방지
function updateCount(props) {
  const newValue = parseInt(props.row.REQ_QTY);

  if (!isNaN(newValue)) {
    return newValue;
  } else {
    props.row.REQ_QTY = undefined;
  }
}

// 수량 증가
function increment(props) {
  if (props.row.REQ_QTY > 0) {
    props.row.REQ_QTY++;
  } else {
    props.row.REQ_QTY = 0;
    props.row.REQ_QTY++;
  }
}

// 수량 감소
function decrement(props) {
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
    }
  } else {
    if ([professionalYn, generalYn, paymentYn, nonPaymentYn].includes("Y")) {
      searchParam.value.searchAll = "N";
    }
  }
}

// 장바구니 담기
async function addBasket(row) {
  basketData.value.saveItemCd = row.ITEM_CD;
  basketData.value.dlvBrchCd = row.DLV_BRCH_CD;
  basketData.value.saveItemQty = row.REQ_QTY === 0 ? 1 : row.REQ_QTY;

  await api.post("/freOrd/addBasket", basketData.value);
  util.showNotify(
    `${row.ITEM_NM} ${basketData.value.saveItemQty}개를 장바구니에 담았습니다.`
  );
  await bus.emit("refreshFreOrdBasket");
}

// 그리드 클릭
async function onRowClick(row) {
  const fieldNm = row.col.field;

  // 담기
  if (fieldNm === "ADD_BASKET") {
    await addBasket(row.row);
  }
  // 반품
  else if (fieldNm === "WEB_BACK_USE_YN") {
    if (row.row.WEB_BACK_USE_YN === "Y") {
      alert(fieldNm);
      // TODO: 팝업 띄워야함
    } else {
      return;
    }
  } else {
    searchParam.value.ingno = row.row.ITEM_INGREDIENT_CD;
    searchParam.value.effno = row.row.ITEM_KIND_NO;

    await bus.emit("freOrdHistoryList", row.row);
    await bus.emit("itemDetailInfo", row.row.ITEM_CD);
  }
}

onMounted(async () => {
  await fetchItemList();
});

//----------------------- Grid Setting -------------------------//

const columns = [
  {
    name: "ITEM_NM",
    label: "품목",
    sortable: true,
    align: "left",
    required: true,
    // field: (row) => row.ITEM_NM,
    field: "ITEM_NM",
    format: (val) => util.textShortter(val, 15, 14),
  },
  { name: "UNIT", label: "규격", field: "UNIT", align: "right" },
  {
    name: "MAKER_NM",
    label: "제약사",
    field: "MAKER_NM",
    align: "left",
    sortable: true,
    format: (val) => util.textShortter(val, 5, 3),
  },
  {
    name: "ORD_WP2_AMT",
    label: "단가",
    field: "ORD_WP2_AMT",
    align: "right",
    format: (val) => util.getNumberFormat(val),
  },
  { name: "AVG_QTY", label: "평균주문수량", field: "AVG_QTY", align: "center" },
  {
    name: "ITEM_QTY_3",
    label: "전전월주문량",
    field: "ITEM_QTY_3",
    align: "center",
  },
  {
    name: "ITEM_QTY_2",
    label: "전월주문량",
    field: "ITEM_QTY_2",
    align: "center",
  },
  {
    name: "ITEM_QTY_1",
    label: "당월주문량",
    field: "ITEM_QTY_1",
    align: "center",
  },
  { name: "AVAIL_STOCK", label: "재고", field: "AVAIL_STOCK", align: "right" },
  { name: "REQ_QTY", label: "수량", field: "REQ_QTY", align: "right" },
  { name: "ADD_BASKET", label: "담기", field: "ADD_BASKET", align: "center" },
  { name: "FAVORITE_YN", label: "관심", field: "FAVORITE_YN", align: "center" },
];

const rows = ref([]);
</script>

<style lang="scss" scoped>
.Table_wrap {
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.33);
}

.table_a_header {
  @include flex_start;
}
.table_a_header p {
  @include fw-7;
}
.table_check {
  margin-left: 20px;
}
.q-checkbox--dense .q-checkbox__label {
  @include fs-5;
}

.table_a_wrap {
  height: 500px;
  border: none;
  overflow-y: auto;

  /* Custom scrollbar styles for .table_a_wrap */
  scrollbar-width: thin;
  scrollbar-color: var(--scrollbar-thumb-dark) var(--scrollbar-track-dark);

  &::-webkit-scrollbar {
    width: var(--scrollbar-width-height);
  }

  &::-webkit-scrollbar-track {
    background: var(--scrollbar-track-dark);
  }

  &::-webkit-scrollbar-thumb {
    background-color: var(--scrollbar-thumb-dark);
    border-radius: 5px;

    &:hover {
      background-color: var(--scrollbar-thumb-hover-dark);
    }
  }
}
.table_font-size-14 {
  @include fs-5;
}
.table_font-size-12 {
  @include fs-6;
}

.table_a_sec div {
  padding: 0;
  box-shadow: none !important;
}
.table-header-class th {
  @include fw-7;
  background-color: #eee;
  border-top: 2px solid $b4 !important;
}

.table_a_wrap thead tr th {
  position: sticky;
  z-index: 1;
}
.table_a_wrap thead tr:last-child th {
  top: 48px;
}
.table_a_wrap thead tr:first-child th {
  top: 0;
}
.table_a_wrap tbody {
  scroll-margin-top: 48px;
}

.top-left_input_wrap {
  @include flex_center;
  gap: 8px;
  margin: 12px 44px;
}
.top-left_input {
  width: 120px;
  border: 1px solid #666666;
  border-radius: 8px;
  padding-left: 6px;
}
.top-left_input::placeholder {
  @include fs-5;
}
.top-left_input_wrap .q-field--dense .q-field__control {
  height: 28px !important;
}
.top-right_btn {
  @include flex_center;
  gap: 16px;
}
.top-left_check .q-checkbox--dense .q-checkbox__label {
  @include fs-6;
  @include fw-7;
}

.cell_name {
  @include flex_start;
  padding: 0 10px !important;
  gap: 5px;
}
.cell_name_badge {
  @include flex_center;
  gap: 8px;
}

.quantity_wrap {
  @include flex_center;
  margin: 0 auto;
  border: 1px solid #666666;
  border-radius: 2.4px;
  width: 55px;
}
.quantity_img_wrap {
  @include flex_center;
  cursor: pointer;
}
.quantity_img {
  padding: 5px 7px;
}

.add_wrap {
  color: $w;
  background-color: $b3;
  border-radius: 2.4px;
}

.minus_wrap {
  color: $w;
  background-color: #999999;
  border-radius: 2.4px;
}

.recode_wrap div {
  border: none !important;
}

.recode_wrap .q-icon,
.like_wrap .q-icon {
  font-size: 20px;
}
.recode_wrap .q-icon {
  color: $b3;
}
.like_wrap .q-icon {
  // color: $r2;
}
</style>

<template>
  <div class="">
    <q-table
      class="salesTable_2g"
      :rows="rows"
      :columns="columns"
      row-key="ITEM_CD"
      table-header-class="table_header"
      dense
      hide-bottom
      v-model:selected="selected"
      selection="multiple"
      color="blue-10"
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
      @row-click="onRowClick"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>

      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="q-table_header_wrap flex_between">
            <div class="q-table_header_title">
              <p>주문상세내역</p>
            </div>
            <div class="q-table_header_sub_wrap flex_end" style="gap: 8px">
              <ReorderPopup
                :is-open-popup="isOpenReorderPopup"
                :data="selectedData"
                :basketCnt="basketQty"
                @close="isOpenReorderPopup = false"
              />
              <BtnComp_default
                v-if="count != 1 && stats == '등록'"
                btn-label="개별주문취소"
                class="btn-line default"
                @click="onClickCnclRe"
              />
              <BtnComp_default
                v-if="reorderYn === 'Y'"
                btn-label="재주문"
                class="btn-line default"
                @click="onClickReorder"
              />
            </div>
          </div>
        </div>
      </template>

      <template v-slot:body-cell-ITEM_NM_UNIT="props">
        <q-td
          class="q-table_sales_product_wrap td-prd"
          :props="props"
          style="cursor: pointer"
        >
          <div class="q-table_item">
            <div class="q-table_badge_wrap">
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
              <div v-if="props.row.G_DLV_YN === 'Y'" class="table_widearea">
                <span>광역</span>
              </div>
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-MAKER_NM="props">
        <q-td :props="props" style="cursor: pointer" class="td-comp_cart">
          <div class="q-table_itemTitleSub ellipsis">
            {{ props.value }}
            <q-tooltip>{{ props.value }}</q-tooltip>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_QTY="props">
        <q-td :props="props" style="cursor: pointer" class="td-qty">
          <div>{{ props.value }}</div>
        </q-td>
      </template>

      <template v-slot:body-cell-ORD_WP2_AMT="props">
        <q-td
          :props="props"
          class="td-price"
          style="text-align: right; cursor: pointer"
        >
          <div>{{ props.value }}</div>
        </q-td>
      </template>

      <template v-slot:body-cell-TOTAL_AMT="props">
        <q-td
          :props="props"
          class="td-price"
          style="text-align: right; cursor: pointer"
        >
          <div :class="Number(props.row.TOTAL_AMT) < 0 ? 'red-400' : ''">
            {{ props.value }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-FAVORITE_YN="props">
        <q-td :props="props" class="td-wish">
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
        </q-td>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import {
  ref,
  inject,
  onMounted,
  onBeforeUnmount,
  onActivated,
  onDeactivated,
} from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { mdiHeart } from "@mdi/js";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import ReorderPopup from "./ReorderPopup.vue";

const bus = inject("bus");
const userInfo = util.getUserData();

const rows = ref([]);
const selected = ref([]);
const selectedData = ref([]);
const loading = ref(false);
const count = ref(0);
const stats = ref("");
const reorderYn = ref("N");
const isOpenReorderPopup = ref(false);
const basketQty = ref("");

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  tblNm: "",
  sn: "",
});

const fetchItemList = async () => {
  loading.value = true;
  selected.value = [];

  const res = await api.get("/recently/searchDtls", {
    params: searchParam.value,
  });
  rows.value = res;
  loading.value = false;

  count.value = rows.value.length;
  stats.value = rows.value[0]?.STATS ?? "";
  if (rows.value.length > 0) {
    const firstRow = rows.value[0];
    await bus.emit("historyList", firstRow);
    await bus.emit("recentlyItemDetailInfo", firstRow.ITEM_CD);
  } else {
    // 👉 데이터 없을 때는 명시적으로 초기화 이벤트 emit
    await bus.emit("init");
  }
};

const handleSearchRecentlyDtls = async (data) => {
  if (data.SN === searchParam.value.sn && rows.value.length > 0) return;
  searchParam.value.sn = data.SN;
  searchParam.value.tblNm = data.TBL_NM;
  reorderYn.value = data.REORDER_YN;
  await fetchItemList();
};

let isRegistered = false;

onMounted(() => {
  bus.off("searchRecentlyDtls"); // 👈 이거 무조건 한번 날려줘
  bus.on("searchRecentlyDtls", handleSearchRecentlyDtls);
});

onBeforeUnmount(() => {
  if (isRegistered) {
    bus.off("searchRecentlyDtls", handleSearchRecentlyDtls);
    isRegistered = false;
  }
});

onDeactivated(() => {
  if (isRegistered) {
    bus.off("searchRecentlyDtls", handleSearchRecentlyDtls);
    isRegistered = false;
  }
});

onActivated(() => {
  if (!isRegistered) {
    bus.on("searchRecentlyDtls", handleSearchRecentlyDtls);
    isRegistered = true;
  }
});

const onRowClick = async (evt, row) => {
  await bus.emit("historyList", row);
  await bus.emit("recentlyItemDetailInfo", row.ITEM_CD);
};

const onCheckInterest = async (row, val) => {
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

    await fetchItemList();
  } catch (error) {
    alert(error.response?.data || "오류 발생");
  }
};

const onClickReorder = () => {
  selectedData.value = [...selected.value];
  isOpenReorderPopup.value = true;
};

const onClickCnclRe = async () => {
  if (count.value === selected.value.length) {
    alert(
      "“개별 주문취소만 가능합니다. 전체 주문취소는 “주문취소” 로 진행해 주시기 바랍니다."
    );
    return;
  }

  if (!confirm(`${selected.value.length}건 주문 취소하시겠습니까?`)) return;

  for (const row of selected.value) {
    const iSn = `${row.SN},${row.SNO},${row.ITEM_CD}`;
    try {
      Object.assign(row, { I_SN: iSn, USER_ID: userInfo.USER_ID });
      await api.delete("/recently/cancelSelRcently", { params: row });
    } catch (error) {
      alert("오류가 발생했습니다.");
    }
  }

  util.showNotify("선택한 품목에 대해 주문 취소가 되었습니다.");
  await fetchItemList();
  await bus.emit("onRecentlyList");
};

const columns = [
  {
    name: "ITEM_NM_UNIT",
    label: "품목 및 규격",
    field: "ITEM_NM_UNIT",
    align: "left",
  },
  { name: "MAKER_NM", label: "제약사", field: "MAKER_NM", align: "left" },
  { name: "ITEM_QTY", label: "수량", field: "ITEM_QTY", align: "center" },
  {
    name: "ORD_WP2_AMT",
    label: "단가",
    field: "ORD_WP2_AMT",
    align: "right",
    format: (val) => util.getNumberFormat(val),
  },
  {
    name: "TOTAL_AMT",
    label: "금액",
    field: "TOTAL_AMT",
    align: "right",
    format: (val) => util.getNumberFormat(val),
  },
  {
    name: "FAVORITE_YN",
    label: "관심",
    field: "FAVORITE_YN",
    align: "center",
    classes: "td-wish",
  },
];
</script>

<style lang="scss" scoped>
.flex_center {
  @include flex_center;
  gap: 8px;
}

.salesTable_2g {
  height: 425px;
  overflow-y: auto;
  @media screen and (max-width: 1025px) and (min-width: 831px) {
    height: 485px;
  }
  @media screen and (max-width: 830px) {
    height: auto;
    min-height: 180px;
  }

  .q-table_header_title {
    min-width: fit-content;
  }
}

.flex_end {
  @include flex_end;
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
  width: 20px;
  min-width: 20px;
  height: 20px;
}
</style>

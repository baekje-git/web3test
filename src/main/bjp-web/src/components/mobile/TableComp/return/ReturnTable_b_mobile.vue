<template>
  <div class="">
    <q-table
      class=""
      row-key="약"
      :rows="filteredRows"
      flat
      hide-header
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      style="margin-bottom: 65px"
    >
      <template v-slot:top>
        <div class="table_Top_wrap">
          <div class="table_Title_mobile">
            <span>{{ TableTitle }}</span>
          </div>
          <div class="filter_area">
            <BtnGroupComp_mobile
              :buttons="btnGroups2"
              btnStyle="active"
              btn-size="lg"
              @on-click-btn="(val) => onClickFilterBtn(val)"
            />
          </div>
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div
            class="table_wrap"
            :class="props.row.SELECTED_YN == 'N' ? '' : 'table_active'"
          >
            <div class="table_item table_item_mobile">
              <q-checkbox
                class="table_checkbox_gray"
                v-model="props.row.SELECTED_YN"
                true-value="N"
                false-value="Y"
                dense
                :disable="props.row.AVAIL_STOCK === 0"
                @update:model-value="(val) => onChecRow(props.row, val)"
                checked-icon="img:assets/icons/chk-chk.svg"
                unchecked-icon="img:assets/icons/chk-none.svg"
              />
              <div class="table_badge_wrap" style="padding: 0">
                <!-- <q-badge class="table_badge_red">
                  {{ props.row.MED_NM }}
                </q-badge>
                <q-badge class="table_badge_blue">
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
              <q-td class="table-td_mobile">
                <p>
                  {{ props.row.ITEM_NM }}
                </p>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <DeleteComp_mobile
                  delete_text1="반품"
                  @delete-basket="onClickDelete(props.row)"
                >
                  <img src="assets/icons/xmark-solid.svg" alt="icon" />
                </DeleteComp_mobile>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{
                util.getNumberFormat(props.row.ITEM_QTY)
              }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <!-- <q-td v-if="props.row.EXP_DT !== ''" class="table-td_mobile">
                {{ moment(props.row.EXP_DT).format("YYYY.MM.DD") }}
              </q-td> -->
              <q-td class="table-td_mobile">
                <DatePickerComp_mobile
                  v-model:input_model_props="props.row.EXP_DT"
                  style="width: 105px"
                  placeholder="유효기간"
                  @on-change-date="onOnChangeDate(props.row)"
                />
                <!-- <RequiredComp_mobile
                  v-model:dialog_required_model="dialog_datepicker_required"
                  Required_text="유효기간"
                /> -->
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <!-- <q-td v-if="props.row.PROD_NO !== ''" class="table-td_mobile">
                {{ props.row.PROD_NO }}
              </q-td> -->
              <q-td class="table-td_mobile">
                <InputComp_mobile
                  v-model:input_model_props="props.row.PROD_NO"
                  @click="dialog_input_required = true"
                  @update:input_model_props="onOnChangeDate(props.row)"
                  placeholder="제조번호"
                />
                <!-- <RequiredComp_mobile
                  v-model:dialog_required_model="dialog_input_required"
                  Required_text="제조번호"
                /> -->
              </q-td>
            </div>
          </div>
        </q-tr>

        <BottomComp_mobile
          v-model:dialog_bottom_model="props.row.bottom_model"
          :bottom_layout="true"
          :bottom_layout_close="false"
          :useTotal="true"
          :useAmount="false"
          first_addBtn="반품등록"
          :totalamt="totalAmt"
          :totalcnt="totalSelect"
          second_bottom_memoTitle="반품"
          second_backBtn="메모추가"
          second_nextBtn="반품등록"
          memo_dialog_title="반품 장바구니 > 메모"
          :selecteditem="selected"
          flag="returnReg"
          @refresh-list="fetchBasketList"
        />
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, onMounted, inject, computed, toRaw } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { mdiCheck, mdiHeart, mdiHeartOutline } from "@mdi/js";
import moment from "moment";

import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BottomComp_mobile from "src/components/mobile/DialogComp/BottomDialogComp/BottomComp_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import InputComp_mobile from "src/components/mobile/__commonComp/inputComp/InputComp_mobile.vue";
import ReturnRecordComp_a_mobile from "src/components/mobile/DialogComp/2.returnDialogComp/ReturnRecordComp_a_mobile.vue";
import FilterComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterComp_mobile.vue";
import BtnGroupComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnGroupComp_mobile.vue";
import DeleteComp_mobile from "src/components/mobile/DialogComp/__commonDialogComp/DeleteComp_mobile.vue";
import RequiredComp_mobile from "src/components/mobile/DialogComp/__commonDialogComp/RequiredComp_mobile.vue";
import DatePickerComp_mobile from "src/components/mobile/__commonComp/datepickerComp/DatePickerComp_mobile.vue";

// const dialog_bottom_model = ref(true);
const dialog_datepicker_required = ref(false);
const dialog_input_required = ref(false);
const selected = ref([]);
const bus = inject("bus");

const props = defineProps({
  TableTitle: {
    type: String,
    default: "TableTitle",
  },
});

const radioLabelAll = computed(() => {
  return `${rows.value.length}건`;
});

const radioLabelA = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) =>
      item.MED_CD === "02" &&
      item.SUBDIV_YN === "N" &&
      item.HAZARD_BACK_YN === "N" &&
      item.TAX_YN === "Y"
  ).length;

  return `${tempCnt}건`;
});
const radioLabelB = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) =>
      item.MED_CD === "01" &&
      item.SUBDIV_YN === "N" &&
      item.HAZARD_BACK_YN === "N" &&
      item.TAX_YN === "Y"
  ).length;

  return `${tempCnt}건`;
});
const radioLabelC = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) => item.SUBDIV_YN === "N" && item.TAX_YN === "N"
  ).length;

  return `${tempCnt}건`;
});
const radioLabelD = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) => item.SUBDIV_YN === "Y"
  ).length;

  return `${tempCnt}건`;
});

const radioLabelF = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) => item.SUBDIV_YN === "N" && item.HAZARD_BACK_YN === "Y"
  ).length;

  return ` ${tempCnt}건`;
});

const filterCond = ref("all");

const filteredRows = computed(() => {
  if (rows.value.length > 0) {
    if (filterCond.value === "01") {
      // 전문
      return rows.value.filter(
        (item) =>
          item.MED_CD === "02" &&
          item.SUBDIV_YN === "N" &&
          item.HAZARD_BACK_YN === "N" &&
          item.TAX_YN === "Y"
      );
    } else if (filterCond.value === "02") {
      // 일반
      return rows.value.filter(
        (item) =>
          item.MED_CD === "01" &&
          item.SUBDIV_YN === "N" &&
          item.HAZARD_BACK_YN === "N" &&
          item.TAX_YN === "Y"
      );
    } else if (filterCond.value === "03") {
      // 비과세반품
      return rows.value.filter(
        (item) => item.SUBDIV_YN === "N" && item.TAX_YN === "N"
      );
    } else if (filterCond.value === "04") {
      // 소분반품
      return rows.value.filter((item) => item.SUBDIV_YN === "Y");
    } else if (filterCond.value === "05") {
      // 회수
      return rows.value.filter(
        (item) => item.SUBDIV_YN === "N" && item.HAZARD_BACK_YN === "Y"
      );
    }
  }

  // 전체
  return rows.value;
});

const userInfo = util.getUserData();
const rows = ref([]);

const rowInfo = ref({
  userId: userInfo.USER_ID,
  custCd: userInfo.CUST_CD,
  basketGbCd: "",
  itemGbCd: "",
  saveItemCd: "",
});

const basketSearchParam = ref({
  userGbCd: userInfo.USER_GB_CD,
  custCd: userInfo.CUST_CD,
  basketGbCd: "02",
});

const basketData = ref({
  basketGbCd: "02",
  expDt: "",
  prodNo: "",
  saveItemCd: "",
  itemGbCd: "",
  custCd: userInfo.CUST_CD,
  userId: userInfo.USER_ID,
});

const totalSelect = computed(() => selected.value.length);
const totalAmt = computed(() => {
  if (selected.value.length > 0) {
    let amt = 0;

    selected.value.forEach((m) => {
      amt += m.ORD_WP2_AMT * m.ITEM_QTY;
    });

    return amt;
  } else {
    return 0;
  }
});

// 반품 장바구니 목록 조회
async function fetchBasketList() {
  const nowFilterCond = filterCond.value;

  let filterVal = "";
  if (filterCond.value == "all") {
    filterVal = "전체";
  } else if (filterCond.value == "01") {
    filterVal = "전문";
  } else if (filterCond.value == "02") {
    filterVal = "일반";
  } else if (filterCond.value == "03") {
    filterVal = "비과세";
  } else if (filterCond.value == "04") {
    filterVal = "소분";
  } else if (filterCond.value == "05") {
    filterVal = "회수";
  }

  const res = await api.get("/bakRcpt/basketList", {
    params: basketSearchParam.value,
  });

  rows.value = res;

  selected.value = rows.value;

  onClickFilterBtn(filterVal);

  filterCond.value = nowFilterCond;
}

// 삭제
async function onClickDelete(row) {
  rowInfo.value.itemGbCd = row.ITEM_GB_CD;
  rowInfo.value.saveItemCd = row.ITEM_CD;
  rowInfo.value.basketGbCd = row.BASKET_GB_CD;

  let filterVal = "";

  if (filterCond.value == "all") {
    filterVal = "전체";
  } else if (filterCond.value == "01") {
    filterVal = "전문";
  } else if (filterCond.value == "02") {
    filterVal = "일반";
  } else if (filterCond.value == "03") {
    filterVal = "비과세";
  } else if (filterCond.value == "04") {
    filterVal = "소분";
  } else if (filterCond.value == "05") {
    filterVal = "회수";
  }

  try {
    await api.delete("/bakRcpt/deleteBakRcptBasket", {
      params: rowInfo.value,
    });

    util.showNotifyTop("삭제되었습니다.");
    await bus.emit("mobileBtnChk2", filterVal);
    await fetchBasketList();
  } catch (error) {
    alert(error.response.data);
  }
}

function isRet(val) {
  if (
    val.indexOf("판매중지") === 0 ||
    val.indexOf("급여중지") === 0 ||
    val.indexOf("회수") === 0 ||
    val.indexOf("향정") === 0
  ) {
    return true;
  }

  return false;
}

function onClickFilterBtn(val) {
  if (rows.value.length > 0) {
    for (var i = 0; i < rows.value.length; i++) {
      rows.value[i].bottom_model = false;
    }
  }
  selected.value = [];

  if (val.startsWith("전체")) {
    filterCond.value = "all";
    const newRowsYn = rows.value.map((i) => {
      return { ...i, SELECTED_YN: "Y" };
    });
    rows.value = newRowsYn;

    if (rows.value.length > 0) {
      rows.value[0].bottom_model = true;
      selected.value = rows.value;
    }
  } else if (val.startsWith("전문")) {
    filterCond.value = "01";

    const newRowsYn = rows.value.map((i) => {
      return i.MED_CD === "02" &&
        i.SUBDIV_YN === "N" &&
        i.HAZARD_BACK_YN === "N" &&
        i.TAX_YN === "Y"
        ? { ...i, SELECTED_YN: "Y" }
        : { ...i, SELECTED_YN: "N" };
    });
    rows.value = newRowsYn;

    if (rows.value.length > 0) {
      let idx = rows.value.indexOf(
        rows.value.filter((i) => i.SELECTED_YN == "Y")[0]
      );
      idx == -1 ? "" : (rows.value[idx].bottom_model = true);
      selected.value = rows.value.filter((i) => i.SELECTED_YN == "Y");
    }
  } else if (val.startsWith("일반")) {
    filterCond.value = "02";

    const newRowsYn = rows.value.map((i) => {
      return i.MED_CD === "01" &&
        i.SUBDIV_YN === "N" &&
        i.HAZARD_BACK_YN === "N" &&
        i.TAX_YN === "Y"
        ? { ...i, SELECTED_YN: "Y" }
        : { ...i, SELECTED_YN: "N" };
    });
    rows.value = newRowsYn;

    if (rows.value.length > 0) {
      let idx = rows.value.indexOf(
        rows.value.filter((i) => i.SELECTED_YN == "Y")[0]
      );
      idx == -1 ? "" : (rows.value[idx].bottom_model = true);
      selected.value = rows.value.filter((i) => i.SELECTED_YN == "Y");
    }
  } else if (val.startsWith("비과세")) {
    filterCond.value = "03";

    const newRowsYn = rows.value.map((i) => {
      return i.SUBDIV_YN === "N" && i.TAX_YN === "N"
        ? { ...i, SELECTED_YN: "Y" }
        : { ...i, SELECTED_YN: "N" };
    });
    rows.value = newRowsYn;

    if (rows.value.length > 0) {
      let idx = rows.value.indexOf(
        rows.value.filter((i) => i.SELECTED_YN == "Y")[0]
      );
      idx == -1 ? "" : (rows.value[idx].bottom_model = true);
      selected.value = rows.value.filter((i) => i.SELECTED_YN == "Y");
    }
  } else if (val.startsWith("소분")) {
    filterCond.value = "04";

    const newRowsYn = rows.value.map((i) => {
      return i.SUBDIV_YN === "Y"
        ? { ...i, SELECTED_YN: "Y" }
        : { ...i, SELECTED_YN: "N" };
    });
    rows.value = newRowsYn;

    if (rows.value.length > 0) {
      let idx = rows.value.indexOf(
        rows.value.filter((i) => i.SELECTED_YN == "Y")[0]
      );
      idx == -1 ? "" : (rows.value[idx].bottom_model = true);
      selected.value = rows.value.filter((i) => i.SELECTED_YN == "Y");
    }
  } else if (val.startsWith("회수")) {
    filterCond.value = "05";

    const newRowsYn = rows.value.map((i) => {
      return i.SUBDIV_YN === "N" && i.HAZARD_BACK_YN === "Y"
        ? { ...i, SELECTED_YN: "Y" }
        : { ...i, SELECTED_YN: "N" };
    });
    rows.value = newRowsYn;

    if (rows.value.length > 0) {
      let idx = rows.value.indexOf(
        rows.value.filter((i) => i.SELECTED_YN == "Y")[0]
      );
      idx == -1 ? "" : (rows.value[idx].bottom_model = true);
      selected.value = rows.value.filter((i) => i.SELECTED_YN == "Y");
    }
  }
}

async function onOnChangeDate(row) {
  //소분일경우
  if (row.SUBDIV_YN != "N") {
    basketData.value.basketGbCd = "03";
  }

  basketData.value.expDt = row.EXP_DT;
  basketData.value.prodNo = row.PROD_NO;
  basketData.value.saveItemCd = row.ITEM_CD;
  basketData.value.itemGbCd = row.ITEM_GB_CD;

  try {
    await api.put("/bakRcpt/updateItemQty", basketData.value);
  } catch (error) {
    error.response.data;
  }

  // for (var i = 0; i < rows.value.length; i++) {
  //     rows.value[i].bottom_model = false;
  //   }
  // row.bottom_model = true;

  const nowFilterCond = filterCond.value;
  filterCond.value = "all";
  filterCond.value = nowFilterCond;
}

// 체크
function onChecRow(row, val) {
  if (val === "N") {
    selected.value = rows.value.filter((item) => item.SELECTED_YN != "N");
    // for (var i = 0; i < rows.value.length; i++) {
    //   rows.value[i].bottom_model = false;
    // }
    // row.bottom_model = true;
  } else {
    selected.value = rows.value.filter((item) => item.SELECTED_YN == "Y");
    // for (var i = 0; i < rows.value.length; i++) {
    //   rows.value[i].bottom_model = false;
    // }
    // row.bottom_model = true;
  }
}
bus.on("mobileOrder2", async (data) => {
  onClickFilterBtn(data);
});

onMounted(async () => {
  await fetchBasketList();
  if (rows.value.length > 0) {
    rows.value[0].bottom_model = true;
  }
});

////////////////////////////////////////////////////////////////////////////////////
const rows1 = ref([
  {
    badge: 1,
    약: "우루사정300mg(병)",
    규격: "30T",
    제약사: "대웅제약",
    수량: 5,
    날짜: "2025.12.01",
    SN: "SN00058",
  },
  {
    badge: 1,
    약: "우루사정100mg(병)",
    규격: "30T",
    제약사: "대웅제약",
    수량: 5,
    날짜: "",
    SN: "",
  },
]);

const btnGroups2 = [
  {
    title: "전체",
    sub: radioLabelAll,
  },
  {
    title: "전문",
    sub: radioLabelA,
  },
  {
    title: "일반",
    sub: radioLabelB,
  },
  {
    title: "비과세",
    sub: radioLabelC,
  },
  {
    title: "소분",
    sub: radioLabelD,
  },
  {
    title: "회수",
    sub: radioLabelF,
  },
];
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  // padding: 6px 0 !important;
  gap: 8px;
  .table_item {
    td {
      // height: 24px !important;
    }
  }
  .table_item:nth-child(1) {
    @include flex_start;
    @include fw-7;
    align-items: flex-start;
    grid-column: span 5;
    gap: 7px;
    height: auto !important;
    td {
      // overflow: hidden;
      // text-overflow: ellipsis;
      height: auto !important;
      white-space: normal;
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 1;
    td {
      @include flex_start;
      @include fs-5;
      // color: #666666;
      width: 50px;
      overflow: hidden;
      text-overflow: ellipsis;
      display: block;
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 1;
    td {
      @include flex_center;
      @include fs-5;
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 2;
    td {
      @include flex_center;
      @include fs-5;
      // color: #666666;
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 2;
    td {
      @include flex_center;
      @include fs-5;
      // color: #666666;
    }
  }
}
.q-table__container {
  padding-bottom: 75px;
}
</style>

<template>
  <div class="">
    <q-table
      class=""
      row-key="ITEM_CD"
      :rows="filteredRows"
      flat
      hide-header
      hide-bottom
      virtual-scroll
      selection="multiple"
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      style="margin-bottom: 65px"
    >
      <template v-slot:top>
        <div class="table_Top_wrap">
          <div class="table_Title_mobile flex_between">
            <span>{{ TableTitle }}</span>
            <div v-if="btnChk" class="flex_center" style="gap: 8px">
              <BtnComp_mobile
                buttonText="전체"
                btnStyle="default"
                btnSize="md"
                @click="onClickBtnAll"
              />
              <BtnComp_mobile
                v-if="userInfo.G_DLV_YN != 'N'"
                :buttonText="btnWideAreaLabel"
                btnStyle="transparent"
                btnSize="md"
                @click="onClickBtnWideArea"
              />
            </div>
            <div v-else class="flex_center" style="gap: 8px">
              <BtnComp_mobile
                buttonText="전체"
                btnStyle="transparent"
                btnSize="md"
                @click="onClickBtnAll"
              />
              <BtnComp_mobile
                :buttonText="btnWideAreaLabel"
                btnStyle="default"
                btnSize="md"
                @click="onClickBtnWideArea"
              />
            </div>
          </div>
          <div class="flex_between" style="padding: 0 0 14px 0">
            <BtnGroupComp_mobile_order
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
            :class="props.row.SELECTED_YN == 'N' ? '' : ''"
          >
            <div class="table_item table_item_mobile" @click="clickEvt(props)">
              <!--  @click="props.row.dialog_record = true" -->
              <q-checkbox
                class="table_checkbox_gray"
                v-model="props.row.SELECTED_YN"
                true-value="N"
                false-value="Y"
                dense
                :disable="props.row.AVAIL_STOCK === 0"
                @update:model-value="(val) => onChecRow(props.row, val)"
              />

              <div class="table_badge_wrap">
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
                {{ props.row.ITEM_NM }}
              </q-td>

              <DialogLayout_mobile
                dialog_title="제품 출고이력"
                v-model:dialog_model_prop="props.row.dialog_record"
                :dialog_full="dialog_full"
              >
                <div class="">
                  <OrderRecordComp_c_mobile
                    :ordBasketData="props.row"
                    :isDisable="disableChk"
                    @edit-wp2-amt="fetchBasketList"
                    v-model="isActiveBtn"
                  >
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
                        :class="
                          props.row.PAY_ITEM_NM === '비급여' ? 'nonpay' : 'pay'
                        "
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
                    {{ props.row.ITEM_NM }}
                  </OrderRecordComp_c_mobile>
                </div>
              </DialogLayout_mobile>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <DeleteComp_mobile
                  delete_text1="장바구니"
                  @delete-basket="deleteOrdBasket(props.row)"
                >
                  <img src="assets/icons/xmark-solid.svg" alt="icon" />
                </DeleteComp_mobile>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ props.row.MAKER_NM }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td
                v-if="props.row.G_DLV_BRCH_FLAG === 'G'"
                class="table-td_mobile"
              >
                <div class="table_widearea bdg-line red"><span>광역</span></div>
              </q-td>
            </div>

            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}원
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <EAInputComp_mobile
                  v-if="props.row.AVAIL_STOCK > 0"
                  inputStyle="EA-sm"
                  style="width: 120px"
                  v-model="props.row.ITEM_QTY"
                  @update:model-value="() => updateItemQty(props.row)"
                  @blur="() => updateItemQty(props.row)"
                />
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <q-checkbox
                  class="table_checkbox_heart"
                  :checked-icon="mdiHeart"
                  :unchecked-icon="mdiHeart"
                  v-model="props.row.FAVORITE_YN"
                  true-value="Y"
                  false-value="N"
                  dense
                  @update:model-value="(val) => onCheckInterest(props.row, val)"
                />
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
          :totalcnt="totalSelect"
          :totalamt="totalAmt"
          :selecteditem="selected"
          first_addBtn="장바구니 주문"
          second_bottom_memoTitle="주문"
          second_backBtn="메모추가"
          second_nextBtn="주문등록"
          memo_dialog_title="장바구니 > 메모"
          flag="ordReg"
          @refresh-list="fetchBasketList"
        />
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, inject, onMounted, computed, toRaw, watch } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import {
  mdiCheck,
  mdiHeart,
  mdiHeartOutline,
  mdiCheckboxOutline,
  mdiCheckboxBlankOutline,
} from "@mdi/js";

import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BottomComp_mobile from "src/components/mobile/DialogComp/BottomDialogComp/BottomComp_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import InputComp_mobile from "src/components/mobile/__commonComp/inputComp/InputComp_mobile.vue";
import OrderRecordComp_a_mobile from "src/components/mobile/DialogComp/1.orderDialogComp/OrderRecordComp_a_mobile.vue";
import FilterComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterComp_mobile.vue";
import BtnGroupComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnGroupComp_mobile.vue";
import DeleteComp_mobile from "src/components/mobile/DialogComp/__commonDialogComp/DeleteComp_mobile.vue";
import EAInputComp_mobile from "src/components/mobile/__commonComp/inputComp/EAInputComp_mobile.vue";
import OrderRecordComp_c_mobile from "src/components/mobile/DialogComp/1.orderDialogComp/OrderRecordComp_c_mobile.vue";
import BtnGroupComp_mobile_order from "../../__commonComp/btnComp/BtnGroupComp_mobile_order.vue";
// import { json } from "stream/consumers";

//------------------------------------------------------------------------//
const props = defineProps({
  TableTitle: {
    type: String,
    default: "TableTitle",
  },
});

//필터표시
const isActiveBtn = ref(null);

const dialog_full = ref(true);
const dialog_record = ref(false);

//----------------------------------------------------------//
const userInfo = util.getUserData();
const rows = ref([]);
const loading = ref(false);
const bus = inject("bus");
const selected = ref([]);
const filterCond = ref("all");

const disableChk = ref(false);

const btnChk = ref(true);

const onClickFilterBtn = (val) => {
  if (rows.value.length > 0) {
    for (var i = 0; i < rows.value.length; i++) {
      rows.value[i].bottom_model = false;
    }
  }

  selected.value = [];

  if (val.startsWith("전체")) {
    filterCond.value = "all";

    // ❗ 기존 SELECTED_YN 유지 (초기값이 "N"이라면 그대로 둠)
    rows.value.forEach((row) => {
      row.SELECTED_YN = row.SELECTED_YN || "N";
    });

    selected.value = rows.value.filter((i) => i.SELECTED_YN === "N");
    if (selected.value.length > 0) {
      selected.value[0].bottom_model = true;
    }
  } else {
    // 🔁 공통 필터 로직
    const filterMap = {
      전문급여: (i) => i.PAY_ITEM_YN === "Y" && i.MED_CD === "02",
      전문비급: (i) => i.PAY_ITEM_YN === "N" && i.MED_CD === "02",
      일반급여: (i) => i.PAY_ITEM_YN === "Y" && i.MED_CD === "01",
      일반비급: (i) => i.PAY_ITEM_YN === "N" && i.MED_CD === "01",
    };

    const key = Object.keys(filterMap).find((k) => val.startsWith(k));
    filterCond.value = {
      전문급여: "01",
      전문비급: "02",
      일반급여: "03",
      일반비급: "04",
    }[key];

    const newRowsYn = rows.value.map((i) => {
      return filterMap[key](i)
        ? { ...i, SELECTED_YN: "N" } // ✅ 체크 상태 유지
        : { ...i, SELECTED_YN: "Y" }; // 해제 상태
    });

    rows.value = newRowsYn;

    const filtered = rows.value.filter((i) => i.SELECTED_YN === "N");
    if (filtered.length > 0) {
      filtered[0].bottom_model = true;
    }

    selected.value = filtered;
  }
};

const clickEvt = (props) => {
  if (userInfo.USER_GB_CD === "30" || props.row.PAY_ITEM_YN === "Y") {
    disableChk.value = true;
  } else if (userInfo.USER_GB_CD !== "30" || props.row.PAY_ITEM_YN === "N") {
    disableChk.value = false;
  }

  props.row.dialog_record = true;
};

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

onMounted(async () => {
  await onClickBtnAll();
  if (rows.value.length > 0) {
    rows.value[0].bottom_model = true;
  }
});

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

// 장바구니 목록 조회
async function fetchBasketList() {
  const nowFilterCond = filterCond.value;

  // 필터 라벨 텍스트 설정
  let filterVal = "";
  if (filterCond.value === "all") {
    filterVal = "전체";
  } else if (filterCond.value === "01") {
    filterVal = "전문급여";
  } else if (filterCond.value === "02") {
    filterVal = "전문비급";
  } else if (filterCond.value === "03") {
    filterVal = "일반급여";
  } else if (filterCond.value === "04") {
    filterVal = "일반비급";
  }

  // ✅ API 호출 후 rows에 직접 할당
  const res = await api.get("/ord/basketList", {
    params: basketSearchParam.value,
  });

  rows.value = res;

  // ✅ 레퍼런스 유지한 채 SELECTED_YN만 수정 (map 사용 ❌)
  rows.value.forEach((row) => {
    row.SELECTED_YN = "N"; // true-value="N" → 체크 상태
  });

  selected.value = [...rows.value];

  if (rows.value.length > 0) {
    rows.value[0].bottom_model = true;
  }

  // ✅ 필터 적용
  onClickFilterBtn(filterVal);

  // ✅ 기존 필터 조건 복원
  filterCond.value = nowFilterCond;
}

bus.on("mobileOrder", async (data) => {
  onClickFilterBtn(data);
});

// 전체 버튼 클릭
async function onClickBtnAll() {
  basketSearchParam.value.gDlvBrchFlag = "";
  btnChk.value = true;
  filterCond.value = "all";
  await bus.emit("mobileBtnChk", "전체");
  await fetchBasketList();
}

// 광역 버튼 클릭
async function onClickBtnWideArea() {
  basketSearchParam.value.gDlvBrchFlag = "G";
  btnChk.value = false;
  filterCond.value = "all";
  await bus.emit("mobileBtnChk", "전체");
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
      util.showNotifyTop("관심상품이 추가되었습니다.");
    } else {
      await api.delete("/interest/removeInterest", { params: interestItem });
      util.showNotifyTop("관심상품이 삭제되었습니다.");
    }

    // await fetchBasketList();
  } catch (error) {
    alert(error.response.data);
  }
}

// 체크
function onChecRow(row, val) {
  // ✅ N이면 체크된 상태니까 === 'N' 으로 필터
  selected.value = rows.value.filter((item) => item.SELECTED_YN === "N");
}

// 삭제
async function deleteOrdBasket(row) {
  rowInfo.value.saveItemGbCd = row.ITEM_GB_CD;
  rowInfo.value.saveItemCd = row.ITEM_CD;
  rowInfo.value.dlvBrchCd = row.DLV_BRCH_CD;
  let filterVal = "";

  if (filterCond.value == "all") {
    filterVal = "전체";
  } else if (filterCond.value == "01") {
    filterVal = "전문급여";
  } else if (filterCond.value == "02") {
    filterVal = "전문비급";
  } else if (filterCond.value == "03") {
    filterVal = "일반급여";
  } else if (filterCond.value == "04") {
    filterVal = "일반비급";
  }

  try {
    await api.delete("/ord/deleteComOrdBasket", {
      params: rowInfo.value,
    });

    util.showNotifyTop("삭제되었습니다.");
    await bus.emit("refreshMobileBasket");
    await bus.emit("mobileBtnChk", filterVal);
    await fetchBasketList();
  } catch (error) {
    alert(error.response.data);
  }
}

const basketAll = computed(() => {
  return `${rows.value.length}건`; //전체
});

const radioPrfssnlPmnt = computed(() => {
  const prfssnlPmntCnt = toRaw(rows.value).filter(
    (item) => item.PAY_ITEM_YN === "Y" && item.MED_CD === "02"
  ).length;
  return `${prfssnlPmntCnt}건`; //전문급여
});

const radioPrfssnlNonPmnt = computed(() => {
  const prfssnlNonPmntCnt = toRaw(rows.value).filter(
    (item) => item.PAY_ITEM_YN === "N" && item.MED_CD === "02"
  ).length;
  return `${prfssnlNonPmntCnt}건`; // 전문비급
});

const radioGnrlPmnt = computed(() => {
  const gnrlPmntCnt = toRaw(rows.value).filter(
    (item) => item.PAY_ITEM_YN === "Y" && item.MED_CD === "01"
  ).length;
  return `${gnrlPmntCnt}건`; //일반급여
});

const radioGnrlNonPmnt = computed(() => {
  const prfssnlPmntCnt = toRaw(rows.value).filter(
    (item) => item.PAY_ITEM_YN === "N" && item.MED_CD === "01"
  ).length;
  return `${prfssnlPmntCnt}건`; //일반비급
});

const btnWideAreaLabel = computed(() => {
  const wideAreaCnt = toRaw(rows.value).filter(
    (item) => item.G_DLV_BRCH_FLAG === "G"
  ).length;
  return `광역 ${wideAreaCnt}건`; //광역
});

const btnGroups2 = [
  {
    title: "전체",
    sub: basketAll,
  },
  {
    title: "전문급여",
    sub: radioPrfssnlPmnt,
  },
  {
    title: "전문비급",
    sub: radioPrfssnlNonPmnt,
  },
  {
    title: "일반급여",
    sub: radioGnrlPmnt,
  },
  {
    title: "일반비급",
    sub: radioGnrlNonPmnt,
  },
];
const updateItemQty = (row) => {
  if (!row) return;

  let strValue = String(row.ITEM_QTY);
  let newValue = strValue.replace(/,/g, "").replace(/^0+(?!$)/, "");
  newValue = parseInt(newValue) || 0;
  row.ITEM_QTY = newValue;
  onClickUpdateQty(row);
};
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

  await api.put("/ord/updateItemQty", rowInfo.value);
  // 변경되었는지 확인하기 애매해서 추가함.. 요청 있을경우 삭제
}
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 8px;
  align-items: center;
  .table_item:nth-child(1) {
    @include flex_start;
    @include fw-6;
    align-items: flex-start;
    grid-column: span 5;
    gap: 7px;
    height: auto !important;
    td {
      // height: auto !important;
      padding-right: 20px !important;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }
  }
  .table_item:nth-child(2) {
    position: absolute;
    top: 8px;
    right: 0;
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 2;
    td {
      @include flex_start;
      overflow: hidden;
      text-overflow: ellipsis;
      display: block;
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 2;
    td {
      @include flex_end;
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 2;
    td {
      @include flex_start;
    }
  }
  .table_item:nth-child(7) {
    grid-column: span 2;
    td {
      @include flex_end;
      gap: 8px 4px;
    }
  }
  .table_item:nth-child(8) {
    grid-column: span 1;
    td {
      @include flex_end;
      gap: 8px;
    }
  }
}
</style>

<template>
  <div class="">
    <q-table
      class="interestTable_b"
      :rows="rows"
      :columns="columns"
      row-key="ITEM_CD"
      table-header-class="table_header"
      dense
      hide-bottom
      selection="multiple"
      v-model:selected="selected"
      color="blue-10"
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
      @row-click="onClickRow"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="q-table_header_wrap" style="margin-top: 7px">
            <div class="q-table_header_title">
              <p>
                상품리스트
                <span :class="{ grpNm: groupNm }">{{ ` ${groupNm}` }}</span>
              </p>
            </div>
          </div>
          <div class="q-table_header_sub_wrap flex_between">
            <div class="srch_form">
              <div class="slct-wrap">
                <!-- <SelectComp_default
                  :selectModel="listModel"
                  :selectOption="listOption"
                /> -->
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
                  @keyup.enter="onClickSearch"
                /> -->
                <q-input
                  v-model="searchParam.keyword"
                  class="input_search1"
                  borderless
                  dense
                  placeholder="품목명/보험코드 입력"
                  @keyup.enter="onClickSearch"
                  autofocus
                  @focus="(input) => input.target.select()"
                />
                <q-input
                  v-model="searchParam.makerNm"
                  class="input_search2"
                  borderless
                  dense
                  placeholder="제약회사명 입력"
                  @keyup.enter="onClickSearch"
                  @focus="(input) => input.target.select()"
                />
              </div>
              <BtnComp_default
                class="btn_srch"
                btn-label="검색"
                @click="onClickSearch"
              />
            </div>
            <div class="q-table_btn_export_wrap flex_center btn_area">
              <DialogComp_groupMove
                :is-open="isOpenGroupMovePopup"
                @close="isOpenGroupMovePopup = false"
              />
              <BtnComp_default
                class="btn-line default btn-pd12"
                btn-label="삭제"
                @click="onClickDeleteM"
              />
              <BtnComp_default
                class="btn-line default btn-pd12"
                btn-label="이동"
                @click="onClickGroupMove"
              />
              <BtnComp_default
                class="btn-line default btn-pd12 btn-excel"
                btn-label="엑셀"
                @click="exportTable"
              />
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

      <template v-slot:header-cell-전월="props">
        <q-th style="line-height: 1em">
          {{ props.col.label }}<br />
          주문량
        </q-th>
      </template>

      <template v-slot:header-cell-당월="props">
        <q-th style="line-height: 1em">
          {{ props.col.label }}<br />
          주문량
        </q-th>
      </template>

      <template v-slot:body-cell-ITEM_NM="props">
        <q-td class="td-prd">
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
            <div class="q-table_itemTitle interestTable_ellipsis_b td-prd_name">
              {{ props.value }}
            </div>
          </div>
        </q-td>
      </template>

      <!-- 규격/ -->
      <template v-slot:body-cell-UNIT="props">
        <q-td :props="props" class="td-unit">
          <div>
            {{ props.value }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-MAKER_NM="props">
        <q-td style="cursor: pointer" class="td-comp_int">
          <div class="q-table_itemTitleSub ellipsis">
            {{ props.value }}
            <q-tooltip>{{ props.value }}</q-tooltip>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ORD_WP2_AMT="props">
        <q-td class="td-price">
          <div class="q-tableitemTitleSub">
            {{ props.value }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-MONTH_QTY="props">
        <q-td :props="props" class="td-use_month">
          <div class="q-table_number_wrap flex_center">
            <q-input
              v-model="props.row.MONTH_QTY"
              input-class="q-table_number"
              placeholder="입력"
              borderless
              dense
              reverse-fill-mask
              mask="###,###,###"
              @update:model-value="onUpdate(props)"
              @focus="(input) => input.target.select()"
            />
            <BtnComp_active_small
              class="btn-line default"
              btn_activeLabel="저장"
              @click="onClickMonthQtySave(props.row)"
            />
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-BFR_MTH_ORD="props">
        <q-td :props="props" style="cursor: pointer" class="td-ord_mnt">
          <div class="q-table_itemTitleSub">
            {{ props.value }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-THS_MTH_ORD="props">
        <q-td :props="props" style="cursor: pointer" class="td-use_ths">
          <div class="q-table_itemTitleSub">
            {{ props.value }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-AVAIL_STOCK="props">
        <q-td :props="props" style="cursor: pointer" class="td-inven">
          <div class="q-table_stock_wrap">
            <p v-if="props.row.AVAIL_STOCK > 0">
              {{ props.value }}
            </p>
            <p class="q-table_stock" v-else>품절</p>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-REQ_QTY="props">
        <q-td :props="props" class="td-qty">
          <div v-if="props.row.AVAIL_STOCK > 0">
            <!-- <EAComp /> -->
            <div class="qty_wrap">
              <button
                type="button"
                class="btn_qty"
                @click="props.row.REQ_QTY > 0 ? props.row.REQ_QTY-- : null"
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
          <div v-else></div>
        </q-td>
      </template>

      <template v-slot:body-cell-담기="props">
        <q-td :props="props" class="td-add">
          <div>
            <!-- <BtnComp_active_small
              v-if="props.row.담기 === '담기'"
              btn_activeLabel="담기"
              style="padding: 0"
            />
            <BtnComp_default_small
              v-if="props.row.담기 === '광역'"
              btn_defaultLabel="광역"
            /> -->
            <BtnComp_active_small
              v-if="props.row.AVAIL_STOCK > 0"
              class="btn-line default"
              btn_activeLabel="담기"
              style="padding: 0"
              @click="onClickAddBasket(props.row)"
            />
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-삭제="props">
        <q-td :props="props" class="td-del">
          <div v-if="groupNm !== '알림품목'" class="q-table_del">
            <img
              src="assets/images/ico_delete.svg"
              alt="삭제"
              @click="onClickDelete(props.row)"
            />
          </div>
          <div v-else></div>
        </q-td>
      </template>

      <template v-slot:body-cell-ALARM_YN="props">
        <q-td :props="props" class="td-alrm">
          <div class="q-table_toggle flex_center">
            <!-- <Toggle_small_Comp /> -->
            <div class="Toggle_wrap">
              <label class="toggle">
                <input
                  v-model="props.row.ALARM_YN"
                  id="toggle"
                  type="checkbox"
                  true-value="Y"
                  false-value="N"
                  @change="onClickAlarm(props.row)"
                />
                <span class="slider"></span>
              </label>
            </div>
          </div>
        </q-td>
      </template>
    </q-table>
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted, inject, reactive, onBeforeMount } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { useRouter } from "vue-router";
import moment from "moment";

// import SearchComp from "src/components/web/common/SearchComp.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
import BtnComp_active_small from "src/components/web/common/BtnComp_active_small.vue";
import BtnComp_purple from "src/components/web/common/BtnComp_purple.vue";
import EAComp from "src/components/web/common/EAComp.vue";
import Toggle_small_Comp from "src/components/web/common/Toggle_small_Comp.vue";
import DialogComp_groupMove from "src/components/web/interest/InterestGroupMove.vue";
import SearchComp_select from "../common/SearchComp_select.vue";
import SelectComp_default from "../common/SelectComp_default.vue";

const router = useRouter();
const bus = inject("bus");
const userInfo = util.getUserData();
const rows = ref([]);
const loading = ref(false);
const searched = ref(false);

const listOption = [
  { label: "전체", value: "0" },
  { label: "전문급여", value: "1" },
  { label: "전문비급여", value: "2" },
  { label: "일반급여", value: "3" },
  { label: "일반비급여", value: "4" },
];

const selected = ref([]);

const searchParam = ref({
  searchType: "",
  custCd: userInfo.CUST_CD,
  searchOption: "0",
  keyword: "",
  makerNm: "",
  groupNo: "",
});

const dataParam = ref({
  userId: userInfo.USER_ID,
  custCd: userInfo.CUST_CD,
  itemCd: "",
  alarmYn: "",
  monthQty: 0,
});

const basketData = ref({
  userId: userInfo.USER_ID,
  custCd: userInfo.CUST_CD,
  basketGbCd: "01",
  saveItemCd: "",
  saveItemGbCd: "",
  saveItemQty: "",
  dlvBrchCd: "",
});

const groupNm = ref("");

const isOpenGroupMovePopup = ref(false);

const autoCompleteData = ref([]);
const itemNmData = ref([]);

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  selected.value = [];

  loading.value = true;
  const res = await api.get("/interest/groupItemList", {
    params: searchParam.value,
  });

  searched.value = true;
  rows.value = res;
  loading.value = false;

  if (rows.value.length > 0) {
    await onClickRow(null, rows.value[0]);
  }
}

// 검색 버튼 클릭
async function onClickSearch() {
  await fetchItemList();
}

const getSelectedString = () => {
  return selected.value.length === 0
    ? ""
    : `${selected.value.length} record${
        selected.value.length > 1 ? "s" : ""
      } selected of ${rows.value.length}`;
};

// row 클릭
async function onClickRow(evt, row, index) {
  bus.emit("interestItemDtl", row);
}

// 저장 클릭(월평균사용량)
async function onClickMonthQtySave(row) {
  if (row.MONTH_QTY === 0 || row.MONTH_QTY === "") {
    alert("수량을 입력해 주시기 바랍니다.");
    return;
  }

  dataParam.value.itemCd = row.ITEM_CD;
  dataParam.value.monthQty = util.getNumberFormat2(row.MONTH_QTY);

  try {
    await api.put("/interest/saveMonthQty", dataParam.value);
    util.showNotify("저장되었습니다.");
    await fetchItemList();
  } catch (error) {
    alert(error.response.data);
  }
}

// 담기 클릭
async function onClickAddBasket(row) {
  if (row.REQ_QTY === 0 || row.REQ_QTY === "" || isNaN(row.REQ_QTY)) {
    alert("수량을 선택해 주시기 바랍니다.");
    row.REQ_QTY = 0;
    return;
  }

  if (row.AVAIL_STOCK < row.REQ_QTY) {
    alert(`${row.ITEM_NM} 재고수량(${row.AVAIL_STOCK})이 초과되었습니다.`);
    row.REQ_QTY = util.getNumberFormat(row.REQ_QTY);

    return;
  }

  //담기 시 장바구니 체크
  const res = await api.post("/ord/getBasketDupOrd2", {
    custCd: userInfo.CUST_CD,
    basketGbCd: "01",
    saveItemCd: row.ITEM_CD,
    gDlvBrchCd: row.DLV_BRCH_CD,
  });

  basketData.value.saveItemCd = row.ITEM_CD;
  basketData.value.saveItemGbCd = row.ITEM_GB_CD;
  basketData.value.dlvBrchCd = row.DLV_BRCH_CD;
  basketData.value.saveItemQty = util.getNumberFormat2(row.REQ_QTY);

  if (res.ITEM_CNT == 0) {
    if (basketQty.value >= 100) {
      alert(
        "장바구니에 100개 품목을 초과하여 담을 수 없습니다. 기존 품목 주문등록 후 다시 담기 바랍니다"
      );
      row.REQ_QTY = 0;
      return;
    }

    try {
      await api.post("/ord/addBasket", basketData.value);
      await bus.emit("refreshHeaderBasket");
      row.REQ_QTY = 0;
      const confirmMsg =
        "선택한 품목이 장바구니에 담겼습니다.\n장바구니로 이동 하시겠습니까?";
      if (!confirm(confirmMsg)) return;

      await router.push({ path: "/comOrd" });
    } catch (error) {
      alert(error.response.data);
    }
  } else {
    const confirmMsg =
      "장바구니에 이미 담은 품목입니다. 수량 추가하시겠습니까?";
    if (!confirm(confirmMsg)) return;

    try {
      await api.post("/ord/addBasket", basketData.value);
      await bus.emit("refreshHeaderBasket");
      row.REQ_QTY = 0;
      const confirmMsg =
        "선택한 품목이 장바구니에 담겼습니다.\n장바구니로 이동 하시겠습니까?";
      if (!confirm(confirmMsg)) return;

      await router.push({ path: "/comOrd" });
    } catch (error) {
      alert(error.response.data);
    }
  }
}

const onClickDeleteM = () => {
  if (selected.value.length === 0) {
    alert("삭제하실 관심품목을 선택하여 주세요.");
    return;
  }

  if (
    !confirm(
      "선택하신 관심품목 " + selected.value.length + "건을 삭제하시겠습니까??"
    )
  )
    return;

  selected.value.forEach(async (m) => {
    dataParam.value.itemCd = m.ITEM_CD;
    try {
      await api.delete("/interest/removeInterest", { params: dataParam.value });
      await fetchItemList();
      bus.emit("refreshInterestGrp");
    } catch (error) {
      alert(error.response.data);
    }
  });
};

// 삭제 클릭
async function onClickDelete(row) {
  if (!confirm("삭제하시겠습니까?")) return;

  dataParam.value.itemCd = row.ITEM_CD;

  try {
    await api.delete("/interest/removeInterest", { params: dataParam.value });
    await fetchItemList();
    bus.emit("refreshInterestGrp");
  } catch (error) {
    alert(error.response.data);
  }
}

// 알림 클릭
async function onClickAlarm(row) {
  dataParam.value.itemCd = row.ITEM_CD;
  dataParam.value.alarmYn = row.ALARM_YN;

  try {
    await api.put("/interest/editAlarmYn", dataParam.value);
    bus.emit("refreshInterestGrp");

    if (searchParam.value.searchType === "2") {
      await fetchItemList();
    }
  } catch (error) {
    alert(error.response.data);
  }
}

// 이동
async function onClickGroupMove() {
  if (selected.value.length === 0) {
    alert("선택된 항목이 없습니다.");
    return;
  }

  isOpenGroupMovePopup.value = true;

  await bus.emit("groupMove", { list: selected.value, groupNm: groupNm.value });
}

// 엑셀
function exportTable() {
  api
    .get("excel/interest", {
      params: searchParam.value,
      responseType: "arraybuffer",
    })
    .then(function (res) {
      const url = window.URL.createObjectURL(new Blob([res]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute(
        "download",
        "관심상품_" + `${groupNm.value}_` + moment().format("YYYYMMDD") + ".xls"
      );
      document.body.appendChild(link);
      link.click();
      link.remove();
    })
    .catch(function (error) {
      console.log(error);
    });
}

bus.on("searchGroupItems", async (data) => {
  if (data.GROUP_NO === "X") {
    if (data.GROUP_NM === "입고알림품목") {
      searchParam.value.searchType = "1";
    } else if (data.GROUP_NM === "알림품목") {
      searchParam.value.searchType = "2";
    }
  } else if (Number(data.GROUP_NO) >= 0) {
    searchParam.value.searchType = "3";
    searchParam.value.groupNo = data.GROUP_NO;
  }

  searchParam.value.keyword = "";
  groupNm.value = data.GROUP_NM;
  await fetchItemList();
});

bus.on("refreshGroupItems", async () => {
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

function updateCount(props, evt) {
  if (!isNaN(evt.target.value)) {
    props.row.REQ_QTY = evt.target.value;
  } else {
    props.row.REQ_QTY = 0;
  }
}

// 자동완성
bus.on("getAutoCompleteData", async (data) => {
  if (data) {
    itemNmData.value = await data.map((obj) => obj.ITEM_NM);
  }
});

function setKeyword(val) {
  searchParam.value.keyword = val;

  if (val !== "") {
    const needle = val.toLocaleLowerCase();
    autoCompleteData.value = itemNmData.value.filter(
      (v) => v.toLocaleLowerCase().indexOf(needle) > -1
    );
  } else {
    autoCompleteData.value = [];
  }
}

// 주문, 반품 장바구니 count조회
async function getBasketCount() {
  if (userInfo.CUST_CD !== "") {
    const res = await api.get("/main/basketCount", {
      params: { custCd: userInfo.CUST_CD },
    });
    basketQty.value = res.ORD_COUNT;
  }
}

const basketQty = ref("");
onMounted(async () => {
  await bus.emit("onLoadGetAutoCompleteData");
  await getBasketCount();

  // bus.on("basketC", (data)=>{
  //   basketQty.value = data;
  //   //console.log("관심상품 마운트 장바구니 품목수: " +data);
  // });
});

const onUpdate = (val) => {
  if (val.col.name == "MONTH_QTY") {
    let newValue = val.row.MONTH_QTY.replace(/,/g, "");
    newValue = newValue.replace(/^0+(?!$)/, "");
    newValue = parseInt(newValue);
    val.row.MONTH_QTY = newValue;
  } else if (val.col.name == "REQ_QTY") {
    let newValue = val.row.REQ_QTY.replace(/,/g, "");
    newValue = newValue.replace(/^0+(?!$)/, "");
    newValue = parseInt(newValue);
    val.row.REQ_QTY = newValue;
  }
};

//-------------------------- Grid Setting ----------------------------//

const columns = [
  {
    name: "ITEM_NM",
    label: "관심품목",
    field: "ITEM_NM",
    align: "left",
    classes: "td-prd",
    sortable: true,
  },
  {
    name: "UNIT",
    label: "규격",
    field: "UNIT",
    align: "right",
    classes: "td-unit",
  },
  {
    name: "MAKER_NM",
    label: "제약회사",
    field: "MAKER_NM",
    align: "left",
    classes: "td-comp_int",
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
    name: "MONTH_QTY",
    label: "월평균사용량",
    field: "MONTH_QTY",
    align: "center",
    classes: "td-use_month",
  },
  {
    name: "BFR_MTH_ORD",
    label: "전월",
    field: "BFR_MTH_ORD",
    align: "center",
    classes: "td-ord_mnt",
  },
  {
    name: "THS_MTH_ORD",
    label: "당월",
    field: "THS_MTH_ORD",
    align: "center",
    classes: "td-ord_mnt",
  },
  {
    name: "AVAIL_STOCK",
    label: "재고",
    field: "AVAIL_STOCK",
    align: "center",
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
    name: "담기",
    label: "담기",
    field: "담기",
    align: "center",
    classes: "td-add",
  },
  {
    name: "삭제",
    label: "삭제",
    field: "삭제",
    align: "center",
    classes: "td-del",
  },
  // {
  //   name: "ALARM_YN",
  //   label: "알림",
  //   field: "ALARM_YN",
  //   align: "center",
  //   sortable: true,
  // },
];
</script>

<style lang="scss" scoped>
.flex_center {
  @include flex_center;
  gap: 8px;
}

.interestTable_b {
  // height: 740px;
  // height: 690px;
  overflow-y: auto;
  height: auto;
  min-height: 420px;
  max-height: 690px;
  // .q-table__middle {
  //   overflow: hidden;
  // }
  @media screen and (max-width: 830px) {
  }
}

.interestTable_ellipsis_b {
  // max-width: 200px;
  // max-width: 400px;
  // overflow: hidden;
  // white-space: nowrap;
  // text-overflow: ellipsis;
  white-space: wrap;
}

.Toggle_wrap {
  display: flex;
  align-items: center;
  gap: 4px;
}
.Toggle_label {
  cursor: pointer;
}
.toggle {
  position: relative;
  display: inline-block;
  width: 28px;
  height: 16px;
}
.toggle input {
  opacity: 0;
  width: 0;
  height: 0;
}
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: $gray-c;
  transition: 0.4s;
  border-radius: 20px;
}
.slider:before {
  position: absolute;
  content: "";
  height: 16px;
  width: 16px;
  left: 0;
  bottom: 0;
  background-color: #fff;
  border: 1px solid $gray-a;
  transition: 0.4s;
  border-radius: 50%;
}
.toggle input:checked + .slider {
  background-color: $primary;
}
.toggle input:checked + .slider:before {
  transform: translateX(12px);
  border-color: $primary;
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

// .q-table {
//   thead th,
//   tbody td:not(.q-virtual-scroll__padding td) {
//     padding: 0 4px !important;
//   }
//   th:first-child,
//   td:first-child {
//     padding-left: 5px !important;
//   }
//   th:last-child,
//   td:last-child {
//     padding-right: 5px !important;
//   }
// }
.q-table_header_title .grpNm {
  color: $primary;
  font-weight: 700;
  &::before {
    content: "";
    display: inline-block;
    margin: 0 10px;
    width: 1px;
    height: 12px;
    background-color: $line-400;
    vertical-align: middle;
  }
}

.btn_area {
  @include flex_end;
  flex-wrap: wrap;
  padding-right: 1px;
  gap: 4px;
  @supports (-webkit-touch-callout: none) and (not (translate: none)) {
    > * + * {
      margin-left: 4px;
    }
  }
}

.q-table_header_sub_wrap {
  @include flex_between;
  align-items: flex-start;
  // .srch-form {
  //   width: fit-content;
  //   width: calc(100% - 190px);
  // }

  @media screen and (max-width: 1710px) and (min-width: 1601px) {
    .srch_form {
      gap: 8px;
      @supports (-webkit-touch-callout: none) and (not (translate: none)) {
        > * + * {
          margin-left: 8px;
        }
      }
    }
  }
  @media screen and (max-width: 1600px) {
    // .SearchComp_wrap {
    //   width: calc(100% - 15% - 6px - 90px);
    // }
    .srch_form {
      gap: 6px;
      // width: calc(100% - 190px);
      @supports (-webkit-touch-callout: none) and (not (translate: none)) {
        > * + * {
          margin-left: 6px;
        }
      }
    }
  }
  @media screen and (max-width: 1440px) and (min-width: 831px) {
    gap: 6px;
    // .srch_form {
    //   width: calc(100% - 116px);
    // }

    .btn_area {
      gap: 6px 4px;
      // width: 110px;
      @supports (-webkit-touch-callout: none) and (not (translate: none)) {
        > * + * {
          margin-left: 4px;
        }
      }
    }
  }
}

.td-prd {
  .td-prd_name {
    display: inline;
  }
}
.td-price .q-tableitemTitleSub {
  text-align: right;
}
.q-table_number_wrap {
  gap: 2px;
}
</style>

<template>
  <div class="">
    <q-table
      class="returnTable_b cart_wrap"
      :rows="filteredRows"
      :columns="columns"
      row-key="ITEM_CD"
      table-header-class="table_header"
      table-class="tbl_cart"
      dense
      :selectedItems-rows-label="getselectedItemsString"
      selection="multiple"
      v-model:selected="selectedItems"
      color="blue-10"
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div
            class="q-table_header_wrap"
            style="
              display: flex;
              justify-content: space-between;
              align-items: center;
            "
          >
            <div class="q-table_header_title">
              <p>반품 장바구니</p>
            </div>
            <!-- <BtnComp_default_c
              class="btn-ord"
              @click="onClickImsiSave"
              style="margin-left: auto"
            >
              <b>임시저장</b>
            </BtnComp_default_c> -->
          </div>

          <div class="q-table_header_sub_wrap">
            <div class="radioTab_wrap">
              <q-radio
                v-model="filterCond"
                class="radioTab"
                size="xs"
                inline
                dense
                val="all"
                :label="radioLabelAll"
                @click="radioClick(0)"
              />
              <q-radio
                v-model="filterCond"
                class="radioTab"
                size="xs"
                inline
                dense
                val="01"
                style="margin-left: 8px"
                :label="radioLabelA"
                @click="radioClick(1)"
              />
              <q-radio
                v-model="filterCond"
                class="radioTab"
                size="xs"
                inline
                dense
                val="02"
                style="margin-left: 8px"
                :label="radioLabelB"
                @click="radioClick(2)"
              />
              <q-radio
                v-model="filterCond"
                class="radioTab"
                size="xs"
                inline
                dense
                val="03"
                style="margin-left: 8px"
                :label="radioLabelC"
                @click="radioClick(3)"
              />
              <q-radio
                v-model="filterCond"
                class="radioTab"
                size="xs"
                inline
                dense
                val="04"
                style="margin-left: 8px"
                :label="radioLabelD"
                @click="radioClick(4)"
              />
              <q-radio
                v-model="filterCond"
                class="radioTab"
                size="xs"
                inline
                dense
                val="05"
                style="margin-left: 8px"
                :label="radioLabelE"
                @click="radioClick(5)"
              />
            </div>
          </div>
        </div>
      </template>

      <template v-slot:body-cell-품목="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <div class="">
            <div class="q-table_itemTitle td-prd_name">
              {{ props.value }}
            </div>
            <!-- <p v-if="props.row.수량 === 0" class="q-table_itemTitleSub">
              (제약사품절)
            </p> -->
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-제약회사="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <div class="" style="">
            <div class="q-table_itemTitleSub ellipsis">
              {{ props.value }}
              <q-tooltip>{{ props.value }}</q-tooltip>
            </div>
            <!-- <p v-if="props.row.수량 === 0" class="q-table_itemTitleSub">
              (제약사품절)
            </p> -->
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-수량="props">
        <q-td :props="props">
          <div class="q-table_number_wrap flex_center">
            <div class="qty_wrap">
              <button
                type="button"
                class="btn_qty"
                @click="props.row.ITEM_QTY > 0 ? props.row.ITEM_QTY-- : null"
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
                @focus="(input) => input.target.select()"
              />
              <button
                type="button"
                class="btn_qty"
                @click="props.row.ITEM_QTY++"
              >
                <i class="ico qty-plus"></i>
              </button>
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-유효기간="props">
        <q-td :props="props">
          <div class="itemSearch_input_wrap">
            <!-- <DatePickerComp /> -->
            <div class="DatePickerComp_wrap">
              <!-- <div> -->
              <!-- <p>
                  {{ date }}
                </p> -->
              <q-input
                v-model="props.row.EXP_DT"
                input-class="itemSearch_input"
                bg-color="white"
                mask="####-##-##"
                borderless
                dense
                @focus="(input) => input.target.select()"
                @update:model-value="(val) => handleExpDtUpdate(val, props)"
              />
              <!-- </div> -->
              <div class="date_btn_wrap">
                <q-btn class="calendar_icon" unelevated dense>
                  <img
                    src="assets/icons/calendar-days-solid.svg"
                    alt="calendar"
                  />
                  <q-popup-proxy
                    @before-show="updateProxy(props.row)"
                    cover
                    transition-show="scale"
                    transition-hide="scale"
                  >
                    <q-date
                      v-model="proxyDate"
                      :default-date="today"
                      :title="selectedDate"
                      :subtitle="selectedYear"
                    >
                      <div class="row items-center justify-end q-gutter-sm">
                        <q-btn
                          label="취소"
                          color="primary"
                          flat
                          v-close-popup
                        />
                        <q-btn
                          label="확인"
                          color="primary"
                          flat
                          @click="() => onDateConfirm(props.row)"
                          v-close-popup
                        />
                      </div>
                    </q-date>
                  </q-popup-proxy>
                </q-btn>
              </div>
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:top-row v-if="rows.length === 0 && searched">
        <q-tr class="no-date">
          <q-td colspan="100%">
            <span>검색된 결과가 없습니다.</span>
          </q-td>
        </q-tr>
      </template>

      <template v-slot:body-cell-제조번호="props">
        <q-td :props="props">
          <div class="q-table_number_wrap flex_center">
            <q-input
              v-if="filterCond !== '04' && props.row.SUBDIV_YN !== 'Y'"
              v-model="props.row.PROD_NO"
              input-class="itemSearch_input"
              bg-color="white"
              borderless
              dense
              @focus="(input) => input.target.select()"
              @update:model-value="updateChk(props)"
              @blur="handleProdNoBlur(props)"
            />
            <q-input
              v-if="filterCond !== '04' && props.row.SUBDIV_YN === 'Y'"
              v-model="props.row.PROD_NO"
              disable
            />
            <q-input
              v-if="filterCond === '04'"
              v-model="props.row.PROD_NO"
              disable
            />
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-삭제="props">
        <q-td :props="props" @click="onClickDelete(props.row)">
          <div>
            <!-- <img src="assets/icons/x-solid.svg" alt="x" /> -->
            <img src="assets/images/ico_delete.svg" alt="삭제" />
          </div>
        </q-td>
      </template>

      <template v-slot:bottom>
        <div class="all_price">
          <div class="all_left">
            <span>총 주문 품목</span>
            <b>{{ JSON.stringify(selectedItems.length) }}</b>
            <span>건</span>
          </div>
          <div class="all_right">
            <b>{{ total }}</b>
            <span>원</span>
          </div>
          <BtnComp_default_c class="btn-ord" @click="onClickBakRcptReg">
            <b class="fs-5">반품등록</b><i class="ico h12 arrw-rt go"></i>
          </BtnComp_default_c>
        </div>

        <div class="memo flex-beteween">
          <q-input
            v-model="ordMemo"
            class="q-table_bottom_input"
            outlined
            placeholder="메모"
            type="textarea"
            maxlength="2000"
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
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { computed, ref, onMounted, inject, toRaw } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";
import BtnComp_default_c from "src/components/web/common/BtnComp_default_c.vue";
import DatePickerComp from "src/components/web/common/DatePickerComp.vue";

const userInfo = util.getUserData();
const bus = inject("bus");
const selectedItems = ref([]);
const ordMemo = ref("");
const rows = ref([]);
const loading = ref(false);
const searched = ref(false);

const basketSearchParam = ref({
  userGbCd: userInfo.USER_GB_CD,
  custCd: userInfo.CUST_CD,
  basketGbCd: "02",
});

const rowInfo = ref({
  userId: userInfo.USER_ID,
  custCd: userInfo.CUST_CD,
  basketGbCd: "",
  itemGbCd: "",
  saveItemCd: "",
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
function handleExpDtUpdate(val, props) {
  props.row.EXP_DT = val;

  // 10자리 다 입력됐을 때만 유효성 체크
  if (val && val.trim().length === 10) {
    if (!moment(val, "YYYY-MM-DD", true).isValid()) {
      alert("유효하지 않은 날짜 형식입니다.");
      props.row.EXP_DT = "";
      return;
    }

    updateChk(props);
    onClickImsiSaveWithoutValidation();
  }
}
function handleProdNoUpdate(val, props) {
  props.row.PROD_NO = val;
  updateChk(props);

  // ✅ 유효기간이 있을 때만 저장 시도
  if (props.row.EXP_DT && props.row.EXP_DT.trim() !== "") {
    onClickImsiSave();
  }
}
const filterCond = ref("all");
function saveAndSaveImsi(row) {
  save(row); // 기존 날짜 저장 로직
  onClickImsiSave(); // 임시 저장 호출
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

//라디오 클릭시 해당 품목 셀렉트
const radioClick = (val) => {
  selectedItems.value = [];

  if (val == 0) {
    if (rows.value.length > 0) {
      selectedItems.value = rows.value;
    }
  } else if (val == 1) {
    const newRows = toRaw(rows.value).filter(
      (item) =>
        item.MED_CD === "02" &&
        item.SUBDIV_YN === "N" &&
        item.HAZARD_BACK_YN === "N" &&
        item.TAX_YN === "Y"
    );
    if (newRows.length > 0) {
      selectedItems.value = newRows;
    }
  } else if (val == 2) {
    const newRows = toRaw(rows.value).filter(
      (item) =>
        item.MED_CD === "01" &&
        item.SUBDIV_YN === "N" &&
        item.HAZARD_BACK_YN === "N" &&
        item.TAX_YN === "Y"
    );
    if (newRows.length > 0) {
      selectedItems.value = newRows;
    }
  } else if (val == 3) {
    const newRows = toRaw(rows.value).filter(
      (item) => item.SUBDIV_YN === "N" && item.TAX_YN === "N"
    );
    if (newRows.length > 0) {
      selectedItems.value = newRows;
    }
  } else if (val == 4) {
    const newRows = toRaw(rows.value).filter((item) => item.SUBDIV_YN === "Y");
    if (newRows.length > 0) {
      selectedItems.value = newRows;
    }
  } else if (val == 5) {
    const newRows = toRaw(rows.value).filter(
      (item) => item.SUBDIV_YN === "N" && item.HAZARD_BACK_YN === "Y"
    );
    if (newRows.length > 0) {
      selectedItems.value = newRows;
    }
  }
};

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
// 숫자 포맷 변환 함수
const formatNumber = (value) => {
  return value ? util.getNumberFormat(value) : "0";
};
const radioLabelAll = computed(() => {
  // //전문
  // const tempCnt = toRaw(rows.value).filter(
  //   (item) => item.MED_CD === "02" && item.SUBDIV_YN === 'N'
  // ).length;

  // //일반
  // const tempCnt2 = toRaw(rows.value).filter(
  //   (item) => item.MED_CD === "01" && item.SUBDIV_YN === 'N'
  // ).length;

  // //소분
  // const tempCnt3 = toRaw(rows.value).filter(
  //   (item) => item.SUBDIV_YN === "Y"
  // ).length;

  // let sum = tempCnt + tempCnt2 + tempCnt3;

  return `전체 ${rows.value.length}건`;
});

const radioLabelA = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) =>
      item.MED_CD === "02" &&
      item.SUBDIV_YN === "N" &&
      item.HAZARD_BACK_YN === "N" &&
      item.TAX_YN === "Y"
  ).length;

  return `전문 ${tempCnt}건`;
});
const radioLabelB = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) =>
      item.MED_CD === "01" &&
      item.SUBDIV_YN === "N" &&
      item.HAZARD_BACK_YN === "N" &&
      item.TAX_YN === "Y"
  ).length;

  return `일반 ${tempCnt}건`;
});
const radioLabelC = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) => item.SUBDIV_YN === "N" && item.TAX_YN === "N"
  ).length;

  return `비과세반품 ${tempCnt}건`;
});
const radioLabelD = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) => item.SUBDIV_YN === "Y"
  ).length;

  return `소분반품 ${tempCnt}건`;
});
const radioLabelE = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) => item.SUBDIV_YN === "N" && item.HAZARD_BACK_YN === "Y"
  ).length;

  return `회수 ${tempCnt}건`;
});

const getselectedItemsString = () => {
  return selectedItems.value.length === 0
    ? ""
    : `${selectedItems.value.length} record${
        selectedItems.value.length > 1 ? "s" : ""
      } selectedItems of ${rows.value.length}`;
};

const total = computed(() => {
  return util.getNumberFormat(
    selectedItems.value.reduce((total, selectedItem) => {
      const selectedItemsRow = rows.value.find(
        (row) => row.ITEM_CD === selectedItem.ITEM_CD
      );

      return (
        total +
        (selectedItemsRow
          ? selectedItemsRow.ORD_WP2_AMT * selectedItemsRow.ITEM_QTY
          : 0)
      );
    }, 0)
  );
});

const proxyDate = ref(moment().format("YYYY-MM-DD"));

function updateProxy(row) {
  proxyDate.value = row.EXP_DT;
}

function save(row) {
  const tempCond = filterCond.value;
  row.EXP_DT = proxyDate.value;
  filterCond.value = "00";
  filterCond.value = tempCond;
}

function validDate(row) {
  const expDt = row.EXP_DT;

  if (expDt) {
    if (!moment(expDt, "YYYY-MM-DD", true).isValid()) {
      alert("유효하지 않은 날짜 형식입니다.");
      row.EXP_DT = "";
      return;
    }
  }
  onClickImsiSave();
}

// 오늘 날짜와 기본 날짜 설정 250307
const today = new Date();
// 캘린더 title 250307
const selectedDate = computed(() => {
  const date = new Date(proxyDate.value);
  const month = date.getMonth() + 1; // 0-11에서 1-12로 변경
  const day = date.getDate();
  const weekday = date.toLocaleDateString("ko-KR", { weekday: "short" }); // 요일 가져오기

  return proxyDate.value
    ? `${month}월 ${day}일 (${weekday[0].toUpperCase() + weekday.slice(1)})`
    : `${new Date(today).getMonth() + 1}월 ${new Date(today).getDate()}일`;
});
// 캘린더 subtitle 250307
const selectedYear = computed(() => {
  return proxyDate.value
    ? new Date(proxyDate.value).getFullYear()
    : new Date(today).getFullYear();
});

//---------------------------- Function ------------------------------//

// 반품 장바구니 목록 조회
async function fetchBasketList(filterVal) {
  // 필터 체크
  let selectChk = "all";

  if (filterVal !== undefined) {
    selectChk = filterVal;
  }

  selectedItems.value = [];
  loading.value = true;
  ordMemo.value = "";

  const res = await api.get("/bakRcpt/basketList", {
    params: basketSearchParam.value,
  });

  searched.value = true;
  rows.value = res;

  // ✅ 제조번호 이전값 저장 (_PREV_PROD_NO)
  rows.value.forEach((row) => {
    row._PREV_PROD_NO = row.PROD_NO;
  });

  loading.value = false;

  // 필터 적용
  if (selectChk === "all") {
    if (rows.value.length > 0) {
      selectedItems.value = rows.value;
      filterCond.value = selectChk;
    }
  } else {
    if (filteredRows.value.length > 0) {
      selectedItems.value = filteredRows.value;
      filterCond.value = selectChk;
    }
  }
}

// 장바구니 목록 단건 삭제
async function onClickDelete(row) {
  //필터값
  let filterVal = filterCond.value;

  // lint 정렬때문에 컨펌 메세지 틀어져서 이렇게 처리함..
  let confirmMsg = `품목 : ${row.ITEM_NM}\n`;
  confirmMsg += `수량 : ${row.ITEM_QTY}\n`;
  confirmMsg += `취소하시겠습니까?`;

  if (!confirm(confirmMsg)) return;

  rowInfo.value.itemGbCd = row.ITEM_GB_CD;
  rowInfo.value.saveItemCd = row.ITEM_CD;
  rowInfo.value.basketGbCd = row.BASKET_GB_CD;

  await api.delete("/bakRcpt/deleteBakRcptBasket", {
    params: rowInfo.value,
  });

  await fetchBasketList(filterVal);
  await bus.emit("refreshHeaderBasket");
  // await bus.emit("refreshHistoryList");
  // await bus.emit("refreshReturnList");
}

const vaildBakRcptData = computed(() => {
  let errorCnt = 0;

  selectedItems.value.forEach((m) => {
    const medCd = m.MED_CD;
    const expDt = m.EXP_DT;
    const prodNo = m.PROD_NO;
    const subdiv = m.SUBDIV_YN;
    if (filterCond.value !== "04" && subdiv !== "Y") {
      if (medCd === "02") {
        if (!expDt) {
          alert(`${m.ITEM_NM}의 유효기간을 입력해주세요.`);
          errorCnt++;
        } else if (!prodNo) {
          alert(`${m.ITEM_NM}의 제조번호를 입력해주세요.`);
          errorCnt++;
        }
      } else if (medCd === "01") {
        if (!expDt) {
          alert(`${m.ITEM_NM}의 유효기간을 입력해주세요.`);
          errorCnt++;
        }
      }
    }
  });

  if (errorCnt > 0) {
    return false;
  }

  return true;
});
// 주문 품목 유효성 체크
function checkBasket(obj) {
  const itemNm = obj.ITEM_NM;
  const itemQty = util.getNumberFormat2(obj.ITEM_QTY);
  const limitQty = obj.LIMIT_QTY;
  const itemQtyOrg = obj.ITEM_QTY_ORG;
  if (itemQty === null || itemQty === "" || itemQty === "0") {
    alert(itemNm + "\n반품가능수량을 확인해 주세요.");
    return false;
  }
  if (limitQty < itemQty) {
    alert(itemNm + "\n반품가능수량(" + limitQty + ")이 초과되었습니다.");
    return false;
  }

  return true;
}
// 임시저장 클릭
async function onClickImsiSave() {
  if (!vaildBakRcptData.value) return;
  selectedItems.value.forEach((item) => {
    if (!checkBasket(item)) {
      errorCnt++;
      return;
    }
  });

  selectedItems.value.forEach((m) => {
    Object.assign(m, {
      CUST_CD: userInfo.CUST_CD,
      USER_ID: userInfo.USER_ID,
      BRCH_CD: userInfo.BRCH_CD,
      ORD_MEMO: ordMemo.value,
    });
  });

  try {
    await api.post("/bakRcpt/saveBakRcptImsi", selectedItems.value);

    util.showNotify("반품접수 임시저장 되었습니다.");

    await bus.emit("refreshHeaderBasket");
    await fetchBasketList();

    // selectedItems.value = [];
  } catch (error) {
    error.response.data;
  }
}
// 반품등록 클릭
async function onClickBakRcptReg() {
  if (selectedItems.value.length === 0) {
    alert("선택된 항목이 없습니다.");
    return;
  }

  if (!vaildBakRcptData.value) return;
  selectedItems.value.forEach((item) => {
    if (!checkBasket(item)) {
      errorCnt++;
      return;
    }
  });

  const confirmMsg = `총 ${selectedItems.value.length}건을 반품하시겠습니까?`;
  if (!confirm(confirmMsg)) return;
  selectedItems.value.forEach((m) => {
    const subdivYn = m.SUBDIV_YN;
    const taxYn = m.TAX_YN;

    // 프로시저 분기때문에 추가
    if (subdivYn === "Y" && taxYn === "Y" && !isRet(m.ITEM_NM)) {
      Object.assign(m, { RETURN_CAT: 6 });
    } else {
      Object.assign(m, { RETURN_CAT: 0 });
    }

    Object.assign(m, {
      CUST_CD: userInfo.CUST_CD,
      USER_ID: userInfo.USER_ID,
      BRCH_CD: userInfo.BRCH_CD,
      ORD_MEMO: ordMemo.value,
    });
  });

  try {
    await api.post("/bakRcpt/saveBakRcpt", selectedItems.value);

    util.showNotify("반품접수 되었습니다.");

    await bus.emit("refreshHeaderBasket");
    await fetchBasketList();

    // selectedItems.value = [];
  } catch (error) {
    error.response.data;
  }
}

// 그리드 row 클릭
async function onClickRow(row) {
  await bus.emit("bakRcptBasketHistoryList", row);
  await bus.emit("itemDetailInfo", row);
}

// 반품 그리드에서 담기 버튼 클릭 시 장바구니 목록 재조회
bus.on("refreshRetrunBasket", async () => {
  await fetchBasketList();
});

onMounted(async () => {
  await fetchBasketList();
});

const updateChk = async (props) => {
  const isSubdiv = props.row.SUBDIV_YN !== "N";

  if (isSubdiv) {
    basketData.value.basketGbCd = "03";
  }

  basketData.value.expDt = props.row.EXP_DT;
  basketData.value.prodNo = props.row.PROD_NO;
  basketData.value.saveItemCd = props.row.ITEM_CD;
  basketData.value.itemGbCd = props.row.ITEM_GB_CD;
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

  if (rowInfo.value.itemQty == 0 || rowInfo.value.itemQty == " ") {
    alert("수량을 입력해 주시기 바랍니다.");
    row.ITEM_QTY = row.ITEM_QTY_ORG;
    return;
  }

  if (rowInfo.value.ordWp2Amt == 0 || rowInfo.value.ordWp2Amt == " ") {
    alert("단가를 입력해 주시기 바랍니다.");
    return;
  }

  // 변경되었는지 확인하기 애매해서 추가함.. 요청 있을경우 삭제
}
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
    name: "품목",
    label: "품목 및 규격",
    align: "left",
    required: true,
    field: "ITEM_NM_UNIT",
    classes: "td-prd_cart",
    format: (val) => `${val}`,
    // style: {
    //   paddingLeft: "8px",
    // },
    // headerStyle: {
    //   paddingLeft: "8px",
    // },
  },
  {
    name: "제약회사",
    label: "제약회사",
    field: "MAKER_NM",
    align: "left",
    classes: "td-comp_cart",
  },
  {
    name: "수량",
    label: "수량",
    field: "ITEM_QTY",
    align: "center",
    classes: "td-qty",
  },
  {
    name: "유효기간",
    label: "유효기간(대표)",
    field: "EXP_DT",
    align: "center",
    classes: "td-exp",
  },
  {
    name: "제조번호",
    label: "제조번호(대표)",
    field: "PROD_NO",
    align: "center",
    classes: "td-serial",
  },
  {
    name: "삭제",
    label: "삭제",
    field: "삭제",
    align: "center",
    classes: "td-del",
  },
];
function handleProdNoBlur(props) {
  const currVal = props.row.PROD_NO?.trim() ?? "";
  const prevVal = props.row._PREV_PROD_NO?.trim() ?? "";

  if (currVal !== "" && currVal !== prevVal) {
    updateChk(props);
    onClickImsiSaveWithoutValidation();
    props.row._PREV_PROD_NO = currVal; // ✅ 저장 후 이전값 갱신
  }
}
async function onClickImsiSaveWithoutValidation() {
  selectedItems.value.forEach((m) => {
    Object.assign(m, {
      CUST_CD: userInfo.CUST_CD,
      USER_ID: userInfo.USER_ID,
      BRCH_CD: userInfo.BRCH_CD,
      ORD_MEMO: ordMemo.value,
    });
  });

  try {
    await api.post("/bakRcpt/saveBakRcptImsi", selectedItems.value);

    util.showNotify("반품접수 임시저장 되었습니다.");
    await bus.emit("refreshHeaderBasket");
    await fetchBasketList();
  } catch (error) {
    console.error(error.response?.data);
  }
}
function onDateConfirm(row) {
  row.EXP_DT = proxyDate.value; // ✅ 값 반영
  updateChk({ row }); // 서버 전송용 데이터 세팅
  saveSingleItemWithoutValidation(row); // ✅ 해당 행만 저장
}
</script>

<style lang="scss">
.returnTable_b {
  // height: 490px;
  height: 385px;
  overflow-y: auto;
}
.q-table_radio_wrap {
  padding: 2px;
}
.selectedItems td:first-child {
  padding: 20px;
}
.q-table--col-auto-width {
  padding: 0 0 0 4px !important;
}
.q-checkbox--dense .q-checkbox__inner {
  width: 20px;
  min-width: 20px;
  height: 20px;
}
.returnTable_b {
  .itemSearch_input {
    @include fs-5;
    width: 100px;
    height: 32px;
    border: 1px solid $line-400;
    padding: 4px 6px;
    border-radius: 0;
  }
  .DatePickerComp_wrap {
    > div:nth-child(1) {
      width: auto !important;
    }
    .q-input {
      width: 100% !important;
    }
  }
  .td-exp {
    width: 120px;
  }
  .itemSearch_input_wrap {
    .DatePickerComp_wrap {
      @include flex_start;
      gap: 8px 0;
      padding: 0 0 0 4px;
      width: 120px;
      height: 24px;
      border: 1px solid $line-400;
      overflow: hidden;
      .q-input {
        width: calc(100% - 24px);
        padding: 0;
      }
      .q-field__native {
        width: 100%;
        padding: 0;
        border: none;
      }
    }
    .date_input_wrap,
    .date_btn_wrap {
      display: inline-flex;
      align-items: center;
    }
    .date_input_wrap {
      padding: 0 4px;

      border-radius: 0;
    }
    .date_btn_wrap {
      position: relative;
      width: 24px;
      .q-btn {
        width: 100%;
        border-radius: 0;
        img {
          height: 13px;
        }
      }
      &::before {
        content: "";
        position: absolute;
        top: 50%;
        left: -1px;
        transform: translateY(-50%);
        width: 1px;
        height: 12px;
        background-color: $line-400;
      }
    }
  }
}
.q-table_number_wrap {
  max-width: 100px;
}
</style>

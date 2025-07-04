<template>
  <div class="Bottom_wrap">
    <q-dialog
      :model-value="dialog_bottom_model"
      @update:model-value="$emit('update:dialog_bottom_model', $event)"
      no-backdrop-dismiss
      seamless
      position="bottom"
    >
      <div class="BottomDialog_wrap" :class="BottomDialogStyle">
        <!-- style="height: 140px" -->
        <div
          class="BottomDialog_close_btn_wrap"
          v-if="bottom_layout_close === true"
        >
          <q-btn flat dense size="xs" @click="closeDialog">
            <img src="assets/icons/bars-solid-close.svg" alt="icon" />
          </q-btn>
        </div>
        <q-tab-panels v-model="dialog_bottom_tab" animated>
          <q-tab-panel name="first_tab" class="first_tab">
            <div class="first_grid">
              <div class="first_item">
                <div class="table_badge_wrap" style="padding: 0">
                  <!-- <q-badge class="table_badge_red">
                    {{ first_bottom_mednm }}
                  </q-badge>
                  <q-badge class="table_badge_blue">
                    {{ first_bottom_payitemnm }}
                  </q-badge> -->
                  <q-badge class="bdg-line normal" v-if="first_bottom_mednm">
                    {{
                      first_bottom_mednm === "일"
                        ? "일반"
                        : first_bottom_mednm === "전"
                        ? "전문"
                        : first_bottom_mednm
                    }}
                  </q-badge>
                  <q-badge
                    class="bdg-line"
                    :class="first_bottom_payitemnm === '비' ? 'nonpay' : 'pay'"
                    v-if="first_bottom_payitemnm"
                  >
                    {{
                      first_bottom_payitemnm === "급"
                        ? "급여"
                        : first_bottom_payitemnm === "비"
                        ? "비급여"
                        : first_bottom_payitemnm
                    }}
                  </q-badge>
                </div>
                <span>{{ first_bottom_약 }}</span>
                <span>{{ first_bottom_규격 }}</span>
                <span>{{ first_bottom_배송 === "G" ? "광역" : "" }}</span>
              </div>
              <div class="first_item">
                <template v-if="first_bottom_재고 > 0">
                  <span>재고</span>
                  <span>{{ first_bottom_재고 }}</span>
                  <span>개</span>
                </template>
                <template v-else>
                  <span style="color: red">품절</span>
                </template>
              </div>
              <div class="first_item" v-if="first_bottom_가격 > 0">
                <span>금액</span>
                <span>{{ util.getNumberFormat(first_bottom_가격) }}</span>
                <span>원</span>
              </div>
            </div>
            <div class="first_bottom">
              <div class="first_left">
                <div class="first_total" v-if="useTotal">
                  <div class="">
                    <span>총 주문 품목</span>
                    <span>{{ totalcnt }}</span>
                    <span>건</span>
                  </div>
                  <div class="">
                    <span>{{ util.getNumberFormat(totalamt) }}</span>
                    <span>원</span>
                  </div>
                </div>
                <div class="first_amount" v-else-if="useAmount">
                  <div class="">
                    <span>수량</span>
                    <span>{{ first_amount }}</span>
                    <span>건</span>
                  </div>
                </div>
                <div class="first_ea" v-else>
                  <EAInputComp_mobile
                    inputStyle="EA-md"
                    v-model:model-value="EAcount"
                    @update:model-value="(val) => (ordCount = val)"
                  />
                </div>
              </div>
              <BtnComp_mobile
                :buttonText="first_addBtn"
                btnStyle="default"
                btnSize="md"
                style="width: 100%"
                @click="change_tab('second_tab')"
              />
            </div>
          </q-tab-panel>
          <q-tab-panel name="second_tab" class="second_tab">
            <div class="second_text">
              <div class="second_item">
                <div class="table_badge_wrap" style="padding: 0">
                  <!-- <q-badge class="table_badge_red">
                    {{ first_bottom_mednm }}
                  </q-badge>
                  <q-badge class="table_badge_blue">
                    {{ first_bottom_payitemnm }}
                  </q-badge> -->
                  <q-badge class="bdg-line normal" v-if="first_bottom_mednm">
                    {{
                      first_bottom_mednm === "일"
                        ? "일반"
                        : first_bottom_mednm === "전"
                        ? "전문"
                        : first_bottom_mednm
                    }}
                  </q-badge>
                  <q-badge
                    class="bdg-line"
                    :class="first_bottom_payitemnm === '비' ? 'nonpay' : 'pay'"
                    v-if="first_bottom_payitemnm"
                  >
                    {{
                      first_bottom_payitemnm === "급"
                        ? "급여"
                        : first_bottom_payitemnm === "비"
                        ? "비급여"
                        : first_bottom_payitemnm
                    }}
                  </q-badge>
                </div>
                <span>{{ second_bottom_약 }}</span>
                <span>{{ second_bottom_규격 }}</span>
                <!-- <span>{{ second_bottom_배송 }}</span> -->
              </div>
              <div class="second_item" v-if="ordQty">
                <span>주문 수량</span>
                <span>{{ ordCount }}개,</span>
                <span>장바구니에 담겼습니다.</span>
              </div>
              <div class="second_memo_item" v-if="bottom_layout === true">
                <img
                  src="assets/icons/arrow-back-black.svg"
                  alt="icon"
                  @click="change_tab('first_tab')"
                />
                <span>{{ second_bottom_memoTitle }}에 메모를</span>
                <span>추가</span>
                <span>하시겠습니까?</span>
              </div>
            </div>
            <div class="second_bottom" v-if="bottom_layout === false">
              <!-- <BtnComp_mobile
                :buttonText="second_backBtn"
                btnStyle="white"
                btnSize="md"
                style="width: 100%"
                @click="change_tab('first_tab')"
              />
              <BtnComp_mobile
                :buttonText="second_nextBtn"
                btnStyle="default"
                btnSize="md"
                style="width: 100%"
                @click="onClickNextBtn"
              /> -->
              <BtnComp_mobile
                buttonText="확인"
                btnStyle="default"
                btnSize="md"
                @click="closeDialog"
              />
            </div>
            <div class="second_bottom" v-if="bottom_layout === true">
              <BtnComp_mobile
                :buttonText="second_backBtn"
                btnStyle="white"
                btnSize="md"
                class="btn-line default"
                @click="dialog_메모 = true"
              />
              <DialogLayout_mobile
                v-model:dialog_model_prop="dialog_메모"
                :dialog_title="memo_dialog_title"
                :dialog_full="dialog_full"
              >
                <div class="">
                  <TextAreaComp_mobile
                    submit="메모등록"
                    :is-visible="true"
                    flag="ordReg"
                    @update-memo="(val) => regMemo(val)"
                  />
                </div>
              </DialogLayout_mobile>
              <BtnComp_mobile
                :buttonText="second_nextBtn"
                btnStyle="default"
                btnSize="md"
                @click="onClickRegBtn"
              />
            </div>
          </q-tab-panel>
        </q-tab-panels>
        <div>
          <span></span>
        </div>
      </div>
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, defineEmits, computed, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import _ from "lodash";
import { useRouter } from "vue-router";

import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import EAInputComp_mobile from "src/components/mobile/__commonComp/inputComp/EAInputComp_mobile.vue";
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import TextAreaComp_mobile from "src/components/mobile/DialogComp/__commonDialogComp/TextAreaComp_mobile.vue";

const router = useRouter();
const EAcount = ref(0);
const ordCount = ref(0);
const bus = inject("bus");

const dialog_메모 = ref(false);
const dialog_full = ref(true);

const dialog_bottom_tab = ref("first_tab");
const ordMemo = ref("");
const emit = defineEmits(["update:dialog_bottom_model", "refreshList"]);

//************************************************************ */

const closeDialog = () => {
  emit("update:dialog_bottom_model", false);
  dialog_bottom_tab.value = "first_tab";
};

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
  const selected = _.cloneDeep(props.selecteditem);

  let errorCnt = 0;
  let wideOrderCnt = 0;

  selected.forEach((item) => {
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
    let orderMsg = `총 ${selected.length}건을 주문하시겠습니까?`;

    if (wideOrderCnt > 0) {
      orderMsg += `\n\n광역 주문 상품의 경우\n배송에 2일 이내 소요될 수 있습니다.`;
    }

    if (confirm(orderMsg)) {
      selected.forEach((m) =>
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
        await api.post("/ord/orderReg", selected);
        await bus.emit("refreshMobileBasket");
        util.showNotifyTop("주문접수 처리되었습니다.");

        ordMemo.value = "";
        emit("refreshList");
      } catch (error) {
        alert(error.response.data);
      }
    }
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

// 반품등록 유효성 검사
const vaildBakRcptData = computed(() => {
  const selectedItems = _.cloneDeep(props.selecteditem);
  let errorCnt = 0;

  selectedItems.forEach((m) => {
    const medCd = m.MED_CD;
    const expDt = m.EXP_DT;
    const prodNo = m.PROD_NO;

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
  });

  if (errorCnt > 0) {
    return false;
  }

  return true;
});

// 반품등록 클릭
async function onClickBakRcptReg() {
  const returnItems = _.cloneDeep(props.selecteditem);

  if (!vaildBakRcptData.value) {
    return;
  }

  returnItems.forEach((m) => {
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
    await api.post("/bakRcpt/saveBakRcpt", returnItems);

    util.showNotify("반품접수 처리되었습니다.");

    emit("refreshList");
  } catch (error) {
    error.response.data;
  }
}

async function onClickRegBtn() {
  if (props.flag === "addOrdBasket") {
    // 주문 장바구니 추가
    await onClickAddBasket(tabName);
  } else if (props.flag === "ordReg") {
    //주문등록
    await onClickOrderReg();
  } else if (props.flag === "addReturnBasket") {
    // 반품 바구니 추가
    await onClickAddBakBasket(props.selecteditem, tabName);
  } else if (props.flag === "returnReg") {
    // 반품등록
    await onClickBakRcptReg();
  }
}

async function change_tab(tabName) {
  if (props.flag === "addOrdBasket") {
    // 장바구니 추가

    if (tabName === "second_tab") {
      if (EAcount.value === 0) {
        alert("수량을 선택해 주시기 바랍니다.");
        return;
      }

      await onClickAddBasket(tabName);
    } else {
      dialog_bottom_tab.value = tabName;
    }
  } else if (props.flag === "ordReg") {
    // 주문등록
    dialog_bottom_tab.value = tabName;
  } else if (props.flag === "addReturnBasket") {
    // 반품 바구니 추가
    await onClickAddBakBasket(props.selecteditem, tabName);
  } else if (props.flag === "returnReg") {
    // 반품등록
    // await onClickBakRcptReg();
    dialog_bottom_tab.value = tabName;
  } else if (props.flag === "reOrder") {
    // 재주문(최근주문내역)

    if (tabName === "second_tab") {
      if (EAcount.value === 0) {
        alert("수량을 선택해 주시기 바랍니다.");
        return;
      }

      if (props.first_bottom_재고 < EAcount.value) {
        let msg = `${props.first_bottom_약} 품목의 재고가 부족합니다.\n`;
        msg += `주문 수량을 확인해 주시기 바랍니다.`;
        alert(msg);
        return;
      }

      await onClickAddBasket(tabName);
    } else {
      dialog_bottom_tab.value = tabName;
    }
  }
}

const userInfo = util.getUserData();

const basketData = ref({
  userId: userInfo.USER_ID,
  custCd: userInfo.CUST_CD,
  basketGbCd: "01",
  saveItemCd: "",
  saveItemGbCd: "",
  saveItemQty: "",
  dlvBrchCd: "",
});

// 반품 담기
async function onClickAddBakBasket(row, tabName) {
  ordCount.value = props.second_bottom_주문수량;

  if (ordCount.value === 0) {
    alert("수량을 선택해 주시기 바랍니다.");
    return;
  }

  const returnBasketData = ref({
    basketGbCd: "02",
    expDt: "",
    prodNo: "",
    saveItemCd: "",
    subdivYn: "",
    itemGbCd: "",
    saveItemQty: "",
    custCd: userInfo.CUST_CD,
    userId: userInfo.USER_ID,
  });

  // if (row.REQ_QTY === 0) {
  //   alert("수량을 선택해 주시기 바랍니다.");
  //   return;
  // }

  if (row.LIMIT_QTY < EAcount.value) {
    alert("반품 가능한 수량을 초과할 수 없습니다.");
    return;
  }

  if (row.BASKET_QTY !== "") {
    const qtySum = row.BASKET_QTY + EAcount.value;

    if (row.LIMIT_QTY < qtySum) {
      alert("반품 가능한 수량을 초과할 수 없습니다.");
      return;
    }
  }

  if (row.RETURN_CAT === "2" || row.RETURN_CAT === "6") {
    returnBasketData.value.basketGbCd = "03";
  } else {
    returnBasketData.value.basketGbCd = "02";
  }

  returnBasketData.value.expDt = row.EXP_DT;
  returnBasketData.value.prodNo = row.PROD_NO;
  returnBasketData.value.saveItemCd = row.ITEM_CD;
  returnBasketData.value.subdivYn = row.SUBDIV_YN;
  returnBasketData.value.itemGbCd = "01";
  returnBasketData.value.saveItemQty = EAcount.value;

  try {
    await api.post("/bakRcpt/addBasket", returnBasketData.value);

    dialog_bottom_tab.value = tabName;
  } catch (error) {
    alert(error.response.data);
  }
}

// 담기 클릭
async function onClickAddBasket(tabName) {
  basketData.value.saveItemCd = props.first_bottom_약코드;
  basketData.value.saveItemGbCd = props.first_bottom_GB코드;
  basketData.value.dlvBrchCd = props.first_bottom_GBRCH코드;
  basketData.value.saveItemQty = EAcount.value;

  const availData = props.first_bottom_재고;

  if (availData < EAcount.value) {
    alert("재고수량(" + availData + ")이 초과되었습니다.");
    return;
  }

  //담기 시 장바구니 체크
  const res = await api.post("/ord/getBasketDupOrd2", {
    custCd: userInfo.CUST_CD,
    basketGbCd: "01",
    saveItemCd: props.first_bottom_약코드,
    gDlvBrchCd: props.first_bottom_GBRCH코드,
  });

  if (res.ITEM_CNT == 0) {
    if (userInfo.CUST_CD !== "") {
      const res = await api.get("/main/basketCount", {
        params: { custCd: userInfo.CUST_CD },
      });

      if (res.ORD_COUNT >= 150) {
        alert(
          "장바구니에 150개 품목을 초과하여 담을 수 없습니다. 기존 품목 주문등록 후 다시 담기 바랍니다"
        );
        closeDialog();
        EAcount.value = 0;
        return;
      }
    }
  } else {
    const confirmMsg =
      "장바구니에 이미 담은 품목입니다. 수량 추가하시겠습니까?";
    if (!confirm(confirmMsg)) return;
  }

  if (props.first_bottom_배송 === "G") {
    alert("광역 주문 상품의 경우 배송에 2일 이내 소요될 수 있습니다.");

    const res = await api.post("/ord/getBasketDupOrd", {
      custCd: userInfo.CUST_CD,
      brchCd: userInfo.BRCH_CD,
      basketGbCd: "01",
      saveItemCd: props.first_bottom_약코드,
      saveItemGbCd: props.first_bottom_GB코드,
      gDlvBrchCd: props.first_bottom_GBRCH코드,
    });

    if (res.CNT > 0) {
      alert("동일한 품목이 장바구니에 담겼습니다.\n다시 확인하시기 바랍니다.");
      EAcount.value = 0;
      closeDialog();
      return;
    }
  }
  try {
    await api.post("/ord/addBasket", basketData.value);
    await bus.emit("refreshMobileBasket");
    EAcount.value = 0;
    dialog_bottom_tab.value = tabName;
  } catch (error) {
    alert(error.response.data);
  }
}

// 이동 버튼 클릭
async function onClickNextBtn() {
  if (props.flag === "addOrdBasket") {
    router.push({ path: "/ordBasket" });
  } else if (props.flag === "addReturnBasket") {
    router.push({ path: "/returnBasket" });
  } else if (props.flag === "reOrder") {
    router.push({ path: "/ordBasket" });
  }
}

// 메모 등록
function regMemo(val) {
  ordMemo.value = val;

  dialog_메모.value = false;
}

/////////////////////////////////////////////////////////////////////

const props = defineProps({
  dialog_bottom_model: {
    type: Boolean,
    default: false,
  },
  bottom_layout: {
    type: Boolean,
  },
  bottom_layout_close: {
    type: Boolean,
  },
  BottomDialogStyle: {
    type: String,
  },

  first_bottom_mednm: {
    type: String,
    default: "",
  },
  first_bottom_payitemnm: {
    type: String,
    default: "",
  },
  first_bottom_badge: {
    type: Number,
    default: 0,
  },
  first_bottom_약: {
    type: String,
    default: "",
  },
  first_bottom_규격: {
    type: String,
    default: "",
  },
  first_bottom_배송: {
    type: String,
    default: "",
  },
  first_bottom_재고: {
    type: Number,
    default: 0,
  },
  first_bottom_가격: {
    type: Number,
    default: 0,
  },
  first_bottom_주문수량: {
    type: Number,
    default: 0,
  },
  first_amount: {
    type: Number,
    default: 0,
  },
  first_bottom_약코드: {
    type: String,
    default: "",
  },
  first_bottom_GB코드: {
    type: String,
    default: "",
  },
  first_bottom_BRCH코드: {
    type: String,
    default: "",
  },
  first_bottom_GBRCH코드: {
    type: String,
    default: "",
  },

  second_bottom_mednm: {
    type: String,
    default: "",
  },
  second_bottom_payitemnm: {
    type: String,
    default: "",
  },
  second_bottom_badge: {
    type: Number,
    default: 0,
  },
  second_bottom_약: {
    type: String,
    default: "",
  },
  second_bottom_규격: {
    type: String,
    default: "",
  },
  second_bottom_배송: {
    type: String,
    default: "",
  },
  second_bottom_재고: {
    type: Number,
    default: 0,
  },
  second_bottom_가격: {
    type: Number,
    default: 0,
  },
  second_bottom_주문수량: {
    type: Number,
    default: 0,
  },
  first_addBtn: {
    type: String,
    default: "담기",
  },
  second_bottom_memoTitle: {
    type: String,
    default: "Title",
  },
  second_backBtn: {
    type: String,
    default: "뒤로가기",
  },
  second_nextBtn: {
    type: String,
    default: "다음",
  },
  useAmount: {
    type: Boolean,
    default: true,
  },
  useTotal: {
    type: Boolean,
    default: true,
  },
  memo_dialog_title: {
    type: String,
    default: "TITLE",
  },
  totalcnt: {
    type: Number,
    default: 0,
  },
  totalamt: {
    type: Number,
    default: 0,
  },
  selecteditem: {
    type: Array,
  },
  flag: {
    type: String,
    default: "",
  },
  ordQty: {
    type: Boolean,
    default: false,
  },
});
</script>

<style lang="scss" scoped>
.first_tab {
  .first_grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    .first_item:nth-child(1) {
      @include flex_start;
      @include fw-7;
      grid-column: span 2;
      gap: 8px;
      span:nth-child(2) {
        max-width: 220px;
        display: block;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
      }
      span:nth-child(4) {
        color: $r1;
      }
    }
    .first_item:nth-child(2) {
      @include flex_start;
      grid-column: span 1;
      gap: 8px;
      span {
        @include fs-5;
      }
      span:nth-child(2),
      span:nth-child(3) {
        @include fw-7;
      }
    }
    .first_item:nth-child(3) {
      @include flex_end;
      grid-column: span 1;
      gap: 8px;
      span {
        @include fs-5;
      }
      span:nth-child(2) {
        color: $primary;
      }
      span:nth-child(2),
      span:nth-child(3) {
        @include fw-7;
      }
    }
  }
  .first_bottom {
    @include flex_between;
    gap: 8px;
    margin-top: 4px;
    text-align: right;
    .first_left {
      width: 100%;
      text-align: left;
      .first_total {
        div {
          @include flex_start;
          gap: 8px;
          span {
            @include fs-5;
            @include fw-7;
          }
        }
        div:nth-child(1) {
          span:nth-child(2) {
            color: $primary;
          }
        }
        div:nth-child(2) {
          span:nth-child(1) {
            color: $primary;
          }
        }
      }
      .first_amount {
        div {
          @include flex_start;
          gap: 8px;
          span {
            @include fw-7;
          }
          span:nth-child(2) {
            color: $primary;
          }
        }
      }
    }
  }
}
.second_tab {
  .second_text {
    text-align: center;
    span {
      @include fs-5;
    }
    .second_item:nth-child(1) {
      @include flex_center;
      @include fw-7;
      gap: 8px;
      .table_badge_wrap {
        .table_badge_red,
        .table_badge_blue {
          @include fs-5;
        }
      }
      span:nth-child(4) {
        color: $r1;
      }
    }
    .second_item:nth-child(2) {
      @include flex_center;
      span:nth-child(2) {
        @include fw-7;
        margin: 0 2px;
      }
    }
    .second_memo_item {
      @include flex_center;
      padding: 14px 0;
      gap: 8px;
      position: relative;
      img {
        position: absolute;
        top: 0;
        right: 0;
        width: 18px;
      }
      span {
        font-size: 16px;
      }
      span:nth-child(3) {
        @include fw-7;
      }
    }
  }
  .second_bottom {
    @include flex_center;
    gap: 8px;
    margin-top: 24px;
    text-align: right;
    .first_left {
      width: 100%;
      text-align: left;
    }
    > div:nth-child(1) {
      text-align: left;
    }
  }
}
</style>

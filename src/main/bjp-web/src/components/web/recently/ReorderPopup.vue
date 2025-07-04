<template>
  <div class="DialogComp_wrap">
    <!-- <div class="" @click="alert = true">
      <slot></slot>
    </div> -->

    <q-dialog v-model="isOpen">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="q-card_reorder_title">
            <p>재주문</p>
            <img src="assets/icons/x-solid.svg" @click="onClickClose" />
          </div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>재주문</span>
        </div>

        <div class="q-card_reorder_section_wrap">
          <q-table
            class="reorderTable"
            :rows="reorderRows"
            :columns="columns"
            row-key="ITEM_CD"
            table-header-class="table_header"
            dense
            v-model:selected="selectedReorder"
            virtual-scroll
            :rows-per-page-options="[0]"
            :virtual-scroll-sticky-size-start="0"
          >
            <template v-slot:body-cell-ITEM_NM_UNIT="props">
              <q-td :props="props" style="padding: 0">
                <div class="q-table_item" style="width: 100%">
                  <div class="q-table_badge_wrap">
                    <!-- <q-badge class="q-table_badge_red">
                      {{ props.row.MED_NM }}
                    </q-badge>
                    <q-badge class="q-table_badge_blue"> -->
                    <!-- 쿼리에서 이 컬럼만 SUBSTRING 안됨.. -->
                    <!-- {{ props.row.PAY_ITEM_NM.substring(0, 1) }}
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
                  <div
                    class="q-table_itemTitle td-prd_name"
                    style="width: 200px"
                  >
                    {{ props.value }}
                  </div>
                </div>
              </q-td>
            </template>

            <template v-slot:body-cell-MAKER_NM="props">
              <q-td :props="props" style="cursor: pointer">
                <div>
                  <div
                    class="q-table_itemTitleSub ellipsis"
                    style="width: 80px"
                  >
                    {{ props.value }}
                    <q-tooltip>{{ props.value }}</q-tooltip>
                  </div>
                </div>
              </q-td>
            </template>

            <template v-slot:body-cell-ORD_WP2_AMT="props">
              <q-td :props="props" style="padding: 0; text-align: right">
                <div style="font-size: 14px" class="td-price">
                  {{ props.value }}
                </div>
              </q-td>
            </template>

            <template v-slot:body-cell-AVAIL_STOCK="props">
              <q-td :props="props" style="padding: 0">
                <div class="q-table_stock_wrap" style="width: 50px">
                  <p v-if="props.row.AVAIL_STOCK > 0">
                    {{ props.value }}
                  </p>
                  <p class="q-table_stock" v-else>품절</p>
                </div>
              </q-td>
            </template>

            <template v-slot:body-cell-REQ_QTY="props">
              <q-td :props="props" style="padding: 0">
                <div class="q-table_EA_wrap td-qty">
                  <EAComp
                    @update-req-qty="(val) => updateReqQty(props.row, val)"
                  />
                </div>
              </q-td>
            </template>

            <template v-slot:body-cell-삭제="props">
              <q-td :props="props" style="padding: 0">
                <div class="q-table_del">
                  <img
                    src="assets/images/ico_delete.svg"
                    alt="x"
                    @click="onClickDelete(props)"
                  />
                </div>
              </q-td>
            </template>

            <template v-slot:bottom>
              <div class="price_wrap">
                <div class="price">
                  <p>
                    {{ total }}
                    <span>원</span>
                  </p>
                  <BtnComp_default btn-label="담기" @click="onClickAddBasket" />
                </div>
              </div>
            </template>
          </q-table>
        </div>
        <q-card-actions class="q-card_btn_wrap">
          <BtnComp_active
            class="btn-line default"
            btn-label="닫기"
            @click="onClickClose"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, computed, defineEmits, watch, inject, onMounted } from "vue";
import { useRouter } from "vue-router";
import { api } from "boot/axios";
import * as util from "src/support/util";
import _ from "lodash";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import EAComp from "src/components/web/common/EAComp.vue";
import { over } from "webstomp-client";

const router = useRouter();
const userInfo = util.getUserData();
const reorderRows = ref([]);
const selectedReorder = ref([]);
const emit = defineEmits(["close"]);
const bus = inject("bus");

const total = computed(() => {
  let cost = 0;

  reorderRows.value.forEach((item) => {
    cost += Number(item.ORD_WP2_AMT) * Number(item.REQ_QTY);
  });

  return util.getNumberFormat(cost);
});

const props = defineProps({
  isOpenPopup: { type: Boolean },
  data: { type: Array },
});

const isOpen = computed(() => {
  return props.isOpenPopup;
});

const basketData = ref({
  basketGbCd: "01",
  saveItemCd: "",
  saveItemGbCd: "",
  dlvBrchCd: "",
  saveItemQty: "",
  userId: userInfo.USER_ID,
  custCd: userInfo.CUST_CD,
});

//---------------------------- Function ------------------------------//

// 삭제
function onClickDelete(data) {
  reorderRows.value.splice(data.rowIndex, 1);

  selectedReorder.value = reorderRows.value;
}

// // 담기 (멀티)
// async function onClickAddBasket() {
//   //취소
//   let canCnt = 0;
//   let resCnt = 0;
//   let errorCnt = 0;
//   //오버카운트
//   let itemOver = 0;
//   let itemOverYn = "";
//   let bskCnt = 0;
//   //장바구니 카운트
//   const resA = await api.get("/main/basketCount", {
//             params: { custCd: userInfo.CUST_CD },
//           });

//   bskCnt = resA.ORD_COUNT;
//   console.log("장바구니 품목개수 res.ORD_COUNT: "+bskCnt);

//   if (selectedReorder.value.length === 0) {
//     alert("선택된 항목이 없습니다.");
//     return;
//   }

//   selectedReorder.value.forEach(async(m) => {

//     if (m.REQ_QTY === 0 || m.REQ_QTY === "") {
//       let errorMsg = `${m.ITEM_NM_UNIT} 품목의 수량을 선택해 주시기 바랍니다.`;

//       alert(errorMsg);
//       errorCnt++;
//     }

//     if (m.REQ_QTY > m.AVAIL_STOCK) {
//       // lint 정렬때문에  컨펌 메세지 틀어져서 이렇게 처리함
//       let errorMsg = `${m.ITEM_NM_UNIT} 품목의 재고가 부족합니다.\n`;
//           errorMsg += `주문 수량을 확인해 주시기 바랍니다.`;

//       alert(errorMsg);

//       errorCnt++;
//     }
//     // 에러 발생시 멈춤
//     if (errorCnt > 0) return;

//     const res = await api.post("/ord/getBasketDupOrd2", {
//           custCd: userInfo.CUST_CD,
//           basketGbCd: "01",
//           saveItemCd: m.ITEM_CD,
//           gDlvBrchCd: m.DLV_BRCH_CD,
//     });
//     console.log(m.ITEM_NM+" 중복쿼리 체크 res.ITEM_CNT: "+res.ITEM_CNT);

//     //0: 장바구니에 없음, 1: 장바구니에 있음
//     if (res.ITEM_CNT == 0) {

//     }else{

//     }

//   });

// }

async function onClickAddBasket() {
  //취소
  let canCnt = 0;
  let resCnt = 0;
  let errorCnt = 0;
  //오버카운트
  let itemOver = 0;
  let itemOverYn = "";
  let bskCnt = 0;
  //장바구니 카운트
  const resA = await api.get("/main/basketCount", {
    params: { custCd: userInfo.CUST_CD },
  });

  bskCnt = resA.ORD_COUNT;
  // console.log("장바구니 품목개수 초기 res.ORD_COUNT: "+bskCnt);

  reorderRows.value.forEach(async (m) => {
    if (m.REQ_QTY === 0 || m.REQ_QTY === "") {
      let errorMsg = `${m.ITEM_NM_UNIT} 품목의 수량을 선택해 주시기 바랍니다.`;

      alert(errorMsg);
      errorCnt++;
    }

    if (m.REQ_QTY > m.AVAIL_STOCK) {
      // lint 정렬때문에  컨펌 메세지 틀어져서 이렇게 처리함
      let errorMsg = `${m.ITEM_NM_UNIT} 품목의 재고가 부족합니다.\n`;
      errorMsg += `주문 수량을 확인해 주시기 바랍니다.`;

      alert(errorMsg);

      errorCnt++;
    }
    // 에러 발생시 멈춤
    if (errorCnt > 0) return;

    //중복쿼리 체크
    const res = await api.post("/ord/getBasketDupOrd2", {
      custCd: userInfo.CUST_CD,
      basketGbCd: "01",
      saveItemCd: m.ITEM_CD,
      gDlvBrchCd: m.DLV_BRCH_CD,
    });
    // console.log("중복쿼리 체크 res.ITEM_CNT: "+res.ITEM_CNT);
    //0: 장바구니에 없음, 1: 장바구니에 있음
    // console.log("장바구니 품목개수 중복 체크전 bskCnt: "+ bskCnt);
    if (res.ITEM_CNT == 0) {
      //장바구니 카운트
      // const res = await api.get("/main/basketCount", {
      //   params: { custCd: userInfo.CUST_CD },
      // });
      //  console.log("장바구니 품목개수 중복 체크후 bskCnt: "+ bskCnt);

      if (bskCnt >= 100) {
        bskCnt++;
        resCnt++;
        itemOver++;
        // itemOverYn = "Y";
        // console.log(m.ITEM_NM+"   100개 품목 resCnt: "+resCnt);
        // console.log(m.ITEM_NM+"   100개 품목 오버 itemOver: "+itemOver);
        // console.log(m.ITEM_NM+"   100개 품목 오버 시작 itemOverYn: "+itemOverYn);
        alert(
          "장바구니에 100개 품목(" +
            m.ITEM_NM +
            ")은 초과하여 담을 수 없습니다. 기존 품목 주문등록 후 다시 담기 바랍니다"
        );

        if (reorderRows.value.length != canCnt + itemOver) {
          // console.log(m.ITEM_NM+"   장바구니 100개 품목 체크 후 resCnt: " +resCnt);
          if (reorderRows.value.length == resCnt) {
            const confirmMsg =
              "선택한 품목에 대해 재주문을 위한 장바구니로 이동하겠습니까?";

            if (confirm(confirmMsg)) {
              await router.push("/comOrd");
            } else {
              emit("close");
            }
          }
          return;
        }
        return;
      } else {
        basketData.value.basketGbCd = "01";
        basketData.value.userId = userInfo.USER_ID;
        basketData.value.custCd = userInfo.CUST_CD;
        basketData.value.saveItemCd = m.ITEM_CD;
        basketData.value.saveItemGbCd = "01";
        basketData.value.saveItemQty = m.REQ_QTY;
        basketData.value.dlvBrchCd = userInfo.DLV_BRCH_CD;
        basketData.value.gdlvBrchCd = m.DLV_BRCH_CD;
        bskCnt++;
        resCnt++;
        // console.log(m.ITEM_NM+"  장바구니 신규 품목 추가 후 개수 bskCnt: "+ bskCnt);
        // console.log(m.ITEM_NM+"  장바구니 신규 품목 추가 후 개수 resCnt: "+ resCnt);
      }
    } else {
      const confirmMsg =
        "장바구니에 이미 담은 품목(" +
        m.ITEM_NM +
        ") 입니다. 수량 추가하시겠습니까?";
      if (!confirm(confirmMsg)) {
        resCnt++;
        canCnt++;
        // console.log(m.ITEM_QTY+"   수량취소 resCnt: "+resCnt);
        // console.log(m.ITEM_QTY+"   수량취소 canCnt: "+resCnt);
        return;
      }

      basketData.value.basketGbCd = "01";
      basketData.value.userId = userInfo.USER_ID;
      basketData.value.custCd = userInfo.CUST_CD;
      basketData.value.saveItemCd = m.ITEM_CD;
      basketData.value.saveItemGbCd = "01";
      basketData.value.saveItemQty = m.REQ_QTY;
      basketData.value.dlvBrchCd = userInfo.DLV_BRCH_CD;
      basketData.value.gdlvBrchCd = m.DLV_BRCH_CD;
      resCnt++;
      // console.log(m.ITEM_NM+"   장바구니 품목 수량 추가resCnt: " +resCnt );

      // 선택한 품목이 중복된 경우 마지막에 알림메세지를 제공하기 위해 초기화
      itemOverYn = "";
    }

    // console.log(m.ITEM_NM+"   장바구니 담기 로직 전 resCnt: "+resCnt);
    await api.post("/recently/addBasket2", basketData.value);
    await bus.emit("refreshHeaderBasket");
    //resCnt += result;

    // console.log(m.ITEM_NM+"   장바구니 100개 품목 체크  itemOverYn: "+itemOverYn);
    if (itemOverYn != "Y") {
      // console.log(m.ITEM_NM+"   장바구니 담기 로직 이후 resCnt: "+resCnt);
      // console.log("reorderRows.value.length: " + reorderRows.value.length);
      if (reorderRows.value.length == resCnt) {
        const confirmMsg =
          "선택한 품목에 대해 재주문을 위한 장바구니로 이동하겠습니까?";

        if (confirm(confirmMsg)) {
          itemOverYn = "Y";
          await router.push("/comOrd");
        } else {
          emit("close");
        }
      }
    }
  });
}

// 팝업 닫기
function onClickClose() {
  emit("close");
}

function updateReqQty(row, val) {
  row.REQ_QTY = util.getNumberFormat2(val);
}

watch(isOpen, () => {
  if (isOpen.value) {
    reorderRows.value = _.cloneDeep(props.data);
  } else {
    reorderRows.value = [];
    selectedReorder.value = [];
  }
});

const basketQty = ref("");
onMounted(async () => {
  bus.on("basketC", (data) => {
    basketQty.value = data;
  });
});

//-------------------------- Grid Setting ----------------------------//

const columns = [
  {
    name: "ITEM_NM_UNIT",
    label: "품목 및 규격",
    field: "ITEM_NM_UNIT",
    align: "left",
    sortable: true,
  },
  { name: "MAKER_NM", label: "제약사", field: "MAKER_NM", align: "left" },
  {
    name: "ORD_WP2_AMT",
    label: "단가",
    field: "ORD_WP2_AMT",
    align: "right",
    format: (val) => util.getNumberFormat(val),
  },
  {
    name: "AVAIL_STOCK",
    label: "현재고",
    field: "AVAIL_STOCK",
    align: "center",
    format: (val) => util.getNumberFormat(val),
  },
  { name: "REQ_QTY", label: "주문수량", field: "REQ_QTY", align: "center" },
  { name: "삭제", label: "삭제", field: "삭제", align: "center" },
];
</script>

<style lang="scss" scoped>
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  // border-bottom: 1px solid $line-300;
  border-bottom: none;
}
.q-card_reorder_title {
  @include flex_between;
  padding: 0 0 16px 0;
  border-bottom: solid 1px #999 !important;
  p {
    @include fw-7;
  }
  img {
    cursor: pointer;
  }
}

.q-card_reorder_section_wrap {
  // padding: 0 16px 16px;
}

.q-card_btn_wrap {
  @include flex_end;
  margin: 16px 0 0 0;
  padding: 0 !important;
  div {
    @include flex_end;
    border-radius: 2.4px;

    gap: 8px;
  }
}

.reorderTable {
  height: 304px;
  width: 650px;
  overflow-y: auto;
  .q-table_item {
    margin-left: 6px !important;
  }

  .price_wrap {
    @include flex_end;
    width: 100%;
    .price {
      @include flex_center;
      gap: 8px;
      // padding: 6px 8px 6px 28px !important;
      padding: 0 0 6px 28px !important;
      // background-color: $b5;
      p {
        @include fs-3;
        @include fw-7;
        color: $b3;
        span {
          font-size: 16px;
          color: $b;
          margin-left: 2px;
        }
      }
    }
  }
  // .q-table .q-virtual-scroll__content tr th:nth-child(1),
  // .q-table .q-virtual-scroll__content tr td:nth-child(1) {
  //   padding: 0 !important;
  //   background-color: #000;
  // }
}
</style>

<template>
  <div class="Bottom_wrap">
    <q-dialog
      :model-value="dialog_bottom_model"
      @update:model-value="$emit('update:dialog_bottom_model', $event)"

      no-backdrop-dismiss
      seamless


      position="bottom"
    >
      <div
        class="BottomDialog_wrap"
        :class="BottomDialogStyle"
        style="margin-bottom: 56px"
      >
        <div class="BottomDialog_close_btn_wrap" v-if="bottom_layout === false">
          <q-btn flat dense size="xs" v-close-popup>
            <img src="/public/assets/icons/bars-solid-close.svg" alt="icon" />
          </q-btn>
        </div>
        <q-tab-panels v-model="dialog_bottom_tab" animated>
          <q-tab-panel name="first_tab" class="first_tab">
            <div class="first_grid">
              <div class="first_item">
                <div class="table_badge_wrap">
                  <template v-if="first_bottom_badge === 1 || first_bottom_badge === 3">
                    <q-badge class="table_badge_red">전</q-badge>
                  </template>
                  <!-- <template v-else-if="first_bottom_badge === 0"></template> -->
                  <template v-else>
                    <q-badge class="table_badge_red">일</q-badge>
                  </template>

                  <template v-if="first_bottom_badge === 1 || first_bottom_badge === 2">
                    <q-badge class="table_badge_blue">급</q-badge>
                  </template>
                  <!-- <template v-else-if="first_bottom_badge === 0"></template> -->
                  <template v-else>
                    <q-badge class="table_badge_blue">비</q-badge>
                  </template>
                </div>
                <span>{{ first_bottom_약 }}</span>
                <span>{{ first_bottom_규격 }}</span>
                <span>{{ first_bottom_배송 === "G" ? '광역' : '' }}</span>
              </div>
              <div class="first_item">
                <template v-if="first_bottom_재고 > 0" >
                  <span>재고</span>
                  <span>{{ first_bottom_재고 }}</span>
                  <span>개</span>
                </template>
                <template v-else>
                  <span></span>
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
                    <span>3</span>
                    <span>건</span>
                  </div>
                  <div class="">
                    <span>123,456,789</span>
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
                    v-model="EAcount"
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
                <div class="table_badge_wrap">
                  <template v-if="second_bottom_badge === 1 || second_bottom_badge === 3">
                    <q-badge class="table_badge_red">전</q-badge>
                  </template>
                  <template v-else-if="second_bottom_badge === 0"><!--none--></template>
                  <template v-else>
                    <q-badge class="table_badge_red">일</q-badge>
                  </template>

                  <template v-if="second_bottom_badge === 1 || second_bottom_badge === 2">
                    <q-badge class="table_badge_blue">급</q-badge>
                  </template>
                  <template v-else-if="second_bottom_badge === 0"><!--none--></template>
                  <template v-else>
                    <q-badge class="table_badge_blue">비</q-badge>
                  </template>
                </div>
                <span>{{ second_bottom_약 }}</span>
                <span>{{ second_bottom_규격 }}</span>
                <span>{{ second_bottom_배송 }}</span>
              </div>
              <div class="second_item" v-if="second_bottom_주문수량 > 0">
                <span>주문 수량</span>
                <span>{{ EAcount }}개,</span>
                <span>장바구니에 담겼습니다.</span>
              </div>
              <div class="second_memo_item" v-if="bottom_layout === true">
                <img
                  src="/public/assets/icons/arrow-back-black.svg"
                  alt="icon"
                  @click="change_tab('first_tab')"
                />
                <span>{{ second_bottom_memoTitle }}에 메모를</span>
                <span>추가</span>
                <span>하시겠습니까?</span>
              </div>
            </div>
            <div class="second_bottom" v-if="bottom_layout === false">
              <BtnComp_mobile
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
                @click="$router.push({ path: '/mobile/mainCart'})"
              />
            </div>
            <div class="second_bottom" v-if="bottom_layout === true">
              <BtnComp_mobile
                :buttonText="second_backBtn"
                btnStyle="white"
                btnSize="md"
                @click="dialog_메모 = true"
                style="width: 100%"
              />
                <DialogLayout_mobile
                  v-model:dialog_model_prop="dialog_메모"
                  :dialog_title="memo_dialog_title"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <TextAreaComp_mobile
                      submit="메모등록"
                    />
                  </div>
                </DialogLayout_mobile>
              <BtnComp_mobile
                :buttonText="second_nextBtn"
                btnStyle="default"
                btnSize="md"
                style="width: 100%"
                @click="orderAlert"
              />
            </div>
          </q-tab-panel>
        </q-tab-panels>
      </div>
    </q-dialog>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import BtnComp_mobile from "src/components/__commonComp/btnComp/BtnComp_mobile.vue";
import EAInputComp_mobile from "src/components/__commonComp/inputComp/EAInputComp_mobile.vue";
import DialogLayout_mobile from "src/layouts/DialogLayouts/DialogLayout_mobile.vue";
import TextAreaComp_mobile from "src/components/DialogComp/__commonDialogComp/TextAreaComp_mobile.vue";

const EAcount = ref(0);

const dialog_메모 = ref(false);
const dialog_full = ref(true);

const dialog_bottom_tab = ref("first_tab");

//************************************************************ */

function orderAlert() {
  alert("주문등록 api로 연결")
}

async function change_tab(tabName) {
  dialog_bottom_tab.value = tabName;


  await onClickAddBasket()
};

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

// 담기 클릭 : 값을 하드코딩하니 저장되는거 확인 함
async function onClickAddBasket() {
  basketData.value.saveItemCd = props.first_bottom_약코드;
  basketData.value.saveItemGbCd = props.first_bottom_GB코드;
  basketData.value.dlvBrchCd = props.first_bottom_BRCH코드;
  basketData.value.saveItemQty = EAcount ;

  // basketData.value.saveItemQty = row.REQ_QTY === 0 ? 1 : row.REQ_QTY;

  try {
    await api.post("/ord/addBasket", basketData.value);

    // await router.push({ path: "/" });

  } catch (error) {
    alert(error.response.data);
  }
}










/////////////////////////////////////////////////////////////////////


const emits = defineEmits(["update:dialog_bottom_model"]);


const props = defineProps({
  dialog_bottom_model: {
    type: Boolean,
    default: false
  },
  bottom_layout: {
    type: Boolean,
  },
  BottomDialogStyle: {
    type: String,
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
      gap: 4px;
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
      gap: 4px;
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
      gap: 4px;
      span {
        @include fs-5;
      }
      span:nth-child(2) {
        color: $b3;
      }
      span:nth-child(2),
      span:nth-child(3) {
        @include fw-7;
      }
    }
  }
  .first_bottom {
    @include flex_center;
    gap: 8px;
    margin-top: 4px;
    .first_left {
      width: 100%;
      .first_total {
        div {
          @include flex_start;
          gap: 4px;
          span {
            @include fs-5;
            @include fw-7;
          }
        }
        div:nth-child(1) {
          span:nth-child(2) {
            color: $b3;
          }
        }
        div:nth-child(2) {
          span:nth-child(1) {
            color: $b3;
          }
        }
      }
      .first_amount {
        div {
          @include flex_start;
          gap: 4px;
          span {
            @include fw-7;
          }
          span:nth-child(2) {
            color: $b3;
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
      gap: 4px;
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
      gap: 4px;
      position: relative;
      img {
        position: absolute;
        top: 0;
        right: 0;
        width: 18px;
      }
      span:nth-child(3) {
        @include fw-7;
      }
    }
  }
  .second_bottom {
    @include flex_center;
    gap: 8px;
    margin-top: 4px;
    .first_left {
      width: 100%;
    }
  }
}


</style>

<template>
  <div class="">
    <q-table
      class=""
      row-key="ITEM_CD"
      :rows="rows"
      flat
      hide-header
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      style="margin-bottom: 160px"
    >
      <template v-slot:top>
        <div class="border-top" style="margin-top: 8px"></div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <!-- <div
              class="table_item table_item_mobile"
              @click="props.row.dialog_bottom_model = true"
            > -->
            <div
              class="table_item table_item_mobile"
              @click="onClickItemNm(props.row)"
              style="gap: 7px"
            >
              <q-checkbox
                class="table_checkbox_gray"
                v-model="table_chk"
                checked-icon="img:assets/icons/checkbox-outline.svg"
                unchecked-icon="img:assets/icons/checkbox-blank-outline.svg"
                :val="props.row"
                dense
              />
              <div class="table_badge_wrap">
                <!-- <q-badge class="table_badge_red">
                  {{ props.row.MED_NM }}
                </q-badge>
                <q-badge class="table_badge_blue">
                  {{ props.row.PAY_ITEM_NM.substring(0, 1) }}
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
                {{ props.row.ITEM_NM_UNIT }}
                <div
                  class="table_widearea bdg-line red"
                  v-if="props.row.G_DLV_YN === 'Y'"
                >
                  <span>광역</span>
                </div>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <q-checkbox
                  class="table_checkbox_heart"
                  :checked-icon="mdiHeart"
                  :unchecked-icon="mdiHeartOutline"
                  v-model="props.row.FAVORITE_YN"
                  true-value="Y"
                  false-value="N"
                  dense
                  @update:model-value="(val) => onCheckInterest(props.row, val)"
                />
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile"
                >{{ util.getNumberFormat(props.row.ITEM_QTY) }}개</q-td
              >
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">단가</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span>{{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}</span>
                <span>원</span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <BtnComp_mobile
                  buttonText="출고이력"
                  btnStyle="default"
                  class="btn-line default"
                  @click="props.row.dialog_record = true"
                />
                <DialogLayout_mobile
                  dialog_title="제품 출고이력 조회"
                  v-model:dialog_model_prop="props.row.dialog_record"
                >
                  <div class="">
                    <MyRecentDetailRecordComp_mobile
                      :itemcd="props.row.ITEM_CD"
                      v-model="isActiveBtn"
                    >
                      <!-- <div class="table_badge_wrap">
                        <q-badge class="table_badge_red">
                          {{ props.row.MED_NM }}
                        </q-badge>
                        <q-badge class="table_badge_blue">
                          {{ props.row.PAY_ITEM_NM.substring(0, 1) }}
                        </q-badge>
                      </div> -->

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
                          :class="
                            props.row.PAY_ITEM_NM === '비' ? 'nonpay' : 'pay'
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
                      {{ props.row.ITEM_NM_UNIT }}
                    </MyRecentDetailRecordComp_mobile>
                  </div>
                </DialogLayout_mobile>
                <BtnComp_mobile
                  buttonText="제품정보"
                  btnStyle="icon"
                  btnAlign="between"
                  :btnIcon="props.row.expand ? btnIconUp2 : btnIconDown2"
                  @click="props.row.expand = !props.row.expand"
                />
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">금액</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span :class="{ 'red-400': props.row.TOTAL_AMT < 0 }">
                  {{ util.getNumberFormat(props.row.TOTAL_AMT) }}
                </span>
                <span>원</span>
              </q-td>
            </div>
          </div>
        </q-tr>

        <!-- expand -->
        <q-tr v-show="props.row.expand" :props="props">
          <q-markup-table separator="none" flat dense>
            <tbody>
              <tr>
                <div class="table_expand_wrap">
                  <div class="table_expand_item">
                    <td>{{ props.row.ITEM_NM_UNIT }}</td>
                    <!-- <td>제품정보</td>
                    <div class="table_expand_item_sub">
                      <q-badge class="table_badge_red">
                        {{ props.row.MED_NM }}
                      </q-badge>
                      <q-badge class="table_badge_blue">
                        {{ props.row.PAY_ITEM_NM.substring(0, 1) }}
                      </q-badge>
                      <q-td>{{ props.row.ITEM_NM_UNIT }}</q-td>
                    </div> -->
                  </div>
                  <div class="table_expand_item">
                    <td>보험코드</td>
                    <td>{{ props.row.BOHUM_CD }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>반품</td>
                    <td
                      v-if="props.row.WEB_BACK_USE_YN === 'Y'"
                      style="color: #f5536d"
                    >
                      불가
                    </td>
                    <td
                      v-if="props.row.TAKEBACK_LIMIT_YN === 'Y'"
                      style="color: blue"
                    >
                      제한
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>반품조건</td>
                    <td>{{ props.row.WEB_BACK_MEMO }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>박스입수</td>
                    <td>
                      <span>(소)</span>
                      <span>{{
                        util.getNumberFormat(props.row.SMALLBOX_QTY)
                      }}</span>
                      <span>(중)</span>
                      <span>{{
                        util.getNumberFormat(props.row.MIDBOX_QTY)
                      }}</span>
                      <span>(대)</span>
                      <span>{{
                        util.getNumberFormat(props.row.LARGEBOX_QTY)
                      }}</span>
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>최소주문</td>
                    <td>
                      {{
                        props.row.MIN_ORD_QTY == 1
                          ? ""
                          : util.getNumberFormat(props.row.MIN_ORD_QTY)
                      }}
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>최대주문</td>
                    <td>
                      {{
                        props.row.MAX_ORD_QTY == 0 ||
                        props.row.MAX_ORD_QTY == null
                          ? ""
                          : util.getNumberFormat(props.row.MAX_ORD_QTY)
                      }}
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>포장형태</td>
                    <td>
                      {{ props.row.PACK_NM }}
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>입고일정</td>
                    <td>{{ props.row.SOLDOUT_NOTE }}</td>
                  </div>
                  <div
                    class="table_expand_close"
                    @click="props.row.expand = !props.row.expand"
                  >
                    <span>제품정보 닫기</span>
                    <span
                      ><img src="assets/images/arrw_up.svg" alt="icon"
                    /></span>
                  </div>
                </div>
              </tr>
            </tbody>
          </q-markup-table>
        </q-tr>

        <BottomComp_mobile_order2
          v-model:dialog_bottom_model="props.row.dialog_bottom_model"
          BottomDialogStyle="fixed_bottom"
          :first_bottom_mednm="props.row.MED_NM"
          :first_bottom_payitemnm="props.row.PAY_ITEM_NM.substring(0, 1)"
          :first_bottom_약="props.row.ITEM_NM"
          :first_bottom_규격="props.row.UNIT"
          :first_bottom_배송="props.row.배송"
          :first_bottom_재고="props.row.AVAIL_STOCK"
          :first_bottom_가격="props.row.ORD_WP2_AMT"
          :first_bottom_주문수량="0"
          :first_bottom_약코드="props.row.ITEM_CD"
          :first_bottom_GB코드="props.row.ITEM_GB_CD"
          :first_bottom_BRCH코드="props.row.DLV_BRCH_CD"
          :second_bottom_약="props.row.ITEM_NM"
          :second_bottom_규격="props.row.UNIT"
          :second_bottom_재고="props.row.AVAIL_STOCK"
          :second_bottom_가격="props.row.ORD_WP2_AMT"
          first_addBtn="재주문 하기"
          second_backBtn="계속 주문"
          second_nextBtn="장바구니 이동"
          :useTotal="false"
          :useAmount="false"
          :bottom_layout_close="false"
          flag="reOrder"
          :ordQty="true"
        />
      </template>
    </q-table>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, watch, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { mdiCheck, mdiHeart, mdiHeartOutline } from "@mdi/js";

import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BottomComp_mobile from "src/components/mobile/DialogComp/BottomDialogComp/BottomComp_mobile.vue";
import BottomComp_mobile_order2 from "src/components/mobile/DialogComp/BottomDialogComp/BottomComp_mobile_order2.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import InputComp_mobile from "src/components/mobile/__commonComp/inputComp/InputComp_mobile.vue";
import FilterComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterComp_mobile.vue";
import BtnGroupComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnGroupComp_mobile.vue";
import MyRecentDetailRecordComp_mobile from "src/components/mobile/DialogComp/4.myDialogComp/MyRecentDetailRecordComp_mobile.vue";
import StockComp_mobile from "src/components/mobile/DialogComp/__commonDialogComp/StockComp_mobile.vue";

//필터표시
const isActiveBtn = ref(null);

const userInfo = util.getUserData();
const dialog_stock_model = ref(false);
const dialog_bottom_model = ref(false);
const dialog_record = ref(false);

const table_chk = ref([]);
const bus = inject("bus");

// const btnIcon = ref("img:assets/icons/sort-down-white.svg");

const btnIconUp2 = ref("img:assets/images/arrw_up.svg");
const btnIconDown2 = ref("img:assets/images/arrw_dwn.svg");

const rows = ref([]);

const rowval = () => {
  return rows.value.length;
};

const props = defineProps({
  tblNm: { type: String, default: "" },
  sn: { type: Number },
});

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  tblNm: props.tblNm,
  sn: props.sn,
});

//---------------------------- Function ------------------------------//
//체크된 값 보내기
watch(table_chk, () => {
  bus.emit("recloadTotal", rows.value);
  bus.emit("recchkTotal", table_chk);
});
async function fetchItemList() {
  const res = await api.get("/recently/searchDtls", {
    params: searchParam.value,
  });
  rows.value = res;
  rows.value[0].dialog_bottom_model = true;
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

    // await fetchBasketList();
  } catch (error) {
    alert(error.response.data);
  }
}

defineExpose({ fetchItemList });

function onClickItemNm(row) {
  rows.value.forEach((m) => {
    m.dialog_bottom_model = false;
  });

  row.dialog_bottom_model = true;
}
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  padding: 8px 0 !important;
  gap: 8px;
  .table_item:nth-child(1) {
    @include flex_start;
    @include fw-7;
    grid-column: span 9;
    gap: 4px;
    height: auto !important;
    td {
      // font-size: 15.5px !important;
      height: auto !important;
      // overflow: hidden;
      // text-overflow: ellipsis;
      display: block;
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
    grid-column: span 4;
    td {
      @include flex_start;
      // @include fs-6;
      font-size: 12px !important;
      color: #666666;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 2;
    td {
      @include flex_end;
      @include fs-5;
      color: #666666;
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 3;
    td {
      @include flex_end;
      @include fs-5;
      gap: 4px;
      span {
        @include fs-5;
      }
    }
  }
  .table_item:nth-child(7) {
    grid-column: span 6;
    td {
      @include flex_start;
      gap: 8px 4px;
    }
  }
  .table_item:nth-child(8) {
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
    }
  }
  .table_item:nth-child(9) {
    grid-column: span 3;
    td {
      @include flex_end;
      gap: 4px;
      span {
        @include fs-5;
      }
      .text-red {
        color: $red-400;
      }
    }
  }
}

// .table_expand_wrap {
//   .table_expand_item {
//     td {
//       @include fs-5;
//     }
//     td:nth-child(1) {
//       @include fw-7;
//       // width: 70px;
//     }
//   }
//   .table_expand_item:nth-child(1) {
//     @include flex_start;
//     @include border-default;
//     padding-bottom: 4px !important;
//     td {
//       @include flex_start;
//       @include fw-7;
//       font-size: 16px;
//       white-space: normal;
//     }
//     .table_expand_item_sub {
//       @include flex_start;
//       @include fw-7;
//       grid-column: span 9;
//       gap: 4px;
//       max-width: 220px;
//       td {
//         display: block;
//         overflow: hidden;
//         text-overflow: ellipsis;
//       }
//     }
//   }
//   .table_expand_item:nth-child(2) {
//   }
//   .table_expand_item:nth-child(3) {
//     td:nth-child(2) {
//       @include fw-7;
//       color: $b4;
//     }
//   }
//   .table_expand_item:nth-child(4) {
//   }
//   .table_expand_item:nth-child(5) {
//     span:nth-child(odd) {
//       @include fs-5;
//       @include fw-7;
//     }
//     span:nth-child(even) {
//       @include fs-5;
//       margin-right: 8px;
//     }
//   }
//   .table_expand_item:nth-child(6) {
//   }
// }

// .table_widearea {
//   span {
//     @include fs-5;
//     @include fw-7;
//     border: 1px solid $red-400;
//     border-radius: 20px;
//     padding: 4px 4px;
//     color: $red-400;
//     font-size: 10px;
//   }
// }
</style>

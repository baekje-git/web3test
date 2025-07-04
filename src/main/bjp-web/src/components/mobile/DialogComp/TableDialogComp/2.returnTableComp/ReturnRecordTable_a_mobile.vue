<template>
  <div class="">
    <q-table
      class=""
      row-key=""
      :rows="top_rows"
      flat
      hide-header
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
    >
      <template v-slot:loading>
        <q-inner-loading class="loading" :showing="loading" color="primary" />
      </template>
      <template v-slot:top>
        <div class="border-top"></div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap top">
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>
                  {{
                    moment(props.row.APRV_DT, "YYYY-MM-DD HH:mm:ss").format(
                      "YYYY-MM-DD"
                    )
                  }}
                </span>
              </q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>유효번호</span>
              </q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>
                  {{
                    props.row.EXP_DT !== ""
                      ? moment(props.row.EXP_DT, "YYYY-MM-DD HH:mm:ss").format(
                          "YYYY-MM-DD"
                        )
                      : ""
                  }}
                </span>
              </q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>출하수량</span>
                <span :class="{ 'red-400': props.row.ITEM_QTY < 0 }">
                  {{ props.row.ITEM_QTY < 0 ? "-" : ""
                  }}{{ util.getNumberFormat(Math.abs(props.row.ITEM_QTY)) }}
                </span>
                <span>개</span>
              </q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>제조번호</span>
              </q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>{{ props.row.PROD_NO }}</span>
              </q-td>
            </div>
            <!-- <div class="table_item">
              <q-td class="table-td_mobile">
                <span>반품가능수량</span>
                <span>{{ util.getNumberFormat(props.row.LIMIT_QTY) }}</span>
                <span>개</span>
              </q-td>
            </div>
            <div
              v-if="
                props.row.LIMIT_QTY > 0 &&
                props.row.ITEM_QTY > 0 &&
                medCd === '02' &&
                props.row.PROD_NO !== '' &&
                props.row.EXP_DT !== ''
              "
              class="table_item"
            >
              <EAInputComp_mobile
                inputStyle="EA-xs-return"
                style="width: 64px"
                v-model:model-value="props.row.REQ_QTY"
              />
              <BtnComp_mobile
                buttonText="선택"
                btnStyle="default"
                style="width: 40px"
                @click="onClickReqQtySelect(props)"
              />
              <BottomComp_mobile
                v-model:dialog_bottom_model="dialog_record"
                BottomDialogStyle="fixed_bottom"
                :second_bottom_mednm="props.row.MED_NM"
                :second_bottom_payitemnm="props.row.PAY_ITEM_NM"
                :second_bottom_약="props.row.ITEM_NM"
                :second_bottom_규격="props.row.UNIT"
                :second_bottom_배송="props.row.배송"
                :second_bottom_재고="props.row.재고"
                :second_bottom_가격="props.row.가격"
                :second_bottom_주문수량="totalCnt"
                :bottom_layout="false"
                :useAmount="true"
                :useTotal="false"
                first_addBtn="반품바구니 담기"
                second_backBtn="계속 반품"
                second_nextBtn="반품 이동"
                :first_amount="totalCnt"
                flag="addReturnBasket"
                :selecteditem="returnData"
                :ordQty="true"
              />
            </div> -->
          </div>
        </q-tr>
      </template>
    </q-table>

    <!-- <q-table
      class=""
      row-key=""
      :rows="bottom_rows"
      flat
      hide-header
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      style="height: 197px"
    >
      <template v-slot:top>
        <div class="border-top"></div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap top">
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>{{ props.row.주문일자 }}</span>
              </q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>유효</span><span>{{ props.row.유효기간 }}</span>
              </q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>제조번호</span>
                <span>{{ props.row.제조번호 }}</span>
              </q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>출하수량</span>
                <span :class="{ 'red-400': props.row.출하수량 < 0 }">
                  {{ props.row.출하수량 < 0 ? "-" : ""
                  }}{{ Math.abs(props.row.출하수량) }}
                </span>
                <span>개</span>
              </q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>반품가능수량</span><span>{{ props.row.반품수량 }}</span>
                <span>개</span>
              </q-td>
            </div>
            <div class="table_item"></div>
          </div>
        </q-tr>
      </template>
    </q-table> -->
  </div>
</template>

<script setup>
import { ref, watch, onMounted, computed } from "vue";
import { api } from "boot/axios";
import _ from "lodash";
import moment from "moment";
import * as util from "src/support/util";

import EAInputComp_mobile from "src/components/mobile/__commonComp/inputComp/EAInputComp_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import BottomLayout_mobile from "src/layouts/mobile/__BottomLayout_mobile.vue";
import BottomComp_mobile from "src/components/mobile/DialogComp/BottomDialogComp/BottomComp_mobile.vue";

//로딩
const loading = ref(false);

const props = defineProps({
  searchParam: Map,
  returnData: Array,
  medCd: String,
});

defineExpose({
  fetchHistoryList,
});

const dialog_record = ref(false);
const searchHistoryParam = ref();

const EAcount1 = ref(0);
const EAcount2 = ref(0);
const totalCnt = ref(0);

async function fetchHistoryList() {
  loading.value = true;
  const res = await api.get("/bakRcpt/history", {
    params: searchHistoryParam.value,
  });

  top_rows.value = res;

  loading.value = false;
}

// 선택 버튼 클릭
function onClickReqQtySelect(props) {
  if (props.row.REQ_QTY === 0) {
    alert("수량을 선택해 주시기 바랍니다.");
    return;
  }

  if (props.row.REQ_QTY > props.row.LIMIT_QTY) {
    alert("반품 가능한 수량을 초과할 수 없습니다.");
    return;
  }

  totalCnt.value += props.row.REQ_QTY;
  dialog_record.value = true;
}

watch(props, () => {
  searchHistoryParam.value = _.cloneDeep(props.searchParam);
});

onMounted(() => {
  searchHistoryParam.value = _.cloneDeep(props.searchParam);
});

const top_rows = ref([]);

// const bottom_rows = ref([
//   {
//     주문일자: "2023.01.05",
//     유효기간: "",
//     제조번호: "",
//     출하수량: -10,
//     반품수량: 0,
//   },
//   {
//     주문일자: "2023.01.05",
//     유효기간: "",
//     제조번호: "",
//     출하수량: 60,
//     반품수량: 0,
//   },
// ]);
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  padding: 6px 0 !important;
  gap: 0 4px;
  .table_item {
    td {
      height: 26px !important;
    }
  }
  .table_item:nth-child(1) {
    grid-column: span 1;
    max-width: 50px;
    td {
      @include flex_start;
      max-width: 50px;
      span {
        @include fw-7;
      }
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 1;
    td {
      @include flex_center;
      gap: 4px;
      span {
        @include fs-5;
        color: #666666;
      }
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 1;
    td {
      @include flex_start;
      gap: 4px;
      span {
        @include fs-5;
        color: #666666;
      }
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 1;
    td {
      @include flex_start;
      span {
        @include fs-5;
      }
      span:nth-child(2) {
        margin-left: 2px;
      }
      .text-red {
        color: $r1;
      }
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 1;
    td {
      @include flex_center;
      gap: 4px;
      span {
        @include fs-5;
        color: #666666;
      }
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 1;
    td {
      @include flex_start;
      gap: 4px;
      span {
        @include fs-5;
        color: #666666;
      }
    }
  }
}
</style>

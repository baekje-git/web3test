<template>
  <div>
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
      :loading="loading"
    >
      <template v-slot:loading>
        <q-inner-loading
          class="loading custom"
          :showing="loading"
          color="primary"
        />
      </template>
      <template v-slot:top>
        <div class="flex_between" style="width: 100%">
          <div class="">
            <div class="table_totalVal">
              <span class="table_total_Text">총</span>
              <span class="table_total_Val">{{ rows.length }}</span>
              <span class="table_total_Text">건이 조회되었습니다.</span>
            </div>
            <div class="table_totalVal">
              <span class="table_total_Text">
                주문금액(소계) {{ util.getNumberFormat(totalAmt) }} 원
              </span>
            </div>
          </div>
          <div class="">
            <div class="table_totalVal" style="justify-content: end !important">
              <span class="table_total_Text">상세에서 품목별</span>
            </div>
            <div class="table_totalVal">
              <span class="blue-text bold-text"> 재주문 </span>
              <span class="table_total_Text"> 이 가능합니다. </span>
            </div>
          </div>
        </div>
        <div class="border-top"></div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item table_item_mobile">
              <!-- <div class="table_badge_wrap">
                <q-badge class="table_badge_red">전</q-badge>
                <q-badge class="table_badge_blue">비</q-badge>
              </div> -->
              <q-td class="table-td_mobile">
                {{ props.row.ITEM_NM_UNIT }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td
                v-if="props.row.STATS === '접수'"
                class="blue-400 table-td_mobile"
              >
                {{ props.row.STATS }}
              </q-td>
              <q-td
                v-else-if="props.row.STATS === '등록'"
                class="red-400 table-td_mobile"
              >
                {{ props.row.STATS }}
              </q-td>
              <q-td v-else class="black-text table-td_mobile">
                {{ props.row.STATS }}
              </q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">주문</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ props.row.ORDDTTM }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.주문시간 }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.GUBUN }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span>{{ util.getNumberFormat(props.row.ITEM_CNT) }}</span>
                <span>종</span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">금액</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td
                class="table-td_mobile"
                :style="props.row.TOTAL_AMT < 0 ? 'color: red' : ''"
              >
                {{ util.getNumberFormat(props.row.TOTAL_AMT) }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">승인</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ props.row.APRVDTTM !== "" ? props.row.APRVDTTM : "" }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.승인시간 }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.승인상태 }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <div>
                  <DeleteComp_mobile
                    delete_text1="최근주문"
                    delete_text2="주문 취소"
                    delete_btn="확인"
                    v-if="
                      props.row.GUBUN == '매출' && props.row.STATS == '등록'
                    "
                    :dataRow="props.row"
                    @delete-basket="(val) => delOrder(val)"
                  >
                    <BtnComp_mobile
                      buttonText="주문취소"
                      btnStyle="default"
                      style="width: 64px"
                    />
                  </DeleteComp_mobile>
                </div>
                <div>
                  <BtnComp_mobile
                    v-if="props.row.ORD_MEMO == ''"
                    buttonText="메모"
                    btnStyle="icon"
                    btnAlign="between"
                    :btnIcon="props.row.expand ? btnIconUp2 : btnIconDown2"
                    style="width: 64px"
                    @click="props.row.expand = !props.row.expand"
                    :is-disable="true"
                  />
                  <BtnComp_mobile
                    v-else
                    buttonText="메모"
                    btnStyle="icon"
                    btnAlign="between"
                    :btnIcon="props.row.expand ? btnIconUp2 : btnIconDown2"
                    style="width: 64px"
                    @click="props.row.expand = !props.row.expand"
                  />
                </div>
                <div>
                  <BtnComp_mobile
                    buttonText="상세"
                    btnStyle="default"
                    @click="props.row.dialog_dtl = true"
                    style="width: 64px"
                  />
                  <DialogLayout_mobile
                    dialog_title="주문 상세 내역 (재주문)"
                    v-model:dialog_model_prop="props.row.dialog_dtl"
                    :dialog_full="dialog_full"
                  >
                    <div class="">
                      <MyRecentDetailComp_mobile
                        :ordDt="props.row.ORD_DT"
                        :ordTm="props.row.ORD_TM"
                        :gubun="props.row.GUBUN"
                        :aprvDt="props.row.APRV_DT"
                        :aprvTm="props.row.APRV_TM"
                        :aprvNo="props.row.APRV_NO"
                        :isOpen="props.row.dialog_dtl"
                        :sn="props.row.SN"
                        :tblNm="props.row.TBL_NM"
                        :searchParamData="searchParam"
                        :ordDtTm="props.row.ORDDTTM"
                        :aprvDtTm="props.row.APRVDTTM"
                      />
                    </div>
                  </DialogLayout_mobile>
                </div>
              </q-td>
            </div>
          </div>
        </q-tr>

        <q-tr v-show="props.row.expand" :props="props">
          <div class="expand_table_wrap">
            <div class="expand_table_item">
              <q-td>
                {{
                  props.row.ORD_MEMO !== "" ? props.row.ORD_MEMO : "메모 없음"
                }}
              </q-td>
            </div>
            <!-- <div class="expand_table_item">
              <q-td @click="props.row.expand = !props.row.expand">
                <div class="">
                  <span>메모 닫기</span>
                  <span>
                    <img
                      src="/src/assets/icons/sort-down-solid-close.svg"
                      alt="icon"
                    />
                  </span>
                </div>
              </q-td>
            </div> -->
          </div>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>
<script setup>
import { ref, watch, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";

import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import MyRecentDetailComp_mobile from "src/components/mobile/DialogComp/4.myDialogComp/MyRecentDetailComp_mobile.vue";
import DeleteComp_mobile from "src/components/mobile/DialogComp/__commonDialogComp/DeleteComp_mobile.vue";

const expanded_model = ref(true);

const dialog_full = ref(true);
// const dialog_dtl = ref(false);

const dialog_MyPage = ref(true);
const expanded_btn = () => {
  dialog_MyPage.value = true;
};

const bus = inject("bus");

// const btnIcon = ref("img:assets/icons/sort-down-white.svg");

const btnIconUp2 = ref("img:assets/images/arrw_up.svg");
const btnIconDown2 = ref("img:assets/images/arrw_dwn.svg");

const rows = ref([]);

const totalAmt = ref(0);

const userInfo = util.getUserData();

const props = defineProps({
  recentData: Array,
  loading: {
    type: Boolean,
    default: false,
  },
  searchParamData: Array,
});
const searchParam = ref(props.searchParamData);
/////////////////////////////////////////////////////////////////////////////////////////////////

const delOrder = async (val) => {
  let row = val;

  try {
    Object.assign(row, { USER_ID: userInfo.USER_ID });

    await api.delete("/recently/cancelRcently", { params: row });

    util.showNotifyTop("취소되었습니다.");

    // await fetchItemList();
    await bus.emit("refSearch");
    await bus.emit("refItemList");
  } catch (error) {
    // alert(error.response.data);
    alert("오류가 발생했습니다.");
  }
};

watch(props, () => {
  rows.value = props.recentData;
  totalAmt.value = 0;
  if (rows.value.length > 0) {
    rows.value.forEach((m) => {
      totalAmt.value += m.TOTAL_AMT;
    });
  }
});
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  padding: 8px 0 !important;
  gap: 8px 2px;

  td {
    @include fs-6;
    span {
      @include fs-6;
    }
  }
  .table_item:nth-child(1) {
    @include flex_start;
    @include fw-7;
    grid-column: span 9;
    gap: 4px;
    td {
      // font-size: 16px !important;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
      @include fw-7;
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 1;
    td {
      @include flex_start;
      font-size: 12px !important;
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 2;
    td {
      @include flex_start;
      color: #666666;
      font-size: 12px !important;
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 2;
    td {
      @include flex_start;
      color: #666666;
      font-size: 12px !important;
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 1;
    td {
      @include flex_center;
      font-size: 12px !important;
    }
  }
  .table_item:nth-child(7) {
    grid-column: span 1;
    td {
      @include flex_center;
      color: #666666;
      font-size: 12px !important;
    }
  }
  .table_item:nth-child(8) {
    grid-column: span 1;
    td {
      @include flex_center;
      font-size: 12px !important;
    }
  }
  .table_item:nth-child(9) {
    grid-column: span 2;
    td {
      @include flex_end;
      color: #666666;
      font-size: 12px !important;
    }
  }
  .table_item:nth-child(10) {
    grid-column: span 1;
    td {
      @include flex_start;
      font-size: 12px !important;
    }
  }
  .table_item:nth-child(11) {
    grid-column: span 2;
    td {
      @include flex_start;
      color: #666666;
      font-size: 12px !important;
    }
  }
  .table_item:nth-child(12) {
    grid-column: span 2;
    td {
      @include flex_start;
      color: #666666;
      font-size: 12px !important;
    }
  }
  .table_item:nth-child(13) {
    grid-column: span 2;
    td {
      @include flex_center;
      font-size: 12px !important;
    }
  }
  .table_item:nth-child(14) {
    grid-column: span 10;
    td {
      @include flex_end;
      gap: 8px;
      font-size: 12px !important;
    }
  }
}

.expand_table_wrap {
  @include border-b;
  padding: 8px !important;
  .expand_table_item:nth-child(1) {
    td {
      white-space: normal;
    }
  }
  .expand_table_item:nth-child(2) {
    @include flex_center;
    margin-top: 16px;
    td {
      div {
        @include flex_center;
        gap: 8px;
        span {
          @include fs-5;
        }
      }
    }
  }
}
</style>

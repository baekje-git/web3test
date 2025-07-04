<template>
  <div>
    <ExpandLayout_mobile
      v-model:expanded_model="expanded_model"
      expand_title="최근 주문 내역"
      expand_btn="+ more"
      :expand_btnWidth="68"
      @expanded_btnClick="expanded_btn"
    >
      <DialogLayout_mobile
        dialog_title="최근 주문 내역"
        v-model:dialog_model_prop="dialog_MyPage"
      >
        <div class="">
          <MyRecentComp_mobile
            v-model:activeBtnModel="isActiveBtn"
            v-model:periodBtnModel="isPeriodBtn"
          />
        </div>
      </DialogLayout_mobile>

      <q-table
        class=""
        row-key="약"
        :rows="rows"
        flat
        hide-header
        hide-bottom
        virtual-scroll
        :rows-per-page-options="[0]"
        :virtual-scroll-sticky-size-start="0"
      >
        <template v-slot:body="props">
          <q-tr>
            <div class="table_wrap">
              <div class="table_item table_item_mobile">
                <!-- <div class="table_badge_wrap">
                  <q-badge class="table_badge_red">전</q-badge>
                  <q-badge class="table_badge_blue">비</q-badge>
                </div> -->
                <q-td class="table-td_mobile">
                  <p>
                    {{ props.row.ITEM_NM_UNIT }}
                  </p>
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
              <div class="table_item table_item_mobile">
                <q-td class="table-td_mobile">주문</q-td>
              </div>
              <div class="table_item table_item_mobile">
                <q-td class="table-td_mobile">
                  <!-- {{
                    props.row.ORD_DT !== ""
                      ? moment(props.row.ORD_DT).format("YYYY.MM.DD")
                      : "-"
                  }} -->
                  {{ props.row.ORDDTTM }}
                </q-td>
              </div>
              <div class="table_item table_item_mobile">
                <!-- <q-td class="table-td_mobile">{{ props.row.주문시간 }}</q-td> -->
              </div>
              <div class="table_item table_item_mobile">
                <q-td class="table-td_mobile">{{ props.row.GUBUN }}</q-td>
              </div>
              <div class="table_item table_item_mobile">
                <q-td class="table-td_mobile">
                  <span> {{ props.row.ITEM_CNT }} </span>
                  <span>종</span>
                </q-td>
              </div>
              <div class="table_item table_item_mobile">
                <q-td class="table-td_mobile">금액</q-td>
              </div>
              <div class="table_item table_item_mobile">
                <q-td
                  class="table-td_mobile"
                  :class="props.row.TOTAL_AMT < 0 ? 'red-400' : ''"
                >
                  {{ util.getNumberFormat(props.row.TOTAL_AMT) }}
                </q-td>
              </div>
              <div class="table_item table_item_mobile">
                <q-td class="table-td_mobile">승인</q-td>
              </div>
              <div class="table_item table_item_mobile">
                <q-td class="table-td_mobile">
                  <!-- {{
                    props.row.APRV_DT !== ""
                      ? moment(props.row.APRV_DT).format("YYYY.MM.DD")
                      : "-"
                  }} -->
                  {{ props.row.APRVDTTM }}
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
                        btnStyle="line_default"
                      />
                    </DeleteComp_mobile>
                  </div>
                  <div>
                    <!-- <BtnComp_mobile
                      v-if="props.row.ORD_MEMO == ''"
                      buttonText="메모"
                      btnStyle="icon"
                      btnAlign="between"
                      :btnIcon="props.row.expand ? btnIconUp2 : btnIconDown2"
                      @click="props.row.expand = !props.row.expand"
                      :is-disable="true"
                    />
                    <BtnComp_mobile
                      v-else
                      buttonText="메모"
                      btnStyle="icon"
                      btnAlign="between"
                      :btnIcon="props.row.expand ? btnIconUp2 : btnIconDown2"
                      @click="props.row.expand = !props.row.expand"
                    /> -->
                    <BtnComp_mobile
                      v-if="props.row.ORD_MEMO == ''"
                      buttonText="메모"
                      btnStyle="transparent-black"
                      @click="props.row.expand = !props.row.expand"
                      :is-disable="true"
                    />
                    <BtnComp_mobile
                      v-else
                      buttonText="메모"
                      btnStyle="transparent"
                      @click="props.row.expand = !props.row.expand"
                    />
                  </div>
                  <div>
                    <BtnComp_mobile
                      buttonText="상세"
                      btnStyle="line_default"
                      @click="props.row.dialog_dtl = true"
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
                          :ordDtTm="props.row.ORDDTTM"
                          :aprvDtTm="props.row.APRVDTTM"
                          :searchParamData="searchParam"
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
              <div class="expand_table_item table_item_mobile">
                <q-td class="table-td_mobile">
                  {{
                    props.row.ORD_MEMO !== "" ? props.row.ORD_MEMO : "메모 없음"
                  }}
                </q-td>
              </div>
              <!-- <div class="expand_table_item table_item_mobile">
                <q-td
                  class="table-td_mobile"
                  @click="props.row.expand = !props.row.expand"
                >
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
    </ExpandLayout_mobile>
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import ExpandLayout_mobile from "src/layouts/mobile/ExpandLayouts/ExpandLayout_mobile.vue";
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import MyRecentComp_mobile from "src/components/mobile/DialogComp/4.myDialogComp/MyRecentComp_mobile.vue";
import MyRecentDetailComp_mobile from "src/components/mobile/DialogComp/4.myDialogComp/MyRecentDetailComp_mobile.vue";
import DeleteComp_mobile from "src/components/mobile/DialogComp/__commonDialogComp/DeleteComp_mobile.vue";

//필터표시
const isActiveBtn = ref();
const isPeriodBtn = ref();

const userInfo = util.getUserData();
const expanded_model = ref(true);

const dialog_full = ref(true);
const dialog_dtl = ref(false);
const dialog_메모 = ref(false);

const bus = inject("bus");

const dialog_MyPage = ref(false);
const expanded_btn = () => {
  dialog_MyPage.value = true;
};

// const btnIcon = ref("img:assets/icons/sort-down-white.svg");

const btnIconUp2 = ref("img:assets/images/arrw_up.svg");
const btnIconDown2 = ref("img:assets/images/arrw_dwn.svg");

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  searchGb: "0",
  startDt: "",
  endDt: "",
});

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/myPage/rcntlyOrd", { params: searchParam.value });

  rows.value = res;
}

onMounted(async () => {
  await fetchData();
});

bus.off("refItemList");
bus.on("refItemList", async () => {
  await fetchData();
});

const delOrder = async (val) => {
  let row = val;

  try {
    Object.assign(row, { USER_ID: userInfo.USER_ID });

    await api.delete("/recently/cancelRcently", { params: row });

    util.showNotifyTop("취소되었습니다.");

    await fetchData();
  } catch (error) {
    // alert(error.response.data);
    alert("오류가 발생했습니다.");
  }
};

const rows = ref([
  // {
  //   badge: 1,
  //   약: "아즈정(피임)",
  //   규격: "30T",
  //   상태: "승인",
  //   주문일: "2023.01.01",
  //   주문시간: "08:09:35",
  //   주문상태: "매출",
  //   종류: 3,
  //   금액: 40000000,
  //   승인일: "2023.01.01",
  //   승인시간: "12:24:17",
  //   승인상태: "주문취소",
  //   승인번호: "02457",
  //   메모: true,
  //   상세: true,
  // },
  // {
  //   badge: 1,
  //   약: "아즈정(피임)",
  //   규격: "30T",
  //   상태: "접수",
  //   주문일: "2023.01.01",
  //   주문시간: "08:09:35",
  //   주문상태: "매출",
  //   종류: 3,
  //   금액: 40000000,
  //   승인일: "2023.01.01",
  //   승인시간: "12:24:17",
  //   승인상태: "주문취소",
  //   승인번호: "02457",
  //   메모: true,
  //   상세: true,
  // },
  // {
  //   badge: 1,
  //   약: "아즈정(피임)",
  //   규격: "30T",
  //   상태: "등록",
  //   주문일: "2023.01.01",
  //   주문시간: "08:09:35",
  //   주문상태: "매출",
  //   종류: 3,
  //   금액: 40000000,
  //   승인일: "2023.01.01",
  //   승인시간: "12:24:17",
  //   승인상태: "주문취소",
  //   승인번호: "02457",
  //   메모: true,
  //   상세: true,
  // },
]);
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  // padding: 8px 0 !important;
  gap: 8px;

  // td {
  //   @include fs-6;
  //   span {
  //     @include fs-6;
  //   }
  // }
  .table_item:nth-child(1) {
    @include flex_start;
    @include fw-7;
    align-items: flex-start;
    grid-column: span 9;
    gap: 4px;
    height: auto !important;
    td {
      height: auto !important;
      // font-size: 16px;
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
      @include fs-5;
      @include fw-7;
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 1;
    td {
      @include flex_start;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 2;
    td {
      @include flex_start;
      // color: #666666;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 2;
    td {
      @include flex_start;
      // color: #666666;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 1;
    td {
      @include flex_center;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(7) {
    grid-column: span 1;
    td {
      @include flex_center;
      // color: #666666;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(8) {
    grid-column: span 1;
    td {
      @include flex_center;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(9) {
    grid-column: span 2;
    td {
      @include flex_end;
      // color: #666666;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(10) {
    grid-column: span 1;
    td {
      @include flex_start;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(11) {
    grid-column: span 2;
    td {
      @include flex_start;
      // color: #666666;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(12) {
    grid-column: span 2;
    // gap: 8px;
    td {
      @include flex_end;
      // color: #666666;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(13) {
    grid-column: span 1;
    td {
      @include flex_center;
      // font-size: 12px !important;
    }
  }
  .table_item:nth-child(14) {
    grid-column: span 4;
    td {
      @include flex_end;
      gap: 8px;
      // font-size: 12px !important;
    }
  }
}

.expand_table_wrap {
  // @include border-b;
  border-bottom: 1px solid $line-400;
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

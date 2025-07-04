<template>
  <div class="RecentDetail_wrap">
    <q-dialog
      v-model="dtl_bottom_model"
      no-backdrop-dismiss
      seamless
      position="bottom"
    >
      <div
        class="BottomDialog_wrap"
        :class="BottomDialogStyle"
        style="margin-bottom: 0"
      >
        <div class="first_tab">
          <div class="" v-if="gubunChk == 1">
            <span class="red-400">
              현재 상태에서는 개별주문 취소가 불가 합니다.
            </span>
          </div>
          <div class="" v-else-if="gubunChk == 2">
            <span class="red-400">
              반품인 경우는 매출원장 > 반품현황에서 반품취소가 가능합니다.
            </span>
          </div>
          <div class="" v-else-if="gubunChk == 3">
            <span class="red-400">
              품목 1건으로 개별주문취소가 불가합니다.<br />
              주문취소로 진행해 주시기 바랍니다.
            </span>
          </div>
          <div class="" v-else-if="gubunChk == 4">
            <span>총 주문취소 품목</span>
            <span>{{ util.getNumberFormat(totalCount) }}</span>
            <span>건</span>
          </div>
          <DeleteComp_mobile_d
            delete_text1="상세내역"
            :dataRow="chkRow"
            @delete-detl-basket="(val) => bakDetlReq(val)"
          >
            <BtnComp_mobile
              v-if="gubunChk == 4"
              buttonText="개별주문취소"
              btnStyle="red"
              btnSize="md"
              style="width: 160px"
              :isDisable="disableChk"
            />
          </DeleteComp_mobile_d>
        </div>
      </div>
    </q-dialog>
    <q-markup-table separator="none" flat dense style="min-height: auto">
      <tbody>
        <tr style="height: 28px !important">
          <td style="padding: 0">등록</td>
          <td style="padding: 0">
            <!-- {{ moment(ordDt).format("YYYY.MM.DD") }} {{ ordTm }} -->
            {{ ordDtTm }}
          </td>
          <td style="padding: 0">구분</td>
          <td style="padding: 0">{{ gubun }}</td>
        </tr>
        <tr style="height: 28px !important">
          <td style="padding: 0">승인</td>
          <td style="padding: 0">
            <!-- {{ aprvDt !== "" ? moment(aprvDt).format("YYYY.MM.DD") : "" }}
            {{ aprvTm }} -->
            {{ aprvDtTm }}
          </td>
          <td style="padding: 0">승인번호</td>
          <td style="padding: 0">{{ aprvNo }}</td>
        </tr>
      </tbody>
    </q-markup-table>
  </div>
  <div class="RecentDetail_Table_wrap">
    <MyRecentDetailTable_mobile
      ref="myRecentDetailTable"
      :tblNm="tblNm"
      :sn="sn"
    />
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, defineProps, onMounted, computed, inject, watch } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import MyRecentDetailTable_mobile from "src/components/mobile/DialogComp/TableDialogComp/4.myTableComp/MyRecentDetailTable_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import DeleteComp_mobile_d from "src/components/mobile/DialogComp/__commonDialogComp/DeleteComp_mobile_d.vue";

const myRecentDetailTable = ref(null);

const dtl_bottom_model = ref(false);

const props = defineProps({
  ordDt: {
    type: String,
    default: "",
  },
  ordTm: {
    type: String,
    default: "",
  },
  gubun: {
    type: String,
    default: "",
  },
  aprvDt: {
    type: String,
    default: "",
  },
  aprvTm: {
    type: String,
    default: "",
  },
  aprvNo: {
    type: String,
    default: "",
  },
  isOpen: {
    type: Boolean,
    default: false,
  },
  sn: {
    type: Number,
  },
  tblNm: {
    type: String,
  },
  ordDtTm: {
    type: String,
    default: "",
  },
  aprvDtTm: {
    type: String,
    default: "",
  },
});

const userInfo = util.getUserData();
const bus = inject("bus");
const totalCount = ref("0");
const chkRow = ref([]);
const loadTotal = ref([]);

const stats = ref("");
const stats2 = ref("");

const disableChk = computed(() => (chkRow.value.length == 0 ? true : false));

const gubunChk = computed(() => {
  if (stats2.value == "반품") {
    return 2;
  } else if (
    stats2.value != "반품" &&
    loadTotal.value.length == 1 &&
    stats.value == "등록"
  ) {
    return 3;
  } else if (
    stats2.value != "반품" &&
    loadTotal.value.length != 1 &&
    stats.value == "등록"
  ) {
    return 4;
  } else if (
    stats2.value != "반품" &&
    loadTotal.value.length == 1 &&
    stats.value != "등록"
  ) {
    return 1;
  } else if (
    stats2.value != "반품" &&
    loadTotal.value.length != 1 &&
    stats.value != "등록"
  ) {
    return 1;
  } else {
    return 4;
  }
});

//---------------------------- Function ------------------------------//
//체크된 값 받아오기
bus.off("recchkTotal");
bus.on("recchkTotal", async (tableData) => {
  chkRow.value = tableData.value;
  totalCount.value = tableData.value.length;
  if (chkRow.value.length > 0) {
    stats.value = chkRow.value[0].STATS;
    stats2.value = chkRow.value[0].GUBUN;
  }
});

//재주문 내역 받아오기
bus.off("recloadTotal");
bus.on("recloadTotal", async (val) => {
  loadTotal.value = val;
});

//삭제버튼
const bakDetlReq = async (dataVal) => {
  if (totalCount.value == loadTotal.value.length) {
    alert(
      "“개별 주문취소만 가능합니다. 전체 주문취소는 “주문취소” 로 진행해 주시기 바랍니다."
    );
    return;
  }

  for (let i = 0; i < chkRow.value.length; i++) {
    let row = chkRow.value[i];
    let iSn = row.SN + "," + row.SNO + "," + row.ITEM_CD;

    try {
      Object.assign(row, { I_SN: iSn });
      Object.assign(row, { USER_ID: userInfo.USER_ID });

      await api.delete("/recently/cancelSelRcently", { params: row });
    } catch (error) {
      // alert(error.response.data);
      alert("오류가 발생했습니다.");
    }
  }
  util.showNotifyTop("선택한 품목에 대해 주문 취소가 되었습니다.");
  await bus.emit("refSearch");
  await bus.emit("refItemList");
};

async function fetchItemList() {
  await myRecentDetailTable.value.fetchItemList();
}

watch(chkRow, () => {
  chkRow.value.length > 0
    ? (dtl_bottom_model.value = true)
    : (dtl_bottom_model.value = false);
});

onMounted(async () => {
  await fetchItemList();
});
</script>

<style lang="scss" scoped>
.RecentDetail_wrap {
  td {
    @include fs-5;
  }
  td:nth-child(1),
  td:nth-child(3) {
    @include fw-7;
    padding-right: 2px !important;
  }
}

.first_tab {
  @include flex_between;
  div:nth-child(1) {
    @include flex_start;
    gap: 4px;
    span {
      @include fs-5;
      @include fw-7;
    }
    span:nth-child(2) {
      color: $primary;
    }
  }
}
</style>

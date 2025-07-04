<template>
  <div class="SalesDetail_wrap">
    <q-dialog
      v-model="dialog_bottom_model"
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
          <div class="" v-if="props.cnclYn == 'Y'">
            <span>총 반품 품목</span>
            <span>{{ util.getNumberFormat(totalCount) }}</span>
            <span>건</span>
          </div>
          <div class="" v-else>
            <span class="red-400"
              >현재 상태에서는 품목 삭제가 불가 합니다.</span
            >
          </div>
          <DeleteComp_mobile_d
            delete_text1="반품"
            :dataRow="chkRow"
            @delete-detl-basket="(val) => bakDetlReq(val)"
          >
            <BtnComp_mobile
              v-if="props.cnclYn == 'Y'"
              buttonText="삭제"
              btnStyle="red"
              btnSize="md"
              class="btn-line default"
              :isDisable="disableChk"
            />
          </DeleteComp_mobile_d>
        </div>
      </div>
    </q-dialog>
  </div>
  <div class="Detail_Table_wrap">
    <SalesDetailTable_c_mobile :sn="props.sn" :cnclYn="props.cnclYn" />
  </div>
</template>

<script setup>
import { ref, defineProps, inject, computed } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import SalesDetailTable_c_mobile from "src/components/mobile/DialogComp/TableDialogComp/3.salesTableComp/SalesDetailTable_c_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import DeleteComp_mobile_d from "src/components/mobile/DialogComp/__commonDialogComp/DeleteComp_mobile_d.vue";

const dialog_bottom_model = ref(true);

const userInfo = util.getUserData();
const bus = inject("bus");
const props = defineProps({
  sn: {
    type: String,
    default: "",
  },
  cnclYn: {
    type: String,
    default: "",
  },
  searchParam: {},
});
const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
const searchParam = ref(props.searchParam);
const totalCount = ref("0");

const chkRow = ref([]);
const loadTotal = ref([]);
const disableChk = computed(() => (chkRow.value.length == 0 ? true : false));

//체크된 값 받아오기
bus.off("chkTotal");
bus.on("chkTotal", async (tableData) => {
  chkRow.value = tableData.value;
  totalCount.value = tableData.value.length;
});
//체크된 값 받아오기
bus.off("loadTotal");
bus.on("loadTotal", async (val) => {
  loadTotal.value = val;
});

//삭제버튼
const bakDetlReq = async (dataVal) => {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  Object.assign(searchParam.value, { sn: loadTotal.value[0].SN });

  const res = await api.get("/bakReq/chk", {
    params: searchParam.value,
  });

  if (res.CNCL_ABLE_YN != "Y") {
    alert(
      "선택하신 반품건에 대해 진행상태가 변경되어 삭제가 불가능합니다.\n다시 확인하시고 진행해주시기 바랍니다."
    );
    return;
  }

  if (dataVal.length === 0) {
    alert("선택된 항목이 없습니다.");
    return;
  }

  if (loadTotal.value.length === 1) {
    alert(
      "품목이 1개인 경우에는 접수 마스터 항목에서 반품취소를 하시기 바랍니다."
    );
    return;
  }

  if (dataVal.length == loadTotal.value.length) {
    alert(
      "전체 선택한 경우에는 접수 마스터 항목에서 반품취소를 하시기 바랍니다."
    );
    return;
  }

  try {
    dataVal.forEach((m) => {
      Object.assign(m, { USER_ID: userInfo.USER_ID });
    });

    await api.post("/bakReq/deleteBakReqDtl", dataVal);
    util.showNotifyTop("삭제되었습니다.");
    await bus.emit("fetchDtlItemList");
    totalCount.value = 0;
  } catch (error) {
    // alert(error.response.data);
    alert("오류가 발생했습니다.");
  }
};
</script>

<style lang="scss" scoped>
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

<template>
  <div class="SalesDetail_wrap">
    <q-dialog
      v-model="dialog_bottom_model"

      no-backdrop-dismiss
      seamless

      position="bottom"
    >
      <div class="BottomDialog_wrap" :class="BottomDialogStyle" style="margin-bottom: 0;">
        <div class="first_tab">
          <div class="">
            <span>총 주문 품목</span>
            <span>{{ util.getNumberFormat(totalCount)}}</span>
            <span>건</span>
          </div>
          <DeleteComp_mobile
            delete_text1="반품"
            callFunction="onClickDelete"
          >
            <BtnComp_mobile
              buttonText="삭제"
              btnStyle="red"
              btnSize="md"
              style="width: 160px;"
            />
          </DeleteComp_mobile>
        </div>
      </div>
    </q-dialog>
  </div>
  <div class="Detail_Table_wrap">
    <SalesDetailTable_c_mobile :sn="props.sn"/>
  </div>
</template>

<script setup>
import { ref, defineProps, inject } from 'vue';
import { api } from "boot/axios";
import * as util from "src/support/util";
import SalesDetailTable_c_mobile from 'src/components/DialogComp/TableDialogComp/3.salesTableComp/SalesDetailTable_c_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import DeleteComp_mobile from 'src/components/DialogComp/__commonDialogComp/DeleteComp_mobile.vue';

const dialog_bottom_model = ref(true)

const userInfo = util.getUserData();
const bus = inject("bus");
const props = defineProps({
  sn: {
    type: String,
    default: ''
  },
})
const totalCount = ref("0")

bus.off("loadTotalCount");
bus.on("loadTotalCount", async (params) => {
  totalCount.value = params;
});

//식제버튼
bus.off("onClickDelete");
bus.on("onClickDelete", async () => {
  getParams();
});
async function getParams(){
  await bus.emit("getRowDataList");
}
bus.off("setRowDataList");
bus.on("setRowDataList", async (params) => {
  console.log(params);
  if (params.value.length === 0) {
    alert("선택된 항목이 없습니다.");
    return;
  }
  try {
    params.value.forEach((m) => {
      Object.assign(m, { USER_ID: userInfo.USER_ID });
    });

    await api.post("/bakReq/deleteBakReqDtl", params.value);

    util.showNotify("삭제되었습니다.");

    await bus.emit("fetchItemList");
  } catch (error) {
    // alert(error.response.data);
    alert("오류가 발생했습니다.");
  }
});





</script>

<style lang="scss" scoped>

.first_tab {
  @include flex_between;
  div:nth-child(1) {
    @include flex_start;
    gap: 2px;
    span {
      @include fs-5;
      @include fw-7;
    }
    span:nth-child(2) {
      color: $b3;
    }
  }
}

</style>

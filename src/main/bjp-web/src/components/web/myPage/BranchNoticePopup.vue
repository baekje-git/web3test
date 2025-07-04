<template>
  <div class="DialogComp_wrap">
    <div class="" @click="isOpenBranchNoticePopup = true">
      <slot></slot>
    </div>

    <q-dialog v-model="isOpenBranchNoticePopup">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="q-card_c_title"><p>지점공지사항</p></div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>지점공지사항</span>
        </div>
        <div class="q-card_c_user_wrap">
          <q-table
            class="myOrderTable_view"
            :rows="rows"
            :columns="columns"
            row-key="name"
            table-header-class="table_header"
            dense
            hide-bottom
            virtual-scroll
            :rows-per-page-options="[0]"
            :virtual-scroll-sticky-size-start="0"
            @row-click="onRowClick"
          >
            <template v-slot:body-cell-TTL="props">
              <q-td
                :props="props"
                style="padding: 0; cursor: pointer; text-align: left"
              >
                <div
                  class="q-table_stock_wrap"
                  style="font-size: 14px; white-space: normal"
                >
                  {{ props.value }}
                </div>
              </q-td>
            </template>
          </q-table>
        </div>

        <q-card-actions class="q-card_btn_wrap">
          <BtnComp_default
            class="btn-fill gray"
            btn-label="닫기"
            v-close-popup
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";

import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";

const isOpenBranchNoticePopup = ref(false);
const userInfo = util.getUserData();
const bus = inject("bus");
const rows = ref([]);

const searchParam = ref({
  brchCd: userInfo.BRCH_CD,
});

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/myPage/branchNoticePopup", {
    params: searchParam.value,
  });

  rows.value = res.map((i) => {
    return {
      ...i,
      INS_DTM: moment(i.INS_DTM, "YYYY-MM-DD HH:mm:ss").format("YYYY-MM-DD"),
    };
  });
}

function onRowClick(evt, row, index) {
  bus.emit("branchNoticeDtlPopupOpen", row.SEQ_NO);
}

bus.on("branchNoticePopupOpen", async () => {
  await fetchData();
  isOpenBranchNoticePopup.value = true;
});

bus.on("branchNoticeDtlPopupClose", () => {
  isOpenBranchNoticePopup.value = false;
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  { name: "ROWNUMBER", label: "순번", field: "ROWNUMBER", align: "center" },
  { name: "TTL", label: "제목", field: "TTL", align: "center" },
  { name: "INS_DTM", label: "등록일", field: "INS_DTM", align: "center" },
]);
</script>

<style lang="scss">
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  margin-bottom: 10px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid $line-300;
}
.q-card_c_title p {
  @include fw-7;
  padding: 0 0 16px 0;
  border-bottom: solid 1px #999 !important;
}
.q-card_c_user_wrap,
.q-card_c_section_wrap {
  padding: 0 16px;
  max-width: 640px;
}

.q-card_c_table_header_wrap {
  @include flex_between;
  @include fw-7;
  padding-bottom: 8px;
  // border-bottom: 2px solid $b3;
  border-bottom: 1px solid $gray-9;
}
.q-card_c_section_wrap {
  margin-bottom: 40px;
  tr:last-child,
  td {
    border-bottom: 1px solid $td-line;
  }
  tr td:nth-child(1),
  td:nth-child(3) {
    @include fw-7;
  }
  td {
    height: 40px !important;
  }
}
.q-card_c_input {
  @include fs-5;
  border: 1px solid $line-400;
  border-radius: 0;
  max-width: 140px;
}
.q-card_c_input .q-field__control {
  padding: 0 8px;
  height: 32px !important;
}
.q-card_btn_wrap {
  @include flex_end;
  margin: 0;
  padding: 16px 0 !important;
  gap: 8px;
  justify-content: flex-end !important;
}

.q-card_c_Table_wrap {
  max-height: 120px;
  overflow-y: auto;
}
.q-card_c_Table_wrap tr {
  height: 40px !important;
}
</style>

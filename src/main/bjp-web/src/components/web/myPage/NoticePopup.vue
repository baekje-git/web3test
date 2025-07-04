<template>
  <div class="DialogComp_wrap">
    <div class="" @click="isOpenNoticePopup = true">
      <slot></slot>
    </div>

    <q-dialog v-model="isOpenNoticePopup">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="q-card_c_title"><p>공지사항</p></div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>공지사항</span>
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
                  <span
                    v-if="isVisibleNew(props.row)"
                    class="q-table_newText red-300"
                  >
                    NEW
                  </span>
                </div>
              </q-td>
            </template>
          </q-table>
        </div>

        <q-card-actions class="q-card_btn_wrap">
          <BtnComp_default
            btnLabel="닫기"
            class="btn-fill gray"
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

const isOpenNoticePopup = ref(false);
const bus = inject("bus");
const rows = ref([]);

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/myPage/noticePopup");

  rows.value = res.map((i) => {
    return {
      ...i,
      INS_DTM: moment(i.INS_DTM, "YYYY-MM-DD HH:mm:ss").format("YYYY-MM-DD"),
    };
  });
}

function onRowClick(evt, row, index) {
  bus.emit("noticeDtlPopupOpen", row.SEQ_NO);
}

bus.on("noticePopupOpen", async () => {
  await fetchData();
  isOpenNoticePopup.value = true;
});

bus.on("noticePopupClose", () => {
  isOpenNoticePopup.value = false;
});

function isVisibleNew(row) {
  const insDtm = moment(row.INS_DTM.replace(/[^0-9]/g, "")).format("YYYYMMDD");
  const today = moment().format("YYYYMMDD");

  const day = today - insDtm;

  if (day <= 7) {
    return true;
  } else {
    return false;
  }
}

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  { name: "ROWNUMBER", label: "순번", field: "ROWNUMBER", align: "center" },
  { name: "TTL", label: "제목", field: "TTL", align: "center" },
  { name: "INS_DTM", label: "등록일", field: "INS_DTM", align: "center" },
]);
</script>

<style lang="scss">
.q-body--force-scrollbar-y {
  overflow-y: auto !important;
}
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
.q-card_btn_wrap {
  @include flex_end;
  margin: 0;
  padding: 16px 0 !important;
  gap: 8px;
  justify-content: flex-end !important;
}
// .q-card_c_title p {
//   @include fw-7;
//   padding: 0 0 16px 0;
//   border-bottom: solid 1px #999 !important;
// }
.q-card_c_user_wrap,
.q-card_c_section_wrap {
  padding: 0 !important;
  width: 640px;
}

// .q-card_c_table_header_wrap {
//   @include flex_between;
//   @include fw-7;
//   padding-bottom: 8px;
//   border-bottom: 2px solid $b3;
// }
// .q-card_c_section_wrap {
//   margin-bottom: 40px;
//   tr:last-child,
//   td {
//     border-bottom: 1px solid rgba(0, 0, 0, 0.1);
//   }
//   tr td:nth-child(1),
//   td:nth-child(3) {
//     @include fw-7;
//   }
//   td {
//     height: 40px !important;
//   }
// }
// .q-card_c_input {
//   @include fs-5;
//   height: 24px;
//   border: 1px solid #666666;
//   border-radius: 5px;
//   max-width: 140px;
// }
// .q-card_c_input .q-field__control {
//   padding: 0 8px;
//   height: 24px !important;
// }
// .q-card_btn_wrap {
//   @include flex_center;
// }

// .q-card_c_Table_wrap {
//   max-height: 120px;
//   overflow-y: auto;
// }
// .q-card_c_Table_wrap tr {
//   height: 40px !important;
// }
</style>

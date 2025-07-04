<template>
  <div class="">
    <q-table
      class="myBranchTable_view my_view"
      :rows="rows"
      :columns="columns"
      row-key="name"
      table-header-class="table_header"
      dense
      hide-header
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      @row-click="onRowClick"
    >
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="flex_between q-table_noheader">
            <div class="q-table_header_wrap">
              <div class="q-table_header_title">
                <p>지점 공지사항</p>
              </div>
            </div>
            <div class="q-table_btn_wrap">
              <BtnComp_default_small
                class="btn-line default"
                btn_defaultLabel="+ more"
                :btn_defaultLink="btn_defaultLink"
                @click="onClickBranchNoticeMore"
              />
            </div>
          </div>
        </div>
      </template>

      <template v-slot:body-cell-TTL="props">
        <q-td :props="props" style="cursor: pointer">
          <div class="inner">
            <div class="item subject">
              <span class="inline-cut" style="max-width: calc(100% - 30px)">
                {{ props.value }}
              </span>
              <span
                v-if="isVisibleNew(props.row)"
                class="q-table_newText red-300"
              >
                NEW
              </span>
            </div>
            <div class="item date">
              <span>{{
                moment(props.row.INS_DTM, "YYYY-MM-DD HH:mm:ss").format(
                  "YYYY-MM-DD"
                )
              }}</span>
            </div>
          </div>
        </q-td>
      </template>

      <!-- <template v-slot:body-cell-INS_DTM="props">
        <q-td :props="props">
          {{ moment(props.value).format("YYYY-MM-DD") }}
        </q-td>
      </template> -->
    </q-table>
    <BranchNoticePopup />
    <BranchNoticeDetailPopup />
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, defineProps, onMounted, computed, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";

import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
import BranchNoticePopup from "src/components/web/myPage/BranchNoticePopup.vue";
import BranchNoticeDetailPopup from "src/components/web/myPage/BranchNoticeDetailPopup.vue";

const bus = inject("bus");
const userInfo = util.getUserData();
const props = defineProps({
  btn_defaultLink: {
    type: String,
    default: "",
  },
});

const rows = ref([]);
const today = moment().format("YYYY.MM.DD");

const searchParam = ref({
  brchCd: userInfo.BRCH_CD,
});

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/myPage/branchNotice", {
    params: searchParam.value,
  });

  rows.value = res;
}

// 더보기
async function onClickBranchNoticeMore() {
  await bus.emit("branchNoticePopupOpen");
}

// row 클릭
async function onRowClick(evt, row, index) {
  await bus.emit("branchNoticeDtlPopupOpen", row.SEQ_NO);
}

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

onMounted(async () => {
  await fetchData();
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "TTL",
    label: "제목",
    field: "TTL",
    align: "left",
  },
  // { name: "INS_DTM", label: "날짜", field: "INS_DTM", align: "right" },
]);
</script>

<style lang="scss">
.flex_between {
  @include flex_between;
}
.myBranchTable_view {
  height: 159px;
  overflow-y: auto;
  margin-bottom: 25px;
}

.myBranchTable_view tr {
  height: 36px !important;
}
</style>

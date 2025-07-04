<template>
  <div class="">
    <q-table
      class="myNoticeTable_view my_view"
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
                <p>공지사항</p>
              </div>
            </div>
            <div class="q-table_btn_wrap">
              <BtnComp_default_small
                class="btn-line default"
                btn_defaultLabel="+ more"
                @click="onClickNoticeMore"
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
          <span>
            {{ moment(props.value).format("YYYY-MM-DD") }}
          </span>
        </q-td>
      </template> -->
    </q-table>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, defineProps, onMounted, inject } from "vue";
import { api } from "boot/axios";
import moment from "moment";

import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
//import NoticePopup from "src/components/web/myPage/NoticePopup.vue";

const bus = inject("bus");
const props = defineProps({
  btn_defaultLink: {
    type: String,
    default: "",
  },
});

const rows = ref([]);

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/myPage/notice");

  rows.value = res;
}

// 더보기
async function onClickNoticeMore() {
  await bus.emit("noticePopupOpen");
}

// row 클릭
async function onRowClick(evt, row, index) {
  await bus.emit("noticeDtlPopupOpen", row.SEQ_NO);
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
  { name: "TTL", label: "제목", field: "TTL", align: "left" },
  // { name: "INS_DTM", label: "날짜", field: "INS_DTM", align: "right" },
]);
</script>

<style lang="scss">
.flex_between {
  @include flex_between;
}
.myNoticeTable_view {
  height: 159px;
  overflow-y: auto;
  margin-bottom: 25px;
}

.myNoticeTable_view tr {
  height: 36px !important;
}
.q-table_noheader {
  border-color: $gray-9;
}
</style>

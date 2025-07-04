<template>
  <div>
    <ExpandLayout_mobile
      v-model:expanded_model="expanded_model"
      expand_title="공지사항"
      expand_btn="+ more"
      :expand_btnWidth="68"
      @expanded_btnClick="expanded_btn(0)"
    >
      <DialogLayout_mobile
        dialog_title="공지사항"
        v-model:dialog_model_prop="dialog_MyPage"
      >
        <div class="">
          <MyNoticeComp_mobile :rowSno="rowChk" />
        </div>
      </DialogLayout_mobile>

      <q-table
        class=""
        row-key=""
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
              <div class="table_item" @click="expanded_btn(props.row)">
                <q-td class="table-td_mobile">
                  <span>{{ props.row.TTL }}</span>
                  <span
                    v-if="isVisibleNew(props.row)"
                    class="q-table_newText red-300"
                  >
                    NEW
                  </span>
                </q-td>
              </div>
              <div class="table_item">
                <q-td class="table-td_mobile">
                  <span>{{
                    moment(props.row.INS_DTM, "YYYY-MM-DD HH:mm:ss").format(
                      "YYYY-MM-DD"
                    )
                  }}</span>
                </q-td>
              </div>
            </div>
          </q-tr>
        </template>
      </q-table>
    </ExpandLayout_mobile>
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted } from "vue";
import { api } from "boot/axios";
import moment from "moment";

import ExpandLayout_mobile from "src/layouts/mobile/ExpandLayouts/ExpandLayout_mobile.vue";
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import MyNoticeComp_mobile from "src/components/mobile/DialogComp/4.myDialogComp/MyNoticeComp_mobile.vue";

const expanded_model = ref(true);

const dialog_MyPage = ref(false);

const rows = ref([]);

const rowChk = ref(0);

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/myPage/notice");

  let newList = res.map((i) => {
    return { ...i, INS_DTM: i.INS_DTM.replace(/\./g, "/") };
  });

  rows.value = newList;
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

function expanded_btn(row) {
  dialog_MyPage.value = true;
  rowChk.value = row.SEQ_NO;
}

onMounted(async () => {
  await fetchData();
});
</script>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap");

.q-table tr {
  display: block;
  border-bottom: none !important;
  overflow: hidden;
}
.table_wrap {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  padding: 8px 0 !important;
  td {
    span {
      @include fs-5;
    }
  }
  .table_item:nth-child(1) {
    grid-column: span 5;
    max-width: 240px;
    td {
      @include flex_start;
      span:nth-child(1) {
        gap: 3px;
        display: block;
        overflow: hidden;
        text-overflow: ellipsis;
      }
      span:nth-child(2) {
        @include fw-7;
      }
    }
  }
  .table_item:nth-child(2) {
    // @include flex_center; // 날짜 가운데 정렬
    grid-column: span 1;
    td {
      @include flex_end;
      span {
        // font-family: "Malgun Gothic";
      }
    }
  }
}
</style>

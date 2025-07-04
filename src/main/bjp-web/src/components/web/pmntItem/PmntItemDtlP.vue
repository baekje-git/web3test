<template>
  <q-dialog v-model="isOpen" @escape-key="onClickClose">
    <q-card style="padding: 32px">
      <div class="dialog_header">
        <span>유효기간/제조번호</span>
      </div>
      <q-table
        class="salesTable_a"
        :rows="rows"
        :columns="columns"
        row-key="name"
        table-header-class="table_header"
        dense
        hide-bottom
        virtual-scroll
        :rows-per-page-options="[0]"
        :virtual-scroll-sticky-size-start="0"
        :loading="loading"
      >
        <template v-slot:loading>
          <q-inner-loading showing color="primary" />
        </template>
        <template v-slot:top>
          <div class="q-table_wrap">
            <div class="q-table_header_wrap">
              <div class="q-table_header_title">
                <p>품목수불상세</p>
              </div>
            </div>
          </div>
        </template>

        <template v-slot:top-row v-if="rows.length === 0 && searched">
          <q-tr class="no-date">
            <q-td colspan="100%">
              <span>검색된 결과가 없습니다.</span>
            </q-td>
          </q-tr>
        </template>

        <template v-slot:body="props">
          <q-tr :props="props">
            <q-td key="SORT" :props="props" style="width: 10%">
              {{ props.row.SORT }}
            </q-td>
            <q-td key="EXP_DT" :props="props">
              {{
                props.row.EXP_DT != ""
                  ? moment(props.row.EXP_DT).format("YYYY-MM-DD")
                  : ""
              }}
            </q-td>
            <q-td key="PROD_NO" :props="props">
              {{ props.row.PROD_NO }}
            </q-td>
            <q-td key="SERIAL_NO" :props="props">
              {{ props.row.SERIAL_NO }}
            </q-td>
            <q-td key="ITEM_QTY" :props="props">
              {{ props.row.ITEM_QTY }}
            </q-td>
          </q-tr>
        </template>
      </q-table>
      <div>
        <div align="right" style="margin: 10px 8px 10px">
          <BtnComp_active btn-label="닫기" @click="onClickClose" />
        </div>
      </div>
    </q-card>
  </q-dialog>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, computed, defineEmits, inject } from "vue";
import * as util from "src/support/util";
import { api } from "boot/axios";
import moment from "moment";

import BtnComp_active from "../common/BtnComp_active.vue";

const emit = defineEmits(["close"]);
const bus = inject("bus");
const searched = ref(false);
const loading = ref(false);

const rows = ref([]);

const props = defineProps({
  isOpen: { type: Boolean },
});

const isOpen = computed(() => {
  return props.isOpen;
});

const dataParams = ref({
  sn: "",
  sno: "",
});

//---------------------------- Function ------------------------------//

function onClickClose() {
  rows.value = [];

  emit("close");
}

bus.on("pmntItemDetail", async (data) => {
  loading.value = true;

  dataParams.value.sn = data.SN;
  dataParams.value.sno = data.SNO;

  const res = await api.get("/pmntItem/pmntItemDtl", {
    params: dataParams.value,
  });

  rows.value = res;
  searched.value = true;
  loading.value = false;
});

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "SORT",
    label: "분류",
    align: "center",
    required: true,
    field: "SORT",
  },
  { name: "EXP_DT", label: "유효기간", field: "EXP_DT", align: "center" },
  {
    name: "PROD_NO",
    label: "제조번호",
    field: "PROD_NO",
    align: "center",
  },
  {
    name: "SERIAL_NO",
    label: "일련번호",
    field: "SERIAL_NO",
    align: "center",
  },
  { name: "ITEM_QTY", label: "수량", field: "ITEM_QTY", align: "center" },
]);
</script>

<style lang="scss" scoped>
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid $line-300;
}
.salesTable_a {
  // width: 700px;
  width: calc(90vw - 64px);
  max-width: 1200px;
  // height: 756px;
  min-height: 50vh;
  max-height: 70vh;
  overflow: auto;
  // padding: 15px;
}
.q-table_radio_wrap {
  padding: 0 8px;
}
.flex_between {
  @include flex_between;
}
// .q-table_header_title {
//   padding: 10px 0;
// }
.q-table_calrendar_wrap,
.q-table_btn_export_wrap {
  @include flex_center;
  gap: 8px;
}
.q-table_btn_option_wrap {
  margin-left: 20px;
}
.q-table_header_sub_wrap {
  @include flex_start;
  gap: 40px;
  margin: 8px 0;
  padding: 8px 15px;
  background-color: rgba(31, 63, 130, 0.1);
}
.q-table_header_sub_wrap p span {
  @include fs-5;
}
.q-table_header_sub_wrap p span:nth-child(2) {
  @include fw-7;
  margin-left: 15px;
}
</style>

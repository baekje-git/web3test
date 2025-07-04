<template>
  <div class="" style="padding: 10px 0">
    <q-table
      class=""
      row-key="name"
      :rows="rows"
      :columns="columns"
      flat
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
    >
      <template v-slot:top>
        <div class="border-top"></div>
      </template>

      <template v-slot:header="props">
        <div class="table_header_wrap">
          <div class="table_header_item">
            {{ props.cols[0].label }}
          </div>
          <div class="table_header_item">
            {{ props.cols[1].label }}
          </div>
          <div class="table_header_item">
            {{ props.cols[2].label }}
          </div>
          <div class="table_header_item">
            {{ props.cols[3].label }}
          </div>
          <!-- <div class="table_header_item">
            {{ props.cols[4].label }}
          </div> -->
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <!-- <div class="table_item">
              <q-td>{{ props.row.SORT }}</q-td>
            </div> -->
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{
                  props.row.EXP_DT === ""
                    ? ""
                    : moment(props.row.EXP_DT, "YYYY-MM-DD HH:mm:ss").format(
                        "YYYY-MM-DD"
                      )
                }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.PROD_NO }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.SERIAL_NO }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ util.getNumberFormat(props.row.ITEM_QTY) }}
              </q-td>
            </div>
          </div>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, defineProps, onMounted } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";

const props = defineProps({
  SN: {
    type: String,
    default: "",
  },
  SNO: {
    type: String,
    default: "",
  },
});

const searchParam = ref({
  sn: props.SN,
  sno: props.SNO,
});

const columns = [
  // { name: 'SORT', label: '종류', align: 'center' },
  { name: "EXP_DT", label: "유효기간", align: "center" },
  { name: "PROD_NO", label: "제조번호", align: "center" },
  { name: "SERIAL_NO", label: "일련번호", align: "center" },
  { name: "ITEM_QTY", label: "수량", align: "center" },
];

const rows = ref([]);

onMounted(async () => {
  const res = await api.get("/pmntItem/pmntItemDtl", {
    params: searchParam.value,
  });

  rows.value = res;
});
</script>

<style lang="scss" scoped>
.table_header_wrap {
  display: grid;
  grid-template-columns: repeat(8, minmax(calc(100% / 8), 1fr));
  background-color: $th-bg;
  border-bottom: 1px solid $th-line;
  .table_header_item {
    @include fs-5;
    @include fw-7;
    text-align: center;
    padding: 10px 0 !important;
    // border-right: 1px solid $td-line;
  }
  .table_header_item:nth-child(1) {
    grid-column: span 2;
  }
  .table_header_item:nth-child(2) {
    grid-column: span 2;
  }
  .table_header_item:nth-child(3) {
    grid-column: span 3;
  }
  .table_header_item:last-child {
    grid-column: span 1;
  }
}

.table_wrap {
  display: grid;
  grid-template-columns: repeat(8, minmax(calc(100% / 8), 1fr));
  padding: 0;
  border: none;
  .table_item {
    // padding: 4px 2px !important;
    // border-right: 1px solid $td-line;
    td {
      @include flex_center;
      @include fs-6;
    }
  }
  .table_item_mobile {
    padding: 10px 2px;
  }
  .table_item:nth-child(1) {
    grid-column: span 2;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 2;
  }
  .table_item:nth-child(3) {
    grid-column: span 3;
    height: auto !important;
    td {
      height: auto !important;
      white-space: normal;
    }
  }
  .table_item:last-child {
    grid-column: span 1;
    border: none;
  }
}
</style>

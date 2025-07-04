<template>
  <div class="">
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
      :loading="loading"
    >
      <template v-slot:loading>
        <q-inner-loading
          class="loading custom"
          :showing="loading"
          color="primary"
        />
      </template>
      <template v-slot:top>
        <div class="border-top"></div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span>
                  {{
                    moment(props.row.ORD_DT, "YYYY-MM-DD HH:mm:ss").format(
                      "YYYY-MM-DD"
                    )
                  }}
                </span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span>{{ props.row.GUBUN_NM }}</span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <div v-if="props.row.GUBUN_NM == '반품'">
                  <span class="red-400">{{
                    util.getNumberFormat(props.row.ITEM_QTY)
                  }}</span>
                  <span class="red-400">개</span>
                </div>
                <div v-else>
                  <span>{{ util.getNumberFormat(props.row.ITEM_QTY) }}</span>
                  <span>개</span>
                </div>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span>{{ util.getNumberFormat(props.row.WP2_AMT) }}</span>
                <span>원</span>
              </q-td>
            </div>
          </div>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import moment from "moment";
import * as util from "src/support/util";

const props = defineProps({
  historyData: Array,
  loading: {
    type: Boolean,
    default: false,
  },
});

const rows = ref([]);

watch(props, () => {
  rows.value = props.historyData;
});
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  padding: 18px 0 !important;
  gap: 0 10px;
  .table_item:nth-child(1) {
    grid-column: span 1;
    width: 100px;
    td {
      @include fw-7;
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 1;
    td {
      @include flex_end;
      span {
        @include fs-5;
        color: #666666;
      }
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 1;
    td {
      @include flex_end;
      span {
        @include fs-5;
        color: #666666;
      }
    }
  }
}
</style>

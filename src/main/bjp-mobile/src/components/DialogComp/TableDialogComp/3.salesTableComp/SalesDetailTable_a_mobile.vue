<template>
  <div class="" style="padding: 10px 0;">
    <q-table
      class=""
      row-key="날짜"
      :rows="rows"

      flat

      hide-header
      hide-bottom

      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
    >
      <template v-slot:top>
        <div class="border-top"></div>
      </template>
      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item">
              <div class="table_badge_wrap">
                  <q-badge class="table_badge_red">{{ props.row.MED_NM }}</q-badge>
                  <q-badge class="table_badge_blue">{{ props.row.PAY_ITEM_NM_MOBILE }}</q-badge>
              </div>
              <q-td>{{ props.row.ITEM_NM_UNIT }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.규격 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>단가 {{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}원</q-td>
            </div>
            <div class="table_item">
              <q-td>유효기간 {{ (props.row.EXP_DT === "" ? "" : moment(props.row.EXP_DT).format("YYYY.MM.DD")) }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.ITEM_QTY }}개</q-td>
            </div>
            <div class="table_item">
              <q-td>금액 {{ util.getNumberFormat(props.row.TOTAL_AMT) }}원</q-td>
            </div>
          </div>
        </q-tr>

      </template>

    </q-table>
  </div>
</template>

<script setup>
import { ref, defineProps, onMounted } from 'vue';
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";

const props = defineProps({
  SN: {
    type: String,
    default: '',
  }
});

const searchParam = ref({
  sn : props.SN
});

const rows = ref([]);

onMounted(async () => {
  const { custList, detailMaster }= await api.get("/ordLedger/detailSearch", {
    params: searchParam.value,
  });

  rows.value = custList;
});
</script>

<style lang="scss" scoped>


.table_wrap {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  padding: 6px 0 !important;
  gap: 0 8px;
  .table_item:nth-child(1){
    @include flex_start;
    @include fw-7;
    grid-column: span 6;
    max-width: 328px;
    gap: 4px;
    td {
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .table_item:nth-child(2){
    grid-column: span 3;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(3){
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
      color: #666666;
    }
  }
  .table_item:nth-child(4){
    grid-column: span 2;
    td {
      @include flex_end;
      @include fs-5;
    }
  }
  .table_item:nth-child(5){
    grid-column: span 3;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(6){
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
      color: #666666;
    }
  }
  .table_item:nth-child(7){
    grid-column: span 2;
    td {
      @include flex_end;
      @include fs-5;
    }
  }

}



</style>

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
              <q-td>{{ props.row.구 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.거래상태 }} {{ props.row.약국명 }} {{ props.row.동 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.날짜 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.시간 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.제품번호 }}</q-td>
            </div>
            <div class="table_item">
              <q-td v-if="props.row.재고 <= 0" class="red-text">{{ props.row.재고 }}개</q-td>
              <q-td v-else>{{ props.row.재고 }}개</q-td>
            </div>
            <div class="table_item">
              <q-td>단가</q-td>
            </div>
            <div class="table_item">
              <q-td v-if="props.row.단가 <= 0"><span class="red-text">{{ props.row.단가 }}</span><span style="color: #666666;">원</span></q-td>
              <q-td v-else>{{ props.row.단가 }}원</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.상태 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>금액</q-td>
            </div>
            <div class="table_item">
              <q-td v-if="props.row.금액 <= 0"><span class="red-text">{{ props.row.금액 }}</span><span style="color: #666666;">원</span></q-td>
              <q-td v-else>{{ props.row.금액 }}원</q-td>
            </div>
          </div>
        </q-tr>

      </template>

    </q-table>
  </div>
</template>

<script setup>
import { ref } from 'vue';


const rows = ref([
  {
    구: '서대문',
    거래상태: '[거래종료]',
    약국명: '구로엔씨온누리약국',
    동: '(구로동)',
    날짜: '2024.05.28',
    시간: '19:05:54',
    제품번호: '12848',
    재고: -3,
    단가: 20000,
    상태: '매출반품',
    금액: -10000,
  },
  {
    구: '서대문',
    거래상태: '[거래종료]',
    약국명: '종로수약국',
    동: '(구로동)',
    날짜: '2024.05.28',
    시간: '19:05:54',
    제품번호: '12848',
    재고: 3,
    단가: 20000,
    상태: '매출반품',
    금액: 60000,
  },
]);



</script>

<style lang="scss" scoped>


.table_wrap {
  display: grid;
  grid-template-columns: repeat(11, 1fr);
  padding: 6px 0 !important;
  gap: 0 4px;
  .table_item {
    td {
      @include fs-6;
    }
  }
  .table_item:nth-child(1){
    grid-column: span 1;
    td {
      @include flex_center;
      font-size: 16px;
    }
  }
  .table_item:nth-child(2){
    grid-column: span 10;
    td {
      @include flex_start;
      @include fw-7;
      font-size: 16px;
    }
  }
  .table_item:nth-child(3){
    grid-column: span 2;
    td {
      @include flex_start;
    }
  }
  .table_item:nth-child(4){
    grid-column: span 2;
    td {
      @include flex_start;
    }
  }
  .table_item:nth-child(5){
    grid-column: span 2;
    td {
      @include flex_start;
      color: #666666;
    }
  }
  .table_item:nth-child(6){
    grid-column: span 2;
    td {
      @include flex_end;
    }
  }
  .table_item:nth-child(7){
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(8){
    grid-column: span 2;
    td {
      @include flex_end;
      color: #666666;
      span {
        @include fs-6;
      }
    }
  }
  .table_item:nth-child(9){
    grid-column: span 8;
    td {
      @include flex_end;
      color: #666666;
    }
  }
  .table_item:nth-child(10){
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(11){
    grid-column: span 2;
    td {
      @include flex_end;
      color: #666666;
      span {
        @include fs-6;
      }
    }
  }

}



</style>

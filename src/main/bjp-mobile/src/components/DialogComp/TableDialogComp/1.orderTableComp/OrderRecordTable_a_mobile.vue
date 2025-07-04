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
    >
      <template v-slot:top>
        <div class="border-top"></div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item">
              <q-td><span>{{ props.row.주문일자 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>{{ props.row.수량 }}</span><span>개</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>{{ props.row.단가 }}</span><span>원</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>{{ props.row.상태 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>{{ props.row.제조번호 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>유효</span><span>{{ props.row.유효기간 }}</span></q-td>
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
    주문일자: '2023.01.05',
    수량 : 300,
    단가: '18,000',
    상태: '매출',
    제조번호: '제조',
    유효기간: '2023.01.05'
  },
  {
    주문일자: '2023.01.05',
    수량 : 300,
    단가: '18,000',
    상태: '매출',
    제조번호: '제조',
    유효기간: '2023.01.05'
  },
  {
    주문일자: '2023.01.05',
    수량 : 300,
    단가: '18,000',
    상태: '매출',
    제조번호: '제조',
    유효기간: '2023.01.05'
  },
]);


</script>

<style lang="scss" scoped>


.table_wrap {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  padding: 6px 0 !important;
  gap: 0 10px;
  td {
    span {
      @include fs-5;
      color: #666666;
    }
    span:nth-child(1) {
      margin-right: 2px;
    }
  }
  .table_item:nth-child(1){
    grid-column: span 1;
    td {
      span {
        @include fw-7;
        font-size: 16px;
        color: #000;
      }
    }
  }
  .table_item:nth-child(2){
    grid-column: span 1;
    td {

    }
  }
  .table_item:nth-child(3){
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
  .table_item:nth-child(4){
    grid-column: span 1;
    td {
      span {
        @include fw-7;
        font-size: 16px;
        color: #000;
      }
    }
  }
  .table_item:nth-child(5){
    grid-column: span 1;
    td {

    }
  }
  .table_item:nth-child(6){
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
}



</style>

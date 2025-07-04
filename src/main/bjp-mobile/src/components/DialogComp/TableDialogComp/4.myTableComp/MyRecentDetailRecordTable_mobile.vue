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
              <q-td><span>주문일자</span><span>{{ props.row.주문일자 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>수량</span><span>{{ props.row.수량 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>단가</span><span>{{ props.row.단가 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>유효기간</span><span>{{ props.row.유효기간 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>제조번호</span><span>{{ props.row.제조번호 }}</span></q-td>
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
    유효기간: '2023.01.05',
    제조번호: 'E03202',
  },
  {
    주문일자: '2023.01.05',
    수량 : 300,
    단가: '18,000',
    유효기간: '2023.01.05',
    제조번호: 'E03202',
  },
  {
    주문일자: '2023.01.05',
    수량 : 300,
    단가: '18,000',
    유효기간: '2023.01.05',
    제조번호: 'E03202',
  },
]);


</script>

<style lang="scss" scoped>


.table_wrap {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  padding: 2px 0 !important;
  gap: 0 10px;
  .table_item {
    padding: 2px 0 !important;
    td {
      @include flex_start;
      gap: 8px;
      span {
        @include fs-5;
      }
    }
  }
  .table_item:nth-child(1){
    grid-column: span 1;
    td {
      span:nth-child(1) {
        @include fw-5;
      }
      span:nth-child(2) {
        @include fw-7;
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
      span:nth-child(2) {
        color: #666666;
      }
    }
  }
  .table_item:nth-child(4){
    grid-column: span 1;
    td {

    }
  }
  .table_item:nth-child(5){
    grid-column: span 2;
    td {
      span:nth-child(2) {
        color: #666666;
      }
    }
  }
}



</style>

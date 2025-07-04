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
              <q-td><span>{{ props.row.상태 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>{{ props.row.수량 }}</span><span>개</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>{{ props.row.단가 }}</span><span>원</span></q-td>
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
    상태: '매출',
    수량 : 300,
    단가: '6,255,354',
  },
  {
    주문일자: '2023.01.05',
    상태: '매출',
    수량 : 300,
    단가: '6,255,354',
  },
  {
    주문일자: '2023.01.05',
    상태: '매출',
    수량 : 300,
    단가: '6,255,354',
  },
]);


</script>

<style lang="scss" scoped>


.table_wrap {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  padding: 18px 0 !important;
  gap: 0 10px;
  .table_item:nth-child(1){
    grid-column: span 1;
    td {
      @include fw-7;
    }
  }
  .table_item:nth-child(2){
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(3){
    grid-column: span 1;
    td {
      @include flex_start;
      span {
        @include fs-5;
        color: #666666;
      }
    }
  }
  .table_item:nth-child(4){
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

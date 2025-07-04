<template>
  <div class="">
    <q-table
      class=""
      row-key=""
      :rows="top_rows"

      flat

      hide-header
      hide-bottom

      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"

      style="height: 197px; margin-bottom: 30px;"
    >
      <template v-slot:top>
        <div class="border-top"></div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap top">
            <div class="table_item">
              <q-td><span>{{ props.row.주문일자 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>유효</span><span>{{ props.row.유효기간 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>제조번호</span><span>{{ props.row.제조번호 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>출하수량</span><span :class="{ 'text-red': props.row.출하수량 < 0 }">{{ props.row.출하수량 < 0 ? '-' : '' }}{{ Math.abs(props.row.출하수량) }}</span><span>개</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>반품가능수량</span><span>{{ props.row.반품수량 }}</span><span>개</span></q-td>
            </div>
            <div class="table_item">
              <EAInputComp_mobile
                inputStyle="EA-xs"
                v-model="EAcount1"
                style="width: 64px;"
              />
              <BtnComp_mobile
                buttonText="검색"
                btnStyle="default"
                v-model="EAcount2"
                style="width: 40px;"
              />
            </div>
          </div>
        </q-tr>
      </template>
    </q-table>

    <q-table
      class=""
      row-key=""
      :rows="bottom_rows"

      flat

      hide-header
      hide-bottom

      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"

      style="height: 197px;"
    >
    <template v-slot:top>
        <div class="border-top"></div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap top">
            <div class="table_item">
              <q-td><span>{{ props.row.주문일자 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>유효</span><span>{{ props.row.유효기간 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>제조번호</span><span>{{ props.row.제조번호 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>출하수량</span><span :class="{ 'text-red': props.row.출하수량 < 0 }">{{ props.row.출하수량 < 0 ? '-' : '' }}{{ Math.abs(props.row.출하수량) }}</span><span>개</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>반품가능수량</span><span>{{ props.row.반품수량 }}</span><span>개</span></q-td>
            </div>
            <div class="table_item">

            </div>
          </div>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import EAInputComp_mobile from 'src/components/__commonComp/inputComp/EAInputComp_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import BottomLayout_mobile from 'src/layouts/__BottomLayout_mobile.vue';


const EAcount1 = ref(0)
const EAcount2 = ref(0)


const top_rows = ref([
  {
    주문일자: '2023.01.05',
    유효기간: '2024.12.31',
    제조번호: '024221121',
    출하수량: 10,
    반품수량: 240,
  },
  {
    주문일자: '2023.01.05',
    유효기간: '2024.12.31',
    제조번호: '024221121',
    출하수량: 60,
    반품수량: 240,
  },
]);

const bottom_rows = ref([
  {
    주문일자: '2023.01.05',
    유효기간: '',
    제조번호: '',
    출하수량: -10,
    반품수량: 0,
  },
  {
    주문일자: '2023.01.05',
    유효기간: '',
    제조번호: '',
    출하수량: 60,
    반품수량: 0,
  },
]);


</script>

<style lang="scss" scoped>


.table_wrap {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  padding: 6px 0 !important;
  gap: 0 4px;
  .table_item {
    td {
      height: 26px !important;
    }
  }
  .table_item:nth-child(1){
    grid-column: span 1;
    td {
      @include flex_start;
      span {
        @include fw-7;
      }
    }
  }
  .table_item:nth-child(2){
    grid-column: span 1;
    td {
      @include flex_start;
      gap: 2px;
      span {
        @include fs-5;
        color: #666666;
      }
    }
  }
  .table_item:nth-child(3){
    grid-column: span 1;
    td {
      @include flex_start;
      gap: 2px;
      span {
        @include fs-5;
        color: #666666;
      }
    }
  }
  .table_item:nth-child(4){
    grid-column: span 1;
    td {
      @include flex_start;
      span {
        @include fs-5;
      }
      span:nth-child(2) {
        margin-left: 2px;
      }
      .text-red {
        color: $r1;
      }
    }
  }
  .table_item:nth-child(5){
    grid-column: span 1;
    td {
      @include flex_start;
      span {
        @include fs-5;
      }
      span:nth-child(2) {
        margin-left: 2px;
      }
    }
  }
  .table_item:nth-child(6){
    @include flex_end;
    gap: 6px;
    grid-column: span 1;

  }
}



</style>

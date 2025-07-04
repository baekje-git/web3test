<template lang="">
  <div>
    <q-table
      class=""
      row-key="제목"
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
            <div class="table_item">
              <q-td>
                <span v-if="props.row.badge === '필독'" class="blue-text bold-text">필독</span>
                <span>{{ props.row.제목 }}</span>
                <span v-if="props.row.new === true" class="red-text">NEW</span>
              </q-td>
            </div>
            <div class="table_item">
              <q-td><span>{{ props.row.날짜 }}</span></q-td>
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
    new: false,
    badge: '필독',
    제목: '타사이트를 통한 주문 관련 공지사항',
    날짜: '2022.04.21',
  },
  {
    new: false,
    badge: '',
    제목: '판피린 주문 수량 단위 안내 건 (9개  ★6개 / 1₩B…',
    날짜: '2022.04.21',
  },
  {
    new: false,
    badge: '',
    제목: '판피린 주문 수량 단위 안내 건 (9개  ★6개 / 1₩B…',
    날짜: '2022.04.21',
  },
]);





</script>

<style lang="scss" scoped>


.table_wrap {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  padding: 12px 0 !important;
  .table_item {
    td {
      span {
        @include fs-5;
      }
    }
  }
  .table_item:nth-child(1){
    grid-column: span 5;
    td {
      @include flex_start;
      gap: 3px;
      max-width: 250px;
      display: block;
      overflow: hidden;
      text-overflow: ellipsis;
      span:nth-child(3) {
        @include fw-7;
        @include fs-6;
      }
    }
  }
  .table_item:nth-child(2){
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
}


</style>

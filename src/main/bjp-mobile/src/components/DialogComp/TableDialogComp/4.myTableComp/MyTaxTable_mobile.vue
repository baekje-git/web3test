<template>
  <div class="">
    <q-table
      class=""
      row-key="name"

      :rows="rows1"
      :columns="columns"

      flat

      hide-bottom

      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"

      style="padding-bottom: 12px; border-bottom: 1px solid rgba(0,0,0, 0.1);"
    >
      <template v-slot:top>
        <div class="table_totalVal">
          <span class="table_total_Text">총</span>
          <span class="table_total_Val">n</span>
          <span class="table_total_Text">건이 조회되었습니다.</span>
        </div>
        <div class="table_totalVal">
          <span class="table_total_Text">
            공급가 49,276,078원 | 부가세 4,928,030원 | 총계 54,204,108원
          </span>
        </div>
        <div class="border-top"></div>
      </template>

      <template v-slot:header="props">
        <div class="table_header_top">
          <span>2024.01 발행</span>
          <span>|</span>
          <span>과세</span>
        </div>
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
        </div>
      </template>

      <template v-slot:top-row>
        <q-tr>
          <div class="table_top_row_wrap" style="font-weight: bold;">
            <div class="table_item">
              <q-td>합계</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ totals.total공급가 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ totals.total부가세 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ totals.total총합계 }}</q-td>
            </div>
          </div>
        </q-tr>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item">
              <q-td>{{ props.row.name }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.공급가 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.부가세 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.총합계 }}</q-td>
            </div>
          </div>
        </q-tr>
      </template>

    </q-table>
  </div>
  <div class="">
    <q-table
      class=""
      row-key="name"

      :rows="rows2"
      :columns="columns"

      flat

      hide-bottom

      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"

      style="padding-bottom: 12px; border-bottom: 1px solid rgba(0,0,0, 0.1);"
    >
      <template v-slot:header="props">
        <div class="table_header_top">
          <span>2024.01 발행</span>
          <span>|</span>
          <span>수금할인</span>
        </div>
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
        </div>
      </template>

      <template v-slot:top-row>
        <q-tr>
          <div class="table_top_row_wrap" style="font-weight: bold;">
            <div class="table_item">
              <q-td>합계</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ totals.total공급가 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ totals.total부가세 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ totals.total총합계 }}</q-td>
            </div>
          </div>
        </q-tr>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item">
              <q-td>{{ props.row.name }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.공급가 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.부가세 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.총합계 }}</q-td>
            </div>
          </div>
        </q-tr>
      </template>

    </q-table>
  </div>
</template>

<script setup>
import { ref, computed  } from 'vue';

const columns = [
  { name: 'name', label: '', align: 'center' },
  { name: '공급가', label: '공급가', align: 'center' },
  { name: '부가세', label: '부가세',align: 'center' },
  { name: '총합계', label: '총합계', align: 'center' },
]

const rows1 = ref([
  {
    name: '일반비급여',
    공급가: '134,018,845',
    부가세: '113,402,111',
    총합계: '137,420,956'
  },
  {
    name: '전문비급여',
    공급가: '34,018,845',
    부가세: '3,402,111',
    총합계: '37,420,956'
  },
  {
    name: '일반급여',
    공급가: '34,018,845',
    부가세: '3,402,111',
    총합계: '37,420,956'
  },
  {
    name: '전문급여',
    공급가: '34,018,845',
    부가세: '3,402,111',
    총합계: '37,420,956'
  },
]);

const rows2 = ref([
  {
    name: '일반비급여',
    공급가: '0',
    부가세: '0',
    총합계: '0'
  },
  {
    name: '전문비급여',
    공급가: '-452,420',
    부가세: '-452,420',
    총합계: '-452,420'
  },
  {
    name: '일반급여',
    공급가: '0',
    부가세: '0',
    총합계: '0'
  },
  {
    name: '전문급여',
    공급가: '0',
    부가세: '0',
    총합계: '0'
  },
]);


const allRows = ref([...rows1.value, ...rows2.value]);

const totals = computed(() => {
  let total공급가 = 0;
  let total부가세 = 0;
  let total총합계 = 0;

  allRows.value.forEach(row => {
    total공급가 += parseFloat(row.공급가.replace(/,/g, ''));
    total부가세 += parseFloat(row.부가세.replace(/,/g, ''));
    total총합계 += parseFloat(row.총합계.replace(/,/g, ''));
  });

  const total공급가Str = total공급가 >= 0 ? total공급가.toLocaleString() : '-' + Math.abs(total공급가).toLocaleString();
  const total부가세Str = total부가세 >= 0 ? total부가세.toLocaleString() : '-' + Math.abs(total부가세).toLocaleString();
  const total총합계Str = total총합계 >= 0 ? total총합계.toLocaleString() : '-' + Math.abs(total총합계).toLocaleString();

  return {
    total공급가: total공급가Str,
    total부가세: total부가세Str,
    total총합계: total총합계Str
  };
});



</script>

<style lang="scss" scoped>

.table_header_top {
  @include flex_start;
  padding: 16px 0 !important;
  gap: 4px;
  span {
    @include fs-5;
  }
  span:nth-child(3) {
    @include fw-7;
  }
}
.table_header_wrap {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  .table_header_item {
    @include flex_center;
    @include fs-5;
    padding-bottom: 8px !important;
    border-right: 1px solid rgba(0,0,0,0.1);
  }
  .table_header_item:last-child {
    border: none
  }
}

.table_top_row_wrap,
.table_wrap {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  border: none;
  .table_item{
    padding: 4px 0 !important;
    border-right: 1px solid rgba(0,0,0,0.1);
    td {
      @include fs-6;
    }
  }
  .table_item:last-child {
    border: none
  }
  .table_item:nth-child(2),
  .table_item:nth-child(3),
  .table_item:nth-child(4) {
    td {
      @include flex_center;
    }
  }
}



</style>

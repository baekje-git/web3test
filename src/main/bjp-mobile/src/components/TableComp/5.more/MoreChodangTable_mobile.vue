<template lang="">
  <div>
    <TabLayout_mobile
      TabTitle="초당약품 매출현황"
      First_btn="일반현황"
      Second_btn="거래처별"
    >
      <template v-slot:first_tab>
        <div class="">
          <q-table
            class=""
            row-key="name"

            :rows="rows1"
            :columns="columns1"

            flat

            hide-bottom

            virtual-scroll
            :rows-per-page-options="[0]"
            :virtual-scroll-sticky-size-start="0"

            style="padding-bottom: 12px; border-bottom: 1px solid rgba(0,0,0, 0.1);"
          >
            <template v-slot:top>
              <div class="table_Top_wrap" style="margin-bottom:16px;">
                <div class="flex_between" style="padding: 0 0 14px 0;">
                  <div class="flex_start" style="gap: 10px;">
                    <DatePickerComp_mobile
                      style="width: 105px;"
                    />
                    <span style="font-size: 12px; color: #999999; margin: 0 4px 0 1px;">-</span>
                    <DatePickerComp_mobile
                      style="width: 105px;"
                    />
                  </div>
                  <BtnComp_mobile
                    buttonText="검색"
                    btnStyle="default"
                    style="width: 64px;"
                  />
                </div>
              </div>
            </template>

            <template v-slot:header="props">
              <div class="first_table_header_wrap">
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
                <div class="table_header_item">
                  {{ props.cols[4].label }}
                </div>
              </div>
            </template>

            <template v-slot:body="props">
              <q-tr>
                <div class="first_table_wrap">
                  <div class="table_item">
                    <q-td>{{ props.row.name }}</q-td>
                  </div>
                  <div class="table_item">
                    <q-td>{{ props.row.매출 }}</q-td>
                  </div>
                  <div class="table_item">
                    <q-td>{{ props.row.반품 }}</q-td>
                  </div>
                  <div class="table_item">
                    <q-td>{{ props.row.판매 }}</q-td>
                  </div>
                  <div class="table_item">
                    <q-td>{{ props.row.판매sub }}</q-td>
                  </div>
                </div>
              </q-tr>
            </template>
          </q-table>
        </div>
      </template>
      <template v-slot:second_tab>
        <div class="">
          <q-table
            class=""
            row-key="name"

            :rows="rows2"
            :columns="columns2"

            flat

            hide-bottom

            virtual-scroll
            :rows-per-page-options="[0]"
            :virtual-scroll-sticky-size-start="0"

            style="padding-bottom: 12px; border-bottom: 1px solid rgba(0,0,0, 0.1);"
          >
            <template v-slot:top>
              <div class="table_Top_wrap">
                <div class="flex_between" style="gap: 12px; padding: 0 0 14px 0;">
                  <div class="table_Filter" @click="dialog_filter = true">
                    <BtnFilterComp_mobile
                      :filterCheck="true"
                    />
                    <DialogLayout_mobile
                      dialog_title="검색 필터"
                      v-model:dialog_model_prop="dialog_filter"
                      :dialog_full="dialog_full"
                    >
                      <div class="">
                        <FilterComp_mobile
                          :btnGroups="filterbtnGroups"
                          btnResult="on"
                        />
                      </div>
                    </DialogLayout_mobile>
                  </div>
                  <div class="flex_start" style="gap: 10px;">
                    <DatePickerComp_mobile
                      style="width: 105px;"
                    />
                    <span style="font-size: 12px; color: #999999; margin: 0 4px 0 1px;">-</span>
                    <DatePickerComp_mobile
                      style="width: 105px;"
                    />
                  </div>
                  <BtnComp_mobile
                    buttonText="검색"
                    btnStyle="default"
                    style="width: 64px;"
                  />
                </div>
              </div>
            </template>

            <template v-slot:header="props">
              <div class="second_table_header_wrap">
                <div class="table_header_item">
                  <span>{{ props.cols[0].label }}</span>
                </div>
                <div class="table_header_item">
                  <div class="table_header_grid_item">
                    <span>{{ props.cols[1].label }}</span>
                  </div>
                  <div class="table_header_grid_item">
                    <span>{{ props.cols[2].label }}</span>
                  </div>
                  <div class="table_header_grid_item">
                    <span>{{ props.cols[3].label }}</span>
                  </div>
                  <div class="table_header_grid_item">
                    <span>{{ props.cols[4].label }}</span>
                  </div>
                </div>
              </div>
            </template>

            <template v-slot:body="props">
              <q-tr>
                <div class="second_table_wrap">
                  <div class="table_item">
                    <q-td>{{ props.row.name }}</q-td>
                  </div>
                  <div class="table_item">
                    <div class="table_grid_item">
                      <q-td>{{ props.row.품목명 }}</q-td>
                    </div>
                    <div class="table_grid_item">
                      <q-td>{{ props.row.거래처명 }}</q-td>
                    </div>
                    <div class="table_grid_item">
                      <q-td>{{ props.row.수량 }}</q-td>
                    </div>
                    <div class="table_grid_item">
                      <q-td>{{ props.row.금액 }}</q-td>
                    </div>
                  </div>
                </div>
              </q-tr>
            </template>
          </q-table>
        </div>
      </template>
    </TabLayout_mobile>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import TabLayout_mobile from 'src/layouts/TabLayouts/TabLayout_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import DatePickerComp_mobile from 'src/components/__commonComp/datepickerComp/DatePickerComp_mobile.vue';
import FilterComp_mobile from 'src/components/DialogComp/FilterDialogComp/FilterComp_mobile.vue';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import BtnFilterComp_mobile from 'src/components/__commonComp/btnComp/BtnFilterComp_mobile.vue';

const dialog_filter = ref(false)

const columns1 = [
  { name: 'name', label: '부서명', align: 'center' },
  { name: '매출', label: '매출', align: 'center' },
  { name: '반품', label: '반품',align: 'center' },
  { name: '판매', label: '판매', align: 'center' },
  { name: '판매', label: '판매', align: 'center' },
]

const rows1 = ref([
  {
    name: '전체',
    매출: '53,100,000',
    반품: '28,14%',
    판매: '66,700',
    판매sub: '13,420,956'
  },
  {
    name: '영업부',
    매출: '53,100,000',
    반품: '28,14%',
    판매: '66,700',
    판매sub: '13,420,956'
  },
  {
    name: '영업1팀',
    매출: '53,100,000',
    반품: '28,14%',
    판매: '66,700',
    판매sub: '13,420,956'
  },
  {
    name: '마포',
    매출: '53,100,000',
    반품: '28,14%',
    판매: '66,700',
    판매sub: '13,420,956'
  },
  {
    name: '신림',
    매출: '53,100,000',
    반품: '28,14%',
    판매: '66,700',
    판매sub: '13,420,956'
  },
  {
    name: '금천',
    매출: '53,100,000',
    반품: '28,14%',
    판매: '66,700',
    판매sub: '13,420,956'
  },
  {
    name: '목동',
    매출: '53,100,000',
    반품: '28,14%',
    판매: '66,700',
    판매sub: '13,420,956'
  },
  {
    name: '영업2팀',
    매출: '53,100,000',
    반품: '28,14%',
    판매: '66,700',
    판매sub: '13,420,956'
  },
  {
    name: '영등포',
    매출: '53,100,000',
    반품: '28,14%',
    판매: '66,700',
    판매sub: '13,420,956'
  },
  {
    name: '서대문',
    매출: '53,100,000',
    반품: '28,14%',
    판매: '66,700',
    판매sub: '13,420,956'
  },
  {
    name: '용산',
    매출: '53,100,000',
    반품: '28,14%',
    판매: '66,700',
    판매sub: '13,420,956'
  },
]);


const columns2 = [
  { name: 'name', label: '거래일자', align: 'center' },
  { name: '품목명', label: '품목명', align: 'center' },
  { name: '거래처명', label: '거래처명', align: 'center' },
  { name: '수량', label: '수량',align: 'center' },
  { name: '금액', label: '금액',align: 'center' },
]

const rows2 = ref([
  {
    name: '2024.02.14',
    품목명: '메가비타민C3000(크리스탈) 60P',
    거래처명: '(신림역) 엘림약국',
    수량: '2',
    금액: '37,000',
  },
  {
    name: '2024.02.14',
    품목명: '메가비타민C3000(크리스탈) 60P',
    거래처명: '(신림역) 엘림약국',
    수량: '2',
    금액: '37,000',
  },
  {
    name: '2024.02.14',
    품목명: '에비스칫솔(더프리미엄케어_치주포켓플러스_8015S) 12개입',
    거래처명: '(신림역) 엘림약국',
    수량: '2',
    금액: '37,000',
  },
  {
    name: '2024.02.14',
    품목명: '메가비타민C3000(크리스탈) 60P',
    거래처명: '거래처명',
    수량: '2',
    금액: '금액',
  },
  {
    name: '2024.02.14',
    품목명: '메가비타민C3000(크리스탈) 60P',
    거래처명: '거래처명',
    수량: '2',
    금액: '금액',
  },
]);



const filterbtnGroups = [
  {
    title: '정렬 순서',
    subtitle: '',
    gridStyle: 3,
    btnDatePicker: 'off',
    buttons: [
      { title: '기본정렬' },
      { title: '매출순' },
      { title: '판매순' },
    ]
  },
];



</script>

<style lang="scss" scoped>



.first_table_header_wrap {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  .table_header_item {
    @include fs-5;
    @include fw-7;
    text-align: center;
    padding-bottom: 8px !important;
    border-right: 1px solid rgba(0,0,0,0.1);
  }
  .table_header_item:last-child {
    border: none
  }
}

.first_table_wrap {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  border: none;
  .table_item{
    padding: 4px 0 !important;
    border-right: 1px solid rgba(0,0,0,0.1);
    td {
      @include flex_center;
      @include fs-6;
    }
  }
  .table_item:last-child {
    border: none
  }
}



.second_table_header_wrap {
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  .table_header_item {
    padding: 6px 0 !important;
    border-right: 1px solid rgba(0,0,0,0.1);
    span {
      @include fs-5;
      @include fw-7;
    }
  }
  .table_header_item:nth-child(1) {
    @include flex_center;
    grid-column: span 2;
  }
  .table_header_item:nth-child(2) {
    grid-column: span 6;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    .table_header_grid_item {
      padding: 2px 0 !important;
      text-align: center;
    }
    .table_header_grid_item:nth-child(1){
      grid-column: span 3 !important;
    }
  }
  .table_header_item:last-child {
    border: none;
  }
}

.second_table_wrap {
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  border: none;
  .table_item{
    border-right: 1px solid rgba(0,0,0,0.1);
    padding-bottom: 8px !important;
    td {
      @include fs-6;
    }
  }
  .table_item:nth-child(1) {
    @include flex_center;
    grid-column: span 2;
  }
  .table_item:nth-child(2) {
    grid-column: span 6;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    margin-left: 4px;
    .table_grid_item {
      padding: 2px 0 !important;
      text-align: center;
    }
    .table_grid_item:nth-child(1){
      grid-column: span 3 !important;
      td {
        text-align: left;
        white-space: normal;
      }
    }
    .table_grid_item:nth-child(2){
      td {
        @include flex_start;
        @include fw-7;
      }
    }
    .table_grid_item:nth-child(3){
      td {
        @include flex_center;
      }
    }
    .table_grid_item:nth-child(4){
      td {
        @include flex_end;
      }
    }
  }
  .table_item:last-child {
    border: none;
  }
}


</style>

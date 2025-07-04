<template lang="">
  <div>
    <div class="">
      <q-table
        class=""
        row-key="name"

        :rows="rows"
        :columns="columns"

        flat

        hide-bottom

        virtual-scroll
        :rows-per-page-options="[0]"
        :virtual-scroll-sticky-size-start="0"

        style="padding-bottom: 12px; border-bottom: 1px solid rgba(0,0,0, 0.1);"
      >
        <template v-slot:top>
          <div class="table_Top_wrap" style="margin-bottom:16px;">
            <div class="table_Title" style="padding: 16px 0 12px;">
              <span>{{ TableTitle }}</span>
            </div>
            <div class="flex_between" style="padding: 0 0 14px 0;">
              <div class="flex_start" style="gap: 10px;">
                <div class="table_Filter" @click="dialog_filter = true">
                  <!-- <img src="/public/assets/icons/sliders-solid.svg" alt="icon"> -->
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
          <div class="table_header_wrap">
            <div class="table_header_item">
              <span>{{ props.cols[0].label }}</span>
            </div>
            <div class="table_header_item">
              <span>{{ props.cols[1].label }}</span> <br/>
              <span>{{ props.cols[2].label }}</span>
            </div>
            <div class="table_header_item">
              <span>{{ props.cols[3].label }}</span> <br/>
              <span>{{ props.cols[4].label }}</span>
            </div>
          </div>
        </template>

        <template v-slot:body="props">
          <q-tr>
            <div class="table_wrap">
              <div class="table_item">
                <q-td>{{ props.row.name }}</q-td>
                <q-td v-if="props.row.sub주소 !== ''">{{ props.row.sub주소 }}</q-td>
                <q-td v-if="props.row.주소 !== ''">{{ props.row.주소 }}</q-td>
              </div>
              <div class="table_item">
                <div>
                  <q-td>{{ props.row.판매 }}</q-td>
                  <q-td>{{ props.row.반품 }}</q-td>
                </div>
              </div>
              <div class="table_item">
                <div>
                  <q-td>{{ props.row.이익율 }}</q-td>
                  <q-td>{{ props.row.입금 }}</q-td>
                </div>
              </div>
            </div>
          </q-tr>
        </template>
      </q-table>
    </div>
  </div>
</template>
<script setup>
import { ref,defineProps } from 'vue';
import TabLayout_mobile from 'src/layouts/TabLayouts/TabLayout_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import DatePickerComp_mobile from 'src/components/__commonComp/datepickerComp/DatePickerComp_mobile.vue';
import FilterComp_mobile from 'src/components/DialogComp/FilterDialogComp/FilterComp_mobile.vue';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import InputComp_mobile from 'src/components/__commonComp/inputComp/InputComp_mobile.vue';
import BtnFilterComp_mobile from 'src/components/__commonComp/btnComp/BtnFilterComp_mobile.vue';

const dialog_filter = ref(false)



const props = defineProps({
  TableTitle: {
    type: String,
    default: 'TableTitle'
  }
})



const columns = [
  { name: 'name', label: '거래처명', align: 'center' },
  { name: '판매', label: '판매', align: 'center' },
  { name: '반품', label: '반품', align: 'center' },
  { name: '이익율', label: '이익율',align: 'center' },
  { name: '입금', label: '입금',align: 'center' },
]

const rows = ref([
  {
    name: '마포',
    sub주소: '',
    주소: '',
    판매: '11,000,000,000',
    반품: '5,000,000,000',
    이익율: '6.3',
    입금: '16,481,169,516',
  },
  {
    name: '3층 365약국',
    sub주소: '',
    주소: '[장항동]',
    판매: '11,000,000,000',
    반품: '5,000,000,000',
    이익율: '6.3',
    입금: '16,481,169,516',
  },
  {
    name: 'W-Store',
    sub주소: '연세약국',
    주소: '[노고산동]',
    판매: '11,000,000,000',
    반품: '5,000,000,000',
    이익율: '6.3',
    입금: '16,481,169,516',
  },
  {
    name: '광장약국',
    sub주소: '',
    주소: '[대신동]',
    판매: '0',
    반품: '0',
    이익율: '6.3',
    입금: '0',
  },
]);



const filterbtnGroups = [
  {
    title: '정렬 순서',
    subtitle: '',
    gridStyle: 4,
    btnDatePicker: 'off',
    buttons: [
      { title: '거래처순' },
      { title: '판매순' },
      { title: '반품순' },
      { title: '입금순' },
    ]
  },
];



</script>

<style lang="scss" scoped>




.table_header_wrap {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  .table_header_item {
    text-align: center;
    padding-bottom: 8px !important;
    border-right: 1px solid rgba(0,0,0,0.1);
    span {
      @include fs-5;
      @include fw-7;
    }
  }
  .table_header_item:nth-child(1) {
    @include flex_center;
    grid-column: span 1;
  }
  .table_header_item:nth-child(2) {
    grid-column: span 2;
    span:nth-child(1) {
      @include fw-7;
    }
  }
  .table_header_item:nth-child(3) {
    grid-column: span 2;
    span:nth-child(1) {
      @include fw-7;
    }
  }
  .table_header_item:last-child {
    border: none
  }
}

.table_wrap {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  border: none;
  .table_item{
    padding: 8px 0 !important;
    border-right: 1px solid rgba(0,0,0,0.1);
    td {
      @include fs-6;
    }
  }
  .table_item:nth-child(1) {
    @include flex_center;
    flex-direction: column;
    grid-column: span 1;
    td {
      white-space: normal;
    }
  }
  .table_item:nth-child(2) {
    @include flex_end;
    grid-column: span 2;
    td {
      @include flex_end;
      margin-right: 4px;
    }
  }
  .table_item:nth-child(3) {
    @include flex_end;
    grid-column: span 2;
    td {
      @include flex_end;
      margin-right: 8px;
    }
  }
  .table_item:last-child {
    border: none
  }
}



</style>

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
                  <q-td>{{ props.row.잔고1개월 }}</q-td>
                  <q-td>{{ props.row.잔고3개월 }}</q-td>
                </div>
              </div>
              <div class="table_item">
                <div>
                  <q-td>{{ props.row.잔고2개월 }}</q-td>
                  <q-td>{{ props.row.수금예상액 }}</q-td>
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
  { name: '1개월잔고', label: '1개월잔고', align: 'center' },
  { name: '3개월잔고', label: '3개월잔고', align: 'center' },
  { name: '2개월잔고', label: '2개월잔고',align: 'center' },
  { name: '수금예상액', label: '수금예상액',align: 'center' },
]

const rows = ref([
  {
    name: '영업부',
    sub주소: '',
    주소: '',
    잔고1개월: '11,000,000,000',
    잔고3개월: '5,000,000,000',
    잔고2개월: '-',
    수금예상액: '16,481,169,516',
  },
  {
    name: '영업1팀',
    sub주소: '',
    주소: '[장항동]',
    잔고1개월: '11,000,000,000',
    잔고3개월: '5,000,000,000',
    잔고2개월: '-',
    수금예상액: '',
  },
  {
    name: '마포',
    sub주소: '',
    주소: '',
    잔고1개월: '11,000,000,000',
    잔고3개월: '5,000,000,000',
    잔고2개월: '-',
    수금예상액: '',
  },
  {
    name: '㈜ 신보',
    sub주소: '',
    주소: '',
    잔고1개월: '0',
    잔고3개월: '0',
    잔고2개월: '6.3',
    수금예상액: '0',
  },
  {
    name: '3층365약국',
    sub주소: '',
    주소: '[장항동]',
    잔고1개월: '11,000,000,000',
    잔고3개월: '5,000,000,000',
    잔고2개월: '-',
    수금예상액: '',
  },
]);



const filterbtnGroups = [
  {
    title: '정렬 순서',
    subtitle: '',
    gridStyle: 4,
    btnDatePicker: 'off',
    buttons: [
      { title: '전체' },
      { title: '사용안함' },
      { title: '말일입금' },
      { title: '15일입금' },
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

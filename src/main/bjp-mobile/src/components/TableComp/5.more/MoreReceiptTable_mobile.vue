<template>
  <div class="">
    <q-table
      class=""
      row-key="약"
      :rows="rows"

      flat

      hide-header
      hide-bottom

      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
    >
      <template v-slot:top>
        <div class="table_Top_wrap">
          <div class="table_Title" style="padding: 16px 0 12px;">
            <span>{{ TableTitle }}</span>
          </div>
          <div class="flex_between" style="padding: 0 0 14px 0;">
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
            <div class="flex_between" style="gap: 8px;">
              <InputComp_mobile
                placeholder="품목명"
                style="width: 148px;"
              />
              <InputComp_mobile
                placeholder="부서명"
                style="width: 78px;"
              />
            </div>
            <BtnComp_mobile
              buttonText="검색"
              btnStyle="default"
              style="width: 64px;"
            />
          </div>
          <div class="" style="padding: 0 0 16px 0;">
            <BtnGroupComp_mobile
              :buttons="['당월', '전월', '3개월', '6개월', '1년', '2년', '3년']"
              btnStyle="active"
            />
          </div>
          <div class="table_totalVal">
            <span class="table_total_Val">{{ rowval() }}</span><span class="table_total_Text">개가 검색되었습니다.</span>
          </div>
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item">
              <div class="table_badge_wrap">
                <template v-if="props.row.badge === 1 || props.row.badge === 3">
                  <q-badge class="table_badge_red">전</q-badge>
                </template>
                <template v-else>
                  <q-badge class="table_badge_red">일</q-badge>
                </template>
                <template v-if="props.row.badge === 1 || props.row.badge === 2">
                  <q-badge class="table_badge_blue">급</q-badge>
                </template>
                <template v-else>
                  <q-badge class="table_badge_blue">비</q-badge>
                </template>
              </div>
              <q-td>{{ props.row.약 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>
                <BtnComp_mobile
                  buttonText="상세"
                  btnStyle="default"
                  style="width: 30px;"
                  @click="dialog_상세 = true"
                />
                <DialogLayout_mobile
                  dialog_title="제품수불내역 > 제품수불내역 상세"
                  v-model:dialog_model_prop="dialog_상세"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <MoreReceiptComp_mobile
                      :가용재고="props.row.재고"
                      :제약사="props.row.제약사"
                    >
                    <div class="flex_start" style="font-weight: bold; gap: 4px; padding: 8px 0;">
                      <div class="table_badge_wrap">
                        <template v-if="props.row.badge === 1 || props.row.badge === 3">
                          <q-badge class="table_badge_red">전</q-badge>
                        </template>
                        <template v-else>
                          <q-badge class="table_badge_red">일</q-badge>
                        </template>
                        <template v-if="props.row.badge === 1 || props.row.badge === 2">
                          <q-badge class="table_badge_blue">급</q-badge>
                        </template>
                        <template v-else>
                          <q-badge class="table_badge_blue">비</q-badge>
                        </template>
                      </div>
                      <q-td>{{ props.row.약 }}</q-td>
                    </div>
                    </MoreReceiptComp_mobile>
                  </div>
                </DialogLayout_mobile>
              </q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.제약사 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>규격</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.규격 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.단가 }} 원</q-td>
            </div>
            <div class="table_item">
              <q-td>재고</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.재고 }}</q-td>
            </div>
          </div>
        </q-tr>



      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { mdiCheck, mdiHeart, mdiHeartOutline } from '@mdi/js';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import BottomComp_mobile from 'src/components/DialogComp/BottomDialogComp/BottomComp_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import InputComp_mobile from 'src/components/__commonComp/inputComp/InputComp_mobile.vue';
import ReturnRecordComp_a_mobile from 'src/components/DialogComp/2.returnDialogComp/ReturnRecordComp_a_mobile.vue';
import FilterComp_mobile from 'src/components/DialogComp/FilterDialogComp/FilterComp_mobile.vue';
import BtnGroupComp_mobile from 'src/components/__commonComp/btnComp/BtnGroupComp_mobile.vue';
import MoreReceiptComp_mobile from 'src/components/DialogComp/5.moreDialogComp/MoreReceiptComp_mobile.vue';
import BtnFilterComp_mobile from 'src/components/__commonComp/btnComp/BtnFilterComp_mobile.vue';

const dialog_full = ref(true)
const dialog_filter = ref(false)
const dialog_상세 = ref(false)






const props = defineProps({
  TableTitle: {
    type: String,
    default: 'TableTitle'
  }
})


const rows = ref([
  {
    badge: 1,
    약: '우루사정300mg(병)',
    제약사: '대웅제약바이엘코리아앙',
    규격: '30T',
    단가: 21200000,
    재고: 2300,
  },
]);



const filterbtnGroups = [
  {
    title: '조회필터',
    subtitle: '',
    gridStyle: 3,
    btnDatePicker: 'off',
    buttons: [
      { title: '소분제외' },
      { title: '소분만' },
      { title: '전체' },
    ]
  },
  {
    title: '기간설정',
    subtitle: '',
    gridStyle: 0,
    btnDatePicker: 'only-datapicker',

  },
  {
    title: '정렬순서',
    subtitle: '',
    gridStyle: 2,
    btnDatePicker: 'off',
    buttons: [
      { title: '최신순' },
      { title: '과거순' },
    ]
  },
];

const rowval = () => {
  return rows.value.length;
};


</script>

<style lang="scss" scoped>

.table_wrap {
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  padding: 8px 0 !important;
  gap: 4px;
  .table_item:nth-child(1){
    @include flex_start;
    @include fw-7;
    grid-column: span 9;
    gap: 2px;
    td {
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .table_item:nth-child(2){
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
  .table_item:nth-child(3){
    grid-column: span 8;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(4){
    grid-column: span 1;
    td {
      @include flex_center;
      @include fs-5;
    }
  }
  .table_item:nth-child(5){
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
      color: #666666;
    }
  }
  .table_item:nth-child(6){
    grid-column: span 8;
    td {
      @include flex_start;
      @include fs-5;
      @include fw-7;
    }
  }
  .table_item:nth-child(7){
    grid-column: span 1;
    td {
      @include flex_center;
      @include fs-5;
      @include fw-7;
    }
  }
  .table_item:nth-child(8){
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
      @include fw-7;
      color: #666666;
    }
  }
}





</style>

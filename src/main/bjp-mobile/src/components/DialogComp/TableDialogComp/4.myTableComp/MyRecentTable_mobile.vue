<template lang="">
  <div>
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
          <div class="flex_between" style="width: 100%">
            <div class="">
              <div class="table_totalVal">
                <span class="table_total_Text">총</span>
                <span class="table_total_Val">n</span>
                <span class="table_total_Text">건이 조회되었습니다.</span>
              </div>
              <div class="table_totalVal">
                <span class="table_total_Text">
                  주문금액(소계) 1,000,000원
                </span>
              </div>
            </div>
            <div class="">
              <div class="table_totalVal" style="justify-content: end !important;">
                <span class="table_total_Text">상세에서 품목별</span>
              </div>
              <div class="table_totalVal">
                <span class="blue-text bold-text">
                  재주문
                </span>
                <span class="table_total_Text">
                  이 가능합니다.
                </span>
              </div>
            </div>
          </div>
          <div class="border-top"></div>
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
                <q-td>{{ props.row.약 }} {{ props.row.규격 }}</q-td>
              </div>
              <div class="table_item">
                <q-td v-if="props.row.상태 === '접수'" class="blue-text">{{ props.row.상태 }}</q-td>
                <q-td v-else-if="props.row.상태 === '등록'" class="red-text">{{ props.row.상태 }}</q-td>
                <q-td v-else class="black-text">{{ props.row.상태 }}</q-td>
              </div>
              <div class="table_item">
                <q-td>주문</q-td>
              </div>
              <div class="table_item">
                <q-td>{{ props.row.주문일 }}</q-td>
              </div>
              <div class="table_item">
                <q-td>{{ props.row.주문시간 }}</q-td>
              </div>
              <div class="table_item">
                <q-td>{{ props.row.주문상태 }}</q-td>
              </div>
              <div class="table_item">
                <q-td><span>{{ props.row.종류 }}</span><span>종</span></q-td>
              </div>
              <div class="table_item">
                <q-td>금액</q-td>
              </div>
              <div class="table_item">
                <q-td>{{ props.row.금액 }}</q-td>
              </div>
              <div class="table_item">
                <q-td>승인</q-td>
              </div>
              <div class="table_item">
                <q-td>{{ props.row.승인일 }}</q-td>
              </div>
              <div class="table_item">
                <q-td>{{ props.row.승인시간 }}</q-td>
              </div>
              <div class="table_item">
                <q-td>{{ props.row.승인상태 }}</q-td>
              </div>
              <div class="table_item">
                <q-td>
                  <div>
                    <BtnComp_mobile
                      v-if="props.row.메모 === true"
                      buttonText="메모"
                      btnStyle="icon"
                      btnAlign="between"
                      :btnIcon="btnIcon"
                      style="width: 64px;"
                      @click="props.row.expand = !props.row.expand"
                    />
                  </div>
                  <div>
                    <BtnComp_mobile
                      v-if="props.row.상세 === true"
                      buttonText="상세"
                      btnStyle="default"
                      @click="dialog_상세 = true"
                      style="width: 32px;"
                    />
                    <DialogLayout_mobile
                      dialog_title="주문 상세 내역 (재주문)"
                      v-model:dialog_model_prop="dialog_상세"
                      :dialog_full="dialog_full"
                    >
                      <div class="">
                        <MyRecentDetailComp_mobile
                          :등록="props.row.주문일"
                          :등록시간="props.row.주문시간"
                          :구분="props.row.주문상태"
                          :승인="props.row.승인일"
                          :승인시간="props.row.승인시간"
                          :승인번호="props.row.승인번호"
                        />
                      </div>
                    </DialogLayout_mobile>
                  </div>
                </q-td>
              </div>
            </div>
          </q-tr>

          <q-tr v-show="props.row.expand" :props="props">
            <div class="expand_table_wrap">
              <div class="expand_table_item">
                <q-td>주문 메모 입력 내용</q-td>
              </div>
              <div class="expand_table_item">
                <q-td @click="props.row.expand = !props.row.expand">
                  <div class="">
                    <span>메모 닫기</span>
                    <span><img src="/public/assets/icons/sort-down-solid-close.svg" alt="icon"></span>
                  </div>
                </q-td>
              </div>
            </div>
          </q-tr>

        </template>
      </q-table>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import BottomComp_mobile from 'src/components/DialogComp/BottomDialogComp/BottomComp_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import InputComp_mobile from 'src/components/__commonComp/inputComp/InputComp_mobile.vue';
import ReturnRecordComp_a_mobile from 'src/components/DialogComp/2.returnDialogComp/ReturnRecordComp_a_mobile.vue';
import FilterComp_mobile from 'src/components/DialogComp/FilterDialogComp/FilterComp_mobile.vue';
import BtnGroupComp_mobile from 'src/components/__commonComp/btnComp/BtnGroupComp_mobile.vue';
import SalesDetailComp_b_mobile from 'src/components/DialogComp/3.salesDialogComp/SalesDetailComp_b_mobile.vue';
import TextAreaComp_mobile from 'src/components/DialogComp/__commonDialogComp/TextAreaComp_mobile.vue';
import MyRecentComp_mobile from 'src/components/DialogComp/4.myDialogComp/MyRecentComp_mobile.vue';
import MyRecentDetailComp_mobile from 'src/components/DialogComp/4.myDialogComp/MyRecentDetailComp_mobile.vue';


const expanded_model = ref(true)

const dialog_full = ref(true)
const dialog_상세 = ref(false)

const dialog_MyPage = ref(true);
const expanded_btn = () => {dialog_MyPage.value = true;};


const btnIcon = ref("img:/public/assets/icons/sort-down-white.svg");



const rows = ref([
  {
    badge: 1,
    약: '아즈정(피임)',
    규격: '30T',
    상태: '승인',
    주문일: '2023.01.01',
    주문시간: '08:09:35',
    주문상태: '매출',
    종류: 3,
    금액: 40000000,
    승인일: '2023.01.01',
    승인시간: '12:24:17',
    승인상태: '주문취소',
    메모: true,
    상세: true,
  },
  {
    badge: 1,
    약: '아즈정(피임)',
    규격: '30T',
    상태: '접수',
    주문일: '2023.01.01',
    주문시간: '08:09:35',
    주문상태: '매출',
    종류: 3,
    금액: 40000000,
    승인일: '2023.01.01',
    승인시간: '12:24:17',
    승인상태: '주문취소',
    메모: true,
    상세: true,
  },
  {
    badge: 1,
    약: '아즈정(피임)',
    규격: '30T',
    상태: '등록',
    주문일: '2023.01.01',
    주문시간: '08:09:35',
    주문상태: '매출',
    종류: 3,
    금액: 40000000,
    승인일: '2023.01.01',
    승인시간: '12:24:17',
    승인상태: '주문취소',
    메모: true,
    상세: true,
  },
]);



</script>

<style lang="scss" scoped>

.table_wrap {
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  padding: 8px 0 !important;
  gap: 4px 2px;

  td {
    @include fs-6;
    span {
      @include fs-6;
    }
  }
  .table_item:nth-child(1){
    @include flex_start;
    @include fw-7;
    grid-column: span 9;
    gap: 2px;
    td {
      font-size: 16px;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .table_item:nth-child(2){
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
      @include fw-7;
    }
  }
  .table_item:nth-child(3){
    grid-column: span 1;
    td {
      @include flex_start;
    }
  }
  .table_item:nth-child(4){
    grid-column: span 2;
    td {
      @include flex_start;
      color: #666666;
    }
  }
  .table_item:nth-child(5){
    grid-column: span 2;
    td {
      @include flex_start;
      color: #666666;
    }
  }
  .table_item:nth-child(6){
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(7){
    grid-column: span 1;
    td {
      @include flex_center;
      color: #666666;
    }
  }
  .table_item:nth-child(8){
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(9){
    grid-column: span 2;
    td {
      @include flex_end;
      color: #666666;
    }
  }
  .table_item:nth-child(10){
    grid-column: span 1;
    td {
      @include flex_start;
    }
  }
  .table_item:nth-child(11){
    grid-column: span 2;
    td {
      @include flex_start;
      color: #666666;
    }
  }
  .table_item:nth-child(12){
    grid-column: span 2;
    td {
      @include flex_start;
      color: #666666;
    }
  }
  .table_item:nth-child(13){
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(14){
    grid-column: span 4;
    td {
      @include flex_end;
      gap: 4px;
    }
  }
}


.expand_table_wrap {
  @include border-b;
  padding: 8px !important;
  .expand_table_item:nth-child(1) {
    td {
      white-space: normal;
    }
  }
  .expand_table_item:nth-child(2) {
    @include flex_center;
    margin-top: 16px;
    td {
      div {
        @include flex_center;
        gap: 8px;
        span {
          @include fs-5;
        }
      }
    }
  }
}






</style>

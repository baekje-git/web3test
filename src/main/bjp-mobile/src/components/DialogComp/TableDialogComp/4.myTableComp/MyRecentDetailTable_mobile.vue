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
        <div class="border-top" style="margin-top: 8px;"></div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item" @click="dialog_bottom_model = true, dialog_stock_model = true">
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
              <q-td>
                <q-checkbox
                  class="table_checkbox_heart"
                  :checked-icon="mdiHeart"
                  :unchecked-icon="mdiHeartOutline"
                  v-model="props.row.관심"
                  dense
                />
              </q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.제약사 }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.재고 }}개</q-td>
            </div>
            <div class="table_item">
              <q-td>단가</q-td>
            </div>
            <div class="table_item">
              <q-td><span>{{ props.row.단가 }}</span><span>원</span></q-td>
            </div>
            <div class="table_item">
              <q-td>
                <BtnComp_mobile
                  buttonText="출고이력"
                  btnStyle="default"
                  @click="dialog_record = true"
                  style="width: 64px;"
                />
                <DialogLayout_mobile
                  dialog_title="제품 출고이력 조회"
                  v-model:dialog_model_prop="dialog_record"
                >
                  <div class="">
                    <MyRecentDetailRecordComp_mobile>
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
                      {{ props.row.약 }}
                      {{ props.row.규격 }}
                    </MyRecentDetailRecordComp_mobile>
                  </div>
                </DialogLayout_mobile>
                <BtnComp_mobile
                  buttonText="제품정보"
                  btnStyle="icon"
                  btnAlign="between"
                  :btnIcon="btnIcon"
                  style="width: 80px;"
                  @click="props.expand = !props.expand"
                />
              </q-td>
            </div>
            <div class="table_item">
              <q-td>금액</q-td>
            </div>
            <div class="table_item">
              <q-td><span :class="{ 'text-red': props.row.금액 < 0 }">{{ props.row.금액 < 0 ? '-' : '' }}{{ Math.abs(props.row.금액) }}</span><span>원</span></q-td>
            </div>
          </div>
        </q-tr>


        <!-- expand -->
        <q-tr v-show="props.expand" :props="props">
          <q-markup-table separator="noen" flat dense>
            <tbody>
              <tr>
                <div class="table_expand_wrap">
                  <div class="table_expand_item">
                    <td>제품정보</td>
                    <div class="table_expand_item_sub">
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
                      <q-td>{{ props.row.약 }} {{ props.row.규격 }}</q-td>
                    </div>
                  </div>
                  <div class="table_expand_item">
                    <td>보험코드</td>
                    <td>{{ props.row.보험코드 }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>반품</td>
                    <td>{{ props.row.반품제약 }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>반품조건</td>
                    <td>{{ props.row.반품조건 }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>박스입수</td>
                    <td>
                      <span>(소)</span>
                      <span>{{ props.row.박스_소 }}</span>
                      <span>(중)</span>
                      <span>{{ props.row.박스_중 }}</span>
                      <span>(대)</span>
                      <span>{{ props.row.박스_대 }}</span>
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>최소주문</td>
                    <td>{{ props.row.최소주문 }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>최대주문</td>
                    <td>{{ props.row.최대주문 }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>보험약가</td>
                    <td>
                    {{ props.row.보험약가날짜 }} |
                    {{ props.row.보험약가가격 }}
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>입고일정</td>
                    <td>{{ props.row.입고일정 }}</td>
                  </div>
                  <div class="table_expand_close"  @click="props.expand = !props.expand">
                    <span>제품정보 닫기</span>
                    <span><img src="/public/assets/icons/sort-down-solid-close.svg" alt="icon"></span>
                  </div>
                </div>
              </tr>
            </tbody>
          </q-markup-table>
        </q-tr>


        <StockComp_mobile
          v-model:stock_model="dialog_stock_model"
        >
          <q-td>
            <template v-if="props.row.badge === 1 || props.row.badge === 3">
              <q-badge class="table_badge_red" style="font-size:14px;">전</q-badge>
            </template>
            <template v-else>
              <q-badge class="table_badge_red" style="font-size:14px;">일</q-badge>
            </template>
            <template v-if="props.row.badge === 1 || props.row.badge === 2">
              <q-badge class="table_badge_blue" style="font-size:14px;">급</q-badge>
            </template>
            <template v-else>
              <q-badge class="table_badge_blue" style="font-size:14px;">비</q-badge>
            </template>
            <span style="font-size:14px;">{{ props.row.약 }}</span>
          </q-td>
        </StockComp_mobile>


        <BottomComp_mobile
          v-model:dialog_bottom_model="dialog_bottom_model"
          BottomDialogStyle="fixed_bottom"

          :first_bottom_badge="props.row.badge"
          :first_bottom_약="props.row.약"
          :first_bottom_규격="props.row.규격"
          :first_bottom_배송="props.row.배송"
          :first_bottom_재고="props.row.재고"
          :first_bottom_가격="props.row.가격"
          :first_bottom_주문수량="rowval()"

          :second_bottom_badge="props.row.badge"
          :second_bottom_약="props.row.약"
          :second_bottom_규격="props.row.규격"
          :second_bottom_배송="props.row.배송"
          :second_bottom_재고="props.row.재고"
          :second_bottom_가격="props.row.가격"
          :second_bottom_주문수량="rowval()"

          first_addBtn="재주문 하기"
          second_backBtn="계속 주문"
          second_nextBtn="장바구니 이동"

          :useTotal="false"
          :useAmount="false"

        />


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
import FilterComp_mobile from 'src/components/DialogComp/FilterDialogComp/FilterComp_mobile.vue';
import BtnGroupComp_mobile from 'src/components/__commonComp/btnComp/BtnGroupComp_mobile.vue';
import MyRecentDetailRecordComp_mobile from 'src/components/DialogComp/4.myDialogComp/MyRecentDetailRecordComp_mobile.vue';
import StockComp_mobile from 'src/components/DialogComp/__commonDialogComp/StockComp_mobile.vue';


const dialog_stock_model = ref(false)
const dialog_bottom_model = ref(false)
const dialog_record = ref(false)



const btnIcon = ref("img:/public/assets/icons/sort-down-white.svg");


const rows = ref([
  {
    badge: 1,
    약: '휴마로그믹스25퀵펜네주양100단위/mL',
    규격: '10T',
    관심: true,
    단가: 21200000,
    제약사: '대웅제약바이엘코리아앙',
    금액: -124600000,
    배송: '광역',
    재고: 2300,
    가격: 120000,

    /* expand */
    보험코드: 641602040,
    반품제약: '반품제약',
    박스_소: 12,
    박스_중: 48,
    박스_대: 144,
    최소주문: '-',
    최대주문: 10,
    반품조건: '유효기간 6개월 미만 불가',
    보험약가날짜: '2024.01.01',
    보험약가가격: '25,000',
    입고일정: '24년 1월 중순 ~ 2월 초 예정',
  },
]);

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
      font-size: 15.5px;
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
    grid-column: span 4;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(4){
    grid-column: span 2;
    td {
      @include flex_end;
      @include fs-5;
      color: #666666;
    }
  }
  .table_item:nth-child(5){
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
    }
  }
  .table_item:nth-child(6){
    grid-column: span 3;
    td {
      @include flex_end;
      @include fs-5;
      gap: 2px;
      span {
        @include fs-5;
      }
    }
  }
  .table_item:nth-child(7){
    grid-column: span 6;
    td {
      @include flex_start;
      gap: 4px;
    }
  }
  .table_item:nth-child(8){
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
    }
  }
  .table_item:nth-child(9){
    grid-column: span 3;
    td {
      @include flex_end;
      gap: 2px;
      span {
        @include fs-5;
      }
      .text-red {
        color: $r1;
      }
    }
  }
}


.table_expand_wrap {
  .table_expand_item {
    td {
      @include fs-5;
    }
    td:nth-child(1) {
      @include fw-7;
      width: 70px;
    }
  }
  .table_expand_item:nth-child(1){
    @include flex_start;
    @include border-default;
    padding-bottom: 4px !important;
    td {
      @include flex_start;
      @include fw-7;
      font-size: 16px;
    }
    .table_expand_item_sub {
      @include flex_start;
      @include fw-7;
      grid-column: span 9;
      gap: 2px;
      max-width: 220px;
      td {
        display: block;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
  }
  .table_expand_item:nth-child(2){}
  .table_expand_item:nth-child(3){
    td:nth-child(2) {
      @include fw-7;
      color: $b4;
    }
  }
  .table_expand_item:nth-child(4){}
  .table_expand_item:nth-child(5){
    span:nth-child(odd) {
      @include fs-5;
      @include fw-7;
    }
    span:nth-child(even) {
      @include fs-5;
      margin-right: 8px;
    }
  }
  .table_expand_item:nth-child(6){}
}




</style>

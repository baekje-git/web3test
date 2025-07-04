<template lang="">
  <div>
    <ExpandLayout_mobile
      v-model:expanded_model="expanded_model"
      expand_title="마이페이지"
      expand_btn="세금계산서"
      expand_btnWidth="88"
      @expanded_btnClick="expanded_btn"
    >
      <DialogLayout_mobile
        dialog_title="세금계산서"
        v-model:dialog_model_prop="dialog_MyPage"
      >
        <div class="">
          <MyTaxComp_mobile/>
        </div>
      </DialogLayout_mobile>

      <q-table
        class=""
        row-key="날짜"
        :rows="rows"

        flat

        hide-header
        hide-bottom

        virtual-scroll
        :rows-per-page-options="[0]"
        :virtual-scroll-sticky-size-start="0"
      >
        <template v-slot:body="props">

          <q-tr class="table_container" @click="props.expand = !props.expand">
            <div class="table_wrap">
              <div class="table_item">
                <q-td>{{ props.row.신규}}</q-td>
              </div>
              <div class="table_item">
                <q-td>{{ props.row.약국명}}</q-td>
              </div>
              <div class="table_item">
                <q-td>대표약사</q-td>
              </div>
              <div class="table_item">
                <q-td>{{ props.row.약사}}</q-td>
              </div>
              <div class="table_item">
                <q-td><img src="/public/assets/icons/sort-down-solid.svg" alt="icon"></q-td>
              </div>
            </div>
          </q-tr>

          <!-- expand -->
          <q-tr v-show="props.expand" :props="props">
            <div class="expand_table_wrap" v-for="(items, i) in props.row.expandDate" :key="i">
              <div class="expand_table_item">
                <q-td>{{ items.지점 }}</q-td>
              </div>
              <div class="expand_table_item">
                <q-td>지점 연락처</q-td>
              </div>
              <div class="expand_table_item">
                <q-td>{{ items.지점연락처 }}</q-td>
              </div>
              <div class="expand_table_item">
                <q-td>영업사원</q-td>
              </div>
              <div class="expand_table_item">
                <q-td>{{ items.영업사원 }}</q-td>
              </div>
              <div class="expand_table_item">
                <q-td>{{ items.영업사원연락처 }}</q-td>
              </div>
              <div class="expand_table_item">
                <q-td>배송기사</q-td>
              </div>
              <div class="expand_table_item">
                <q-td>{{ items.배송기사 }}</q-td>
              </div>
              <div class="expand_table_item">
                <q-td>{{ items.배송기사연락처 }}</q-td>
              </div>
              <div class="expand_table_item" @click="props.expand = !props.expand">
                <q-td>
                  <div class="">
                    <span>기본정보 닫기</span>
                    <span><img src="/public/assets/icons/sort-down-solid-close.svg" alt="icon"></span>
                  </div>
                </q-td>
              </div>
            </div>
          </q-tr>

        </template>

      </q-table>
    </ExpandLayout_mobile>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import ExpandLayout_mobile from 'src/layouts/ExpandLayouts/ExpandLayout_mobile.vue';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import MyTaxComp_mobile from 'src/components/DialogComp/4.myDialogComp/MyTaxComp_mobile.vue';

const expanded_model = ref(true);

const dialog_MyPage = ref(false);
const expanded_btn = () => {dialog_MyPage.value = true;};


const rows = ref([
  {
    신규: '(NEW)',
    약국명: '3층파란문약국',
    약사: '홍길동',
    expandDate: [
      {
        지점: '백제약품 (주) 영등포지점',
        지점연락처: '02-862-0211',
        영업사원: '주현근',
        영업사원연락처: '010-0000-0000',
        배송기사: '홍길수',
        배송기사연락처: '010-0000-0000'
      }
    ],
  },
]);



</script>
<style lang="scss" scoped>

.table_container {
  &:hover {
    background-color: transparent;
  }
}

.table_wrap {
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  padding: 4px 8px !important;
  margin-top: 10px;
  gap: 0 6px;
  background-color: #eeeeee;
  border: none;
  td {
    @include fs-5;
  }
  .table_item:nth-child(1){
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(2){
    grid-column: span 6;
    td {
      @include flex_start;
    }
  }
  .table_item:nth-child(3){
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(4){
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(5){
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
}

.expand_table_wrap {
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  padding: 12px 4px !important;
  background-color: $b5;
  td {
    @include fs-5;
    padding: 4px 0 !important;
  }
  .expand_table_item:nth-child(1) {
    grid-column: span 8;
    td {

    }
  }
  .expand_table_item:nth-child(2) {
    grid-column: span 2;
    td {

    }
  }
  .expand_table_item:nth-child(3) {
    grid-column: span 6;
    td {
      color: #666666;
    }
  }
  .expand_table_item:nth-child(4) {
    grid-column: span 2;
    td {

    }
  }
  .expand_table_item:nth-child(5) {
    grid-column: span 1;
    td {
      color: #666666;
    }
  }
  .expand_table_item:nth-child(6) {
    grid-column: span 5;
    td {
      color: #666666;
    }
  }
  .expand_table_item:nth-child(7) {
    grid-column: span 2;
    td {

    }
  }
  .expand_table_item:nth-child(8) {
    grid-column: span 1;
    td {
      color: #666666;
    }
  }
  .expand_table_item:nth-child(9) {
    grid-column: span 5;
    td {
      color: #666666;
    }
  }
  .expand_table_item:nth-child(10) {
    grid-column: span 8;
    padding: 8px 0 4px !important;
    td {
      @include flex_center;
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

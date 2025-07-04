<template lang="">
  <div>
    <ExpandLayout_mobile
      v-model:expanded_model="expanded_model"
      expand_title="문의 답변"
      expand_btn="+ more"
      expand_btnWidth= 64
      @expanded_btnClick="expanded_btn"
    >
      <DialogLayout_mobile
          dialog_title="문의/답변"
          v-model:dialog_model_prop="dialog_MyPage"
        >
          <div class="">
            <MyQAComp_mobile/>
          </div>
      </DialogLayout_mobile>

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
          <q-tr @click="props.expand = !props.expand">
            <div class="table_wrap">
              <div class="table_item">
                <q-td>
                  <span>{{ props.row.제목 }}</span>
                  <span v-if="props.row.new === true" class="red-text">NEW</span>
                </q-td>
              </div>
              <div class="table_item">
                <q-td><span>{{ props.row.날짜 }}</span><span><img src="/public/assets/icons/sort-down-solid.svg" alt="icon"></span></q-td>
              </div>
            </div>
          </q-tr>

          <q-tr v-show="props.expand" :props="props">
            <div class="expand_table_wrap">
              <div class="expand_table_item">
                <q-td>{{ props.row.content }}</q-td>
              </div>
              <div class="expand_table_item">
                <q-td><span>답변</span><span>{{ props.row.result }}</span></q-td>
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
import MyQAComp_mobile from 'src/components/DialogComp/4.myDialogComp/MyQAComp_mobile.vue';

const expanded_model = ref(true)

const dialog_MyPage = ref(false);
const expanded_btn = () => {dialog_MyPage.value = true;};

const rows = ref([
  {
    new: true,
    제목: '언제 배송되나요?',
    날짜: '2022.04.21',
    content: '주문하신 품목은 오늘 배송 예정입니다. 감사합니다.',
    result: '2024.01.26'
  },
  {
    new: false,
    제목: '반품 처리가 완료 되었나요 ?',
    날짜: '2022.04.21',
    content: '반품 처리예정입니다. 감사합니다.',
    result: '2024.01.26'
  },
  {
    new: false,
    제목: '주문 취소 확인해 주시기 바랍니다.ELIPSIS TEST',
    날짜: '2022.04.21',
    content: '주문 취소 확인해 주시기 바랍니다. 감사합니다.',
    result: '2024.01.26'
  },
]);





</script>

<style lang="scss" scoped>


.table_wrap {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  padding: 4px 0 !important;
  td {
    span {
      @include fs-5;
    }
  }
  .table_item:nth-child(1){
    grid-column: span 5;
    max-width: 235px;
    td {
      @include flex_start;
      gap: 3px;
      span {
        display: block;
        overflow: hidden;
        text-overflow: ellipsis;
      }
      span:nth-child(2) {
        @include fw-7;

      }
    }
  }
  .table_item:nth-child(2){
    grid-column: span 1;
    td {
      @include flex_end;
      gap: 8px;
    }
  }
}


.expand_table_wrap {
  @include border-b;
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  padding: 8px 0 !important;
  background-color: #eeeeee;

  .expand_table_item:nth-child(1) {
    grid-column: span 1;
    td {
      @include fw-7;
      @include fs-5;
      white-space: normal;
    }
  }
  .expand_table_item:nth-child(2) {
    grid-column: span 1;
    margin-top: 4px;
    td {
      @include flex_start;
      gap: 4px;
      span {
        @include fs-5;
      }
    }
  }
}



</style>

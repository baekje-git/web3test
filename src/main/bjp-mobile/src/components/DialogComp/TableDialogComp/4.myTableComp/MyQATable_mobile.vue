<template lang="">
  <div>
    <q-table
      class=""
      row-key="문의날짜"
      :rows="rows"

      flat

      hide-header
      hide-bottom

      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
    >
      <template v-slot:top>
        <div class="border-top"></div>
      </template>
      <template v-slot:body="props">
        <q-tr @click="props.expand = !props.expand">
          <div class="table_wrap">
            <div class="table_item">
              <q-td><span>문의</span><span>{{ props.row.문의날짜 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>{{ props.row.품목상태 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>{{ props.row.상태 }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.문의 }}</q-td>
            </div>
          </div>
        </q-tr>

        <q-tr v-show="props.expand" :props="props">
          <div class="expand_table_wrap" v-if="props.row.답변 === ''">
            <div class="expand_table_item">
              <q-td class="table_btn">
                <BtnComp_mobile
                  buttonText="답변하기"
                  btnStyle="default"
                  @click="dialog_답변 = true"
                  style="width: 64px;"
                />
                <DialogLayout_mobile
                  dialog_title="문의/답변 > 답변하기"
                  v-model:dialog_model_prop="dialog_답변"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <div class ="expand_sub_table_wrap">
                      <div class="expand_sub_table_itme">
                        <q-td>문의종류</q-td>
                      </div>
                      <div class="expand_sub_table_itme">
                        <q-td>{{ props.row.품목상태 }}</q-td>
                      </div>
                      <div class="expand_sub_table_itme">
                        <q-td>문의자</q-td>
                      </div>
                      <div class="expand_sub_table_itme">
                        <q-td>{{ props.row.문의자 }}</q-td>
                      </div>
                      <div class="expand_sub_table_itme">
                        <q-td>제목</q-td>
                      </div>
                      <div class="expand_sub_table_itme">
                        <q-td>{{ props.row.문의 }}</q-td>
                      </div>
                      <div class="expand_sub_table_itme">
                        <q-td>답변내용</q-td>
                      </div>
                    </div>
                    <TextAreaComp_mobile
                      submit="답변하기"
                      style="margin-top: 16px;"
                    />
                  </div>
                </DialogLayout_mobile>
              </q-td>
            </div>
          </div>
          <div class="expand_table_wrap" v-else>
            <div class="expand_table_item">
              <q-td class="table_result">{{ props.row.답변 }}</q-td>
            </div>
            <div class="expand_table_item">
              <q-td><span>답변</span><span>{{ props.row.답변날짜 }}</span></q-td>
            </div>
          </div>
        </q-tr>

      </template>
    </q-table>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import TextAreaComp_mobile from 'src/components/DialogComp/__commonDialogComp/TextAreaComp_mobile.vue';

const dialog_답변 = ref(false)

const rows = ref([
  {
    문의날짜: '2024.01.01',
    문의자 : '테스트약국',
    품목상태: '품목',
    상태: '검토중',
    문의: '레스날린패취 0.5mg 30P 재고가 있나요?',

    답변: '레스날린패취 0.5mg 30P 재고는 있습니다.',
    답변날짜: '2024.01.01',
  },
  {
    문의날짜: '2024.01.02',
    문의자 : '테스트약국',
    품목상태: '품목',
    상태: '답변완료',
    문의: '레스날린패취 0.5mg 30P 재고가 있나요?',

    답변: '',
    답변날짜: '',
  },
  {
    문의날짜: '2024.01.03',
    문의자 : '테스트약국',
    품목상태: '품목',
    상태: '검토중',
    문의: '레스날린패취 0.5mg 30P 재고가 있나요?',

    답변: '레스날린패취 0.5mg 30P 재고는 있습니다.',
    답변날짜: '2024.01.01',
  },
]);





</script>

<style lang="scss" scoped>


.table_wrap {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  padding: 4px 0 !important;
  .table_item {
    padding: 2px 0 !important;
    td {
      @include fs-5;
      span {
        @include fw-7;
        @include fs-5;
      }
    }
  }
  .table_item:nth-child(1){
    grid-column: span 1;
    td {
      @include flex_start;
      gap: 6px;
    }
  }
  .table_item:nth-child(2){
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(3){
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
  .table_item:nth-child(4){
    grid-column: span 3;
    td {
      white-space: normal;
    }
  }
}


.expand_table_wrap {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  padding: 4px 0 !important;
  background-color: #eeeeee;
  .expand_table_item {
    padding: 2px 0 !important;
    td {
      @include fs-5;
      span {
        @include fs-5;
      }
    }
  }
  .expand_table_item:nth-child(1) {
    grid-column: span 1;
    .table_result {
      @include flex_start;
      @include fw-7;
      white-space: normal;
    }
    .table_btn {
      @include flex_center;
      padding: 28px 0 !important;
    }

  }
  .expand_table_item:nth-child(2) {
    grid-column: span 1;
    td {
      @include flex_start;
      gap: 4px;
    }
  }
}

.expand_sub_table_wrap {
  display: grid;
  grid-template-columns: 25% 75%;
  .expand_sub_table_itme {
    margin-bottom: 16px;
    td {
      @include fs-5;
    }
  }
  .expand_sub_table_itme:nth-child(odd) {
    td {
      @include fw-7;
    }
  }
  .expand_sub_table_itme:last-child {
    margin-top: 24px;
    margin-bottom: 0;
  }
}

</style>

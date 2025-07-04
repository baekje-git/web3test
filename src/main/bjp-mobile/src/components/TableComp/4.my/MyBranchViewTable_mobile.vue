<template lang="">
  <div>
    <ExpandLayout_mobile
      v-model:expanded_model="expanded_model"
      expand_title="지점 공지사항"
      expand_btn="+ more"
      expand_btnWidth= 64
      @expanded_btnClick="expanded_btn"
    >
      <DialogLayout_mobile
          dialog_title="지점 공지사항"
          v-model:dialog_model_prop="dialog_MyPage"
        >
          <div class="">
            <MyBranchComp_mobile/>
          </div>
      </DialogLayout_mobile>

      <q-table
        class=""
        row-key=""
        :rows="rows"

        flat

        hide-header
        hide-bottom

        virtual-scroll
        :rows-per-page-options="[0]"
        :virtual-scroll-sticky-size-start="0"
      >
        <template v-slot:body="props">
          <q-tr>
            <div class="table_wrap">
              <div class="table_item">
                <q-td>
                  <span v-if="props.row.badge === '필독'" class="blue-text">필독</span>
                  <span>{{ props.row.제목 }}</span>
                  <span v-if="props.row.new === true" class="red-text">NEW</span>
                </q-td>
              </div>
              <div class="table_item">
                <q-td><span>{{ props.row.날짜 }}</span></q-td>
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
import MyBranchComp_mobile from 'src/components/DialogComp/4.myDialogComp/MyBranchComp_mobile.vue';

const expanded_model = ref(false)

const dialog_MyPage = ref(false);
const expanded_btn = () => {dialog_MyPage.value = true;};


const rows = ref([
  {
    new: false,
    badge: '필독',
    제목: '지점공지사항' ,
    날짜: '2022.04.21',
  },
  {
    new: false,
    badge: '필독',
    제목: '지점공지사항 2',
    날짜: '2022.04.21',
  },
  {
    new: false,
    badge: '필독',
    제목: '지점공지사항 3',
    날짜: '2022.04.21',
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
    td {
      @include flex_start;
      gap: 3px;
      span:nth-child(1) {
        @include fw-7;
      }
      span:nth-child(3) {
        @include fw-7;
        @include fs-6;
      }
    }
  }
  .table_item:nth-child(2){
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
}



</style>

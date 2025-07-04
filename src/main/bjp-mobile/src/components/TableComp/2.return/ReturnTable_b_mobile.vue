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
          <div class="" style="margin-bottom: 16px;">
            <BtnGroupComp_mobile
              :buttons="['전체', '전문 n', '일반 n', '회수 n', '소분n', '소분 n', '소분 n']"
              btnStyle="active"
            />
          </div>
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item">
              <div class="table_badge_wrap">
                <template v-if="props.row.badge === 1 || props.row.badge === 3">
                  <q-badge class="table_badge_red">{{props.row.MED_NM}}</q-badge>
                </template>
                <template v-else>
                  <q-badge class="table_badge_red">{{props.row.MED_NM}}</q-badge>
                </template>
                <template v-if="props.row.badge === 1 || props.row.badge === 2">
                  <q-badge class="table_badge_blue">{{props.row.PAY_ITEM_NM}}</q-badge>
                </template>
                <template v-else>
                  <q-badge class="table_badge_blue">{{props.row.PAY_ITEM_NM}}</q-badge>
                </template>
              </div>
              <q-td>{{ props.row.ITEM_NM }} </q-td>
            </div>
            <div class="table_item">
              <q-td>
                <DeleteComp_mobile
                  delete_text1="반품"
                ><img src="/public/assets/icons/xmark-solid.svg" alt="icon"/></DeleteComp_mobile>
              </q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.ITEM_QTY }}</q-td>
            </div>
            <div class="table_item">
              <q-td v-if="props.row.EXP_DT !== ''">{{ props.row.EXP_DT }}</q-td>
              <q-td v-else>
                <DatePickerComp_mobile
                  style="width: 105px;"
                  @click="dialog_datepicker_required = true"
                />
                <RequiredComp_mobile
                  v-model:dialog_required_model="dialog_datepicker_required"
                  Required_text="유효기간"
                />
              </q-td>
            </div>
            <div class="table_item">
              <q-td v-if="props.row.SN !== ''">{{ props.row.PROD_NO }}</q-td>
              <q-td v-else>
                <InputComp_mobile
                  style="width: 84px;"
                  @click="dialog_input_required = true"
                />
                <RequiredComp_mobile
                  v-model:dialog_required_model="dialog_input_required"
                  Required_text="제조번호"
                />
              </q-td>
            </div>
          </div>
        </q-tr>


        <BottomComp_mobile
          v-model:dialog_bottom_model="dialog_bottom_model"

          :bottom_layout="true"
          :useTotal="true"
          :useAmount="false"

          first_addBtn="장바구니 담기"
          second_bottom_memoTitle="반품"
          second_backBtn="메모추가"
          second_nextBtn="주문등록"

          memo_dialog_title="반품 바구니 > 메모"
        />


      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, onMounted, inject } from 'vue';
import { api } from "boot/axios";
import * as util from "src/support/util";
import { mdiCheck, mdiHeart, mdiHeartOutline } from '@mdi/js';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import BottomComp_mobile from 'src/components/DialogComp/BottomDialogComp/BottomComp_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import InputComp_mobile from 'src/components/__commonComp/inputComp/InputComp_mobile.vue';
import ReturnRecordComp_a_mobile from 'src/components/DialogComp/2.returnDialogComp/ReturnRecordComp_a_mobile.vue';
import FilterComp_mobile from 'src/components/DialogComp/FilterDialogComp/FilterComp_mobile.vue';
import BtnGroupComp_mobile from 'src/components/__commonComp/btnComp/BtnGroupComp_mobile.vue';
import DeleteComp_mobile from 'src/components/DialogComp/__commonDialogComp/DeleteComp_mobile.vue';
import RequiredComp_mobile from 'src/components/DialogComp/__commonDialogComp/RequiredComp_mobile.vue';
import DatePickerComp_mobile from 'src/components/__commonComp/datepickerComp/DatePickerComp_mobile.vue';


const dialog_bottom_model = ref(true)
const dialog_datepicker_required = ref(false)
const dialog_input_required = ref(false)

const props = defineProps({
  TableTitle: {
    type: String,
    default: 'TableTitle'
  }
})
//****************************************************************************** */
const userInfo = util.getUserData();
const bus = inject("bus");
const rows = ref([]);
const loading = ref(false);


onMounted(async () => {
  await fetchBasketList();
});

const basketSearchParam = ref({
  userGbCd: userInfo.USER_GB_CD,
  custCd: userInfo.CUST_CD,
  basketGbCd: "02",
});

// 반품 장바구니 목록 조회
async function fetchBasketList() {
  loading.value = true;

  const res = await api.get("/bakRcpt/basketList", {
    params: basketSearchParam.value,
  });

  rows.value = res;
  loading.value = false;
}






////////////////////////////////////////////////////////////////////////////////////
const rows1 = ref([
  {
    badge: 1,
    약: '우루사정300mg(병)',
    규격: '30T',
    제약사: '대웅제약',
    수량: 5,
    날짜: '2025.12.01',
    SN: 'SN00058',
  },
  {
    badge: 1,
    약: '우루사정100mg(병)',
    규격: '30T',
    제약사: '대웅제약',
    수량: 5,
    날짜: '',
    SN: '',
  },
]);



</script>

<style lang="scss" scoped>

.table_wrap {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  padding: 6px 0 !important;
  gap: 0 8px;
  .table_item {
    td {
      height: 24px !important;
    }
  }
  .table_item:nth-child(1){
    @include flex_start;
    @include fw-7;
    grid-column: span 5;
    gap: 4px;
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
    grid-column: span 1;
    td {
      @include flex_start;
      @include fs-5;
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
    grid-column: span 2;
    td {
      @include flex_center;
      @include fs-5;
      color: #666666;
    }
  }
  .table_item:nth-child(6){
    grid-column: span 2;
    td {
      @include flex_center;
      @include fs-5;
      color: #666666;
    }
  }
}





</style>

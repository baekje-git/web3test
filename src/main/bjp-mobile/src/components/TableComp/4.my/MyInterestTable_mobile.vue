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
        <div class="table_Top_wrap">
          <div class="table_Title" style="padding: 16px 0 8px;">
            <span>{{ TableTitle }}</span>
          </div>
          <div class="" style="padding: 0 0 16px 0;">
            <BtnGroupComp_mobile
              :buttons="['입고알림', '알림품목', '기본그룹', '관심그룹1', '관심그룹2', '관심그룹3']"
              btnStyle="active"
            />
          </div>
          <div class="table_Title" style="padding: 0 0 8px 0;">
            <span>상품리스트</span>
          </div>
          <div class="flex_between" style="padding: 0 0 14px 0;">
            <div class="table_Filter" @click="dialog_filter = true">
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
            <div class="flex_between" style="gap: 10px;">
              <InputComp_mobile
                placeholder="보험코드/품명 입력"
                style="width: 110px;"
                v-model="searchParam.keyword"
                @update:model-value="input_model_props = $event"
              />
              <InputComp_mobile
                placeholder="제약사 입력"
                style="width: 110px;"
                v-model="searchParam.makerNm"
                @update:model-value="input_model_props = $event"
              />
            </div>
            <BtnComp_mobile
              buttonText="검색"
              btnStyle="default"
              style="width: 64px;"
              @click="onClickSearch"
            />
          </div>
        </div>
      </template>
      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item" @click="dialog_bottom_model = true, dialog_stock_model = true">
              <div class="table_badge_wrap">
                <template v-if="props.row.badge === 1 || props.row.badge === 3">
                  <q-badge class="table_badge_red">{{ props.row.MED_NM }}</q-badge>
                </template>
                <template v-else>
                  <q-badge class="table_badge_red">{{ props.row.MED_NM }}</q-badge>
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
                <span>알림</span>
                <ToggleComp_mobile
                  toggleLabel=""
                  Toggle_Style="Toggle_Switch_wrap"
                />
              </q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item">
              <q-td><span>단가</span><span>{{ util.getNumberFormat(props.row.ORD_WP2_AMT) }} 원</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>전월주</span><span>{{ props.row.BFR_MTH_ORD }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>당월주</span><span>{{ props.row.THS_MTH_ORD }}</span></q-td>
            </div>
            <div class="table_item">
              <q-td><span>월평사</span><span>{{ props.row.AVRG_MTNLY_USG }}</span></q-td>
            </div>
            <div class="table_item" >
              <q-td @click="onRowClick(props.row)">
                <BtnComp_mobile
                  buttonText="제품정보"
                  btnStyle="icon"
                  btnAlign="between"
                  :btnIcon="btnIcon"
                  style="width: 86px;"
                />
              </q-td>
            </div>
          </div>
        </q-tr>
        <!-- expand -->
        <q-tr v-show="props.row.expand" :props="props">
          <!-- <q-markup-table separator="noen" flat dense> -->
          <q-markup-table flat dense>
            <tbody>
              <tr>
                <div class="table_expand_wrap">
                  <div class="table_expand_item">
                    <td>제품정보</td>
                    <div class="table_expand_item_sub">
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
                      <q-td>{{ props.row.ITEM_NM }} </q-td>
                    </div>
                  </div>
                  <div class="table_expand_item">
                    <td>규격</td>
                    <td>{{ props.row.UNIT }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>반품</td>
                    <td class="red-text">{{ props.row.TAKEBACK ?? "-" }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>반품조건</td>
                    <td>{{ props.row.TAKEBACK_COND ?? "-"  }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>박스입수</td>
                    <td>
                      <span>(소)</span>
                      <span>{{ props.row.BOX_PROCURE_S ?? "-" }}</span>
                      <span>(중)</span>
                      <span>{{ props.row.BOX_PROCURE_M ?? "-" }}</span>
                      <span>(대)</span>
                      <span>{{ props.row.BOX_PROCURE_L ?? "-" }}</span>
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>최대주문</td>
                    <td>{{ props.row.MAX_ORD_QTY ?? "-" }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>입고일정</td>
                    <td>{{ props.row.INCMNG_SCHDL ?? "-" }}</td>
                  </div>
                  <div class="table_expand_close"  @click="props.row.expand = !props.row.expand">
                    <span>제품정보 닫기</span>
                    <span><img src="/public/assets/icons/sort-down-solid-close.svg" alt="icon"></span>
                  </div>
                </div>
              </tr>
            </tbody>
          </q-markup-table>
        </q-tr>

        <StockComp_mobile v-model:stock_model="dialog_stock_model" >
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
            <span style="font-size:14px;">{{ props.row.약 }} {{ props.row.규격 }}</span>
          </q-td>
        </StockComp_mobile>

        <BottomComp_mobile
          v-model:dialog_bottom_model="dialog_bottom_model"
          :first_bottom_badge="props.row.badge"
          :first_bottom_약="props.row.ITEM_NM"
          :first_bottom_규격="props.row.UNIT"
          :first_bottom_재고="props.row.재고"
          :first_bottom_가격="props.row.단가"
          :first_bottom_주문수량="rowval()"

          :second_bottom_badge="props.row.badge"
          :second_bottom_약="props.row.약"
          :second_bottom_규격="props.row.규격"
          :second_bottom_재고="props.row.재고"
          :second_bottom_가격="props.row.단가"
          :second_bottom_주문수량="rowval()"

          first_addBtn="장바구니 담기"
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
import { ref, defineProps, onMounted } from 'vue';
import { api } from "boot/axios";
import * as util from "src/support/util";

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
import ToggleComp_mobile from 'src/components/__commonComp/toggleComp/ToggleComp_mobile.vue';
import BtnFilterComp_mobile from 'src/components/__commonComp/btnComp/BtnFilterComp_mobile.vue';
import StockComp_mobile from 'src/components/DialogComp/__commonDialogComp/StockComp_mobile.vue';


const dialog_full = ref(true)
const dialog_filter = ref(false)

const dialog_bottom_model = ref(false)
const dialog_stock_model = ref(false)

const btnIcon = ref("img:/public/assets/icons/sort-down-white.svg");


const props = defineProps({
  TableTitle: {
    type: String,
    default: 'TableTitle'
  }
})

const rowval = () => {
  return rows.value.length;
};
//************************************************************************** */
const rows = ref([])

const userInfo = util.getUserData();
const loading = ref(false);


// 화면 초기 자동 조회
onMounted(async () => {
  await fetchItemList();
});

const searchParam = ref({
  searchType: "",
  custCd: userInfo.CUST_CD,
  searchOption: "0",
  keyword: "",
  makerNm: "",
  groupNo: "",
});

async function fetchItemList() {
  // selected.value = [];
  loading.value = true;

  const res = await api.get("/interest/groupItemList", {
    params: searchParam.value,
  });

  rows.value = res;
  loading.value = false;
}

// 검색 버튼 클릭
async function onClickSearch() {
  await fetchItemList();
}

// 제품정보
async function onRowClick(row) {
    for (var i = 0; i < rows.value.length; i++) {
      rows.value[i].expand = false;
    }
    if (!row.expand) {
      const res = await api.get("/ord/itemDetail", {
        params: { selItemCd: row.ITEM_CD },
      });

      row.TAKEBACK = res.TAKEBACK;
      row.TAKEBACK_COND = res.TAKEBACK_COND;
      row.BOX_PROCURE_S = res.BOX_PROCURE_S;
      row.BOX_PROCURE_M = res.BOX_PROCURE_M;
      row.BOX_PROCURE_L = res.BOX_PROCURE_L;
      row.MIN_ORD_QTY = res.MIN_ORD_QTY;
      row.MAX_ORD_QTY = res.MAX_ORD_QTY;
      row.POLICY_PRICE = res.POLICY_PRICE;
      row.INCMNG_SCHDL = res.INCMNG_SCHDL;
    }
    searchParam.value.ingno = row.ITEM_INGREDIENT_CD;
    searchParam.value.effno = row.ITEM_KIND_NO;
    row.expand = !row.expand;
}


/////////////////////////////////////////////////////////////////////////////
// const rows1 = ref([
//   {
//     badge: 1,
//     약: '아즈정(피임)',
//     규격: '30T',
//     제약사: '바이엘코리아',
//     단가: 18300,
//     전월주: 40,
//     당월주: 1000,
//     월평사: 100,
//     재고: 5,

//     /* expand */
//     보험코드: 641602040,
//     반품제약: '반품제한',
//     박스_소: 12,
//     박스_중: 48,
//     박스_대: 144,
//     최대주문: 10,
//     반품조건: '유효기간 6개월 미만 불가',
//     입고일정: '24년 1월 중순 ~ 2월 초 예정',
//   },
// ]);


const filterbtnGroups = [
  {
    title: '조회 필터',
    subtitle: '',
    gridStyle: 3,
    btnTitle: 'on',
    btnDatePicker: 'off',
    buttons: [
      { title: '전체' },
      { title: '전문' },
      { title: '일반' },
    ]
  },
  {
    title: '',
    subtitle: '',
    gridStyle: 3,
    btnTitle: 'off',
    btnDatePicker: 'off',
    buttons: [
      { title: '전체' },
      { title: '급여' },
      { title: '비급여' },
    ]
  },
];

</script>

<style lang="scss" scoped>

.table_wrap {
  display: grid;
  grid-template-columns: repeat(10, 1fr);
  padding: 8px 0 !important;
  gap: 4px 2px;
  .table_item {
    td {
      @include fs-6;
      span {
        @include fs-6;
      }
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
      gap: 4px;
    }
  }
  .table_item:nth-child(3){
    grid-column: span 6;
    td {
      @include flex_start;
      color: #666666;
    }
  }
  .table_item:nth-child(4){
    grid-column: span 4;
    td {
      @include flex_end;
      gap: 6px;
      span {
        @include fs-5;
      }
    }
  }
  .table_item:nth-child(5){
    grid-column: span 3;
    td {
      @include flex_start;
      gap: 4px;
      color: #666666;
    }
  }
  .table_item:nth-child(6){
    grid-column: span 3;
    td {
      @include flex_start;
      gap: 4px;
      color: #666666;
    }
  }
  .table_item:nth-child(7){
    grid-column: span 3;
    td {
      @include flex_start;
      gap: 4px;
      color: #666666;
    }
  }
  .table_item:nth-child(8){
    grid-column: span 1;
    td {
      @include flex_end;
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

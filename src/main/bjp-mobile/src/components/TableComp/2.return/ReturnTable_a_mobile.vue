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
          <div class="table_Title" style="padding: 16px 0 8px;">
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
              @click="onClickItemSearch"
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
              <q-td>{{ props.row.ITEM_NM }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item">
              <q-td>규격:{{ props.row.UNIT }}</q-td>
            </div>
            <div class="table_item">
              <q-td v-if="props.row.ORD_WP2_AMT === undefined">
                <span style="font-size:10px">ORD_WP2_AMT 컬럼없음</span>
                <!-- <span>{{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}원</span> -->
                </q-td>
            </div>
            <div class="table_item" @click="props.expand = !props.expand">
              <q-td v-if="props.row.WEB_BACK_USE_YN === 'Y'">
                <span>반품불가</span>
                <span><img src="/public/assets/icons/sort-down-solid.svg" alt="icon"></span>
              </q-td>
            </div>
            <div class="table_item">
              <q-td>반품가능수량 {{ props.row.LIMIT_QTY }}개</q-td>
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
                  dialog_title="반품 - 제품 출고이력 조회"
                  v-model:dialog_model_prop="dialog_record"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <ReturnRecordComp_a_mobile>
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
                      {{ props.row.ITEM_NM }}
                      {{ props.row.UNIT }} <span> : </span>
                      {{ props.row.MAKER_NM }}


                      <BottomComp_mobile
                        v-model:dialog_bottom_model="dialog_record"
                        BottomDialogStyle="fixed_bottom"

                        :second_bottom_badge="props.row.badge"
                        :second_bottom_약="props.row.약"
                        :second_bottom_규격="props.row.규격"
                        :second_bottom_배송="props.row.배송"
                        :second_bottom_재고="props.row.재고"
                        :second_bottom_가격="props.row.가격"
                        :second_bottom_주문수량="rowval()"

                        :bottom_layout="false"
                        :useAmount="true"
                        :useTotal="false"

                        first_addBtn="장바구니 담기"
                        second_backBtn="계속 반품"
                        second_nextBtn="반품 이동"

                        :first_amount="4"
                     />
                      <!--
                        :first_amount="4"
                        "4" 부분을 EAcount1 + EAcount2 = result
                        ReturnRecordTable_a_mobile(EAcount1, EAcount2)->
                        ReturnRecordComp_a_mobile->
                        value -> first_amount = result
                      -->
                    </ReturnRecordComp_a_mobile>
                  </div>
                </DialogLayout_mobile>


              </q-td>
            </div>
          </div>
        </q-tr>

        <!-- expand -->
        <q-tr v-show="props.expand" :props="props">
          <q-markup-table flat dense>
          <!-- <q-markup-table separator="noen" flat dense> -->
            <tbody>
              <tr>
                <div class="table_expand_wrap">
                  <div class="table_expand_item">
                    <td>{{ props.row.TAKEBACK }}</td>
                  </div>

                  <div class="table_expand_close"  @click="props.expand = !props.expand">
                    <span>상세정보 닫기</span>
                    <span><img src="/public/assets/icons/sort-down-solid-close.svg" alt="icon"></span>
                  </div>
                </div>
              </tr>
            </tbody>
          </q-markup-table>
        </q-tr>

      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue';
import { api } from "boot/axios";
import * as util from "src/support/util";
import { mdiCheck, mdiHeart, mdiHeartOutline } from '@mdi/js';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import BottomComp_mobile from 'src/components/DialogComp/BottomDialogComp/BottomComp_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import InputComp_mobile from 'src/components/__commonComp/inputComp/InputComp_mobile.vue';
import ReturnRecordComp_a_mobile from 'src/components/DialogComp/2.returnDialogComp/ReturnRecordComp_a_mobile.vue';
import FilterComp_mobile from 'src/components/DialogComp/FilterDialogComp/FilterComp_mobile.vue';
import BtnFilterComp_mobile from 'src/components/__commonComp/btnComp/BtnFilterComp_mobile.vue';

const dialog_full = ref(true)
const dialog_filter = ref(false)
const dialog_record = ref(false)

const props = defineProps({
  TableTitle: {
    type: String,
    default: 'TableTitle'
  }
})

const rowval = () => {
  return rows.value.length;
};

//------------------------------------------------------------------------
const userInfo = util.getUserData();
const loading = ref(false);
const bus = inject("bus");
const rows = ref([])

const searchParam = ref({
  keyword: "",
  makerNm: "",
  history: "Y",
  itemGbCd: "01",
  returnCat: "1",
  subdivYn: "",
  taxYn: "",
  retYn: "",
  custCd: userInfo.CUST_CD,
  custGbCd: userInfo.CUST_GB_CD,
  ordMakerCd: userInfo.ORD_MAKER_CD,
  userGbCd: userInfo.USER_GB_CD,
});

async function fetchItemList() {
  loading.value = true;
  const res = await api.get("/bakRcpt/itemSearch", {
    params: searchParam.value,
  });


  rows.value = res;
  loading.value = false;


  // 출고이력, 제품정보 그리드 초기화
  await bus.emit("bakRcptInit");
  await bus.emit("init");

  // 검색결과 있으면 첫번째 row 정보 뿌려줌(출고이력, 제품정보)
  if (rows.value.length > 0) {
    rows.value.forEach((m) =>
      Object.assign(m, {
        RETURN_CAT: searchParam.value.returnCat,
        SUBDIV_YN: searchParam.value.subdivYn,
      })
    );

    await bus.emit("bakRcptHistoryList", rows.value[0]);
    await bus.emit("itemDetailInfo", rows.value[0].ITEM_CD);
  }
}

// 검색 버튼 클릭
async function onClickItemSearch() {
  const keyword = searchParam.value.keyword;
  const makerNm = searchParam.value.makerNm;
  const returnCat = searchParam.value.returnCat;

  if (keyword.length === 0 && makerNm.length === 0) {
    alert("보험코드/품명을 입력하세요.");
    return;
  } else if (keyword.length !== 0 && keyword.length < 2) {
    alert("검색어는 2글자 이상이어야 합니다.");
    return;
  } else if (keyword.length === 0 && makerNm.length !== 0) {
    alert("보험코드/품명과 같이 입력해야합니다.");
    return;
  }

  // if (returnCat === "1") {
  //   // 일반반품
  //   searchParam.value.subdivYn = "N";
  //   searchParam.value.taxYn = "Y";
  //   searchParam.value.retYn = "N";
  // } else if (returnCat === "2") {
  //   // 소분반품
  //   searchParam.value.subdivYn = "Y";
  //   searchParam.value.taxYn = "Y";
  //   searchParam.value.retYn = "N";
  // } else if (returnCat === "3") {
  //   // 비과세반품
  //   searchParam.value.subdivYn = "N";
  //   searchParam.value.taxYn = "N";
  //   searchParam.value.retYn = "N";
  // } else if (returnCat === "4") {
  //   // 소분반품(회수)
  //   searchParam.value.subdivYn = "Y";
  //   searchParam.value.taxYn = "Y";
  //   searchParam.value.retYn = "Y";
  // } else if (returnCat === "6") {
  //   searchParam.value.subdivYn = "Y";
  //   searchParam.value.taxYn = "Y";
  //   searchParam.value.retYn = "N";
  // }

  await fetchItemList();
}






//******************************************************************** */
// const rows1 = ref([
//   {
//     badge: 1,
//     약: '우루사정300mg(병)',
//     제약사: '대웅제약',
//     규격: '30T',
//     가격: 120000,
//     상태: '반품불가',
//     반품수량: 5,

//     /* expand */
//     반품조건: '유효기간 6개월 미만 불가',
//   },
//   {
//     badge: 1,
//     약: '우루사정100mg(병)',
//     제약사: '대웅제약',
//     규격: '30T',
//     가격: 120000,
//     상태: '',
//     반품수량: 5,

//     /* expand */
//     반품조건: '유효기간 6개월 미만 불가',
//   },
// ]);



const filterbtnGroups = [
  {
    title: '조회 조건',
    subtitle: '중복가능',
    gridStyle: 2,
    btnDatePicker: 'off',
    buttons: [
      { title: '이력' },
      { title: '품질제외' },
    ]
  },
  {
    title: '조회필터',
    subtitle: '',
    gridStyle: 4,
    btnDatePicker: 'off',
    buttons: [
      { title: '일반반품', sub_title:''},
      { title: '비과세', sub_title:'반품'},
      { title: '소분반품', sub_title:''},
      { title: '소분반품', sub_title:'(회수)'},
    ]
  },
];




</script>

<style lang="scss" scoped>

.table_wrap {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  padding: 6px 0 !important;
  gap: 0 8px;
  .table_item:nth-child(1){
    @include flex_start;
    @include fw-7;
    grid-column: span 4;
    gap: 4px;
    td {
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .table_item:nth-child(2){
    grid-column: span 1;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(3){
    grid-column: span 1;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(4){
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
      @include fw-7;
    }
  }
  .table_item:nth-child(5){
    grid-column: span 2;
    td {
      @include flex_start;
      gap: 4px;
      span {
        @include fs-5;
        @include fw-7;
        color: $r1;
      }
    }
  }
  .table_item:nth-child(6){
    grid-column: span 1;
    td {
      @include flex_center;
      @include fs-5;
    }
  }
  .table_item:nth-child(7){
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
}



.table_expand_wrap {

  .table_expand_item {
    td {
      @include fw-7;
      @include fs-5;
    }
  }
}




</style>

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
          <div class="table_Title flex_between" style="padding: 16px 0 8px;">
            <span>{{ TableTitle }} </span>
            <div class="flex_center" style="gap: 8px;">
              <BtnComp_mobile
                buttonText="동일성분"
                btnStyle="default"
                style="width: 64px;"
                @click="onClickIng"
              />
              <BtnComp_mobile
                buttonText="동일효능"
                btnStyle="default"
                style="width: 64px;"
                @click="onClickEff"
              />
            </div>
          </div>
          <div class="flex_between" style="padding: 0 0 14px 0;">
            <div class="table_Filter" @click="dialog_filter = true">
              <!-- <img src="/public/assets/icons/sliders-solid.svg" alt="icon"> -->
              <BtnFilterComp_mobile
                :filterCheck="false"
              />
              <DialogLayout_mobile
                dialog_title="검색 필터"
                :dialog_full="dialog_full"
                v-model:dialog_model_prop="dialog_filter"
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
                v-model:input_model_props="searchParam.keyword"
                @keyup.enter="onClickItemSearch"
              />

              <InputComp_mobile
                placeholder="제약사 입력"
                style="width: 110px;"
                v-model:input_model_props="searchParam.makerNm"
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
        <q-tr @click="props.bottom_model = onViewBottom(props.row)">
          <div class="table_wrap">
            <div class="table_item">
                <div class="table_badge_wrap" >
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
              <q-td>
                <span>이력</span>
                <q-checkbox
                  class="table_checkbox"
                  :checked-icon="mdiCheck"
                  v-model="props.row.HISTORY_YN"
                  true-value="Y"
                  false-value="N"
                  dense
                />
                <q-checkbox
                  class="table_checkbox_heart"
                  :checked-icon="mdiHeart"
                  :unchecked-icon="mdiHeartOutline"
                  v-model="props.row.FAVORITE_YN"
                  true-value="Y"
                  false-value="N"
                  dense
                  @update:model-value="(val) => onCheckInterest(props.row, val)"
                />
              </q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item">
              <q-td>규격:{{ props.row.UNIT }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}원</q-td>
            </div>
            <div class="table_item">
              <q-td v-if="props.row.WEB_BACK_USE_YN === 'Y'">반품불가</q-td>
              <!-- <q-td> {{ props.row.WEB_BACK_USE_YN === 'Y' ? '반품불가' : '' }} </q-td> -->
            </div>
            <div class="table_item">
              <q-td>{{ props.row.G_DLV_BRCH_FLAG === "G" ? '광역' : ''}}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.AVAIL_STOCK > 0
                  ? "재고:" + props.row.AVAIL_STOCK
                  : "품절" }}</q-td>
            </div>
            <div class="table_item">
              <!-- <q-td @click="props.expand = !props.expand"> -->
              <q-td @click="props.expand = onRowDetailClick(props.row)">
                <span>상세정보</span>
                <img src="/public/assets/icons/sort-down-solid.svg" alt="icon">
              </q-td>
            </div>
          </div>
        </q-tr>

        <!-- expand -->
        <q-tr v-show="props.row.expand" :props="props">
          <q-markup-table flat="" dense="">
            <!-- <q-markup-table :separator="noen" flat="" dense=""> -->
            <tbody>
              <tr>
                <div class="table_expand_wrap">
                  <div class="table_expand_item">
                    <td>제품정보</td>
                    <td>{{ props.row.ITEM_NM }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>규격</td>
                    <td>{{ props.row.UNIT }}</td>

                    <div class="table_expand_record_btn">
                      <BtnComp_mobile
                        buttonText="출고이력"
                        btnStyle="default"
                        @click="dialog_record = true"
                        style="width: 64px;"
                      />
                      <DialogLayout_mobile
                        dialog_title="제품 출고이력 조회"
                        v-model:dialog_model_prop="dialog_record"
                        :dialog_full="dialog_full"
                      >
                        <div class="">
                          <OrderRecordComp_a_mobile>
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
                            {{ props.row.UNIT }}
                          </OrderRecordComp_a_mobile>
                        </div>
                      </DialogLayout_mobile>
                    </div>

                  </div>
                  <div class="table_expand_item">
                    <td>제약사</td>
                    <td>{{ props.row.MAKER_NM }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>보험코드</td>
                    <td>{{ props.row.BOHUM_CD }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>반품</td>
                    <td>{{ props.row.TAKEBACK ?? "-" }}</td>
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
                    <td>최소주문</td>
                    <td>{{ props.row.MIN_ORD_QTY ?? "-"  }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>최대주문</td>
                    <td>{{ props.row.MAX_ORD_QTY ?? "-"  }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>반품조건</td>
                    <td>{{ props.row.TAKEBACK_COND ?? "-" }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>보험약가</td>
                    <td>
                    <!-- {{ props.row.보험약가날짜 }} -->
                    {{ props.row.POLICY_PRICE ?? "-" }}
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>입고일정</td>
                    <td>{{ props.row.INCMNG_SCHDL ?? "-" }}</td>
                  </div>
                  <div class="table_expand_close"  @click="props.row.expand = !props.row.expand">
                    <span>상세정보 닫기</span>
                    <span><img src="/public/assets/icons/sort-down-solid-close.svg" alt="icon"></span>
                  </div>
                </div>
              </tr>
            </tbody>
          </q-markup-table>
        </q-tr>

        <BottomComp_mobile
          v-model:dialog_bottom_model="props.row.bottom_model"

          :first_bottom_badge="props.row.badge"
          :first_bottom_약="props.row.ITEM_NM"
          :first_bottom_규격="props.row.UNIT"
          :first_bottom_배송="props.row.G_DLV_BRCH_FLAG"
          :first_bottom_재고="props.row.AVAIL_STOCK"
          :first_bottom_가격="props.row.ORD_WP2_AMT"
          :first_bottom_주문수량="rowval()"
          :first_bottom_약코드="props.row.ITEM_CD"
          :first_bottom_GB코드="props.row.ITEM_GB_CD"
          :first_bottom_BRCH코드="props.row.DLV_BRCH_CD"



          :second_bottom_badge="props.row.badge"
          :second_bottom_약="props.row.ITEM_NM"
          :second_bottom_규격="props.row.UNIT"
          :second_bottom_배송="props.row.G_DLV_BRCH_FLAG"
          :second_bottom_재고="props.row.AVAIL_STOCK"
          :second_bottom_가격="props.row.ORD_WP2_AMT"
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
import { ref, inject } from 'vue';
import { api } from "boot/axios";
import * as util from "src/support/util";
import { mdiCheck, mdiHeart, mdiHeartOutline } from '@mdi/js';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import BottomComp_mobile from 'src/components/DialogComp/BottomDialogComp/BottomComp_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import InputComp_mobile from 'src/components/__commonComp/inputComp/InputComp_mobile.vue';
import OrderRecordComp_a_mobile from 'src/components/DialogComp/1.orderDialogComp/OrderRecordComp_a_mobile.vue';
import FilterComp_mobile from 'src/components/DialogComp/FilterDialogComp/FilterComp_mobile.vue';
import BtnFilterComp_mobile from 'src/components/__commonComp/btnComp/BtnFilterComp_mobile.vue';

const dialog_full = ref(true)          // 화면size 설정
const dialog_filter = ref(false)       // 검색 필터 개발 ???
const dialog_record = ref(false)       // 출고 이력 개발 ???
// const dialog_bottom_model = ref(true)  // 주문 담기 개발 ???


const bus = inject("bus");
const userInfo = util.getUserData();
const loading = ref(false);

const rows = ref([]);
const rowval = () => {
  return rows.value.length;
};

const props = defineProps({
  TableTitle: {
    type: String,
    default: 'TableTitle'
  }
})

// 검색 기능
const searchParam = ref({
  keyword: "",
  makerNm: "",
  history: "N", // 이력
  excludingOutOfOtock: "N", // 품절제외
  custCd: userInfo.CUST_CD,
  custGbCd: userInfo.CUST_GB_CD,
  ordMakerCd: userInfo.ORD_MAKER_CD,
  userGbCd: userInfo.USER_GB_CD,
  ing: "N",
  eff: "N",
  ingno: "",
  effno: "",
  searchAll: "Y", // 전체
  professionalYn: "Y", // 전문
  generalYn: "Y", // 일반
  paymentYn: "Y", // 급여
  nonPaymentYn: "Y", // 비급여
});


//검색버튼 클릭
async function onClickItemSearch() {
  const keyword = searchParam.value.keyword;
  const makerNm = searchParam.value.makerNm;

  if (keyword.length === 0 && makerNm.length === 0) {
    alert("보험코드/품명을 입력하세요.");
    return;
  } else if (keyword.length === 0 && makerNm.length !== 0) {
    alert("보험코드/품명과 같이 입력해야합니다.");
    return;
  }

  searchParam.value.ing = "N";
  searchParam.value.ingno = "";
  searchParam.value.eff = "N";
  searchParam.value.effno = "";



  await fetchItemList();
}

async function fetchItemList() {
  loading.value = true;
  const res = await api.get("/ord/itemSearch", { params: searchParam.value });
  for (var i = 0; i < res.length; i++) {
    res[i].expand = false;
    res[i].history = false;
    res[i].cart = false;
    res[i].req_qty = 1;
  }
  console.log("res.length :"+ res.length)

  rows.value = res;
  loading.value = false;

  // 출고이력, 제품정보 그리드 초기화
  bus.emit("init");

  // 검색결과 있으면 첫번째 row 정보 뿌려줌(출고이력, 제품정보)
  if (rows.value.length > 0) {
    await bus.emit("historyList", rows.value[0]);
    await bus.emit("itemDetailInfo", rows.value[0]);
  }

}

// 동일성분 버튼 클릭
async function onClickIng() {
  searchParam.value.ing = "Y";
  searchParam.value.eff = "N";

  await fetchItemList();
}

// 동일효능 버튼 클릭
async function onClickEff() {
  searchParam.value.ing = "N";
  searchParam.value.eff = "Y";

  await fetchItemList();
}

// 관심
async function onCheckInterest(row, val) {
  try {
    const interestItem = {
      userId: userInfo.USER_ID,
      custCd: userInfo.CUST_CD,
      itemCd: row.ITEM_CD,
    };

    if (val === "Y") {
      await api.post("/interest/addInterest", interestItem);
      util.showNotify("관심상품이 추가되었습니다.");
    } else {
      await api.delete("/interest/removeInterest", { params: interestItem });
      util.showNotify("관심상품이 삭제되었습니다.");
    }

    // await fetchBasketList();
  } catch (error) {
    alert(error.response.data);
  }
}

/** CLick event consol.log 에서 확인 가능 **/
const filterbtnGroups = [
  {
    title: '조회 조건',
    subtitle: '중복가능',
    gridStyle: 2,
    btnDatePicker: 'off',
    buttons: [
      { title: '이력', Click: '이력 btn' },
      { title: '품질제외', Click: '품질제외 btn' },
    ]
  },
  {
    title: '조회필터',
    subtitle: '',
    gridStyle: 3,
    btnDatePicker: 'off',
    buttons: [
      { title: '전체' , Click: '전체 btn' },
      { title: '전문' , Click: '전문 btn' },
      { title: '일반' , Click: '일반 btn' },
    ]
  },
  {
    subtitle: '',
    gridStyle: 3,
    btnDatePicker: 'off',
    btnTitle: 'off',
    buttons: [
      { title: '전체' , Click: '전체 btn' },
      { title: '급여' , Click: '급여 btn' },
      { title: '비급여' , Click: '비급여 btn' },
    ]
  },
];

// 상세보기
async function onRowDetailClick(row) {
  if (row.expand === false) {
    for (var i = 0; i < rows.value.length; i++) {
      rows.value[i].expand = false;
    }
    // if (!row.expand && row.TAKEBACK == undefined) {
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
  } else {
    row.expand = !row.expand
  }
}

async function onViewBottom(row) {
  for (var i = 0; i < rows.value.length; i++) {
      rows.value[i].bottom_model = false;
    }
    row.bottom_model = true;
}





//--------------------------------------------------------------------------//
// const rows = ref([
//   {
//     badge: 1,
//     약: '우루사정300mg(병)',
//     이력: true,
//     관심: false,
//     제약사: '대웅제약',
//     규격: '30T',
//     가격: 120000,
//     상태: '반품불가',
//     배송: '광역',
//     재고: 2300,
//     bottom_model: false,

//     /* expand */
//     보험코드: 641602040,
//     반품제약: '반품제약',
//     박스_소: 12,
//     박스_중: 48,
//     박스_대: 144,
//     최소주문: '-',
//     최대주문: 10,
//     반품조건: '유효기간 6개월 미만 불가',
//     보험약가날짜: '2024.01.01',
//     보험약가가격: '25,000',
//     입고일정: '24년 1월 중순 ~ 2월 초 예정',
//   },
// ]);
</script>

<style lang="scss" scoped>

.table_wrap {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
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
      @include flex_center;
      gap: 2px;
      span{
        @include fs-6;
      }
    }
  }
  .table_item:nth-child(3){
    grid-column: span 3;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(4){
    grid-column: span 2;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(5){
    grid-column: span 1;
    td {
      @include flex_start;
      @include fs-5;
      @include fw-7;
    }
  }
  .table_item:nth-child(6){
    grid-column: span 1;
    td {
      @include flex_center;
      @include fs-5;
      @include fw-7;
      color: $r1;
    }
  }
  .table_item:nth-child(7){
    grid-column: span 1;
    td {
      @include flex_center;
      @include fs-5;
      @include fw-7;
      color: $r1;
    }
  }
  .table_item:nth-child(8){
    grid-column: span 1;
    td {
      @include fs-6;
      @include fw-7;
    }
  }
  .table_item:nth-child(9){
    grid-column: span 1;
    td {
      @include flex_center;
      gap: 4px;
      span {
        @include fs-5;
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
    @include border-default;
    padding-bottom: 4px !important;
    td {
      @include fw-7;
      font-size: 16px;
    }
    td:nth-child(2) {
      color: $b3;
    }
  }
  .table_expand_item:nth-child(2){
    position: relative;
    margin-top: 4px;
    .table_expand_record_btn {
      position: absolute;
      top: 7%;
      right: 0;
    }
  }
  .table_expand_item:nth-child(3){

  }
  .table_expand_item:nth-child(4){

  }
  .table_expand_item:nth-child(5){
    td:nth-child(2) {
      @include fw-7;
      color: $b4;
    }
  }
  .table_expand_item:nth-child(6){
    span:nth-child(odd) {
      @include fs-5;
      @include fw-7;
    }
    span:nth-child(even) {
      @include fs-5;
      margin-right: 8px;
    }
  }
  .table_expand_item:nth-child(7){

  }
  .table_expand_item:nth-child(8){

  }
  .table_expand_item:nth-child(9){

  }

}




</style>

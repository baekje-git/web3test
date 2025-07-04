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
            <span>{{ TableTitle }}</span>
            <div class="flex_center" style="gap: 8px;">
              <BtnComp_mobile
                buttonText="전체"
                btnStyle="default"
                btnSize="md"
                style="width: 80px;"
                @click="onClickBtnAll"
              />
              <BtnComp_mobile
                :buttonText="btnWideAreaLabel"
                btnStyle="transparent"
                btnSize="md"
                style="width: 80px;"
                @click="onClickBtnWideArea"
              />
            </div>
          </div>
          <div class="flex_between" style="padding: 0 0 14px 0;">
            <!-- <BtnComp_mobile
              v-for="(items, i) in btnGroup" :key="i"
              :buttonText="items.종류"
              :sub_buttonText="items.건수"
              btnStyle="active"
              btnSize="lg"
              style="width: 64px;"
            /> -->
            <BtnComp_mobile
              buttonText="전체"
              :sub_buttonText="basketAll"
              btnStyle="active"
              btnSize="lg"
              style="width: 64px;"
              @click="onClickBtnAll"
            />
            <BtnComp_mobile
              buttonText="전문급여"
              :sub_buttonText="radioPrfssnlPmnt"
              btnStyle="active"
              btnSize="lg"
              style="width: 64px;"
            />
            <BtnComp_mobile
              buttonText="전문비급"
              :sub_buttonText="radioPrfssnlNonPmnt"
              btnStyle="active"
              btnSize="lg"
              style="width: 64px;"
            />
            <BtnComp_mobile
              buttonText="일반급여"
              :sub_buttonText="radioGnrlPmnt"
              btnStyle="active"
              btnSize="lg"
              style="width: 64px;"
            />
            <BtnComp_mobile
              buttonText="일반비급"
              :sub_buttonText="radioGnrlNonPmnt"
              btnStyle="active"
              btnSize="lg"
              style="width: 64px;"
            />
          </div>
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item" @click="dialog_record = true">
              <q-checkbox
                class="table_checkbox_gray"
                v-model="table_checkbox"
                :checked-icon="mdiCheckboxOutline "
                :unchecked-icon="mdiCheckboxBlankOutline "
                dense
              />
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


              <DialogLayout_mobile
                dialog_title="제품 출고이력"
                v-model:dialog_model_prop="dialog_record"
                :dialog_full="dialog_full"
              >
                <div class="">
                  <OrderRecordComp_c_mobile>
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
                  </OrderRecordComp_c_mobile>
                </div>
              </DialogLayout_mobile>


            </div>
            <div class="table_item">
              <q-td>
                <DeleteComp_mobile
                  delete_text1="장바구니"
                ><img src="/public/assets/icons/xmark-solid.svg" alt="icon"/></DeleteComp_mobile>
              </q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.G_DLV_BRCH_FLAG === "G" ? '광역' : '담기'}}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ props.row.AVAIL_STOCK > 0
                  ? "재고:" + props.row.AVAIL_STOCK
                  : "품절" }}</q-td>
            </div>
            <div class="table_item">
              <q-td>{{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}원</q-td>
            </div>
            <div class="table_item">
              <q-td>
                <EAInputComp_mobile
                  inputStyle="EA-sm"
                  style="width: 120px;"
                  v-model="EAcount"
                />{{ props.row.ITEM_QTY }}
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
          </div>
        </q-tr>



        <BottomComp_mobile
          v-model:dialog_bottom_model="dialog_bottom_model"

          :bottom_layout="true"
          :useTotal="true"
          :useAmount="false"

          first_addBtn="장바구니 주문"
          second_bottom_memoTitle="주문"
          second_backBtn="메모추가"
          second_nextBtn="주문등록"

          memo_dialog_title="장바구니 > 메모"

        />

      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, inject, onMounted, computed, toRaw } from "vue";
// import { computed, ref, onMounted, inject, toRaw } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { mdiCheck, mdiHeart, mdiHeartOutline, mdiCheckboxOutline, mdiCheckboxBlankOutline } from '@mdi/js';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import BottomComp_mobile from 'src/components/DialogComp/BottomDialogComp/BottomComp_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import InputComp_mobile from 'src/components/__commonComp/inputComp/InputComp_mobile.vue';
import OrderRecordComp_a_mobile from 'src/components/DialogComp/1.orderDialogComp/OrderRecordComp_a_mobile.vue';
import FilterComp_mobile from 'src/components/DialogComp/FilterDialogComp/FilterComp_mobile.vue';
import BtnGroupComp_mobile from 'src/components/__commonComp/btnComp/BtnGroupComp_mobile.vue';
import DeleteComp_mobile from 'src/components/DialogComp/__commonDialogComp/DeleteComp_mobile.vue';
import EAInputComp_mobile from 'src/components/__commonComp/inputComp/EAInputComp_mobile.vue';
import OrderRecordComp_c_mobile from 'src/components/DialogComp/1.orderDialogComp/OrderRecordComp_c_mobile.vue';
// import { json } from "stream/consumers";

//------------------------------------------------------------------------//
const props = defineProps({
  TableTitle: {
    type: String,
    default: 'TableTitle'
  }
})

const table_checkbox = ref(false)
const dialog_full = ref(true)
const dialog_record = ref(false)
const dialog_bottom_model = ref(true)

//----------------------------------------------------------//
const userInfo = util.getUserData();
const rows = ref([]);
const loading = ref(false);
const bus = inject("bus");
const selected = ref([]);
const EAcount = ref(0+123)


onMounted(async () => {
  await onClickBtnAll();
})

const basketSearchParam = ref({
  userGbCd: util.getUserData().USER_GB_CD,
  custCd: util.getUserData().CUST_CD,
  basketGbCd: "01",
  gDlvBrchFlag: "",
});

// 장바구니 목록 조회
async function fetchBasketList() {
  const res = await api.get("/ord/basketList", {
    params: basketSearchParam.value,
  });

  selected.value = [];
  loading.value = true;

  rows.value = res;
  loading.value = false;

  console.log(res[0].ITEM_QTY)
}

// 전체 버튼 클릭
async function onClickBtnAll() {
  basketSearchParam.value.gDlvBrchFlag = "";
  await fetchBasketList();
}

// 광역 버튼 클릭
async function onClickBtnWideArea() {
  basketSearchParam.value.gDlvBrchFlag = "G";
  await fetchBasketList();
}

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

const rowval = () => {
  return rows.value.length;
};

const basketAll = computed(() => {
  return `${rows.value.length} 건` //전체
});

const radioPrfssnlPmnt = computed(() => {
  const prfssnlPmntCnt = toRaw(rows.value).filter(
    (item) => item.PAY_ITEM_YN === "Y" && item.MED_CD === "02"
  ).length;
  return `${prfssnlPmntCnt} 건`; //전문급여
});

const radioPrfssnlNonPmnt = computed(() => {
  const prfssnlNonPmntCnt = toRaw(rows.value).filter(
    (item) => item.PAY_ITEM_YN === "N" && item.MED_CD === "02"
  ).length;
  return `${prfssnlNonPmntCnt} 건`; // 전문비급
});

const radioGnrlPmnt = computed(() => {
  const gnrlPmntCnt = toRaw(rows.value).filter(
    (item) => item.PAY_ITEM_YN === "Y" && item.MED_CD === "01"
  ).length;
  return `${gnrlPmntCnt} 건`; //일반급여
});

const radioGnrlNonPmnt = computed(() => {
  const prfssnlPmntCnt = toRaw(rows.value).filter(
    (item) => item.PAY_ITEM_YN === "N" && item.MED_CD === "01"
  ).length;
  return `${prfssnlPmntCnt}건`; //일반비급
});

const btnWideAreaLabel = computed(() => {
  const wideAreaCnt = toRaw(rows.value).filter(
    (item) => item.G_DLV_BRCH_FLAG === "G"
  ).length;
  return `광역 ${wideAreaCnt}건`;  //광역
});


//----------------------------------------------------------//
// const rows1 = ref([
//   {
//     badge: 1,
//     약: '우루사정300mg(병)',
//     규격: '30T',
//     제약사: '대웅제약',
//     배송: '광역',
//     재고: 2300,
//     가격: 120000,
//     관심: false,
//   },
// ]);
const btnGroup = [
  {
    종류: '전체',
    건수: '10건',
  },
  {
    종류: '전문급여',
    건수: '3건',
  },
  {
    종류: '전문비급',
    건수: '2건',
  },
  {
    종류: '일반급여',
    건수: '4건',
  },
  {
    종류: '일반비급',
    건수: '1건',
  },
];




</script>

<style lang="scss" scoped>

.table_wrap {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  padding: 6px 0 !important;
  .table_item {
    padding: 2px 0 !important;
  }
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
      @include flex_end;
    }
  }
  .table_item:nth-child(3){
    grid-column: span 2;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(4){
    grid-column: span 1;
    td {
      @include flex_center;
      @include fs-5;
      @include fw-7;
      color: $r1;
    }
  }
  .table_item:nth-child(5){
    grid-column: span 2;
    td {
      @include flex_end;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(6){
    grid-column: span 2;
    td {
      @include flex_start;
      @include fs-5;
      @include fw-7;
    }
  }
  .table_item:nth-child(7){
    grid-column: span 3;
    td {
      @include flex_end;
      gap: 4px;
    }
  }
}




</style>

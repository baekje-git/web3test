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
      :loading="loading"
      ref="tableRef"
    >
      <template v-slot:loading>
        <div class="m-loading_wrap" v-if="loading === true">
          <q-inner-loading class="loading" :showing="loading" color="primary" />
        </div>
      </template>
      <template v-slot:top>
        <div class="table_Top_wrap">
          <div class="table_Title_mobile flex_between">
            <span>{{ TableTitle }} </span>
            <div class="flex_end" style="gap: 8px">
              <BtnComp_mobile
                buttonText="동일성분"
                class="btn-line default"
                @click="onClickIng"
              />
              <BtnComp_mobile
                buttonText="동일효능"
                class="btn-line default"
                @click="onClickEff"
              />
            </div>
          </div>
          <div class="filter_area" @click="focus">
            <div class="flex_between" style="gap: 8px">
              <InputComp_mobile
                placeholder="보험코드/품명"
                style="width: 50%"
                v-model:input_model_props="searchParam.keyword"
                @keyup.enter="onClickItemSearch"
              />

              <InputComp_mobile
                placeholder="제약사"
                style="width: 50%"
                v-model:input_model_props="searchParam.makerNm"
                @keyup.enter="onClickItemSearch"
              />
            </div>
            <BtnComp_mobile
              buttonText="검색"
              class="btn_srch"
              @click="onClickItemSearch"
            />
            <div class="table_Filter" @click="dialog_filter = true">
              <!-- <img src="/src/assets/icons/sliders-solid.svg" alt="icon"> -->
              <BtnFilterComp_mobile :filterCheck="isFilterChecked" />
              <DialogLayout_mobile
                dialog_title="검색 필터"
                :dialog_full="dialog_full"
                v-model:dialog_model_prop="dialog_filter"
              >
                <div class="">
                  <!-- <FilterComp_mobile
                    :btnGroups="filterbtnGroups"
                    btnResult="on"
                  /> -->
                  <FilterMultipulComp_mobile
                    :btnGroups="filtergroup1"
                    :isActiveBtns="isActiveBtns2"
                    @selected-multi-btn="(val) => selectedGroup1(val)"
                  />
                  <FilterMultipulComp_mobile
                    :btnGroups="filtergroup2"
                    :isActiveBtns="isActiveBtns"
                    @selected-multi-btn="(val) => selectedGroup2(val)"
                  />
                  <BtnComp_mobile
                    :class="btnResult"
                    buttonText="적용"
                    btnStyle="default"
                    btnSize="lg"
                    class="btn-full"
                    style="padding-top: 20px"
                    @click="onClickFilterApply"
                  />
                </div>
              </DialogLayout_mobile>
            </div>
          </div>
          <div class="table_totalVal">
            <span class="table_total_Val">{{ rowval() }}</span
            ><span class="table_total_Text">개가 검색되었습니다.</span>
          </div>
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item table_item_mobile">
              <div class="table_badge_wrap">
                <!-- <q-badge class="table_badge_red">
                  {{ props.row.MED_NM }}
                </q-badge>
                <q-badge class="table_badge_blue">
                  {{ props.row.PAY_ITEM_NM }}
                </q-badge> -->
                <q-badge class="bdg-line normal">
                  {{
                    props.row.MED_NM === "일"
                      ? "일반"
                      : props.row.MED_NM === "전"
                      ? "전문"
                      : props.row.MED_NM
                  }}
                </q-badge>
                <q-badge
                  class="bdg-line"
                  :class="props.row.PAY_ITEM_NM === '비' ? 'nonpay' : 'pay'"
                >
                  {{
                    props.row.PAY_ITEM_NM === "급"
                      ? "급여"
                      : props.row.PAY_ITEM_NM === "비"
                      ? "비급여"
                      : props.row.PAY_ITEM_NM
                  }}
                </q-badge>
              </div>
              <!-- @click="props.bottom_model = onClickRow(props.row)" -->
              <q-td class="table-td_mobile td-prd_name">
                <div>
                  {{ props.row.ITEM_NM }}
                  <span
                    v-if="props.row.ITEM_GB_CD === '02'"
                    class="highlight-special"
                    >(특)</span
                  >
                </div>
                <div
                  v-if="props.row.PRICE_DOWN_INFO"
                  class="extra-info"
                  style="color: red"
                >
                  {{ props.row.PRICE_DOWN_INFO }}
                </div>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <div v-if="props.row.HISTORY_YN == 'Y'">
                  <span class="chk-history"></span>
                </div>
                <div v-else>
                  <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                </div>
                <!-- <q-checkbox
                  class="table_checkbox"
                  :checked-icon="mdiCheck"
                  v-model="props.row.HISTORY_YN"
                  true-value="Y"
                  false-value="N"
                  dense
                  disable
                /> -->
                <q-checkbox
                  class="table_checkbox_heart"
                  :checked-icon="mdiHeart"
                  :unchecked-icon="mdiHeart"
                  v-model="props.row.FAVORITE_YN"
                  true-value="Y"
                  false-value="N"
                  dense
                  @update:model-value="(val) => onCheckInterest(props.row, val)"
                />
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ props.row.MAKER_NM }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile"> 규격:{{ props.row.UNIT }} </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}원
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td
                v-if="props.row.WEB_BACK_USE_YN === 'Y'"
                class="table-td_mobile red-400"
              >
                반품불가
              </q-td>
              <q-td
                v-if="props.row.TAKEBACK_LIMIT_YN === 'Y'"
                class="table-td_mobile blue-400"
              >
                반품제한
              </q-td>
              <!-- <q-td> {{ props.row.WEB_BACK_USE_YN === 'Y' ? '반품불가' : '' }} </q-td> -->
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ props.row.G_DLV_BRCH_FLAG === "G" ? "광역" : "" }}
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile" v-if="props.row.AVAIL_STOCK > 0">
                {{ "재고:" + util.getNumberFormat(props.row.AVAIL_STOCK) }}
              </q-td>
              <q-td class="table-td_mobile" v-else>
                <span class="red-400">품절</span>
              </q-td>
            </div>
            <div
              class="table_item table_item_mobile"
              @click="props.row.bottom_model = false"
            >
              <!-- <q-td @click="props.expand = !props.expand"> -->
              <q-td
                class="table-td_mobile"
                @click="props.expand = onRowDetailClick(props.row)"
              >
                <span>상세정보</span>
                <img
                  :src="
                    props.row.expand
                      ? 'assets/images/arrw_up.svg'
                      : 'assets/images/arrw_dwn.svg'
                  "
                  alt="icon"
                />
              </q-td>
            </div>
            <div class="table_item" @click="props.expand = false">
              <div
                class="click_arear_order_a"
                @click="props.bottom_model = onClickRow(props.row)"
              ></div>
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
                    <!--<td>제품정보</td>-->
                    <td>
                      <p>{{ props.row.ITEM_NM }}</p>
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>규격</td>
                    <td>{{ props.row.UNIT }}</td>

                    <div class="table_expand_record_btn">
                      <BtnComp_mobile
                        buttonText="출고이력"
                        btnStyle="default"
                        class="btn-line default"
                        @click="props.row.dialog_record = true"
                      />
                      <DialogLayout_mobile
                        dialog_title="제품 출고이력 조회"
                        v-model:dialog_model_prop="props.row.dialog_record"
                        :dialog_full="dialog_full"
                      >
                        <div>
                          <OrderRecordComp_a_mobile
                            :mednm="props.row.MED_NM"
                            :payitemnm="props.row.PAY_ITEM_NM"
                            :itemnm="props.row.ITEM_NM"
                            :unit="props.row.UNIT"
                            :itemcd="props.row.ITEM_CD"
                            v-model="isActiveBtn"
                          >
                          </OrderRecordComp_a_mobile>
                        </div>
                      </DialogLayout_mobile>
                    </div>
                  </div>
                  <div class="table_expand_item">
                    <td>제약회사</td>
                    <td>{{ props.row.MAKER_NM }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>보험코드</td>
                    <td>{{ props.row.BOHUM_CD }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>반품</td>
                    <td>
                      <p
                        v-if="props.row.WEB_BACK_USE_YN === 'Y'"
                        class="red-400"
                      >
                        불가
                      </p>
                      <p
                        v-if="props.row.TAKEBACK_LIMIT_YN === 'Y'"
                        class="blue-400"
                      >
                        제한
                      </p>
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>박스입수</td>
                    <td>
                      <span>(소)</span>
                      <span>
                        {{
                          props.row.SMALLBOX_QTY > 0
                            ? `${util.getNumberFormat(
                                props.row.SMALLBOX_QTY
                              )}개입`
                            : util.getNumberFormat(props.row.SMALLBOX_QTY)
                        }} </span
                      >&#160;
                      <span>(중)</span>
                      <span>
                        {{
                          props.row.MIDBOX_QTY > 0
                            ? `${util.getNumberFormat(
                                props.row.MIDBOX_QTY
                              )}개입`
                            : util.getNumberFormat(props.row.MIDBOX_QTY)
                        }} </span
                      >&#160;
                      <span>(대)</span>
                      <span>
                        {{
                          props.row.LARGEBOX_QTY > 0
                            ? `${util.getNumberFormat(
                                props.row.LARGEBOX_QTY
                              )}개입`
                            : util.getNumberFormat(props.row.LARGEBOX_QTY)
                        }}
                      </span>
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>최소주문</td>
                    <td>
                      {{
                        props.row.MIN_ORD_QTY === 1
                          ? ""
                          : util.getNumberFormat(props.row.MIN_ORD_QTY)
                      }}
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>최대주문</td>
                    <td>
                      {{
                        props.row.MAX_ORD_QTY
                          ? util.getNumberFormat(props.row.MAX_ORD_QTY)
                          : ""
                      }}
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>반품조건</td>
                    <td>{{ props.row.WEB_BACK_MEMO ?? "-" }}</td>
                  </div>
                  <div class="table_expand_item">
                    <td>포장형태</td>
                    <td>
                      {{ props.row.PACK_NM }}
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>입고일정</td>
                    <td>{{ props.row.SOLDOUT_NOTE ?? "-" }}</td>
                  </div>
                  <div
                    class="table_expand_close"
                    @click="props.row.expand = !props.row.expand"
                  >
                    <span>상세정보 닫기</span>
                    <span>
                      <img src="assets/images/arrw_up.svg" alt="icon" />
                    </span>
                  </div>
                </div>
              </tr>
            </tbody>
          </q-markup-table>
        </q-tr>

        <BottomComp_mobile_order
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
          :first_bottom_mednm="props.row.MED_NM"
          :first_bottom_payitemnm="props.row.PAY_ITEM_NM"
          :first_bottom_GBRCH코드="props.row.G_DLV_BRCH_CD"
          :second_bottom_badge="props.row.badge"
          :second_bottom_약="props.row.ITEM_NM"
          :second_bottom_규격="props.row.UNIT"
          :second_bottom_배송="props.row.G_DLV_BRCH_FLAG"
          :second_bottom_재고="props.row.AVAIL_STOCK"
          :second_bottom_가격="props.row.ORD_WP2_AMT"
          :second_bottom_주문수량="rowval()"
          :first_addBtn="
            props.row.G_DLV_BRCH_FLAG === 'G'
              ? '(광역) 장바구니 담기'
              : '장바구니 담기'
          "
          second_backBtn="계속 주문"
          second_nextBtn="장바구니 이동"
          :useTotal="false"
          :useAmount="false"
          flag="addOrdBasket"
          :ordQty="true"
        />
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, inject, onMounted } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { mdiCheck, mdiHeart, mdiHeartOutline } from "@mdi/js";
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BottomComp_mobile from "src/components/mobile/DialogComp/BottomDialogComp/BottomComp_mobile.vue";
import BottomComp_mobile_order from "src/components/mobile/DialogComp/BottomDialogComp/BottomComp_mobile_order.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import InputComp_mobile from "src/components/mobile/__commonComp/inputComp/InputComp_mobile.vue";
import OrderRecordComp_a_mobile from "src/components/mobile/DialogComp/1.orderDialogComp/OrderRecordComp_a_mobile.vue";
import FilterComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterComp_mobile.vue";
import BtnFilterComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnFilterComp_mobile.vue";
import FilterMultipulComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterMultipulComp_mobile.vue";

const tableRef = ref();
const dialog_full = ref(true); // 화면size 설정
const dialog_filter = ref(false); // 검색 필터 개발 ???
// const dialog_record = ref(false); // 출고 이력 개발 ???
// const dialog_bottom_model = ref(true)  // 주문 담기 개발 ???

const bus = inject("bus");
const userInfo = util.getUserData();
const isFilterChecked = ref(false);
//로딩
const loading = ref(true);
const show = ref(false);
const nodata_show = ref(false);

const rows = ref([]);
const rowval = () => {
  return rows.value.length;
};

const props = defineProps({
  TableTitle: {
    type: String,
    default: "TableTitle",
  },
});

const group1List = ref([]);
const group2List = ref(["전체", "전문", "일반", "급여", "비급여"]);

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

function selectedGroup1(val) {
  group1List.value = val;
}

function selectedGroup2(val) {
  group2List.value = val;
}

//필터표시
const isActiveBtn = ref();
const isActiveBtns = ref([]);

const isActiveBtns2 = ref([]);

const focus = () => {
  if (rows.value.length > 0) {
    for (var i = 0; i < rows.value.length; i++) {
      rows.value[i].bottom_model = false;
    }
  }
};

// 필터 적용 버튼 클릭
async function onClickFilterApply() {
  dialog_filter.value = false;

  if (group1List.value.length > 0) {
    //필터표시 초기화
    isActiveBtns2.value.splice(0);

    searchParam.value.history =
      group1List.value.indexOf("이력") !== -1 ? "Y" : "N";

    searchParam.value.excludingOutOfOtock =
      group1List.value.indexOf("품절제외") !== -1 ? "Y" : "N";

    //필터표시
    for (let i = 0; i < filtergroup1[0].buttons.length; i++) {
      if (
        group1List.value.indexOf(filtergroup1[0].buttons[i].btnLabel) !== -1
      ) {
        isActiveBtns2.value.push(i);
      }
    }
  } else {
    searchParam.value.history = "N";
    searchParam.value.excludingOutOfOtock = "N";
  }

  isActiveBtns.value.splice(0);

  if (group2List.value.length > 0) {
    searchParam.value.searchAll =
      group2List.value.indexOf("전체") !== -1 ? "Y" : "N";
    searchParam.value.professionalYn =
      group2List.value.indexOf("전문") !== -1 ? "Y" : "N";
    searchParam.value.generalYn =
      group2List.value.indexOf("일반") !== -1 ? "Y" : "N";
    searchParam.value.paymentYn =
      group2List.value.indexOf("급여") !== -1 ? "Y" : "N";
    searchParam.value.nonPaymentYn =
      group2List.value.indexOf("비급여") !== -1 ? "Y" : "N";

    //필터표시
    for (let i = 0; i < filtergroup2[0].buttons.length; i++) {
      if (
        group2List.value.indexOf(filtergroup2[0].buttons[i].btnLabel) !== -1
      ) {
        isActiveBtns.value.push(i);
      }
    }
  } else {
    // searchParam.value.searchAll = "Y";
    // searchParam.value.professionalYn = "Y";
    // searchParam.value.generalYn = "Y";
    // searchParam.value.paymentYn = "Y";
    // searchParam.value.nonPaymentYn = "Y";
    // //필터표시
    // filtergroup2[0].buttons.forEach((el, index) => {
    //   isActiveBtns.value.push(index);
    // });
  }

  //필터 체크표시
  isFilterChecked.value = true;
  await onClickItemSearch();
}

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
  } else if (keyword.length < 2) {
    alert("검색어는 2글자 이상이어야 합니다.");
    return;
  }

  //조회필터 체크
  if (isActiveBtns.value.length === 0) {
    alert("조회 조건을 선택해 주시기 바랍니다.");
    return;
  }

  searchParam.value.ing = "N";
  searchParam.value.ingno = "AAAAAAAAAAAAAA";
  searchParam.value.eff = "N";
  searchParam.value.effno = "AAAAAAAAAAAA";
  bus.emit("scrollUp");
  await fetchItemList();
}

async function fetchItemList() {
  loading.value = true;
  rows.value = [];

  const res = await api.get("/ord/itemSearch", { params: searchParam.value });
  for (var i = 0; i < res.length; i++) {
    res[i].expand = false;
    res[i].history = false;
    res[i].cart = false;
    res[i].req_qty = 1;
  }
  rows.value = res;
  loading.value = false;

  if (rows.value.length > 0) {
    rows.value.forEach((m) => {
      Object.assign(m, { dialog_record: false });
    });
    searchParam.value.ingno = rows.value[0].ITEM_INGREDIENT_CD;
    searchParam.value.effno = rows.value[0].ITEM_KIND_NO;
  }

  // 출고이력, 제품정보 그리드 초기화
  // bus.emit("init");

  // 검색결과 있으면 첫번째 row 정보 뿌려줌(출고이력, 제품정보)
  // if (rows.value.length > 0) {
  //   await bus.emit("historyList", rows.value[0]);
  //   await bus.emit("itemDetailInfo", rows.value[0]);
  // }
}

// 동일성분 버튼 클릭
async function onClickIng() {
  searchParam.value.ing = "Y";
  searchParam.value.eff = "N";
  searchParam.value.keyword = "";

  if (searchParam.value.ingno === "") {
    searchParam.value.ingno = "AAAAAAAAAAAAAA";
  }

  await fetchItemList();
}

// 동일효능 버튼 클릭
async function onClickEff() {
  searchParam.value.ing = "N";
  searchParam.value.eff = "Y";
  searchParam.value.keyword = "";

  if (searchParam.value.effno === "") {
    searchParam.value.effno = "AAAAAAAAAAAA";
  }

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
      util.showNotifyTop("관심상품이 추가되었습니다.");
    } else {
      await api.delete("/interest/removeInterest", { params: interestItem });
      util.showNotifyTop("관심상품이 삭제되었습니다.");
    }

    // await fetchBasketList();
  } catch (error) {
    alert(error.response.data);
  }
}

/** CLick event consol.log 에서 확인 가능 **/
const filterbtnGroups = [
  {
    title: "조회 조건",
    subtitle: "중복가능",
    gridStyle: 2,
    btnDatePicker: "off",
    buttons: [
      { title: "이력", Click: "이력 btn" },
      { title: "품질제외", Click: "품질제외 btn" },
    ],
  },
  {
    title: "조회필터",
    subtitle: "",
    gridStyle: 3,
    btnDatePicker: "off",
    buttons: [
      { title: "전체", Click: "전체 btn" },
      { title: "전문", Click: "전문 btn" },
      { title: "일반", Click: "일반 btn" },
    ],
  },
  {
    subtitle: "",
    gridStyle: 3,
    btnDatePicker: "off",
    btnTitle: "off",
    buttons: [
      { title: "전체", Click: "전체 btn" },
      { title: "급여", Click: "급여 btn" },
      { title: "비급여", Click: "비급여 btn" },
    ],
  },
];

const filtergroup1 = [
  {
    title: "조회 조건",
    subtitle: "복수선택",
    gridStyle: 2,
    buttons: [
      {
        btnLabel: "이력",
      },
      {
        btnLabel: "품절제외",
      },
    ],
  },
];
const filtergroup2 = [
  {
    title: "조회 필터",
    subtitle: "복수선택",
    gridStyle: 3,
    buttons: [
      {
        btnLabel: "전체",
      },
      {
        btnLabel: "전문",
      },
      {
        btnLabel: "일반",
      },
      {
        btnLabel: "급여",
      },
      {
        btnLabel: "비급여",
      },
    ],
  },
];

// 상세보기
async function onRowDetailClick(row) {
  if (row.expand === false) {
    for (var i = 0; i < rows.value.length; i++) {
      rows.value[i].expand = false;
      rows.value[i].bottom_model = false;
    }
    // if (!row.expand && row.TAKEBACK == undefined) {
    if (!row.expand) {
      const res = await api.get("/ord/itemDetail", {
        params: {
          selItemCd: row.ITEM_CD,
          custCd: userInfo.CUST_CD,
          GdlvBrchCd: row.G_DLV_BRCH_CD,
        },
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
    row.expand = !row.expand;
  }
}

async function onClickRow(row) {
  for (var i = 0; i < rows.value.length; i++) {
    rows.value[i].bottom_model = false;
    rows.value[i].expand = false;
  }

  if (row.AVAIL_STOCK > 0) {
    row.bottom_model = true;
    row.expand = false;
  }

  searchParam.value.ingno = row.ITEM_INGREDIENT_CD;
  searchParam.value.effno = row.ITEM_KIND_NO;
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

onMounted(() => {
  //필터표시
  filtergroup2[0].buttons.forEach((el, index) => {
    isActiveBtns.value.push(index);
  });
  setTimeout(() => {
    loading.value = false;
    show.value = true;
    nodata_show.value = true;
  }, 3000);
});
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 8px;
  align-items: center;
  .table_item:nth-child(1) {
    @include flex_start;
    @include fw-6;
    align-items: flex-start;
    grid-column: span 5;
    gap: 8px;
    height: auto !important;
    td {
      // height: auto !important;
      // white-space: normal;
      padding-right: 40px !important;
      line-height: 20px;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .table_item:nth-child(2) {
    position: absolute;
    top: 8px;
    right: 0;
    grid-column: span 1;
    td {
      @include flex_end;
      gap: 8px;
      span {
        max-width: 90px;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 3;
    td {
      @include flex_start;
      @include fw-6;
      display: block;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 2;
    td {
      @include flex_start;
      // @include fs-6;
      @include fw-6;
      // font-size: 12px !important;
      // color: #666666;
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 1;
    td {
      @include flex_start;
      @include fs-5;
      // @include fw-6;
      color: #666666;
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 1;
    td {
      @include flex_center;
      @include fs-5;
      @include fw-6;
      // color: $r1;
    }
  }
  .table_item:nth-child(7) {
    grid-column: span 1;
    td {
      @include flex_center;
      @include fs-5;
      @include fw-6;
      color: $r1;
    }
  }
  .table_item:nth-child(8) {
    grid-column: span 1;
    td {
      color: #666666;
    }
  }
  .table_item:nth-child(9) {
    grid-column: span 1;
    td {
      @include flex_center;
      gap: 8px;
      span {
        @include fs-5;
      }
    }
  }
}
.table_expand_wrap {
  padding: 16px;
  border: none;
  border-bottom: 1px solid $line-300;
  // background-color: #f8f8f8;
  .table_expand_item {
    td:nth-child(1) {
      min-width: 80px;
      font-weight: 600;
    }
  }
}
.td-prd_name {
  .extra-info {
    margin-top: 2px; /* 줄바꿈 후의 여백 */
    font-size: 12px; /* 스타일 조정 */
    color: #ff0000; /* 색상 조정 */
    font-weight: 300;
  }
}
// .table_expand_wrap {
//   .table_expand_item {
//     td {
//       @include fs-5;
//     }
//     td:nth-child(1) {
//       @include fw-6;
//       width: auto;
//     }
//   }
//   .table_expand_item:nth-child(1) {
//     @include border-default;
//     padding-bottom: 4px !important;
//     height: auto !important;
//     td {
//       @include fw-6;
//       font-size: 16px;
//       // max-width: 220px;
//       // overflow: hidden;
//       // text-overflow: ellipsis;
//       height: auto !important;
//       white-space: normal;
//     }
//     td:nth-child(2) {
//       color: $b3;
//     }
//   }
//   .table_expand_item:nth-child(2) {
//     position: relative;
//     margin-top: 4px;
//     .table_expand_record_btn {
//       position: absolute;
//       top: 7%;
//       right: 0;
//     }
//   }
//   .table_expand_item:nth-child(3) {
//     td {
//       // @include fw-6;
//       font-size: 16px;
//       max-width: 220px;
//       // overflow: hidden;
//       // text-overflow: ellipsis;
//       height: auto !important;
//       white-space: normal;
//     }
//   }
//   .table_expand_item:nth-child(4) {
//   }
//   .table_expand_item:nth-child(5) {
//     td:nth-child(2) {
//       @include fw-6;
//       color: $b4;
//     }
//   }
//   .table_expand_item:nth-child(6) {
//     span:nth-child(odd) {
//       @include fs-5;
//       @include fw-6;
//     }
//     span:nth-child(even) {
//       @include fs-5;
//       margin-right: 8px;
//     }
//   }
//   .table_expand_item:nth-child(7) {
//   }
//   .table_expand_item:nth-child(8) {
//   }
//   .table_expand_item:nth-child(9) {
//   }
// }
.highlight-special {
  color: red;
  font-weight: 500;
  margin-left: 4px;
}
</style>

<template>
  <div class="">
    <q-table
      class=""
      row-key="ITEM_CD"
      :rows="rows"
      flat
      hide-header
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
    >
      <template v-slot:loading>
        <div class="m-loading_wrap" v-if="loading === true">
          <q-inner-loading class="loading" :showing="loading" color="primary" />
        </div>
      </template>
      <template v-slot:top>
        <div class="table_Top_wrap" @click="focus">
          <div class="table_Title_mobile">
            <span>{{ TableTitle }}</span>
          </div>
          <div class="filter_area">
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
              btnStyle="default"
              class="btn_srch"
              @click="onClickItemSearch"
            />
            <div class="table_Filter" @click="dialog_filter = true">
              <!-- <img src="/src/assets/icons/sliders-solid.svg" alt="icon"> -->
              <BtnFilterComp_mobile :filterCheck="isFilterChecked" />
              <DialogLayout_mobile
                dialog_title="검색 필터"
                v-model:dialog_model_prop="dialog_filter"
                :dialog_full="dialog_full"
              >
                <div class="">
                  <!-- <FilterComp_mobile
                    :btnGroups="filterbtnGroups"
                    btnResult="on"
                  /> -->
                  <FilterSingleComp_mobile_return
                    :btnGroups="filtergroup1"
                    :isActiveBtn="isActiveBtn1"
                    @selected-single-btn="(val) => selectedGroup1(val)"
                  />
                  <FilterSingleComp_mobile_a
                    :btnGroups="filtergroup2"
                    :isActiveBtn="isActiveBtn2"
                    @selected-single-btn="(val) => selectedGroup2(val)"
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
              <q-td class="table-td_mobile">
                <p>
                  {{ props.row.ITEM_NM }}
                </p>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.MAKER_NM }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">규격:{{ props.row.UNIT }}</q-td>
            </div>

            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}원
              </q-td>
            </div>
            <div
              class="table_item table_item_mobile"
              @click="onClickTakeback(props)"
            >
              <q-td
                class="table-td_mobile"
                v-if="
                  props.row.WEB_BACK_USE_YN === 'Y' ||
                  props.row.TAKEBACK_LIMIT_YN === 'Y'
                "
              >
                <span v-if="props.row.WEB_BACK_USE_YN === 'Y'" class="red-400">
                  불가
                </span>
                <span
                  v-if="props.row.TAKEBACK_LIMIT_YN === 'Y'"
                  class="blue-400"
                >
                  제한
                </span>
                <span>
                  <img
                    :src="
                      props.expand
                        ? 'assets/images/arrw_up.svg'
                        : 'assets/images/arrw_dwn.svg'
                    "
                    alt="icon"
                  />
                </span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span
                  v-if="props.row.LIMIT_QTY > 0 && props.row.SUBDIV_YN == 'Y'"
                ></span>
                <span
                  v-else-if="
                    props.row.LIMIT_QTY > 0 && props.row.SUBDIV_YN == 'N'
                  "
                  >반품가능수량
                  {{
                    props.row.LIMIT_QTY == 99999
                      ? "-"
                      : util.getNumberFormat(props.row.LIMIT_QTY)
                  }}개
                </span>
                <span v-else class="red-400"
                  >반품가능수량
                  {{ util.getNumberFormat(props.row.LIMIT_QTY) }}개
                </span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <BtnComp_mobile
                  buttonText="출고이력"
                  btnStyle="default"
                  style="width: 64px"
                  :is-disable="props.row.HISTORY_YN === 'N'"
                  @click="props.row.dialog_record = true"
                />
                <DialogLayout_mobile
                  dialog_title="반품 - 제품 출고이력 조회"
                  v-model:dialog_model_prop="props.row.dialog_record"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <ReturnRecordComp_a_mobile
                      :itemcd="props.row.ITEM_CD"
                      :medCd="props.row.MED_CD"
                      :return-data="props.row"
                      v-model="isActiveSellMonth"
                    >
                      <template v-slot:title>
                        <div class="table_badge_wrap" style="padding: 0">
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
                            :class="
                              props.row.PAY_ITEM_NM === '비' ? 'nonpay' : 'pay'
                            "
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
                        {{ props.row.ITEM_NM }}
                        {{ props.row.UNIT }}
                      </template>
                      <template v-slot:sub-title>
                        <span
                          v-if="
                            props.row.LIMIT_QTY > 0 &&
                            props.row.SUBDIV_YN == 'Y'
                          "
                        ></span>
                        <span
                          v-else-if="
                            props.row.LIMIT_QTY > 0 &&
                            props.row.SUBDIV_YN == 'N'
                          "
                          >반품가능수량
                          {{
                            props.row.LIMIT_QTY == 99999
                              ? "-"
                              : util.getNumberFormat(props.row.LIMIT_QTY)
                          }}개
                        </span>
                        <span v-else class="red-400"
                          >반품가능수량
                          {{ util.getNumberFormat(props.row.LIMIT_QTY) }}개
                        </span>
                      </template>
                    </ReturnRecordComp_a_mobile>
                  </div>
                </DialogLayout_mobile>
              </q-td>
            </div>
            <div
              class="click_arear_return_a"
              @click="props.bottom_model = onClickRow(props.row)"
            ></div>
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
                    <td>{{ props.row.TAKEBACK_INFO }}</td>
                  </div>

                  <!-- <div
                    class="table_expand_close"
                    @click="props.expand = !props.expand"
                  >
                    <span>상세정보 닫기</span>
                    <span
                      ><img
                        src="/src/assets/icons/sort-down-solid-close.svg"
                        alt="icon"
                    /></span>
                  </div> -->
                </div>
              </tr>
            </tbody>
          </q-markup-table>
        </q-tr>

        <BottomComp_mobile_return
          v-model:dialog_bottom_model="props.row.bottom_model"
          :first_bottom_badge="props.row.badge"
          :first_bottom_약="props.row.ITEM_NM"
          :first_bottom_약코드="props.row.ITEM_CD"
          :first_bottom_규격="props.row.UNIT"
          :first_bottom_재고="props.row.AVAIL_STOCK"
          :first_bottom_배송="props.row.G_DLV_BRCH_FLAG"
          :first_bottom_반품수량="props.row.LIMIT_QTY"
          :first_bottom_가격="props.row.ORD_WP2_AMT"
          :first_bottom_SUBDIV="props.row.SUBDIV_YN"
          :first_bottom_BASKET="props.row.BASKET_QTY"
          :first_bottom_RETURN="props.row.RETURN_CAT"
          :first_bottom_주문수량="rowval()"
          :second_bottom_badge="props.row.badge"
          :second_bottom_약="props.row.ITEM_NM"
          :second_bottom_규격="props.row.UNIT"
          :second_bottom_주문수량="rowval()"
          first_addBtn="반품 장바구니 담기"
          :useTotal="false"
          :useAmount="false"
          flag="addReturnBasket"
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

import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BottomComp_mobile_return from "src/components/mobile/DialogComp/BottomDialogComp/BottomComp_mobile_return.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import InputComp_mobile from "src/components/mobile/__commonComp/inputComp/InputComp_mobile.vue";
import ReturnRecordComp_a_mobile from "src/components/mobile/DialogComp/2.returnDialogComp/ReturnRecordComp_a_mobile.vue";
import BtnFilterComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnFilterComp_mobile.vue";

import FilterSingleComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile.vue";
import FilterSingleComp_mobile_a from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile_a.vue";
import FilterSingleComp_mobile_return from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile_return.vue";

const btnResult = ref("on");

const dialog_full = ref(true);
const dialog_filter = ref(false);
const dialog_record = ref(false);

const props = defineProps({
  TableTitle: {
    type: String,
    default: "TableTitle",
  },
});

//필터표시
const isActiveSellMonth = ref();

//필터 체크
const isFilterChecked = ref(false);

const rowval = () => {
  return rows.value.length;
};

//------------------------------------------------------------------------
const userInfo = util.getUserData();
//로딩
const loading = ref(true);
const show = ref(false);
const nodata_show = ref(false);

const bus = inject("bus");
const rows = ref([]);

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

const filtergroup1 = [
  {
    title: "조회 조건",
    subtitle: "",
    gridStyle: 1,
    buttons: [
      {
        btnLabel: "이력",
      },
    ],
  },
];
const filtergroup2 = [
  {
    title: "조회 필터",
    subtitle: "",
    gridStyle: 4,
    buttons: [
      {
        btnLabel: "일반반품",
      },
      {
        btnLabel: "비과세반품",
      },
      {
        btnLabel: "소분반품",
      },
      {
        btnLabel: "소분(회수)",
      },
    ],
  },
];

const group1 = ref("이력");
const group2 = ref("일반반품");

const focus = () => {
  if (rows.value.length > 0) {
    for (var i = 0; i < rows.value.length; i++) {
      rows.value[i].bottom_model = false;
    }
  }
};

async function fetchItemList() {
  loading.value = true;
  console.log(searchParam.value);
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
        dialog_record: false,
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

  if (isActiveBtn2.value === -1) {
    alert("조회 조건을 선택해 주시기 바랍니다.");
    return;
  }

  if (returnCat === "1") {
    // 일반반품
    searchParam.value.subdivYn = "N";
    searchParam.value.taxYn = "Y";
    searchParam.value.retYn = "N";
  } else if (returnCat === "2") {
    // 소분반품
    searchParam.value.subdivYn = "Y";
    searchParam.value.taxYn = "Y";
    searchParam.value.retYn = "N";
  } else if (returnCat === "3") {
    // 비과세반품
    searchParam.value.subdivYn = "N";
    searchParam.value.taxYn = "N";
    searchParam.value.retYn = "N";
  } else if (returnCat === "4") {
    // 소분반품(회수)
    searchParam.value.subdivYn = "Y";
    searchParam.value.taxYn = "Y";
    searchParam.value.retYn = "Y";
  } else if (returnCat === "6") {
    searchParam.value.subdivYn = "Y";
    searchParam.value.taxYn = "Y";
    searchParam.value.retYn = "N";
  }

  bus.emit("scrollUp");
  await fetchItemList();
}

function selectedGroup1(val) {
  group1.value = val;
}

function selectedGroup2(val) {
  group2.value = val;

  if (val == "소분반품") {
    group1.value = "";
    bus.emit("updateChk", "");
  } else if (val == "소분(회수)") {
    group1.value = "";
    bus.emit("updateChk", "");
  } else if (val == "일반반품") {
    group1.value = "이력";
    bus.emit("updateChk", 0);
  } else if (val == "비과세반품") {
    group1.value = "이력";
    bus.emit("updateChk", 0);
  }
}

//현재 적용중인 버튼 표시
const isActiveBtn1 = ref(0);
const isActiveBtn2 = ref("일반반품");

// 필터 적용 버튼 클릭
async function onClickFilterApply() {
  dialog_filter.value = false;

  if (group1.value === "이력") {
    searchParam.value.history = "Y";
  } else {
    searchParam.value.history = "N";
  }

  if (group2.value !== "") {
    if (group2.value === "일반반품") {
      searchParam.value.returnCat = "1";
    } else if (group2.value === "소분반품") {
      searchParam.value.returnCat = "2";
    } else if (group2.value === "비과세반품") {
      searchParam.value.returnCat = "3";
    } else if (group2.value === "소분(회수)") {
      searchParam.value.returnCat = "4";
    }
  } else {
    searchParam.value.returnCat = "1";
  }

  //필터표시
  if (!util.isEmpty(group1.value)) {
    isActiveBtn1.value = filtergroup1[0].buttons.findIndex(
      (obj) => obj.btnLabel == group1.value
    );
  } else {
    isActiveBtn1.value = -1;
  }

  if (!util.isEmpty(group2.value)) {
    // isActiveBtn2.value = filtergroup2[0].buttons.findIndex(
    //   (obj) => obj.btnLabel == group2.value
    // );
    isActiveBtn2.value = group2.value;
  } else {
    isActiveBtn2.value = "";
  }

  //필터 체크표시
  isFilterChecked.value = true;
  await onClickItemSearch();
}

// 반품불가, 제한 클릭
function onClickTakeback(props) {
  //props.expand = !props.expand

  if (
    props.row.WEB_BACK_USE_YN === "Y" ||
    props.row.TAKEBACK_LIMIT_YN === "Y"
  ) {
    props.expand = !props.expand;
  }
}

async function onClickRow(row) {
  for (var i = 0; i < rows.value.length; i++) {
    rows.value[i].bottom_model = false;
  }

  if (
    row.SUBDIV_YN == "Y" ||
    (row.HISTORY_YN == "Y" && row.LIMIT_QTY > 0 && row.WEB_BACK_USE_YN != "Y")
  ) {
    row.bottom_model = true;
  }
}

bus.on("refBacList", () => {
  onClickItemSearch();
});

onMounted(() => {
  setTimeout(() => {
    loading.value = false;
    show.value = true;
    nodata_show.value = true;
  }, 3000);
});
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
    title: "조회 조건",
    subtitle: "중복가능",
    gridStyle: 2,
    btnDatePicker: "off",
    buttons: [{ title: "이력" }, { title: "품질제외" }],
  },
  {
    title: "조회필터",
    subtitle: "",
    gridStyle: 4,
    btnDatePicker: "off",
    buttons: [
      { title: "일반반품", sub_title: "" },
      { title: "비과세", sub_title: "반품" },
      { title: "소분반품", sub_title: "" },
      { title: "소분반품", sub_title: "(회수)" },
    ],
  },
];
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  padding: 6px 0 !important;
  gap: 0 8px;
  .table_item:nth-child(1) {
    @include flex_start;
    @include fw-7;
    grid-column: span 5;
    gap: 8px;
    height: auto !important;
    td {
      // font-size: 16px !important;
      // overflow: hidden;
      // text-overflow: ellipsis;
      height: auto !important;
      white-space: normal;
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 3;
    td {
      @include flex_start;
      @include fs-6;
      color: #666666;
      display: block;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-6;
      color: #666666;
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
      @include fw-7;
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 3;
    td {
      @include flex_start;
      gap: 8px;
      span {
        @include fs-5;
        @include fw-7;
      }
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 1;
    td {
      @include flex_center;
      @include fs-5;
    }
  }
  .table_item:nth-child(7) {
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
}

.table_expand_wrap {
  min-height: 60px;
  .table_expand_item {
    margin: -10px;
    td {
      @include fw-7;
      @include fs-5;
      white-space: normal;
    }
  }
}
</style>

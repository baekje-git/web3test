<template>
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
      :loading="loading"
    >
      <template v-slot:loading>
        <div class="m-loading_wrap" v-if="loading === true">
          <q-inner-loading class="loading" :showing="loading" color="primary" />
        </div>
      </template>
      <template v-slot:top>
        <div class="table_Top_wrap">
          <div class="table_Title_mobile">
            <span>{{ TableTitle }}</span>
          </div>
          <div class="">
            <BtnGroupComp_mobile
              :buttons="btnGroups"
              btn-size="lg"
              btnStyle="active"
              @on-click-btn="(val) => onClickFilterBtn(val)"
            />
          </div>
          <div class="table_Title_mobile">
            <span>상품리스트</span>
          </div>
          <div class="filter_area">
            <div class="flex_between" style="gap: 8px">
              <InputComp_mobile
                placeholder="보험코드/품명"
                style="width: 50%"
                v-model:input_model_props="searchParam.keyword"
                @keyup.enter="onClickSearch"
              />
              <InputComp_mobile
                placeholder="제약사"
                style="width: 50%"
                v-model:input_model_props="searchParam.makerNm"
                @keyup.enter="onClickSearch"
              />
            </div>
            <BtnComp_mobile
              buttonText="검색"
              btnStyle="default"
              class="btn_srch"
              @click="onClickSearch"
            />
            <div class="table_Filter" @click="dialog_filter = true">
              <BtnFilterComp_mobile :filterCheck="isFilterChecked" />
              <DialogLayout_mobile
                dialog_title="검색 필터"
                v-model:dialog_model_prop="dialog_filter"
                :dialog_full="dialog_full"
              >
                <div class="">
                  <FilterSingleComp_mobile_a
                    :btnGroups="filterbtnGroups"
                    :isActiveBtn="isActiveBtn"
                    @selected-single-btn="(val) => selectedSortType(val)"
                  />
                  <BtnComp_mobile
                    :class="btnResult"
                    buttonText="적용"
                    btnStyle="default"
                    btnSize="lg"
                    class="btn-full"
                    style="padding-top: 20px"
                    @click="onClickFilterApply('D')"
                  />
                </div>
              </DialogLayout_mobile>
            </div>
          </div>
        </div>
      </template>
      <template v-slot:body="props">
        <q-tr>
          <div class="table_wrap">
            <div class="table_item" @click="onClickRow(props.row)">
              <div class="table_badge_wrap">
                <template v-if="props.row.badge === 1 || props.row.badge === 3">
                  <!-- <q-badge class="table_badge_red">{{
                    props.row.MED_NM
                  }}</q-badge> -->
                  <q-badge class="bdg-line normal">
                    {{
                      props.row.MED_NM === "일"
                        ? "일반"
                        : props.row.MED_NM === "전"
                        ? "전문"
                        : props.row.MED_NM
                    }}
                  </q-badge>
                </template>
                <template v-else>
                  <!-- <q-badge class="table_badge_red">{{
                    props.row.MED_NM
                  }}</q-badge> -->
                  <q-badge class="bdg-line normal">
                    {{
                      props.row.MED_NM === "일"
                        ? "일반"
                        : props.row.MED_NM === "전"
                        ? "전문"
                        : props.row.MED_NM
                    }}
                  </q-badge>
                </template>
                <template v-if="props.row.badge === 1 || props.row.badge === 2">
                  <!-- <q-badge class="table_badge_blue">{{
                    props.row.PAY_ITEM_NM
                  }}</q-badge> -->
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
                </template>
                <template v-else>
                  <!-- <q-badge class="table_badge_blue">{{
                    props.row.PAY_ITEM_NM
                  }}</q-badge> -->
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
                </template>
              </div>
              <q-td class="table-td_mobile">{{ props.row.ITEM_NM }} </q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">
                <!-- <ToggleComp_mobile
                  toggleLabel=""
                  Toggle_Style="Toggle_Switch_wrap"
                /> -->
                <!-- <div class="Toggle_wrap">
                  <label class="toggle">
                    <input
                      v-model="props.row.ALARM_YN"
                      id="toggle"
                      type="checkbox"
                      true-value="Y"
                      false-value="N"
                      @change="onClickAlarm(props.row)"
                    />
                    <span class="slider"></span>
                  </label>
              </div> -->
              </q-td>
            </div>
            <!-- <div class="table_item">
              <q-td>{{ props.row.MAKER_NM }}</q-td>
            </div> -->
            <div class="table_item">
              <q-td class="table-td_mobile">
                <span>규격</span>
                <span>{{ util.getNumberFormat(props.row.UNIT) }} </span>
              </q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile"
                ><span>월평균</span
                ><span>{{
                  props.row.MONTH_QTY > 0 ? props.row.MONTH_QTY : "미입력"
                }}</span></q-td
              >
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">
                <div v-if="props.row.AVAIL_STOCK > 0">
                  <span>재고 </span>
                  <span>{{ props.row.AVAIL_STOCK }}</span>
                </div>
                <div v-else>
                  <span class="red-400">품절</span>
                </div>
              </q-td>
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile"
                ><span>{{ props.row.MAKER_NM }}</span></q-td
              >
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile"
                ><span>전월</span><span>{{ props.row.BFR_MTH_ORD }}</span></q-td
              >
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile"
                ><span>단가</span
                ><span
                  >{{ util.getNumberFormat(props.row.ORD_WP2_AMT) }} 원</span
                ></q-td
              >
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile"
                ><span>당월</span><span>{{ props.row.THS_MTH_ORD }}</span></q-td
              >
            </div>
            <div class="table_item">
              <q-td class="table-td_mobile">
                <BtnComp_mobile
                  buttonText="제품정보"
                  btnStyle="icon"
                  btnAlign="between"
                  :btnIcon="props.row.expand ? btnIconUp2 : btnIconDown2"
                  @click="onRowClick(props.row)"
                  style="width: 86px"
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
                    <td>{{ props.row.ITEM_NM }}</td>
                  </div>
                  <!-- <div class="table_expand_item">
                    <td>제품정보</td>
                    <div class="table_expand_item_sub">
                      <template
                        v-if="props.row.badge === 1 || props.row.badge === 3"
                      >
                        <q-badge class="table_badge_red">{{
                          props.row.MED_NM
                        }}</q-badge>
                      </template>
                      <template v-else>
                        <q-badge class="table_badge_red">{{
                          props.row.MED_NM
                        }}</q-badge>
                      </template>
                      <template
                        v-if="props.row.badge === 1 || props.row.badge === 2"
                      >
                        <q-badge class="table_badge_blue">{{
                          props.row.PAY_ITEM_NM
                        }}</q-badge>
                      </template>
                      <template v-else>
                        <q-badge class="table_badge_blue">{{
                          props.row.PAY_ITEM_NM
                        }}</q-badge>
                      </template>
                      <q-td>{{ props.row.ITEM_NM }} </q-td>
                    </div>
                  </div> -->
                  <div class="table_expand_item">
                    <td>규격</td>
                    <td>{{ props.row.UNIT }}</td>
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
                    <td>반품조건</td>
                    <td>{{ props.row.TAKEBACK_COND ?? "-" }}</td>
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
                    <td>
                      {{
                        props.row.MAX_ORD_QTY == 0 ||
                        props.row.MAX_ORD_QTY == null
                          ? "-"
                          : util.getNumberFormat(props.row.MAX_ORD_QTY)
                      }}
                    </td>
                  </div>
                  <div class="table_expand_item">
                    <td>입고일정</td>
                    <td>{{ props.row.INCMNG_SCHDL ?? "" }}</td>
                  </div>
                  <div
                    class="table_expand_close"
                    @click="props.row.expand = !props.row.expand"
                  >
                    <span>제품정보 닫기</span>
                    <span
                      ><img src="assets/images/arrw_up.svg" alt="icon"
                    /></span>
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
import { ref, defineProps, onMounted, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BottomComp_mobile from "src/components/mobile/DialogComp/BottomDialogComp/BottomComp_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import InputComp_mobile from "src/components/mobile/__commonComp/inputComp/InputComp_mobile.vue";

import BottomComp_mobile_order from "../../DialogComp/BottomDialogComp/BottomComp_mobile_order.vue";
import FilterSingleComp_mobile_a from "../../DialogComp/FilterDialogComp/FilterSingleComp_mobile_a.vue";
import BtnGroupComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnGroupComp_mobile.vue";
import ToggleComp_mobile from "src/components/mobile/__commonComp/toggleComp/ToggleComp_mobile.vue";
import BtnFilterComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnFilterComp_mobile.vue";
import StockComp_mobile from "src/components/mobile/DialogComp/__commonDialogComp/StockComp_mobile.vue";

const bus = inject("bus");
const dialog_full = ref(true);
const dialog_filter = ref(false);

const dialog_bottom_model = ref(false);
const dialog_stock_model = ref(false);

// const btnIcon = ref("img:assets/icons/sort-down-white.svg");
const btnIcon = ref("img:assets/images/arrw_dwn.svg");

const btnIconUp2 = ref("img:assets/images/arrw_up.svg");
const btnIconDown2 = ref("img:assets/images/arrw_dwn.svg");

const props = defineProps({
  TableTitle: {
    type: String,
    default: "TableTitle",
  },
});

const rows = ref([]);

const userInfo = util.getUserData();
//로딩
const loading = ref(true);
const show = ref(false);
const nodata_show = ref(false);

//필터 체크
const isFilterChecked = ref(false);

//관심상품 그룹리스트
const btnGroups = ref([]);

//현재 적용중인 버튼 표시
const isActiveBtn = ref("전체");

const sortType = ref("전체");

const searchParam = ref({
  searchType: "3",
  custCd: userInfo.CUST_CD,
  searchOption: "0",
  keyword: "",
  makerNm: "",
  groupNo: "0",
});

const dataParam = ref({
  userId: userInfo.USER_ID,
  custCd: userInfo.CUST_CD,
  itemCd: "",
  alarmYn: "",
  monthQty: 0,
});

const rowval = () => {
  return rows.value.length;
};
//************************************************************************** */

// 알림 클릭
async function onClickAlarm(row) {
  dataParam.value.itemCd = row.ITEM_CD;
  dataParam.value.alarmYn = row.ALARM_YN;

  try {
    await api.put("/interest/editAlarmYn", dataParam.value);
    bus.emit("refreshInterestGrp");

    if (searchParam.value.searchType === "2") {
      await fetchItemList();
    }
  } catch (error) {
    alert(error.response.data);
  }
}

const onClickRow = async (row) => {
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
};

//정렬 선택
const selectedSortType = (val) => {
  sortType.value = val;
};

// 필터 적용 버튼 클릭
const onClickFilterApply = (type) => {
  dialog_filter.value = false;

  let val = sortType.value;

  if (val == "전체") {
    searchParam.value.searchOption = "0";
  } else if (val == "전문급여") {
    searchParam.value.searchOption = "1";
  } else if (val == "전문비급여") {
    searchParam.value.searchOption = "2";
  } else if (val == "일반급여") {
    searchParam.value.searchOption = "3";
  } else if (val == "일반비급여") {
    searchParam.value.searchOption = "4";
  }

  //필터표시
  isActiveBtn.value = val;

  //필터 체크표시
  if (type === "D") {
    isFilterChecked.value = true;
  }

  onClickSearch();
};

const onClickFilterBtn = async (val) => {
  //버튼라벨값
  let data = btnGroups.value.filter((i) => i.title == val);

  if (data[0].GROUP_NO === "X") {
    if (data[0].title === "입고알림품목") {
      searchParam.value.searchType = "1";
    } else if (data[0].title === "알림품목") {
      searchParam.value.searchType = "2";
    }
  } else if (Number(data[0].GROUP_NO) >= 0) {
    searchParam.value.searchType = "3";
    searchParam.value.groupNo = data[0].GROUP_NO;
  }

  searchParam.value.keyword = "";
  // groupNm.value = data.GROUP_NM;
  await fetchItemList();
};

async function fetchItemList() {
  rows.value = [];
  loading.value = true;

  const { fixedList, list } = await api.get("/interest/interestGroupList", {
    params: searchParam.value,
  });

  const res = await api.get("/interest/groupItemList", {
    params: searchParam.value,
  });

  //입고알림, 알림품목, 기본그룹 설정가능 (고정)
  const newFixed = fixedList
    .filter((i) => i.GROUP_NO != "X")
    .map((i) => {
      return { title: i.GROUP_NM, sub: "", GROUP_NO: i.GROUP_NO };
    });

  //관심그룹1~... (가변)
  const newList = list
    .filter((i) => i.GROUP_NO > 0)
    .map((i) => {
      return { title: i.GROUP_NM, sub: "", GROUP_NO: i.GROUP_NO };
    });

  newFixed.push(...newList);
  btnGroups.value = newFixed;

  rows.value = res;
  loading.value = false;
}

// 검색 버튼 클릭
async function onClickSearch() {
  await fetchItemList();
}

// 제품정보
async function onRowClick(row) {
  if (row.expand === false) {
    for (var i = 0; i < rows.value.length; i++) {
      rows.value[i].expand = false;
      rows.value[i].bottom_model = false;
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
  } else {
    row.expand = !row.expand;
  }
}

// 화면 초기 자동 조회
onMounted(async () => {
  await bus.emit("mobileBtnChk2", "기본그룹");
  await fetchItemList();
});

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

// const btnGroups = [
//   {
//     title: "기본그룹",
//     sub:''
//   },

// ]

const filterbtnGroups = [
  {
    title: "조회필터",
    subtitle: "",
    gridStyle: 3,
    btnDatePicker: "off",
    buttons: [
      { btnLabel: "전체" },
      { btnLabel: "전문급여" },
      { btnLabel: "전문비급여" },
      { btnLabel: "일반급여" },
      { btnLabel: "일반비급여" },
    ],
  },
];
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 8px;
  // .table_item {
  //   td {
  //     @include fs-6;
  //     span {
  //       @include fs-6;
  //     }
  //   }
  // }
  .table_item:nth-child(1) {
    @include flex_start;
    @include fw-6;
    align-items: flex-start;
    grid-column: span 5;
    gap: 8px;
    height: auto !important;
    td {
      height: auto !important;
      // font-size: 16px;
      // overflow: hidden;
      // text-overflow: ellipsis;
      display: block;
      white-space: normal;
    }
  }
  .table_item:nth-child(2) {
    display: none;
    grid-column: span 1;
    td {
      @include flex_end;
      gap: 8px;
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 3;
    td {
      @include flex_start;
      color: #666666;
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 1;
    td {
      @include flex_start;
      gap: 12px;
      span {
        // @include fs-5;
        color: #666666;
      }
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 1;
    td {
      @include flex_center;
      gap: 8px;
      color: #666666;
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 3;
    td {
      @include flex_start;
      gap: 8px;
      color: #666666;
      overflow: hidden;
      text-overflow: ellipsis;
      display: block;
    }
  }
  .table_item:nth-child(7) {
    grid-column: span 2;
    td {
      @include flex_start;
      gap: 8px 4px;
      color: #666666;
    }
  }
  .table_item:nth-child(8) {
    grid-column: span 3;
    td {
      @include flex_start;
      gap: 8px;
    }
  }
  .table_item:nth-child(9) {
    grid-column: span 1;
    td {
      @include flex_start;
      gap: 8px;
      color: #666666;
    }
  }
  .table_item:nth-child(10) {
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
}

// .table_expand_wrap {
//   .table_expand_item {
//     td {
//       @include fs-5;
//     }
//     td:nth-child(1) {
//       @include fw-6;
//       // width: 70px;
//       height: auto !important;
//     }
//     &:not(:nth-child(1)) td:nth-child(1) {
//       width: 60px;
//     }
//   }
//   .table_expand_item:nth-child(1) {
//     @include flex_start;
//     // @include border-default;
//     padding-bottom: 4px !important;
//     td {
//       @include flex_start;
//       @include fw-6;
//       // font-size: 16px;
//       display: block;
//       white-space: normal;
//     }
//     .table_expand_item_sub {
//       @include flex_start;
//       @include fw-6;
//       grid-column: span 9;
//       gap: 8px;
//       max-width: 220px;
//       td {
//         display: block;
//         overflow: hidden;
//         text-overflow: ellipsis;
//       }
//     }
//   }
//   .table_expand_item:nth-child(2) {
//   }
//   .table_expand_item:nth-child(3) {
//     td:nth-child(2) {
//       @include fw-6;
//     }
//   }
//   .table_expand_item:nth-child(4) {
//   }
//   .table_expand_item:nth-child(5) {
//     span:nth-child(odd) {
//       @include fs-5;
//       @include fw-6;
//     }
//     span:nth-child(even) {
//       @include fs-5;
//       margin-right: 8px;
//     }
//   }
//   .table_expand_item:nth-child(6) {
//   }
// }

.Toggle_wrap {
  display: flex;
  align-items: center;
  gap: 4px;
}
.Toggle_label {
  cursor: pointer;
}
.toggle {
  position: relative;
  display: inline-block;
  width: 28px;
  height: 16px;
}
.toggle input {
  opacity: 0;
  width: 0;
  height: 0;
}
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: $gray-c;
  transition: 0.4s;
  border-radius: 20px;
}
.slider:before {
  position: absolute;
  content: "";
  height: 16px;
  width: 16px;
  left: 0;
  bottom: 0;
  background-color: #fff;
  border: 1px solid $gray-a;
  transition: 0.4s;
  border-radius: 50%;
}
.toggle input:checked + .slider {
  background-color: $primary;
}
.toggle input:checked + .slider:before {
  transform: translateX(12px);
  border-color: $primary;
}

.Toggle_Switch_wrap {
  display: flex;
  align-items: center;
  .Toggle_label {
    @include fs-5;
    color: #666666;
    cursor: pointer;
    margin-left: 8px;
  }
  .toggle {
    position: relative;
    display: inline-block;
    width: 44px;
    height: 20px;
    input {
      opacity: 0;
      width: 0;
      height: 0;
    }
    .slider {
      position: absolute;
      cursor: pointer;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background-color: #ccc;
      transition: 0.4s;
      border-radius: 20px;
      &::before {
        position: absolute;
        content: "";
        height: 16px;
        width: 16px;
        left: 2px;
        bottom: 2px;
        background-color: white;
        transition: 0.4s;
        border-radius: 50%;
      }
      &::after {
        position: absolute;
        right: 3px;
        top: 4px;
        font-size: 12px;
        color: #fff;
        content: "OFF";
      }
    }
  }
  .toggle input:checked + .slider {
    background-color: $b3;
  }
  .toggle input:checked + .slider:before {
    transform: translateX(24px);
  }
  .toggle input:checked + .slider:after {
    position: absolute;
    left: unset;
    left: 3px;
    top: 4px;
    font-size: 12px;
    color: #fff;
    content: "ON";
  }
}
</style>

<template>
  <div class="">
    <q-table
      class=""
      row-key="name"
      :rows="filteredRows"
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
          <div class="filter_area">
            <div class="flex_between">
              <DatePickerComp_mobile
                style="width: 50%"
                v-model:input_model_props="searchParam.startDt"
                @enter="onClickSearch"
              />
              <span style="font-size: 12px; color: #999999; margin: 0 8px">
                -
              </span>
              <DatePickerComp_mobile
                style="width: 50%"
                v-model:input_model_props="searchParam.endDt"
                @enter="onClickSearch"
              />
            </div>
            <BtnComp_mobile
              buttonText="검색"
              btnStyle="default"
              class="btn_srch"
              @click="onClickSearch"
            />
          </div>
          <div class="" style="padding: 0 0 8px 0">
            <BtnDateGroupComp_mobile btnStyle="active" />
          </div>
          <div class="" style="margin-bottom: 16px">
            <div class="btn-group-wrap">
              <div class="button-container">
                <q-btn
                  v-model="filterCond"
                  :class="searchAll"
                  label="전체"
                  size="sm"
                  style="min-width: 64px; line-height: normal"
                  flat
                  dense
                  @click="onClickSearchType('전체')"
                />
                <q-btn
                  v-model="filterCond"
                  :class="notReg"
                  :label="radioLabelA"
                  size="sm"
                  style="min-width: 64px; line-height: normal"
                  flat
                  dense
                  @click="onClickSearchType('미등록N')"
                />
                <q-btn
                  v-model="filterCond"
                  :class="manager"
                  :label="radioLabelB"
                  size="sm"
                  style="min-width: 64px; line-height: normal"
                  flat
                  dense
                  @click="onClickSearchType('담당자N')"
                />
                <q-btn
                  v-model="filterCond"
                  :class="inProgress"
                  :label="radioLabelC"
                  size="sm"
                  style="min-width: 64px; line-height: normal"
                  flat
                  dense
                  @click="onClickSearchType('처리중N')"
                />
                <q-btn
                  v-model="filterCond"
                  :class="insCompleted"
                  :label="radioLabelD"
                  size="sm"
                  style="min-width: 64px; line-height: normall"
                  flat
                  dense
                  @click="onClickSearchType('검수완료N')"
                />
                <q-btn
                  v-model="filterCond"
                  :class="cancel"
                  :label="radioLabelE"
                  size="sm"
                  style="min-width: 64px; line-height: normal"
                  flat
                  dense
                  @click="onClickSearchType('취소N')"
                />
              </div>
            </div>
            <!-- <BtnGroupComp_mobile
              :buttons="['전체', '미등록N', '담당자N', '처리중N', '검수완료N', '취소N']"
              btnStyle="active"
            /> -->
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
                  {{ props.row.ITEM_NM_UNIT }}
                </p>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">{{ props.row.BACK_EXAM_NM }}</q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span>등록</span>
                <span>{{
                  moment(props.row.BACK_DT, "YYYY-MM-DD HH:mm:ss").format(
                    "YYYY-MM-DD"
                  )
                }}</span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span>{{ props.row.ITEM_CNT }} </span>
                <span>종</span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span>
                  {{ util.getNumberFormat(props.row.TOTAL_AMT) }}
                </span>
                <span>원</span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <div class="">
                  <BtnComp_mobile
                    v-if="props.row.ORD_MEMO == ''"
                    buttonText="반품 메모"
                    btnStyle="icon-white"
                    btnAlign="between"
                    :btnIcon="props.row.expand1 ? btnIconUp : btnIconDown"
                    @click="props.row.expand1 = !props.row.expand1"
                  />
                  <BtnComp_mobile
                    v-else
                    buttonText="반품 메모"
                    btnStyle="icon"
                    btnAlign="between"
                    :btnIcon="props.row.expand1 ? btnIconUp2 : btnIconDown2"
                    @click="props.row.expand1 = !props.row.expand1"
                  />
                  <BtnComp_mobile
                    v-if="props.row.TREAT_MEMO == ''"
                    buttonText="담당자 메모"
                    btnStyle="icon-white"
                    btnAlign="between"
                    :btnIcon="props.row.expand2 ? btnIconUp : btnIconDown"
                    @click="props.row.expand2 = !props.row.expand2"
                  />
                  <BtnComp_mobile
                    v-else
                    buttonText="담당자 메모"
                    btnStyle="icon"
                    btnAlign="between"
                    :btnIcon="props.row.expand2 ? btnIconUp2 : btnIconDown2"
                    @click="props.row.expand2 = !props.row.expand2"
                  />
                </div>
                <div class="">
                  <DeleteComp_mobile
                    v-if="props.row.CNCL_ABLE_YN === 'Y'"
                    delete_text1="반품현황"
                    delete_text2="반품 취소"
                    delete_btn="반품 취소"
                    :dataRow="props.row"
                    @delete-basket="(val) => bakReq(val)"
                  >
                    <BtnComp_mobile
                      buttonText="반품취소"
                      btnStyle="transparent-black"
                    />
                  </DeleteComp_mobile>

                  <BtnComp_mobile
                    buttonText="상세"
                    btnStyle="transparent-black"
                    @click="onViewDtl(props.row)"
                  />
                  <DialogLayout_mobile
                    dialog_title="반품현황 > 반품상세"
                    v-model:dialog_model_prop="props.row.dtl_model"
                    :dialog_full="dialog_full"
                  >
                    <div class="">
                      <SalesDetailComp_c_mobile
                        :sn="props.row.SN"
                        :cnclYn="props.row.CNCL_ABLE_YN"
                        :searchParam="searchParam"
                      />
                    </div>
                  </DialogLayout_mobile>
                </div>
              </q-td>
            </div>
          </div>
        </q-tr>

        <q-tr v-show="props.row.expand1" :props="props">
          <div class="expand_table_wrap">
            <div class="expand_table_item">
              <q-td class="table-td_mobile">{{
                props.row.ORD_MEMO == "" ? "반품 메모 없음" : props.row.ORD_MEMO
              }}</q-td>
              <q-td class="table-td_mobile">
                <BtnComp_mobile
                  v-if="
                    props.row.BACK_EXAM_CD == '01' && props.row.ORD_MEMO == ''
                  "
                  buttonText="편집"
                  btnStyle="transparent-black"
                  style="width: 64px"
                  @click="onViewMemo(props.row)"
                />
                <BtnComp_mobile
                  v-else-if="
                    props.row.BACK_EXAM_CD == '01' && props.row.ORD_MEMO != ''
                  "
                  buttonText="수정"
                  btnStyle="transparent-black"
                  style="width: 64px"
                  @click="onViewMemo(props.row)"
                />
                <DialogLayout_mobile
                  dialog_title="반품현황 > 반품메모"
                  v-model:dialog_model_prop="props.row.memo_model"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <TextAreaComp_mobile
                      :memo="props.row.ORD_MEMO"
                      :sn="props.row.SN"
                      :is-visible="true"
                      submit="반품메모 수정"
                      @update-memo="(val) => regMemo(val, props.row)"
                    />
                  </div>
                </DialogLayout_mobile>
              </q-td>
            </div>
          </div>
        </q-tr>
        <q-tr v-show="props.row.expand2" :props="props">
          <div class="expand_table_wrap">
            <div class="expand_table_item">
              <q-td class="table-td_mobile">{{
                props.row.TREAT_MEMO == ""
                  ? "담당자 메모 없음"
                  : props.row.TREAT_MEMO
              }}</q-td>
              <!-- <q-td class="table-td_mobile">
                <BtnComp_mobile
                  buttonText="편집"
                  btnStyle="transparent-black"
                  style="width: 64px"
                  @click="onViewManagerMemo(props.row)"
                />
                <DialogLayout_mobile
                  dialog_title="반품현황 > 담당자메모"
                  v-model:dialog_model_prop="props.row.manager_memo_model"
                  :dialog_full="dialog_full"
                >
                  <div class="">
                    <TextAreaComp_mobile
                      :memo="props.row.TREAT_MEMO"
                      submit="담당자메모 수정"
                    />
                  </div>
                </DialogLayout_mobile>
              </q-td> -->
            </div>
          </div>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, inject, computed, toRaw, onMounted } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import BtnDateGroupComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnDateGroupComp_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import BtnGroupComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnGroupComp_mobile.vue";
import SalesDetailComp_c_mobile from "src/components/mobile/DialogComp/3.salesDialogComp/SalesDetailComp_c_mobile.vue";
import DatePickerComp_mobile from "src/components/mobile/__commonComp/datepickerComp/DatePickerComp_mobile.vue";
import DeleteComp_mobile from "src/components/mobile/DialogComp/__commonDialogComp/DeleteComp_mobile.vue";
import TextAreaComp_mobile from "src/components/mobile/DialogComp/__commonDialogComp/TextAreaComp_mobile.vue";

const dialog_full = ref(true);

const btnIconUp = ref("img:assets/images/arrw_up.svg");
const btnIconDown = ref("img:assets/images/arrw_dwn.svg");
const btnIconUp2 = ref("img:assets/images/arrw_up.svg");
const btnIconDown2 = ref("img:assets/images/arrw_dwn.svg");

const bus = inject("bus");
const userInfo = util.getUserData();
const rows = ref([]);

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  startDt: moment().add(-1, "months").startOf("month").format("YYYY-MM-DD"),
  endDt: moment().format("YYYY-MM-DD"),
  searchType: "", //일단 쿼리보니 따로 조건이 안보여서 임시로 "":전체, 1:미등록N, 2:담당자N, 3:처리중N, 4:검수완료N, 5:취소N으로 함'
  keyword: "",
  makerNm: "",
});

const props = defineProps({
  TableTitle: {
    type: String,
    default: "TableTitle",
  },
});
const dataParam = ref({
  bakMemo: "",
  sn: "",
});

//로딩
const loading = ref(true);
const show = ref(false);
const nodata_show = ref(false);

const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
//---------------------------- Function ------------------------------//

// 메모 등록
async function regMemo(val, row) {
  row.ORD_MEMO = val;
  dataParam.value.bakMemo = row.ORD_MEMO;
  dataParam.value.sn = row.SN;
  await api.post("/bakReq/saveBakMemo", dataParam.value);
  util.showNotifyTop("메모가 저장되었습니다");
  row.memo_model = false;
}

// 검색버튼 클릭
async function onClickSearch() {
  await fetchItemList();
}

async function fetchItemList() {
  //로딩추가
  loading.value = true;

  searchParam.value.startDt = moment(searchParam.value.startDt).format(
    "YYYYMMDD"
  );
  searchParam.value.endDt = moment(searchParam.value.endDt).format("YYYYMMDD");

  const res = await api.get("/bakReq/listSearch", {
    params: searchParam.value,
  });

  searchParam.value.startDt = moment(searchParam.value.startDt).format(
    "YYYY-MM-DD"
  );
  searchParam.value.endDt = moment(searchParam.value.endDt).format(
    "YYYY-MM-DD"
  );
  //ios 날짜형식으로 인해 변경 로직
  let newList = res.map((i) => {
    return { ...i, BACK_DT: i.BACK_DT.replace(/\./g, "/") };
  });
  rows.value = newList;
  loading.value = false;
}

//기간 클릭
bus.off("onClickPeriod");
bus.on("onClickPeriod", async (params) => {
  searchParam.value.startDt = params.value.startDt;
  searchParam.value.endDt = params.value.endDt;

  await fetchItemList();
});

async function onViewDtl(row) {
  for (var i = 0; i < rows.value.length; i++) {
    rows.value[i].dtl_model = false;
  }
  row.dtl_model = true;

  //await bus.emit("pmntItemDetail", props.row);
}

async function onViewMemo(row) {
  for (var i = 0; i < rows.value.length; i++) {
    rows.value[i].memo_model = false;
  }
  row.memo_model = true;

  //await bus.emit("pmntItemDetail", props.row);
}

async function onViewManagerMemo(row) {
  for (var i = 0; i < rows.value.length; i++) {
    rows.value[i].manager_memo_model = false;
  }
  row.manager_memo_model = true;

  //await bus.emit("pmntItemDetail", props.row);
}

const bakReq = async (row) => {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  Object.assign(searchParam.value, { sn: row.SN });

  const res = await api.get("/bakReq/chk", {
    params: searchParam.value,
  });

  if (res.CNCL_ABLE_YN != "Y") {
    alert(
      "선택하신 반품건에 대해 진행상태가 변경되어 반품취소가 불가능합니다.\n다시 확인하시고 진행해주시기 바랍니다."
    );
    return;
  }

  try {
    Object.assign(row, { USER_ID: userInfo.USER_ID });

    await api.delete("/bakReq/cancelBakReq", { params: row });

    util.showNotifyTop("취소되었습니다.");

    await fetchItemList();
  } catch (error) {
    // alert(error.response.data);
    alert("오류가 발생했습니다.");
  }
};

bus.off("fetchItemList");
bus.on("fetchItemList", async () => {
  await fetchItemList();
});

bus.off("closeDialogPopup");
bus.on("closeDialogPopup", async () => {
  await fetchItemList();
});

//검색조건 표시
const searchAll = computed(() =>
  filterCond.value === "00"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const notReg = computed(() =>
  filterCond.value === "01"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const manager = computed(() =>
  filterCond.value === "11"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const inProgress = computed(() =>
  filterCond.value === "02"
    ? " btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const insCompleted = computed(() =>
  filterCond.value === "03"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const cancel = computed(() =>
  filterCond.value === "05"
    ? "btn-common btn-active btn-focus"
    : "btn-common btn-active"
);

const filterCond = ref("00");

//조건검색 클릭
const onClickSearchType = async (val) => {
  for (var i = 0; i < rows.value.length; i++) {
    rows.value[i].expand1 = false;
    rows.value[i].expand2 = false;
  }

  if (val === "전체") {
    filterCond.value = "00";
  } else if (val === "미등록N") {
    filterCond.value = "01";
  } else if (val === "담당자N") {
    filterCond.value = "11";
  } else if (val === "처리중N") {
    filterCond.value = "02";
  } else if (val === "검수완료N") {
    filterCond.value = "03";
  } else if (val === "취소N") {
    filterCond.value = "05";
  }
  // await fetchItemList();
};

const radioLabelA = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) => item.BACK_EXAM_CD === "01"
  ).length;

  return `미등록 ${tempCnt}건`;
});
const radioLabelB = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) => item.BACK_EXAM_CD === "11"
  ).length;

  return `담당자 ${tempCnt}건`;
});
const radioLabelC = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) => item.BACK_EXAM_CD === "02"
  ).length;

  return `처리중 ${tempCnt}건`;
});
const radioLabelD = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) => item.BACK_EXAM_CD === "03"
  ).length;

  return `완료 ${tempCnt}건`;
});
const radioLabelE = computed(() => {
  const tempCnt = toRaw(rows.value).filter(
    (item) => item.BACK_EXAM_CD === "05"
  ).length;

  return `취소 ${tempCnt}건`;
});

const filteredRows = computed(() => {
  if (rows.value.length > 0) {
    if (filterCond.value === "01") {
      // 미등록
      return rows.value.filter((item) => item.BACK_EXAM_CD === "01");
    } else if (filterCond.value === "11") {
      // 담당자
      return rows.value.filter((item) => item.BACK_EXAM_CD === "11");
    } else if (filterCond.value === "02") {
      // 처리중
      return rows.value.filter((item) => item.BACK_EXAM_CD === "02");
    } else if (filterCond.value === "03") {
      // 검수완료
      return rows.value.filter((item) => item.BACK_EXAM_CD === "03");
    } else if (filterCond.value === "05") {
      // 취소
      return rows.value.filter((item) => item.BACK_EXAM_CD === "05");
    }
  }

  // 전체
  return rows.value;
});

onMounted(async () => {
  await fetchItemList();
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
  grid-template-columns: repeat(6, 1fr);
  // padding: 8px 0 !important;
  gap: 8px;
  .table_item:nth-child(1) {
    @include flex_start;
    @include fw-6;
    align-items: flex-start;
    grid-column: span 5;
    gap: 4px;
    height: auto !important;
    td {
      // overflow: hidden;
      // text-overflow: ellipsis;
      height: auto !important;
      white-space: normal;
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 1;
    td {
      @include flex_end;
      @include fs-5;
      @include fw-6;
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 3;
    td {
      @include flex_start;
      gap: 8px;
      span {
        @include fs-5;
      }
      // span:nth-child(2) {
      //   color: #666666;
      // }
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 1;
    td {
      @include flex_end;
      gap: 4px;
      span {
        @include fs-5;
        // color: #666666;
      }
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 2;
    td {
      @include flex_end;
      gap: 4px;
      span {
        // @include fs-5;
      }
    }
  }
  .table_item:nth-child(6) {
    grid-column: span 6;
    td {
      @include flex_between;
      > div:nth-child(1) {
        @include flex_start;
        gap: 8px;
      }
      > div:nth-child(2) {
        @include flex_end;
        // gap: 8px;
        text-align: right;
      }
    }
  }
}

.expand_table_wrap {
  .expand_table_item {
    @include flex_between;
    padding: 16px 15px 8px;
    border-bottom: 1px solid $line-400;
    td {
    }
  }
}

//기간 표시 스타일
// .button-container {
//   .btn-focus {
//     background-image: linear-gradient(to bottom, #1f3f82, #003853) !important;
//     color: #ffffff !important;
//   }
// }
</style>

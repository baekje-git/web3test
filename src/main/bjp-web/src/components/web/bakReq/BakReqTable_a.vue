<template>
  <div class="">
    <q-table
      class="salesTable_c_top"
      :rows="filteredRows"
      :columns="top_columns"
      row-key="SN"
      table-header-class="table_header"
      dense
      :hide-bottom="isHideBottom"
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      v-model:pagination="pagination"
      :loading="loadingTop"
      :selected="selectedTop"
    >
      <div class="row justify-center q-mt-md">
        <q-pagination
          v-model="pagination.page"
          color="grey-8"
          :max="pagesNumber"
          size="sm"
        />
      </div>
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="q-table_header_wrap flex_between">
            <div class="q-table_header_title flex_between">
              <p>반품기간</p>
              <div class="radio-group" style="margin-left: 15px">
                <!-- <RadioComp_a
              v-model="salesRadio"
              :radio_options="salesRadioOptions"
            /> -->
                <q-radio
                  v-model="filterCond"
                  color="blue-10"
                  size="xs"
                  inline
                  dense
                  val="00"
                  style="margin-left: 8px"
                  label="전체"
                />
                <q-radio
                  v-model="filterCond"
                  color="blue-10"
                  size="xs"
                  inline
                  dense
                  val="01"
                  style="margin-left: 8px"
                  :label="radioLabelA"
                />
                <q-radio
                  v-model="filterCond"
                  color="blue-10"
                  size="xs"
                  inline
                  dense
                  val="11"
                  style="margin-left: 8px"
                  :label="radioLabelB"
                />
                <q-radio
                  v-model="filterCond"
                  color="blue-10"
                  size="xs"
                  inline
                  dense
                  val="02"
                  style="margin-left: 8px"
                  :label="radioLabelC"
                />
                <q-radio
                  v-model="filterCond"
                  color="blue-10"
                  size="xs"
                  inline
                  dense
                  val="03"
                  style="margin-left: 8px"
                  :label="radioLabelD"
                />
                <q-radio
                  v-model="filterCond"
                  color="blue-10"
                  size="xs"
                  inline
                  dense
                  val="05"
                  style="margin-left: 8px"
                  :label="radioLabelE"
                />
              </div>
            </div>
            <div class="q-table_header_title_sub">
              <div class="q-table_header_btnOption">
                <!-- <BtnComp_options /> -->

                <div class="Btn_options_wrap">
                  <q-btn
                    :class="periodClassA"
                    label="당월"
                    dense
                    @click="onClickPeriod(0)"
                  />
                  <q-btn
                    :class="periodClassB"
                    label="전월"
                    dense
                    @click="onClickPeriod(1)"
                  >
                  </q-btn>
                  <q-btn
                    :class="periodClassC"
                    label="3개월"
                    dense
                    @click="onClickPeriod(3)"
                  />
                  <q-btn
                    :class="periodClassD"
                    label="6개월"
                    dense
                    @click="onClickPeriod(6)"
                  />
                  <q-btn
                    :class="periodClassE"
                    label="1년"
                    dense
                    @click="onClickPeriod(12)"
                  />
                  <q-btn
                    :class="periodClassF"
                    label="2년"
                    dense
                    @click="onClickPeriod(24)"
                  />
                  <q-btn
                    :class="periodClassG"
                    label="3년"
                    dense
                    @click="onClickPeriod(36)"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="q-table_header_sub_wrap">
            <div class="flex_start">
              <div class="SearchComp_wrap">
                <div class="q-table_input_wrap">
                  <q-input
                    v-model="searchParam.keyword"
                    class="input_search1"
                    borderless
                    dense
                    placeholder="품목명/보험코드 입력"
                    @keyup.enter="onClickSearch"
                    @focus="(input) => input.target.select()"
                    autofocus
                  />

                  <q-input
                    v-model="searchParam.makerNm"
                    class="input_search2"
                    borderless
                    dense
                    placeholder="제약회사명 입력"
                    @keyup.enter="onClickSearch"
                    @focus="(input) => input.target.select()"
                  />
                </div>
              </div>
              <div class="q-table_calrendar_wrap">
                <!-- <DatePickerComp_b /> -->
                <div class="flex_between" style="gap: 4px">
                  <div class="DatePickerComp_b_wrap">
                    <div class="date_input_wrap">
                      <!-- <p style="color: black; font-weight: 400">
                        {{ date1 }}
                      </p> -->
                      <q-input
                        input-class="input_default"
                        v-model="searchParam.startDt"
                        borderless
                        dense
                        @blur="validDate"
                        @focus="(input) => input.target.select()"
                        mask="####-##-##"
                      />
                    </div>
                    <div class="date_btn_wrap">
                      <q-btn class="calendar_icon" unelevated dense>
                        <img
                          src="assets/icons/calendar-days-solid.svg"
                          alt="calendar"
                        />
                        <q-popup-proxy
                          @before-show="updateProxy1"
                          cover
                          transition-show="scale"
                          transition-hide="scale"
                        >
                          <q-date
                            v-model="proxyDate1"
                            mask="YYYY-MM-DD"
                            @update:model-value="updateStartDt"
                            :default-date="today"
                            :title="selectedDate1"
                            :subtitle="selectedYear1"
                          >
                            <div
                              class="row items-center justify-end q-gutter-sm"
                            >
                              <q-btn
                                label="취소"
                                color="primary"
                                flat
                                v-close-popup
                              />
                              <q-btn
                                label="확인"
                                color="primary"
                                flat
                                @click="save1"
                                v-close-popup
                              />
                            </div>
                          </q-date>
                        </q-popup-proxy>
                      </q-btn>
                    </div>
                  </div>
                  <p style="color: black; font-weight: 400">~</p>
                  <div class="DatePickerComp_b_wrap">
                    <div class="date_input_wrap">
                      <!-- <p style="color: rgb(39, 37, 37); font-weight: 400">
                        {{ date2 }}
                      </p> -->
                      <q-input
                        input-class="input_default"
                        v-model="searchParam.endDt"
                        borderless
                        dense
                        @blur="validDate"
                        @focus="(input) => input.target.select()"
                        mask="####-##-##"
                      />
                    </div>
                    <div class="date_btn_wrap">
                      <q-btn class="calendar_icon" unelevated dense>
                        <img
                          src="assets/icons/calendar-days-solid.svg"
                          alt="calendar"
                        />
                        <q-popup-proxy
                          @before-show="updateProxy2"
                          cover
                          transition-show="scale"
                          transition-hide="scale"
                        >
                          <q-date
                            v-model="proxyDate2"
                            mask="YYYY-MM-DD"
                            @update:model-value="updateEndDt"
                            :default-date="today"
                            :title="selectedDate2"
                            :subtitle="selectedYear2"
                          >
                            <div
                              class="row items-center justify-end q-gutter-sm"
                            >
                              <q-btn
                                label="취소"
                                color="primary"
                                flat
                                v-close-popup
                              />
                              <q-btn
                                label="확인"
                                color="primary"
                                flat
                                @click="save2"
                                v-close-popup
                              />
                            </div>
                          </q-date>
                        </q-popup-proxy>
                      </q-btn>
                    </div>
                  </div>
                </div>
              </div>
              <BtnComp_default
                class="btn_srch"
                btn-label="검색"
                @click="onClickSearch"
              />
            </div>
            <!-- <div class="q-table_btn_export_wrap"> -->
            <BtnComp_default
              class="btn-line default btn-pd12 btn-excel"
              btn-label="엑셀"
              @click="exportTable"
            />
            <!-- </div> -->
          </div>
        </div>
      </template>

      <template v-slot:top-row v-if="filteredRows.length === 0">
        <q-tr class="no-date">
          <q-td colspan="100%">
            <span>검색된 결과가 없습니다.</span>
          </q-td>
        </q-tr>
      </template>

      <template v-slot:header-cell-소분반품라벨="props">
        <!-- <q-th :props="props" style="position: relative"> -->
        <q-th :props="props">
          <div class="tooltip_wrap">
            <span
              style="display: inline-block; height: 2.2em; line-height: 1.15em"
              >소분<br />반품라벨</span
            >
            <!-- {{ props.col.label }} -->
            <q-badge rounded style="top: 0; left: 78%">
              ?
              <q-tooltip anchor="top right" self="top left">
                <p>
                  소분반품라벨 활용 <br />
                  - 소분반품라벨을 품목별로 약품 케이스에 부착해 주십시오.
                </p>
              </q-tooltip>
            </q-badge>
          </div>
        </q-th>
      </template>

      <template v-slot:header-cell-박스라벨="props">
        <!-- <q-th :props="props" style="position: relative"> -->
        <q-th :props="props">
          <div class="tooltip_wrap">
            {{ props.col.label }}
            <q-badge rounded>
              ?
              <q-tooltip anchor="top right" self="top left">
                <p>
                  박스라벨 활용 <br />
                  - 대형 : 반품박스에 부착해 주십시오.<br />
                  - 소형 : 소형박스 또는 비닐봉투에 부착해 주십시오.
                </p>
              </q-tooltip>
            </q-badge>
          </div>
        </q-th>
      </template>

      <template v-slot:body-cell-BACK_DT="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <div class="q-table_progress_wrap">
            <p>
              {{
                props.row.BACK_DT != ""
                  ? moment(props.row.BACK_DT, "YYYY-MM-DD HH:mm:ss").format(
                      "YYYY-MM-DD"
                    )
                  : ""
              }}
            </p>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-EXAM_DT="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <div class="q-table_progress_wrap">
            <p>
              {{
                props.row.EXAM_DT != ""
                  ? moment(props.row.EXAM_DT, "YYYY-MM-DD HH:mm:ss").format(
                      "YYYY-MM-DD"
                    )
                  : ""
              }}
            </p>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_NM_UNIT="props">
        <q-td
          :props="props"
          style="cursor: pointer; text-align: left"
          :class="{ 'q-tr--selected': isSelected(props.row) }"
          @click="onClickRow(props.row)"
        >
          <div class="q-table_progress_wrap td-prd_name">
            <p>
              {{ props.row.ITEM_NM_UNIT }}
            </p>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_CNT="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <div class="q-table_progress_wrap">
            <p>
              {{ props.row.ITEM_CNT }}
            </p>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-TOTAL_AMT="props">
        <q-td
          :props="props"
          style="text-align: right; cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <div class="q-table_progress_wrap">
            <p>
              {{ util.getNumberFormat(props.row.TOTAL_AMT) }}
            </p>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-BACK_EXAM_NM="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <div class="q-table_progress_wrap">
            <p
              :class="{
                'red-400': props.row.BACK_EXAM_NM === '취소완료',
                'gray-5': props.row.BACK_EXAM_NM === '미등록',
              }"
            >
              {{ props.row.BACK_EXAM_NM }}
            </p>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ORD_MEMO="props">
        <q-td :props="props">
          <MemoComp_default
            :memo="props.row.ORD_MEMO"
            :is-visible="props.row.BACK_EXAM_CD === '01' ? true : false"
            :is-disable="props.row.BACK_EXAM_CD === '01' ? false : true"
            @update:model-value="(memo) => updateBakMemo(props.row, memo)"
          >
            <BtnComp_active_small
              class="btn-line default"
              v-if="props.row.ORD_MEMO === ''"
              btn_activeLabel="메모"
            />
            <BtnComp_default_small
              class="btn-line default"
              v-if="props.row.ORD_MEMO !== ''"
              btn_default-label="메모"
            />
          </MemoComp_default>
        </q-td>
      </template>

      <template v-slot:body-cell-회수확인서="props">
        <q-td :props="props">
          <img
            v-if="props.row.HAZARD_YN === 'Y'"
            src="assets/icons/file-lines-solid.svg"
            alt="file"
            style="cursor: pointer"
            @click="onClicReturnConfirmation(props)"
          />
        </q-td>
      </template>

      <template v-slot:body-cell-소분반품라벨="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <img
            v-if="props.row.CNT > 0"
            src="assets/icons/file-lines-solid.svg"
            alt="file"
            style="cursor: pointer"
            @click="onClickSmallReturnLabel(props)"
          />
        </q-td>
      </template>

      <template v-slot:body-cell-박스라벨="props">
        <q-td :props="props">
          <div class="q-table_boxLevel_wrap">
            <BtnComp_active_small
              class="btn-line default"
              btn_activeLabel="소형"
              @click="onClickSmallBoxLabel(props)"
            />
            <BtnComp_active_small
              class="btn-line default"
              btn_activeLabel="대형"
              @click="onClickBigBoxLabel(props)"
            />
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-PRINT_YN="props">
        <q-td :props="props" style="cursor: pointer">
          <div v-if="props.row.PRINT_YN === 'Y'">
            <BtnComp_active_small
              class="btn-line default"
              v-if="props.row.BACK_PRT_CNT > 0"
              btn_activeLabel="재출력"
              @click="exportPrint(props.row)"
            />
            <BtnComp_active_small
              class="btn-line default"
              v-else
              btn_activeLabel="출력"
              @click="exportPrint(props.row)"
            />
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-TREAT_MEMO="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <MemoComp_default_a :memo="props.row.TREAT_MEMO">
            <BtnComp_active_small
              class="btn-line default"
              v-if="props.row.TREAT_MEMO === ''"
              btn_activeLabel="메모"
            />
            <BtnComp_default_small
              class="btn-line default"
              v-if="props.row.TREAT_MEMO !== ''"
              btn_default-label="메모"
            />
          </MemoComp_default_a>
        </q-td>
      </template>

      <template v-slot:body-cell-CNCL_BAK_REQ="props">
        <q-td
          :props="props"
          style="cursor: pointer"
          @click="onClickRow(props.row)"
        >
          <div class="">
            <BtnComp_default
              class="btn-line default btn-sm"
              v-if="props.row.CNCL_ABLE_YN === 'Y'"
              btnLabel="반품취소"
              @click="onClickCnclBakReq(props.row)"
            />
          </div>
        </q-td>
      </template>
    </q-table>

    <!--///////////////////////////////////////////////////////////////////-->

    <q-table
      class="salesTable_c_bottom"
      :rows="bottom_rows"
      :columns="bottom_columns"
      row-key="ITEM_CD"
      table-header-class="table_header"
      dense
      hide-bottom
      selection="multiple"
      v-model:selected="selected"
      color="blue-10"
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      style="margin-top: 30px"
      :loading="loadingBottom"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="flex_between" style="margin: 12px 0 8px">
            <div class="q-table_header_wrap">
              <div class="q-table_header_title">
                <p>반품상세</p>
              </div>
            </div>
            <div class="q-table_btn_export_wrap">
              <BtnComp_default
                v-if="canYn === 'Y'"
                btnLabel="삭제"
                @click="onClickDelBakReqDtl"
              />
            </div>
          </div>
        </div>
      </template>

      <template v-slot:top-row v-if="bottom_rows.length === 0">
        <q-tr class="no-date">
          <q-td colspan="100%">
            <span>검색된 결과가 없습니다.</span>
          </q-td>
        </q-tr>
      </template>

      <!-- 제약회사/ -->
      <template v-slot:body-cell-MAKER_NM="props">
        <q-td :props="props" @click="onRowClick(props)">
          <div class="q-table_itemTitleSub ellipsis">
            {{ props.value }}<q-tooltip>{{ props.value }}</q-tooltip>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_NM_UNIT="props">
        <q-td :props="props" class="td-prd">
          <div class="q-table_item">
            <div class="q-table_badge_wrap">
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
            <div class="td-prd_name">
              {{ props.row.ITEM_NM_UNIT }}
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ITEM_QTY_NO="props">
        <q-td :props="props">
          <div class="">
            <div v-if="props.row.ITEM_QTY_NO > 0" class="red-400 bold">
              {{ props.row.ITEM_QTY_NO }}
            </div>
            <div v-else>
              {{ props.row.ITEM_QTY_NO }}
            </div>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-ORD_WP2_AMT="props">
        <q-td :props="props">
          <div class="">
            <p>
              {{ util.getNumberFormat(props.row.ORD_WP2_AMT) }}
            </p>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-PRICE="props">
        <q-td :props="props">
          <div class="">
            <p>
              {{ util.getNumberFormat(props.row.PRICE) }}
            </p>
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-BOHUM_CD="props">
        <q-td :props="props">
          <div class="">
            {{ props.row.BOHUM_CD }}
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-EXAM_RSLT_NM="props">
        <q-td :props="props">
          <div class="">
            {{ props.row.EXAM_RSLT_NM }}
          </div>
        </q-td>
      </template>

      <template v-slot:header-selection="scope">
        <q-checkbox v-model="scope.selected" v-if="canYn === 'Y'" />
      </template>
      <template v-slot:body-selection="scope">
        <q-checkbox v-model="scope.selected" v-if="canYn === 'Y'" />
      </template>
    </q-table>
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { computed, ref, onMounted, toRaw } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";
import { exportFile, useQuasar } from "quasar";
import * as reportUtil from "src/router/report";

import RadioComp_a from "src/components/web/common/RadioComp_a.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";
import BtnComp_active_small from "src/components/web/common/BtnComp_active_small.vue";
import BtnComp_purple from "src/components/web/common/BtnComp_purple.vue";
import BtnComp_options from "src/components/web/common/BtnComp_options.vue";
import MemoComp_default from "src/components/web/common/MemoComp_default.vue";
import MemoComp_default_a from "src/components/web/common/MemoComp_default_a.vue";
import SearchComp_select from "src/components/web/common/SearchComp_select.vue";
import DatePickerComp_b from "src/components/web/common/DatePickerComp_b.vue";

const userInfo = util.getUserData();
const top_rows = ref([]);
const bottom_rows = ref([]);
const isHideBottom = computed(() => top_rows.value.length === 0);
const loadingTop = ref(false);
const loadingBottom = ref(false);
const selectedTop = ref([]);

const pagination = ref({
  sortBy: "desc",
  descending: false,
  page: 1,
  rowsPerPage: 13,
  // rowsNumber: xx if getting data from a server
});

const pagesNumber = computed(() =>
  Math.ceil(top_rows.value.length / pagination.value.rowsPerPage)
);

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  startDt: "",
  endDt: "",
  keyword: "",
  makerNm: "",
});

const dataParam = ref({
  sn: "",
  bakMemo: "",
});

const selected = ref([]);
const period = ref("");

const periodClassA = computed(() =>
  period.value === 0 ? "btn" : "btn_options"
);
const periodClassB = computed(() =>
  period.value === 1 ? "btn" : "btn_options"
);
const periodClassC = computed(() =>
  period.value === 3 ? "btn" : "btn_options"
);
const periodClassD = computed(() =>
  period.value === 6 ? "btn" : "btn_options"
);
const periodClassE = computed(() =>
  period.value === 12 ? "btn" : "btn_options"
);
const periodClassF = computed(() =>
  period.value === 24 ? "btn" : "btn_options"
);
const periodClassG = computed(() =>
  period.value === 36 ? "btn" : "btn_options"
);

const filterCond = ref("00");

const filteredRows = computed(() => {
  if (top_rows.value.length > 0) {
    if (filterCond.value === "01") {
      // 미등록
      return toRaw(top_rows.value).filter((item) => item.BACK_EXAM_CD === "01");
    } else if (filterCond.value === "11") {
      // 담당자
      return toRaw(top_rows.value).filter((item) => item.BACK_EXAM_CD === "11");
    } else if (filterCond.value === "02") {
      // 처리중
      return toRaw(top_rows.value).filter((item) => item.BACK_EXAM_CD === "02");
    } else if (filterCond.value === "03") {
      // 검수완료
      return toRaw(top_rows.value).filter((item) => item.BACK_EXAM_CD === "03");
    } else if (filterCond.value === "05") {
      // 취소
      return toRaw(top_rows.value).filter((item) => item.BACK_EXAM_CD === "05");
    }
  }

  // 전체
  return top_rows.value;
});

const radioLabelA = computed(() => {
  const tempCnt = toRaw(top_rows.value).filter(
    (item) => item.BACK_EXAM_CD === "01"
  ).length;

  return `미등록 ${tempCnt}건`;
});
const radioLabelB = computed(() => {
  const tempCnt = toRaw(top_rows.value).filter(
    (item) => item.BACK_EXAM_CD === "11"
  ).length;

  return `담당자 ${tempCnt}건`;
});
const radioLabelC = computed(() => {
  const tempCnt = toRaw(top_rows.value).filter(
    (item) => item.BACK_EXAM_CD === "02"
  ).length;

  return `처리중 ${tempCnt}건`;
});
const radioLabelD = computed(() => {
  const tempCnt = toRaw(top_rows.value).filter(
    (item) => item.BACK_EXAM_CD === "03"
  ).length;

  return `검수완료 ${tempCnt}건`;
});
const radioLabelE = computed(() => {
  const tempCnt = toRaw(top_rows.value).filter(
    (item) => item.BACK_EXAM_CD === "05"
  ).length;

  return `취소 ${tempCnt}건`;
});

const canYn = ref("");

const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

//---------------------------- Function ------------------------------//

async function fetchItemDtlList() {
  loadingBottom.value = true;
  bottom_rows.value = [];

  const res = await api.get("/bakReq/detailSearch", {
    params: dataParam.value,
  });

  bottom_rows.value = res;
  loadingBottom.value = false;
}

async function fetchItemList() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  if (startDt === "" || endDt === "") {
    alert("기간을 입력해 주시기 바랍니다.");
    return;
  }

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  loadingTop.value = true;
  pagination.value.page = 1;

  const res = await api.get("/bakReq/listSearch", {
    params: searchParam.value,
  });

  top_rows.value = res;

  if (top_rows.value.length > 0) {
    dataParam.value.sn = top_rows.value[0].SN;

    await onClickRow(top_rows.value[0]);
    await fetchItemDtlList();
  } else {
    bottom_rows.value = [];
  }
  loadingTop.value = false;
}

// 검색버튼 클릭
async function onClickSearch() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  if (!moment(startDt).isBefore(endDt) && !moment(startDt).isSame(endDt)) {
    alert("입력한 끝 날짜가 시작 날짜보다 빠릅니다");
    searchParam.value.startDt = "";
    date1.value = "";

    return;
  }
  await fetchItemList();
}

const isSelected = (row) => {
  return selected.value.includes(row);
};

// 그리드 row 클릭
async function onClickRow(row) {
  selected.value = [];
  const index = selectedTop.value.indexOf(row);
  if (index === -1) {
    selectedTop.value = [];
    selectedTop.value.push(row);
  } else {
    selectedTop.value.splice(index, 1);
  }

  dataParam.value.sn = row.SN;
  canYn.value = row.CNCL_ABLE_YN;

  await fetchItemDtlList();
}

// 메모 수정
async function updateBakMemo(row, memo) {
  dataParam.value.sn = row.SN;
  dataParam.value.bakMemo = memo;

  await api.post("/bakReq/saveBakMemo", dataParam.value);
  util.showNotify("저장되었습니다.");
  await fetchItemList();
}

// 조회기간 버튼 클릭
async function onClickPeriod(val) {
  period.value = val;

  await setDate(val);
  await fetchItemList();
}

// 반품취소 버튼 클릭
async function onClickCnclBakReq(row) {
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

  if (!confirm("반품 취소하시겠습니까?")) return;

  try {
    Object.assign(row, { USER_ID: userInfo.USER_ID });

    await api.delete("/bakReq/cancelBakReq", { params: row });

    util.showNotify("취소되었습니다.");

    await fetchItemList();
  } catch (error) {
    // alert(error.response.data);
    alert("오류가 발생했습니다.");
  }
}

// 삭제 버튼 클릭
async function onClickDelBakReqDtl() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  Object.assign(searchParam.value, { sn: bottom_rows.value[0].SN });

  // console.log(searchParam);
  const res = await api.get("/bakReq/chk", {
    params: searchParam.value,
  });

  if (res.CNCL_ABLE_YN != "Y") {
    alert(
      "선택하신 반품건에 대해 진행상태가 변경되어 삭제가 불가능합니다.\n다시 확인하시고 진행해주시기 바랍니다."
    );
    return;
  }

  if (selected.value.length === 0) {
    alert("선택된 항목이 없습니다.");
    return;
  }

  if (selected.value.length == bottom_rows.value.length) {
    alert(
      "전체 선택한 경우에는 접수 마스터 항목에서 반품취소를 하시기 바랍니다."
    );
    return;
  }

  if (bottom_rows.value.length === 1) {
    alert(
      "품목이 1개인 경우에는 접수 마스터 항목에서 반품취소를 하시기 바랍니다."
    );
    return;
  }

  if (!confirm("선택한 항목을 삭제하시겠습니까?")) return;

  try {
    selected.value.forEach((m) => {
      Object.assign(m, { USER_ID: userInfo.USER_ID });
    });

    await api.post("/bakReq/deleteBakReqDtl", selected.value);

    util.showNotify("삭제되었습니다.");

    await fetchItemList();
    await fetchItemDtlList();
  } catch (error) {
    // alert(error.response.data);
    alert("오류가 발생했습니다.");
  }
}

// 회수확인서
async function onClicReturnConfirmation(data) {
  /* 레포트 호출 부분 */
  const param = {
    /* 레포트 파라미터 예시*/
    dataset_0: { CUST_CD: userInfo.CUST_CD },
    dataset_1: { sn: data.row.SN },
  };

  const paramList = [];
  paramList.CUST_CD = userInfo.CUST_CD;
  paramList.sn = data.row.SN;

  reportUtil.ubiReport2(
    param,
    /* 레포트 명 */
    "WithdrawConfirm",
    paramList,
    "if_report"
  );
}

// 소분반품라벨
async function onClickSmallReturnLabel(data) {
  /* 레포트 호출 부분 */
  const param = {
    /* 레포트 파라미터 예시*/
    dataset_0: {},
  };
  const paramList = [];

  paramList.SN = data.row.SN;
  paramList.FROMDT = searchParam.value.startDt;
  paramList.TODT = searchParam.value.endDt;

  reportUtil.ubiReport2(
    param,
    /* 레포트 명 */
    "ordLedgerBakRcptPrt",
    paramList,
    "if_report"
  );
}

// 박스라벨 소형
async function onClickSmallBoxLabel(data) {
  const res = await api.post("/bakReq/getDlvInfo", {
    custCd: userInfo.CUST_CD,
  });

  /* 레포트 호출 부분 */
  const param = {
    dataset_0: {},
    dataset_1: {},
    dataset_2: {},
  };

  const paramList = [];
  paramList.CUST_CD = userInfo.CUST_CD;
  paramList.DLV_COURSE_CD = res.DLV_COURSE_CD;
  paramList.DLV_BRCH_CD = res.DLV_BRCH_CD;
  paramList.DLV_AREA_CD = res.DLV_AREA_CD;

  reportUtil.ubiReport2(
    param,
    /* 레포트 명 */
    "BoxLabelSmall",
    paramList,
    "if_report"
  );
}

// 박스라벨 대형
async function onClickBigBoxLabel(data) {
  const res = await api.post("/bakReq/getDlvInfo", {
    custCd: userInfo.CUST_CD,
  });

  /* 레포트 호출 부분 */
  const param = {
    dataset_0: {},
    dataset_1: {},
    dataset_2: {},
  };

  const paramList = [];
  paramList.CUST_CD = userInfo.CUST_CD;
  paramList.DLV_COURSE_CD = res.DLV_COURSE_CD;
  paramList.DLV_BRCH_CD = res.DLV_BRCH_CD;
  paramList.DLV_AREA_CD = res.DLV_AREA_CD;

  reportUtil.ubiReport2(
    param,
    /* 레포트 명 */
    "BoxLabelBig",
    paramList,
    "if_report"
  );
}

/*********** datepicker ***********/
// 컴포넌트로 사용할수가 없어
// DatePickerComp_b.vue 이벤트 여기로 뻄

const date1 = ref("");
const date2 = ref("");
const proxyDate1 = ref("");
const proxyDate2 = ref("");
// const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

const updateProxy1 = () => {
  proxyDate1.value = date1.value;
};
const save1 = () => {
  date1.value = proxyDate1.value;
};
const updateProxy2 = () => {
  proxyDate2.value = date2.value;
};
const save2 = () => {
  date2.value = proxyDate2.value;
};

// 오늘 날짜와 기본 날짜 설정 250307
const today = new Date();
// 캘린더 title 250307
const selectedDate1 = computed(() => {
  const date = new Date(proxyDate1.value);
  const month = date.getMonth() + 1; // 0-11에서 1-12로 변경
  const day = date.getDate();
  const weekday = date.toLocaleDateString("ko-KR", { weekday: "short" }); // 요일 가져오기

  return proxyDate1.value
    ? `${month}월 ${day}일 (${weekday[0].toUpperCase() + weekday.slice(1)})`
    : `${new Date(today).getMonth() + 1}월 ${new Date(today).getDate()}일 (${
        weekday[0].toUpperCase() + weekday.slice(1)
      })`;
});
// 캘린더 subtitle 250307
const selectedYear1 = computed(() => {
  return proxyDate1.value
    ? new Date(proxyDate1.value).getFullYear()
    : new Date(today).getFullYear();
});

// 캘린더 title 250307
const selectedDate2 = computed(() => {
  const date = new Date(proxyDate2.value);
  const month = date.getMonth() + 1; // 0-11에서 1-12로 변경
  const day = date.getDate();
  const weekday = date.toLocaleDateString("ko-KR", { weekday: "short" }); // 요일 가져오기

  return proxyDate2.value
    ? `${month}월 ${day}일 (${weekday[0].toUpperCase() + weekday.slice(1)})`
    : `${new Date(today).getMonth() + 1}월 ${new Date(today).getDate()}일 (${
        weekday[0].toUpperCase() + weekday.slice(1)
      })`;
});
// 캘린더 subtitle 250307
const selectedYear2 = computed(() => {
  return proxyDate2.value
    ? new Date(proxyDate2.value).getFullYear()
    : new Date(today).getFullYear();
});

function getYYYYMMDD(date) {
  return (
    date.getFullYear() +
    "." +
    (date.getMonth() + 1).toString().padStart(2, "0") +
    "." +
    date.getDate().toString().padStart(2, "0")
  );
}

async function setDate(val) {
  if (!val) {
    val = 1;
  } else {
    val++;
  }

  const now = new Date();
  const year = now.getFullYear();
  const month = now.getMonth() + 1;
  const strtDt = new Date(year, month - val, 1);
  const endDt = new Date(year, month, 0);

  date1.value = getYYYYMMDD(strtDt);
  date2.value = getYYYYMMDD(endDt);
  proxyDate1.value = getYYYYMMDD(strtDt);
  proxyDate2.value = getYYYYMMDD(endDt);

  searchParam.value.startDt = date1.value.replace(match, "");
  searchParam.value.endDt = date2.value.replace(match, "");
}

function updateStartDt() {
  searchParam.value.startDt = proxyDate1.value.replace(match, "");
}

function updateEndDt() {
  searchParam.value.endDt = proxyDate2.value.replace(match, "");
}

function validDate() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  if (startDt) {
    if (!moment(startDt, "YYYY-MM-DD", true).isValid()) {
      alert("유효하지 않은 날짜 형식입니다.");
      searchParam.value.startDt = "";
      date1.value = "";

      return;
    }
  }

  if (endDt) {
    if (!moment(endDt, "YYYY-MM-DD", true).isValid()) {
      alert("유효하지 않은 날짜 형식입니다.");
      searchParam.value.endDt = "";
      date2.value = "";

      return;
    }
  }
}
/**********************************/

function exportTable() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  api
    .get("excel/bakReq", {
      params: searchParam.value,
      responseType: "arraybuffer",
    })
    .then(function (res) {
      const url = window.URL.createObjectURL(new Blob([res]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute(
        "download",
        "반품현황_" +
          searchParam.value.startDt +
          "_" +
          searchParam.value.endDt +
          ".xls"
      );
      document.body.appendChild(link);
      link.click();
      link.remove();
    })
    .catch(function (error) {
      console.log(error);
    });
}

function exportPrint(row) {
  var openParam = "";
  var cw = screen.availWidth; // 화면 넓이
  var ch = screen.availHeight; // 화면 높이
  var sw = 825; // 띄울 창의 넓이
  var sh = 760; // 띄울 창의 높이
  var ml = (cw - sw) / 2; // 가운데 띄우기위한 창의 x위치
  var mt = (ch - sh) / 2; // 가운데 띄우기위한 창의 y위치

  if (navigator.userAgent.indexOf("Chrome") > 0) {
    sw = sw + 20;
    sh = sh + 10;
    openParam = "width=" + sw + ",height=" + sh + ",top=" + mt + ",left=" + ml;
  } else if (navigator.userAgent.indexOf("MSIE") > 0) {
    /*IE10미만*/
    openParam = "width=" + sw + ",height=" + sh + ",top=" + mt + ",left=" + ml;
  } else {
    /*IE10이상*/
    openParam = "width=" + sw + ",height=" + sh + ",top=" + mt + ",left=" + ml;
  }

  var parmeter = "sn=" + row.SN;
  parmeter += "&custCd=" + userInfo.CUST_CD;
  parmeter += "&custNm=" + userInfo.CUST_NM;
  if (userInfo.USER_GB_CD !== "30") {
    parmeter += "&empNm=" + userInfo.EMP_NM;
    parmeter += "&empHpNo=" + userInfo.EMP_HP_NO;
  }
  parmeter += "&brchSnm=" + userInfo.BRCH_SNM;
  window.open(
    process.env.PRINT_URL + "/bakReq?" + parmeter,
    "_blank",
    openParam
  );

  const upatePrintOrder = {
    SN: row.SN,
  };
  api.put("/ordLedger/upatePrintOrder", upatePrintOrder);

  // await fetchBasketList();
}

onMounted(async () => {
  await setDate(1);
  period.value = 1;
  await fetchItemList();
});

//-------------------------- Grid Setting ----------------------------//

const top_columns = ref([
  {
    name: "BACK_DT",
    label: "등록일자",
    align: "center",
    required: true,
    field: "BACK_DT",
    classes: "td-date",
    format: (val) => `${val}`,
  },
  {
    name: "EXAM_DT",
    label: "검수일자",
    field: "EXAM_DT",
    align: "center",
    classes: "td-date",
  },
  {
    name: "ITEM_NM_UNIT",
    label: "대표품목",
    field: "ITEM_NM_UNIT",
    align: "left",
    classes: "td-prd",
  },
  {
    name: "ITEM_CNT",
    label: "품목수",
    field: "ITEM_CNT",
    align: "center",
    classes: "td-count",
  },
  {
    name: "TOTAL_AMT",
    label: "총금액",
    field: "TOTAL_AMT",
    align: "right",
    classes: "td-price",
  },
  {
    name: "BACK_EXAM_NM",
    label: "진행현황",
    field: "BACK_EXAM_NM",
    align: "center",
    classes: "td-exam",
  },
  {
    name: "ORD_MEMO",
    label: "반품메모",
    field: "ORD_MEMO",
    align: "center",
    classes: "td-memo",
  },
  {
    name: "회수확인서",
    label: "회수확인서",
    field: "회수확인서",
    align: "center",
    classes: "td-label",
  },
  {
    name: "소분반품라벨",
    label: "소분반품라벨",
    field: "소분반품라벨",
    align: "center",
    classes: "td-label",
  },
  {
    name: "박스라벨",
    label: "박스라벨",
    field: "박스라벨",
    align: "center",
    classes: "td-label_box",
  },
  {
    name: "PRINT_YN",
    label: "반품요청서",
    field: "PRINT_YN",
    align: "center",
    classes: "td-print",
  },
  {
    name: "TREAT_MEMO",
    label: "담당자메모",
    field: "TREAT_MEMO",
    align: "center",
    classes: "td-memo",
  },
  {
    name: "CNCL_BAK_REQ",
    label: "반품취소",
    field: "CNCL_BAK_REQ",
    align: "center",
    classes: "td-cncl",
  },
]);

const bottom_columns = ref([
  // {
  //   name: "선택",
  //   label: "",
  //   align: "left",
  //   sortable: true,
  //   required: true,
  //   field: (row) => row.name,
  //   format: (val) => `${val}`,
  // },
  {
    name: "MAKER_NM",
    label: "제약회사",
    field: "MAKER_NM",
    align: "left",
    classes: "td-comp",
  },
  {
    name: "ITEM_NM_UNIT",
    label: "품목 및 규격",
    field: "ITEM_NM_UNIT",
    align: "left",
    classes: "td-prd",
  },
  {
    name: "BOHUM_CD",
    label: "보험코드",
    field: "BOHUM_CD",
    align: "center",
    classes: "td-code",
  },
  {
    name: "ITEM_QTY_REQ",
    label: "등록수량",
    field: "ITEM_QTY_REQ",
    align: "center",
    classes: "td-qty",
  },
  {
    name: "ITEM_QTY",
    label: "검수수량",
    field: "ITEM_QTY",
    align: "center",
    classes: "td-qty",
  },
  {
    name: "ITEM_QTY_NO",
    label: "불가수량",
    field: "ITEM_QTY_NO",
    align: "center",
    classes: "td-qty",
  },
  {
    name: "ORD_WP2_AMT",
    label: "단가",
    field: "ORD_WP2_AMT",
    align: "center",
    classes: "td-price",
  },
  {
    name: "PRICE",
    label: "금액",
    field: "PRICE",
    align: "center",
    classes: "td-price",
  },
  {
    name: "EXAM_RSLT_NM",
    label: "검수결과",
    field: "EXAM_RSLT_NM",
    align: "center",
    classes: "td-exam_rslt",
  },
]);
</script>

<style lang="scss" scoped>
// .SearchComp_wrap {
//   display: flex;
//   gap: 8px;
// }
// .input_search {
//   @include fs-5;
//   width: 120px;
//   height: 28px;
//   border: 1px solid #666666;
//   padding: 4px 6px;
//   border-radius: 5px;
// }
.salesTable_c_top {
  // height: 415px;
  height: 370px;
  overflow-y: auto;
}

.salesTable_c_bottom {
  height: 290px;
  overflow-y: auto;
}
.flex_between {
  @include flex_between;
}
.q-table_header_wrap {
  gap: 2px 16px;
  flex-wrap: wrap;
}
.q-table_input_wrap {
  display: flex;
  gap: 8px;
  // padding-right: 8px;
}
.q-table_boxLevel_wrap,
.q-table_calrendar_wrap,
.q-table_btn_export_wrap {
  @include flex_center;
  gap: 8px 2px;
}
.q-table_btn_option_wrap {
  margin-left: 20px;
}
.q-table_header_sub_wrap {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 8px;
  // margin: 8px 0;
  @media screen and (max-width: 830px) {
    gap: 8px 4px;
  }
}
.q-table_header_title {
  display: flex;
  flex-wrap: wrap;
  align-items: flex-start;
}
.q-table_header_title_sub {
  // margin-bottom: 8px;
  gap: 8px;
}
.q-table_header_title_sub p {
  @include fw-7;
  color: $r1;
}
.q-table_progress_wrap p {
  @include fs-5;
}
.q-table_progress_wrap p:nth-child(2) {
  @include fw-7;
  color: $r1;
}

// .btn_active {
//   @include gradation;
//   border-radius: 5px;
//   color: $w;
//   padding: 0 16px;
// }

.DatePickerComp_b_wrap {
  @include flex_start;
  gap: 4px;
  width: 126px;
  height: 32px;
  border: 1px solid $line-400;
  .q-input {
    width: 100%;
  }
  .q-field__native {
    width: 100%;
    border: none;
  }
}
.date_input_wrap,
.date_btn_wrap {
  display: inline-flex;
  align-items: center;
}
.date_input_wrap {
  width: calc(100% - 30px);
  padding: 0 4px;
  border-radius: 0;
}
.date_btn_wrap {
  position: relative;
  width: 30px;
  .q-btn {
    width: 100%;
    border-radius: 0;
  }
  &::before {
    content: "";
    position: absolute;
    top: 50%;
    left: -1px;
    transform: translateY(-50%);
    width: 1px;
    height: 12px;
    background-color: $line-400;
  }
}
// .input_search {
//   @include fs-5;
//   width: 140px;
//   font-size: 14px;
//   height: 32px;
//   border: 1px solid $line-400;
//   background-color: #fff;
//   padding: 4px 6px;
//   border-radius: 0px;
// }
.q-table_header_sub_wrap > .flex_start {
  flex-wrap: wrap;
  gap: 8px;
}
@media screen and (max-width: 905px) {
  .q-table_header_sub_wrap > .flex_start {
    .SearchComp_wrap {
      .input_search1,
      .input_search2 {
        max-width: 150px;
      }
    }
  }
}
</style>

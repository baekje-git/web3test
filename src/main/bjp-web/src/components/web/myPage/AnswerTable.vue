<template>
  <div class="">
    <q-table
      class="answerTable"
      :rows="rows"
      :columns="columns"
      row-key="KEY_SNO"
      table-header-class="table_header"
      dense
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
      :loading="loading"
    >
      <template v-slot:loading>
        <q-inner-loading showing color="primary" />
      </template>
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="flex_between">
            <div class="q-table_header_wrap">
              <!-- <div class="q-table_header_title">
                <p>문의/답변</p>
              </div> -->

              <div class="flex_center">
                <!-- <BtnComp_active btn-label="최근1개월" />
                <BtnComp_active btn-label="3개월" />
                <BtnComp_active btn-label="6개월" /> -->
                <div class="Btn_options_wrap">
                  <q-btn
                    :class="periodClassA"
                    label="최근 1개월"
                    dense
                    @click="onClickPeriod(1)"
                  />
                  <q-btn
                    :class="periodClassB"
                    label="3개월"
                    dense
                    @click="onClickPeriod(3)"
                  />
                  <q-btn
                    :class="periodClassC"
                    label="6개월"
                    dense
                    @click="onClickPeriod(6)"
                  />
                </div>
              </div>
            </div>
          </div>

          <div class="q-table_header_sub_wrap flex_between">
            <div class="flex_start">
              <q-input
                class="Input_default_wrap input_search_wrap"
                input-class="input_default"
                v-model="searchParam.qTitle"
                placeholder="제목"
                @focus="(input) => input.target.select()"
              />
              <div class="flex_center">
                <!-- <DatePickerComp_b /> -->
                <div class="flex_between">
                  <div class="DatePickerComp_b_wrap">
                    <div class="date_input_wrap">
                      <!-- <p>
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
                            @click="updateStartDt"
                            :default-date="today"
                            :title="selectedDate1"
                            :subtitle="selectedYear1"
                          >
                            <div
                              class="row items-center justify-end q-gutter-sm"
                            >
                              <q-btn
                                label="Cancel"
                                color="primary"
                                flat
                                v-close-popup
                              />
                              <q-btn
                                label="OK"
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
                  <p>~</p>
                  <div class="DatePickerComp_b_wrap">
                    <div class="date_input_wrap">
                      <!-- <p>
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
                            @click="updateEndDt"
                            :default-date="today"
                            :title="selectedDate2"
                            :subtitle="selectedYear2"
                          >
                            <div
                              class="row items-center justify-end q-gutter-sm"
                            >
                              <q-btn
                                label="Cancel"
                                color="primary"
                                flat
                                v-close-popup
                              />
                              <q-btn
                                label="OK"
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
                  <BtnComp_default btn-label="검색" @click="onClickSearch" />
                </div>
              </div>
            </div>
            <div class="flex_center">
              <DialogComp_a>
                <BtnComp_default v-if="!isSalesman" btn-label="문의하기" />
              </DialogComp_a>
              <BtnComp_default
                class="btn-fill gray"
                btnLabel="뒤로가기"
                @click="onClickMyPage"
              />
            </div>
          </div>
        </div>
      </template>

      <template v-slot:top-row v-if="rows.length === 0 && searched">
        <q-tr class="no-date">
          <q-td colspan="100%">
            <span>검색된 결과가 없습니다.</span>
          </q-td>
        </q-tr>
      </template>

      <template v-slot:body="props">
        <q-tr
          :props="props"
          @click="props.expand = !props.expand"
          :icon="props.expand ? 'remove' : 'add'"
        >
          <q-td key="ROWNUMBER" :props="props">
            <div>{{ props.row.ROWNUMBER }}</div>
          </q-td>
          <q-td key="Q_GB_NM" :props="props">
            <div>{{ props.row.Q_GB_NM }}</div>
          </q-td>
          <q-td key="Q_TITLE" :props="props">
            <div class="flex_between">
              <span class="flex_start" style="width: calc(100% - 20px)">
                <span
                  class="ellipsis"
                  style="display: inline-block; max-width: calc(100% - 40px)"
                  >{{ props.row.Q_TITLE }}</span
                >
                <span
                  v-if="isVisibleNew(props.row)"
                  class="q-table_newText"
                  style="color: #eb1818"
                >
                  NEW
                </span>
              </span>
              <img
                :src="
                  props.expand
                    ? 'assets/icons/sort-up-solid.svg'
                    : 'assets/icons/sort-down-solid.svg'
                "
                alt="icon"
              />
            </div>
          </q-td>
          <q-td key="Q_DT" :props="props">
            <div>
              {{
                props.row.Q_DT != ""
                  ? moment(props.row.Q_DT, "YYYY-MM-DD HH:mm:ss").format(
                      "YYYY-MM-DD"
                    )
                  : ""
              }}
            </div>
          </q-td>
          <q-td key="CUST_NM" :props="props">
            {{ props.row.CUST_NM }}
          </q-td>
          <q-td key="A_DT" :props="props">
            <div>
              {{
                props.row.A_DT != ""
                  ? moment(props.row.A_DT, "YYYY-MM-DD HH:mm:ss").format(
                      "YYYY-MM-DD"
                    )
                  : ""
              }}
            </div>
          </q-td>
          <q-td key="A_ST_GB_NM" :props="props">
            <div>{{ props.row.A_ST_GB_NM }}</div>
          </q-td>
        </q-tr>

        <q-tr v-show="props.expand" :props="props" class="q-table_QA_wrap">
          <!-- <q-td colspan="100%" class="q-table_QA_wrap"> -->
          <!-- <div class="flex_center"> -->
          <q-td colspan="2"></q-td>
          <q-td colspan="1">
            <!-- <div class="" style="width: 25%"></div> -->
            <div class="q-table_QA">
              <p>
                <span><img src="assets/icons/q-solid.svg" alt="q" /></span>
                <span style="white-space: pre-line">
                  {{ props.row.Q_TXT }}
                </span>
              </p>
              <p v-if="props.row.A_TXT !== ''">
                <span><img src="assets/icons/a-solid.svg" alt="a" /></span>
                <span style="white-space: pre-line">
                  {{ props.row.A_TXT }}
                </span>
              </p>
            </div>
          </q-td>
          <q-td colspan="3">
            <div class="flex_end" style="padding-right: 40px">
              <DialogComp_b
                :cust-cd="props.row.CUST_CD"
                :sno="props.row.SNO"
                :q-gb-cd="props.row.Q_GB_CD"
                :questioner="props.row.CUST_NM"
                :q-title="props.row.Q_TITLE"
                :q-txt="props.row.Q_TXT"
              >
                <BtnComp_default
                  v-if="isSalesman && props.row.A_ST_GB !== 'Y'"
                  btn-label="답변하기"
                />
              </DialogComp_b>
            </div>
            <!-- </div> -->
            <!-- </q-td> -->
          </q-td>
          <q-td></q-td>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, computed, onMounted, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { useRouter } from "vue-router";
import moment from "moment";

import InputComp_default from "src/components/web/common/InputComp_default.vue";
import BtnComp_options from "src/components/web/common/BtnComp_options.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import DatePickerComp_b from "src/components/web/common/DatePickerComp_b.vue";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
// import DialogComp_a from "src/components/web/common/DialogComp_a.vue";
import DialogComp_a from "src/components/web/myPage/MyQuestionComp.vue";
import DialogComp_b from "src/components/web/myPage/MyAnswerComp.vue";

const userInfo = util.getUserData();
const rows = ref([]);
const period = ref("");
const bus = inject("bus");
const router = useRouter();
const loading = ref(false);
const searched = ref(false);

const isSalesman = computed(
  () => userInfo.USER_GB_CD === "10" || userInfo.USER_GB_CD === "20"
);

const searchParam = ref({
  qTitle: "",
  startDt: "",
  endDt: "",
  custCd: userInfo.CUST_CD,
  loginDeptCd: userInfo.LOGIN_DEPT_CD,
  userGbCd: userInfo.USER_GB_CD, //30:약국개정
  loginBrchCd: userInfo.LOGIN_BRCH_CD,
});

const periodClassA = computed(() =>
  period.value === 1 ? "btn_options btn_active" : "btn_options"
);
const periodClassB = computed(() =>
  period.value === 3 ? "btn_options btn_active" : "btn_options"
);
const periodClassC = computed(() =>
  period.value === 6 ? "btn_options btn_active" : "btn_options"
);

const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  const startDt = searchParam.value.startDt;
  const endDt = searchParam.value.endDt;

  if (startDt === "" || endDt === "") {
    alert("기간을 입력해 주시기 바랍니다.");
    return;
  }

  searchParam.value.startDt = startDt.replace(match, "");
  searchParam.value.endDt = endDt.replace(match, "");

  loading.value = true;
  const res = await api.post("/myPage/qnaList", searchParam.value);
  loading.value = false;
  rows.value = res;
  searched.value = true;
}

// 조회버튼 클릭
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

// 조회기간 버튼 클릭
async function onClickPeriod(val) {
  period.value = val;

  await setDate(val);
  await fetchItemList();
}

bus.on("refreshQna", async () => {
  await fetchItemList();
});

async function onClickMyPage() {
  await router.push({ path: "/myPage" });
}

onMounted(async () => {
  period.value = 1;

  await setDate();
  await fetchItemList();
});

/*********** datepicker ***********/
const date1 = ref("");
const date2 = ref("");
const proxyDate1 = ref("");
const proxyDate2 = ref("");

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

function isVisibleNew(row) {
  //문의일 기준
  const insDtm = moment(row.Q_DT.replace(/[^0-9]/g, "")).format("YYYYMMDD");
  const today = moment().format("YYYYMMDD");

  //답변일 기준
  const insDtm2 = moment(row.A_DT.replace(/[^0-9]/g, "")).format("YYYYMMDD");
  const today2 = moment().format("YYYYMMDD");

  const day = today - insDtm;
  const day2 = today2 - insDtm2;

  if (day <= 7 || day2 <= 7) {
    return true;
  } else {
    return false;
  }
}

/**********************************/

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "ROWNUMBER",
    label: "번호",
    field: "ROWNUMBER",
    align: "center",
    classes: "td-num",
  },
  {
    name: "Q_GB_NM",
    label: "문의종류",
    field: "Q_GB_NM",
    align: "center",
    classes: "td-type",
  },
  {
    name: "Q_TITLE",
    label: "제목",
    field: "Q_TITLE",
    align: "center",
    classes: "td-tit",
  },
  {
    name: "Q_DT",
    label: "문의일",
    field: "Q_DT",
    align: "center",
    classes: "td-date",
  },
  {
    name: "CUST_NM",
    label: "작성자",
    field: "CUST_NM",
    align: "center",
    classes: "td-writer",
  },
  {
    name: "A_DT",
    label: "답변일",
    field: "A_DT",
    align: "center",
    classes: "td-date",
  },
  {
    name: "A_ST_GB_NM",
    label: "상태",
    field: "A_ST_GB_NM",
    align: "center",
    classes: "td-exam",
  },
]);
// rows.value = [
//   {
//     name: "1",
//     문의종류: "품목",
//     제목: "우루사 ...",
//     문의일: "2023.12.10",
//     답변일: "",
//     상태: "검토중",
//   },
//   {
//     name: "2",
//     문의종류: "배송",
//     제목: "언제 배송되나요?",
//     문의일: "2023.12.09",
//     답변일: "2023.12.10",
//     상태: "답변완료",
//   },
//   {
//     name: "3",
//     문의종류: "반품",
//     제목: "반품처리가 완료 되었나요?",
//     문의일: "2023.12.07",
//     답변일: "",
//     상태: "검토중",
//   },
//   {
//     name: "4",
//     문의종류: "기타",
//     제목: "",
//     문의일: "",
//     답변일: "",
//     상태: "",
//   },
// ];
</script>
<style lang="scss">
.answerTable {
  height: 756px;
  overflow-y: auto;
}
.flex_center {
  @include flex_center;
  gap: 4px;
}
.flex_start {
  @include flex_start;
  gap: 8px !important;
}
.flex_between {
  @include flex_between;
  &:has(.DatePickerComp_b_wrap) {
    gap: 4px;
  }
}

.input_search_wrap {
  width: 260px;
}

// .btn_options {
//   background-color: #999999;
//   border-radius: 5px !important;
//   padding: 0 5px;
// }
// .btn_options span {
//   @include fs-5;
//   color: #ffffff !important;
// }
// .btn_options:active,
// .btn_options:hover,
// .btn_options:focus {
//   @include gradation;
//   border-radius: 5px;
//   color: $w;
//   padding: 0 5px;
// }
.Btn_options_wrap .q-btn {
  min-width: 60px;
  width: auto !important;
}
.btn_active {
  @include gradation;
  border-radius: 5px;
  color: $w;
  padding: 0 5px;
}

.DatePickerComp_b_wrap {
  @include flex_center;
  width: 136px;
  gap: 4px;
}
</style>

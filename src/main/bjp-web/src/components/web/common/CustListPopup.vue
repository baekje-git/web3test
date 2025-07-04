<template>
  <div class="DialogComp_wrap">
    <!-- <div class="" @click="isOpenCustListPopup = true">
      <slot></slot>
    </div> -->

    <q-dialog v-model="isOpen">
      <q-card style="min-height: 650px; padding: 32px" class="clients">
        <!-- <q-card-section>
          <div class="q-card_c_title"><p>거래처 선택</p></div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>거래처 선택</span>
        </div>
        <div class="q-card_c_user_wrap">
          <q-table
            class="cust_list_Table_wrap"
            :rows="rows"
            :columns="columns"
            row-key="name"
            table-header-class="table_header"
            dense
            hide-bottom
            virtual-scroll
            :rows-per-page-options="[0]"
            :virtual-scroll-sticky-size-start="0"
            @row-click="onRowDblclick"
            style="height: 650px"
          >
            <template v-slot:top>
              <div class="q-table_wrap">
                <div class="q-table_header_wrap">
                  <div class="srch_form">
                    <div class="slct-wrap">
                      <q-select
                        class="select_default"
                        v-model="searchParam.custNmCdGb"
                        :options="custNmCdGb"
                        borderless
                        dense
                        emit-value
                        map-options
                      />
                    </div>
                    <div class="SearchComp_wrap">
                      <q-input
                        v-model="searchParam.keyword"
                        class="input_search"
                        style="width: 220px"
                        input-style="width: 100%"
                        placeholder="거래처명/코드를 입력하세요"
                        borderless
                        dense
                        autofocus
                        @keyup.enter="onClickSearch"
                      />
                    </div>
                    <BtnComp_default
                      class="btn_srch"
                      btn-label="검색"
                      @click="onClickSearch"
                    />
                  </div>
                </div>
              </div>
            </template>

            <template v-slot:body-cell-CUST_NM="props">
              <q-td :props="props" style="padding: 0; cursor: pointer">
                <div class="" style="font-size: 14px; width: 200px">
                  {{ props.value }}
                </div>
              </q-td>
            </template>
            <template v-slot:body-cell-CUST_CD="props">
              <q-td :props="props" style="padding: 0; cursor: pointer">
                <div class="" style="font-size: 14px">
                  {{ props.value }}
                </div>
              </q-td>
            </template>
            <template v-slot:body-cell-TEL_NO="props">
              <q-td :props="props" style="padding: 0; cursor: pointer">
                <div class="" style="font-size: 14px">
                  {{ props.value }}
                </div>
              </q-td>
            </template>
            <template v-slot:body-cell-HP_NO="props">
              <q-td :props="props" style="padding: 0; cursor: pointer">
                <div class="" style="font-size: 14px">
                  {{ props.value }}
                </div>
              </q-td>
            </template>
            <template v-slot:body-cell-CEO_NM="props">
              <q-td :props="props" style="padding: 0; cursor: pointer">
                <div class="" style="font-size: 14px; width: 70px">
                  {{ props.value }}
                </div>
              </q-td>
            </template>
            <template v-slot:body-cell-DLV_COURSE_NM="props">
              <q-td :props="props" style="padding: 0; cursor: pointer">
                <div class="" style="font-size: 14px; width: 150px">
                  {{ props.value }}
                </div>
              </q-td>
            </template>
          </q-table>
        </div>

        <q-card-actions class="q-card_btn_wrap">
          <BtnComp_active v-if="gb" btn-label="닫기" @click="onClickClose" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject, watch, computed, defineEmits } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { useRouter } from "vue-router";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";

const emit = defineEmits(["close"]);
const isOpenCustListPopup = ref(false);
const router = useRouter();
const userInfo = util.getUserData();
const bus = inject("bus");
const rows = ref([]);

const props = defineProps({
  isOpen: { type: Boolean },
  gb: { type: Boolean, default: false },
});

const isOpen = computed(() => {
  return props.isOpen;
});

const searchParam = ref({
  custNmCdGb: "1",
  keyword: "",
  custCd: "",
  userId: userInfo.USER_ID,
  loginId: userInfo.LOGIN_ID,
  loginBrchCd: userInfo.LOGIN_BRCH_CD,
  loginDeptCd: userInfo.LOGIN_DEPT_CD,
  userNm: userInfo.USER_NM,
  roleCd: userInfo.ROLE_CD,
  userGbCd: userInfo.USER_GB_CD,
  empNo: userInfo.EMP_NO,
  brchCd: userInfo.BRCH_CD,
  dlvBrchCd: userInfo.DLV_BRCH_CD,
  loginBrchCd: userInfo.LOGIN_BRCH_CD,
  brchNm: userInfo.BRCH_NM,
  brchSnm: userInfo.BRCH_SNM,
});

const custNmCdGb = ref([
  { value: "1", label: "거래처명" },
  { value: "2", label: "거래처코드" },
]);

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/main/getCustList", { params: searchParam.value });

  rows.value = res;
}

async function onClickSearch() {
  await fetchData();
}

async function onRowDblclick(evt, row, index) {
  searchParam.value.custCd = row.CUST_CD;

  const res = await api.get("/main/editCustInfo", {
    params: searchParam.value,
  });

  util.setUserData(res);
  isOpenCustListPopup.value = false;
  router.go(0);
}

async function onClickClose() {
  emit("close");
  rows.value = [];
}

bus.on("custList", async () => {
  isOpenCustListPopup.value = true;
  await fetchData();
});

// watch(isOpenCustListPopup, async () => {
//   if (isOpenCustListPopup.value) {
//     await fetchData();
//   }
// });

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  // { name: "BRCH_SNM", label: "지점", field: "BRCH_SNM", align: "center" },
  { name: "CUST_NM", label: "거래처명", field: "CUST_NM", align: "left" },
  {
    name: "CUST_CD",
    label: "거래처코드",
    field: "CUST_CD",
    align: "center",
  },
  { name: "TEL_NO", label: "전화번호", field: "TEL_NO", align: "center" },
  { name: "HP_NO", label: "휴대전화", field: "HP_NO", align: "center" },
  {
    name: "CEO_NM",
    label: "대표자명",
    field: "CEO_NM",
    align: "center",
  },
  {
    name: "DLV_COURSE_NM",
    label: "배송권역(배송코스)",
    field: "DLV_COURSE_NM",
    align: "center",
  },
]);
</script>

<style lang="scss" scoped>
.q-card.clients {
  .q-card_c_user_wrap {
    max-width: 100% !important;
  }
  @media screen and (min-width: 1270px) {
    width: 900px;
  }
  @media screen and (min-width: 780px) and (max-width: 1269px) {
    width: 90vw;
  }
}
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  margin-bottom: 20px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid $line-300;
}
.q-card_c_title p {
  font-size: rem(19px);
  @include fw-6;
  padding: 0 0 16px 0;
  border-bottom: solid 1px #eaeaea;
}
.q-card_c_user_wrap,
.q-card_c_section_wrap {
  // padding: 0 16px;
  // width: 640px;
  width: 100%;
}
.q-card_c_table_header_wrap {
  @include flex_between;
  @include fw-7;
  padding-bottom: 8px;
  // border-bottom: 2px solid $b3;
  border-bottom: 1px solid $gray-9;
}
.q-card_c_section_wrap {
  margin-bottom: 40px;
  tr:last-child,
  td {
    border-bottom: 1px solid $td-line;
  }
  tr td:nth-child(1),
  td:nth-child(3) {
    @include fw-7;
  }
  td {
    height: 40px !important;
  }
}
.q-card_c_input {
  @include fs-5;
  border: 1px solid $line-400;
  border-radius: 0;
  max-width: 140px;
}
.q-card_c_input .q-field__control {
  padding: 0 8px;
  height: 32px !important;
}
.q-card_btn_wrap {
  @include flex_end;
  margin-top: 32px;
  padding: 0;
}

.cust_list_Table_wrap {
  gap: 8px 0;
  max-height: 600px;
  // width: 850px;
  overflow-y: auto;

  @media screen and (max-width: 1270px) {
    .slct-wrap {
      width: 110px;
    }
  }
}
.cust_list_Table_wrap tr {
  height: 40px !important;
}

.cust_list_Table_wrap .srch_form {
  @include flex_start;
  width: 100%;
  // margin-bottom: 8px;
  gap: 8px;
}

.SearchComp_wrap .input_search {
  @include fs-5;
  height: 32px;
  border: 1px solid $line-400;
  padding: 4px 6px;
  border-radius: 0;
}
</style>

<template>
  <div class="">
    <q-table
      class="myAnswerTable_view my_view"
      :rows="rows"
      :columns="columns"
      row-key="SNO"
      table-header-class="table_header"
      dense
      hide-header
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
    >
      <template v-slot:top>
        <div class="q-table_wrap">
          <div class="flex_between q-table_noheader">
            <div class="q-table_header_wrap">
              <div class="q-table_header_title">
                <p>문의/답변</p>
              </div>
            </div>
            <div class="q-table_btn_wrap">
              <BtnComp_default_small
                class="btn-line default"
                btn_defaultLabel="+ more"
                :btn_defaultLink="btn_defaultLink"
                @click="onClickQna"
              />
            </div>
          </div>
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td
            :props="props"
            key="Q_TITLE"
            style="cursor: pointer"
            @click="props.expand = !props.expand"
            :icon="props.expand ? 'remove' : 'add'"
          >
            <div class="inner">
              <div class="item subject">
                <span class="inline-cut" style="max-width: calc(100% - 30px)">
                  {{ props.row.Q_TITLE }}
                </span>
                <span
                  v-if="isVisibleNew(props.row)"
                  class="q-table_newText"
                  style="color: #eb1818"
                >
                  NEW
                </span>
              </div>
              <div class="q-table_sort_icon item date">
                <span>{{
                  moment(props.row.Q_DT, "YYYY-MM-DD HH:mm:ss").format(
                    "YYYY-MM-DD"
                  )
                }}</span>
                <!-- <img src="/src/assets/icons/sort-down-solid.svg" alt="icon" /> -->
                <i class="ico arrw-dwn h8"></i>
              </div>
            </div>
          </q-td>
        </q-tr>

        <q-tr class="q-table_expend_wrap" v-show="props.expand" :props="props">
          <q-td>
            <div class="flex_between">
              <div class="q-table_view_text_wrap">
                <div class="">
                  <span class="q-tableQAText">Q</span>
                </div>
                <div class="">
                  <sapn
                    class="q-table_view_expendText"
                    style="white-space: pre-line"
                  >
                    {{ props.row.Q_TXT }}
                  </sapn>
                </div>
              </div>
            </div>
            <div class="flex_between" v-if="props.row.A_TXT != ''">
              <div class="q-table_view_text_wrap">
                <div class="">
                  <span class="q-tableQAText">A</span>
                </div>
                <div class="">
                  <sapn
                    class="q-table_view_expendText"
                    style="white-space: pre-line"
                  >
                    {{ props.row.A_TXT }}
                  </sapn>
                </div>
              </div>
              <div
                class="q-table_sort_icon"
                @click="props.expand = !props.expand"
                :icon="props.expand ? 'remove' : 'add'"
              >
                <!-- <img src="/src/assets/icons/sort-up-solid.svg" alt="icon" /> -->
                <i class="ico arrw-up h8"></i>
              </div>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, defineProps, onMounted, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { useRouter } from "vue-router";
import moment from "moment";

import BtnComp_default_small from "src/components/web/common/BtnComp_default_small.vue";

const userInfo = util.getUserData();
const router = useRouter();

const props = defineProps({
  btn_defaultLink: {
    type: String,
    default: "",
  },
});

const rows = ref([]);

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  loginDeptCd: userInfo.LOGIN_DEPT_CD,
  userGbCd: userInfo.USER_GB_CD, //30:약국개정
  loginBrchCd: userInfo.LOGIN_BRCH_CD,
});

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/myPage/qna", { params: searchParam.value });

  rows.value = res;
}

async function onClickQna() {
  await router.push({ path: "/qna" });
}

onMounted(async () => {
  await fetchData();
});

function isVisibleNew(row) {
  const insDtm = moment(row.Q_DT.replace(/[^0-9]/g, "")).format("YYYYMMDD");
  const today = moment().format("YYYYMMDD");

  const day = today - insDtm;

  if (day <= 7) {
    return true;
  } else {
    return false;
  }
}

//-------------------------- Grid Setting ----------------------------//

const columns = ref([
  {
    name: "Q_TITLE",
    label: "제목",
    field: "Q_TITLE",
    align: "left",
  },
]);
</script>

<style lang="scss">
.flex_between {
  @include flex_between;
}
.myAnswerTable_view {
  height: 159px;
  overflow-y: auto;
  margin-bottom: 0;
}

.myAnswerTable_view tr {
  height: 36px !important;
}
</style>

<template>
  <div>
    <ExpandLayout_mobile
      v-model:expanded_model="expanded_model"
      expand_title="문의 답변"
      expand_btn="+ more"
      :expand_btnWidth="68"
      @expanded_btnClick="expanded_btn"
    >
      <DialogLayout_mobile
        dialog_title="문의/답변"
        v-model:dialog_model_prop="dialog_MyPage"
      >
        <div class="">
          <MyQAComp_mobile v-model="isActiveBtn" />
        </div>
      </DialogLayout_mobile>

      <q-table
        class=""
        row-key="Q_TITLE"
        :rows="rows"
        flat
        hide-header
        hide-bottom
        virtual-scroll
        :rows-per-page-options="[0]"
        :virtual-scroll-sticky-size-start="0"
      >
        <template v-slot:body="props">
          <q-tr @click="props.expand = !props.expand">
            <div class="table_wrap">
              <div class="table_item table_item_mobile">
                <q-td class="table-td_mobile">
                  <span>{{ props.row.Q_TITLE }}</span>
                  <span
                    v-if="isVisibleNew(props.row)"
                    class="q-table_newText red-300"
                  >
                    NEW
                  </span>
                </q-td>
              </div>
              <div class="table_item table_item_mobile">
                <q-td class="table-td_mobile">
                  <span>{{
                    moment(props.row.Q_DT, "YYYY-MM-DD HH:mm:ss").format(
                      "YYYY-MM-DD"
                    )
                  }}</span>
                  <span
                    ><img
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
            </div>
          </q-tr>

          <q-tr v-show="props.expand" :props="props">
            <div class="expand_table_wrap">
              <div class="expand_table_item">
                <q-td class="table-td_mobile">
                  <img src="assets/icons/q-solid.svg" alt="icon" />
                </q-td>
                <q-td class="table-td_mobile">
                  <span style="white-space: pre-line">{{
                    props.row.Q_TXT
                  }}</span>
                </q-td>
              </div>
              <div class="expand_table_item">
                <q-td v-if="props.row.A_TXT != ''" class="table-td_mobile">
                  <img src="assets/icons/a-solid.svg" alt="icon" />
                </q-td>
                <q-td class="table-td_mobile">
                  <span style="white-space: pre-line">{{
                    props.row.A_TXT
                  }}</span>
                  <p v-if="props.row.A_TXT != ''">
                    답변
                    {{
                      moment(props.row.A_DT, "YYYY-MM-DD HH:mm:ss").format(
                        "YYYY-MM-DD"
                      )
                    }}
                  </p>
                </q-td>
              </div>
            </div>
          </q-tr>
        </template>
      </q-table>
    </ExpandLayout_mobile>
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//
import moment from "moment";
import { ref, onMounted } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import ExpandLayout_mobile from "src/layouts/mobile/ExpandLayouts/ExpandLayout_mobile.vue";
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import MyQAComp_mobile from "src/components/mobile/DialogComp/4.myDialogComp/MyQAComp_mobile.vue";

const userInfo = util.getUserData();
const expanded_model = ref(true);

const dialog_MyPage = ref(false);
const expanded_btn = () => {
  dialog_MyPage.value = true;
};

const rows = ref([]);

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  loginDeptCd: userInfo.LOGIN_DEPT_CD,
  userGbCd: userInfo.USER_GB_CD, //30:약국개정
  loginBrchCd: userInfo.LOGIN_BRCH_CD,
});

//필터표시
const isActiveBtn = ref(0);

onMounted(async () => {
  await fetchData();
});

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/myPage/qna", { params: searchParam.value });

  let newList = res.map((i) => {
    return {
      ...i,
      Q_DT: i.Q_DT.replace(/\./g, "/"),
      A_DT: i.A_DT.replace(/\./g, "/"),
    };
  });

  rows.value = newList;
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
</script>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap");

.q-table tr {
  display: block;
  height: auto !important;
  border-bottom: none !important;
  // overflow: hidden;
}
.table_wrap {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  padding: 8px 0 !important;
  td {
    span {
      @include fs-5;
    }
  }
  .table_item:nth-child(1) {
    grid-column: span 5;
    max-width: 230px;
    td {
      @include flex_start;
      gap: 3px;
      span:nth-child(1) {
        display: block;
        overflow: hidden;
        text-overflow: ellipsis;
      }
      span:nth-child(2) {
        @include fw-7;
      }
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 1;
    td {
      @include flex_end;
      gap: 8px;
      span {
        // font-family: "Malgun Gothic";
      }
    }
  }
}

// .expand_table_wrap {
//   @include border-b;
//   display: grid;
//   grid-template-columns: repeat(1, 1fr);
//   padding: 8px 0 !important;
//   background-color: #eeeeee;

//   .expand_table_item:nth-child(1) {
//     grid-column: span 1;
//     td {
//       @include fw-7;
//       @include fs-5;
//       white-space: normal;
//     }
//   }
//   .expand_table_item:nth-child(2) {
//     grid-column: span 1;
//     margin-top: 4px;
//     td {
//       @include flex_start;
//       gap: 8px;
//       span {
//         @include fs-5;
//       }
//     }
//   }
// }

.expand_table_wrap {
  // @include border-b;
  padding: 8px !important;
  border-bottom: 1px solid $line-400;
  // background-color: #eeeeee;
  // .expand_table_item:nth-child(1) {
  //   td {
  //     span {
  //       color: slategray;
  //     }
  //   }
  // }
  .expand_table_item:nth-child(1),
  .expand_table_item:nth-child(2) {
    margin-bottom: 12px;
    td {
      span {
        @include fw-7;
        @include fs-5;
        white-space: normal;
      }
      p {
        @include flex_end;
      }
    }
    td:first-child {
      vertical-align: top;
      img {
        margin-right: 8px !important;
      }
    }
    td:nth-child(2) {
      white-space: normal;
    }
  }
  .expand_table_item:nth-child(3) {
    grid-column: span 1;
    padding: 0 22px !important;
    margin-top: 4px;
    td {
      @include flex_start;
      white-space: normal;
      gap: 8px;
      span {
        @include fs-5;
      }
    }
  }
}
</style>

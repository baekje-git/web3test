<template>
  <div>
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
      <template v-slot:top>
        <div class="border-top"></div>
      </template>
      <template v-slot:body="props">
        <q-tr @click="props.expand = !props.expand">
          <div class="table_wrap">
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span v-if="props.row.CUST_NM !== ''">{{
                  props.row.CUST_NM
                }}</span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span>문의</span>
                <span>{{
                  moment(props.row.Q_DT, "YYYY-MM-DD HH:mm:ss").format(
                    "YYYY-MM-DD"
                  )
                }}</span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span>{{ props.row.Q_GB_NM }}</span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                <span>{{ props.row.A_ST_GB_NM }}</span>
              </q-td>
            </div>
            <div class="table_item table_item_mobile">
              <q-td class="table-td_mobile">
                {{ props.row.Q_TITLE }}
                <span
                  v-if="props.row.NEW == 'Y'"
                  class="q-table_newText"
                  style="color: #eb1818"
                >
                  NEW
                </span>
              </q-td>
            </div>
          </div>
        </q-tr>

        <q-tr v-show="props.expand" :props="props">
          <div class="expand_table_wrap" v-if="props.row.A_TXT === ''">
            <div class="expand_table_item">
              <q-td class="table_result table-td_mobile">
                <span>
                  <img src="assets/icons/q-solid.svg" alt="icon" />
                </span>
                <span style="color: slategrey; white-space: pre-line">
                  {{ props.row.Q_TXT }}
                </span>
              </q-td>
              <q-td class="table_btn">
                <BtnComp_mobile
                  buttonText="답변하기"
                  btnStyle="default"
                  @click="props.row.dialog_answer = true"
                  style="width: 64px"
                />
                <DialogLayout_mobile
                  dialog_title="문의/답변 > 답변하기"
                  v-model:dialog_model_prop="props.row.dialog_answer"
                  :dialog_full="true"
                >
                  <div class="">
                    <div class="expand_sub_table_wrap">
                      <div class="expand_sub_table_itme">
                        <q-td>문의종류</q-td>
                      </div>
                      <div class="expand_sub_table_itme">
                        <q-td>{{ props.row.Q_GB_NM }}</q-td>
                      </div>
                      <div class="expand_sub_table_itme">
                        <q-td>문의자</q-td>
                      </div>
                      <div class="expand_sub_table_itme">
                        <q-td>{{ props.row.CUST_NM }}</q-td>
                      </div>
                      <div class="expand_sub_table_itme">
                        <q-td>제목</q-td>
                      </div>
                      <div class="expand_sub_table_itme">
                        <q-td>
                          <span>{{ props.row.Q_TITLE }}</span>
                        </q-td>
                      </div>
                      <div class="expand_sub_table_itme">
                        <q-td>내용</q-td>
                      </div>
                      <div class="expand_sub_table_itme">
                        <q-td>{{ props.row.Q_TXT }}</q-td>
                      </div>
                      <div class="expand_sub_table_itme">
                        <q-td>답변내용</q-td>
                      </div>
                    </div>
                    <TextAreaComp_mobile
                      submit="답변하기"
                      :isVisible="true"
                      style="margin-top: 16px"
                      @update-memo="(val) => onClickRegAnswer(props.row, val)"
                    />
                  </div>
                </DialogLayout_mobile>
              </q-td>
            </div>
          </div>
          <div class="expand_table_wrap" v-else>
            <div class="expand_table_item">
              <q-td class="table_result table-td_mobile">
                <span>
                  <img src="assets/icons/q-solid.svg" alt="icon" />
                </span>
                <span style="color: slategrey; white-space: pre-line">
                  {{ props.row.Q_TXT }}
                </span>
              </q-td>
              <q-td class="table_result table-td_mobile">
                <span>
                  <img src="assets/icons/a-solid.svg" alt="icon" />
                </span>
                <span style="white-space: pre-line">
                  {{ props.row.A_TXT }}
                </span>
              </q-td>
            </div>
            <div class="expand_table_item">
              <q-td class="table-td_mobile">
                <span>답변</span>
                <span>{{
                  moment(props.row.A_DT, "YYYY-MM-DD HH:mm:ss").format(
                    "YYYY-MM-DD"
                  )
                }}</span>
              </q-td>
            </div>
          </div>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, watch } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";

import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import TextAreaComp_mobile from "src/components/mobile/DialogComp/__commonDialogComp/TextAreaComp_mobile.vue";

const userInfo = util.getUserData();
const emit = defineEmits(["refreshQAList"]);

const rows = ref([]);

const props = defineProps({
  QAdata: Array,
});

const dataParams = ref({
  custCd: "",
  sno: "",
  aTxt: "",
  userId: userInfo.USER_ID,
  loginId: userInfo.LOGIN_ID,
});

//---------------------------- Function ------------------------------//

async function onClickRegAnswer(row, val) {
  if (val === "") {
    alert("답변 내용을 입력해 주세요.");
    return;
  }

  dataParams.value.aTxt = val;
  dataParams.value.custCd = row.CUST_CD;
  dataParams.value.sno = row.SNO;

  if (!confirm("문의 내용에 대한 답변을 등록 하시겠습니까?")) return;

  try {
    await api.put("/myPage/regAnswer", dataParams.value);
    emit("refreshQAList");
  } catch (error) {
    alert(error.response.data);
  }
}

watch(props, () => {
  let newList = props.QAdata.map((i) => {
    return {
      ...i,
      A_DT: i.A_DT.replace(/\./g, "/"),
      Q_DT: i.Q_DT.replace(/\./g, "/"),
    };
  });
  rows.value = newList;
});
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  padding: 4px 0 !important;
  .table_item {
    padding: 2px 0 !important;
    td {
      @include fs-5;
      span {
        @include fw-7;
        @include fs-5;
      }
    }
  }
  .table_item:nth-child(1) {
    grid-column: span 3;
    td {
      @include flex_start;
      gap: 6px;
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 1;
    td {
      @include flex_start;
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 1;
    td {
      @include flex_end;
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 3;
    height: auto;
    td {
      white-space: normal;
      // span:nth-child(1) {
      //   margin: 0 4px;
      //   img {
      //     width: 14px;
      //   }
      // }
    }
  }
}

.expand_table_wrap {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  padding: 4px 0 !important;
  // background-color: #eeeeee;
  border-bottom: 1px solid $td-line;
  .expand_table_item {
    padding: 2px 0 !important;
    td {
      @include fs-5;
      span {
        @include fs-5;
      }
    }
  }
  .expand_table_item:nth-child(1) {
    grid-column: span 1;

    .table_result {
      @include flex_start;
      @include fw-7;
      height: auto !important;
      white-space: normal;
      margin-bottom: 5px;
      span:nth-child(1) {
        margin: 0 4px;
        img {
          width: 12px;
        }
      }
    }
    .table_btn {
      @include flex_center;
      padding: 28px 0 !important;
    }
  }
  .expand_table_item:nth-child(2) {
    grid-column: span 1;
    td {
      @include flex_end;
      gap: 8px;
    }
  }
}

.expand_sub_table_wrap {
  display: grid;
  grid-template-columns: 25% 75%;
  .expand_sub_table_itme {
    margin-bottom: 16px;
    td {
      @include fs-5;
    }
  }
  .expand_sub_table_itme:nth-child(odd) {
    td {
      @include fw-7;
    }
  }
  .expand_sub_table_itme:last-child {
    margin-top: 24px;
    margin-bottom: 0;
  }
}
</style>

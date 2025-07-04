<template>
  <div class="DialogComp_wrap">
    <div class="" @click="isOpenAnswerPopup = true">
      <slot></slot>
    </div>

    <q-dialog v-model="isOpenAnswerPopup">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="q-card_b_title"><p>답변하기</p></div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>답변하기</span>
        </div>

        <div class="q-card_b_section_wrap">
          <q-markup-table>
            <tbody>
              <tr>
                <td style="width: 120px">문의종류</td>
                <td>
                  <!-- <RadioComp_a
                    v-model="dialogRadio"
                    :radio_options="qGbOptions"
                  /> -->
                  <q-option-group
                    :modelValue="props.qGbCd"
                    :options="qGbOptions"
                    color="blue-10"
                    size="xs"
                    inline
                    dense
                    disable
                  />
                </td>
              </tr>
              <tr>
                <td>문의자</td>
                <td>{{ props.questioner }}</td>
              </tr>
              <tr>
                <td>제목</td>
                <td>
                  <!-- <q-input
                    class="q-card_b_input"
                    v-model="textarea"
                    borderless
                  /> -->
                  <p sty>{{ props.qTitle }}</p>
                </td>
              </tr>
              <tr>
                <td>내용</td>
                <td>
                  <!-- <q-input
                    class="q-card_b_input_textarea"
                    v-model="textarea"
                    type="textarea"
                    borderless
                  /> -->
                  <p style="white-space: pre-line">{{ props.qTxt }}</p>
                </td>
              </tr>
            </tbody>
          </q-markup-table>
        </div>

        <div class="q-card_b_section_wrap" style="margin-top: 10px">
          <q-markup-table>
            <tbody>
              <tr>
                <td style="width: 120px">내용</td>
                <td>
                  <q-input
                    v-model="dataParams.aTxt"
                    class="q-card_b_input_textarea"
                    type="textarea"
                    borderless
                  />
                </td>
              </tr>
            </tbody>
          </q-markup-table>
        </div>

        <q-card-actions class="q-card_btn_wrap">
          <BtnComp_default
            btn-label="취소"
            class="btn-fill gray btn-pop"
            v-close-popup
          />
          <BtnComp_default
            btn-label="등록"
            class="btn-pop"
            @click="onClickRegAnswer"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";

const isOpenAnswerPopup = ref(false);
const userInfo = util.getUserData();
const bus = inject("bus");

const qGbOptions = [
  {
    label: "주문",
    value: "01",
  },
  {
    label: "반품",
    value: "02",
  },
  {
    label: "배송",
    value: "03",
  },
  {
    label: "품목",
    value: "04",
  },
  {
    label: "약가인하",
    value: "05",
  },
  {
    label: "이용문의",
    value: "06",
  },
  {
    label: "기타",
    value: "99",
  },
];

const props = defineProps({
  qGbCd: { type: String },
  questioner: { type: String },
  qTitle: { type: String },
  qTxt: { type: String },
  custCd: { type: String },
  sno: { type: Number },
});

const dataParams = ref({
  custCd: props.custCd,
  sno: props.sno,
  aTxt: "",
  userId: userInfo.USER_ID,
  loginId: userInfo.LOGIN_ID,
});

//---------------------------- Function ------------------------------//

async function onClickRegAnswer() {
  if (dataParams.value.aTxt.length == 0 || dataParams.value.aTxt == "") {
    alert("내용을 입력해주세요.");
    return;
  }

  if (!confirm("문의 내용에 대한 답변을 등록 하시겠습니까?")) return;

  await api.put("/myPage/regAnswer", dataParams.value);

  // util.showNotify("답변이 등록되었습니다.");
  bus.emit("refreshQna");
  isOpenAnswerPopup.value = false;
}
</script>

<style lang="scss">
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  margin-bottom: 10px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid $line-300;
}
.flex_start {
  @include flex_start;
  gap: 16px;
}

.q-card_b_title p {
  @include fw-7;
  padding: 0 0 16px 0;
  border-bottom: solid 1px #999 !important;
}
.q-card_b_section_wrap {
  // padding: 0 16px;
  margin: 16px 0;
  padding: 0 !important;
  width: 800px;
  border-top: 1px solid $gray-9;
  td {
    border-bottom: 1px solid $td-line !important;
  }
  td + td {
    border-left: 1px solid $td-line !important;
  }
}
.q-card_b_section_subText {
  @include fs-5;
  @include fw-7;
  color: $b3;
  padding-bottom: 16px;
  text-align: center;
}
.q-card_b_section_wrap tr:nth-child(1) td:nth-child(1) {
  @include fw-7;
  // background-color: rgba(31, 63, 130, 0.1) !important;
}
.q-card_b_section_wrap tr:nth-child(1) td:nth-child(2) {
  // background-color: rgba(31, 63, 130, 0.1) !important;
}
.q-card_b_section_wrap tr td {
  border: none;
  padding: 0 16px;
}
.q-card_b_section_wrap tr td:nth-child(1) {
  text-align: right;
  background-color: #f8f8f8 !important;
}
// .q-card_btn_wrap {
//   @include flex_end;
//   gap: 10px;
//   .Btn_active_wrap,
//   .Btn_wrap {
//     width: calc(50% - 5px);
//   }
//   .btn_active,
//   .btn {
//     height: 40px;
//   }
// }

.q-card_b_input {
  height: 32px;
  border: 1px solid $line-400 !important;
}

.q-card_b_input .q-field__control {
  padding: 0 8px;
  height: 32px !important;
}

.q-card_b_input_textarea {
  width: 100%;
  margin: 16px 0;
  border: 1px solid $line-400 !important;
}
.q-card_b_input_textarea textarea {
  height: 80px !important;
  padding: 8px !important;
}

.q-card_etcTextTitle {
  @include fs-5;
  @include fw-7;
}
.q-card_etcText {
  @include fs-5;
}
.card_btn_wrap {
  gap: 10px;
}
.btn-pop {
  width: calc(50% - 5px);
}
.btn-pop .btn_active,
.btn-pop .q-btn {
  width: 100%;
  height: 40px;
}
</style>

<template>
  <div class="DialogComp_wrap">
    <div class="" @click="isOpenQuestionPopup = true">
      <slot></slot>
    </div>

    <q-dialog v-model="isOpenQuestionPopup">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="q-card_b_title"><p>문의하기</p></div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>문의하기</span>
        </div>

        <div class="q-card_b_section_wrap">
          <p class="q-card_b_section_subText">
            문의를 남겨주시면 담당자가 직접 답변을 드립니다.
          </p>
          <q-markup-table>
            <tbody>
              <tr>
                <td style="width: 120px">문의종류</td>
                <td>
                  <RadioComp_a
                    v-model="dataParams.qGbCd"
                    :radio_options="qGbOptions"
                  />
                </td>
              </tr>
              <tr>
                <td>문의자</td>
                <td>{{ questioner }}</td>
              </tr>
              <tr>
                <td>제목</td>
                <td>
                  <q-input
                    class="q-card_b_input"
                    v-model="dataParams.qTitle"
                    borderless
                    placeholder="제목을 30자 이내로 작성해주세요."
                  />
                </td>
              </tr>
              <tr>
                <td>내용</td>
                <td>
                  <q-input
                    class="q-card_b_input_textarea"
                    v-model="dataParams.qTxt"
                    type="textarea"
                    borderless
                  />
                  <p class="q-card_etcTextTitle">※ 문의 시 주의사항</p>
                  <p class="q-card_etcText">
                    - 주민등록번호, 연락처 등의 정보는 타인에게 노출될 경우
                    개인정보 도용의 위험이 있으니 주의해 주시기 바랍니다.
                  </p>
                  <p class="q-card_etcText">
                    - 비방, 광고, 불건전한 내용의 글은 관리자에 의해 사전 동의
                    없이 삭제 될 수 있습니다.
                  </p>
                </td>
              </tr>
            </tbody>
          </q-markup-table>
        </div>

        <q-card-actions class="q-card_btn_wrap">
          <BtnComp_default btn-label="등록" @click="onClickRegQuestion" />
          <BtnComp_active btn-label="취소" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject, watch } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import RadioComp_a from "src/components/web/common/RadioComp_a.vue";

const isOpenQuestionPopup = ref(false);
const userInfo = util.getUserData();
const questioner = userInfo.CUST_NM;
const bus = inject("bus");

// const props = defineProps({
//   custCd: { type: String },
//   userId: { type: String },
// });

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

const dataParams = ref({
  custCd: userInfo.CUST_CD,
  userId: userInfo.USER_ID,
  loginId: userInfo.LOGIN_ID,
  qGbCd: "01",
  qTitle: "",
  qTxt: "",
});

//---------------------------- Function ------------------------------//

async function onClickRegQuestion() {
  if (dataParams.value.qTitle.length == 0 || dataParams.value.qTitle == "") {
    alert("제목을 입력해주세요.");
    return;
  } else if (dataParams.value.qTxt.length == 0 || dataParams.value.qTxt == "") {
    alert("내용을 입력해주세요.");
    return;
  }

  if (dataParams.value.qTitle.length > 30) {
    alert("제목이 30자가 넘었습니다.\n수정하여 등록해주세요.");
    return;
  }
  if (!confirm("문의 내용을 등록 하시겠습니까?")) return;

  await api.post("/myPage/regQuestion", dataParams.value);

  util.showNotify("문의가 등록되었습니다.");
  bus.emit("refreshQna");

  isOpenQuestionPopup.value = false;
}

watch(isOpenQuestionPopup, () => {
  dataParams.value.qGbCd = "01";
  dataParams.value.qTitle = "";
  dataParams.value.qTxt = "";
});
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
.q-card_btn_wrap {
  @include flex_end;
  gap: 8px;
}

.q-card_b_input {
  height: 32px;
  border: 1px solid $line-400 !important;
}
.q-card_b_input {
  height: 32px;
  border: 1px solid $line-400 !important;
}

.q-placeholder {
  padding: 0 0%;
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
</style>

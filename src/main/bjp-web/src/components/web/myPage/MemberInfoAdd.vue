<template>
  <div class="DialogComp_wrap">
    <div class="" @click="isOpenMemberAddPopup = true">
      <slot></slot>
    </div>

    <q-dialog v-model="isOpenMemberAddPopup">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="q-card_c_title"><p>사용자 정보 추가</p></div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>사용자 정보 추가</span>
        </div>

        <div class="q-card_c_section_wrap">
          <q-markup-table>
            <tbody>
              <tr>
                <td style="width: 15%">아이디</td>
                <td class="flex_between">
                  <q-input
                    v-model="dataParams.loginId"
                    class="q-card_c_input"
                    borderless
                  />
                  <q-btn
                    class="btn"
                    label="중복확인"
                    dense
                    @click="onClickChkIdDup"
                  />
                </td>
              </tr>
              <tr>
                <td style="width: 15%">신규 비밀번호</td>
                <td style="width: 35%">
                  <q-input
                    v-model="dataParams.newPwd"
                    class="q-card_c_input"
                    borderless
                    type="password"
                  />
                  <span color="red">
                    (영문(특수문자 ~!@#$%^&* 가능) + 숫자 6~12자리로 구성)
                  </span>
                </td>
              </tr>
              <tr>
                <td style="width: 15%">비밀번호 확인</td>
                <td style="width: 35%">
                  <q-input
                    v-model="dataParams.newPwdChk"
                    class="q-card_c_input"
                    borderless
                    type="password"
                  />
                </td>
              </tr>
              <tr>
                <td style="width: 15%">사용자명</td>
                <td style="width: 35%">
                  <q-input
                    v-model="dataParams.userNm"
                    class="q-card_c_input"
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
            class="btn-line default btn-pop"
            v-close-popup
          />
          <BtnComp_default
            btn-label="저장"
            class="btn-pop"
            @click="onClickAddMemberInfo"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, inject } from "vue";
import * as util from "src/support/util";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import { api } from "src/boot/axios";

const isOpenMemberAddPopup = ref(false);
const userInfo = util.getUserData();
const bus = inject("bus");

const newUserAbleYn = ref(false);

const dataParams = ref({
  loginId: "",
  userNm: "",
  newPwd: "",
  newPwdChk: "",
  brchCd: userInfo.BRCH_CD,
  custCd: userInfo.CUST_CD,
  insId: userInfo.LOGIN_ID,
});

//---------------------------- Function ------------------------------//

async function initDataParams() {
  dataParams.value.loginId = "";
  dataParams.value.userNm = "";
  dataParams.value.newPwd = "";
  dataParams.value.newPwdChk = "";
}

async function onClickChkIdDup() {
  const loginId = dataParams.value.loginId;

  if (loginId === "") {
    alert("아이디를 입력해 주세요.");
    return;
  }

  const res = await api.get("/myPage/chkIdDup", { params: dataParams.value });

  if (res.dupCnt > 0) {
    alert("등록된ID 가 이미 있습니다.");
    newUserAbleYn.value = false;
    return;
  } else {
    alert("등록 가능한 ID 입니다.");
    newUserAbleYn.value = true;
  }
}

function pwdNmChk() {
  const newPwd = dataParams.value.newPwd;
  const newPwdChk = dataParams.value.newPwdChk;
  const userNm = dataParams.value.userNm;

  const pattern1 = /[0-9]/;
  const pattern2 = /[a-zA-Z~!@#$%^&*]/;

  if (newPwd === "") {
    alert("비밀번호를 입력해 주세요.");
    return false;
  }

  if (newPwd !== newPwdChk) {
    alert("비밀번호가 일치하지 않습니다.");
    return false;
  }

  if (
    !pattern1.test(newPwd) ||
    !pattern2.test(newPwd) ||
    newPwd.length < 6 ||
    newPwd.length > 12
  ) {
    alert(
      "영문(특수문자 ~!@#$%^&* 가능) + 숫자 6자리 이상 12자리 이하로 구성하여야 합니다."
    );
    return false;
  }

  if (userNm === "") {
    alert("사용자명을 입력해 주세요.");
    return false;
  }

  return true;
}

async function onClickAddMemberInfo() {
  if (!newUserAbleYn.value) {
    alert("ID 중복확인을 먼저 확인해 주세요.");
    return;
  }

  if (!pwdNmChk()) return;

  await api.post("/myPage/addMemberInfo", dataParams.value);
  await initDataParams();

  util.showNotify("추가 완료 되었습니다.");
  bus.emit("memberInfo");

  isOpenMemberAddPopup.value = false;
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
.q-card_c_title p {
  @include fw-7;
  padding: 0 0 16px 0;
  border-bottom: solid 1px #999 !important;
}
.q-card_c_user_wrap,
.q-card_c_section_wrap {
  padding: 0 16px;
  width: 640px;
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
  @include flex_center;
}

.q-card_c_Table_wrap {
  max-height: 120px;
  overflow-y: auto;
}
.q-card_c_Table_wrap tr {
  height: 40px !important;
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

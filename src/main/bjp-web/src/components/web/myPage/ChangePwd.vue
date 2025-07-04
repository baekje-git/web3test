<template>
  <div class="DialogComp_wrap">
    <div class="" @click="isOpenEditPopup = true">
      <slot></slot>
    </div>

    <q-dialog v-model="isOpenEditPopup">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="q-card_c_title"><p>사용자 정보 수정</p></div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>사용자 정보 수정</span>
        </div>
        <div class="q-card_c_section_wrap">
          <!-- <div class="q-card_c_table_header_wrap">
            <p>기본정보</p>
            <BtnComp_default
              btn_defaultLabel="거래약정서/개인정보이용동의서 확인"
            />
          </div> -->
          <q-markup-table>
            <tbody>
              <tr>
                <td style="width: 15%">기존 비밀번호</td>
                <td style="width: 35%">
                  <q-input
                    v-model="dataParams.oldPwd"
                    class="q-card_c_input"
                    borderless
                    type="password"
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
            </tbody>
          </q-markup-table>
        </div>

        <q-card-actions class="q-card_btn_wrap" style="gap: 8px">
          <BtnComp_default btn-label="저장" @click="onClickEditPwd" />
          <BtnComp_default
            class="btn-fill gray"
            btn-label="취소"
            v-close-popup
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

const isOpenEditPopup = ref(false);
const userInfo = util.getUserData();
const bus = inject("bus");

const props = defineProps({
  loginId: { type: String },
  userId: { type: String },
  userNm: { type: String },
});

const dataParams = ref({
  loginId: props.loginId,
  userId: props.userId,
  userNm: props.userNm,
  modId: userInfo.USER_ID,
  oldPwd: "",
  newPwd: "",
  newPwdChk: "",
});

//---------------------------- Function ------------------------------//

async function editPwd() {
  await api.put("/myPage/editPwd", dataParams.value);
}

function pwdChk() {
  const oldPwd = dataParams.value.oldPwd;
  const newPwd = dataParams.value.newPwd;
  const newPwdChk = dataParams.value.newPwdChk;

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
  if (newPwd === oldPwd) {
    alert("변경하려는 비밀번호가 동일합니다.");
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

  return true;
}

async function onClickEditPwd() {
  if (pwdChk()) {
    const res = await api.get("/myPage/pwdChk", { params: dataParams.value });

    if (res) {
      if (res.PASSWORD == 0) {
        alert("현재 비밀번호가 일치하지않습니다.");
        return;
      } else if (
        res.PASSWORD3 == 1 ||
        res.PASSWORD2 == 1 ||
        res.PASSWORD1 == 1
      ) {
        alert("최근에 사용한 비밀번호와 같습니다.다른비밀번호를 입력하세요.");
        return;
      } else if (res.PASSWORD == 1) {
        // 패스워드 변경
        if (!confirm("변경 하시겠습니까?")) return;

        await editPwd();
        util.showNotify("변경 되었습니다.");
        bus.emit("memberInfo");

        isOpenEditPopup.value = false;
      }
    }
  }
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
.q-card_c_input .q-field__control {
  overflow: hidden;
}
</style>

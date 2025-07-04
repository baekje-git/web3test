<template>
  <div class="DialogComp_wrap">
    <!-- <div class="" @click="isOpenPwdEdit = true">
      <slot></slot>
    </div> -->

    <q-dialog v-model="isOpenPwdEdit">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="passupdate_title">
            <p>비밀번호 초기화</p>
            <img src="/src/assets/icons/x-solid.svg" @click="onClickClose" />
          </div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>비밀번호 초기화</span>
        </div>

        <div class="passupdate_section_wrap">
          <div class="passupdate_table_header_wrap">
            <img src="assets/icons/exclamation-solid.svg" />
            <p>
              <span
                >보안성 강화로 인하여 초기비밀번호를 사용하실 수 없습니다.</span
              >
            </p>
            <p>
              <span>새로운 비밀번호로 변경하여 주시기 바랍니다.</span>
            </p>
          </div>
          <q-markup-table style="height: 125px">
            <tbody>
              <tr>
                <td style="width: 10%">아이디</td>
                <td style="width: 70%">{{ userData.loginId }}</td>
              </tr>
              <tr>
                <td>비밀번호</td>
                <td class="flex_start">
                  <q-input
                    v-model="userData.pwd"
                    class="passupdate_input"
                    borderless
                    type="password"
                  />
                  <span style="font-size: 14px; color: #f5536c"
                    >(영문 + 숫자 6~12자리로 구성)</span
                  >
                </td>
              </tr>
              <tr>
                <td>비밀번호 확인</td>
                <td>
                  <q-input
                    v-model="userData.pwdChk"
                    class="passupdate_input"
                    borderless
                    type="password"
                  />
                </td>
              </tr>
            </tbody>
          </q-markup-table>
        </div>

        <q-card-actions class="passupdate_btn_wrap">
          <BtnComp_active btn-label="닫기" @click="onClickClose" />
          <BtnComp_default btn-label="확인" @click="onClickSave" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, computed, defineEmits, watch } from "vue";
import { api } from "src/boot/axios";
import * as util from "src/support/util";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import BtnComp_active_small from "src/components/web/common/BtnComp_active_small.vue";

const emit = defineEmits(["close"]);
const props = defineProps({
  isOpen: { type: Boolean, default: false },
  loginId: { type: String },
});

const isOpenPwdEdit = computed(() => {
  return props.isOpen;
});

const userData = ref({
  loginId: props.loginId,
  pwd: "",
  pwdChk: "",
});

//---------------------------- Function ------------------------------//

// 확인
async function onClickSave() {
  const pwd = userData.value.pwd;
  const pwdChk = userData.value.pwdChk;

  if (pwd === "") {
    alert("비밀번호를 입력해 주세요.");
    return;
  } else if (pwd !== pwdChk) {
    alert("비밀번호가 일치하지 않습니다.");
    return;
  }

  try {
    await api.post("/main/editPassword", userData.value);
    util.showNotify("수정 되었습니다.");

    userData.value.pwd = "";
    userData.value.pwdChk = "";

    emit("close");
  } catch (error) {
    alert(error.response.data);
  }
}

// 닫기
function onClickClose() {
  userData.value.pwd = "";
  userData.value.pwdChk = "";

  emit("close");
}

watch(() => {
  if (isOpenPwdEdit.value) {
    userData.value.loginId = props.loginId;
  }
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
.passupdate_title {
  @include flex_between;
  @include fw-7;
  padding: 0 0 16px 0;
  border-bottom: solid 1px #999 !important;
}
.passupdate_user_wrap,
.passupdate_section_wrap {
  padding: 0 16px;
  width: 560px;
}

.passupdate_table_header_wrap {
  position: relative;
  padding-bottom: 8px;
  border-bottom: 2px solid $b3;
  img {
    position: absolute;
    top: 3px;
    left: 0;
  }
  p {
    span {
      @include fs-5;
      margin-left: 12px;
      color: #666666;
    }
  }
}
.passupdate_section_wrap {
  margin-bottom: 40px;
  tr:last-child,
  td {
    border: none;
  }
  tr td:nth-child(1),
  td:nth-child(3) {
    @include fw-7;
  }
  td {
    height: 40px !important;
  }
}
.passupdate_input {
  @include fs-5;
  height: 24px;
  border: 1px solid #666666;
  border-radius: 5px;
  max-width: 160px;
}
.passupdate_input .q-field__control {
  padding: 0 8px;
  height: 24px !important;
}
.passupdate_btn_wrap {
  @include flex_center;
  padding: 8px 0 16px;
  gap: 16px;
}
</style>

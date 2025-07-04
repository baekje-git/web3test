<template>
  <q-dialog v-model="isOpen" persistent>
    <q-card style="padding: 32px">
      <div class="dialog_header">
        <span>비밀번호 찾기</span>
      </div>
      <div class="dialog_body">
        <div>
          <div>
            <form>
              <div>
                <div class="dialog_txt">
                  * 아이디, 대표자명과 사업자번호를 입력하고 전송을 누르시면<br />
                  &nbsp;&nbsp;&nbsp;아이디 등록시 입력한 휴대전화 번호로 임시
                  비밀번호가 발송됩니다.<br />
                  * 휴대전화가 변경 되신 분은 담당자에게 연락주세요.
                </div>
                <div class="table_wrap">
                  <q-markup-table separator="cell">
                    <tbody>
                      <tr>
                        <th scope="row">아이디</th>
                        <td>
                          <q-input
                            v-model="findLoginPwData.userId"
                            type="text"
                            placeholder="아이디를 입력하세요."
                            maxlength="20"
                            class="dia_input"
                          />
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">대표자명</th>
                        <td>
                          <q-input
                            v-model="findLoginPwData.ceoNm"
                            type="text"
                            placeholder="대표자를 입력하세요."
                            maxlength="6"
                            class="dia_input"
                          />
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">사업자번호</th>
                        <td>
                          <q-input
                            v-model="findLoginPwData.serviceNo"
                            type="text"
                            placeholder="번호입력시(-제외)"
                            maxlength="15"
                            :onkeyup="onKeyupServiceNo"
                            class="dia_input"
                          />
                        </td>
                      </tr>
                    </tbody>
                  </q-markup-table>
                </div>
              </div>

              <div
                v-if="returnFindPwInfo"
                style="text-align: center; margin-top: 10px"
              >
                <b>{{ findPwValue }}</b>
              </div>

              <div class="btn_wrap">
                <q-btn class="btn_trans" label="닫기" @click="onClickClose" />
                <q-btn
                  class="btn_close"
                  label="전송"
                  color="primary"
                  @click="onClickTransmission"
                />
              </div>
            </form>
          </div>
        </div>
      </div>
    </q-card>
  </q-dialog>
</template>
<script setup>
//--------------------------- Import -----------------------------//

import { ref, computed, defineEmits } from "vue";
import * as util from "src/support/util";
import { api } from "boot/axios";

const emit = defineEmits(["close"]);

//------------------------- Declaration --------------------------//

const props = defineProps({
  isOpen: { type: Boolean },
});

const isOpen = computed(() => {
  return props.isOpen;
});

const findLoginPwData = ref({
  userId: "",
  ceoNm: "",
  serviceNo: "",
});

const returnFindPwInfo = ref(false);

let findPwValue = ref("");

//--------------------------- Event -----------------------------//

async function initFindLoginPwData() {
  findLoginPwData.value.userId = "";
  findLoginPwData.value.ceoNm = "";
  findLoginPwData.value.serviceNo = "";
  returnFindPwInfo.value = false;
}

// 임시 비밀번호 sms발송
async function tempPasswordTransmission() {
  try {
    await api.post("/main/sendTempPw", findLoginPwData.value);
    alert("문자발송이 완료되었습니다.");

    await initFindLoginPwData();
    emit("close");
  } catch (error) {
    alert(error.response.data);
  }
}

// 전송 버튼 클릭
async function onClickTransmission() {
  if (findLoginPwData.value.userId.length === 0) {
    alert("아이디를 입력하세요.");
    return;
  } else if (findLoginPwData.value.ceoNm.length === 0) {
    alert("대표자를 입력하세요.");
    return;
  } else if (findLoginPwData.value.serviceNo.length === 0) {
    alert("사업자번호를 입력하세요.");
    return;
  }

  try {
    const res = await api.get("/main/findLoginPw", {
      params: findLoginPwData.value,
    });

    if (res.HP_NO === "") {
      returnFindPwInfo.value = true;
      findPwValue.value = "전송할 번호정보가 없습니다. 관리자에게 문의하세요.";
    } else if (res.LOGIN_ID) {
      await tempPasswordTransmission();
      returnFindPwInfo.value = false;
      findPwValue.value = "";
    } else {
      returnFindPwInfo.value = true;
      findPwValue.value = "일치하는 정보가 없습니다.";
    }
  } catch (error) {
    returnFindPwInfo.value = true;
    findPwValue.value = "일치하는 정보가 없습니다.";
  }
}

function onKeyupServiceNo() {
  const serviceNo = findLoginPwData.value.serviceNo;

  findLoginPwData.value.serviceNo = serviceNo.replace(/[^0-9]/g, "");
}

async function onClickClose() {
  await initFindLoginPwData();
  emit("close");
}
</script>
<style lang="scss" scoped>
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid $line-300;
}
.btn_wrap {
  @include flex_between;
  margin-top: 40px;
  gap: 10px;
}
.btn_trans,
.btn_close {
  width: 50%;
  border-radius: 0 !important;
  &::after,
  &::before {
    box-shadow: none;
  }
}
.btn_trans {
  color: #111;
  background-color: #fff;
  border: 1px solid $gray-a;
}

.btn_trans::v-deep span {
  color: #111 !important;
}

.dialog_txt {
  @include fs-5;
  padding: 32px 0 4px;
  color: $gray-9;
  font-weight: 600;
}

.table_wrap {
  width: 536px;
  border-top: 1px solid $gray-9;
  border-bottom-color: $td-line;
  tr:focus td::before,
  tr:hover td::before {
    display: none;
  }
  tr:not(:nth-child(1)) th,
  tr:not(:nth-child(1)) td {
    border-top: 1px solid $td-line;
  }
  th {
    width: 120px;
    @include fs-5;
    text-align: right;
    background: #f8f8f8;
  }
  td {
    @include fs-5;
    border-color: $td-line !important;
    border-bottom: none;
  }
  .q-input {
    padding: 0 10px 0 5px;
  }
}
</style>

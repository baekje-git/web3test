<template>
  <q-dialog v-model="isOpen" persistent>
    <q-card>
      <div class="dialog_header" style="width: 380px">
        <span>비밀번호 찾기</span>
      </div>
      <div>
        <div>
          <div>
            <form>
              <div>
                <div style="padding: 10px">
                  <p>
                    <strong style="font-size: 9pt">
                      * 아이디, 대표자명과 사업자번호를 입력하고 전송을 누르시면 </strong
                    ><br />
                    <strong style="font-size: 9pt">
                      &nbsp;&nbsp;&nbsp;아이디 등록시 입력한 휴대전화 번호로
                      임시 비밀번호가 발송됩니다. </strong
                    ><br />
                    <strong style="font-size: 9pt">
                      * 휴대전화가 변경 되신 분은 담당자에게 연락주세요.
                    </strong>
                  </p>
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

              <div align="right" style="margin: 10px 8px 0">
                <q-btn
                  class="btn_trans"
                  label="전송"
                  @click="onClickTransmission"
                />
                <q-btn
                  class="btn_close"
                  label="닫기"
                  style="width: 100%"
                  color="primary"
                  @click="onClickClose"
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
  alert("임시 비밀번호 발송");
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
  font-size: 13px;
  line-height: 23px;
  font-weight: bold;
  box-sizing: border-box;
  padding: 5px 10px;
  position: relative;
  background-color: #1d4c9e;
  color: #ffffff;
}

.btn_trans {
  width: 100%;
  background-color: #1f3f82 !important;
  border-radius: 5px !important;
}

.btn_trans::v-deep span {
  color: #ffffff !important;
}

.btn_close {
  margin: 8px 0 10px 0;
  border-radius: 5px !important;
}

.q-input {
  padding: 0 10px 0 5px;
}

.table_wrap {
  padding: 0 9px 0 9px;
}
</style>

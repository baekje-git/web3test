<template>
  <q-dialog v-model="isOpen" persistent>
    <q-card>
      <div class="dialog_header" style="width: 380px">
        <span>아이디 찾기</span>
      </div>
      <div>
        <div>
          <div>
            <form>
              <div>
                <div style="padding: 10px">
                  <p>
                    <strong style="font-size: 9pt">
                      사용자명과 사업자번호를 입력하고 확인 버튼을 눌러주세요.
                    </strong>
                  </p>
                </div>
                <div class="table_wrap">
                  <q-markup-table separator="cell">
                    <tbody>
                      <tr>
                        <th scope="row">사용자명</th>
                        <td>
                          <q-input
                            v-model="findLoginIdData.userNm"
                            type="text"
                            placeholder="이름을 입력하세요."
                            maxlength="6"
                          />
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">사업자번호</th>
                        <td>
                          <q-input
                            v-model="findLoginIdData.serviceNo"
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
                v-if="returnFindIdInfo"
                style="text-align: center; margin-top: 10px"
              >
                <b>{{ findIdValue }}</b>
              </div>

              <div align="right" style="margin: 10px 8px 0">
                <q-btn
                  class="btn_trans"
                  label="확인"
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

const findLoginIdData = ref({
  userNm: "",
  serviceNo: "",
});

const returnFindIdInfo = ref(false);
let findIdValue = "";

//--------------------------- Event -----------------------------//

async function initFindLoginIdData() {
  findLoginIdData.value.userNm = "";
  findLoginIdData.value.serviceNo = "";
}

// 확인 버튼 클릭
async function onClickTransmission() {
  if (findLoginIdData.value.userNm.length === 0) {
    alert("사용자명을 입력하세요.");
    return;
  } else if (findLoginIdData.value.serviceNo.length === 0) {
    alert("사업자번호를 입력하세요.");
    return;
  }

  try {
    const res = await api.get("/main/findLoginId", {
      params: findLoginIdData.value,
    });
    returnFindIdInfo.value = true;

    if (res.length === 0) {
      findIdValue = `일치하는 정보가 없습니다.`;
    } else {
      findIdValue = `로그인 아이디는 ${res[0].LOGIN_ID} 입니다`;
    }
  } catch (error) {
    returnFindIdInfo.value = true;
    findIdValue = `일치하는 정보가 없습니다.`;
  }
}

function onKeyupServiceNo() {
  const serviceNo = findLoginIdData.value.serviceNo;

  findLoginIdData.value.serviceNo = serviceNo.replace(/[^0-9]/g, "");
}

async function onClickClose() {
  await initFindLoginIdData();
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

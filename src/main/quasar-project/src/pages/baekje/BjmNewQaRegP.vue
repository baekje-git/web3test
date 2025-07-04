<template>
  <q-dialog v-model="isOpen" persistent>
    <q-card>
      <div class="dialog_header">
        <span>신규 거래문의</span>
      </div>
      <div>
        <div>
          <div>
            <form>
              <div>
                <div style="padding: 10px">
                  <p>
                    <strong style="font-size: 9pt">
                      아래 내용을 기재하신 후 전송버튼을 누르시면, 신속하게
                      연락드리겠습니다.
                    </strong>
                  </p>
                </div>
                <div class="table_wrap">
                  <q-markup-table separator="cell">
                    <tbody>
                      <tr>
                        <th scope="row">
                          약&nbsp;&nbsp;&nbsp;&nbsp;국&nbsp;&nbsp;&nbsp;&nbsp;명
                        </th>
                        <td>
                          <q-input
                            v-model="newQaData.drugstoreName"
                            type="text"
                            placeholder="약국명"
                            maxlength="12"
                          />
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">
                          약&nbsp;&nbsp;&nbsp;&nbsp;사&nbsp;&nbsp;&nbsp;&nbsp;명
                        </th>
                        <td>
                          <q-input
                            v-model="newQaData.pharmacistName"
                            type="text"
                            placeholder="약사명"
                            maxlength="6"
                          />
                        </td>
                      </tr>
                      <tr>
                        <th scope="col">
                          주&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소<br />
                          <p style="font-size: 8pt">(상세주소 제외)</p>
                        </th>
                        <td>
                          <q-input
                            v-model="newQaData.address"
                            type="text"
                            placeholder="(예) 서울시 구로구"
                            maxlength="20"
                          />
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">
                          연&nbsp;&nbsp;&nbsp;&nbsp;락&nbsp;&nbsp;&nbsp;&nbsp;처
                        </th>
                        <td>
                          <q-input
                            v-model="newQaData.cellPhoneNumber"
                            maxlength="13"
                            placeholder="휴대폰 번호"
                            :onkeyup="onKeyupCellPhoneNumber"
                          />
                        </td>
                      </tr>
                    </tbody>
                  </q-markup-table>
                </div>
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

const newQaData = ref({
  drugstoreName: "",
  pharmacistName: "",
  address: "",
  cellPhoneNumber: "",
  ctt: "",
});

//--------------------------- Event -----------------------------//

async function initNewQaData() {
  newQaData.value.drugstoreName = "";
  newQaData.value.pharmacistName = "";
  newQaData.value.address = "";
  newQaData.value.cellPhoneNumber = "";
}

// 전송 버튼 클릭
async function onClickTransmission() {
  if (newQaData.value.drugstoreName.length === 0) {
    alert("약국명을 입력하세요.");
    return;
  } else if (newQaData.value.pharmacistName.length === 0) {
    alert("약사명을 입력하세요.");
    return;
  } else if (newQaData.value.address.length === 0) {
    alert("주소를 입력하세요.");
    return;
  } else if (newQaData.value.cellPhoneNumber.length === 0) {
    alert("휴대폰 번호를 입력하세요.");
    return;
  }

  const drugstoreName = newQaData.value.drugstoreName;
  const pharmacistName = newQaData.value.pharmacistName;
  const address = newQaData.value.address;
  const cellPhoneNumber = newQaData.value.cellPhoneNumber;

  newQaData.value.ctt = `[${drugstoreName}] [약사명 : ${pharmacistName}] [주소 : ${address}] [연락처 : ${cellPhoneNumber}] [Web신청]`;

  await api.post("/main/newQa", newQaData.value);

  util.showNotify("신규 거래문의가 등록이 완료되었습니다.");

  await initNewQaData();
  emit("close");
}

async function onClickClose() {
  await initNewQaData();
  emit("close");
}

function onKeyupCellPhoneNumber() {
  const cellPhoneNumber = newQaData.value.cellPhoneNumber;

  newQaData.value.cellPhoneNumber = cellPhoneNumber
    .replace(/[^0-9]/g, "")
    .replace(
      /(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,
      "$1-$2-$3"
    )
    .replace("--", "-");
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

<template>
  <q-dialog v-model="isOpen" persistent>
    <q-card style="padding: 32px">
      <div class="dialog_header">
        <span>신규 거래문의</span>
      </div>
      <div class="dialog_body">
        <div>
          <div>
            <form>
              <div>
                <div class="dialog_txt">
                  아래 내용을 기재하신 후 전송버튼을 누르시면, 신속하게
                  연락드리겠습니다.
                </div>
                <div class="table_wrap">
                  <q-markup-table separator="cell">
                    <tbody>
                      <tr>
                        <th scope="row">병ㆍ의원명</th>
                        <td>
                          <q-input
                            v-model="newQaData.drugstoreName"
                            type="text"
                            placeholder="병ㆍ의원명"
                            maxlength="12"
                            class="dia_input"
                          />
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">대표자명</th>
                        <td>
                          <q-input
                            v-model="newQaData.pharmacistName"
                            type="text"
                            placeholder="대표자명"
                            maxlength="6"
                            class="dia_input"
                          />
                        </td>
                      </tr>
                      <tr>
                        <th scope="col">
                          주소<br />
                          <p>(상세주소 제외)</p>
                        </th>
                        <td>
                          <q-input
                            v-model="newQaData.address"
                            type="text"
                            placeholder="(예) 서울시 구로구"
                            maxlength="20"
                            class="dia_input"
                          />
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">연락처</th>
                        <td>
                          <q-input
                            v-model="newQaData.cellPhoneNumber"
                            maxlength="13"
                            placeholder="휴대폰 번호"
                            :onkeyup="onKeyupCellPhoneNumber"
                            class="dia_input"
                          />
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">Email</th>
                        <td>
                          <q-input
                            v-model="newQaData.email"
                            maxlength="30"
                            placeholder="Email"
                            :onkeyup="onKeyupemail"
                            class="dia_input"
                          />
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">사업자등록증</th>
                        <td>
                          <input
                            ref="file1InputRef"
                            type="file"
                            accept=".jpg,.jpeg,.png"
                            @change="handleFileChange($event, 1)"
                          />
                          <div
                            v-if="file1Preview"
                            style="
                              margin-top: 8px;
                              display: flex;
                              align-items: center;
                            "
                          >
                            <img
                              :src="file1Preview"
                              alt="파일1 미리보기"
                              style="max-width: 100px"
                            />
                            <q-btn
                              icon="close"
                              flat
                              round
                              dense
                              @click="removeFile(1)"
                              title="삭제"
                              style="margin-left: 8px"
                            />
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">면허증명서</th>
                        <td>
                          <input
                            ref="file2InputRef"
                            type="file"
                            accept=".jpg,.jpeg,.png"
                            @change="handleFileChange($event, 2)"
                          />
                          <div
                            v-if="file2Preview"
                            style="
                              margin-top: 8px;
                              display: flex;
                              align-items: center;
                            "
                          >
                            <img
                              :src="file2Preview"
                              alt="파일2 미리보기"
                              style="max-width: 100px"
                            />
                            <q-btn
                              icon="close"
                              flat
                              round
                              dense
                              @click="removeFile(2)"
                              title="삭제"
                              style="margin-left: 8px"
                            />
                          </div>
                        </td>
                      </tr>
                    </tbody>
                  </q-markup-table>
                </div>
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
import appConfig from "src/support/config";

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
  email: "",
});

// 파일 refs 및 프리뷰
const file1 = ref(null);
const file2 = ref(null);
const file1Preview = ref(null);
const file2Preview = ref(null);
const file1InputRef = ref(null);
const file2InputRef = ref(null);

function handleFileChange(event, num) {
  const f = event.target.files[0];
  if (!f || !["image/jpeg", "image/png"].includes(f.type)) {
    alert("jpg 또는 png 파일만 업로드 가능합니다.");
    return;
  }
  const reader = new FileReader();
  reader.onload = () => {
    if (num === 1) {
      file1.value = f;
      file1Preview.value = reader.result;
    } else {
      file2.value = f;
      file2Preview.value = reader.result;
    }
  };
  reader.readAsDataURL(f);
}
function removeFile(num) {
  if (num === 1) {
    file1.value = null;
    file1Preview.value = null;
    if (file1InputRef.value) {
      file1InputRef.value.value = "";
    }
  } else {
    file2.value = null;
    file2Preview.value = null;
    if (file2InputRef.value) {
      file2InputRef.value.value = "";
    }
  }
}

//--------------------------- Event -----------------------------//

async function initNewQaData() {
  newQaData.value.drugstoreName = "";
  newQaData.value.pharmacistName = "";
  newQaData.value.address = "";
  newQaData.value.cellPhoneNumber = "";
  newQaData.value.email = "";
  file1.value = null;
  file2.value = null;
  file1Preview.value = null;
  file2Preview.value = null;

  if (file1InputRef.value) file1InputRef.value.value = "";
  if (file2InputRef.value) file2InputRef.value.value = "";
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
  } else if (newQaData.value.email.length === 0) {
    alert("email주소를 입력하세요");
    return;
  }

  const drugstoreName = newQaData.value.drugstoreName;
  const pharmacistName = newQaData.value.pharmacistName;
  const address = newQaData.value.address;
  const cellPhoneNumber = newQaData.value.cellPhoneNumber;
  const email = newQaData.value.email;

  newQaData.value.ctt = `[${drugstoreName}] [약사명 : ${pharmacistName}] [주소 : ${address}] [연락처 : ${cellPhoneNumber} [email : ${email}] [Web신청]`;

  const res = await api.post("/main/newQa", newQaData.value);

  const seqNo = res.seqNo;

  if (file1.value || file2.value) {
    const fd = new FormData();
    fd.append("seqNo", seqNo);
    if (file1.value) fd.append("files", file1.value);
    if (file2.value) fd.append("files", file2.value);

    await api.post("/main/newQaFiles", fd, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  }

  //await api.post("/main/newQaFiles", fd);
  //  }
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

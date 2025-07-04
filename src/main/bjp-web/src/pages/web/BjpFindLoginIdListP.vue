<template>
  <q-dialog v-model="isOpen" persistent>
    <q-card style="padding: 32px">
      <div class="dialog_header">
        <span>아이디 찾기</span>
      </div>
      <div class="dialog_body">
        <div>
          <div>
            <form>
              <div>
                <div class="dialog_txt">
                  사용자명과 사업자번호를 입력하고 확인 버튼을 눌러주세요.
                  <br />
                  비밀번호는 영업 담당자에게 문의해주세요.
                  <span class="red-400">횟수({{ count }}/3)</span>
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
                            class="dia_input"
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
                            class="dia_input"
                            :onkeyup="onKeyupServiceNo"
                            @keyup.enter="onClickTransmission"
                          />
                        </td>
                      </tr>
                    </tbody>
                  </q-markup-table>
                </div>
              </div>

              <div
                v-if="returnFindIdInfo"
                style="text-align: center; margin-top: 10px; color: red"
              >
                <b>{{ findIdValue }}</b>
              </div>

              <div class="btn_wrap">
                <q-btn
                  class="btn_trans"
                  label="닫기"
                  @click="onClickClose"
                  :disable="disA"
                />
                <q-btn
                  class="btn_close"
                  color="primary"
                  label="확인"
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

import { ref, computed, defineEmits, onMounted } from "vue";
import { Cookies } from "quasar";
import * as util from "src/support/util";
import { api } from "boot/axios";

const emit = defineEmits(["close", "findLoginId"]);

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
const findIdValue = ref("");
const findIdValue2 = ref("");
const count = ref(0);
const disA = ref(false);

//--------------------------- Event -----------------------------//

async function initFindLoginIdData() {
  findLoginIdData.value.userNm = "";
  findLoginIdData.value.serviceNo = "";
}

// const lockChk = () => {

//   if (!util.isEmpty(Cookies.get("lockTimeFindId"))) {
//     let lockCount = Cookies.get("lockTimeFindId");
//     if( lockCount > 2){
//       findIdValue.value = `정보노출이 의심됩니다. 잠시후에 다시 시도하세요`;

//       if( lockCount == 3){
//         let today = new Date();
//         today.setMinutes(today.getMinutes() + 30);
//         Cookies.set("lockTimeFindId", count.value, { expires: today} );
//         disA.value = true;
//       }
//       return;
//     }
//   }
// }

// 확인 버튼 클릭
async function onClickTransmission() {
  if (count.value > 2) {
    findIdValue.value = `정보노출이 의심됩니다. 잠시후에 다시 시도하세요`;
    if (count.value == 3) {
      let today = new Date();
      today.setMinutes(today.getMinutes() + 30);
      Cookies.set("lockTimeFindId", count.value, { expires: today });
      disA.value = true;
    }
    return;
  }

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
      findIdValue.value = `일치하는 정보가 없습니다.`;
      count.value++;
    } else {
      findIdValue.value = `로그인 아이디는 ${res[0].LOGIN_ID} 입니다`;
      emit("findLoginId", res[0].LOGIN_ID);
      emit("close");
      await initFindLoginIdData();
      findIdValue.value = "";
    }
  } catch (error) {
    returnFindIdInfo.value = true;
    findIdValue.value = `일치하는 정보가 없습니다.`;
  }
}

function onKeyupServiceNo() {
  const serviceNo = findLoginIdData.value.serviceNo;

  findLoginIdData.value.serviceNo = serviceNo.replace(/[^0-9]/g, "");
}

async function onClickClose() {
  await initFindLoginIdData();
  if (!util.isEmpty(Cookies.get("lockTimeFindId"))) {
    findIdValue.value = `정보노출이 의심됩니다. 잠시후에 다시 시도하세요`;
  } else {
    findIdValue.value = "";
  }
  emit("close");
}

onMounted(() => {
  // 30분 로그인 락
  if (!util.isEmpty(Cookies.get("lockTimeFindId"))) {
    count.value = Cookies.get("lockTimeFindId");
    findIdValue.value = `정보노출이 의심됩니다. 잠시후에 다시 시도하세요`;
    returnFindIdInfo.value = true;
    disA.value = true;
  } else {
    count.value = 0;
    disA.value = false;
  }
});
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

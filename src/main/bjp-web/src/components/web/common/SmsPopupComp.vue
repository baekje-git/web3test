<template>
  <div class="DialogComp_wrap">
    <div class="" @click="isOpen = true">
      <slot></slot>
    </div>

    <q-dialog v-model="isOpen">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="q-card_a_title"><p>SMS 전송</p></div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>SMS 전송</span>
        </div>

        <div class="q-card_a_section_wrap">
          <q-markup-table>
            <tbody>
              <tr>
                <td style="width: 120px">{{ recvNmLabel }}</td>
                <td style="width: ">{{ sendSmsData.recvNm }}</td>
              </tr>
              <tr>
                <td>전화번호</td>
                <td>{{ recvHp }}</td>
              </tr>
              <tr>
                <td>내용</td>
                <td>
                  <q-input
                    v-model="sendSmsData.content"
                    class="q-card_a_input_textarea"
                    type="textarea"
                    borderless
                    bottom-slots
                    autogrow
                    @update:model-value="(val) => onshowCount(val)"
                  >
                    <template v-slot:counter>
                      <div class="">{{ byteCount }} / 80 Byte</div>
                    </template>
                  </q-input>
                </td>
              </tr>
              <tr>
                <td>보내는 사람</td>
                <td class="flex_start">
                  <span>{{ sendSmsData.sendNm }}</span>
                  <BtnComp_default
                    btn-label="전송"
                    class="btn-line default"
                    @click="onClickSendSms"
                  />
                </td>
              </tr>
            </tbody>
          </q-markup-table>
        </div>

        <q-card-actions class="q-card_btn_wrap">
          <BtnComp_default
            class="btn-fill gray"
            btn-label="닫기"
            v-close-popup
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted, inject, computed } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import BtnComp_active from "./BtnComp_active.vue";
import BtnComp_default from "./BtnComp_default.vue";

const isOpen = ref(false);
const bus = inject("bus");
const userInfo = util.getUserData();

const searchParam = ref({
  custCd: userInfo.CUST_CD,
});

const sendSmsData = ref({
  recvNm: "",
  recvTel: "",
  sendNm: "",
  sendTel: "",
  content: "",
  userId: userInfo.USER_ID,
  loginId: userInfo.LOGIN_ID,
  brchCd: userInfo.BRCH_CD,
  userGbCd: userInfo.USER_GB_CD,
  custNm: userInfo.CUST_NM,
});

const recvNmLabel = computed(() =>
  userInfo.USER_GB_CD === "30" ? "담당자" : "약사님명"
);

const recvHp = ref("");
const byteCount = ref(0);

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/myPage/popupSms", { params: searchParam.value });

  if (userInfo.USER_GB_CD === "10" || userInfo.USER_GB_CD === "20") {
    // 관리자, 영업사원
    sendSmsData.value.recvNm = res.CEO_NM;
    sendSmsData.value.sendNm = res.EMP_NM;
    sendSmsData.value.recvTel = res.SMS_NO.replaceAll("-", "");
    sendSmsData.value.sendTel = res.TEL_NO.replaceAll("-", "");

    recvHp.value = res.SMS_NO;
  } else if (userInfo.USER_GB_CD === "30") {
    // 약국
    sendSmsData.value.recvNm = res.EMP_NM;
    sendSmsData.value.sendNm = res.CEO_NM;
    sendSmsData.value.recvTel = res.EMP_HP.replaceAll("-", "");
    sendSmsData.value.sendTel = res.TEL_NO.replaceAll("-", "");

    recvHp.value = res.EMP_HP;
  }
}

function onshowCount(newValue) {
  let textLength = 0;

  textLength = (function (s, b, i, c) {
    for (b = i = 0; (c = s.charCodeAt(i++)); b += c >> 11 ? 3 : c >> 7 ? 2 : 1);
    return b;
  })(newValue);
  byteCount.value = textLength;
}

async function onClickSendSms() {
  let textLength = 0;

  textLength = (function (s, b, i, c) {
    for (b = i = 0; (c = s.charCodeAt(i++)); b += c >> 11 ? 3 : c >> 7 ? 2 : 1);
    return b;
  })(sendSmsData.value.content);

  if (textLength > 80) {
    alert("최대 글자 입력수는 80자 까지 입력 가능합니다.");
    return;
  }

  try {
    if (!confirm("SMS를 전송 하시겠습니까?")) return;

    await api.post("/myPage/sendSms", sendSmsData.value);

    util.showNotify("정상처리 되었습니다.");
    isOpen.value = false;
  } catch (error) {
    alert("오류가 발생하였습니다.");
  }
}

bus.on("sendSms", async () => {
  sendSmsData.value.content = ""; // ✅ 내용 초기화
  byteCount.value = 0; // ✅ 바이트 카운트도 초기화 (선택)
  await fetchData();
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
  align-items: center !important;
  gap: 16px;
}

.q-card_a_title p {
  @include fw-7;
  padding: 0 0 16px 0;
  border-bottom: solid 1px #999 !important;
}
.q-card_a_section_wrap {
  padding: 0 !important;
  margin: 16px 0;
  width: 640px;
  border-top: 1px solid $gray-9;
}
.q-card_a_section_wrap tr:nth-child(1) td {
  @include fw-7;
  background-color: transparent !important;
}
.q-card_a_section_wrap tr td {
  padding: 0 16px;
  border-bottom: 1px solid $td-line;
}
.q-card_a_section_wrap tr td:nth-child(1) {
  text-align: center;
  background-color: #f8f8f8 !important;
}
.q-card_btn_wrap {
  @include flex_end;
  gap: 8px;
  padding: 16px !important;
}

.q-card_a_input_textarea {
  margin: 16px 0;
  border: 1px solid $line-400 !important;
}
.q-card_a_input_textarea textarea {
  height: 80px !important;
  padding: 8px !important;
}
</style>

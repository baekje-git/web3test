<template>
  <div class="DialogComp_wrap">
    <div class="" @click="isOpenEditPopup = true">
      <slot></slot>
    </div>

    <q-dialog v-model="isOpenEditPopup">
      <q-card>
        <q-card-section>
          <div class="q-card_c_title"><p>사용자 정보 수정</p></div>
        </q-card-section>

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
                <td style="width: 15%">아이디</td>
                <td style="width: 35%">
                  <q-input
                    v-model="dataParams.loginId"
                    class="q-card_c_input"
                    borderless
                    disable
                  />
                </td>
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

        <q-card-actions
          class="q-card_btn_wrap"
          style="gap: 8px; padding: 16px !important"
        >
          <BtnComp_default btn-label="저장" @click="onClickMemberInfoEdit" />
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
});

//---------------------------- Function ------------------------------//

async function onClickMemberInfoEdit() {
  if (!confirm("변경 하시겠습니까?")) return;

  await api.put("/myPage/editMemberInfo", dataParams.value);
  util.showNotify("변경 되었습니다.");
  bus.emit("memberInfo");

  isOpenEditPopup.value = false;
}
</script>

<style lang="scss">
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
</style>

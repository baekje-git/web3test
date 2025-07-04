<template>
  <div class="form_wrap">
    <div class="id_wrap">
      <label class="input_label"> 아이디 </label>
      <q-input
        class="input"
        v-model="user_id"
        stack-label
        :dense="dense"
        placeholder="아이디를 입력해 주세요"
      />
    </div>

    <div class="pass_wrap">
      <label class="input_label"> 비밀번호 </label>
      <q-input
        class="input"
        v-model="user_pass"
        stack-label
        :type="isPassword ? 'password' : 'text'"
        :dense="dense"
        placeholder="비밀번호를 입력해 주세요"
      >
        <template v-slot:append>
          <q-icon
            :name="isPassword ? 'visibility_off' : 'visibility'"
            class="cursor-pointer"
            @click="isPassword = !isPassword"
          />
        </template>
      </q-input>
    </div>

    <div class="switch_wrap">
      <!-- <q-toggle size="xl" v-model="toggle_value" val="xl" label="수정해야함" /> -->
      <ToggleComp toggleLabel="자동로그인" />
      <ToggleComp toggleLabel="아이디 기억하기" />
    </div>

    <div class="button_wrap">
      <q-btn class="login_btn" label="로그인" @click="$router.push(loginBtn)" />
      <div class="search_wrap">
        <q-btn
          flat
          outline
          class="search_btn"
          label="아이디, 비밀번호 찾기"
          :icon-right="mdiArrowRight"
        />
      </div>
      <div class="etcBtn_wrap">
        <q-btn class="etc_btn" label="신규 거래문의" />
        <q-btn class="etc_btn" label="제약회사 로그인" />
      </div>
    </div>

    <div class="formFooter_wrap">
      <p>
        서울특별시 구로구 공원로8길 24 백제빌딩 백제약품(주)
        <span>대표자</span>
        김승관
        <span>사업자번호</span>
        113-85-04425
      </p>
      <p>COPYRIGHT (C) BAEKJE PHARMACEUTICAL Co., Ltd. All rights reserved.</p>
    </div>
  </div>
</template>
<script setup>
//-------------------------- Import ----------------------------//

import { ref } from "vue";
import { mdiArrowRight } from "@mdi/js";
import ToggleComp from "./ToggleComp.vue";
import { Cookies } from "quasar";

//------------------------ Declaration -------------------------//

const idInfo = ref({
  isAutoLogin: Cookies.get("autoLoginData") ? true : false,
  isRememberId: Cookies.get("saveId") ? true : false,
});

const loginInfo = ref({
  loginId: Cookies.get("saveId") ?? "",
  pwd: "",
  companyYn: "N",
});

const newQaInfo = ref({
  dialog: false,
  cancelEnabled: false,
});

const isCompanyLogin = ref(false); // 제약회사 로그인 여부
const isPassword = ref(false);
const dialog = ref(false);
const cancelEnabled = ref(false);

//--------------------------- Event ----------------------------//

// export default {
//   components: {
//     ToggleComp,
//   },
//   props: {
//     loginBtn: {
//       type: String,
//       default: "/order",
//     },
//   },
//   data: () => ({}),
//   setup() {
//     const toggle_value = ref(true);
//     return {
//       user_id: ref(""),

//       user_pass: ref(""),
//       password: ref(""),
//       isPwd: ref(true),

//       toggle_value,

//       search_icon: mdiArrowRight,
//     };
//   },
// };
</script>
<style lang="scss">
.form_wrap .input {
  margin-bottom: 20px;
}
.input_label {
  @include fs-5;
  @include fw-7;
  color: $b3;
}
.q-placeholder {
  padding: 0 5%;
}

.switch_wrap {
  @include flex_start;
  gap: 32px;
  margin-bottom: 32px;
}

.login_btn {
  padding: 10px 0;
  width: 100%;
  background-color: $b3;
  border-radius: 5px !important;
}

.login_btn span {
  @include fs-4;
  color: #ffffff !important;
}
.search_wrap {
  @include flex_center;
  margin-top: 24px;
  gap: 5px;
  opacity: 0.6;
}
.search_btn {
  padding: 0 5px;
  border-radius: 4px !important;
}
.search_btn span {
  @include fs-5;
}

.etcBtn_wrap {
  @include flex_center;
  margin: 24px 0;
  gap: 16px;
}
.etc_btn {
  background-color: $w;
  border-radius: 4px !important;
  border: 1px solid rgba(102, 102, 102, 0.5);
  box-shadow: none;
}
.etc_btn {
  color: #333;
}
.etcBtn_wrap .etc_btn:nth-child(1) {
  padding: 0 33px;
}
.etcBtn_wrap .etc_btn:nth-child(2) {
  padding: 0 26px;
}

.formFooter_wrap {
  margin-top: 90px;
}
.formFooter_wrap p {
  @include fs-6;
  text-align: center;
}
.formFooter_wrap p span {
  @include fs-6;
  @include fw-7;
  color: #666666;
}
.formFooter_wrap p:nth-child(1) {
  color: #666666;
  margin-bottom: 6px;
}
.formFooter_wrap p:nth-child(2) {
  color: #999999;
}
</style>

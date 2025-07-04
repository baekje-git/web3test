<template>
  <div>
    <!-- <LoginMain>
      <LoginComp loginBtn="/order"></LoginComp>
    </LoginMain> -->
    <div class="wrap">
      <div class="section_wrap">
        <div class="section_left">
          <div class="line" />
          <div class="main_text">
            <p>
              백제약품은 <br />
              약사님과 더불어 성장합니다
            </p>
          </div>
          <div class="sub_text">
            <p>
              <span>지속적인 혁신과 도전 정신을</span> 바탕으로 <br />
              대한민국 곳곳에 의약품을 신속하게 공급하여 <br />
              국민 건강에 이바지 하는 것이 <br />
              백제약품의 목표입니다.
            </p>
          </div>
          <div class="left_link">
            <p>
              <a target="_blank" @click="onClickRedirect('baekje')">
                <span>백제약품 홈페이지 이동하기</span>
                <span>회사소개, 사업소개, 비즈니스 문의 등</span>
              </a>
            </p>
            <p>
              <a target="_blank" @click="onClickRedirect('network')">
                <span> 전국 지점망 안내 바로가기 </span>
              </a>
            </p>
          </div>
        </div>
        <div class="section_right">
          <div class="form_wrap">
            <div class="id_wrap">
              <label class="input_label"> 아이디 </label>
              <q-input
                class="input"
                v-model="loginInfo.loginId"
                stack-label
                placeholder="아이디를 입력해 주세요"
              />
            </div>

            <div class="pass_wrap">
              <label class="input_label"> 비밀번호 </label>
              <q-input
                class="input"
                v-model="loginInfo.pwd"
                stack-label
                :type="isPassword ? 'password' : 'text'"
                placeholder="비밀번호를 입력해 주세요"
                @keyup.enter="onClickLogin"
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
              <div class="switch-wrap">
                <label class="switch">
                  <input
                    v-model="idInfo.isAutoLogin"
                    type="checkbox"
                    @change="onChangeAutoLogin"
                  />
                  <span class="slider"></span>
                </label>
                <label class="switch-label">자동로그인</label>
              </div>
              <div class="switch-wrap">
                <label class="switch">
                  <input
                    v-model="idInfo.isRememberId"
                    type="checkbox"
                    @change="onChangeRememberId"
                  />
                  <span class="slider"></span>
                </label>
                <label class="switch-label">아이디 기억하기</label>
              </div>
            </div>

            <div class="button_wrap">
              <q-btn
                class="login_btn"
                :label="loginLabel"
                @click="onClickLogin"
              />
              <div class="search_wrap">
                <q-btn
                  flat
                  outline
                  class="search_btn"
                  label="아이디 찾기"
                  @click="openFindLoginId"
                />
                <FindLoginId
                  :isOpen="popupOpenInfo.isOpenFindLoginId"
                  @close="closeFindLoginId"
                />
                <q-btn
                  flat
                  outline
                  class="search_btn find_pwd"
                  label="비밀번호 찾기"
                  @click="openFindLoginPw"
                />
                <FindLoginPw
                  :isOpen="popupOpenInfo.isOpenFindLoginPw"
                  @close="closeFindLoginPw"
                />
              </div>
              <div class="etcBtn_wrap">
                <q-btn
                  v-if="!isCompanyLogin"
                  class="etc_btn"
                  label="신규 거래문의"
                  @click="openNewQa"
                />
                <NewQa
                  :isOpen="popupOpenInfo.isOpenNewQa"
                  @close="closeNewQa"
                />
                <q-btn
                  class="etc_btn"
                  :label="companyLoginLabel"
                  @click="companyLogin"
                />
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
              <p>
                COPYRIGHT (C) BAEKJE PHARMACEUTICAL Co., Ltd. All rights
                reserved.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
//-------------------------- Import ----------------------------//

import { ref, onMounted, computed, watch } from "vue";
import { jwtLogin } from "./login";
import * as util from "src/support/util";
import { useRouter } from "vue-router";
import { Cookies } from "quasar";
// import { mdiArrowRight } from "@mdi/js";
import NewQa from "src/pages/baekje/BjmNewQaRegP.vue";
import FindLoginId from "src/pages/baekje/BjmFindLoginIdListP.vue";
import FindLoginPw from "src/pages/baekje/BjmFindLoginPwListP.vue";

const router = useRouter();

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

const popupOpenInfo = ref({
  isOpenNewQa: false,
  isOpenFindLoginId: false,
  isOpenFindLoginPw: false,
});

const isCompanyLogin = ref(false);
const isPassword = ref(true);

//------------------------- Function ---------------------------//

// 자동로그인
function onChangeAutoLogin() {
  if (!idInfo.value.isAutoLogin) {
    Cookies.remove("autoLoginData");
  }
}

// 아이디 기억하기
function onChangeRememberId() {
  if (!idInfo.value.isRememberId) {
    Cookies.remove("saveId");
  }
}

// 로그인버튼 클릭
async function onClickLogin() {
  // 로그인 필수값 입력 체크
  const loginId = loginInfo.value.loginId;
  const pwd = loginInfo.value.pwd;

  if (loginId.length === 0) {
    alert("아이디를 입력해 주세요.");
    return;
  } else if (pwd.length === 0) {
    alert("비밀번호를 입력해 주세요.");
    return;
  }

  try {
    const resData = await jwtLogin(loginInfo.value);

    util.setToken(resData.accessToken);
    util.setRefreshToken(resData.refreshToken);
    util.setUserData(resData.userData);

    // 자동로그인 true면 쿠키에 autoLogin 데이터 저장
    if (idInfo.value.isRememberId) {
      Cookies.set(
        "autoLoginData",
        {
          userId: util.getUserData().USER_ID,
          refreshToken: resData.refreshToken,
          companyYn: loginInfo.value.companyYn,
        },
        { expires: 7 }
      );
    }

    // 아이디 기억하기 true면 쿠키에 loginId저장
    if (idInfo.value.isRememberId) {
      Cookies.set("saveId", loginInfo.value.loginId, { expires: 7 });
    }

    await router.push({ path: "/com-ord" }); // FIXME: 메인 페이지 화면명으로
    util.showNotify("로그인 되었습니다.");
  } catch (error) {
    if (error && error.response.status === 400) {
      alert("아이디 비밀번호를 확인해주세요.");
    }
  }
}

// 로그인버튼 Label
const loginLabel = computed(() =>
  isCompanyLogin.value === false ? "로그인" : "제약회사 로그인"
);

// 일반 or 제약회사 로그인 버튼 Label
const companyLoginLabel = computed(() =>
  isCompanyLogin.value === false ? "제약회사로그인" : "로그인"
);

// 제약회사 로그인 클릭
function companyLogin() {
  isCompanyLogin.value = isCompanyLogin.value === false ? true : false;
}

// 백제약품 홈 이동하기 or 전국 지점망안내 바로가기
function onClickRedirect(val) {
  if (val === "baekje") {
    // 백제약품 홈페이지
    window.open("http://www.baekje.net");
  } else if (val === "network") {
    // 전국 지점망
    window.open("http://www.baekje.net/bbs/board.php?bo_table=network");
  }
}

// 아이디찾기 팝업 Open
function openFindLoginId() {
  popupOpenInfo.value.isOpenFindLoginId = true;
}

// 아이디찾기 팝업 Close
function closeFindLoginId() {
  popupOpenInfo.value.isOpenFindLoginId = false;
}

// 비밀번호찾기 팝업 Open
function openFindLoginPw() {
  popupOpenInfo.value.isOpenFindLoginPw = true;
}

// 비밀번호찾기 팝업 Close
function closeFindLoginPw() {
  popupOpenInfo.value.isOpenFindLoginPw = false;
}

// 신규 거래문의 팝업 Open
function openNewQa() {
  popupOpenInfo.value.isOpenNewQa = true;
}

// 신규 거래문의 팝업 Close
function closeNewQa() {
  popupOpenInfo.value.isOpenNewQa = false;
}

watch(
  () => isCompanyLogin.value,
  async () => {
    if (isCompanyLogin.value) {
      loginInfo.value.companyYn = "Y";
    } else {
      loginInfo.value.companyYn = "N";
    }
  }
);

onMounted(async () => {
  // 자동 로그인
  if (idInfo.value.isAutoLogin) {
    const resData = await jwtLogin(Cookies.get("autoLoginData"));

    util.setToken(resData.accessToken);
    util.setRefreshToken(resData.refreshToken);
    util.setUserData(resData.userData);

    await router.push({ path: "/" });
  }
});
</script>
<style lang="scss" scoped>
.wrap {
  width: 100vw;
  height: 100vh;
  background-image: url("src/assets/images/login_bg.png");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  display: flex;
  align-items: center;
  justify-content: center;
}
.section_wrap {
  @include flex_center;
  min-width: 1200px;
  gap: 4%;
}

.section_left,
.section_right {
  min-width: 600px;
}

.line {
  width: 44px;
  height: 8px;
  background-color: #ffffff;
  border-radius: 4px;
}
.section_left div {
  margin-bottom: 36px;
}
.main_text p {
  @include fs-1;
  @include fw-7;
  color: $w;
}
.sub_text p {
  @include fs-4;
  color: $w;
}
.sub_text p span {
  @include fw-7;
  @include fs-4;
  color: $w;
}
.left_link p {
  margin-bottom: 24px;
}
.left_link p a {
  @include flex_start;
  @include fw-7;
  padding: 13px 32px;
  background-color: $b1;
  border-radius: 22px;
}
.left_link p a span {
  color: $w;
}
.left_link p a span:nth-child(2) {
  @include fs-6;
  @include fw-5;
  margin-left: 16px;
}

.section_right {
  padding: 7% 5% 3%;
  object-fit: contain;
  border-radius: 32px;
  box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
  background-color: rgba(255, 255, 255, 0.66);
}

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

.login_btn::v-deep span {
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

.switch-wrap {
  display: flex;
  align-items: center;
}

.switch-label {
  color: #666666;
  cursor: pointer;
  margin-left: 8px;
}

.switch {
  position: relative;
  display: inline-block;
  width: 40px;
  height: 20px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  transition: 0.4s;
  border-radius: 20px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 16px;
  width: 16px;
  left: 2px;
  bottom: 2px;
  background-color: white;
  transition: 0.4s;
  border-radius: 50%;
}

.switch input:checked + .slider {
  background-color: $b3;
}

.switch input:checked + .slider:before {
  transform: translateX(20px);
}
</style>

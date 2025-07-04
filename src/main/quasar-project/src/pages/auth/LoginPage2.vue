<template>
  <div class="page__login bg-spring">
    <div class="login__outer">
      <div class="left">
        <div class="bar"></div>
        <h1>
          백제약품은<br />
          약사님과 더불어 성장합니다
        </h1>
        <p class="subtext">
          <b>지속적인 혁신과 도전 정신</b>을 바탕으로<br />
          대한민국 곳곳에 의약품을 신속하게 공급하여<br />
          국민 건강에 이바지 하는 것이<br />
          백제약품의 목표입니다.
        </p>
        <a class="btn-link" @click="onClickRedirect('baekje')">
          <b>백제약품 홈페이지 이동하기</b>
          <span>회사소개, 사업소개, 비즈니스 문의 등</span>
        </a>
        <a class="btn-link" @click="onClickRedirect('network')">
          <b>전국 지점망안내 바로가기</b>
        </a>
      </div>
      <div class="right">
        <div class="loginbox">
          <div class="loginbox__contents">
            <label>
              <p>아이디</p>
              <q-input
                v-model="loginInfo.loginId"
                type="text"
                placeholder="아이디를 입력해 주세요"
              />
            </label>
            <label>
              <p>비밀번호</p>
              <q-input
                v-model="loginInfo.pwd"
                type="password"
                placeholder="비밀번호를 입력해 주세요"
                @keyup.enter="login"
              />
              <button class="btn-pw">
                <img src="~/assets/images/eye.png" alt="패스워드 보이기" />
              </button>
            </label>
            <div class="setting">
              <q-checkbox
                v-model="idInfo.isAutoLogin"
                label="자동로그인"
                @update:model-value="onClickAutoLogin"
              />
              <!-- <label>
                <button class="toggle-slider">
                  <span></span>
                </button>
                <span>자동로그인</span>
              </label> -->
              <q-checkbox
                v-model="idInfo.isRememberId"
                label="아이디 기억하기"
                @update:model-value="onClickRememberId"
              />
              <!-- <label>
                <button class="toggle-slider active" @click="onClickRememberId">
                  <span></span>
                </button>
                <span>아이디 기억하기</span>
              </label> -->
            </div>
            <a v-if="!isCompanyLogin" @click="login" class="btn-login">
              로그인
            </a>
            <a v-if="isCompanyLogin" @click="login" class="btn-login">
              제약회사 로그인
            </a>
            <a href="javascript:;" class="btn-idpw">
              <span>아이디, 비밀번호 찾기</span>
              <img src="~/assets/images/arrow_gray.png" alt="arrow" />
            </a>
            <div class="btm__btnbox">
              <!-- <a v-if="!isCompanyLogin" href="javascript:;" class="btn-default">
                신규 거래문의
              </a> -->
              <q-btn
                v-if="!isCompanyLogin"
                label="신규 거래문의"
                @click="newQaInfo.dialog = true"
              />
              <q-dialog v-model="newQaInfo.dialog" persistent>
                <q-card>
                  <q-form> </q-form>
                  <newQa />
                  <q-card-actions align="right">
                    <q-btn flat label="Cancel" color="primary" v-close-popup />
                    <q-btn
                      flat
                      label="Turn on Wifi"
                      color="primary"
                      v-close-popup
                    />
                  </q-card-actions>
                </q-card>
              </q-dialog>
              <!-- <newQa
                v-if="this.openModal == true"
                @sendClose="closeModalView"
              /> -->
              <q-btn :label="companyLoginLabel" @click="companyLogin" />
            </div>
            <div class="footerinfo">
              <p class="info">
                <span>
                  서울특별시 구로구 공원로8길 24 백제빌딩 백제약품(주)
                </span>
                <b>대표자</b>
                <span>김승관</span>
                <b>사업자번호</b>
                <span>113-85-04425</span>
              </p>
              <p class="copy">
                COPYRIGHT (C) BAEKJE PHARMACEUTICAL Co., Ltd. All rights
                reserved.
              </p>
            </div>
          </div>
          <div class="bg"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
//--------------------------- Import -----------------------------//

import { ref, onMounted, computed, watch } from "vue";
import { jwtLogin } from "./login";
import * as util from "src/support/util";
import { useRouter } from "vue-router";
import { Cookies } from "quasar";
// import router from "src/router";
import newQa from "src/pages/baekje/BjmNewQaRegP.vue";

const router = useRouter();

//------------------------- Declaration --------------------------//

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

const isCompanyLogin = ref(false);
const dialog = ref(false);
const cancelEnabled = ref(false);

//--------------------------- Event -----------------------------//

// 자동로그인
function onClickAutoLogin() {
  if (!idInfo.value.isAutoLogin) {
    Cookies.remove("autoLoginData");
  }
}

// 아이디 기억하기
function onClickRememberId() {
  if (!idInfo.value.isRememberId) {
    Cookies.remove("saveId");
  }
}

// 로그인버튼 클릭
async function login() {
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

    // await router.push({ path: "/" }); // FIXME: 메인 페이지 화면명으로
    util.showNotify("로그인 되었습니다.");
  } catch (error) {
    if (error && error.response.status === 400) {
      alert("아이디 비밀번호를 확인해주세요.");
    }
  }
}

// 일반 or 제약회사로그인 Label
const companyLoginLabel = computed(() =>
  isCompanyLogin.value === false ? "제약회사로그인" : "로그인"
);

// 신규거래 문의 클릭
function onClickNewQa() {
  window.open("qaPopup", "_blank", "width=300; height=800;");
}

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

<style scoped>
.page__login .btm__btnbox .q-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 152px;
  height: 32px;
  object-fit: contain;
  border-radius: 4px !important;
  border: solid 1px rgba(102, 102, 102, 0.5);
  background-color: #fff;
  margin: 0 10px;
  font-weight: bold;
  color: #333;
  /* padding-top: 1px; */
  padding-top: 5px;
}
</style>

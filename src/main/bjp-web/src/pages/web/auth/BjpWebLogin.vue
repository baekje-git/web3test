<template>
  <div>
    <!-- <LoginMain>
      <LoginComp loginBtn="/order"></LoginComp>
    </LoginMain> -->
    <div class="wrap">
      <div class="section_wrap">
        <div class="section_left">
          <div v-if="loginCnt.CNT === 0" class="main_txt">
            백제약품 주식회사<br />
            <b class="fw-7">병원 주문 사이트</b>
          </div>

          <div v-else class="main_txt">
            {{ loginCnt.BRCH_NM }}<br />
            <b class="fw-7">병원 주문 사이트</b>
          </div>
          <div class="main_visual" style="height: 500px">
            <!--
            <div class="obj_step1">
              <img src="assets/images/login/step01.png" />
            </div>
            <div class="obj_step2">
              <img src="assets/images/login/step02.png" />
            </div>
            <div class="obj_step3">
              <img src="assets/images/login/step03.png" />
            </div>
            <div class="obj_step4">
              <img src="assets/images/login/step04.png" />
            </div>
            <div class="obj_step5">
              <img src="assets/images/login/step05.png" />
            </div>
            <div class="visual_bg">
              <img src="assets/images/login/step_bg.png" />
            </div>
            -->
            <div class="main_img" style="margin-top: 100px">
              <img src="/combined_main_image.png" alt="MAIN IMG" />
            </div>
          </div>
        </div>
        <div class="section_right">
          <h2 class="tit">LOGIN</h2>
          <div class="form_wrap">
            <div class="id_wrap">
              <label class="input_label">User ID</label>
              <q-input
                class="input"
                v-model="loginInfo.loginId"
                stack-label
                placeholder="아이디를 입력해 주세요"
                autofocus
              />
            </div>

            <div class="pass_wrap">
              <label class="input_label">Password</label>
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
            <div class="switch_wrap" v-if="!isCompanyLogin">
              <div class="CheckBox_wrap">
                <q-checkbox
                  v-model="idInfo.isAutoLogin"
                  label="자동로그인"
                  color="blue-10"
                  size="sm"
                  @update:model-value="chkAutoLogin"
                />
              </div>
              <div class="CheckBox_wrap">
                <q-checkbox
                  v-model="idInfo.isRememberId"
                  label="아이디 기억하기"
                  color="blue-10"
                  size="sm"
                  @update:model-value="chkRememberId"
                />
              </div>

              <div class="find_wrap" v-if="!isCompanyLogin">
                <a
                  flat
                  outline
                  class="link_txt"
                  label="아이디 찾기"
                  @click="openFindLoginId"
                  >아이디 찾기</a
                >
                <FindLoginId
                  :isOpen="popupOpenInfo.isOpenFindLoginId"
                  @find-login-id="(val) => getLoginId(val)"
                  @close="closeFindLoginId"
                />
                <!-- <a
                  flat
                  outline
                  class="link_txt find_pwd"
                  label="비밀번호 찾기"
                  @click="openFindLoginPw"
                  >비밀번호 찾기</a
                > -->
                <FindLoginPw
                  :isOpen="popupOpenInfo.isOpenFindLoginPw"
                  @close="closeFindLoginPw"
                />
              </div>
            </div>

            <div class="button_wrap">
              <q-btn
                class="login_btn"
                :label="loginLabel"
                @click="onClickLogin"
              />
            </div>
            <div class="etcBtn_wrap" v-if="loginCnt.CNT === 0">
              <a
                v-if="!isCompanyLogin"
                class="link_txt active"
                label="신규 거래문의"
                @click="openNewQa"
                >신규 거래문의</a
              >
              <a
                target="_blank"
                class="link_txt"
                @click="onClickRedirect('baekje')"
              >
                백제약품 바로가기
              </a>
              <NewQa :isOpen="popupOpenInfo.isOpenNewQa" @close="closeNewQa" />
              <a
                class="link_txt"
                :label="companyLoginLabel"
                @click="companyLogin"
                >{{ companyLoginLabel }}</a
              >
            </div>

            <div class="formFooter_wrap" v-if="loginCnt.CNT === 0">
              <div class="logo">
                <img src="assets/images/login/logo_foot.svg" alt="백제약품" />
              </div>
              <p style="margin-">
                &copy; <b>{{ copyrightLabel }}</b> All rights reserved.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <DialogComp_salesDetail />
    <DialogComp_passupdate
      :is-open="isOpenPwdEdit"
      :login-id="loginInfo.loginId"
      @close="isOpenPwdEdit = false"
    />
  </div>
</template>
<script setup>
//-------------------------- Import ----------------------------//

import { ref, onMounted, computed, watch, inject } from "vue";
import { jwtLogin, jwtRefreshToken } from "./login";
import * as util from "src/support/util";
import { useRouter, useRoute } from "vue-router";
import { Cookies, event } from "quasar";
// import { mdiArrowRight } from "@mdi/js";
import NewQa from "src/pages/web/BjpNewQaRegP.vue";
import FindLoginId from "src/pages/web/BjpFindLoginIdListP.vue";
import FindLoginPw from "src/pages/web/BjpFindLoginPwListP.vue";
//추가한부분
import { api } from "boot/axios";
import DialogComp_salesDetail from "src/components/web/common/DialogComp_salesDetail.vue";
import DialogComp_passupdate from "src/components/web/common/DialogComp_passupdate.vue";

const router = useRouter();
const route = useRoute();
const bus = inject("bus");
const loginUrl = ref({});
//------------------------ Declaration -------------------------//
//추가한부분
const loginCnt = ref({});

async function selectLoginUrl() {
  try {
    const baseUrl = `${window.location.origin}/`;
    const res = await api.get("/main/selectLoginUrl", {
      params: { url: baseUrl },
    });

    loginCnt.value = res ?? {}; // 객체 전체 할당
  } catch (err) {
    console.error("❌ 에러 발생:", err);
    loginCnt.value = {}; // 초기화
  }
}
//---------------------------------------------
onMounted(async () => {
  //추가한부분
  await selectLoginUrl();
  //---------------------------------------------
  // 자동 로그인
  if (!util.isEmpty(Cookies.get("autoLoginData"))) {
    const resData = await jwtRefreshToken(Cookies.get("autoLoginData"));
    util.setToken(resData.accessToken);
    util.setRefreshToken(resData.refreshToken);
    util.setUserData(resData.userData);

    await router.push({ path: "/" });
  }

  const url = window.location.href;

  company.forEach((m) => {
    if (url.includes(m)) {
      companyNm.value = m;
    }
  });
});
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

const company = [
  "dhhorim",
  "tspharm",
  "kaymed",
  "charmacist",
  "gfpharm",
  "reinpharm",
  "nurip",
  "mediplankorea",
  "ansimpharm",
];

const companyNm = ref("N");

const copyrightLabel = computed(() => {
  if (companyNm.value === "dhhorim") {
    return "디에이치호림(주)";
  } else if (companyNm.value === "tspharm") {
    return "티에스팜(주)";
  } else if (companyNm.value === "kaymed") {
    return "(주)케이메디칼";
  } else if (companyNm.value === "charmacist") {
    return "(주)참약사";
  } else if (companyNm.value === "gfpharm") {
    return "대길약품(주)";
  } else if (companyNm.value === "reinpharm") {
    // let label = `리인팜(주) 대표자 이승민 | 사업자등록번호 571-88-02624
    // 통신판매업신고번호 제 2022-부산사상구-0719호
    // 주소 : 부산광역시 사상구 주례로 101,상가1동 지하4호(주례동)
    // 대표전화 : 070-5147-5199 이메일 :reinpharm@naver.com`;
    // label += `\n통신판매업신고번호 제 2022-부산사상구-0719호`;
    // label += `\n주소 : 부산광역시 사상구 주례로 101,상가1동 지하4호(주례동)`;
    // label += `\n대표전화 : 070-5147-5199 이메일 :reinpharm@naver.com`;
    // return label;
  } else if (companyNm.value === "nurip") {
    return "누리팜(주)";
  } else if (companyNm.value === "mediplankorea") {
    return "메디플랜(주)";
  } else if (companyNm.value === "ansimpharm") {
    return "안심약품(주)";
  }

  return "BAEKJE PHARMACEUTICAL Co., Ltd.";
});

const isOpenPwdEdit = ref(false);

//------------------------- Function ---------------------------//

// 자동 로그인
const chkAutoLogin = (val) => {
  idInfo.value.isAutoLogin = val;
  if (!idInfo.value.isAutoLogin) {
    Cookies.remove("autoLoginData");
  }
};

// 아이디 기억하기
const chkRememberId = (val) => {
  idInfo.value.isRememberId = val;
  if (!idInfo.value.isRememberId) {
    Cookies.remove("saveId");
  }
};

// 자동로그인
// function onChangeAutoLogin() {
//   if (!idInfo.value.isAutoLogin) {
//     Cookies.remove("autoLoginData");
//   }
// }

// // 아이디 기억하기
// function onChangeRememberId() {
//   if (!idInfo.value.isRememberId) {
//     Cookies.remove("saveId");
//   }
// }

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
    if (resData.userData.PASSWORD_INIT_YN === "INIT") {
      isOpenPwdEdit.value = true;
      loginInfo.value.pwd = "";
      return;
    } else if (resData.userData.PASSWORD_INIT_YN === "N") {
      alert("아이디 비밀번호를 확인해주세요.");
      return;
    }

    if (loginInfo.value.companyYn == "N") {
      util.setToken(resData.accessToken);
      util.setRefreshToken(resData.refreshToken);
      util.setUserData(resData.userData);

      // 자동로그인 true면 쿠키에 autoLogin 데이터 저장
      if (idInfo.value.isAutoLogin) {
        Cookies.set(
          "autoLoginData",
          {
            userId: util.getUserData().USER_ID,
            refreshToken: resData.refreshToken,
            //       companyYn: loginInfo.value.companyYn,
          },
          { expires: 7 }
        );
      }

      // 아이디 기억하기 true면 쿠키에 loginId저장
      if (idInfo.value.isRememberId) {
        Cookies.set("saveId", loginInfo.value.loginId, { expires: 7 });
      }

      await router.push({ path: "/" });
    } else {
      util.setToken(resData.accessToken);
      await bus.emit("saleDetail", resData.userData);
    }

    util.showNotify("로그인 되었습니다.");
  } catch (error) {
    if (error && error.response.status === 400) {
      let data = error.response.data;
      if (data == "미사용처리") {
        alert("영업담당자에게 문의 바랍니다.");
      } else if (data == "제약사 사용권한") {
        alert(`사용권한이 없습니다. \n구매부에 문의하세요.(Tel: 02-2109-9552)`);
      } else {
        alert("아이디 비밀번호를 확인해주세요.");
      }
    }
  }
}

// 로그인버튼 Label
const loginLabel = computed(() =>
  isCompanyLogin.value === false ? "로그인" : "제약회사 로그인"
);

// 일반 or 제약회사 로그인 버튼 Label
const companyLoginLabel = computed(() =>
  isCompanyLogin.value === false ? "제약회사 로그인" : "로그인"
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

// 아이디 찾기 return
function getLoginId(val) {
  loginInfo.value.loginId = val;
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
</script>
<style lang="scss">
// @import url("https://cdn.jsdelivr.net/gh/webfontworld/score@master/SCoreDream.css");
@font-face {
  font-family: "SCoreDream";
  font-weight: 400;
  font-style: normal;
}
@font-face {
  font-family: "SCoreDream";
  font-weight: 500;
  font-style: normal;
}
@font-face {
  font-family: "SCoreDream";
  font-weight: 600;
  font-style: normal;
}
$ft-login: "SCoreDream";

.wrap {
  width: 100%;
  height: 100vh;
  background-image: url("assets/images/login/bg.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  overflow: hidden;
}
.section_wrap {
  @include flex_between;
  width: 100%;
  height: 100%;
  img {
    max-width: 100%;
  }
}

.section_left,
.section_right {
  height: 100%;
}

// .line {
//   width: 44px;
//   height: 8px;
//   background-color: #fff;
//   border-radius: 4px;
// }
.section_left {
  width: calc(100% - #{per(700px)});
  padding: per(230px) per(93px) 0 per(107px);
}
.main_txt {
  line-height: 1.2em;
  font-size: clamp(48px, #{rem(60px)}, 60px);
  font-weight: 200;
  color: #071b7d;
}
.main_visual {
  position: relative;
  margin-top: rem(20px);
  padding-top: rem(50px);
  width: 100%;
  max-width: 1410px;
  .visual_bg,
  .visual_bg img {
    width: 100%;
  }
  [class*="obj_"] {
    position: absolute;
    opacity: 0;
  }

  @keyframes fade {
    from {
      opacity: 0;
    }
    to {
      opacity: 1;
    }
  }
  @for $i from 1 to 6 {
    [class*="obj_"]:nth-child(#{$i}) {
      animation: fade 1.27s 400ms * $i forwards;
    }
  }
  $obj-h: 461px;
  $obj-w: 1021px;
  .obj_step1 {
    top: per(160px, $obj-h);
    left: per(60px, $obj-w);
    max-width: per(149px, $obj-w);
  }
  .obj_step2 {
    top: 0;
    left: per(336px, $obj-w);
    max-width: per(176px, $obj-w);
  }
  .obj_step3 {
    top: per(316px, $obj-h);
    left: per(313px, $obj-w);
    max-width: per(156px, $obj-w);
  }
  .obj_step4 {
    top: per(138px, $obj-h);
    left: per(648px, $obj-w);
    max-width: per(122px, $obj-w);
  }
  .obj_step5 {
    top: 0;
    right: per(27px, $obj-w);
    max-width: per(136px, $obj-w);
  }
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
  display: flex;
  flex-direction: column;
  width: per(700px);
  min-width: 396px;
  padding: per(100px) per(90px) per(50px);
  object-fit: contain;
  border-radius: 50px 0 0 50px;
  box-shadow: 0 30px 25px 0 rgba(#000, 0.16);
  background-color: $w;
  .tit {
    margin-bottom: rem(100px);
    font-family: $ft-login;
    font-size: clamp(48px, #{rem(60px)}, 60px);
    font-weight: 600;
    line-height: 1em;
  }
}

.form_wrap {
  position: relative;
  height: 100%;
  padding-top: per(146px);
  // margin-top: rem(146px);
  [class*="label"],
  input {
    font-family: $ft-login;
  }

  .q-field__append {
    padding-right: 12px;
  }
  .q-field__marginal {
    height: 50px;
    font-size: 20px;
    color: #888;
  }

  .formFooter_wrap {
    position: absolute;
    bottom: 0;
    margin-top: 0;
    display: grid;
    grid-template-columns: repeat(2, auto);
    gap: 0 rem(40px);
    font-family: $ft-login;
    text-align: left;
    .logo {
      grid-row: span 2;
      img {
        height: 32px;
      }
    }
  }
}
.form_wrap .input {
  margin-bottom: 10px;
}
.input_label {
  display: block;
  margin-bottom: 10px;
  font-family: $ft-login;
  font-weight: 600;
  color: #aaa;
}

.q-field__control {
  height: 50px;
  background-color: #f5f5f5;
  border-radius: 5px;
  &::before {
    border-bottom: none !important;
  }
}
.dia_input .q-field__control {
  height: 32px;
}
.q-placeholder {
  padding: 0 5%;
}

.switch_wrap {
  position: relative;
  @include flex_start;
  gap: 0 12px;
  margin-bottom: rem(70px);

  .q-checkbox__inner {
    width: 20px;
    height: 20px;
    font-size: 1rem !important;

    .q-checkbox__bg {
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      border-radius: 3px;
      border: 1px solid #ccc;

      .q-checkbox__svg {
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        .q-checkbox__truthy {
          // stroke: #ccc;
        }
        &.fit {
          width: 60% !important;
          height: 60% !important;
        }
      }
    }
    &.text-blue-10 .q-checkbox__bg {
      border: none;
    }
  }
  .q-checkbox:not(.disabled) .q-checkbox__inner:before {
    border-radius: 0 !important;
  }

  .CheckBox_wrap .q-checkbox__label {
    margin-left: 5px;
    font-size: rem(14px);
    font-weight: 400;
    color: #aaa;
  }
}

.login_btn {
  padding: 10px 0;
  width: 100%;
  background-color: $b3;
  border-radius: 5px !important;
}

.login_btn::v-deep span {
  @include fs-4;
  color: #fff !important;
}
.find_wrap {
  position: absolute;
  right: 0;
  @include flex_end;
  gap: 5px;
  // opacity: 0.6;
}
.link_txt {
  font-size: rem(14px);
  font-family: $ft-login;
  color: #aaa;
  cursor: pointer;
  &.active,
  &:hover {
    color: #033da8;
    background: transparent;
  }
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
  margin-top: rem(30px);
  .link_txt:not(:nth-child(1))::before {
    content: "";
    display: inline-block;
    margin: 0 rem(12px);
    width: 1px;
    height: rem(14px);
    background-color: #ddd;
  }
}
.etc_btn {
  background-color: $w;
  border-radius: 4px;
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
  // margin-top: 90px;
  // .formFooter_wrap p {
  //   @include fs-6;
  //   text-align: center;
  // }
  // .formFooter_wrap p span {
  //   @include fs-6;
  //   @include fw-7;
  //   color: #666666;
  // }
  // .formFooter_wrap p:nth-child(1) {
  //   color: #666666;
  //   margin-bottom: 6px;
  // }
  // .formFooter_wrap p:nth-child(2) {
  //   color: #999999;
  // }

  p {
    font-size: rem(12px);
    color: #999;
    word-break: keep-all;
    &:last-child {
      margin-top: 9px;
    }
    span,
    b {
      font-family: $ft-login;
    }
  }
  span + span::before {
    content: "";
    display: inline-block;
    margin: 0 rem(8px);
    width: 1px;
    height: rem(10px);
    background-color: #ddd;
  }
  // span:nth-last-child(1)::before {
  //   width: 100%;
  //   margin: 0;
  //   height: 0;
  //   background-color: transparent;
  // }
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

.login_btn {
  padding: 10px 0;
  width: 100%;
  height: rem(70px);
  // background-color: $b3;
  background-color: #003eb1;
  border-radius: 5px;
}
.login_btn span {
  @include fs-4;
  font-family: $ft-login;
  font-weight: 500;
  color: $w;
}
.CheckBox_wrap {
  .q-checkbox__label {
    @include fs-5;
    color: #666;
  }
}
</style>

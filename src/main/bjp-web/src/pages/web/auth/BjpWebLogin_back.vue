<template>
  <div>
    <!-- <LoginMain>
      <LoginComp loginBtn="/order"></LoginComp>
    </LoginMain> -->
    <div class="wrap">
      <div class="section_wrap">
        <div v-if="companyNm === 'N'" class="section_left">
          <div class="line" />
          <div class="main_text">
            <p>
              백제약품은 <br />
              약사님과 더불어 성장합니다
            </p>
          </div>
          <!-- <div class="sub_text">
            <p>
              <span>지속적인 혁신과 도전 정신을</span> 바탕으로 <br />
              대한민국 곳곳에 의약품을 신속하게 공급하여 <br />
              국민 건강에 이바지 하는 것이 <br />
              백제약품의 목표입니다.
            </p>
          </div> -->
          <div class="left_link">
            <p style="cursor: pointer">
              <a target="_blank" @click="onClickRedirect('baekje')">
                <span>백제약품 홈페이지 이동하기</span>
                <!-- <span>회사소개, 사업소개, 비즈니스 문의 등</span> -->
              </a>
            </p>
            <p style="cursor: pointer">
              <a target="_blank" @click="onClickRedirect('network')">
                <span> 전국 지점망 안내 이동하기 </span>
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

            <!-- <div class="switch_wrap" v-if="!isCompanyLogin">
              <div class="switch-wrap">
                <label class="switch">
                  <input
                    v-model="idInfo.isAutoLogin"
                    type="checkbox"
                    @change="onChangeAutoLogin"
                  />
                  <span class="slider"></span>
                </label>
                <label class="switch-label" style="cursor: default"
                  >자동로그인</label
                >
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
                <label class="switch-label" style="cursor: default"
                  >아이디 기억하기</label
                >
              </div>
            </div> -->
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
            </div>

            <div class="button_wrap">
              <q-btn
                class="login_btn"
                :label="loginLabel"
                @click="onClickLogin"
              />
              <div class="search_wrap" v-if="!isCompanyLogin">
                <q-btn
                  flat
                  outline
                  class="search_btn"
                  label="아이디 찾기"
                  @click="openFindLoginId"
                />
                <FindLoginId
                  :isOpen="popupOpenInfo.isOpenFindLoginId"
                  @find-login-id="(val) => getLoginId(val)"
                  @close="closeFindLoginId"
                />
                <!-- <q-btn
                  flat
                  outline
                  class="search_btn find_pwd"
                  label="비밀번호 찾기"
                  @click="openFindLoginPw"
                /> -->
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
              <p v-if="companyNm === 'N'">
                서울특별시 구로구 공원로8길 24 백제빌딩 백제약품(주)
                <span>대표자</span>
                김승관
                <span>사업자번호</span>
                113-85-04425
              </p>

              <p v-if="companyNm === 'reinpharm'">
                COPYRIGHT (C) 리인팜(주) 대표자 이승민 <br />
                사업자등록번호 571-88-02624 통신판매업신고번호 제
                2022-부산사상구-0719호 <br />
                주소 : 부산광역시 사상구 주례로 101,상가1동 지하4호(주례동)<br />
                대표전화 : 070-5147-5199 이메일 :reinpharm@naver.com All rights
                reserved.
              </p>
              <p v-else>
                COPYRIGHT (C) {{ copyrightLabel }} All rights reserved.
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

import DialogComp_salesDetail from "src/components/web/common/DialogComp_salesDetail.vue";
import DialogComp_passupdate from "src/components/web/common/DialogComp_passupdate.vue";

const router = useRouter();
const route = useRoute();
const bus = inject("bus");

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

onMounted(async () => {
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
</script>
<style lang="scss">
.wrap {
  width: 100%;
  height: 100vh;
  background-image: url("assets/images/bg_illustration.png");
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

.login_btn {
  padding: 10px 0;
  width: 100%;
  background-color: $b3;
  border-radius: 5px;
}
.login_btn span {
  @include fs-4;
  color: $w;
}
.CheckBox_wrap {
  .q-checkbox__label {
    @include fs-5;
    color: #666;
  }
}
</style>

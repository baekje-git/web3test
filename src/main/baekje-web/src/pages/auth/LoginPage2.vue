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
        <a href="javascript:;" class="btn-link">
          <b>백제약품 홈페이지 이동하기</b>
          <span>회사소개, 사업소개, 비즈니스 문의 등</span>
        </a>
        <a href="javascript:;" class="btn-link">
          <b>전국 지점망안내 바로가기adf</b>
        </a>
      </div>
      <div class="right">
        <div class="loginbox">
          <div class="loginbox__contents">
            <label>
              <p>아이디</p>
              <input type="text" placeholder="아이디를 입력해 주세요" />
            </label>
            <label>
              <p>비밀번호</p>
              <input type="password" placeholder="비밀번호를 입력해 주세요" />
              <button class="btn-pw">
                <img src="~/assets/img/eye.png" alt="패스워드 보이기" />
              </button>
            </label>
            <div class="setting">
              <label>
                <button class="toggle-slider">
                  <span></span>
                </button>
                <span>자동로그인</span>
              </label>
              <label>
                <button class="toggle-slider active">
                  <span></span>
                </button>
                <span>아이디 기억하기</span>
              </label>
            </div>
            <a @click="login" class="btn-login">로그인</a>
            <a href="javascript:;" class="btn-idpw">
              <span>아이디, 비밀번호 찾기</span>
              <img src="~/assets/img/arrow_gray.png" alt="arrow" />
            </a>
            <div class="btm__btnbox">
              <a href="javascript:;" class="btn-default">신규 거래문의</a>
              <a href="javascript:;" class="btn-default">제약회사 로그인</a>
            </div>
            <div class="footerinfo">
              <p class="info">
                <span
                  >서울특별시 구로구 공원로8길 24 백제빌딩 백제약품(주)</span
                >
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

import { ref, onMounted } from "vue";
import { jwtLogin } from "./login";
import * as util from "src/support/util";
import { useRouter } from "vue-router";
import { Cookies } from "quasar";

//------------------------- Declaration --------------------------//

//--------------------------- Event -----------------------------//

// 로그인버튼 클릭
async function login() {
  alert("!!");

  util.setUserData();
}
</script>

<template>
  <div class="LoginLayout_wrap">
    <div class="LoginBox">
      <div class="Login_Title">백제약품</div>
      <div class="LoginCard_wrap">
        <slot></slot>
        <div class="LoginCopyRigth">
          COPYRIGHT (C) BAEKJE PHARMACEUTICAL Co., Ltd. All rights reserved.
        </div>
      </div>
    </div>
  </div>
</template>
<script setup></script>
<style lang="scss" scoped>
.LoginLayout_wrap {
  @include flex_center;
  width: 100vw;
  height: 100vh;
  background-image: url("assets/images/bg_illustration.png");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  position: relative;
}
.Login_Title {
  @include fs-2;
  @include fw-7;
  color: $w;
  text-align: center;
  margin-bottom: 40px;
}
.LoginCard_wrap {
  min-width: 330px;
  padding: 15% 5%;
  border-radius: 32px;
  box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
  background-color: rgba(255, 255, 255, 0.77);
}
.LoginCopyRigth {
  @include fs-8;
  text-align: center;
  margin-top: 100px;
}
</style>

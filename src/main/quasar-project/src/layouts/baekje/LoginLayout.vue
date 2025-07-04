<template>
  <div class="wrap">
    <div class="section_wrap">
      <div class="section_left">
        <div class="line"/>
        <div class="main_text">
          <p>
            백제약품은 <br/>
            약사님과 더불어 성장합니다
          </p>
        </div>
        <div class="sub_text">
          <p>
            <span>지속적인 혁신과 도전 정신을</span> 바탕으로 <br/>
            대한민국 곳곳에 의약품을 신속하게 공급하여 <br/>
            국민 건강에 이바지 하는 것이 <br/>
            백제약품의 목표입니다.
          </p>
        </div>
        <div class="left_link">
          <p>
            <a href="#none" target="_blank">
              <span>백제약품 홈페이지 이동하기</span>
              <span>회사소개, 사업소개, 비즈니스 문의 등</span>
            </a>
          </p>
          <p>
            <a href="#none" target="_blank">
              <span>
                전국 지점망 안내 바로가기
              </span>
            </a>
          </p>
        </div>
      </div>
      <div class="section_right">
        <slot></slot>
      </div>
    </div>
  </div>
</template>
<script>
export default {

}
</script>
<style lang="scss" scoped>

.wrap {
  width: 100vw;
  height: 100vh;
  background-image: url("../assets/images/login_bg.png");
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






</style>

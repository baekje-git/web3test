<template>
  <div class="PageLayout_wrap">
    <div class="page_left">
      <!-- 페이지 상단/ -->
      <div class="left_top">
        <!-- 페이지 타이틀/ -->
        <div class="page_tit">
          <slot name="pageTit"></slot>
        </div>
        <SubTop />
      </div>
      <div class="left_left">
        <slot name="left_left"></slot>
      </div>
      <div class="left_right">
        <slot name="left_right"></slot>
      </div>
    </div>
    <div class="page_right">
      <SideComp />
    </div>
  </div>
</template>
<script setup>
import SubTop from "src/components/web/common/SubTop.vue";
import SideComp from "src/components/web/common/SideComp.vue";
</script>
<style lang="scss" scoped>
.PageLayout_wrap {
  display: inline-flex;
  //padding: 40px rem(30px);
  padding: 30px rem(20px);
  width: 100%;
  // min-width: 1366px;
  gap: 20px;
}
.page_left {
  display: flex;
  flex-wrap: wrap;
  align-items: flex-start;
  //padding: 20px rem(30px);
  padding: 20px rem(20px);
  width: 100%;
  background-color: #fff;
  border-radius: 10px;

  .left_top {
    @include flex_between;
    align-items: flex-start;
    position: relative;
    margin-bottom: 16px;
    width: 100%;
    height: 40px;
    border-bottom: 1px solid $line-300;

    .page_tit {
      height: 1em;
      line-height: 1em;
      font-size: rem(24px);
      font-weight: 500;
      color: $gray-1;
    }
  }
}
.page_left .left_left {
  width: 30%;
}
.page_left .left_right {
  width: 70%;
}
.page_right {
  width: 90px;
  min-width: 90px;
}
@media screen and (max-width: 1270px) {
  .PageLayout_wrap {
    padding: 30px 10px;
    gap: 20px 10px;
  }
  .page_left {
    padding: 20px 15px;
    width: 100%;
  }
  .page_right {
    display: none;
  }
}
@media screen and (max-width: 830px) {
  .page_left .left_left,
  .page_left .left_right {
    width: 100%;
  }
  .page_left .left_left {
    display: flex;
    gap: 20px;
  }
}
</style>

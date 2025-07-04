<template>
  <div class="PageLayout_wrap">
    <div class="page_left">
      <div class="left_left">
        <slot name="leftSlot"></slot>
      </div>
      <div class="left_right">
        <slot name="rightSlot"></slot>
      </div>
    </div>
    <div class="page_right">
      <SideComp />
    </div>
  </div>
</template>
<script setup>
import SideComp from "src/components/web/common/SideComp.vue";
</script>
<style lang="scss" scoped>
.PageLayout_wrap {
  // display: flex;
  // justify-content: center;
  display: inline-flex;
  padding: 0 80px;
  margin-top: 40px;
  gap: 40px;
  // height: 2000px; //-----------------------------------임시
}
.page_left {
  display: flex;
  width: 1600px;
  min-width: 1600px;
  gap: 40px;
}
.left_left {
  width: 900px;
}
.left_right {
  width: 660px;
}
.page_right {
  width: 120px;
  min-width: 120px;
}
</style>

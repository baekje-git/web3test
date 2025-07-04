<template>
  <div class="mainPage_wrap">
    <HeaderLayout_mobile/>
    <div class="">
      <router-view/>
    </div>
    <BottomLayout_mobile/>
  </div>
</template>
<script setup>
import HeaderLayout_mobile from 'src/layouts/__HeaderLayout_mobile.vue';
import BottomLayout_mobile from 'src/layouts/__BottomLayout_mobile.vue';

</script>
<style lang="scss" scoped>

.mainPage_wrap {
  min-width: 360px;
  position: relative;
  padding: 10px 16px 66px;
}

</style>

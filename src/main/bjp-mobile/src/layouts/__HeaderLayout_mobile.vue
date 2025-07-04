<template>
  <div class="HeaderLayout_wrap">
    <div class="Header_title">
      <router-link to="/">
        <span>백제약품</span>
      </router-link>
    </div>
    <div class="CountBox_wrap">
      <CountBoxComp_mobile
        countbox_style="red"
        countdown="18:00:00"

        상태="남음"
        요일=""
      />
      <!-- <CountBoxComp_mobile
        countbox_style="default"
        countdown="18:00:00"

        상태="남음"
        요일=""
      /> -->
      <!-- <CountBoxComp_mobile
        countbox_style="end"
        countdown="18:00"

        상태="마감"
        요일="(월)"
      /> -->
    </div>
  </div>
</template>
<script setup>
import CountBoxComp_mobile from 'src/components/__commonComp/countboxComp/CountBoxComp_mobile.vue'

</script>
<style lang="scss" scoped>

.HeaderLayout_wrap {
  @include flex_between;
}
.Header_title span {
  @include fs-3;
  @include fw-7;
  color: $b3;
}

.CountBox_wrap {
  @include flex_center;
  gap: 4px;
}

</style>

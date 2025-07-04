<template>
  <div class="">
    <q-layout style="height: 100vh">
      <q-page-container>
        <q-page>
          <div class="mainPage_wrap" ref="divScroll">
            <HeaderLayout_mobile />
            <div class="pageTop_wrap">
              <router-view />
            </div>
            <BottomLayout_mobile />
          </div>
          <q-page-scroller
            position="bottom-right"
            :scroll-offset="250"
            :offset="[15, 75]"
          >
            <q-btn class="btn-top" dense>
              <img src="assets/icons/arrow-up.svg" alt="dd" />
            </q-btn>
          </q-page-scroller>
        </q-page>
      </q-page-container>
    </q-layout>
  </div>
</template>
<script setup>
import { ref, inject } from "vue";
import HeaderLayout_mobile from "src/layouts/mobile/__HeaderLayout_mobile.vue";
import BottomLayout_mobile from "src/layouts/mobile/__BottomLayout_mobile.vue";

const bus = inject("bus");
const divScroll = ref();

//모바일 검색 => 스크롤 상단으로 이동
bus.on("scrollUp", () => {
  divScroll.value.scrollIntoView();
});
</script>

<style lang="scss" scoped>
.mainPage_wrap {
  min-width: 360px;
  position: relative;
  // padding: 10px 16px 66px;
  padding-bottom: 66px;
}
.pageTop_wrap {
}
</style>

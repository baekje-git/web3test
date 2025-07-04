<template>
  <div>
    <PageLayout_g>
      <template v-slot:pageTit>통합주문</template>
      <template v-slot:leftSlot>
        <CardLayout v-for="(table, index) in leftSlots" :key="index">
          <div>
            <component :is="table" />
          </div>
        </CardLayout>
        <CardLayout>
          <div>
            <component :is="table" />
          </div>
        </CardLayout>
      </template>
      <template v-slot:rightSlot>
        <CardLayout v-for="(table, index) in rightSlots" :key="index">
          <div class="inner_layout rt">
            <component :is="table" />
          </div>
        </CardLayout>
        <CardLayout>
          <div class="inner_layout rt">
            <component :is="table" />
          </div>
        </CardLayout>
      </template>
    </PageLayout_g>
  </div>
</template>
<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from "vue";
import PageLayout_g from "src/layouts/web/common/PageLayout_g.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import OrderTable_1a from "src/components/web/order/OrderTable_1a.vue";
import OrderTable_2a from "src/components/web/order/OrderTable_2a.vue";
import OrderTable_3a from "src/components/web/order/OrderTable_3a.vue";
import OrderTable_4a from "src/components/web/order/OrderTable_4a.vue";

// 화면 크기에 따라 레이아웃을 변경 추가 240213
const isSmallScreen = ref(window.innerWidth < 830);
const updateScreenSize = () => {
  isSmallScreen.value = window.innerWidth < 830;
};
onMounted(() => {
  window.addEventListener("resize", updateScreenSize);
});
onBeforeUnmount(() => {
  window.removeEventListener("resize", updateScreenSize);
});

// 왼쪽 슬롯
const leftSlots = computed(() => {
  return isSmallScreen.value
    ? [OrderTable_1a, OrderTable_2a] // 작은 화면에서 순서 변경
    : [OrderTable_1a /*, OrderTable_3a*/]; // 기본 순서
});

// 오른쪽 슬롯
const rightSlots = computed(() => {
  return isSmallScreen.value
    ? [/*OrderTable_3a,*/ OrderTable_4a] // 작은 화면에서 순서 변경
    : [OrderTable_2a, OrderTable_4a]; // 기본 순서
});
</script>
<style lang="scss" scoped></style>

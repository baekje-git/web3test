<template>
  <div>
    <PageLayout>
      <template v-slot:pageTit>반품등록</template>
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
    </PageLayout>
  </div>
</template>
<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from "vue";
import PageLayout from "src/layouts/web/common/PageLayout_a.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import ReturnTable_a from "src/components/web/return/ReturnTable_a.vue";
import ReturnTable_b from "src/components/web/return/ReturnTable_b.vue";
import ReturnTable_c from "src/components/web/return/ReturnTable_c.vue";
// import ItemDetail from "src/components/web/common/BjpItemDetail.vue";
import ItemDetail from "src/components/web/order/OrderTable_4a.vue";

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
    ? [ReturnTable_a, ReturnTable_b] // 작은 화면에서 순서 변경
    : [ReturnTable_a, ReturnTable_c]; // 기본 순서
});

// 오른쪽 슬롯
const rightSlots = computed(() => {
  return isSmallScreen.value
    ? [ReturnTable_c, ItemDetail] // 작은 화면에서 순서 변경
    : [ReturnTable_b, ItemDetail]; // 기본 순서
});
</script>
<style lang="scss" scoped></style>

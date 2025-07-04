<template>
  <div>
    <PageLayout_f>
      <template v-slot:pageTit>결제</template>
      <template v-slot:leftSlot>
        <CardLayout v-for="(table, index) in leftSlots" :key="index">
          <div class="inner_layout lt">
            <component :is="table" />
          </div>
        </CardLayout>
        <CardLayout>
          <div class="inner_layout lt">
            <component :is="table" />
          </div>
        </CardLayout>
      </template>
      <template v-slot:rightSlot>
        <CardLayout v-for="(table, index) in rightSlots" :key="index">
          <div class="inner_layout">
            <component :is="table" />
          </div>
        </CardLayout>
        <CardLayout>
          <div class="inner_layout">
            <component :is="table" />
          </div>
        </CardLayout>
      </template>
    </PageLayout_f>
  </div>
</template>
<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from "vue";
import PageLayout_f from "src/layouts/web/common/PageLayout_f.vue";
import CardLayout from "src/layouts/web/common/CardLayout.vue";
import PaymentTable_1a from "src/components/web/payment/PaymentTable_a.vue";
import PaymentTable_2a from "src/components/web/payment/PaymentTable_b.vue";
import PaymentTable_3a from "src/components/web/payment/PaymentTable_c.vue";
import PaymentTable_4a from "src/components/web/payment/PaymentTable_d.vue";

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
    ? [PaymentTable_2a, PaymentTable_1a] // 작은 화면에서 순서 변경
    : [PaymentTable_2a, PaymentTable_1a]; // 기본 순서
});

// 오른쪽 슬롯
const rightSlots = computed(() => {
  return isSmallScreen.value
    ? [PaymentTable_3a, PaymentTable_4a] // 작은 화면에서 순서 변경
    : [PaymentTable_3a, PaymentTable_4a]; // 기본 순서
});
</script>
<style lang="scss" scoped></style>

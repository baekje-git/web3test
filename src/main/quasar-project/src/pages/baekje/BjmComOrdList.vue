<template>
  <div>
    <HeaderLayout />
    <OrderLayout>
      <router-view />
    </OrderLayout>
  </div>
</template>
<script setup>
import OrderLayout from "/src/layouts/baekje/OrderLayout.vue";
import HeaderLayout from "/src/layouts/baekje/BjmHeaderLayout.vue";
</script>
<style lang="scss" scoped></style>

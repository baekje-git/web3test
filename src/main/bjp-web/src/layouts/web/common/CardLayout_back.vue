<template>
  <div class="CardLayout_wrap">
    <slot></slot>
  </div>
</template>
<script setup></script>
<style lang="scss" scoped>
.CardLayout_wrap {
  @include boxshadow;
  // width: 100%;
  margin-bottom: 40px;
  padding: 20px;
  border-radius: 10px;
}
.CardLayout_wrap:last-child {
  margin-bottom: 0;
}
.card_header_main {
  @include flex_start;
  gap: 8px;
}
</style>

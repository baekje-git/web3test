<template>
  <div class="CardLayout_wrap">
    <slot></slot>
  </div>
</template>
<script setup></script>
<style lang="scss" scoped>
.CardLayout_wrap {
  width: 100%;
  padding: 10px 0;
  &::after {
    content: "";
    clear: both;
    display: block;
  }
}
.left_left {
  .CardLayout_wrap + .CardLayout_wrap::before {
    width: calc(100% + #{rem(30px)});
  }
}
.CardLayout_wrap + .CardLayout_wrap::before {
  content: "";
  display: block;
  margin: rem(28px) 0;
  width: 100%;
  height: 12px;
  background-color: #f8f8f8;
}
@media screen and (min-width: 831px) {
  .left_left .CardLayout_wrap:nth-last-child(1)::before,
  .left_right .CardLayout_wrap:nth-last-child(1)::before {
    display: none;
  }
}
@media screen and (max-width: 830px) {
  .left_left {
    .CardLayout_wrap + .CardLayout_wrap::before {
      width: 100%;
    }
  }
  .left_right .CardLayout_wrap:nth-last-child(1)::before {
    display: none;
  }
}
.card_header_main {
  @include flex_start;
  gap: 8px;
}
</style>

<template>
  <div class="Btn_options_wrap">
    <q-btn class="btn_options" label="당월" dense />
    <q-btn class="btn_options" label="전월" dense />
    <q-btn class="btn_options" label="3개월" dense />
    <q-btn class="btn_options" label="6개월" dense />
    <q-btn class="btn_options" label="1년" dense />
    <q-btn class="btn_options" label="2년" dense />
    <q-btn class="btn_options" label="3년" dense />
  </div>
</template>

<script setup></script>

<style lang="scss">
.Btn_options_wrap .q-btn {
  @include flex_center;
  padding: 0 5px;
  width: 60px;
  height: 32px;
  color: $gray-9 !important;
  background-color: #fff;
  background-image: none !important;
  border: 1px solid $line-400;
  border-radius: 0;
  transition: none;
  &::before {
    display: none;
  }
  &:nth-child(1) {
    padding-left: 12.5px;
    width: 70px;
    border-radius: 15px 0 0 15px !important;
  }
  &:nth-last-child(1) {
    padding-right: 12.5px;
    width: 70px;
    border-radius: 0 15px 15px 0 !important;
  }
  span {
    @include fs-5;
    line-height: 1em;
  }
}
.Btn_options_wrap {
  @include flex_center;
  gap: 0;
  .q-btn + .q-btn {
    border-left: none;
  }
  .btn,
  .btn_active,
  .btn_options:active,
  .btn_options:hover,
  .btn_options:focus {
    color: #fff !important;
    background-color: $primary;
    border-top-color: $primary;
    border-bottom-color: $primary;
    &:nth-child(1),
    &:nth-last-child(1) {
      border-color: $primary;
    }
  }
}
</style>

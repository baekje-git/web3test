<template>
  <div class="Btn_active_wrap">
    <q-btn class="btn_active" :label="btnLabel" dense>
      <slot></slot>
    </q-btn>
  </div>
</template>

<script>
export default {
  props: {
    btnLabel: {
      type: String,
      default: "버튼",
    },
  },
  data() {
    return {};
  },
};
</script>

<style lang="scss">
.btn_active {
  background-color: #999999;
  border-radius: 5px !important;
  padding: 0 16px;
}
.btn_active span {
  @include fs-5;
  color: #ffffff !important;
}
.btn_active:active,
.btn_active:hover,
.btn_active:focus {
  @include gradation;
  border-radius: 5px;
  color: $w;
  padding: 0 16px;
}
</style>

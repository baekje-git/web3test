<template>
  <div class="Btn_wrap">
    <q-btn class="btn" :label="btnLabel" dense />
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
.btn {
  @include gradation;
  border-radius: 5px !important;
  color: $w;
  padding: 0 16px;
}
.btn span {
  @include fs-5;
  color: #ffffff !important;
}
</style>

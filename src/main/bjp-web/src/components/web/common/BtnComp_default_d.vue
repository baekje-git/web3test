<template>
  <div class="Btn_wrap">
    <q-btn class="btn_default_d" :label="btnLabel" dense :disable="disable" />
  </div>
</template>

<script>
export default {
  props: {
    btnLabel: {
      type: String,
      default: "버튼",
    },
    disable: {
      type: Boolean,
      default: false,
    },
    btnStyle: {
    type: String,
    default: 'btn',
  },
  },
  data() {
    return {};
  },
};
</script>

<style lang="scss">
.btn_default_d {
  background-color: #999999;
  border-radius: 5px !important;
  padding: 0 16px;
  height: 24px;
}
.btn_default_d span {
  @include fs-5;
  color: #ffffff !important;
}
.btn_default_d:active,
.btn_default_d:hover,
.btn_default_d:focus {
  @include gradation;
  border-radius: 5px;
  color: $w;
  padding: 0 16px;
}

</style>

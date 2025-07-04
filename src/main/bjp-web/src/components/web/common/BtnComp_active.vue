<template>
  <div class="Btn_active_wrap">
    <q-btn class="btn_active" :label="btnLabel" dense :class="btnStyle">
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
    btnStyle: {
      type: String,
      default: "btn",
    },
  },
  data() {
    return {};
  },
};
</script>

<style lang="scss">
.btn_active {
  padding: 0 16px;
  height: 32px;
  color: #fff;
  background-color: $gray-9;
  background-image: none;
  border-radius: 0 !important;
  &::after,
  &::before {
    box-shadow: none;
    display: none;
  }
}

.btn_active {
  span {
    @include fs-5;
    line-height: 1;
  }
  .q-focus-helper {
    display: none;
  }
  // &:active,
  // &:hover,
  // &:focus {
  //   padding: 0 5px;
  // }
}

.btn-line {
  .q-btn {
    border-style: solid;
    border-width: 1px;
  }
  &.default .q-btn {
    background-color: #fff;
    border-color: $gray-a;
    span {
      color: $gray-1;
    }
  }
  &.dark .q-btn {
    background-color: $gray-3;
    border-color: $gray-3;
  }
}
.btn-fill {
  &.dark .q-btn {
    background-color: $gray-3;
    border-color: $gray-3;
  }
  &.gray .btn {
    background-color: $gray-a;
    border-color: $gray-a;
  }
}
</style>

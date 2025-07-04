<template>
  <div class="Btn_wrap">
    <q-btn
      :class="btnStyle"
      :label="btnLabel"
      dense
      :disable="disable"
      tabindex="-1"
    />
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
      default: "btn",
    },
  },
  data() {
    return {};
  },
};
</script>

<style lang="scss">
.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background-color: $primary;
  color: #fff;
  padding: 0 16px;
  height: 32px;
  border-radius: 0px;
  &::before,
  &::after {
    box-shadow: none !important;
  }
}
.btn-sm .q-btn {
  padding: 0 8px;
  height: 24px !important;
  min-height: auto !important;
  line-height: 1em;
}
.btn span {
  @include fs-5;
  line-height: 1;
  color: #fff;
}
.strong span {
  font-weight: 600;
}
.btn_default_d {
  background-color: $gray-9;
  padding: 0 5px;
  height: 24px;
}
.btn_default_d span {
  @include fs-5;
  color: #fff;
}
.btn_default_d:active,
.btn_default_d:hover,
.btn_default_d:focus {
  background-color: $gray-1;
  border-radius: 0px;
  color: $w;
  padding: 0 5px;
}

.btn-line {
  .btn {
    border-style: solid;
    border-width: 1px;
  }
  &.default .btn {
    background-color: #fff;
    border-color: $gray-a;
    span {
      color: $gray-1;
    }
  }
  &.dark .btn {
    background-color: $gray-3;
    border-color: $gray-3;
  }
}
.btn-fill {
  &.dark .btn {
    background-color: $gray-3;
    border-color: $gray-3;
  }
  &.gray .btn {
    background-color: $gray-a;
    border-color: $gray-a;
  }
}
.btn-pd5 .btn {
  padding: 0 5px;
}
.btn-pd10 .btn {
  padding: 0 10px;
}
.btn-pd12 .btn {
  padding: 0 12px;
}
</style>

<template>
  <div>
    <q-btn
      :class="[btnCommon, btnClass]"
      :size="btnSize"
      :align="btnAlign"
      :icon-right="btnIcon"
      @click="$emit('btnClickEvent')"
      flat
      dense
      no-caps
      :disable="isDisable"
    >
      <div class="" style="line-height: normal">
        <span>{{ buttonText }}</span>
        <br />
        <span>{{ sub_buttonText }}</span>
      </div>
    </q-btn>
  </div>
</template>

<script setup>
import { ref, defineProps } from "vue";

// const emits = defineEmits(['btnClickEvent'])

const props = defineProps({
  btnCommon: {
    type: String,
    default: "btn-m",
  },
  btnStyle: {
    type: String,
    default: "defalut",
  },
  buttonText: {
    type: String,
    default: "버튼",
  },
  sub_buttonText: {
    type: String,
    default: "",
  },
  btnSize: {
    type: String,
    default: "sm",
  },
  btnAlign: {
    type: String,
    default: "center",
  },
  btnIcon: {
    type: String,
  },
  btnClickEvent: {
    type: String,
    default: "",
  },
  isDisable: {
    type: Boolean,
    default: false,
  },
});

const btnClass = ref("");

btnClass.value += ` btn-${props.btnStyle}`;
</script>

<style lang="scss">
.btn-m {
  display: inline-flex;
  align-items: center;
  padding: 0 10px;
  min-width: 50px;
  height: 30px;
  background-color: #fff;
  border-color: $gray-a;
  background-image: none;
  border-radius: 0;
  span {
    @include fs-5;
    font-weight: 500;
    color: $gray-1;
  }
}
.fw-mid span {
  @include fw-5;
}
.btn-default {
  background: $primary;
  border: none;
  span {
    color: #fff;
  }
}
.btn-white {
  background: #fff;
  border: none;
  span {
    color: #111;
  }
}
.btn-line {
  .btn-m {
    border-style: solid;
    border-width: 1px;
  }
  &.default .btn-m {
    background-color: #fff;
    border-color: $gray-a;
    span {
      color: $gray-1;
    }
  }
  &.dark .btn-m {
    background-color: $gray-3;
    border-color: $gray-3;
  }
}
.btn_srch .btn-m {
  height: 30px !important;
  background-color: $primary;
  span {
    color: #fff;
  }
}
.btn-full {
  .btn-m {
    width: 100%;
  }
}
.btn-icon {
  border-style: solid;
  border-width: 1px;
  background-color: #fff;
  border-color: $gray-a;
  span {
    color: $gray-1;
  }
  .q-icon {
    margin-left: 4px;
  }
}
.q-btn.disabled {
  opacity: 0.5 !important;
}
</style>

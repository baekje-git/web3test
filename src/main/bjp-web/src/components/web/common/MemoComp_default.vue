<template>
  <div class="">
    <slot></slot>
    <q-menu
      class="q-menu_wrap"
      fit
      anchor="bottom end"
      self="top right"
      style="width: 500px; height: 250px"
    >
      <p>{{ memo_btnLabel }}</p>

      <q-input
        class="input_textarea"
        v-model="textarea"
        type="textarea"
        borderless
        autogrow
        style="height: 165px"
        :disable="isDisable"
      />
      <div class="q-textarea_btn_wrap">
        <q-btn
          v-if="isVisible"
          class="q-textarea_btn_default"
          style="margin-right: 8px"
          label="수정"
          dense
          @click="emit('update:modelValue', textarea)"
        />
        <BtnComp_default btn-label="닫기" class="btn" v-close-popup />
      </div>
    </q-menu>
  </div>
</template>
<script setup>
import { ref, defineProps, onMounted } from "vue";

import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";

const emit = defineEmits(["update:modelValue"]);

const textarea = ref("");

const props = defineProps({
  memo_btnLabel: {
    type: String,
    default: "메모",
  },
  memo: {
    type: String,
    default: "",
  },
  isVisible: {
    type: Boolean,
    default: false,
  },
  isDisable: {
    type: Boolean,
    default: false,
  },
});

onMounted(() => {
  textarea.value = props.memo;
});
</script>
<style lang="scss">
.q-menu_wrap {
  width: 240px;
  padding: 8px !important;
}
.q-menu_wrap p {
  @include fw-7;
}
.input_textarea {
  margin: 8px 0;
  border: 1px solid $line-400;
}
.input_textarea textarea {
  height: 85px !important;
  padding: 8px !important;
  cursor: default !important;
}
.q-textarea_btn_wrap {
  @include flex_end;
  .q-btn {
    color: #fff;
    &::before,
    &::after {
      box-shadow: none !important;
    }
    .q-focus-helper {
      display: none !important;
    }
  }
  .btn {
    height: 28px;
  }
  > .btn {
    padding: 0;
  }
}
.q-textarea_btn_default {
  padding: 0 16px;
  color: #fff;
  background-color: $gray-a;
  border-color: $gray-a;
  border-radius: 0;
}
.q-textarea_btn_default span {
  @include fs-5;
}
</style>

<template>
  <div class="DialogLayout_wrap">
    <q-dialog
      :maximized="dialog_full"
      :transition-show="dialog_show"
      :transition-hide="dialog_hide"
      :position="dialog_position"
      :model-value="dialog_model_prop"
      @update:model-value="$emit('update:dialog_model_prop', $event)"
    >
      <div
        class="Dialog_wrap"
        style="background-color: #fff"
        :class="dialog_style"
      >
        <div class="Dialog_header">
          <span>{{ dialog_title }}</span>
          <q-btn v-if="closeBtnVisible" dense flat icon="close" v-close-popup />
        </div>
        <div class="Dialog_section">
          <slot></slot>
        </div>
      </div>
    </q-dialog>
  </div>
</template>
<script setup>
import { defineProps, inject, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";

const emits = defineEmits(["update:dialog_model_prop"]);

// const bus = inject("bus");
//팝업창 닫을 때 이벤트 발상
// const closeDialogPopup = () => {
//   bus.emit("closeDialogPopup");
// };

const props = defineProps({
  dialog_title: {
    type: String,
    default: "TITLE",
  },
  dialog_model_prop: {
    type: Boolean,
  },
  dialog_full: {
    type: Boolean,
    default: true,
  },
  dialog_show: {
    type: String,
    default: "slide-up",
  },
  dialog_hide: {
    type: String,
    default: "slide-down",
  },
  dialog_position: {
    type: String,
    default: "standard",
  },
  dialog_style: {
    type: String,
    default: "",
  },
  closeBtnVisible: {
    type: Boolean,
    default: true,
  },
});

const router = useRouter();
let removeGuard;

const closeDialog = () => {
  emits("update:dialog_model_prop", false);
};

onMounted(() => {
  removeGuard = router.beforeEach((to, from, next) => {
    if (props.dialog_model_prop) {
      closeDialog();
      next(false);
    } else {
      next();
    }
  });
});

onBeforeUnmount(() => {
  if (removeGuard) {
    removeGuard();
  }
});
</script>

<style lang="scss" scoped></style>

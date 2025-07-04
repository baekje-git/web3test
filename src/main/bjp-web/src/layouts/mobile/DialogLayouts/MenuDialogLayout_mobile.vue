<template>
  <div class="DialogLayout_wrap">
    <q-dialog
      :model-value="dialog_model_prop"
      :maximized="dialog_full"
      :transition-show="dialog_show"
      :transition-hide="dialog_hide"
      :position="dialog_position"
      @update:model-value="$emit('update:dialog_model_prop', $event)"
    >
      <div class="Dialog_wrap" :class="dialog_style">
        <div class="Dialog_header">
          <span>{{ dialog_title }}</span>
          <q-btn dense flat icon="close" v-close-popup />
        </div>
        <div class="Dialog_section">
          <slot></slot>
        </div>
      </div>
    </q-dialog>
  </div>
</template>
<script setup>
import { defineProps } from "vue";

const emits = defineEmits(["update:dialog_model_prop"]);

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
});
</script>

<style lang="scss" scoped></style>

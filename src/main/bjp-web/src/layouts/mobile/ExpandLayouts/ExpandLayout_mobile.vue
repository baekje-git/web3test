<template>
  <div class="ExpandLayout_wrap">
    <q-expansion-item
      :model-value="expanded_model"
      switch-toggle-side
      expand-icon="img:assets/icons/angles-down-solid.svg"
      expand-icon-toggle
      hideExpandIcon
      @update:model-value="$emit('update:expanded_model', $event)"
    >
      <template v-slot:header>
        <div class="Expand_head">
          <div class="">
            <p>{{ expand_title }}</p>
          </div>
          <BtnComp_mobile_a
            btnStyle="line_default"
            class="line_btm-none"
            :buttonText="expand_btn"
            :style="{ width: `${expand_btnWidth}px` }"
            @click="expanded_btn"
          />
        </div>
      </template>
      <div class="Expand_container">
        <slot></slot>
      </div>
    </q-expansion-item>
  </div>
</template>

<script setup>
import { ref, defineProps } from "vue";
import BtnComp_mobile_a from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile_a.vue";

const emits = defineEmits(["update:expanded_model"]);
const expanded_btn = () => {
  emits("expanded_btnClick");
};

const props = defineProps({
  expanded_model: {
    type: Boolean,
  },
  expand_title: {
    type: String,
    default: "Title",
  },
  expand_btn: {
    type: String,
    default: "버튼",
  },
  expand_btnWidth: {
    type: Number,
  },
  expanded_btnClick: {
    type: String,
  },
});
</script>

<style lang="scss" scoped></style>

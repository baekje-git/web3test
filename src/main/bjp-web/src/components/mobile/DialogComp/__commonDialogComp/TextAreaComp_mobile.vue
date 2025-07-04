<template>
  <div class="TextArea_wrap" style="padding: 0 20px">
    <slot></slot>
    <div class="" style="height: 240px">
      <q-input
        v-if="isVisible"
        class="TextArea"
        v-model="dataParam.bakMemo"
        type="textarea"
        placeholder="메모를 입력해 주세요."
        dense
        borderless
      />
      <q-input
        v-else
        class="TextArea"
        v-model="dataParam.bakMemo"
        type="textarea"
        placeholder="메모를 입력해 주세요."
        dense
        borderless
        readonly
      />
    </div>
    <div class="TextArea_btn_wrap">
      <div class="">
        <BtnComp_mobile
          v-if="isVisible"
          :buttonText="submit"
          btnStyle="default"
          btnSize="lg"
          @click="updateBakMemo"
        />
      </div>
      <div class="">
        <BtnComp_mobile
          v-if="isVisible"
          buttonText="취소"
          btnStyle="white"
          btnSize="lg"
          class="btn-line default"
          v-close-popup
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from "vue";
import { api } from "boot/axios";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";

const emit = defineEmits(["updateMemo"]);

const props = defineProps({
  submit: {
    type: String,
    default: "버튼",
  },
  memo: {
    type: String,
    default: "",
  },
  sn: {
    type: String,
    default: "",
  },
  isVisible: {
    type: Boolean,
    default: false,
  },
  flag: {
    type: String,
    default: "",
  },
});

const dataParam = ref({
  bakMemo: props.memo,
  sn: props.sn,
});

async function updateBakMemo() {
  if (props.flag === "ordReg") {
  } else {
    // await api.put("/bakReq/saveBakMemo", dataParam.value);
  }

  emit("updateMemo", dataParam.value.bakMemo);
}
</script>

<style lang="scss" scoped></style>

<template>
    <div class="TextArea_wrap">
      <slot></slot>
      <div class="">
        <q-input v-if="isVisible"
          class="TextArea"
          v-model="dataParam.bakMemo"
          type="textarea"
          placeholder="메모를 입력해 주세요."
          dense
          borderless
        />
        <q-input v-else
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
            @click="updateBakMemo()"
          />
        </div>
        <div class="">
          <BtnComp_mobile
            v-if="isVisible"
            buttonText="취소"
            btnStyle="white"
            btnSize="lg"
            v-close-popup
          />
        </div>
      </div>
    </div>
</template>

<script setup>
import { ref , defineProps} from 'vue';
import { api } from "boot/axios";
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';

const props = defineProps({
  submit: {
    type: String,
    default
    : '버튼'
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
});

const dataParam = ref({
  bakMemo : props.memo,
  sn : props.sn
});

async function updateBakMemo() {
  await api.put("/bakReq/saveBakMemo", dataParam.value);
}
</script>

<style lang="scss" scoped>


</style>

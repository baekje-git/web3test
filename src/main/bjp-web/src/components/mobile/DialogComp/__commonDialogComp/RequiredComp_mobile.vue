<template>
  <div class="Required_wrap">
    <q-dialog
      :model-value="dialog_required_model"
      @update:model-value="$emit('update:dialog_required_model', $event)"
      persistent
    >
      <div class="Required_container">
        <div>
          <div class="Required_text">
            <p>{{ Required_text }}을 입력해주세요</p>
          </div>
          <div class="Required_btn">
            <BtnComp_mobile
              buttonText="확인"
              btnStyle="white"
              btnSize="lg"
              class="btn-line default"
              v-close-popup
            />
          </div>
        </div>
      </div>
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, defineProps } from "vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";

const emits = defineEmits(["update:dialog_required_model"]);

const props = defineProps({
  dialog_required_model: {
    type: Boolean,
  },
  Required_text: {
    type: String,
    default: "TEXT",
  },
});
</script>

<style lang="scss" scoped>
.Required_wrap {
  .Delelte_btn {
    @include flex_center;
  }
}
.Required_container {
  @include border;
  @include boxshadow;
  padding: 24px;
  width: 100%;
  background-color: $w;
  border-radius: 10px;
  .Required_text {
    text-align: center;
    p {
      // @include fs-5;
      font-size: 16px;
      @include fw-7;
    }
  }
  .Required_btn {
    margin-top: 24px;
  }
}
</style>

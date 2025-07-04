<template>
  <div class="Delete_wrap">
    <q-btn class="Delelte" size="sm" flat dense @click="delete_model = true">
      <slot></slot>
    </q-btn>

    <q-dialog v-model="delete_model" persistent class="Delete_dialog">
      <div class="Delete_container">
        <div>
          <div class="Delete_text">
            <p>{{ delete_text1 }} 목록에서 <span>삭제</span>됩니다.</p>
            <p>
              <span>{{ delete_text2 }}</span> 하시겠습니까?
            </p>
          </div>
          <div class="Delete_btn">
            <BtnComp_mobile
              buttonText="취소"
              btnStyle="white"
              btnSize="lg"
              class="btn-line default"
              v-close-popup
            />
            <BtnComp_mobile
              :buttonText="delete_btn"
              btnStyle="red"
              btnSize="lg"
              class="btn-line default"
              @click="onClickDelete"
            />
          </div>
        </div>
      </div>
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, defineProps, inject, defineEmits, watch } from "vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";

const bus = inject("bus");
const emit = defineEmits(["deleteBasket"]);

const delete_model = ref(false);

const props = defineProps({
  delete_text1: {
    type: String,
    default: "TEXT",
  },
  delete_text2: {
    type: String,
    default: "삭제",
  },
  delete_btn: {
    type: String,
    default: "삭제",
  },
  dataRow: {
    type: Array,
  },
  callFunction: {
    type: String,
    default: "",
  },
});

async function onClickDelete() {
  emit("deleteDetlBasket", props.dataRow);
  delete_model.value = false;
}
</script>

<style lang="scss" scoped>
.Delete_wrap {
  .Delelte {
    @include flex_center;
  }
}

.Delete_dialog {
  // .absolute-full,
  // .fullscreen,
  // .fixed-full {
  // }

  .Delete_container {
    // @include border;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    // width: 100%;
    width: 80vw;
    height: 140px;
    padding: 24px;
    background-color: $w;
    border-radius: 10px;
    @include boxshadow;
    .Delete_text {
      text-align: center;
      p {
        // @include fs-5;
        // line-height: 18px;
        font-size: 16px;
        span {
          // @include fs-5;
          // @include fw-7;
          color: $red-400;
          vertical-align: baseline;
        }
      }
    }
    .Delete_btn {
      @include flex_center;
      margin-top: 24px;
      gap: 12px;
    }
  }
}
</style>

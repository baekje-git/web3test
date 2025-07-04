<template>
  <div
    class="btn-group-wrap"
    :style="{ maxWidth: maxWidth + '%' }"
    ref="buttonGroupWrap"
  >
    <div class="button-container" ref="buttonContainer">
      <q-btn
        v-for="buttonText in buttons"
        :key="buttonText.title"
        :class="[
          btnCommon,
          btnClass,
          { active: isActive === buttonText.title },
        ]"
        :size="btnSize"
        flat
        dense
        style="min-width: 74px; line-height: normal"
        @click="onClickBtn(buttonText.title)"
      >
        <span>{{ buttonText.title }} {{ buttonText.sub.value }} </span>
      </q-btn>
    </div>
  </div>
</template>

<script setup>
import {
  ref,
  defineProps,
  onMounted,
  onUpdated,
  defineEmits,
  inject,
} from "vue";
const bus = inject("bus");

const emit = defineEmits(["onClickBtn"]);

const props = defineProps({
  btnCommon: {
    type: String,
    default: "btn-common",
  },
  btnStyle: {
    type: String,
    default: "default",
  },
  buttons: {
    type: Array,
    default: () => [],
  },
  btnSize: {
    type: String,
    default: "sm",
  },
});

const btnClass = ref("");
btnClass.value += ` btn-${props.btnStyle}`;

const maxWidth = ref(344); // 기본값 설정

const buttonGroupWrap = ref(null);
const buttonContainer = ref(null);
const isActive = ref(props.buttons.length > 0 ? props.buttons[0].title : "");
onMounted(() => {
  updateButtonContainerWidth();
});

onUpdated(() => {
  updateButtonContainerWidth();
});

/**
 * 버튼이 추가되면(예, 관심그룹3) 화면 에러 발생하여,
 * 버튼 고정 너비를 64 -> 70으로 변경하니 해소됨
 */
function updateButtonContainerWidth() {
  if (buttonGroupWrap.value && buttonContainer.value) {
    const wrapWidth = buttonGroupWrap.value.clientWidth;
    const buttonCount = buttonContainer.value.children.length;
    const buttonWidth = 70; // 버튼의 고정 너비

    // 버튼 그룹의 너비가 버튼 너비보다 작으면 버튼이 너비를 초과하여 보여지지 않아야 함
    if (wrapWidth < buttonWidth * buttonCount) {
      buttonContainer.value.style.width = `${buttonWidth * buttonCount}px`;
      maxWidth.value = buttonWidth * buttonCount + 8 * (buttonCount - 1);
    } else {
      buttonContainer.value.style.width = "auto";
      maxWidth.value = 100; // 기본값으로 되돌림
    }
  }
}

// 버튼 클릭
function onClickBtn(val) {
  isActive.value = val === isActive.value ? "" : val;
  emit("onClickBtn", val);
}

bus.on("mobileBtnChk2", async (data) => {
  isActive.value = data;
});
</script>

<style lang="scss" scoped>
.btn-group-wrap {
  position: relative;
  @include flex_between;
  margin-top: 8px;
  width: 100%;
  gap: 0;
  padding-bottom: 1px;
  &::after {
    content: "";
    position: absolute;
    display: block;
    left: 0;
    bottom: 0;
    width: 100%;
    height: 1px;
    background-color: $primary;
    z-index: 1;
  }
}
.button-container {
  display: flex;
  gap: 0 !important;
  transition: width 0.3s ease;
  .q-btn__content {
    flex-direction: column;
  }
}
.btn-group-wrap .btn-common {
  position: relative;
  display: inline-flex;
  align-items: center;
  // min-width: 49%;
  padding: 8px 15px;
  height: auto;
  word-break: keep-all;
  border: none;
  background: #fff;
  border-radius: 0;
  z-index: 2;
  span {
    color: $gray-7;
  }
  &::before,
  &::after {
    content: "";
    position: absolute;
  }
  &::after {
    display: block;
    top: 100%;
    left: 0;
    width: 100%;
    height: 1px;
    background-color: $primary;
  }
}
.btn-group-wrap .btn-common + .btn-common::before {
  display: block;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 1px;
  height: 40%;
  background-color: $line-300;
}
.btn-group-wrap .btn-common.active {
  border: solid 1px $primary;
  border-bottom: none;
  span {
    @include fw-6;
    color: $primary;
  }
  &::after {
    height: 2px;
    background-color: #fff;
  }
}
</style>

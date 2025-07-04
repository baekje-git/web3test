<template>
  <div class="btn-group-wrap" :style="{ maxWidth: maxWidth + 'px' }" ref="buttonGroupWrap">
    <div class="button-container" ref="buttonContainer">
      <q-btn
        v-for="buttonText in buttons"
        :key="buttonText"
        :class="[btnCommon, btnClass]"
        :size="btnSize"
        flat
        dense
        style="min-width: 64px;"
      >
        <span>{{ buttonText }}</span>
      </q-btn>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, onMounted, onUpdated } from 'vue';

const props = defineProps({
  btnCommon: {
    type: String,
    default: 'btn-common',
  },
  btnStyle: {
    type: String,
    default: 'default',
  },
  buttons: {
    type: Array,
    default: () => [],
  },
  btnSize: {
    type: String,
    default: 'sm',
  },
});

const btnClass = ref('');
btnClass.value += ` btn-${props.btnStyle}`;

const maxWidth = ref(344); // 기본값 설정

const buttonGroupWrap = ref(null);
const buttonContainer = ref(null);

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
      buttonContainer.value.style.width = 'auto';
      maxWidth.value = 344; // 기본값으로 되돌림
    }
  }
}

</script>

<style lang="scss" scoped>

</style>

<template>
  <div
    class="btn-group-wrap"
    :style="{ maxWidth: maxWidth + 'px' }"
    ref="buttonGroupWrap"
  >
    <div class="button-container" ref="buttonContainer">
      <q-btn
        v-for="item in items"
        :key="item.title"
        :class="[
          btnCommon,
          btnClass,
          { 'btn-focus': isActiveBtn === item.value ? true : false },
        ]"
        :size="btnSize"
        flat
        dense
        style="min-width: 74px"
        @click="onClickPeriod(item.value)"
      >
        <span>{{ item.title }}</span>
      </q-btn>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, onMounted, onUpdated, inject, computed } from "vue";

const bus = inject("bus");

const props = defineProps({
  btnCommon: {
    type: String,
    default: "btn-common",
  },
  btnStyle: {
    type: String,
    default: "default",
  },
  btnSize: {
    type: String,
    default: "sm",
  },
  callFunction: {
    type: String,
    default: "onClickPeriod",
  },
});

const items = ref([
  { title: "당월", value: 0 },
  { title: "전월", value: 1 },
  { title: "3개월", value: 3 },
  { title: "6개월", value: 6 },
  { title: "1년", value: 12 },
  { title: "2년", value: 24 },
  { title: "3년", value: 36 },
]);

const btnClass = ref("");
btnClass.value += ` btn-${props.btnStyle}`;

const maxWidth = ref(344); // 기본값 설정

const buttonGroupWrap = ref(null);
const buttonContainer = ref(null);

const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

//기간표시
const isActiveBtn = ref(
  items.value[items.value.findIndex((obj) => obj.title == "전월")].value
);
//---------------------------- Function ------------------------------//

onMounted(() => {
  updateButtonContainerWidth();
});

onUpdated(() => {
  updateButtonContainerWidth();
});

function updateButtonContainerWidth() {
  if (buttonGroupWrap.value && buttonContainer.value) {
    const wrapWidth = buttonGroupWrap.value.clientWidth;
    const buttonCount = buttonContainer.value.children.length;
    const buttonWidth = 64; // 버튼의 고정 너비

    // 버튼 그룹의 너비가 버튼 너비보다 작으면 버튼이 너비를 초과하여 보여지지 않아야 함
    if (wrapWidth < buttonWidth * buttonCount) {
      buttonContainer.value.style.width = `${buttonWidth * buttonCount}px`;
      maxWidth.value = buttonWidth * buttonCount + 8 * (buttonCount - 1);
    } else {
      buttonContainer.value.style.width = "auto";
      maxWidth.value = 344; // 기본값으로 되돌림
    }
  }
}

function getYYYYMMDD(date) {
  return (
    date.getFullYear() +
    "." +
    (date.getMonth() + 1).toString().padStart(2, "0") +
    "." +
    date.getDate().toString().padStart(2, "0")
  );
}

function setDate(val) {
  if (!val) {
    val = 1;
  } else {
    val++;
  }

  const now = new Date();
  const year = now.getFullYear();
  const month = now.getMonth() + 1;
  const strtDt = new Date(year, month - val, 1);
  const endDt = new Date(year, month, 0);
  const date1 = getYYYYMMDD(strtDt);
  const date2 = getYYYYMMDD(endDt);

  const params = ref({
    startDt: date1.replace(match, ""),
    endDt: date2.replace(match, ""),
  });

  return params;
}

async function onClickPeriod(val) {
  isActiveBtn.value = val;
  const params = setDate(val);
  await bus.emit(`${props.callFunction}`, params);
}
</script>

<style lang="scss" scoped>
//기간 표시 스타일
.button-container {
  .btn-focus {
    // background-image: linear-gradient(to bottom, #1f3f82, #003853) !important;
    // color: #ffffff !important;
  }
}
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
  background: #fff;
  border: none;
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
.btn-group-wrap .btn-focus {
  border: 1px solid $primary;
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

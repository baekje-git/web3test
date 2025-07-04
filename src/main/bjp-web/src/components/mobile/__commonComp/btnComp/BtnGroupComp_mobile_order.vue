<template>
  <div class="btn-group-fix-wrap" ref="buttonGroupWrap">
    <div class="button-container-fix" ref="buttonContainer">
      <q-btn
        v-for="button in buttons"
        :key="button.title"
        :class="[btnCommon, btnClass, { active: isActive === button.title }]"
        :size="btnSize"
        flat
        dense
        no-caps
        style="min-width: 52px"
        @click="setActive(button.title)"
      >
        <div class="" style="line-height: normal">
          <span>{{ button.title }}</span>
          <br />
          <span>{{ button.sub.value }}</span>
        </div>
      </q-btn>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, inject } from "vue";

const emit = defineEmits(["onClickBtn"]);
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

const buttonGroupWrap = ref(null);
const buttonContainer = ref(null);

const isActive = ref(props.buttons.length > 0 ? props.buttons[0].title : "");

function setActive(buttonTitle) {
  isActive.value = buttonTitle === isActive.value ? "" : buttonTitle;
  emit("onClickBtn", buttonTitle);
}

bus.on("mobileBtnChk", async (data) => {
  isActive.value = data;
});
</script>

<style lang="scss" scoped>
.btn-group-fix-wrap {
  @include flex_between;
  margin-top: 8px;
  width: 100%;
  gap: 8px;
  padding-bottom: 1px;
}
.button-container-fix {
  display: flex;
  width: 100%;
  border-bottom: 1px solid $primary;
  transition: width 0.3s ease;
  .q-btn__content {
    flex-direction: column;
  }
}
.btn-group-fix-wrap .btn-common {
  display: inline-flex;
  align-items: center;
  padding: 8px 5px;
  height: auto;
  word-break: keep-all;
  background: #fff;
  border: none;
  border-radius: 0;
  span {
    color: $gray-7;
  }
  &::before,
  &::after {
    content: "";
    position: absolute;
  }
}
.btn-group-fix-wrap .btn-common + .btn-common::before {
  display: block;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 1px;
  height: 40%;
  background-color: $line-300;
}
.btn-group-fix-wrap .btn-common.active {
  border: solid 1px $primary;
  border-bottom: none;
  span {
    @include fw-6;
    color: $primary;
  }
  &::after {
    display: block;
    top: 100%;
    left: 0;
    width: 100%;
    height: 2px;
    background-color: #fff;
  }
}
</style>

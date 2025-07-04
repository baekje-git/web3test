<template>
  <div class="DatePickerComp_wrap">
    <div class="date_input_wrap">
      <p>
        {{ date }}
      </p>
    </div>
    <div class="date_btn_wrap">
      <q-btn class="calendar_icon" unelevated dense>
        <img src="assets/icons/calendar-days-solid.svg" alt="calendar" />
        <q-popup-proxy
          @before-show="updateProxy"
          cover
          transition-show="scale"
          transition-hide="scale"
        >
          <q-date v-model="proxyDate" :default-date="today">
            <div class="row items-center justify-end q-gutter-sm">
              <q-btn label="취소" color="primary" flat v-close-popup />
              <q-btn
                label="확인"
                color="primary"
                flat
                @click="save"
                v-close-popup
              />
            </div>
          </q-date>
        </q-popup-proxy>
      </q-btn>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const date = ref("2024/01/01");
const proxyDate = ref("2024/01/01");

const updateProxy = () => {
  proxyDate.value = date.value;
};

const save = () => {
  date.value = proxyDate.value;
};
</script>

<style lang="scss">
.DatePickerComp_wrap {
  @include flex_start;
  // gap: 8px;
  width: 136px;
  height: 32px;
  border: 1px solid $line-400;
  .q-input {
    width: 100%;
  }
  .q-field__native {
    width: 100%;
    border: none;
  }
}
.date_input_wrap,
.date_btn_wrap {
  display: inline-flex;
  align-items: center;
}
.date_input_wrap {
  padding: 0 4px;
  width: calc(100% - 30px);
  border-radius: 0;
}
.date_btn_wrap {
  position: relative;
  width: 30px;
  .q-btn {
    width: 100%;
    border-radius: 0;
  }
  &::before {
    content: "";
    position: absolute;
    top: 50%;
    left: -1px;
    transform: translateY(-50%);
    width: 1px;
    height: 12px;
    background-color: $line-400;
  }
}
// .date_btn_wrap .q-btn {
//   @include flex_center;
//   padding: 0 13px;
//   height: 30px;
//   color: $gray-9;
//   background-color: #fff;
//   background-image: none !important;
//   border: 1px solid $line-400;
//   border-radius: 0;
//   &::before {
//     display: none;
//   }
//   span {
//     @include fs-5;
//   }
// }
</style>

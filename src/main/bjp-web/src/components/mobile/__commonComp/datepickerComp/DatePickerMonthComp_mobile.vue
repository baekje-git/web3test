<template>
  <div class="flex_between" style="gap: 8px">
    <div class="DatePickerComp_wrap">
      <div class="date_input_wrap">
        <q-input
          v-model="date"
          class="input-common input-default"
          :placeholder="placeholder"
          borderless
          dense
          flat
          mask="####-##"
          :disable="isDisable"
          @blur="validDate(date)"
          @keyup.enter="enter"
          type="search"
          ref="refInput"
        />
      </div>
      <div class="date_btn_wrap">
        <q-btn class="calendar_icon" unelevated dense style="padding: 0">
          <!-- <img src="/src/assets/icons/calendar-days-solid.svg" alt="calendar" /> -->
          <img src="assets/icons/calendar-solid.svg" alt="calendar" />
          <q-popup-proxy
            @before-show="updateProxy"
            cover
            transition-show="scale"
            transition-hide="scale"
          >
            <q-date
              v-model="proxyDate"
              default-view="Months"
              emit-immediately
              minimal
              mask="YYYY-MM"
              ref="dateref"
              :default-date="selectedDate || today"
            >
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
  </div>
</template>

<script setup>
import { ref, defineProps, defineModel, defineEmits } from "vue";
import { getToday } from "src/support/util";
import moment from "moment";

const date = defineModel("input_model_props");
const emits = defineEmits(["onChangeDate", "enter"]);

const props = defineProps({
  placeholder: {
    type: String,
    default: getToday("YYYY-MM"),
  },
  input_model_props: {
    type: String,
  },
  isDisable: {
    type: Boolean,
    default: false,
  },
});

const refInput = ref();

const enter = () => {
  refInput.value.blur();
  emits("enter");
};

const proxyDate = ref();

const updateProxy = () => {
  proxyDate.value = date.value;
};

const save = () => {
  date.value = proxyDate.value;
  emits("onChangeDate");
};

function validDate(val) {
  if (val) {
    const isValid1 = moment(val, "YYYY.MM", true).isValid();
    const isValid2 = moment(val, "YYYYMM", true).isValid();
    const isValid3 = moment(val, "YYYY/MM", true).isValid();
    const isValid4 = moment(val, "YYYY-MM", true).isValid();

    if (!isValid1 && !isValid2 && !isValid3 && !isValid4) {
      alert("유효하지 않은 날짜 형식입니다.");
      date.value = "";
      return;
    } else {
      emits("onChangeDate");
    }
  }
}

const dateref = ref(null);
const currentView = ref("Years");

function onUpdateMv(val) {
  currentView.value = currentView.value === "Years" ? "Months" : "Years";
  dateref.value.setView("Months");
}
</script>

<style lang="scss">
.DatePickerComp_wrap {
  @include flex_start;
  // gap: 8px;
  // width: 126px;
  width: 100%;
  height: 30px;
  border: 1px solid $line-400;
  .input-common {
    width: 100%;
    padding: 0 !important;
    border: none;
    .q-field__native {
      padding: 0 4px !important;
      letter-spacing: -0.05em;
    }
  }
}
.date_input_wrap,
.date_btn_wrap {
  display: inline-flex;
  align-items: center;
}
.date_input_wrap {
  width: calc(100% - 20px);
  border-radius: 0;
}
.date_btn_wrap {
  position: relative;
  width: 20px;
  .q-btn {
    width: 100%;
    border-radius: 0;
  }
  // &::before {
  //   content: "";
  //   position: absolute;
  //   top: 50%;
  //   left: -1px;
  //   transform: translateY(-50%);
  //   width: 1px;
  //   height: 12px;
  //   background-color: $line-400;
  // }
}
</style>

<template>
  <div class="flex_between">
    <div class="DatePickerComp_b_wrap">
      <div class="date_input_wrap">
        <p>
          {{ date1 }}
        </p>
      </div>
      <div class="date_btn_wrap">
        <q-btn class="calendar_icon" unelevated dense>
          <img src="assets/icons/calendar-days-solid.svg" alt="calendar" />
          <q-popup-proxy
            @before-show="updateProxy1"
            cover
            transition-show="scale"
            transition-hide="scale"
          >
            <q-date
              v-model="proxyDate1"
              :default-date="today"
              :title="selectedDate1"
              :subtitle="selectedYear1"
            >
              <div class="row items-center justify-end q-gutter-sm">
                <q-btn label="취소" color="primary" flat v-close-popup />
                <q-btn
                  label="확인"
                  color="primary"
                  flat
                  @click="save1"
                  v-close-popup
                />
              </div>
            </q-date>
          </q-popup-proxy>
        </q-btn>
      </div>
    </div>
    <p>~</p>
    <div class="DatePickerComp_b_wrap">
      <div class="date_input_wrap">
        <p>
          {{ date2 }}
        </p>
      </div>
      <div class="date_btn_wrap">
        <q-btn class="calendar_icon" unelevated dense>
          <img src="assets/icons/calendar-days-solid.svg" alt="calendar" />
          <q-popup-proxy
            @before-show="updateProxy2"
            cover
            transition-show="scale"
            transition-hide="scale"
          >
            <q-date
              v-model="proxyDate2"
              :default-date="today"
              :title="selectedDate2"
              :subtitle="selectedYear2"
            >
              <div class="row items-center justify-end q-gutter-sm">
                <q-btn label="취소" color="primary" flat v-close-popup />
                <q-btn
                  label="확인"
                  color="primary"
                  flat
                  @click="save2"
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
import { ref } from "vue";

// const date1 = ref("2024/01/01");
// const proxyDate1 = ref("2024/01/01");
// const date2 = ref("2024/01/02");
// const proxyDate2 = ref("2024/01/02");

// const updateProxy1 = () => {
//   proxyDate1.value = date1.value;
// };
// const save1 = () => {
//   date1.value = proxyDate1.value;
// };
// const updateProxy2 = () => {
//   proxyDate2.value = date2.value;
// };
// const save2 = () => {
//   date2.value = proxyDate2.value;
// };
const date1 = ref("");
const date2 = ref("");
const proxyDate1 = ref("");
const proxyDate2 = ref("");
// const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

const updateProxy1 = () => {
  proxyDate1.value = date1.value;
};
const save1 = () => {
  date1.value = proxyDate1.value;
};
const updateProxy2 = () => {
  proxyDate2.value = date2.value;
};
const save2 = () => {
  date2.value = proxyDate2.value;
};

// 오늘 날짜와 기본 날짜 설정 250307
const today = new Date();
// 캘린더 title 250307
const selectedDate1 = computed(() => {
  const date = new Date(proxyDate1.value);
  const month = date.getMonth() + 1; // 0-11에서 1-12로 변경
  const day = date.getDate();
  const weekday = date.toLocaleDateString("ko-KR", { weekday: "long" }); // 요일 가져오기

  return proxyDate1.value
    ? `${month}월 ${day}일 (${weekday[0].toUpperCase() + weekday.slice(3)})`
    : `${new Date(today).getMonth() + 1}월 ${new Date(today).getDate()}일 (${
        weekday[0].toUpperCase() + weekday.slice(3)
      })`;
});
// 캘린더 subtitle 250307
const selectedYear1 = computed(() => {
  return proxyDate1.value
    ? new Date(proxyDate2.value).getFullYear()
    : new Date(today).getFullYear();
});

// 캘린더 title 250307
const selectedDate2 = computed(() => {
  const date = new Date(proxyDate2.value);
  const month = date.getMonth() + 1; // 0-11에서 1-12로 변경
  const day = date.getDate();
  const weekday = date.toLocaleDateString("ko-KR", { weekday: "long" }); // 요일 가져오기

  return proxyDate2.value
    ? `${month}월 ${day}일 (${weekday[0].toUpperCase() + weekday.slice(3)})`
    : `${new Date(today).getMonth() + 1}월 ${new Date(today).getDate()}일 (${
        weekday[0].toUpperCase() + weekday.slice(3)
      })`;
});
// 캘린더 subtitle 250307
const selectedYear2 = computed(() => {
  return proxyDate2.value
    ? new Date(proxyDate2.value).getFullYear()
    : new Date(today).getFullYear();
});
</script>

<style lang="scss">
.flex_between {
  @include flex_between;
  gap: 8px;
}
.DatePickerComp_b_wrap {
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

  .input_default {
    padding: 4px 0;
  }
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
</style>

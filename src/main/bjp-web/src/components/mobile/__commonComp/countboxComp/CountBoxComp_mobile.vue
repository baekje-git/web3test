<template>
  <q-carousel
    v-model="slide"
    vertical
    :autoplay="3500"
    :infinite="true"
    :transition-duration="1500"
    transition-prev="slide-down"
    transition-next="slide-up"
    class="countbox_wrap"
    animated
  >
    <q-carousel-slide name="1" class="column no-wrap flex-center">
      <div class="countbox_wrap">
        <div
          :class="{
            countbox_common: true,
            'countbox-white': remainingTime1.hours !== '01',
            'countbox-red': remainingTime1.hours === '01',
          }"
        >
          <div>
            <img
              :src="
                remainingTime1.hours === '01'
                  ? 'assets/icons/truck-outline-white.svg'
                  : 'assets/icons/truck-outline.svg'
              "
              alt="icon"
            />
          </div>
          <div>
            <p>({{ dlvday }}) 1차 배송</p>
            <p>
              <!-- {{ remainingTime1.hours.padStart(2, "0") }}:{{
                remainingTime1.minutes.padStart(2, "0")
              }}:{{ remainingTime1.seconds.padStart(2, "0") }} -->
              {{ remainingTime1.hours }}:{{ remainingTime1.minutes }}:{{
                remainingTime1.seconds
              }}
              남음
            </p>
          </div>
        </div>
      </div>
    </q-carousel-slide>
    <q-carousel-slide name="2" class="column no-wrap flex-center">
      <div class="countbox_wrap">
        <div class="countbox_common countbox-white">
          <div>
            <img src="assets/icons/truck-outline.svg" alt="icon" />
          </div>
          <div>
            <p>({{ dlvday }}) 1차 배송</p>
            <p>
              ({{ endday }}) {{ remainingTime3.hours }}:{{
                remainingTime3.minutes
              }}
              남음
            </p>
          </div>
        </div>
      </div>
    </q-carousel-slide>
  </q-carousel>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";

const props = defineProps({
  currentDay: { type: String },
  currentTime: { type: String },
  endDay: { type: String },
  endTime: { type: String },
  countdown: { type: String },
  endday: { type: String },
  dlvday: { type: String },
});

const slide = ref("1");

// const Time1 = new Date().getTime() + 1000 * 60 * 60 * 24;
// const Time3 = new Date().getTime() + 1000 * 60 * 60 * 17;

// const remainingTime1 = ref(calculateRemainingTime(Time1));
// const remainingTime3 = ref(calculateRemainingTime(Time3));

const countdown = ref(props.countdown);

const remainingTime1 = ref({
  hours: "",
  minutes: "",
  seconds: "",
});

const remainingTime3 = ref({
  hours: "",
  minutes: "",
  seconds: "",
});

// function calculateRemainingTime(Time) {
//   const now = new Date().getTime();
//   const distance = Time - now;
//   const hours = Math.floor(distance / (1000 * 60 * 60))
//     .toString()
//     .padStart(2, "0");
//   const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60))
//     .toString()
//     .padStart(2, "0");
//   const seconds = Math.floor((distance % (1000 * 60)) / 1000)
//     .toString()
//     .padStart(2, "0");
//   return { hours, minutes, seconds };
// }

// onMounted(() => {
//   setInterval(() => {
//     remainingTime1.value = calculateRemainingTime(Time1);
//     remainingTime3.value = calculateRemainingTime(Time3);
//   }, 500);
// });

watch(props, () => {
  const timeParts = props.countdown.split(":");
  let seconds =
    parseInt(timeParts[2]) +
    60 * parseInt(timeParts[1]) +
    3600 * parseInt(timeParts[0]);

  const intervalId = setInterval(() => {
    if (seconds > 0) {
      seconds--;

      const hours = Math.floor(seconds / 3600);
      const minutes = Math.floor((seconds % 3600) / 60);
      const remainingSeconds = seconds % 60;

      countdown.value = `${String(hours).padStart(2, "0")}:${String(
        minutes
      ).padStart(2, "0")}:${String(remainingSeconds).padStart(2, "0")}`;

      remainingTime1.value.hours = String(hours).padStart(2, "0");
      remainingTime1.value.minutes = String(minutes).padStart(2, "0");
      remainingTime1.value.seconds = String(remainingSeconds).padStart(2, "0");

      remainingTime3.value.hours = String(hours).padStart(2, "0");
      remainingTime3.value.minutes = String(minutes).padStart(2, "0");
      remainingTime3.value.seconds = String(remainingSeconds).padStart(2, "0");
    } else {
      clearInterval(intervalId);
    }
  }, 1000);
});
</script>

<style lang="scss" scoped>
.countbox_on {
  display: block;
}
.countbox_off {
  display: none;
}

.countbox_wrap {
  width: 120px;
  // height: 42px;
  height: 45px;
  .countbox_common {
    @include flex_between;
    padding: 4px 3px;
    border-radius: 5px;
    div:nth-child(2) {
      p {
        @include fs-6;
        display: block;
        text-align: center;
        &:nth-child(1) {
          margin-bottom: 1px;
        }
        &:nth-child(2) {
          min-width: 77px;
        }
      }
    }
  }

  .countbox-red {
    background-color: $r1;
    p {
      color: $w;
      &:nth-child(2) {
        @include fw-7;
      }
    }
  }

  .countbox-white {
    @include countbox_shadow;
    background-color: $w;
    p {
      color: $b;
      &:nth-child(2) {
        color: $b3;
        @include fw-7;
      }
    }
  }
}
</style>

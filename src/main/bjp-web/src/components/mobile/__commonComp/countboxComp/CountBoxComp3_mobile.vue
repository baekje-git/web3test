<template>
  <q-carousel
    v-model="slide"
    vertical
    :infinite="true"
    :transition-duration="1500"
    transition-prev="slide-down"
    transition-next="slide-up"
    class="countbox_wrap"
    animated
  >
    <q-carousel-slide
      name="1"
      class="column no-wrap flex-center"
      style="padding: 16px 0"
    >
      <div class="countbox_wrap">
        <div class="countbox_common countbox-red">
          <div>
            <!-- <img src="/src/assets/icons/truck-outline-white.svg" alt="icon" /> -->
            <img src="assets/images/ico_delivery.svg" alt="icon" />
          </div>

          <div>
            <!-- <p>({{ dlvday }}) {{ dlvtime }}차 배송</p> -->
            <p>
              {{ remainingTime1.minutes }}:{{ remainingTime1.seconds }}
              남음
            </p>
          </div>
        </div>
      </div>
    </q-carousel-slide>
  </q-carousel>
</template>

<script setup>
import { ref, watch } from "vue";

const props = defineProps({
  currentDay: { type: String },
  currentTime: { type: String },
  endDay: { type: String },
  endTime: { type: String },
  countdown: { type: String },
  endday: { type: String },
  dlvday: { type: String },
  dlvtime: { type: String },
});

const slide = ref("1");

// const countdown = ref(props.countdown);

const remainingTime1 = ref({
  hours: "",
  minutes: "",
  seconds: "",
});

watch(
  () => props.countdown,
  (countdown) => {
    const timeParts = countdown.split(":");
    let seconds =
      parseInt(timeParts[2]) +
      60 * parseInt(timeParts[1]) +
      3600 * parseInt(timeParts[0]);

    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const remainingSeconds = seconds % 60;
    remainingTime1.value.hours = String(hours).padStart(2, "0");
    remainingTime1.value.minutes = String(minutes).padStart(2, "0");
    remainingTime1.value.seconds = String(remainingSeconds).padStart(2, "0");

    // const intervalId = setInterval(() => {
    //   if (seconds > 0) {
    //     seconds--;

    //     const hours = Math.floor(seconds / 3600);
    //     const minutes = Math.floor((seconds % 3600) / 60);
    //     const remainingSeconds = seconds % 60;
    //     remainingTime1.value.hours = String(hours).padStart(2, "0");
    //     remainingTime1.value.minutes = String(minutes).padStart(2, "0");
    //     remainingTime1.value.seconds = String(remainingSeconds).padStart(2, "0");
    //   } else {
    //     clearInterval(intervalId);
    //   }
    // }, 1000);
  }
);

// watch(props,() => {
//   const timeParts = props.countdown.split(":");
//   let seconds =
//     parseInt(timeParts[2]) +
//     60 * parseInt(timeParts[1]) +
//     3600 * parseInt(timeParts[0]);

//   const intervalId = setInterval(() => {
//     if (seconds > 0) {
//       seconds--;

//       const hours = Math.floor(seconds / 3600);
//       const minutes = Math.floor((seconds % 3600) / 60);
//       const remainingSeconds = seconds % 60;

//       countdown.value = `${String(hours).padStart(2, "0")}:${String(
//         minutes
//       ).padStart(2, "0")}:${String(remainingSeconds).padStart(2, "0")}`;

//       remainingTime1.value.hours = String(hours).padStart(2, "0");
//       remainingTime1.value.minutes = String(minutes).padStart(2, "0");
//       remainingTime1.value.seconds = String(remainingSeconds).padStart(2, "0");

//     } else {
//       clearInterval(intervalId);
//     }
//   }, 1000);
// });
</script>

<style lang="scss" scoped>
.countbox_on {
  display: block;
}
.countbox_off {
  display: none;
}

.countbox_wrap {
  // width: 120px;
  // height: 42px;
  height: 45px;
  .countbox_common {
    // @include flex_between;
    @include flex_end;
    padding: 4px 3px;
    gap: 8px;
    // border-radius: 5px;
    div:nth-child(2) {
      p {
        @include fs-5;
        display: block;
        // text-align: center;
        // &:nth-child(1) {
        //   margin-bottom: 1px;
        // }
        // &:nth-child(2) {
        //   min-width: 77px;
        // }
      }
    }
  }

  .countbox-red {
    // background-color: $r1;
    p {
      // color: $w;
      color: $red-400;
      @include fw-5;
      // &:nth-child(2) {
      //   @include fw-7;
      // }
    }
  }
}
</style>

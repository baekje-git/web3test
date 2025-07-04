<template>


    <q-carousel
      v-model="slide"
      vertical

      :autoplay="autoplay"
      :infinite="infinite"
      :transition-duration="duration"

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
              'countbox-white': remainingTime1.hours !== '23',
              'countbox-red': remainingTime1.hours === '23',
            }"
          >
            <div>
              <img
                :src="
                  remainingTime1.hours === '23'
                    ? 'src/assets/icons/truck-outline-white.svg'
                    : 'src/assets/icons/truck-outline.svg'
                "
                alt="icon"
              />
            </div>
            <div>
              <p>(평일) 1차 배송</p>
              <p>
                {{ remainingTime1.hours.padStart(2, "0") }}:{{
                  remainingTime1.minutes.padStart(2, "0")
                }}:{{ remainingTime1.seconds.padStart(2, "0") }} 남음
              </p>
            </div>
          </div>
        </div>
      </q-carousel-slide>
      <q-carousel-slide name="2" class="column no-wrap flex-center">
        <div class="countbox_wrap">
          <div class="countbox_common countbox-white">
            <div>
              <img src="src/assets/icons/truck-outline.svg" alt="icon" />
            </div>
            <div>
              <p>(평일) 1차 배송</p>
              <p>
                (월) {{ remainingTime3.hours.padStart(2, "0") }}:{{
                  remainingTime3.minutes.padStart(2, "0")
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
import { ref, onMounted } from "vue";

const slide = ref('1');
const autoplay = ref(3000);
const duration = ref(1500);
const infinite = ref(true);


const Time1 = new Date().getTime() + 1000 * 60 * 60 * 24;
const Time3 = new Date().getTime() + 1000 * 60 * 60 * 17;

const remainingTime1 = ref(calculateRemainingTime(Time1));
const remainingTime3 = ref(calculateRemainingTime(Time3));

function calculateRemainingTime(Time) {
  const now = new Date().getTime();
  const distance = Time - now;

  const hours = Math.floor(distance / (1000 * 60 * 60))
    .toString()
    .padStart(2, "0");
  const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60))
    .toString()
    .padStart(2, "0");
  const seconds = Math.floor((distance % (1000 * 60)) / 1000)
    .toString()
    .padStart(2, "0");

  return { hours, minutes, seconds };
}

onMounted(() => {
  setInterval(() => {
    remainingTime1.value = calculateRemainingTime(Time1);
    remainingTime3.value = calculateRemainingTime(Time3);
  }, 500);
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
  height: 42px;
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

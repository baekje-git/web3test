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
        <div class="countbox_common countbox-white">
          <div>
            <!-- <img src="/src/assets/icons/truck-outline-white.svg" alt="icon" /> -->
            <img src="assets/images/ico_delivery.svg" alt="icon" />
          </div>
          <div class="time" style="color: white; font-size: 8pt">
            ({{ dlvday }}) {{ dlvtime }}차 배송 ({{ endday }})
            {{ endTime.slice(0, 5) }} 주문마감
          </div>
        </div>
      </div>
    </q-carousel-slide>
  </q-carousel>
</template>

<script setup>
import { ref } from "vue";

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

  .countbox-white {
    // @include countbox_shadow;
    // background-color: $w;
    p {
      // color: $b;
      color: #f6f7b7;
      @include fw-5;
      // &:nth-child(2) {
      //   color: $b3;
      //   @include fw-7;
      // }
    }
  }
}
</style>

<template>
  <div class="CountBoxComp_wrap">
    <div class="box_left">
      <div class="clock_wrap">
        <img src="assets/icons/clock-solid.svg" alt="clock" />
      </div>
      <p class="left_text">광역</p>
    </div>
    <div class="box_right">
      <p>{{ countdown }}</p>
      <p>익일오후배송</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

const countdown = ref("04:00:00");

onMounted(() => {
  const timeParts = countdown.value.split(":");
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
    } else {
      clearInterval(intervalId);
    }
  }, 1000);
});
</script>

<style lang="scss" scoped>
.CountBoxComp_wrap {
  @include flex_start;
  width: 122px;
  height: 46px;
  padding: 0 8px;
  border-radius: 5px;
  background-color: $w;
  gap: 8px;
}
.CountBoxComp_wrap p {
  text-align: center;
}
.clock_wrap {
  @include flex_center;
}
.box_left p:nth-child(2),
.box_right p:nth-child(1) {
  @include fs-5;
  @include fw-7;
  color: $b;
}
.box_right p:nth-child(2) {
  @include fs-5;
  color: $b3;
}
</style>

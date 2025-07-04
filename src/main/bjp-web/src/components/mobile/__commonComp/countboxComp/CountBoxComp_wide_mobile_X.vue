<template>
  <div class="CountBox_wrap">
    <div class="countbox">
      <div class="countbox_top">
        <span>광역</span>
        <span>{{ countdown }}</span>
      </div>
      <div class="countbox_bottom">
        <span>익일오후배송</span>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';

const countdown = ref('04:00:00');

onMounted(() => {
  const timeParts = countdown.value.split(':');
  let seconds = parseInt(timeParts[2]) + 60 * parseInt(timeParts[1]) + 3600 * parseInt(timeParts[0]);

  const intervalId = setInterval(() => {
    if (seconds > 0) {
      seconds--;


      const hours = Math.floor(seconds / 3600);
      const minutes = Math.floor((seconds % 3600) / 60);
      const remainingSeconds = seconds % 60;

      countdown.value = `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(remainingSeconds).padStart(2, '0')}`;
    } else {
      clearInterval(intervalId);
    }
  }, 1000);
});
</script>
<style lang="scss" scoped>

</style>

<template>
  <div :class="['delivery', isRemain1Hour ? 'red-box' : 'blue-box']">
    <div class="ico">
      <img src="assets/images/ico_delivery.svg" />
    </div>
    <div
      class="time"
      v-if="!isRemain1Hour"
      style="color: white; font-size: 10pt"
    >
      ({{ dlvday }}) {{ dlvtime }}차 배송 ({{ endday }})
      {{ displayText }} 주문마감
    </div>
    <div class="time" v-else style="font-size: 10pt">
      ({{ dlvday }}) {{ dlvtime }}차 배송 주문 {{ formattedCountdown }} 남음
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from "vue";
import moment from "moment";

const props = defineProps({
  endTime: { type: String },
  endday: { type: String },
  dlvday: { type: String },
  dlvtime: { type: String },
});

const isRemain1Hour = ref(false);
const countdownSeconds = ref(0);
const formattedCountdown = ref("--:--");
const displayText = ref("--:--");
let timer = null;

function updateCountdown() {
  const now = moment();
  const deadline = moment(`${now.format("YYYY-MM-DD")} ${props.endTime}:00`);
  const countdownStart = deadline.clone().subtract(1, "hour");

  const diffToDeadline = deadline.diff(now, "seconds");
  const diffToStart = countdownStart.diff(now, "seconds");

  if (diffToDeadline <= 0) {
    clearInterval(timer);
    displayText.value = props.endTime;
    isRemain1Hour.value = false;
    return;
  }

  if (diffToStart > 0) {
    displayText.value = props.endTime;
    isRemain1Hour.value = false;
    return;
  }

  isRemain1Hour.value = true;
  countdownSeconds.value = diffToDeadline;
  updateFormattedCountdown();
}

function updateFormattedCountdown() {
  const min = String(Math.floor(countdownSeconds.value / 60)).padStart(2, "0");
  const sec = String(countdownSeconds.value % 60).padStart(2, "0");
  formattedCountdown.value = `${min}:${sec}`;
}

function runTimer() {
  timer = setInterval(() => {
    if (isRemain1Hour.value && countdownSeconds.value > 0) {
      countdownSeconds.value--;
      updateFormattedCountdown();
    } else {
      updateCountdown();
    }
  }, 1000);
}

onMounted(() => {
  updateCountdown();
  runTimer();
});

onBeforeUnmount(() => {
  clearInterval(timer);
});
</script>

<style lang="scss" scoped>
.delivery {
  display: flex;
  align-items: center;
  margin-left: 20px;
  padding: 0 18px;
  height: 32px;
  border-radius: 4px;
  .ico {
    height: 24px;
    img {
      height: 100%;
    }
  }
  .time {
    padding-left: 6px;
    font-size: 14px;
    font-weight: 600;
  }
}

.red-box {
  background-color: #e53935;
  .time {
    color: #fff;
  }
}

.blue-box {
  background-color: #17328f;
  .time {
    color: #fff;
  }
}
</style>

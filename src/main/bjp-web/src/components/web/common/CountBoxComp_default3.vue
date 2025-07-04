<template>
  <div v-if="!hideBox" :class="isRedBox ? 'delivery' : 'delivery2'">
    <div class="ico" style="width: 25px">
      <img src="assets/images/ico_delivery.svg" />
    </div>
    <div></div>
    <div
      class="time"
      :style="{
        fontSize: '10pt',
        width: isRedBox ? '200px' : 'auto',
        color: '#fff',
      }"
    >
      <div v-if="displayText === '배송정보만료(F5로 새로고침)'">
        <a
          href="#"
          @click.prevent="refreshPage"
          style="color: #fff; text-decoration: underline; cursor: pointer"
        >
          배송정보만료(F5로 새로고침)
        </a>
      </div>
      <div v-else-if="isRedBox">
        ({{ dlvday }}) {{ dlvtime }}차 배송 주문 {{ displayText }} 남음
      </div>
      <div v-else>
        ({{ dlvday }}) {{ dlvtime }}차 배송 ({{ endday }})
        {{ displayText }} 주문마감
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import moment from "moment";

const props = defineProps({
  currentDay: { type: String },
  currentTime: { type: String },
  endDay: { type: String },
  endTime: { type: String }, // 예: "16:00"
  endday: { type: String },
  dlvday: { type: String },
  dlvtime: { type: String },
  remainHms: { type: String },
});

const isCountingDown = ref(false);
const displayText = ref("--:--");
const isRedBox = ref(false);
const hideBox = ref(false); // ✅ 박스 숨김 여부
let timer = null;

function refreshPage() {
  window.location.reload();
}

function startCountdown() {
  if (isCountingDown.value) return;
  if (displayText.value === "배송정보만료(F5로 새로고침)") return;

  if (props.remainHms === "00:00:00") {
    displayText.value = "익일 영업일 배송 예정";
    isRedBox.value = false;
    hideBox.value = true; // ✅ 익일배송일 경우 전체 박스 숨김
    return;
  }

  hideBox.value = false; // ✅ 다시 보여줘야 하는 경우 해제

  if (!props.endTime || !moment(props.endTime, "HH:mm", true).isValid()) {
    displayText.value = "--:--";
    return;
  }

  const now = moment();
  const deadline = moment(`${now.format("YYYY-MM-DD")} ${props.endTime}:00`);
  const countdownStart = deadline.clone().subtract(1, "hour");

  const diffToDeadline = deadline.diff(now, "seconds");
  const diffToStart = countdownStart.diff(now, "seconds");

  const inCountdown = diffToDeadline > 0 && diffToStart <= 0;

  if (inCountdown) {
    isRedBox.value = true;
    isCountingDown.value = true;
    runCountdown(deadline);
  } else {
    isRedBox.value = false;
    displayText.value = props.endTime.slice(0, 5);
  }
}

function runCountdown(deadline) {
  updateCountdown(deadline);
  timer = setInterval(() => {
    updateCountdown(deadline);
  }, 1000);
}

function updateCountdown(deadline) {
  const now = moment();
  const diff = deadline.diff(now, "seconds", true);

  if (diff <= 2) {
    displayText.value = "배송정보만료(F5로 새로고침)";
    isRedBox.value = false;
    clearInterval(timer);
    isCountingDown.value = false;
    return;
  }

  const min = String(Math.floor(diff / 60)).padStart(2, "0");
  const sec = String(Math.floor(diff % 60)).padStart(2, "0");
  displayText.value = `${min}:${sec}`;
}

onMounted(() => {
  startCountdown();
  setInterval(startCountdown, 1000);
});

onBeforeUnmount(() => {
  clearInterval(timer);
});
</script>

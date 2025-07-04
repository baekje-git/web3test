<template>
  <div class="header_wrap">
    <div class="h_logo">
      <router-link to="/com-ord">
        <p>백제약품</p>
      </router-link>
    </div>

    <div class="h_menu">
      <ol class="main_wrap">
        <li
          class="main_menu"
          v-for="(item, i) in items"
          :key="i"
          @mouseover="showSubmenu(i)"
          @mouseleave="hideSubmenu(i)"
          @click="onClickMainMenu(item)"
        >
          {{ item.label }}
          <ul class="sub_wrap" v-if="item.showSubmenu">
            <li
              class="sub_menu"
              v-for="(subItem, j) in item.sub"
              :key="j"
              @click="onClickSubMenu(subItem)"
            >
              {{ subItem }}
            </li>
          </ul>
        </li>
      </ol>
    </div>

    <div class="h_icon">
      <q-btn class="icon" push round :icon="mdiAccount">
        <q-badge color="red" floating>3</q-badge>
      </q-btn>
      <q-btn class="icon" push round :icon="mdiHeart">
        <q-badge color="red" floating>2</q-badge>
      </q-btn>
      <q-btn class="icon" push round :icon="mdiCart">
        <q-badge color="red" floating>1</q-badge>
      </q-btn>
    </div>

    <div class="h_time">
      <div class="wide_wrap">
        <div class="left">
          <div class="time_icon">
            <img src="assets/icons/clock-solid.svg" alt="clock" />
          </div>
          <p>광역</p>
        </div>
        <div class="right">
          <p>
            {{ timeLimitData.hours }}
            :{{ timeLimitData.minutes }}:{{ timeLimitData.seconds }}
          </p>
          <p>익일오후배송</p>
        </div>
      </div>
      <div class="dead_wrap">
        <div class="left">
          <div class="time_icon">
            <img src="assets/icons/clock-regular.svg" alt="clock" />
          </div>
          <p>마감</p>
        </div>
        <div class="right">
          <p>
            {{ timeLimitData.hours }}
            :{{ timeLimitData.minutes }} :{{ timeLimitData.seconds }}
          </p>
          <p>당일오후배송</p>
        </div>
      </div>
    </div>

    <div class="h_exit">
      <q-btn class="icon" push round :icon="mdiLogout" @click="onClickLogout" />
    </div>
  </div>
  <router-view />
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//
import { onBeforeMount, onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { mdiAccount, mdiHeart, mdiCart, mdiLogout } from "@mdi/js";
import { Cookies } from "quasar";
import * as util from "src/support/util";

const router = useRouter();

const items = ref([
  {
    label: "주문",
    sub: ["통합주문", "다빈도", "건식기", "동물약품"],
    showSubmenu: false,
  },
  {
    label: "반품",
    sub: [],
    showSubmenu: false,
  },
  {
    label: "매출원장",
    sub: [],
    showSubmenu: false,
  },
  {
    label: "결제",
    sub: [],
    showSubmenu: false,
  },
  {
    label: "약가인하정보",
    sub: [],
    showSubmenu: false,
  },
  {
    label: "마이페이지",
    sub: [],
    showSubmenu: false,
  },
]);

const timeLimitData = ref({
  intervalId: 0,
  hours: "00",
  minutes: "00",
  seconds: "00",
});

//---------------------------- Function ------------------------------//

function showSubmenu(index) {
  items.value[index].showSubmenu = true;
}

function hideSubmenu(index) {
  items.value[index].showSubmenu = false;
}

function startTimer(duration, display) {
  let timer =
    duration +
    Number(timeLimitData.value.hours) +
    Number(timeLimitData.value.minutes) +
    Number(timeLimitData.value.seconds);

  timeLimitData.value.intervalId = setInterval(() => {
    timeLimitData.value.hours = parseInt(timer / 3600, 10);
    timeLimitData.value.minutes = parseInt((timer % 3600) / 60, 10);
    timeLimitData.value.seconds = parseInt(timer % 60, 10);

    timeLimitData.value.hours =
      timeLimitData.value.hours < 10
        ? "0" + timeLimitData.value.hours
        : timeLimitData.value.hours;
    timeLimitData.value.minutes =
      timeLimitData.value.minutes < 10
        ? "0" + timeLimitData.value.minutes
        : timeLimitData.value.minutes;
    timeLimitData.value.seconds =
      timeLimitData.value.seconds < 10
        ? "0" + timeLimitData.value.seconds
        : timeLimitData.value.seconds;

    timeLimitData.value.hours = timeLimitData.value.hours;
    timeLimitData.value.minutes = timeLimitData.value.minutes;
    timeLimitData.value.seconds = timeLimitData.value.seconds;

    if (--timer < 0) {
      timer = duration;
    }
  }, 1000);
}

async function onClickLogout() {
  Cookies.remove("autoLoginData");
  util.logout();
  router.push({ path: "/login" });
  util.showNotify("로그아웃 되었습니다.");
}

async function onClickMainMenu(val) {
  if (val.label === "반품") {
    await router.push("/return");
  } else if (val.label === "매출원장") {
    await router.push("/ordLedger");
  }
}

async function onClickSubMenu(val) {
  if (val === "통합주문") {
    await router.push("/");
  } else if (val === "다빈도") {
    // await router.push("/test");
  }
}

onBeforeMount(() => {
  clearInterval(timeLimitData.value.intervalId);
});

onMounted(() => {
  startTimer(60 * 60 * 1);
});
</script>

<style lang="scss" scoped>
.header_wrap {
  @include gradation;
  @include flex_around;
  width: 100vw;
  min-width: 1840px;
  padding: 15px 80px;
  margin: 0 auto;
  box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.33);
}
.h_logo p {
  @include fs-2;
  color: $w;
}
.main_wrap {
  @include flex_center;
  position: relative;
  gap: 100px;
  margin: 5px;
}
.main_menu {
  @include fs-4;
}
.main_menu:hover {
  border-bottom: 2px solid #00eeff;
}
.sub_wrap {
  @include flex_around;
  position: absolute;
  height: 90px;
  gap: 60px;
}
.sub_menu {
  color: $b;
}
.sub_menu:hover {
  @include fw-7;
  color: $b3;
}
.h_icon {
  @include flex_center;
  gap: 32px;
}
.icon {
  width: 32px !important;
  height: 32px !important;
  background-color: $w;
  color: $b3;
}

.h_time {
  @include flex_center;
  gap: 8px;
}
.time_icon {
  @include flex_center;
}
.left p,
.right p:nth-child(1) {
  @include fw-7;
}
.dead_wrap {
  @include flex_center;
  gap: 8px;
  background-color: $r2;
  padding: 3px 10px;
  border-radius: 8px;
}
.dead_wrap p {
  @include fs-5;
  color: $w;
}
.wide_wrap {
  @include flex_center;
  gap: 8px;
  background-color: $w;
  padding: 3px 10px;
  border-radius: 8px;
}
.wide_wrap p {
  @include fs-5;
}
.wide_wrap .right p:nth-child(2) {
  color: $b4;
}

.h_exit {
  @include flex_center;
}
</style>

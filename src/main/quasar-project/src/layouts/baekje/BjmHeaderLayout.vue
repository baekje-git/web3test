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
        >
          {{ item.label }}
          <ul class="sub_wrap" v-if="item.showSubmenu">
            <li class="sub_menu" v-for="(subItem, j) in item.sub" :key="j">
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
      <div class="dead_wrap">
        <div class="left">
          <div class="time_icon">
            <img src="src/assets/icons/clock-regular.svg" alt="clock" />
          </div>
          <p>마감</p>
        </div>
        <div class="right">
          <p>{{ hour }}:{{ minutes }}:{{ seconds }}</p>
          <p>당일오후배송</p>
        </div>
      </div>
      <div class="wide_wrap">
        <div class="left">
          <div class="time_icon">
            <img src="src/assets/icons/clock-solid.svg" alt="clock" />
          </div>
          <p>광역</p>
        </div>
        <div class="right">
          <p>{{ hour }}:{{ minutes }}:{{ seconds }}</p>
          <p>익일오후배송</p>
        </div>
      </div>
    </div>

    <div class="h_exit">
      <q-btn class="icon" push round :icon="mdiLogout" @click="onClickLogout" />
    </div>
  </div>
</template>
<script>
import { useRouter } from "vue-router";
import { mdiAccount, mdiHeart, mdiCart, mdiLogout } from "@mdi/js";
import { Cookies } from "quasar";
import * as util from "src/support/util";

export default {
  components: {},

  setup() {},
  data: () => ({
    mdiAccount: mdiAccount,
    mdiHeart: mdiHeart,
    mdiCart: mdiCart,
    mdiLogout: mdiLogout,

    intervalId: 0,
    hour: "00",
    minutes: "00",
    seconds: "00",

    items: [
      {
        label: "주문",
        sub: ["통합주문", "다빈도", "건식기", "동물의약품"],
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
    ],
  }),
  mounted() {
    this.startTimer(60 * 60 * 1);
  },
  beforeMount() {
    clearInterval(this.intervalId);
  },
  methods: {
    showSubmenu(index) {
      this.items[index].showSubmenu = true;
    },
    hideSubmenu(index) {
      this.items[index].showSubmenu = false;
    },

    startTimer(duration, display) {
      var timer = duration,
        hours,
        minutes,
        seconds;
      this.intervalId = setInterval(() => {
        hours = parseInt(timer / 3600, 10);
        minutes = parseInt((timer % 3600) / 60, 10);
        seconds = parseInt(timer % 60, 10);

        hours = hours < 10 ? "0" + hours : hours;
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;

        if (--timer < 0) {
          timer = duration;
        }
      }, 1000);
    },

    onClickLogout() {
      const router = useRouter();
      util.logout();
      // clearInterval(polling);
      console.log("logout===>");

      Cookies.remove("autoLoginData");
      debugger;
      router.push({ path: "/login" });
      util.showNotify("로그아웃 되었습니다.");
    },
  },
};
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

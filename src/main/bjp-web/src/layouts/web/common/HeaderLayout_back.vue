<template>
  <div class="HeaderComp_wrap">
    <div class="h_logo">
      <!-- <router-link to="/"> -->
      <!-- <p>백제약품</p> -->
      <img
        :src="imgSrc"
        style="cursor: pointer"
        alt="logo"
        @click="onClickLogo"
      />
      <!-- </router-link> -->
    </div>

    <div class="h_menu">
      <ol class="gnb_wrap">
        <li
          class="gnb_menu"
          v-for="(gnb_menu, i) in menuItems"
          :key="i"
          @mouseover="showLnb(i)"
          @mouseleave="selectLnb"
        >
          <router-link :to="gnb_menu.gnblink">
            <p class="gnb_text">{{ gnb_menu.gnb }}</p>
          </router-link>
          <ul class="lnb_wrap" v-if="gnb_menu.lnbShow">
            <li class="lnb_menu" v-for="(lnb_menu, j) in gnb_menu.lnb" :key="j">
              <router-link :to="gnb_menu.lnblink[j]">
                <p class="lnb_text" @click="onClickSubMenu(lnb_menu)">
                  {{ lnb_menu }}
                </p>
              </router-link>
            </li>
          </ul>
        </li>
      </ol>
    </div>

    <div class="h_icon" @mouseleave="hideIconMenu">
      <q-btn
        size="11px"
        :class="
          userInfo === '30' ? 'icon_disable flex_center' : 'icon flex_center'
        "
        push
        round
        style="cursor: pointer"
        :icon="mdiAccount"
        :disable="isDisabel"
        @click="onClickCustListPopup"
      >
        <!-- <q-badge color="red" floating>3</q-badge> -->
        <q-tooltip
          class="h_icon_tooltip"
          anchor="bottom middle"
          self="center middle"
        >
          {{ custNm }}
        </q-tooltip>
      </q-btn>

      <!-- <p class="h_icon_text">{{ custNm }}</p> -->
      <q-btn
        size="11px"
        class="icon"
        push
        round
        :icon="mdiHeart"
        @click="onClickInterest"
      >
        <q-badge v-if="soldoutInYn" color="red" floating rounded />
      </q-btn>
      <q-btn
        size="11px"
        class="icon"
        push
        round
        :icon="mdiCart"
        @mouseover="showIconMenu"
      >
        <q-badge v-if="basketCountData.totalCount > 0" color="red" floating>
          {{ basketCountData.totalCount }}
        </q-badge>
      </q-btn>
      <ol class="h_icon_menu">
        <div class="triangle"></div>
        <ul>
          <li>
            <router-link to="/comOrd" @click="initMenuContorll('comOrd')">
              <span>주문</span>
              <span>{{ " (" + basketCountData.ordCount + ")" }}</span>
            </router-link>
          </li>
          <li>
            <router-link to="/return" @click="initMenuContorll('return')">
              <span>반품</span>
              <span>{{ " (" + basketCountData.takebackCount + ")" }}</span>
            </router-link>
          </li>
        </ul>
      </ol>
    </div>

    <div class="h_time">
      <!-- <CountBoxComp_b />
      <CountBoxComp_a /> -->
      <CountBoxComp_default
        v-if="isRemain1Hour"
        countbox_style="red"
        :countdown="countdown"
        :dlvday="dlvday"
        :dlvtime="dlvTimeData.dlvTime"
        state="남음"
      />
      <CountBoxComp_default3
        v-else
        :current-day="dlvTimeData.curYmd"
        :current-time="dlvTimeData.curHms"
        :end-day="dlvTimeData.endYmd"
        :endTime="dlvTimeData.endHm"
        :dlvtime="dlvTimeData.dlvTime"
        :countdown="countdown"
        :endday="endday"
        :dlvday="dlvday"
      />
    </div>

    <div class="h_exit">
      <q-btn
        class="icon"
        push
        round
        :icon="mdiLogout"
        size="11px"
        @click="onClickLogout"
      />
      <p>로그아웃</p>
    </div>
  </div>
  <router-view />
  <DialogComp_notice
    :is-open="isOpenNoticePopup"
    @close="isOpenNoticePopup = false"
  />
  <CustListPopup
    :gb="isHeaderClick"
    :is-open="isOpenCustListPopup"
    @close="isOpenCustListPopup = false"
  />
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted, computed, inject, watch, onUnmounted } from "vue";
import { api } from "boot/axios";
import { mdiAccount, mdiHeart, mdiCart, mdiLogout } from "@mdi/js";
import { useRouter, useRoute } from "vue-router";
import { Cookies } from "quasar";
import * as util from "src/support/util";
import "moment/locale/ko";
import moment from "moment";
moment.locale("ko");

import CountBoxComp_a from "src/components/web/common/CountBoxComp_a.vue";
import CountBoxComp_b from "src/components/web/common/CountBoxComp_b.vue";
import CountBoxComp_default from "src/components/web/common/CountBoxComp_default.vue";
import CountBoxComp_default2 from "src/components/web/common/CountBoxComp_default2.vue";
import CountBoxComp_default3 from "src/components/web/common/CountBoxComp_default3.vue";
import CustListPopup from "src/components/web/common/CustListPopup.vue";
import DialogComp_notice from "src/components/web/common/BjpNoticePopup.vue";

const router = useRouter();
const route = useRoute();
const userInfo = util.getUserData();
const bus = inject("bus");
const itemData = ref();
const isTodayDisable = ref(Cookies.get("todayDisable") ? true : false);
const isWeekDisable = ref(Cookies.get("weekDisable") ? true : false);

//하위 메뉴 닫기
const lnbShowFalse = () => {
  menuItems.value.forEach((item) => {
    item.lnbShow = false;
  });
};

const selectLnb = (event) => {
  lnbShowFalse();
  const selUrl = event.target.baseURI.split("/");
  const mItem = menuItems.value.findIndex((item) =>
    item.lnblink.includes("/" + selUrl[4])
  );
  if (mItem != -1) {
    menuItems.value[mItem].lnbShow = true;
  }
};

const showLnb = (index) => {
  menuItems.value.forEach((item) => {
    item.lnbShow = false;
  });

  menuItems.value[index].lnbShow = true;
};

// const hideLnb = (index) => {
//   menuItems.value[index].lnbShow = false;
// };

const showIconMenu = () => {
  const iconMenu = document.querySelector(".h_icon_menu");
  iconMenu.style.display = "block";
};

const hideIconMenu = () => {
  const iconMenu = document.querySelector(".h_icon_menu");
  iconMenu.style.display = "none";
};

const menuItems = ref([
  {
    gnb: "주문",
    gnblink: "/comOrd",
    lnb: ["통합주문", "다빈도", "관심상품"],
    lnblink: ["/comOrd", "/freOrd", "/interest"],
    lnbShow: false,
  },
  {
    gnb: "반품",
    gnblink: "/return",
    lnb: ["반품등록", "반품현황", "반품상세"],
    lnblink: ["/return", "/bakReq", "/bakReqDetail"],
  },
  {
    gnb: "매출원장",
    gnblink: "/ordLedger",
    lnb: ["거래원장", "품목수불", "회수품목내역"],
    lnblink: ["/ordLedger", "/pmntItem", "/clctItm"],
    lnbShow: false,
  },
  {
    gnb: "결제",
    gnblink: "/payment",
    lnb: [],
    lnblink: [],
    lnbShow: false,
  },
  {
    gnb: "약가인하정보",
    gnblink: "/ntfctnInf",
    lnb: ["고시정보", "대상품목"], // "보상내역"],
    lnblink: ["/ntfctnInf", "/trgtItm"], // "/cmpnsDtl"],
    lnbShow: false,
  },
  {
    gnb: "마이페이지",
    gnblink: "/myPage",
    lnb: ["마이페이지", "월간조회", "최근주문내역"],
    lnblink: ["/myPage", "/custMonth", "/recently"],
    lnbShow: false,
  },
]);

const dataParam = ref({
  userId: "",
  refreshToken: "",
});

const custNm = computed(() => userInfo.CUST_NM ?? "");
const isDisabel = computed(() => {
  if (userInfo.USER_GB_CD === "10" || userInfo.USER_GB_CD === "20") {
    return false;
  }
  return true;
});

const basketCountData = ref({
  ordCount: 0,
  takebackCount: 0,
  totalCount: 0,
});

const isOpenCustListPopup = ref(false);
const isOpenNoticePopup = ref(false);
const isHeaderClick = ref(false);

const dlvTimeData = ref({
  custNm: "",
  dlvCourseCd: "",
  curYmd: "",
  curHmsOrg: "",
  curHms: "",
  curWeek: "",
  endYmd: "",
  endHmOrg: "",
  endHm: "",
  endWeek: "",
  dlvWeek: "",
  dlvTime: "",
});

const company = [
  "dhhorim",
  "tspharm",
  "kaymed",
  "charmacist",
  "gfpharm",
  "reinpharm",
  "nurip",
  "mediplankorea",
  "ansimpharm",
];

const companyNm = ref("N");

const imgSrc = computed(() => {
  if (companyNm.value === "dhhorim") {
    return "assets/images/img_logo_horim.png";
  } else if (companyNm.value === "tspharm") {
    return "assets/images/img_logo_h_tsfarm.png";
  } else if (companyNm.value === "kaymed") {
    return "assets/images/img_logo_h_kaymed.png";
  } else if (companyNm.value === "charmacist") {
    return "assets/images/img_logo_h_chpharm.png";
  } else if (companyNm.value === "gfpharm") {
    return "assets/images/img_logo_h_dgpharm.png";
  } else if (companyNm.value === "reinpharm") {
    return "assets/images/img_logo_h_reinpharm.png";
  } else if (companyNm.value === "nurip") {
    return "assets/images/img_logo_h_nuripharm.png";
  } else if (companyNm.value === "mediplankorea") {
    return "assets/images/img_logo_h_mediplan.png";
  } else if (companyNm.value === "ansimpharm") {
    return "assets/images/img_logo_h_ansimpharm.png";
  }

  return "assets/images/logo.png";
});

const soldoutInYn = ref(false);

const isRemain1Hour = ref("");
const countdown = ref("");
const endday = ref("");
const dlvday = ref("");
const intervalId = ref(null);

//---------------------------- Function ------------------------------//

// 주문, 반품 장바구니 count조회
async function getBasketCount() {
  if (userInfo.CUST_CD !== "") {
    const res = await api.get("/main/basketCount", {
      params: { custCd: userInfo.CUST_CD },
    });

    basketCountData.value.ordCount = res.ORD_COUNT;
    basketCountData.value.takebackCount = res.TAKEBACK_COUNT;
    basketCountData.value.totalCount = res.TOTAL_COUNT;
    //통합주문 장바구니 품목수
    bus.emit("basketC", res.ORD_COUNT);
  }
}

// 로그아웃
async function onClickLogout() {
  Cookies.remove("autoLoginData");
  util.logout();
  router.push({ path: "/login" });
  util.showNotify("로그아웃 되었습니다.");
}

// 품목명 조회(자동완성)
async function getItemNms() {
  const res = await api.get("/main/getItemNms");

  itemData.value = res;
}

// 관심상품
async function onClickInterest() {
  try {
    await api.put("/main/saveAllAlarmYn", {
      custCd: userInfo.CUST_CD,
      brchCd: userInfo.BRCH_CD,
    });
    await getSoldOutInYn();
    await router.push("/interest");
  } catch (error) {
    alert(error.response.data);
  }
}

// 거래처 목록
async function onClickCustListPopup() {
  isHeaderClick.value = true;
  isOpenCustListPopup.value = true;
  await bus.emit("custList", userInfo.LOGIN_DEPT_CD);
}

// 배송마감시간 조회
async function getDlvTime() {
  const res = await api.get("/main/getDlvTime", {
    params: { custCd: userInfo.CUST_CD },
  });

  dlvTimeData.value.custNm = res.CUST_NM;
  dlvTimeData.value.dlvCourseCd = res.DLV_COURSE_CD;
  dlvTimeData.value.curYmd = res.CUR_YMD;
  dlvTimeData.value.curHmsOrg = res.CUR_HMS_ORG;
  dlvTimeData.value.curHms = res.CUR_HMS;
  dlvTimeData.value.curWeek = res.CUR_WEEK;
  dlvTimeData.value.endYmd = res.END_YMD;
  dlvTimeData.value.endHmOrg = res.END_HM_ORG;
  dlvTimeData.value.endHm = res.END_HM;
  dlvTimeData.value.endWeek = res.END_WEEK;
  dlvTimeData.value.dlvWeek = res.DLV_WEEK;
  dlvTimeData.value.dlvTime = res.DLV_TIME;

  const curYmd = moment(dlvTimeData.value.curYmd).format("YYYY-MM-DD");
  const curHms = moment(dlvTimeData.value.curHmsOrg, "HH:mm:ss");
  const endYmd = moment(dlvTimeData.value.endYmd).format("YYYY-MM-DD");
  const endHm =
    dlvTimeData.value.endHm !== ""
      ? moment(dlvTimeData.value.endHmOrg, "HH:mm:ss")
      : dlvTimeData.value.endHm;

  // if (curYmd === endYmd) {
  if (endHm) {
    const hour = `${moment.duration(endHm.diff(curHms)).hours()}`;
    const minute = `${moment.duration(endHm.diff(curHms)).minutes()}`;
    const second = `${moment.duration(endHm.diff(curHms)).seconds()}`;

    countdown.value =
      hour.padStart(2, "0") +
      ":" +
      minute.padStart(2, "0") +
      ":" +
      second.padStart(2, "0");

    // day.value = moment().day(dlvTimeData.value.endWeek).format("ddd");

    /** moment 한글화 안먹혀서 이렇게 처리함 **/
    if (dlvTimeData.value.endWeek === "1") {
      endday.value = "일";
    } else if (dlvTimeData.value.endWeek === "2") {
      endday.value = "월";
    } else if (dlvTimeData.value.endWeek === "3") {
      endday.value = "화";
    } else if (dlvTimeData.value.endWeek === "4") {
      endday.value = "수";
    } else if (dlvTimeData.value.endWeek === "5") {
      endday.value = "목";
    } else if (dlvTimeData.value.endWeek === "6") {
      endday.value = "금";
    } else if (dlvTimeData.value.endWeek === "7") {
      endday.value = "토";
    }

    if (dlvTimeData.value.dlvWeek === "1") {
      dlvday.value = "일";
    } else if (dlvTimeData.value.dlvWeek === "2") {
      dlvday.value = "월";
    } else if (dlvTimeData.value.dlvWeek === "3") {
      dlvday.value = "화";
    } else if (dlvTimeData.value.dlvWeek === "4") {
      dlvday.value = "수";
    } else if (dlvTimeData.value.dlvWeek === "5") {
      dlvday.value = "목";
    } else if (dlvTimeData.value.dlvWeek === "6") {
      dlvday.value = "금";
    } else if (dlvTimeData.value.dlvWeek === "7") {
      dlvday.value = "토";
    }
    /****************************************/
    if (hour < 1 && minute >= 0 && second >= 0) {
      isRemain1Hour.value = true;
    } else {
      isRemain1Hour.value = false;
    }
    // } else {
    //   isRemain1Hour.value = false;
    // }
  }
}

// 입고알림품목 여부 조회
async function getSoldOutInYn() {
  const res = await api.get("/main/getSoldOutYn", {
    params: { brchCd: userInfo.BRCH_CD, custCd: userInfo.CUST_CD },
  });

  if (res.CNT > 0) {
    soldoutInYn.value = true;
  } else {
    soldoutInYn.value = false;
  }
}

// 좌측 로고 클릭
async function onClickLogo() {
  lnbShowFalse();
  if (route.path === "/comOrd") {
    router.go(0);
  } else {
    await router.push("/comOrd");
  }
}

// sub menu 관심상품 클릭 시 우측 관심상품 도트 제거
async function onClickSubMenu(lnb_menu) {
  if (lnb_menu === "관심상품") {
    try {
      await api.put("/main/saveAllAlarmYn", { custCd: userInfo.CUST_CD });
      await getSoldOutInYn();
    } catch (error) {
      alert(error.response.data);
    }
  }
}

// 팝업 공지사항 조회
async function getPopupNotice() {
  const res = await api.post("/myPage/getPopupNotice");

  if (res.length > 0 && !isTodayDisable.value && !isWeekDisable.value) {
    isOpenNoticePopup.value = true;
  }
}

// 클릭한메뉴 클래스 변경
// const toggleActiveClass = (event) => {
//   document.querySelectorAll(".lnb_text").forEach((lnbText) => {
//     lnbText.classList.remove("lnb_text_active");
//   });

//   const clickedLnbText = event.currentTarget.querySelector(".lnb_text");
//   clickedLnbText.classList.add("lnb_text_active");
// };

// 최초 화면 진입 시 메뉴 controll
async function initMenuContorll(urlStr) {
  lnbShowFalse();

  const mItem = menuItems.value.findIndex((item) =>
    item.lnblink.includes("/" + urlStr)
  );
  if (mItem != -1) {
    menuItems.value[mItem].lnbShow = true;
  }
}

bus.on("onLoadGetAutoCompleteData", async () => {
  await bus.emit("getAutoCompleteData", itemData.value);
});

bus.on("refreshHeaderBasket", async () => {
  await getBasketCount();
});

watch(itemData, async () => {
  await bus.emit("getAutoCompleteData", itemData.value);
});

watch(isRemain1Hour, (val) => {
  clearInterval(intervalId.value);
  if (val) {
    intervalId.value = setInterval(() => {
      getDlvTime();
    }, 1000);
  } else {
    intervalId.value = setInterval(() => {
      getDlvTime();
    }, 60000);
  }
});

onMounted(async () => {
  const url = window.location.href;
  const urlStr = url.split("/");

  initMenuContorll(urlStr[4]);

  company.forEach((m) => {
    if (url.includes(m)) {
      companyNm.value = m;
    }
  });

  if (userInfo.CUST_CD === "") {
    isOpenCustListPopup.value = true;
    isHeaderClick.value = false;

    await bus.emit("custList", userInfo.LOGIN_DEPT_CD);
  } else {
    await getBasketCount();
    getPopupNotice();
  }

  dataParam.value.userId = userInfo.USER_ID;
  dataParam.value.refreshToken = util.getRefreshToken();

  await getSoldOutInYn();
  await getDlvTime();
  await getItemNms();

  await bus.emit("setDlvTime");

  await getDlvTime();
});

onUnmounted(() => {
  clearInterval(intervalId.value);
});
</script>

<style lang="scss" scoped>
.HeaderComp_wrap {
  @include flex_around;
  @include gradation;
  width: 100%;
  min-width: 1900px;
  height: 80px;
  padding: 20px 80px;
}
.h_logo {
  img {
    @include flex_center;
    width: 110px;
    height: 40px;
  }
  p {
    @include fs-2;
    @include fw-7;
    color: $w;
  }
}
.gnb_wrap {
  @include flex_around;
  position: relative;
  gap: 100px;
}
.gnb_text {
  color: #f0f0f0;
  @include fs-4;
}
.gnb_text:hover {
  cursor: pointer;
  color: $w;
  // border-bottom: 2px solid #00eeff;
  box-shadow: 0px 2px 0 0 #00eeff;
}
.lnb_wrap {
  // @include flex_around;
  // position: absolute;
  // gap: 50px;
  // height: 90px;
  @include flex_start;
  position: absolute;
  width: 700px;
  height: 90px;
  margin-left: 5px;
  gap: 40px;
}
.lnb_text {
  color: #1f3f82;
}
// .lnb_text:hover {
//   @include fw-7;
//   cursor: pointer;
//   color: $b3;
// }
.lnb_text_active {
  @include fw-7;
  cursor: pointer;
  color: $b3;
}

.h_icon {
  position: relative;
}
.h_icon {
  @include flex_center;
  gap: 32px;
}

/** 로그아웃 스타일 **/
.h_exit {
  @include flex_center;
  flex-direction: column;
  .icon {
    // 배경크기
    font-size: 14px;
  }
  .q-icon {
    // 아이콘 크기
    font-size: 30px !important;
  }
  p {
    color: $w;
    font-size: 14px;
  }
}
.icon {
  color: $b3;
  background-color: $w;
  cursor: pointer !important;
}
.icon_disable {
  color: $b3;
  background-color: $w;
  cursor: default !important;
}
.h_icon_tooltip {
  background-color: transparent;
  font-size: 14px;
}
// .h_icon_text {
//   @include fs-5;
//   color: $w;
//   position: absolute;
//   bottom: -22px;
//   left: -14%;
// }

.h_icon_menu {
  display: none;
  text-align: center;
  position: absolute;
  bottom: -94px;
  right: -26px;
  z-index: 100;
  .triangle {
    margin: 0 auto;
    width: 0;
    height: 20px;
    border-bottom: 12px solid #fff;
    border-top: 0px solid transparent;
    border-left: 8px solid transparent;
    border-right: 8px solid transparent;
  }
  ul {
    padding: 16px 24px 16px 8px;
    background-color: #fff;
    border: 1px solid $b3;
    border-radius: 5px;
    li {
      @include flex_center;
      gap: 8px;
      cursor: pointer;
      span {
        @include fs-5;
        color: $b;
      }
      span:nth-child(1) {
      }
      span:nth-child(2) {
        @include fw-7;
      }
    }
  }
}

.q-btn .q-icon {
  font-size: 24px;
}
.h_time {
  @include flex_center;
  gap: 8px;
}
.router-link-exact-active {
  @include fw-7;
  cursor: pointer;
  color: $b3;
}
</style>

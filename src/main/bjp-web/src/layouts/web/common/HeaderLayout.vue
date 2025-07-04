<template>
  <div class="HeaderComp_wrap">
    <div class="h_wrap">
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

      <div class="h_menu" v-if="userInfo.ANI_TREAT_YN !== 'Y'">
        <ol class="gnb_wrap">
          <li
            class="gnb_menu"
            v-for="(gnb_menu, i) in menuItems"
            :key="i"
            @mouseover="showLnb(i)"
            @mouseleave="selectLnb"
          >
            <a @click.prevent="onClickGnbMenu(gnb_menu)">
              <p class="gnb_text">{{ gnb_menu.gnb }}</p>
            </a>
            <ul class="lnb_wrap" v-if="gnb_menu.lnbShow">
              <li
                class="lnb_menu"
                v-for="(lnb_menu, j) in gnb_menu.lnb"
                :key="j"
              >
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
      <div class="h_menu" v-if="userInfo.ANI_TREAT_YN === 'Y'">
        <ol class="gnb_wrap">
          <li
            class="gnb_menu"
            v-for="(gnb_menu, i) in menuItems2"
            :key="i"
            @mouseover="showLnb(i)"
            @mouseleave="selectLnb"
          >
            <router-link :to="gnb_menu.gnblink">
              <p class="gnb_text">{{ gnb_menu.gnb }}</p>
            </router-link>
            <ul class="lnb_wrap" v-if="gnb_menu.lnbShow">
              <li
                class="lnb_menu"
                v-for="(lnb_menu, j) in gnb_menu.lnb"
                :key="j"
              >
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
    </div>

    <div class="h_wrap">
      <div class="h_icon w880-none" @mouseleave="hideIconMenu">
        <a
          class="h_link2"
          :disable="isDisabel"
          v-if="userInfo.USER_GB_CD == '30'"
        >
          <span class="h_ico"><img src="assets/images/ico_store.svg" /></span>
          <span
            ><b>{{ custNm }}</b></span
          >
        </a>
        <a
          class="h_link"
          :disable="isDisabel"
          @click="onClickCustListPopup"
          v-if="userInfo.USER_GB_CD != '30'"
        >
          <span class="h_ico"><img src="assets/images/ico_store.svg" /></span>
          <span
            ><b>{{ custNm }}</b></span
          >
        </a>
        <DialogComp_a>
          <a class="h_link" :disable="isDisabel" @click="onClickSms">
            <span class="h_ico"
              ><img src="assets/images/left-icon02.png"
            /></span>
            <span class="link-txt">SMS 전송</span>
          </a>
        </DialogComp_a>
        <a
          class="h_link w880-none"
          :disable="isDisabel"
          @click="onClickInterest"
        >
          <span class="h_ico"
            ><img src="assets/images/ico_heart_line.svg"
          /></span>
          <span class="link-txt">관심상품</span>
          <span v-if="soldoutInYn" class="badge"></span>
        </a>

        <a
          class="h_link w880-none"
          :disable="isDisabel"
          @mouseover="showIconMenu"
        >
          <span class="h_ico"><img src="assets/images/ico_cart.svg" /></span>
          <span class="link-txt">장바구니</span>
          <span v-if="basketCountData.totalCount > 0" class="badge">{{
            basketCountData.totalCount
          }}</span>
        </a>
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

      <!-- <div class="h_time">
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
      </div> -->
      <a
        class="h_link logout w880-none"
        :disable="isDisabel"
        @click="onClickLogout"
      >
        <span class="h_ico"><img src="assets/images/ico_logout.svg" /></span>
        <span class="link-txt">로그아웃</span>
      </a>

      <div class="w880-block">
        <a
          class="h_link2"
          :disable="isDisabel"
          v-if="userInfo.USER_GB_CD == '30'"
        >
          <span class="h_ico"><img src="assets/images/ico_store.svg" /></span>
          <span
            ><b>{{ custNm }}</b></span
          >
        </a>
        <a
          class="h_link"
          :disable="isDisabel"
          @click="onClickCustListPopup"
          v-if="userInfo.USER_GB_CD != '30'"
        >
          <span class="h_ico"><img src="assets/images/ico_store.svg" /></span>
          <span
            ><b>{{ custNm }}</b></span
          >
        </a>
        <div class="dropdown">
          <button
            type="button"
            class="btn-menu"
            @click="toggleActive"
            :class="{ active: isActive }"
          >
            메뉴
          </button>
          <ul class="drop-menu">
            <li>
              <DialogComp_a>
                <a class="h_link" :disable="isDisabel" @click="onClickSms">
                  <span class="h_ico"
                    ><img src="assets/images/left-icon02.png"
                  /></span>
                  <span>SMS 전송</span>
                </a>
              </DialogComp_a>
            </li>
            <li>
              <a class="h_link" :disable="isDisabel" @click="onClickInterest">
                <span class="h_ico"
                  ><img src="assets/images/ico_heart_line.svg"
                /></span>
                <span>관심상품</span>
                <span v-if="soldoutInYn" class="badge"></span>
              </a>
            </li>
            <li>
              <a class="h_link" :disable="isDisabel" @mouseover="showIconMenu">
                <span class="h_ico"
                  ><img src="assets/images/ico_cart.svg"
                /></span>
                <span>장바구니</span>
                <span v-if="basketCountData.totalCount > 0" class="badge">{{
                  basketCountData.totalCount
                }}</span>
              </a>
              <ul class="in-menu">
                <li>
                  <router-link to="/comOrd" @click="initMenuContorll('comOrd')">
                    <span>주문</span>
                    <span>{{ " (" + basketCountData.ordCount + ")" }}</span>
                  </router-link>
                </li>
                <li>
                  <router-link to="/return" @click="initMenuContorll('return')">
                    <span>반품</span>
                    <span>{{
                      " (" + basketCountData.takebackCount + ")"
                    }}</span>
                  </router-link>
                </li>
              </ul>
            </li>
            <li>
              <a
                class="h_link logout"
                :disable="isDisabel"
                @click="onClickLogout"
              >
                <span class="h_ico"
                  ><img src="assets/images/ico_logout.svg"
                /></span>
                <span>로그아웃</span>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <NoticeDetailPopup />
  </div>
  <router-view />
  <DialogComp_notice :is-open="isOpenNoticePopup" @close="onCloseNoticePopup" />

  <DialogComp_noticeDetail
    :is-open="isOpenNoticeDetailPopup"
    @close="onCloseNoticeDetailPopup"
  />
  <CustListPopup
    :gb="isHeaderClick"
    :is-open="isOpenCustListPopup"
    @close="isOpenCustListPopup = false"
  />
  <NoticePopup />
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
import DialogComp_a from "src/components/web/common/SmsPopupComp.vue";
import CustListPopup from "src/components/web/common/CustListPopup.vue";
import DialogComp_notice from "src/components/web/common/BjpNoticePopup.vue";
import DialogComp_noticeDetail from "src/components/web/common/BjpNoticeDetailPopup.vue";
import NoticePopup from "src/components/web/myPage/NoticePopup.vue";
import NoticeDetailPopup from "src/components/web/myPage/NoticeDetailPopup.vue";
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
  menuItems2.value.forEach((item) => {
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
  const mItem2 = menuItems2.value.findIndex((item) =>
    item.lnblink.includes("/" + selUrl[4])
  );
  if (mItem2 != -1) {
    menuItems2.value[mItem2].lnbShow = true;
  }
};

const showLnb = (index) => {
  menuItems.value.forEach((item) => {
    item.lnbShow = false;
  });

  menuItems.value[index].lnbShow = true;
  menuItems2.value.forEach((item) => {
    item.lnbShow = false;
  });

  menuItems2.value[index].lnbShow = true;
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
// async function getAniTreatYn() {
//   const res = await api.get("/main/selectChangeCustInfo", {});

//   aniYn.value = res.ANI_TREAT_YN;
// }
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
    lnbShow: false,
  },
  {
    gnb: "매출원장",
    gnblink: "/ordLedger",
    lnb: ["거래원장", "품목수불", "회수품목내역"],
    lnblink: ["/ordLedger", "/pmntItem", "/clctItm"],
    lnbShow: false,
  },
  {
    gnb: "결제", // 제거 대상
    gnblink: "/payment",
    lnb: [],
    lnblink: [],
    lnbShow: false,
  },
  /*
  {
    gnb: "약가인하정보",
    gnblink: "/ntfctnInf",
    lnb: ["고시정보", "대상품목"],
    lnblink: ["/ntfctnInf", "/trgtItm"],
    lnbShow: false,
  },
  */
  {
    gnb: "마이페이지",
    gnblink: "/myPage",
    lnb: ["마이페이지", "월간조회", "최근주문내역"],
    lnblink: ["/myPage", "/custMonth", "/recently"],
    lnbShow: false,
  },
]);

// PAY_YN 값에 따라 "결제" 메뉴 제거
const updateMenuItems = () => {
  if (
    loginImgData.value?.PAY_YN !== "Y" ||
    userInfo.CUST_GB_CD === "03" ||
    userInfo.CUST_GB_CD === "32"
  ) {
    menuItems.value = menuItems.value.filter((menu) => menu.gnb !== "결제");
  } else {
  }
};

const menuItems2 = ref([
  {
    gnb: "주문",
    gnblink: "/comOrd",
    lnb: ["통합주문", "다빈도", "관심상품", "동물약품"],
    lnblink: ["/comOrd", "/freOrd", "/interest", "/ani"],
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
const isOpenNoticeDetailPopup = ref(false);
const isHeaderClick = ref(false);
const loginImgData = ref(null);
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
  const basePath = "assets/images/";
  if (loginImgData.value?.LOGO_IMG) {
    return `${basePath}${loginImgData.value.LOGO_IMG}`; // DB에서 가져온 파일명 적용
  }

  if (userInfo.BRCH_CD === "41") {
    `${basePath}${loginImgData.value?.LOGO_IMG}`;
  } else if (userInfo.BRCH_CD === "45") {
    return `${basePath}${loginImgData.value?.LOGO_IMG}`;
  } else if (userInfo.BRCH_CD === "49") {
    return `${basePath}${loginImgData.value?.LOGO_IMG}`;
  } else if (userInfo.BRCH_CD === "51") {
    return `${basePath}${loginImgData.value?.LOGO_IMG}`;
  } else if (userInfo.BRCH_CD === "52") {
    return `${basePath}${loginImgData.value?.LOGO_IMG}`;
  } else if (userInfo.BRCH_CD === "53") {
    return `${basePath}${loginImgData.value?.LOGO_IMG}`;
  } else if (userInfo.BRCH_CD === "54") {
    return `${basePath}${loginImgData.value?.LOGO_IMG}`;
  } else if (userInfo.BRCH_CD === "57") {
    return `${basePath}${loginImgData.value?.LOGO_IMG}`;
  } else if (userInfo.BRCH_CD === "58") {
    return `${basePath}${loginImgData.value?.LOGO_IMG}`;
  } else if (userInfo.BRCH_CD === "44") {
    return `${basePath}${loginImgData.value?.LOGO_IMG}`;
  }

  return `${basePath}${loginImgData.value?.LOGO_IMG}`; // 기본 로고
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
// 품목명 조회(자동완성)
async function selectLoginMainVar() {
  try {
    const res = await api.get("/main/selectLoginMainVar", {
      params: { brchCd: userInfo.BRCH_CD },
    });

    loginImgData.value = res; // DB에서 받은 데이터 저장
    updateMenuItems(); // PAY_YN 값에 따라 메뉴 업데이트
  } catch (error) {
    console.error("API 요청 중 에러 발생:", error);
  }
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

  const isTodayBlocked = Cookies.get("noticeTodayDisable");
  const isWeekBlocked = Cookies.get("noticeWeekDisable");
  const isBranchTodayBlocked = Cookies.get("branchTodayDisable");
  const isBranchWeekBlocked = Cookies.get("branchWeekDisable");

  const hasNotice = res.length > 0;

  // 일반 공지 우선 체크
  if (hasNotice && !isTodayBlocked && !isWeekBlocked) {
    isOpenNoticePopup.value = true;
    return; // 일반 공지 먼저 띄우고, 닫힌 후 지점 공지 처리
  }

  // 일반 공지 없거나 차단된 경우에만 지점공지 띄움
  if (!isBranchTodayBlocked && !isBranchWeekBlocked) {
    isOpenNoticeDetailPopup.value = true;
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
  const mItem2 = menuItems2.value.findIndex((item) =>
    item.lnblink.includes("/" + urlStr)
  );
  if (mItem2 != -1) {
    menuItems2.value[mItem2].lnbShow = true;
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

onMounted(async () => {
  const url = window.location.href;
  const urlStr = url.split("/");
  await selectLoginMainVar();
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
  dataParam.value.brchCd = userInfo.BRCH_CD;
  dataParam.value.refreshToken = util.getRefreshToken();
  await getSoldOutInYn();
  await getDlvTime();
  await getItemNms();

  await bus.emit("setDlvTime");

  await getDlvTime();
  //await getAniTreatYn();
});

onUnmounted(() => {
  clearInterval(intervalId.value);
});
function onClickSms() {
  bus.emit("sendSms");
}

//토글 클래스 추가 250217
const isActive = ref(false);
const toggleActive = () => {
  isActive.value = !isActive.value; // 상태 반전
};
function onClickGnbMenu(menu) {
  if (menu.gnb === "결제") {
    if (userInfo.BRCH_CD === "51") {
      const custCd = userInfo.CUST_CD ?? ""; // 고객코드
      const targetUrl = `https://charmacist.store/pay.html?id=${custCd}`;
      window.open(targetUrl, "_blank");
    } else {
      router.push(menu.gnblink); // 내부 라우팅 (/payment)
    }
  } else {
    router.push(menu.gnblink);
  }
}

function onCloseCustomerPopup() {
  isOpenCustomerPopup.value = false;

  const userInfo = util.getUserData();

  // 거래처 선택 후 조건에 맞을 경우 공지사항 오픈
  if (
    !Cookies.get("todayDisable") &&
    !Cookies.get("weekDisable") &&
    userInfo.CUST_CD !== ""
  ) {
    isOpenNoticePopup.value = true;
  }
}

// 공지사항 닫으면 지점공지사항 열기
function onCloseNoticePopup() {
  isOpenNoticePopup.value = false;

  const isBranchTodayBlocked = Cookies.get("branchTodayDisable");
  const isBranchWeekBlocked = Cookies.get("branchWeekDisable");

  if (!isBranchTodayBlocked && !isBranchWeekBlocked) {
    isOpenNoticeDetailPopup.value = true;
  }
}
function onCloseNoticeDetailPopup() {
  isOpenNoticeDetailPopup.value = false;
}
</script>

<style lang="scss" scoped>
.HeaderComp_wrap {
  position: relative;
  @include flex_between;
  @include gradation;
  width: 100%;
  // min-width: 1366px;
  // height: 54px;
  height: 34px;
  padding: 0 per(40px);
  background: $header-bg;
  .h_wrap {
    @include flex_start;
    position: absolute;
    top: 0;
    height: 100%;

    .h_link {
      font-size: rem(14px);
      color: $w;
      transition: 0.3s;
      b {
        @include fw-6;
      }

      &.logout {
        margin-left: rem(20px);
      }
      span {
        display: inline-block;
        vertical-align: middle;
        transition: 0.3s;
      }
      .h_ico {
        margin-right: 4px;
        height: 20px;
        opacity: 0.8;
      }
      &:hover {
        cursor: pointer;
        .h_ico {
          opacity: 1;
        }
        span:not(.badge, .h_ico) {
          opacity: 0.85;
        }
      }

      .badge {
        margin-left: 3px;
        width: rem(18px);
        height: rem(18px);
        line-height: rem(18px);
        font-size: rem(12px);
        font-weight: 600;
        text-align: center;
        background-color: #e53030;
        border-radius: 50%;
        font-variant-numeric: normal;
      }
    }
    .h_link2 {
      pointer-events: none;
      font-size: rem(14px);
      color: $w;
      transition: 0.3s;
      b {
        @include fw-6;
      }

      &.logout {
        margin-left: rem(20px);
      }
      span {
        display: inline-block;
        vertical-align: middle;
        transition: 0.3s;
      }
      .h_ico {
        margin-right: 4px;
        height: 20px;
        opacity: 0.8;
      }
      &:hover {
        cursor: pointer;
        .h_ico {
          opacity: 1;
        }
        span:not(.badge, .h_ico) {
          opacity: 0.85;
        }
      }

      .badge {
        margin-left: 3px;
        width: rem(18px);
        height: rem(18px);
        line-height: rem(18px);
        font-size: rem(12px);
        font-weight: 600;
        text-align: center;
        background-color: #e53030;
        border-radius: 50%;
        font-variant-numeric: normal;
      }
    }
    &:nth-child(1) {
      left: 2%;
    }
    &:nth-child(2) {
      right: 2% !important;
    }
  }
}
.h_logo {
  margin-right: rem(48px);
  @media screen and (max-width: 1025px) {
    margin-right: 30px;
  }
  img {
    @include flex_center;
    // width: 65px;
    // height: 32px;
    height: 25px;
  }
  p {
    // @include fs-2;
    // @include fw-7;
    @include fs-3;
    @include fw-6;
    color: $w;
  }
}
.h_menu {
  height: 100%;
  .gnb_wrap {
    @include flex_around;
    position: relative;
    gap: rem(15px);
    height: 100%;
    @media screen and (max-width: 1025px) {
      gap: 7px;
    }
    .gnb_menu {
      @include flex_center;
      justify-content: flex-start;
      position: relative;
      height: 100%;
      > a {
        @include flex_center;
        padding: 0 rem(10px);
        height: 100%;
      }
      > a::after {
        content: "";
        display: block;
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: $header-on;
        mix-blend-mode: soft-light;
        z-index: 1;
        opacity: 0;
        transition: 0.5s;
      }
      > a[aria-current="page"]::after,
      > a:hover::after {
        opacity: 1;
      }
    }
  }
  .gnb_text {
    position: relative;
    line-height: 1em;
    color: $w;
    font-weight: 400;
    font-size: 15px;
    z-index: 2;
    transition: 0.3s;
    &:hover {
      cursor: pointer;
    }
  }
}
.lnb_wrap {
  @include flex_start;
  position: absolute;
  top: 100%;
  padding-left: rem(10px);
  height: 30px;
  gap: 40px;
  white-space: nowrap;
}
.lnb_text {
  color: $lnb-txt;
  // font-size: rem(14px);
  font-size: 14px;
}
a[aria-current="page"] .lnb_text,
.lnb_text:hover {
  // @include fw-7;
  @include fw-6;
  cursor: pointer;
  color: $lnb-on;
}

.h_icon {
  @include flex_end;
  position: relative;
  gap: rem(20px);
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

.h_icon_menu {
  display: none;
  text-align: center;
  position: absolute;
  // top: 100%;
  top: calc(100% - 11px);
  right: 0;
  z-index: 100;
  .triangle {
    margin: 0 auto;
    width: 0;
    height: 18px;
    border-bottom: 7px solid #fff;
    border-top: 0px solid transparent;
    border-left: 5px solid transparent;
    border-right: 5px solid transparent;
  }
  ul {
    padding: 15px;
    background-color: #fff;
    border-radius: 0 0 5px 5px;
    box-shadow: 0 5px 8px rgba(#000, 0.2);
    li {
      @include flex_center;
      gap: 8px;
      cursor: pointer;
      span {
        font-size: rem(14px);
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
  // @include fw-7;
  cursor: pointer;
  color: $b3;
}

@media screen and (max-width: 1160px) {
  .HeaderComp_wrap .h_wrap .h_link .link-txt {
    display: none;
  }
}

.HeaderComp_wrap .h_wrap {
  .w880-block {
    display: none;
    align-items: center;
  }
  @media screen and (max-width: 880px) {
    .w880-none {
      display: none;
    }
    .w880-block {
      display: flex;
    }
  }
}

.dropdown {
  position: relative;
  display: inline-block;
  margin-left: 5px;
  height: 100%;
  z-index: 999;
  // 드롭다운 버튼 스타일
  .btn-menu {
    display: inline-block;
    width: 34px;
    height: 34px;
    background-color: transparent;
    background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" width="20" height="14.064" viewBox="0 0 20 14.064"><g id="menu" transform="translate(-4 -10)" opacity="0.8"><g id="Group_15" data-name="Group 15" transform="translate(4 10)"><path id="Path_44" data-name="Path 44" d="M4,11a1,1,0,0,1,1-1H23a1,1,0,0,1,0,2H5A1,1,0,0,1,4,11Z" transform="translate(-4 -10)" fill="%23fff"/><path id="Path_45" data-name="Path 45" d="M4,23.064a1,1,0,0,1,1-1H23a1,1,0,0,1,0,2H5A1,1,0,0,1,4,23.064Z" transform="translate(-4 -16.032)" fill="%23fff"/><path id="Path_46" data-name="Path 46" d="M5,34.128a1,1,0,0,0,0,2H23a1,1,0,0,0,0-2Z" transform="translate(-4 -22.064)" fill="%23fff"/></g></g></svg>');
    background-size: auto 14px;
    background-repeat: no-repeat;
    background-position: center;
    font-size: 0;
    text-indent: -99px;
    overflow: hidden;
    cursor: pointer;
    transition: 0.2s;
  }
  .btn-menu.active {
    background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" width="14" height="13.999" viewBox="0 0 14 13.999"><g id="close" transform="translate(-9.034 -9.036)" opacity="0.8"><g id="Group_16" data-name="Group 16" transform="translate(9.034 9.036)"><path id="Path_47" data-name="Path 47" d="M10.632,9.31a.935.935,0,1,0-1.323,1.323l5.4,5.4-5.4,5.4a.935.935,0,1,0,1.323,1.323l5.4-5.4,5.4,5.4a.935.935,0,1,0,1.323-1.323l-5.4-5.4,5.4-5.4A.935.935,0,1,0,21.437,9.31l-5.4,5.4-5.4-5.4Z" transform="translate(-9.034 -9.036)" fill="%23fff"/></g></g></svg>');
  }

  // 드롭다운 메뉴 스타일
  .drop-menu {
    display: none;
    position: absolute;
    top: 100%;
    right: -17px;
    width: 120px;
    gap: 5px;
    padding: 10px;
    background-color: #475a9c;
    border-radius: 0 0 10px 10px;
    z-index: 1;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.35);
    .h_link,
    .h_ico {
      display: inline-flex !important;
      align-items: center;
    }
    .h_ico {
      width: 20px;
      img {
        height: 14px;
      }
    }
    b {
      display: inline-block;
      width: 106px;
      white-space: nowrap;
      text-overflow: ellipsis;
      overflow: hidden;
    }
    .h_link.logout {
      margin-left: 0;
    }

    .in-menu li {
      padding-left: 25px;
      a {
        color: #fff;
        font-size: 12px;
        font-weight: normal;
        opacity: 1;
        &:hover {
          opacity: 0.7;
        }
      }
    }
  }

  .btn-menu.active + .drop-menu {
    display: flex !important;
    flex-direction: column;
  }
}
</style>

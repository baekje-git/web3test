<template>
  <div class="HeaderLayout_wrap">
    <div class="Header_title">
      <router-link to="/">
        <div class="h_logo">
          <span> <img :src="imgSrc" alt="logo" /></span>
        </div>
      </router-link>
    </div>
    <div class="CountBox_wrap">
      <!-- <CountBoxComp_mobile
        :current-day="dlvTimeData.curYmd"
        :current-time="dlvTimeData.curHms"
        :end-day="dlvTimeData.endYmd"
        :end-time="dlvTimeData.endHm"
        :countdown="countdown"
        :endday="endday"
        :dlvday="dlvday"
        :dlvtime="dlvTimeData.dlvTime"
      /> -->
      <CountBoxComp3_mobile
        v-if="isRemain1Hour"
        countbox_style="red"
        :current-day="dlvTimeData.curYmd"
        :current-time="dlvTimeData.curHms"
        :end-day="dlvTimeData.endYmd"
        :end-time="dlvTimeData.endHm"
        :countdown="countdown"
        :endday="endday"
        :dlvday="dlvday"
        :dlvtime="dlvTimeData.dlvTime"
      />
      <CountBoxComp2_mobile
        v-else
        :current-day="dlvTimeData.curYmd"
        :current-time="dlvTimeData.curHms"
        :end-day="dlvTimeData.endYmd"
        :end-time="dlvTimeData.endHm"
        :countdown="countdown"
        :endday="endday"
        :dlvday="dlvday"
        :dlvtime="dlvTimeData.dlvTime"
      />
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, computed, inject, watch, onUnmounted } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";

import CountBoxComp_mobile from "src/components/mobile/__commonComp/countboxComp/CountBoxComp_mobile.vue";
import CountBoxComp2_mobile from "src/components/mobile/__commonComp/countboxComp/CountBoxComp2_mobile.vue";
import CountBoxComp3_mobile from "src/components/mobile/__commonComp/countboxComp/CountBoxComp3_mobile.vue";

const userInfo = util.getUserData();
const countdown = ref("");
const endday = ref("");
const dlvday = ref("");
const intervalId = ref(null);
const loginImgData = ref(null);
const isRemain1Hour = ref("");
const imgSrc = computed(() => {
  const basePath = "assets/images/";
  if (loginImgData.value?.LOGO_IMG_MOBILE) {
    return `${basePath}${loginImgData.value.LOGO_IMG_MOBILE}`; // DB에서 가져온 파일명 적용
  }

  if (userInfo.BRCH_CD === "41") {
    return `${basePath}${loginImgData.value?.LOGO_IMG_MOBILE}`; // return 문 빠져있었음
  } else if (userInfo.BRCH_CD === "45") {
    return `${basePath}${loginImgData.value?.LOGO_IMG_MOBILE}`;
  } else if (userInfo.BRCH_CD === "49") {
    return `${basePath}${loginImgData.value?.LOGO_IMG_MOBILE}`;
  } else if (userInfo.BRCH_CD === "51") {
    return `${basePath}${loginImgData.value?.LOGO_IMG_MOBILE}`;
  } else if (userInfo.BRCH_CD === "52") {
    return `${basePath}${loginImgData.value?.LOGO_IMG_MOBILE}`;
  } else if (userInfo.BRCH_CD === "53") {
    return `${basePath}${loginImgData.value?.LOGO_IMG_MOBILE}`;
  } else if (userInfo.BRCH_CD === "54") {
    return `${basePath}${loginImgData.value?.LOGO_IMG_MOBILE}`;
  } else if (userInfo.BRCH_CD === "57") {
    return `${basePath}${loginImgData.value?.LOGO_IMG_MOBILE}`;
  } else if (userInfo.BRCH_CD === "58") {
    return `${basePath}${loginImgData.value?.LOGO_IMG_MOBILE}`;
  } else if (userInfo.BRCH_CD === "44") {
    return `${basePath}${loginImgData.value?.LOGO_IMG_MOBILE}`;
  }

  return `${basePath}${loginImgData.value?.LOGO_IMG_MOBILE}`; // 기본 로고
});
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
async function selectLoginMainVar() {
  try {
    const res = await api.get("/main/selectLoginMainVar", {
      params: { brchCd: userInfo.BRCH_CD },
    });

    loginImgData.value = res; // DB에서 받은 데이터 저장
  } catch (error) {
    console.error("API 요청 중 에러 발생:", error);
  }
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

  //if (curYmd === endYmd) {
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
    //} else {
    // isRemain1Hour.value = false;
    //}
  }
}

watch(isRemain1Hour, (val) => {
  clearInterval(intervalId.value);
  // if (val) {
  //   intervalId.value = setInterval(() => {
  //     getDlvTime();
  //   }, 60000);
  // } else {
  //   intervalId.value = setInterval(() => {
  //     getDlvTime();
  //   }, 60000);
  // }
});

onMounted(async () => {
  await getDlvTime();
  await selectLoginMainVar();
  // if (isRemain1Hour.value) {
  //   intervalId.value = setInterval(() => {
  //    console.log("마운트 1초 백엔드 시간")
  //    getDlvTime();
  //   }, 1000);
  // } else {
  //   intervalId.value = setInterval(() => {
  //    console.log("마운트 1분 백엔드 시간")
  //    getDlvTime();
  //   }, 60000);
  // }
});

onUnmounted(() => {
  clearInterval(intervalId.value);
});
</script>
<style lang="scss" scoped>
.HeaderLayout_wrap {
  @include flex_between;
  padding: 16px 20px;
  background-color: $header-bg;
}
.Header_title span {
  @include fs-3;
  @include fw-7;
  color: $b3;
}

.CountBox_wrap {
  @include flex_center;
  gap: 8px;
  .q-carousel {
    background: none;
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
</style>

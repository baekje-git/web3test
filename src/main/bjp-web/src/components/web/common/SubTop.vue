<template>
  <!-- 공지사항, 배송마감/ -->
  <div class="wrap_rt">
    <div class="notice_wrap">
      <div class="tit">
        <span><img src="assets/images/ico_notice.svg" /></span>
        <span>공지사항</span>
      </div>
      <swiper class="list" v-bind="noticeSwiper" :modules="modules">
        <swiper-slide v-for="(row, index) in rows" :key="index">
          <div class="list_item" @click="(e) => onRowClick(e, row, index)">
            {{ row.TTL }}
          </div>
        </swiper-slide>
      </swiper>
      <div class="btn_wrap">
        <button type="button" class="btn_arrw prev swipePrev"></button>
        <button type="button" class="btn_arrw next swipeNext"></button>
      </div>
    </div>

    <div class="time">
      <div class="h_time">
        <CountBoxComp_default3
          :current-day="dlvTimeData.curYmd"
          :current-time="dlvTimeData.curHms"
          :end-day="dlvTimeData.endYmd"
          :endTime="dlvTimeData.endHm"
          :dlvtime="dlvTimeData.dlvTime"
          :countdown="countdown"
          :endday="endday"
          :dlvday="dlvday"
          :remainHms="dlvTimeData.remainHms"
        />
      </div>
    </div>
  </div>
</template>
<script setup>
import "swiper/scss";
import CountBoxComp_default from "src/components/web/common/CountBoxComp_default.vue";
import CountBoxComp_default2 from "src/components/web/common/CountBoxComp_default2.vue";
import CountBoxComp_default3 from "src/components/web/common/CountBoxComp_default3.vue";
import { ref, defineProps, onMounted, inject, watch } from "vue";
import { api } from "boot/axios";
import { Swiper, SwiperSlide } from "swiper/vue";
import { Autoplay, Mousewheel, Navigation } from "swiper/modules";
import * as util from "src/support/util";
import "moment/locale/ko";
import moment from "moment";
moment.locale("ko");
const bus = inject("bus");
const modules = [Autoplay, Mousewheel, Navigation];
const isRemain1Hour = ref("");
const countdown = ref("");
const userInfo = util.getUserData();
const endday = ref("");
const dlvday = ref("");
const intervalId = ref(null);
const noticeSwiper = {
  direction: "vertical",
  slidesPerView: 2,
  spaceBetween: 32,
  loop: true,
  autoplay: {
    delay: 3000,
    disableOnInteraction: false,
  },
  navigation: {
    prevEl: ".swipePrev",
    nextEl: ".swipeNext",
  },
};
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
const rows = ref([]);
const time = ref([]);
async function fetchData() {
  const res = await api.get("/myPage/notice");

  rows.value = res;
}
// row 클릭
async function onRowClick(evt, row, index) {
  await bus.emit("noticeDtlPopupOpen", row.SEQ_NO);
}

// 배송마감시간 조회
async function getDlvTime() {
  const res = await api.get("/main/getDlvTime", {
    params: { custCd: userInfo.CUST_CD },
  });
  console.log("getDlvTime res =", res);
  time.value = res;
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
  dlvTimeData.value.remainHms = res.REMAIN_HMS;

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

onMounted(async () => {
  await fetchData();
  await getDlvTime();
});
</script>
<style lang="scss" scoped>
.page_left .wrap_rt {
  @include flex_end;
  // width: 40%;
  padding-left: rem(30px);
}

.wrap_rt .notice_wrap {
  @include flex_start;
  // width: calc(100% - #{rem(245px)});
  .tit {
    @include flex_start;
    gap: 8px;
    line-height: 1;
    color: $gray-1;
    font-size: 14px;
    font-weight: 700;
    img {
      vertical-align: middle;
    }
    // span:nth-child(2) {
    //   transform: translateY(1px);
    // }
  }

  .list {
    position: relative;
    // padding-top: 6px;
    margin-left: 10px;
    // width: calc(100% - #{rem(145px)});
    width: 170px;
    height: 32px;
    color: $gray-1;
    // font-size: rem(14px);
    font-size: 14px;
    overflow: hidden;
    .list_item {
      // @include flex_start;
      display: block;
      width: 100%;
      height: 32px;
      line-height: 32px;
      white-space: nowrap;
      text-overflow: ellipsis;
      overflow: hidden;
      cursor: pointer;
    }
  }

  .btn_wrap {
    @include flex_start;
    margin-left: 12px;
    gap: 8px;
    .btn_arrw {
      width: 14px;
      height: 14px;
      background-color: #fff;
      border: 1px solid $gray-3;
      border-radius: 50%;
      background-size: 4px auto;
      background-position: center;
      background-repeat: no-repeat;
      cursor: pointer;
      transition: 0.3s;
      &.prev {
        background-image: url("assets/images/arrw_prev.svg");
      }
      &.next {
        background-image: url("assets/images/arrw_next.svg");
      }
      &:hover {
        background-color: $gray-e;
      }
    }
  }
}

.wrap_rt .delivery {
  @include flex_center;
  // margin-left: rem(40px);
  margin-left: rem(20px);
  padding: 0 rem(18px);
  height: rem(32px);
  background-color: $red-300;
  .ico {
    height: rem(24px);
    img {
      height: 100%;
    }
  }
  .time {
    padding-left: 6px;
    font-size: rem(14px);
    font-weight: 600;
    color: #fff;
  }
}
.wrap_rt .delivery2 {
  @include flex_center;
  // margin-left: rem(40px);
  margin-left: rem(20px);
  padding: 0 rem(18px);
  height: rem(32px);
  background-color: #17328f;
  .ico {
    height: rem(24px);
    img {
      height: 100%;
    }
  }
  .time {
    padding-left: 6px;
    font-size: rem(14px);
    font-weight: 600;
    color: #fff;
  }
}
</style>

<template>
  <div class="Side_wrap">
    <swiper v-bind="swiperOptions" :modules="modules">
      <swiper-slide class="banner" v-for="(banner, i) in banners" :key="i">
        <img @click="openDialog(banner)" :src="banner.src" :alt="banner.alt" />
      </swiper-slide>
    </swiper>

    <q-dialog
      v-model="dialog_side"
      class="dialog_side_wrap"
      transition-show="fade"
      transition-hide="fade"
    >
      <div class="dialog_side">
        <div class="iframe_wrap" v-if="selectedBanner.iframe !== ''">
          <iframe
            :src="selectedBanner.iframe"
            width="1280px"
            height="1150px"
            allowfullscreen
            scrolling="yes"
            sandbox
            @load="resizeImage"
          ></iframe>
        </div>
        <div class="img_wrap" v-else>
          <img
            :src="selectedBanner.dialog_img"
            :alt="selectedBanner.alt"
            v-if="selectedBanner"
          />
        </div>
        <div class="dialog_bottom_wrap">
          <div class="dialog_text_left">
            <div class="">
              <div class="flex_start">
                <span class="name">
                  {{ `${selectedBanner.itemNm} ${selectedBanner.unit}` }}
                </span>
              </div>
              <div class="flex_between">
                <span class="price">
                  {{ util.getNumberFormat(selectedBanner.wp2Amt) }} 원
                </span>

                <div class="qty_wrap">
                  <button type="button" class="btn_qty" @click="data.count--">
                    <i class="ico qty-minus"></i>
                  </button>
                  <q-input
                    v-model="data.count"
                    class="q-table_number"
                    borderless
                    dense
                    bg-color="white"
                    input-class="text-center"
                    reverse-fill-mask
                    mask="###,###,###"
                    @update:model-value="onUpdate()"
                    @focus="(input) => input.target.select()"
                  />
                  <button type="button" class="btn_qty" @click="data.count++">
                    <i class="ico qty-plus"></i>
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div class="dialog_btn_right">
            <q-btn
              v-if="
                selectedBanner.URL_LARGE_LINK &&
                selectedBanner.URL_LARGE_LINK.trim() !== ''
              "
              class="dialog_btn_default1 mr10"
              label="상세정보 열기"
              @click="openDetailLink"
            />
            <q-btn
              class="dialog_btn_default"
              label="장바구니 담기"
              @click="onClickAddBasket"
            />
          </div>
        </div>
        <div class="flex_end btnClose">
          <BtnComp_active class="btn-fill" btn-label="닫기" v-close-popup />
        </div>
      </div>
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, inject, watch } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { useRouter, useRoute } from "vue-router";

import BtnComp_active from "src/components/web/common/BtnComp_active.vue";

import { Swiper, SwiperSlide } from "swiper/vue";
import { Autoplay, Mousewheel } from "swiper/modules";
import "swiper/scss";

const userInfo = util.getUserData();
const router = useRouter();
const route = useRoute();
const bus = inject("bus");

const modules = [Autoplay, Mousewheel];

const dialog_side = ref(false);
const banners = ref([]);
const openDetailLink = () => {
  let link = selectedBanner.value.URL_LARGE_LINK;
  if (link && link.trim() !== "") {
    // https:// 또는 http:// 안 붙어 있으면 붙여주기
    if (!/^https?:\/\//i.test(link)) {
      link = "https://" + link;
    }
    window.open(link, "_blank");
  }
};
const selectedBanner = ref({
  src: "",
  alt: "",
  dialog_img: "",
  iframe: "",
});

const swiperOptions = {
  direction: "vertical",
  slidesPerView: 6,
  spaceBetween: 15,
  mousewheel: true,
  loop: true,
  autoplay: {
    delay: 10000,
    disableOnInteraction: false,
  },
};

const openDialog = (banner) => {
  dialog_side.value = true;
  selectedBanner.value = banner;
};

const data = reactive({
  count: 0,
});

const dataParam = ref({
  custCd: userInfo.CUST_CD,
  dlvBrchCd: userInfo.DLV_BRCH_CD,
  saveItemCd: "",
  saveItemGbCd: "01",
  basketGbCd: "01",
  saveItemQty: 0,
});

//---------------------------- Function ------------------------------//

watch(() => {
  if (dialog_side.value) {
    data.count = 0;
  }
});

async function getBannerItemInfo() {
  const res = await api.get("/main/bannerItemInfo", {
    params: { custCd: userInfo.CUST_CD },
  });

  if (res.length > 0) {
    res.forEach((m) => {
      banners.value.push({
        src: m.URL_SMALL,
        dialog_img: m.URL_LARGE,
        itemCd: m.ITEM_CD,
        itemNm: m.ITEM_NM,
        unit: m.UNIT,
        wp2Amt: m.WP2_AMT,
        iframe: m.URL_LARGE,
        gDlvBrchCd: m.DLV_BRCH_CD,
        URL_LARGE_LINK: m.URL_LARGE_LINK,
      });
    });
  }
  banners.value.forEach((banner) => {
    if (
      !banner.src.endsWith(".jpg") &&
      !banner.src.endsWith(".png") &&
      !banner.src.endsWith(".gif")
    ) {
      banner.src += ".png";
    }

    if (
      !banner.dialog_img.endsWith(".jpg") &&
      !banner.dialog_img.endsWith(".png") &&
      !banner.dialog_img.endsWith(".gif")
    ) {
      banner.dialog_img += ".png";
    }

    if (
      banner.iframe.endsWith(".jpg") ||
      banner.iframe.endsWith(".png") ||
      banner.iframe.endsWith(".gif")
    ) {
      banner.iframe = "";
    }
  });
}

// 장바구니 담기 클릭
async function onClickAddBasket() {
  if (data.count == 0 || isNaN(data.count)) {
    alert("수량을 선택해 주시기 바랍니다.");
    data.count = 0;
    return;
  }
  //담기 시 장바구니 체크
  const res = await api.post("/ord/getBasketDupOrd2", {
    custCd: userInfo.CUST_CD,
    basketGbCd: "01",
    saveItemCd: selectedBanner.value.itemCd,
    gDlvBrchCd: selectedBanner.value.gDlvBrchCd,
  });

  if (res.ITEM_CNT == 0) {
    if (basketQty.value >= 100) {
      alert(
        "장바구니에 100개 품목을 초과하여 담을 수 없습니다. 기존 품목 주문등록 후 다시 담기 바랍니다"
      );
      data.count = 0;
      return;
    }
  } else {
    const confirmMsg =
      "장바구니에 이미 담은 품목입니다. 수량 추가하시겠습니까?";
    if (!confirm(confirmMsg)) return;
  }

  try {
    dataParam.value.saveItemCd = selectedBanner.value.itemCd;
    dataParam.value.saveItemQty = data.count;

    await api.post("/main/addBasket", dataParam.value);

    alert("선택한 품목이 장바구니에 담겼습니다.");

    bus.emit("refreshHeaderBasket"); // 헤더 카운트 새로고침
    dialog_side.value = false; // 팝업 닫음

    if (route.path === "/comOrd") {
      // 현재 페이지가 통합주문이면 장바구니만 새로고침
      bus.emit("refreshBasket");
    } else {
      // 그 외 페이지면 통합주문페이지로 이동
      await router.push("/comOrd");
    }
  } catch (error) {
    alert(error.response.data);
  }
}

const plus = () => {
  data.count++;
};

const minus = () => {
  if (data.count > 0) {
    data.count--;
  }
};

const onUpdate = (val) => {
  let newValue = val.replace(/,/g, "");
  newValue = newValue.replace(/^0+(?!$)/, "");
  newValue = parseInt(newValue);
  data.count = newValue;
};

const update = (event) => {
  if (!isNaN(event.target.value)) {
    data.count = event.target.value;
  } else {
    data.count = 0;
  }
};

const basketQty = ref("");
onMounted(async () => {
  await getBannerItemInfo();
  bus.on("BasketQty", (data) => {
    basketQty.value = data;
  });
});
</script>

<style lang="scss">
.Side_wrap {
  position: relative;
  .swiper {
    height: 100%;
    padding: 0;
  }
}

.banner {
  @include flex_center;
  width: 90px !important;
  height: 90px !important;
  background-color: #fff;
  border: 1px solid $line-300;
  border-radius: 0;
  overflow: hidden;
  img {
    width: 100%;
    height: auto;
    cursor: pointer;
  }
}
.mr10 {
  margin-right: 10px;
}
.banner:last-child {
  margin-bottom: 0;
}

.dialog_side_wrap {
  .dialog_side {
    position: relative;
    display: grid;
    grid-template-columns: 45% 55%;
    width: 730px;
    max-width: 90vh;
    max-height: 80vh;
    padding: 30px;
    background-color: $w;
    border: 1px solid #c2d0f3;
    border-radius: 0px;
    -ms-overflow-style: none; /* 인터넷 익스플로러 */
    scrollbar-width: none; /* 파이어폭스 */
    &::-webkit-scrollbar {
      display: none;
    }
    .iframe_wrap {
      position: relative;
      display: flex;
      justify-content: start;
      align-items: start;
      flex-direction: column;
      width: 300px;
      height: 300px;
      max-width: 100%;
      max-height: 100%;
      overflow: auto;
      -ms-overflow-style: none; /* 인터넷 익스플로러 */
      scrollbar-width: none; /* 파이어폭스 */
      iframe {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        z-index: 1;
        transform: scale(0.56);
        transform-origin: left top;
        background-color: #fff;
      }
    }
    .img_wrap {
      grid-row: span 2;
      width: 300px;
      height: 300px;
      max-width: 100%;
      max-height: 100%;
      img {
        width: 100%;
        height: 100%;
        // margin-bottom: 6px;
      }
    }
    .dialog_bottom_wrap {
      padding: 4px 0 0 20px;
      .dialog_text_left {
        @include flex_start;
        gap: 16px;
        width: 100%;
        > div {
          width: 100%;
        }
        .name,
        .price {
          @include fs-3;
          display: block;
          text-align: left;
        }
        .price {
          color: $primary;
          font-weight: 500;
        }
        .name {
          @include fw-6;
          word-break: break-word;
        }

        .qty_wrap {
          width: auto;
          height: auto;
          .q-field__control {
            height: 32px;
          }
          .q-field__native {
            font-size: rem(17px);
          }
        }
        .EAComp_wrap {
          @include flex_center;
          width: 90px;
          height: 32px;
          padding: 0 2px;
          border: 1px solid #666666;
          border-radius: 2.4px;
          .EA_minus,
          .EA_plus {
            @include flex_center;
            cursor: pointer;
            height: 24px;
            img {
              width: 12px;
            }
          }
          .EA_input {
            @include fs-4;
            width: 65px;
            text-align: center;
            border: none;
          }
        }
      }
    }

    .btnClose {
      position: absolute;
      right: 30px;
      bottom: 30px;
    }
  }
  .q-dialog__backdrop {
    background-color: transparent !important;
  }
  .dialog_btn_right {
    margin-top: 30px;
    padding-top: 30px;
    border-top: 1px solid $line-300;
    .dialog_btn_default {
      // @include gradation;
      background-color: $primary;
      padding: 0 46px;
      color: $w;
      border-radius: 0;
      height: 40px;
      &::after,
      &::before {
        box-shadow: none;
      }
    }
    .dialog_btn_default span {
      font-size: rem(17px);
      color: $w !important;
    }
    .dialog_btn_default1 {
      background-color: #fff; // 흰색 배경
      color: #000; // 글씨색 검정
      border: 1px solid #000; // 테두리 검정
      padding: 0 26px;
      border-radius: 0;
      height: 40px;

      &::after,
      &::before {
        box-shadow: none;
      }
    }

    .dialog_btn_default1 span {
      font-size: rem(17px);
      color: #000 !important; // 글씨 검정으로 강제
    }
  }
  .dialog_btn_left {
    .dialog_btn_default {
      background-color: #999999;
      padding: 0 16px;
      color: $w;
      border-radius: 0;
      height: 40px;
      &::after,
      &::before {
        box-shadow: none;
      }
    }
    .dialog_btn_default span {
      @include fs-4;
      color: $w !important;
    }
  }
}
</style>

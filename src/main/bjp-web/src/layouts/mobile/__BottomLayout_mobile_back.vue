<template>
  <div class="BottomLayout_wrap">
    <div class="Bottom_wrap">
      <ul>
        <li v-for="(items, i) in menuItems" :key="i">
          <div class="Bottom_btn_wrap">
            <q-btn
              flat
              dense
              :to="items.link"
              @click="dialog_model = items.dialog_on"
            >
              <img :src="items.icon" alt="icon" />
              <q-badge
                color="red"
                rounded
                floating
                v-if="items.badge_on === true"
              >
                {{ items.badge }}
              </q-badge>
            </q-btn>
            <p>{{ items.title }}</p>
          </div>
        </li>
      </ul>
    </div>
  </div>

  <MenuDialogLayout_mobile
    dialog_title="전체 메뉴"
    v-model:dialog_model_prop="dialog_model"
    :dialog_full="dialog_full"
    dialog_position="bottom"
    dialog_style="Dialog_menu"
  >
    <MenuComp_mobile />
  </MenuDialogLayout_mobile>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, onMounted, computed, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import MenuDialogLayout_mobile from "src/layouts/mobile/DialogLayouts/MenuDialogLayout_mobile.vue";
import MenuComp_mobile from "src/components/mobile/MenuComp/MenuComp_mobile.vue";

const userInfo = util.getUserData();
const dialog_model = ref(false);
const dialog_full = ref(true);
const ordBasketCount = computed(() => ordCount.value);
const soldoutInYn = ref(false);
const bus = inject("bus");

const menuItems = [
  {
    link: "",
    title: "전체메뉴",
    icon: "assets/icons/bars-solid.svg",
    badge: "",
    badge_on: false,
    dialog_on: true,
  },
  {
    link: "/comOrd",
    title: "통합주문",
    icon: "assets/icons/mobile-screen-solid.svg",
    badge: "",
    badge_on: false,
  },
  {
    link: "/ordBasket",
    title: "장바구니",
    icon: "assets/icons/cart-shopping-solid.svg",
    badge: ordBasketCount,
    badge_on: true,
  },
  {
    link: "/interest",
    title: "관심상품",
    icon: "assets/icons/heart-solid.svg",
    badge: "",
    badge_on: soldoutInYn.value,
  },
  {
    link: "/myPage",
    title: "마이페이지",
    icon: "assets/icons/user-solid.svg",
    badge: "3",
    badge_on: false,
  },
];

const ordCount = ref(0);

//---------------------------- Function ------------------------------//

// 주문 장바구니 count조회
async function getBasketCount() {
  if (userInfo.CUST_CD !== "") {
    const res = await api.get("/main/basketCount", {
      params: { custCd: userInfo.CUST_CD },
    });

    ordCount.value = res.ORD_COUNT;
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

bus.on("refreshMobileBasket", async () => {
  await getBasketCount();
});

onMounted(async () => {
  if (userInfo.CUST_CD !== "") {
    await getBasketCount();
    await getSoldOutInYn();
  }
});
</script>

<style lang="scss" scoped>
.BottomLayout_wrap {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: $w;
}
.Bottom_wrap {
  min-width: 360px;
  height: 56px;
  padding: 7px 0 3px;
  box-shadow: 0 -1px 6px 0 rgba(0, 0, 0, 0.16);
  border-top: solid 1px #ccc;
}
.Bottom_wrap ul {
  @include flex_center;
  gap: 20px;
}
.Bottom_wrap ul li:last-child p {
  @include fs-7;
}
.Bottom_btn_wrap p {
  @include fs-6;
  @include fw-7;
  color: #999999;
  line-height: 14px;
}
.Bottom_btn_wrap {
  text-align: center;
}
</style>

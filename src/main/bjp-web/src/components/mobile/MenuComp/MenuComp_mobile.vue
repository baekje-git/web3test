<template>
  <div class="q-tab_wrap">
    <q-splitter>
      <template v-slot:before>
        <q-tabs
          v-model="menuItem"
          vertical
          active-class="q-tabs_active"
          indicator-color="transparent"
          v-for="(tab_items, i) in tabItems"
          :key="i"
        >
          <q-tab class="q-tab" :name="tab_items.name" style="padding: 0">
            <div class="q-tab_items">
              <img :src="activeTabIcon[i]" alt="icon" />
              <p>
                {{ tab_items.label }}
              </p>
            </div>
          </q-tab>
        </q-tabs>
      </template>

      <template v-slot:after>
        <q-tab-panels
          class="q-tab-panels_wrap"
          v-model="menuItem"
          animated
          swipeable
          vertical
        >
          <q-tab-panel
            v-for="(panel_items, i) in tabItems"
            :key="i"
            :name="panel_items.name"
          >
            <ul>
              <!-- <li
                @click="$router.push(list_items.list_link)"
                class="q-panel_list_wrap"
                v-for="(list_items, j) in panel_items.list"
                :key="j"
                v-close-popup
              > -->
              <li
                @click="onClickMenu(list_items.list_link)"
                class="q-panel_list_wrap"
                v-for="(list_items, j) in panel_items.list"
                :key="j"
                v-close-popup
              >
                <p>
                  {{ list_items.list_title }}
                </p>
                <p>
                  <img src="assets/icons/arrw_right.svg" alt="icon" />
                </p>
              </li>
            </ul>
          </q-tab-panel>
        </q-tab-panels>
      </template>
    </q-splitter>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { Cookies } from "quasar";

const userInfo = util.getUserData();
const router = useRouter();
const menuItem = ref("order");
const loginMisData = ref(null);
const getTabIcon = (tab) => {
  return menuItem.value === tab.name ? tab.icon_active : tab.icon;
};

const activeTabIcon = computed(() => {
  return tabItems.value.map((tab) => getTabIcon(tab));
});

async function onClickMenu(menu) {
  if (menu === "logout") {
    Cookies.remove("autoLoginData");
    util.logout();
    router.push("/login");
    util.showNotify("로그아웃 되었습니다.");
  } else {
    await router.push(menu);
  }
}

const tabItems = ref([]);
async function selectLogin() {
  try {
    const res = await api.get("/main/selectLoginNoPwd", {
      params: { loginId: userInfo.LOGIN_ID, userId: userInfo.LOGIN_ID },
    });

    loginMisData.value = res; // DB에서 받은 데이터 저장
    if (userInfo.USER_GB_CD === "30") {
      tabItems.value = [
        {
          icon: "assets/icons/m-menu01.svg",
          icon_active: "assets/icons/m-menu01_on.svg",
          name: "order",
          label: "주문",
          list: [
            {
              list_title: "통합주문",
              list_link: "/comOrd",
            },
            {
              list_title: "장바구니",
              list_link: "/ordBasket",
            },
            {
              list_title: "관심상품",
              list_link: "/interest",
            },
          ],
        },
        {
          icon: "assets/icons/m-menu03.svg",
          icon_active: "assets/icons/m-menu03_on.svg",
          name: "sale",
          label: "매출원장",
          list: [
            {
              list_title: "거래원장",
              list_link: "/ordLedger",
            },
            {
              list_title: "품목수불",
              list_link: "/pmntItem",
            },
          ],
        },
        {
          icon: "assets/icons/m-menu04.svg",
          icon_active: "assets/icons/m-menu04_on.svg",
          name: "my",
          label: "마이페이지",
          list: [
            {
              list_title: "마이페이지",
              list_link: "/myPage",
            },
            {
              list_title: "로그아웃",
              list_link: "logout",
            },
          ],
        },
        // {
        //   icon: "assets/icons/ellipsis-solid.svg",
        //   icon_active: "assets/icons/active/ellipsis-solid-active.svg",
        //   name: "etc",
        //   label: "더보기",
        //   list: [
        //     {
        //       list_title: "일일거래내역서",
        //       list_link: "/daily",
        //     },
        //   ],
        // },
      ];
    } else {
      tabItems.value = [
        {
          icon: "assets/icons/m-menu01.svg",
          icon_active: "assets/icons/m-menu01_on.svg",
          name: "order",
          label: "주문",
          list: [
            {
              list_title: "통합주문",
              list_link: "/comOrd",
            },
            {
              list_title: "장바구니",
              list_link: "/ordBasket",
            },
            {
              list_title: "관심상품",
              list_link: "/interest",
            },
          ],
        },
        {
          icon: "assets/icons/m-menu02.svg",
          icon_active: "assets/icons/m-menu02_on.svg",
          name: "return",
          label: "반품",
          list: [
            {
              list_title: "반품",
              list_link: "/return",
            },
            {
              list_title: "반품 장바구니",
              list_link: "/returnBasket",
            },
            {
              list_title: "반품현황",
              list_link: "/bakReq",
            },
            {
              list_title: "반품상세",
              list_link: "/bakReqDetail",
            },
          ],
        },
        {
          icon: "assets/icons/m-menu03.svg",
          icon_active: "assets/icons/m-menu03_on.svg",
          name: "sale",
          label: "매출원장",
          list: [
            {
              list_title: "거래원장",
              list_link: "/ordLedger",
            },
            {
              list_title: "품목수불",
              list_link: "/pmntItem",
            },
          ],
        },
        {
          icon: "assets/icons/m-menu04.svg",
          icon_active: "assets/icons/m-menu04_on.svg",
          name: "my",
          label: "마이페이지",
          list: [
            {
              list_title: "마이페이지",
              list_link: "/myPage",
            },
            {
              list_title: "로그아웃",
              list_link: "logout",
            },
          ],
        },
        {
          icon: "assets/icons/m-menu05.svg",
          icon_active: "assets/icons/m-menu05_on.svg",
          name: "etc",
          label: "더보기",
          list: [
            {
              list_title: "담당자별 영업현황",
              list_link: "/personnel",
            },
            {
              list_title: "초당약품 매출현황",
              list_link: "/chodang",
            },
            {
              list_title: "거래처별 영업현황",
              list_link: "/customer",
            },
            {
              list_title: "일일거래내역서",
              list_link: "/daily",
            },
            {
              list_title: "수금계획",
              list_link: "/collection",
            },
            {
              list_title: "제품수불내역",
              list_link: "/receipt",
            },
            ...(res.MIS_YN === "Y"
              ? [{ list_title: "전지점 매출현황", list_link: "/mis" }]
              : []),
          ],
        },
      ];
    }
  } catch (error) {
    console.error("API 요청 중 에러 발생:", error);
  }
}
onMounted(() => {
  selectLogin();
});
</script>

<style lang="scss" scoped>
.q-tab_wrap {
  // margin: -16px;
  // margin-bottom: -16px;
}
.q-tab {
  // @include border-b;
  border-bottom: 1px solid #f2f2f2;
}
.q-tab-panel {
  padding: 0;
}
.q-tab_items {
  width: 104px;
  padding: 16px 20px;
  text-align: left;
}
.q-tab_items p {
  @include fs-5;
  line-height: 160%;
}
.q-panel_list_wrap {
  @include flex_between;
  padding: 16px;
  // border-bottom: 1px solid $line-300;
}
.q-panel_list_wrap p {
  @include fs-5;
  color: $b;
}
.q-tabs_active {
  // @include gradation;
  background-color: $primary;
  color: $w;
}
</style>

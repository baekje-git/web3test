import MobileDetect from "mobile-detect";

const webRoutes = [
  {
    path: "/",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/order/BjpComOrdList.vue"),
      },
    ],
  },
  {
    path: "/comOrd",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/order/BjpComOrdList.vue"),
      },
    ],
  },
  {
    path: "/login",
    component: () => import("pages/web/auth/BjpWebLogin.vue"),
    // mobile: () => import("pages/auth/BjpMobileLogin.vue"),
  },
  {
    path: "/onuriLogin",
    component: () => import("pages/web/auth/OnuriLogin.vue"),
  },
  {
    path: "/shinhanLogin",
    component: () => import("pages/web/auth/ShinhanLogin.vue"),
  },

  {
    path: "/freOrd",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/fre/BjpFreOrdList.vue"),
      },
    ],
  },
  {
    path: "/ani",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/ani/BjpAniOrdList.vue"),
      },
    ],
  },
  {
    path: "/return",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/return/BjpReturnList.vue"),
      },
    ],
  },
  {
    path: "/ordLedger",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/ordLedger/BjpOrdLedgerList.vue"),
      },
    ],
  },
  {
    path: "/pmntItem",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/pmntItem/BjpPmntItemList.vue"),
      },
    ],
  },
  {
    path: "/bakReq",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/bakReq/BjpBakReqList.vue"),
      },
    ],
  },
  {
    path: "/bakReqDetail",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/bakReqDetail/BjpBakReqDetail.vue"),
      },
    ],
  },
  {
    path: "/clctItm",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/clctItm/BjpClctItemList.vue"),
      },
    ],
  },
  {
    path: "/payment",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/payment/BjpPayment.vue"),
      },
    ],
  },
  {
    path: "/mycard",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/payment/BjpMyCard.vue"),
      },
    ],
  },
  // {
  //   path: "/payment/simplePay",
  //   // component: () => import("layouts/web/common/BjpHeader.vue"),
  //   component: () => import("pages/web/payment/SimplePay.vue"),
  // },
  {
    path: "/ntfctnInf",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/insurance/BjpNtfctnInfList.vue"),
      },
    ],
  },
  {
    path: "/trgtItm",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/trgtItm/BjpTrgtItmList.vue"),
      },
    ],
  },
  {
    path: "/cmpnsDtl",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/cmpnsDtl/BjpCmpnsDtlList.vue"),
      },
    ],
  },
  {
    path: "/myPage",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/myPage/BjpMyPage.vue"),
      },
    ],
  },
  {
    path: "/faq",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/myPage/BjpMyHelpPage.vue"),
      },
    ],
  },
  {
    path: "/qna",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/myPage/BjpMyAnswerPage.vue"),
      },
    ],
  },
  {
    path: "/custMonth",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/myPage/BjpCustMonthList.vue"),
      },
    ],
  },
  {
    path: "/recently",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/myPage/BjpRecentlyList.vue"),
      },
    ],
  },
  {
    path: "/interest",
    // component: () => import("layouts/web/common/BjpHeader.vue"),
    component: () => import("layouts/web/common/HeaderLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/web/interest/BjpInterestItemList.vue"),
      },
    ],
  },
  {
    path: "/kakaoPay",
    component: () => import("components/web/payment/KakaoPayPopup.vue"),
  },
];

const mobileRoutes = [
  {
    path: "/login",
    component: () => import("pages/mobile/auth/loginPage_mobile.vue"),
  },

  {
    path: "/",
    component: () => import("pages/mobile/mainPage_mobile.vue"),
    children: [
      {
        path: "/comOrd",
        component: () => import("pages/mobile/order/OrderPage_a_mobile.vue"),
      },
      {
        path: "/ordBasket",
        component: () => import("pages/mobile/order/OrderPage_c_mobile.vue"),
      },

      {
        path: "/return",
        component: () => import("pages/mobile/return/ReturnPage_a_mobile.vue"),
      },
      {
        path: "/returnBasket",
        component: () => import("pages/mobile/return/ReturnPage_b_mobile.vue"),
      },

      {
        path: "/ordLedger",
        component: () => import("pages/mobile/sales/SalesPage_a_mobile.vue"),
      },
      {
        path: "/pmntItem",
        component: () => import("pages/mobile/sales/SalesPage_b_mobile.vue"),
      },
      {
        path: "/bakReq",
        component: () => import("pages/mobile/sales/SalesPage_c_mobile.vue"),
      },
      {
        path: "/bakReqDetail",
        component: () => import("pages/mobile/sales/SalesPage_d_mobile.vue"),
      },

      {
        path: "/myPage",
        component: () => import("pages/mobile/myPage/MyPage_mobile.vue"),
      },
      {
        path: "/interest",
        component: () => import("pages/mobile/myPage/MyInterest_mobile.vue"),
      },

      {
        path: "/personnel",
        component: () => import("pages/mobile/more/MorePersonnel_mobile.vue"),
      },
      {
        path: "/chodang",
        component: () => import("pages/mobile/more/MoreChodang_mobile.vue"),
      },
      {
        path: "/customer",
        component: () => import("pages/mobile/more/MoreCustomer_mobile.vue"),
      },
      {
        path: "/daily",
        component: () => import("pages/mobile/more/MoreDaily_mobile.vue"),
      },
      {
        path: "/collection",
        component: () => import("pages/mobile/more/MoreCollection_mobile.vue"),
      },
      {
        path: "/receipt",
        component: () => import("pages/mobile/more/MoreReceipt_mobile.vue"),
      },
      {
        path: "/mis",
        component: () => import("pages/mobile/more/MoreMis_mobile.vue"),
      },
    ],
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },

  // {
  //   path: '/TEST',
  //   component: () => import('pages/TEST.vue'),
  // },
];

let routes;
const md = new MobileDetect(window.navigator.userAgent);

if (md.mobile()) {
  console.log("CONNECTION WITH MOBILE");
  routes = mobileRoutes;
} else {
  console.log("CONNECTION WITH PC");
  routes = webRoutes;
}

export default routes;

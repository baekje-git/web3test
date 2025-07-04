import MobileDetect from "mobile-detect";

// const routes = [
const mobileRoutes = [
  {
    path: '/',
    component: () => import('pages/loginPage_mobile.vue'),
  },

  {
    path: '/mobile/mainOrder',
    component: () => import('pages/mainPage_mobile.vue'),
    children: [
      {
        // path: '/mobile/mainOrder',
        path: '',
        component: () => import('pages/1.order/OrderPage_a_mobile.vue')
      },
      {
        path: '/mobile/mainCart',
        component: () => import('pages/1.order/OrderPage_c_mobile.vue')
      },


      {
        path: '/mobile/mainReturn',
        component: () => import('pages/2.return/ReturnPage_a_mobile.vue')
      },
      {
        path: '/mobile/mainReturn/Cart',
        component: () => import('pages/2.return/ReturnPage_b_mobile.vue')
      },



      {
        path: '/mobile/mainSales',
        component: () => import('pages/3.sales/SalesPage_a_mobile.vue')
      },
      {
        path: '/mobile/mainSales/subul',
        component: () => import('pages/3.sales/SalesPage_b_mobile.vue')
      },
      {
        path: '/mobile/mainSales/state',
        component: () => import('pages/3.sales/SalesPage_c_mobile.vue')
      },
      {
        path: '/mobile/mainSales/detail',
        component: () => import('pages/3.sales/SalesPage_d_mobile.vue')
      },


      {
        path: '/mobile/mainMy',
        component: () => import('pages/4.my/MyPage_mobile.vue')
      },
      {
        path: '/mobile/mainMyInterest',
        component: () => import('pages/4.my/MyInterest_mobile.vue')
      },



      {
        path: '/mobile/more/personnel',
        component: () => import('pages/5.more/MorePersonnel_mobile.vue')
      },
      {
        path: '/mobile/more/chodang',
        component: () => import('pages/5.more/MoreChodang_mobile.vue')
      },
      {
        path: '/mobile/more/customer',
        component: () => import('pages/5.more/MoreCustomer_mobile.vue')
      },
      {
        path: '/mobile/more/daily',
        component: () => import('pages/5.more/MoreDaily_mobile.vue')
      },
      {
        path: '/mobile/more/collection',
        component: () => import('pages/5.more/MoreCollection_mobile.vue')
      },
      {
        path: '/mobile/more/receipt',
        component: () => import('pages/5.more/MoreReceipt_mobile.vue')
      },

    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  }

  // {
  //   path: '/TEST',
  //   component: () => import('pages/TEST.vue'),
  // },
]


const webRoutes = [];

let routes;
const md = new MobileDetect(window.navigator.userAgent);

if (md.mobile()) {
  console.log("CONNECTION WITH MOBILE");
  routes = mobileRoutes;
} else {
  console.log("CONNECTION WITH PC");
  routes = webRoutes;
}

export default routes

const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [{ path: "", component: () => import("pages/IndexPage.vue") }],
  },
  {
    path: "/inicisTest",
    component: () => import("layouts/MainLayout.vue"),
    children: [{ path: "", component: () => import("pages/InicisTest.vue") }],
    meta: { requiresAuth: true },
  },
  {
    path: "/sampleA",
    component: () => import("layouts/MainLayout.vue"),
    children: [{ path: "", component: () => import("pages/SampleA.vue") }],
    meta: { requiresAuth: true },
  },
  {
    path: "/sampleB",
    component: () => import("layouts/MainLayout.vue"),
    children: [{ path: "", component: () => import("pages/SampleB.vue") }],
    meta: { requiresAuth: true },
  },
  {
    path: "/sampleC",
    component: () => import("layouts/MainLayout.vue"),
    children: [{ path: "", component: () => import("pages/SampleC.vue") }],
    meta: { requiresAuth: true },
  },
  {
    path: "/sampleD",
    component: () => import("layouts/MainLayout.vue"),
    children: [{ path: "", component: () => import("pages/SampleD.vue") }],
    meta: { requiresAuth: true },
  },
  {
    path: "/sampleE",
    component: () => import("layouts/MainLayout.vue"),
    children: [{ path: "", component: () => import("pages/SampleE.vue") }],
    meta: { requiresAuth: true },
  },
  {
    path: "/sampleF",
    component: () => import("layouts/MainLayout.vue"),
    children: [{ path: "", component: () => import("pages/SampleF.vue") }],
    meta: { requiresAuth: true },
  },
  {
    path: "/sampleG",
    component: () => import("layouts/MainLayout.vue"),
    children: [{ path: "", component: () => import("pages/SampleG.vue") }],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-bpr",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpBprList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-bpr/detail",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpBprDetail.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-bpr/reg",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpBprReg.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-csfm",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpCsfmList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-csfm/detail",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpCsfmDetail.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-csfm/reg",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpCsfmReg.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-cmps",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpCmpsList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-cmps/detail",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpCmpsDetail.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-cmps/reg",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpCmpsReg.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-dsps",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpDspsList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-dsps/detail",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpDspsDetail.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-dsps/reg",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpDspsReg.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-pack",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpPackList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-pack/detail",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpPackDetail.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgp-pack/reg",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgp/FdgpPackReg.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgq-fnreq",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgq/FdgqFnreqList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgq-fnreq/detail",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgq/FdgqFnreqDetail.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgq-fnins",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgq/FdgqFninsList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgq-fnins/detail",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgq/FdgqFninsDetail.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgq-fnrec",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgq/FdgqFnrecList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgq-fnrec/detail",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgq/FdgqFnrecDetail.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgq-fnrep",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgq/FdgqFnrepList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgq-fnrep/detail",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgq/FdgqFnrepDetail.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgq-cs",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgq/FdgqCsList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgq-cs/detail",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgq/FdgqCsDetail.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgq-st",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgq/FdgqStList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgq-st/detail",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgq/FdgqStDetail.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgq-fc",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgq/FdgqFcList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgq-fc/detail",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgq/FdgqFcDetail.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdg-apqr",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdg/FdgApqrList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdg-apqrgp",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdg/FdgApqrgpList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdg-prodHis",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdg/FdgProdHisList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdg-qna",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdg/FdgQnaList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdg-dispose",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdg/FdgDisposeList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdg-dispose/regList",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdg/FdgDisposeRegList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdg-qna/qusList",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdg/FdgQnaQusList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdg-qna/qusReg",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdg/FdgQnaQusReg.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdg-qna/qusDtl",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdg/FdgQnaQusDetail.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdg-qna/ansUpd",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdg/FdgQnaAnsUpdate.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdg-qna/ansDtl",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdg/FdgQnaAnsDetail.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgi-mst",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgi/FdgiMstList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgi-in",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgi/FdgiInList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgi-in/reg",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgi/FdgiInReg.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgi-lot",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgi/FdgiLotList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgi-inUseHis",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgi/FdgiInUseHisList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/fdgi-mstQty",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/fdgi/FdgiMstQtyList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/sys-hist",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/system/HistList.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/sys-user",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/system/UserMng.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/sys-auth",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/system/AuthMng.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/sys-menu",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/system/MenuMng.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/sys-code",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("src/pages/chem/system/CodeMng.vue"),
      },
    ],
    meta: { requiresAuth: true },
  },
  {
    path: "/login",
    component: () => import("pages/auth/LoginPage3.vue"),
  },
  // Always leave this as last one,
  // but you can also remove it
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },

  //-------- 백제약품 Routes --------//
  {
    path: "/com-ord",
    component: () => import("pages/baekje/BjmComOrdList.vue"),
    // children: [
    //   {
    //     path: "",
    //     component: () => import("src/pages/baekje/BjmNewQaReg.vue"),
    //   },
    // ],
  },
];

export default routes;

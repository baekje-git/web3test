import { route } from "quasar/wrappers";
import {
  createRouter,
  createMemoryHistory,
  createWebHistory,
  createWebHashHistory,
} from "vue-router";
import routes from "./routes";
import * as util from "src/support/util";

/*
 * If not building with SSR mode, you can
 * directly export the Router instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Router instance.
 */

export default route(function (/* { store, ssrContext } */) {
  const createHistory = process.env.SERVER
    ? createMemoryHistory
    : process.env.VUE_ROUTER_MODE === "history"
    ? createWebHistory
    : createWebHashHistory;

  const Router = createRouter({
    scrollBehavior: () => ({ left: 0, top: 0 }),
    routes,

    // Leave this as is and make changes in quasar.conf.js instead!
    // quasar.conf.js -> build -> vueRouterMode
    // quasar.conf.js -> build -> publicPath
    history: createHistory(process.env.VUE_ROUTER_BASE),
  });

  Router.beforeEach((to, _, next) => {
    const isLoggedIn = util.isUserLoggedIn();
    // localstorage에 user정보가 남아있는데, login path입력하면 login화면으로 이동하는 현상..
    // if (to.path === "/login" || isLoggedIn) {
    //   return next();
    // } else {
    //   return next({ path: "/login" });
    // }

    //최초 로그인 페이지 들어올 경우
    if (to.path === "/login" && !isLoggedIn) {
      return next();
    }
    //로그인 된 상태에서 로그인페이지 들어올 경우
    else if (to.path === "/login" && isLoggedIn) {
      return next({ path: "/" });
    }
    //로그인을 안한 경우
    else if (!isLoggedIn) {
      return next({ path: "/login" });
    }
    //
    else {
      return next();
    }
  });

  return Router;
});

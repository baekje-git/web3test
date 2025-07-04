import { boot } from "quasar/wrappers";
import { EventBus } from "quasar";
import axios from "axios";
import * as util from "src/support/util";
import appConfig from "src/support/config";

const api = axios.create({
  headers: { "content-type": "application/json" },
  baseURL: process.env.API_URL,
});

let isAlreadyFetchingAccessToken = false;
// For Refreshing Token
let subscribers = [];

function onAccessTokenFetched(accessToken) {
  subscribers = subscribers.filter((callback) => callback(accessToken));
}

function addSubscriber(callback) {
  subscribers.push(callback);
}

// Request Interceptor
api.interceptors.request.use(
  function (config) {
    const accessToken = util.getToken();
    if (accessToken) {
      config.headers.Authorization = `${appConfig.tokenType} ${accessToken}`;
    }

    return config;
  },
  function (error) {
    // console.error("axios interceptor error(request) ===>", error.response);
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  function (response) {
    // console.log('axios interceptor ===>', response)
    return response.data || response;
  },
  function (error) {
    const { response } = error;
    // console.error(
    //   "axios interceptor error(response) ===>",
    //   error.config,
    //   response
    // );
    const originalRequest = error.config;

    if (response && response.status === 401) {
      if (!isAlreadyFetchingAccessToken) {
        isAlreadyFetchingAccessToken = true;
        // refreshToken요청
        console.log("refreshToken 요청");
        api
          .post(appConfig.refreshEndpoint, {
            userId: util.getUserData().USER_ID || "",
            refreshToken: util.getRefreshToken(),
          })
          .then((response) => {
            if (response.accessToken) {
              util.setToken(response.accessToken);
              util.setRefreshToken(response.refreshToken);
              isAlreadyFetchingAccessToken = false;
              onAccessTokenFetched(response.accessToken); // 요청건 재실행
            } else {
              console.error("refresh token 에러===>", response.result_msg);
              isAlreadyFetchingAccessToken = false;
              util.logout();
              window.location = "/#/login";
              util.showNotify("로그아웃 되었습니다.");
            }
          })
          .catch((error) => {
            const { response } = error;
            console.error("refresh token 에러===>", response || error);
            isAlreadyFetchingAccessToken = false;
            util.logout();
            window.location = "/#/login";
            util.showNotify("로그아웃 되었습니다.");
          });
      }
      // 요청건을 재실행배열 저장 : subscribers
      const retryOriginalRequest = new Promise((resolve) => {
        addSubscriber((accessToken) => {
          // Make sure to assign accessToken according to your response.
          // Check: https://pixinvent.ticksy.com/ticket/2413870
          // Change Authorization header
          originalRequest.headers.Authorization = `${appConfig.tokenType} ${accessToken}`;
          resolve(api(originalRequest));
        });
      });
      return retryOriginalRequest;
    } else if (response && response.status === 403) {
      // 라이센스 에러
      console.error("라이센스 에러===>", response);
      isAlreadyFetchingAccessToken = false;
      util.logout();
      window.location = "/#/Login";
      util.showNotify("로그아웃 되었습니다.");
      util.errorNotify(response.data.result_msg);
      return Promise.reject(response);
    }

    return Promise.reject(error);
  }
);

export default boot(({ app }) => {
  // for use inside Vue files (Options API) through this.$axios and this.$api

  app.config.globalProperties.$axios = axios;
  // ^ ^ ^ this will allow you to use this.$axios (for Vue Options API form)
  //       so you won't necessarily have to import axios in each vue file

  app.config.globalProperties.$api = api;
  // ^ ^ ^ this will allow you to use this.$api (for Vue Options API form)
  //       so you can easily perform requests against your app's API

  // 백제약품 EventBus 추가
  const bus = new EventBus();
  app.config.globalProperties.$bus = bus;
  app.provide("bus", bus);
});

export { axios, api };

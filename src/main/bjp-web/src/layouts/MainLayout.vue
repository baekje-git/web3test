<template>
  <q-layout view="lHh Lpr lFf">
    <q-drawer
      v-model="leftDrawerOpen"
      show-if-above
      bordered
      ref="drawref"
      :width="260"
      :mini="miniState"
      :mini-width="60"
      @click.capture="drawerClick"
    >
      <q-btn class="btn-slide" @click="fnOpenClose('close')" />
      <q-scroll-area :visible="false" delay="less" class="fit aside">
        <h1>
          <router-link to="/" class="home">
            <!-- <img
            src="assets/images/logo.png"
            width="107"
            height="46"
            alt="future chem logo"
          /> -->
            <q-img
              src="~assets/images/logo.png"
              style="width: 107px; height: 46px"
              loading="eager"
              alt="future chem logo"
            />
            <span>전체메뉴</span>
          </router-link>
        </h1>
        <RoleMenu :menus="roleMenuList" />
      </q-scroll-area>
    </q-drawer>

    <q-page-container>
      <!-- header S  -->
      <q-header class="header">
        <div class="top-mobile">
          <h1>
            <router-link to="/" class="home">
              <q-img
                src="~assets/images/logo.png"
                style="width: 107px; height: 46px"
                loading="eager"
                alt="future chem logo"
              />
            </router-link>
          </h1>
          <q-btn class="menu-hamburger" @click="fnOpenClose('open')" />
        </div>
        <div class="header-in">
          <div class="user-name">
            <i><q-img src="~assets/images/icon_user.png" /></i>
            <span
              >관리자<strong>{{ userData.USER_NM }}</strong></span
            >
            <!-- stomp sample -->
            <!-- <q-btn
              unelevated
              type="button"
              @click="stompSend"
              label="stomp send"
            />
            <q-btn
              unelevated
              type="button"
              @click="directSend"
              label="specify send"
            />
            <q-btn
              unelevated
              type="button"
              @click="excludeSend"
              label="exclude send"
            /> -->
            <!--// stomp sample -->
          </div>
          <div class="login-btn">
            <!-- <button><i>icon</i><span>비밀번호변경</span></button> -->
            <q-btn
              unelevated
              type="button"
              icon="~assets/images/icon-pw-change-on.png"
              label="비밀번호변경"
            />
            <!-- <button><i>icon</i><span>로그아웃</span></button> -->
            <q-btn
              unelevated
              type="button"
              icon="~assets/images/icon-logout.png"
              @click="logout"
              label="로그아웃"
            />
          </div>
        </div>
      </q-header>
      <!--// header E  -->
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import {
  defineComponent,
  ref,
  onMounted,
  onUnmounted,
  onBeforeMount,
} from "vue";
import RoleMenu from "components/RoleMenu.vue";

import { useRouter, useRoute } from "vue-router";
import * as util from "src/support/util";
import SockJS from "sockjs-client/dist/sockjs.min.js";
import Stomp from "webstomp-client";
import config from "src/support/config";
import { useQuasar, Screen } from "quasar";

export default defineComponent({
  name: "MainLayout",

  components: {
    RoleMenu,
  },

  setup() {
    const userData = util.getUserData();
    const leftDrawerOpen = ref(false);
    const roleMenuList = ref([]);

    const polling = null;
    const router = useRouter();
    const route = useRoute();

    const $q = useQuasar();
    const drawref = ref();
    const miniState = ref(false);

    // let sock = new SockJS(process.env.STOMP_URL);
    // let stompClient = Stomp.over(sock);
    // //webstomp.js > debug function is disabled.
    // stompClient.debug = () => {};

    let connected = false;
    let reconInv;
    //let sessionId;

    // 메뉴데이터
    function getRoleMenuList() {
      // try {
      //   const { data } = util.bindList("k99.role", "selectMenuByUser", {
      //     USER_ID: util.getUserData().USER_ID,
      //   });
      //   roleMenuList.value = [...data];
      // } catch (error) {
      //   console.error("메뉴조회중 에러발생===>", error);
      //   util.errorNotify(error.result_msg || config.getError);
      // }
      util
        .bindList("login", "selectMenuByUser", {
          USER_ID: userData.USER_ID,
        })
        .then((res) => {
          //console.log(res);
          roleMenuList.value = res;
        })
        .catch(() => {
          console.error("메뉴조회중 에러발생===>", error);
          util.errorNotify(error.result_msg || config.getError);
        });
    }

    // function successCallback() {
    //   stompClient.subscribe("/send", (res) => {
    //     //console.log("subscribe send::::::::::", res);
    //     //util.showNotify(JSON.parse(res.body).content);
    //     util.showNotify("common send");
    //   });

    //   stompClient.subscribe("/send/" + userData.USER_ID, (res) => {
    //     //console.log("send user id::::::::" + res);
    //     util.showNotify("specify send");
    //   });

    //   stompClient.subscribe("/user/send/exclude", (res) => {
    //     //console.log("send exclude self::::::::" + res);
    //     //util.showNotify("exclude self send");
    //     util.showNotify(JSON.parse(res.body).content);
    //   });
    // }

    // function reconnect(successCallback) {
    //   connected = false;
    //   reconInv = setInterval(() => {
    //     //console.log("stomp 재연결...");
    //     sock = new SockJS(process.env.STOMP_URL);
    //     stompClient = Stomp.over(sock);
    //     stompClient.connect(
    //       {},
    //       (frame) => {
    //         clearInterval(reconInv);
    //         connected = true;
    //         successCallback();
    //       },
    //       () => {
    //         if (connected) {
    //           reconnect(successCallback);
    //         }
    //       }
    //     );
    //   }, 30 * 1000);
    // }

    //mobile 전체메뉴 click event
    function fnOpenClose(type) {
      //leftDrawerOpen.value = !leftDrawerOpen.value;
      // if ($q.screen.width < 1024) {
      if (window.innerWidth < 1024) {
        //console.log(event.currentTarget.parentNode.parentNode);
        //console.log(this.$refs.test.$el);
        if (type == "close") {
          drawref.value.$el.style.display = "none";
        } else {
          drawref.value.$el.style.display = "block";
        }
      } else {
        miniState.value = true;
      }
    }

    //resize event
    function resizeEvent() {
      // console.log("resizeEvent::::::::");
      // console.log("$q.screen.width:::::::", $q.screen.width);
      // console.log("window.outerWidth:::::::", window.innerWidth);
      // if($q.screen.width < 1024) {
      if (window.innerWidth < 1024) {
        fnOpenClose("close");
        document.body.classList.remove("desktop");
        document.body.classList.add("mobile");
      } else {
        document.body.classList.remove("mobile");
        document.body.classList.add("desktop");
        drawref.value.$el.style.display = "block";
      }
    }
    onMounted(() => {
      window.addEventListener("resize", resizeEvent);

      resizeEvent();
      //mobile에서 전체메뉴 삭제
      // if ($q.screen.width < 1024) {
      //   fnOpenClose("close");
      //   document.body.classList.remove("desktop");
      //   document.body.classList.add("mobile");
      // }
      getRoleMenuList();
      // stompClient.connect(
      //   {
      //     TOKEN: `${util.getToken()}`,
      //     USER_ID: `${userData.USER_ID}`,
      //   },
      //   (frame) => {
      //     // let url = stompClient.ws._transport.url;
      //     // console.log(stompClient);
      //     // url = url.replace("ws://localhost:9090/", "");
      //     // url = url.replace("/websocket", "");
      //     // url = url.replace(/^[0-9]+\//, "");
      //     // console.log("Your current session is:::::::::::::::::::::: " + url);
      //     // sessionId = url;
      //     //console.log("stomp connect:::::", frame);
      //     successCallback();
      //   },
      //   (error) => {
      //     console.error("stomp connect error:::::", error);
      //     reconnect(successCallback);
      //   }
      // );
    });

    onUnmounted(() => {
      window.removeEventListener("resize", resizeEvent);
      // stompClient.disconnect(() => {
      //   stompClient.unsubscribe();
      //   connected = false;
      // });
      clearInterval(polling);
    });

    return {
      userData,
      roleMenuList,
      val: ref(true),
      leftDrawerOpen,
      fnOpenClose,
      drawref,
      resizeEvent,
      miniState,
      drawerClick(e) {
        // if in "mini" state and user
        // click on drawer, we switch it to "normal" mode
        if (miniState.value) {
          miniState.value = false;
          // notice we have registered an event with capture flag;
          // we need to stop further propagation as this click is
          // intended for switching drawer to "normal" mode only
          e.stopPropagation();
        }
      },
      current: ref(1),
      model: ref(null),
      model2: ref(null),

      options: ["Google", "Facebook", "Twitter", "Apple", "Oracle"],
      shape: ref("line"),

      logout() {
        util.logout();
        clearInterval(polling);

        router.push({ path: "/login" });
        util.showNotify("로그아웃 되었습니다.");
      },
      // stompSend() {
      //   if (stompClient && stompClient.connected) {
      //     stompClient.send(
      //       "/receive",
      //       JSON.stringify({
      //         USER_NM: userData.USER_NM,
      //         USER_ID: userData.USER_ID,
      //       }),
      //       {}
      //     );
      //   }
      // },
      // directSend() {
      //   if (stompClient && stompClient.connected) {
      //     stompClient.send(
      //       //test1은 userId
      //       "/test1",
      //       JSON.stringify({
      //         USER_NM: userData.USER_NM,
      //         USER_ID: userData.USER_ID,
      //       }),
      //       {}
      //     );
      //   }
      // },
      // excludeSend() {
      //   if (stompClient && stompClient.connected) {
      //     stompClient.send(
      //       "/exclude",
      //       JSON.stringify({
      //         USER_NM: userData.USER_NM,
      //         USER_ID: userData.USER_ID,
      //         content: "api callback message",
      //       }),
      //       {}
      //     );
      //   }
      // },

      // onSubmit(evt) {
      //   console.log("@submit - do something here", evt);
      //   evt.target.submit();
      // },
    };
  },
  watch: {
    $route(to, from) {
      //router 이동 후 전체메뉴 닫기(Platform or Screen으로 분기)
      //if (Platform.is.mobile) this.fnOpenClose("close");
      if (Screen.width < 1024) this.fnOpenClose("close");
    },
  },
});
</script>

<style scoped>
/* 여기서 선언한 CSS가 우선 적용
   그 다음은 app.scss가 우선 적용 (ctrl+p 눌러서 파일 검색)
*/
@font-face {
  font-family: "Pretendard";
  font-weight: 400;
  font-style: normal;
  font-display: swap;
  src: /* url("../css/font/Pretendard-Regular.woff2") format("woff2"),*/
    /* Super Modern Browsers */ url("font/Pretendard-Regular.woff")
    format("woff"); /* Modern Browsers */
}
@font-face {
  font-family: "Pretendard";
  font-weight: 500;
  font-style: normal;
  font-display: swap;
  src: /* url("../css/font/Pretendard-SemiBold.woff2") format("woff2"),*/
    /* Super Modern Browsers */ url("font/Pretendard-SemiBold.woff")
    format("woff"); /* Modern Browsers */
}
/* Roboto */
@font-face {
  font-family: "RBT";
  font-style: normal;
  font-weight: 400;
  font-display: swap;
  src: url("../css/font/Roboto-Regular.eot?#iefix") format("embedded-opentype"),
    /* IE6-IE8 */ /*url('../Roboto-Regular.woff2') format('woff2'),*/
      /* Super Modern Browsers */ url("../css/font/Roboto-Regular.woff")
      format("woff"),
    /* Modern Browsers */ url("../css/font/Roboto-Regular.ttf")
      format("truetype"); /* Safari, Android, iOS */
}
@font-face {
  font-family: "RBT";
  font-style: normal;
  font-weight: 500;
  font-display: swap;
  src: url("../css/font/Roboto-Medium.eot?#iefix") format("embedded-opentype"),
    /* IE6-IE8 */ /*url('../Roboto-Medium.woff2') format('woff2'),*/
      /* Super Modern Browsers */ url("../css/font/Roboto-Medium.woff")
      format("woff"),
    /* Modern Browsers */ url("../css/font/Roboto-Medium.ttf")
      format("truetype"); /* Safari, Android, iOS */
}
@font-face {
  font-family: "RBT";
  font-style: normal;
  font-weight: 600;
  font-display: swap;
  src: url("../css/font/Roboto-Bold.eot?#iefix") format("embedded-opentype"),
    /* IE6-IE8 */ /*url('../Roboto-Bold.woff2') format('woff2'),*/
      /* Super Modern Browsers */ url("../css/font/Roboto-Bold.woff")
      format("woff"),
    /* Modern Browsers */ url("../css/font/Roboto-Bold.ttf") format("truetype"); /* Safari, Android, iOS */
}
*,
body,
html {
  font-family: "Pretendard" !important;
  letter-spacing: 0;
  font-size: 14px;
}
</style>

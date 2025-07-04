<script setup>
import { ref, onMounted } from "vue";
import { jwtLogin } from "./login";
import * as util from "src/support/util";
import { useRouter } from "vue-router";
import { Cookies } from "quasar";

const router = useRouter();
const userid = ref("");
const password = ref("");
const loginFrm = ref(null);
const checkVal = ref(false);

onMounted(() => {
  //cookie 있으면 id & checkbox setting
  if (Cookies.has("saveId")) {
    userid.value = Cookies.get("saveId");
    checkVal.value = true;
  }
});

//checkbox click event
function clickSaveId() {
  //checked
  if (checkVal.value) {
    Cookies.set("saveId", userid.value, { expires: 7 });
  }
  //unchecked
  else {
    Cookies.remove("saveId");
  }
}

async function login() {
  try {
    const validate = await loginFrm.value.validate();
    if (validate) {
      const response = await jwtLogin({
        USER_ID: userid.value,
        USER_PWD: password.value,
      });
      // if (!response.result) {
      //   util.errorNotify(response.result_msg);
      //   return;
      // }
      const data = response;
      console.log("로그인 성공===>", data);
      util.setToken(data.accessToken);
      util.setRefreshToken(data.refreshToken);
      util.setUserData(data.userData);
      await router.push({ path: "/" });
      util.showNotify("로그인 되었습니다.");
    }
  } catch (error) {
    console.error("로그인 에러===>", error);
    if (error && error.status !== 403)
      util.errorNotify(error.data || "로그인중 에러가 발생했습니다.");
  }
}
</script>

<template>
  <!--- login-wrap S ---->
  <section class="login-wrap">
    <div class="ani-cell01"></div>
    <div class="ani-cell02"></div>
    <!--- login-area S ---->
    <div class="login-area">
      <!--- login-text S ---->
      <div class="login-text">
        <p>Future Chem</p>
        <p>의료RI･방사성의약품<br />전자문서결재관리시스템</p>
      </div>
      <!---// login-text E ---->
      <!--- login S ---->
      <div class="login">
        <q-form ref="loginFrm" class="q-gutter-md">
          <!--- login-in S --->
          <div class="login-in">
            <strong>로그인</strong>
            <form>
              <q-banner dense :class="$q.dark.isActive ? '' : ''">
                <div class="row items-start">
                  <q-input
                    filled
                    v-model="userid"
                    autocomplete="off"
                    lazy-rules
                    label="아이디를 입력해주세요"
                    style="width: 320px"
                    :rules="[
                      (val) => (val && val.length > 0) || '필수입력값입니다.',
                    ]"
                    tabindex="1"
                    autofocus
                  />
                </div>
              </q-banner>
              <q-banner dense :class="$q.dark.isActive ? '' : ''">
                <div class="row items-start">
                  <q-input
                    filled
                    clearable
                    v-model="password"
                    type="password"
                    label="비밀번호"
                    lazy-rules
                    autocomplete="off"
                    @keyup.enter="login"
                    :rules="[
                      (val) => (val && val.length > 0) || '필수입력값입니다.',
                    ]"
                    tabindex="2"
                    style="width: 320px"
                  />
                </div>
              </q-banner>
              <q-checkbox
                v-model="checkVal"
                label="아이디저장"
                @click="clickSaveId"
              />
            </form>
            <q-btn
              @click="login"
              unelevated
              tabindex="3"
              class="btn-login"
              label="로그인"
            />
            <!-- <div class="btn-member-area">
              <q-btn @click="showNotif" class="btn-member" label="회원가입" />
              <q-btn
                @click="showNotif"
                class="btn-member"
                label="비밀번호가 기억나지 않나요?"
              />
            </div> -->
          </div>
        </q-form>
        <!---// login-in E --->
      </div>
      <!---// login E ---->
    </div>
    <!---// login-area E ---->
  </section>
  <!---// login-wrap E ---->
</template>

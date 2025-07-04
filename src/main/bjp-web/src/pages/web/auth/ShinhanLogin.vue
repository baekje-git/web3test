<template>
  <div>로그인중 신한...{{ shinhanCheck }}</div>
</template>
<script setup>
import { ref, onMounted } from "vue";
import * as util from "src/support/util";
import { useRoute } from "vue-router";
import { jwtLoginCust } from "./login";

const route = useRoute(); // 현재 라우트 객체
const shinhanCust = "46" + route.query.shinhanCust;
const shinhanKey = route.query.shinhanKey;
const shinhanCheck = route.query.shinhanCheck;
const loginInfo = ref({
  loginId: shinhanCust,
  loginKey: shinhanKey,
  companyYn: "N",
});

onMounted(async () => {
  try {
    const resData = await jwtLoginCust(loginInfo.value);
    if (loginInfo.value.companyYn == "N") {
      util.setToken(resData.accessToken);
      util.setRefreshToken(resData.refreshToken);
      util.setUserData(resData.userData);

      window.location.href = "/dist/comOrd";
    }

    util.showNotify("로그인 되었습니다.");
  } catch (error) {
    if (error && error.response.status === 400) {
      let data = error.response.data;
      if (data == "미사용처리") {
        alert("영업담당자에게 문의 바랍니다.");
      } else if (data == "제약사 사용권한") {
        alert(`사용권한이 없습니다. \n구매부에 문의하세요.(Tel: 02-2109-9552)`);
      } else {
        alert("아이디 비밀번호를 확인해주세요.");
      }
    }
  }
});
</script>

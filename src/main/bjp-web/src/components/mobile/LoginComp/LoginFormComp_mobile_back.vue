<template>
  <div class="form_wrap_mobile">
    <div class="id_wrap_mobile">
      <label class="input_label_mobile"> 아이디 </label>
      <q-input
        class="input_mobile"
        v-model="loginInfo.loginId"
        stack-label
        dense="dense"
        placeholder="아이디를 입력해 주세요"
        @keyup.enter="onClickLogin"
      />
    </div>
    <div class="pass_wrap">
      <label class="input_label_mobile"> 비밀번호 </label>
      <q-input
        class="input_mobile"
        v-model="loginInfo.pwd"
        stack-label
        :type="isPassword ? 'password' : 'text'"
        dense="dense"
        placeholder="비밀번호를 입력해 주세요"
        @keyup.enter="onClickLogin"
      >
        <template v-slot:append>
          <q-icon
            :name="isPassword ? 'visibility_off' : 'visibility'"
            class="cursor-pointer"
            @click="isPassword = !isPassword"
          />
        </template>
      </q-input>
    </div>

    <div class="switch_wrap_mobile">
      <!-- <ToggleComp_mobile
        v-model="idInfo.isAutoLogin"
        @change="onChangeAutoLogin"
        toggleLabel="자동로그인"
      />

      <ToggleComp_mobile
        v-model="idInfo.isRememberId"
        @change="onChangeRememberId"
        toggleLabel="아이디 기억하기"
      /> -->
      <div class="CheckBox_wrap">
        <q-checkbox
          v-model="idInfo.isAutoLogin"
          label="자동로그인"
          color="blue-10"
          size="xs"
          @update:model-value="chkAutoLogin"
        />
      </div>
      <div class="CheckBox_wrap">
        <q-checkbox
          v-model="idInfo.isRememberId"
          label="아이디 기억하기"
          color="blue-10"
          size="xs"
          @update:model-value="chkRememberId"
        />
      </div>
    </div>
    <!-- {{ idInfo.isAutoLogin }}{{ idInfo.isRememberId }} -->
    <div class="button_wrap">
      <q-btn class="login_btn" label="로그인" @click="onClickLogin" />

      <div class="etcBtn_wrap_mobile">
        <q-btn
          dense
          class="etc_btn_mobile"
          label="신규 거래문의"
          @click="dialog_new = true"
        />
        <q-btn
          dense
          class="etc_btn_mobile"
          label="아이디 찾기"
          @click="dialog_id = true"
        />
        <!-- <q-btn
          dense
          class="etc_btn_mobile"
          label="비밀번호 찾기"
          @click="dialog_pass = true"
        /> -->
      </div>
    </div>

    <DialogLayout_mobile
      dialog_title="신규거래문의"
      v-model:dialog_model_prop="dialog_new"
    >
      <div class="etc_dialog_wrap">
        <div class="etc_text">
          <span class="blue-text bold-text">
            아래 내용을 기재하신 후 전송을 누르시면, <br />
            신속하게 연락드리겠습니다.
          </span>
        </div>
        <div class="etc_input_wrap">
          <div class="etc_input_item">
            <span>약국명</span>
            <InputComp_mobile
              style="width: 240px"
              placeholder="약국명"
              v-model:input_model_props="newQaData.drugstoreName"
            />
          </div>
          <div class="etc_input_item">
            <div class="">
              <span>주소</span><br />
              <span style="font-size: 12px; color: #666666"
                >(상세주소제외)</span
              >
            </div>
            <InputComp_mobile
              style="width: 240px"
              placeholder="(예) 서울시 구로구"
              v-model:input_model_props="newQaData.address"
            />
          </div>
          <div class="etc_input_item">
            <span>연락처</span>
            <InputComp2_mobile
              style="width: 240px"
              placeholder="휴대폰 번호"
              maxlength="13"
              @input="onKeyupCellPhoneNumber"
              v-model:input_model_props="newQaData.cellPhoneNumber"
            />
          </div>
        </div>
        <div class="etc_result">
          <BtnComp_mobile
            buttonText="전송"
            btnStyle="default"
            btnSize="md"
            style="width: 50%"
            @click="onClickTransmission1"
          />
          <BtnComp_mobile
            buttonText="취소"
            btnStyle="white"
            btnSize="md"
            style="width: 50%"
            v-close-popup
          />
        </div>
      </div>
    </DialogLayout_mobile>

    <DialogLayout_mobile
      dialog_title="아이디 찾기"
      v-model:dialog_model_prop="dialog_id"
    >
      <div class="etc_dialog_wrap">
        <div class="etc_text">
          <span> 사용자명과 사업자번호를 입력하고 </span> <br />
          <span class="blue-text bold-text"> 확인 </span>
          <span> 버튼을 눌러주세요. </span><br />
          <span>
            비밀번호는 영업 담당자에게 문의해주세요. 횟수({{ count }}/3)
          </span>
        </div>
        <div class="etc_input_wrap">
          <div class="etc_input_item">
            <span>사용자명</span>
            <InputComp_mobile
              style="width: 240px"
              placeholder="이름을 입력하세요."
              v-model:input_model_props="findLoginIdData.userNm"
            />
          </div>
          <div class="etc_input_item">
            <span>사업자번호</span>
            <InputComp_mobile
              style="width: 240px"
              placeholder="번호만 입력(-제외)"
              maxlength="15"
              @input="onServiceNo"
              v-model:input_model_props="findLoginIdData.serviceNo"
            />
          </div>
        </div>
        <div
          v-if="returnFindIdInfo"
          style="text-align: center; margin-top: 10px; color: red"
        >
          <b>{{ findIdValue }}</b>
        </div>
        <div class="etc_result">
          <BtnComp_mobile
            :buttonText="textVal"
            btnStyle="default"
            btnSize="md"
            style="width: 50%"
            @click="onClickTransmission2(textVal)"
            :disable="disA"
          />
          <BtnComp_mobile
            buttonText="취소"
            btnStyle="white"
            btnSize="md"
            style="width: 50%"
            v-close-popup
          />
        </div>
      </div>
    </DialogLayout_mobile>

    <!-- <DialogLayout_mobile
      dialog_title="비밀번호 찾기"
      v-model:dialog_model_prop="dialog_pass"
    >
      <div class="etc_dialog_wrap">
        <div class="etc_text">
          <span>
            아이디, 대표자명과 사업자번호를 입력하고 전송을 누르시면 아이디
          </span>
          <span class="blue-text bold-text"> 등록시 입력한 휴대전화번호 </span>
          <span>
            로 <br />
            임시 비밀번호가 발송됩니다.
          </span>
          <br />
          <br />
          <span>
            휴대전화번호가 변경 되신 분은 담당자에게 <br />
            연락주세요. 횟수(0/3)
          </span>
        </div>
        <div class="etc_input_wrap">
          <div class="etc_input_item">
            <span>아이디</span>
            <InputComp_mobile
              style="width: 240px"
              placeholder="아이디를 입력하세요."
              v-model:input_model_props="findLoginPwData.userId"
            />
          </div>
          <div class="etc_input_item">
            <span>대표자명</span>
            <InputComp_mobile
              style="width: 240px"
              placeholder="대표자명을 입력하세요."
              v-model:input_model_props="findLoginPwData.ceoNm"
            />
          </div>
          <div class="etc_input_item">
            <span>사업자번호</span>
            <InputComp_mobile
              style="width: 240px"
              placeholder="번호만 입력(-제외)"
              maxlength="15"
              @input="onKeyupServiceNo"
              v-model:input_model_props="findLoginPwData.serviceNo"
            />
          </div>
        </div>
        <div class="etc_result">
          <BtnComp_mobile
            buttonText="전송"
            btnStyle="default"
            btnSize="md"
            style="width: 50%"
            @click="onClickTransmission3"
          />
          <BtnComp_mobile
            buttonText="취소"
            btnStyle="white"
            btnSize="md"
            style="width: 50%"
            v-close-popup
          />
        </div>
      </div>
    </DialogLayout_mobile> -->
  </div>
</template>
<script setup>
import { ref, onMounted, watch, computed } from "vue";
import { api } from "boot/axios";
import { jwtLogin, jwtRefreshToken } from "./login";
import * as util from "src/support/util";
import { useRouter } from "vue-router";
import { Cookies } from "quasar";

import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import ToggleComp_mobile from "src/components/mobile/__commonComp/toggleComp/ToggleComp_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import InputComp_mobile from "src/components/mobile/__commonComp/inputComp/InputComp_mobile.vue";
import InputComp2_mobile from "src/components/mobile/__commonComp/inputComp/InputComp2_mobile.vue";
const router = useRouter();
const isPassword = ref(true);
const count = ref(0);
const disA = ref(false);

const loginInfo = ref({
  loginId: Cookies.get("saveId") ?? "",
  pwd: "",
  companyYn: "N",
});

const idInfo = ref({
  isAutoLogin: Cookies.get("autoLoginData") ? true : false,
  isRememberId: Cookies.get("saveId") ? true : false,
});

// 자동 로그인
const chkAutoLogin = (val) => {
  idInfo.value.isAutoLogin = val;
  if (!idInfo.value.isAutoLogin) {
    Cookies.remove("autoLoginData");
  }
};

// 아이디 기억하기
const chkRememberId = (val) => {
  idInfo.value.isRememberId = val;
  if (!idInfo.value.isRememberId) {
    Cookies.remove("saveId");
  }
};

// 자동로그인
// function onChangeAutoLogin() {
//   if (!idInfo.value.isAutoLogin) {
//     Cookies.remove("autoLoginData");
//   }
// }

// 아이디 기억하기
// function onChangeRememberId() {
//   if (!idInfo.value.isRememberId) {
//     Cookies.remove("saveId");
//   }
// }

// 로그인버튼 클릭
async function onClickLogin() {
  // 로그인 필수값 입력 체크
  // const loginId = loginInfo.value.loginId;
  // const pwd = loginInfo.value.pwd;

  // if (loginId.length === 0) {
  //   alert("아이디를 입력해 주세요.");
  //   return;
  // } else if (pwd.length === 0) {
  //   alert("비밀번호를 입력해 주세요.");
  //   return;
  // }

  // try {
  //   const resData = await jwtLogin(loginInfo.value);

  //   util.setToken(resData.accessToken);
  //   util.setRefreshToken(resData.refreshToken);
  //   util.setUserData(resData.userData);

  //   // 자동로그인 true면 쿠키에 autoLogin 데이터 저장
  //   if (idInfo.value.isAutoLogin) {
  //     Cookies.set(
  //       "autoLoginData",
  //       {
  //         userId: util.getUserData().USER_ID,
  //         refreshToken: resData.refreshToken,
  //         companyYn: loginInfo.value.companyYn,
  //       },
  //       { expires: 7 }
  //     );
  //   }

  //   // 아이디 기억하기 true면 쿠키에 loginId저장
  //   if (idInfo.value.isRememberId) {
  //     Cookies.set("saveId", loginInfo.value.loginId, { expires: 7 });
  //   }

  //   // console.log("로그인 value"+ JSON.stringify(router))

  //   // await router.push({ path: "/mobile/mainOrder" });
  //   await router.push({ path: "/comOrd" });
  //   util.showNotify("로그인 되었습니다.");
  // } catch (error) {
  //   if (error && error.response.status === 400) {
  //     alert(error.response.data);
  //   }
  // }

  // 로그인 필수값 입력 체크
  const loginId = loginInfo.value.loginId;
  const pwd = loginInfo.value.pwd;

  if (loginId.length === 0) {
    alert("아이디를 입력해 주세요.");
    return;
  } else if (pwd.length === 0) {
    alert("비밀번호를 입력해 주세요.");
    return;
  }

  try {
    const resData = await jwtLogin(loginInfo.value);
    if (resData.userData.PASSWORD_INIT_YN === "INIT") {
      isOpenPwdEdit.value = true;
      loginInfo.value.pwd = "";
      return;
    } else if (resData.userData.PASSWORD_INIT_YN === "N") {
      alert("아이디 비밀번호를 확인해주세요.");
      return;
    }

    if (loginInfo.value.companyYn == "N") {
      util.setToken(resData.accessToken);
      util.setRefreshToken(resData.refreshToken);
      util.setUserData(resData.userData);

      // 자동로그인 true면 쿠키에 autoLogin 데이터 저장
      if (idInfo.value.isAutoLogin) {
        Cookies.set(
          "autoLoginData",
          {
            userId: util.getUserData().USER_ID,
            refreshToken: resData.refreshToken,
            //       companyYn: loginInfo.value.companyYn,
          },
          { expires: 7 }
        );
      }

      // 아이디 기억하기 true면 쿠키에 loginId저장
      if (idInfo.value.isRememberId) {
        Cookies.set("saveId", loginInfo.value.loginId, { expires: 7 });
      }

      await router.push({ path: "/comOrd" });
    } else {
      util.setToken(resData.accessToken);
      await bus.emit("saleDetail", resData.userData);
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
}

// 자동 로그인
onMounted(async () => {
  // if (idInfo.value.isAutoLogin) {
  //   const resData = await jwtLogin(Cookies.get("autoLoginData"));

  //   util.setToken(resData.accessToken);
  //   util.setRefreshToken(resData.refreshToken);
  //   util.setUserData(resData.userData);

  //   // await router.push({ path: "/" });  // 개발용
  //   await router.push({ path: "/comOrd" }); //자동로그인후 화면이동
  // }

  // 자동 로그인
  if (!util.isEmpty(Cookies.get("autoLoginData"))) {
    const resData = await jwtRefreshToken(Cookies.get("autoLoginData"));
    util.setToken(resData.accessToken);
    util.setRefreshToken(resData.refreshToken);
    util.setUserData(resData.userData);

    await router.push({ path: "/comOrd" });
  }

  // 30분 로그인 락
  if (!util.isEmpty(Cookies.get("lockTimeFindId"))) {
    count.value = Cookies.get("lockTimeFindId");
    returnFindIdInfo.value = true;
    findIdValue.value = `정보노출이 의심됩니다. 잠시후에 다시 시도하세요`;
    disA.value = true;
  } else {
    count.value = 0;
    disA.value = false;
  }
});

const dialog_new = ref(false);
const dialog_id = ref(false);
const dialog_pass = ref(false);

//신규거래문의---------------------------------------------------//
const newQaData = ref({
  drugstoreName: "",
  address: "",
  cellPhoneNumber: "",
  ctt: "",
});

async function initNewQaData() {
  newQaData.value.drugstoreName = "";
  newQaData.value.pharmacistName = "";
  newQaData.value.address = "";
  newQaData.value.cellPhoneNumber = "";
}

async function onClickTransmission1() {
  if (newQaData.value.drugstoreName.length === 0) {
    alert("약국명을 입력하세요.");
    return;
  } else if (newQaData.value.address.length === 0) {
    alert("주소를 입력하세요.");
    return;
  } else if (newQaData.value.cellPhoneNumber.length === 0) {
    alert("휴대폰 번호를 입력하세요.");
    return;
  }

  const drugstoreName = newQaData.value.drugstoreName;
  const address = newQaData.value.address;
  const cellPhoneNumber = newQaData.value.cellPhoneNumber;

  newQaData.value.ctt = `[${drugstoreName}] [주소 : ${address}] [연락처 : ${cellPhoneNumber}] [Mobile신청]`;

  await api.post("/main/newQa", newQaData.value);

  util.showNotify("신규 거래문의 등록이 완료되었습니다.");
  await initNewQaData();
  dialog_new.value = false;
}

function onKeyupCellPhoneNumber() {
  const cellPhoneNumber = newQaData.value.cellPhoneNumber;

  newQaData.value.cellPhoneNumber = cellPhoneNumber
    .replace(/[^0-9]/g, "")
    .replace(
      /(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,
      "$1-$2-$3"
    )
    .replace("--", "-");
}

//아이디 찾기-------------------------------------------------//
const findLoginIdData = ref({
  userNm: "",
  serviceNo: "",
});

const returnFindIdInfo = ref(false);
const findInfo = ref(false);
const findIdValue = ref("");

const textVal = computed(() => {
  if (findInfo.value) {
    return "로그인";
  }
  return "확인";
});

async function initFindLoginIdData() {
  findLoginIdData.value.userNm = "";
  findLoginIdData.value.serviceNo = "";
}

// 확인 버튼 클릭
async function onClickTransmission2(val) {
  if (count.value > 2) {
    findIdValue.value = `정보노출이 의심됩니다. 잠시후에 다시 시도하세요`;
    if (count.value == 3) {
      let today = new Date();
      today.setMinutes(today.getMinutes() + 30);
      Cookies.set("lockTimeFindId", count.value, { expires: today });
      disA.value = true;
    }
    return;
  }

  if (findLoginIdData.value.userNm.length === 0) {
    alert("사용자명을 입력하세요.");
    return;
  } else if (findLoginIdData.value.serviceNo.length === 0) {
    alert("사업자번호를 입력하세요.");
    return;
  }

  if (val == "확인") {
    try {
      const res = await api.get("/main/findLoginId", {
        params: findLoginIdData.value,
      });
      returnFindIdInfo.value = true;

      if (res.length === 0) {
        findIdValue.value = `일치하는 정보가 없습니다.`;
        count.value++;
      } else {
        findInfo.value = true;
        loginInfo.value.loginId = res[0].LOGIN_ID;
        findIdValue.value = `로그인 아이디는 ${res[0].LOGIN_ID} 입니다`;
      }
    } catch (error) {
      returnFindIdInfo.value = true;
      findIdValue.value = `일치하는 정보가 없습니다.`;
    }
  } else if (val == "로그인") {
    dialog_id.value = false;
    findInfo.value = false;
  }
}

function onServiceNo(val) {
  const serviceNo = findLoginIdData.value.serviceNo;

  findLoginIdData.value.serviceNo = serviceNo.replace(/[^0-9]/g, "");
}

//비밀번호 찾기-------------------------------------------------//
const findLoginPwData = ref({
  userId: "",
  ceoNm: "",
  serviceNo: "",
});

const returnFindPwInfo = ref(false);

let findPwValue = ref("");

async function initFindLoginPwData() {
  findLoginPwData.value.userId = "";
  findLoginPwData.value.ceoNm = "";
  findLoginPwData.value.serviceNo = "";
  returnFindPwInfo.value = false;
}

// 임시 비밀번호 sms발송
async function tempPasswordTransmission() {
  try {
    await api.post("/main/sendTempPw", findLoginPwData.value);
    alert("문자발송이 완료되었습니다.");

    await initFindLoginPwData();
    dialog_pass.value = false;
  } catch (error) {
    alert(error.response.data);
  }
}

// 전송 버튼 클릭
async function onClickTransmission3() {
  if (findLoginPwData.value.userId.length === 0) {
    alert("아이디를 입력하세요.");
    return;
  } else if (findLoginPwData.value.ceoNm.length === 0) {
    alert("대표자를 입력하세요.");
    return;
  } else if (findLoginPwData.value.serviceNo.length === 0) {
    alert("사업자번호를 입력하세요.");
    return;
  }

  try {
    const res = await api.get("/main/findLoginPw", {
      params: findLoginPwData.value,
    });

    console.log("findLoginPwData " + JSON.stringify(findLoginPwData));
    console.log("res " + JSON.stringify(res));
    // ~ main/findLoginPw 성공확인, 실제 데이터로 테스트 필요

    if (res.HP_NO === "") {
      returnFindPwInfo.value = true;
      findPwValue.value = "전송할 번호정보가 없습니다. 관리자에게 문의하세요.";
    } else if (res.LOGIN_ID) {
      await tempPasswordTransmission();
      returnFindPwInfo.value = false;
      findPwValue.value = "";
    } else {
      returnFindPwInfo.value = true;
      findPwValue.value = "일치하는 정보가 없습니다.";
    }
  } catch (error) {
    returnFindPwInfo.value = true;
    findPwValue.value = "일치하는 정보가 없습니다.";
    console.log("error " + error);
  }
}

function onKeyupServiceNo() {
  const serviceNo = findLoginPwData.value.serviceNo;

  findLoginPwData.value.serviceNo = serviceNo.replace(/[^0-9]/g, "");
}

watch(dialog_id, () => {
  if (!dialog_id.value) {
    if (!util.isEmpty(Cookies.get("lockTimeFindId"))) {
      count.value = Cookies.get("lockTimeFindId");
      returnFindIdInfo.value = true;
      findIdValue.value = `정보노출이 의심됩니다. 잠시후에 다시 시도하세요`;
      disA.value = true;
    } else {
      count.value = 0;
      disA.value = false;
      returnFindIdInfo.value = true;
      findIdValue.value = "";
      findLoginIdData.value.userNm = "";
      findLoginIdData.value.serviceNo = "";
    }
    // returnFindIdInfo.value = true;
    // findIdValue.value = "";
    // findLoginIdData.value.userNm = "";
    // findLoginIdData.value.serviceNo = "";
  }
});

watch(dialog_new, () => {
  if (!dialog_new.value) {
    newQaData.value.drugstoreName = "";
    newQaData.value.pharmacistName = "";
    newQaData.value.address = "";
    newQaData.value.cellPhoneNumber = "";
  }
});

//-------------------------------------------------------------//
</script>

<style lang="scss">
.form_wrap_mobile .input_mobile {
  margin-bottom: 20px;
}
.input_label_mobile {
  @include fs-5;
  @include fw-7;
  color: $b3;
}
.form_wrap_mobile {
  .q-placeholder {
    font-size: 16px !important;
    padding: 0 5%;
  }
}
.switch_wrap_mobile {
  @include flex_start;
  gap: 32px;
  margin-bottom: 32px;
}

.login_btn {
  padding: 10px 0;
  width: 100%;
  background-color: $b3;
  border-radius: 5px;
}
.login_btn span {
  @include fs-4;
  color: $w;
}
.etcBtn_wrap_mobile {
  @include flex_between;
  margin: 24px 0;
  gap: 8px;
}
.etc_btn_mobile {
  width: 100%;
  background-color: $w;
  border-radius: 4px;
  border: 1px solid rgba(102, 102, 102, 0.5);
  box-shadow: none;
}
.etc_btn_mobile span {
  @include fs-5;
  color: #333;
}

.etc_dialog_wrap {
  .etc_text {
    margin-bottom: 40px;
    span {
      line-height: 24px;
    }
  }
  .etc_input_wrap {
    margin-bottom: 40px;
    .etc_input_item {
      @include flex_between;
      margin-bottom: 16px;
      span {
        @include fs-5;
      }
      span:nth-child(1) {
        @include fw-7;
      }
    }
  }
  .etc_result {
    @include flex_between;
    gap: 8px;
  }
}

.CheckBox_wrap {
  .q-checkbox__label {
    @include fs-5;
    color: #666;
  }
}
</style>

<template>
  <div class="form_wrap">
    <div class="id_wrap">
      <label class="input_label">
        아이디
      </label>
      <q-input
      class="input"
      v-model="loginInfo.loginId"
      stack-label
      dense="dense"
      placeholder="아이디를 입력해 주세요"
      />
    </div>
    <div class="pass_wrap">
      <label class="input_label">
        비밀번호
      </label>
      <q-input
      class="input"
      v-model="loginInfo.pwd"
      stack-label
      :type="isPassword ? 'password' : 'text'"
      dense="dense"
      placeholder="비밀번호를 입력해 주세요"
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

    <div class="switch_wrap">
      <ToggleComp_mobile
       v-model="idInfo.isAutoLogin"
       @change="onChangeAutoLogin"
       toggleLabel="자동로그인"
      />

      <ToggleComp_mobile
        v-model="idInfo.isRememberId"
        @change="onChangeRememberId"
        toggleLabel="아이디 기억하기"
      />
    </div>
    <!-- {{ idInfo.isAutoLogin }}{{ idInfo.isRememberId }} -->
    <div class="button_wrap">
      <q-btn class="login_btn" label="로그인" @click="onClickLogin"/>

      <div class="etcBtn_wrap">
        <q-btn dense class="etc_btn" label="신규 거래문의" @click="dialog_new = true"/>
        <q-btn dense class="etc_btn" label="아이디 찾기" @click="dialog_id = true"/>
        <q-btn dense class="etc_btn" label="비밀번호 찾기" @click="dialog_pass = true"/>
      </div>
    </div>


    <DialogLayout_mobile
      dialog_title="신규거래문의"
      v-model:dialog_model_prop="dialog_new"
    >
      <div class="etc_dialog_wrap">
        <div class="etc_text">
          <span class="blue-text bold-text">
            아래 내용을 기재하신 후 전송을 누르시면, <br/>
            신속하게 연락드리겠습니다.
          </span>
        </div>
        <div class="etc_input_wrap">
          <div class="etc_input_item">
            <span>약국명</span>
            <InputComp_mobile
              style="width: 240px;"
              placeholder="약국명"
              v-model="newQaData.drugstoreName"
              @update:model-value="input_model_props = $event"
            />
          </div>
          <div class="etc_input_item">
            <div class="">
              <span>주소</span><br/>
              <span style="font-size: 12px; color: #666666;">(상세주소제외)</span>
            </div>
            <InputComp_mobile
              style="width: 240px;"
              placeholder="(예) 서울시 구로구"
              v-model="newQaData.address"
              @update:model-value="input_model_props = $event"
            />
          </div>
          <div class="etc_input_item">
            <span>연락처</span>
            <InputComp_mobile
              style="width: 240px;"
              placeholder="휴대폰 번호"
              maxlength="13"
              @input="onKeyupCellPhoneNumber"
              v-model="newQaData.cellPhoneNumber"
              @update:model-value="input_model_props = $event"
            />
          </div>
        </div>
        <div class="etc_result">
          <BtnComp_mobile
            buttonText="전송"
            btnStyle="default"
            btnSize="md"
            style="width: 50%;"
            @click="onClickTransmission1"
          />
          <BtnComp_mobile
            buttonText="취소"
            btnStyle="white"
            btnSize="md"
            style="width: 50%;"
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
          <span>
            사용자명과 사업자번호를 입력하고
          </span> <br/>
          <span class="blue-text bold-text">
            확인
          </span>
          <span>
            버튼을 눌러주세요.횟수(0/3)
          </span>
        </div>
        <div class="etc_input_wrap">
          <div class="etc_input_item">
            <span>사용자명</span>
            <InputComp_mobile
              style="width: 240px;"
              placeholder="이름을 입력하세요."
              v-model="findLoginIdData.userNm"
              @update:model-value="input_model_props = $event"

            />
          </div>
          <div class="etc_input_item">
            <span>사업자번호</span>
            <InputComp_mobile
              style="width: 240px;"
              placeholder="번호만 입력(-제외)"
              maxlength="10"
              @input="onServiceNo"
              v-model="findLoginIdData.serviceNo"
              @update:model-value="input_model_props = $event"
            />
          </div>
        </div>
        <div class="etc_result">
          <BtnComp_mobile
            buttonText="전송"
            btnStyle="default"
            btnSize="md"
            style="width: 50%;"
            @click="onClickTransmission2"
          />
          <BtnComp_mobile
            buttonText="취소"
            btnStyle="white"
            btnSize="md"
            style="width: 50%;"
            v-close-popup
          />
        </div>
      </div>
    </DialogLayout_mobile>

    <DialogLayout_mobile
      dialog_title="비밀번호 찾기"
      v-model:dialog_model_prop="dialog_pass"
    >
      <div class="etc_dialog_wrap">
        <div class="etc_text">
          <span>
            아이디, 대표자명과 사업자번호를 입력하고 전송을 누르시면 아이디
          </span>
          <span class="blue-text bold-text">
            등록시 입력한 휴대전화번호
          </span>
          <span>
            로 <br/> 임시 비밀번호가 발송됩니다.
          </span> <br/> <br/>
          <span>
            휴대전화번호가 변경 되신 분은 담당자에게 <br/>
            연락주세요. 횟수(0/3)
          </span>
        </div>
        <div class="etc_input_wrap">
          <div class="etc_input_item">
            <span>아이디</span>
            <InputComp_mobile
              style="width: 240px;"
              placeholder="아이디를 입력하세요."
              v-model="findLoginPwData.userId"
              @update:model-value="input_model_props = $event"
              />
          </div>
          <div class="etc_input_item">
            <span>대표자명</span>
            <InputComp_mobile
              style="width: 240px;"
              placeholder="대표자명을 입력하세요."
              v-model="findLoginPwData.ceoNm"
              @update:model-value="input_model_props = $event"
            />
          </div>
          <div class="etc_input_item">
            <span>사업자번호</span>
            <InputComp_mobile
              style="width: 240px;"
              placeholder="번호만 입력(-제외)"
              maxlength="10"
              @input="onKeyupServiceNo"
              v-model="findLoginPwData.serviceNo"
              @update:model-value="input_model_props = $event"
            />
          </div>
        </div>
        <div class="etc_result">
          <BtnComp_mobile
            buttonText="전송"
            btnStyle="default"
            btnSize="md"
            style="width: 50%;"
            @click="onClickTransmission3"
          />
          <BtnComp_mobile
            buttonText="취소"
            btnStyle="white"
            btnSize="md"
            style="width: 50%;"
            v-close-popup
          />
        </div>
      </div>
    </DialogLayout_mobile>



  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { api } from "boot/axios";
import { jwtLogin } from "./login";
import * as util from "src/support/util";
import { useRouter } from "vue-router";
import { Cookies } from "quasar";
import ToggleComp_mobile from 'src/components/__commonComp/toggleComp/ToggleComp_mobile.vue';
import DialogLayout_mobile from 'src/layouts/DialogLayouts/DialogLayout_mobile.vue';
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import InputComp_mobile from 'src/components/__commonComp/inputComp/InputComp_mobile.vue';

const router = useRouter();
const isPassword = ref(true);

const loginInfo = ref({
  loginId: Cookies.get("saveId") ?? "",
  pwd: "",
  companyYn: "N",
});

const idInfo = ref({
  isAutoLogin: Cookies.get("autoLoginData") ? true : false,
  isRememberId: Cookies.get("saveId") ? true : false,
})

// 자동로그인
function onChangeAutoLogin() {
  if (!idInfo.value.isAutoLogin) {
    Cookies.remove("autoLoginData");
  }
}

// 아이디 기억하기
function onChangeRememberId() {
  if (!idInfo.value.isRememberId) {
    Cookies.remove("saveId");
  }
}

// 로그인버튼 클릭
async function onClickLogin() {
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
          companyYn: loginInfo.value.companyYn,
        },
        { expires: 7 }
      );
    }

    // 아이디 기억하기 true면 쿠키에 loginId저장
    if (idInfo.value.isRememberId) {
      Cookies.set("saveId", loginInfo.value.loginId, { expires: 7 });
    }

    // console.log("로그인 value"+ JSON.stringify(router))

    await router.push({ path: "/mobile/mainOrder" });
    util.showNotify("로그인 되었습니다.");
  } catch (error) {
    if (error && error.response.status === 400) {
      alert(error.response.data);
    }
  }
}

// 자동 로그인
onMounted(async () => {
  if (idInfo.value.isAutoLogin) {
    const resData = await jwtLogin(Cookies.get("autoLoginData"));

    util.setToken(resData.accessToken);
    util.setRefreshToken(resData.refreshToken);
    util.setUserData(resData.userData);

    // await router.push({ path: "/" });  // 개발용
    await router.push({ path: "/mobile/mainOrder" }); //자동로그인후 화면이동
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
let findIdValue = "";

async function initFindLoginIdData() {
  findLoginIdData.value.userNm = "";
  findLoginIdData.value.serviceNo = "";
}

// 전송 버튼 클릭
async function onClickTransmission2() {
  if (findLoginIdData.value.userNm.length === 0) {
    alert("사용자명을 입력하세요.");
    return;
  } else if (findLoginIdData.value.serviceNo.length === 0) {
    alert("사업자번호를 입력하세요.");
    return;
  }

  try {
    const res = await api.get("/main/findLoginId", {
      params: findLoginIdData.value,
    });
    returnFindIdInfo.value = true;

    if (res.length === 0) {
      findIdValue = `일치하는 정보가 없습니다.`;
    } else {
      findIdValue = `로그인 아이디는 ${res[0].LOGIN_ID} 입니다`;
    }
  } catch (error) {
    returnFindIdInfo.value = true;
    findIdValue = `일치하는 정보가 없습니다.`;
  }
}

function onServiceNo() {
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
  alert("임시 비밀번호 발송 개발 필요");
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

    console.log("findLoginPwData "+JSON.stringify(findLoginPwData))
    console.log("res "+JSON.stringify(res))
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
    console.log("error "+error)
  }
}

function onKeyupServiceNo() {
  const serviceNo = findLoginPwData.value.serviceNo;

  findLoginPwData.value.serviceNo = serviceNo
    .replace(/[^0-9]/g, "");

}

//-------------------------------------------------------------//
</script>

<style lang="scss">

.form_wrap .input{
  margin-bottom: 20px;
}
.input_label {
  @include fs-5;
  @include fw-7;
  color: $b3;
}
.form_wrap {
  .q-placeholder {
    font-size: 16px !important;
    padding: 0 5%;
  }
}
.switch_wrap {
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
.etcBtn_wrap {
  @include flex_between;
  margin: 24px 0;
  gap: 8px;
}
.etc_btn {
  width: 100%;
  background-color: $w;
  border-radius: 4px;
  border: 1px solid rgba(102, 102, 102, 0.5);
  box-shadow: none;
}
.etc_btn span {
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
      span:nth-child(1){
        @include fw-7;
      }
    }
  }
  .etc_result {
    @include flex_between;
    gap: 8px;
  }
}




</style>

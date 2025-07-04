<template>
  <div class="paymentTable_2b">
    <div class="q-table_wrap">
      <div class="q-table_header_wrap">
        <div class="q-table_header_title">
          <p>나의카드 상세정보</p>
        </div>
      </div>

      <div class="mycardTable_wrap">
        <div class="markup_table_header_wrap">신용/체크카드</div>
        <ul style="padding: 17px">
          <li>
            <ul>
              <li>카드명칭</li>
              <li>
                <q-input
                  v-model="cardInfo.cardNm"
                  dense
                  placeholder="입력"
                  :disable="isDisable"
                />
              </li>
            </ul>
            <ul>
              <li>카드회사</li>
              <li>
                <!-- <SelectComp_default
                  :selectModel="cardInfo.cardComp"
                  :selectOption="cardOption"
                /> -->
                <div class="slct-wrap">
                  <q-select
                    class="select_default"
                    v-model="cardInfo.cardComp"
                    :options="cardOption"
                    borderless
                    dense
                    :disable="isDisable2"
                    @update:model-value="onChangeSelect($event)"
                  />
                </div>
              </li>
            </ul>
            <!-- <ul>
              <li>PG사</li>
              <li>
                !-- <RadioComp_a
                  v-model="cardInfo.pg"
                  :radio_options="pgOptions"
                ></RadioComp_a> --
                <q-option-group
                  v-model="cardInfo.pg"
                  :options="pgOptions"
                  color="blue-10"
                  size="xs"
                  inline
                  dense
                  disable
                />
              </li>
            </ul> -->
            <ul>
              <li>카드번호</li>
              <li>
                <q-input
                  filled
                  v-model="cardInfo.cardNoV"
                  mask="#### - #### - #### - ####"
                  fill-mask="#"
                  :disable="isDisable2"
                />

                <!-- 카드입력 폼. 변경 건 주석 -->
                <!-- <div class="flex_start" style="gap: 8px">
                  <q-input
                    v-model="cardInfo.cardNoV"
                    dense
                    mask="####"
                    placeholder="0000"
                    :disable="isDisable2"
                    style="width: 80px"
                  />
                  -
                  <q-input
                    dense
                    mask="####"
                    placeholder="0000"
                    :disable="isDisable2"
                    style="width: 80px"
                  />
                  -
                  <q-input
                    dense
                    mask="####"
                    placeholder="0000"
                    :disable="isDisable2"
                    style="width: 80px"
                  />
                  -
                  <q-input
                    dense
                    mask="####"
                    placeholder="0000"
                    :disable="isDisable2"
                    style="width: 80px"
                  />
                </div> -->
              </li>
            </ul>
            <ul>
              <li>유효기간</li>
              <li>
                <q-input
                  v-model="cardInfo.cardMmYy"
                  dense
                  mask="##/##"
                  placeholder="MM / YY"
                  :disable="isDisable2"
                  @update:model-value="vaildCardMm"
                />
              </li>
            </ul>
            <ul>
              <li>
                <div class="tooltip_wrap">
                  간편결제 비밀번호
                  <q-badge rounded>
                    ?
                    <q-tooltip anchor="top right" self="top left">
                      <p style="font-size: 14px">
                        비밀번호 설정 후 분실 시 <br />
                        보안을 위해 삭제 후 재등록 해주십시오.
                      </p>
                    </q-tooltip>
                  </q-badge>
                </div>
              </li>
              <li>
                <q-input
                  v-model="cardInfo.pwd"
                  dense
                  type="password"
                  placeholder="4자리 이상"
                />
              </li>
            </ul>
            <ul v-if="saveMode === 'C'">
              <li>비밀번호 확인</li>
              <li>
                <q-input
                  v-model="pwdChk"
                  dense
                  type="password"
                  placeholder="입력"
                />
              </li>
            </ul>
          </li>
        </ul>
      </div>

      <div class="mycard_btn_wrap">
        <div class="mycard_btn">
          <BtnComp_default
            v-if="saveMode === 'C' || isEditMode"
            btn-label="저장"
            :disable="isDisabel"
            @click="onClickSave"
          />
          <BtnComp_default
            v-if="saveMode === 'U' && !isEditMode"
            btn-label="수정"
            :disable="isDisabel"
            @click="isEditMode = true"
          />
          <BtnComp_red
            v-if="saveMode === 'U'"
            btn_redLabel="삭제"
            @click="onClickDelete"
          />
          <BtnComp_active
            v-if="saveMode === 'C'"
            btn-label="취소"
            @click="onClickCancel"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, computed, onMounted, inject } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

const userInfo = util.getUserData();
const bus = inject("bus");

import SelectComp_default from "src/components/web/common/SelectComp_default.vue";
import RadioComp_a from "src/components/web/common/RadioComp_a.vue";
import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_red from "src/components/web/common/BtnComp_red.vue";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import router from "src/router";

const pgOptions = [
  {
    label: "이니시스",
    value: "INICIS",
  },
  {
    label: "팜페이",
    value: "PHARMPAY",
  },
];

// const cardOption = computed(() => {
//   if (cardInfo.value.pg === "INICIS") {
//     return ["신한"];
//   } else if (cardInfo.value.pg === "PHARMPAY") {
//     return ["삼성", "현대", "국민", "비씨"];
//   }

//   return [];
// });

const cardOption = ["신한", "삼성", "현대", "국민", "비씨"];

const pwdChk = ref("");

function onChangeSelect(e) {
  e == "신한"
    ? (cardInfo.value.pg = "INICIS")
    : (cardInfo.value.pg = "PHARMPAY");
}

const cardInfo = ref({
  custCd: userInfo.CUST_CD,
  userId: userInfo.USER_ID,
  sno: "",
  cardNm: "",
  pg: "",
  cardComp: "",
  cardNo: "",
  cardNoV: "",
  cardMmYy: "",
  expYy: "",
  expMm: "",
  pwd: "",
  saveMode: "",
});

const saveMode = ref(""); // C: 저장, U: 수정
const isEditMode = ref(false);
const isDisable = computed(() => {
  if (saveMode.value === "C") {
    return false;
  } else if (saveMode.value === "U" && isEditMode.value) {
    return false;
  }
  return true;
});

const isDisable2 = computed(() => {
  if (saveMode.value === "C") {
    return false;
  } else if (saveMode.value === "U" && isEditMode.value) {
    return true;
  }
  return true;
});

const isEqualPwd = computed(
  () => cardInfo.value.pwd !== "" && cardInfo.value.pwd === pwdChk.value
);

const searchParam = ref({
  custCd: "",
  sno: "",
});

const isDisabel = computed(() => {
  if (userInfo.USER_GB_CD === "20") {
    return true;
  }
  return false;
});

//---------------------------- Function ------------------------------//

async function validateCardInfo() {
  const cardNm = cardInfo.value.cardNm;
  const pg = cardInfo.value.pg;
  const cardComp = cardInfo.value.cardComp;
  const cardNo = cardInfo.value.cardNoV;
  const cardMmYy = cardInfo.value.cardMmYy;
  const pwd = cardInfo.value.pwd;

  if (cardNm === "") {
    alert("카드명칭을 입력해주세요.");
    return false;
  }

  if (pg === "") {
    alert("PG사를 선택해주세요.");
    return false;
  }

  if (cardComp === "") {
    alert("카드사를 선택해주세요.");
    return false;
  }

  if (cardNo === "") {
    alert("카드번호를 입력해주세요.");
    return false;
  } else {
    const regSpace = /\s/g;
    const regex = /^(\d{4})-(\d{4})-(\d{4})-(\d{4})$/;

    if (!regex.test(cardNo.replace(regSpace, ""))) {
      alert("카드번호 형식이 올바르지 않습니다.");
      return false;
    }
  }

  if (cardMmYy === "") {
    alert("유효기간을 입력해주세요.");
    return false;
  }

  if (pwd === "") {
    alert("비밀번호를 입력해주세요.");
    return false;
  }

  if (saveMode.value === "C" && !isEqualPwd.value) {
    alert("비밀번호를 확인해주세요.");
    return false;
  }

  return true;
}

async function initCardInfo() {
  cardInfo.value.sno = "";
  cardInfo.value.cardNm = "";
  cardInfo.value.pg = "";
  cardInfo.value.cardComp = "";
  cardInfo.value.cardNo = "";
  cardInfo.value.cardNoV = "";
  cardInfo.value.cardMmYy = "";
  cardInfo.value.expYy = "";
  cardInfo.value.expMm = "";
  cardInfo.value.pwd = "";
  pwdChk.value = "";
}

// 저장
async function onClickSave() {
  // if (!validateCardInfo()) {
  //   return;
  // }

  const cardNm = cardInfo.value.cardNm;
  const pg = cardInfo.value.pg;
  const cardComp = cardInfo.value.cardComp;
  const cardNo = cardInfo.value.cardNoV;
  const cardMmYy = cardInfo.value.cardMmYy;
  const pwd = cardInfo.value.pwd;

  if (cardNm === "") {
    alert("카드명칭을 입력해주세요.");
    return;
  }

  if (pg === "") {
    alert("PG사를 선택해주세요.");
    return;
  }

  if (cardComp === "") {
    alert("카드사를 선택해주세요.");
    return;
  }

  if (cardNo === "") {
    alert("카드번호를 입력해주세요.");
    return;
  } else {
    const regSpace = /\s/g;
    const regex = /^(\d{4})-(\d{4})-(\d{4})-(\d{4})$/;

    if (!regex.test(cardNo.replace(regSpace, ""))) {
      alert("카드번호 형식이 올바르지 않습니다.");
      return;
    }
  }

  if (cardMmYy === "") {
    alert("유효기간을 입력해주세요.");
    return;
  }

  if (pwd === "") {
    alert("비밀번호를 입력해주세요.");
    return;
  }

  if (cardInfo.value.pwd.length < 4) {
    alert("비밀번호를 4자리 이상 입력해주세요.");
    return;
  }

  if (saveMode.value === "C" && !isEqualPwd.value) {
    alert("비밀번호를 확인해주세요.");
    return;
  }

  const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"\s]/gi;

  cardInfo.value.expMm = cardMmYy.substring(0, 2);
  cardInfo.value.expYy = cardMmYy.substring(3);
  cardInfo.value.cardNo = cardNo.replace(match, "");
  cardInfo.value.saveMode = saveMode.value;

  if (saveMode.value === "U") {
    try {
      await api.get("/payment/pwdChk", { params: cardInfo.value });
    } catch (error) {
      alert(error.response.data);
      return;
    }
  }

  await api.post("/payment/saveMyCard", cardInfo.value);

  if (saveMode.value === "C") {
    util.showNotify("카드가 등록되었습니다.");
  } else if (saveMode.value === "U") {
    util.showNotify("카드정보가 수정되었습니다.");
  }

  await initCardInfo();

  bus.emit("refreshMyCardList");
}

// 삭제
async function onClickDelete() {
  // if (cardInfo.value.pwd === "") {
  //   alert("비밀번호를 입력해주세요.");
  //   return false;
  // }

  // 비밀번호 체크
  try {
    // const match = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"\s]/gi;

    // cardInfo.value.expMm = cardInfo.value.cardMmYy.substring(0, 2);
    // cardInfo.value.expYy = cardInfo.value.cardMmYy.substring(3);
    // cardInfo.value.cardNo = cardInfo.value.cardNoV.replace(match, "");

    // await api.get("/payment/pwdChk", { params: cardInfo.value });

    const delCnfrmMsg = `${cardInfo.value.cardNm} 카드를 삭제하시겠습니까?`;

    if (!confirm(delCnfrmMsg)) return;

    await api.delete("/payment/removeMyCard", { params: cardInfo.value });

    util.showNotify(`${cardInfo.value.cardNm} 카드가 삭제되었습니다.`);
    initCardInfo();

    await bus.emit("refreshMyCardList");
  } catch (error) {
    alert(error.response.data);
    return;
  }
}

// 취소
function onClickCancel() {
  initCardInfo();
}

function vaildCardMm() {
  const cardMm = cardInfo.value.cardMmYy;

  if (cardMm !== "" && cardMm.length > 1) {
    const chkVal = cardMm.substring(0, 2);

    if (Number(chkVal) > 12) {
      alert("유효기간 월 형식이 잘못되었습니다.");
      cardInfo.value.cardMmYy = "";
    }
  }
}

async function fetchData(data) {
  searchParam.value.custCd = data.CUST_CD;
  searchParam.value.sno = data.SNO;

  const res = await api.get("/payment/myCardDtl", {
    params: searchParam.value,
  });

  cardInfo.value.sno = res.SNO;
  cardInfo.value.cardNm = res.CARD_NM;
  cardInfo.value.pg = res.PG_GB;
  cardInfo.value.cardComp = res.CARD_COMP;
  cardInfo.value.cardNoV = res.CARD_NO;
  cardInfo.value.cardMmYy = res.EXP_MM + res.EXP_YY;
}

bus.on("regNewCard", async (data) => {
  saveMode.value = data;

  await initCardInfo();
});

bus.on("myCardDetail", async (data) => {
  saveMode.value = "U";
  isEditMode.value = false;

  if (data.CUST_CD != userInfo.CUST_CD) {
    alert(
      "등록된 카드의 거래처코드(" +
        data.CUST_CD +
        ")와 로그인한 거래처 코드(" +
        userInfo.CUST_CD +
        ")가 맞지 않아 카드 수정 및 삭제가 불가합니다."
    );
    saveMode.value = "";
    return;
  }

  await fetchData(data);
});
</script>

<style lang="scss">
.paymentTable_2b {
  // height: 740px;
  height: 690px;
  overflow-y: auto;
}
.mycardTable_wrap {
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}
.q-table_header_wrap {
  height: 32px;
}
.markup_table_header_wrap {
  @include fs-5;
  @include fw-7;
  border-top: 1px solid $th-line;
  border-bottom: 1px solid $th-line;
  text-align: center;
  background-color: $th-bg;
  margin-top: 16px;
  padding: 8px 0;
}
.mycard_detail {
  padding: 0 !important;
  line-height: 18px !important;
}
.mycardTable_wrap ul {
  margin-bottom: 10px;
}
.mycardTable_wrap ul:last-child {
  margin-bottom: 0;
}
.mycardTable_wrap li {
  @include fs-5;
  color: #000;
}
.mycardTable_wrap ul li ul {
  @include flex_start;
}
.mycardTable_wrap ul li ul li:nth-child(1),
.mycardTable_wrap ul li ul li:nth-child(3) {
  @include fw-7;
  width: 200px;
}
.mycardTable_wrap ul li ul li:nth-child(2),
.mycardTable_wrap ul li ul li:nth-child(4) {
  width: 230px;
}
.mycardTable_wrap ul li ul li .q-placeholder {
  @include fs-5;
  color: rgba(0, 0, 0, 1);
}

.mycard_btn_wrap {
  @include flex_end;
  margin-top: 15px;
}
.mycard_btn {
  @include flex_center;
  gap: 8px;
}
.paymentTable_2b {
  .q-field__control,
  .q-field__control-container {
    height: 32px;
    line-height: 32px;
  }
  .q-field--filled.q-field--highlighted .q-field__control:before {
    background: rgba(0, 0, 0, 0.06);
  }
  .q-field__native {
    padding: 0;
  }
  .mycard_btn .q-btn {
    min-width: 56px;
  }
}
</style>

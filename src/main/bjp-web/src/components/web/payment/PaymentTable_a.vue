<template>
  <div class="paymentTable_1a">
    <div class="q-table_wrap">
      <div class="q-table_header_wrap">
        <div class="q-table_header_title">
          <p>카드결제</p>
        </div>
      </div>
      <div class="paySub_header_wrap">
        <div class="paySub_left">
          <div class="payby_wrap">
            <div class="payby_title">
              <p>결제방법</p>
            </div>
            <div class="payby_radio">
              <!-- <RadioComp_a
                v-model="paybyRadio"
                :radio_options="paybyRadioOptions"
                @update:model-value="onChangePaybyRadio"
              /> -->
              <div>
                <q-option-group
                  v-model="paybyRadio"
                  :options="filteredPaybyRadioOptions"
                  color="blue-10"
                  size="xs"
                  inline
                  dense
                  @update:model-value="onChangePaybyRadio"
                />
              </div>
            </div>
          </div>
          <div v-if="paybyRadio === '1'" class="payby_card">
            <div class="payby_title">
              <p>카드선택</p>
            </div>
            <div class="card_select_wrap slct-wrap">
              <!-- <SelectComp_default
                :selectModel="selectModel"
                :selectOption="selectOption"
              /> -->
              <q-select
                v-model="selectCard"
                class="select_default"
                :options="cardListOptions"
                borderless
                dense
                emit-value
                map-options
                @update:model-value="onUpdate"
              />
            </div>
            <!--<BtnComp_default btnLabel="카드선택" @click="onClickSelectCard" /> -->
          </div>
        </div>
        <div class="paySub_right">
          <BtnComp_br
            btn_br_link="/mycard"
            btn_br_top="나의"
            btn_br_bottom="카드정보"
          />
          <BtnComp_br_purple btn_br_top="카드회사" btn_br_bottom="공지사항">
            <q-tooltip anchor="top right" self="top left">
              <p style="font-size: 14px; margin-bottom: 20px">
                &#60;신한카드 결제 관련 공지사항&#62;<br />
                신한카드사에서 최근 당사로 공문을 보내와, 특정카드의
                특정금액(5,999원 등) <br />
                거래를 비정상 거래로 규정하고 해당 거래의 즉시 중단 및 소명자료
                제출을 요청하였습니다. <br />
                또한 동일 유형의 거래가 지속 발생될 경우 거래정지 등의 조치가
                취해질 수 있다고 알려 왔습니다. <br />
                따라서 약품대금을 소액 다건으로 결제(5,999원 등)해 오셨던
                경우에는 <br />
                향후 소액 다건 결제의 중지를 요청드립니다. <br />
                신한카드의 경우 최소결제 가능 금액을 5만원으로 조정하였습니다.
                감사합니다.<br />
              </p>
              <p style="font-size: 14px">
                ※ 카드결제한 금액은 2~3일 후에(주말제외) 입금 확인 후 잔고에
                반영됩니다. <br />
                "카드번호와 유효기간을 잘못 입력하여 타인의 카드로 결제가
                됨으로써 <br />
                당사로 항의전화가 오는 사례가 있습니다. <br />
                이경우 카드번호나 유효기간을 잘못 입력한 개인에게 형사적 민사적
                책임이 있으므로 <br />
                카드번호와 유효기간을 다시한번 확인하신 후 결제 진행하시기
                바랍니다."
              </p>
            </q-tooltip>
          </BtnComp_br_purple>
        </div>
      </div>

      <div class="paymentTable_wrap">
        <div class="cardWrap">
          <dl>
            <dt></dt>
            <dd>
              <q-option-group
                v-if="paybyRadio === '1'"
                v-model="cardInfo.cardGb"
                :options="filteredcardGbOptions"
                color="blue-10"
                size="xs"
                inline
                dense
                :disable="isDisable"
              />
              <q-option-group
                v-if="paybyRadio === '2'"
                v-model="cardInfo.simplCardGb"
                :options="simplPmntGbOptions"
                color="blue-10"
                size="xs"
                inline
                dense
              />
            </dd>
          </dl>
          <dl v-if="paybyRadio === '1'" class="card_num">
            <dt>카드번호</dt>
            <dd>
              <q-input
                ref="card1"
                v-model="cardInfo.cardNo1"
                input-class="input_default "
                borderless
                dense
                maxlength="4"
                mask="####"
                :disable="isDisable"
                @update:model-value="autoMove($event, 4, 'card1')"
              />-
              <q-input
                ref="card2"
                v-model="cardInfo.cardNo2"
                input-class="input_default"
                borderless
                dense
                maxlength="4"
                type="password"
                mask="####"
                :disable="isDisable"
                @update:model-value="autoMove($event, 4, 'card2')"
              />-
              <q-input
                ref="card3"
                v-model="cardInfo.cardNo3"
                input-class="input_default"
                borderless
                dense
                maxlength="4"
                type="password"
                mask="####"
                :disable="isDisable"
                @update:model-value="autoMove($event, 4, 'card3')"
              />-
              <q-input
                ref="card4"
                v-model="cardInfo.cardNo4"
                input-class="input_default"
                borderless
                dense
                maxlength="4"
                mask="####"
                :disable="isDisable"
                @update:model-value="autoMove($event, 4, 'card4')"
              />
            </dd>
          </dl>
          <dl v-if="paybyRadio === '1'">
            <dt>유효기간</dt>
            <dd>
              <q-input
                ref="cardMonth"
                v-model="cardInfo.cardM"
                input-class="input_default"
                borderless
                dense
                style="width: 40px"
                mask="##"
                placeholder="MM"
                :disable="isDisable"
                @update:model-value="autoMove($event, 2, 'cardMonth')"
              />/
              <q-input
                ref="cardYear"
                v-model="cardInfo.cardY"
                input-class="input_default"
                borderless
                dense
                style="width: 40px"
                mask="##"
                placeholder="YY"
                :disable="isDisable"
                @update:model-value="autoMove($event, 2, 'cardYear')"
              />
            </dd>
            <dt v-if="cardInfo.cardGb === 'INICIS' && userInfo.BRCH_CD != '53'">
              개인카드/기업카드
            </dt>
            <dd v-if="cardInfo.cardGb === 'INICIS' && userInfo.BRCH_CD != '53'">
              <q-input
                v-model="cardInfo.birthDt"
                input-class="input_default"
                mask="##########"
                borderless
                dense
              />
            </dd>
          </dl>
          <dl
            v-if="cardInfo.cardGb === 'INICIS' && userInfo.BRCH_CD != '53'"
            class="card_alert"
          >
            <dt></dt>
            <dd></dd>
            <dd>
              <span
                >★개인카드 : 생년월일(6자리) 입력<br />
                ★기업카드 : 사업자등록번호(10자리) 입력</span
              >
            </dd>
          </dl>
          <dl v-if="paybyRadio === '1'">
            <dt>할부</dt>
            <dd>
              <div class="slct-wrap">
                <q-select
                  ref="cardIns"
                  v-model="cardInfo.ins"
                  class="select_default"
                  :options="monthOption"
                  style="background-color: #fff"
                  borderless
                  dense
                  emit-value
                  map-options
                />
              </div>
            </dd>
            <dt v-if="selectCard !== 0">비밀번호</dt>
            <dd v-if="selectCard !== 0" class="flex_start">
              <!-- <InputComp_password
                  v-model="cardInfo.pwd"
                  style="width: 80px"
                /> -->
              <q-input
                v-model="cardInfo.pwd"
                input-class="input_default"
                borderless
                dense
                type="password"
              />
            </dd>
          </dl>
          <div class="btnArea">
            <BtnComp_default btnLabel="결제" @click="onClickPayment" />
          </div>
        </div>
      </div>
    </div>
  </div>
  <form id="creditForm">
    <input type="hidden" name="custCd" :value="cardInfo.custCd" />
    <input type="hidden" name="brchCd" :value="cardInfo.brchCd" />
    <input type="hidden" name="CARD_GB" :value="cardInfo.simplCardGb" />
    <input type="hidden" name="g_CARDCODE" :value="cardInfo.g_CARDCODE" />
    <input type="hidden" name="g_amountTi" :value="cardInfo.g_amountTi" />
    <input type="hidden" name="CARD_GB" :value="cardInfo.simplCardGb" />
    <input
      type="hidden"
      name="PAYMENT_AMOUNT_TI"
      :value="cardInfo.paymentAmountTi"
    />
  </form>
  <form id="kakaoPayForm">
    <input type="hidden" name="custCd" :value="cardInfo.custCd" />
    <input type="hidden" name="CARD_GB" :value="cardInfo.simplCardGb" />
    <input type="hidden" name="g_CARDCODE" :value="cardInfo.g_CARDCODE" />
    <input type="hidden" name="g_amountTi" :value="cardInfo.g_amountTi" />
    <input type="hidden" name="CARD_GB" :value="cardInfo.simplCardGb" />
    <input
      type="hidden"
      name="PAYMENT_AMOUNT_TI"
      :value="cardInfo.paymentAmountTi"
    />
  </form>
  <q-form
    id="kakaoForm"
    action="http://localhost:1010/inicis/reqPayment"
    method="post"
    target="PayForm"
  >
    <input type="hidden" name="price" :value="cardInfo.paymentAmountTi" />
    <input
      type="hidden"
      name="buyertel"
      :value="userInfo.HP_NO_CUST.replaceAll('-', '')"
    />
    <input type="hidden" name="goodname" value="백제약품" />
    <input type="hidden" name="gopaymethod" value="onlykakaopay" />
    <input type="hidden" name="acceptmethod" value="cardonly" />
  </q-form>
  <!-- <iframe
    id="PayForm"
    name="PayForm"
    style="display: block; width: 80vw; height: 80vh; border: none"
  ></iframe> -->
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { ref, computed, onMounted, inject, watch } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import { useRoute, useRouter } from "vue-router";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import SelectComp_default from "src/components/web/common/SelectComp_default.vue";
import RadioComp_a from "src/components/web/common/RadioComp_a.vue";
import BtnComp_br from "src/components/web/common/BtnComp_br.vue";
import BtnComp_br_purple from "src/components/web/common/BtnComp_br_purple.vue";
import InputComp_default from "src/components/web/common/InputComp_default.vue";
import InputComp_password from "src/components/web/common/InputComp_password.vue";

const route = useRoute();
const router = useRouter();
const userInfo = util.getUserData();
const bus = inject("bus");
const selectCard = ref(0);
const cardListOptions = ref([]);

const paybyRadio = ref("1");
bus.on("pmntAmt", (data) => {
  const num = Number(String(data).replace(/,/g, ""));
  cardInfo.value.paymentAmountTi = num;
});
const paybyRadioOptions = [
  {
    label: "일반결제",
    value: "1",
  },
  {
    label: "카드 간편결제",
    value: "2",
  },
];
// 🔍 userInfo.BRCH_CD 값에 따라 옵션을 필터링하여 제공
const filteredPaybyRadioOptions = computed(() => {
  const restrictedBranches = ["53", "58", "59", "61"];

  if (restrictedBranches.includes(userInfo.BRCH_CD)) {
    // 카드 간편결제 옵션을 제외 (value가 '2'인 항목을 필터링)
    return paybyRadioOptions.filter((option) => option.value !== "2");
  }
  return paybyRadioOptions;
});
const cardInfo = ref({
  userId: userInfo.USER_ID,
  custCd: userInfo.CUST_CD,
  brchCd: userInfo.BRCH_CD,
  cardGb: "",
  simplCardGb: "",
  ins: "",
  cardNo1: "",
  cardNo2: "",
  cardNo3: "",
  cardNo4: "",
  cardNo: "",
  birthDt: "",
  cardM: "",
  cardY: "",
  paymentAmountTi: "",
  pwd: "",
  expYy: "",
  expMm: "",
  g_CARDCODE: "",
  g_amountTi: "",
});

const cardGbOptions = [
  {
    label: "신한, 롯데, 비씨 (최소결제 가능 금액: 5만원)",
    value: "INICIS",
  },
  {
    label: "삼성, 현대, 국민",
    value: "PHARMPAY",
  },
];
// ✅ 옵션 필터링 로직 (조건에 따라 동적으로 표시)
const filteredcardGbOptions = computed(() => {
  const branchCode = userInfo.BRCH_CD;
  const filteredOptions = [];

  // 1. 신한, 비씨 (카드결제1) - 조건 확인 후 추가
  if (
    branchCode !== "53" &&
    branchCode !== "58" &&
    branchCode !== "59" &&
    branchCode !== "61"
  ) {
    filteredOptions.push({
      label: "신한, 롯데, 비씨 (최소결제 가능 금액: 5만원)",
      value: "INICIS",
    });
  }

  // 2. 카드결제1 표시 (BRCH_CD == '53')
  if (branchCode === "53") {
    filteredOptions.push({ label: "카드결제1", value: "INICIS" });
  }

  // 3. 삼성, 현대, 국민 (카드결제2) - 조건 확인 후 추가
  if (
    branchCode !== "53" &&
    branchCode !== "58" &&
    branchCode !== "59" &&
    branchCode !== "61"
  ) {
    filteredOptions.push({ label: "삼성, 현대, 국민", value: "PHARMPAY" });
  }

  // 4. 카드결제2 표시 (BRCH_CD == '53' || '58' || '59' || '61')
  if (["53", "58", "59", "61"].includes(branchCode)) {
    filteredOptions.push({ label: "카드결제2", value: "PHARMPAY" });
  }

  return filteredOptions;
});
const simplPmntGbOptions = [
  {
    label: "신한, 롯데, 비씨(최소결제 가능 금액: 5만원)",
    value: "INICIS_SHINHAN",
  },
  {
    label: "현대, 삼성",
    value: "INICIS_ETC",
  },
];

const monthOption = [
  { label: "", value: "-1" },
  { label: "일시불", value: "00" },
  { label: "2 개월", value: "02" },
  { label: "3 개월", value: "03" },
  { label: "4 개월", value: "04" },
  { label: "5 개월", value: "05" },
  { label: "6 개월", value: "06" },
  { label: "7 개월", value: "07" },
  { label: "8 개월", value: "08" },
  { label: "9 개월", value: "09" },
  { label: "10 개월", value: "10" },
  { label: "11 개월", value: "11" },
  { label: "12 개월", value: "12" },
];

const searchParam = ref({
  custCd: userInfo.CUST_CD,
  cardNo: "",
  sno: "",
  cardGb: "",
});

const isDisable = computed(() => selectCard.value !== 0);

const card1 = ref(null);
const card2 = ref(null);
const card3 = ref(null);
const card4 = ref(null);
const cardMonth = ref(null);
const cardYear = ref(null);
const cardIns = ref(null);

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/payment/myCard", {
    params: searchParam.value,
  });

  cardListOptions.value.push({ value: 0, label: "직접 입력" });

  res.forEach((m) => {
    cardListOptions.value.push({
      value: m.SNO,
      label: m.CARD_NM,
    });
  });
}

function onChangePaybyRadio() {
  cardInfo.value.cardGb = "";
  cardInfo.value.simplCardGb = "";

  initCardInfo();
}

// 카드선택
// async function onClickSelectCard() {
//   if (selectCard.value === 0) return;

//   searchParam.value.sno = selectCard.value;

//   const res = await api.get("/payment/myCardDtl", {
//     params: searchParam.value,
//   });

//   const cardNoDec = res.CARD_NO;

//   cardInfo.value.cardGb = res.PG_GB;
//   cardInfo.value.cardNo1 = cardNoDec.substr(0, 4);
//   cardInfo.value.cardNo2 = cardNoDec.substr(4, 4);
//   cardInfo.value.cardNo3 = cardNoDec.substr(8, 4);
//   cardInfo.value.cardNo4 = cardNoDec.substr(12, 4);
//   cardInfo.value.cardM = res.EXP_MM;
//   cardInfo.value.cardY = res.EXP_YY;
// }

function initCardInfo() {
  cardInfo.value.cardGb = "";
  cardInfo.value.simplCardGb = "";
  cardInfo.value.ins = "";
  cardInfo.value.cardNo1 = "";
  cardInfo.value.cardNo2 = "";
  cardInfo.value.cardNo3 = "";
  cardInfo.value.cardNo4 = "";
  cardInfo.value.cardNo = "";
  cardInfo.value.cardM = "";
  cardInfo.value.cardY = "";
  cardInfo.value.pwd = "";
  cardInfo.value.expYy = "";
  cardInfo.value.expMm = "";
  cardInfo.value.birthDt = "";
}

//간편결제 popup
async function makeNewWindow() {
  const frm = document.getElementById("creditForm");
  const url = "/payment/wpaystdPayAuthCardRequest";
  let IFWin;
  const OpenOption =
    "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=550,height=650,top=100,left=400,";
  document.cookie = "cross-site-cookie=bar; SameSite=None; Secure";
  IFWin = window.open("", "IfWindow", OpenOption);

  frm.action = url; // 개발 및 운영 URL - 로컬에서 테스트시, 하기의 로컬 URL을 사용하거나 vue project 빌드 후 1010으로 접속하여 진행
  //frm.action = "http://localhost:1010/payment/wpaystdPayAuthCardRequest"; // 로컬 URL
  frm.target = "IfWindow";
  frm.method = "POST";
  frm.submit();
}

// 결제
async function onClickPayment() {
  const cardGb = cardInfo.value.cardGb;
  const simplCardGb = cardInfo.value.simplCardGb;
  const cardNo1 = cardInfo.value.cardNo1;
  const cardNo2 = cardInfo.value.cardNo2;
  const cardNo3 = cardInfo.value.cardNo3;
  const cardNo4 = cardInfo.value.cardNo4;
  const cardM = cardInfo.value.cardM;
  const cardY = cardInfo.value.cardY;
  const paymentAmountTi = cardInfo.value.paymentAmountTi;
  const pwd = cardInfo.value.pwd;
  const birthDt = cardInfo.value.birthDt;
  const cardIns = cardInfo.value.ins;
  //const custCd = cardInfo.value.custCd;

  if (paymentAmountTi === 0) {
    alert("결제금액이 0원입니다.");
    return;
  }

  if (paybyRadio.value !== "3") {
    if (cardGb === "" && simplCardGb === "") {
      alert("카드사를 선택하세요.");
      return;
    }

    if (userInfo.BRCH_CD !== "53") {
      if (
        (cardGb === "INICIS" || simplCardGb === "INICIS_SHINHAN") &&
        paymentAmountTi < 50000
      ) {
        alert("신한카드 최소결제 가능금액은 50,000 원 입니다.");
        return;
      }
    }

    if (simplCardGb === "INICIS_SHINHAN" || simplCardGb === "INICIS_ETC") {
      if (paymentAmountTi < 10) {
        alert("결제금액은 10원 이상 입력하세요.");
        return;
      }
    }

    if (paybyRadio.value === "1") {
      if (
        cardNo1 === "" ||
        cardNo2 === "" ||
        cardNo3 === "" ||
        cardNo4 === ""
      ) {
        alert("카드번호를 입력하세요.");
        return;
      }

      if (cardGb === "INICIS" && birthDt === "") {
        alert("개인카드/기업카드를 입력하세요.");
        return;
      }

      if (cardM === "" || cardY === "") {
        alert("유효기간을 입력하세요.");
        return;
      }

      if (pwd === "" && selectCard.value !== 0) {
        alert("비밀번호를 입력하세요.");
        return;
      }

      if (cardIns == "") {
        alert("할부 개월을 선택하여 주십시오.");
        return;
      }
    }
  }

  cardInfo.value.cardNo = cardNo1 + cardNo2 + cardNo3 + cardNo4;
  cardInfo.value.expMm = cardM;
  cardInfo.value.expYy = cardY;

  if (paybyRadio.value === "1") {
    /** 일반결제 **/
    // 비밀번호 체크
    if (selectCard.value !== 0) {
      try {
        await api.get("/payment/pwdChk", { params: cardInfo.value });
      } catch (error) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
      }
    }

    try {
      if (!confirm("결제를 진행하시겠습니까?")) return;
      await api.post("/payment/reqPayment", cardInfo.value);

      //util.showNotify("정상처리 되었습니다.");
      alert("정상처리 되었습니다.");
      initCardInfo();
    } catch (error) {
      alert(error.response.data);

      // initCardInfo();
    }
  } else if (paybyRadio.value === "2") {
    /** 간편결제 **/

    if (window.location.href.startsWith("https") === false) {
      alert(
        "간편결제시에는 https://www.ibjp.co.kr (보안URL)로 접근하여야 합니다."
      );
      return;
    }

    // Wpay user Key 조회
    searchParam.value.cardGb = cardInfo.value.simplCardGb;
    const res = await api.get("/payment/wPayUserKey", {
      params: searchParam.value,
    });

    if (res.WPAY_USER_KEY === "") {
      let msg = `간편결제를 이용하시려면\n`;
      msg += `최초 1회 회원가입이 필요합니다.\n`;
      msg += `회원 가입하시겠습니까?`;

      if (!confirm(msg)) return;
      makeNewWindow();
    } else {
      makeNewWindow();
    }
  } else if (paybyRadio.value === "3") {
    // 네이버페이

    const OpenOption =
      "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=900,height=650,top=100,left=400,";
    //const popup = window.open("kakaoPay", "PayForm", OpenOption);
    const popup = window.open("naverPay", "PayForm", OpenOption);

    document.getElementById("kakaoForm").submit();
  }

  bus.emit("initPmntAmt");
}

// 커서이동
function autoMove(val, maxLength, nowField) {
  if (val.length === maxLength) {
    if (nowField === "card1") {
      card2.value.focus();
    } else if (nowField === "card2") {
      card3.value.focus();
    } else if (nowField === "card3") {
      card4.value.focus();
    } else if (nowField === "card4") {
      cardMonth.value.focus();
    } else if (nowField === "cardMonth") {
      cardYear.value.focus();
    } else if (nowField === "cardYear") {
      cardIns.value.focus();
    }
  }
}

watch(selectCard, () => {
  initCardInfo();
});

onMounted(async () => {
  await fetchData();

  if (!util.isEmpty(route.query.MSG)) {
    const paymentMSG =
      route.query.MSG == "success"
        ? "결제가 완료되었습니다."
        : "결제가 취소되었습니다.";

    util.alert("확인", paymentMSG, function () {
      router.push({ path: "/payment" });
    });
  }

  //자식창에서 넘어오는 거 확인
  window.addEventListener("message", (e) => {
    // 부모창의 함수 실행
    if (e.data.functionName === "inicisClose") {
      util.alert("확인", "결제를 취소하였습니다.", function () {
        router.go(router.currentRoute);
      });
    }
  });
});

const onUpdate = async (val) => {
  selectCard.value = val;
  if (selectCard.value === 0) return;

  searchParam.value.sno = selectCard.value;

  const res = await api.get("/payment/myCardDtl", {
    params: searchParam.value,
  });

  const cardNoDec = res.CARD_NO;

  cardInfo.value.cardGb = res.PG_GB;
  cardInfo.value.cardNo1 = cardNoDec.substr(0, 4);
  cardInfo.value.cardNo2 = cardNoDec.substr(4, 4);
  cardInfo.value.cardNo3 = cardNoDec.substr(8, 4);
  cardInfo.value.cardNo4 = cardNoDec.substr(12, 4);
  cardInfo.value.cardM = res.EXP_MM;
  cardInfo.value.cardY = res.EXP_YY;
};
</script>

<style lang="scss" scoped>
$dt-w: 70px;
$dt-w2: 130px;

.paymentTable_1a {
  // height: 320px;
  height: 360px;
  @media screen and (max-width: 830px) {
    height: auto;
  }
}
.paySub_header_wrap {
  @include flex_between;
  align-items: center;
  margin-top: 8px;
  padding: 10px 8px;
  border-top: 1px solid $th-line;
  border-bottom: 1px solid $th-line;
  // .paySub_left {
  //   width: 100%;
  // }
}
.payby_wrap,
.payby_card {
  @include flex_start;
  // gap: 14px;
}
.payby_wrap {
  margin-bottom: 12px;
}
.payby_title {
  @include fs-5;
  @include fw-6;
  // width: 84px;
  width: $dt-w;
}
.card_select_wrap {
  // width: 400px;
  width: 150px !important;
}
.paySub_right {
  @include flex_end;
  gap: 8px;
  flex-wrap: wrap;
}

.paymentTable_wrap {
  // background-color: #eeeeee;
  // border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  //border-bottom: 1px solid $line-300;
  padding: 10px 8px;
}

.paymentTable_wrap > ul > li {
  display: flex;
  flex-direction: column;
  // gap: 12px;
}
// .paymentTable_wrap ul {
//   margin-bottom: 24px;
// }
.paymentTable_wrap ul:last-child {
  margin-bottom: 0;
}
.paymentTable_wrap li {
  @include fs-5;
  color: #000;
}
.paymentTable_wrap ul li ul {
  @include flex_start;
  height: 32px;
}
.paymentTable_wrap ul li ul + ul {
  margin-top: 12px;
}
.paymentTable_wrap ul li ul:last-child {
  @include flex_end;
  li {
    padding: 0;
    text-align: right;
  }
}
.paymentTable_wrap ul li ul li:nth-child(1) {
  width: 130px;
}
// .paymentTable_wrap ul li ul li:nth-child(3) {
//   width: 90px;
// }
.paymentTable_wrap ul li ul li:nth-child(2),
.paymentTable_wrap ul li ul li:nth-child(4) {
  // min-width: 200px;
  max-width: 400px;
  padding: 0 8px;
}
.paymentTable_wrap ul li ul li span {
  @include fs-6;
  color: #000;
}

.q-option-group {
  display: flex;
  gap: 16px;
  // flex-wrap: wrap;
  @media screen and (max-width: 1330px) {
    gap: 0;
    width: calc(100% + 32px);
  }
}

.cardWrap {
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  gap: 10px 0;
  .q-option-group {
    flex-wrap: wrap;
  }
  dl {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    @include fs-5;
    dt {
      width: $dt-w;
      font-weight: bold;
    }
    dd {
      display: inline-flex;
      width: calc(100% - #{$dt-w});
      flex-wrap: wrap;
      gap: 0 3px;
      @media screen and (min-width: 831px) {
        .q-input {
          max-width: 100px;
        }
      }
    }
    &:has(> dd:nth-child(4)) {
      dd:nth-child(2) {
        width: calc(45% - #{$dt-w});
      }
      dt:nth-child(3) {
        width: $dt-w2;
      }
      dd:nth-child(4) {
        width: calc(55% - #{$dt-w2});
      }
    }
    &:has(input:not([type="radio"])) {
      min-height: 32px;
    }
  }
  .card_alert {
    margin-top: -8px;
    dd:nth-child(2) {
      width: calc(45% - #{$dt-w});
    }
    dd:nth-child(3) {
      width: auto;
      @include fs-6;
      color: #000;
    }
  }
  .card_num {
    dd {
      flex-wrap: nowrap;
    }
  }
  .btnArea {
    text-align: right;
  }
}
</style>

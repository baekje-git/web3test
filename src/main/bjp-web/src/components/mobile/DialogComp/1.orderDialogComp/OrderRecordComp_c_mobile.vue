<template>
  <div class="Record_Title">
    <slot></slot>
  </div>
  <div class="flex_center" style="gap: 24px; margin-bottom: 16px">
    <!-- <InputComp_mobile
      v-if="userInfo.USER_GB_CD !== '30'"
      v-model:input_model_props="dataParams.ordWp2Amt"
      style="width: 240px"
    /> -->
    <q-input
      v-model="dataParams.ordWp2Amt"
      class="input-common input-default"
      borderless
      dense
      flat
      @update:model-value="onBlur"
      reverse-fill-mask
      mask="###,###,###,###,###,###,###,###,###"
      @focus="(input) => input.target.select()"
    />
    <BtnComp_mobile
      v-if="userInfo.USER_GB_CD !== '30'"
      buttonText="변경"
      btnStyle="default"
      :is-disable="props.isDisable"
      style="width: 64px"
      @click="onClickEditWp2Amt"
    />
  </div>
  <FilterSingleComp_mobile
    :btnGroups="filterbtnGroups"
    :isActiveBtn="isActiveBtn"
    @selectedSingleBtn="(val) => onClickPeriod(val)"
  />
  <div class="Record_Table_wrap">
    <OrderRecordTable_c_mobile :historyData="rows" :loading="loading" />
  </div>
</template>

<script setup>
//---------------------- Import & Declaration ------------------------//

import { onMounted, ref, computed } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

import FilterComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterComp_mobile.vue";
import OrderRecordTable_c_mobile from "src/components/mobile/DialogComp/TableDialogComp/1.orderTableComp/OrderRecordTable_c_mobile.vue";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import InputComp_mobile from "src/components/mobile/__commonComp/inputComp/InputComp_mobile.vue";
import FilterSingleComp_mobile from "src/components/mobile/DialogComp/FilterDialogComp/FilterSingleComp_mobile.vue";

//로딩표시
const loading = ref(false);

const userInfo = util.getUserData();
const rows = ref([]);
const emit = defineEmits(["editWp2Amt"]);

const filterbtnGroups = [
  {
    title: "기간 설정",
    subtitle: "",
    gridStyle: 4,
    buttons: [
      { btnLabel: "당월" },
      { btnLabel: "전월" },
      { btnLabel: "3개월" },
      { btnLabel: "6개월" },
      { btnLabel: "1년" },
      { btnLabel: "2년" },
      { btnLabel: "3년" },
    ],
  },
];

//필터표시
const model = defineModel();
const isActiveBtn = computed(() => {
  if (util.isEmpty(model.value)) {
    return filterbtnGroups[0].buttons.findIndex(
      (obj) => obj.btnLabel === "6개월"
    );
  }

  return model.value;
});

const props = defineProps({
  ordBasketData: Array,
  isDisable: Boolean,
});

const dataParams = ref({
  ordWp2Amt: props.ordBasketData.ORD_WP2_AMT,
  userId: userInfo.USER_ID,
  custCd: userInfo.CUST_CD,
  saveItemCd: props.ordBasketData.ITEM_CD,
  basketGbCd: "01",
  saveItemGbCd: props.ordBasketData.ITEM_GB_CD,
  dlvBrchCd: props.ordBasketData.DLV_BRCH_CD,
  itemQty: props.ordBasketData.ITEM_QTY,
});

const searchHistoryParam = ref({
  custCd: userInfo.CUST_CD,
  selItemCd: props.ordBasketData.ITEM_CD,
  sellMonth: "",
});

//---------------------------- Function ------------------------------//

async function fetchItemList() {
  loading.value = true;
  const res = await api.get("/ord/history", {
    params: searchHistoryParam.value,
  });

  rows.value = res;
  loading.value = false;
}

// 기간 버튼 클릭
async function onClickPeriod(val) {
  setDate(val);

  //기간표시
  model.value = filterbtnGroups[0].buttons.findIndex(
    (obj) => obj.btnLabel === val
  );

  await fetchItemList();
}

// 변경 버튼 클릭
async function onClickEditWp2Amt() {
  const wp2ChgYn = props.ordBasketData.WP2_CHG_YN;
  const itemNm = props.ordBasketData.ITEM_NM;
  const ordWp2AmtOrg = props.ordBasketData.ORD_WP2_AMT_ORG;
  const minWp2Amt = props.ordBasketData.MIN_WP2_AMT;
  const ordWp2Amt = dataParams.value.ordWp2Amt;
  const maxAmt = props.ordBasketData.MAX_AMT;

  if (ordWp2Amt == null || ordWp2Amt == "" || ordWp2Amt == "0") {
    alert(itemNm + "\n단가를 확인해 주세요.");
    return false;
  }

  if (wp2ChgYn == "Y" && minWp2Amt > ordWp2Amt) {
    alert(
      itemNm +
        "\n입력한 단가가 최소 단가(" +
        util.getNumberFormat(minWp2Amt) +
        ") 보다 적습니다."
    );
    dataParams.value.ordWp2Amt = ordWp2AmtOrg;
    return false;
  }

  //20190117 최대단가 체크로직 추가
  if (maxAmt < ordWp2Amt) {
    alert(itemNm + "\n입력한 단가가 최대 단가(" + maxAmt + ") 보다 많습니다.");
    dataParams.value.ordWp2Amt = ordWp2AmtOrg;
    return false;
  }

  try {
    await api.put("/ord/updateItemQty", dataParams.value);
    util.showNotifyTop("변경되었습니다.");
    emit("editWp2Amt");
  } catch (error) {
    alert(error.response.data);
  }
}

function onBlur(val) {
  let newValue = val.replace(/,/g, "");
  newValue = newValue.replace(/^0+(?!$)/, "");
  newValue = parseInt(newValue);
  dataParams.value.ordWp2Amt = newValue;
}

//기간 설정
function setDate(val) {
  if (val === "당월") {
    searchHistoryParam.value.sellMonth = "0";
  } else if (val === "전월") {
    searchHistoryParam.value.sellMonth = "-1";
  } else if (val === "3개월") {
    searchHistoryParam.value.sellMonth = "-3";
  } else if (val === "6개월") {
    searchHistoryParam.value.sellMonth = "-6";
  } else if (val === "1년") {
    searchHistoryParam.value.sellMonth = "-12";
  } else if (val === "2년") {
    searchHistoryParam.value.sellMonth = "-24";
  } else if (val === "3년") {
    searchHistoryParam.value.sellMonth = "-36";
  }
}

//내가 선택한 필터로 조회되게
onMounted(() => {
  let val = filterbtnGroups[0].buttons[isActiveBtn.value]?.btnLabel || "";
  setDate(val);

  //조회
  fetchItemList();
});
</script>

<style lang="scss" scoped></style>

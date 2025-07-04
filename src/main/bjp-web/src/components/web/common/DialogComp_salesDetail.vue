<template>
  <div class="DialogComp_wrap">
    <!-- <div class="" @click="popupOpen = true">
      <slot></slot>
    </div> -->

    <q-dialog v-model="popupOpen">
      <q-card style="padding: 32px">
        <!-- <q-card-section>
          <div class="q-card_salesDetail_title">
            <p>제약회사 재고 매출내역</p>
            <img src="/src/assets/icons/x-solid.svg" v-close-popup>
          </div>
        </q-card-section> -->
        <div class="dialog_header">
          <span>제약회사 재고 매출내역</span>
        </div>

        <div class="q-card_salesDetail_section_wrap">
          <div class="q-card_salesDetail_table_header_wrap">
            <p>제약회사 재고 매출</p>
            <div class="">
              <div class="q-card_salesDetail_select">
                <!-- <SelectComp_default
                  :selectModel="selectModel" :selectOption="selectOption"
                  style="width: 240px;"
                /> -->
                <div class="Select_default_wrap">
                  <q-select
                    class="select_default"
                    v-model="selectModel"
                    :options="selectOption"
                    borderless
                    dense
                    style="width: 270px"
                  />
                </div>
              </div>

              <div class="q-card_salesDetail_checkbox">
                <RadioComp_a
                  v-model="searchParam.ioYm"
                  :radio_options="checkModelOptions"
                  @update:modelValue="updateRadioModel"
                ></RadioComp_a>
              </div>
            </div>
          </div>
          <q-markup-table style="height: 240px">
            <tbody class="q-card_salesDetail_table">
              <tr>
                <td>제약회사</td>
                <td>{{ searchParam.makerNm }}</td>
              </tr>
              <tr>
                <td>매출자료</td>
                <td>
                  <img
                    src="assets/icons/excel.svg"
                    @click="exportTable('makerSale')"
                  />
                </td>
              </tr>
              <tr>
                <td>재고장</td>
                <td>
                  <img
                    src="assets/icons/excel.svg"
                    @click="exportTable('makerStock')"
                  />
                </td>
              </tr>
            </tbody>
          </q-markup-table>
        </div>

        <q-card-actions class="q-card_btn_wrap">
          <div class="">
            <BtnComp_active btnLabel="닫기" v-close-popup />
          </div>
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, inject, onMounted } from "vue";
import { api } from "boot/axios";
import moment from "moment";
import * as util from "src/support/util";

import BtnComp_default from "src/components/web/common/BtnComp_default.vue";
import BtnComp_active from "src/components/web/common/BtnComp_active.vue";
import SelectComp_default from "src/components/web/common/SelectComp_default.vue";
import CheckComp_a from "src/components/web/common/CheckComp_a.vue";
import RadioComp_a from "src/components/web/common/RadioComp_a.vue";

const bus = inject("bus");
const popupOpen = ref(false);

const searchParam = ref({
  makerNm: "",
  makerCd: "",
  medCd: "",
  brchCd: "",
  startDt: "",
  endDt: "",
  ioYm: "",
});

//제약회사 SelectBox
const selectModel = ref([]);
const selectOption = ref([]);
//날짜 CheckBox
// const salesRadioOptions = dateSetting();

//---------------------------- Function ------------------------------//
const updateRadioModel = (newValue) => {
  searchParam.value.startDt = newValue + "01";
  searchParam.value.endDt = newValue + moment(newValue, "YYYYMM").daysInMonth();
};

//제약회사 조회
async function fetchData() {
  const res = await api.get("/main/getMakerBrchList", {
    params: searchParam.value,
  });
  const responseData = res.map((item) => ({
    label: item.BRCH_NM,
    value: item.BRCH_CD,
  }));
  selectModel.value = { label: res[0].BRCH_NM, value: res[0].BRCH_CD };
  selectOption.value = responseData;

  popupOpen.value = true;
}

bus.off("saleDetail");
bus.on("saleDetail", async (userData) => {
  searchParam.value.makerNm = userData.MAKER_NM;
  searchParam.value.makerCd = userData.MAKER_CD;
  searchParam.value.medCd = userData.SUPPLY_MED_CD;
  searchParam.value.startDt = util.getFromMonth(-1, "YYYYMM") + "01";
  searchParam.value.endDt =
    util.getFromMonth(-1, "YYYYMM") + moment().daysInMonth();
  searchParam.value.ioYm = util.getFromMonth(-1, "YYYYMM");

  await fetchData();
});

//엑셀 다운로드
function exportTable(type) {
  if (!selectModel.value.value) {
    alert("제약회사를 선택하여주세요");
    return;
  }
  searchParam.value.brchCd = selectModel.value.value;

  api
    .get("excel/" + type, {
      params: searchParam.value,
      responseType: "arraybuffer",
    })
    .then(function (res) {
      const url = window.URL.createObjectURL(new Blob([res]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute(
        "download",
        (type === "makerSale" ? "매출자료_" : "재고내역_") +
          searchParam.value.startDt +
          "_" +
          searchParam.value.endDt +
          ".xls"
      );
      document.body.appendChild(link);
      link.click();
      link.remove();
    })
    .catch(function (error) {
      console.log(error);
    });
}

// function dateSetting(){
//   const dateList = [];
//   for(var i = 0 ; i < 6 ; i ++){
//     const month = util.getFromMonth(-i-1, "YYYYMM");
//     dateList.push({label : month, value : month});
//   }

//   return dateList;
// }

const checkModel = ref([""]);

function getFromMonth(gap, format = "YYYY-MM") {
  return moment().add(gap, "months").format(format);
}

const checkModelSet = new Set();
for (let i = 0; i < 6; i++) {
  checkModelSet.add(getFromMonth(-i));
}

const checkModelOptions = Array.from(checkModelSet).map((date) => ({
  label: date,
  value: moment(date).format("YYYYMM"),
}));

// const updateCheckModel = (newValue) => {
//   checkModel.value = newValue;
// };
</script>

<style lang="scss" scoped>
.dialog_header {
  position: relative;
  width: 100%;
  height: 42px;
  margin-bottom: 10px;
  padding-bottom: 4px;
  font-size: 24px;
  font-weight: 600;
  border-bottom: 1px solid $line-300;
}
.q-card_salesDetail_title {
  @include flex_between;
  padding: 0 0 16px 0;
  border-bottom: solid 1px #999 !important;
  p {
    @include fw-7;
  }
  img {
    cursor: pointer;
  }
}

.q-card_salesDetail_section_wrap {
  padding: 0 16px;
  width: 545px;
  .q-card_salesDetail_table_header_wrap {
    padding-bottom: 8px;
    border-bottom: 2px solid $b3;
    p {
      @include fs-5;
      @include fw-7;
      color: $b3;
      margin-bottom: 16px;
    }

    .q-card_salesDetail_select {
      margin-bottom: 16px;
    }
  }
  .q-card_salesDetail_table {
    td:nth-child(1) {
      width: 30%;
      text-align: center;
      border-right: 1px solid rgba(0, 0, 0, 0.1);
    }
    td:nth-child(2) {
      @include fw-7;
      width: 70%;
      text-align: center;
      img {
        cursor: pointer;
      }
    }
  }
}

.q-card_btn_wrap {
  @include flex_end;
  margin: 8px;
  div {
    @include flex_center;
    border-radius: 2.4px;

    gap: 8px;
  }
}
</style>

<template>
  <div>
    <ExpandLayout_mobile_a
      v-model:expanded_model="expanded_model"
      expand_title="마이페이지"
      expand_btn="세금계산서"
      @expanded_btnClick="expanded_btn"
      :expanded_isVisibleDot="isVisibleDot"
    >
      <DialogLayout_mobile
        dialog_title="세금계산서"
        v-model:dialog_model_prop="dialog_MyPage"
      >
        <div class="">
          <MyTaxComp_mobile />
        </div>
      </DialogLayout_mobile>

      <q-table
        class="q-table-custom"
        row-key="날짜"
        :rows="rows"
        flat
        hide-header
        hide-bottom
        virtual-scroll
        :rows-per-page-options="[0]"
        :virtual-scroll-sticky-size-start="0"
      >
        <template v-slot:body="props">
          <q-tr class="table_container" @click="props.expand = !props.expand">
            <div class="table_wrap">
              <div class="table_item table_item_mobile">
                <q-td class="table-td_mobile">{{ userInfo.CUST_NM }}</q-td>
              </div>
              <div class="table_item table_item_mobile">
                <q-td class="table-td_mobile"></q-td>
              </div>
              <div class="table_item table_item_mobile">
                <q-td class="table-td_mobile">대표약사</q-td>
              </div>
              <div class="table_item table_item_mobile">
                <q-td class="table-td_mobile">{{ userInfo.CEO_NM }}</q-td>
              </div>
              <div class="table_item table_item_mobile">
                <q-td class="table-td_mobile">
                  <img
                    :src="
                      props.expand
                        ? 'assets/images/arrw_up.svg'
                        : 'assets/images/arrw_dwn.svg'
                    "
                    alt="icon"
                  />
                </q-td>
              </div>
            </div>
          </q-tr>

          <!-- expand -->
          <q-tr v-show="props.expand" :props="props">
            <div class="expand_table_wrap">
              <div class="expand_table_item table_item_mobile">
                <q-td class="table-td_mobile">{{ data.brchNm }}</q-td>
              </div>
              <div class="expand_table_item table_item_mobile">
                <q-td class="table-td_mobile">지점 연락처</q-td>
              </div>
              <div class="expand_table_item table_item_mobile">
                <q-td class="table-td_mobile">{{ data.brchTelNo }}</q-td>
              </div>
              <div class="expand_table_item table_item_mobile">
                <q-td class="table-td_mobile">영업사원</q-td>
              </div>
              <div class="expand_table_item table_item_mobile">
                <q-td class="table-td_mobile">{{ data.empNm }}</q-td>
              </div>
              <div class="expand_table_item table_item_mobile">
                <q-td class="table-td_mobile">{{ data.empHpNo }}</q-td>
              </div>
              <div class="expand_table_item table_item_mobile">
                <q-td class="table-td_mobile">배송기사</q-td>
              </div>
              <div class="expand_table_item table_item_mobile">
                <q-td class="table-td_mobile">{{ data.driverNm }}</q-td>
              </div>
              <div class="expand_table_item table_item_mobile">
                <q-td class="table-td_mobile">{{ data.driverHpPhone }}</q-td>
              </div>
              <!-- <div
                class="expand_table_item table_item_mobile"
                @click="props.expand = !props.expand"
              >
                <q-td class="table-td_mobile">
                  <div class="">
                    <span>기본정보 닫기</span>
                    <span>
                      <img
                        src="/src/assets/icons/sort-down-solid-close.svg"
                        alt="icon"
                      />
                    </span>
                  </div>
                </q-td>
              </div> -->
            </div>
          </q-tr>
        </template>
      </q-table>
    </ExpandLayout_mobile_a>
  </div>
</template>
<script setup>
//---------------------- Import & Declaration ------------------------//
import { ref, onMounted, computed } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";
import moment from "moment";

import ExpandLayout_mobile_a from "src/layouts/mobile/ExpandLayouts/ExpandLayout_mobile_a.vue";
import DialogLayout_mobile from "src/layouts/mobile/DialogLayouts/DialogLayout_mobile.vue";
import MyTaxComp_mobile from "src/components/mobile/DialogComp/4.myDialogComp/MyTaxComp_mobile.vue";

const userInfo = util.getUserData();
const expanded_model = ref(true);

const dialog_MyPage = ref(false);
const expanded_btn = async () => {
  if (taxBillData.value.yn == "N") {
    try {
      await api.post("/myPage/taxBillCountUp", searchParam.value);

      await fetchData();
    } catch (error) {
      error.response.data;
    }
  }

  dialog_MyPage.value = true;
};

const searchParam = ref({
  custCd: userInfo.CUST_CD,
});

const data = ref({
  brchCd: "",
  brchNm: "",
  brchTelNo: "",
  empHpNo: "",
  empId: "",
  empNm: "",
  driverNm: "",
  driverHpPhone: "",
});

const taxBillData = ref({
  cnt: 0,
  pubDt: "",
  today: "",
  yn: "",
});

const isVisibleDot = computed(() => {
  const cnt = taxBillData.value.cnt;

  if (
    cnt > 0 &&
    taxBillData.value.pubDt !== "" &&
    taxBillData.value.yn !== "Y"
  ) {
    const pubDt = moment(taxBillData.value.pubDt);
    const today = moment(taxBillData.value.today);
    const day = today.diff(pubDt, "days");

    if (day <= 7) {
      return true;
    } else {
      return false;
    }
  }

  return false;
});

//---------------------------- Function ------------------------------//

async function fetchData() {
  const res = await api.get("/myPage/custInfo", { params: searchParam.value });

  data.value.brchCd = res.BRCH_CD;
  data.value.brchNm = res.BRCH_NM;
  data.value.brchTelNo = res.BRCH_TEL_NO;
  data.value.empHpNo = res.EMP_HP_NO;
  data.value.empId = res.EMP_ID;
  data.value.empNm = res.EMP_NM;
  data.value.driverNm = res.DRIVER_NM;
  data.value.driverHpPhone = res.DRIVER_HP_PHONE;

  const taxBillRes = await api.get("/myPage/taxBillCount", {
    params: searchParam.value,
  });

  taxBillData.value.cnt = taxBillRes.CNT;
  taxBillData.value.pubDt = taxBillRes.PUB_DT;
  taxBillData.value.today = taxBillRes.TODAY;
  taxBillData.value.yn = taxBillRes.WEB_READ_YN;
}

onMounted(async () => {
  await fetchData();
});

//-------------------------- Grid Setting ----------------------------//

const rows = ref([
  {
    신규: "(NEW)",
    약국명: "3층파란문약국",
    약사: "홍길동",
    expandDate: [
      {
        지점: "백제약품 (주) 영등포지점",
        지점연락처: "02-862-0211",
        영업사원: "주현근",
        영업사원연락처: "010-0000-0000",
        배송기사: "홍길수",
        배송기사연락처: "010-0000-0000",
      },
    ],
  },
]);
</script>

<style lang="scss" scoped>
.table_container {
  &:hover {
    background-color: transparent;
  }
}

.table_wrap {
  display: grid;
  align-items: center;
  grid-template-columns: repeat(10, 1fr);
  // padding: 4px 8px !important;
  // margin-top: 10px;
  gap: 8px;
  // background-color: #eeeeee;
  border: none;
  td {
    @include fs-5;
  }
  .table_item:nth-child(1) {
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(2) {
    grid-column: span 6;
    td {
      @include flex_start;
    }
  }
  .table_item:nth-child(3) {
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(4) {
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
  .table_item:nth-child(5) {
    grid-column: span 1;
    td {
      @include flex_center;
    }
  }
}

.expand_table_wrap {
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  padding: 12px 15px !important;
  // background-color: $b5;
  td {
    @include fs-5;
    padding: 4px 0 !important;
  }
  .expand_table_item:nth-child(1) {
    grid-column: span 8;
    td {
    }
  }
  .expand_table_item:nth-child(2) {
    grid-column: span 2;
    td {
    }
  }
  .expand_table_item:nth-child(3) {
    grid-column: span 6;
    td {
      // color: #666666;
    }
  }
  .expand_table_item:nth-child(4) {
    grid-column: span 2;
    td {
    }
  }
  .expand_table_item:nth-child(5) {
    grid-column: span 1;
    td {
      // color: #666666;
    }
  }
  .expand_table_item:nth-child(6) {
    grid-column: span 5;
    td {
      // color: #666666;
    }
  }
  .expand_table_item:nth-child(7) {
    grid-column: span 2;
    td {
    }
  }
  .expand_table_item:nth-child(8) {
    grid-column: span 1;
    td {
      // color: #666666;
    }
  }
  .expand_table_item:nth-child(9) {
    grid-column: span 5;
    td {
      // color: #666666;
    }
  }
  .expand_table_item:nth-child(10) {
    grid-column: span 8;
    padding: 8px 0 4px !important;
    td {
      @include flex_center;
      div {
        @include flex_center;
        gap: 8px;
        span {
          @include fs-5;
        }
      }
    }
  }
}
</style>

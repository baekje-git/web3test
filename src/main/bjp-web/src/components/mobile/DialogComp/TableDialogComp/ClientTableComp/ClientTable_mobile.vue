<template>
  <div class="">
    <q-table
      class=""
      row-key="약"
      :rows="rows"
      :filter="search_model"
      dense
      flat
      hide-header
      hide-bottom
      virtual-scroll
      :rows-per-page-options="[0]"
      :virtual-scroll-sticky-size-start="0"
    >
      <template v-slot:top>
        <div class="table_Top_wrap">
          <div class="flex_between" style="padding: 0 0 16px 0; gap: 8px">
            <!-- SELECT 박스 비율 ↑ -->
            <div class="slct-wrap" style="width: 35%">
              <q-select
                class="select_default"
                v-model="searchParam.custNmCdGb"
                :options="custNmCdGb"
                borderless
                dense
                emit-value
                map-options
                behavior="menu"
                @update:model-value="focusInput"
              />
            </div>

            <!-- INPUT 줄이기 -->
            <InputComp_mobile
              ref="keywordInputRef"
              placeholder="거래처를 입력해주세요."
              style="width: 45%"
              v-model:input_model_props="searchParam.keyword"
              @update:model-value="input_model_props = $event"
              @enter="enterpushtest"
            />

            <!-- 검색 버튼은 그대로 -->
            <BtnComp_mobile
              buttonText="검색"
              btnStyle="active"
              style="width: 90px"
              class="btn_srch btn-full"
              @click="fetchData()"
            />
          </div>
          <div class="table_totalVal">
            <span class="table_total_Val">{{ rowval() }}</span
            ><span class="table_total_Text">개가 검색되었습니다.</span>
          </div>
        </div>
      </template>

      <template v-slot:body="props">
        <q-tr class="table_container" @click="onRowClick(props.row.CUST_CD)">
          <div class="table_wrap">
            <div class="">
              <q-td class="table-td_mobile">
                <div>{{ props.row.CUST_CD }}</div>
                <div>{{ props.row.CUST_NM }}</div>
              </q-td>
            </div>
            <div class="">
              <ul>
                <li>
                  <q-td>
                    <img src="assets/icons/location.svg" />
                    {{ props.row.DLV_COURSE_NM }}
                  </q-td>
                </li>
                <li>
                  <q-td>
                    <img src="assets/icons/build.svg" />
                    {{ props.row.TEL_NO }}
                  </q-td>
                </li>
                <li>
                  <q-td>
                    <img src="assets/icons/call.svg" />
                    {{ props.row.HP_NO }}
                  </q-td>
                </li>
                <li>
                  <q-td>
                    <img src="assets/icons/user.svg" /> 대표자 :
                    {{ props.row.CEO_NM }}
                  </q-td>
                </li>
              </ul>
            </div>
          </div>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { api } from "boot/axios";
import { useRouter } from "vue-router";
import * as util from "src/support/util";
import BtnComp_mobile from "src/components/mobile/__commonComp/btnComp/BtnComp_mobile.vue";
import InputComp_mobile from "src/components/mobile/__commonComp/inputComp/InputComp_mobile.vue";
const keywordInputRef = ref(null);

function focusInput() {
  // 다음 tick에 실행되도록 타이밍 보장
  setTimeout(() => {
    keywordInputRef.value?.$el?.querySelector("input")?.focus();
  }, 0);
}
const router = useRouter();
const userInfo = util.getUserData();

const searchParam = ref({
  custNmCdGb: "1",
  keyword: "",
  custCd: "",
  loginBrchCd: userInfo.LOGIN_BRCH_CD,
  loginDeptCd: userInfo.LOGIN_DEPT_CD,
  userId: userInfo.USER_ID,
  loginId: userInfo.LOGIN_ID,
  loginBrchCd: userInfo.LOGIN_BRCH_CD,
  loginDeptCd: userInfo.LOGIN_DEPT_CD,
  userNm: userInfo.USER_NM,
  roleCd: userInfo.ROLE_CD,
  userGbCd: userInfo.USER_GB_CD,
  empNo: userInfo.EMP_NO,
  brchCd: userInfo.BRCH_CD,
  dlvBrchCd: userInfo.DLV_BRCH_CD,
  loginBrchCd: userInfo.LOGIN_BRCH_CD,
  brchNm: userInfo.BRCH_NM,
  brchSnm: userInfo.BRCH_SNM,
});
const custNmCdGb = ref([
  { value: "1", label: "거래처명" },
  { value: "2", label: "거래처코드" },
]);
const rows = ref([]);

const props = defineProps({
  isOpen: { type: Boolean, default: false },
});

async function fetchData() {
  const res = await api.get("/main/getCustList", { params: searchParam.value });
  rows.value = res;
}

const rowval = () => {
  return rows.value.length;
};

async function onClickSearch() {
  await fetchData();
}

async function onRowClick(CUST_CD) {
  searchParam.value.custCd = CUST_CD;

  const res = await api.get("/main/editCustInfo", {
    params: searchParam.value,
  });

  util.setUserData(res);
  router.go(0);
}

//엔터키
const enterpushtest = async () => {
  await fetchData();
};

onMounted(async () => {
  await fetchData();
});
</script>

<style lang="scss" scoped>
.table_wrap {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  padding: 4px 0 !important;
  div {
    td {
      max-width: 160px;
      // overflow: hidden;
      // text-overflow: ellipsis;
      white-space: normal;
    }
  }
  div:nth-child(1) {
    @include flex_start;
    td {
      @include fw-7;
      // font-size: 16px !important;
    }
  }
  div:nth-child(2) {
    td {
      // @include fs-6;
      // font-size: 12px !important;
      // color: #666666;
    }
  }
}
</style>

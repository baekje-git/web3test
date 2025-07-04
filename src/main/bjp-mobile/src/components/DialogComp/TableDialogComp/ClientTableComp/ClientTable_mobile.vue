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
          <div class="flex_between" style="padding: 0 0 16px 0; gap: 24px;">
            <InputComp_mobile
              placeholder="거래처를 입력해주세요."
              style="width: 240px;"
              v-model:input_model_props="searchParam.keyword"
              @update:model-value="input_model_props = $event"            />

            <BtnComp_mobile
              buttonText="검색"
              btnStyle="active"
              style="width: 64px;"
              @click="fetchData()"
            />

          </div>
          <div class="table_totalVal">
            <span class="table_total_Val">{{ rowval() }}</span><span class="table_total_Text">개가 검색되었습니다.</span>
          </div>
        </div>
      </template>


      <template v-slot:body="props">
        <q-tr class="table_container"  @click="onRowClick(props.row.CUST_CD)">
          <div class="table_wrap">
            <div class="">
              <q-td>
                {{ props.row.CUST_NM }}
              </q-td>
            </div>
            <div class="">
              <ul>
                <li>
                  <q-td>
                    {{ props.row.BRCH_SNM }}
                  </q-td>
                </li>
                <li>
                  <q-td>
                    {{ props.row.TEL_NO }}
                  </q-td>
                </li>
                <li>
                  <q-td>
                    {{ props.row.HP_NO }}
                  </q-td>
                </li>
                <li>
                  <q-td>
                    대표자:{{ props.row.CEO_NM }}
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
import { ref } from 'vue';
import { api } from "boot/axios";
import { useRouter } from "vue-router";
import * as util from "src/support/util";
import BtnComp_mobile from 'src/components/__commonComp/btnComp/BtnComp_mobile.vue';
import InputComp_mobile from 'src/components/__commonComp/inputComp/InputComp_mobile.vue';

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

const rows = ref([]);

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











// const rows = ref([
//   {
//     약: '(NEW)3층파란문약국 TEST ellipsis',
//     배송: '배송권역&배송코스 TEST ellipsis',
//     전화번호: '02-1234-5678',
//     휴대폰: '010-0000-0000',
//     대표자: '홍길동',
//   },
//   {
//     약: '(NEW)3층빨강문약국',
//     배송: '배송권역&배송코스',
//     전화번호: '02-1234-5678',
//     휴대폰: '010-0000-0000',
//     대표자: '홍길동',
//   },
//   {
//     약: 'Search TEST',
//     배송: '배송권역&배송코스',
//     전화번호: '02-1234-5678',
//     휴대폰: '010-0000-0000',
//     대표자: '홍길동',
//   },
//   {
//     약: '(NEW)검색테스트',
//     배송: '배송권역&배송코스 테스트검색',
//     전화번호: '02-1234-5678',
//     휴대폰: '010-0000-0000',
//     대표자: '홍길동',
//   },
// ]);
</script>

<style lang="scss" scoped>


.table_wrap {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  padding: 4px 0 !important;
  div {
    td {
      max-width: 160px;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  div:nth-child(1) {
    @include flex_start;
    td {
      @include fw-7;
    }
  }
  div:nth-child(2) {
    td {
      @include fs-6;
      color: #666666;
    }
  }
}

</style>

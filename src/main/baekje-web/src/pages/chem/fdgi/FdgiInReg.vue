<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>입고 관리</h2>
    </div>
    <q-page-container>
      <div class="tit-sub-wrap">
        <h3>입고 등록</h3>
      </div>
      <div class="reg-wrap">
        <table>
          <colgroup>
            <col width="10%" />
            <col width="" />
          </colgroup>
          <tbody>
            <!--<tr>
                  <th>재료코드</th>
                  <td>
                    <q-banner dense class="w100p">
                      <div class="row items-start">
                        <q-input
                          filled
                          v-model="itemCd"
                          @blur="(evt) => searchItemNm(evt.target.value)"
                          maxlength="20"
                        />
                      </div>
                    </q-banner>
                  </td>
                </tr>
                <tr>
                  <th>재료명</th>
                  <td>
                    <q-banner dense class="w100p">
                      <div class="row items-start">
                        <q-input filled v-model="itemNm" disable />
                      </div>
                    </q-banner>
                  </td>
                </tr>-->
            <tr>
              <th>재료</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <CmmnCode
                      code="ITEM"
                      name="code"
                      v-model="itemCd"
                      :all="true"
                      @loadEnd="fnLoadEnd"
                      class="w100p"
                      allTitle="선택"
                    />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>Lot NO</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input filled v-model="lotNo" maxlength="20" />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>수량</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input filled v-model="qty" maxlength="20" />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>시험번호</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input filled v-model="testNo" maxlength="40" />
                  </div>
                </q-banner>
              </td>
            </tr>
            <tr>
              <th>입고일자</th>
              <td>
                <q-banner dense class="w100p">
                  <div class="row items-start">
                    <q-input filled type="date" v-model="receiptDt" />
                  </div>
                </q-banner>
              </td>
            </tr>
          </tbody>
        </table>
        &nbsp;
        <div class="btn-wrap btn-bottom">
          <q-btn @click="this.$router.go(-1)" class="btn-point" label="목록" />
          &nbsp;
          <q-btn @click="registData" class="btn-point" label="저장" />
        </div>
      </div>
    </q-page-container>
  </section>
</template>
<script>
import { defineComponent, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { api } from "boot/axios";
import * as util from "src/support/util";
import CmmnCode from "components/CmmnCode.vue";

export default defineComponent({
  name: "MainLayout",

  components: { CmmnCode },

  methods: {
    searchItemNm(val) {
      if (!util.isEmpty(val)) {
        api
          .post("/fdgi/mst/fdgiMstNmByCdData", {
            ITEM_CD: val,
          })
          .then((response) => {
            if (util.isEmpty(response.data.ITEM_NM)) {
              this.itemNm = "등록되지 않은 재료코드 입니다.";
            } else {
              this.itemNm = response.data.ITEM_NM;
            }
          });
      }
    },
  },

  setup() {
    const router = useRouter();

    const itemCd = ref("");
    //const itemNm = ref("");
    const lotNo = ref("");
    const qty = ref("");
    const testNo = ref("");
    const receiptDt = ref("");

    function fnLoadEnd(option) {
      itemCd.value = option;
    }

    async function registData() {
      try {
        if (util.isEmpty(itemCd.value.value)) {
          util.errorNotify("재료를 선택하세요.");
          return;
        }
        /*if (itemNm.value === "등록되지 않은 재료코드 입니다.") {
          util.errorNotify("유효한 재료코드를 입력하세요.");
          return;
        }*/
        if (util.isEmpty(lotNo.value)) {
          util.errorNotify("Lot NO를 입력하세요.");
          return;
        }
        if (util.isEmpty(qty.value)) {
          util.errorNotify("수량을 입력하세요.");
          return;
        }
        if (util.isEmpty(testNo.value)) {
          util.errorNotify("시험번호를 입력하세요.");
          return;
        }
        if (util.isEmpty(receiptDt.value)) {
          util.errorNotify("입고일자를 입력하세요.");
          return;
        }
        await api.post("/fdgi/in/insertFdgiIn", {
          ITEM_CD: itemCd.value.value,
          LOT_NO: lotNo.value,
          QTY: qty.value,
          TEST_NO: testNo.value,
          RECEIPT_DT: receiptDt.value,
          userData: util.getUserData(),
        });
        util.showNotify("정상처리 되었습니다.");
        //list 이동
        router.push({ path: "/fdgi-in" });
      } catch (error) {
        util.errorNotify("오류가 발생하였습니다.");
      }
    }

    return {
      itemCd,
      //itemNm,
      lotNo,
      qty,
      testNo,
      receiptDt,
      registData,
      fnLoadEnd,
    };
  },

  mounted() {},
});
</script>
<style scoped>
/* 여기서 선언한 CSS가 우선 적용
   그 다음은 app.scss가 우선 적용 (ctrl+p 눌러서 파일 검색)
*/
body,
html {
  font-family: "Pretendard" !important;
  letter-spacing: 0;
  font-size: 14px;
}
</style>

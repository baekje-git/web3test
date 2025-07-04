<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>입고 관리</h2>
      <div class="btn-wrap">
        <div class="btn-toast">
          <q-btn
            @click="searchList"
            class="btn-point ico-search"
            label="조회"
          />
          &nbsp;
          <q-btn @click="onRegClick" class="btn-point" label="등록" />
        </div>
      </div>
    </div>
    <q-page-container class="first-container">
      <q-banner dense :class="$q.dark.isActive ? '' : ''" class="search-wrap">
        <div class="q-gutter-md row items-start">
          <dev>
            <span>입고일자</span>
            <q-input filled type="date" v-model="startDate" />~
            <q-input filled type="date" v-model="endDate" />
          </dev>
          <dev>
            <span>재료</span>
            <CmmnCode
              code="ITEM"
              name="code"
              v-model="itemCd"
              :all="true"
              @loadEnd="fnLoadEnd"
              class="w200"
            />
          </dev>
          <dev>
            <span>Lot NO</span>
            <q-input filled v-model="lotNo" label="Lot NO" />
          </dev>
          <dev>
            <span>시험번호</span>
            <q-input filled v-model="testNo" label="시험번호" />
          </dev>
        </div>
      </q-banner>
    </q-page-container>
    <div class="main-notice">
      <q-table
        flat
        :bordered="false"
        :rows="rows"
        :columns="columns"
        :visible-columns="visibleColumns"
        :loading="loading"
        row-key="SEQ"
        hide-pagination
        :rows-per-page-options="[0]"
        class="hover-none"
      >
      </q-table>
    </div>
    <q-dialog v-model="dialog">
      <q-card style="width: 500px">
        <section class="content">
          <q-page-container>
            <div class="tit-sub-wrap">
              <h3>폐기 등록</h3>
            </div>
            <div class="reg-wrap">
              <table>
                <colgroup>
                  <col width="20%" />
                  <col width="" />
                </colgroup>
                <tbody>
                  <tr>
                    <th>재료</th>
                    <td>
                      <q-banner dense class="w100p">
                        <div class="row items-start">
                          <CmmnCode
                            code="ITEM"
                            name="code"
                            v-model="itemCdDial"
                            :all="true"
                            @loadEnd="fnDialLoadEnd"
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
                          <q-input filled v-model="lotNoDial" maxlength="20" />
                        </div>
                      </q-banner>
                    </td>
                  </tr>
                  <tr>
                    <th>수량</th>
                    <td>
                      <q-banner dense class="w100p">
                        <div class="row items-start">
                          <q-input filled v-model="qtyDial" maxlength="20" />
                        </div>
                      </q-banner>
                    </td>
                  </tr>
                  <tr>
                    <th>시험번호</th>
                    <td>
                      <q-banner dense class="w100p">
                        <div class="row items-start">
                          <q-input filled v-model="testNoDial" maxlength="40" />
                        </div>
                      </q-banner>
                    </td>
                  </tr>
                  <tr>
                    <th>입고일자</th>
                    <td>
                      <q-banner dense class="w100p">
                        <div class="row items-start">
                          <q-input filled type="date" v-model="receiptDtDial" />
                        </div>
                      </q-banner>
                    </td>
                  </tr>
                </tbody>
              </table>
              &nbsp;
              <div class="btn-wrap btn-bottom">
                <q-btn class="btn-point" label="취소" v-close-popup />
                &nbsp;
                <q-btn @click="registData" class="btn-point" label="저장" />
              </div>
            </div>
          </q-page-container>
        </section>
      </q-card>
    </q-dialog>
  </section>
</template>
<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import { api } from "boot/axios";
import { useQuasar } from "quasar";
import * as util from "src/support/util";
import CmmnCode from "components/CmmnCode.vue";

export default defineComponent({
  name: "MainLayout",

  components: { CmmnCode },

  /*methods: {
    onRegClick() {
      this.$router.push({
        path: "/fdgi-in/reg",
      });
    },
  },*/

  setup() {
    const popup = useQuasar();
    const $R = useQuasar();
    const loading = ref(true);
    const dialog = ref(false);
    /* 테이블 */

    const rows = ref([]);
    const startDate = ref("");
    const endDate = ref("");
    const itemCd = ref("");
    const lotNo = ref("");
    const testNo = ref("");
    const itemCdDial = ref("");
    const lotNoDial = ref("");
    const qtyDial = ref("");
    const testNoDial = ref("");
    const receiptDtDial = ref("");

    function fnLoadEnd(option) {
      itemCd.value = option;
    }

    function fnDialLoadEnd(option) {
      itemCdDial.value = option;
    }

    const searchList = (page = 1) => {
      loading.value = true;
      return api
        .post("/fdgi/in/fdgiInList", {
          START_DATE: startDate.value,
          END_DATE: endDate.value,
          ITEM_CD: itemCd.value.value,
          LOT_NO: lotNo.value,
          TEST_NO: testNo.value,
        })
        .then((response) => {
          rows.value = response.data;
        })
        .finally(() => {
          loading.value = false;
        });
    };

    function onRegClick() {
      dialog.value = true;
    }

    async function registData() {
      try {
        if (util.isEmpty(itemCdDial.value.value)) {
          util.errorNotify("재료를 선택하세요.");
          return;
        }
        if (util.isEmpty(lotNoDial.value)) {
          util.errorNotify("Lot NO를 입력하세요.");
          return;
        }
        if (util.isEmpty(qtyDial.value)) {
          util.errorNotify("수량을 입력하세요.");
          return;
        }
        if (util.isEmpty(testNoDial.value)) {
          util.errorNotify("시험번호를 입력하세요.");
          return;
        }
        if (util.isEmpty(receiptDtDial.value)) {
          util.errorNotify("입고일자를 입력하세요.");
          return;
        }
        await api.post("/fdgi/in/insertFdgiIn", {
          ITEM_CD: itemCdDial.value.value,
          LOT_NO: lotNoDial.value,
          QTY: qtyDial.value,
          TEST_NO: testNoDial.value,
          RECEIPT_DT: receiptDtDial.value,
          userData: util.getUserData(),
        });
        util.showNotify("정상처리 되었습니다.");
        dialog.value = false;
        searchList();
      } catch (error) {
        util.errorNotify("오류가 발생하였습니다.");
      }
    }

    onMounted(() => {
      searchList();
    });

    return {
      val: ref(true),
      /* ///////그리드 스크립트 Start//////// */
      visibleColumns,
      columns,
      loading,
      rows,
      /* ///////그리드 스크립트 End//////// */
      searchList,
      onRegClick,
      dialog,
      startDate,
      endDate,
      itemCd,
      lotNo,
      testNo,
      itemCdDial,
      lotNoDial,
      qtyDial,
      testNoDial,
      receiptDtDial,
      registData,
      fnLoadEnd,
      fnDialLoadEnd,
    };
  },
});
/* ///////그리드 스크립트 Start//////// */
const columns = [
  { name: "SEQ", label: "SEQ", field: "SEQ" },
  {
    name: "RECEIPT_DT",
    label: "입고일자",
    field: "RECEIPT_DT",
    sortable: true,
  },
  {
    name: "ITEM_CD",
    required: true,
    label: "재료코드",
    field: (row) => row.ITEM_CD,
    sortable: true,
  },
  { name: "ITEM_NM", label: "재료명", field: "ITEM_NM" },
  { name: "LOT_NO", label: "Lot NO", field: "LOT_NO" },
  { name: "QTY", label: "수량", field: "QTY" },
  { name: "TEST_NO", label: "시험번호", field: "TEST_NO" },
];
const visibleColumns = [
  "ITEM_CD",
  "ITEM_NM",
  "LOT_NO",
  "QTY",
  "TEST_NO",
  "RECEIPT_DT",
];
/* ///////그리드 스크립트 End //////// */
</script>

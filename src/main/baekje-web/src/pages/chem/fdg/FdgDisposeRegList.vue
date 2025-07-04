<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>폐기 등록</h2>
      <div class="btn-wrap">
        <div class="btn-toast">
          <q-btn @click="this.$router.go(-1)" class="btn-point" label="목록" />
          &nbsp;
          <q-btn
            @click="searchList"
            class="btn-point ico-search"
            label="조회"
          />
        </div>
      </div>
    </div>
    <q-page-container class="first-container">
      <q-banner dense :class="$q.dark.isActive ? '' : ''" class="search-wrap">
        <div class="q-gutter-md row items-start">
          <dev>
            <span>제조일자</span>
            <q-input filled type="date" v-model="startProdDate" />~
            <q-input filled type="date" v-model="endProdDate" />
          </dev>
          <dev>
            <span>제조번호</span>
            <q-input filled v-model="prodNo" label="제조번호" />
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
        :loading="loading"
        row-key="PROD_NO"
        hide-pagination
        :rows-per-page-options="[0]"
        @row-click="onRowClick"
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
                    <th>제조일자</th>
                    <td>
                      <q-banner dense class="w100p">
                        <div class="row items-start">
                          <q-input filled v-model="prodDtDial" disable />
                        </div>
                      </q-banner>
                    </td>
                  </tr>
                  <tr>
                    <th>제조번호</th>
                    <td>
                      <q-banner dense class="w100p">
                        <div class="row items-start">
                          <q-input filled v-model="prodNoDial" disable />
                        </div>
                      </q-banner>
                    </td>
                  </tr>
                  <tr>
                    <th>제조지시일자</th>
                    <td>
                      <q-banner dense class="w100p">
                        <div class="row items-start">
                          <q-input filled v-model="mandateDtDial" disable />
                        </div>
                      </q-banner>
                    </td>
                  </tr>
                  <tr>
                    <th>폐기사유</th>
                    <td>
                      <q-banner dense class="w100p">
                        <div class="row items-start">
                          <q-input
                            filled
                            v-model="delReasonDial"
                            maxlength="100"
                          />
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

export default defineComponent({
  name: "MainLayout",

  components: {},

  setup() {
    const popup = useQuasar();
    const $R = useQuasar();
    const loading = ref(true);
    const dialog = ref(false);
    /* 테이블 */

    const rows = ref([]);
    const startProdDate = ref("");
    const endProdDate = ref("");
    const prodNo = ref("");
    const prodDtDial = ref("");
    const prodNoDial = ref("");
    const mandateDtDial = ref("");
    const delReasonDial = ref("");

    const searchList = (page = 1) => {
      loading.value = true;
      return api
        .post("/fdgp/bpr/fdgpBprDisposeList", {
          START_PROD_DT: startProdDate.value,
          END_PROD_DT: endProdDate.value,
          PROD_NO: prodNo.value,
          DEL_NY: "N",
        })
        .then((response) => {
          rows.value = response.data;
        })
        .finally(() => {
          loading.value = false;
        });
    };

    function onRowClick(evt, row) {
      prodDtDial.value = row.PROD_DT;
      prodNoDial.value = row.PROD_NO;
      mandateDtDial.value = row.MANDATE_DT;
      dialog.value = true;
    }

    async function registData() {
      try {
        if (util.isEmpty(delReasonDial.value)) {
          util.errorNotify("폐기사유를 입력하세요.");
          return;
        }
        await api.post("/fdgp/bpr/updateFdgpBprDispose", {
          PROD_NO: prodNoDial.value,
          DEL_REASON: delReasonDial.value,
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
      columns,
      loading,
      rows,
      /* ///////그리드 스크립트 End//////// */
      searchList,
      onRowClick,
      dialog,
      registData,
      startProdDate,
      endProdDate,
      prodNo,
      prodDtDial,
      prodNoDial,
      mandateDtDial,
      delReasonDial,
    };
  },
});
/* ///////그리드 스크립트 Start//////// */
const columns = [
  {
    name: "PROD_DT",
    label: "제조일자",
    field: "PROD_DT",
    sortable: true,
  },
  {
    name: "PROD_NO",
    required: true,
    label: "제조번호",
    field: (row) => row.PROD_NO,
    sortable: true,
  },
  { name: "MANDATE_DT", label: "제조지시일자", field: "MANDATE_DT" },
  { name: "FINAL_PROC", label: "최종 진행 문서", field: "FINAL_PROC" },
];
/* ///////그리드 스크립트 End //////// */
</script>
<style scoped></style>

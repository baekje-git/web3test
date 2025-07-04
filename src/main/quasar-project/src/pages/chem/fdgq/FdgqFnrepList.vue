<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>완제품 시험성적서</h2>
      <div class="btn-wrap">
        <div class="btn-toast">
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
          <dev>
            <span>담당자</span>
            <q-input filled v-model="chargeNm" label="담당자" />
          </dev>
          <dev>
            <span>확인자</span>
            <q-input filled v-model="confirmNm" label="확인자" />
          </dev>
          <dev>
            <span>승인자</span>
            <q-input filled v-model="mandateNm" label="승인자" />
          </dev>
          <dev>
            <span>문의등록여부</span>
            <CmmnCode
              code="QNA_YN"
              name="code"
              v-model="qnaYn"
              :all="true"
              @loadEnd="fnLoadEnd1"
              class="w80"
            />
          </dev>
          <dev>
            <span>폐기여부</span>
            <CmmnCode
              code="DEL_NY"
              name="code"
              v-model="delNy"
              :all="true"
              @loadEnd="fnLoadEnd2"
              class="w80"
            />
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
        <template v-slot:body-cell-actions="props">
          <q-td :props="props">
            <q-btn
              @click.stop="openQnaList(props.row)"
              class="btn-point"
              label="문의"
            />
          </q-td>
        </template>
      </q-table>
    </div>
  </section>
</template>
<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import { api } from "boot/axios";
import { useQuasar } from "quasar";
import CmmnCode from "components/CmmnCode.vue";

export default defineComponent({
  name: "MainLayout",

  components: { CmmnCode },

  methods: {
    onRowClick(evt, row) {
      console.log("clicked on", row.PROD_NO);
      this.$router.push({
        path: "/fdgq-fnrep/detail",
        query: { prodNo: row.PROD_NO },
      });
    },
    openQnaList(item) {
      this.$router.push({
        path: "/fdg-qna/qusList",
        query: {
          prodNo: item.PROD_NO,
          process: this.$route.path,
        },
      });
    },
  },

  setup() {
    const popup = useQuasar();
    const $R = useQuasar();
    const loading = ref(true);
    /* 테이블 */

    const rows = ref([]);
    const startProdDate = ref("");
    const endProdDate = ref("");
    const prodNo = ref("");
    const qnaYn = ref("");
    const chargeNm = ref("");
    const confirmNm = ref("");
    const mandateNm = ref("");
    const delNy = ref("");

    function fnLoadEnd1(option) {
      qnaYn.value = option;
    }

    function fnLoadEnd2(option) {
      delNy.value = option;
    }
    const searchList = (page = 1) => {
      loading.value = true;
      return api
        .post("/fdgq/fnrep/fdgqFnrepList", {
          START_PROD_DT: startProdDate.value,
          END_PROD_DT: endProdDate.value,
          PROD_NO: prodNo.value,
          QNA_YN: qnaYn.value.value,
          DEL_NY: delNy.value.value,
          CHARGE_NM: chargeNm.value,
          CONFIRM_NM: confirmNm.value,
          MANDATE_NM: mandateNm.value,
        })
        .then((response) => {
          rows.value = response.data;
        })
        .finally(() => {
          loading.value = false;
        });
    };

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
      startProdDate,
      endProdDate,
      prodNo,
      qnaYn,
      chargeNm,
      confirmNm,
      mandateNm,
      delNy,
      fnLoadEnd1,
      fnLoadEnd2,
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
  { name: "TEST_REQ_NO", label: "시험의뢰번호", field: "TEST_REQ_NO" },
  { name: "TEST_GRD_NO", label: "시험성적번호", field: "TEST_GRD_NO" },
  { name: "CHARGE_NM", label: "담당자", field: "CHARGE_NM" },
  { name: "CHARGE_DT", label: "담당자 확인일", field: "CHARGE_DT" },
  { name: "CONFIRM_NM", label: "확인자", field: "CONFIRM_NM" },
  { name: "CONFIRM_DT", label: "확인자 확인일", field: "CONFIRM_DT" },
  { name: "MANDATE_NM", label: "승인자", field: "MANDATE_NM" },
  { name: "MANDATE_DT", label: "승인자 확인일", field: "MANDATE_DT" },
  { name: "actions", label: "문의", field: "actions" },
  { name: "QNA_YN", label: "문의등록 여부", field: "QNA_YN" },
  { name: "DEL_NY", label: "폐기 여부", field: "DEL_NY" },
];
/* ///////그리드 스크립트 End //////// */
</script>

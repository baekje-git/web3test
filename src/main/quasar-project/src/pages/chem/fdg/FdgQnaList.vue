<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>질의 게시판</h2>
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
            <span>작성일자</span>
            <q-input filled type="date" v-model="startDate" />~
            <q-input filled type="date" v-model="endDate" />
          </dev>
          <dev>
            <span>문서</span>
            <CmmnCode
              code="PROCESS"
              name="code"
              v-model="process"
              :all="true"
              @loadEnd="fnLoadEnd"
              class="w240"
            />
          </dev>
          <dev>
            <span>제조번호</span>
            <q-input filled v-model="prodNo" label="제조번호" />
          </dev>
          <dev>
            <span>질의자</span>
            <q-input filled v-model="inquirerNm" label="질의자" />
          </dev>
          <dev>
            <span>답변자</span>
            <q-input filled v-model="responserNm" label="답변자" />
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
        @row-click="onRowClick"
      >
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
      this.$router.push({
        path: "fdg-qna/ansUpd",
        query: { seq: row.SEQ },
      });
    },
  },

  setup() {
    const popup = useQuasar();
    const $R = useQuasar();
    const loading = ref(true);
    /* 테이블 */

    const rows = ref([]);
    const startDate = ref("");
    const endDate = ref("");
    const process = ref("");
    const prodNo = ref("");
    const inquirerNm = ref("");
    const responserNm = ref("");

    function fnLoadEnd(option) {
      process.value = option;
    }

    const searchList = (page = 1) => {
      loading.value = true;
      return api
        .post("/fdg/qna/fdgQnaList", {
          START_DATE: startDate.value,
          END_DATE: endDate.value,
          PROCESS: process.value.value ? "/" + process.value.value : "",
          PROD_NO: prodNo.value,
          INQUIRER_NM: inquirerNm.value,
          RESPONSER_NM: responserNm.value,
        })
        .then((response) => {
          console.log(response);
          rows.value = response.data;
        })
        .finally(() => {
          loading.value = false;
        });
    };

    onMounted(() => {
      // get initial data from server (1st page)
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
      startDate,
      endDate,
      process,
      prodNo,
      inquirerNm,
      responserNm,
      fnLoadEnd,
    };
  },
});
/* ///////그리드 스크립트 Start//////// */
const columns = [
  { name: "SEQ", label: "SEQ", field: "SEQ" },
  {
    name: "PROCESS_NM",
    label: "문서",
    field: "PROCESS_NM",
    sortable: true,
  },
  {
    name: "PROD_NO",
    required: true,
    label: "제조번호",
    field: (row) => row.PROD_NO,
    sortable: true,
  },
  { name: "INQUIRE_TITLE", label: "제목", field: "INQUIRE_TITLE" },
  { name: "INQUIRER_NM", label: "질의자", field: "INQUIRER_NM" },
  { name: "INQUIRE_DTM", label: "작성일시", field: "INQUIRE_DTM" },
  { name: "RESPONSER_NM", label: "답변자", field: "RESPONSER_NM" },
  { name: "RESPONSE_DTM", label: "답변일시", field: "RESPONSE_DTM" },
];
const visibleColumns = [
  "PROCESS_NM",
  "PROD_NO",
  "INQUIRE_TITLE",
  "INQUIRER_NM",
  "INQUIRE_DTM",
  "RESPONSER_NM",
  "RESPONSE_DTM",
];
/* ///////그리드 스크립트 End //////// */
</script>

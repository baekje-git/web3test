<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>문의 목록</h2>
      <div class="btn-wrap">
        <div class="btn-toast">
          <q-btn @click="this.$router.go(-1)" class="btn-point" label="목록" />
          &nbsp;
          <q-btn @click="onRegClick" class="btn-point" label="문의" />
        </div>
      </div>
    </div>
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
        <template v-slot:body-cell-actions="props">
          <q-td :props="props">
            <q-btn
              @click.stop="openQnaAnsDtl(props.row)"
              class="btn-point ico-search"
              label="답변"
              v-if="props.row.RESPONSER_NM"
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
import { useRoute, useRouter } from "vue-router";

export default defineComponent({
  name: "MainLayout",

  components: {},

  methods: {
    onRowClick(evt, row) {
      this.$router.push({
        path: "/fdg-qna/qusDtl",
        query: {
          seq: row.SEQ,
          process: this.$route.query.process,
        },
      });
    },
    onRegClick() {
      this.$router.push({
        path: "/fdg-qna/qusReg",
        query: {
          prodNo: this.$route.query.prodNo,
          process: this.$route.query.process,
        },
      });
    },
    openQnaAnsDtl(item) {
      this.$router.push({
        path: "/fdg-qna/ansDtl",
        query: { seq: item.SEQ },
      });
    },
  },

  setup() {
    const route = useRoute();
    const loading = ref(true);
    /* 테이블 */

    const rows = ref([]);

    const searchList = (process, prodNo) => {
      loading.value = true;
      return api
        .post("/fdg/qna/fdgQnaList", {
          PROCESS: process,
          PROD_NO: prodNo,
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
      searchList(route.query.process, route.query.prodNo);
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
    };
  },

  mounted() {
    //searchList(this.$route.query.process, this.$route.query.prodNo);
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
  { name: "actions", label: "답변", field: "actions" },
  { name: "RESPONSER_NM", label: "RESPONSER_NM", field: "RESPONSER_NM" },
];
const visibleColumns = [
  "PROCESS_NM",
  "PROD_NO",
  "INQUIRE_TITLE",
  "INQUIRER_NM",
  "INQUIRE_DTM",
  "actions",
];
/* ///////그리드 스크립트 End //////// */
</script>
<style scoped></style>

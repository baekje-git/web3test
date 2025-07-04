<template>
  <section class="content">
    <q-page-container class="first-container">
      <div class="dashboard">
        <q-card class="month" flat>
          <q-card-section>
            <!--<div>월간 생산 실적</div>
            <div>1425건</div>
            <div>8월 생산 실적</div>-->
            <ul>
              <li>월간 생산 실적</li>
              <li>
                <span>{{ month }}월<br />생산 실적</span>
                <strong>{{ monthlyData }}<span>건</span></strong>
              </li>
            </ul>
          </q-card-section>
        </q-card>
        <q-card class="week" flat>
          <q-card-section>
            <ul>
              <li>주간 생산 실적</li>
              <li>
                <span>{{ week }}주<br />생산 실적</span>
                <strong>{{ weeklyData }}<span>건</span></strong>
              </li>
            </ul>
          </q-card-section>
        </q-card>
      </div>
    </q-page-container>
    <q-page-container>
      <!-- main-notice S -->
      <div class="main-notice">
        <q-table
          title="미확인 문서"
          flat
          :bordered="false"
          :rows="rows"
          :columns="columns"
          :visible-columns="visibleColumns"
          :loading="loading"
          row-key="name"
          v-model:pagination="pagination"
          binary-state-sort
          hide-pagination
          @request="onRequest"
          ref="tableref"
          @row-click="onRowClick"
        >
        </q-table>

        <div class="q-pa-lg flex flex-center">
          <q-pagination
            v-model="pagination.page"
            :max="pagesNumber"
            :max-pages="10"
            direction-links
            boundary-links
            @click="searchList"
          />
        </div>
      </div>
    </q-page-container>
  </section>
</template>

<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

export default defineComponent({
  name: "IndexPage",

  methods: {
    onRowClick(evt, row) {
      this.$router.push({
        path: "/" + row.PROCESS + "/detail",
        query: {
          prodNo: row.PROD_NO,
        },
      });
    },
  },

  setup() {
    /* 테이블 */
    const tableref = ref();
    const loading = ref(true);
    const rows = ref([]);
    /* 페이지 */
    const pagination = ref({
      sortBy: "PROD_DT",
      descending: true,
      page: 1,
      firstNum: 0,
      rowsPerPage: 5,
      rowsNumber: 0,
    });

    const month = ref("");
    const monthlyData = ref("");
    const week = ref("");
    const weeklyData = ref("");

    // const search_type = ref(null);
    // const search_keyword = ref(null);

    // emulate ajax call
    // SELECT * FROM ... WHERE...LIMIT...
    async function fetchFromServer(
      startRow,
      count,
      //filter,
      sortBy,
      descending
    ) {
      try {
        const response = await api.post("/fdgp/bpr/mainContentsList", {
          firstNum: startRow,
          once_display_count: count,
          sortBy: sortBy,
          descending: descending ? "desc" : "asc",
          userData: util.getUserData(),
        });
        //console.log(response);
        pagination.value.rowsNumber = response.cnt.TOTAL;
        rows.value = response.data;
      } catch (error) {
        console.error("조회중 에러발생===>", error);
        util.errorNotify(error.result_msg || config.getError);
      }

      try {
        const response = await api.post("/fdgp/dsps/mainContentsData", {});
        month.value = response.data.MONTH;
        monthlyData.value = response.data.MONTHLY_DATA;
        week.value = response.data.WEEK;
        weeklyData.value = response.data.WEEKLY_DATA;
        console.log(response.data);
      } catch (error) {
        console.error("조회중 에러발생===>", error);
        util.errorNotify(error.result_msg || config.getError);
      }
    }
    function onRequest(props) {
      //props.pagination에서 왜 page 값이 초기화 되는지??? => ex) 3페이지에서 다시 sort하면 1페이지로 이동함...
      //const { page, rowsPerPage, sortBy, descending } = props.pagination;
      const page = pagination.value.page;
      const { rowsPerPage, sortBy, descending } = props.pagination;
      loading.value = true;

      setTimeout(() => {
        //limit offset 계산
        const fetchCount =
          rowsPerPage === 0 ? pagination.value.rowsNumber : rowsPerPage;
        const startRow = (page - 1) * rowsPerPage;

        fetchFromServer(startRow, fetchCount, sortBy, descending);

        pagination.value.page = page;
        pagination.value.rowsPerPage = rowsPerPage;
        pagination.value.sortBy = sortBy;
        pagination.value.descending = descending;

        loading.value = false;
      }, 100);
    }

    function searchList() {
      //v-model로 keyword 가져오기
      onRequest({ pagination: pagination.value });
    }

    onMounted(() => {
      // get initial data from server (1st page)
      tableref.value.requestServerInteraction();
    });

    return {
      columns,
      visibleColumns,
      loading,
      rows,
      /* 페이지 */
      pagination,
      pagesNumber: computed(() =>
        Math.ceil(pagination.value.rowsNumber / pagination.value.rowsPerPage)
      ),
      onRequest,
      tableref,
      searchList,
      month,
      monthlyData,
      week,
      weeklyData,
    }; //return
  },
});

const columns = [
  { name: "PROCESS", label: "프로세스", field: "PROCESS" },
  {
    name: "PROD_DT",
    label: "제조일자",
    field: "PROD_DT",
    sortable: true,
  },
  {
    name: "FINAL_PROC",
    label: "문서",
    field: "FINAL_PROC",
    sortable: true,
  },
  {
    name: "PROD_NO",
    required: true,
    label: "제조번호",
    field: (row) => row.PROD_NO,
    sortable: true,
  },
  { name: "CHARGE_NM", label: "담당자", field: "CHARGE_NM" },
  { name: "CHARGE_DT", label: "담당자 확인일", field: "CHARGE_DT" },
  { name: "CONFIRM_NM", label: "확인자", field: "CONFIRM_NM" },
  { name: "CONFIRM_DT", label: "확인자 확인일", field: "CONFIRM_DT" },
];
const visibleColumns = [
  "PROD_DT",
  "FINAL_PROC",
  "PROD_NO",
  "CHARGE_NM",
  "CHARGE_DT",
  "CONFIRM_NM",
  "CONFIRM_DT",
];
</script>

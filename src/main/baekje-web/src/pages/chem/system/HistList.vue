<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>사용내역 조회</h2>
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
            <span>일자</span>
            <q-input filled type="date" v-model="startDate" /> ~
            <q-input filled type="date" v-model="endDate" />
          </dev>
          <!--<dev>
            <span>항목</span>
            <CmmnCode
              code="ITEM"
              name="code"
              v-model="itemCd"
              :all="true"
              @loadEnd="fnLoadEnd"
              class="w200"
            />
          </dev>-->
          <dev>
            <span>사용자</span>
            <q-input filled v-model="userNm" label="사용자" />
          </dev>
        </div>
      </q-banner>
    </q-page-container>
    <!-- main-notice S -->
    <div class="main-notice">
      <q-table
        flat
        :bordered="false"
        :rows="rows"
        :columns="columns"
        :loading="loading"
        row-key="name"
        v-model:pagination="pagination"
        binary-state-sort
        hide-pagination
        @request="onRequest"
        ref="tableref"
        class="hover-none"
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
  </section>
</template>

<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import { api } from "boot/axios";
import * as util from "src/support/util";

export default defineComponent({
  name: "IndexPage",

  setup() {
    /* 테이블 */
    const tableref = ref();
    const loading = ref(true);
    const rows = ref([]);
    /* 페이지 */
    const pagination = ref({
      sortBy: "LOG_DTM",
      descending: true,
      page: 1,
      firstNum: 0,
      rowsPerPage: 14,
      rowsNumber: 0,
    });

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
        const response = await api.post("/system/useHisList", {
          firstNum: startRow,
          once_display_count: count,
          sortBy: sortBy,
          descending: descending ? "desc" : "asc",
        });
        //console.log(response);
        pagination.value.rowsNumber = response.cnt.TOTAL;
        rows.value = response.data;
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
    }; //return
  },
});

const columns = [
  { name: "LOG_DTM", label: "작업 시각", field: "LOG_DTM" },
  { name: "URL_NM", label: "작업 내용", field: "URL_NM" },
  {
    name: "USER_ID",
    required: true,
    label: "유저ID",
    align: "left",
    //field: "USER_ID",
    field: (row) => row.USER_ID,
    sortable: true,
  },
  {
    name: "USER_NM",
    align: "center",
    label: "유저명",
    field: "USER_NM",
    sortable: true,
  },
];
</script>

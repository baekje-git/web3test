<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>사용자관리</h2>
      <div class="btn-wrap">
        <div class="btn-toast">
          <q-btn
            @click="searchList(1)"
            class="btn-point ico-search"
            label="조회"
          />
        </div>
      </div>
    </div>
    <q-page-container class="first-container">
      <q-banner dense :class="$q.dark.isActive ? '' : ''" class="search-wrap">
        <div class="q-gutter-md row items-start">
          <q-select
            filled
            name="search_type"
            v-model="search_type"
            :options="[
              { label: '유저ID', value: 'I' },
              { label: '유저명', value: 'N' },
            ]"
            style="width: 250px"
          />
          <CmmnCode
            code="STATUS"
            name="status_code"
            v-model="status_code"
            :all="true"
            @loadEnd="fnLoadEnd"
            style="width: 250px"
          />
          <q-input
            filled
            name="search_keyword"
            v-model="search_keyword"
            label="아이디를 입력해주세요"
            @keydown.enter="searchList(1)"
          />
        </div>
      </q-banner>
    </q-page-container>
    <q-page-container>
      <div class="main-notice">
        <q-table
          title="최신작업이력"
          flat
          :bordered="false"
          :rows="rows"
          :columns="columns"
          :loading="loading"
          row-key="__index"
          v-model:pagination="pagination"
          binary-state-sort
          hide-pagination
          @request="onRequest"
          ref="tableref"
        >
          <template #body-cell-MOD_BTN="props">
            <q-td :props="props">
              <q-btn
                dense
                flat
                round
                field="edit"
                icon="edit"
                @click="editItem(props.row)"
              ></q-btn>
            </q-td>
          </template>
        </q-table>

        <div class="q-pa-lg flex flex-center">
          <q-pagination
            v-model="pagination.page"
            :max="pagesNumber"
            :max-pages="10"
            direction-links
            boundary-links
            @click="searchList(pagination.page)"
          />
        </div>

        <q-dialog v-model="show_dialog">
          <q-card style="width: 600px; max-width: 60vw">
            <q-card-section>
              <q-btn
                round
                flat
                dense
                icon="close"
                class="float-right"
                v-close-popup
              ></q-btn>
              <div class="text-h6">사용자 상세정보</div>
            </q-card-section>
            <q-separator inset></q-separator>
            <q-card-section class="q-pt-none">
              <q-form class="q-gutter-md">
                <q-list>
                  <q-item>
                    <q-item-section>
                      <q-item-label>유저ID</q-item-label>
                      <q-input
                        :readonly="true"
                        dense
                        outlined
                        v-model="editedItem.USER_ID"
                      />
                    </q-item-section>
                  </q-item>
                  <q-item>
                    <q-item-section>
                      <q-item-label>유저명</q-item-label>
                      <q-input dense outlined v-model="editedItem.USER_NM" />
                    </q-item-section>
                  </q-item>
                  <q-item>
                    <q-item-section>
                      <q-item-label class="q-pb-xs"
                        >유저레벨(1,2,3)</q-item-label
                      >
                      <q-input dense outlined v-model="editedItem.USER_LVL" />
                    </q-item-section>
                  </q-item>
                  <q-item>
                    <q-item-section>
                      <q-item-label class="q-pb-xs">사용여부</q-item-label>
                      <q-checkbox
                        v-model="editedItem.USE_YN"
                        true-value="Y"
                        false-value="N"
                      />
                    </q-item-section>
                  </q-item>
                  <q-item>
                    <q-item-section>
                      <q-item-label class="q-pb-xs">비밀번호</q-item-label>
                      <q-input dense outlined v-model="editedItem.USER_PWD" />
                    </q-item-section>
                  </q-item>
                </q-list>
              </q-form>
            </q-card-section>
            <q-card-section>
              <q-card-actions align="right">
                <q-btn
                  flat
                  label="취소"
                  color="warning"
                  dense
                  v-close-popup
                ></q-btn>
                <q-btn
                  flat
                  label="확인"
                  color="primary"
                  dense
                  v-close-popup
                  @click="updateRow"
                ></q-btn>
              </q-card-actions>
            </q-card-section>
          </q-card>
        </q-dialog>
      </div>
    </q-page-container>
  </section>
</template>
<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import { useQuasar } from "quasar";
import { api } from "boot/axios";
import * as util from "src/support/util";
import CmmnCode from "components/CmmnCode.vue";

export default defineComponent({
  name: "SampleE",

  components: { CmmnCode },

  setup() {
    const q$ = useQuasar();
    /* 테이블 */
    const tableref = ref();
    const loading = ref(true);
    const rows = ref([]);
    /* 페이지 */
    const pagination = ref({
      sortBy: "",
      descending: false,
      page: 1,
      firstNum: 0,
      rowsPerPage: 5,
      rowsNumber: 0,
    });
    const editedIndex = ref(-1);
    const editedItem = ref({
      USER_ID: "",
      USER_NM: "",
      USER_LVL: "",
      USE_YN: "",
      USER_PWD: "",
    });
    const show_dialog = ref(false);
    //default label&value
    const search_type = ref({ label: "유저ID", value: "I" });
    const search_keyword = ref(null);
    //selectbox
    const status_code = ref(null);

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
        const params = {
          firstNum: startRow,
          once_display_count: count,
          sortBy: sortBy,
          descending: descending ? "desc" : "asc",
          search_keyword: search_keyword.value,
          search_type: util.isEmpty(search_type.value)
            ? ""
            : search_type.value.value,
        };

        //공통코드 데이터 확인

        const response = await api.post("/userList", params);
        pagination.value.rowsNumber = response.cnt.TOTAL;
        //row에 index
        rows.value = response.data.map((v, i) => ({ ...v, __index: i }));
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

    function searchList(page) {
      pagination.value.page = page;
      onRequest({ pagination: pagination.value });
    }

    function editItem(item) {
      editedIndex.value = rows.value.findIndex(
        (v, i) => v.__index === item.__index
      );
      editedItem.value = Object.assign({}, item);
      show_dialog.value = true;
    }

    function close() {
      show_dialog.value = false;
      setTimeout(() => {
        editedItem.value = defaultItem;
        editedIndex.value = -1;
      }, 300);
    }

    function updateRow() {
      //팝업에서 바로 db저장 후 list다시 호출하기
      //지금은 팝업에서 수정한 데이터를 list에 저장없이 보여주는걸로...
      rows.value.splice(editedIndex.value, 1, editedItem.value);
      q$.notify({
        type: "positive",
        message: `${editedItem.value.USER_ID}의 사용자정보가 수정되었습니다.`,
        timeout: 1000,
      });
    }

    function fnLoadEnd(option) {
      status_code.value = option;
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
      editItem,
      close,
      editedItem,
      updateRow,
      search_type,
      search_keyword,
      show_dialog,
      fnLoadEnd,
      status_code,
    }; //return
  },
});
const columns = [
  {
    name: "USER_ID",
    required: true,
    label: "유저ID",
    align: "center",
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
  {
    name: "USER_LVL",
    label: "유저레벨(1,2,3)",
    align: "center",
    field: "USER_LVL",
    sortable: true,
  },
  { name: "USE_YN", label: "사용여부", align: "center", field: "USE_YN" },
  { name: "USER_PWD", label: "비밀번호", align: "center", field: "USER_PWD" },
  {
    name: "MOD_BTN",
    align: "center",
    label: "수정",
    field: "",
  },
];
</script>
<style scoped></style>

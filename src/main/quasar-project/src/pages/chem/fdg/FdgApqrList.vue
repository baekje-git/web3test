<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>연간품질평가 조회</h2>
      <div class="btn-wrap">
        <div class="btn-toast">
          <q-btn
            @click="searchList"
            class="btn-point ico-search"
            label="조회"
          />
          &nbsp;
          <q-btn @click="excelDown" class="btn-point" label="엑셀" />
        </div>
      </div>
    </div>
    <q-page-container class="first-container">
      <q-banner dense :class="$q.dark.isActive ? '' : ''" class="search-wrap">
        <div class="q-gutter-md row items-start">
          <dev>
            <span>작성년도</span>
            <!--<q-input filled type="date" v-model="year" />-->
            <CmmnCode
              code="YEAR"
              name="code"
              v-model="year"
              :all="true"
              @loadEnd="fnLoadEnd"
              class="w100"
            />
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
        class="hover-none"
      >
      </q-table>
    </div>
  </section>
</template>
<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import { api } from "boot/axios";
import CmmnCode from "components/CmmnCode.vue";

export default defineComponent({
  name: "MainLayout",

  components: { CmmnCode },

  methods: {
    excelDown() {},
  },

  setup() {
    const loading = ref(true);
    /* 테이블 */

    const rows = ref([]);
    const year = ref("");
    const prodNo = ref("");

    function fnLoadEnd(option) {
      year.value = option;
    }

    const searchList = (page = 1) => {
      loading.value = true;
      return api
        .post("/fdg/apqr/fdgApqrList", {
          YEAR: year.value.value,
          PROD_NO: prodNo.value,
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
      year,
      prodNo,
      fnLoadEnd,
    };
  },
});
/* ///////그리드 스크립트 Start//////// */
const columns = [
  {
    name: "PROD_NO",
    required: true,
    label: "제조번호",
    field: (row) => row.PROD_NO,
    sortable: true,
  },
  { name: "C03", label: "반감기(분)", field: "C03" },
  { name: "E03", label: "pH", field: "E03" },
  { name: "D0601", label: "확인시험", field: "D0601" },
  { name: "D0602", label: "순도", field: "D0602" },
  { name: "B0401", label: "주피크1", field: "B0401" },
  { name: "B0402", label: "주피크2", field: "B0402" },
  { name: "J03", label: "잔류유기용매", field: "J03" },
  { name: "G04", label: "엔도톡신", field: "G04" },
  { name: "I03", label: "정량법", field: "I03" },
  { name: "E0104", label: "수율", field: "E0104" },
  { name: "C0304", label: "수량", field: "C0304" },
];
/* ///////그리드 스크립트 End //////// */
</script>

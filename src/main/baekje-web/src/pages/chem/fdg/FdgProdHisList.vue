<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>제조내역 조회</h2>
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
            <span>제조일자</span>
            <q-input filled type="date" v-model="startDate" />
            <q-input filled type="date" v-model="endDate" />
          </dev>
        </div>
      </q-banner>
    </q-page-container>
    <!--<div class="main-notice">
      <q-table
        flat
        :bordered="false"
        :rows="rows"
        :columns="columns"
        :loading="loading"
        row-key="PROD_NO"
        hide-pagination
        :rows-per-page-options="[0]"
      >
      </q-table>
    </div>-->
  </section>
</template>
<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import { api } from "boot/axios";
import { useQuasar } from "quasar";

export default defineComponent({
  name: "MainLayout",

  components: {},

  methods: {
    excelDown() {},
  },

  setup() {
    const popup = useQuasar();
    const $R = useQuasar();
    const loading = ref(true);
    /* 테이블 */

    const rows = ref([]);
    const startDate = ref("");
    const endDate = ref("");

    const searchList = (page = 1) => {
      loading.value = true;
      return api
        .post("/fdg/apqr/fdgApqrList", {
          YEAR: year.value,
          PROD_NO: prodNo.value,
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
      //searchList();
    });

    return {
      val: ref(true),
      /* ///////그리드 스크립트 Start//////// */
      columns,
      loading,
      rows,
      /* ///////그리드 스크립트 End//////// */
      searchList,
      startDate,
      endDate,
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
  { name: "A", label: "반감기(분)", field: "A" },
  { name: "B", label: "pH", field: "B" },
  { name: "C", label: "확인시험", field: "C" },
  { name: "D", label: "순도", field: "D" },
  { name: "E", label: "주피크1", field: "E" },
  { name: "F", label: "주피크2", field: "F" },
  { name: "G", label: "잔류유기용매", field: "G" },
  { name: "H", label: "엔도톡신", field: "H" },
  { name: "I", label: "정량법", field: "I" },
  { name: "J", label: "수율", field: "J" },
  { name: "K", label: "수량", field: "K" },
];
/* ///////그리드 스크립트 End //////// */
</script>
<style scoped></style>

<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>폐기 관리</h2>
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
            <span>제조일자</span>
            <q-input filled type="date" v-model="startProdDate" />~
            <q-input filled type="date" v-model="endProdDate" />
          </dev>
          <dev>
            <span>제조번호</span>
            <q-input filled v-model="prodNo" label="제조번호" />
          </dev>
          <dev>
            <span>폐기일자</span>
            <q-input filled type="date" v-model="startDelDate" />~
            <q-input filled type="date" v-model="endDelDate" />
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
import { useQuasar } from "quasar";

export default defineComponent({
  name: "MainLayout",

  components: {},

  methods: {
    onRegClick() {
      this.$router.push({
        path: "/fdg-dispose/regList",
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
    const startDelDate = ref("");
    const endDelDate = ref("");

    const searchList = (page = 1) => {
      loading.value = true;
      return api
        .post("/fdgp/bpr/fdgpBprDisposeList", {
          START_PROD_DT: startProdDate.value,
          END_PROD_DT: endProdDate.value,
          PROD_NO: prodNo.value,
          START_DEL_DT: startDelDate.value,
          END_DEL_DT: endDelDate.value,
          DEL_NY: "Y",
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
      startDelDate,
      endDelDate,
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
  { name: "FINAL_PROC", label: "최종 진행 문서", field: "FINAL_PROC" },
  { name: "DEL_DT", label: "폐기일자", field: "DEL_DT" },
  { name: "DEL_REASON", label: "폐기사유", field: "DEL_REASON" },
];
/* ///////그리드 스크립트 End //////// */
</script>
<style scoped></style>

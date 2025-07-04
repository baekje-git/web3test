<template>
  <section class="content">
    <div class="tit-wrap">
      <h2>수불 이력 조회</h2>
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
            <q-input filled type="date" v-model="startDate" />~
            <q-input filled type="date" v-model="endDate" />
          </dev>
          <dev>
            <span>재료</span>
            <CmmnCode
              code="ITEM"
              name="code"
              v-model="itemCd"
              :all="true"
              @loadEnd="fnLoadEnd1"
              class="w200"
            />
          </dev>
          <dev>
            <span>상태</span>
            <CmmnCode
              code="STATUS"
              name="code"
              v-model="status"
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
        row-key="ITEM_CD"
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
import CmmnCode from "components/CmmnCode.vue";

export default defineComponent({
  name: "MainLayout",

  components: { CmmnCode },

  setup() {
    const popup = useQuasar();
    const $R = useQuasar();
    const loading = ref(true);
    /* 테이블 */

    const rows = ref([]);
    const itemCd = ref("");
    const startDate = ref("");
    const endDate = ref("");
    const status = ref("");

    function fnLoadEnd1(option) {
      itemCd.value = option;
    }

    function fnLoadEnd2(option) {
      status.value = option;
    }

    const searchList = (page = 1) => {
      loading.value = true;
      return api
        .post("/fdgi/in/fdgiInUseHisList", {
          START_DATE: startDate.value,
          END_DATE: endDate.value,
          ITEM_CD: itemCd.value.value,
          STATUS: status.value.value,
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
      startDate,
      endDate,
      itemCd,
      status,
      fnLoadEnd1,
      fnLoadEnd2,
    };
  },
});
/* ///////그리드 스크립트 Start//////// */
const columns = [
  {
    name: "RECEIPT_DT",
    label: "일자",
    field: "RECEIPT_DT",
    sortable: true,
  },
  { name: "STATUS", label: "상태", field: "STATUS" },
  {
    name: "ITEM_CD",
    required: true,
    label: "재료코드",
    field: (row) => row.ITEM_CD,
    sortable: true,
  },
  { name: "ITEM_NM", label: "재료명", field: "ITEM_NM" },
  { name: "LOT_NO", label: "Lot NO", field: "LOT_NO" },
  { name: "IN_QTY", label: "입고수량", field: "IN_QTY" },
  { name: "USE_QTY", label: "사용수량", field: "USE_QTY" },
  { name: "LOT_QTY", label: "Lot재고", field: "LOT_QTY" },
  { name: "MST_QTY", label: "재고", field: "MST_QTY" },
];
/* ///////그리드 스크립트 End //////// */
</script>
